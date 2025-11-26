$(document).ready(function() {
			
			// 1. 아이디 저장 기능 (로컬 스토리지 활용)
			const savedId = localStorage.getItem("savedUserId");
			if (savedId) {
				$("#userId").val(savedId);
				$("#remember_me").prop("checked", true);
			}

			// 2. 로그인 폼 전송 이벤트
			$("#loginForm").on("submit", function(e) {
				e.preventDefault(); // 폼의 기본 제출 동작(새로고침) 막기

				const userId = $("#userId").val();
				const password = $("#password").val();
				const remember = $("#remember_me").is(":checked");

				if(!userId) { alert("아이디를 입력해주세요."); return; }
				if(!password) { alert("비밀번호를 입력해주세요."); return; }

				// 3. AJAX 요청 (JSON 전송)
				$.ajax({
					url: "/login", // LoginController의 @PostMapping("/login") 호출
					type: "POST",
					contentType: "application/json", // 중요: @RequestBody가 받을 수 있게 설정
					data: JSON.stringify({
						userId: userId,
						password: password
					}),
					success: function(response) {
						// [성공 시] response에는 UserAccountResponseDto(JSON)가 들어있음
						
						// 4. 아이디 저장 처리
						if (remember) {
							localStorage.setItem("savedUserId", userId);
						} else {
							localStorage.removeItem("savedUserId");
						}

						// 5. 세션 스토리지에 사용자 정보 저장 (화면 표시용)
						sessionStorage.setItem("loginUser", JSON.stringify(response));

						// 6. 메인 페이지로 이동
						window.location.href = "/";
					},
					error: function(xhr) {
						// [실패 시] Controller가 보낸 에러 메시지(body) 출력
						// ResponseEntity.badRequest().body("메시지")의 내용이 xhr.responseText에 들어있음
						alert(xhr.responseText);
					}
				});
			});
		});