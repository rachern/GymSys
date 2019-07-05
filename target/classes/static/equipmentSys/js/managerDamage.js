var vm = new Vue({
	el:"#damage",
	data:{
		equipmentList:[]
	},
	methods:{
		changeNum:function(val,index){
			this.equipmentList[index].number = val.target.value;
		},
		confirm:function(){
			if (confirm("您确定这些器材是损坏的吗?") == true){
				var eqList = [];
				var list = {
					eqOrderDetailList:[]
				};
				for(var i=0;i<this.equipmentList.length;i++){
					if(this.equipmentList[i].number != 0){
						var equipment = {
							eqId:this.equipmentList[i].id,
							eqName:this.equipmentList[i].name,
							eqNumber:this.equipmentList[i].number
						};
						eqList.push(equipment);
					}
				}
				list.eqOrderDetailList = eqList;
				console.log(list);
				var eqData = JSON.stringify(list);
				$.ajax({
					type: "POST",
					url: "http://127.0.0.1:8080/equipment/damage",
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
							usedNumber:data.data[i].eqUsedNumber,
							number:0
						}
						list.push(equipment);
					};
					vm.$data.equipmentList = list;
				}
            }
        });
	}
})