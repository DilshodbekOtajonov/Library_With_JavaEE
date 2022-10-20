package com.company.library.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/14/22 3:15 PM (Thursday)
 * libraryEE/IntelliJ IDEA
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {

    private Integer number;
    private Integer totalPages;
    private Boolean hasPrevious;
    private Boolean hasNext;
}
