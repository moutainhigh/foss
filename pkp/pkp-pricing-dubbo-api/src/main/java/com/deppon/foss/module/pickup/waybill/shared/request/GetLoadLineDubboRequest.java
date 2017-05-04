package com.deppon.foss.module.pickup.waybill.shared.request;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询时效是否存在请求参数
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-316759-wangruipeng,date:2017-4-1 下午4:06:49,content:查询时效是否存在请求参数 </p>
 * @author Foss-316759-wangruipeng 
 * @date 2017-4-1 下午4:06:49
 * @since
 * @version
 */
public class GetLoadLineDubboRequest implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** 出发部门 */
	private String originalOrgCode;

	/** 到达部门 */
	private String destinationOrgCode;

	/** 产品code */
	private String productCode;

	/** 开单日期 可空 ，默认为当前时间 */
	private Date billDate;

	public String getOriginalOrgCode() {
		return originalOrgCode;
	}

	public void setOriginalOrgCode(String originalOrgCode) {
		this.originalOrgCode = originalOrgCode;
	}

	public String getDestinationOrgCode() {
		return destinationOrgCode;
	}

	public void setDestinationOrgCode(String destinationOrgCode) {
		this.destinationOrgCode = destinationOrgCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

}
