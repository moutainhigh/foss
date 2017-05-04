package com.deppon.foss.module.pickup.pricing.api.shared.domain.pop;

import java.math.BigDecimal;
import com.deppon.foss.framework.entity.BaseEntity;

public class PriceValueAddedDetailEntity extends BaseEntity {
	
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = -3174437507371210173L;

	/**
	 * Description 创建人
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String createUser;
	
	/**
	 * Description get 创建人
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getCreateUser(){
		return this.createUser;
	}
	
	/**
	 * Description set 创建人
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param create_user
	 */
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	/**
	 * Description 修改人
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String modifyUser;
	
	/**
	 * Description get 修改人
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getModifyUser(){
		return this.modifyUser;
	}
	
	/**
	 * Description set 修改人
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param modify_user
	 */
	public void setModifyUser(String modifyUser){
		this.modifyUser = modifyUser;
	}
	/**
	 * Description 增值服务类型
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String valueaddType;
	
	/**
	 * Description get 增值服务类型
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getValueaddType(){
		return this.valueaddType;
	}
	
	/**
	 * Description set 增值服务类型
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param valueadd_type
	 */
	public void setValueaddType(String valueaddType){
		this.valueaddType = valueaddType;
	}
	/**
	 * Description 当增值选择其他费用时候使用,数据来源于维护其他费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String valueaddSubType;
	
	/**
	 * Description get 当增值选择其他费用时候使用,数据来源于维护其他费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getValueaddSubType(){
		return this.valueaddSubType;
	}
	
	/**
	 * Description set 当增值选择其他费用时候使用,数据来源于维护其他费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param valueadd_sub_type
	 */
	public void setValueaddSubType(String valueaddSubType){
		this.valueaddSubType = valueaddSubType;
	}
	/**
	 * Description 重量或者体积
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String caculateType;
	
	/**
	 * Description get 重量或者体积
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getCaculateType(){
		return this.caculateType;
	}
	
	/**
	 * Description set 重量或者体积
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param caculate_type
	 */
	public void setCaculateType(String caculateType){
		this.caculateType = caculateType;
	}
	/**
	 * Description 左区间
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private BigDecimal leftrange;
	
	/**
	 * Description get 左区间
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return Double
	 */
	public BigDecimal getLeftrange(){
		return this.leftrange;
	}
	
	/**
	 * Description set 左区间
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param leftrange
	 */
	public void setLeftrange(BigDecimal leftrange){
		this.leftrange = leftrange;
	}
	/**
	 * Description 右区间
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private BigDecimal rightrange;
	
	/**
	 * Description get 右区间
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return Double
	 */
	public BigDecimal getRightrange(){
		return this.rightrange;
	}
	
	/**
	 * Description set 右区间
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param rightrange
	 */
	public void setRightrange(BigDecimal rightrange){
		this.rightrange = rightrange;
	}
	/**
	 * Description 业务类型:标示类型是 距离,代收,保价
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String businessType;
	
	/**
	 * Description get 业务类型:标示类型是 距离,代收,保价
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getBusinessType(){
		return this.businessType;
	}
	
	/**
	 * Description set 业务类型:标示类型是 距离,代收,保价
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param business_type
	 */
	public void setBusinessType(String businessType){
		this.businessType = businessType;
	}
	/**
	 * Description 例如:超远派送 10KM起点或者 声明价值起点
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String businessLeftrange;
	
	/**
	 * Description get 例如:超远派送 10KM起点或者 声明价值起点
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getBusinessLeftrange(){
		return this.businessLeftrange;
	}
	
	/**
	 * Description set 例如:超远派送 10KM起点或者 声明价值起点
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param business_leftrange
	 */
	public void setBusinessLeftrange(String businessLeftrange){
		this.businessLeftrange = businessLeftrange;
	}
	/**
	 * Description 例如:超远派送 20KM终点或者 声明价值终点
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String businessRightrange;
	
	/**
	 * Description get 例如:超远派送 20KM终点或者 声明价值终点
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getBusinessRightrange(){
		return this.businessRightrange;
	}
	
	/**
	 * Description set 例如:超远派送 20KM终点或者 声明价值终点
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param business_rightrange
	 */
	public void setBusinessRightrange(String businessRightrange){
		this.businessRightrange = businessRightrange;
	}
	/**
	 * Description 是否可以修改
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String canModify;
	
	/**
	 * Description get 是否可以修改
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getCanModify(){
		return this.canModify;
	}
	
	/**
	 * Description set 是否可以修改
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param can_modify
	 */
	public void setCanModify(String canModify){
		this.canModify = canModify;
	}
	/**
	 * Description 费率或者费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String feeRate;
	
	/**
	 * Description get 费率或者费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getFeeRate(){
		return this.feeRate;
	}
	
	/**
	 * Description set 费率或者费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param fee_rate
	 */
	public void setFeeRate(String feeRate){
		this.feeRate = feeRate;
	}
	/**
	 * Description 最低费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private Double minFee;
	
	/**
	 * Description get 最低费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return Double
	 */
	public Double getMinFee(){
		return this.minFee;
	}
	
	/**
	 * Description set 最低费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param min_fee
	 */
	public void setMinFee(Double minFee){
		this.minFee = minFee;
	}
	/**
	 * Description 最高费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private Double maxFee;
	
	/**
	 * Description get 最高费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return Double
	 */
	public Double getMaxFee(){
		return this.maxFee;
	}
	
	/**
	 * Description set 最高费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param max_fee
	 */
	public void setMaxFee(Double maxFee){
		this.maxFee = maxFee;
	}
	/**
	 * Description 默认费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private BigDecimal defaultFee;
	
	/**
	 * Description get 默认费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return Double
	 */
	public BigDecimal getDefaultFee(){
		return this.defaultFee;
	}
	
	/**
	 * Description set 默认费用
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param default_fee
	 */
	public void setDefaultFee(BigDecimal defaultFee){
		this.defaultFee = defaultFee;
	}
	/**
	 * Description 最低一票
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private Double minVote;
	
	/**
	 * Description get 最低一票
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return Double
	 */
	public Double getMinVote(){
		return this.minVote;
	}
	
	/**
	 * Description set 最低一票
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param min_vote
	 */
	public void setMinVote(Double minVote){
		this.minVote = minVote;
	}
	/**
	 * Description 最高一票
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private Double maxVote;
	
	/**
	 * Description get 最高一票
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return Double
	 */
	public Double getMaxVote(){
		return this.maxVote;
	}
	
	/**
	 * Description set 最高一票
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param max_vote
	 */
	public void setMaxVote(Double maxVote){
		this.maxVote = maxVote;
	}
	/**
	 * Description 包装类型
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String packageType;
	
	/**
	 * Description get 包装类型
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getPackageType(){
		return this.packageType;
	}
	
	/**
	 * Description set 包装类型
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param package_type
	 */
	public void setPackageType(String packageType){
		this.packageType = packageType;
	}
	/**
	 * Description 退款类型
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String refundType;
	
	/**
	 * Description get 退款类型
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getRefundType(){
		return this.refundType;
	}
	
	/**
	 * Description set 退款类型
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param refund_type
	 */
	public void setRefundType(String refundType){
		this.refundType = refundType;
	}
	/**
	 * Description 返单类型
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String returnbillType;
	
	/**
	 * Description get 返单类型
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getReturnbillType(){
		return this.returnbillType;
	}
	
	/**
	 * Description set 返单类型
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param returnbill_type
	 */
	public void setReturnbillType(String returnbillType){
		this.returnbillType = returnbillType;
	}
	/**
	 * Description 最低保险费
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private Double minInsuranceFee;
	
	/**
	 * Description get 最低保险费
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return Double
	 */
	public Double getMinInsuranceFee(){
		return this.minInsuranceFee;
	}
	
	/**
	 * Description set 最低保险费
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param min_insurance_fee
	 */
	public void setMinInsuranceFee(Double minInsuranceFee){
		this.minInsuranceFee = minInsuranceFee;
	}
	/**
	 * Description 归集列别
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String togeterCategory;
	
	/**
	 * Description get 归集列别
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getTogeterCategory(){
		return this.togeterCategory;
	}
	
	/**
	 * Description set 归集列别
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param togeter_category
	 */
	public void setTogeterCategory(String togeterCategory){
		this.togeterCategory = togeterCategory;
	}
	/**
	 * Description 是否可不收取
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String canNotCharge;
	
	/**
	 * Description get 是否可不收取
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return boolean
	 */
	public String getCanNotCharge(){
		return this.canNotCharge;
	}
	
	/**
	 * Description set 是否可不收取
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param can_not_charge
	 */
	public void setCanNotCharge(String canNotCharge){
		this.canNotCharge = canNotCharge;
	}
	/**
	 * Description 是否有效
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String active;
	
	/**
	 * Description get 是否有效
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return boolean
	 */
	public String getActive(){
		return this.active;
	}
	
	/**
	 * Description set 是否有效
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param active
	 */
	public void setActive(String active){
		this.active = active;
	}
	/**
	 * Description 增值规则ID  T_POP_VALUEADDED的id
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 */
	private String valueaddId;
	
	/**
	 * Description get 增值规则ID  T_POP_VALUEADDED的id
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @return String
	 */
	public String getValueaddId(){
		return this.valueaddId;
	}
	
	/**
	 * Description set 增值规则ID  T_POP_VALUEADDED的id
	 * @author 106138 
	 * @version 0.1 2014-10-10 09:28:30
	 * @param valueadd_id
	 */
	public void setValueaddId(String valueaddId){
		this.valueaddId = valueaddId;
	}
	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}
	/**
	 * <p>
	 * Description:priceRuleId<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public String getPriceRuleId() {
		return priceRuleId;
	}

	/**
	 * <p>
	 * Description:priceRuleId<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setPriceRuleId(String priceRuleId) {
		this.priceRuleId = priceRuleId;
	}
	/**
	 * <p>
	 * Description:subType<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * <p>
	 * Description:subType<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}
	/**
	 * 版本号
	 */
	private long versionNo;
	
	 /**
     * 价格表达式id
     */
    private String  priceRuleId;
    /**
     * 增值子类型 主要用于保费
     */
    private String subType;
}
