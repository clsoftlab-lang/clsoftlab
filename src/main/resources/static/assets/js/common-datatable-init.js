function initializeResizableDataTable(tableSelector, datatableOptions) {

	const defaultOptions = {
	        'autoWidth': true,
			'searching' : false,

	        'layout': {
	            topStart: 'pageLength',  // 'l' (페이지 길이 메뉴)
	            bottomStart: 'info',     // 'i' (정보)
	            bottomEnd: 'paging'      // 'p' (페이징)
	        }
	    };
		
	const finalOptions = $.extend(true, {}, defaultOptions, datatableOptions);

    // 2. DataTable 초기화
    const dataTableInstance = $(tableSelector).DataTable(finalOptions);

    // 3. colResizable 플러그인 초기화
    if ($.fn.colResizable) {
        $(tableSelector).colResizable({
            liveDrag: true,
            postUnload: true,
            minWidth: 50,
            onResize: function() {
                // 리사이즈 시 DataTables 레이아웃 다시 그리기
                if (dataTableInstance) {
                    dataTableInstance.draw(false);
                }
            }
        });
    }

        
    // 생성된 DataTable 인스턴스 반환 (추후 사용 가능)
    return dataTableInstance;
}