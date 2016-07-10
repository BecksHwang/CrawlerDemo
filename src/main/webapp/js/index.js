function getJson(url,time,func){
	$.ajax({
		type:'GET',
		url:url,
		data:{ 'pickTime':time},
		dataType: 'json',
		success: function(data){
			func(data);
		},
		error: function(data){
			alert(JSON.stringify(data));
		}
	});
}

// 左边！！
function initGetJson(){
	var url = '/ggzzjc/news/getNews';  //家蓓的url
	//var url = 'js/myData.json';  //安安的url
	var time = null;
	getJson(url,time,initData);
};
initGetJson();
function initData(data){
	for(var i=0;i<data.length;i++){
		data[i].pickTime = moment(data[i].pickTime).format('YYYY-MM-DD HH:mm:ss');
	}
	vm1 = new Vue({
		el: '#myRow',
		data: {
			items : data
		}
	})
}
function refreshGetJson(){
	var time = vm1.items[0].pickTime;
	var options = '/ggzzjc/news/getNews';  //家蓓的url
	var url = options + '?' + 'pickTime' + '=' + time;
	//var url = 'js/myData2.json';  //安安的url
	getJson(url,time,refreshData);
}
function refreshData(data){
	vm1.items = data.concat(vm1.items);
	for(var i=0;i<vm1.items.length;i++){
		vm1.items[i].pickTime = moment(vm1.items[i].pickTime).format('YYYY-MM-DD HH:mm:ss');
	}
	if(data.length){
		var layout = 'bottomLeft';
		setInterval(show_newsNotice(data,layout),5000);
	}
}
setInterval(refreshGetJson,5000);


// 右边！！
function initGetJson1(){
	var url = '/ggzzjc/interaction/getInteractions';  //家蓓的url
	//var url = 'js/myDa.json';  //安安的url
	var time = null;
	getJson(url,time,initData1);
};
initGetJson1();
function initData1(data){
	for(var i=0;i<data.length;i++){
		data[i].pickTime = moment(data[i].pickTime).format('YYYY-MM-DD HH:mm:ss');
	}
	vm2 = new Vue({
		el: '#myRow1',
		data: {
			items2 : data
		}
	})
}
function refreshGetJson1(){
	var time = vm2.items2[0].pickTime;
	var options = '/ggzzjc/interaction/getInteractions';  //家蓓的url
	var url = options + '?' + 'pickTime' + '=' + time;
	//var url = 'js/myDa2.json';  //安安的url
	getJson(url,time,refreshData1);
}
function refreshData1(data){
	vm2.items2 = data.concat(vm2.items2);
	for(var i=0;i<vm2.items2.length;i++){
		vm2.items2[i].pickTime = moment(vm2.items2[i].pickTime).format('YYYY-MM-DD HH:mm:ss');
	}
	if(data.length){
		var layout = 'bottomRight';
		setInterval(show_newsNotice(data,layout),5000);
	}
}
setInterval(refreshGetJson1,5000);



//复制粘贴
var clipboard = new Clipboard('.btn');
var clipboard2 = new Clipboard('.btn2');

//消息通知框
function show_newsNotice(data,layout) {
	var newsId = data[0].id;
	//console.log(newsId);
	var newsNum = data.length;
    var n = noty({
    	text: '<a  href="#' + newsId + '" style="font-size:15px;font-weight:bold;">跳转</a></br><span>有' + newsNum + '条新消息</span> ',
        type        : 'alert', //默认类型
        timeout: 5000, //5秒后自动关闭
        dismissQueue: true, //是否添加到队列
        maxVisible: 2,  //一队列最多显示2条
        closeWith: ['click'], //关闭事件
        layout      : layout, //默认布局(包括'top'等11种)
        theme       : 'bootstrapTheme'
    });
}

