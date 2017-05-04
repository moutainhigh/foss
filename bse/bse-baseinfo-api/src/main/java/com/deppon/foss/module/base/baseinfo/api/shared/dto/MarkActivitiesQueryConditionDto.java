package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkOptionsEntity;

/**
 * 提供给开单查询活动优惠信息的实体类
 * @author 078816
 * @date   2014-04-16
 *
 */
public class MarkActivitiesQueryConditionDto extends MarkActivitiesEntity
		implements Serializable { 

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7833286009095027096L;
	
	//折扣信息的crmID
//	private BigDecimal discountCrmId;
	//折扣类型
//	private String discountName;
	
	//折扣值
//	private BigDecimal discountValue;
	//折扣列表
	List<MarkOptionsEntity> optionList;

	//开单品名
	private String goodsName;
	
	//一级行业
	private String firstTrade;
	
	//二级行业
	private String secondeTrade;
	
	//订单来源来源
	private String orderResource;
	
	//产品类型
	private String productType;
	
	//开展部门
	private String developDeptCode;
	
	//线路出发外场
	private String startOutFieldCode;
	
	//线路到达外场
	private String arriveOutFieldCode;
	
	//出发区域编码
	private String leaveAreaCode;
	
	//到达区域编码
	private String arriveAreaCode;
	
	//开单时间
	private Date billlingTime;
	
	//开单金额
	private BigDecimal billlingAmount;
	
	//开单体积
	private BigDecimal billlingVolumn;
	
	//开单重量
	private BigDecimal billlingWeight;

	//是否为PDA计算运费
    private boolean isPda = false;
    
    //活动相关的货物品名列表(供PDA使用)
    private List<String> goodNameList;    
    //快递集中录单项目-是否快递集中开单
    private String isExpressFous;    
	public List<String> getGoodNameList() {
		return goodNameList;
	}

	public void setGoodNameList(List<String> goodNameList) {
		this.goodNameList = goodNameList;
	}
    
	public boolean isPda() {
		return isPda;
	}

	public void setPda(boolean isPda) {
		this.isPda = isPda;
	}
	
	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the firstTrade
	 */
	public String getFirstTrade() {
		return firstTrade;
	}

	/**
	 * @param firstTrade the firstTrade to set
	 */
	public void setFirstTrade(String firstTrade) {
		this.firstTrade = firstTrade;
	}

	/**
	 * @return the secondeTrade
	 */
	public String getSecondeTrade() {
		return secondeTrade;
	}

	/**
	 * @param secondeTrade the secondeTrade to set
	 */
	public void setSecondeTrade(String secondeTrade) {
		this.secondeTrade = secondeTrade;
	}

	/**
	 * @return the orderResource
	 */
	public String getOrderResource() {
		return orderResource;
	}

	/**
	 * @param orderResource the orderResource to set
	 */
	public void setOrderResource(String orderResource) {
		this.orderResource = orderResource;
	}

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return the developDeptCode
	 */
	public String getDevelopDeptCode() {
		return developDeptCode;
	}

	/**
	 * @param developDeptCode the developDeptCode to set
	 */
	public void setDevelopDeptCode(String developDeptCode) {
		this.developDeptCode = developDeptCode;
	}

	/**
	 * @return the startOutFieldCode
	 */
	public String getStartOutFieldCode() {
		return startOutFieldCode;
	}

	/**
	 * @param startOutFieldCode the startOutFieldCode to set
	 */
	public void setStartOutFieldCode(String startOutFieldCode) {
		this.startOutFieldCode = startOutFieldCode;
	}

	/**
	 * @return the arriveOutFieldCode
	 */
	public String getArriveOutFieldCode() {
		return arriveOutFieldCode;
	}

	/**
	 * @param arriveOutFieldCode the arriveOutFieldCode to set
	 */
	public void setArriveOutFieldCode(String arriveOutFieldCode) {
		this.arriveOutFieldCode = arriveOutFieldCode;
	}

	/**
	 * @return the billlingAmount
	 */
	public BigDecimal getBilllingAmount() {
		return billlingAmount;
	}

	/**
	 * @param billlingAmount the billlingAmount to set
	 */
	public void setBilllingAmount(BigDecimal billlingAmount) {
		this.billlingAmount = billlingAmount;
	}

	/**
	 * @return the billlingVolumn
	 */
	public BigDecimal getBilllingVolumn() {
		return billlingVolumn;
	}

	/**
	 * @param billlingVolumn the billlingVolumn to set
	 */
	public void setBilllingVolumn(BigDecimal billlingVolumn) {
		this.billlingVolumn = billlingVolumn;
	}

	/**
	 * @return the billlingWeight
	 */
	public BigDecimal getBilllingWeight() {
		return billlingWeight;
	}

	/**
	 * @param billlingWeight the billlingWeight to set
	 */
	public void setBilllingWeight(BigDecimal billlingWeight) {
		this.billlingWeight = billlingWeight;
	}

	/**
	 * @return the billlingTime
	 */
	public Date getBilllingTime() {
		return billlingTime;
	}

	/**
	 * @param billlingTime the billlingTime to set
	 */
	public void setBilllingTime(Date billlingTime) {
		this.billlingTime = billlingTime;
	}

	
	/**
	 * @return the optionList
	 */
	public List<MarkOptionsEntity> getOptionList() {
		return optionList;
	}

	/**
	 * @param optionList the optionList to set
	 */
	public void setOptionList(List<MarkOptionsEntity> optionList) {
		this.optionList = optionList;
	}

	/**
	 * @return the leaveAreaCode
	 */
	public String getLeaveAreaCode() {
		return leaveAreaCode;
	}

	/**
	 * @param leaveAreaCode the leaveAreaCode to set
	 */
	public void setLeaveAreaCode(String leaveAreaCode) {
		this.leaveAreaCode = leaveAreaCode;
	}

	/**
	 * @return the arriveAreaCode
	 */
	public String getArriveAreaCode() {
		return arriveAreaCode;
	}

	/**
	 * @param arriveAreaCode the arriveAreaCode to set
	 */
	public void setArriveAreaCode(String arriveAreaCode) {
		this.arriveAreaCode = arriveAreaCode;
	}

	public String getIsExpressFous() {
		return isExpressFous;
	}

	public void setIsExpressFous(String isExpressFous) {
		this.isExpressFous = isExpressFous;
	}
}
