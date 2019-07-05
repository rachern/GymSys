$(document).ready(function () {
	init();
	datepicker ();
	bookOnClick();
	reset();
});

// let baseUrl="http://47.107.137.51:8002";

var baseUrl=" ";

var areaId;
 
function init(){
	var url=baseUrl+"/areaInfo/list";
	$.get(url,'',function onSuccess(result){
		if(result.code=='200') {
			let fieldList=result.data;
			if(fieldList.length>0){
				for (let field of fieldList) {
					var classed="否";
					var teamed="否";
					if(field.classed==1){
						classed="是";
					}
					if(field.teamed==1){
						teamed="是";
					}
					let html=`
						<tr>
							<td>${field.areaId}</td>
							<td>${field.name}</td>
							<td>${field.areaType}</td>
							<td>${classed}</td>
							<td>${teamed}</td>
							<td>${field.money}</td>
							<td><a class="a-book" href="javascript:;" id='${field.areaId}' onclick='bookOnclick(${field.areaId})' >预约</a></td>
						</tr>
						`;
					
					$('#FieldListTable').append(html);
					
				}
			}
			
		}
	});
}
/* 
日期选择器 
 */
function datepicker () {
	$('#order_date').datepicker();
	$('#d_begin').datepicker();
	$( "#d_end" ).datepicker();
}

/* 预约button onclick事件 */
function bookOnclick(areaId){
	this.areaId=areaId;
	var url=baseUrl+"/areaInfo/"+areaId;
	$.get(url,'',function onSuccess(result){
		if(result.code=='200'){
			console.log(result.data.name)
			$("input[name='idfield']").val(result.data.name);
			$("input[name='user_id']").val(localStorage.getItem('username'));
			$("input[name='order_cost']").val(result.data.money);
			
		}
	});
}

/* 提交预约叮当 */
function bookOnClick(){
	$('#btn_book').click(function (){
		 
		 
		 var userId=localStorage.getItem('userid');
		 var money=$("input[name='order_cost']").val();
		 var order_date=$("input[name='order_date']").val();
		 var order_begin=$("input[name='order_begin']").val();
		 var order_end=$("input[name='order_end']").val();
		 /* if(areaId==='' ||userId===''||money===''||order_date===''||order_begin===''||order_end===''){
			 alert("请填写完整信息!");
			 return;
		 } */
		var array_begin=order_begin.split("/");
		var array_end=order_end.split("/");
		var beginTime=array_begin[2]+"-"+array_begin[0]+"-"+array_begin[1]+" 23:59:59";
		var endTime=array_end[2]+"-"+array_end[0]+"-"+array_end[1]+" 23:59:59";
		

		 var requestBody={
			 "areaId":1,
			 "userId":5,
			 // "bookStartTime":"2019-06-27 23:59:59",
			 // "bookEndTime":"2019-07-06 00:00:00"
			 "bookStartTime":beginTime,
			 "bookEndTime":endTime
		 };
		 
		 var url=baseUrl+"/areaBook";
		 
		 if(confirm("确认提交预约订单吗？")){
			  $.ajax({
			 	type:"post",
			 	url:url,
			 	dataType:"json",
			 	// contentType:"application/json;charset=UTF-8",
			 	data:requestBody,
			 	success:bookSuccess,
			 	error:function (){
					alert("提交失败，请再次尝试");
				}	
			 });
			 		
		 }
		 
		 function bookSuccess(data){
		 			alert("提交成功!");
		 		 }
		 
		
	});
}

/* 重置 */
function reset(){
	$('#btn_reset').click(function (){
		$("input[name='order_cost']").val('');
		$("input[name='order_date']").val('');
		$("input[name='order_begin']").val('');
		$("input[name='order_end']").val('');
		$("input[name='user_id']").val('');
		$("input[name='idfield']").val('');
	});
}



 