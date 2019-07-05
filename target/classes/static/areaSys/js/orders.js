$(document).ready(function () {
    common();

    tabchange();
    init();
	
});

let page = null;
/* 初始化页面 */
function init() {
	
	$('.order-list-item-sum').html('');
	var thead_html=`
	<tr>
	<th>订单号编号</th>
	    <th>预约场地名称</th>
	    <th>预约时间</th>
	    <th>截止时间</th>
		<th>价格(元)</th>
		<th>状态</th>
		<th>操作</th>
	</tr>`;
	$('.order-list-item-sum').append(thead_html);
	
    var url = headUrl + "areaBook/page?";
	
	var requestBody={
		"pageNum":1,
		"pageSize":5,
		"userId":$.cookie('userid')
	};
	// console.log($.cookie('userid'));
	
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
			
			$('.order-list-item-detail').append(html);
			return;
		}
		//填写信息
		appendOrder(callback);
		//数据分页
		pageChanged(callback,0);
		
	}


}

/* 切换选项状态 */
function tabchange() {
    // 待审核
    $('.payed').click(function () {
        // active的切换
        if ($(this).hasClass('active')) return;
        $('.order-classify li').removeClass('active');
        $(this).addClass('active');
		init();
		
    });
	
    // 已审核
    $('.shipped').click(function () {
        // active的切换
        if ($(this).hasClass('active')) return;
        $('.order-classify li').removeClass('active');
        $(this).addClass('active');
		checkedFinish();
		
		
        });
    // 已完成
    $('.received').click(function () {
        // active的切换
        if ($(this).hasClass('active')) return;
        $('.order-classify li').removeClass('active');
        $(this).addClass('active');
		finished();
	});
}

/* 一审核订单页面 */
function checkedFinish(){
	$('.order-list-item-sum').html('');
	var thead_html=`
	<tr>
	    <th>预约场地名称</th>
	    <th>预约时间</th>
	    <th>截止时间</th>
		<th>价格(元)</th>
		<th>状态</th>
		<th>操作</th>
	</tr>`;
	$('.order-list-item-sum').append(thead_html);
	
	var url = headUrl + "areaBook/page?";
	
	var requestBody={
		"pageNum":1,
		"pageSize":5,
		"userId":$.cookie('userid')
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
			
			$('.order-list-item-detail').append(html);
			return;
		}
		//填写信息
		appendOrderWithChecked(callback);
		//数据分页
		pageChanged(callback,1);
		
	}
	
	
}

/* 已完成订单页面 */
function finished(){
	$('.order-list-item-sum').html('');
	var thead_html=`
	<tr>
	    <th>预约场地名称</th>
	    <th>预约时间</th>
	    <th>截止时间</th>
		<th>价格(元)</th>
		<th>状态</th>
		<th>操作</th>
	</tr>`;
	$('.order-list-item-sum').append(thead_html);
	
	var url = headUrl + "areaBook/page?";
	var requestBody={
		"pageNum":1,
		"pageSize":5,
		"userId":$.cookie('userid')
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
			
			$('.order-list-item-detail').append(html);
			return;
		}
		//填写信息
		appendOrderWithFinished(callback);
		//数据分页
		pageChanged(callback,2);
		
	}
	
	
}


/* 填写未审核订单列表 */
function appendOrder(data){
	$('.order-list-item-detail').html('');
	
	var orderLists=data.records;
	if(orderLists==''){
		var html = `
			<tr>
				<td>没有更多了..<td>
			</tr>`;
		
		$('.order-list-item-detail').append(html);
		return;
	}
	for(var order of orderLists){
		var html=`
			<tr id="order_${order.bookId}">
			    <td>${order.bookId}</td>
			    <td>${order.areaName}</td>
			    <td>${order.bookStartTime}</td>
			    <td>${order.bookEndTime}</td>
				<td>${order.money}</td>
				<td>待审核</td>
				<td><a class="order-a" href="javascript:;" onclick="deleteOrder(${order.bookId})" >取消订单</a></td>
			</tr>
			`;
		$('.order-list-item-detail').append(html);
	}
	
}


/* 填写已审核订单列表 */
function appendOrderWithChecked(data){
	$('.order-list-item-detail').html('');
	
	var orderLists=data.records;
	if(orderLists==''){
		var html = `
			<tr>
				<td>没有更多了..<td>
			</tr>`;
		
		$('.order-list-item-detail').append(html);
		return;
	}
	for(var order of orderLists){
		var html=`
			<tr id="order_${order.bookId}">
			    <td>${order.areaName}</td>
			    <td>${order.bookStartTime}</td>
			    <td>${order.bookEndTime}</td>
				<td>${order.money}</td>
				<td>通过审核</td>
				<td><a class="order-a" href="javascript:;" onclick="deleteOrder(${order.bookId})" >取消订单</a></td>
			</tr>
			`;
		$('.order-list-item-detail').append(html);
	}
	
}

/* 填写已完成订单列表 */
function appendOrderWithFinished(data){
	$('.order-list-item-detail').html('');
	
	var orderLists=data.records;
	if(orderLists==''){
		var html = `
			<tr>
				<td>没有更多了..<td>
			</tr>`;
		
		$('.order-list-item-detail').append(html);
		return;
	}
	for(var order of orderLists){
		var html=`
			<tr id="order_${order.bookId}">
			    <td>${order.areaName}</td>
			    <td>${order.bookStartTime}</td>
			    <td>${order.bookEndTime}</td>
				<td>${order.money}</td>
				<td>已完成</td>
				<td><a class="order-a" href="javascript:;" onclick="orderInfo(${order.bookId})" >查看</a></td>
			</tr>
			`;
		$('.order-list-item-detail').append(html);
		
		
		
		
		
	}
	
}


/* 分页查询 */
function pageChanged(callback,selectType){
	
	 var url = headUrl + "areaBook/page?";
	
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
	                "pageSize":5,
					"userId":$.cookie('userid')
	            },  
	            success:function (data) {
					if(selectType==0){
						appendOrder(data); 
					}else if(selectType==1){
						appendOrderWithChecked(data);
					}else if(selectType==2){
						appendOrderWithFinished(data);
					}
	                  
	            }  
	        });
	    }  
		    
	});
	
}

/* 查看订单详情 */
function orderInfo(orderId){
	console.log(orderId);
	// var url="http://47.107.137.51:8002/areaBook/"+orderId;
	var url="/"+orderId;
	$.get(url,'',function (result){
		if(result.msg=="成功"){
			var data=result.data;
			$('#orderModal').modal("toggle");
			$('.order_id').val(data.bookId);
			$('.order_filed').val(data.areaName);
			$('.order_filed_type').val(data.areaTypeName);
			$('.order_money').val(data.money);
			$('.order_begin').val(data.bookStartTime);
			$('.order_end').val(data.bookEndTime);
		}
	});
}

/* 取消订单 */
function deleteOrder(orderId){
	var url=headUrl+"areaBook/"+orderId;
	if(confirm("是否确认取消订单")){
		$.ajax({
			type:'DELETE',
			url:url,
			contentType:"application/json",
			success:function(callback){
				if(callback.code=='200'){
					alert("取消成功");
					$('#order_'+orderId).remove();
				}
			}
		});
	}
	
}


