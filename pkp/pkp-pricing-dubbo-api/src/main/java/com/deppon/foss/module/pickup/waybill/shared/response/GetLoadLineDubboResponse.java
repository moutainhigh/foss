package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.EffectivePlanDubboDto;

/**
 * 查询产品时效返回参数
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-316759-wangruipeng,date:2017-3-30 下午7:04:50,content:查询产品时效返回参数 </p>
 * @author Foss-316759-wangruipeng 
 * @date 2017-3-30 下午7:04:50
 * @since
 * @version
 */
public class GetLoadLineDubboResponse implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/** 产品时效详情 */
	private List<EffectivePlanDubboDto> effectivePlanDubboDtos;

	public List<EffectivePlanDubboDto> getEffectivePlanDubboDtos() {
		return effectivePlanDubboDtos;
	}

	public void setEffectivePlanDubboDtos(
			List<EffectivePlanDubboDto> effectivePlanDubboDtos) {
		this.effectivePlanDubboDtos = effectivePlanDubboDtos;
	}

}
