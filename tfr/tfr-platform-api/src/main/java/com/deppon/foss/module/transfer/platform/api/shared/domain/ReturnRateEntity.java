package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
* @description 退单率实体
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月3日 下午3:13:36
*/
public class ReturnRateEntity extends BaseEntity{
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午3:02:56
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	
	 
	/**
	 * 主键
	* @fields returnRateId
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:29:47
	* @version V1.0
	*/
	private String returnRateId;
	 
	/**
	 * 部门code
	* @fields orgCode
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:29:48
	* @version V1.0
	*/
	private String orgCode;
	 
	/**
	 * 部门Name
	* @fields orgName
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:29:50
	* @version V1.0
	*/
	private String orgName;
	 
	/**
	 * 预排单量
	* @fields forecastWaybill
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:29:53
	* @version V1.0
	*/
	private Long forecastWaybill;
	 
	/**
	 * 实际装车量
	* @fields quantityCarReality
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:29:55
	* @version V1.0
	*/
	private Long quantityCarReality;
	 
	/**
	 * 拉回量
	* @fields quantityReturn
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:29:58
	* @version V1.0
	*/
	private Long quantityReturn;
	 
	/**
	 * 退单率
	* @fields returnRate
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:30:00
	* @version V1.0
	*/
	private Float returnRate;
	 
	/**
	 * 理论的统计时间
	* @fields statisticsTimeTheory
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:30:02
	* @version V1.0
	*/
	private Date statisticsTimeTheory;
	 
	/**
	 * 货区code
	* @fields goodsAreaCode
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:30:04
	* @version V1.0
	*/
	private String goodsAreaCode;

	
	
	/**
	 * 查询数据的系统时间
	* @fields dataTimeQuery
	* @author 14022-foss-songjie
	* @update 2014年3月7日 上午11:29:03
	* @version V1.0
	*/
	private String dataTimeQuery;
	
	/**
	 * 日的预排单量累计  累计排单量里的 日排单量 
	* @fields forecastWaybill
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午4:59:10
	* @version V1.0
	*/
	private Long forecastWaybillAll;
	 
	/**
	 * 日的实际装车量累计  累计实际装车量里的 日实际装车量
	* @fields quantityCarReality
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午4:59:10
	* @version V1.0
	*/
	private Long quantityCarRealityAll;
	 
	/**
	 * 日的拉回量累计  累计拉回量里的 日拉回量
	* @fields quantityReturn
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午4:59:10
	* @version V1.0
	*/
	private Long quantityReturnAll;
	 
	/**
	 * 日的退单率累计  累计退单率里的 日退单率
	 * 
	* @fields returnRate
	* @author 14022-foss-songjie
	* @update2014年3月17日 下午4:59:10
	* @version V1.0
	*/
	private Float returnRateAll;
	/**
	 * 查询的开始时间
	* @fields beginDate
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午4:59:10
	* @version V1.0
	*/
	
	private String beginDate; 
	/**
	 * 查询的截止时间
	* @fields endDate
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午4:59:10
	* @version V1.0
	*/
	private String endDate;
	
	//提交派送装车任务票数
	private int loadWaybillQty;
	
	
	
	/**
	* @description 获取主键ID
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:33:02
	*/
	public String getReturnRateId() {
		return returnRateId;
	}

	
	/**
	* @description 设置主键
	* @param returnRateId
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:33:06
	*/
	public void setReturnRateId(String returnRateId) {
		this.returnRateId = returnRateId;
	}

	
	/**
	* @description 获取部门code
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:33:41
	*/
	public String getOrgCode() {
		return orgCode;
	}

	
	/**
	* @description 设置部门code
	* @param orgCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:33:46
	*/
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	/**
	* @description 获取部门Name
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:34:06
	*/
	public String getOrgName() {
		return orgName;
	}

	
	/**
	* @description 设置部门Name
	* @param orgName
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:34:09
	*/
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	
	/**
	* @description 获取预排单量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:34:55
	*/
	public Long getForecastWaybill() {
		return forecastWaybill;
	}

	
	/**
	* @description 设置预排单量
	* @param forecastWaybill
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:34:59
	*/
	public void setForecastWaybill(Long forecastWaybill) {
		this.forecastWaybill = forecastWaybill;
	}

	
	/**
	* @description 获取实际装车量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:35:56
	*/
	public Long getQuantityCarReality() {
		return quantityCarReality;
	}

	
	/**
	* @description 设置实际装车量
	* @param quantityCarReality
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:36:15
	*/
	public void setQuantityCarReality(Long quantityCarReality) {
		this.quantityCarReality = quantityCarReality;
	}

	
	/**
	* @description 获取退单量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:36:33
	*/
	public Long getQuantityReturn() {
		return quantityReturn;
	}

	
	/**
	* @description 设置退单量
	* @param quantityReturn
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:37:00
	*/
	public void setQuantityReturn(Long quantityReturn) {
		this.quantityReturn = quantityReturn;
	}

	
	/**
	* @description 获取退单率
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:37:17
	*/
	public Float getReturnRate() {
		return returnRate;
	}

	
	/**
	* @description 设置退单率
	* @param returnRate
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:37:35
	*/
	public void setReturnRate(Float returnRate) {
		this.returnRate = returnRate;
	}

	
	/**
	* @description 获取理论统计时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:37:53
	*/
	public Date getStatisticsTimeTheory() {
		return statisticsTimeTheory;
	}

	
	/**
	* @description 设置理论统计时间
	* @param statisticsTimeTheory
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:38:08
	*/
	public void setStatisticsTimeTheory(Date statisticsTimeTheory) {
		this.statisticsTimeTheory = statisticsTimeTheory;
	}

	
	/**
	* @description 获取货区code
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:38:25
	*/
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	
	/**
	* @description 设置获取code
	* @param goodsAreaCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:38:40
	*/
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}


	
	/**
	* @description 获取查询数据的系统时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 上午11:29:32
	*/
	public String getDataTimeQuery() {
		return dataTimeQuery;
	}


	
	/**
	* @description 设置查询数据的系统时间
	* @param dataTimeQuery
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 上午11:29:46
	*/
	public void setDataTimeQuery(String dataTimeQuery) {
		this.dataTimeQuery = dataTimeQuery;
	}


	
	/**
	* @description 日的预排单量累计  累计排单量里的 日排单量 
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午5:12:46
	*/
	public Long getForecastWaybillAll() {
		return forecastWaybillAll;
	}


	
	/**
	* @description 日的预排单量累计  累计排单量里的 日排单量 
	* @param forecastWaybillAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午5:12:43
	*/
	public void setForecastWaybillAll(Long forecastWaybillAll) {
		this.forecastWaybillAll = forecastWaybillAll;
	}


	
	/**
	* @description 日的实际装车量累计  累计实际装车量里的 日实际装车量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午5:08:28
	*/
	public Long getQuantityCarRealityAll() {
		return quantityCarRealityAll;
	}


	
	/**
	* @description 日的拉回量累计  累计拉回量里的 日拉回量
	* @param quantityCarRealityAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午5:07:24
	*/
	public void setQuantityCarRealityAll(Long quantityCarRealityAll) {
		this.quantityCarRealityAll = quantityCarRealityAll;
	}


	
	/**
	* @description 日的拉回量累计  累计拉回量里的 日拉回量
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午5:07:20
	*/
	public Long getQuantityReturnAll() {
		return quantityReturnAll;
	}


	
	/**
	* @description 日的拉回量累计  累计拉回量里的 日拉回量
	* @param quantityReturnAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午5:03:35
	*/
	public void setQuantityReturnAll(Long quantityReturnAll) {
		this.quantityReturnAll = quantityReturnAll;
	}


	
	/**
	* @description 日的退单率累计  累计退单率里的 日退单率
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午5:03:13
	*/
	public Float getReturnRateAll() {
		return returnRateAll;
	}


	
	/**
	* @description 日的退单率累计  累计退单率里的 日退单率
	* @param returnRateAll
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午5:03:04
	*/
	public void setReturnRateAll(Float returnRateAll) {
		this.returnRateAll = returnRateAll;
	}


	
	/**
	* @description 查询的开始时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午5:02:33
	*/
	public String getBeginDate() {
		return beginDate;
	}


	
	/**
	* @description 查询的开始时间
	* @param beginDate
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午5:02:37
	*/
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	
	
	/**
	* @description 查询的截止时间
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午5:02:07
	*/
	public String getEndDate() {
		return endDate;
	}


	
	/**
	* @description 获取 查询的截止时间
	* @param endDate
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午5:02:10
	*/
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return the loadWaybillQty
	 */
	public int getLoadWaybillQty() {
		return loadWaybillQty;
	}


	/**
	 * @param loadWaybillQty the loadWaybillQty to set
	 */
	public void setLoadWaybillQty(int loadWaybillQty) {
		this.loadWaybillQty = loadWaybillQty;
	}
	
}
