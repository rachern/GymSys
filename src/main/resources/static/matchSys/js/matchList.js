var vm = new Vue({
	el:"#result",
	data:{
		matchList:[]
	},
	methods:{
		add:function(){
			window.location.href = "matchAdd.html";
		},
		detail:function(index){
			window.location.href = "matchDetail.html?id="+this.matchList[index].matchId;
		},
		update:function(index){
			window.location.href = "matchUpdate.html?id="+this.matchList[index].matchId;
		},
		deleteR:function(index){
			if (confirm("您确定删除该赛事吗？") == true){
				$.ajax({
					type: "DELETE",
					url: ("http://127.0.0.1:8080/matchInfo/"+this.matchList[index].matchId),
					dataType: "json",
					contentType:"application/json;charset=UTF-8",
					success: function(data){
						console.log(data);
						alert("删除成功");
						location.reload();
					}
				});
			}
		}
	},
	created:function(){
		$.ajax({
            type:"get",
            url: "http://127.0.0.1:8080/matchInfo/list",
            contentType:'application/json;charset=UTF-8',
            dataType:"json",
            async:true,
            success:function(data){
            	if(data.code == 200){
					list = [];
					for(i = 0 ; i < data.data.length ; i++){
						var match = {
							matchId:data.data[i].matchId,
							refereeId:data.data[i].refereeId,
							refereeName:data.data[i].refereeName,
							areaBookId:data.data[i].areaBookId,
							areaName:data.data[i].areaName,
							title:data.data[i].title,
							message:data.data[i].message
						}
						list.push(match);
					};
					vm.$data.matchList = list;
				}
            }
        });
	}
})