var vm = new Vue({
	el:"#content",
	data:{
		orderList:[],
		pages:0,
		nextPage:0,
		type:0
	},
	methods:{
		order:function(val){
			this.type = val;
			this.search();
		},
		detail:function(index){
			window.location.href = "managerOrderDetail.html?id="+this.orderList[index].id;
		},
		search:function(){
			$(".table").show();
			$(".paging").show();
			var path = "";
			var $username = $(".username").val();
			if($username != null){
				if(this.type != 0){
					path = "http://127.0.0.1:8080/equipment/order/admin/"+$username+"?type="+this.type+"&pageNum=1&pageSize=8";
				}else{
					path = "http://127.0.0.1:8080/equipment/order/admin/"+$username+"?pageNum=1&pageSize=8";
				}
			}else{
				if(this.type != 0){
					path = "http://127.0.0.1:8080/equipment/order/admin?type="+this.type+"&pageNum=1&pageSize=8";
				}else{
					path = "http://127.0.0.1:8080/equipment/order/admin?pageNum=1&pageSize=8";
				}
			};
			$.ajax({
	            type:"get",
	            url: path,
	            contentType:'application/json;charset=UTF-8',
	            dataType:"json",
	            async:true,
	            success:function(data){
	            	if(data.code == 200){
						list = [];
						for(i = 0 ; i < data.data.records.length ; i++){
							var order = {
								id:data.data.records[i].orderId,
								createTime:data.data.records[i].createTime,
								orderType:data.data.records[i].orderType,
								orderStatus:data.data.records[i].orderStatus
							}
							list.push(order);
						};
						vm.$data.orderList = list;
						vm.$data.pages = data.data.pages;
					}
	            }
	        });
		},
		turn:function(n){
			this.nextPage = n;
			$(".table").show();
			$(".paging").show();
			var path = "";
			var $username = $(".username").val();
			if($username != null){
				if(this.type != 0){
					path = "http://127.0.0.1:8080/equipment/order/admin/"+$username+"?type="+this.type+"&pageNum="+n+"&pageSize=8";
				}else{
					path = "http://127.0.0.1:8080/equipment/order/admin/"+$username+"?pageNum="+n+"&pageSize=8";
				}
			}else{
				if(this.type != 0){
					path = "http://127.0.0.1:8080/equipment/order/admin?type="+this.type+"&pageNum="+n+"&pageSize=8";
				}else{
					path = "http://127.0.0.1:8080/equipment/order/admin?pageNum="+n+"&pageSize=8";
				}
			};
			$.ajax({
				type:"get",
				url: path,
				contentType:'application/json;charset=UTF-8',
				dataType:"json",
				async:true,
				success:function(data){
					if(data.code == 200){
						list = [];
						for(i = 0 ; i < data.data.records.length ; i++){
							var order = {
								id:data.data.records[i].orderId,
								createTime:data.data.records[i].createTime,
								orderType:data.data.records[i].orderType,
								orderStatus:data.data.records[i].orderStatus
							}
							list.push(order);
						};
						vm.$data.orderList = list;
						vm.$data.pages = data.data.pages;
					}
				}
			});
		},
		prev:function () {
			if (this.nextPage > 1){
				this.nextPage--;
				$(".table").show();
				$(".paging").show();
				var path = "";
				var $username = $(".username").val();
				if($username != null){
					if(this.type != 0){
						path = "http://127.0.0.1:8080/equipment/order/admin/"+$username+"?type="+this.type+"&pageNum="+this.nextPage+"&pageSize=8";
					}else{
						path = "http://127.0.0.1:8080/equipment/order/admin/"+$username+"?pageNum="+this.nextPage+"&pageSize=8";
					}
				}else{
					if(this.type != 0){
						path = "http://127.0.0.1:8080/equipment/order/admin?type="+this.type+"&pageNum="+this.nextPage+"&pageSize=8";
					}else{
						path = "http://127.0.0.1:8080/equipment/order/admin?pageNum="+this.nextPage+"&pageSize=8";
					}
				};
				$.ajax({
					type:"get",
					url: path,
					contentType:'application/json;charset=UTF-8',
					dataType:"json",
					async:true,
					success:function(data){
						if(data.code == 200){
							list = [];
							for(i = 0 ; i < data.data.records.length ; i++){
								var order = {
									id:data.data.records[i].orderId,
									createTime:data.data.records[i].createTime,
									orderType:data.data.records[i].orderType,
									orderStatus:data.data.records[i].orderStatus
								}
								list.push(order);
							};
							vm.$data.orderList = list;
							vm.$data.pages = data.data.pages;
						}
					}
				});
			}
		},
		next:function(){
			if (this.nextPage < this.pages){
				this.nextPage++;
				$(".table").show();
				$(".paging").show();
				var path = "";
				var $username = $(".username").val();
				if($username != null){
					if(this.type != 0){
						path = "http://127.0.0.1:8080/equipment/order/admin/"+$username+"?type="+this.type+"&pageNum="+this.nextPage+"&pageSize=8";
					}else{
						path = "http://127.0.0.1:8080/equipment/order/admin/"+$username+"?pageNum="+this.nextPage+"&pageSize=8";
					}
				}else{
					if(this.type != 0){
						path = "http://127.0.0.1:8080/equipment/order/admin?type="+this.type+"&pageNum="+this.nextPage+"&pageSize=8";
					}else{
						path = "http://127.0.0.1:8080/equipment/order/admin?pageNum="+this.nextPage+"&pageSize=8";
					}
				};
				$.ajax({
					type:"get",
					url: path,
					contentType:'application/json;charset=UTF-8',
					dataType:"json",
					async:true,
					success:function(data){
						if(data.code == 200){
							list = [];
							for(i = 0 ; i < data.data.records.length ; i++){
								var order = {
									id:data.data.records[i].orderId,
									createTime:data.data.records[i].createTime,
									orderType:data.data.records[i].orderType,
									orderStatus:data.data.records[i].orderStatus
								}
								list.push(order);
							};
							vm.$data.orderList = list;
							vm.$data.pages = data.data.pages;
						}
					}
				});
			}
		}
	}
})