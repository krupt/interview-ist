<!DOCTYPE html>
<html>
<head>
	<title>IST - Главная страница</title>
<#include "head.ftl">
</head>
<body>
<@headerPanel />
<div class="container-fluid">
    <ul class="list-group col-lg-2">
        <li class="list-group-item">
            <i class="fa fa-lg fa-fw fa-credit-card text-success"></i>
            <a href="/payment/list">Платежи</a>
        </li>
        <li class="list-group-item">
            <i class="fa fa-lg fa-fw fa-cogs text-danger"></i>
            <a href="admin">Администрирование</a>
        </li>
        <li class="list-group-item">Еще что-нибудь :)</li>
    </ul>
</div>
<div class="container">
    <@security.authorize access="hasRole('ADMIN')">
        <div class="well-lg">
            <h4><i class="fa fa-5x fa-unlock text-warning"></i>Вы вошли в систему с полными правами</h4>
        </div>
    </@security.authorize>
</div>
</body>
</html>
