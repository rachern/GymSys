//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use(['element','layer'], function(){
	var element = layui.element,
	    layer = layui.layer;
	
	var arrName = ['个人信息'];    //在此处添加对应url的展示名称
	var arrLink = ['user-Info.html',];    //再此处添加url
	
	//获取管理员名称
	$(document).ready(function(){
		var data1 = JSON.parse(sessionStorage.getItem('name'));
		console.log(sessionStorage.namex);
	if(sessionStorage.name !== undefined){ 
		console.log(sessionStorage.name)
		$("#Administrator-name").html(data1.username);
	}else if(sessionStorage.name === undefined){
//		window.location.href="login.html"
	}
	})
	
	$("#sider ul li dd a").click(function(){
		$("#iframe_wrap").css("padding-top","35px");
		$("#content .layui-breadcrumb").css("display","block");
		var obj1 = $(this).parent().parent().siblings().clone();       //找到功能标题
		obj1.find(':nth-child(1)').remove();                           //去除功能标题前的图标
		$("#content .layui-breadcrumb a").eq(0).html(obj1.html());
		var obj2 = $(this).clone();
		obj2.find(':nth-child(1)').remove();            //去除小功能点前的图标
		$("#content .layui-breadcrumb a cite").html(obj2.html()) ;
		
		// index是下标，value是数组内容
	    $.each(arrName,function(index,value){
	       	if(obj2.html() == arrName[index]){
				$("iframe").attr("src",arrLink[index]);
			}
	    });
	});

	$("#header .layui-nav-child a").click(function(){
		$("#iframe_wrap").css("padding-top","15px");
		$("#sider .layui-nav-item dd").each(function(){
			$(this).removeClass("layui-this");
			console.log("remove");
		});
		$("iframe").attr("src","person.html");
		$("#content .layui-breadcrumb").css("display","none");
	});

	$(".layui-logo").click(function(){
		if($(window).width()<640){
			
			if($("#sider").css("width") == "0px"){
				//展开
				$("#sider").css({
					"width": "200px"
				});
				$("#content").css({
					"left": "200px"
				});
				$(".layui-logo .layui-icon").html("&#xe602;");
			}
			//折叠
			else{
				$("#sider").css({
					"width": "0"
				});
				$("#content").css({
					"left": "0"
				});
				$(".layui-logo .layui-icon").html("&#xe603;");
			}
			
		}
	});
	
	$(window).resize(function(){
		if($(window).width()>640){
			$("#sider").css({
				"width": "200px"
			});
			$("#content").css({
				"left": "200px"
			});
			$(".layui-logo .layui-icon").html("&#xe602;");
		}else{
			$("#sider").css({
				"width": "0"
			});
			$("#content").css({
				"left": "0"
			});
			$(".layui-logo .layui-icon").html("&#xe603;");
		}
	});
	
	$(function () {  
        //防止页面后退  
        history.pushState(null, null, document.URL);  
        window.addEventListener('popstate', function () {  
                history.pushState(null, null, document.URL);  
        });  
    }) 
	function close(){
		sessionStorage.clear();
	}

	//调用退出函数
	$("#exit").click(function(){
		confirmWindow();
	});
	//确认退出弹窗
	function confirmWindow(){
		layer.confirm('确定要退出吗？', {
  			btn: ['确定', '取消'] //可以无限个按钮
  		}, function(index, layero){
  				window.location.href="login.html"
  				/*$.ajax({
  					type:"GET",
  					url:"Ineedaname假数据.json",
  					async:true,
  					dataType: "json",
  					success: function(res){
  						
  					},
  					error: function(xhr,status,err){
  						
  					}
  				});*/
  				sessionStorage.clear();
			}, function(index){
	    		console.log("不关闭啦~");
		});
	}
});
