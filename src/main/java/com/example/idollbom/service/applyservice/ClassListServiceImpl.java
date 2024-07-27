package com.example.idollbom.service.applyservice;

import com.example.idollbom.domain.dto.applydto.ClassListDTO;
import com.example.idollbom.domain.dto.recommend.PagedResponse;
import com.example.idollbom.mapper.applymapper.ClassListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassListServiceImpl implements ClassListService {
    private final ClassListMapper classListMapper;

    @Override
    public List<ClassListDTO> findAllClass(String category, int page, int pageSize) {
        int startRow = (page - 1) * pageSize;
        int endRow = page * pageSize;

        return classListMapper.findAllClass(category, startRow, endRow);
    }

    @Override
    public int classCount(String category) {
        return classListMapper.classCount(category);
    }

    @Override
    public PagedResponse<ClassListDTO> searchClassList(String searchType, String searchWord, String category, int page, int pageSize) {
        int startRow = (page - 1) * pageSize;
        int endRow = page * pageSize;

        int totalBoards = classListMapper.countClasses(category, searchType, searchWord);
        int totalPages = (int) Math.ceil((double) totalBoards / pageSize);

        List<ClassListDTO> classLisDTO = classListMapper.searchClassList(searchType, searchWord, category, startRow, endRow);

        return new PagedResponse<>(classLisDTO, page, totalPages, pageSize, totalBoards);
    }

    @Override
    public int countClasses(String category, String searchType, String searchWord) {
        return classListMapper.countClasses(category, searchType, searchWord);
    }
}
