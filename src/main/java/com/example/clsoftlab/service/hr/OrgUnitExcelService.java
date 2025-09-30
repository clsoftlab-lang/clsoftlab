package com.example.clsoftlab.service.hr;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.OrgUnitExcelDto;

@Service
public class OrgUnitExcelService {

	public void generateOrgUnitExcel(List<OrgUnitExcelDto> orgUnit, OutputStream outputStream) throws IOException {
		
		// .xlsx 형식의 새 워크북 생성
        Workbook workbook = new XSSFWorkbook();
        // 새 시트 생성
        Sheet sheet = workbook.createSheet("조직 목록");

        // 헤더 행(Row) 생성
        String[] headers = {"조직 코드", "조직명", "조직 유형", "상위 조직", "대표 직책", "조직 유효 시작일", "조직 유효 종료일", "담당자", "사용 여부", "비고"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        
     // 데이터 행 생성
        for (int i = 0; i < orgUnit.size(); i++) {
            OrgUnitExcelDto dto = orgUnit.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(dto.getOrgCode());
            row.createCell(1).setCellValue(dto.getOrgName());
            row.createCell(2).setCellValue(dto.getOrgType());
            row.createCell(3).setCellValue(dto.getParentOrgCode());
            row.createCell(5).setCellValue(dto.getMainPos());
            row.createCell(4).setCellValue(dto.getValidFrom());
            row.createCell(5).setCellValue(dto.getValidTo());
            row.createCell(5).setCellValue(dto.getManagerId());
            row.createCell(5).setCellValue(dto.getUseYn());
            row.createCell(5).setCellValue(dto.getRemark());
            
        }
        
        // OutputStream에 워크북 데이터 쓰기
        workbook.write(outputStream);
        // 리소스 정리
        workbook.close();
	
	}
	
}
