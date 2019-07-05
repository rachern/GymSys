var vm = new Vue({
	el:"#content",
	data:{
		orderList:[],
	},
	methods:{
		detail:function(index){
			window.location.href = "userOrderDetail.html?id="+this.orderList[index].id;
		}
	},
	created:function(){
		$.ajax({
            type:"get",
            url: "http://127.0.0.1:8080/equipment/order/mine",
            contentType:'application/json;charset=UTF-8',
            dataType:"json",
            async:true,
            success:function(data){
            	if(data.code == 200){
					list = [];
					for(i = 0 ; i < data.data.length ; i++){
						var order = {
							id:data.data[i].orderId,
							price:data.data[i].price,
							beginTime:data.data[i].beginTime,
							endTime:data.data[i].endTime,
							orderStatus:data.data[i].orderStatus,
						}
						list.push(order);
					};
					vm.$data.orderList = list;
					if (list.length == 0){
						$(".order").hide();
					}
				}
            }
        });
	}
})
