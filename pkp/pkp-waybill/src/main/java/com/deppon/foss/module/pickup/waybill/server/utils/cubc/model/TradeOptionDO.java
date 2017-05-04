package com.deppon.foss.module.pickup.waybill.server.utils.cubc.model;

import java.io.Serializable;

/**
 * @ClassName: TradeDO
 * @Description: 物流交易表DO
 * @author: 273238
 * @date: 2016年11月10日 下午2:14:22
 */
public class TradeOptionDO implements Serializable {

    /**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 */
	private static final long serialVersionUID = -3067229828443453235L;
	// 标记字段
    Long option;

    /**
     * option标记位
     * @see TradeOptionConstants
     * @return
     */
    public Long getOption() {
        return option;
    }

    public void setOption(Long option) {
        this.option = option;
    }
}
