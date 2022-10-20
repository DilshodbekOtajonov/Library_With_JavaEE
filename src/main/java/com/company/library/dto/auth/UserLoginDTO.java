package com.company.library.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/9/22 8:29 PM (Saturday)
 * lib16/IntelliJ IDEA
 */

@AllArgsConstructor
@Data
public class UserLoginDTO {
    private String email;
    private String password;
}
