package com.company.library.dto.auth;

import com.company.library.enums.UserStatus;
import lombok.*;


/**
 * @author "Otajonov Dilshodbek
 * @since 7/9/22 8:43 PM (Saturday)
 * lib16/IntelliJ IDEA
 */

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private UserStatus status;
}
