package com.company.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/13/22 3:48 PM (Wednesday)
 * libraryEE/IntelliJ IDEA
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ErrorDto {
    private String message;
    private String detailedMessage;
    private String path;
    @Builder.Default
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
}
