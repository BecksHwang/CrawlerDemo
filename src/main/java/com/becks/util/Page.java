package com.becks.util;

import java.io.Serializable;
import java.util.List;

/**
 * 创建时间：
 * 
 * @Description Page
 * @author 
 * @version
 */
@SuppressWarnings("serial")
public class Page implements Serializable{

	private int currentPage = 1;
	private int pageSize = 20;
	private int totalRow;
	private int totalPage; 
	private int index;
	
	private List<? extends Object> items;
	
	public Page() {
	}
	public Page(int currentPage, int pageSize, int totalRow) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalRow = totalRow;
		if(totalRow%pageSize==0){
			this.totalPage=(this.totalRow/this.pageSize);
		}
		if(totalRow%pageSize!=0){
			this.totalPage=(this.totalRow/this.pageSize)+1;
		}
	}
	
	public Page(int currentPage, int totalRow, int pageSize, List<? extends Object> items) {
		setCurrentPage(currentPage);
		setTotalRow(totalRow);
		setItems(items);
		setPageSize(pageSize);
		if(totalRow%pageSize==0){
			this.totalPage=(this.totalRow/this.pageSize);
		}
		if(totalRow%pageSize!=0){
			this.totalPage=(this.totalRow/this.pageSize)+1;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * 
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * 
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		this.index  = (this.currentPage-1)*this.pageSize;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 
	 * @return
	 */
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
		if(totalRow%pageSize==0){
			this.totalPage=(this.totalRow/this.pageSize);
		}
		if(totalRow%pageSize!=0){
			this.totalPage=(this.totalRow/this.pageSize)+1;
		}
	}
	/**
	 * 
	 * @return
	 */
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	/**
	 * 
	 * @return
	 */
	public int nextPageStart(){
		int next = (this.currentPage-1)*this.pageSize;
		return next;
	}
	public List<? extends Object> getItems() {
		return items;
	}
	public void setItems(List<? extends Object> items) {
		this.items = items;
	}
}
