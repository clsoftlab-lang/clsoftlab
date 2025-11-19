if($('#attendance').length > 0) {
			var ctx = document.getElementById('attendance').getContext('2d');
	    var mySemiDonutChart = new Chart(ctx, {
	        type: 'doughnut', // Chart type
	        data: {
	            labels: ['정상 출근','연차/휴가', '지각', '결근'],
	            datasets: [{
	                label: 'Semi Donut',
	                data: [135, 8, 7, 4],						
	                backgroundColor: ['#03C95A', '#FFC107', '#0C4B5E', '#E70D0D'],
	                borderWidth: 5,
					borderRadius: 10,
	                borderColor: '#fff', // Border between segments
	                hoverBorderWidth: 0,   // Border radius for curved edges
	                cutout: '60%',   
	            }]
	        },
			options: {
				rotation: -100,
				circumference: 200,
				layout: {
					padding: {
						top: -20,    // Set to 0 to remove top padding
						bottom: -20, // Set to 0 to remove bottom padding
					}
				},
				responsive: true,
				maintainAspectRatio: false,
				plugins: {
					legend: {
						display: false // Hide the legend
					}
				},
			  }
	    });
		}
		
// 1. 월별 인건비 추이 (Bar Chart)
    if($('#payrollChart').length > 0) {
        var ctxPayroll = document.getElementById('payrollChart').getContext('2d');
        var myPayrollChart = new Chart(ctxPayroll, {
            type: 'bar',
            data: {
                labels: ['1월', '2월', '3월', '4월', '5월', '6월'],
                datasets: [{
                    label: '총 급여 지출 (백만 원)',
                    data: [420, 430, 425, 450, 460, 480],
                    backgroundColor: '#556ee6', // Primary color
                    borderRadius: 4,
                    barThickness: 20
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { display: false } // HTML 타이틀이 있으므로 범례 숨김
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        grid: { borderDash: [5, 5], drawBorder: false }
                    },
                    x: {
                        grid: { display: false }
                    }
                }
            }
        });
    }

    // 2. 채용 퍼널 (Horizontal Bar Chart)
    if($('#recruitmentChart').length > 0) {
        var ctxRecruit = document.getElementById('recruitmentChart').getContext('2d');
        var myRecruitChart = new Chart(ctxRecruit, {
            type: 'bar',
            indexAxis: 'y', // 가로형 막대로 변환
            data: {
                labels: ['서류 접수', '코딩 테스트', '1차 면접', '2차 면접', '최종 합격'],
                datasets: [{
                    label: '인원 수',
                    data: [120, 80, 45, 20, 5],
                    backgroundColor: [
                        '#e9ecef', // Gray
                        '#ced4da',
                        '#adb5bd', 
                        '#6c757d', 
                        '#343a40'  // Dark
                    ],
                    borderRadius: 4,
                    barThickness: 20
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { display: false }
                },
                scales: {
                    x: {
                        beginAtZero: true,
                        grid: { display: false }
                    },
                    y: {
                        grid: { display: false }
                    }
                }
            }
        });
    }

    // 3. 부서별 역량 평가 (Radar Chart) 
    if($('#skillRadarChart').length > 0) {
        var ctxSkill = document.getElementById('skillRadarChart').getContext('2d');
        var mySkillChart = new Chart(ctxSkill, {
            type: 'radar',
            data: {
                labels: ['기술력', '커뮤니케이션', '리더십', '문제해결', '협업', '창의성'],
                datasets: [{
                    label: '개발팀',
                    data: [90, 70, 60, 85, 80, 75],
                    fill: true,
                    backgroundColor: 'rgba(85, 110, 230, 0.2)', // Primary transparent
                    borderColor: '#556ee6',
                    pointBackgroundColor: '#556ee6',
                    pointBorderColor: '#fff',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: '#556ee6'
                }, {
                    label: '영업팀',
                    data: [60, 95, 80, 70, 85, 60],
                    fill: true,
                    backgroundColor: 'rgba(244, 106, 106, 0.2)', // Danger transparent
                    borderColor: '#f46a6a',
                    pointBackgroundColor: '#f46a6a',
                    pointBorderColor: '#fff',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: '#f46a6a'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { display: false } // HTML 하단에 뱃지로 표시했으므로 숨김
                },
                scales: {
                    r: {
                        angleLines: { display: true },
                        suggestedMin: 0,
                        suggestedMax: 100,
                        ticks: { display: false } // 숫자 숨김
                    }
                }
            }
        });
    }

    // 4. 부서별 만족도 (Polar Area Chart)
    if($('#satisfactionChart').length > 0) {
        var ctxSatisfaction = document.getElementById('satisfactionChart').getContext('2d');
        var mySatisfactionChart = new Chart(ctxSatisfaction, {
            type: 'polarArea',
            data: {
                labels: ['개발팀', '인사팀', '영업팀', '마케팅팀', '경영지원'],
                datasets: [{
                    label: '만족도 점수',
                    data: [8.5, 9.0, 7.8, 8.2, 8.8],
                    backgroundColor: [
                        'rgba(85, 110, 230, 0.8)',  // Primary
                        'rgba(52, 195, 143, 0.8)',  // Success
                        'rgba(244, 106, 106, 0.8)', // Danger
                        'rgba(241, 180, 76, 0.8)',  // Warning
                        'rgba(80, 165, 241, 0.8)'   // Info
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { 
                        position: 'right',
                        labels: { usePointStyle: true, boxWidth: 8 }
                    }
                },
                scales: {
                    r: { ticks: { display: false } } // 눈금 숨김
                }
            }
        });
    }

    // 5. 교육 이수 시간 추이 (Line Chart)
    if($('#trainingLineChart').length > 0) {
        var ctxTraining = document.getElementById('trainingLineChart').getContext('2d');
        var myTrainingChart = new Chart(ctxTraining, {
            type: 'line',
            data: {
                labels: ['1월', '2월', '3월', '4월', '5월', '6월'],
                datasets: [{
                    label: '평균 이수 시간',
                    data: [12, 15, 10, 18, 20, 22],
                    borderColor: '#34c38f', // Success Green
                    backgroundColor: 'rgba(52, 195, 143, 0.1)',
                    pointBackgroundColor: '#34c38f',
                    tension: 0.4, // 곡선 부드럽게
                    fill: true,
                    borderWidth: 3
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { display: false }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        grid: { borderDash: [5, 5] }
                    },
                    x: {
                        grid: { display: false }
                    }
                }
            }
        });
    }

    // 6. 업무 진행 현황 (Doughnut Chart)
    if($('#taskDoughnutChart').length > 0) {
        var ctxTask = document.getElementById('taskDoughnutChart').getContext('2d');
        var myTaskChart = new Chart(ctxTask, {
            type: 'doughnut',
            data: {
                labels: ['진행 전', '진행 중', '완료', '보류'],
                datasets: [{
                    data: [15, 45, 30, 10],
                    backgroundColor: [
                        '#f1b44c', // Warning
                        '#556ee6', // Primary
                        '#34c38f', // Success
                        '#f46a6a'  // Danger
                    ],
                    borderWidth: 2,
                    borderColor: '#fff',
                    hoverOffset: 4
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                cutout: '75%', // 도넛 두께 얇게
                plugins: {
                    legend: { 
                        position: 'bottom', 
                        labels: { usePointStyle: true, padding: 20, boxWidth: 8 } 
                    }
                }
            }
        });
    }
	
	// 7. 부서별 업무강도 분석 (Bubble Chart)
	    // 시나리오: X축(월 평균 야근시간), Y축(성과 점수), r(부서 인원 규모)
	    if($('#bubbleChart').length > 0) {
	        var ctxBubble = document.getElementById('bubbleChart').getContext('2d');
	        var myBubbleChart = new Chart(ctxBubble, {
	            type: 'bubble',
	            data: {
	                datasets: [{
	                    label: '개발팀',
	                    data: [{x: 20, y: 90, r: 15}], // 야근 많음, 성과 높음, 인원 많음
	                    backgroundColor: 'rgba(85, 110, 230, 0.6)'
	                }, {
	                    label: '영업팀',
	                    data: [{x: 10, y: 85, r: 10}], // 야근 보통, 성과 보통
	                    backgroundColor: 'rgba(244, 106, 106, 0.6)'
	                }, {
	                    label: '인사팀',
	                    data: [{x: 5, y: 95, r: 8}],   // 야근 적음, 성과 높음
	                    backgroundColor: 'rgba(52, 195, 143, 0.6)'
	                }, {
	                    label: '디자인',
	                    data: [{x: 15, y: 80, r: 6}],
	                    backgroundColor: 'rgba(241, 180, 76, 0.6)'
	                }]
	            },
	            options: {
	                responsive: true,
	                maintainAspectRatio: false,
	                plugins: {
	                    legend: { position: 'top', labels: { boxWidth: 8, usePointStyle: true } }
	                },
	                scales: {
	                    y: { 
	                        beginAtZero: false, 
	                        title: { display: true, text: '성과 점수' },
	                        grid: { borderDash: [3, 3] }
	                    },
	                    x: { 
	                        title: { display: true, text: '야근 시간' },
	                        grid: { display: false } 
	                    }
	                }
	            }
	        });
	    }

	    // 8. 채용 인원 vs 퇴사율 (Mixed Chart: Bar + Line)
	    if($('#mixedChart').length > 0) {
	        var ctxMixed = document.getElementById('mixedChart').getContext('2d');
	        var myMixedChart = new Chart(ctxMixed, {
	            type: 'bar',
	            data: {
	                labels: ['1월', '2월', '3월', '4월', '5월', '6월'],
	                datasets: [{
	                    label: '채용 인원 (명)',
	                    data: [10, 15, 8, 12, 20, 18],
	                    backgroundColor: '#556ee6',
	                    order: 2 // 뒤에 그려짐
	                }, {
	                    label: '퇴사율 (%)',
	                    data: [2.1, 1.5, 3.0, 1.8, 1.2, 0.9],
	                    type: 'line', // 선 그래프
	                    borderColor: '#f46a6a',
	                    backgroundColor: 'transparent',
	                    borderWidth: 3,
	                    tension: 0.4,
	                    yAxisID: 'y1', // 오른쪽 Y축 사용
	                    order: 1 // 앞에 그려짐
	                }]
	            },
	            options: {
	                responsive: true,
	                maintainAspectRatio: false,
	                plugins: { legend: { display: false } },
	                scales: {
	                    y: {
	                        beginAtZero: true,
	                        position: 'left',
	                        grid: { display: false }
	                    },
	                    y1: { // 오른쪽 축 설정
	                        beginAtZero: true,
	                        position: 'right',
	                        grid: { borderDash: [5, 5] }
	                    },
	                    x: { grid: { display: false } }
	                }
	            }
	        });
	    }

	    // 9. 부서별 성별 구성 (Stacked Bar Chart)
	    if($('#stackedBarChart').length > 0) {
	        var ctxStacked = document.getElementById('stackedBarChart').getContext('2d');
	        var myStackedChart = new Chart(ctxStacked, {
	            type: 'bar',
	            data: {
	                labels: ['개발', '영업', '인사', '마케팅', '지원'],
	                datasets: [{
	                    label: '남성',
	                    data: [30, 20, 5, 8, 4],
	                    backgroundColor: '#556ee6'
	                }, {
	                    label: '여성',
	                    data: [10, 15, 10, 12, 6],
	                    backgroundColor: '#f46a6a'
	                }]
	            },
	            options: {
	                responsive: true,
	                maintainAspectRatio: false,
	                plugins: { legend: { position: 'top', align: 'end', labels: { boxWidth: 10 } } },
	                scales: {
	                    x: { 
	                        stacked: true, // X축 스택 설정
	                        grid: { display: false } 
	                    },
	                    y: { 
	                        stacked: true, // Y축 스택 설정
	                        beginAtZero: true,
	                        grid: { borderDash: [5, 5] }
	                    }
	                }
	            }
	        });
	    }