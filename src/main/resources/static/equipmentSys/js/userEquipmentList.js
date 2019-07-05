var vm = new Vue({
	el:"#content",
	data:{
		equipmentList:[],
		rentEquipments:[],
		time:0,
		totalP:0,
	},
	methods:{
		rent:function(index){
			$("#cart").show();
			this.rentEquipments.push(this.equipmentList[index]);
			console.log(this.rentEquipments);
		},
		minusNum:function(index){
			if(this.equipmentList[index].rentNumber > 0){
				this.equipmentList[index].rentNumber--;
			}
		},
		addNum:function(index){
			if(this.equipmentList[index].rentNumber < this.equipmentList[index].usedNumber){
				this.equipmentList[index].rentNumber++;
			}
		},
		cancelThis:function(index){
			this.rentEquipments.splice(index,1);
			if (this.rentEquipments.length == 0){
				$("#cart").hide();
			}
		},
		cancel:function(){
			this.rentEquipments = [];
			this.time = 0;
		},
		confirm:function(){
			if (confirm("您确定租借器材吗？") == true){
				var eqList = [];
				var now = new Date(new Date().getTime()+8*3600000);
				var end = new Date(now.getTime()+this.time*3600000);
				var list = {
					beginTime:now,
					createTime:now,
					endTime:end,
					price:this.totalP,
					eqOrderDetailList:[]
				};
				for(var i=0;i<this.rentEquipments.length;i++){
					var equipment = {
						eqId:this.rentEquipments[i].id,
						eqName:this.rentEquipments[i].name,
						eqNumber:this.rentEquipments[i].rentNumber,
						eqOrderPrice:(this.rentEquipments[i].price*this.rentEquipments[i].rentNumber)
					};
					eqList.push(equipment);
				}
				list.eqOrderDetailList = eqList;
				var eqData = JSON.stringify(list);
				$.ajax({
					type: "POST",
					url: "http://127.0.0.1:8080/equipment/rent",
					dataType: "json",
					contentType:"application/json;charset=UTF-8",
					data: eqData,
					success: function(data){
						console.log(data);
						window.location.href = "userOrder.html";
					}
				});
			}
		}
	},
	computed:{
		totalPrice:function(){
			var total=0;
			for(var i=0; i<this.rentEquipments.length; i++){
				total += this.rentEquipments[i].price * this.rentEquipments[i].rentNumber;
			};
			total *= this.time;
			this.totalP = total;
			return total;
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
							rentNumber:0
						}
						list.push(equipment);
					};
					vm.$data.equipmentList = list;
					$("#cart").hide();
				}
            }
        });
	}
})