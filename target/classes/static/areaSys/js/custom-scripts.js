
/* 
admin.html页面切换 
 */
(function ($) {
    "use strict";
    var mainApp = {

        initFunction: function () {
            /*MENU 
            ------------------------------------*/
            $('#main-menu').metisMenu();
			
            $(window).bind("load resize", function () {
                if ($(this).width() < 768) {
                    $('div.sidebar-collapse').addClass('collapse')
                } else {
                    $('div.sidebar-collapse').removeClass('collapse')
                }
            });
	 
        },

        initialization: function () {
            mainApp.initFunction();

        }

    }
	
	var baseUrl="";
    // Initializing ///

    $(document).ready(function () {

				
        // if ($.cookie('admin')==''||$.cookie('admin')==null) {
        //     window.location.href = 'adminLogin.html';
        // }

        //点击导航栏切换面板
        //点击订单管理
        $('.btn-ord').click(function(){
            $('.project').hide();
            $('.cate').hide();
            $('.commodity').hide();                                  
            $('.command').hide();  
            $('.modify').hide();      
            $('.member').hide();     
            $('#modifyCate').hide();                   
            $('.rep').hide();
            $('.orderDetail').hide();
            $('.memberCoupon').hide();
            $('.coupon').hide();
            $('.permission').hide();
            $('.ord').show();
            // findAllOrder();
			

       });        
        //点击公告管理
        $('.btn-rep').click(function(){
            $('.active-menu').removeClass("active-menu");            
             $('.project').hide();
             $('.cate').hide();
             $('.commodity').hide();                                  
             $('.command').hide();  
             $('.modify').hide();      
             $('.member').hide();     
             $('#modifyCate').hide();      
             $('.ord').hide();        
            $('.memberCoupon').hide();                  
            $('.orderDetail').hide();     
            $('.permission').hide();           
            $('.coupon').hide();                
            $('.rep').show();
        });

        //点击分类管理
        $('.btn-category').click(function(){
            $('.active-menu').removeClass("active-menu");
            $('.rep').hide();          
            $('.commodity').hide();
            $('.command').hide();       
            $('.coupon').hide();              
            $('.member').hide();
            $('.memberCoupon').hide();            
            $('.orderDetail').hide();            
            $('.modify').hide();                                                                                                                     
            $('.project').hide();
            $('.ord').hide();     
            $('.permission').hide();                                
            $('#modifyCate').hide();  
            $('.cate').show();  
        });
   
        //点击场地管理
        $('.btn-commodity').click(function(){
            $('.active-menu').removeClass("active-menu");            
            $('.project').hide();
            $('.rep').hide();
            $('.cate').hide();            
            $('.command').hide();     
            $('.memberCoupon').hide();            
            $('.coupon').hide();              
            $('.member').hide();  
            $('.orderDetail').hide();            
            $('.ord').hide();                         
            $('#modifyCate').hide();         
            $('.permission').hide();                                  
            $('.modify').hide();                                                                                                                     
            $('#commodity').show();
        });
})
       
        mainApp.initFunction(); 
		$("#sideNav").click(function(){
			if($(this).hasClass('closed')){
				$('.navbar-side').animate({left: '0px'});
				$(this).removeClass('closed');
				$('#page-wrapper').animate({'margin-left' : '260px'});
				
			}
			else{
			    $(this).addClass('closed');
				$('.navbar-side').animate({left: '-260px'});
				$('#page-wrapper').animate({'margin-left' : '0px'}); 
			}
        });

}(jQuery));
