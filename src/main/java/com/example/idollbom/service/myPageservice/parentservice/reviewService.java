package com.example.idollbom.service.myPageservice.parentservice;

import com.example.idollbom.domain.dto.parentdto.ReviewDTO;
import com.example.idollbom.domain.dto.prodto.ProDetailDTO;
import com.example.idollbom.domain.vo.reviewVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface reviewService {
    List<reviewVO> selectRiviewList();

    void insertReview(ReviewDTO reviewDTO);
}
