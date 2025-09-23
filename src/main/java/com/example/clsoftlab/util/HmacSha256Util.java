package com.example.clsoftlab.util;

import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSha256Util {

	private static final String ALGORITHM = "HmacSHA256";
	
	public static String generateHmacSha256(String data, String key) {
        try {
            // 1. Mac 인스턴스를 HmacSHA256 알고리즘으로 생성
            Mac sha256_HMAC = Mac.getInstance(ALGORITHM);

            // 2. 비밀 키를 SecretKeySpec 객체로 변환
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);

            // 3. Mac 인스턴스를 비밀 키로 초기화
            sha256_HMAC.init(secret_key);

            // 4. 데이터에 대한 HMAC-SHA256 해시를 바이트 배열로 계산
            byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // 5. 계산된 바이트 배열을 16진수 문자열로 변환하여 반환
            return bytesToHex(hash);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC-SHA256", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
