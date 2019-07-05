var vm = new Vue({
	el:"#buy",
	data:{
		existEquipments:[],
		addEquipments:[],
		totalPrice:0
	},
	methods:{
		change:function(val,index){
			$(val.target).parent().next().val(val.target.value);
			for(var i=0;i<this.existEquipments.length;i++){
				if(this.existEquipments[i].name == val.target.value){
					this.addEquipments[index].eqId = this.existEquipments[i].id;
					this.addEquipments[index].eqName = this.existEquipments[i].name;
				}else{
					this.addEquipments[index].eqName = val.target.value;
				}
			};
		},
		changeName:function(val,index){
			for(var i=0;i<this.existEquipments.length;i++){
				if(this.existEquipments[i].name == val.target.value){
					this.addEquipments[index].eqId = this.existEquipments[i].id;
					this.addEquipments[index].eqName = this.existEquipments[i].name;
				}else{
					this.addEquipments[index].eqName = val.target.value;
				}
			};
		},
		changeNum:function(val,index){
			this.addEquipments[index].eqNumber = val.target.value;
			this.addEquipments[index].eqOrderPrice = this.addEquipments[index].eqNumber * this.addEquipments[index].eqPrice;
		},
		changePrice:function(val,index){
			this.addEquipments[index].eqPrice = val.target.value;
			this.addEquipments[index].eqOrderPrice = this.addEquipments[index].eqNumber * this.addEquipments[index].eqPrice;
		},
		add:function(){
			var equipment = {
				eqId:null,
				eqName:"",
				eqNumber:0,
				eqPrice:0,
				eqOrderPrice:0
			};
			this.addEquipments.push(equipment);
		},
		confirm:function(){
			if (confirm("您确定购买这些器材吗？") == true) {
				var eqList = [];
				var now = new Date(new Date().getTime()+8*3600000);
				var list = {
					beginTime:now,
					createTime:now,
					price:this.totalPrice,
					eqOrderDetailList:[]
				};
				for(var i=0;i<this.addEquipments.length;i++){
					var equipment = {
						eqId:this.addEquipments[i].eqId,
						eqName:this.addEquipments[i].eqName,
						eqNumber:this.addEquipments[i].eqNumber,
						eqOrderPrice:this.addEquipments[i].eqOrderPrice
					};
					eqList.push(equipment);
				}
				list.eqOrderDetailList = eqList;
				var eqData = JSON.stringify(list);
				$.ajax({
					type: "POST",
					url: "http://127.0.0.1:8080/equipment/purchase",
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
	computed:{
		totalP:function(){
			var total = 0;
			for(var i=0; i<this.addEquipments.length;i++){
				total += this.addEquipments[i].eqOrderPrice;
			};
			this.totalPrice = total;
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
						var existEquipment = {
							id:data.data[i].eqId,
							name:data.data[i].eqName
						}
						list.push(existEquipment);
					};
					console.log(list);
					vm.$data.existEquipments = list;
					list2 = [];
					var equipment = {
						eqId:null,
						eqName:"",
						eqNumber:0,
						eqPrice:0,
						eqOrderPrice:0
					};
					list2.push(equipment);
					vm.$data.addEquipments = list2;
				}
            }
        });
	}
})