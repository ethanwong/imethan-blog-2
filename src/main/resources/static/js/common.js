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

function submitForm(form, successCallback, errorCallback) {
    let serializeObject = $(form).serializeObject();

    let data = JSON.stringify(serializeObject);
    // console.log("submitForm data="+data);
    console.log("submitForm _csrf=" + serializeObject["_csrf"]);
    // console.log("submitForm form.method="+form.method);
    // console.log("submitForm form.action="+form.action);

    let token = $("meta[name='_csrf']").attr("content");
    console.log("submitForm _csrf=" + token);


    $.ajax({
        type: form.method,
        url: form.action,
        contentType: 'application/json',
        headers: {
            "X-CSRF-TOKEN": token
        },
        data: data,

        success: function (result) {
            successCallback(result);
        },
        error: function (result) {
            errorCallback(result);
        },
        dataType: 'json'
    })
}

function ajaxDelete(uri, successCallback, errorCallback) {
    let token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        type: "delete",
        url: uri,
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function (result) {
            successCallback(result);
        },
        error: function (result) {
            errorCallback(result);
        },
    });
}