package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.Date;


import org.springframework.format.annotation.DateTimeFormat;

public class Search {
	private String keyword;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	private String filter;
	
	private String sortby;
	

	public String getFilter() {
		return filter;
	}



	public void setFilter(String filter) {
		this.filter = filter;
	}






	public String getKeyword() {
		return keyword;
	}
	
	

	public void setKeyword(String keyword) {
		this.keyword = keyword.toLowerCase();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}



	public Search() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getSortby() {
		return sortby;
	}



	public void setSortby(String sortby) {
		this.sortby = sortby;
	}


	

}
