package com.example.idollbom.service.myPageservice.parentservice;

import com.example.idollbom.domain.dto.logindto.ParentDTO;
import com.example.idollbom.domain.vo.ParentVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface parentInfoService {
    public ParentVO selectParentInfo();

    public void update(ParentDTO parentDTO);

    public String updateImg(MultipartFile file);

    public void updatePassword(String password);

    public ParentVO selectParent(Long parentNumber);
}
