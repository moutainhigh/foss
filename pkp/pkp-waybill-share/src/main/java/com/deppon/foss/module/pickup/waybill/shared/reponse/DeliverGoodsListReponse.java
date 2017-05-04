package com.deppon.foss.module.pickup.waybill.shared.reponse;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 货物清单 对外结果返回 字段列表
 * @author 272311
 *
 */
public class DeliverGoodsListReponse  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3881251927073178986L;

	private List<DeliverGoodsListVo> deliverGoodsListVoList ;
	
	 /**
	  * 数据总记录数
	  */
	 private int totalCount = 0 ;
	 
	 private String success = FossConstants.YES ;
	 
	 private String errorMsg ;
	 
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public List<DeliverGoodsListVo> getDeliverGoodsListVoList() {
		return deliverGoodsListVoList;
	}
	public void setDeliverGoodsListVoList(
			List<DeliverGoodsListVo> deliverGoodsListVoList) {
		this.deliverGoodsListVoList = deliverGoodsListVoList;
	}
	@Override
	public String toString() {
		return "DeliverGoodsListReponse [ totalCount=" + totalCount
				+ ", success=" + success + ", errorMsg=" + errorMsg + "]";
	}
	
		
		

}
