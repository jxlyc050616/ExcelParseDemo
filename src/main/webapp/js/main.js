function openTab(text, url, iconCls) {
    if($("#tabs").tabs("exists", text)) {
        //如果已经存在，则使之处于选中的状态
        $("#tabs").tabs("select", text);
    } else {
        //如果不存在则新增加一个
        var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add", {
            title: text, //标题
            iconCls: iconCls, //图标
            closable: true, //可以关闭
            content: content //内容，内嵌一个 iframe！
        });
    }
}