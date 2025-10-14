package com.example.clsoftlab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.clsoftlab.dto.common.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExcepctionHandler { // 버전 관리 예외 처리용

	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<ErrorResponse> handleOptimisticLockingFailure (ObjectOptimisticLockingFailureException ex) {
		log.warn("Optimistic Lock Conflict: {}", ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), // 409 CONFICT 
				"다른 사용자에 의해 데이터가 변경되었습니다. 페이지를 새로고침 후 다시 시도해주세요.");
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}
}
