package com.example.idollbom.service.loginservice;

import com.example.idollbom.domain.dto.logindto.ProDTO;
import com.example.idollbom.domain.vo.ProVO;
import com.example.idollbom.mapper.loginmapper.ProMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProServiceImpl implements ProService {

    private final ProMapper proMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ProVO selectPro(String proId) {
        return proMapper.selectPro(proId);
    }

    // 전문가 회원가입 서비스
    @Override
    public void savePro(ProDTO proDTO) {
        // 비밀번호 암호화
        proDTO.setProPassword(bCryptPasswordEncoder.encode(proDTO.getProPassword()));

        ProVO pro = ProVO.toEntity(proDTO);
        proMapper.insert(pro);
    }
}