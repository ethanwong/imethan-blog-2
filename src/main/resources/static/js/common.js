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

/**
 * 加载Gitalk评论组件
 * @param boxId
 * @param issue
 */
function loadGitalk(boxId,issue){
    const gitalk = new Gitalk({
        clientID: 'a68e03c29c3ae6f39b67',
        clientSecret: '1ce647c0c4f0c3d1e35863f54528bfda786b7081',
        repo: 'imethan-gitalk',      // The repository of store comments,
        owner: 'ethanwong',
        admin: ['ethanwong'],
        id: issue,      // Ensure uniqueness and length less than 50
        distractionFreeMode: false,  // Facebook-like distraction free mode
    })

    gitalk.render(boxId)
}