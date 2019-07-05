$(document).ready(function () {
	common();
	
	toNotice();
	toAreaResearch();
	toPriceStandran();
	init();
	
});

let pageCount=1;

/* 
获取公告信息 
 */
function init(){
	// let url=headUrl+'areaNotice/list';
	let url=headUrl+"areaNotice/page?pageNum=1&pageSize=6";
	//获取公告列表
	$.get(url,'',getNoticesSuccess);
	
	//获取场地信息
	let area_url=headUrl+"areaInfo/page?pageNum=1&pageSize=8";
	$.get(area_url,'',getAreaSuccess);
	
	
}

/* 
 请求公告信息回调
 */
function getNoticesSuccess(data){
			let noticeList=data.records;
			if(noticeList.length>0){
				$("#baseShows").html('');
			
				for(item of noticeList){
					let html=
					`<tr>
						<td>${item.title}</td>
						<td><a class="details" href="javascript:;" onclick="detail_content(${item.areaNoticeId})" >详情</a></td>
					</tr>`;
					$("#baseShows").append(html);
				}
				$('#page_count').text(data.current);				
			}
		
	}

function getAreaSuccess(result){
	let areaList=result.records;
	for (let item of areaList) {
		var html=`
		 <li class="product-item">
		    <div class="image-box">
		        <a href="">
		            <img src="image/field.jpg" alt="">
		        </a>
		    </div>
		    <div class="product-description">
		        <a class="product-title" href="">${item.name}</a>
		        <div>
		            <span class="product-price">￥${item.money}</span>
		            <a href="javascript:;" areaId="${item.areaId}" class="btn addcar-btn" onclick='toAreaInfo(${item.areaId})'>详情</a>
		        </div>
		    </div>
		</li>
		`;
		$('.product-list').append(html);
	}
}

/* 
查看公告详情 
 */
function detail_content(_id){
	let url=headUrl+"areaNotice/"+_id;
	console.log(url);
	$.get(url,'',getSuccess);
	function getSuccess(data){
		if(data.code=='200'){
			$('#detailModal').modal("toggle");
			$('#detail_title').text(data.data.title);
			$('#detail_content').text(data.data.message);
			$("#detail_writer").text(data.code);
			console.log(data.code+data.msg);
		}
	}
}

function toNotice(){
	$('.notice').click(function (){
		if ($(this).hasClass('active')) return;
		$('.order-classify li').removeClass('active');
		$(this).addClass('active');
		let url=headUrl+"areaNotice/page?pageNum=1&pageSize=6";
		$.get(url,'',getNoticesSuccess);
	});
	
}

function toAreaResearch(){
	$('.area_research').click(function (){
		if ($(this).hasClass('active')) return;
		$('.order-classify li').removeClass('active');
		$(this).addClass('active');
		$("#baseShows").html('');
	});
}

function toPriceStandran(){
	$('.price_standran').click(function (){
		if ($(this).hasClass('active')) return;
		$('.order-classify li').removeClass('active');
		$(this).addClass('active');
		$("#baseShows").html('');
	});
}

function toAreaInfo(areaId){
	console.log(areaId);
	// window.open("http://47.107.137.51:8002/areaInfo/1");
	localStorage.setItem("areaId", areaId);
	window.open("./areainfo.html");
}

/* 下一页请求 */
function NextAreaNotices(){
	pageCount++;
	let url=headUrl+"areaNotice/page?pageNum="+pageCount+"&pageSize=6";
	$.get(url,'',updateSuccess);
	
	function updateSuccess(data){
		let noticeList=data.records;
		if(noticeList.length>0){
			$("#baseShows").html('');
		
			for(item of noticeList){
				let html=
				`<tr>
					<td>${item.message}</td>
					<td><a class="details" href="javascript:;" onclick="detail_content(${item.areaNoticeId})" >详情</a></td>
				</tr>`;
				$("#baseShows").append(html);
			}
			$('#page_count').text(data.current);
			pageCount=data.current;
		}
	}
}

/* 上一页请求 */
function PreAreaNotices(){
	pageCount--;
	if (pageCount<=0) {
		return;
	}
	let url=headUrl+"areaNotice/page?pageNum="+pageCount+"&pageSize=6";
	$.get(url,'',updateSuccess);
	
	function updateSuccess(data){
		let noticeList=data.records;
		if(noticeList.length>0){
			$("#baseShows").html('');
		
			for(item of noticeList){
				let html=
				`<tr>
					<td>${item.message}</td>
					<td><a class="details" href="javascript:;" onclick="detail_content(${item.areaNoticeId})" >详情</a></td>
				</tr>`;
				$("#baseShows").append(html);
			}
			$('#page_count').text(data.current);
			pageCount=data.current;
		}
	}
	
}






