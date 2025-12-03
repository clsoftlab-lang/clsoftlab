package com.example.clsoftlab;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.clsoftlab.entity.EmployeeFamily;
import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.hr.EmployeeFamilyRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class EmployeeFamilyTest {

    @Autowired
    private EmployeeMasterRepository employeeMasterRepository;

    @Autowired
    private EmployeeFamilyRepository employeeFamilyRepository;

    @Test
    @DisplayName("사원 가족정보 더미 데이터 적재 (암호화 자동 적용)")
    @Transactional
    @Commit // 롤백 방지
    void initEmployeeFamilyData() {
        // 기존 데이터 삭제 (중복 방지)
        employeeFamilyRepository.deleteAllInBatch();

        List<EmployeeFamily> familyList = new ArrayList<>();

        // ============================================================
        // [1] 2018001 양한재 (부장) : 4인 가족 (배우자, 대1, 고2)
        // ============================================================
        addFamily(familyList, "2018001", 1, "10", "이수진", "780320-2111111", "F", "50", null, null, "Y", "Y", "Y", "Y", "N", "010-1111-2222", "전업주부");
        addFamily(familyList, "2018001", 2, "20", "양준혁", "030515-3222222", "M", "40", "UNI", "서울대학교", "Y", "N", "Y", "Y", "N", "010-3333-4444", "장남");
        addFamily(familyList, "2018001", 3, "20", "양서윤", "070820-4333333", "F", "40", "HIG", "경기고등학교", "Y", "Y", "Y", "Y", "N", null, "장녀");

        // ============================================================
        // [2] 2201001 김철수 (퇴직자) : 배우자만 존재
        // ============================================================
        addFamily(familyList, "2201001", 1, "10", "김미영", "800505-2999999", "F", "50", null, null, "Y", "Y", "Y", "Y", "N", null, "배우자");

        // ============================================================
        // [3] 2203004 최서연 (과장) : 워킹맘 (배우자 맞벌이, 유치원생)
        // ============================================================
        addFamily(familyList, "2203004", 1, "10", "박지훈", "850110-1444444", "M", "10", null, null, "Y", "N", "N", "N", "N", "010-5555-6666", "배우자(근로소득)");
        addFamily(familyList, "2203004", 2, "20", "박소은", "190305-4555555", "F", "99", "KIN", "햇살유치원", "Y", "Y", "Y", "Y", "N", null, "자녀(유치원)");

        // ============================================================
        // [4] 2402024 백시원 (사원) : 부모님 동거 (부:사업자, 모:주부)
        // ============================================================
        addFamily(familyList, "2402024", 1, "30", "백종원", "650210-1666666", "M", "20", null, null, "Y", "N", "N", "Y", "N", "010-7777-8888", "부(사업자)");
        addFamily(familyList, "2402024", 2, "31", "정유미", "681125-2777777", "F", "50", null, null, "Y", "Y", "Y", "Y", "N", null, "모(주부)");

        // ============================================================
        // [5] 2207005 정지훈 : 다자녀 (세 쌍둥이) - 리스트 테스트용
        // ============================================================
        addFamily(familyList, "2207005", 1, "10", "최유리", "880101-2123456", "F", "10", null, null, "Y", "N", "N", "N", "N", "010-9876-5432", "배우자(맞벌이)");
        addFamily(familyList, "2207005", 2, "20", "정대한", "180505-3011111", "M", "99", "KIN", "숲속유치원", "Y", "Y", "Y", "Y", "N", null, "장남(쌍둥이1)");
        addFamily(familyList, "2207005", 3, "20", "정민국", "180505-3022222", "M", "99", "KIN", "숲속유치원", "Y", "Y", "Y", "Y", "N", null, "차남(쌍둥이2)");
        addFamily(familyList, "2207005", 4, "20", "정만세", "180505-3033333", "M", "99", "KIN", "숲속유치원", "Y", "Y", "Y", "Y", "N", null, "삼남(쌍둥이3)");

        // ============================================================
        // [6] 2210009 한지우 : 한부모 가정
        // ============================================================
        addFamily(familyList, "2210009", 1, "20", "한바다", "150910-4111111", "F", "99", "ELE", "서울초등학교", "Y", "Y", "Y", "Y", "N", null, "자녀(한부모)");

        // ============================================================
        // [7] 2201002 이영희 : 기러기 가족 (비동거)
        // ============================================================
        addFamily(familyList, "2201002", 1, "10", "박철민", "821111-1000000", "M", "20", null, null, "N", "N", "N", "N", "N", "010-0000-0000", "배우자(해외)");
        addFamily(familyList, "2201002", 2, "20", "박민지", "120202-4000000", "F", "40", "MID", "International Sch", "N", "Y", "N", "Y", "N", null, "자녀(유학)");

        // ============================================================
        // [8] 2305016 안태현 : 조부모 부양
        // ============================================================
        addFamily(familyList, "2305016", 1, "32", "안상수", "380101-1123123", "M", "60", null, null, "Y", "Y", "Y", "Y", "N", null, "조부");
        addFamily(familyList, "2305016", 2, "33", "김말순", "411231-2123123", "F", "60", null, null, "Y", "Y", "Y", "Y", "N", null, "조모");

        // ============================================================
        // [9] 2308017 문채원 : 위탁 아동
        // ============================================================
        addFamily(familyList, "2308017", 1, "90", "김희망", "160101-3000000", "M", "40", "ELE", "행복초등학교", "Y", "Y", "Y", "Y", "N", null, "위탁아동");

        // ============================================================
        // [10] 2401021 유정민 : 형제자매 (가장 역할)
        // ============================================================
        addFamily(familyList, "2401021", 1, "40", "유정현", "050505-3000000", "M", "60", "HIG", "고등학교", "Y", "Y", "N", "Y", "N", null, "남동생");

        // ============================================================
        // [11] 2508050 함유주 : 부모님 별거 (시골 거주)
        // ============================================================
        addFamily(familyList, "2508050", 1, "30", "함석규", "650101-1000000", "M", "20", null, null, "N", "N", "N", "N", "N", null, "부(별거/농업)");
        addFamily(familyList, "2508050", 2, "31", "김사부", "680101-2000000", "F", "50", null, null, "N", "Y", "Y", "Y", "N", null, "모(별거/소득없음)");

        // ============================================================
        // [12] 2404026 차유진 : 장애인 형제
        // ============================================================
        addFamily(familyList, "2404026", 1, "33", "최순자", "400101-2000000", "F", "60", null, null, "Y", "Y", "Y", "Y", "N", null, "조모");
        addFamily(familyList, "2404026", 2, "40", "차은우", "020505-3111111", "M", "40", "UNI", "연세대학교", "Y", "Y", "Y", "Y", "Y", null, "동생(장애인)");
        
        // [13] 2405028 기태영 : 장애인 배우자
        addFamily(familyList, "2405028", 1, "10", "유진", "810303-2000000", "F", "50", null, null, "Y", "Y", "Y", "Y", "Y", "010-5678-5678", "배우자(장애인)");


        // 일괄 저장 (Entity의 @Convert로 인해 이 시점에 SSN 암호화됨)
        employeeFamilyRepository.saveAll(familyList);
        System.out.println(">>> 가족 데이터 " + familyList.size() + "건 적재 완료!");
    }

    /**
     * 가족 데이터 생성 헬퍼 메서드
     */
    private void addFamily(List<EmployeeFamily> list, String pernr, Integer seq, String typeCode, 
                           String name, String ssn, String gender, 
                           String jobCode, String schoolCode, String schoolName,
                           String liveYn, String dependYn, String allowYn, String taxYn, String disabledYn,
                           String phone, String note) {

        // 1. 사원 마스터 조회 (FK)
        EmployeeMaster master = employeeMasterRepository.findById(pernr).orElse(null);
        if (master == null) {
            System.out.println("Skip: Employee Master not found for " + pernr);
            return;
        }

        // 2. 생년월일 추출 (주민번호 앞 6자리 기준)
        // 1900년대/2000년대 구분 로직 간단 적용
        String birthStr = ssn.substring(0, 6); // 880101
        String yearPrefix = (ssn.charAt(7) == '1' || ssn.charAt(7) == '2' || ssn.charAt(7) == '5' || ssn.charAt(7) == '6') ? "19" : "20"; 
        
        // 간단하게 주민번호 7번째 자리로 구분 (1,2,5,6 -> 1900 / 3,4,7,8 -> 2000 / 9,0 -> 1800)
        int year = Integer.parseInt(yearPrefix + birthStr.substring(0, 2));
        int month = Integer.parseInt(birthStr.substring(2, 4));
        int day = Integer.parseInt(birthStr.substring(4, 6));
        LocalDate birthDate = LocalDate.of(year, month, day);

        // 3. Entity 생성 (Builder 패턴이 없으면 Setter 사용)
        EmployeeFamily family = new EmployeeFamily();
        family.setEmployee(master);
        family.setFamilySeq(seq);
        family.setFamilyType(typeCode); // 공통코드 (10, 20 ...)
        family.setFamilyName(name);
        family.setSsn(ssn.replace("-", "")); // 평문 입력 -> DB 저장 시 암호화
        family.setBirth(birthDate);
        family.setGender(gender);
        
        family.setJobType(jobCode);
        family.setSchoolType(schoolCode);
        family.setSchoolName(schoolName);
        
        family.setLiveYn(liveYn);
        family.setDependYn(dependYn);
        family.setAllowYn(allowYn);
        family.setTaxYn(taxYn);
        family.setDisabledYn(disabledYn);
        
        String formattedPhone = (phone != null) ? phone.replace("-", "") : null;
        family.setPhone(formattedPhone);
        
        // 기본값 설정
        family.setFromDate(LocalDate.of(2000, 1, 1));
        family.setToDate(LocalDate.of(9999, 12, 31));
        family.setNote(note);
        family.setVersion(0L);

        list.add(family);
    }
}