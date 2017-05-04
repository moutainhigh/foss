/**
 * company   : com.deppon
 * poroject   : foss结算
 * copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author   : panshiqi (309613)
 * @date     : 2016年3月2日 下午9:17:23
 * @version  : v1.0
 */
package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.WscWayBillManageDto;

/**
 * 
* @description: 待刷卡运单管理视图传输对象
* @className: WscWayBillManageVo
* 
* @author panshiqi 309613
* @date 2016年3月2日 下午9:17:57 
*
 */
public class WscWayBillManageVo implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 待刷卡运单dto
	 */
	private WscWayBillManageDto wscWayBillDto = new WscWayBillManageDto();

	/**  
	 * 获取 待刷卡运单dto  
	 * @return wscWayBillDto 待刷卡运单dto  
	 */
	public WscWayBillManageDto getWscWayBillDto() {
		return wscWayBillDto;
	}

	/**  
	 * 设置 待刷卡运单dto  
	 * @param wscWayBillDto 待刷卡运单dto  
	 */
	public void setWscWayBillDto(WscWayBillManageDto wscWayBillDto) {
		this.wscWayBillDto = wscWayBillDto;
	}
}
