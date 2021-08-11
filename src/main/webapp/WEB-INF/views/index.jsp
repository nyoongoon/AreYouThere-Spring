<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
<link rel="icon" href="/favicon.ico" type="image/x-icon">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="resources/css/bootstrap.css">
<link rel="stylesheet" href="resources/css/custom.css">
<title>Are you there?</title>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="resources/js/bootstrap.js"></script>
<script type="text/javascript">
	var lastID = 0;
	
	function checkfunction(str){
		cho = ["ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ","ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"];
		  result = "";
		  for(i=0;i<str.length;i++) {
		    code = str.charCodeAt(i)-44032;
		    if(code>-1 && code<11172) result += cho[Math.floor(code/588)];
		  }
		  return result;
		}
	
	function submitFunction() {				
		var chatName = $('#chatName').val();
		chatName = checkfunction(chatName);
		var chatContent = $('#chatContent').val();
		chatContent = checkfunction(chatContent);
		$.ajax({
			type : "POST",
			url : "/areyouthere/submit",
			data : {
				chatName : chatName,
				chatContent : chatContent
			},
			success : function(data) { //콜백함수. // 결과를 json으로 받아서새로 붙이기
				console.log('submit결과');
				console.log(data);
				if (data == 1) {
					autoClosingAlert('#successMessage', 2000);
					
						// submit 결과 비동기로 보여주기
						console.log("refresh 들어옴");
						$.ajax({
							type : "POST",
							url : "/areyouthere/refresh",
							success : function(data) {
								console.log('refresh ajax 들어옴');
								console.log(data);
								 var parsed = JSON.parse(data);
								 var result = parsed.result;
									//맨 아래에 하나의 list만 붙이면 됨
									//하나만 붙이기						
								 console.log(result[0][0].value, result[0][1].value, result[0][2].value);
	  							 refreshChat(result[0][0].value, result[0][1].value, result[0][2].value); 
	  							$('#chatList').scrollTop($('#chatList')[0].scrollHeight);				
							}
						});
					
				} else if (data == 0) {
					autoClosingAlert('#dangerMessage', 2000);
				} else {
					autoClosingAlert('#warningMessage', 2000);
				}
			}
		});
		$('#chatContent').val('');
	}
	function autoClosingAlert(selector, delay) {
		var alert = $(selector).alert();
		alert.show();
		window.setTimeout(function() {
			alert.hide()
		}, delay);
	
	}
	function chatListHome(){
		
		
		console.log('chatListHome'); 
		var data = <%= request.getAttribute("result") %> //JSP 스크립트 태그
			
		
		var result = data.result;
		for(var i = 0; i < result.length; i++){
			console.log(result[i][0].value, result[i][1].value, result[i][2].value);
			addChat(result[i][0].value, result[i][1].value, result[i][2].value);
			
		}
		lastID = Number(data.last);
			 
	}
	
	function chatListFunction(){
		$.ajax({
			  type: "POST",
			  url: '/areyouthere/update',
			  data: {lastId : lastID},
			  contentType: 'application/x-www-form-urlencoded; charset=utf-8',
			  success: function(data) {
				  console.log('success ajax'); 	
				  console.log(result);
				  
				  var parsed = JSON.parse(data);
				  var result = parsed.result;
				  for(var i = 0; i < result.length; i++){
					  console.log(result[i][0].value, result[i][1].value, result[i][2].value);
						addChat(result[i][0].value, result[i][1].value, result[i][2].value);
					}
				  console.log('현재 last의 값');
				  console.log(lastID);
				  lastID =  Number(parsed.last); //변수를 못 담는건가?
				  console.log('새롭게 담은 last의 값');
				  console.log(lastID);	 
				}
			});

		
		
	}

		
		
		
		


	

	function addChat(chatName, chatContent, chatTime){
		console.log('addChat');
		$('#chatList').prepend('<div class="row"' +
		 					'<div class="col-lg-12">' +
		 					'<div class="media">' + 
		 					'<a class="pull-left" href="#">' +
		 					//'<img class="media-object" src="images/w.png" alt="">'+
		 					'</a>' + 
		 					'<div class="media-body">'+
		 					'<h4 class="media-heading">'+
		 					chatName +
		 					'<span class="small pull-right">' +
		 					chatTime + 
		 					'</span>' +
		 					'</h4>' +
		 					'<p>' +
		 					chatContent +
		 					'</p>' +
		 					'</div>' +
		 					'</div>' +
		 					'</div>' +
		 					'</div>' +
		 					'<hr>');
					$('#chatList').scrollTop($('#chatList')[0].clientHeight+7);
	}
	// CompletedSubmit
	function refreshChat(chatName, chatContent, chatTime){
		console.log('freshChat');
		$('#chatList').append('<div class="row"' +
		 					'<div class="col-lg-12">' +
		 					'<div class="media">' + 
		 					'<a class="pull-left" href="#">' +
		 					//'<img class="media-object" src="images/w.png" alt="">'+
		 					'</a>' + 
		 					'<div class="media-body">'+
		 					'<h4 class="media-heading">'+
		 					chatName +
		 					'<span class="small pull-right">' +
		 					chatTime + 
		 					'</span>' +
		 					'</h4>' +
		 					'<p>' +
		 					chatContent +
		 					'</p>' +
		 					'</div>' +
		 					'</div>' +
		 					'</div>' +
		 					'</div>' +
		 					'<hr>');
					
	}
	//페이지가 로딩이 완료됐을 경우 실행하라고 알려줌
	
	$(document).ready(function(){
        $('#chatList').scroll(function(){
	        var scrollT = $(this).scrollTop(); //스크롤바의 상단위치
	      	
	        if(scrollT < 2) { // 스크롤바가 아래 쪽에 위치할 때
	        	console.log('스크롤lastID값');
	        	console.log(lastID);
	        	
	        	chatListFunction();
	        }
	    });
	});
	
	//페이지 시작
	$(document).ready(function(){
		console.log('start'); //여기까지 들어옴
		chatListHome();
		
		$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
		console.log('scroll');
	});	
	
</script>

</head>
<body>
<p></p>
	<div class="container">
		<div class="container_bootstrap_snippet">
			<div class="row">
				<div class="col-xs-12">
					<div class="portlet portlet-default">
						<div class="portlet-heading">
							<div class="portlet-title">
								<h4>
								
									<i class="="></i>Are you there?
								</h4>
							</div>
							<div class="clearfix"></div>
						</div>
						<div id="chat" class="panel-collapse collapse in">
							
							
							<div id="chatList" class="portlet-body chat-widget"
								style="overflow-y: scroll; width: auto; height: 430px;">
							</div>		
							
										
						<div class="portlet-footer">
							<div class="row">
								<div class="form-group col-xs-4">
									<input style="height: 40px;" type="text" id="chatName" onkeyup="choHangul(this.value)"
										class="form-control" placeholder="이름을 입력하세요." maxlength="8">
								</div>
							</div>
							<div class="row" style="height: 90px">
								<div class="form-group col-xs-10">
									<textarea style="height: 80px;" id="chatContent" onkeyup="choHangul(this.value)"
										class="form-control" placeholder="초성을 남겨주세요." maxlength="100"></textarea>
								</div>
								<div class="form-group col-xs-2">
									<button type="button" class="btn btn-default pull-right"
										onclick="submitFunction();">남기기</button>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	<div class="alert alert-success" id="successMessage"
		style="display: none;">
		<strong>초성을 남겼습니다.</strong>
	</div>
	<div class="alert alert-danger" id="dangerMessage"
		style="display: none;">
		<strong>초성을 남기지 못했습니다.</strong>
	</div>
	<div class="alert alert-warning" id="warningMessage"
		style="display: none;">
		<strong>오류가 발생했습니다.</strong>
	</div>
	</div>
</body>
</html>