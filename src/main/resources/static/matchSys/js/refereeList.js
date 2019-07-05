var vm = new Vue({
	el:"#result",
	data:{
		refereeList:[]
	},
	methods:{
		add:function(){
			window.location.href = "refereeAdd.html";
		},
		detail:function(index){
			window.location.href = "refereeDetail.html?id="+this.refereeList[index].refereeId;
		},
		update:function(index){
			window.location.href = "refereeUpdate.html?id="+this.refereeList[index].refereeId;
		},
		deleteR:function(index){
			if (confirm("您确定删除该裁判吗？") == true){
				$.ajax({
					type: "DELETE",
					url: ("http://127.0.0.1:8080/refereeInfo/"+this.refereeList[index].refereeId),
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
            url: "http://127.0.0.1:8080/refereeInfo/list",
            contentType:'application/json;charset=UTF-8',
            dataType:"json",
            async:true,
            success:function(data){
            	if(data.code == 200){
					list = [];
					for(i = 0 ; i < data.data.length ; i++){
						var referee = {
							refereeId:data.data[i].refereeId,
							name:data.data[i].name,
							message:data.data[i].message
						}
						list.push(referee);
					};
					vm.$data.refereeList = list;
				}
            }
        });
	}
})