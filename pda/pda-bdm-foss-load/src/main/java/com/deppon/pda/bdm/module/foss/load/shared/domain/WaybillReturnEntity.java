package com.deppon.pda.bdm.module.foss.load.shared.domain;


/**   
 * @ClassName WaybillReturnEntity  
 * @Description 运单退回实体  
 * @author  092038 张贞献  
 * @date 2015-5-5    
 */ 
public class WaybillReturnEntity{
	
	/*
	 * 退回原因
	 * ①少货/无货； LESS_GOODS
     * ②异形货（超长/超方）；STRANGE_SHAPE
     * ③排单过多，装不下；ITEMIZE_MORE
     * ④托盘货/木架货；  PALLET_GOODS
     * ⑤其他； ELSE_REASON
	 */
	 //装车任务编号
     private String taskCode;
	 //运单号
	 private String wblCode;
	 //退回类型
	 private String retreatType;
	 //运输性质
	 private String transType;
	 //库存件数
	 private int stockQty;
	 //操作件数
	 private int operateQty;
	//退回内容
	 private String retreatReason;
	 
	 
	public String getRetreatReason() {
		return retreatReason;
	}
	public void setRetreatReason(String retreatReason) {
		this.retreatReason = retreatReason;
	}
	public String getRetreatType() {
		return retreatType;
	}
	public void setRetreatType(String retreatType) {
		this.retreatType = retreatType;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public int getStockQty() {
		return stockQty;
	}
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}
	public int getOperateQty() {
		return operateQty;
	}
	public void setOperateQty(int operateQty) {
		this.operateQty = operateQty;
	}
     
}
