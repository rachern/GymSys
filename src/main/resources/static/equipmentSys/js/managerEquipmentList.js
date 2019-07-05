var vm = new Vue({
	el:"#result",
	data:{
		equipmentList:[]
	},
	methods:{
		buy:function(){
			window.location.href = "managerBuy.html";
		},
		update:function(){
			window.location.href = "managerUpdate.html";
		},
		damage:function(){
			window.location.href = "managerDamage.html";
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