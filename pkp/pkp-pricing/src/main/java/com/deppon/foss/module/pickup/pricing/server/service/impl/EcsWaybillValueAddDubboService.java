package com.deppon.foss.module.pickup.pricing.server.service.impl;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEcsWaybillValueAddDubboService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IWaybillExpressValueAddServiceForECS;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.ESCValueAddVo;
import com.deppon.foss.module.pickup.waybill.shared.response.WaybillValueAddResponse;
import com.deppon.foss.module.pickup.waybill.shared.request.WaybillValueAddRequest;
import com.deppon.foss.module.pickup.waybill.shared.vo.EscWayBillValueAddDetaillVo;

/**
 * FOSS 提供给ECS系统查询增值服务信息
 * @author foss-265475-GELL
 *
 */
public class EcsWaybillValueAddDubboService implements IEcsWaybillValueAddDubboService{

	Log logger = LogFactory.getLog(EcsWaybillValueAddDubboService.class);
	
	/**
	 * ECS系统查询增值服务信息
	 */
	@Resource
	private IWaybillExpressValueAddServiceForECS waybillExpressValueAddServiceForECS;
	
	/**
	 * 根据请求实体查询 悟空系统所需要的增值服务费明细
	 * @param appWaybillInfosRequest 请求参数
	 * @param response 
	 * @return 查询到的结果集
	 * @author 265475
	 * 2017.02.27
	 */
	public WaybillValueAddResponse getValueAddDetailInfosForECS(WaybillValueAddRequest waybillValueAddRequest){
		
		WaybillValueAddResponse waybillValueAddResponse = new WaybillValueAddResponse();
		
		if(null == waybillValueAddRequest){
			logger.info("悟空快递计费接口--请求实体为空");
			waybillValueAddResponse.setResult("0");
			waybillValueAddResponse.setMessage("请求实体为空");
    		return waybillValueAddResponse;
        }
		try {
			logger.info(new StringBuffer("请求参数：").append(waybillValueAddRequest));		
			Map<String, Object> resultMap = waybillExpressValueAddServiceForECS
					.queryWaybillInfosValueAddEcs(translatParam(waybillValueAddRequest));
			int count = (Integer) resultMap.get("count");		
			List<EscWayBillValueAddDetaillVo> lists = (List<EscWayBillValueAddDetaillVo>) resultMap.get("list");
			waybillValueAddResponse.setResult("1");
			waybillValueAddResponse.setData(lists);
			logger.info(new StringBuffer("返回参数:").append(count));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			waybillValueAddResponse.setResult("0");
			waybillValueAddResponse.setMessage(e.getMessage());			
		}
		return waybillValueAddResponse;
	}
	
	private ESCValueAddVo translatParam(WaybillValueAddRequest waybillValueAddRequest) {
		
		ESCValueAddVo EscValueAddVo = new ESCValueAddVo();
		// 出发部门CODE
		EscValueAddVo.setOriginalOrgCode(waybillValueAddRequest.getOriginalOrgCode());
		// 到达部门CODE
		EscValueAddVo.setDestinationOrgCode(waybillValueAddRequest.getDestinationOrgCode());
		// 产品CODE
		EscValueAddVo.setProductCode(waybillValueAddRequest.getProductCode());
		// 营业部收货日期（可选，无则表示当前日期）
		EscValueAddVo.setBillTime(waybillValueAddRequest.getBillTime());
		// 重量
		EscValueAddVo.setWeight(waybillValueAddRequest.getWeight());
		// 体积
		EscValueAddVo.setVolume(waybillValueAddRequest.getVolume());
		
		return EscValueAddVo;
		
	}
	
}
