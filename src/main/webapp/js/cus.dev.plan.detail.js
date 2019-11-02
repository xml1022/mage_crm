//可编辑表格，一进来就要加载
$(function () {
    var devresult = $("#devResult").val();
    if(devresult==2||devresult==3){//开发成功，结束计划
        $("#toolbar").remove();
    }
    //可编辑表格
    $("#dg").edatagrid({
        //分页查询
        url:ctx+"/cus_dev_plan/queryCusDevPlans?saleChanceId="+$("#saleChanceId").val(),
        //保存计划
        saveUrl:ctx+"/cus_dev_plan/insert?saleChanceId="+$("#saleChanceId").val(),
        //修改计划
        updateUrl:ctx+"/cus_dev_plan/update?saleChanceId="+$("#saleChanceId").val()
    })
});

//保存行
function saveCusDevPlan() {
    $("#dg").edatagrid("saveRow")
    $("#dg").edatagrid("load")

}
//更新行
function updateCusDevPlan(){
    $("#dg").edatagrid("saveRow")
}

/**
 * 删除行
 */
function delCusDevPlan(){
    var rows = $("#dg").datagrid("getSelected")
    if(rows==0){
        $.messager.alert("来自crm","请选择要删除的数据！","warning");
        return;
    }
    $.messager.confirm("来自crm","确定删除吗?",function (r) {
        if(r){
            $.ajax({
                type:"post",
                data:"id="+rows.id,
                dataType:"json",
                url:ctx+"/cus_dev_plan/delete",
                success:function (data) {
                    if(data.code==200){
                        $.messager.alert("来自crm",data.msg,"info")
                        $("#dg").edatagrid("load")
                    }else{
                        $.messager.alert("来自crm",data.msg,"info")
                    }
                }
            });
        }
    })
    
}
//撤销行
function updateSaleChanceDevResult(devResult){
    $.messager.confirm("来自crm","确定修改吗？",function(r){
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/sale_chance/updateSaleChanceDevResult",
                data:"devResult"+devResult+"&saleChanceId="+ $("#saleChanceId").val(),
                dataType:"json",
                success:function(data){
                    $.messager.alert("来自crm",data.msg,"info")
                    $.messager.alert("来自crm",data.msg,"info");
                    if(data.code==200){
                        $("#toolbar").remove();
                    }
                }
            })
        }
    })
}
