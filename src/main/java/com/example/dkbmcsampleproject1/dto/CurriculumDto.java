package com.example.dkbmcsampleproject1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CurriculumDto {
    private String curriculumPk; // 교육 과정 기본 키
    private String manageUserPk; // 관리 사용자 기본 키
    private String curriculumName; // 교육 과정 이름
    private String curriculumTypeCode; // 교육 과정 유형 코드
    private String fromDate; // 시작 날짜
    private String toDate; // 종료 날짜
    private String useYn; // 사용 여부
    private String createUserPk; // 생성 사용자 기본 키
    private LocalDateTime createDatetime; // 생성 일시
    private String updateUserPk; // 수정 사용자 기본 키
    private LocalDateTime updateDatetime; // 수정 일시

}
