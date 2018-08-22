
$(function(){
	$('#dlg').dialog('close')//隐藏提示框
	showUserInfo();
	});
//文件上传
function uploadFile(){
	if(tableTitleCheck()&&excelFileCheck()){//校验
		var formdata = new FormData($("#upload-file-form")[0]); // 模拟表单对象（获取excel文件）
		console.log($("#upload-file-form"));
		console.log($("#upload-file-form")[0]);
		console.log(formdata);
		formdata.append("tableTitle",$("#tableTitle").combobox("getValue"));//获取需要上传的参数
		$.ajax({
		    url: "/user/uploadFile",
		    type: "POST",
		    //data: new FormData($("#upload-file-form")[0]),
		    data: formdata,
		    enctype: 'multipart/form-data',
		    processData: false,
		    contentType: false,
		    cache: false,
		    success: function () {
		    	showUserInfo();
		    },
		    error: function () {

		    }
		});
	}
}
//下载文件
function downloadFile(){
	if(tableTitleCheck()){
		window.location.href = "/user/dowloadExcel?tableTitle="+$("#tableTitle").combobox("getValue");
	}
}
//判断是否选择需要上传的excel
function excelFileCheck(){
	var tableTitle = $("#uploadfile").filebox('getValue');
	if(null == tableTitle || tableTitle.length==0){
		$("#dlg").html("请选择excel文件");
		$('#dlg').dialog('open');
		return false;
	}else{
		return true;
	}
}
//判断是否选择需要导入的表名
function tableTitleCheck(){
	var tableTitle = $("#tableTitle").combobox("getValue");
	if(null == tableTitle || tableTitle.length==0){
		$("#dlg").html("请选择表名");
		$('#dlg').dialog('open');
		return false;
	}else{
		return true;
	}
}
function changeAlign(align){
	//$('#fb').filebox({buttonAlign:align});
}
//数据绑定
function showUserInfo(){
	$("#DataGridInbound").datagrid({
	      title: '数据详情',
	      idField: 'Id',
	      rownumbers: 'true',
	      url: '/user/all',
	      pagination: true,//表示在datagrid设置分页     
	      loadFilter:pagerFilter,
	      rownumbers: true,
	      singleSelect: true,
	      striped: true,
	      nowrap: true,
	      collapsible: true,
	      fitColumns: true,
	      remoteSort: false,
	      loadMsg: "数据加载中，请稍后...",
	      queryParams: {},
	      onLoadSuccess: function (data) {
	        if (data.total == 0) {
	          var body = $(this).data().datagrid.dc.body2;
	          body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h1>暂无数据</h1></td></tr>');
	          $(this).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
	        }
	          //如果通过调用reload方法重新加载数据有数据时显示出分页导航容器
	        else $(this).closest('div.datagrid-wrap').find('div.datagrid-pager').show();
	      },
	      columns: [[
	    /*  { field: 'ck', checkbox: true },
	        { field: 'userId', hidden: 'true' },
	        { field: 'userName', hidden: 'true' },
	        { field: 'password', hidden: 'true' },
	        { field: 'phone', hidden: 'true' },
	        { field: 'SqNo', title: '入库参考号', width: '100', align: 'left', sortable: true },
	        {
	          field: 'Status', title: '状态', width: '100', align: 'left', sortable: true,
	          formatter: function (value, index, row) {
	            if (value == "1") {
	              return '<span style="color:green;">已入库</span>';
	            }
	            else if (value == "-1") {
	              return '<span style="color:#FFA54F;">待入库</span>';
	            }
	          }
	        },
	        {
	          field: 'InboundDate', title: '入库日期', width: '100', align: 'left', sortable: true,          
	          formatter: function (date) {
	            var pa = /.*\((.*)\)/;
	            var unixtime = date.match(pa)[1].substring(0, 10); //通过正则表达式来获取到时间戳的字符串
	            return getTime(unixtime);
	          }
	        }, */
	        { field: 'userId', title: '员工号', width: '100', align: 'left', sortable: true },
	        { field: 'userName', title: '员工姓名', width: '100', align: 'left', sortable: true },
	        { field: 'password', title: '密码', width: '100', align: 'left', sortable: true },
	        { field: 'phone', title: '电话', width: '100', align: 'left', sortable: true },
	      ]],
	    });
}

 
//分页功能    
function pagerFilter(data) {
    if (typeof data.length == 'number' && typeof data.splice == 'function') {
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage: function (pageNum, pageSize) {
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh', {
                pageNumber: pageNum,
                pageSize: pageSize
            });
            dg.datagrid('loadData', data);
        }
    });
    if (!data.originalRows) {
        if(data.rows)
            data.originalRows = (data.rows);
        else if(data.data && data.data.rows)
            data.originalRows = (data.data.rows);
        else
            data.originalRows = [];
    }
    var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}  