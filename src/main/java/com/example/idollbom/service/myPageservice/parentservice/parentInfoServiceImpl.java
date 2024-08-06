package com.example.idollbom.service.myPageservice.parentservice;

import com.example.idollbom.domain.dto.logindto.ParentDTO;
import com.example.idollbom.domain.dto.prodto.ClassImgDTO;
import com.example.idollbom.domain.vo.ParentVO;
import com.example.idollbom.mapper.boardmapper.ParentFileMapper;
import com.example.idollbom.mapper.loginmapper.ParentMapper;
import com.example.idollbom.mapper.myPagemapper.parentmapper.infoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class parentInfoServiceImpl implements parentInfoService {
    private final ParentMapper parentMapper;
    private final infoMapper infoMapper;
    private final ParentFileMapper parentFileMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public ParentVO selectParentInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUserName = userDetails.getUsername();

//      parent VO 찾아서 아이디 찾기
        ParentVO parent = parentMapper.selectOne(currentUserName);

        return infoMapper.selectMyInfo(parent.getParentNumber());
    }

    @Override
    public void update(ParentDTO parentDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUserName = userDetails.getUsername();

//      parent VO 찾아서 아이디 찾기
        ParentVO parent = parentMapper.selectOne(currentUserName);

        parentDTO.setParentNumber(parent.getParentNumber());
        parentDTO.setParentEmail(parent.getParentEmail());
        parentDTO.setParentPassword(parent.getParentPassword());
        parentDTO.setParentProfileImageUrl(parent.getParentProfileImageUrl());
        parentDTO.setParentReportCount(parent.getParentReportCount());
        log.info(parentDTO.getParentAddress());
        log.info(parentDTO.getParentName());
        parentMapper.updateInfo(ParentVO.toEntity(parentDTO));
    }

    @Override
    public String updateImg(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        String imgName = file.getOriginalFilename();

        if (imgName == null || imgName.trim().isEmpty()) {
            System.out.println("파일 이름이 유효하지 않습니다.");
            return null;
        }

        // 파일 이름에서 경로 구분 기호를 안전하게 처리
        imgName = imgName.replaceAll("[/:*?\"<>|]", "_");

        // 파일 이름 유효성 검사
        try {
            Paths.get(imgName); // 파일 이름이 유효한지 검사
        } catch (InvalidPathException e) {
            System.out.println("유효하지 않은 파일 이름입니다: " + imgName);
            return null;
        }

        System.out.println(imgName);

        try {
            Path directoryPath = Paths.get("src/main/resources/static/backImage/parent/profile/");
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path filePath = directoryPath.resolve(imgName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // url 지정
            String imageUrl = "/backImage/parent/profile/" + imgName;

            //      parent VO 찾아서 아이디 찾기
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String currentUserName = userDetails.getUsername();

                ParentVO parent = parentMapper.selectOne(currentUserName);

                parentFileMapper.updatePic(imageUrl,parent.getParentNumber());

                return imageUrl;


            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public void updatePassword(String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUserName = userDetails.getUsername();

//      parent VO 찾아서 아이디 찾기
        ParentVO parent = parentMapper.selectOne(currentUserName);

        infoMapper.updatePassword(parent.getParentNumber(),bCryptPasswordEncoder.encode(password));
    }

    @Override
    public ParentVO selectParent(Long parentNumber) {
        return null;
    }
}
