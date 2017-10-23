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
@RestController
public abstract class BaseRestController<T, Q extends QueryModel> {

    protected abstract BaseService<T, Q> getService();

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    @ResponseBody
    public T get(@PathVariable("id") Long id){
        return getService().get(id);
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
    public List<T> query(Q queryModel){
        return getService().query(queryModel);
    }
    @RequestMapping(value="/page", method= RequestMethod.GET)
    @ResponseBody
    public Page<T> page(Q queryModel){
        return getService().findByPage(queryModel);
    }
}
