package com.clothesdelivery.web.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public final class FileService {
    public static @NotNull String saveFile(@NotNull MultipartFile file, String uploadDir, String fileName) throws IOException {
        var newFileName = fileName + Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        var path = Paths.get(uploadDir, newFileName);

        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
        }

        file.transferTo(path);
        return newFileName;
    }
}
