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
function loadGitalk(boxId, issue) {
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

/**
 * 从josn中检索出符合key条件的信息
 * @param key
 * @param json
 * @param jsonArray
 *
 * let jsonArray = [];
 * searchJsonKey("java.home", json, jsonArray)
 * console.log("java.runtime.name=" + JSON.stringify(jsonArray));
 *
 * json格式说明
 * var json1 = {"data":[{"name":"zs","age":"10"}]};
 * var json2 = {"name":"zs","age":"10"};
 */
window.searchJsonKey = function (key, json, jsonArray) {

    $.each(json, function (i, item) {
        console.log("key=" + key + " i=" + i + " -item=" + JSON.stringify(item))
        if (item instanceof Array) {
            //队列处理
            searchJsonKey(key, item, jsonArray)
        } else if (isJson(item)) {
            //json处理
            searchJsonKey(key, item, jsonArray)
            if (i == key) {
                jsonArray.push(item);
            }
        }else{
            //字符串处理
            if (i == key) {
                jsonArray.push(item);
            }
        }
    })
}

function getJsonLength(json) {
    let jsonLength = 0;
    for (let item in json) {
        jsonLength++;
    }
    return jsonLength;
}

function searchJson(key, json, jsonArray) {
    $.each(json, function (i, item) {
        console.log("i=" + i + " -item=" + item + " -key=" + key);
        if (key = i) {
            jsonArray.push(item);
        }
    })
}


/**
 * 判断对象是否是json
 * @param obj
 * @returns {boolean}
 */
window.isJson = function (obj) {
    return typeof (obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
}