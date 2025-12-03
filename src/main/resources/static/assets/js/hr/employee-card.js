const codeMaps = {
    dept: {},
    rank: {},
    duty: {},
    status: {},
    nation: {},   
    marital: {},  
    military: {},
	relation: {}, 
	job: {}
};

document.addEventListener('DOMContentLoaded', function () {
    
    // 코드 리스트를 Map으로 변환
    if (typeof deptList !== 'undefined') deptList.forEach(c => codeMaps.dept[c.code] = c.name);
    if (typeof rankList !== 'undefined') rankList.forEach(c => codeMaps.rank[c.code] = c.name);
    if (typeof dutyList !== 'undefined') dutyList.forEach(c => codeMaps.duty[c.code] = c.name);
    if (typeof statusList !== 'undefined') statusList.forEach(c => codeMaps.status[c.code] = c.name);
	if (typeof nationList !== 'undefined') nationList.forEach(c => codeMaps.nation[c.code] = c.name);
	if (typeof maritalList !== 'undefined') maritalList.forEach(c => codeMaps.marital[c.code] = c.name);
    if (typeof militaryList !== 'undefined') militaryList.forEach(c => codeMaps.military[c.code] = c.name);
	if (typeof relationList !== 'undefined') relationList.forEach(c => codeMaps.relation[c.code] = c.name);
    if (typeof jobList !== 'undefined') jobList.forEach(c => codeMaps.job[c.code] = c.name);

    // 초기 로드 (URL 파라미터 or 로그인 유저)
	const urlParams = new URLSearchParams(window.location.search);
    let targetPernr = urlParams.get('pernr');

	const isManager = document.getElementById('searchKeyword') !== null;

	// "URL에 사번이 있고" + "내 사번이 아닌데" + "관리자도 아니라면" -> 차단
	   if (targetPernr && targetPernr !== loginUserPernr && !isManager) {
	       alert('타인의 정보를 조회할 권한이 없습니다.\n본인 정보로 이동합니다.');
	       // 본인 사번 URL로 페이지 새로고침(이동)
	       window.location.href = location.pathname + '?pernr=' + loginUserPernr;
	       return;
	   }

	   // 사번이 없으면 본인 사번 기본값
	   if (!targetPernr) {
	   		targetPernr = loginUserPernr;
		}

	   if (targetPernr) {
	       loadCardData(targetPernr);
	   }

    // 검색 엔터키 이벤트
    const searchInput = document.getElementById('searchKeyword');
    if (searchInput) {
        searchInput.addEventListener("keypress", function(event) {
            if (event.key === "Enter") {
                event.preventDefault();
                searchCard();
            }
        });
    }
	
	// 발령 이력 탭 클릭 시 데이터 로드 (Lazy Loading)
    const tabJoin = document.getElementById('tab-join');
    if (tabJoin) {
        tabJoin.addEventListener('shown.bs.tab', function () {
            const currentPernr = getCurrentPernr();
            loadAppointHistory(currentPernr);
        });
    }

    // 가족 정보 탭 클릭 시 데이터 로드
    const tabFamily = document.getElementById('tab-family');
    if (tabFamily) {
        tabFamily.addEventListener('shown.bs.tab', function () {
            const currentPernr = getCurrentPernr();
            loadFamilyData(currentPernr);
        });
    }
	
    // 탭 변경 시 '정보 수정' 버튼 표시/숨김 제어
    const tabEls = document.querySelectorAll('button[data-bs-toggle="tab"]');
    tabEls.forEach(tab => {
        tab.addEventListener('shown.bs.tab', function (event) {
            const activeTabId = event.target.id; // 클릭한 탭의 ID (tab-basic, tab-join, tab-family)
            const btnEdit = document.getElementById('btnEditMode');
            const btnGroup = document.getElementById('editBtnGroup');

            toggleEditMode(false); 

            if (activeTabId === 'tab-basic') {
                if (btnEdit) btnEdit.style.display = 'block';
            } else {
                if (btnEdit) btnEdit.style.display = 'none';
                if (btnGroup) btnGroup.style.display = 'none'; 
            }
        });
    });
	
	
});

function getCurrentPernr() {
    const searchInput = document.getElementById('searchKeyword');
    // 검색창 값이 있으면(관리자 검색) 그 값, 없으면 로그인 유저(일반 사용자)
    return (searchInput && searchInput.value) ? searchInput.value : loginUserPernr;
}

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

async function loadAppointHistory(pernr) {
    const container = document.getElementById('appointTimeline');
    if (!pernr || !container) return;

    try {
        // 로딩 표시
        container.innerHTML = `
            <div class="text-center text-muted py-5">
                <div class="spinner-border text-primary spinner-border-sm" role="status"></div>
                <div class="mt-2 small">이력을 불러오는 중...</div>
            </div>`;

        // API 호출
        const response = await fetch(`/hr/employee-appoint/history/${pernr}`);
        if (!response.ok) throw new Error('이력 조회 실패');
        
        const historyList = await response.json();

        if (!historyList || historyList.length === 0) {
            container.innerHTML = '<div class="text-center text-muted py-5">발령 이력이 없습니다.</div>';
            return;
        }

        // HTML 조립
        let html = '';
        historyList.forEach(hist => {
            html += renderTimelineItem(hist);
        });

        // 마지막 라인 마감 (템플릿 스타일 유지)
        html += `
            <div class="d-flex align-items-center">
                <div class="flex-fill ps-3 pb-0 timeline-hrline"></div>
            </div>`;

        container.innerHTML = html;

    } catch (error) {
        console.error(error);
        container.innerHTML = '<div class="text-center text-danger py-5">정보를 불러오지 못했습니다.</div>';
    }
}

/**
 * 타임라인 아이템 렌더링 
 */
function renderTimelineItem(hist) {
    // 1. 상세 변경 내역 (Details)
    let detailsHtml = '';
    if (hist.details && hist.details.length > 0) {
        detailsHtml = '<ul class="list-unstyled mb-0 mt-2 text-muted small">';
        hist.details.forEach(det => {
            const label = det.fieldName || det.fieldCode; // 필드명 없으면 코드로
			
			let oldName = det.valueOld;
	        let newName = det.value;
	
	        // fieldCode에 따라 매핑할 코드 리스트 결정
	        if (det.fieldCode.includes('DEPT')) { // 부서 (TO_DEPT)
	            oldName = codeMaps.dept[det.valueOld] || det.valueOld;
	            newName = codeMaps.dept[det.value] || det.value;
	        } 
	        else if (det.fieldCode.includes('RANK')) { // 직급 (TO_RANK)
	            oldName = codeMaps.rank[det.valueOld] || det.valueOld;
	            newName = codeMaps.rank[det.value] || det.value;
	        }
	        else if (det.fieldCode.includes('DUTY')) { // 직무 (TO_DUTY)
	            oldName = codeMaps.duty[det.valueOld] || det.valueOld;
	            newName = codeMaps.duty[det.value] || det.value;
	        }
	        else if (det.fieldCode.includes('SALARY')) { // 연봉 (숫자 포맷)
	            if(det.valueOld) oldName = Number(det.valueOld).toLocaleString();
	            if(det.value) newName = Number(det.value).toLocaleString();
	        }
            
            // Old -> New 표시 로직
			let valueDisplay = '';
            if (oldName) {
                valueDisplay = `${oldName} <i class="ti ti-arrow-right mx-1"></i> <b>${newName}</b>`;
            } else {
                valueDisplay = `<b>${newName}</b>`;
            }
            
            detailsHtml += `<li>• ${label} : ${valueDisplay}</li>`;
        });
        detailsHtml += '</ul>';
    }

    // 2. 비고 (Remark)
    if(hist.remark) {
        detailsHtml += `<div class="mt-2 text-info small bg-light p-2 rounded"><i class="ti ti-note me-1"></i> ${hist.remark}</div>`;
    }

    // 3. 스타일링 (취소, 점 색상)
    const isCanceled = (hist.isCanceled === 'Y');
    const opacityClass = isCanceled ? 'opacity-50' : '';
    const badgeHtml = isCanceled ? '<span class="badge bg-danger ms-1">철회됨</span>' : '';
    
    // 발령 유형별 점 색상 (10:채용-초록, 20:승진-파랑, 30:전보-주황, 90:퇴직-빨강)
    let dotColorClass = 'text-gray-2';
    if (hist.ruleType === '10') dotColorClass = 'text-success';
    else if (hist.ruleType === '20') dotColorClass = 'text-primary';
    else if (hist.ruleType === '30') dotColorClass = 'text-warning';
    else if (hist.ruleType === '90') dotColorClass = 'text-danger';

    // 4. HTML 반환 (User Template Structure)
    return `
        <div class="d-flex align-items-center ${opacityClass}">
            <!-- [Left] Date & Dot -->
            <div class="d-flex align-items-center active-time">
                <span class="timeline-date text-dark fw-bold" style="min-width: 90px;">${hist.effDate}</span>
                <span class="timeline-border d-flex align-items-center justify-content-center bg-white">
                    <i class="ti ti-point-filled ${dotColorClass} fs-18"></i>
                </span>
            </div>
            
            <!-- [Right] Content -->
            <div class="flex-fill ps-3 pb-4 timeline-hrline">
                <div class="mt-4">
                    <p class="fw-medium text-gray-9 mb-1" style="font-size: 1.05rem;">
                        ${hist.ruleName} ${badgeHtml}
                        <span class="text-muted fw-normal ms-1 small">(${hist.reqTitle})</span>
                    </p>
                    <div class="ms-1">
                        ${detailsHtml}
                    </div>
                </div>
            </div>
        </div>
    `;
}

// [TAB 3] 가족 정보 로드
async function loadFamilyData(pernr) {
    const $tbody = $('#familyTableBody');
    const $noMsg = $('#noFamilyMsg');

    if (!pernr) {
		return;
	}

    try {
        // 로딩 중 표시 (테이블 초기화)
        $tbody.html('<tr><td colspan="4" class="text-center py-4"><div class="spinner-border spinner-border-sm text-primary"></div></td></tr>');
        $noMsg.hide();

        const response = await fetch(`/hr/employee-family/simple/${pernr}`); 
        
        if (!response.ok) {
            throw new Error('가족 정보 조회 실패');
        }

        const familyList = await response.json();
        
        $tbody.empty(); // 로딩 스피너 제거

        if (!familyList || familyList.length === 0) {
            $noMsg.show();
            return;
        }

        let html = '';
        familyList.forEach(item => {
			
			const relationTxt = codeMaps.relation[item.familyType] || item.familyType || '-'; 
            const jobTxt      = codeMaps.job[item.jobType]         || item.jobType    || '-';

			html += `
			                <tr>
			                    <td class="fw-bold">${item.familyName}</td>
			                    <td>${relationTxt}</td> <td>${item.birthDate}</td>
			                    <td>${jobTxt}</td>
			                </tr>
			            `;
        });

        $tbody.html(html);

    } catch (error) {
        console.error(error);
        $tbody.html('<tr><td colspan="4" class="text-center text-danger py-3">정보를 불러오지 못했습니다.</td></tr>');
    }
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