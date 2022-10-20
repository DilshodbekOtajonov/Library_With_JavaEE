package com.company.library.dto;


import com.company.library.enums.BookStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdateDTO {
    private Long id;
    private String name;
    private String description;
    private Integer downloadCount;
    private BookStatus status;
}
