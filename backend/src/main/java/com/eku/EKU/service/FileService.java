package com.eku.EKU.service;

import com.eku.EKU.domain.Image;
import com.eku.EKU.domain.InfoBoard;
import com.eku.EKU.repository.ImageRepository;
import com.eku.EKU.repository.InfoBoardRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class FileService {
    private final ImageRepository imageRepository;
    private final InfoBoardRepository infoBoardRepository;

    @Value("${server.tomcat.document-root}")
    private String uploadDir;

    public FileService(ImageRepository imageRepository, InfoBoardRepository infoBoardRepository) {
        this.imageRepository = imageRepository;
        this.infoBoardRepository = infoBoardRepository;
    }

    /**
     * 파일의 이름을 변경하여 저장하는 메소드
     * @param multipartFile
     * @param id
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws NoSuchElementException
     */
    public void fileUpload(MultipartFile multipartFile, Long id) throws IOException, IllegalArgumentException, NoSuchElementException {
        UUID uuid = UUID.randomUUID();
        String uploadFileName = uuid.toString() + "_" + multipartFile.getOriginalFilename();

        //파일 형식 검사
        if(!multipartFile.isEmpty()) {
            String contentType = multipartFile.getContentType();
            if (ObjectUtils.isEmpty(contentType)) {
                throw new IllegalArgumentException();
            } else {
                if (contentType.contains("image/jpeg") || contentType.contains("image/png") || contentType.contains("image/gif")) {
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }

        Path serverPath = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(uploadFileName));
        Files.copy(multipartFile.getInputStream(), serverPath, StandardCopyOption.REPLACE_EXISTING);

        InfoBoard article = infoBoardRepository.getById(id);
        Image image = Image.builder()
                .fileName(uploadFileName)
                .path(serverPath.toString())
                .infoBoard(article)
                .build();
        imageRepository.save(image);
    }

    /**
     *
     * @param articleId 공지게시물 id
     * @return 해당 게시물에 첨부된 사진의 경로 list
     */
    public List<String> imageList(Long articleId) throws IOException {
        InfoBoard infoBoard = infoBoardRepository.getById(articleId);
        List<Image> imageList = imageRepository.findAllByInfoBoard(infoBoard);
        List<String> pathList = new ArrayList<>();
        for(Image i : imageList){
            String out = i.getPath();
            pathList.add(out);
        }
        return pathList;
    }
}
