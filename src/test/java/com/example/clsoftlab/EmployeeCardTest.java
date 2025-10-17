package com.example.clsoftlab;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.clsoftlab.dto.hr.EmployeeCardRequestDto;
import com.example.clsoftlab.service.hr.EmployeeCardService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class EmployeeCardTest {

	@Autowired
	private EmployeeCardService employeeCardService;
	
	@Test
    @Transactional
    @Commit // 테스트가 끝나도 롤백하지 않고 실제 DB에 커밋
    void initializeDummyData() {
        // 더미데이터 DTO 리스트 생성
        List<EmployeeCardRequestDto> dtoList = Arrays.asList(
        		// 1. 2012011 (김철수)
                new EmployeeCardRequestDto("2012011", "김철수", LocalDate.of(1980, 5, 15), "M", "대한민국", "800515-1123456",
                    "010-1111-2222", "cs.kim@company.com", "서울시 강남구 테헤란로 123, 101동 501호", "010-1111-3333",
                    LocalDate.of(2012, 1, 10), LocalDate.of(2008, 3, 1), "EXP", "REC", "REG", null, "GEN", "ACV",
                    "박영희", "SPOUSE", "010-2222-3333", "서울시 강남구 테헤란로 123, 101동 501호",
                    "FIN", "ARMY", LocalDate.of(2000, 3, 10), LocalDate.of(2002, 5, 9), "N", "N"),
                
                // 2. 2015022 (이유진)
                new EmployeeCardRequestDto("2015022", "이유진", LocalDate.of(1992, 11, 20), "F", "대한민국", "921120-2234567",
                    "010-3333-4444", "yj.lee@company.com", "경기도 성남시 분당구 판교역로 235", "010-3333-5555",
                    LocalDate.of(2016, 1, 1), LocalDate.of(2016, 1, 1), "NEW", "PUB", "REG", "3개월", "GEN", "ACV",
                    "이정국", "FATHER", "010-4444-5555", "경기도 성남시 분당구 판교역로 235",
                    "NA", null, null, null, "N", "N"),

                // 3. 2018013 (박서준)
                new EmployeeCardRequestDto("2018013", "박서준", LocalDate.of(1988, 2, 18), "M", "대한민국", "880218-1345678",
                    "010-5555-6666", "sj.park@company.com", "경기도 수원시 영통구 삼성로 129", "010-5555-7777",
                    LocalDate.of(2018, 1, 1), LocalDate.of(2014, 1, 2), "EXP", "REC", "REG", null, "GEN", "ACV",
                    "박현식", "FATHER", "010-6666-7777", "경기도 수원시 영통구 삼성로 129",
                    "FIN", "OTHER", LocalDate.of(2014, 1, 2), LocalDate.of(2017, 1, 1), "N", "N"),

                // 4. 2019101 (최민준)
                new EmployeeCardRequestDto("2019101", "최민준", LocalDate.of(1994, 8, 30), "M", "대한민국", "940830-1456789",
                    "010-7777-8888", "mj.choi@company.com", "대구시 수성구 달구벌대로 2424", "010-7777-9999",
                    LocalDate.of(2019, 6, 1), LocalDate.of(2019, 6, 1), "NEW", "PUB", "REG", "3개월", "GEN", "ACV",
                    "최경환", "FATHER", "010-8888-9999", "대구시 수성구 달구벌대로 2424",
                    "FIN", "MARINE", LocalDate.of(2014, 3, 1), LocalDate.of(2016, 1, 31), "N", "N"),

                // 5. 2021002 (정다은)
                new EmployeeCardRequestDto("2021002", "정다은", LocalDate.of(1996, 3, 12), "F", "대한민국", "960312-2567890",
                    "010-9999-0000", "de.jung@company.com", "인천시 연수구 송도과학로 32", "010-9999-1111",
                    LocalDate.of(2021, 4, 1), LocalDate.of(2021, 4, 1), "NEW", "REC", "REG", "3개월", "DIS", "ACV",
                    "한미숙", "MOTHER", "010-0000-1111", "인천시 연수구 송도과학로 32",
                    "NA", null, null, null, "Y", "Y"),

                // 6. 2016088 (윤지수)
                new EmployeeCardRequestDto("2016088", "윤지수", LocalDate.of(1990, 7, 7), "F", "대한민국", "900707-2678901",
                    "010-1234-5678", "js.yoon@company.com", "서울시 서초구 서초대로 77길 54", "010-1234-9999",
                    LocalDate.of(2016, 9, 1), LocalDate.of(2013, 2, 1), "EXP", "REC", "REG", null, "GEN", "ACV",
                    "윤태영", "FATHER", "010-5678-1234", "서울시 서초구 서초대로 77길 54",
                    "NA", null, null, null, "N", "N"),

                // 7. 2014030 (강태현)
                new EmployeeCardRequestDto("2014030", "강태현", LocalDate.of(1985, 12, 25), "M", "대한민국", "851225-1789012",
                    "010-8765-4321", "th.kang@company.com", "서울시 종로구 종로3길 17", "010-8765-1111",
                    LocalDate.of(2014, 3, 1), LocalDate.of(2011, 1, 15), "EXP", "REC", "REG", null, "GEN", "ACV",
                    "강신일", "FATHER", "010-4321-8765", "서울시 종로구 종로3길 17",
                    "EXM", null, null, null, "N", "N"),
                
                // 8. 2013077 (왕웨이)
                new EmployeeCardRequestDto("2013077", "왕웨이", LocalDate.of(1978, 10, 1), "M", "중국", "123456-4789012",
                    "010-5555-1234", "wei.wang@company.com", "중국 상하이시 푸동신구 세기대도 88", "010-5555-4321",
                    LocalDate.of(2013, 7, 1), LocalDate.of(2013, 7, 1), "EXP", "REC", "REG", null, "GEN", "ACV",
                    "CHEN LI", "SPOUSE", "010-1111-4321", "중국 상하이시 푸동신구 세기대도 88",
                    "NA", null, null, null, "N", "N")
        );
        
        // 리스트를 순회하며 서비스의 등록 메서드 호출
        dtoList.forEach(dto -> {
            try {
                    employeeCardService.saveCard(dto);
                    System.out.println(dto.getPernr() + " 등록 성공");
                
            } catch (Exception e) {
                System.err.println(dto.getPernr() + " 등록 실패: " + e.getMessage());
            }
        });
    }
}
