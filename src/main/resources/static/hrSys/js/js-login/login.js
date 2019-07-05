// layui.use(['element','layer'], function(){
// $(document).ready(function() {
// 	//防止页面后退
//         history.pushState(null, null, "");
//         window.addEventListener('popstate', function () {
//             history.pushState(null, null, document.URL);
//         });
	
// });

//验证码函数封装yzm函数
/*function yzm(){
	$.ajax({
	url: 'jiashuju.json',
	type: 'POST',
	dataType: 'json',
})
.done(function(obj) {
	console.log("success");
	$.each(obj,function(index, el) {
		$("img").attr('url', el['url']);
	});
})
.fail(function() {
	console.log("error");
		 	alert("服务器出问题了，请重试");
})
.always(function() {
	console.log("complete");
});
};*/
//提交数据
$(function(){
	$("#form1").submit(function() {
		/* Act on the event */
	// 	var name = $("#login").val();
	// var password = $("#password").val();
	// if(name==''){
	// 	alert("账号为空");
	// 	return false;
	// }else if (password=='') {
	// 	alert("密码为空");
	// 	return false;
	// }else{

	// }
window.location.href = 'demo.html';
	// var postData = $("#form1").serialize();
	// console.log(postData);
   


	// $.ajax({
	// 	url: '/pickmeup/Login/doLogin',
	// 	//url: 'js/js-login/login.json',
	// 	type: 'POST',
	// 	dataType: 'json',
	// 	data:postData,
	// 	beforeSend:function(){
	// 		$("#submit").attr('disabled', true);
	// 		$("#submit").val("LOADING");},

	// })
	// .done(function(obj) {
	// 	console.log("success");
	// 	if (obj.code !== 200) {
	// 		$("#submit").attr('disabled', false);
	// 		$("#submit").val("LOGIN");
	// 		alert("账号密码错误");
	// 	}else  {
	// 		console.log("登陆成功");
	// 		layer.msg('登陆成功',{
	// 			icon:1,
	// 			time:1500
	// 		},function(){
	// 			const info = {
	// 			username: name
	// 		}
	// 		sessionStorage.setItem('name', JSON.stringify(info));
	// 		window.location.href = 'demo.html';
	// 		})
	// 		return false;

			
	// 	}
	// })
	// .fail(function() {
	// 	console.log("error");
	// 		 	alert("服务器出问题了，请重试");
	// })
	// .always(function() {
	// 	console.log("complete");
 //     	});
	// });
});
})
/*window.onload=function(){
      yzm();
	$("img").click(function(){
		yzm();
	});
	$("[for='yanzhengma']").click(function() {

		yzm();
	});}	*/

	// })