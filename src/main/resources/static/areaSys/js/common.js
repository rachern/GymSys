function common() {
	login();
	sign();
	autoLogin();
	loginOut();
	
	jumpOrder();
	jumpMyself();
}

// var headUrl = 'http://47.107.137.51:8002/';

var headUrl = "/";

function login() {
	$('.login-btn').click(function () {
		$('#loginModal').modal("toggle");
		$('.login-username').val('');
		$('.login-password').val('');
		$('.tips').css("visibility", "hidden");
	});
	// 提示填写用户名
	$('.login-username').focus(function () {
		$('.login-username').blur(function () {
			if ($('.login-username').val() === '') {
				$('.login-name-message').css("visibility", "visible");
			} else {
				$('.login-name-message').css("visibility", "hidden");
			}
		});
	});
	// 提示填写密码
	$('.login-password').focus(function () {
		$('.login-password').blur(function () {
			if ($('.login-password').val() === '') {
				$('.login-password-message').css("visibility", "visible");
			} else {
				$('.login-password-message').css("visibility", "hidden");
			}
		});
	});

	// 点击登录
	$('.login-submit').click(function () {
		let username = $('.login-username').val();
		let password = $('.login-password').val();
		if (username === '' || password === '') {
			return;
		}
		let requestbody = {
			"username": username,
			"password": password
		};
		
		// let url ="http://47.107.137.51:8002/login";
		var url ="/login";
		
		
		$.ajax({
			type:"post",
			url:url,
			contentType:'application/x-www-form-urlencoded',
            dataType:"json",
            async:true,
			data:requestbody,
			success:loginsuccess
			
		});
	});

	function loginsuccess(data) {
		
		console.log(data);
		console.log(data.userEmail+"//"+data.username);
	

		// 设置cookie  TODO:设置cookie的过期时间
		$.cookie.json = true;
		$.cookie('username',data.name); //存储用户名
		$.cookie('userEmail',data.userEmail);
		
		$.cookie('userid', data.userId);  //存储用户id
		
		localStorage.setItem('username',data.name);
		localStorage.setItem('userid',data.userId);
		localStorage.setItem('userEmail',data.userEmail);

		$('#loginModal').modal("hide"); /*隐藏登录表单*/
		$('.loginsign-btn-box').css("display", "none"); /*隐藏登录注册按钮*/
		$('.hello-user-box').css("display", "block"); //显示用户问候语
		
		$('.hello-user-box .username').text(data.name); //显示用户名
	}
}

function sign() {
	$('.sign-btn').click(function () {
		$('#signinModal').modal("toggle");
		$('.signin-username').val('');
		$('.signin-password').val('');
		$('.resignin-password').val('');
		$('.tips').css("visibility", "hidden");
	});
	// 提示填写用户名
	$('.signin-username').focus(function () {
		$('.signin-username').blur(function () {
			if ($('.signin-username').val() === '') {
				$('.sign-name-message').css("visibility", "visible");
			} else {
				$('.sign-name-message').css("visibility", "hidden");
			}
		});
	});
	// 提示用户填写密码
	$('.signin-password').focus(function () {
		$('.signin-password').blur(function () {
			if ($('.signin-password').val() === '') {
				$('.sign-password-message').css("visibility", "visible");
			} else {
				$('.sign-password-message').css("visibility", "hidden");
			}
		});
	});
	// 提示用户二次填写密码
	$('.resignin-password').focus(function () {
		$('.resignin-password').blur(function () {
			if ($('.resignin-password').val() === '') {
				$('.sign-secpassword-message').text('请再次输入以确认密码').css("visibility", "visible");
			} else if ($('.resignin-password').val() !== $('.signin-password').val()) {
				$('.sign-secpassword-message').text('两次输入密码不一致，请重新输入01').css("visibility", "visible");
			} else {
				$('.sign-secpassword-message').css("visibility", "hidden");
			}
		});
	});
	// 提示用户输入正确的邮箱
	$('input[name="userEmail"]').focus(function () {
		$('input[name="userEmail"]').blur(function () {
			let email = $('input[name="userEmail"]').val();
			if (email === '') {
				$('.emailtips').css("visibility", "visible").text("请输入邮箱");
				return;
			}
			let mailPattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
			if (!mailPattern.test(email)) {
				$('.emailtips').css("visibility", "visible").text("请输入正确的邮箱");
			}
			else {
				$('.emailtips').css("visibility", "hidden");
			}
		});
	});

	$('.signin-submit').click(function () {
		let username = $('.signin-username').val();
		let password = $('.signin-password').val();
		let confirmpass = $('.resignin-password').val();
		if (username === '' || password === '' || confirmpass === '') return; //信息不完整,拒绝提交
		if (password !== confirmpass) { //两次输入的密码不匹配,重新输入
			$('.sign-secpassword-message').text('两次输入密码不一致，请重新输入').css("visibility", "visible");
			return;
		}
		// 表单校验
		// 校验邮箱
		let email = $('input[name="userEmail"]').val();
		let mailPattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;

		if (!mailPattern.test(email)) {
			$('.emailtips').css("visibility", "visible").text("请输入正确的邮箱");
			return;
		}

		// 获取表单数据
		let data = $('.signup-form').serialize();
		
		let url = headUrl + `authUser?${data}`;
		
		$.ajax({
			url: url,
			type: 'POST',
			processData: false,
			cache: false,
			success: signSuccess
		});

		function signSuccess(data) {
			
			alert("注册成功，赶紧去登录吧！");
			$("#signinModal").modal('hide');
			$('#loginModal').modal("toggle");
			return;

			// console.log(data);
			// // 显示用户hello语,关闭注册框,隐藏登录注册按钮
			$("#signinModal").modal('hide');
			$('.loginsign-btn-box').css("display", "none");
			$('.hello-user-box').css("display", "block");
			// // 显示用户名
			$('.hello-user-box .username').text(data.data.userName);
			$.cookie('username', `${data.data.userName}`);
			$.cookie('userEmail',`${data.data.userEmail}`);
			$.cookie('userid', `${data.data.userId}`);  //存储用户id
			
			
		}

	});
}

function autoLogin() {
	if ($.cookie('username')) {
		$('.loginsign-btn-box').css("display", "none"); /*隐藏登录注册按钮*/
		$('.hello-user-box').css("display", "block"); //显示用户问候语
		$('.hello-user-box .username').text($.cookie('username')); //显示用户名
	}
}

// 退出登录
function loginOut() {

	$('.login-out-btn').click(function () {
		$.cookie('username', '', {
			expires: -1
		});
		$.cookie('userid', '', {
			expires: -1
		});
		$.cookie('userEmail', '', {
			expires: -1
		});
		window.location.href = './index.html';
	});
}

// 跳转订单管理页
function jumpOrder() {
	$('.jump-order').click(function () {
		if (!$.cookie('username')) {
			$('#loginModal').modal("toggle");
			$('.login-error').text("请先登录！");
			$('.login-username').val('');
			$('.login-password').val('');
			$('.tips').css("visibility", "hidden");
			$('.login-error').css("visibility", "visible");
			return;
		}
		window.location.href = './orders.html';
	});
}

// 跳转个人中心页
function jumpMyself() {
	$('.jump-myself').click(function () {
		if (!$.cookie('username')) {
			$('#loginModal').modal("toggle");
			$('.login-error').text("请先登录！");
			$('.login-username').val('');
			$('.login-password').val('');
			$('.tips').css("visibility", "hidden");
			$('.login-error').css("visibility", "visible");
			return;
		}
		window.location.href = './myself.html';
	});

}