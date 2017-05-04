package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.WsGuiResultBillCalculateDubboDto;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-28 下午4:04:10,content: </p>
 * @author 316759 
 * @date 2017-2-28 下午4:04:10
 * @since
 * @version
 */
public class QueryGuiBillPriceDubboResponse implements Serializable {
	
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private List<WsGuiResultBillCalculateDubboDto> billPriceList;
    
    private BigDecimal totalFee;

    private String success;
    
    private String msg;

	public List<WsGuiResultBillCalculateDubboDto> getBillPriceList() {
		if(billPriceList == null){
			billPriceList = new ArrayList<WsGuiResultBillCalculateDubboDto>();
		}
		return billPriceList;
	}

	public void setBillPriceList(
			List<WsGuiResultBillCalculateDubboDto> billPriceList) {
		this.billPriceList = billPriceList;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
