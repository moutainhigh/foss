package com.deppon.foss.module.pickup.waybill.shared.request;

import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListQueryVo;

/**
 * 货物清单 外部系统请求参数列表
 * @author 272311
 *
 */
public class DeliverGoodsListQueryRequest extends DeliverGoodsListQueryVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7599491915178825965L;
	/**
	 * 起始页
	 */
	private int start = 0 ;
	/**
	 * 每页显示记录数
	 */
	private int limit = 0 ;
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	@Override
	public String toString() {
		return "DeliverGoodsListQueryRequest [start=" + start + ", limit="
				+ limit + " " +super.toString()+" ] ";
	}
	
	

}
