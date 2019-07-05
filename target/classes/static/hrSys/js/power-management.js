layui.use(['table', 'laypage', 'laytpl'], function () {
    var table = layui.table,
      laypage = layui.laypage;
  
    table.render({
      elem: '#test'
      , method: 'GET'
      , url: 'http://localhost:8080/authPower/list'
      // ,url:'../order-management假数据.json'
      , cellMinWidth: 20 //全局定义常规单元格的最小宽度
      , cols: [[
        { field: 'powerId', title: '权限ID', align: 'center', sort: true }
        , { field: 'powerName', edit: 'text', title: '权限名', align: 'center', templet: '#powerName' }
        , { field: 'url', edit: 'text', title: '接口URL', align: 'center', templet: '#url' }
        , { field: 'createTime', edit: 'text', title: '创建时间', align: 'center', templet: '#createTime' }
        , { field: 'updateTime', edit: 'text', title: '更新时间', align: 'center', templet: '#updateTime' }
      ]]
      , id: 'test'
      , page: false
      , height: 'full-70'
      , limit: 10
      , request: {
        pageName: 'pageIndex' //页码的参数名称，默认：page
        , limitName: 'pageSize' //每页数据量的参数名，默认：limit
  
      }
      , response: {
        statusName: 'code' //数据状态的字段名称，默认：code
        , statusCode: 200 //成功的状态码，默认：0
        , msgName: 'msg' //状态信息的字段名称，默认：msg
        , countName: 'count' //数据总数的字段名称，默认：count
        , dataName: 'data' //数据列表的字段名称，默认：data
      }
    });
  
    //根据状态查看订单(选择状态的时候，向后台发送状态，然后后台返回相应状态的数据，渲染到表格)
    $("form dd").each(function () {
      if ($(this).html() != "请选择要查看的状态") {
        $(this).click(function () {
          var str = $(this).html();
          console.log(str);
          tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
              status: str
            }
            , page: {
              curr: 1 //重新从第 1 页开始
            }
          });
        });
      }
    });
  
  });