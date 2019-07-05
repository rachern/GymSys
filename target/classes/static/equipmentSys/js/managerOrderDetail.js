var vm = new Vue({
    el:"#content",
    data:{
        equipmentList:[],
        totalPrice:0,
        damageEquipmentList:[],
        orderStatus:0,
        orderId:0,
        status:"",
        num:""
    },
    methods:{
        damage:function(index){
            $("#damage").show();
            this.damageEquipmentList.push(this.equipmentList[index]);
            this.orderStatus = 1;
        },
        changeNum:function(val,index){
            this.damageEquipmentList[index].eqNumber = val.target.value;
        },
        finishMaintain:function(){
            if (confirm("您确定维修完成吗？") == true){
                this.orderStatus = 4;
                $.ajax({
                    type: "POST",
                    url: ("http://127.0.0.1:8080/equipment/order/status/"+this.orderStatus+"/"+this.orderId),
                    dataType: "json",
                    contentType:"application/json;charset=UTF-8",
                    data: null,
                    success: function(data){
                        console.log(data);
                        window.location.href = "managerOrder.html";
                    }
                });
            }
        },
        renting:function(){
            if (confirm("您确定将状态改为租借中吗？") == true){
                this.orderStatus = 2;
                $.ajax({
                    type: "POST",
                    url: ("http://127.0.0.1:8080/equipment/order/status/"+this.orderStatus+"/"+this.orderId),
                    dataType: "json",
                    contentType:"application/json;charset=UTF-8",
                    data: null,
                    success: function(data){
                        console.log(data);
                        window.location.href = "managerOrder.html";
                    }
                });
            }
        },
        confirmPay:function(){
            if (confirm("您确定支付吗？") == true){
                this.orderStatus = 1;
                $.ajax({
                    type: "POST",
                    url: ("http://127.0.0.1:8080/equipment/order/status/"+this.orderStatus+"/"+this.orderId),
                    dataType: "json",
                    contentType:"application/json;charset=UTF-8",
                    data: null,
                    success: function(data){
                        console.log(data);
                        window.location.href = "managerOrder.html";
                    }
                });
            }
        },
        cancel:function(){
            if (confirm("您确定取消订单吗？") == true){
                this.orderStatus = 3;
                $.ajax({
                    type: "POST",
                    url: ("http://127.0.0.1:8080/equipment/order/status/"+this.orderStatus+"/"+this.orderId),
                    dataType: "json",
                    contentType:"application/json;charset=UTF-8",
                    data: null,
                    success: function(data){
                        console.log(data);
                        window.location.href = "managerOrder.html";
                    }
                });
            }
        },
        confirmReturn:function(){
            if (confirm("您确定归还吗？") == true){
                var eqList = [];
                var list = {
                    eqOrderDetailList:[]
                };
                for(var i=0;i<this.damageEquipmentList.length;i++){
                    if(this.damageEquipmentList[i].number != 0){
                        var equipment = {
                            eqId:this.damageEquipmentList[i].eqId,
                            eqNumber:this.damageEquipmentList[i].eqNumber
                        };
                        eqList.push(equipment);
                    }
                };
                list.eqOrderDetailList = eqList;
                console.log(list);
                if (list.eqOrderDetailList.length == 0){
                    this.orderStatus = 0;
                }else{
                    this.orderStatus = 1;
                }
                var eqData = JSON.stringify(list);
                $.ajax({
                    type: "POST",
                    url: ("http://127.0.0.1:8080/equipment/order/return/"+this.orderStatus+"/"+this.orderId),
                    dataType: "json",
                    contentType:"application/json;charset=UTF-8",
                    data: eqData,
                    success: function(data){
                        console.log(data);
                        window.location.href = "managerOrder.html";
                    }
                });
            }
        }
    },
    created:function(){
        var id = GetQueryString("id");
        $.ajax({
            type:"get",
            url: ("http://127.0.0.1:8080/equipment/order/"+id),
            contentType:'application/json;charset=UTF-8',
            dataType:"json",
            async:true,
            success:function(data){
                if(data.code == 200){
                    list = [];
                    for(var i = 0 ; i < data.data.eqOrderDetailList.length ; i++){
                        var equipment = {
                            eqId:data.data.eqOrderDetailList[i].eqId,
                            eqName:data.data.eqOrderDetailList[i].eqName,
                            eqNumber:data.data.eqOrderDetailList[i].eqNumber,
                            eqOrderPrice:data.data.eqOrderDetailList[i].eqOrderPrice,
                        }
                        list.push(equipment);
                    }
                    vm.$data.totalPrice = data.data.price;
                    vm.$data.equipmentList = list;
                    vm.$data.orderId = id;
                    vm.$data.status = data.data.orderStatus;
                    console.log(data.data);
                }
                if (data.data.orderType == "器材购置"){
                    vm.$data.num = "购置数量";
                } else if (data.data.orderType == "器材报废") {
                    vm.$data.num = "报废数量";
                } else{
                    vm.$data.num = "租借数量";
                }
                
                if(data.data.orderStatus == "未支付"){
                    $(".unpaid").hide();
                    $("#damage").hide();
                }else if(data.data.orderStatus == "已支付未到租借时间"){
                    $(".unrent").hide();
                    $("#damage").hide();
                }else if(data.data.orderStatus == "租借中" || data.data.orderStatus == "租借超时未归还"){
                    $(".return").hide();
                    $(".uReturn").show();
                    $("#damage").hide();
                }else if(data.data.orderStatus == "报修中"){
                    $(".repairs").hide();
                    $("#damage").hide();
                }else{
                    $(".other").hide();
                    $("#damage").hide();
                }
            }
        });
        function GetQueryString(name){
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)
                return  unescape(r[2]);
            return null;
        }
    }
})