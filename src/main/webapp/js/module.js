function formatterGrade(grade) {
    if(grade==0){
        return "根级"
    }else if(grade==1){
        return "一级"
    }else if(grade==2){
        return "二级"
    }else{
        return "error"
    }
}

function searchModules(){
    $("#dg").datagrid("load",{
        moduleName:$("#moduleName").val(),
        optValue:$("#optValue").val(),
        parentModuleName:$("#parentModuleName").val
    })
}

function openAddModuleDialog(){
    $("#dlg").dialog("open").dialog("setTitle","添加模块管理")
}
function closeDialog() {
    $("#dlg").dialog("close")
}
function initFormData(){
    $("#fm").form("clear");
}
$(function() {
    $("#dlg").dialog({
        onClose: function () {
            initFormData();
        }
    });
    $("#parentMenu").hide();
    $("#grade").combobox({
        onChange: function (grade) {
            if (grade == 1 || grade == 2) {
                $("#parentMenu").show();
            }
            if (grade == 0) {
                $("#parentMenu").hide();
            }
            loadParentModules(grade - 1);
        }
    });
});
function loadParentModules(grade){
    $("#parentId").combobox("clear");
    $("#parentId").combobox({
        url:ctx+"/module/queryModulesByGrade?grade="+grade,
        valueField:'id',
        textField:'moduleName'
    });
}

function saveOrUpdateModule(){
    var id=$("#id").val();
    var url=ctx+"/module/insert";
    if(!isEmpty(id)){
        url=ctx+"/module/update";
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
                searchModules();
            }else{
                $.messager.alert("来自crm系统",data.msg,"info")
            }
        }
    });
}

function openModifyModuleDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择一条记录进行更新!");
        return ;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","只能选择一条记录进行更新!");
        return ;
    }
    /**
     * 更新操作
     */
    $("#fm").form("load",rows[0]);//填充表单数据 数据回显
    var grade=rows[0].grade;
    if(grade!=0){
        loadParentModules(grade-1);
        $("#parentMenu").show();
        $("#parentId").combobox("setValue",rows[0].parentId);
    }else{
        $("#grade").combobox("setValue",grade);
    }
    $("#dlg").dialog("open").dialog("setTitle","修改模块");
}



