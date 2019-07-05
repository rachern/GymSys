$(document).ready(function () {
     
    function login(){
        let username = $('#account').val();
        let password= $('#password').val();
        console.log(username);
        console.log(password);
        // var url="http://47.107.137.51:8002/login";
		var url="/login";
		let requestbody = {
			"username": username,
			"password": password
		};
        $.ajax({
                      
            url:url,
            type:'post',
             dataType:"json",
            async:true,
            data:requestbody,
            success:function(data){
                console.log(data);                           
                $.cookie.json = true;               
                $.cookie('admin', `${data.userName}`);
                console.log($.cookie());
                console.log($.cookie('admin'));
                window.location.href = 'admin.html';
                // if(data.userId===""){
                //     alert("用户名或密码错误，请重试！");
                // }
            }
        }); 
    };

    //按回车登陆
    $(".form-signin").keydown(function (e) {
        if (e.keyCode == 13) {
            login();
        }
    });
    //点击登陆按钮
    $('.btn').click(function(){
        login();   
    })
})