var vm = new Vue({
	el:"#register",
	data:{
		email:"",
		username:"",
		password:"",
		role:0
	},
	methods:{
		confirm:function(){
			if (confirm("您确定提交信息吗？") == true){
				var role = $("input[type='radio']:checked").val();
				var data = {
					userEmail:this.email,
					userName:this.username,
					userPassword:this.password
				}
				var data1 = JSON.stringify(data);
				$.ajax({
					type: "POST",
					url: ("http://127.0.0.1:8080/authUser/admin/"+this.role),
					dataType: "json",
					contentType:"application/json;charset=UTF-8",
					data:data1,
					success: function(data){
						console.log(data);
						alert("注册成功");
						window.location.href = "/login.html";
					}
				});
			}
		}
	}
})
