package br.com.municipios.entity.auth.dto;

import br.com.municipios.service.AuthService;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private AuthService.UserRole role;

    public UserResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }

}