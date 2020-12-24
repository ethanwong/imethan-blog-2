/**
 * 自动将form表单封装成json对象
 */
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

function submitForm(form, callback) {
    $.ajax({
        type: form.method,
        url: form.action,
        contentType: 'application/json',
        data: JSON.stringify($(form).serializeObject()),
        success: function (result) {
            callback(result);
        },
        dataType: 'json'
    })
}