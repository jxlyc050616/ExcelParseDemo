package com.example.dao;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.UserDomain;

public interface UserDao {
    int insert(UserDomain user);
    
    List<UserDomain> selectUsers();
    
    int deleteByPrimaryKey(Integer userId);

    int insertSelective(UserDomain record);

    UserDomain selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserDomain record);

    int updateByPrimaryKey(UserDomain record);
    
    ResponseEntity<?> uploadFile(MultipartFile file);
    
    void dowloadExcel(HttpServletRequest request,HttpServletResponse response);
    
}