package com.company.library.dto;


import com.company.library.domains.Book;
import com.company.library.enums.Language;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCreateDTO {
    private String name;
    private String description;
    private String author;
    private Book.Genre genre;
    private Language language;
    private int pageCount;
}
