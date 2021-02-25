package br.com.allcool.converter;

import br.com.allcool.dto.FileDTO;
import br.com.allcool.dto.UserClientDTO;
import br.com.allcool.user.domain.UserClient;

import static java.util.Objects.isNull;

public class UserClientDTOConverter {

    public UserClientDTO to(UserClient entity) {

        UserClientDTO dto = new UserClientDTO();

        if (isNull(entity)) {
            return dto;
        }

        dto.setId(entity.getId());
        dto.setBio(entity.getBio());
        dto.setLocation(entity.getLocation());
        dto.setName(entity.getPerson().getName());
        dto.setBirthDate(entity.getPerson().getBirthDate());
        dto.setUserPicture(new FileDTO(entity.getFile().getId(), entity.getFile().getUrl()));

        return dto;
    }
}
