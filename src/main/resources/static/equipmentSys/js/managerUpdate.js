var vm = new Vue({
	el:"#update",
	data:{
		equipmentList:[]
	},
	methods:{
		changeName:function(val,index){
			this.equipmentList[index].name = val.target.value;
		},
		changeTotalNumber:function(val,index){
			this.equipmentList[index].totalNumber = val.target.value;
		},
		changeUsedNumber:function(val,index){
			this.equipmentList[index].usedNumber = val.target.value;
		},
		changePrice:function(val,index){
			this.equipmentList[index].price = val.target.value;
		},
		confirm:function(){
			if (confirm("您确定更新器材信息吗？") == true){
				var eqList = [];
				var list = {
					equipmentList:[]
				};
				for(var i=0;i<this.equipmentList.length;i++){
					var equipment = {
						eqId:this.equipmentList[i].id,
						eqName:this.equipmentList[i].name,
						eqTotalNumber:this.equipmentList[i].totalNumber,
						eqUsedNumber:this.equipmentList[i].usedNumber,
						eqPrice:this.equipmentList[i].price
					};
					eqList.push(equipment);
				}
				list.equipmentList = eqList;
				var eqData = JSON.stringify(list);
				$.ajax({
					type: "POST",
					url: "http://127.0.0.1:8080/equipment/update",
					dataType: "json",
					contentType:"application/json;charset=UTF-8",
					data: eqData,
					success: function(data){
						console.log(data);
						window.location.href = "managerEquipmentList.html";
					}
				});
			}
		}
	},
	created:function(){
		$.ajax({
            type:"get",
            url: "http://127.0.0.1:8080/equipment/info/list",
            contentType:'application/json;charset=UTF-8',
            dataType:"json",
            async:true,
            success:function(data){
            	if(data.code == 200){
					list = [];
					for(i = 0 ; i < data.data.length ; i++){
						var equipment = {
							id:data.data[i].eqId,
							name:data.data[i].eqName,
							totalNumber:data.data[i].eqTotalNumber,
							usedNumber:data.data[i].eqUsedNumber,
							price:data.data[i].eqPrice,
						}
						list.push(equipment);
					};
					vm.$data.equipmentList = list;
				}
            }
        });
	}
})