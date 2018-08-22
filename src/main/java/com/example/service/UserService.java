package com.example.service;

import com.github.pagehelper.PageInfo;
import com.example.model.UserDomain;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2018/4/19.
 */
public interface UserService {

    int addUser(UserDomain user);

    PageInfo<UserDomain> findAllUser(int pageNum, int pageSize);
    
    ResponseEntity<?> uploadFile(MultipartFile file);
    
    void dowloadExcel(HttpServletRequest request,HttpServletResponse response);
}
