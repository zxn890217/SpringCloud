$(function () {
    hookAjax({
        onreadystatechange:function(xhr){
           // console.log("onreadystatechange called: %O",xhr)
            //return true
        },
        onload:function(xhr){
            //console.log("onload called: %O",xhr)
            //xhr.responseText="hook"+xhr.responseText;
            //return true;
        },
        open:function(arg,xhr){
            //console.log("open called: method:%s,url:%s,async:%s",arg[0],arg[1],arg[2],xhr);
        },
        send:function(arg,xhr){
            //统一添加请求头
            if(token){
                xhr.setRequestHeader("token", token);
            }
        },
        setRequestHeader:function(arg,xhr){
           // console.log("setRequestHeader called!",arg)
        }
    })

})
