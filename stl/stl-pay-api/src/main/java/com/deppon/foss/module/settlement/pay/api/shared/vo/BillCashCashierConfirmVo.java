package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashCashierConfirmDto;


/**
 * 确认收银Dto
 * @author foss-pengzhen
 * @date 2012-12-11 上午11:16:02
 * @since
 * @version
 */
public class BillCashCashierConfirmVo implements Serializable {

	/**
	 * Dto序列号
	 */
	private static final long serialVersionUID = -8288761080015694172L;
	/**
	 * 收银确认dto
	 */
	private String billingGroup;
	
	/**
	 * 收银确认dto
	 */
	private BillCashCashierConfirmDto billCashCashierConfirmDto;
	
	/**
	 * 收银确认dto集合
	 */
	private List<BillCashCashierConfirmDto> billCashCashierConfirmDtos;

	/**
	 * 导出时用于收集界面数据的属性
	 */
	private String exportBillCashCashierConfirm;

    /**
     * 待收银确认的代收货款相关单号
     */
	private List<String> unconfirmedCodRelatedBillList;


    public List<String> getUnconfirmedCodRelatedBillList() {
        return unconfirmedCodRelatedBillList;
    }

    public void setUnconfirmedCodRelatedBillList(List<String> unconfirmedCodRelatedBillList) {
        this.unconfirmedCodRelatedBillList = unconfirmedCodRelatedBillList;
    }

    /**
	 * @return  the exportBillCashCashierConfirm
	 */
	public String getExportBillCashCashierConfirm() {
		return exportBillCashCashierConfirm;
	}

	/**
	 * @param exportBillCashCashierConfirm the exportBillCashCashierConfirm to set
	 */
	public void setExportBillCashCashierConfirm(String exportBillCashCashierConfirm) {
		this.exportBillCashCashierConfirm = exportBillCashCashierConfirm;
	}



	/**
	 * @return  the billingGroup
	 */
	public String getBillingGroup() {
		return billingGroup;
	}


	
	/**
	 * @param billingGroup the billingGroup to set
	 */
	public void setBillingGroup(String billingGroup) {
		this.billingGroup = billingGroup;
	}


	/**
	 * @return  the billCashCashierConfirmDto
	 */
	public BillCashCashierConfirmDto getBillCashCashierConfirmDto() {
		return billCashCashierConfirmDto;
	}

	
	/**
	 * @param billCashCashierConfirmDto the billCashCashierConfirmDto to set
	 */
	public void setBillCashCashierConfirmDto(
			BillCashCashierConfirmDto billCashCashierConfirmDto) {
		this.billCashCashierConfirmDto = billCashCashierConfirmDto;
	}

	
	/**
	 * @return  the billCashCashierConfirmDtos
	 */
	public List<BillCashCashierConfirmDto> getBillCashCashierConfirmDtos() {
		return billCashCashierConfirmDtos;
	}

	
	/**
	 * @param billCashCashierConfirmDtos the billCashCashierConfirmDtos to set
	 */
	public void setBillCashCashierConfirmDtos(
			List<BillCashCashierConfirmDto> billCashCashierConfirmDtos) {
		this.billCashCashierConfirmDtos = billCashCashierConfirmDtos;
	}

}
