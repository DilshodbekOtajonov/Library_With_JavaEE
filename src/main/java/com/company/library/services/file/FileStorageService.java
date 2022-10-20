package com.company.library.services.file;

import com.company.library.domains.Uploads;
import com.company.library.dto.UploadsDTO;
import com.company.library.exceptions.NotFoundException;
import lombok.NonNull;

import javax.servlet.http.Part;
import java.io.File;
import java.util.List;


public interface FileStorageService {

    Uploads upload(Part part);

    File download(@NonNull String fileName);

    UploadsDTO get(@NonNull String fileName);

    List<Uploads> getAll();

    UploadsDTO getByPath(String path) throws NotFoundException;

    Uploads extractCover(Part book);
}
