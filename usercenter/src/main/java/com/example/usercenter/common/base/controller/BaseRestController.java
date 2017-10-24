package com.example.usercenter.common.base.controller;

import com.example.usercenter.common.base.model.Page;
import com.example.usercenter.common.base.model.QueryModel;
import com.example.usercenter.common.base.model.RespBody;
import com.example.usercenter.common.base.service.BaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zxn on 2017/10/23.
 */
public abstract class BaseRestController<T, Q extends QueryModel> {

    protected abstract BaseService<T, Q> getService();

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    @ResponseBody
    public RespBody get(@PathVariable("id") Long id){
        T result = getService().get(id);
        if(result!=null)
            return new RespBody(true,"获取成功", result);
        return new RespBody(false,"获取失败");
    }
    @RequestMapping(value="", method= RequestMethod.POST)
    @ResponseBody
    public RespBody save(@RequestBody T entity){
        try {
            getService().save(entity);
            return new RespBody(true, "保存成功");
        }
        catch (Exception e){
            return new RespBody(false, "保存失败");
        }
    }
    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    @ResponseBody
    public RespBody update(@PathVariable("id") Long id, @RequestBody T entity){
        try{
            getService().update(entity);
            return new RespBody(true, "修改成功");
        }
        catch (Exception e){
            return new RespBody(false, "修改失败");
        }
    }
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    @ResponseBody
    public RespBody delete(@PathVariable("id") Long id){
        try{
            getService().delete(id);
            return new RespBody(true, "删除成功");
        }
        catch (Exception e){
            return new RespBody(false, "删除失败");
        }
    }
    @RequestMapping(value="", method= RequestMethod.GET)
    @ResponseBody
    public RespBody query(Q queryModel){
        List<T> result = getService().query(queryModel);
        if(result!=null && result.size()>0)
            return new RespBody(true,"查询成功", result);
        return new RespBody(false,"查询结果为空");
    }
    @RequestMapping(value="/page", method= RequestMethod.GET)
    @ResponseBody
    public Page<T> page(Q queryModel){
        return getService().findByPage(queryModel);
    }
}
