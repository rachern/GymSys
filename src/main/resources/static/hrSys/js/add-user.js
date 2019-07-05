layui.use('form', function () {
	var form = layui.form;
});
$("#sumbit").click(function () {
	var user_name = document.getElementById("user_name").value;
	var user_password = document.getElementById("user_password").value;
	var user_email = document.getElementById("user_email").value;
	console.log(1)
	/* Act on the event */
	if (user_name == "" || user_password == "" || user_email == "") {
		alert("输入不允许为空")
		return false
	};
	console.log(1111)
	var a = {
		'userName': user_name,
		'userPassword': user_password,
		'userEmail': user_email,
	  };
	var b = JSON.stringify(a)
	$.ajax({
		url: 'http://localhost:8080/authUser/admin',
		type: 'POST',
		dataType: 'json',
		headers: { 'Content-Type': 'application/json;charset=utf-8' },
		data: b,
	})
		.done(function () {
			console.log("success");
			alert("添加成功");
			window.location.href = "user-management.html"
		})
		.fail(function () {
			console.log("error");
		})
		.always(function () {
			console.log("complete");
		});
})