package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 定义市场推广活动多选实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-28 下午5:12:04,content:CRM只同步给FOSS折扣信息 </p>
 * @author 316759 
 * @date 2017-2-28 下午5:12:04
 * @since
 * @version
 */
public class MarkOptionsDubboEntity extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 营销活动ID
	 */
	private String  activeId;

	/**
	 * 活动CRM_ID
	 */
	private BigDecimal activeCrmId;

	/**
	 * 折扣值 费 包装费 物品编码等
	 */
	private BigDecimal value;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 类型
	 * 如：折扣 优惠劵 物品信息
	 */
	private String includeType;
	
	/**
	 * CRM ID
	 */
	private BigDecimal crmId;
	
	/**
	 * 是否启用
	 */
    private String active;
   
    /**
     * 续重最低费率
     */
    private BigDecimal continueHeavyLowestRate;

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public BigDecimal getActiveCrmId() {
		return activeCrmId;
	}

	public void setActiveCrmId(BigDecimal activeCrmId) {
		this.activeCrmId = activeCrmId;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIncludeType() {
		return includeType;
	}

	public void setIncludeType(String includeType) {
		this.includeType = includeType;
	}

	public BigDecimal getCrmId() {
		return crmId;
	}

	public void setCrmId(BigDecimal crmId) {
		this.crmId = crmId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public BigDecimal getContinueHeavyLowestRate() {
		return continueHeavyLowestRate;
	}

	public void setContinueHeavyLowestRate(BigDecimal continueHeavyLowestRate) {
		this.continueHeavyLowestRate = continueHeavyLowestRate;
	}

}
