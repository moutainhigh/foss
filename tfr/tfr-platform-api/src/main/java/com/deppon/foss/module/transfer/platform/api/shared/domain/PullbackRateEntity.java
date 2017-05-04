package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
* @description 拉回率实体
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月3日 下午3:13:51
*/
public class PullbackRateEntity extends BaseEntity {

	
	
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午3:03:37
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * 主键
	* @fields pullbackRateId
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:18:04
	* @version V1.0
	*/
	private String pullbackRateId;
	
	/**
	 * 部门code
	* @fields orgCode
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:18:08
	* @version V1.0
	*/
	private String orgCode;
	
	/**
	 * 部门Name
	* @fields orgName
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:18:11
	* @version V1.0
	*/
	private String orgName;
	
	/**
	 * 装车量
	* @fields quantityCar
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:18:24
	* @version V1.0
	*/
	private Long quantityCar;
	
	/**
	 * 拉回量
	* @fields quantityPullback
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:18:27
	* @version V1.0
	*/
	private Long quantityPullback;
	
	/**
	 * 拉回率
	* @fields pullbackRate
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:18:30
	* @version V1.0
	*/
	private Float pullbackRate;
	
	/**
	 * 理论统计时间
	* @fields statisticsTimeTheory
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:18:32
	* @version V1.0
	*/
	private Date statisticsTimeTheory;
	
	/**
	 * 货区
	* @fields goodsAreaCode
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:18:35
	* @version V1.0
	*/
	private String goodsAreaCode;

	
	/**
	 * 查询数据的系统时间
	* @fields dataTimeQuery
	* @author 14022-foss-songjie
	* @update 2014年3月7日 上午11:33:01
	* @version V1.0
	*/
	private String dataTimeQuery;
	
	/**
	 * 查询的开始时间
	* @fields beginDate
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:34:46
	* @version V1.0
	*/
	
	private String beginDate; 
	
	
	/**
	 * 外场对应的累计拉回率：累计月拉回率   /全国对应的累计拉回率：累计日拉回率 
	* @fields pullbackRateAll
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午4:15:00
	* @version V1.0
	*/
	private Float pullbackRateAll;
	
	/**
	 * 查询的截止时间
	* @fields endDate
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:34:46
	* @version V1.0
	*/
	private String endDate;
	
	
	/**
	 * 外场对应的累计拉回率： 累计 装车量   /全国对应的累计拉回率：日装车量
	* @fields quantityCar
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:18:24
	* @version V1.0
	*/
	private Long quantityCarAll;
	
	/**
	 *外场对应的累计拉回率： 累计拉回量   /全国对应的累计拉回率：日拉回量
	* @fields quantityPullback
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:18:27
	* @version V1.0
	*/
	private Long quantityPullbackAll;
	
	/**
	* @description 获取主键
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:20:46
	*/
	public String getPullbackRateId() {
		return pullbackRateId;
	}

	
	/**
	* @description 设置主键
	* @param pullbackRateId
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:20:49
	*/
	public void setPullbackRateId(String pullbackRateId) {
		this.pullbackRateId = pullbackRateId;
	}

	
	/**
	* @description 获取部门code
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:21:09
	*/
	public String getOrgCode() {
		return orgCode;
	}

	
	/**
	* @description 设置部门Code
	* @param orgCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:21:13
	*/
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	/**
	* @description 获取部门name
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:21:31
	*/
	public String getOrgName() {
		return orgName;
	}

	
	/**
	* @description 设置部门Name
	* @param orgName
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:21:33
	*/
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	
	/**
	* @description 获取装车量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:22:01
	*/
	public Long getQuantityCar() {
		return quantityCar;
	}

	
	/**
	* @description 设置装车量
	* @param quantityCar
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:22:03
	*/
	public void setQuantityCar(Long quantityCar) {
		this.quantityCar = quantityCar;
	}

	
	/**
	* @description 获取拉回量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:22:46
	*/
	public Long getQuantityPullback() {
		return quantityPullback;
	}

	
	/**
	* @description 设置拉回量
	* @param quantityPullback
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:22:48
	*/
	public void setQuantityPullback(Long quantityPullback) {
		this.quantityPullback = quantityPullback;
	}

	
	/**
	* @description 获取 拉回率
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:23:13
	*/
	public Float getPullbackRate() {
		return pullbackRate;
	}

	
	/**
	* @description 设置拉回率
	* @param pullbackRate
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:23:46
	*/
	public void setPullbackRate(Float pullbackRate) {
		this.pullbackRate = pullbackRate;
	}

	
	/**
	* @description 获取理论统计时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:24:13
	*/
	public Date getStatisticsTimeTheory() {
		return statisticsTimeTheory;
	}

	
	/**
	* @description 设置理论统计时间
	* @param statisticsTimeTheory
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:24:16
	*/
	public void setStatisticsTimeTheory(Date statisticsTimeTheory) {
		this.statisticsTimeTheory = statisticsTimeTheory;
	}

	
	/**
	* @description 获取货区code
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:24:48
	*/
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	
	/**
	* @description 设置获取code
	* @param goodsAreaCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:24:50
	*/
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}


	
	/**
	* @description 查询数据的系统时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 上午11:33:24
	*/
	public String getDataTimeQuery() {
		return dataTimeQuery;
	}


	
	/**
	* @description 设置查询数据的系统时间
	* @param dataTimeQuery
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 上午11:33:29
	*/
	public void setDataTimeQuery(String dataTimeQuery) {
		this.dataTimeQuery = dataTimeQuery;
	}


	
	/**
	* @description 获取开始时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:35:16
	*/
	public String getBeginDate() {
		return beginDate;
	}


	
	/**
	* @description 设置开始时间
	* @param beginDate
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:35:20
	*/
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}


	
	/**
	* @description 获取截至时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:35:49
	*/
	public String getEndDate() {
		return endDate;
	}


	
	/**
	* @description 设置截至时间
	* @param endDate
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:35:51
	*/
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	
	/**
	* @description 获取 外场对应的累计拉回率：累计月拉回率   /全国对应的累计拉回率：累计日拉回率 
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午4:19:53
	*/
	public Float getPullbackRateAll() {
		return pullbackRateAll;
	}


	
	/**
	* @description 设置 外场对应的累计拉回率：累计月拉回率   /全国对应的累计拉回率：累计日拉回率 
	* @param pullbackRateAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午4:19:56
	*/
	public void setPullbackRateAll(Float pullbackRateAll) {
		this.pullbackRateAll = pullbackRateAll;
	}


	
	/**
	* @description 获取  外场对应的累计拉回率： 累计 装车量   /全国对应的累计拉回率：日装车量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午4:24:15
	*/
	public Long getQuantityCarAll() {
		return quantityCarAll;
	}


	
	/**
	* @description 设置  外场对应的累计拉回率： 累计 装车量   /全国对应的累计拉回率：日装车量
	* @param quantityCarAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午4:24:24
	*/
	public void setQuantityCarAll(Long quantityCarAll) {
		this.quantityCarAll = quantityCarAll;
	}


	
	/**
	* @description 获取 外场对应的累计拉回率： 累计拉回量   /全国对应的累计拉回率：日拉回量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午4:24:46
	*/
	public Long getQuantityPullbackAll() {
		return quantityPullbackAll;
	}


	
	/**
	* @description 设置 外场对应的累计拉回率： 累计拉回量   /全国对应的累计拉回率：日拉回量
	* @param quantityPullbackAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午4:24:58
	*/
	public void setQuantityPullbackAll(Long quantityPullbackAll) {
		this.quantityPullbackAll = quantityPullbackAll;
	}
	
	
	
	
}
