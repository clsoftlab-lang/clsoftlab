document.addEventListener('DOMContentLoaded', function () {
	  		// 수정 모달창을 열 때, 불러오기.
	       const tableBody = document.querySelector('.table tbody');
		    if (tableBody) {
		        tableBody.addEventListener('click', function(event) {
		            const editButton = event.target.closest('.edit-btn');
		            if (editButton) {
		                const id = editButton.dataset.id; 
		                findDetail(id);
		            }
		        });
		    }
		    
		 	// Select2 초기화
		    $('#empSelect').select2();
		    $('#itemSelect').select2();
		    
		    const detailTableSpecificOptions = {
			        'columns': [
			            null, 											// 1. 사번
			            null, 											// 2. 지급월
			            { "width": "50px", "searchable": false }, 		// 3. 차수
			            null, 											// 4. 항목코드
			            { "width": "20%" }, 							// 5. 항목명
			            null, 											// 6. 지급액
			            null, 											// 7. 소급월
			            null, 											// 8. 원금
			            { "width": "25%", "orderable": false }, 		// 9. 비고 (정렬 X)
			            { "width": "80px", "searchable": false, "orderable": false } // 10. 수정 (검색 X, 정렬 X)
			        ],
                    // [추가] DataTables 한글화 및 기본 설정
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
		    
		    initializeResizableDataTable('#detailTable',detailTableSpecificOptions);
		});
	 
	// 새 항목 등록
	    async function addNewDetail() {
			const addForm = document.getElementById('addForm');
		    const formData = new FormData(addForm);
		    const data = Object.fromEntries(formData.entries());

		    if (!data.payYm) {
		        alert('지급월을 선택해주세요.');
		        return;
		    }
		    if (!data.seqNo) {
		        alert('차수를 입력해주세요.');
		        return;
		    }
	        if (!data.empNo) {
		        alert('사번을 선택해주세요.');
		        return;
		    }
	        if (!data.itemCode) {
		        alert('항목코드를 선택해주세요.');
		        return;
		    }
	        if (!data.amount) {
		        alert('지급액을 입력해주세요.');
		        return;
		    }
		    
		    // --- 서버 작업 시작 ---
		    try {
		    	
		        const codeResponse = await fetch(`/pay/pay-detail/checkOverlap?payYm=${data.payYm}&empNo=${data.empNo}&itemCode=${data.itemCode}&seqNo=${data.seqNo}`);
		        const codeCheckResult = await codeResponse.text();
		        
		        	
		        if (codeCheckResult == 'true') { 
		            alert('해당 지급월/차수에 동일한 사번과 항목코드가 이미 존재합니다.'); 
		            return;
		        }

		        // --- 중복 검사를 통과하면 최종 저장 (POST) ---
		        const saveResponse = await fetch('/pay/pay-detail', {
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
		};
		
		// 수정 모달창 데이터 불러오기.
		async function findDetail(id) {
		    try {
		        const response = await fetch(`/pay/pay-detail/detail/${id}`);
		        if (!response.ok) {
		            throw new Error('데이터를 불러오는 데 실패했습니다.');
		        }
		        const data = await response.json();
		        const editForm = document.getElementById('editForm')


		        for (const key in data) {
		            const field = editForm.querySelector(`[name="${key}"]`);
		            
		            if (field) { 
	                    field.value = data[key];
		            }
		        }
		        
		        const empField = editForm.querySelector('[name="employeeMasterPernr"]');
		        if (empField && data.employeeMasterName && data.employeeMasterPernr) {
		            empField.value = `${data.employeeMasterName}(${data.employeeMasterPernr})`;
		        }

		        const itemField = editForm.querySelector('[name="itemCode"]');
		        if (itemField && data.itemName && data.itemCode) {
		            itemField.value = `${data.itemName}(${data.itemCode})`;
		        }
		        
		        const payYmField = editForm.querySelector('[name="payYm"]');
		        if (payYmField && data.payYm && data.payYm.length === 6) { // "yyyymm" (6자리) 형식인지 확인
		            payYmField.value = data.payYm.substring(0, 4) + '-' + data.payYm.substring(4, 6);
		        }
		        
		        const createdAtField = editForm.querySelector('[name="createdAt"]');
		        if (createdAtField && data.createdAt) {
		            
		            let formattedDateTime = data.createdAt.replace('T', ' ');

		            createdAtField.value = formattedDateTime;
		        }
		        
		    } catch (error) {
		        console.error('Error:', error);
		        alert(error.message);
		    }
		}
		
		//항목 정보 수정
		async function updateDetail () {
		    const editForm = document.getElementById('editForm');
		    const formData = new FormData(editForm);
		    const data = Object.fromEntries(formData.entries());
		    
		    if (!data.amount) {
		        alert('지급액을 입력해주세요.');
		        return;
		    }
		    
		    try {
		    	
		        const url = `/pay/pay-detail`;
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
		
		// 삭제
		async function deleteDetail() {
		    if (!confirm('정말로 이 항목을 삭제하시겠습니까?')) {
		        return;
		    }

		    const editForm = document.getElementById('editForm');
		    const id = editForm.querySelector('[name="id"]').value;

		    if (!id) {
		        alert('ID를 찾을 수 없습니다.');
		        return;
		    }
		    
		    try {
		        const url = `/pay/pay-detail/${id}`;
		        const response = await fetch(url, {
		            method: 'DELETE',
		        });

		        if (response.ok) {
		            alert('항목이 성공적으로 삭제되었습니다.');
		            location.reload();
		        } else {
		            const errorText = await response.text();
		            alert(`처리 중 오류가 발생했습니다: ${errorText}`);
		        }
		    } catch (error) {
		        console.error('Error:', error);
		        alert('처리 중 오류가 발생했습니다.');
		    }
		}