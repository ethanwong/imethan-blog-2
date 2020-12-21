function loadTag(box) {

    $.get("/api/article/tag", function (result) {
        if (result.isSuccess) {
            $(box).html("");
            var content = result.data;
            if (content.length > 0) {
                $.each(content, function (i, item) {
                    var tagItem = tagBoxA(item.name, item.id);
                    $(box).append(tagItem);
                    var id = item.id;
                    $("#" + id).on("click", function () {
                        console.log('用on绑定事件' + id)
                        NProgress.start();
                        loadBlog("#blog-box", '#pagination', page, size, 'tag', item.name);
                        NProgress.done();
                    })
                })
            }else{
                $(box).html("<h1>没有标签</h1>");
            }

        }
    });

}

function tagBoxA(name, id) {
    return "<a id=\"" + id + "\" class=\"badge bg-" + randomClazz(id) + "\" href=\"javascript:;\" role=\"button\">" + name + "</a>";
};

var lastClazzIndex;

function randomClazz(id) {
    var clazz = ['primary', 'secondary', 'success', 'danger', 'warning', 'info', 'dark'];
    var clazzIndex = parseInt(clazz.length * ("0." + getHashCode(id)));
    if (lastClazzIndex == clazzIndex) {
        var temp = clazzIndex - 1
        if (temp < 0) {
            temp = clazzIndex + 1
        }
        clazzIndex = temp;
    }
    lastClazzIndex = clazzIndex;
    return clazz[clazzIndex];
};

function getHashCode(str) {
    var hash = 1315423911, i, ch;
    for (i = str.length - 1; i >= 0; i--) {
        ch = str.charCodeAt(i);
        hash ^= ((hash << 5) + ch + (hash >> 2));
    }
    return (hash & 0x7FFFFFFF);
}