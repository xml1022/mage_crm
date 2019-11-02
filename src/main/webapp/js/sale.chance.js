function formatterState(val){
    if(val==0){
        return "未分配";
    }else if(val==1){
        return "已分配";
    }else{
        return "未知";
    }
}
//页面加载就会调用
$(function(){
    searchSaleChances()
})
//查询
function searchSaleChances(){
    $("#dg").datagrid("load",{
        createMan:$("#createMan").val(),
        customerName:$("#customerName").val(),
        createDate:$("#createDate").datebox("getValue"),
        state:$("#state").combobox("getValue")
    });
}
//打开添加对话框
function openAddAccountDialog(){
    //清除表格中的数据
    $("#fm").form("clear");
    $("#dlg").dialog("open").dialog("setTitle","添加营销机会记录")
}
//关闭添加对话框
function closeDialog(){
    $("#dlg").dialog("close")

}
//添加数据中的保存按钮
function saveAccount(){
    var id = $("#id").val();//通过获取id判断是添加还是修改，有id为修改，id为空为添加
    console.log("id"+id);
    var saleUrl = ctx+"/sale_chance/update"
    if(isEmpty(id)){
        saleUrl = ctx+"/sale_chance/insert"
    }
    $('#fm').form({
        url:saleUrl,
        onSubmit: function(params){
            params.createMan=$.cookie("trueName");
            return $("#fm").form("validate");//检查是否为空
        },
        success:function(data){
            data=JSON.parse(data);
            if(data.code==200){
                $.messager.alert("来自crm",data.msg,"info");
                closeDialog();//关闭修改对话框
                searchSaleChances()
            }else{
                $.messager.alert("来自crm",data.msg,"info");
            }
        }
    });
    $('#fm').submit();
}

/**
 * 修改营销机会
 */
function openModifyAccountDialog() {
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
     $("#dlg").dialog("open").dialog("setTitle","修改营销机会记录")
}

function deleteAccount() {
    var rows = $("#dg").datagrid("getSelections")
    if(rows.length==0){
        $.messager.alert("来自crm","请选择要删除的记录！","warning");
        return;
    }
    var params="id=";
    for (var i=0;i<rows.length;i++){
        if(i<rows.length-1){
            params=params+rows[i].id+"&id=";//rows[i].id第几行的id值
        }else {
            params=params+rows[i].id;
        }
    }

    $.messager.confirm("来自Crm","确定删除？",function (r) {
        if (r){
            $.ajax({
                url:ctx+"/sale_chance/delete",
                data:params,
                dataType:'json',
                success:function (data) {
                    if (data.code==200){
                        $.messager.alert("来自crm",data.msg,"info");
                        searchSaleChances();
                    }else {
                        $.messager.alert("来自crm",data.msg,"info");
                    }
                }
            })
        }
    })
}

