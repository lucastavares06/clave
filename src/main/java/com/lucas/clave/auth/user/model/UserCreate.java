package com.lucas.clave.auth.user.model;

import com.lucas.clave.auth.user.entity.Role;

public interface UserCreate {
    String getName();
    String getEmail();
    String getPassword();
    Role getRole();
}
