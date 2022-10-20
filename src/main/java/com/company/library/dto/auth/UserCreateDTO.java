package com.company.library.dto.auth;


import lombok.*;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/9/22 5:31 PM (Saturday)
 * lib16/IntelliJ IDEA
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserCreateDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
}
