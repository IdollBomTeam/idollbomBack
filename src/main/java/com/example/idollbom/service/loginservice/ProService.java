package com.example.idollbom.service.loginservice;

import com.example.idollbom.domain.dto.logindto.ProDTO;
import com.example.idollbom.domain.vo.ProVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ProService {

    // 프로 pk 가져오기
    ProVO selectPro(String proId);

    // 전문가 회원가입 서비스
    void savePro (ProDTO proDTO, MultipartFile file, MultipartFile proImg);

    String proInsertImg(MultipartFile img);

    String proInsertFile(MultipartFile file);

    String selectEmail(String proEmail);
}
