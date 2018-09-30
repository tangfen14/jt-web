package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	//调用后台的数据,则需要用到自写的工具类
	@Autowired
	private HttpClientService httpClient;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	
	@Override
	public void saveUser(User user) {
		//1.定义远程访问路径,根据接口需求文档来写
		String url = "http://sso.jt.com/user/register";
		
		//2.封装user数据
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		
		//3.通过httpClient发起post请求,访问后台数据库,返回一个json串
		String result =  httpClient.doPost(url, params);
		
		try {
			//4.将result数据转化为SysResult对象
			//小技巧:这里如果result为空那么肯定报错,直接抛出,这样写可以少写一部判断是否为空
			SysResult sysResult = objectMapper.readValue(result, SysResult.class);
			
			//5.判断后台入库操作是否正确,正确的话没操作,错误的话抛出异常
			if(200 != sysResult.getStatus()){
				throw new RuntimeException();
			}
		} catch (Exception e) {
			//小技巧:如果result为空或者其他错误,此处抛出,则会被上层的controller捕获(谁调用抛向谁),
			//则会return SysResult.build(201, "用户新增失败");
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	
	
	
	
	
	
	
	

}
