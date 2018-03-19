(function($) {
	$.fn.sidebarMenu = function(options, param){
		if (typeof options == 'string') {
			return $.fn.sidebarMenu.methods[options]($(this), param);
		}
        var _menu = $(this);
        _menu.addClass("sidebar-menu");
        _menu.attr("data-widget", "tree");
        var _defaultConfig = {};
        options = $.extend(_defaultConfig, options || {});
        if(options.url){
        	$.fn.sidebarMenu._loadData(_menu, options.url);
        }
        $.data(_menu[0],"options", options);
        _menu.on("click", "a", function(){
        	var opts = $.data(_menu[0],"options");
			var _a = $(this);
			var rel = _a.attr("rel");
			var _first = _menu.find("li.selected.first");
            _menu.find("li.second").removeClass("active");
			_menu.find("li.second").removeClass("selected");
			if(rel){
				var currSecondNode = _a.parent();
				var currFirstNode = _a.parent().parent().parent();
				if(opts.tabsId){
					$("#"+opts.tabsId).droptabs("addTab",{title:_a.attr("name"),url:rel,close:true});
				}
				if(_first.length==0){
                    currFirstNode.addClass("selected active");
                    currFirstNode.addClass("menu-open");
				}
				else if(_first.length>0 && currFirstNode.attr("name") != _first.attr("name")){
                    _first.removeClass("menu-open");
                    _first.removeClass("active");
                    _first.removeClass("selected");
                    currFirstNode.addClass("selected active");
                    currFirstNode.addClass("menu-open");
				}
                currSecondNode.addClass("selected active");
			}
			else{
                var currFirstNode = _a.parent();
                if(_first.length>0 && currFirstNode.attr("name") != _first.attr("name")){
                    _first.removeClass("menu-open");
                    _first.removeClass("active");
                    _first.removeClass("selected");
                }
                currFirstNode.addClass("selected active");
			}
		});
	};

	$.fn.sidebarMenu.methods = {
		loadData: function(jq, url){
			$.fn.sidebarMenu._loadData(jq, url);
		},
		active: function(jq, title){
			$.fn.sidebarMenu._active(jq, title);
		}
    };

	$.fn.sidebarMenu._loadData = function(jq, url){
		var html ='<li class="header">导航菜单</li>';
		$.ajax({
			url:url,
			method:'GET',
			dataType:'json',
			success:function(data){
				if(data.length>0){
					$.each(data, function(i, n) {
						html += '<li class="treeview first" icon="'+n.iconClass+'" name="'+n.name+'"><a href="#"><i class="fa '+n.iconClass+'"></i><span>'+n.name+'</span> <i class="fa fa-angle-left pull-right"></i></a>';
						if(n.children!=null){
							html += '<ul class="treeview-menu">';
					        $.each(n.children, function(j, o) {
					        	html += '<li class="second"><a href="#" rel="'+o.url+'" name="'+o.name+'"><i class="fa fa-circle-o"></i>'+o.name+'</a></li>';
					        });
					        html += '</ul>'
						}
				        html += '</li>';
					});
				}
				jq.html(html);
			},
			error:function(r){
				jq.html(html);
			}
		});
	};

	$.fn.sidebarMenu._active = function(jq, title){
		var _opts = $.data(jq[0],"options");
		if(_opts.currSecondNode){
			if(_opts.currSecondNode.find("a[name='"+title+"']").length>0){
				return;
			}
		}
		jq.find("li").removeClass("selected");
		jq.find("li").removeClass("active");
		if(_opts.currFirstNode){
			var _ul = _opts.currFirstNode.find("ul");
			_ul.css("display","none");
			//_ul.removeClass("menu-open");
		}
		var node = jq.find("a[name='"+title+"']");
		if(node.length>0){
			if(node.parent().parent().is(".treeview-menu")){
				_opts.currSecondNode = node.parent();
				_opts.currFirstNode = node.parent().parent().parent();
				_opts.currSecondNode.addClass("selected");
				_opts.currFirstNode.addClass("selected");
				_opts.currFirstNode.addClass("active");
				var _ul = _opts.currFirstNode.find("ul");
				_ul.css("display","block");
			}
		}
		else{
			_opts.currSecondNode = null;
			_opts.currFirstNode = null;
		}
	};

})(jQuery);