var vm = new Vue({
	el:"#result",
	data:{
		refereeId:0,
		name:"",
		message:""
	},
	created:function(){
		var id = GetQueryString("id");
		$.ajax({
            type:"get",
            url: ("http://127.0.0.1:8080/refereeInfo/"+id),
            contentType:'application/json;charset=UTF-8',
            dataType:"json",
            async:true,
            success:function(data){
            	if(data.code == 200){
					vm.$data.refereeId = data.data.refereeId;
					vm.$data.name = data.data.name;
					vm.$data.message = data.data.message;
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