package com.company.library.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/9/22 8:34 PM (Saturday)
 * lib16/IntelliJ IDEA
 */

@AllArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {
    private Long id;
    private String name;
    private String surname;
    private int email;

}
