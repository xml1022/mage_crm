//查询客户信息
function searchCustomer(){
    $("#dg").datagrid("load",{
        khno:$("#s_khno").val(),
        name:$("#s_name").val(),
    });
}

//打开添加对话框
function openCustomerAddDialog() {
    $("#fm").form("clear")
    $("#dlg").dialog("open").dialog("setTitle","添加客户信息")
}
//关闭对话框
function closeCustomerDialog() {
    $("#dlg").dialog("close");
}

/**
 * 保存按钮，提交表单中的数据
 */
function saveOrUpdateCustomer(){
    var id = $("#id").val();
    var url = ctx + "/customer/update"
    if(isEmpty(id)){
        var url = ctx + "/customer/insert"
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function(data){
            data=JSON.parse(data)
            if(data.code==200){
                $.messager.alert("来自crm",data.msg,"info")
                closeCustomerDialog()
                $("#dg").datagrid("load")
            }else {
                $.messager.alert("来自crm",data.msg,"error")
            }
        }
    });
}

//打开修改对话框
function openCustomerModifyDialog(){
    var rows = $("#dg").datagrid("getSelections")
    if(rows.length==0){
        $.messager.alert("来自crm","请选择要修改的记录！","warning")
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","请选择一条记录！","warning")
        return;
    }
    //将选中的数据加载到表格中
    $("#fm").form("load",rows[0])
    $("#dlg").dialog("open").dialog("setTitle","添加客户信息")
}


//打开删除对话框
function deleteCustomer() {
    var rows = $("#dg").datagrid("getSelections")
    if(rows.length==0){
        $.messager.alert("来自crm","请选择要删除的记录！","warning")
        return;
    }

    var params = "id=";
    for(var i=0;i<rows.length;i++){
        if(i<rows.length-1){
            params = params+rows[i].id+"&id=";
        }else{
            params = params+rows[i].id;
        }
    }
    $.messager.confirm("来自crm","确定删除吗？",function (r){
       if(r){
           $.ajax({
               type:"post",
               url:ctx+"/customer/delete",
               data:params,
               dataType:'json',
               success:function(data){
                   if(data.code==200){
                       $.messager.alert("来自crm",data.msg,"info")
                       $("#dg").datagrid("load")//刷新
                   }else{
                       $.messager.alert("来自crm",data.msg,"error")
                   }
               }
           })
       }
    });
}

//打开历史订单详情框
function openCustomerOtherInfo(title,id) {
    window.parent.openTab(title,ctx+"/customer_order/index");
}
