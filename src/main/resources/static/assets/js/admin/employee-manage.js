
document.addEventListener('DOMContentLoaded', function () {
    
 	// Select2 초기화
    $('#searchEmployeeSelect').select2();
    $('#searchDeptCodeSelect').select2();
    $('#searchRankCodeSelect').select2();
    $('#searchEmpStatusSelect').select2();
	
	const deptMap = {};
    const rankMap = {};
    const dutyMap = {};
    const statusMap = {};

    if (typeof deptList !== 'undefined') deptList.forEach(item => deptMap[item.code] = item.name);
    if (typeof rankList !== 'undefined') rankList.forEach(item => rankMap[item.code] = item.name);
    if (typeof dutyList !== 'undefined') dutyList.forEach(item => dutyMap[item.code] = item.name);
    if (typeof statusList !== 'undefined') statusList.forEach(item => statusMap[item.code] = item.name);

    
	const tableSpecificOptions = {
		    'columns': [
		        { "width": "10%" },                                           // 0. 사번
		        { "width": "10%" },                                           // 1. 이름
	            
	            // 2. 부서 (render 함수 사용)
		        { 
	                "width": "15%",
	                "render": function(data, type, row) {
	                    // data(코드값)로 map에서 이름을 찾음. 없으면 코드값 그대로 출력
	                    return deptMap[data] || data; 
	                }
	            }, 
	            
	            // 3. 직급
		        { 
	                "width": "10%",
	                "render": function(data, type, row) {
	                    return rankMap[data] || data;
	                }
	            },
	            
	            // 4. 직무
		        { 
	                "width": "15%",
	                "render": function(data, type, row) {
	                    return dutyMap[data] || data;
	                }
	            },
	            
		        { "width": "120px", "className": "text-center" },             // 5. 입사일
	            
	            // 6. 상태 (뱃지 처리 포함)
		        { 
	                "width": "100px", 
	                "className": "text-center",
	                "render": function(data, type, row) {
	                    // 상태 코드에 따라 뱃지 색상 및 명칭 변경
	                    let badgeClass = 'bg-secondary'; // 기본(퇴직 등)
	                    const statusName = statusMap[data] || data;

	                    if (data === '10') badgeClass = 'bg-success'; // 재직
	                    else if (data === '20') badgeClass = 'bg-warning'; // 휴직

	                    return `<span class="badge ${badgeClass}">${statusName}</span>`;
	                }
	            },
	            
		        { "width": "80px", "className": "text-center", "searchable": false, "orderable": false }  // 7. 관리
		    ],

		    // 1순위: 사번(0) 오름차순
		    "order": [[ 0, "asc" ]], 

		    // DataTables 한글화 및 기본 설정
		    "language": {
		        "emptyTable": "검색된 사원 정보가 없습니다.",
		        "info": "_START_ - _END_ / _TOTAL_ 명",
		        "infoEmpty": "0 명",
		        "infoFiltered": "(_MAX_ 명에서 필터링됨)",
		        "lengthMenu": "_MENU_ 명씩 보기",
		        "loadingRecords": "로딩중...",
		        "processing": "처리중...",
		        "search": "결과 내 검색: ",
		        "zeroRecords": "일치하는 사원이 없습니다.",
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



function openEmployeeCard(pernr) {
    if (!pernr) return;
    // 인사카드 URL로 이동 (쿼리 파라미터로 사번 전달)
    location.href = '/hr/employee-card?pernr=' + pernr;
}


