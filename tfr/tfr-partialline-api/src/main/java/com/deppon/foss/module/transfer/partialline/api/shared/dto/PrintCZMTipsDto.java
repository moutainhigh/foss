/**
 * @author foss 257200
 * 2015-8-25
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsEntity;

/**
 * @author 257220
 *
 */
public class PrintCZMTipsDto  extends PrintCZMTipsEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//交接单号
	private List<String> handOverBillList;
	//运单号
	private List<String> waybillNoList;
	
	//关联代理单号
	private String refAgentWaybillNos;
	//件数
	private int number;
	/**
	 * @return the handOverBillList
	 */
	public List<String> getHandOverBillList() {
		return handOverBillList;
	}
	/**
	 * @param handOverBillList the handOverBillList to set
	 */
	public void setHandOverBillList(List<String> handOverBillList) {
		this.handOverBillList = handOverBillList;
	}
	/**
	 * @return the waybillNoList
	 */
	public List<String> getWaybillNoList() {
		return waybillNoList;
	}
	/**
	 * @param waybillNoList the waybillNoList to set
	 */
	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
	public String getRefAgentWaybillNos() {
		return refAgentWaybillNos;
	}
	public void setRefAgentWaybillNos(String refAgentWaybillNos) {
		this.refAgentWaybillNos = refAgentWaybillNos;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
}
