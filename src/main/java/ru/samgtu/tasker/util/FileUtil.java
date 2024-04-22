package ru.samgtu.tasker.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class FileUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);
    public File convert(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        file.transferTo(convertedFile);
        return convertedFile;
    }
    public File convertMultiPartFileToFile(final MultipartFile multipartFile) {

        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream outputStream = new FileOutputStream(file);
             InputStream inputStream = multipartFile.getInputStream()) {
            byte[] buffer = new byte[8192]; // Увеличим размер буфера до 8 КБ
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println(file.getName());
            return file;
        } catch (IOException e) {
            LOG.error("Error occurred while converting the multipart file", e);
            // Если произошла ошибка, удаляем файл
            if (file.exists()) {
                file.delete();
            }
            return null; // Возвращаем null в случае ошибки
        }
    }
}
