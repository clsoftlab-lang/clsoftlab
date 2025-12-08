package com.example.clsoftlab;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.clsoftlab.dto.common.UserAccountRequestDto;
import com.example.clsoftlab.service.common.UserAccountService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class UserAccountTest {

	@Autowired
    private UserAccountService userAccountService;

    
    @Test
    @Transactional
    @Commit // 테스트 종료 후 DB에 커밋 (데이터 저장)
    @DisplayName("사원 마스터 기반 로그인 계정 일괄 생성")
    void initializeUserAccounts() {
    	
        
        List<UserAccountRequestDto> dtoList = new ArrayList<>();

        // ==========================================
        // [0] 별도 시스템 관리자 계정 (사번 없음)
        // ==========================================
        // ID: admin1, PW: 1234, Name: 시스템총괄, Pernr: null
        dtoList.add(new UserAccountRequestDto("admin1", "1234", "시스템관리자1", null, "ADMIN"));


        // ==========================================
        // [1] 사원 데이터 매핑 (ID = 사번, PW = 사번)
        // ==========================================
        
        // 0. 특수 관리자 (양한재)
        dtoList.add(new UserAccountRequestDto("2018001", "2018001", "양한재", "2018001", "ADMIN"));

        // 1. 2022년 입사자
        addEmployee(dtoList, "2201001", "김철수");
        addEmployee(dtoList, "2201002", "이영희");
        addEmployee(dtoList, "2203003", "박민준");
        addEmployee(dtoList, "2203004", "최서연");
        addEmployee(dtoList, "2207005", "정지훈");
        addEmployee(dtoList, "2207006", "윤서아");
        addEmployee(dtoList, "2209007", "강도윤");
        addEmployee(dtoList, "2210008", "임하윤");
        addEmployee(dtoList, "2210009", "한지우");
        addEmployee(dtoList, "2211010", "오시우");

        // 2. 2023년 입사자
        addEmployee(dtoList, "2301011", "신유나");
        addEmployee(dtoList, "2301012", "장은우");
        addEmployee(dtoList, "2302013", "송지아");
        addEmployee(dtoList, "2304014", "권혁준");
        addEmployee(dtoList, "2305015", "황수빈");
        addEmployee(dtoList, "2305016", "안태현");
        addEmployee(dtoList, "2308017", "문채원");
        addEmployee(dtoList, "2309018", "고은별");
        addEmployee(dtoList, "2310019", "배준호");
        addEmployee(dtoList, "2312020", "조민서");

        // 3. 2024년 입사자
        addEmployee(dtoList, "2401021", "유정민");
        addEmployee(dtoList, "2401022", "홍예준");
        addEmployee(dtoList, "2402023", "나소율");
        addEmployee(dtoList, "2402024", "백시원");
        addEmployee(dtoList, "2403025", "노하린");
        addEmployee(dtoList, "2404026", "차유진");
        addEmployee(dtoList, "2404027", "표지성");
        addEmployee(dtoList, "2405028", "기태영");
        addEmployee(dtoList, "2405029", "선우진");
        addEmployee(dtoList, "2406030", "왕지현");
        addEmployee(dtoList, "2407031", "서재윤");
        addEmployee(dtoList, "2407032", "방민호");
        addEmployee(dtoList, "2408033", "편지후");
        addEmployee(dtoList, "2409034", "성주원");
        addEmployee(dtoList, "2409035", "위하늘");
        addEmployee(dtoList, "2410036", "명지안");
        addEmployee(dtoList, "2411037", "금예서");
        addEmployee(dtoList, "2411038", "도윤재");
        addEmployee(dtoList, "2412039", "채수현");
        addEmployee(dtoList, "2412040", "하동균");

        // 4. 2025년 입사자
        addEmployee(dtoList, "2501041", "진세아");
        addEmployee(dtoList, "2501042", "원태준");
        addEmployee(dtoList, "2502043", "라희선");
        addEmployee(dtoList, "2503044", "설강민");
        addEmployee(dtoList, "2503045", "옥다은");
        addEmployee(dtoList, "2504046", "경수호");
        addEmployee(dtoList, "2505047", "봉준서");
        addEmployee(dtoList, "2506048", "마동욱");
        addEmployee(dtoList, "2507049", "엄지아");
        addEmployee(dtoList, "2508050", "함유주");

        // ==========================================
        // 실행 로직
        // ==========================================
        System.out.println("====== [계정 생성 시작] ======");
        int successCount = 0;
        int failCount = 0;

        for (UserAccountRequestDto dto : dtoList) {
            try {
                userAccountService.addNewAccount(dto);
                System.out.println("✅ 등록 성공: " + dto.getUserName() + " (" + dto.getUserId() + ")");
                successCount++;
            } catch (Exception e) {
                System.err.println("❌ 등록 실패: " + dto.getUserName() + " - " + e.getMessage());
                failCount++;
            }
        }
        
        System.out.println("====== [계정 생성 완료] ======");
        System.out.println("성공: " + successCount + "건 / 실패(중복등): " + failCount + "건");
    }

    // 편의 메서드: ID와 비밀번호를 사번으로 통일하여 리스트에 추가
    private void addEmployee(List<UserAccountRequestDto> list, String pernr, String name) {
        // UserAccountRequestDto 생성자 순서: (userId, password, userName, pernr)
        list.add(new UserAccountRequestDto(pernr, pernr, name, pernr, "USER"));
    }
}