var page = 1;
var size = 12;

function loadBlog(blogbox, pagebox, page, size, key, value) {
    $.get("/api/article/page/" + (page - 1) + "/" + size + "?" + key + "=" + value, function (result) {
        var data = result.data;
        var content = data.content;
        $(blogbox).html("");

        console.log("key=" + key + " value=" + value);

        if (key == 'status') {
            if (value == "1") {
                $("#blog-header-name").html("Draft");
                $("#blog-header-intro").html("&nbsp;草稿箱");
            }
            if (value == "2") {
                $("#blog-header-name").html("Recycle");
                $("#blog-header-intro").html("&nbsp;回收站");
            }
            if (value == "3") {
                $("#blog-header-name").html("Inner");
                $("#blog-header-intro").html("&nbsp;内置内容");
            }
        }else{
            $("#blog-header-name").html("My Blog");
            $("#blog-header-intro").html("&nbsp;我的工作和学习笔记");
        }

        var blogItems = "";
        if (data.totalElements > 0) {
            $.each(content, function (i, item) {
                blogItems += blogItem(item.id, item.title, item.tag, item.createAt, item.channelName);
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
                loadBlog(blogbox, pagebox, api.getCurrent(), size, key, value);
            }
        });

    })
}

function blogItem(id, title, tag, createAt, channelName) {
    return "<li class='list-group-item list-group-item-articlelist'>"
        + " <a href='/blog/article/" + id + "' class='title'>" + title + "</a>"
        + " <small style='color: #a8afb7'><i class=\"fa fa-calendar-o\" aria-hidden=\"true\"> " + createAt + "</i>   <i class=\"fa fa-bookmark-o\" aria-hidden=\"true\"> " + tag + "</i>"
        + "<i class=\"fa fa-link\" aria-hidden=\"true\"> " + channelName + "</i>  </small>"
        // + " <small>" + createAt + "</small>"
        + "</li>";
}

function loadChannel(channelBox) {

    $.get("/api/channel/list", function (result) {
        let data = result.data;
        let items = "";
        $(channelBox).html("");
        if (data.length > 0) {
            $.each(data, function (i, item) {
                items += "<li class='list-group-item' id='" + item.id + "'>" + item.name + "</li>";
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
