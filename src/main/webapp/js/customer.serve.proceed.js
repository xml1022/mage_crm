function openProceedDlg() {
    var rows = $("#dg").datagrid("getSelections")
    if(rows.length==0){
        $.messager.alert("来自crm","请选择需要分配的用户","warning")
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","请选择一条分配的用户","warning")
        return;
    }

    $("#fm").form("load",rows[0])
    $("#dlg").dialog("open")
}
function closeCustomerServeDialog() {
    $("#dlg").dialog("close");
}
function addCustomerServeServiceProceed() {
    $("#fm").form("submit",{
        url:ctx+"/customer_serve/update",
        onSubmit:function(params){
            params.state=3;
            return $("#fm").form("validate")
        },
        success:function(data){
            data:JSON.parse(data)
            if(data.code==200){
                $.messager.alert("来自crm",data.msg,"info")
                closeCustomerServeDialog()
                //刷新
                $("#dg").datagrid("load")
            }else{
                $.messager.alert("来自crm",data.msg,"error")
            }
        }
    })

}