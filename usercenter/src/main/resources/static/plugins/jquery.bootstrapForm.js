/**
 * Created by zxn on 2018/1/16.
 */
(function($) {
    $.fn.bootstrapForm = function(options, param){
        if (typeof options == 'string') {
            return $.fn.bootstrapForm.methods[options]($(this), param);
        }
    };

    $.fn.bootstrapForm.methods = {
        loadData: function(jq, data){
            $.fn.bootstrapForm.fillForm.parseAndFill(jq, null, data);
        },
        load: function(jq, options){
            var configs = {
                type: "GET",
                dataType: "json",
                success: function(r){
                    if(r.success!=undefined && r.result!=undefined){
                        if(r.success && r.result){
                            if(options.onSuccess){
                                options.onSuccess(r.result);
                            }
                            $.fn.bootstrapForm.fillForm.parseAndFill(jq, null, r.result);
                        }
                        else
                            Lobibox.notify('error', {
                                width: $(window).width(),
                                msg: r.msg
                            });
                    }
                    else{
                        options.onSuccess(r);
                        $.fn.bootstrapForm.fillForm.parseAndFill(jq, null, r);
                    }
                }
            }
            var opts = $.extend(configs, options||{});
            return utils.ajax(opts);
        },
        validForm: function(jq, options){
            if(!options.excluded)
                options.excluded = [':disabled', ':hidden', ':not(:visible)'];
            if(options.submitBtnEvent==undefined){
                options.submitBtnEvent=true;
            }
            var $vForm = jq.bootstrapValidator({
                excluded: options.excluded,
                message: '验证不通过',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: options.fields
            }).on('success.form.bv', function(e) {
                e.preventDefault();
                var extendParams = null;
                if(options.beforeSubmit){
                    options.beforeSubmit(jq);
                }
                if(options.extendParams){
                    extendParams = options.extendParams(jq);
                }
                if(options.submitAjax){
                    return options.submitAjax(jq);
                }
                else{
                    var ajaxOpts = {
                        type: "POST",
                        dataType: "json",
                        success:function(r){
                            if(options.submitOnSuccess){
                                if(options.submitOnSuccess(r)==false){
                                    if(options.submitBtnId)
                                        $("#"+options.submitBtnId).removeAttr("disabled");
                                    return;
                                }
                            }
                            if(r){
                                if(r.success){
                                    if(options.dialogId){
                                        var dialog = BootstrapDialog.getDialog(options.dialogId);
                                        dialog.close();
                                    }
                                    if(options.tableId){
                                        $("#"+options.tableId).bootstrapTable("refresh");
                                    }
                                    Lobibox.notify('info', {
                                        size: 'mini',
                                        title: '提示',
                                        msg: r.msg
                                    });
                                }
                                else{
                                    Lobibox.alert('error', {
                                        title: '失败',
                                        msg: r.msg
                                    });
                                }
                            }
                            else{
                                Lobibox.alert('error', {
                                    title: '错误',
                                    msg: '无返回值'
                                });
                            }
                            if(options.submitBtnId)
                                $("#"+options.submitBtnId).removeAttr("disabled");
                        },
                        error:function(){
                            if(options.errorFn){
                                options.errorFn();
                            }
                            else{
                                Lobibox.notify('error', {
                                    width: $(window).width(),
                                    msg: '表单提交失败'
                                });
                            }
                            if(options.submitBtnId)
                                $("#"+options.submitBtnId).removeAttr("disabled");
                        }
                    };
                    ajaxOpts = $.extend(ajaxOpts, options.ajaxOpts||{});
                    var data = jq.serializeJson();
                    if(extendParams){
                        data = $.extend(data, extendParams);
                    }
                    if(ajaxOpts.contentType=="application/json"){
                        ajaxOpts.data=JSON.stringify(data);
                    }
                    else{
                        ajaxOpts.data=data;
                    }
                    $.ajax(ajaxOpts);
                }
            }).on('error.form.bv', function(e) {
                if(options.submitOnError)
                    options.submitOnError(e);
                if(options.submitBtnId)
                    $("#"+options.submitBtnId).removeAttr("disabled");
            });
            //绑定提交表单事件
            if(options.submitBtnEvent&&options.submitBtnId){
                $("#"+options.submitBtnId).on("click",function(){
                    $("#"+options.submitBtnId).attr("disabled","true");//禁用按钮防止重复提交
                    if(options.reValidateFields){
                        for(var i=0; i<options.reValidateFields.length;i++){
                            jq.data('bootstrapValidator').updateStatus(options.reValidateFields[i], 'NOT_VALIDATED', null);
                        }
                    }
                    jq.submit();
                });
            }
        }
    };

    $.fn.bootstrapForm.fillForm={
        parseAndFill: function(form, key, value){
            if(key == null){
                if(value.constructor == Object){
                    for(x in value){
                        var k = x;
                        this.parseAndFill(form, k, value[x]);
                    }
                }
                return;
            }
            if(value=='' || value==null || value==undefined)
                return;
            if(value.constructor == Array){
                key +="[]";
                var _this = this;
                $.each(value, function(index, item){
                    var k = key+"";
                    if(item.constructor == Object){
                        _this.parseAndFill(form, k, item);
                    }
                    else{
                        _this.fillEl(form, k, item);
                    }
                });
            }
            else if(value.constructor == Object){
                key +=".";
                for(x in value){
                    var k = key+x;
                    this.parseAndFill(form, k, value[x]);
                }
            }
            else if(value.constructor == Date){
                value = value.Format("yyyy-MM-dd HH:mm:ss");
                this.fillEl(form, key, value);
            }
            else if(value.constructor == Boolean){
                value = value+"";
                this.fillEl(form, key, value);
            }
            else{
                this.fillEl(form, key, value);
            }
        },
        fillEl: function(form, key, value) {
            form.find("[name='" + key + "']").each(function () {
                var tagName = $(this)[0].tagName;
                var type = $(this).attr('type');
                if (tagName == 'INPUT') {
                    if (type == 'radio') {
                        $(this).attr('checked', $(this).val() == value);
                    } else if (type == 'checkbox') {
                        var arr = value.split(',');
                        for (var i = 0; i < arr.length; i++) {
                            if ($(this).val() == arr[i]) {
                                $(this).attr('checked', true);
                                break;
                            }
                        }
                    } else {
                        $(this).val(value);
                    }
                } else if (tagName == 'SELECT' || tagName == 'TEXTAREA') {
                    $(this).val(value);
                }
            });
        }
    };
})(jQuery);