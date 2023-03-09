package com.example.eback.controller;

import com.example.eback.entity.User;
import com.example.eback.result.Result;
import com.example.eback.result.ResultFactory;
import com.example.eback.service.UserService;
import com.example.eback.util.LogUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.validation.Valid;

/**
 * Login and register controller.
 *
 * @author Evan
 * @date 2019/4
 */
@ApiOperation(value = "登录注册相关接口")
@RestController
public class LoginController {

    @Autowired
    UserService userService;
    String ClassName="login";

    @ApiOperation(value = "登录",notes = "账号+密码")
    @PostMapping("/api/login")
    public Result login(@RequestBody @Valid User requestUser) {

        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        usernamePasswordToken.setRememberMe(true);
        try {
            subject.login(usernamePasswordToken);
            LogUtil.log(ClassName,"info",username+"登录成功");
            return ResultFactory.buildSuccessResult(username);
        } catch (IncorrectCredentialsException e) {
            LogUtil.log(ClassName,"warn",username+"登录，密码错误");
            return ResultFactory.buildFailResult("密码错误");
        } catch (UnknownAccountException e) {
            return ResultFactory.buildFailResult("账号不存在");
        }

    }

    @PostMapping("/api/register")
    public Result register(@RequestBody User user) {
        LogUtil.log(ClassName,"info",user.getUsername()+"注册");
        int status = userService.register(user);
        switch (status) {
            case 0:
                return ResultFactory.buildFailResult("用户名和密码不能为空");
            case 1:
                return ResultFactory.buildSuccessResult("注册成功");
            case 2:
                return ResultFactory.buildFailResult("用户已存在");
            default:
                return ResultFactory.buildFailResult("未知错误");
        }
    }

    @GetMapping("/api/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultFactory.buildSuccessResult("成功登出");
    }

    @GetMapping("/api/admin/authentication")
    public Result authentication() {
        return  ResultFactory.buildSuccessResult("success");
    }

}
