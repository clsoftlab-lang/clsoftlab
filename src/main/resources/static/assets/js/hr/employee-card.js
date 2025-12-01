const codeMaps = {
    dept: {},
    rank: {},
    duty: {},
    status: {},
    nation: {},   
    marital: {},  
    military: {}  
};

document.addEventListener('DOMContentLoaded', function () {
    
    // 코드 리스트를 Map으로 변환 (검색 속도 향상)
    if (typeof deptList !== 'undefined') deptList.forEach(c => codeMaps.dept[c.code] = c.name);
    if (typeof rankList !== 'undefined') rankList.forEach(c => codeMaps.rank[c.code] = c.name);
    if (typeof dutyList !== 'undefined') dutyList.forEach(c => codeMaps.duty[c.code] = c.name);
    if (typeof statusList !== 'undefined') statusList.forEach(c => codeMaps.status[c.code] = c.name);
	if (typeof nationList !== 'undefined') nationList.forEach(c => codeMaps.nation[c.code] = c.name);
	if (typeof maritalList !== 'undefined') maritalList.forEach(c => codeMaps.marital[c.code] = c.name);
    if (typeof militaryList !== 'undefined') militaryList.forEach(c => codeMaps.military[c.code] = c.name);

    // 2. 초기 로드 (URL 파라미터 or 로그인 유저)
    const urlParams = new URLSearchParams(window.location.search);
    // URL에 pernr이 있으면 그 사람을, 없으면 로그인한 본인을 조회
    const targetPernr = urlParams.get('pernr') || loginUserPernr;

    if (targetPernr) {
        loadCardData(targetPernr);
    }

    // 3. 검색 엔터키 이벤트
    const searchInput = document.getElementById('searchKeyword');
    if (searchInput) {
        searchInput.addEventListener("keypress", function(event) {
            if (event.key === "Enter") {
                event.preventDefault();
                searchCard();
            }
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    
    // 코드 리스트를 Map으로 변환 (검색 속도 향상)
    if (typeof deptList !== 'undefined') deptList.forEach(c => codeMaps.dept[c.code] = c.name);
    if (typeof rankList !== 'undefined') rankList.forEach(c => codeMaps.rank[c.code] = c.name);
    if (typeof dutyList !== 'undefined') dutyList.forEach(c => codeMaps.duty[c.code] = c.name);
    if (typeof statusList !== 'undefined') statusList.forEach(c => codeMaps.status[c.code] = c.name);

    // 2. 초기 로드 (URL 파라미터 or 로그인 유저)
    const urlParams = new URLSearchParams(window.location.search);
    // URL에 pernr이 있으면 그 사람을, 없으면 로그인한 본인을 조회
    const targetPernr = urlParams.get('pernr') || loginUserPernr;

    if (targetPernr) {
        loadCardData(targetPernr);
    }

    // 3. 검색 엔터키 이벤트
    const searchInput = document.getElementById('searchKeyword');
    if (searchInput) {
        searchInput.addEventListener("keypress", function(event) {
            if (event.key === "Enter") {
                event.preventDefault();
                searchCard();
            }
        });
    }
});



async function loadCardData(pernr) {
    if (!pernr) return;

    try {
        // API 호출
        const response = await fetch(`/hr/employee-priv/detail/${pernr}`);
        
        if (response.status === 404) {
            alert('사원 정보를 찾을 수 없습니다.');
            return;
        }
        if (!response.ok) throw new Error('서버 오류 발생');

        const data = await response.json();
        
        // 1. 폼 데이터 바인딩
        const form = document.getElementById('editForm');
        form.reset();

        // DTO 필드명과 HTML name 속성이 일치하면 자동 매핑
        for (const key in data) {
            const field = form.querySelector(`[name="${key}"]`);
            if (field) {
                // Checkbox, Radio 처리 필요 시 추가 로직 작성
				let value = data[key];
				
				if (['phoneNo', 'homeTel', 'emergencyPhone'].includes(key)) {
	                value = formatPhoneNumber(value); 
	            }
				
                field.value = value;
            }
        }

        // 2. 상단 배너(Master Info) 수동 바인딩
        // 코드(Code)를 이름(Name)으로 변환하여 표시
        const deptName = codeMaps.dept[data.deptCode] || data.deptCode || '-';
        const rankName = codeMaps.rank[data.rankCode] || data.rankCode || '-';
        const dutyName = codeMaps.duty[data.dutyCode] || data.dutyCode || '-';
        
        document.getElementById('viewDeptName').innerText = deptName;
        document.getElementById('viewRankName').innerText = rankName;
        document.getElementById('viewDutyName').innerText = dutyName;

        // 입사일 (yyyy-MM-dd)
        if (data.entryDate) {
            document.querySelector('input[name="entryDate"]').value = data.entryDate;
        }

        // 상태 뱃지
        const statusBadge = document.getElementById('viewStatusBadge');
        const statusName = codeMaps.status[data.empStatus] || data.empStatus || 'Unknown';
        
        statusBadge.innerText = statusName;
        statusBadge.className = 'badge'; // 초기화
        if (data.empStatus === '10') statusBadge.classList.add('bg-success');      // 재직
        else if (data.empStatus === '99') statusBadge.classList.add('bg-secondary'); // 퇴직
        else statusBadge.classList.add('bg-warning');                                // 휴직 등

        // 3. UI 상태 초기화 (수정 모드 해제)
        toggleEditMode(false);
        // 검색창에도 사번 세팅 (조회된 사번 확인용)
        const searchInput = document.getElementById('searchKeyword');
        if(searchInput) searchInput.value = pernr;

    } catch (error) {
        console.error('데이터 로드 실패:', error);
        alert('데이터를 불러오는 중 문제가 발생했습니다.');
    }
}

function searchCard() {
    const keyword = document.getElementById('searchKeyword').value;
    if (!keyword) {
        alert('사번 또는 성명을 입력하세요.');
        return;
    }
    loadCardData(keyword);
}


function toggleEditMode(isEdit) {
    const form = document.getElementById('editForm');
    
    // 현재 활성화된 탭 내부의 입력 필드만 제어
    // (지금은 #basicInfo 탭만 구현되어 있으므로 전체 제어해도 무방)
    const inputs = form.querySelectorAll('#basicInfo input, #basicInfo select, #basicInfo button');

    inputs.forEach(el => {
        // [예외] 주민번호(ssn), 주소검색버튼 등은 별도 로직 따름
        if (el.name === 'ssn') {
            el.disabled = true; // 주민번호는 항상 비활성 (별도 버튼으로 수정)
        } else if (el.id === 'btnAddrSearch') {
            el.disabled = !isEdit; // 주소 검색 버튼
        } else {
            el.disabled = !isEdit;
        }
    });
	
	const requiredLabels = form.querySelectorAll('.form-label.required');

    requiredLabels.forEach(label => {
        // 이미 추가된 별표가 있는지 확인 (중복 추가 방지)
        const existingStar = label.querySelector('.required-star');

        if (isEdit) {
            // 수정 모드: 별표가 없으면 추가
            if (!existingStar) {
                const star = document.createElement('span');
                star.className = 'text-danger required-star ms-1'; // Bootstrap 클래스 활용
                star.innerText = '*';
                label.appendChild(star);
            }
        } else {
            // 조회 모드: 별표가 있으면 삭제
            if (existingStar) {
                existingStar.remove();
            }
        }
    });

    // 버튼 그룹 전환
    if (isEdit) {
        document.getElementById('btnEditMode').style.display = 'none';
        document.getElementById('editBtnGroup').style.display = 'block';
    } else {
        document.getElementById('btnEditMode').style.display = 'block';
        document.getElementById('editBtnGroup').style.display = 'none';
    }
}

async function savePrivInfo() {
    if (!confirm('변경된 내용을 저장하시겠습니까?')) return;

    const form = document.getElementById('editForm');
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());
	
	const ssnInput = form.querySelector('input[name="ssn"]');
	    if (ssnInput) {
	        data.ssn = ssnInput.value;
			console.log(ssnInput.value);
	    }

    // 유효성 검사
    if (!data.pernr) { alert('사원 번호가 없습니다.'); return; }
    if (!data.phoneNo) { alert('휴대전화번호는 필수입니다.'); return; }


    try {
        const response = await fetch('/hr/employee-priv', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert('성공적으로 저장되었습니다.');
            loadCardData(data.pernr);
        } else {
            const errorMsg = await response.text();
            alert('저장 실패: ' + errorMsg);
        }
    } catch (error) {
        console.error('저장 오류:', error);
        alert('저장 중 서버 오류가 발생했습니다.');
    }
}

// ----------------------------------------------------
// [API] 카카오 주소 검색
// ----------------------------------------------------
function execDaumPostcode() {
    new daum.Postcode({
		shorthand : false,
        oncomplete: function(data) {
            var addr = (data.userSelectedType === 'R') ? data.roadAddress : data.jibunAddress;
            
            document.getElementById('postCode').value = data.zonecode;
            document.getElementById("addrMain").value = addr;
            document.getElementById("sido").value = data.sido;
            document.getElementById("sigungu").value = data.sigungu;
			
			document.getElementById("addrDetail").value = '';
            document.getElementById("addrDetail").focus();
        }
    }).open();
}


const autoHyphenMulti = (target) => {
    let value = target.value.replace(/[^0-9]/g, '');

    let result = '';

    if (value.startsWith('02')) {
        // [서울] 02로 시작하는 경우
        
        // 최대 10자리까지만 허용 (02-1234-5678)
        if (value.length > 10) value = value.substring(0, 10);

        if (value.length < 10) {
            // 9자리 이하일 때 (예: 021234567 -> 02-123-4567)
            // 숫자가 차오를 때는 2-3-4 패턴으로 보여주다가
            result = value.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
            
            // 아직 3번째 그룹(뒷자리)이 완성되지 않았을 때(입력 중)를 위한 코드
            if (value.length < 9) {
                 result = value.replace(/(\d{2})(\d{0,4})(\d{0,4})/, '$1-$2-$3');
            }
        } else {
            // 10자리일 때 (예: 0212345678 -> 02-1234-5678)
            result = value.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
        }

    } else {
        // [그 외] 010, 031, 051 등
        
        // 최대 11자리까지만 허용
        if (value.length > 11) value = value.substring(0, 11);

        if (value.length < 11) {
            // 10자리 이하 (예: 0311234567 -> 031-123-4567)
            result = value.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
            
             // 입력 중 코드
            if (value.length < 10) {
                result = value.replace(/(\d{0,3})(\d{0,4})(\d{0,4})/, '$1-$2-$3');
            }
        } else {
            // 11자리 (예: 01012345678 -> 010-1234-5678)
            result = value.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
        }
    }

    // 3. 뒤에 남는 하이픈(--) 제거 후 반영
    target.value = result.replace(/-{1,2}$/g, "");
}

// [Util] 전화번호 포맷팅 코어 함수 (문자열 -> 문자열)
function formatPhoneNumber(input) {
    if(!input) return '';
    
    const value = input.toString().replace(/[^0-9]/g, '');
    let result = '';

    // 2. 서울(02) vs 그 외 구분 로직
    if (value.startsWith('02')) {
        // 서울: 02-xxxx-xxxx (최대 10자리)
        if (value.length < 10) { 
            // 02-123-4567 (9자리)
            result = value.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
        } else { 
            // 02-1234-5678 (10자리)
            result = value.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3'); 
        }
    } else {
        // 그 외: 010-xxxx-xxxx (최대 11자리)
        if (value.length < 11) {
            // 031-123-4567 (10자리)
            result = value.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
        } else {
            // 010-1234-5678 (11자리)
            result = value.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
        }
    }
    
    // 포맷팅이 안 먹히는 짧은 숫자 등의 경우 원본 반환 (혹은 하이픈 제거 처리)
    return result || value; 
}