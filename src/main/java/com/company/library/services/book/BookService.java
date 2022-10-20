package com.company.library.services.book;

import com.company.library.domains.Book;
import com.company.library.dto.BookCreateDTO;
import com.company.library.dto.BookUpdateDTO;
import com.company.library.enums.BookStatus;
import com.company.library.exceptions.NotFoundException;

import javax.servlet.http.Part;
import java.util.List;


public interface BookService {
    void create(BookCreateDTO dto, Part book);

    Book get(long id);

    void delete(long l);

    void update(BookUpdateDTO dto) throws NotFoundException;


    List<Book> getAll();

    List<Book> getAll(String searchQuery, Integer page, Integer limit);

    List<Book> getAllByStatus(BookStatus pending);

    void updateDownloadCount(Long uploadFileId);
}
