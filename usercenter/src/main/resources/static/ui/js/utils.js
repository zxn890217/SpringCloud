/**
 * Created by zxn on 2018/1/15.
 */
(function($){
    $.fn.extend({serializeJson: function(){
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            var name = this.name;
            var value = this.value;
            var paths = this.name.split(".");
            var len = paths.length;
            var obj = o;
            $.each(paths,function(i,e){
                if(i == len-1){
                    if (obj[e]) {
                        if (!obj[e].push) {
                            obj[e] = [obj[e]];
                        }
                        obj[e].push(value || '');
                    } else {
                        obj[e] = value || '';
                    }
                }else{
                    if(!obj[e]){
                        obj[e] = {};
                    }
                }
                obj = o[e];
            });
        });
        return o
    }});
})(jQuery);

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

utils = {};

utils.ajax = function(options){
    var configs = {type: "POST", dataType: "JSON", timeout:15000};
    var opts = $.extend(configs, options || {});
    opts.success = function(r){
        if(options.success)
            options.success(r);
    };
    return $.ajax(opts);
};

utils.bootstrapTable = function(options){
    var configs = {
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        sidePagination: "server",
        showRefresh: true,
        showToggle: true,
        locale: "zh-CN",
        cache: true,
        dataType: "json",
        pageSize:15,
        clickToSelect: true,
        pageList:[15, 25, 50, 100, 200]
    };
    options = $.extend(configs, options || {});
    var $table = $('#'+options.id);
    if(options.buttons){
        var toolBarId = options.id+"-toolbar";
        var $toolBar = $('<div id="'+toolBarId+'" class="columns columns-right btn-group pull-right"></div>');
        for(var i=0; i<options.buttons.length; i++){
            var btn = options.buttons[i];
            var $btn = $('<button type="button" class="btn btn-default">'+btn.text+'</button>');
            if(btn.id!=undefined){
                $btn.attr("id", btn.id);
            }
            if(btn.iconCls!=undefined){
                $btn.prepend('<i class="'+btn.iconCls+'"></i>');
            }
            if(btn.handler!=undefined){
                $btn.on('click',btn.handler);
            }
            $toolBar.append($btn);
        }
        $table.before($toolBar);
        if(!options.toolbar)
            options.toolbar = "#"+toolBarId;
    }
    $table.bootstrapTable(options);
    //列表搜索表单
    if(options.searchForm){
        var searchForm = $("#"+options.searchForm);
        //搜索表单
        var searchBtn = $('<button type="button" class="btn btn-block btn-default"><i class="fa fa-search"></i>搜索</button>');
        searchBtn.on('click', function(){
            var tbOpts = $table.bootstrapTable("getOptions");
            tbOpts.queryParams=function(params){
                var data = utils.getSearchFormData(searchForm);
                $.extend(data, params);
                return data;
            };
            tbOpts.pageNumber = 1;
            $table.bootstrapTable("refresh");
        });
        var searchBtnContainer = $("<div></div>");
        searchBtnContainer.append(searchBtn);
        searchForm.append(searchBtnContainer);
        //重置表单
        var resetBtn = $('<button type="button" class="btn btn-block btn-default"><i class="fa fa-undo"></i>重置</button>');
        resetBtn.on('click', function(){
            searchForm[0].reset();
            var tbOpts = $table.bootstrapTable("getOptions");
            tbOpts.queryParams=function(params){
                var data = utils.getSearchFormData(searchForm);
                $.extend(data, params);
                return data;
            };
            tbOpts.pageNumber = 1;
            $table.bootstrapTable("refresh");
        });
        var resetBtnContainer = $("<div></div>");
        resetBtnContainer.append(resetBtn);
        searchForm.append(resetBtnContainer);
    }
};

utils.getSearchFormData = function(searchForm){
    var serializeObj={};
    var array=searchForm.serializeArray();
    $(array).each(function(){
        if(serializeObj[this.name]){
            if($.isArray(serializeObj[this.name])){
                serializeObj[this.name].push(this.value);
            }else if(this.value!="" && this.value!=undefined){
                serializeObj[this.name]=[serializeObj[this.name],this.value];
            }
        }else if(this.value!="" && this.value!=undefined){
            serializeObj[this.name]=this.value;
        }
    });
    return serializeObj;
}

utils.dialog = function(options){
    var configs = {
        size: BootstrapDialog.SIZE_NORMAL,
        closable: false,
        //type: 'type-default',
        buttons: [{
            label: '关闭',
            action: function(dialog){
                dialog.close();
            }
        }]
    };

    options = $.extend(configs, options||{});
    if(options.closable==false && options.buttons && options.buttons.length>0){
        var containCloseBtn = false;
        for(var i=0; i<options.buttons.length; i++){
            var btn = options.buttons[0];
            if(btn.label.indexOf("关闭")>=0){
                containCloseBtn = true;
            }
        }
        if(!containCloseBtn){
            options.buttons.push({
                label: '关闭',
                action: function(dialog){
                    dialog.close();
                }
            });
        }
    }
    if(options.url){
        options.message = function(dialog){
            var $message = $('<p><i class="fa fa-spin fa-spinner"></i> Loading...</p>');
            $.ajax({
                type:"GET",
                url:options.url,
                success:function(msg){
                    $message.html(msg);
                },
                error:function(XMLHttpRequest, textStatus, thrownError){
                    $message.html(textStatus);
                }
            });
            return $message;
        };
    }
    BootstrapDialog.show(options);
};

utils.deleteAjax = function(options){
    var configs = {
        title: '确认',
        msg: '是否删除？',
        type: "post",
        dataType: "json",
        success: function(r){
            if(r.success){
                Lobibox.notify('info', {size: 'mini', title: '提示', msg: r.msg});
            }
            else{
                Lobibox.notify('warning', {size: 'mini', title: '失败', msg: r.msg});
            }
            if(options.tableId){
                $("#"+options.tableId).bootstrapTable("refresh");
            }
        },
        error: function(){
            Lobibox.notify('error', {width: $(window).width(), msg: '数据提交错误'});
        }
    };
    options = $.extend(configs, options || {});
    if(!options.data)
        options.data = {};
    options.data._method="DELETE";
    Lobibox.confirm({
        title: options.title,
        msg: options.msg,
        callback: function($this, type, ev){
            if(type == 'yes'){
                $.ajax(options);
            }
        }
    });
};

utils.websocket = function(options){
    utils.sock = new SockJS("/endpointChat");
    utils.stomp = Stomp.over(sock);
    utils.stomp.connect('guest', 'guest', function(frame) {
        /**  订阅了/user/queue/notifications 发送的消息,这里雨在控制器的 convertAndSendToUser 定义的地址保持一致, 
         *  这里多用了一个/user,并且这个user 是必须的,使用user 才会发送消息到指定的用户。 
         *  */
        utils.stomp.subscribe("/user/queue/notifications", options.handleNotification);
    });
};

utils.sendWebsocketMsg = function(options){
    utils.stomp.send(options.url, {}, JSON.stringify(options.msg));
}

/**数据字典常量访问方法*/
constants={};
constants.fn={
    /**根据类型和代码获取常量对象*/
    getByType:function(type){
        return constants.dicts[type];
    },
    /**根据类型和代码获取常量对象*/
    getByTypeAndCode:function(type, code){
        var arr = constants.dicts[type];
        if(arr){
            for(var i=0;i<arr.length;i++){
                if(arr[i].code == code)
                    return arr[i];
            }
        }
        return {};
    },
    /**根据类型和值获取常量对象*/
    getByTypeAndValue:function(type, value){
        var arr = constants.dicts[type];
        if(arr){
            for(var i=0;i<arr.length;i++){
                if(arr[i].value == value)
                    return arr[i];
            }
        }
        return {};
    }
};