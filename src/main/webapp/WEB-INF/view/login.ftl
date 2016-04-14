<!DOCTYPE html>
<html>
<head>
    <title>IST - Авторизация</title>
<#include "head.ftl">
</head>
<body style="padding: 140px 0 0;">
<@headerPanel />
<div class="container">
    <#if error??>
        <div class="alert alert-danger col-sm-offset-3 col-sm-8">
        ${error}
        </div>
    </#if>
    <form class="form-horizontal col-sm-offset-3" method="post" role="form">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">Имя пользователя</label>
            <div class="col-sm-6 input-img">
                <input type="text" class="form-control" id="username" name="username" required autofocus>
                <i class="fa fa-user-secret text-primary"></i>
            </div>
        </div>
        <@input.passwordField "password" "Пароль" 6 />
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <@input.checkBox "remember-me" "Запомнить меня" />
            </div>
        </div>
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button id="authBtn" class="btn btn-lg btn-success"><i class="fa fa-sign-in fa-lg fa-fw"></i>Войти</button>
                <img id="authLoad" src="/resources/img/load.gif" height="46" class="hidden">
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    $('form').submit(function() {
        $('#authBtn').hide();
        $('#authLoad').removeClass('hidden');
    });
</script>
</body>
</html>
