layui.use('form', function(){
  var form = layui.form;
   form.verify({
   	word:function(value,item){
   		if(value.length>20){
   			return '字数超过20个字'
   		}}
   	,content:function(value,item){
   		if(value.length<1){
   			return '内容不能为空'
   		}
   	}
   	
   })
  
});





//查看密码
	var demoInput = document.getElementById("password");
    //隐藏text block，显示password block
	function hideShowPsw(){
		if (demoInput.type == "password") {
			demoInput.type = "text";
		}else {
			demoInput.type = "password";
		}
	}
	var demo_img1 = document.getElementById("demo_img1");
	var demoInput1 = document.getElementById("repassword");
    //隐藏text block，显示password block
	function hideShowPsws(){
		if (demoInput1.type == "password") {
			demoInput1.type = "text";
			
		}else {
			demoInput1.type = "password";
			
		}
	}




//加载页面时获取星信息
 window.load=xx();





//获取信息
function xx(){
   $.ajax({
   	//url: 'jiashuju2.json',
   	url: '/pickmeup/notice/showNotice',
   	type: 'GET',
   	dataType: 'json',
   })
   .done(function(obj) {
   	console.log("success");
   	$("#repassword").attr('value', obj.data.title);
   	$("#word").attr('value', obj.data.heading);
   	$("#phone").val(obj.data.content);
    
   })
   .fail(function(e) {
   	console.log("error");
   	console.log(e);
   })
   .always(function() {
   	console.log("complete");
   });  
}

//input修改触发事件
var status = status1 =status2=1;







$("#form1").submit(function(event) {
	/* Act on the event */
	 var postData = $("#form1").serialize();
	 var a = $("#repassword").val();
	 var b = $("#phone").val();
	 var c = $("#word").val();

	 $.ajax({
	 	url: '/pickmeup/notice/addNotice',
//	 	url: 'jiashuju2.json',
	 	data:{'title':a,'content':b,'heading':c},
	 	type: 'GET',
	 	dataType: 'json',
	 })
	 .done(function() {
	 	console.log("success");
	 	console.log(1);
	 	layer.msg('提交成功',{
	 		icon:1
	 	})

	 })
	 .fail(function() {
	 	console.log("error");
	 	alert("服务器出问题了，请重试");
	 })
	 .always(function() {
	 	console.log("complete");
	 });
	 
});