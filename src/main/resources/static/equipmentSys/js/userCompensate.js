var vm = new Vue({
    el:"#result",
    data:{
        standardList:[]
    },
    created:function(){
        $.ajax({
            type:"get",
            url: "http://127.0.0.1:8080/equipment/compensate/list",
            contentType:'application/json;charset=UTF-8',
            dataType:"json",
            async:true,
            success:function(data){
                if(data.code == 200){
                    list = [];
                    for(i = 0 ; i < data.data.length ; i++){
                        var standard = {
                            name:data.data[i].eqName,
                            price:data.data[i].compensatePrice,
                        }
                        list.push(standard);
                    };
                    vm.$data.standardList = list;
                }
            }
        });
    }
})