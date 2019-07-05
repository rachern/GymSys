var vm = new Vue({
	el:"#result",
	data:{
		refereeName:"",
		areaName:"",
		title:"",
		message:""
	},
	created:function(){
		var id = GetQueryString("id");
		$.ajax({
            type:"get",
            url: ("http://127.0.0.1:8080/matchInfo/"+id),
            contentType:'application/json;charset=UTF-8',
            dataType:"json",
            async:true,
            success:function(data){
            	if(data.code == 200){
					vm.$data.refereeName = data.data.refereeName;
					vm.$data.areaName = data.data.areaName;
					vm.$data.title = data.data.title;
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