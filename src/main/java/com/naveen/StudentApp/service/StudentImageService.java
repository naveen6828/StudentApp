package com.naveen.StudentApp.service;

import com.naveen.StudentApp.entity.StudentImage;
import com.naveen.StudentApp.repository.StudentImageRepository;
import com.naveen.StudentApp.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentImageService {

    private final StudentImageRepository studentImageRepository;
    private final String FOLDER_PATH = "C:\\Files\\Courses\\Suneel Tasks\\StudentImages\\";

//    @Transactional
    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH+file.getOriginalFilename();
        StudentImage studentImage = studentImageRepository.save(StudentImage.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                        .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());
        file.transferTo(new File(filePath));
        if (studentImage != null){
            return "File Uploaded Successfully";
        }
        return null;
    }

    // LOBs cannot be used in auto-commit mode because they require more complex handling due to their size and storage location.
    // To work with LOBs, you should operate within a transaction context.
    // If you’re using Hibernate and Spring, ensure that your service methods are annotated with @Transactional.
    // This annotation ensures that the entire method runs within a single transaction.
    @Transactional
    public byte[] downloadImageFromFileSystem(String filename) throws IOException {
        Optional<StudentImage> studentImage = studentImageRepository.findByName(filename);
        String filePath = studentImage.get().getFilePath();
        byte[] image = Files.readAllBytes(new File(filePath).toPath());
        return image;
    }

    @Transactional
    public byte[] downloadImageFromDataBase(String filename){
        Optional<StudentImage> studentImage = studentImageRepository.findByName(filename);
        byte[] image = ImageUtils.decompressImage(studentImage.get().getImageData());
        return image;
    }
}
