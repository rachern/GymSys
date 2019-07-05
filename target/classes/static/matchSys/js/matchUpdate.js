var vm = new Vue({
	el:"#result",
	data:{
		matchId:0,
		refereeId:0,
		areaBookId:0,
		title:"",
		message:"",
		refereeList:[],
		areaList:[],
		refereeName:"",
		areaName:""
	},
	methods:{
		confirm:function(){
			if (confirm("您确定提交信息吗？") == true){
				$.ajax({
					type: "PUT",
					url: ("http://127.0.0.1:8080/matchInfo?matchId="+this.matchId+"&title="+this.title+"&message="+this.message),
					dataType: "json",
					contentType:"application/json;charset=UTF-8",
					success: function(data){
						console.log(data);
						alert("修改成功");
						window.location.href = "matchList.html";
					}
				});
			}
		}
	},
	created:function(){
		var id = GetQueryString("id");
		$.ajax({
            type:"get",
            url: ("http://127.0.0.1:8080/matchInfo/"+id),
            contentType:'application/json;charset=UTF-8',
            dataType:"json",
            async:true,
            success:function(data){
            	if(data.code == 200){
            		vm.$data.matchId = data.data.matchId;
					vm.$data.refereeId = data.data.refereeId;
					vm.$data.refereeName = data.data.refereeName;
					vm.$data.areaBookId = data.data.areaBookId;
					vm.$data.areaName = data.data.areaName;
					vm.$data.title = data.data.title;
					vm.$data.message = data.data.message;
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