package com.example.clsoftlab;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.clsoftlab.dto.hr.EmployeePrivRequestDto;
import com.example.clsoftlab.service.hr.EmployeePrivService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class EmployeePrivTest {

	@Autowired
	private EmployeePrivService employeePrivService;
	
	@Test
    @Transactional
    @Commit // 테스트가 끝나도 롤백하지 않고 실제 DB에 커밋
    void initializeDummyData() {
        // 더미데이터 DTO 리스트 생성
        List<EmployeePrivRequestDto> dtoList = Arrays.asList(
        		// 1. 경영진 및 기획/지원부서 (서울 본사)
        	    new EmployeePrivRequestDto("2010001", "김철수", "M", LocalDate.parse("1975-03-15"), "750315-1234567", "대한민국", "010-1111-0001", "cs.kim@company.com", "서울특별시 강남구 테헤란로 1", "15층", "기혼", "복무완료 / 육군 / 병장 / 1995.03-1997.05", "N", "김영희", "배우자", "010-1111-0002"),
        	    new EmployeePrivRequestDto("2012011", "박영민", "M", LocalDate.parse("1980-11-20"), "801120-1234568", "대한민국", "010-2222-0011", "ym.park@company.com", "서울특별시 서초구 서초대로 2", null, "기혼", "복무완료 / 공군 / 중위 / 2003.01-2006.01", "N", "박은지", "배우자", "010-2222-0012"),
        	    new EmployeePrivRequestDto("2015022", "이서연", "F", LocalDate.parse("1988-07-01"), "880701-2234561", "대한민국", "010-3333-0022", "sy.lee@company.com", "경기도 성남시 분당구 판교역로 3", "A동 501호", "미혼", null, "N", "이현우", "부", "010-3333-0023"),
        	    new EmployeePrivRequestDto("2018013", "최민준", "M", LocalDate.parse("1990-01-25"), "900125-1234562", "대한민국", "010-4444-0013", "mj.choi@company.com", "서울특별시 송파구 올림픽로 4", null, "미혼", "복무완료 / 해군 / 하사 / 2010.03-2012.02", "N", "최경희", "모", "010-4444-0014"),
        	    new EmployeePrivRequestDto("2020023", "정수빈", "F", LocalDate.parse("1995-09-10"), "950910-2234563", "대한민국", "010-5555-0023", "sb.jung@company.com", "인천광역시 연수구 컨벤시아대로 5", "101동 1102호", "미혼", null, "N", "정재호", "오빠", "010-5555-0024"),
        	    new EmployeePrivRequestDto("2021032", "강지훈", "M", LocalDate.parse("1993-05-30"), "930530-1234564", "대한민국", "010-6666-0032", "jh.kang@company.com", "서울특별시 강동구 천호대로 6", null, "기혼", "복무완료 / 육군 / 병장 / 2013.06-2015.03", "N", "박서현", "배우자", "010-6666-0033"),
        	    new EmployeePrivRequestDto("2022055", "윤채원", "F", LocalDate.parse("1998-02-18"), "980218-2234565", "대한민국", "010-7777-0055", "cw.yoon@company.com", "경기도 하남시 미사강변동로 7", "203호", "미혼", null, "N", "윤상현", "부", "010-7777-0056"),

        	    // 2. R&D 센터 (판교)
        	    new EmployeePrivRequestDto("2021002", "오세훈", "M", LocalDate.parse("1978-12-01"), "781201-1234566", "대한민국", "010-8888-0002", "sh.oh@company.com", "경기도 성남시 분당구 대왕판교로 8", null, "기혼", "전문연구요원 / 2004.01-2007.01", "N", "이민정", "배우자", "010-8888-0003"),
        	    new EmployeePrivRequestDto("2021041", "배현진", "F", LocalDate.parse("1992-08-15"), "920815-2234567", "대한민국", "010-9999-0041", "hj.bae@company.com", "경기도 용인시 수지구 포은대로 9", "가동 301호", "미혼", null, "N", "배성철", "부", "010-9999-0042"),
        	    new EmployeePrivRequestDto("2021042", "임도현", "M", LocalDate.parse("1994-04-04"), "940404-1234568", "대한민국", "010-1234-0042", "dh.lim@company.com", "경기도 수원시 영통구 광교중앙로 10", null, "미혼", "복무완료 / 육군 / 상병 / 2014.05-2016.02", "N", "임정숙", "모", "010-1234-0043"),
        	    new EmployeePrivRequestDto("2022043", "장하윤", "F", LocalDate.parse("1997-10-22"), "971022-2234569", "대한민국", "010-2345-0043", "hy.jang@company.com", "서울특별시 관악구 신림로 11", "2층", "미혼", null, "Y", "장민철", "오빠", "010-2345-0044"),
        	    new EmployeePrivRequestDto("2022052", "신동민", "M", LocalDate.parse("1991-06-12"), "910612-1234561", "대한민국", "010-3456-0052", "dm.shin@company.com", "경기도 성남시 수정구 수정로 12", null, "기혼", "복무완료 / 공군 / 병장 / 2011.08-2013.08", "N", "최수지", "배우자", "010-3456-0053"),
        	    new EmployeePrivRequestDto("2023053", "유지민", "F", LocalDate.parse("2000-04-11"), "000411-4234562", "대한민국", "010-4567-0053", "jm.yoo@company.com", "경기도 성남시 분당구 불정로 13", null, "미혼", null, "N", "유재석", "부", "010-4567-0054"),
        	    new EmployeePrivRequestDto("2023101", "John Smith", "M", LocalDate.parse("1990-01-01"), "900101-5234563", "미국", "010-5678-0101", "john.smith@company.com", "경기도 성남시 분당구 판교로 14", "Apt 5B", "미혼", null, "N", "Jane Smith", "sister", "010-5678-0102"),

        	    // 3. 생산 및 품질 (수원/평택 공장)
        	    new EmployeePrivRequestDto("2019101", "황민현", "M", LocalDate.parse("1982-08-09"), "820809-1234564", "대한민국", "010-1122-0101", "mh.hwang@company.com", "경기도 수원시 영통구 삼성로 15", null, "기혼", "복무완료 / 해병대 / 병장 / 2002.09-2004.08", "N", "강미나", "배우자", "010-1122-0102"),
        	    new EmployeePrivRequestDto("2019112", "송강", "M", LocalDate.parse("1994-04-23"), "940423-1234565", "대한민국", "010-2233-0112", "kang.song@company.com", "경기도 화성시 동탄중앙로 16", "1204동 801호", "미혼", "복무완료 / 육군 / 병장 / 2014.07-2016.04", "N", "송지효", "누나", "010-2233-0113"),
        	    new EmployeePrivRequestDto("2019113", "문가영", "F", LocalDate.parse("1996-07-10"), "960710-2234566", "대한민국", "010-3344-0113", "gy.moon@company.com", "경기도 수원시 팔달구 행궁로 17", null, "미혼", null, "N", "문상민", "오빠", "010-3344-0114"),
        	    new EmployeePrivRequestDto("2020121", "안효섭", "M", LocalDate.parse("1995-04-17"), "950417-1234567", "캐나다", "010-4455-0121", "hs.ahn@company.com", "경기도 수원시 장안구 경수대로 18", null, "미혼", null, "N", "Ahn Dong-il", "Father", "010-4455-0122"),
        	    new EmployeePrivRequestDto("2021114", "조유리", "F", LocalDate.parse("2001-10-22"), "011022-4234568", "대한민국", "010-5566-0114", "yr.cho@company.com", "경기도 오산시 역광장로 19", "가-101", "미혼", null, "N", "조미연", "언니", "010-5566-0115"),
        	    new EmployeePrivRequestDto("2023131", "나인우", "M", LocalDate.parse("1994-09-17"), "940917-1234569", "대한민국", "010-6677-0131", "iw.na@company.com", "경기도 평택시 고덕국제대로 20", null, "미혼", "복무완료 / 육군 / 병장 / 2015.01-2016.10", "N", "나철수", "부", "010-6677-0132"),
        	    new EmployeePrivRequestDto("2023133", "김지원", "F", LocalDate.parse("1992-10-19"), "921019-2234561", "대한민국", "010-7788-0133", "jw.kim@company.com", "경기도 평택시 중앙로 21", "3층 302호", "미혼", null, "N", "김진경", "모", "010-7788-0134"),
        	    new EmployeePrivRequestDto("2024132", "이도현", "M", LocalDate.parse("1995-04-11"), "950411-1234562", "대한민국", "010-8899-0132", "dh.lee2@company.com", "충청남도 천안시 서북구 불당대로 22", null, "미혼", "복무완료 / 공군 / 병장 / 2017.08-2019.08", "N", "이병헌", "부", "010-8899-0133"),
        	    new EmployeePrivRequestDto("2024134", "박은빈", "F", LocalDate.parse("1992-09-04"), "920904-2234563", "대한민국", "010-9900-0134", "eb.park@company.com", "경기도 평택시 비전5로 23", null, "미혼", null, "N", "박철민", "부", "010-9900-0135"),

        	    // 4. 물류센터 (인천)
        	    new EmployeePrivRequestDto("2022201", "한소희", "F", LocalDate.parse("1994-11-18"), "941118-2234564", "대한민국", "010-1212-0201", "sh.han@company.com", "인천광역시 중구 공항로 24", null, "미혼", null, "N", "한지민", "모", "010-1212-0202"),
        	    new EmployeePrivRequestDto("2022211", "차은우", "M", LocalDate.parse("1997-03-30"), "970330-1234565", "대한민국", "010-2323-0211", "ew.cha@company.com", "인천광역시 서구 청라라임로 25", "102동 2504호", "미혼", "복무완료 / 육군 / 병장 / 2018.01-2019.10", "N", "차범근", "부", "010-2323-0212"),
        	    new EmployeePrivRequestDto("2023212", "권나라", "F", LocalDate.parse("1991-03-13"), "910313-2234566", "대한민국", "010-3434-0212", "nr.kwon@company.com", "경기도 부천시 길주로 26", null, "미혼", null, "N", "권상우", "오빠", "010-3434-0213"),
        	    new EmployeePrivRequestDto("2023213", "위하준", "M", LocalDate.parse("1991-08-05"), "910805-1234567", "대한민국", "010-4545-0213", "hj.wi@company.com", "경기도 김포시 김포한강1로 27", "B동 101호", "기혼", "복무완료 / 경찰청 / 수경 / 2012.02-2013.11", "N", "김고은", "배우자", "010-4545-0214"),

        	    // 5. 신규 입사자
        	    new EmployeePrivRequestDto("2025001", "박서준", "M", LocalDate.parse("1988-12-16"), "881216-1234568", "대한민국", "010-5656-1001", "sj.park@company.com", "서울특별시 마포구 월드컵북로 28", "오피스텔 707호", "미혼", "복무완료 / 교정시설 / 2008.07-2010.05", "N", "박용규", "부", "010-5656-1002"),
        	    new EmployeePrivRequestDto("2025002", "김고은", "F", LocalDate.parse("1991-07-02"), "910702-2234569", "대한민국", "010-6767-1002", "ge.kim@company.com", "서울특별시 성동구 왕십리로 29", null, "기혼", null, "N", "위하준", "배우자", "010-4545-0213"),
        	    new EmployeePrivRequestDto("2025003", "변우석", "M", LocalDate.parse("1991-10-31"), "911031-1234561", "대한민국", "010-7878-1003", "ws.byun@company.com", "경기도 고양시 일산동구 중앙로 30", "110동 1501호", "미혼", "복무완료 / 육군 / 병장 / 2011.11-2013.08", "N", "변정수", "누나", "010-7878-1004"),
        	    new EmployeePrivRequestDto("2025004", "김혜윤", "F", LocalDate.parse("1996-11-10"), "961110-2234562", "대한민국", "010-8989-1004", "hy.kim@company.com", "서울특별시 광진구 아차산로 31", null, "미혼", null, "N", "김철민", "부", "010-8989-1005")
        );
        
        // 리스트를 순회하며 서비스의 등록 메서드 호출
        dtoList.forEach(dto -> {
            try {
                // 이미 존재하는 데이터는 건너뛰기
                if (!employeePrivService.checkOverlap(dto.getPernr())) {
                    employeePrivService.addNewEmployeePriv(dto);
                    System.out.println(dto.getPernr() + " 등록 성공");
                } else {
                    System.out.println(dto.getPernr() + "는 이미 존재하여 건너뜁니다.");
                }
            } catch (Exception e) {
                System.err.println(dto.getPernr() + " 등록 실패: " + e.getMessage());
            }
        });
    }
}
