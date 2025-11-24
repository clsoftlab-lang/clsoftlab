package com.example.clsoftlab.service.common;


import org.springframework.stereotype.Service;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

@Service
public class SqlValidationService {

	public void validateSql(String sql) {
		
		try {
	        Statement statement = CCJSqlParserUtil.parse(sql);
	        if (!(statement instanceof Select)) {
	            throw new IllegalArgumentException("SELECT 문만 허용됩니다.");
	        }
	    } catch (JSQLParserException e) {
	        throw new IllegalArgumentException("문법 오류: " + e.getMessage());
	    }
	}
	    
}
