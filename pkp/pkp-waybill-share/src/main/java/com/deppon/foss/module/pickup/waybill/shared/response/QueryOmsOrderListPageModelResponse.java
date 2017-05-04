package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
import java.util.List;

/**
 * @discription OMS通用查询借口,与FOSS数据定义结构不一样，其返回数据有三层，故在其定义PageModel以适应OMS订单列表数据解析
 * @author 323098 2016-04-15
 *
 */
public class QueryOmsOrderListPageModelResponse implements Serializable{
	
	
		/*
		 * 每页条数
		 */
		private Integer pageSize;
		/*
		 * 当前页数
		 */
		private Integer currentPage;
		/*
		 * 总页数
		 */
		private Integer totalPage;
		/*
		 * 总条数
		 */
		private Long totalRows;
		/*
		 * 数据
		 */
		private List<QueryOmsOrderInfoResponse> model;
		/**
		 * 获取pageSize  
		 * @return pageSize pageSize  
		 */
		public Integer getPageSize() {
			return pageSize;
		}
		/**
		 * 设置pageSize  
		 * @param pageSize pageSize
		 */
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}
		/**
		 * 获取currentPage  
		 * @return currentPage currentPage  
		 */
		public Integer getCurrentPage() {
			return currentPage;
		}
		/**
		 * 设置currentPage  
		 * @param currentPage currentPage
		 */
		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}
		/**
		 * 获取totalPage  
		 * @return totalPage totalPage  
		 */
		public Integer getTotalPage() {
			return totalPage;
		}
		/**
		 * 设置totalPage  
		 * @param totalPage totalPage
		 */
		public void setTotalPage(Integer totalPage) {
			this.totalPage = totalPage;
		}
		/**
		 * 获取totalRows  
		 * @return totalRows totalRows  
		 */
		public Long getTotalRows() {
			return totalRows;
		}
		/**
		 * 设置totalRows  
		 * @param totalRows totalRows
		 */
		public void setTotalRows(Long totalRows) {
			this.totalRows = totalRows;
		}
		/**
		 * 获取model  
		 * @return model model  
		 */
		public List<QueryOmsOrderInfoResponse> getModel() {
			return model;
		}
		/**
		 * 设置model  
		 * @param model model
		 */
		public void setModel(List<QueryOmsOrderInfoResponse> model) {
			this.model = model;
		}
	
}
