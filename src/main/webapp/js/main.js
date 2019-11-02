function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}

function logout() {
    $.messager.confirm("crm","确认退出系统吗？",function(r){//这里r代表退出
        if(r){
            setTimeout(function () {
                //清cookie
                $.removeCookie("userName");
                $.removeCookie("trueName");
                //跳转页面
                window.location.href="index";
            },2000)
        }
    })
}

//打开数据表格
function openPasswordModifyDialog() {
    $("#dlgdlg").dialog("open");
}
//关闭按钮
function closePasswordModifyDialog() {
    $("#dlgdlg").dialog("close");
}
//保存按钮
function modifyPassword() {
    $('#fm').form({
        url:ctx+"/user/updatePwd",
        onSubmit: function(){
          return $('#fm').form("validate");  //validate检查表格是否为空
        },
        success:function(data){
            data=JSON.parse(data);//将返回值转换成为json串
            if(data.code==200){
                $.messager.alert("crm","修改密码成功，即将跳转到登录页面，请重新登录！","info");
                setTimeout(function () {
                    $.removeCookie("userName");
                    $.removeCookie("trueNamw");
                    $.removeCookie("userId");
                },2000)
                window.location.href="index"
            }else {
                $.messager.alert("crm",data.msg,"error");
            }
        }
    });
// submit the form
    $('#fm').submit();

}






