function searchRoles(){
    $("#dg").datagrid("load",{
        roleName:$('#roleName').val()
    })
}

function openAddRoleDialog() {
    $("#fm").form("clear");
    $("#dlg").dialog("open").dialog("setTitle","添加角色记录")
}

function closeDialog(){
    $("#dlg").dialog("close");
}

function saveOrUpdateRole() {
    var id = $("#id").val();
    var url = ctx+"/role/update"
    if(isEmpty(id)){
        url:ctx+"/role/insert"
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function(){
            return $("#fm").form("validate")
        },
        success:function(data){
            data=JSON.parse(data);
            if(data.code==200){
                $.messager.alert("来自crm",data.msg,"info")
                closeDialog()
                $("#dg").datagrid("load")
            }else{
                $.messager.alert("来自crm",data.msg,"info")
                $("#fm").form("clear");
            }

        }
    })

}

function openModifyRoleDialog() {
    var row = $("#dg").datagrid("getSelections")
    if(row.length==0){
        $.messager.alert("来自crm","请选择要修改的行","warning")
        return;
    }
    if(row.length>1){
        $.messager.alert("来自crm","请选择一条要修改的行","warning")
        return;
    }
    $("#fm").form("load",row[0])
    $("#dlg").dialog("open").dialog("setTitle","修改角色记录")
}

function deleteRole() {
    var row = $("#dg").datagrid("getSelections")
    if(row.length==0){
        $.messager.alert("来自crm","请选择要修改的行","warning")
        return;
    }
    if(row.length>1){
        $.messager.alert("来自crm","请选择一条要修改的行","warning")
        return;
    }
    //确认框
    $.messager.confirm("来自crm","确认删除吗？",function(r){
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/role/delete",
                data:"id="+row[0].id,
                dataType:"json",
                success:function(data){
                    if(data.code==200){
                        $.messager.alert("来自crm","角色删除成功","info")
                        closeDialog()
                        $("#dg").datagrid("load")
                    }else{
                        $.messager.alert("来自crm","角色删除失败","info")
                    }
            }
            })
        }
    })
}

function openRelatePermissionDlg(){
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择角色进行授权!","info");
        return;
    }
    //获取用户角色id
    $("#rid").val(rows[0].id)
    //加载ztree
    loadZtreeSet()
    $("#dlg02").dialog("open")
}

function closeDialog02() {
    $("#dlg02").dialog("close")
}
var ztreeObj;
function loadZtreeSet(){
    $.ajax({
        type:"post",
        url:ctx+"/module/queryAllsModuleDtos",
        data:"rid="+$("#rid").val(),
        dataType:"json",
        success:function(data){
            // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
            var setting = {
                //使用简单的树
                data: {
                    simpleData: {
                        enable: true,

                    }
                },
                //设置显示 checkbox / radio
                check: {
                    enable: true
                },
                //用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
                callback: {
                    onCheck: zTreeOnCheck
                }
            }
            // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
            var zNodes =data;
            ztreeObj= $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        }

    });
}

function zTreeOnCheck() {
    var znodes=ztreeObj.getCheckedNodes(true);
    var moduleIds="moduleIds=";
    if(znodes.length>0){
        for(var i=0;i<znodes.length;i++){
            if(i<=znodes.length-2){//
                moduleIds=moduleIds+znodes[i].id+"&moduleIds=";
            }else{
                moduleIds=moduleIds+znodes[i].id;
            }
        }
    }
    console.log(moduleIds)
    //将前台的值设置为moduleIds,回调函数
    $("#moduleIds").val(moduleIds);
}

//添加权限
function addPermission(){
    $.ajax({
        type:"post",
        url:ctx+"/role/addPermission",
        data:"rid="+$("#rid").val+"&"+"moduleIds="+$("#moduleIds").val(),
        dataType:"json",
        success:function(data){
            if(data.code==200){
                $.messager.alert("来自crm",data.msg,"info")
                $("#moduleIds").val("")
                $("#rid").val("")
                closeDialog02()
            }else{
                $.messager.alert("来自crm",data.msg,"error")
            }
        }
    })
}






