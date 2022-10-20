package com.company.library.services.book;

import com.company.library.configs.ApplicationContextHolder;
import com.company.library.dao.AbstractDAO;
import com.company.library.dao.BookDAO;
import com.company.library.domains.Book;
import com.company.library.domains.Uploads;
import com.company.library.dto.BookCreateDTO;
import com.company.library.dto.BookUpdateDTO;
import com.company.library.enums.BookStatus;
import com.company.library.exceptions.NotFoundException;
import com.company.library.services.file.FileStorageServiceImpl;
import com.company.library.services.page.Pageable;
import com.company.library.utils.BaseUtils;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class BookServiceImpl extends AbstractDAO<BookDAO> implements BookService, Pageable {
    private static BookServiceImpl bookService;
    private final FileStorageServiceImpl fileStorageService = ApplicationContextHolder.getBean(FileStorageServiceImpl.class);

    private BookServiceImpl() {
        super(ApplicationContextHolder.getBean(BookDAO.class));
    }

    @Override
    public void create(BookCreateDTO dto, Part book) {
        CompletableFuture<Uploads> coverUpload = CompletableFuture.supplyAsync(() ->
                fileStorageService.extractCover(book)
        );
        CompletableFuture<Uploads> bookUpload = CompletableFuture.supplyAsync(() ->
                fileStorageService.upload(book)
        );
        try {
            dao.save(Book.builder()
                    .cover(coverUpload.get())
                    .name(dto.getName())
                    .file(bookUpload.get())
                    .author(dto.getAuthor())
                    .description(dto.getDescription())
                    .language(dto.getLanguage())
                    .genre(dto.getGenre())
                    .pageCount(dto.getPageCount())
                    .build());

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Book get(long id) {
        return null;
    }

    @Override
    public void delete(long id) {
        Book book = this.get(id);

    }

    @Override
    public void update(BookUpdateDTO dto) throws NotFoundException {
        Book book = dao.findById(dto.getId());
        if (book == null) {
            throw new NotFoundException("Book not found by id %s".formatted(dto.getId()));
        }
        if (Objects.nonNull(dto.getName())) {
            book.setName(dto.getName());
        }
        if (Objects.nonNull(dto.getDescription())) {
            book.setDescription(dto.getDescription());
        }
        if (Objects.nonNull(dto.getStatus())) {
            book.setStatus(dto.getStatus());
        }
        if (Objects.nonNull(dto.getDownloadCount())) {
            book.setDownloadCount(dto.getDownloadCount());
        }
        dao.update(book);
    }

    @Override
    public List<Book> getAll() {
        return dao.findAll();
    }

    @Override
    public List<Book> getAllByStatus(BookStatus pending) {
        if (pending == null) {
            throw new RuntimeException("Status can not be null");
        }
        return dao.findAllByStatus(pending);
    }

    @Override
    public List<Book> getAll(String searchQuery, Integer page, Integer limit) {
        if (searchQuery == null)
            searchQuery = "";
        if (page == null)
            page = 1;
        if (limit == null)
            limit = 10;
        return dao.findAll(searchQuery, page, limit).get();

    }

    public static BookServiceImpl getInstance() {
        if (bookService == null) {
            bookService = new BookServiceImpl();
        }
        return bookService;
    }


    @Override
    public Boolean hasNext(String search, Integer offset, Integer limit) {
        Integer size = dao.findNumberOfElementBySearch(search);
        return size > (offset * limit);
    }

    public Boolean hasNext(Book.Genre genre, Integer offset, Integer limit) {
        Integer size = dao.findNumberOfElementByGenre(genre);
        return size > (offset * limit);
    }

    @Override
    public Boolean hasPrevious(Integer offset) {
        return offset > 1;
    }

    @Override
    public Integer totalPage(String search, Integer limit) {
        Integer size = dao.findNumberOfElementBySearch(search);
        return size / limit;
    }

    public Integer totalPage(Book.Genre genre, Integer limit) {
        Integer size = dao.findNumberOfElementByGenre(genre);
        return size / limit;
    }

    public List<Book> getAllByGenre(Book.Genre searchGenre, Integer page, Integer limit) {
        if (searchGenre == null)
            throw new RuntimeException("Genre Not supplied");
        if (page == null)
            page = 1;
        if (limit == null)
            limit = 10;
        return dao.findAll(searchGenre, page, limit).get();
    }

    @Override
    public void updateDownloadCount(Long uploadFileId) {
        Book book = dao.findByUploadFileId(uploadFileId);
        book.setDownloadCount(book.getDownloadCount() + 1);
        dao.update(book);
    }
}
