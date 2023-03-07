package com.example.eback.controller;

import com.example.eback.entity.User;
import com.example.eback.entity.User_Stock;
import com.example.eback.result.Result;
import com.example.eback.result.ResultFactory;
import com.example.eback.service.FavoriteService;
import com.example.eback.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@ApiOperation(value = "用户和股票相关的控制接口")
@RestController
public class UserStockController {
    @Autowired
    FavoriteService favoriteService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "添加关注的股票",notes = "只需要填写int sid")
    @PostMapping("/api/userstock/add")
    public Result add(@RequestBody  User_Stock stock){
        String sid=stock.getSid();
        User user= getUser();
        int uid =user.getId();
        switch (favoriteService.addFavorite(uid,sid)){
            case 0: return ResultFactory.buildFailResult("添加失败，已有此股票");
            case 2:  return ResultFactory.buildFailResult("添加失败，无此股票");
            default: return  ResultFactory.buildSuccessResult("添加成功");
        }
    }


    @ApiOperation(value = "删除关注的股票",notes = "只需要填写int sid")
    @PostMapping("/api/userstock/delete")
    public Result delete(@RequestBody User_Stock stock) {
        String sid=stock.getSid();
        User user= getUser();
        int uid =user.getId();
        switch (favoriteService.deleteFavorite(uid,sid)){
            case 0: return ResultFactory.buildFailResult("删除失败，未添加此股票");
            default: return  ResultFactory.buildSuccessResult("删除成功");
        }
    }
    @ApiOperation(value = "获取关注的股票",notes = "url设置网页和size")
    @GetMapping("/api/userstock/favorite/page={page}/size={size}")
    public Result getAll(@PathVariable("page") int pages,@PathVariable("size") int size) {
        User user= getUser();
        return ResultFactory.buildSuccessResult(favoriteService.findAll(user.getId(),pages,size));
    }

    public   User getUser (){
        User user= userService.findByUsername(SecurityUtils.getSubject().getPrincipal().toString());
        return   user;
    }
}
