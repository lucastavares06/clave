package com.lucas.clave.auth.account.model;

import com.lucas.clave.auth.user.entity.Role;
import com.lucas.clave.auth.user.model.UserCreate;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest implements UserCreate {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    @NotNull
    private Role role;
}
