layui.use('form', function () {
    var form = layui.form;
});

$("#sumbit").click(function () {
    var equipmentName = document.getElementById("equipmentName").value;
    var equipmentTotalNumber = document.getElementById("equipmentTotalNumber").value;
    var equipmentPrice = document.getElementById("equipmentPrice").value;
    /* Act on the event */
    if (equipmentName == ""||equipmentTotalNumber == ""||equipmentPrice == "") {
        alert("输入不允许为空")
        return false
    };
    var a = {
        'equipmentName': equipmentName,
        'equipmentTotalNumber': equipmentTotalNumber,
        'equipmentPrice': equipmentPrice,
      };
      var b = JSON.stringify(a)
    $.ajax({
        url: 'http://localhost:8080/equipmentInfo',
        type: 'POST',
        dataType: 'json',
        headers: { 'Content-Type': 'application/json;charset=utf-8' },
        data: b
    })
        .done(function () {
            console.log("success");
            alert("添加成功");
            window.location.href = "equipment-manager.html"
        })
        .fail(function () {
            console.log("error");
            alert("添加失败");
        })
        .always(function () {
            console.log("complete");
        });
})