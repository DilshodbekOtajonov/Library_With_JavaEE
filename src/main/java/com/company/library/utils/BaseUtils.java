package com.company.library.utils;

import com.company.library.configs.PasswordConfigurer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseUtils {
    private static BaseUtils instance;

    public String encode(String rawPassword) {
        return PasswordConfigurer.encode(rawPassword);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return PasswordConfigurer.matchPassword(rawPassword, encodedPassword);

    }

    public static BaseUtils getInstance() {
        if (instance == null) {
            instance = new BaseUtils();
        }
        return instance;
    }
}
