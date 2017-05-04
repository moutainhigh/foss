package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.GoodsTypeDubboDto;

/**
 * 获取货物类型返回参数
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-316759-wangruipeng,date:2017-3-30 下午7:04:50,content:获取货物类型返回参数 </p>
 * @author Foss-316759-wangruipeng 
 * @date 2017-3-30 下午7:04:50
 * @since
 * @version
 */
public class GetGoodsTypeDubboResponse implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/** 货物类型 */
	private List<GoodsTypeDubboDto> goodsTypeDubboDtos;

	public List<GoodsTypeDubboDto> getGoodsTypeDubboDtos() {
		return goodsTypeDubboDtos;
	}

	public void setGoodsTypeDubboDtos(List<GoodsTypeDubboDto> goodsTypeDubboDtos) {
		this.goodsTypeDubboDtos = goodsTypeDubboDtos;
	}

}
