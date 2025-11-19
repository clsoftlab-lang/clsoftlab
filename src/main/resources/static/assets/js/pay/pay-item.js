document.addEventListener('DOMContentLoaded', function() {
		    const tableBody = document.querySelector('.table tbody');
			const addPayItemForm = document.getElementById('addPayItem');

			
		    if (tableBody) {
		        tableBody.addEventListener('click', function(event) {
		        	
		            const editButton = event.target.closest('.edit-btn');
		
		            
		            if (editButton) {
		                const itemCode = editButton.dataset.value; 
		                findPayItemDetail(itemCode);
		            }
		        });
		    }
		    
		 	// Select2 초기화
		    $('#itemTypeSelect').select2();
		    $('#searchPayItemSelect').select2();
		    
			const tableSpecificOptions = {
			    'columns': [
			        null,                                                        // 0. 항목코드 (기본)
			        { "width": "20%" },                                          // 1. 항목명 (너비 조정)
			        null,                                                        // 2. 유형
			        { "searchable": false, "orderable": false },                 // 3. 과세 (체크박스라 정렬/검색 X)
			        null,                                                        // 4. 절사방식
			        { "searchable": false, "orderable": false },                 // 5. 사용여부 (체크박스라 정렬/검색 X)
			        { "width": "80px", "searchable": false, "orderable": false } // 6. 수정 (버튼이라 정렬/검색 X)
			    ],

			    // 1순위: 항목코드(0) 기준으로 오름차순 정렬
			    "order": [[ 0, "asc" ]], 

			    // DataTables 한글화 및 기본 설정 (유지)
			    "language": {
			        "emptyTable": "데이터가 없습니다.",
			        "info": "_START_ - _END_ / _TOTAL_ 항목",
			        "infoEmpty": "0 항목",
			        "infoFiltered": "(_MAX_ 항목에서 필터링됨)",
			        "lengthMenu": "_MENU_ 개씩 보기",
			        "loadingRecords": "로딩중...",
			        "processing": "처리중...",
			        "search": "검색: ",
			        "zeroRecords": "일치하는 항목이 없습니다.",
			        "paginate": {
			            "first": "처음",
			            "last": "마지막",
			            "next": "다음",
			            "previous": "이전"
			        }
			    },
			    "pageLength": 10, 
			    "lengthMenu": [ [10, 25, 50, 100, -1], [10, 25, 50, 100, "All"] ] // 페이지 메뉴
			};
			
			initializeResizableDataTable('#dataTable',tableSpecificOptions);
		    
		});	


			async function addNewPayItem() {
		    const formData = new FormData(addPayItemForm);
		    const data = Object.fromEntries(formData.entries());

		    //  항목코드 형식 검사 (영어/숫자) ---
		    const regex = /^[a-zA-Z0-9]+$/;
		    if (!regex.test(data.itemCode)) {
		        alert('항목코드는 영어와 숫자로만 구성해야 합니다.');
		        document.querySelector('[name="itemCode"]').focus();
		        return;
		    }
		    
		    // 항목명 공백 검사
		    if (!data.itemName || !data.itemName.trim()) {
		        alert('항목명을 반드시 입력해야 합니다.');
		        document.querySelector('#addPayItem [name="itemName"]').focus();
		        return;
		    }

		    // 과세여부가 Y인 경우 과세방식 체크
		    if (data.taxYn === 'Y' && !data.taxType) {
		        alert('과세여부가 Y인 경우, 과세방식을 반드시 선택해야 합니다.');
		        document.querySelector('[name="taxType"]').focus();
		        return;
		    }
		    // (onchange에서 처리하지만) 최종 저장 전 한번 더 확인 ---
		    if (data.taxType === 'PERCENT' && !data.taxPercent) {
		        alert('과세방식이 PERCENT인 경우, 과세비율을 반드시 입력해야 합니다.');
		        document.querySelector('[name="taxPercent"]').focus();
		        return;
		    }
		    
		    
		    // --- 서버 작업 시작 ---
		    try {
		        const response = await fetch(`/pay/pay-item/checkOverlap`);
						const checkResult = await response.text();
		        
		        if (checkResult == 'true') {
		            alert('이미 사용 중인 항목코드입니다. 다른 코드를 입력해주세요.');
		            document.querySelector('[name="itemCode"]').focus();
		            return;
		        }

		        // --- 모든 검사를 통과하면 최종 저장 (POST) ---
		        const saveResponse = await fetch('/pay/pay-item', {
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
		
		
		// 과세방식이 Percent면, 과세비율을 required로 변경.
		function checkTaxType(selectElement) {
			
		    const parentForm = selectElement.closest('form');
		    const taxPercentInput = parentForm.querySelector('[name="taxPercent"]');

		    if (selectElement.value === 'PERCENT') {
		        taxPercentInput.required = true;
		    } else {
		        taxPercentInput.required = false;
		        taxPercentInput.value = ''; 
		    }
		}
		
		
		//
		
		
		async function findPayItemDetail(itemCode) {
		    try {
		        const response = await fetch(`/pay/pay-item/detail/${itemCode}`);
		        if (!response.ok) {
		            throw new Error('데이터를 불러오는 데 실패했습니다.');
		        }
		        const data = await response.json();

		        const editForm = document.getElementById('editPayItemForm');

		        for (const key in data) {
		        	const field = editForm.querySelector(`[name="${key}"]`); // hidden 또는 일반 input
		            const checkbox = editForm.querySelector(`input[type="checkbox"][name="${key}"]`); // checkbox

		            if (checkbox && field && field.type === 'hidden') {
		                checkbox.checked = (data[key] === 'Y');
		            } else if (field) {
		                field.value = data[key];
		            }
		        }
		        
		        // 과세방식(taxType)의 onchange 이벤트를 수동으로 한번 실행시켜줍니다.
		        checkTaxType(editForm.querySelector('[name="taxType"]'));

		    } catch (error) {
		        console.error('Error:', error);
		        alert(error.message);
		    }
		}
		
		//급여 항목 수정
		async function updatePayItem() {
		    const editForm = document.getElementById('editPayItemForm');
		    const formData = new FormData(editForm);
		    const data = Object.fromEntries(formData.entries());

		    // 유효성 검사 (예: 과세비율 등)
		    if (data.taxYn === 'Y' && !data.taxType) {
		        alert('과세여부가 Y인 경우, 과세방식을 반드시 선택해야 합니다.');
		        document.querySelector('[name="taxType"]').focus();
		        return;
		    }
		    
		    
		    if (data.taxType === 'PERCENT' && !data.taxPercent) {
		        alert('과세방식이 PERCENT인 경우, 과세비율을 반드시 입력해야 합니다.');
		        return;
		    }
		    
		    try {
		        const url = `/pay/pay-item/${data.itemCode}`;
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