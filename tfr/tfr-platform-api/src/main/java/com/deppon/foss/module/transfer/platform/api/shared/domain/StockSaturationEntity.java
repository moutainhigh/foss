package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class StockSaturationEntity extends BaseEntity {

	
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:17:02
	* @version V1.0
	*/
	
	/**
	 * @update wqh
	 * @date 2015-03-12
	 * */
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 主键
	* @fields saturationId
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:21:26
	* @version V1.0
	*/
	private String saturationId;
	
	/**
	 * 外场部门code
	* @fields orgCode
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:21:36
	* @version V1.0
	*/
	private String orgCode;
	
	/**
	 * 外场部门Name
	* @fields orgName
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:21:51
	* @version V1.0
	*/
	private String orgName;
	
	/**
	 * 装车量
	* @fields loadMeasure
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:22:04
	* @version V1.0
	*/
	private BigDecimal loadMeasure;
	
	/**
	 * 卸车量
	* @fields unLoadMeasure
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:22:14
	* @version V1.0
	*/
	private BigDecimal unloadMeasure;
	
	/**
	 * 自提量
	* @fields selfSign
	* @author 14022-foss-songjie
	* @update 2014年3月27日 上午10:09:43
	* @version V1.0
	*/
	private BigDecimal selfSign;
	
	/**
	 * 派送量
	* @fields deliverSign
	* @author 14022-foss-songjie
	* @update 2014年3月27日 上午10:09:57
	* @version V1.0
	*/
	private BigDecimal deliverSign;
	
	/**
	 * 派送拉回量
	* @fields deliverBack
	* @author 14022-foss-songjie
	* @update 2014年3月27日 上午10:10:11
	* @version V1.0
	*/
	private BigDecimal deliverBack;
	
	/**
	 * 驻地营业部开单量
	* @fields stationMeasure
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:22:35
	* @version V1.0
	*/
	private BigDecimal stationMeasure;
	
	/**
	 * 集中开单量
	* @fields receiveMeasure
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:22:53
	* @version V1.0
	*/
	private BigDecimal receiveMeasure;
	
	
	/**
	 * 理论统计时间
	* @fields statisticsTimeTheory
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:23:32
	* @version V1.0
	*/
	private Date statisticsTimeTheory;
	
	
	/**
	 * 查询的开始时间
	* @fields beginDate
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:51:14
	* @version V1.0
	*/
	private String beginDate;
	
	
	/**
	 * 查询的截止时间
	* @fields endDate
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:51:28
	* @version V1.0
	*/
	private String endDate;
	
	
	/**
	 * 装车量
	* @fields loadMeasure
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:51:28
	* @version V1.0
	*/
	private BigDecimal loadMeasureAll;
	
	/**
	 * 卸车量
	* @fields unLoadMeasure
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:51:28
	* @version V1.0
	*/
	private BigDecimal unloadMeasureAll;
	
	/**
	 * 自提量
	* @fields selfSign
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:51:28
	* @version V1.0
	*/
	private BigDecimal selfSignAll;
	
	/**
	 * 派送量
	* @fields deliverSign
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:51:28
	* @version V1.0
	*/
	private BigDecimal deliverSignAll;
	
	/**
	 * 派送拉回量
	* @fields deliverBack
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:51:28
	* @version V1.0
	*/
	private BigDecimal deliverBackAll;
	
	/**
	 * 驻地营业部开单量
	* @fields stationMeasure
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:51:28
	* @version V1.0
	*/
	private BigDecimal stationMeasureAll;
	
	/**
	 * 集中开单量
	* @fields receiveMeasure
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:51:28
	* @version V1.0
	*/
	private BigDecimal receiveMeasureAll;
	
	
	/**
	 * 月承载量
	* @fields sustainDayMeasureAll
	* @author 14022-foss-songjie
	* @update 2014年4月2日 上午8:40:22
	* @version V1.0
	*/
	private BigDecimal sustainMonthMeasure;
	
	/**
	 * 日承载量
	* @fields sustainDayMeasure
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:23:09
	* @version V1.0
	*/
	private BigDecimal sustainDayMeasure;
	
	/**
	 * 危险值
	* @fields dangerrange
	* @author 14022-foss-songjie
	* @update 2014年4月24日 上午8:13:42
	* @version V1.0
	*/
	private BigDecimal dangerrange;
	
	/**
	 * 警戒值
	* @fields warnrange
	* @author 14022-foss-songjie
	* @update 2014年4月24日 上午8:13:44
	* @version V1.0
	*/
	private BigDecimal warnrange;
	
	/**
	 * 是否发送了短信
	* @fields smssendflag
	* @author 14022-foss-songjie
	* @update 2014年4月24日 上午8:13:47
	* @version V1.0
	*/
	private String smssendflag;
	
	/**
	 * 日操作货量
	* @fields operateMeasureDay
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:05:24
	* @version V1.0
	*/
	private BigDecimal operateMeasureDay;
	
	/**
	 * 月操作货量
	* @fields operateMeasureMonth
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:05:55
	* @version V1.0
	*/
	private BigDecimal operateMeasureMonth;
	
	/**
	 * 日饱和度
	* @fields saturationDay
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:05:39
	* @version V1.0
	*/
	private BigDecimal saturationDay;
	
	
	/**
	 * 月饱和度
	* @fields saturationMonth
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:06:09
	* @version V1.0
	*/
	private BigDecimal saturationMonth;
	
	
	/**
	* @fields bigdept 本部  division 事业部
	* @author 14022-foss-songjie
	* @update  2014年3月31日 下午3:06:09
	* @version V1.0
	*/
	private String bigdept,division;
	
	
	/**
	 * 曲线统计时间
	* @fields timeStatisticsGroup
	* @author 14022-foss-songjie
	* @update 2014年4月3日 上午11:08:22
	* @version V1.0
	*/
	private String timeStatisticsGroup;
	
	
	//当月预警天数
	private int warningMothDayCount;
	
	//当月危险预警天数
	private int dangerMothDayCount;
	
	
	/**
	* @description 主键
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:23:45
	*/
	
	
	public String getSaturationId() {
		return saturationId;
	}
	
	/**
	* @description 主键
	* @param saturationId
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:23:53
	*/
	public void setSaturationId(String saturationId) {
		this.saturationId = saturationId;
	}
	
	/**
	* @description 外场部门code
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:24:18
	*/
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	* @description 外场部门code
	* @param orgCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:24:21
	*/
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	* @description 外场部门name
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:24:34
	*/
	public String getOrgName() {
		return orgName;
	}
	
	/**
	* @description 外场部门name
	* @param orgName
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:24:36
	*/
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	/**
	* @description 装车量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:25:10
	*/
	public BigDecimal getLoadMeasure() {
		return loadMeasure;
	}
	
	/**
	* @description 装车量
	* @param loadMeasure
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:25:12
	*/
	public void setLoadMeasure(BigDecimal loadMeasure) {
		this.loadMeasure = loadMeasure;
	}
	
	/**
	* @description 卸车量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:25:20
	*/
	public BigDecimal getUnloadMeasure() {
		return unloadMeasure;
	}
	
	/**
	* @description 卸车量
	* @param unLoadMeasure
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:25:22
	*/
	public void setUnloadMeasure(BigDecimal unLoadMeasure) {
		this.unloadMeasure = unLoadMeasure;
	}
	
	
	/**
	* @description 自提量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月27日 上午10:11:06
	*/
	public BigDecimal getSelfSign() {
		return selfSign;
	}

	
	/**
	* @description 自提量
	* @param selfSign
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月27日 上午10:11:18
	*/
	public void setSelfSign(BigDecimal selfSign) {
		this.selfSign = selfSign;
	}

	
	/**
	* @description 派送量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月27日 上午10:11:29
	*/
	public BigDecimal getDeliverSign() {
		return deliverSign;
	}

	
	/**
	* @description 派送量
	* @param deliverSign
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月27日 上午10:11:31
	*/
	public void setDeliverSign(BigDecimal deliverSign) {
		this.deliverSign = deliverSign;
	}

	
	/**
	* @description 派送拉回
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月27日 上午10:11:43
	*/
	public BigDecimal getDeliverBack() {
		return deliverBack;
	}

	
	/**
	* @description 派送拉回
	* @param deliverBack
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月27日 上午10:11:46
	*/
	public void setDeliverBack(BigDecimal deliverBack) {
		this.deliverBack = deliverBack;
	}

	/**
	* @description 驻地营业部开单量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:26:17
	*/
	public BigDecimal getStationMeasure() {
		return stationMeasure;
	}
	
	/**
	* @description 驻地营业部开单量
	* @param stationMeasure
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:26:19
	*/
	public void setStationMeasure(BigDecimal stationMeasure) {
		this.stationMeasure = stationMeasure;
	}
	
	/**
	* @description 集中开单量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:26:37
	*/
	public BigDecimal getReceiveMeasure() {
		return receiveMeasure;
	}
	
	/**
	* @description 集中开单量
	* @param receiveMeasure
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:26:39
	*/
	public void setReceiveMeasure(BigDecimal receiveMeasure) {
		this.receiveMeasure = receiveMeasure;
	}
	
	/**
	* @description 日承载量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:26:57
	*/
	public BigDecimal getSustainDayMeasure() {
		return sustainDayMeasure;
	}
	
	/**
	* @description 日承载量
	* @param sustainDayMeasure
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:26:59
	*/
	public void setSustainDayMeasure(BigDecimal sustainDayMeasure) {
		this.sustainDayMeasure = sustainDayMeasure;
	}
	
	/**
	* @description 理论统计时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:27:19
	*/
	public Date getStatisticsTimeTheory() {
		return statisticsTimeTheory;
	}
	
	/**
	* @description 理论统计时间
	* @param statisticsTimeTheory
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:27:21
	*/
	public void setStatisticsTimeTheory(Date statisticsTimeTheory) {
		this.statisticsTimeTheory = statisticsTimeTheory;
	}

	
	/**
	* @description 查询开始日期
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:55:25
	*/
	public String getBeginDate() {
		return beginDate;
	}

	
	/**
	* @description 查询开始日期
	* @param beginDate
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:55:28
	*/
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	
	/**
	* @description 查询截至日期
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:55:54
	*/
	public String getEndDate() {
		return endDate;
	}

	
	/**
	* @description 查询截至日期
	* @param endDate
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:55:57
	*/
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	/**
	* @description 装车量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:56:29
	*/
	public BigDecimal getLoadMeasureAll() {
		return loadMeasureAll;
	}

	
	/**
	* @description 装车量
	* @param loadMeasureAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:56:33
	*/
	public void setLoadMeasureAll(BigDecimal loadMeasureAll) {
		this.loadMeasureAll = loadMeasureAll;
	}

	
	/**
	* @description 卸车量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:58:26
	*/
	public BigDecimal getUnloadMeasureAll() {
		return unloadMeasureAll;
	}

	
	/**
	* @description 卸车量
	* @param unloadMeasureAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:58:41
	*/
	public void setUnloadMeasureAll(BigDecimal unloadMeasureAll) {
		this.unloadMeasureAll = unloadMeasureAll;
	}

	
	/**
	* @description 自提签收量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:58:53
	*/
	public BigDecimal getSelfSignAll() {
		return selfSignAll;
	}

	
	/**
	* @description 自提签收量
	* @param selfSignAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:58:56
	*/
	public void setSelfSignAll(BigDecimal selfSignAll) {
		this.selfSignAll = selfSignAll;
	}

	
	/**
	* @description 派送签收量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:59:17
	*/
	public BigDecimal getDeliverSignAll() {
		return deliverSignAll;
	}

	
	/**
	* @description 派送签收量
	* @param deliverSignAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:59:20
	*/
	public void setDeliverSignAll(BigDecimal deliverSignAll) {
		this.deliverSignAll = deliverSignAll;
	}

	
	/**
	* @description 派送拉回量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:59:50
	*/
	public BigDecimal getDeliverBackAll() {
		return deliverBackAll;
	}

	
	/**
	* @description 派送拉回量
	* @param deliverBackAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午2:59:53
	*/
	public void setDeliverBackAll(BigDecimal deliverBackAll) {
		this.deliverBackAll = deliverBackAll;
	}

	
	/**
	* @description 驻地营业部开单量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:00:17
	*/
	public BigDecimal getStationMeasureAll() {
		return stationMeasureAll;
	}

	
	/**
	* @description 驻地营业部开单量
	* @param stationMeasureAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:00:21
	*/
	public void setStationMeasureAll(BigDecimal stationMeasureAll) {
		this.stationMeasureAll = stationMeasureAll;
	}

	
	/**
	* @description 集中开单量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:00:39
	*/
	public BigDecimal getReceiveMeasureAll() {
		return receiveMeasureAll;
	}

	
	/**
	* @description 集中开单量
	* @param receiveMeasureAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:00:43
	*/
	public void setReceiveMeasureAll(BigDecimal receiveMeasureAll) {
		this.receiveMeasureAll = receiveMeasureAll;
	}

	
	/**
	* @description 日操作货量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:06:42
	*/
	public BigDecimal getOperateMeasureDay() {
		return operateMeasureDay;
	}

	
	/**
	* @description 日操作货量
	* @param operateMeasureDay
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:06:46
	*/
	public void setOperateMeasureDay(BigDecimal operateMeasureDay) {
		this.operateMeasureDay = operateMeasureDay;
	}

	
	/**
	* @description 日饱和度
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:06:49
	*/
	public BigDecimal getSaturationDay() {
		return saturationDay;
	}

	
	/**
	* @description 日饱和度
	* @param saturationDay
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:06:52
	*/
	public void setSaturationDay(BigDecimal saturationDay) {
		this.saturationDay = saturationDay;
	}

	
	/**
	* @description 月操作货量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:06:54
	*/
	public BigDecimal getOperateMeasureMonth() {
		return operateMeasureMonth;
	}

	
	/**
	* @description 月操作货量
	* @param operateMeasureMonth
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:06:58
	*/
	public void setOperateMeasureMonth(BigDecimal operateMeasureMonth) {
		this.operateMeasureMonth = operateMeasureMonth;
	}

	
	/**
	* @description 月饱和度
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:07:02
	*/
	public BigDecimal getSaturationMonth() {
		return saturationMonth;
	}

	
	/**
	* @description 月饱和度
	* @param saturationMonth
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:07:05
	*/
	public void setSaturationMonth(BigDecimal saturationMonth) {
		this.saturationMonth = saturationMonth;
	}

	
	/**
	* @description 本部
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:09:28
	*/
	public String getBigdept() {
		return bigdept;
	}

	
	/**
	* @description 本部
	* @param bigdept
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:09:31
	*/
	public void setBigdept(String bigdept) {
		this.bigdept = bigdept;
	}

	
	/**
	* @description  事业部
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:09:54
	*/
	public String getDivision() {
		return division;
	}

	
	/**
	* @description  事业部
	* @param division
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月31日 下午3:09:59
	*/
	public void setDivision(String division) {
		this.division = division;
	}
	
	/**
	* @description 月承载量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年7月3日 上午10:35:16
	*/
	public BigDecimal getSustainMonthMeasure() {
		return sustainMonthMeasure;
	}

	
	/**
	* @description 月承载量
	* @param sustainMonthMeasure
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年7月3日 上午10:35:21
	*/
	public void setSustainMonthMeasure(BigDecimal sustainMonthMeasure) {
		this.sustainMonthMeasure = sustainMonthMeasure;
	}

	/**
	* @description 曲线统计时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月3日 上午11:08:49
	*/
	public String getTimeStatisticsGroup() {
		return timeStatisticsGroup;
	}

	
	/**
	* @description 曲线统计时间
	* @param timeStatisticsGroup
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月3日 上午11:08:52
	*/
	public void setTimeStatisticsGroup(String timeStatisticsGroup) {
		this.timeStatisticsGroup = timeStatisticsGroup;
	}

	
	/**
	* @description 危险值
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月24日 上午8:14:36
	*/
	public BigDecimal getDangerrange() {
		return dangerrange;
	}

	
	/**
	* @description 危险值
	* @param dangerrange
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月24日 上午8:14:39
	*/
	public void setDangerrange(BigDecimal dangerrange) {
		this.dangerrange = dangerrange;
	}

	
	/**
	* @description 警戒值
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月24日 上午8:15:02
	*/
	public BigDecimal getWarnrange() {
		return warnrange;
	}

	
	/**
	* @description 警戒值
	* @param warnrange
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月24日 上午8:15:05
	*/
	public void setWarnrange(BigDecimal warnrange) {
		this.warnrange = warnrange;
	}

	
	/**
	* @description 是否发送过短信
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月24日 上午8:15:24
	*/
	public String getSmssendflag() {
		return smssendflag;
	}

	
	/**
	* @description 是否发送过短信
	* @param smssendflag
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月24日 上午8:15:28
	*/
	public void setSmssendflag(String smssendflag) {
		this.smssendflag = smssendflag;
	}

	/**
	 * @return the warningMothDayCount
	 */
	public int getWarningMothDayCount() {
		return warningMothDayCount;
	}

	/**
	 * @param warningMothDayCount the warningMothDayCount to set
	 */
	public void setWarningMothDayCount(int warningMothDayCount) {
		this.warningMothDayCount = warningMothDayCount;
	}

	/**
	 * @return the dangerMothDayCount
	 */
	public int getDangerMothDayCount() {
		return dangerMothDayCount;
	}

	/**
	 * @param dangerMothDayCount the dangerMothDayCount to set
	 */
	public void setDangerMothDayCount(int dangerMothDayCount) {
		this.dangerMothDayCount = dangerMothDayCount;
	}
	
}
