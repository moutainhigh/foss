package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;


/** 
  * @ClassName MarketingActivityEntity 
  * @Description TODO 
  * @author 092038 
  * @date 2014-4-14 上午8:41:29 
*/ 
public class MarketingActivityEntity implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 营销活动名称
	 */
	private String marketingName;
    /**
     * 营销活动编码
     */
    private String marketingCode;
    /**
     * 货物品名
     */
    private String goodsName;
    
    public String getMarketingName() {
        return marketingName;
    }
    public void setMarketingName(String marketingName) {
        this.marketingName = marketingName;
    }
    public String getMarketingCode() {
        return marketingCode;
    }
    public void setMarketingCode(String marketingCode) {
        this.marketingCode = marketingCode;
    }
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

    
    
    
}