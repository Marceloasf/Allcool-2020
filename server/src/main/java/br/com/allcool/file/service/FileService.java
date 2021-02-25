package br.com.allcool.file.service;

import br.com.allcool.file.domain.File;
import br.com.allcool.file.repository.FileRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@Service
public class FileService {

    private static final String JPG = ".jpg";
    private static final String PNG = ".png";
    private static final String JPEG = ".jpeg";
    private static final String FILE_FORMAT_REGEX = "\\.[^.]{1,10}$";
    private final AmazonS3 s3Bucket;
    private final FileRepository repository;
    private final Path rootLocation = Paths.get("tmp-folder");
    private final List<String> allowedExtensions = Arrays.asList(JPG, JPEG, PNG);

    @Value("${s3.baseUrl}")
    private String baseUrl;

    public FileService(AmazonS3 s3Bucket, FileRepository repository) {
        this.s3Bucket = s3Bucket;
        this.repository = repository;
    }

    @PostConstruct
    public void init() throws IOException {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
        Files.createDirectories(rootLocation);
    }

    public File createImage(MultipartFile file, String folderName, UUID entityId) throws IOException {

        if (file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
            throw new RuntimeException("Failed to store empty file ");
        }

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

        if (originalFilename.contains("..")) {
            // This is a security check
            throw new RuntimeException(
                    "Cannot store file with relative path outside current directory "
                            + originalFilename);
        }

        if (allowedExtensions.stream().noneMatch(originalFilename::contains)) {
            throw new RuntimeException("São permitidos apenas arquivos com a extensão jpg, jpeg ou png");
        }

        java.io.File createdFile = java.io.File.createTempFile("tmp", "", this.rootLocation.toFile());

        try (FileOutputStream output = new FileOutputStream(createdFile)) {
            IOUtils.copy(file.getInputStream(), output);
        }

        String s3PathName = folderName.concat("/").concat(entityId.toString())
                .concat("/").concat(file.getOriginalFilename());

        String url = baseUrl + s3PathName;

        PutObjectRequest putObjectRequest = new PutObjectRequest
                ("allcool", s3PathName, createdFile)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        s3Bucket.putObject(putObjectRequest);

        Pattern pattern = Pattern.compile(FILE_FORMAT_REGEX);

        Matcher matcher = pattern.matcher(file.getOriginalFilename());

        String prefix = "";

        if (matcher.find()) {
            prefix = matcher.group();
        }

        br.com.allcool.file.domain.File newFile = new br.com.allcool.file.domain.File();
        newFile.setDate(LocalDateTime.now());
        newFile.setName(file.getOriginalFilename().replace(prefix, ""));
        newFile.setType(prefix);
        newFile.setUrl(url);

        if (!createdFile.delete()) {
            log.error("Ocorreu um erro ao remover o arquivo {}, path: {}", createdFile.getName(), createdFile.getPath());
        }

        return this.repository.save(newFile);
    }
}
