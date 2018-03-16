(function($) {
    $.fn.droptabs = function(options, param){
		if (typeof options == 'string') {
			return $.fn.droptabs.methods[options]($(this), param);
		}
        var _droptabs = $(this);
        options = $.extend({
            onClose: function (jq) {//关闭后回调函数
            },
            onClick: function(jq, tab){//点击标签头响应事件
            	var _opts = $.data(jq[0],"options");
            	var title = tab.parent().attr("title");
            	if(title&&_opts.menuId)
            		$("#"+_opts.menuId).sidebarMenu("active", title);
            }
        }, options || {});

        _droptabs.on('click', 'a', function (e) {
            options.onClick(_droptabs, $(this));
        });
        $.data(_droptabs[0], "options", options);
        _droptabs.resize(function () {
        	$.fn.droptabs._drop(_droptabs);
        });
	};
	
	$.fn.droptabs._isExist = function (jq, title){
        if(jq.find("li[title='"+title+"']").length>0){
        	return true;
        }
        return false;
	};
	
	$.fn.droptabs._active = function (jq, title){
        if(jq.find("li[title='"+title+"']").length>0){
        	jq.find('.active').removeClass('active');
        	var tab = jq.find("li[title='"+title+"']")[0];
        	var _opts = $.data(tab,"options");
        	$("#tab_" + _opts.id).addClass('active');
            $("#" + _opts.id).addClass("active");
        }
	};
	
	$.fn.droptabs._add = function (jq, opts) {
    	jq.find('.active').removeClass('active');
        //如果TAB不存在，创建一个新的TAB
        var tab = jq.find("li[title='"+opts.title+"']")[0];
        if (!tab) {
            var id = 'tab_' + new Date().getTime();
            opts.id = id;
            //创建新TAB的title
            var title = $('<li>', {
                'role': 'presentation',
                'id': 'tab_' + id,
                'title': opts.title
            });
            var a = $('<a>', {
                'href': '#' + id,
                'aria-controls': id,
                'role': 'tab',
                'data-toggle': 'tab'
            }).html(opts.title);
            //是否允许关闭
            var closeHandler = null;
            if (opts.close) {
                closeHandler = $('<i class="close-tab fa fa-remove"></i>');
                a.append(closeHandler);
            }
            title.append(a);
            //创建新TAB的内容
            var content = $('<div>', {
                'class': 'tab-pane',
                'id': id,
                'role': 'tabpanel'
            });

            //是否指定TAB内容
            if (opts.content) {
                content.append(opts.content);
            } else if(opts.url){
            	var url = opts.url;
            	var timestamp = new Date();
    			timestamp= "_="+timestamp.getTime();
    			if(url.indexOf("?")>0)
    				url += "&"+timestamp;
    			else
    				url += "?"+timestamp;
                $.get(url, function (data) {
                    content.append(data);
                    if(opts.successFn){
                    	opts.successFn();
                    }
                });
            }
            //加入TABS
            jq.find('.nav-droptabs').append(title);
            jq.find(".tab-content").append(content);
            $.data(title[0],"options",opts);
            //激活TAB
            $("#tab_" + opts.id).addClass('active');
            $("#" + opts.id).addClass("active");
            //添加关闭事件
            if(closeHandler){
                closeHandler.on('click', function(){
                    var id = $(this).parent().attr("aria-controls");
                    $.fn.droptabs._close(jq, id);
                    var options = $.data(jq[0], "options");
                    options.onClose(jq);
                    return false;
                });
            }
        }
        else{
        	var _opts = $.data(tab,"options");
        	opts = $.extend(_opts, opts || {});
        	if(opts.reflash&&opts.url){
        		$.data(tab, "options", opts);
        		var content = $("#" + opts.id)
        		content.html('<div class="loading-icon"></div>');
        		var url = opts.url;
            	var timestamp = new Date();
    			timestamp= "_="+timestamp.getTime();
    			if(url.indexOf("?")>0)
    				url += "&"+timestamp;
    			else
    				url += "?"+timestamp;
                $.get(url, function (data) {
                    content.html(data);
                    if(opts.successFn){
                    	opts.successFn();
                    }
                });
        	}
            //激活TAB
            $("#tab_" + opts.id).addClass('active');
            $("#" + opts.id).addClass("active");
        }
        $.fn.droptabs._drop(jq);
    };

    $.fn.droptabs._close = function (jq, id) {
        //如果关闭的是当前激活的TAB，激活他的前一个TAB
    	var curr_tab = null;
        if (jq.find("li.active").attr('id') == "tab_" + id) {
        	curr_tab = $("#tab_" + id).prev();
        	curr_tab.addClass('active');
            $("#" + id).prev().addClass('active');
        }
        //关闭TAB
        $("#tab_" + id).remove();
        $("#" + id).remove();
        $.fn.droptabs._drop(jq);
        var _opts = $.data(jq[0], "options");
        if(curr_tab && curr_tab.length>0&&_opts.menuId){
            var title = curr_tab.attr("title");
        	if(title)
        		$("#"+_opts.menuId).sidebarMenu("active", title);
        }
    };

    $.fn.droptabs._drop = function (jq) {
        element = jq.find('.nav-droptabs');
        //创建下拉标签
        var dropdown = $('<li>', {
            'class': 'dropdown pull-right hide tabdrop'
        }).append(
            $('<a>', {
                'class': 'dropdown-toggle',
                'data-toggle': 'dropdown',
                'href': '#'
            }).append(
                $('<b>', {'class': 'fa fa-caret-square-o-down'})
            )
        ).append(
            $('<ul>', {'class': "dropdown-menu"})
        )

        //检测是否已增加
        if (!$('.tabdrop').html()) {
            dropdown.prependTo(element);
        } else {
            dropdown = element.find('.tabdrop');
        }
        //检测是否有下拉样式
        if (element.parent().is('.tabs-below')) {
            dropdown.addClass('dropup');
        }
        var collection = 0;

        //检查超过一行的标签页
        element.append(dropdown.find('li'))
            .find('>li')
            .not('.tabdrop')
            .each(function () {
	            if (this.offsetTop > 70) {
	                dropdown.find('ul').append($(this));
	                collection++;
	            }
            });
        
        //如果有超出的，显示下拉标签
        if (collection > 0) {
            dropdown.removeClass('hide');
            if (dropdown.find('.active').length == 1) {
                dropdown.addClass('active');
            } else {
                dropdown.removeClass('active');
            }
        } else {
            dropdown.addClass('hide');
        }
    };

	$.fn.droptabs.methods = {
		addTab: function(jq, opts){
			$.fn.droptabs._add(jq, opts);
		},
		closeTab: function(jq, title){
			_li = jq.find("li[title='"+title+"']")[0];
			if(_li){
				var opts = $.data(_li, "options");
				$.fn.droptabs._close(jq, opts.id);
			}
		},
		getSelectedTab: function(jq){
			return jq.find('.nav-droptabs .active')[0];
		},
		isExist: function(jq, title){
			return $.fn.droptabs._isExist(jq, title);
		},
		active: function(jq, title){
			return $.fn.droptabs._active(jq, title);
		}
    };
})(jQuery);