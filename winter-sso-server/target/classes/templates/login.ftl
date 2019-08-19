<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>统一认证中心</title>
</head>
<body class="hold-transition login-page">

<div class="login-box">
    <div class="login-logo">
        <a>SSO</a>
    </div>
    <form action="/login" method="post">
        <div class="login-box-body">
            <p class="login-box-msg">统一认证中心</p>
            <div class="form-group has-feedback">
                <input type="text" name="username" class="form-control" placeholder="Please input username." value="user" maxlength="50" >
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="password" class="form-control" placeholder="Please input password." value="123456" maxlength="50" >
            </div>

            <#if error?exists>
                <p style="color: red;">${error}</p>
            </#if>

            <div class="row">
                <div class="col-xs-4">
                    <input type="hidden" name="redirect_url" value="${redirect_url!''}" />
                    <button type="submit" class="btn btn-primary btn-block btn-flat">Login</button>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>