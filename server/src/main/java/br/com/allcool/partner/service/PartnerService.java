package br.com.allcool.partner.service;

import br.com.allcool.converter.PartnerDTOConverter;
import br.com.allcool.converter.PartnerViewDTOConverter;
import br.com.allcool.dto.PartnerDTO;
import br.com.allcool.dto.PartnerViewDTO;
import br.com.allcool.exception.DataNotFoundException;
import br.com.allcool.partner.repository.PartnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PartnerService {

    private final PartnerRepository repository;

    public PartnerService(PartnerRepository repository) {
        this.repository = repository;
    }

    public PartnerViewDTO findById(UUID id) {

        return new PartnerViewDTOConverter().to(this.repository.findById(id).orElseThrow(DataNotFoundException::new));
    }

    public List<PartnerDTO> findAll() {

        PartnerDTOConverter converter = new PartnerDTOConverter();

        List<PartnerDTO> partnerDTOList = this.repository.findAll()
                .stream().map(converter::to).collect(Collectors.toList());

        return partnerDTOList.stream().sorted(Comparator.comparing(PartnerDTO::getName)).collect(Collectors.toList());
    }

}
