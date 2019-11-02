
function searchUsers(){
    $("#dg").datagrid("load", {
        userName: $("#userName").val(),
        trueName: $("#trueName").val(),
        phone: $("#phone").val(),
        email: $("#email").val()
    })
}

function openAddUserDialog() {
    $("#dlg").dialog("open");
}
function closeDialog(){
    $("#dlg").dialog("close");
}
function saveOrUpdateUser() {
    var id=$("#id").val();
    var url=ctx+"/user/insert";
    if(!isEmpty(id)){
        url=ctx+"/user/update";
    }
    $("#fm").form("submit", {
        url:url,
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function (data) {
            data = JSON.parse(data);
            if(data.code==200){
                $.messager.alert("来自crm系统",data.msg,"info")
                $("#fm").form("clear");
                closeDialog();
                searchUsers();
            }else{
                $.messager.alert("来自crm系统",data.msg,"info")
            }
        }
    });
}

function openModifyUserDialog() {
    var rows= $("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选中待更新记录!","info");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","只能选择一条记录执行更新!","info");
        return;
    }
    $("#fm").form("load",rows[0]);
    $("#dlg").dialog("open").dialog("setTitle","修改用户记录");
}
function deleteUser(){
    var rows= $("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选中待删除记录!","info");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","只能选择一条记录执行删除!","info");
        return;
    }
    $.messager.confirm("来自crm","确认删除？",function(r){
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/user/delete",
                data:"id="+rows[0].id,
                dataType:"json",
                success:function(data){
                    if(data.code==200){
                        $.messager.alert("来自crm",data.msg,"info")
                        closeDialog()
                        searchUsers();
                    }

                }
            })
        }

    })

}

