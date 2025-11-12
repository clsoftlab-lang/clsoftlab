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

import com.example.clsoftlab.dto.hr.BizPlaceListDto;

@Service
public class BizPlaceExcelService {

	public void generateBizPlaceExcel(List<BizPlaceListDto> bizPlace, OutputStream outputStream) throws IOException {
		
		// .xlsx 형식의 새 워크북 생성
        Workbook workbook = new XSSFWorkbook();
        // 새 시트 생성
        Sheet sheet = workbook.createSheet("사업장 목록");

        // 헤더 행(Row) 생성
        String[] headers = {"사업장 코드", "사업장명", "위치 (주소)", "사업자등록번호", "사용여부", "담당자"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        
     // 데이터 행 생성
        for (int i = 0; i < bizPlace.size(); i++) {
            BizPlaceListDto dto = bizPlace.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(dto.getBizCode());
            row.createCell(1).setCellValue(dto.getBizName());
            row.createCell(2).setCellValue(dto.getAddress());
            row.createCell(3).setCellValue(dto.getRegNo());
            row.createCell(4).setCellValue(dto.getUseYn());
            row.createCell(5).setCellValue(dto.getManagerName() + " (" + dto.getManagerPernr() + ")");
        }
        
        // OutputStream에 워크북 데이터 쓰기
        workbook.write(outputStream);
        // 리소스 정리
        workbook.close();
	
	}
	
}
