/**   
* @Title: QuantityStaDto.java 
* @Package com.deppon.foss.module.transfer.platform.api.shared.dto 
* @Description: 货量统计dto
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年8月27日 上午10:35:27 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * @ClassName: QuantityStaDto 
 * @Description: 货量统计dto
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年8月27日 上午10:35:27 
 *  
 */
public class QuantityStaDto implements Serializable{
	
	private static final long serialVersionUID = -1491556862299197458L;

	/**
	 * id
	 */
	private String id;
	
	/**
	 * 类型（出发、到达）
	 */
	private String type;
	/**
	 * 具体类型（出发：本地出发、二次中转、到达中转、派送货量；到达：长途到达、短途到达、集中接货）
	 */
	private String particularType;
	/**
	 * 站点组ID（用于界面货量分组）
	 */
	private String groupSiteId;
	/**
	 * 站点组名称
	 */
	private String groupSiteName;
	/**
	 * 部门编码
	 */
	private String orgCode;
	/**
	 * 上一（下一）部门编码
	 */
	private String relevantOrgCode;
	/**
	 * 上一（下一）部门名称
	 */
	private String relevantOrgName;
	/**
	 * 总重量
	 */
	private BigDecimal weightTotal;
	/**
	 * 总体积
	 */
	private BigDecimal volumeTotal;
	/**
	 * 总票数
	 */
	private BigDecimal waybillQtyTotal;
	/**
	 * 卡航重量
	 */
	private BigDecimal flfWeightTotal;
	/**
	 * 卡航体积
	 */
	private BigDecimal flfVolumeTotal;
	/**
	 * 卡航票数
	 */
	private BigDecimal flfQtyTotal;
	/**
	 * 城运重量
	 */
	private BigDecimal fsfWeightTotal;
	/**
	 * 城运体积
	 */
	private BigDecimal fsfVolumeTotal;
	/**
	 * 城运票数
	 */
	private BigDecimal fsfQtyTotal;
	/**
	 * 快递重量
	 */
	private BigDecimal expWeightTotal;
	/**
	 * 快递体积
	 */
	private BigDecimal expVolumeTotal;
	/**
	 * 快递票数
	 */
	private BigDecimal expQtyTotal;
	/**
	 * 库存重量
	 */
	private BigDecimal instoreWeightTotal;
	/**
	 * 库存体积
	 */
	private BigDecimal instoreVolumeTotal;
	/**
	 * 库存票数
	 */
	private BigDecimal instoreQtyTotal;
	/**
	 * 开单重量
	 */
	private BigDecimal billedWeightTotal;
	/**
	 * 开单体积
	 */
	private BigDecimal billedVolumeTotal;
	/**
	 * 开单票数
	 */
	private BigDecimal billedQtyTotal;
	/**
	 * 在途重量
	 */
	private BigDecimal intransitWeightTotal;
	/**
	 * 在途体积
	 */
	private BigDecimal intransitVolumeTotal;
	/**
	 * 在途票数
	 */
	private BigDecimal intransitQtyTotal;
	/**
	 * 预测未开单重量
	 */
	private BigDecimal forecastUnbilledWeightTotal;
	/**
	 * 预测未开单体积
	 */
	private BigDecimal forecastUnbilledVolumeTotal;
	/**
	 * 预测未开单票数
	 */
	private BigDecimal forecastUnbilledQtyTotal;
	/**
	 * 预测总重量
	 */
	private BigDecimal forecastWeightTotal;
	/**
	 * 预测总体积
	 */
	private BigDecimal forecastVolumeTotal;
	/**
	 * 预测总票数
	 */
	private BigDecimal forecastQtyTotal;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 货量所属日期
	 */
	private Date dataBelongDate;
	/**
	 * 统计日期
	 */
	private Date staDate;
	/**
	 * 统计时间点（整点，格式为HHMM，例如下午1点的货量表示为1300）
	 */
	private int staHH;
	
	/**
	 * 调整的货量
	 */
	private BigDecimal adjustVolume;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParticularType() {
		return particularType;
	}

	public void setParticularType(String particularType) {
		this.particularType = particularType;
	}

	public String getGroupSiteId() {
		return groupSiteId;
	}

	public void setGroupSiteId(String groupSiteId) {
		this.groupSiteId = groupSiteId;
	}

	public String getGroupSiteName() {
		return groupSiteName;
	}

	public void setGroupSiteName(String groupSiteName) {
		this.groupSiteName = groupSiteName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRelevantOrgCode() {
		return relevantOrgCode;
	}

	public void setRelevantOrgCode(String relevantOrgCode) {
		this.relevantOrgCode = relevantOrgCode;
	}

	public String getRelevantOrgName() {
		return relevantOrgName;
	}

	public void setRelevantOrgName(String relevantOrgName) {
		this.relevantOrgName = relevantOrgName;
	}

	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	public BigDecimal getWaybillQtyTotal() {
		return waybillQtyTotal;
	}

	public void setWaybillQtyTotal(BigDecimal waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}

	public BigDecimal getFlfWeightTotal() {
		return flfWeightTotal;
	}

	public void setFlfWeightTotal(BigDecimal flfWeightTotal) {
		this.flfWeightTotal = flfWeightTotal;
	}

	public BigDecimal getFlfVolumeTotal() {
		return flfVolumeTotal;
	}

	public void setFlfVolumeTotal(BigDecimal flfVolumeTotal) {
		this.flfVolumeTotal = flfVolumeTotal;
	}

	public BigDecimal getFlfQtyTotal() {
		return flfQtyTotal;
	}

	public void setFlfQtyTotal(BigDecimal flfQtyTotal) {
		this.flfQtyTotal = flfQtyTotal;
	}

	public BigDecimal getFsfWeightTotal() {
		return fsfWeightTotal;
	}

	public void setFsfWeightTotal(BigDecimal fsfWeightTotal) {
		this.fsfWeightTotal = fsfWeightTotal;
	}

	public BigDecimal getFsfVolumeTotal() {
		return fsfVolumeTotal;
	}

	public void setFsfVolumeTotal(BigDecimal fsfVolumeTotal) {
		this.fsfVolumeTotal = fsfVolumeTotal;
	}

	public BigDecimal getFsfQtyTotal() {
		return fsfQtyTotal;
	}

	public void setFsfQtyTotal(BigDecimal fsfQtyTotal) {
		this.fsfQtyTotal = fsfQtyTotal;
	}

	public BigDecimal getExpWeightTotal() {
		return expWeightTotal;
	}

	public void setExpWeightTotal(BigDecimal expWeightTotal) {
		this.expWeightTotal = expWeightTotal;
	}

	public BigDecimal getExpVolumeTotal() {
		return expVolumeTotal;
	}

	public void setExpVolumeTotal(BigDecimal expVolumeTotal) {
		this.expVolumeTotal = expVolumeTotal;
	}

	public BigDecimal getExpQtyTotal() {
		return expQtyTotal;
	}

	public void setExpQtyTotal(BigDecimal expQtyTotal) {
		this.expQtyTotal = expQtyTotal;
	}

	public BigDecimal getInstoreWeightTotal() {
		return instoreWeightTotal;
	}

	public void setInstoreWeightTotal(BigDecimal instoreWeightTotal) {
		this.instoreWeightTotal = instoreWeightTotal;
	}

	public BigDecimal getInstoreVolumeTotal() {
		return instoreVolumeTotal;
	}

	public void setInstoreVolumeTotal(BigDecimal instoreVolumeTotal) {
		this.instoreVolumeTotal = instoreVolumeTotal;
	}

	public BigDecimal getInstoreQtyTotal() {
		return instoreQtyTotal;
	}

	public void setInstoreQtyTotal(BigDecimal instoreQtyTotal) {
		this.instoreQtyTotal = instoreQtyTotal;
	}

	public BigDecimal getBilledWeightTotal() {
		return billedWeightTotal;
	}

	public void setBilledWeightTotal(BigDecimal billedWeightTotal) {
		this.billedWeightTotal = billedWeightTotal;
	}

	public BigDecimal getBilledVolumeTotal() {
		return billedVolumeTotal;
	}

	public void setBilledVolumeTotal(BigDecimal billedVolumeTotal) {
		this.billedVolumeTotal = billedVolumeTotal;
	}

	public BigDecimal getBilledQtyTotal() {
		return billedQtyTotal;
	}

	public void setBilledQtyTotal(BigDecimal billedQtyTotal) {
		this.billedQtyTotal = billedQtyTotal;
	}

	public BigDecimal getIntransitWeightTotal() {
		return intransitWeightTotal;
	}

	public void setIntransitWeightTotal(BigDecimal intransitWeightTotal) {
		this.intransitWeightTotal = intransitWeightTotal;
	}

	public BigDecimal getIntransitVolumeTotal() {
		return intransitVolumeTotal;
	}

	public void setIntransitVolumeTotal(BigDecimal intransitVolumeTotal) {
		this.intransitVolumeTotal = intransitVolumeTotal;
	}

	public BigDecimal getIntransitQtyTotal() {
		return intransitQtyTotal;
	}

	public void setIntransitQtyTotal(BigDecimal intransitQtyTotal) {
		this.intransitQtyTotal = intransitQtyTotal;
	}

	public BigDecimal getForecastUnbilledWeightTotal() {
		return forecastUnbilledWeightTotal;
	}

	public void setForecastUnbilledWeightTotal(
			BigDecimal forecastUnbilledWeightTotal) {
		this.forecastUnbilledWeightTotal = forecastUnbilledWeightTotal;
	}

	public BigDecimal getForecastUnbilledVolumeTotal() {
		return forecastUnbilledVolumeTotal;
	}

	public void setForecastUnbilledVolumeTotal(
			BigDecimal forecastUnbilledVolumeTotal) {
		this.forecastUnbilledVolumeTotal = forecastUnbilledVolumeTotal;
	}

	public BigDecimal getForecastUnbilledQtyTotal() {
		return forecastUnbilledQtyTotal;
	}

	public void setForecastUnbilledQtyTotal(BigDecimal forecastUnbilledQtyTotal) {
		this.forecastUnbilledQtyTotal = forecastUnbilledQtyTotal;
	}

	public BigDecimal getForecastWeightTotal() {
		return forecastWeightTotal;
	}

	public void setForecastWeightTotal(BigDecimal forecastWeightTotal) {
		this.forecastWeightTotal = forecastWeightTotal;
	}

	public BigDecimal getForecastVolumeTotal() {
		return forecastVolumeTotal;
	}

	public void setForecastVolumeTotal(BigDecimal forecastVolumeTotal) {
		this.forecastVolumeTotal = forecastVolumeTotal;
	}

	public BigDecimal getForecastQtyTotal() {
		return forecastQtyTotal;
	}

	public void setForecastQtyTotal(BigDecimal forecastQtyTotal) {
		this.forecastQtyTotal = forecastQtyTotal;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDataBelongDate() {
		return dataBelongDate;
	}

	public void setDataBelongDate(Date dataBelongDate) {
		this.dataBelongDate = dataBelongDate;
	}

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public int getStaHH() {
		return staHH;
	}

	public void setStaHH(int staHH) {
		this.staHH = staHH;
	}

	public BigDecimal getAdjustVolume() {
		return adjustVolume;
	}

	public void setAdjustVolume(BigDecimal adjustVolume) {
		this.adjustVolume = adjustVolume;
	}

	@Override
	public String toString() {
		return "QuantityStaDto [id=" + id + ", type=" + type
				+ ", particularType=" + particularType + ", groupSiteId="
				+ groupSiteId + ", groupSiteName=" + groupSiteName
				+ ", orgCode=" + orgCode + ", relevantOrgCode="
				+ relevantOrgCode + ", relevantOrgName=" + relevantOrgName
				+ ", weightTotal=" + weightTotal + ", volumeTotal="
				+ volumeTotal + ", waybillQtyTotal=" + waybillQtyTotal
				+ ", flfWeightTotal=" + flfWeightTotal + ", flfVolumeTotal="
				+ flfVolumeTotal + ", flfQtyTotal=" + flfQtyTotal
				+ ", fsfWeightTotal=" + fsfWeightTotal + ", fsfVolumeTotal="
				+ fsfVolumeTotal + ", fsfQtyTotal=" + fsfQtyTotal
				+ ", expWeightTotal=" + expWeightTotal + ", expVolumeTotal="
				+ expVolumeTotal + ", expQtyTotal=" + expQtyTotal
				+ ", instoreWeightTotal=" + instoreWeightTotal
				+ ", instoreVolumeTotal=" + instoreVolumeTotal
				+ ", instoreQtyTotal=" + instoreQtyTotal
				+ ", billedWeightTotal=" + billedWeightTotal
				+ ", billedVolumeTotal=" + billedVolumeTotal
				+ ", billedQtyTotal=" + billedQtyTotal
				+ ", intransitWeightTotal=" + intransitWeightTotal
				+ ", intransitVolumeTotal=" + intransitVolumeTotal
				+ ", intransitQtyTotal=" + intransitQtyTotal
				+ ", forecastUnbilledWeightTotal="
				+ forecastUnbilledWeightTotal
				+ ", forecastUnbilledVolumeTotal="
				+ forecastUnbilledVolumeTotal + ", forecastUnbilledQtyTotal="
				+ forecastUnbilledQtyTotal + ", forecastWeightTotal="
				+ forecastWeightTotal + ", forecastVolumeTotal="
				+ forecastVolumeTotal + ", forecastQtyTotal="
				+ forecastQtyTotal + ", createTime=" + createTime
				+ ", dataBelongDate=" + dataBelongDate + ", staDate=" + staDate
				+ ", staHH=" + staHH + ", adjustVolume=" + adjustVolume + "]";
	}

	
	
}
