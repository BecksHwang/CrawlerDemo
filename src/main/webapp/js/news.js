
var today = moment().hours(23).minutes(59).seconds(59);
$('#filter-beginDate-group').datetimepicker({
	format : "YYYY-MM-DD HH:mm:ss",
	maxDate : today,
	showTodayButton : true,
	showClear : true,
	showClose : true
});
$('#filter-endDate-group').datetimepicker({
	format : "YYYY-MM-DD HH:mm:ss",
	maxDate : today,
	showTodayButton : true,
	showClear : true,
	showClose : true,
});
$("#filter-beginDate-group").on("dp.change", function(e) {
	if (!e.date) {
		$('#filter-endDate-group').data("DateTimePicker").minDate(false);
		;
	} else {
		e.date.hour(0).minute(0).second(0).millisecond(0);
		$('#filter-endDate-group').data("DateTimePicker").minDate(e.date);
	}
});
$("#filter-endDate-group").on("dp.change", function(e) {
	if (!e.date) {
		$('#filter-beginDate-group').data("DateTimePicker").maxDate(false);
	} else {
		e.date.hour(23).minute(59).second(59).millisecond(999);
		$('#filter-beginDate-group').data("DateTimePicker").maxDate(e.date);
	}
});

$('#filter-submit').on('click', function() {
	$('#page').bootstrapTable('selectPage', 1);
});

$('#page').bootstrapTable({
	url : '../news/queryNews.do',
	sidePagination : 'server',
	pagination : true,
	pageList : [ 20, 30, 50 ],
	pageSize : 20,
	sortable : true,
	sortOrder : 'desc',
	sortName : 'id',
	toolbar : '#page-toolbar',
	queryParams : function(params) {
		var title = $("#filter-title").val();
		if (!!title) {
			params.title = title;
		}
		var source = $("#filter-source").val();
		if (!!source) {
			params.source = source;
		}
		var beginDate = $("#beginDate").val();
		if (!!beginDate) {
			params.beginDate = beginDate;
		}
		var endDate = $("#endDate").val();
		if (!!endDate) {
			params.endDate = endDate;
		}
		return params;
	},
	columns : [ {
		field : 'title',
		title : '标题',
		formatter: function(value, row, index) {
        	if(value == null){
        		return '';
        	} else {
        		if(value.length > 50){
        			value= value.substring(0,50)+"...";
        		}
        		return value;
        	}},
	}, {
		field : 'url',
		title : '链接',
		align : 'center',
		valign : 'middle',
		formatter:function(value,row,index){
			var str = '';
			str +='<a href="'+value+'">点击跳转</a>';
			return str;
			},
	}, {
		field : 'source',
		title : '来源名称',
		align : 'center',
		valign : 'middle',
	}, /*{
		field : 'sourceUrl',
		title : '来源网址',
		align : 'center',
		valign : 'middle',
		formatter:function(value,row,index){
			var str = '';
			str +='<a href="'+value+'">点击跳转</a>';
			return str;
			},
	},*/ {
		field : 'pickTime',
		title : '抓取时间',
		align : 'center',
		valign : 'middle',
		formatter:function(value,row,index){
			var commonTime = new Date(value).toLocaleString().replace(/年|月/g, "-").replace(/日/g, "");;
			return commonTime;
			},
	} ]
});
