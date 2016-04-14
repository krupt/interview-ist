'use strict';

var xhr,
    csrfHeader = {};

csrfHeader[$("meta[name='_csrf_header']").attr("content")] = $("meta[name='_csrf']").attr("content");

$('#new_payment').click(function() {
    showPaymentDialog();
});
$('#accept_payment, #decline_payment, #complete_payment').click(function() {
    var paymentId;
    try {
        paymentId = getSelected();
    } catch(e) {
        return;
    }
    var operation = $(this).prop('id').replace('_payment', '');
    showLoadingDialog();
    sendRequest({
        url: "payment/" + operation + "/" + paymentId,
        complete: function(data) {
            if (data.id) {
                removePaymentFromList();
                return successBlock('Платеж <b>#' + data.id + '</b> успешно <b>' + data.state.toLowerCase() + '</b>');
            } else {
                return errorBlock('Во время сохранения платежа произошла ошибка<br>Обратитесь к администратору для уточнения причин');
            }
        }
    });
});
$('#new_payment_data').submit(function(event) {
    hidePaymentDialog();
    showLoadingDialog();
    sendRequest({
        url: 'payment/add',
        data: $(this).serialize(),
        complete: function(data) {
            if (data.id) {
                var row = $('<tr class="success"/>');
                $('#payment_list > tbody').append(row);
                row.append($('<td />').append(data.id));
                row.append($('<td />').append(data.description));
                row.append($('<td />').append(new Date(data.createDate).toLocaleString()));
                row.append($('<td />').append(data.state));
                row.append($('<td />').append($('#current_user_description').text()));
                row.append($('<td />'));
                setTimeout(function() {
                    row.removeClass('success');
                }, 7000);
                return successBlock('Платеж успешно создан с номером <b>#' + data.id + '</b>');
            } else {
                return errorBlock('Во время сохранения платежа произошла ошибка<br>Обратитесь к администратору для уточнения причин');
            }
        }
    });
    clearPaymentDialog();
    event.preventDefault();
});
$('#loading_dialog').on('hide.bs.modal', function() {
    if (xhr)
        xhr.abort();
}).on('shown.bs.modal', function() {
    $(this).find('button').focus();
});
$('#payment_view').on('shown.bs.modal', function() {
    $(this).find('input').first().focus();
});

function getSelected() {
    var paymentId = $("input[name='selected_payment']:checked").val();
    if (!paymentId) {
        $.notify({
            message: 'Платеж не выбран<br>Пожалуйста, выберите платеж для продолжения'
        },{
            type: 'danger'
        });
        throw new Error('Платеж не выбран')
    }
    return paymentId;
}
function showPaymentDialog() {
    $('#payment_view').modal({backdrop: 'static'});
}
function hidePaymentDialog() {
    $('#payment_view').modal('hide');
}
function clearPaymentDialog() {
    $('#new_payment_data').trigger('reset');
}
function showLoadingDialog() {
    $('#loading_dialog').modal({backdrop: 'static'});
}
function sendRequest(requestParam) {
    var result;
    $('#loading_result').html('');
    xhr = $.ajax({
        headers: csrfHeader,
        url: requestParam.url,
        method: 'POST',
        dataType: 'json',
        data: requestParam.data,
        error: function(jqXHR, textStatus) {
            if (textStatus != 'abort') {
                result = {requestError: jqXHR.status, message: 'Возникла непредвиденная ошибка. Пожалуйста, проверьте свое сетевое подключение'};
            }
        },
        success: function(data) {
            result = data;
            // Для удобства разработки
            console.log("data", data);
        },
        complete: function() {
            $('#loading_img').hide();
            if (result.requestError) {
                $('#loading_result').html(errorBlock(result.requestError === 403 ? "У Вас недостаточно прав для совершения данной операции" : result.message));
            } else {
                $('#loading_result').html(requestParam.complete(result));
            }
        }
    });
}
function successBlock(text) {
    return '<div class="alert alert-success">' + text + '</div>';
}
function errorBlock(text) {
    return '<div class="alert alert-danger">' + text + '</div>';
}
function removePaymentFromList() {
    $("input:radio[name='selected_payment']:checked").parents('tr').remove();
    var payment_list = $("#payment_list > tbody");
    if (payment_list.find('tr').length === 0) {
        var columnCount = $("#payment_list > thead > tr td").length;
        payment_list.append($('<tr class="warning" align="center" />').append($('<td colspan="' + columnCount + '">Платежи не найдены</td>')));
    }
}
