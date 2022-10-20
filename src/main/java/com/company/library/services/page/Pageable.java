package com.company.library.services.page;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/14/22 3:24 PM (Thursday)
 * libraryEE/IntelliJ IDEA
 */
public interface Pageable {
    Boolean hasNext(String search,Integer offset,Integer limit);

    Boolean hasPrevious(Integer offset);

    Integer totalPage(String search,Integer limit);
}
