package com.example.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.example.model.UserDomain;
import com.example.model.common.PageData;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;

/**
 * Created by Administrator on 2017/8/16.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@ResponseBody
	@PostMapping("/add")
	public int addUser(UserDomain user) {
		return userService.addUser(user);
	}

	@ResponseBody
	@PostMapping("/all")
	public Object findAllUser(@RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
		PageInfo<UserDomain> pageInfo = userService.findAllUser(pageNum, pageSize);

		PageData pageData = new PageData();
		pageData.setRows(pageInfo.getList());
		pageData.setTotal(pageInfo.getTotal());
		return pageData;
	}

	@PostMapping("/uploadFile")
	@ResponseBody
	public Object uploadFile(@RequestParam("uploadfile") MultipartFile file,@RequestParam("tableTitle") String tableTitle)
			throws IOException, SAXException, InvalidFormatException {
		
		userService.uploadFile(file);
		return findAllUser(1, 10);
	}

	@GetMapping("/dowloadExcel")
	public void dowloadExcel(HttpServletRequest request,HttpServletResponse response,@RequestParam("tableTitle") String tableTitle) {
		System.out.println("==========================");
		System.out.println(tableTitle);
		userService.dowloadExcel(request,response);
	}
}