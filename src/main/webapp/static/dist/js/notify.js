$(function(){
	
	var loadNotify = function(){
		$.post("/notify",function(res){
			if(res.data > 0) { //处理消息为0时通知栏显示数字0
				$("#unreadcount").text(res.data);
			}
		});
	};
	
	setInterval(loadNotify,3000);
	loadNotify();
	
});