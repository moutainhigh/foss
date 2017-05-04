package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @ClassName: LabelInfoEntity 
 * @Description: TODO(标签信息实体) 
 * @author &268974  wangzhili
 * @date 2016-5-17 下午2:53:55 
 *
 */
public class WaybillInfoEntity implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	//交接单号
	private String handOverNo;
	//运单号
	private String wblCode;
	//件数
	private int pieces;
	//重量
	private BigDecimal weight;
	//体积
	private BigDecimal volume;
	//大件上楼（取值为Y / N）
	private String largeUpstrairs;
	//扫描时间
	private Date scanTime;
	//包装备注（必输）
	private String packageRemark;
	//流水号
	private String serailNo;
	
	public String getSerailNo() {
		return serailNo;
	}
	public void setSerailNo(String serailNo) {
		this.serailNo = serailNo;
	}
	public String getHandOverNo() {
		return handOverNo;
	}
	public void setHandOverNo(String handOverNo) {
		this.handOverNo = handOverNo;
	}
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public int getPieces() {
		return pieces;
	}
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public String getLargeUpstrairs() {
		return largeUpstrairs;
	}
	public void setLargeUpstrairs(String largeUpstrairs) {
		this.largeUpstrairs = largeUpstrairs;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getPackageRemark() {
		return packageRemark;
	}
	public void setPackageRemark(String packageRemark) {
		this.packageRemark = packageRemark;
	}
	
	
    
}
