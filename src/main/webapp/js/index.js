function userLogin() {
    //获取参数
    var userName = $("#userName").val();
    var userPwd = $("#userPwd").val();
    //判断参数是否为空
    if(isEmpty(userName)){
        alert("用户名不能为空！");
        return;
    }
    if(isEmpty(userPwd)){
        alert("密码不能为空！");
        return;
    }

    //ajax向后台传送参数
    var params = {};
    //传给后台的参数=前台获取的参数
    params.userName = userName;
    params.userPwd =userPwd;

    $.ajax({
        type:"post",
        //ctx代表项目的路径
        url:ctx+"/user/userLogin",
        data:params,
        dataType:"json",
        //回调函数，接收后台传过来的数据
        success:function(data){//data后台返回过来的数据
            //登录成功
            if(data.code==200){
                //跳转到首页，index页面
                var result = data.result;//获取后台传过来的结果
                //存cookie
                $.cookie("userName",result.userName);
                $.cookie("trueName",result.trueName);
                $.cookie("userId",result.userId);
                //                 //跳转到首页
                window.location.href="main";

            }else{
                //给出提示，登录失败
                alert(data.msg);
            }
        }
    });

}