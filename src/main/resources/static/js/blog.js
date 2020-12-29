var page = 1;
var size = 12;

function loadBlog(blogbox, pagebox, page, size, key, value) {
    $.get("/api/article/page/" + (page - 1) + "/" + size + "?" + key + "=" + value, function (result) {
        var data = result.data;
        var content = data.content;
        $(blogbox).html("");
        var blogItems = "";
        if (data.totalElements > 0) {
            $.each(content, function (i, item) {
                blogItems += blogItem(item.id, item.title, item.tag, item.createAt);
            })

            $(blogbox).append(blogItems);
        } else {
            $(blogbox).html("<h1>没有内容</h1>");
        }

        $(pagebox).pagination({
            pageCount: data.totalPages,
            current: page,
            jump: true,
            callback: function (api) {
                loadBlog(blogbox,pagebox, api.getCurrent(), size, key, value);
            }
        });

    })
}

function blogItem(id, title, tag, createAt) {
    return "<li class='list-group-item list-group-item-articlelist'>"
        + " <a href='/blog/article/" + id + "' class='title'>" + title + "</a>"
        + " <small><i class=\"fa fa-calendar-o\" aria-hidden=\"true\"></i> " + createAt + "   <i class=\"fa fa-bookmark-o\" aria-hidden=\"true\"></i> " + tag + " </small>"
        // + " <small>" + createAt + "</small>"
        + "</li>";
}

function loadChannel(channelBox){

    $.get("/api/channel/list", function (result) {
        let data = result.data;
        let items = "";
        $(channelBox).html("");
        if (data.length > 0) {
            $.each(data, function (i, item) {
                items += "<li class='list-group-item' id='"+item.id+"'>"+item.name+"</li>";
            })
            $(channelBox).append(items);
        } else {
            $(channelBox).html("<h1>没有栏目</h1>");
        }

        /**
         * 加载栏目内容
         */
        $(".channel-box li").each(function () {
            $(this).click(function () {
                NProgress.start();
                //移除全部选中
                $(".list-group li").each(function () {
                    $(this).removeClass("active");
                })
                //添加点击选中
                $(this).addClass("active");

                let id = $(this).attr("id");
                let name = $(this).html();

                loadBlog("#blog-box", '#pagination', page, size, 'channelId', id);

                NProgress.done();
            })
        })

    })
}
