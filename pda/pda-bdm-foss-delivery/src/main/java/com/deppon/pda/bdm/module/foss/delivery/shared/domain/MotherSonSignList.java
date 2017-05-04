package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

//子母件签收明细信息实体
public class MotherSonSignList extends ScanMsgEntity{ 
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//运单号
	   private String  wblCode;
		//到达联编号
		private String arrInfoCode;
		//子母件总件数
		private  int pieces; 
		//签收人
		private String signPerson;
		//车牌号
		private String truckCode;
		//流水号列表
		private List<PdaSignLabelCodeEntity> labelCodes;
		//支付类型
		private String payType;
		//签收状态
		private String signStatus;
		//签收时间
		private Date signTime;
		 //是否提供定额发票
	    private String isofferInvoice;
		//签收类型    子母件正常签收
	    private String signtypee;
	    //签收人类型
	    private String  signPerType;
	    
	    public String getWblCode() {
			return wblCode;
		}
		public void setWblCode(String wblCode) {
			this.wblCode = wblCode;
		}
		public String getArrInfoCode() {
			return arrInfoCode;
		}
		public void setArrInfoCode(String arrInfoCode) {
			this.arrInfoCode = arrInfoCode;
		}
		public String getSignPerson() {
			return signPerson;
		}
		public void setSignPerson(String signPerson) {
			this.signPerson = signPerson;
		}
		public String getTruckCode() {
			return truckCode;
		}
		public void setTruckCode(String truckCode) {
			this.truckCode = truckCode;
		}
		public List<PdaSignLabelCodeEntity> getLabelCodes() {
			return labelCodes;
		}
		public void setLabelCodes(List<PdaSignLabelCodeEntity> labelCodes) {
			this.labelCodes = labelCodes;
		}
		public String getPayType() {
			return payType;
		}
		public void setPayType(String payType) {
			this.payType = payType;
		}
		public String getSignStatus() {
			return signStatus;
		}
		public void setSignStatus(String signStatus) {
			this.signStatus = signStatus;
		}
		public Date getSignTime() {
			return signTime;
		}
		public void setSignTime(Date signTime) {
			this.signTime = signTime;
		}
		public String getIsofferInvoice() {
			return isofferInvoice;
		}
		public void setIsofferInvoice(String isofferInvoice) {
			this.isofferInvoice = isofferInvoice;
		}
		public String getSigntypee() {
			return signtypee;
		}
		public void setSigntypee(String signtypee) {
			this.signtypee = signtypee;
		}
		public String getSignPerType() {
			return signPerType;
		}
		public void setSignPerType(String signPerType) {
			this.signPerType = signPerType;
		}
		public int getPieces() {
			return pieces;
		}
		public void setPieces(int pieces) {
			this.pieces = pieces;
		}
}
