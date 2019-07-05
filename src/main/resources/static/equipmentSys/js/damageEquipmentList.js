var vm = new Vue({
	el:"#content",
	data:{
		equipmentList:[],
		scrapEquipmentList:[],
		repairsEquipmentList:[],
		price:0
	},
	methods:{
		scrap:function(index){
			$(".scrap").show();
			this.scrapEquipmentList.push(this.equipmentList[index]);
		},
		repairs:function(index){
			$(".repairs").show();
			this.repairsEquipmentList.push(this.equipmentList[index]);
		},
		changeScrapNum:function(val,index){
			this.scrapEquipmentList[index].number = val.target.value;
		},
		changeRepairsNum:function(val,index){
			this.repairsEquipmentList[index].number = val.target.value;
		},
		confirmScrap:function(){
			if(confirm("您确定要报废这些器材吗？") == true){
				var eqList = [];
				var list = {
					createTime:new Date(new Date().getTime()+8*3600000),
					eqOrderDetailList:[]
				};
				for(var i=0;i<this.scrapEquipmentList.length;i++){
					if(this.scrapEquipmentList[i].number != 0){
						var equipment = {
							eqId:this.scrapEquipmentList[i].id,
							eqName:this.scrapEquipmentList[i].name,
							eqNumber:this.scrapEquipmentList[i].number
						};
						eqList.push(equipment);
					}
				}
				list.eqOrderDetailList = eqList;
				var eqData = JSON.stringify(list);
				$.ajax({
					type: "POST",
					url: "http://127.0.0.1:8080/equipment/delete",
					dataType: "json",
					contentType:"application/json;charset=UTF-8",
					data: eqData,
					success: function(data){
						console.log(data);
						location.reload();
					}
				});
			}
		},
		confirmRepairs:function(){
			if (confirm("您确定要维修这些器材吗？") == true){
				var eqList = [];
				var list = {
					createTime:new Date(new Date().getTime()+8*3600000),
					price:this.price,
					eqOrderDetailList:[]
				};
				for(var i=0;i<this.repairsEquipmentList.length;i++){
					if(this.repairsEquipmentList[i].number != 0){
						var equipment = {
							eqId:this.repairsEquipmentList[i].id,
							eqName:this.repairsEquipmentList[i].name,
							eqNumber:this.repairsEquipmentList[i].number
						};
						eqList.push(equipment);
					}
				}
				list.eqOrderDetailList = eqList;
				var eqData = JSON.stringify(list);
				$.ajax({
					type: "POST",
					url: "http://127.0.0.1:8080/equipment/maintenance",
					dataType: "json",
					contentType:"application/json;charset=UTF-8",
					data: eqData,
					success: function(data){
						console.log(data);
						location.reload();
					}
				});
			}
		}
	},
	created:function(){
		$.ajax({
            type:"get",
            url: "http://127.0.0.1:8080/equipment/list",
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
							number:data.data[i].number
						}
						list.push(equipment);
					};
					vm.$data.equipmentList = list;
				}
            }
        });
	}
})