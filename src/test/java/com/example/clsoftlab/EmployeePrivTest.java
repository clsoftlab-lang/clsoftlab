package com.example.clsoftlab;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.EmployeePriv;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.hr.EmployeePrivRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class EmployeePrivTest {

	@Autowired
    private EmployeeMasterRepository employeeMasterRepository;

    @Autowired
    private EmployeePrivRepository employeePrivRepository;
    
    @Test
    @DisplayName("사원 개인정보 더미 데이터 적재 (암호화 적용)")
    @Transactional
    @Commit // 테스트가 끝나도 롤백되지 않고 DB에 반영됨
    void initEmployeePrivData() {
        // 기존 데이터가 있다면 삭제 (중복 방지)
        employeePrivRepository.deleteAllInBatch();

        List<EmployeePriv> privList = new ArrayList<>();

        // ---------------------------------------------------------
        // [데이터 생성 헬퍼]
        // 사번, 주민번호, 성별, 생년월일, 국적, 폰, 이메일, 주소, 시도, 시군구, 결혼, 병역
        // ---------------------------------------------------------
        
        // [0] 관리자
        addPriv(privList, "2018001", "850101-1234567", "M", "1985-01-01", "KR", "010-1111-2222", "admin@clsoft.com", "서울특별시 강남구 테헤란로 123", "서울특별시", "강남구", "20", "10");

        // [1] 2022년 입사자
        addPriv(privList, "2201001", "900505-1234567", "M", "1990-05-05", "KR", "010-3333-4444", "chulsoo@naver.com", "경기도 성남시 분당구 판교로 1", "경기도", "성남시", "20", "10");
        addPriv(privList, "2201002", "920303-2345678", "F", "1992-03-03", "KR", "010-5555-6666", "younghee@daum.net", "서울특별시 송파구 올림픽로 300", "서울특별시", "송파구", "10", "00");
        addPriv(privList, "2203003", "881212-1234567", "M", "1988-12-12", "KR", "010-7777-8888", "minjun@gmail.com", "서울특별시 서초구 반포대로 55", "서울특별시", "서초구", "20", "10");
        addPriv(privList, "2203004", "950707-2345678", "F", "1995-07-07", "KR", "010-9999-0000", "seoyeon@naver.com", "서울특별시 마포구 마포대로 10", "서울특별시", "마포구", "10", "00");
        addPriv(privList, "2207005", "930909-1234567", "M", "1993-09-09", "KR", "010-1212-3434", "jihoon@kakao.com", "경기도 용인시 수지구 포은대로 100", "경기도", "용인시", "10", "10");
        addPriv(privList, "2207006", "960404-2345678", "F", "1996-04-04", "US", "010-5656-7878", "seoa_us@gmail.com", "서울특별시 용산구 이태원로 200", "서울특별시", "용산구", "10", "00");
        addPriv(privList, "2209007", "911111-1234567", "M", "1991-11-11", "KR", "010-9090-8080", "doyoon@naver.com", "서울특별시 강동구 천호대로 99", "서울특별시", "강동구", "20", "10");
        addPriv(privList, "2210008", "940202-2345678", "F", "1994-02-02", "KR", "010-3434-5656", "hayoon@daum.net", "인천광역시 연수구 컨벤시아대로 50", "인천광역시", "연수구", "10", "00");
        addPriv(privList, "2210009", "970808-1234567", "M", "1997-08-08", "KR", "010-7878-9090", "jiwoo@gmail.com", "경기도 수원시 영통구 광교로 88", "경기도", "수원시", "10", "10");
        addPriv(privList, "2211010", "981010-1234567", "M", "1998-10-10", "KR", "010-2323-4545", "siwoo@naver.com", "서울특별시 관악구 관악로 1", "서울특별시", "관악구", "10", "30");

        // [2] 2023년 입사자
        addPriv(privList, "2301011", "990101-2345678", "F", "1999-01-01", "KR", "010-1357-2468", "yuna@gmail.com", "서울특별시 동작구 상도로 77", "서울특별시", "동작구", "10", "00");
        addPriv(privList, "2301012", "980505-1234567", "M", "1998-05-05", "KR", "010-2468-1357", "eunwoo@naver.com", "경기도 안양시 동안구 시민대로 200", "경기도", "안양시", "10", "10");
        addPriv(privList, "2302013", "000202-4567890", "F", "2000-02-02", "KR", "010-9876-5432", "jia@daum.net", "서울특별시 영등포구 여의대로 10", "서울특별시", "영등포구", "10", "00");
        addPriv(privList, "2304014", "950606-1234567", "M", "1995-06-06", "KR", "010-5432-9876", "hyukjun@kakao.com", "서울특별시 구로구 디지털로 300", "서울특별시", "구로구", "20", "10");
        addPriv(privList, "2305015", "010303-4567890", "F", "2001-03-03", "CN", "010-1122-3344", "subin_cn@gmail.com", "서울특별시 광진구 능동로 120", "서울특별시", "광진구", "10", "00");
        addPriv(privList, "2305016", "940404-1234567", "M", "1994-04-04", "KR", "010-2233-4455", "taehyun@naver.com", "서울특별시 중구 세종대로 110", "서울특별시", "중구", "10", "10");
        addPriv(privList, "2308017", "960808-2345678", "F", "1996-08-08", "KR", "010-3344-5566", "chaewon@daum.net", "서울특별시 성북구 성북로 10", "서울특별시", "성북구", "10", "00");
        addPriv(privList, "2309018", "970909-2345678", "F", "1997-09-09", "KR", "010-4455-6677", "eunbyul@gmail.com", "서울특별시 강북구 삼양로 50", "서울특별시", "강북구", "10", "00");
        addPriv(privList, "2310019", "931010-1234567", "M", "1993-10-10", "KR", "010-5566-7788", "junho@kakao.com", "경기도 고양시 일산동구 중앙로 1", "경기도", "고양시", "20", "10");
        addPriv(privList, "2312020", "991212-2345678", "F", "1999-12-12", "KR", "010-6677-8899", "minseo@naver.com", "서울특별시 은평구 통일로 200", "서울특별시", "은평구", "10", "00");

        // [3] 2024년 입사자
        addPriv(privList, "2401021", "000101-4567890", "F", "2000-01-01", "KR", "010-7788-9900", "jungmin@daum.net", "서울특별시 서대문구 연세로 50", "서울특별시", "서대문구", "10", "00");
        addPriv(privList, "2401022", "980101-1234567", "M", "1998-01-01", "KR", "010-8899-0011", "yejun@gmail.com", "서울특별시 종로구 종로 1", "서울특별시", "종로구", "10", "30");
        addPriv(privList, "2402023", "010202-4567890", "F", "2001-02-02", "KR", "010-9900-1122", "soyul@kakao.com", "서울특별시 성동구 왕십리로 222", "서울특별시", "성동구", "10", "00");
        addPriv(privList, "2402024", "950202-1234567", "M", "1995-02-02", "KR", "010-0011-2233", "siwon@naver.com", "경기도 부천시 길주로 100", "경기도", "부천시", "20", "10");
        addPriv(privList, "2403025", "020303-4567890", "F", "2002-03-03", "KR", "010-1122-3344", "harin@daum.net", "서울특별시 양천구 목동동로 200", "서울특별시", "양천구", "10", "00");
        addPriv(privList, "2404026", "990404-2345678", "F", "1999-04-04", "KR", "010-2233-4455", "yujin@gmail.com", "서울특별시 강서구 공항대로 300", "서울특별시", "강서구", "10", "00");
        addPriv(privList, "2404027", "940404-1234567", "M", "1994-04-04", "KR", "010-3344-5566", "jisung@kakao.com", "인천광역시 남동구 예술로 150", "인천광역시", "남동구", "10", "10");
        addPriv(privList, "2405028", "960505-1234567", "M", "1996-05-05", "KR", "010-4455-6677", "taeyoung@naver.com", "경기도 광명시 철산로 20", "경기도", "광명시", "10", "10");
        addPriv(privList, "2405029", "970505-1234567", "M", "1997-05-05", "KR", "010-5566-7788", "woojin@daum.net", "경기도 시흥시 시청로 1", "경기도", "시흥시", "10", "10");
        addPriv(privList, "2406030", "000606-4567890", "F", "2000-06-06", "KR", "010-6677-8899", "jihyun@gmail.com", "서울특별시 금천구 벚꽃로 100", "서울특별시", "금천구", "10", "00");
        addPriv(privList, "2407031", "980707-1234567", "M", "1998-07-07", "KR", "010-7788-9900", "jaeyoon@kakao.com", "서울특별시 노원구 노해로 400", "서울특별시", "노원구", "10", "30");
        addPriv(privList, "2407032", "930707-1234567", "M", "1993-07-07", "KR", "010-8899-0011", "minho@naver.com", "서울특별시 도봉구 마들로 600", "서울특별시", "도봉구", "20", "10");
        addPriv(privList, "2408033", "950808-1234567", "M", "1995-08-08", "KR", "010-9900-1122", "jihuu@daum.net", "경기도 의정부시 시민로 1", "경기도", "의정부시", "10", "10");
        addPriv(privList, "2409034", "990909-2345678", "F", "1999-09-09", "KR", "010-0011-2233", "juwon@gmail.com", "경기도 파주시 시민회관길 20", "경기도", "파주시", "10", "00");
        addPriv(privList, "2409035", "960909-1234567", "M", "1996-09-09", "KR", "010-1122-3344", "haneul@kakao.com", "경기도 김포시 사우중로 30", "경기도", "김포시", "10", "10");
        addPriv(privList, "2410036", "011010-4567890", "F", "2001-10-10", "KR", "010-2233-4455", "jian@naver.com", "인천광역시 부평구 부평대로 50", "인천광역시", "부평구", "10", "00");
        addPriv(privList, "2411037", "021111-4567890", "F", "2002-11-11", "KR", "010-3344-5566", "yeseo@daum.net", "인천광역시 서구 서곶로 300", "인천광역시", "서구", "10", "00");
        addPriv(privList, "2411038", "941111-1234567", "M", "1994-11-11", "KR", "010-4455-6677", "yoonjae@gmail.com", "경기도 화성시 시청로 100", "경기도", "화성시", "20", "10");
        addPriv(privList, "2412039", "031212-4567890", "F", "2003-12-12", "KR", "010-5566-7788", "suhyun@kakao.com", "경기도 오산시 성호대로 50", "경기도", "오산시", "10", "00");
        addPriv(privList, "2412040", "921212-1234567", "M", "1992-12-12", "KR", "010-6677-8899", "dongkyun@naver.com", "경기도 평택시 중앙로 200", "경기도", "평택시", "10", "10");

        // [4] 2025년 입사자
        addPriv(privList, "2501041", "030101-4567890", "F", "2003-01-01", "KR", "010-7788-9900", "seah@daum.net", "충청남도 천안시 서북구 번영로 100", "충청남도", "천안시", "10", "00");
        addPriv(privList, "2501042", "980101-1234567", "M", "1998-01-01", "KR", "010-8899-0011", "taejun@gmail.com", "충청남도 아산시 시민로 30", "충청남도", "아산시", "10", "30");
        addPriv(privList, "2502043", "040202-4567890", "F", "2004-02-02", "KR", "010-9900-1122", "heeseon@kakao.com", "대전광역시 서구 둔산로 100", "대전광역시", "서구", "10", "00");
        addPriv(privList, "2503044", "970303-1234567", "M", "1997-03-03", "KR", "010-0011-2233", "kangmin@naver.com", "대전광역시 유성구 대학로 99", "대전광역시", "유성구", "10", "10");
        addPriv(privList, "2503045", "000303-4567890", "F", "2000-03-03", "KR", "010-1122-3344", "daeun@daum.net", "세종특별자치시 한누리대로 200", "세종특별자치시", null, "10", "00");
        addPriv(privList, "2504046", "990404-1234567", "M", "1999-04-04", "KR", "010-2233-4455", "suho@gmail.com", "충청북도 청주시 상당구 상당로 50", "충청북도", "청주시", "10", "10");
        addPriv(privList, "2505047", "950505-1234567", "M", "1995-05-05", "KR", "010-3344-5566", "junseo@kakao.com", "강원특별자치도 춘천시 시청길 10", "강원특별자치도", "춘천시", "10", "10");
        addPriv(privList, "2506048", "940606-1234567", "M", "1994-06-06", "KR", "010-4455-6677", "dongwook@naver.com", "강원특별자치도 원주시 시청로 1", "강원특별자치도", "원주시", "20", "10");
        addPriv(privList, "2507049", "020707-4567890", "F", "2002-07-07", "KR", "010-5566-7788", "jia@daum.net", "경상북도 포항시 남구 시청로 1", "경상북도", "포항시", "10", "00");
        addPriv(privList, "2508050", "010808-4567890", "F", "2001-08-08", "KR", "010-6677-8899", "yuju@gmail.com", "경상남도 창원시 의창구 중앙대로 100", "경상남도", "창원시", "10", "00");

        // DB에 저장 (이때 CryptoConverter가 동작하여 암호화됨)
        employeePrivRepository.saveAll(privList);
    }

		private void addPriv(List<EmployeePriv> list, String pernr, String ssn, String gender, String birthDate,
                         String nation, String phone, String email, String addr, String sido, String sigungu,
                         String marital, String military) {
        
        // Master 엔티티가 있어야 @MapsId가 작동함 (FK 연결)
        EmployeeMaster master = employeeMasterRepository.findById(pernr).orElse(null);
        if (master == null) {
            System.out.println("Skip: Master not found for " + pernr);
            return;
        }

        // 주민번호 하이픈 제거 (DB에는 숫자만 저장)
        String cleanSsn = ssn != null ? ssn.replace("-", "") : null;

        EmployeePriv priv = EmployeePriv.builder()
                .employee(master) // @MapsId 연결
                .ssn(cleanSsn)    // 평문(숫자만) -> 저장 시 암호화
                .gender(gender)
                .birthDate(LocalDate.parse(birthDate))
                .nationCode(nation)
                .phoneNo(phone.replace("-", ""))
                .email(email)
                .addrMain(addr)
                .sido(sido)
                .sigungu(sigungu)
                .maritalCode(marital)
                .militaryCode(military)
                .build();

        list.add(priv);
    }
}
