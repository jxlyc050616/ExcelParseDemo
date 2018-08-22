package com.example.service.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.example.dao.UserDao;
import com.example.model.UserDomain;
import com.example.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.jxls.transformer.XLSTransformer;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;//这里会报错，但是并不会影响

    @Override
    public int addUser(UserDomain user) {

        return userDao.insert(user);
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public PageInfo<UserDomain> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<UserDomain> userDomains = userDao.selectUsers();
        PageInfo result = new PageInfo(userDomains);
        return result;
    }
    
    /**
     * 导入excel中的用户数据
     * @param file
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws InvalidFormatException
     */
	public ResponseEntity<?> uploadFile(MultipartFile file) {
		XLSReader mainReader;
		try {
			InputStream inputXML = new BufferedInputStream(getClass().getResourceAsStream("/configXml/user.xml"));
			mainReader = ReaderBuilder.buildFromXML(inputXML);
			InputStream inputXLS = new BufferedInputStream(file.getInputStream());
			List<UserDomain> userVo = new ArrayList<>();
			Map<String, Object> beans = new HashMap();
			beans.put("userVo", userVo);
			XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
			for(UserDomain user:userVo) {
				addUser(user);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

//	public void dowloadExcel(HttpServletRequest request,HttpServletResponse response) {
//		
//	}
	

    public void dowloadExcel(HttpServletRequest request,HttpServletResponse response){  
//        String templateFileName= request.getServletContext().getRealPath("/") + "/resources/templateFileName.xls";  
        String destFileName= "userTemple.xlsx";  
        //模拟数据  
        List<UserDomain> users = findAllUser(1, 10).getList();

        Map<String,Object> beans = new HashMap<String,Object>();  
        beans.put("users", users);  
        XLSTransformer transformer = new XLSTransformer();  
        InputStream in=null;  
        OutputStream out=null;  
        //设置响应  
        response.setHeader("Content-Disposition", "attachment;filename=" + destFileName);  
        response.setContentType("application/vnd.ms-excel");  
        XSSFWorkbook workbook = null;
        try {  
            in=new BufferedInputStream(getClass().getResourceAsStream("/temple/userTemple.xlsx"));  
            workbook=(XSSFWorkbook) transformer.transformXLS(in, beans);  
//            return workbook;
            out=response.getOutputStream();  
            //将内容写入输出流并把缓存的内容全部发出去  
            workbook.write(out);  
            out.flush();
        } catch (InvalidFormatException | IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (in!=null){try {in.close();} catch (IOException e) {}}  
            if (out!=null){try {out.close();} catch (IOException e) {}}  
        }
//		return workbook;  
    }  
}
