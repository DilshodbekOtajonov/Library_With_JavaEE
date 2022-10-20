package com.company.library.configs;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/10/22 9:23 AM (Sunday)
 * lib16/IntelliJ IDEA
 */
public class PasswordConfigurer {
    public static String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
    }

    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }

}
