layui.use('form', function () {
    var form = layui.form;
});
$("#sumbit").click(function () {
    var role_name = document.getElementById("role_name").value;
    /* Act on the event */
    if (role_name == "") {
        alert("输入不允许为空")
        return false
    };
    var a = {
		'roleName': role_name,
	  };
	var b = JSON.stringify(a)
    $.ajax({
        url: 'http://localhost:8080/authRole',
        type: 'POST',
        dataType: 'json',
        headers: { 'Content-Type': 'application/json;charset=utf-8' },
        data: b
    })
        .done(function () {
            console.log("success");
            alert("添加成功");
            window.location.href = "role-management.html"
        })
        .fail(function () {
            console.log("error");
            alert("添加失败");
        })
        .always(function () {
            console.log("complete");
        });
})