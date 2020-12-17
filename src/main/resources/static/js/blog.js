var page = 1;
var size = 16;

function loadBlog(blogbox, pagebox, page, size, key, value) {
    $.get("/api/article/page/" + (page - 1) + "/" + size + "?" + key + "=" + value, function (result) {
        var data = result.data;
        var content = data.content;
        $(blogbox).html("");
        if (data.totalElements > 0) {
            $.each(content, function (i, item) {
                $(blogbox).append(blogItem(item.id, item.title, item.tag, item.createAt));
            })


        } else {
            $(blogbox).html("<h1>没有内容</h1>");
        }

        $(pagebox).pagination({
            pageCount: data.totalPages,
            current: page,
            jump: true,
            callback: function (api) {
                load(blogbox, api.getCurrent(), size, keyword);
            }
        });

    })
}

function blogItem(id, title, tag, createAt) {
    return "<li class='list-group-item list-group-item-articlelist'>"
        + " <a href='/blog/article/" + id + "' class='title'>" + title + "</a>"
        + " <small>" + createAt + "  <a href=''>" + tag + "</a> </small>"
        // + " <small>" + createAt + "</small>"
        + "</li>";
}
