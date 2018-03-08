package com.example.domain.base;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zxn on 2017/10/23.
 */
public abstract class BaseRestController<T, Q extends QueryBase> {

    protected abstract BaseService<T, Q> getService();

    @GetMapping(value="/{id}")
    @ResponseBody
    public RespBody get(@PathVariable("id") Long id){
        T result = getService().get(id);
        if(result!=null)
            return new RespBody(true,"获取成功", result);
        return new RespBody(false,"获取失败");
    }
    @PostMapping(value="")
    @ResponseBody
    public RespBody save(@RequestBody T entity){
        try {
            if(getService().insert(entity))
                return new RespBody(true, "保存成功");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new RespBody(false, "保存失败");
    }
    @PutMapping(value="/{id}")
    @ResponseBody
    public RespBody update(@PathVariable("id") Long id, @RequestBody T entity){
        try{
            if(getService().update(entity))
                return new RespBody(true, "修改成功");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new RespBody(false, "修改失败");
    }
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public RespBody delete(@PathVariable("id") Long id){
        try{
            if(getService().delete(id))
                return new RespBody(true, "删除成功");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new RespBody(false, "删除失败");
    }
    @PostMapping(value="/page")
    @ResponseBody
    public Page<T> page(Q query){
        return getService().findByPage(query);
    }

    @GetMapping(value="")
    @ResponseBody
    public List<T> query(Q query){
        return getService().query(query);
    }
}
