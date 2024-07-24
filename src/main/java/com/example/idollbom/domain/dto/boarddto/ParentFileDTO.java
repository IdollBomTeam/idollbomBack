package com.example.idollbom.domain.dto.boarddto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParentFileDTO {
    private Long parentFileNumber;
    private String parentFileName;
    private String parentFileOriginName;
    private Long parentFileSize;
    private LocalDateTime parentFileUploadTime;
    private Long parentPostNumber;
}
