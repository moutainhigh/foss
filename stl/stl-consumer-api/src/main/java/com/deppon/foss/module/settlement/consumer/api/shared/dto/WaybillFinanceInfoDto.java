package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 运单财务情况DTO
 * 
 * @author zbw
 * @since 2013-06-26
 * 
 */

public class WaybillFinanceInfoDto implements Serializable {

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 始发运费
	 */
	private OrigFeeInfo origFeeInfo;

	/**
	 * 到付费用
	 */
	private DestFeeInfo destFeeInfo;

	/**
	 * 代收货款费用
	 */
	private CODFeeInfo codFeeInfo;

	/**
	 * 其它费用
	 */
	private List<OtherFeeInfo> otherFeeInfos;

	/**
	 * 小票费用
	 */
	private List<InvoiceFeeInfo> invoiceFeeInfos;

	/**
	 * @return the codFeeInfo
	 */
	public CODFeeInfo getCodFeeInfo() {
		return codFeeInfo;
	}

	/**
	 * @param codFeeInfo
	 *            the codFeeInfo to set
	 */
	public void setCodFeeInfo(CODFeeInfo codFeeInfo) {
		this.codFeeInfo = codFeeInfo;
	}

	/**
	 * @return the otherFeeInfos
	 */
	public List<OtherFeeInfo> getOtherFeeInfos() {
		return otherFeeInfos;
	}

	/**
	 * @param otherFeeInfos
	 *            the otherFeeInfos to set
	 */
	public void setOtherFeeInfos(List<OtherFeeInfo> otherFeeInfos) {
		this.otherFeeInfos = otherFeeInfos;
	}

	/**
	 * @return the invoiceFeeInfos
	 */
	public List<InvoiceFeeInfo> getInvoiceFeeInfos() {
		return invoiceFeeInfos;
	}

	/**
	 * @param invoiceFeeInfos
	 *            the invoiceFeeInfos to set
	 */
	public void setInvoiceFeeInfos(List<InvoiceFeeInfo> invoiceFeeInfos) {
		this.invoiceFeeInfos = invoiceFeeInfos;
	}

	/**
	 * @return the origFeeInfo
	 */
	public OrigFeeInfo getOrigFeeInfo() {
		return origFeeInfo;
	}

	/**
	 * @param origFeeInfo
	 *            the origFeeInfo to set
	 */
	public void setOrigFeeInfo(OrigFeeInfo origFeeInfo) {
		this.origFeeInfo = origFeeInfo;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the destFeeInfo
	 */
	public DestFeeInfo getDestFeeInfo() {
		return destFeeInfo;
	}

	/**
	 * @param destFeeInfo
	 *            the destFeeInfo to set
	 */
	public void setDestFeeInfo(DestFeeInfo destFeeInfo) {
		this.destFeeInfo = destFeeInfo;
	}

}
