var vm = new Vue({
    el:"#update",
    data:{
        equipmentList:[]
    },
    methods:{
        changePrice:function(val,index){
            this.equipmentList[index].compensatePrice = val.target.value;
        },
        confirm:function(){
            if(confirm("您确定提交该更改吗？") == true){
                var eqList = [];
                var list = {
                    compensateList:[]
                };
                for(var i=0;i<this.equipmentList.length;i++){
                    var equipment = {
                        eqId:this.equipmentList[i].id,
                        compensatePrice:this.equipmentList[i].compensatePrice
                    };
                    eqList.push(equipment);
                }
                list.compensateList = eqList;
                console.log(list);
                var eqData = JSON.stringify(list);
                $.ajax({
                    type: "POST",
                    url: "http://127.0.0.1:8080/equipment/compensate",
                    dataType: "json",
                    contentType:"application/json;charset=UTF-8",
                    data: eqData,
                    success: function(data){
                        console.log(data);
                        window.location.href = "compensateStandardList.html";
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
                            compensatePrice:0
                        }
                        list.push(equipment);
                    };
                    vm.$data.equipmentList = list;
                }
            }
        });
    }
})