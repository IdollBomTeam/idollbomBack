package com.example.idollbom.controller.apply;

import com.example.idollbom.domain.dto.applydto.ClassListDTO;
import com.example.idollbom.domain.dto.recommend.PagedResponse;
import com.example.idollbom.service.applyservice.ClassListService;
import com.example.idollbom.service.myPageservice.parentservice.classSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restList")
@Slf4j
public class ClassListRestController {

    private final ClassListService classListService;
    private final classSaveService classSaveService;

    @GetMapping
    public ResponseEntity<PagedResponse<ClassListDTO>> getBoardList(@RequestParam(defaultValue = "1") int pageNo,
                                                                    @RequestParam(defaultValue = "5") int pageSize,
                                                                    @RequestParam(value = "category", defaultValue = "등/하원") String category,
                                                                    @RequestParam String searchType,
                                                                    @RequestParam String searchWord) {

        return ResponseEntity.ok(classListService.searchClassList(searchType, searchWord, category, pageNo, pageSize));
    }

    // 찜 목록 추가 비동기 구현
    @PostMapping
    public ResponseEntity<?> insertClassSave(@RequestParam("classNumber") Long classNumber,
                                             @RequestParam("parentNumber") Long parentNumber){

        log.info("비동기로 뿌려진 데이터에서 넘어온 데이터들 : ");
        log.info("classNumber : " + classNumber);
        log.info("parentNumber : " + parentNumber);

        // 비회원/ 회원에 따라 화면에 버튼의 유무를 결정
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // 회원이라면 찜 목록 추가
        if (authentication != null) {
            // 수업 찜 추가 쿼리문 실행
            return ResponseEntity.ok(classSaveService.saveClass(classNumber, parentNumber));
        }

        return ResponseEntity.ok().build();
    }
    
    // 찜 목록 삭제 비동기 구현
    @DeleteMapping
    public ResponseEntity<?> deleteClassSave(@RequestParam("classNumber") Long classNumber,
                                             @RequestParam("parentNumber") Long parentNumber){

        // 비회원/ 회원에 따라 화면에 버튼의 유무를 결정
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 회원만 삭제 가능
        if (authentication != null) {
            return ResponseEntity.ok(classSaveService.deleteSaveClass(classNumber, parentNumber));
        }

        return ResponseEntity.ok().build();

    }

}
