package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
* @description 派送率统计实体
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 下午4:09:48
*/
public class SendRateEntity extends BaseEntity {
	
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午3:01:53
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	* @fields sendRateId
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:18:18
	* @version V1.0
	*/
	private String sendRateId;
	
	/**
	 * 部门code
	* @fields orgCode
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:18:25
	* @version V1.0
	*/
	private String orgCode;
	
	/**
	 * 部门名称Name
	* @fields orgName
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:18:37
	* @version V1.0
	*/
	private String orgName;
	
	/**
	 * 货区Code
	* @fields goodsAreaCode
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:19:15
	* @version V1.0
	*/
	private String goodsAreaCode;
	
	/**
	 * 前一日的剩余库存体积
	* @fields yesterdayStockVolume
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:19:29
	* @version V1.0
	*/
	private BigDecimal yesterdayStockVolume;
	
	/**
	 * 前一日的剩余库存重量
	* @fields yesterdayStockWeight
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:20:00
	* @version V1.0
	*/
	private BigDecimal yesterdayStockWeight;
	
	/**
	 * 前一日的剩余库存量
	* @fields yesterdayStockWaybill
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:20:14
	* @version V1.0
	*/
	private Long yesterdayStockWaybill;
	
	/**
	 * 当日库存体积
	* @fields dayStockVolume
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:23:21
	* @version V1.0
	*/
	private BigDecimal dayStockVolume;
	
	/**
	 * 当日库存重量
	* @fields dayStockWeight
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:23:27
	* @version V1.0
	*/
	private BigDecimal dayStockWeight;
	
	/**
	 * 当日库存量
	* @fields dayStockWaybill
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:23:30
	* @version V1.0
	*/
	private Long dayStockWaybill;
	
	/**
	 * 派送的体积
	* @fields daySendVolume
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:23:32
	* @version V1.0
	*/
	private BigDecimal daySendVolume;
	
	/**
	 * 派送的重量
	* @fields daySendWeight
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:23:33
	* @version V1.0
	*/
	private BigDecimal daySendWeight;
	
	/**
	 * 派送量
	* @fields daySendWaybill
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:23:35
	* @version V1.0
	*/
	private Long daySendWaybill;
	
	/**
	 * 预计到达库存体积
	* @fields tomorrowStockVolume
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:23:38
	* @version V1.0
	*/
	private BigDecimal tomorrowStockVolume;
	
	/**
	 * 预计到达库存重量
	* @fields tomorrowStockWeight
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:23:42
	* @version V1.0
	*/
	private BigDecimal tomorrowStockWeight;
	
	/**
	 * 预计到达库存量
	* @fields tomorrowStockWaybill
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:23:45
	* @version V1.0
	*/
	private Long tomorrowStockWaybill;
	
	/**
	 * 理论统计时间
	* @fields statisticsTimeTheory
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:23:48
	* @version V1.0
	*/
	private Date statisticsTimeTheory;
	
	/**
	 * 实际统计时间
	* @fields statisticsTimeReality
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:23:50
	* @version V1.0
	*/
	private Date statisticsTimeReality;
	
	/**
	 * 派送率
	* @fields sendRate
	* @author 14022-foss-songjie
	* @update 2014年2月28日 下午4:23:53
	* @version V1.0
	*/
	private BigDecimal sendRate;
	
	
	/**
	 * 预测货量体积
	* @fields remainDayStockVolume
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:27:37
	* @version V1.0
	*/
	private BigDecimal remainDayStockVolume;
	
	/**
	 * 预测货量重量
	* @fields remainDayStockWeight
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:27:40
	* @version V1.0
	*/
	private BigDecimal remainDayStockWeight;
	
	/**
	 * 预测货量
	* @fields remainStockWaybill
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:27:42
	* @version V1.0
	*/
	
	private Long remainStockWaybill;
	
	
	/**
	 * 查询的开始时间
	* @fields beginDate
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午4:25:21
	* @version V1.0
	*/
	private String beginDate;
	
	
	/**
	 * 查询的截止时间
	* @fields endDate
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午4:25:24
	* @version V1.0
	*/
	private String endDate;
	
	
	/**
	 * 全国统计时 对应外场的入库总量
	* @fields dayStockVolumeAll
	* @author 14022-foss-songjie
	* @update 2014年3月12日 下午2:22:03
	* @version V1.0
	*/
	private BigDecimal dayStockVolumeAll;
	
	/**
	 * 全国统计时 对应外场 派送总量
	* @fields daySendVolumeAll
	* @author 14022-foss-songjie
	* @update 2014年3月12日 下午2:22:06
	* @version V1.0
	*/
	private BigDecimal daySendVolumeAll;
	
	/**
	 * 全国统计时 对应外场的 前一日的库存剩余量
	* @fields yesterdayStockVolumeAll
	* @author 14022-foss-songjie
	* @update 2014年3月12日 下午2:22:09
	* @version V1.0
	*/
	private BigDecimal yesterdayStockVolumeAll;
	
	
	/**
	 * 月派送率
	* @fields sendRateAll
	* @author 14022-foss-songjie
	* @update 2014年3月12日 下午4:02:03
	* @version V1.0
	*/
	private BigDecimal sendRateAll;
	
	
	/**
	* @fields bigdept 本部  division 事业部
	* @author 14022-foss-songjie
	* @update 2014年3月13日 下午6:24:58
	* @version V1.0
	*/
	private String bigdept,division;
	
	
	/**
	 * 查询数据的系统时间
	* @fields dataTimeQuery
	* @author 14022-foss-songjie
	* @update 2014年3月19日 上午11:24:22
	* @version V1.0
	*/
	private String dataTimeQuery;
	
	public String getSendRateId() {
		return sendRateId;
	}
	public void setSendRateId(String sendRateId) {
		this.sendRateId = sendRateId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	public BigDecimal getYesterdayStockVolume() {
		return yesterdayStockVolume;
	}
	public void setYesterdayStockVolume(BigDecimal yesterdayStockVolume) {
		this.yesterdayStockVolume = yesterdayStockVolume;
	}
	public BigDecimal getYesterdayStockWeight() {
		return yesterdayStockWeight;
	}
	public void setYesterdayStockWeight(BigDecimal yesterdayStockWeight) {
		this.yesterdayStockWeight = yesterdayStockWeight;
	}
	public Long getYesterdayStockWaybill() {
		return yesterdayStockWaybill;
	}
	public void setYesterdayStockWaybill(Long yesterdayStockWaybill) {
		this.yesterdayStockWaybill = yesterdayStockWaybill;
	}
	public BigDecimal getDayStockVolume() {
		return dayStockVolume;
	}
	public void setDayStockVolume(BigDecimal dayStockVolume) {
		this.dayStockVolume = dayStockVolume;
	}
	public BigDecimal getDayStockWeight() {
		return dayStockWeight;
	}
	public void setDayStockWeight(BigDecimal dayStockWeight) {
		this.dayStockWeight = dayStockWeight;
	}
	public Long getDayStockWaybill() {
		return dayStockWaybill;
	}
	public void setDayStockWaybill(Long dayStockWaybill) {
		this.dayStockWaybill = dayStockWaybill;
	}
	public BigDecimal getDaySendVolume() {
		return daySendVolume;
	}
	public void setDaySendVolume(BigDecimal daySendVolume) {
		this.daySendVolume = daySendVolume;
	}
	public BigDecimal getDaySendWeight() {
		return daySendWeight;
	}
	public void setDaySendWeight(BigDecimal daySendWeight) {
		this.daySendWeight = daySendWeight;
	}
	public Long getDaySendWaybill() {
		return daySendWaybill;
	}
	public void setDaySendWaybill(Long daySendWaybill) {
		this.daySendWaybill = daySendWaybill;
	}
	public BigDecimal getTomorrowStockVolume() {
		return tomorrowStockVolume;
	}
	public void setTomorrowStockVolume(BigDecimal tomorrowStockVolume) {
		this.tomorrowStockVolume = tomorrowStockVolume;
	}
	public BigDecimal getTomorrowStockWeight() {
		return tomorrowStockWeight;
	}
	public void setTomorrowStockWeight(BigDecimal tomorrowStockWeight) {
		this.tomorrowStockWeight = tomorrowStockWeight;
	}
	public Long getTomorrowStockWaybill() {
		return tomorrowStockWaybill;
	}
	public void setTomorrowStockWaybill(Long tomorrowStockWaybill) {
		this.tomorrowStockWaybill = tomorrowStockWaybill;
	}
	public Date getStatisticsTimeTheory() {
		return statisticsTimeTheory;
	}
	public void setStatisticsTimeTheory(Date statisticsTimeTheory) {
		this.statisticsTimeTheory = statisticsTimeTheory;
	}
	public Date getStatisticsTimeReality() {
		return statisticsTimeReality;
	}
	public void setStatisticsTimeReality(Date statisticsTimeReality) {
		this.statisticsTimeReality = statisticsTimeReality;
	}
	public BigDecimal getSendRate() {
		return sendRate;
	}
	public void setSendRate(BigDecimal sendRate) {
		this.sendRate = sendRate;
	}
	public BigDecimal getRemainDayStockVolume() {
		return remainDayStockVolume;
	}
	public void setRemainDayStockVolume(BigDecimal remainDayStockVolume) {
		this.remainDayStockVolume = remainDayStockVolume;
	}
	public BigDecimal getRemainDayStockWeight() {
		return remainDayStockWeight;
	}
	public void setRemainDayStockWeight(BigDecimal remainDayStockWeight) {
		this.remainDayStockWeight = remainDayStockWeight;
	}
	public Long getRemainStockWaybill() {
		return remainStockWaybill;
	}
	public void setRemainStockWaybill(Long remainStockWaybill) {
		this.remainStockWaybill = remainStockWaybill;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getDayStockVolumeAll() {
		return dayStockVolumeAll;
	}
	public void setDayStockVolumeAll(BigDecimal dayStockVolumeAll) {
		this.dayStockVolumeAll = dayStockVolumeAll;
	}
	public BigDecimal getDaySendVolumeAll() {
		return daySendVolumeAll;
	}
	public void setDaySendVolumeAll(BigDecimal daySendVolumeAll) {
		this.daySendVolumeAll = daySendVolumeAll;
	}
	public BigDecimal getYesterdayStockVolumeAll() {
		return yesterdayStockVolumeAll;
	}
	public void setYesterdayStockVolumeAll(BigDecimal yesterdayStockVolumeAll) {
		this.yesterdayStockVolumeAll = yesterdayStockVolumeAll;
	}
	public BigDecimal getSendRateAll() {
		return sendRateAll;
	}
	public void setSendRateAll(BigDecimal sendRateAll) {
		this.sendRateAll = sendRateAll;
	}
	public String getBigdept() {
		return bigdept;
	}
	public void setBigdept(String bigdept) {
		this.bigdept = bigdept;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDataTimeQuery() {
		return dataTimeQuery;
	}
	public void setDataTimeQuery(String dataTimeQuery) {
		this.dataTimeQuery = dataTimeQuery;
	}
	
	
	
}
