<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>登录页面</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/1.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/verify.css">
</head>
<body> 
<div class="page-header">
    <h1>用户登录</h1>
</div>
<form id="user" action="" method="">
    <div class="form-group">
        <span style="color:red;margin-left: 50px;">${loginInfo}</span>
    </div>
    <div class="form-group">
        <label for="username">用户名:</label>
        <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名" />
    </div>
    <div class="form-group">
        <label for="password">密码:</label>
        <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" />
    </div>

    <div class="form-group">
        <label for="loginCode">验证码:</label>
        <div id="mpanel3" style="margin-top: 5px;margin-left: 50px;"></div>
        <input type="text" class="form-control" id="loginCode" name="loginCode" placeholder="请输入结果" />
    </div>

    <button type="button" id="check-btn2" class="btn btn-success">登录</button>
    &nbsp;&nbsp;&nbsp;
    <button onclick="location.href = '../../../'" class="btn btn-primary">注册账号</button>
</form>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/verify.js" ></script>
<%--<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/bootstrap.js"></script>--%>
<script type="text/javascript">
	//验证码..
    $('#mpanel3').codeVerify({
        type : 2,
        figure : 100,	//位数，仅在type=2时生效
        arith : 0,	//算法，支持加减乘，不填为随机，仅在type=2时生效
        width : '200px',
        height : '50px',
        fontSize : '30px',
        btnId : 'check-btn2',
        success : function() {
            //-------------执行登录.---------------------
           var data = $("#user").serialize();
            $.ajax({
                url: "${pageContext.request.contextPath}/user/check-username",            // 请求路径
                // {"username": "admin","age": 23}
                data: "{\"username\": \"" + username + "\"}",           // 请求参数
                type: "post",           // 请求方式
                dataType: "json",       // 预期服务器响应的数据格式.
                success: function(result){// 成功的回调函数
                    if(result.code == 0){
                        // 登录成功
                         location.href = "${pageContext.request.contextPath }/item/list";
                        }else{
                        //登录失败，将响应的json中的msg给用户提示
                        $("#logInfo").html(result.msg);
                    }
                },
                error: function(result) {
                // 出现异常的回调函数.
                alert("服务器爆炸!!");
                },
                contentType: "application/json;charset=utf-8"
            });
            //------------------------------------------
        },
        error : function() {
            alert('验证码不匹配！');
        }
    });
</script>
</html>