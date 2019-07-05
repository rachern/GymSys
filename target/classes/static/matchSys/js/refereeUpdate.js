var vm = new Vue({
	el:"#result",
	data:{
		refereeId:0,
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
					type: "PUT",
					url: ("http://127.0.0.1:8080/refereeInfo?refereeId="+this.refereeId+"&name="+this.name+"&message="+message),
					dataType: "json",
					contentType:"application/json;charset=UTF-8",
					success: function(data){
						console.log(data);
						alert("修改成功");
						window.location.href = "refereeList.html";
					}
				});
			}
		}
	},
	created:function(){
		var id = GetQueryString("id");
		$.ajax({
            type:"get",
            url: ("http://127.0.0.1:8080/refereeInfo/"+id),
            contentType:'application/json;charset=UTF-8',
            dataType:"json",
            async:true,
            success:function(data){
            	if(data.code == 200){
					vm.$data.refereeId = data.data.refereeId;
					vm.$data.name = data.data.name;
				}
            }
        });
        function GetQueryString(name){
		    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if(r!=null)
				return  unescape(r[2]); 
			return null;
		}
	}
})