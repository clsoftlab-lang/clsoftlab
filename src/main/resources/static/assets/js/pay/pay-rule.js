const addPayRuleForm = document.getElementById('addPayRule');
	    
	    document.addEventListener('DOMContentLoaded', function () {

	        // 수정 모달창을 열 때, 불러오기.
	        const tableBody = document.querySelector('.table tbody');
		    if (tableBody) {
		        tableBody.addEventListener('click', function(event) {
		        	
		            const editButton = event.target.closest('.edit-btn');
		
		            
		            if (editButton) {
		                const ruleId = editButton.dataset.value; 
		                findPayRuleDetail(ruleId);
		            }
		        });
		    }
		    
		 	// Select2 초기화
		    $('#searchPayItemSelect').select2();
		    $('#searchPayRuleSelect').select2();
			$('#payItemSelect').select2({
		        dropdownParent: $('#addModal'),
		        theme: "bootstrap-5"
		    });
		
		    
			const tableSpecificOptions = {
			    'columns': [
			        { "width": "15%" },                                           // 0. 항목코드 (검색 O, 정렬 O)
			        { "width": "10%" },                                           // 1. 계산방식 (검색 O, 정렬 O)
			        { "searchable": true, "orderable": false },                   // 2. 수식/쿼리 (내용이 길므로 정렬 X)
			        { "width": "120px", "className": "text-center" },             // 3. 시작일 (검색 O, 정렬 O)
			        { "width": "120px", "className": "text-center" },             // 4. 종료일 (검색 O, 정렬 O)
			        { "width": "80px", "className": "text-center" },              // 5. 사용여부 (검색 O, 정렬 O)
			        { "width": "80px", "searchable": false, "orderable": false }  // 6. 수정 (버튼) (검색 X, 정렬 X)
			    ],

			    // 1순위: 항목코드(0) 오름차순, 2순위: 시작일(3) 내림차순 (최신순)
			    "order": [[ 0, "asc" ], [ 3, "desc" ]], 

			    // DataTables 한글화 및 기본 설정 (유지)
			    "language": {
			        "emptyTable": "등록된 계산 규칙이 없습니다.",
			        "info": "_START_ - _END_ / _TOTAL_ 건",
			        "infoEmpty": "0 건",
			        "infoFiltered": "(_MAX_ 건에서 필터링됨)",
			        "lengthMenu": "_MENU_ 개씩 보기",
			        "loadingRecords": "로딩중...",
			        "processing": "처리중...",
			        "search": "검색: ",
			        "zeroRecords": "일치하는 규칙이 없습니다.",
			        "paginate": {
			            "first": "처음",
			            "last": "마지막",
			            "next": "다음",
			            "previous": "이전"
			        }
			    },
			    "pageLength": 10, 
			    "lengthMenu": [ [10, 25, 50, 100, -1], [10, 25, 50, 100, "전체"] ]
			};
		    
		    initializeResizableDataTable('#dataTable',tableSpecificOptions);
	        
	    });
	    
	    // 새 계산 규칙 등록
	    async function addNewPayRule() {
		    const formData = new FormData(addPayRuleForm);
		    const data = Object.fromEntries(formData.entries());

		    
		    // 항목코드 공백 검사
		    if (!data.itemCode) {
		        alert('항목 코드를 입력해주세요.');
		        return;
		    }
		    
		    // 수식/SQL/테이블명/고정값 검사
		    if (data.ruleType === 'FIXED') {
		    	
		        if (!data.formula || isNaN(data.formula)) {
		            alert('계산 방식이 FIXED일 경우, 유효한 고정값을 숫자로 입력해야 합니다.');
		            addPayRuleForm.querySelector('[name="formula"]').focus();
		            return;
		        }
		    } else { // FORMULA, SQL, TABLE 등
		        if (!data.formula || !data.formula.trim()) {
		            alert('수식, SQL 또는 테이블명을 반드시 입력해야 합니다.');
		            addPayRuleForm.querySelector('[name="formula"]').focus();
		            return;
		        }
		    }

		    // 시작일, 종료일 검사
		    if (!data.fromDate || !data.toDate) {
		        alert('시작일, 종료일을 반드시 입력해야합니다.');
		        return;
		    }
		    
		    if (data.fromDate > data.toDate) {
		        alert('종료일은 시작일보다 빠를 수 없습니다.');
		        return;
		    }
		    
		    // --- 서버 작업 시작 ---
		    try {
		        const response = await fetch(`/pay/pay-rule/checkOverlap?itemCode=${data.itemCode}&fromDate=${data.fromDate}&toDate=${data.toDate}`);
		        
		        const checkResult = Number(await response.text());
		        
		        if (checkResult > 0) { 
		            alert('입력하신 기간이 기존 규칙의 기간과 중복됩니다. 시작일 또는 종료일을 다시 확인해주세요.'); 
		            return;
		        }

		        // --- 중복 검사를 통과하면 최종 저장 (POST) ---
		        const saveResponse = await fetch('/pay/pay-rule', {
		            method: 'POST',
		            headers: {
		                'Content-Type': 'application/json',
		            },
		            body: JSON.stringify(data),
		        });

		        if (saveResponse.ok) {
		            alert('성공적으로 등록되었습니다.');
		            location.reload();
		        } else {
		            const errorText = await saveResponse.text();
		            alert(`등록에 실패했습니다: ${errorText}`);
		        }

		    } catch (error) {
		        console.error('Error:', error);
		        alert('등록 중 오류가 발생했습니다.');
		    }
		}
	    
	    
	    async function findPayRuleDetail(ruleId) {
		    try {
		        const response = await fetch(`/pay/pay-rule/detail/${ruleId}`);
		        if (!response.ok) {
		            throw new Error('데이터를 불러오는 데 실패했습니다.');
		        }
		        const data = await response.json();
		        const editForm = document.getElementById('editPayRule')


		        for (const key in data) {
		            const field = editForm.querySelector(`[name="${key}"]`);
		            const checkbox = editForm.querySelector(`input[type="checkbox"][name="${key}"]`); // checkbox

		            if (checkbox && field && field.type === 'hidden') {
		                checkbox.checked = (data[key] === 'Y');
		            } else if (field) {
		                field.value = data[key];
		            }
		            
		        }
		        
		        if (data.ruleType == 'FIXED') {
		        	editForm.querySelector('[name="formula"]').value = data.fixedValue;
		        }

		    } catch (error) {
		        console.error('Error:', error);
		        alert(error.message);
		    }
		}
	    
	    
	  //계산 규칙 수정
		async function updatePayRule() {
		    const editForm = document.getElementById('editPayRule');
		    const formData = new FormData(editForm);
		    const data = Object.fromEntries(formData.entries());

		    // 수식/SQL/테이블명/고정값 검사
		    if (data.ruleType === 'FIXED') {
		    	
		        if (!data.formula || isNaN(data.formula)) {
		            alert('계산 방식이 FIXED일 경우, 유효한 고정값을 숫자로 입력해야 합니다.');
		            addPayRuleForm.querySelector('[name="formula"]').focus();
		            return;
		        }
		    } else { // FORMULA, SQL, TABLE 등
		        if (!data.formula || !data.formula.trim()) {
		            alert('수식, SQL 또는 테이블명을 반드시 입력해야 합니다.');
		            addPayRuleForm.querySelector('[name="formula"]').focus();
		            return;
		        }
		    }

		    // 시작일, 종료일 검사
		    if (!data.fromDate || !data.toDate) {
		        alert('시작일, 종료일을 반드시 입력해야합니다.');
		        return;
		    }
		    
		    if (data.fromDate > data.toDate) {
		        alert('종료일은 시작일보다 빠를 수 없습니다.');
		        return;
		    }
		    
		    try {
				const checkresponse = await fetch(`/pay/pay-rule/checkOverlap/update?itemCode=${data.itemCode}&ruleId=${data.ruleId}&fromDate=${data.fromDate}&toDate=${data.toDate}`);
		        
		        const checkResult = Number(await checkresponse.text());
		        
		        if (checkResult > 0) { 
		            alert('수정한 기간이 다른 규칙의 기간과 중복됩니다. 시작일 또는 종료일을 다시 확인해주세요.'); 
		            return;
		        }
		        
		        const url = `/pay/pay-rule/${data.ruleId}`;
		        const response = await fetch(url, {
		            method: 'PUT', 
		            headers: {
		                'Content-Type': 'application/json',
		            },
		            body: JSON.stringify(data),
		        });

		        if (response.ok) {
		            alert('성공적으로 수정되었습니다.');
		            location.reload(); // 성공 시 페이지 새로고침
		        } else {
		            const errorText = await response.text();
		            alert(`수정에 실패했습니다: ${errorText}`);
		        }
		    } catch (error) {
		        console.error('Error:', error);
		        alert('수정 중 오류가 발생했습니다.');
		    }
		}
		
		function checkSql() {
		    const $activeModal = $('.modal.show');
		    
		    if ($activeModal.length === 0) {
		        alert("검증할 창이 열려있지 않습니다.");
		        return;
		    }

		    const $form = $activeModal.find('form');
		    const ruleType = $form.find('select[name="ruleType"]').val();
		    const formula = $form.find('textarea[name="formula"]').val(); // name이 formula인 textarea

		    if (!formula || formula.trim() === '') {
		        alert("내용을 입력해주세요.");
		        $form.find('textarea[name="formula"]').focus();
		        return;
		    }

		    if (ruleType !== 'SQL') {
		        alert("SQL 방식만 문법 검증을 지원합니다.\n(다른 방식은 저장 시 로직에 따라 처리됩니다.)");
		        return;
		    }

		    // 5. AJAX로 서버(/sql)에 검증 요청
		    $.ajax({
		        url: '/sql',
		        type: 'POST',
		        contentType: 'application/json', 
		        data: JSON.stringify({ "sql": formula }), 
		        beforeSend: function() {
		            // (선택) 검증 중임을 알리기 위해 커서 변경
		            $('body').css('cursor', 'wait');
		        },
		        success: function(response) {
		            alert(response);
		        },
		        error: function(xhr) {
		            let errorMsg = xhr.responseText || "서버 통신 중 오류가 발생했습니다.";
		            alert(errorMsg);
		        },
		        complete: function() {
		            $('body').css('cursor', 'default');
		        }
		    });
		}