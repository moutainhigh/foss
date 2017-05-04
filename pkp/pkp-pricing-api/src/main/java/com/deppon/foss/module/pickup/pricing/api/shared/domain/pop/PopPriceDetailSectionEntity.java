package com.deppon.foss.module.pickup.pricing.api.shared.domain.pop;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 计价方式分段明细Entity
 * 
 * @author POP-Team- LuoMengxiang
 * 
 * @date 2014/10/21
 */
public class PopPriceDetailSectionEntity extends BaseEntity{
   
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4740181908363174051L;
	/**
     *      ID
     */
	private String id;
	/**
	 * 分段数ID
	 */
	private String sectionID;
	/**
	 * 计费类型
	 */
	private String caculateType;
	/**
	 * 价格
	 */
	private BigDecimal fee;
	/**
	 * 临界值
	 */
    private BigDecimal criticalValue;
    /**
     * 规则明细ID
     */
    private String criteriaDetailID;
    /**
     * 描述
     */
	private String description;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 计费规则ID
	 */
	private String valuationId;
	/**
	 * 获得计费规则ID
	 * @return
	 */
	public String getValuationId() {
		return valuationId;
	}
	/**
	 * 设置计费规则ID
	 * @param valuationId
	 */
	public void setValuationId(String valuationId) {
		this.valuationId = valuationId;
	}
	/**
	 * 获取ID
	 * 
	 */
    public String getId() {
		return id;
	}
    /**
	 * 设置ID
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取修改时间
	 * 
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * 设置修改时间
	 * 
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
     * 
     * 获取描述
     */
	public String getDescription() {
		return description;
	}
	/**
	 * 
	 * 设置描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
    /**
	 * 获取分段数ID
	 * 
	 */
    public String getSectionID() {
		return sectionID;
	}
    /**
	 * 设置分段数ID
	 * 
	 */
    public void setSectionID(String sectionID) {
		this.sectionID = sectionID;
	}
    /**
	 * 获取计费类型
	 * 
	 */
    public String getCaculateType() {
		return caculateType;
	}
    /**
	 * 设置计费类型
	 * 
	 */
    public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}
    /**
	 * 获取费用
	 * 
	 */
    public BigDecimal getFee() {
		return fee;
	}
    /**
	 * 设置费用
	 * 
	 */
    public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
    /**
	 * 获取临界值
	 * 
	 */
    public BigDecimal getCriticalValue() {
		return criticalValue;
	}
    /**
	 * 设置临界值
	 * 
	 */
    public void setCriticalValue(BigDecimal criticalValue) {
		this.criticalValue = criticalValue;
	}
    /**
	 * 获取规则明细ID
	 * 
	 */
    public String getCriteriaDetailID() {
		return criteriaDetailID;
	}
    /**
	 * 设置规则明细ID
	 * 
	 */
    public void setCriteriaDetailID(String criteriaDetailID) {
		this.criteriaDetailID= criteriaDetailID;
	}
}
