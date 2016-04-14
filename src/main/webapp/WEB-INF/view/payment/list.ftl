<!DOCTYPE html>
<html>
<head>
    <title>IST - Список платежей</title>
<#include "../head.ftl">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
</head>
<body>
<@headerPanel />
<div class="container">
    <div class="fixed-button-tool">
        <@security.authorize access="hasRole('USER')">
            <button id="new_payment" type="button" class="btn btn-success"><i class="fa fa-lg fa-plus"></i>&nbsp;Создать платеж</button>
        </@security.authorize>
        <@security.authorize access="hasRole('MANAGER')">
            <button id="accept_payment" type="button" class="btn btn-success"><i class="fa fa-lg fa-check"></i>&nbsp;Подтвердить платеж</button>
            <button id="decline_payment" type="button" class="btn btn-danger"><i class="fa fa-lg fa-close"></i>&nbsp;Отклонить платеж</button>
        </@security.authorize>
        <@security.authorize access="hasRole('CHIEF')">
            <button id="complete_payment" type="button" class="btn btn-success"><i class="fa fa-lg fa-flag-checkered"></i>&nbsp;Завершить платеж</button>
        </@security.authorize>
    </div>
    <table id="payment_list" class="table table-striped">
        <thead>
            <tr>
                <@security.authorize access="hasAnyRole('MANAGER', 'CHIEF')" var="canSelect">
                    <th><i class="fa fa-check-square-o"></i></th>
                </@security.authorize>
                <th>#</th>
                <th>Описание</th>
                <th>Дата создания</th>
                <th>Статус</th>
                <th>Создал</th>
                <th>Подтвердил</th>
            </tr>
        </thead>
        <tbody>
            <#list payments as payment>
                <tr>
                    <#if canSelect>
                        <td><input type="radio" name="selected_payment" value="${payment.id}"></td>
                    </#if>
                    <td>${payment.id}</td>
                    <td>${payment.description}</td>
                    <td>${payment.createDate}</td>
                    <td>${payment.state.description}</td>
                    <td>${(payment.createdUser.employee.description)!}</td>
                    <td>${(payment.acceptedUser.employee.description)!}</td>
                </tr>
            <#else>
                <tr class="warning" align="center">
                    <td colspan="${canSelect?then('7', '6')}">Платежи не найдены</td>
                </tr>
            </#list>
        </tbody>
    </table>
</div>
<div id="payment_view" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Закрыть"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Создание нового платежа</h4>
            </div>
            <div class="modal-body">
                <form id="new_payment_data">
                    <div class="form-group">
                        <label for="payment_description" class="col-sm-2 control-label">Название</label>
                        <input type="text" class="form-control" id="payment_description" name="description" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" form="new_payment_data">Сохранить</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>
<div id="loading_dialog" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div id="loading_img">
                    <img align="middle" src="/resources/img/load.gif" height="46">
                    Пожалуйста, подождите. Идет сохранение
                </div>
                <div id="loading_result">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>
</body>
<script src="/resources/js/payment/payment.js"></script>
</html>
