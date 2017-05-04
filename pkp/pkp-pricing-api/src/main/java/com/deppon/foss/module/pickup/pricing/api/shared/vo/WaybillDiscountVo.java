
package com.deppon.foss.module.pickup.pricing.api.shared.vo;



import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 折扣优惠
 *
 */
public class WaybillDiscountVo {
	
	/**
	 * 折扣ID
	 */
	private String discountId;
	
	/**
	 * 费用类型id
	 */
	private String chargeDetailId;
	
	/**
	 * 优惠项目名称
	 */
	private String favorableItemName;
	
	/**
	 * 优惠项目CODE	
	 */
	private String favorableItemCode;
	
	/**
	 * 优惠类别名称
	 */
	private String favorableTypeName;
	
	/**
	 * 优惠类别CODE
	 */
	private String favorableTypeCode;
	/**
	 * 优惠子类别名称
	 */
	private String favorableSubTypeName;
	
	/**
	 * 优惠子类别CODE
	 */
	private String favorableSubTypeCode;
	
	/**
	 * 优惠折扣率
	 */
	private String favorableDiscount;
	
	/**
	 * 优惠金额
	 */
	private String favorableAmount;
	
	/**
	 * 营销活动CODE
	 */
	private String activeCode;
	/**
	 * 营销活动名称
	 */
	private String activeName;
	/**
	 * 营销活动开始时间
	 */
	private Date activeStartTime;
	/**
	 * 营销活动结束时间
	 */
	private Date activeEndTime;
	/**
	 * 营销活动折扣关联的CRM_ID
	 */
	private BigDecimal optionsCrmId;
	
	/**
	 * 快递续重优惠折扣率
	 */
	private String continueFavorableDiscount;
	
    /**
     * 快递续重费率
     * */
	private BigDecimal expressContinueRate;
	
	
	public BigDecimal getExpressContinueRate() {
		return expressContinueRate;
	}

	public void setExpressContinueRate(BigDecimal expressContinueRate) {
		this.expressContinueRate = expressContinueRate;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public Date getActiveStartTime() {
		return activeStartTime;
	}

	public void setActiveStartTime(Date activeStartTime) {
		this.activeStartTime = activeStartTime;
	}

	public Date getActiveEndTime() {
		return activeEndTime;
	}

	public void setActiveEndTime(Date activeEndTime) {
		this.activeEndTime = activeEndTime;
	}

	public BigDecimal getOptionsCrmId() {
		return optionsCrmId;
	}

	public void setOptionsCrmId(BigDecimal optionsCrmId) {
		this.optionsCrmId = optionsCrmId;
	}

	/**
	 * @return  the discountId
	 */
	public String getDiscountId() {
		return discountId;
	}

	/**
	 * @param discountId the discountId to set
	 */
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	/**
	 * @return  the chargeDetailId
	 */
	public String getChargeDetailId() {
		return chargeDetailId;
	}

	
	/**
	 * @param chargeDetailId the chargeDetailId to set
	 */
	public void setChargeDetailId(String chargeDetailId) {
		this.chargeDetailId = chargeDetailId;
	}

	
	/**
	 * @return  the favorableItemName
	 */
	public String getFavorableItemName() {
		return favorableItemName;
	}

	
	/**
	 * @param favorableItemName the favorableItemName to set
	 */
	public void setFavorableItemName(String favorableItemName) {
		this.favorableItemName = favorableItemName;
	}


	
	/**
	 * @return  the favorableDiscount
	 */
	public String getFavorableDiscount() {
		return favorableDiscount;
	}

	
	/**
	 * @param favorableDiscount the favorableDiscount to set
	 */
	public void setFavorableDiscount(String favorableDiscount) {
		
		//四舍五入保留两位小数
		this.favorableDiscount = formatNumberTwoDecimal(favorableDiscount);
	}

	
	/**
	 * @return  the favorableAmount
	 */
	public String getFavorableAmount() {
		return favorableAmount;
	}

	
	/**
	 * @param favorableAmount the favorableAmount to set
	 */
	public void setFavorableAmount(String favorableAmount) {
		//四舍五入取整数
		this.favorableAmount = formatNumberTwoDecimal(favorableAmount);
	}


	public String getFavorableItemCode() {
		return favorableItemCode;
	}


	public void setFavorableItemCode(String favorableItemCode) {
		this.favorableItemCode = favorableItemCode;
	}


	public String getFavorableTypeName() {
		return favorableTypeName;
	}


	public void setFavorableTypeName(String favorableTypeName) {
		this.favorableTypeName = favorableTypeName;
	}


	public String getFavorableTypeCode() {
		return favorableTypeCode;
	}


	public void setFavorableTypeCode(String favorableTypeCode) {
		this.favorableTypeCode = favorableTypeCode;
	}


	public String getFavorableSubTypeName() {
		return favorableSubTypeName;
	}


	public void setFavorableSubTypeName(String favorableSubTypeName) {
		this.favorableSubTypeName = favorableSubTypeName;
	}


	public String getFavorableSubTypeCode() {
		return favorableSubTypeCode;
	}


	public void setFavorableSubTypeCode(String favorableSubTypeCode) {
		this.favorableSubTypeCode = favorableSubTypeCode;
	}
	
	/**
	 * 四舍五入保留两位小数
	 */
	private String formatNumberTwoDecimal(String number){
		String result=number;		
		if(number!=null && !"".equals(result)){
			BigDecimal decimal=new BigDecimal(number);
			if(decimal.doubleValue()>0){
				result = decimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString(); // 四舍五入
			}			
		}
		return result;
	}

	public String getContinueFavorableDiscount() {
		return continueFavorableDiscount;
	}

	public void setContinueFavorableDiscount(String continueFavorableDiscount) {
		this.continueFavorableDiscount = continueFavorableDiscount;
	}

}