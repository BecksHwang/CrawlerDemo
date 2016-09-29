function toNewsList(){
	window.open("/CrawlerDemo/news/newsList.do");
}

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
	var url = '/CrawlerDemo/news/getNews.do';  //线上的url
	//var url = 'js/myData.json';  //测试的url
	var time = null;
	getJson(url,time,initData);
};
initGetJson();
function initData(data){
	for(var i=0;i<data.length;i++){
		data[i].pickTime = moment(data[i].pickTime).format('YYYY-MM-DD HH:mm:ss');
	}
	//初始数据 升序
	data.sort(function(a,b){
		return new Date(a.pickTime.replace(/-/g, "/")) - new Date(b.pickTime.replace(/-/g, "/"));
	});
	vm1 = new Vue({
		el: '#myRow',
		data: {
			items : data
		}
	})
}
function refreshGetJson(){
	vmIndex = vm1.items.length - 1;  //根据最后一个时间 拿新增的数据
	var time = vm1.items[vmIndex].pickTime;
	// var time = vm1.items[0].pickTime;
	var options = '/CrawlerDemo/news/getNews.do';  //线上的url
	var url = options + '?' + 'pickTime' + '=' + time;
	//var url = 'js/myData2.json';  //测试的url
	getJson(url,time,refreshData);
}
function refreshData(data){
	// vm1.items = data.concat(vm1.items);
	vm1.items = vm1.items.concat(data);
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
	var url = '/CrawlerDemo/interaction/getInteractions.do';  //线上的url
	//var url = 'js/myDa.json';  //测试的url
	var time = null;
	getJson(url,time,initData1);
};
initGetJson1();
function initData1(data){
	for(var i=0;i<data.length;i++){
		data[i].pickTime = moment(data[i].pickTime).format('YYYY-MM-DD HH:mm:ss');
	}
	//初始数据 升序
	data.sort(function(a,b){
		return new Date(a.pickTime.replace(/-/g, "/")) - new Date(b.pickTime.replace(/-/g, "/"));
	});
	// console.log(data);
	// console.log('aaa')
	vm2 = new Vue({
		el: '#myRow1',
		data: {
			items2 : data
		}
	})
}
function refreshGetJson1(){
	vmIndex = vm2.items2.length - 1;  //根据最后一个时间 拿新增的数据
  // console.log(vmIndex)
	var time = vm2.items2[vmIndex].pickTime;
	var options = '/CrawlerDemo/interaction/getInteractions.do';  //线上的url
	var url = options + '?' + 'pickTime' + '=' + time;
	//var url = 'js/myDa2.json';  //测试的url
	getJson(url,time,refreshData1);
}
function refreshData1(data){
	console.log(vmIndex);
	console.log('data');
	console.log(data);
	vm2.items2 = vm2.items2.concat(data);  
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

