package com.company.library.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/9/22 5:25 PM (Saturday)
 * lib16/IntelliJ IDEA
 */
@AllArgsConstructor
@Getter
public enum UserStatus {
    USER(50),
    ADMIN(75),
    SUPER_ADMIN(100);
    private final Integer priority;
}
