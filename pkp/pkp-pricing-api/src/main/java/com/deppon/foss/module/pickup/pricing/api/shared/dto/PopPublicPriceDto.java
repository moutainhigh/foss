package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 分段信息明细
 *
 * @author 219413-Luomengxiang
 * 
 * @date 2014-11-24
 *
 */
public class PopPublicPriceDto implements Serializable {
      
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7466617112048670382L;

	/**
        *分段数 
        */
	  private String sectionId;
	  
	  /**
	   * 重货价格
	   */
	  private BigDecimal heavyPrice;
	 
	  /**
	   * 轻货价格
	   */
	  private BigDecimal lightPrice;
	  
	  /**
	   * 重货临界值
	   */
	  private BigDecimal heavyCriticalValue;
	  
	  /**
	   * 轻货临界值
	   */
	  private BigDecimal lightCriticalValue;
	  
	  /**
	   * 重货范围
	   * 
	   */
	  private String heavyRange;
	  
	  /**
	   * 轻货范围
	   * 
	   */
	  private String lightRange;
	  
	  /**
	   * 获得重货范围
	   * @return
	   */
	public String getHeavyRange() {
		return heavyRange;
	}
	
	/**
	   * 设置重货范围
	   * @return
	   */
	public void setHeavyRange(String heavyRange) {
		this.heavyRange = heavyRange;
	}
	
	/**
	   * 获得轻货范围
	   * @return
	   */
	public String getLightRange() {
		return lightRange;
	}
	
	/**
	   * 设置轻货范围
	   * @return
	   */
	public void setLightRange(String lightRange) {
		this.lightRange = lightRange;
	}
	
	/**
	   * 获得分段数
	   * @return
	   */
	public String getSectionId() {
		return sectionId;
	}
	
	/**
	   * 设置分段数
	   * @return
	   */
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	
	/**
	   * 获得重货价格
	   * @return
	   */
	public BigDecimal getHeavyPrice() {
		return heavyPrice;
	}
	
	/**
	   * 设置重货价格
	   * @return
	   */
	public void setHeavyPrice(BigDecimal heavyPrice) {
		this.heavyPrice = heavyPrice;
	}
	
	/**
	   * 获得轻货价格
	   * @return
	   */
	public BigDecimal getLightPrice() {
		return lightPrice;
	}
	
	/**
	   * 设置轻货价格
	   * @return
	   */
	public void setLightPrice(BigDecimal lightPrice) {
		this.lightPrice = lightPrice;
	}
	
	/**
	   * 获得重货临界值
	   * @return
	   */
	public BigDecimal getHeavyCriticalValue() {
		return heavyCriticalValue;
	}
	
	/**
	   * 设置重货临界值
	   * @return
	   */
	public void setHeavyCriticalValue(BigDecimal heavyCriticalValue) {
		this.heavyCriticalValue = heavyCriticalValue;
	}
	
	/**
	   * 获得轻货临界值
	   * @return
	   */
	public BigDecimal getLightCriticalValue() {
		return lightCriticalValue;
	}
	
	/**
	   * 设置轻货临界值
	   * @return
	   */
	public void setLightCriticalValue(BigDecimal lightCriticalValue) {
		this.lightCriticalValue = lightCriticalValue;
	}
	  
}
