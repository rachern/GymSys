var vm = new Vue({
    el:"#content",
    data:{
        positiveOrderList:[],
        negativeOrderList:[],
        positivePrice:0,
        negativePrice:0,
        type:0
    },
    methods:{
        order:function(val){
            this.type = val;
        },
        detailPosition:function(index){
            window.location.href = "userOrderDetail.html?id="+this.positiveOrderList[index].id;
        },
        detailNegative:function(index){
            window.location.href = "userOrderDetail.html?id="+this.negativeOrderList[index].id;
        },
        search:function(){
            var $year = $(".year").val();
            var $month = $(".month").val();
            $.ajax({
                type:"get",
                url: "http://127.0.0.1:8080/equipment/order/profit?year="+$year+"&month="+$month,
                contentType:'application/json;charset=UTF-8',
                dataType:"json",
                async:true,
                success:function(data){
                    if(data.code == 200){
                        var orderType = "";
                        positiveOrderList = [];
                        negativeOrderList = [];
                        for(i = 0 ; i < data.data.positiveOrderList.length ; i++){
                            switch (data.data.positiveOrderList[i].orderType) {
                                case 1:
                                    orderType = "器材租借";
                                    break;
                                case 2:
                                    orderType = "器材购置";
                                    break;
                                case 3:
                                    orderType = "器材维护";
                                    break;
                                case 4:
                                    orderType = "器材报废";
                                    break;
                                default:
                                    break;
                            };
                            var positiveOrder = {
                                id:data.data.positiveOrderList[i].orderId,
                                createTime:data.data.positiveOrderList[i].orderCreateTime,
                                orderType:orderType,
                                orderPrice:data.data.positiveOrderList[i].orderPrice
                            }
                            positiveOrderList.push(positiveOrder);
                        };
                        for(i = 0 ; i < data.data.negativeOrderList.length ; i++){
                            switch (data.data.negativeOrderList[i].orderType) {
                                case 1:
                                    orderType = "器材租借";
                                    break;
                                case 2:
                                    orderType = "器材购置";
                                    break;
                                case 3:
                                    orderType = "器材维护";
                                    break;
                                case 4:
                                    orderType = "器材报废";
                                    break;
                                default:
                                    break;
                            };
                            var negativeOrder = {
                                id:data.data.negativeOrderList[i].orderId,
                                createTime:data.data.negativeOrderList[i].orderCreateTime,
                                orderType:orderType,
                                orderPrice:data.data.negativeOrderList[i].orderPrice
                            }
                            negativeOrderList.push(negativeOrder);
                        };
                        vm.$data.positiveOrderList = positiveOrderList;
                        vm.$data.negativeOrderList = negativeOrderList;
                        vm.$data.positivePrice = data.data.positivePrice;
                        vm.$data.negativePrice = data.data.negativePrice;
                        console.log(positiveOrderList);
                        if (positiveOrderList.length != 0){
                            $(".income").show();
                        }
                        if (negativeOrderList.length != 0){
                            $(".outcome").show();
                        }
                    }
                }
            });
        }
    }
})