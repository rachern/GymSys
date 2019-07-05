$(document).ready(function () {
	init();
	initAllOrders();
	// checkedOrderIsEffect();
	
});

// var baseUrl="http://47.107.137.51:8002";

var baseUrl=" ";


/* 公告ID */
var areaNoticeId=1;

/* 是否创建新公告 */
var isCreateNotice=false;

function init(){
	//---------公告管理------->
	/* 公告管理 */
	$('.btn-rep').click(getNoticeLists());
	
	/* button监听 */
	$('.login-submit').click(function (){
		var title=$('#detail_title').val();
		var content=$('#detail_content').val();
		var writer=$("#detail_writer").val();
		if(isCreateNotice==true){
			//创建新公告
			if(title!=''||content!=''){
				var body={
					"title":title,
					"message":content
				};
				var url=baseUrl+"/areaNotice";
				$.ajax({
					type:'POST',
					url:url,
					dataType:'json',
					data:body,
					success:setNoticeCallback
				});
			}
		}else if(isCreateNotice==false){
			//查看、修改
			if(title!=''||content!=''){
				var body={
					"areaNoticeId":areaNoticeId,
					"title":title,
					"message":content
				};
				var url=baseUrl+"/areaNotice";
				$.ajax({
					type:'PUT',
					url:url,
					dataType:'json',
					data:body,
					success:setNoticeCallback
				});
			}
		}
	});
	
	//-------------类型管理------------ >
	/* 类型管理 */
	$('.btn-category').click(getAreaTypeList());
	
	
	//增加分类
	$('.newCate').click(function(){
	    $('.newpop2,.newpop2').show();
	    $('.product-ok').click(function(){
	        var typeName = $('#categoryname').val();
			var requestBody={
				"typeName":typeName
			};
			var url=baseUrl+"/areaType";
	        $.ajax({
				type: 'POST',
	            url:url,
				data:requestBody,
	            success:function(result){
	                if(result.msg=="成功"){
	                    alert("新增分类成功!");
						getAreaTypeList();
						 $('.newpop2,.newpop2').hide();
	                }
	            }
	        })
	    });
	})
	
	 //关闭新增窗口
	$('.pop-close').click(function () {
	    $('.bgPop,.pop').hide();
	    $('.bgPop2,.pop2').hide();
	    $('.bgPop3,.pop3').hide();
	    $('.bgPop4,.pop4').hide();
	    $('.bgPop5,.pop5').hide();
	});
	
	//按名称搜索分类
	//监听搜索框按下回车
	$("#searchCate").keydown(function (e) {
	    if (e.keyCode == 13) {
	        var value = $('#searchCate').val();
			
			var url=baseUrl+"/areaType/list?name="+value;
			
	        //console.log(value);
	        
	        $.ajax({
	            url:url,
	            type:'GET',
	            dataType:'JSON',
	            success:searchCateCallback
				});
	    }
	});
		
	//-----------场地管理--------->
	//场地管理
	$('.btn-commodity').click(getAreaLists());
	
	 //新增场地
	$('.newProduct').click(function(){
	    $('.newpop,.newpop').show();
		
		//设置下拉框内容
		var typeUrl=baseUrl+"/areaType/list";
		$.get(typeUrl,'',function (result){
			if(result.msg=="成功"){
				var typeLists=result.data;
				var html=`<select id="menu" class="form-control">`;
				for (var type of typeLists) {
					html=html+`<option value='${type.areaTypeId}'>${type.name}</option>`;
				}
				html=html+`</select>`;
				$('#type_selected_menu').append(html);
			}
		});
		
		//保存上传数据
		 $('.product-ok').click(function (){
			 
			 var url=baseUrl+"/areaInfo";
			 var type_id=$("#menu").find("option:selected").val();
			 var classed=$("input[name='filed_classed']:checked").val();
			 var teamed=$("input[name='filed_teamed']:checked").val();
			 var money=$("input[name='filed_money']").val();
			 var name=$("input[name='filed_name']").val();
			 
			 var requestBody={				 
				"areaTypeId":type_id,
				"classed": classed,
				"money":money,
				"name":name,
				"teamed": teamed
			 }
			 
			 $.ajax({
				 type:'POST',
				 url:url,
				 dataType:'json',
				 contentType:"application/json;charset=UTF-8",
				 data:JSON.stringify(requestBody),
				 success:function(result){
					 if(result.msg=="成功"){
						 alert("创建成功!")
						 getAreaLists();
					 }
					 console.log(result);
				 }
			 });
			 
			 // console.log($("#menu").find("option:selected").text());
			 // console.log($("#menu").find("option:selected").val());
		 });
		
	});
	
	//按类型搜索场地信息
	//监听搜索框按下回车
	$("#searchFiled").keydown(function (e) {
	    if (e.keyCode == 13) {
			
	        var value = $('#searchFiled').val();
			
			var typeUrl=baseUrl+"/areaType/list?name="+value;
			
	        //console.log(value);
	        
	        $.ajax({
	            url:typeUrl,
	            type:'GET',
	            dataType:'JSON',
				
	            success:function (result){
					if(result.msg=="成功"){
						var typeLists=result.data;
						var typeID=typeLists[0].areaTypeId
						console.log(typeID);
						console.log(typeLists[0].name);
						searchFiled(typeID);
					}
				}
				});
	    }
	});
	
	//---------订单管理--------->
	$('#btn-ord').click(initAllOrders());
	
	   //按订单号搜索订单
	//监听搜索框按下回车
	$("#searchOrder").keydown(function (e) {
	    if (e.keyCode == 13) {
	        var value = $('#searchOrder').val();
	        $('.tbodyOrder').html(``);
	       var url=baseUrl+"/areaBook/"+value;
	       $.get(url,'',function (result){
	       	if(result.msg=="成功"){
	       		var order=result.data;
				if(order==''||order==null){
					$('#tbodyOrder').append(`<tr><td>查无此订单...</td></tr>`);
					return;
				}
	       		$('#orderDetail').hide();
	       		var html=`
	       			<tr id="order_${order.bookId}">
	       			    <td>${order.bookId}</td>
	       			    <td>${order.money}</td>
	       			    <td>${order.bookStartTime}-${order.bookEndTime}</td>
	       				<td id="order_status_${order.bookId}">待审核</td>
	       				<td>
	       					<a href="javascript:;" onclick="orderDetail(${order.bookId})" >查看</a>
	       					<a href="javascript:;" onclick="verifyOrder(${order.bookId})" >审核</a>
	       				</td>
	       			</tr>
	       			`;
	       		$('#tbodyOrder').append(html);
	       	}
	       });
		}
	});
	
	
}


	//-----公告start---->

/* 请求获取公告列表 */
function getNoticeLists(){
	var url=baseUrl+"/areaNotice/list";
	$('#tbodyReport').html(``);
	$.get(url,'',onSuccessToNoticeLists);
}

/* 创建新公告 */
function edit_new_notice(){
	isCreateNotice=true;
	$('#detailModal').modal("toggle");
	$('#detail_title').val('');
	$('#detail_content').val('');
	
	$("#label_writer").css("visibility", "hidden");
	$("#detail_writer").css("visibility", "hidden");
	
	
}


/* 获取公告列表 */
function onSuccessToNoticeLists(result){
	if(result.code=='200'){
		var notices=result.data;
		if(notices.length<=0){
			return;
		}
		for(var notice of notices){
			let html=`
			<tr >
			    <td>${notice.areaNoticeId}</td>
			    <td>${notice.title}</td>
			    <td>
			        <a href="javascript:;" onclick="detail_content(${notice.areaNoticeId})" style="display:block;">查看&修改</a>
			        <a href="javascript:;" onclick="deleteNotice(${notice.areaNoticeId})" style="display:block;">删除</a>
			    </td>
			`;
			$('#tbodyReport').append(html);
		}
	}
}

/* 查看&&修改公告Callbacck  */
function detail_content(_id){
	isCreateNotice=false;
	areaNoticeId=_id;
	var url=baseUrl+"/areaNotice/"+_id;
	console.log(url);
	$.get(url,'',getSuccess);
	function getSuccess(data){
		if(data.code=='200'){
			$('#detailModal').modal("toggle");
			$("#label_writer").css("visibility", "visible");
			$("#detail_writer").css("visibility", "visible");
			$('#detail_title').val(data.data.title);
			$('#detail_content').val(data.data.message);
			$("#detail_writer").val(data.code);
			console.log(data.code+data.msg);
		}
	}
}

/* 创建、修改公告Callbacck */
function setNoticeCallback(result){
	
	if(result.code=='200'){
		alert("操作成功！")
		getNoticeLists();
	}else{
		alert("操作失败，请再次尝试")
	}
}

/* 删除公告 */
function deleteNotice(noticeId){
	var url=baseUrl+"/areaNotice/"+noticeId;
	$.ajax({
		type:'DELETE',
		url:url,
		contentType:"application/json",
		success:function(callback){
			if(callback.code=='200'){
				alert("删除成功");
				getNoticeLists();
			}
		}
	});
	
}

//-----公告end---->

//------场地分类start---->

function getAreaTypeList(){
	var url=baseUrl+"/areaType/list";
	$.get(url,'',function(callback){
		if(callback.code=='200'){
			$('#tbodyCate').html(``);
			var typeList=callback.data;
			for(var type of typeList){
				 $('#tbodyCate').append(`
				<tr>
				    <td>${type.areaTypeId}</td>
				    <td>${type.name}</td>
				    <td>
				        <a href="javascript:;" onclick="updateType(${type.areaTypeId})" >修改</a>
				    </td>
				</tr>   
				`);
			}
		}
	});
}

/* 修改场地类型 */
 function updateType(typeId){
	 $('.cate').hide();
	 $('#modifyCate').show();
	 $('.modiCateInfo').click(function (){
		 if($('#name').val()!=''){
		 	var name =$('#name').val();
			var url=baseUrl+"/areaType";
			
			var requestBody={
				"areaTypeId":typeId,
				"name":name
			};
			
			$.ajax({
				type:'PUT',
				url:url,
				data:requestBody,
				success:updateTypeCallback
			});
		 }
	 });
	 
	 function updateTypeCallback(callback){
		 if(callback.code=='200'){
			 alert("修改成功!");
			 $('#modifyCate').hide();
			 $('.cate').show();
			 getAreaTypeList();
		 }

		 
	 }
	 
	 
 }

/* 删除类型 */
function deleteType(typeId){
	var url=baseUrl+"/areaType/"+typeId;
	$.ajax({
		type:'DELETE',
		url:url,
		contentType:"application/json",
		success:function(callback){
			if(callback.msg=="成功"){
				alert("删除成功!");
				getAreaTypeList()();
			}
		}
	});
}

/* 搜索结果 */
function searchCateCallback(result){
			if(result.msg=="成功"){
				$('#tbodyCate').html(``);
				$('#tbodyCate').append(`
				<tr >
				    <td>${result.data[0].areaTypeId}</td>
				    <td>${result.data[0].name}</td>
				    <td>
				      <a href="javascript:;" onclick="updateType(${result.data[0].areaTypeId})">修改</a>
				      <a href="javascript:;" onclick="deleteType(${result.data[0].areaTypeId})">删除</a>
				    </td>
				</tr>   
				`);
			}
			
		}
	
//------场地分类end---->


//------场地管理start-------->

/* 获取场地列表 */
function getAreaLists(){
		$('#tbodyCommodity').html(``);
		var url=baseUrl+"/areaInfo/page?";
		
		var requestBody={
			"pageNum":1,
			"pageSize":5
		};
		
		$.ajax({
			type:'GET',
			url:url,
			dataType:'json',
			data:requestBody,
			success:onSuccess
			
		});
		
		function onSuccess(callback){
			var areaLists=callback.records;
			if(areaLists.length<=0){
				return;
			}
			
			appendComodity(callback);
			
			//总数量
			var size=callback.size;
			//页码
			var currentPage=callback.current;
			
			var pageCount=size/5;
			
			//页码显示
			$('.M-box').pagination({  
			    pageCount:pageCount,   //总页码  
			    coping:false,           //是否开启首页和末页    
			    prevContent:'上页',  
			    nextContent:'下页',  
			    current:1,                    //当前页码  
			    callback:function (result) { 
				
					//回调函数  
			        $.ajax({  
			            url:url,     
			            type:'GET',  
			            data:{  
			                "pageNum":result.getCurrent(),
			                "pageSize":5
			            },  
			            success:function (data) {  
			                appendComodity(data);    
			            }  
			        })  
			    }  
				    
			});
			
		}
		
		
}

 //填充场地数据
function appendComodity(data){
         $('#tbodyCommodity').html(``);
         var areaLists=data.records;
         var classed="否";
		 var teamed="否";
		 
         for(var area of areaLists){
			if (area.classed=='0') {
				classed="否";
			}else{
				classed="是";
			}
			
			if (area.teamed=='0') {
				teamed="否";
			}else{
				teamed="是";
			}
			
			
            $('#tbodyCommodity').append(`
             <tr id="areaId${area.areaId}"> 
                <td>${area.areaId}</td>                
                <td>${area.name}</td>
                <td>${area.areaType}</td>                            
                <td>${classed}</td>
                <td>${teamed}</td>
                <td>${area.money}</td>
				<td>
					<a href="javascript:;" onclick="updateArea(${area.areaId})" >查看&修改</a>
					<a href="javascript:;" onclick="deleteArea(${area.areaId})" >删除</a>
				</td> 
             </tr>   
                `);
			
            }
        }

/* 修改场地信息 */
function updateArea(areaId){
	$('.project').hide();
	$('.rep').hide();             
	$('.commodity').hide();
	$('.member').hide();                                                   
	$('.command').hide();
	$('.modify').show();
	
	// $('input:radio').eq(2).attr('checked', 'checked');
	// $("input[name='classed']:checked").val();
	var url=baseUrl+"/areaInfo/"+areaId;
	var type_id=0;
	
	$.ajax({
		type:'GET',
		url:url,
		
		success:function (result){
		if(result.msg=="成功"){
			$('#areaName').val(result.data.name);
			$('#areaType').val(result.data.areaType);
			if(result.data.classed==1){
				$('input:radio').eq(0).attr('checked', 'checked')
			}
			if(result.data.teamed==1){
				$('input:radio').eq(2).attr('checked', 'checked')
			}
			$('#money').val(result.data.money);
			
			//获取typeId
			var typeUrl=baseUrl+"/areaType/list?name="+result.data.areaType;
			
			/* $.ajax({
				type:'GET',
				url:typeUrl,
				
				success:function (data){
				if(data.msg=="成功"){
					var lists=data.data;
					type_id=lists[0].areaTypeId;
				}
			}
				
			});
			
			 */
		}
	}
	});
	
	//提交修改信息
	$('.modiProInfo').click(function(){                
	    var url=baseUrl+"/areaInfo";
		var requestBody={
			"areaId":areaId,
			"name":$('#areaName').val(),
			"teamed":$("input[name='teamed']:checked").val(),
			"classed":$("input[name='classed']:checked").val(),
			"money":$('#money').val(),
			"areaTypeId":1
			// "areaTypeId":type_id
			
		};
		
		console.log(requestBody);
		
	  $.ajax({
	        url:url,
	        type:'PUT',
	        data:requestBody,
	        success:function(data){
				console.log(data);
	            if(data.message=="No message available"){
	                alert("您没有操作这个功能的权限");
	            }else if(data.code=='200'){
					alert("修改成功！");
					$('.modify').hide();
					$('.commodity').show();
					getAreaLists();
				}
	            
	        }
	    });
	
	});
		
	
	
}	
// 删除场地信息
function deleteArea(areaId){
	
	var deleteUrl=baseUrl+"/areaInfo/"+areaId;
	$.ajax({
		type:'DELETE',
		url:deleteUrl,
		dataType:'json',
		success:function (result){
			if(result.msg=="成功"){
				alert("删除成功!");
				$('#areaId'+areaId).remove();
		
			}
		}
	});
	
}

/* 搜索结果 */
function searchFiled(typeID){
	/* if(typeID==0||typeID==''){
		return;
	} */
	var url=baseUrl+"/areaInfo/page?";
	var requestBody={
		"areaTypeId":typeID,
		"pageNum":1,
		"pageSize":5
	};
	
	$.ajax({
		type:'GET',
		url:url,
		dataType:'json',
		data:requestBody,
		success:onSuccess
		
	});
	
	function onSuccess(callback){
		var areaLists=callback.records;
		if(areaLists.length<=0){
			return;
		}
		
		appendComodity(callback);
		
		//总数量
		var size=callback.size;
		//页码
		var currentPage=callback.current;
		
		var pageCount=size/5;
		
		//页码显示
		$('.M-box').pagination({  
		    pageCount:pageCount,   //总页码  
		    coping:false,           //是否开启首页和末页    
		    prevContent:'上页',  
		    nextContent:'下页',  
		    current:1,                    //当前页码  
		    callback:function (result) { 
			
				//回调函数  
		        $.ajax({  
		            url:url,     
		            type:'GET',  
		            data:{
						"areaTypeId":typeID,
		                "pageNum":result.getCurrent(),
		                "pageSize":5
		            },  
		            success:function (data) {  
		                appendComodity(data);    
		            }  
		        })  
		    }  
			    
		});
		
	}
	
}

//---------场地管理end---------->

//---------订单start------------>

/* 初始化首页 */
function initAllOrders(){
	var url = baseUrl + "/areaBook/page?";
	
	var requestBody={
		"pageNum":1,
		"pageSize":8,
	};
	
	$.ajax({
		type:'GET',
		url:url,
		dataType:'json',
		data:requestBody,
		success:onSuccess
		
	});
	
	function onSuccess(callback){
		var orderLists=callback.records;
		if(orderLists==''){
			var html = `
				<tr>
					<td>还没有订单呢..<td>
				</tr>`;
			
			$('#tbodyOrder').append(html);
			return;
		}
		//填写信息
		appendOrder(callback);
		//数据分页
		pageChanged(callback);
		
	}
}

/* 填写订单列表 */
function appendOrder(data){
	$('#tbodyOrder').html('');
	
	var orderLists=data.records;
	if(orderLists==''){
		var html = `
			<tr>
				<td>没有更多了..<td>
			</tr>`;
		
		$('#tbodyOrder').append(html);
		return;
	}
	for(var order of orderLists){
		var html=`
			<tr id="order_${order.bookId}">
			    <td>${order.bookId}</td>
			    <td>${order.money}</td>
			    <td>${order.bookStartTime}-${order.bookEndTime}</td>
				<td id="order_status_${order.bookId}">待审核</td>
				<td>
					<a href="javascript:;" onclick="orderDetail(${order.bookId})" >查看</a>
					<a href="javascript:;" onclick="verifyOrder(${order.bookId})" >审核</a>
				</td>
			</tr>
			`;
		$('#tbodyOrder').append(html);
	}
		
}

/* 分页查询订单 */
function pageChanged(callback){
	
	 var url = baseUrl + "/areaBook/page?";
	
	//总数量
	var size=callback.size;
	//页码
	var currentPage=callback.current;
	
	var pageCount=size/5;
	
	//页码显示
	$('.M-box').pagination({  
	    pageCount:pageCount,   //总页码  
	    coping:false,           //是否开启首页和末页    
	    prevContent:'上页',  
	    nextContent:'下页',  
	    current:1,                    //当前页码  
	    callback:function (result) { 
		
			//回调函数  
	        $.ajax({  
	            url:url,     
	            type:'GET',  
	            data:{  
	                "pageNum":result.getCurrent(),
	                "pageSize":8,
	            },  
	            success:function (data) {
					appendOrder(data); 
	                  
	            }  
	        });
	    }  
		    
	});
	
}

/* 审核订单 */
function verifyOrder(orderId){
	$('#orderDetail').hide();
	$('.newpop3,.newpop3').show();
	$('#orderid').val(orderId);
	$('.pop-ok').click(function (){
		var value=$("input[name='radio2']:checked").val();
		if(value=="1"||value==1){
			$('#order_status_'+orderId).text("已审核");
			$('.newpop3,.newpop3').hide();
		}else if(value=="2"||value==2){
			$('#order_status_'+orderId).text("未通过");
			$('.newpop3,.newpop3').hide();
		}
	});
	
	$('.pop-cancel').click(function (){
		$('.newpop3,.newpop3').hide();
	});
}

/* 查看详情 */
function orderDetail(orderId){
	$('.tbodyDetail').html('');
	var url=baseUrl+"/areaBook/"+orderId;
	$.get(url,'',function (result){
		if(result.msg=="成功"){
			var orderInfo=result.data;
			$('#orderDetail').show();
			var html=`
				<tr id="order_${orderInfo.bookId}">
				    <td>${orderInfo.bookId}</td>
				    <td>${orderInfo.areaName}</td>
				    <td>${orderInfo.bookStartTime}</td>
				    <td>${orderInfo.bookEndTime}</td>
					<td>${orderInfo.money}</td>
					
				</tr>
				`;
			$('.tbodyDetail').append(html);
		}
	});
}



/* function checkedOrderIsEffect(){
	var url=baseUrl+"/areaBook/conflict?";
	var requestBody={
		"bookId":14,
		"areaId":1,
		"bookStartTime":"2019-06-25T17:00:00",
		"userId":5,
		"bookEndTime":"2019-07-07T19:00:00"
	};
	
	$.ajax({
		url:url,
		type:'GET',
		data:requestBody,
		success:function (data){
			console.log(data);
		}
	});
	
	
}
 */
//---------订单end-------------->

