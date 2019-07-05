layui.use('form', function () {
    var form = layui.form;
});
$("#sumbit").click(function () {
    var user_name = document.getElementById("user_name").value;
    var user_password = document.getElementById("user_password").value;
    /* Act on the event */
    if (user_name == "" || user_password == "") {
        alert("输入不允许为空")
        return false
    };
    $.ajax({
        url: 'http://localhost:8080/login',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/x-www-form-urlencoded',
        data: {
            'username': user_name,
            'password': user_password,
        },
    })
        .done(function (data) {
            console.log("success");
            alert("登录成功");
            sessionStorage.setItem('name', data[0].name)
            sessionStorage.setItem("id",data[0].id)
            if ((data[0].children[0].name) == "器材管理员") {
                window.location.href = "equipment-demo.html";
            } if ((data[0].children[0].name) == "用户管理员") {
                window.location.href = "user_demo.html";
            } else{
                window.location.href = "user.html";
            }
        })
        .fail(function () {
            console.log("error");
            alert("登录失败");
        })
        .always(function () {
            console.log("complete");
        });
})