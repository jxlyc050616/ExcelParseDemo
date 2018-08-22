package com.example.model.common;

import java.util.List;

import com.example.model.UserDomain;

import java.io.Serializable;

public class PageData implements Serializable {
	
	private long total;
	
	private List<UserDomain> rows;
	
//	public PageData(List<Object> list) {
//		this.total = list.size();
//		this.rows = list;
//	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<UserDomain> getRows() {
		return rows;
	}

	public void setRows(List<UserDomain> rows) {
		this.rows = rows;
	}
	
	
}
