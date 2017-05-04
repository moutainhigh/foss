package com.deppon.foss.module.base.querying.shared.dto;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.DestFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OrigFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherFeeInfo;
public class FinanceInfoDto {
	
	private OrigFeeInfo origFeeInfo;
	private DestFeeInfo destFeeInfo;
	private CODFeeInfo codFeeInfo;
	private List<OtherFeeInfo> otherFeeInfos;
	private InvoiceFeeInfo invoiceFeeInfo;
	private String result;
	private String message;
	
	public OrigFeeInfo getOrigFeeInfo() {
		return origFeeInfo;
	}
	public void setOrigFeeInfo(OrigFeeInfo origFeeInfo) {
		this.origFeeInfo = origFeeInfo;
	}
	public DestFeeInfo getDestFeeInfo() {
		return destFeeInfo;
	}
	public void setDestFeeInfo(DestFeeInfo destFeeInfo) {
		this.destFeeInfo = destFeeInfo;
	}
	public CODFeeInfo getCodFeeInfo() {
		return codFeeInfo;
	}
	public void setCodFeeInfo(CODFeeInfo codFeeInfo) {
		this.codFeeInfo = codFeeInfo;
	}
	public List<OtherFeeInfo> getOtherFeeInfos() {
		return otherFeeInfos;
	}
	public void setOtherFeeInfos(List<OtherFeeInfo> otherFeeInfos) {
		this.otherFeeInfos = otherFeeInfos;
	}
	public InvoiceFeeInfo getInvoiceFeeInfo() {
		return invoiceFeeInfo;
	}
	public void setInvoiceFeeInfo(InvoiceFeeInfo invoiceFeeInfo) {
		this.invoiceFeeInfo = invoiceFeeInfo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
