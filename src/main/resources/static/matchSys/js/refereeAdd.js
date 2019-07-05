var vm = new Vue({
	el:"#result",
	data:{
		name:"",
		sex:"",
		age:"",
		height:"",
		weight:"",
		phone:"",
		email:"",
		other:""
	},
	methods:{
		confirm:function(){
			if (confirm("您确定提交信息吗？") == true){
				var message = "性别："+this.sex+"，年龄："+this.age+"，身高："+this.height
						+"，体重："+this.weight+"，电话："+this.phone+"，邮箱："+this.email+"，其他："+this.other;
				$.ajax({
					type: "POST",
					url: ("http://127.0.0.1:8080/refereeInfo?name="+this.name+"&message="+message),
					dataType: "json",
					contentType:"application/json;charset=UTF-8",
					success: function(data){
						console.log(data);
						alert("添加成功");
						window.location.href = "refereeList.html";
					}
				});
			}
		}
	}
})