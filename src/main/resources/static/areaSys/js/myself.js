$(document).ready(function () {
	
    init();
    detailShow();
    datachange();

    common();
	
});


function init() {
	
        $('.hello-user-box').css("display", "block");
        $('.hello-user-box').find('.username').text($.cookie('username'));
        $('.user-banner .user-name').text($.cookie('username'));
		console.log($.cookie('username'));
		
		userInfo();
      
	  
}

/* 展示用户信息 */
function userInfo(){
	 let basicDetail = `<form role="form" class="form-horizontal" action="">
	            <div class="form-group userheader-form-box">
	                <div class="image-box  col-sm-2 col-sm-offset-2">
	                    <img src=" " alt="头像">
	                </div>
	                <label for="userHeaderPic" class="col-sm-2">修改头像:</label>
	                <div class="col-sm-2">
	                    <input type="file" name="fileupload" id="userHeaderPic" accept="image/gif,image/png,image/bmp,image/jpeg">
	                </div>
	                <div class="col-sm-2 upLoadhead-box col-sm-offset-2">
	                    <a href="javascript:;" class="upLoadhead">上传修改</a>
	                </div>
	            </div>
	            <div class="form-group">
	                <label for="userName" class="control-label col-sm-2">用户名</label>
	                <div class="col-sm-10">
	                    <input type="text" id="userName" class="form-control username-input" placeholder="用户名" value=${$.cookie('username')}>
	                </div>
	            </div>
	            <div class="form-group">
	                <label for="" class="col-sm-2 control-label">性别</label>
	                <div class="col-sm-1">
	                    <input type="radio" class="gender-radio gender-man-radio" name="gender" value="男">
	                </div>
	                <label for="" class="col-sm-1 text-left">男</label>
	                <div class="col-sm-1">
	                    <input type="radio" class="gender-radio gender-woman-radio"  name="gender" value="女">
	                </div>
	                <label for="" class="col-sm-1">女</label>
	            </div>
	            <div class="form-group">
	                <label for="" class="control-label col-sm-2">邮箱</label>
	                <div class="col-sm-10">
	                    <input type="text" class="form-control telphone-input" placeholder="输入邮箱" value=${$.cookie('userEmail')}>
	                </div>
	            </div>
	            <a class="save-userinfo-btn">保存修改</a>
	            
	        </form>`;
			// <div class="changeinfo-success-tips">修改成功</div>
	$('.show-borad').html('').append(basicDetail);
	// 设置性别
	$('.gender-man-radio').prop("checked", true);
	
	// 重新渲染保存修改按钮
	datachange();
}


function detailShow() {

    let passwordBorad = `<h4 class="text-center">修改密码</h4>
                    <form action="" role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="" class="control-label col-sm-2">原始密码：</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control origin-pass" placeholder="输入原始密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="" class="control-label col-sm-2">新密码：</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control newpass01"  placeholder="输入新密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="" class="control-label col-sm-2">确认新密码：</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control newpass02" placeholder="再次输入新密码"> 
                            </div>
                        </div>
                        <a href="javascript:;" class="save-user-password">确认修改</a>
                       
                    </form>`;
					//  <div class="changepass-tips">修改成功<div>
					
    // 个人资料面板
    $('.basic-detail-btn').click(function () {
        if ($('.basic-detail-btn').hasClass('active')) return;
        $('.change-password-btn').removeClass('active');
        $('.coupon-btn').removeClass('active');
        $('.basic-detail-btn').addClass('active');
		
		userInfo();

    });


    // 修改密码面板
    $('.change-password-btn').click(function () {
        if ($('.change-password-btn').hasClass('active')) return;
        $('.coupon-btn').removeClass('active');
        $('.basic-detail-btn').removeClass('active');
        $('.change-password-btn').addClass('active');
        $('.show-borad').html('').append(passwordBorad);

        datachange();
    });


}

function datachange() {

    // 点击保存按钮 个人资料面板修改
    $('.save-userinfo-btn').click(function () {
        let username = $('.username-input').val();
        let gender = $('.gender-radio:checked').val();
        let userEmail = $('.telphone-input').val();

        if (username === '' || userEmail === '') {
            return;
        }
        let url = headUrl + 'authUser';

        
        let userdata = { 
		"userId": $.cookie('userid'), 
		"userName": username, 
		"userEmail": userEmail, 
		};
		
		$.ajax({
			type:'PUT',
			url:url,
			dataType:'json',
			data:userdata,
			success:changeInfoSuccess
		});
        
		function changeInfoSuccess(data) {
            if(data.code == '200'){
				console.log("data:"+data);
				 $('.changeinfo-success-tips').css({ "display": "block", "opacity": 1, "top": "100%" }).animate({ "top": "40%", "opacity": "0" }, 1200, "linear");
				console.log(data);
                
				alert("修改成功");
				
            }

           
        }
    });

    // 点击修改密码按钮 修改密码
    $('.save-user-password').click(function () {
        let origin = $('.origin-pass').val();
        let newpass01 = $('.newpass01').val();
        let newpass02 = $('.newpass02').val();

        console.log(origin);
        console.log(newpass01);
        console.log(newpass02);
        // 是否填上内容
        if (origin === '' || newpass01 === '' || newpass02 === '') {
            return;
        }
        // 两次的新密码的输入不一样
        if (newpass01 !== newpass02) {
            return;
        }
		let url = headUrl + 'authUser';
       
        let data = { "userId":$.cookie('userid'),"userPassword": newpass01 };

        $.ajax({
        	type:'PUT',
        	url:url,
        	dataType:'json',
        	data:data,
        	success:changePasssuccess
        });
        function changePasssuccess(data) {
			if(data.code=="200"){
				  console.log(data);
				$('.changepass-tips').css({ "display": "block", "opacity": 1, "top": "100%" })
				.animate({ "top": "40%", "opacity": "0" }, 1200, "linear");
				alert("修改成功");
				
			}
          
        }

    });


}