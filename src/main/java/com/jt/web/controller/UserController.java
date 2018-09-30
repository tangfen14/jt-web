package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//实现页面的通用跳转  http://www.jt.com/user/login.html
	//使用restfull风格
	@RequestMapping("/{moduleName}")
	public String toModule(@PathVariable String moduleName){
		//返回的moduleName就是login或者register,没有.html的
		return moduleName;
	}
	
	
	//用户注册信息提交数据库
	@RequestMapping("/doRegister")
	@ResponseBody
	//通过js分析得知,返回值为SysResult,传入的数据为4个属性,用user对象接收即可
	public SysResult saveUser(User user){
		try {
			//执行保存操作
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			//出现了任何的问题打印在控制台上,方便查看
			e.printStackTrace();
		}
		//如果走到这一步,说明前面的成功的return没运行,那么则返回失败
		return SysResult.build(201, "用户新增失败");
	}
}
