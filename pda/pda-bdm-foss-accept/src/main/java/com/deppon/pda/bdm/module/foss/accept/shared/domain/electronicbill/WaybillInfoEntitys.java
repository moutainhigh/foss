package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

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
public class WaybillInfoEntitys implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	// 包装信息
    private String packInfo;
	//单号
	private String wblCode;
	//件数
	private int pieces;
	//重量
	private BigDecimal weight;
	//体积
	private BigDecimal volume;
	//大件上楼 [取值为Y  或者N]
	private String largeUpstairs;
	//扫描时间
	private Date scanTime;
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
	public String getLargeUpstairs() {
		return largeUpstairs;
	}
	public void setLargeUpstairs(String largeUpstairs) {
		this.largeUpstairs = largeUpstairs;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getPackInfo() {
		return packInfo;
	}
	public void setPackInfo(String packInfo) {
		this.packInfo = packInfo;
	}
	
}
