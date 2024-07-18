package com.example.idollbom.domain.dto.applydto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class ClassListDTO {
    private Long proNumber;
    private String proProfileImageUrl;
    private String proName;
    private String proAddress;
    private Long classNumber;
    private String className;
    private String classIntro;
    private String classCategorySmall;
    private String classContent;
    private String classRegisterDate;
    private Long reviewCount;
}
