package br.com.allcool.file.resource;

import br.com.allcool.file.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileResource {

    private final FileService service;

    public FileResource(FileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createFile(@RequestParam("image") MultipartFile file) throws IOException {

        this.service.createImage(file, "files", UUID.randomUUID());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
