package com.deppon.foss.module.pickup.waybill.shared.dto;


/**
 * 获取从数据库中查询到的分配业务实体
 * @author Foss-278328-hujinyang
 * @date 2016-1-28
 */
public class SubCenterBillOrgDto extends CenterBillOrgDto {

	/**
	 * @author Foss-278328-hujinyang
	 * @date 2016-1-28
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 峰值(开单量)
	 */
	private Integer peakValue;
	
	/**
	 * 当前百分比
	 */
	private String curPercent;
	
	/**
	 * 本地百分比
	 */
	private String localPercent;
	
	/**
	 * 是否是本地开单
	 */
	private String isLocal;
	
	/**
	 * 查询时间
	 */
	private String queryDate;
	
	/**
	 * 运单号
	 */
	private String waybillNo;

	public String getCurPercent() {
		return curPercent;
	}

	public void setCurPercent(String curPercent) {
		this.curPercent = curPercent;
	}

	public String getLocalPercent() {
		return localPercent;
	}

	public void setLocalPercent(String localPercent) {
		this.localPercent = localPercent;
	}

	public Integer getPeakValue() {
		return peakValue;
	}

	public void setPeakValue(Integer peakValue) {
		this.peakValue = peakValue;
	}
	
	public String getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取当然日志信息
	 * @author Foss-278328-hujinyang
	 * @date 2016-1-28
	 * @return
	 */
	public String getCurrentInfo(){
		StringBuffer sb = new StringBuffer();
		sb.append("pkp-waybill-share ---> SubCenterBillOrgDto[");
		sb.append("当前处理的运单号:").append(waybillNo);
		sb.append("当前开单组:").append(getBillOrgCode())
		.append(";当前峰值:").append(peakValue)
		.append(";当前开单量:").append(getBillCount())
		.append(";当前百分比:").append(curPercent)
		.append(";是否为本地开单:").append(isLocal);
		if("N".equals(isLocal)){
			sb.append(";本地百分比:").append(localPercent);
		}
		sb.append(";当前查询时间:").append(queryDate);
		sb.append("]");
		
		return sb.toString();
	}

}
