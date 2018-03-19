(function ($) {
    "use strict";
    
    $.fn.treegridData = function (options, param) {
        //如果是调用方法
        if (typeof options == 'string') {
            return $.fn.treegridData.methods[options](this, param);
        }
        
        //如果是初始化组件
        options = $.extend({}, $.fn.treegridData.defaults, options || {});
        
        var target = $(this);
        //构造布局
        var layout = $('<div class="bootstrap-table"><div class="fixed-table-toolbar"><div class="bs-bars pull-left" style="margin:20px 5px;"></div><div class="columns columns-right btn-group pull-right"></div></div><div class="fixed-table-container"></div></div>');
        layout.insertBefore(target);
        target.appendTo(layout.find('.fixed-table-container'));
        var toolbar = layout.find('.fixed-table-toolbar');
        if(options.buttons){
        	var leftBars = layout.find('.fixed-table-toolbar .bs-bars.pull-left');
        	$.each(options.buttons, function(index, item){
        		var btn = $('<button type="button" class="btn btn-default"><i class="'+item.iconCls+'"></i>'+item.text+'</button>');
        		leftBars.append(btn);
        		btn.off('click').on('click', item.handler);
        		
        	});
        }
        if(options.refreshBtn){
            var rightContainer = layout.find('.fixed-table-toolbar .btn-group.pull-right');
            var refreshBtn = $('<button class="btn btn-default" type="button" name="refresh" aria-label="refresh" title="刷新"><i class="glyphicon glyphicon-refresh icon-refresh"></i></button>');
            refreshBtn.off('click').on('click', function(e){
            	target.treegridData('reload');
            });
            rightContainer.append(refreshBtn);
        }
        //记住配置
        target.data("options", options);
        target.addClass('table');
        if (options.url) {
        	$.fn.treegridData._remote(target, options);
        }
        else if(options.data){
        	$.fn.treegridData._loadData(target, options, options.data);
        }
        return target;
    };

    $.fn.treegridData.methods = {
        getAllNodes: function (target, data) {
            return target.treegrid('getAllNodes');
        },
        reload: function(_this){
        	var target = $(_this);
        	var options = target.data("options");
        	if(options.url){
        		target.empty();
        		$.fn.treegridData._remote(target, options);
        	}
        },
        getSelections: function(_this){
        	return $.fn.treegridData._getSelected(_this);
        },
        getChecked: function(_this){
        	return $.fn.treegridData._getChecked(_this);
        }
    };

    $.fn.treegridData.defaults = {
        id: 'Id',
        parentColumn: 'ParentId',
        data: [],    //构造table的数据集合
        type: "GET", //请求数据的ajax类型
        url: null,   //请求数据的ajax的url
        ajaxParams: {}, //请求数据的ajax的data属性
        expandColumn: null,//在哪一列上面显示展开按钮
        expandAll: true,  //是否全部展开
        striped: false,   //是否各行渐变色
        bordered: false,  //是否显示边框
        singleSelect: false,
        checkOnSelect: true,
        selectOnCheck: true,
        columns: [],
        expanderExpandedClass: 'glyphicon glyphicon-chevron-down',//展开的按钮的图标
        expanderCollapsedClass: 'glyphicon glyphicon-chevron-right'//缩起的按钮的图标
        
    };
    
    $.fn.treegridData._appendChildNodes = function (data, parentNode, tbody, options) {
        $.each(data, function (i, item) {
            var parentVal = null;
            if(options.parentColumn.indexOf(".")>0){
                parentVal = item;
                var arr = options.parentColumn.split(".");
                $.each(arr, function(i, n){
                    if(parentVal == null || parentVal==undefined || parentVal.constructor != Object){
                        return false;
                    }
                    parentVal = parentVal[n];
                });
            }
            else{
                parentVal = item[options.parentColumn];
            }
            if (parentVal == parentNode[options.id]) {
                var tr = $('<tr></tr>');
                tr.addClass('treegrid-' + item[options.id]);
                tr.addClass('treegrid-parent-' + parentVal);
                $.each(options.columns, function (index, column) {
                    var td = $('<td></td>');
                    if(column.checkbox){
                    	td.html('<input type="checkbox" class="treegrid-checkbox" name="'+column.field+'" value="'+item[options.id]+'">');
                    }
                    else if(column.formatter){
                    	var formatVal = column.formatter(item[column.field], item, i);
                    	if(formatVal!=null && formatVal!=undefined)
                    		td.text(formatVal);
                    	else
                    		td.text('-');
                    }
                    else
                    	td.text(item[column.field]);
                    tr.append(td);
                });
                tbody.append(tr);
                $.fn.treegridData._appendChildNodes(data, item, tbody, options);
                
            }
        });
    };
    
    $.fn.treegridData._getRootNodes = function(data, options){
    	var result = [];
        $.each(data, function (index, item) {
            var parentVal = null;
            if(options.parentColumn.indexOf(".")>0){
                parentVal = item;
                var arr = options.parentColumn.split(".");
                $.each(arr, function(i, n){
                    if(parentVal == null || parentVal==undefined || parentVal.constructor != Object){
                        return false;
                    }
                    parentVal = parentVal[n];
                });
            }
            else{
                parentVal = item[options.parentColumn];
            }
            if (parentVal == null || parentVal==undefined || parentVal=="") {
                result.push(item);
            }
            else{
                var isMatch = false;
                $.each(data, function (i, n){
                    if(index != i && n[options.id] == parentVal){
                        isMatch = true;
                        return false;
                    }
                });
                if(!isMatch){
                    result.push(item);
                }
            }
        });
        return result;
    }
    
    $.fn.treegridData._loadData = function(target, options, data){
    	target.data("data", data);
    	if(options.onLoadSuccess){
    		options.onLoadSuccess(data);
    	}
        //构造表头
        var thr = $('<tr></tr>');
        $.each(options.columns, function (i, item) {
            var th = $('<th style="padding:10px;"></th>');
            if(item.checkbox){
            	th.css("width","36px");
            	th.html('<div style="width: 16px;"></div>');
            }
            else
            	th.text(item.title);
            thr.append(th);
        });
        var thead = $('<thead></thead>');
        thead.append(thr);
        target.append(thead);
        //构造表体
        var tbody = $('<tbody></tbody>');
        var rootNode = $.fn.treegridData._getRootNodes(data, options);
        $.each(rootNode, function (i, item) {
            var tr = $('<tr></tr>');
            tr.addClass('treegrid-' + item[options.id]);
            $.each(options.columns, function (index, column) {
                var td = $('<td></td>');
                if(column.checkbox){
                	td.html('<input type="checkbox" class="treegrid-checkbox" name="'+column.field+'" value="'+item[options.id]+'">');
                }
                else if(column.formatter){
                	var formatVal = column.formatter(item[column.field], item, i);
                	if(formatVal!=null && formatVal!=undefined)
                		td.text(formatVal);
                	else
                		td.text('-');
                }
                else
                	td.text(item[column.field]);
                tr.append(td);
            });
            tbody.append(tr);
            $.fn.treegridData._appendChildNodes(data, item, tbody, options);
        });
        target.append(tbody);
        target.treegrid(options);
        if (!options.expandAll) {
            target.treegrid('collapseAll');
        }
        
        //点击行事件
        target.find('>tbody>tr').off('click').on('click', function (e) {
        	var tr = $(this);
        	var selected = tr.hasClass("selected");
        	if(selected){
        		$.fn.treegridData._unSelect(target, tr, options);
        	}
        	else{
        		$.fn.treegridData._select(target, tr, options);
        	}
        });
        //勾选checkbox事件
        target.find('tr td .treegrid-checkbox').off('click').on('click', function (e) {
        	e.stopPropagation();
        	var tr = $(this).parent().parent();
        	var cb = $(this);
        	if(this.checked){
        		$.fn.treegridData._check(target, tr, options);
        	}
        	else{
        		$.fn.treegridData._unCheck(target, tr, options);
        	}
        });
    };
    
    $.fn.treegridData._check = function(treegrid, target, options){
    	if(options.checkOnSelect){
    		$.fn.treegridData._select(treegrid, target, options);
    	}
    	else{
    		var checkbox = target.find(".treegrid-checkbox");
    		if(checkbox.length>0)
    			checkbox[0].checked = true;
    	}
    };
    
    $.fn.treegridData._unCheck = function(treegrid, target, options){
    	if(options.checkOnSelect){
    		$.fn.treegridData._unSelect(treegrid, target, options);
    	}
    	else{
    		var checkbox = target.find(".treegrid-checkbox");
    		if(checkbox.length>0)
    			checkbox[0].checked = false;
    	}
    };
    
    $.fn.treegridData._select = function(treegrid, target, options){
    	var id = $.fn.treegridData._getIDByClass(target);
    	var clazz = "treegrid-"+id;
    	if(options.singleSelect){//单选
    		var selected = treegrid.find("tr.selected");
    		$.each(selected, function(i, n){
    			$.fn.treegridData._unSelect(treegrid, $(n), options);
    		});
    	}
    	//选中行
    	target.addClass("selected");
    	if(options.selectOnCheck){
    		var checkbox = target.find(".treegrid-checkbox");
    		if(checkbox.length>0)
    			checkbox[0].checked = true;
    	}
    };
    $.fn.treegridData._unSelect = function(treegrid, target, options){
    	target.removeClass("selected");
    	if(options.selectOnCheck){
    		var checkbox = target.find(".treegrid-checkbox");
    		if(checkbox.length>0)
    			checkbox[0].checked = false;
    	}
    };
    $.fn.treegridData._remote = function(target, options){
    	$.ajax({
            type: options.type,
            url: options.url,
            data: options.ajaxParams,
            dataType: "JSON",
            success: function (data, textStatus, jqXHR) {
            	$.fn.treegridData._loadData(target, options, data);
            }
        });
    };
    $.fn.treegridData._getIDByClass = function(target){
    	var clazz = target.attr("class");
    	var classes = clazz.split(" ");
    	var id;
    	$.each(classes, function(i, n){
    		if(n.indexOf("treegrid-")==0 && n.indexOf("treegrid-parent-")<0){
    			id=n.substring(9);
    			return false;
    		}
    	});
    	return id;
    };
    
    $.fn.treegridData._getSelected = function(target){
    	var rows = target.find(">tbody>tr.selected");
    	if(rows==null || rows.length==0)
    		return [];
    	var data = target.data("data");
    	var options = target.data("options");
    	return $.grep(data, function(n, i){
    		var rs = false;
    		$.each(rows, function(ii, nn){
    			var id = $.fn.treegridData._getIDByClass($(nn));
        		var _id = n[options.id]+"";
        		if(id == _id){
        			rs = true;
        			return false;
        		}
    		});
    		return rs;
    	});
    };
    
    $.fn.treegridData._getChecked = function(target){
    	var cbs = target.find(".treegrid-checkbox:checked");
    	if(cbs==null || cbs.length==0)
    		return [];
    	var data = target.data("data");
    	var options = target.data("options");
    	return $.grep(data, function(n, i){
    		var rs = false;
    		$.each(cbs, function(ii, nn){
    			var id = $(nn).val();
        		var _id = n[options.id]+"";
        		if(id == _id){
        			rs = true;
        			return false;
        		}
    		});
    		return rs;
    	});
    };
})(jQuery);