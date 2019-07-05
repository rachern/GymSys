layui.use(['table', 'laypage', 'laytpl'], function () {
    var table = layui.table,
      laypage = layui.laypage;
  
    table.render({
      elem: '#test'
      , method: 'GET'
      , url: 'http://localhost:8080/equipmentUse/list/'+sessionStorage.id
      , cellMinWidth: 20 //全局定义常规单元格的最小宽度
      , cols: [[
        { field: 'equipmentUseId', title: '申请Id', align: 'center', sort: true }
        , { field: 'equipmentId', title: 'text', title: '器材Id', align: 'center' }
        , { field: 'number', title: 'text', title: '数量', align: 'center', templet: '#number' }
        , { field: 'startTime', edit: 'text', title: '开始时间', align: 'center', templet: '#startTime' }
        , { field: 'endTime', edit: 'text', title: '结束时间', align: 'center', templet: '#endTime' }
        , { field: 'sure', edit: 'text', title: '是否结束', align: 'center', templet: '#sure' }
        , { align: 'center', toolbar: '#barDemo', title: '操作', minWidthh: '70', align: 'center' } //这里的toolbar值是模板元素的选择器
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
  
  
  
    //监听单元格编辑
    table.on('edit(test)', function (obj) {
      var value = obj.value //得到修改后的值
        , data = obj.data //得到所在行所有键值
        , field = obj.field; //得到字段
      var a = {
        'userId': data.userId,
        'userName': data.userName,
        'userPassword': data.userPassword,
        'userEmail': data.userEmail,
      };
      var b = JSON.stringify(a)
      $.ajax({
        url: 'http://localhost:8080/authUser',
        type: 'PUT',
        headers: { 'Content-Type': 'application/json;charset=utf-8' },
        dataType: 'json',
        data: b,
      })
        .done(function () {
          console.log("success");
          layer.msg('修改成功', {
            icon: 1
          })
        })
        .fail(function () {
          console.log("error");
          alert("修改失败")
        })
        .always(function () {
          console.log("complete");
        });
  
    });
  
    //监听工具条
    table.on('tool(test)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
      var data = obj.data;
      var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
      console.log(data);
      var use_id = obj.data.equipmentUseId;
      if (layEvent === 'return') { //删除
        layer.confirm('真的归还么', function (index) {
          console.log(data);
          layer.close(index);
          //向服务端发送删除指令
          $.ajax({
            url: 'http://localhost:8080/equipmentUse/back/'+use_id,
            type: 'PUT',
            dataType: 'json',
          })
            .done(function () {
              console.log("success");
              layer.msg('归还成功', {
                icon: 1
              })
            })
            .fail(function () {
              console.log("归还失败");
            })
            .always(function () {
              console.log("complete");
            });
        });
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