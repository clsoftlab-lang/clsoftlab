package com.example.clsoftlab.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clsoftlab.service.common.SqlValidationService;

@Controller
@RequestMapping("/sql")
public class SqlValidationController {
	
	private final SqlValidationService sqlValidationService;
	
	public SqlValidationController(SqlValidationService sqlValidationService) {
		this.sqlValidationService = sqlValidationService;
	}

	@PostMapping("")
	public ResponseEntity<String> checkSql (@RequestBody Map<String, String> request) {
		
		String sql = request.get("sql");
	
		try {
			
			if (sql == null || sql.trim().isEmpty()) {
                throw new IllegalArgumentException("검증할 SQL을 입력해주세요.");
            }
			
	        sqlValidationService.validateSql(sql);
	        
	        return ResponseEntity.ok("✅ SQL 문법이 정확합니다. (실행 가능)");
	
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body("❌ 검증 실패: " + e.getMessage());
	
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다: " + e.getMessage());
	    }
	}
}
