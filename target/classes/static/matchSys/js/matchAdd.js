var vm = new Vue({
	el:"#result",
	data:{
		startTime:"",
		endTime:"",
		refereeList:[],
		areaList:[],
		refereeId:0,
		areaId:0,
		message:"",
		title:"",
		userId:0,
		bookId:0
	},
	methods:{
		confirm:function(){
			if (confirm("您确定提交信息吗？") == true){
				var data = {
					areaId:this.areaId,
					bookStartTime:this.startTime,
					userId:this.userId,
					bookEndTime:this.endTime
				}
				var data1 = {
					refereeId:this.refereeId,
					areaBookId:0,
					title:this.title,
					message:this.message
				}
				$.ajax({
					type: "POST",
					url: "http://127.0.0.1:8080/areaBook",
					dataType: "json",
					contentType:"application/x-www-form-urlencoded;charset=UTF-8",
					data:data,
					success: function(data){
						console.log(data.data.bookId);
						vm.$data.bookId = data.data.bookId;
							data1.areaBookId = data.data.bookId;
						$.ajax({
							type: "POST",
							url: "http://127.0.0.1:8080/matchInfo",
							dataType: "json",
							contentType:"application/x-www-form-urlencoded;charset=UTF-8",
							data:data1,
							success: function(data){
								console.log(data);
								alert("添加成功");
								window.location.href = "matchList.html";
							}
						});
					}
				});

			}
		}
	},
	created:function(){
		var userId=localStorage.getItem('userid');
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
							name:data.data[i].name
						}
						list.push(referee);
					};
					vm.$data.refereeList = list;
					vm.$data.userId = userId;
				}
            }
        });
        $.ajax({
            type:"get",
            url: "http://127.0.0.1:8080/areaInfo/list",
            contentType:'application/json;charset=UTF-8',
            dataType:"json",
            async:true,
            success:function(data){
            	if(data.code == 200){
					list = [];
					for(i = 0 ; i < data.data.length ; i++){
						var place = {
							areaId:data.data[i].areaId,
							name:data.data[i].name
						}
						list.push(place);
					};
					vm.$data.areaList = list;
				}
            }
        });
	}
})