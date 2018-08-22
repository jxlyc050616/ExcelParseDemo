package com.example.util;

import com.example.model.UserDomain;
import com.example.model.common.PageData;
import com.github.pagehelper.PageInfo;

public class PageInfoConvert {
	
	private PageInfo<UserDomain> pageInfo;
	
	public PageInfoConvert(PageInfo<UserDomain> pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	public PageData infoConvert() {
		PageData pageData = new PageData();
		pageData.setRows(pageInfo.getList());
		pageData.setTotal(pageInfo.getTotal());
		return pageData;
	}
}
