package com.deppon.foss.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillValueAddServiceForECS;
import com.deppon.foss.module.pickup.waybill.shared.request.WaybillValueAddRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.WaybillValueAddResponse;
import com.deppon.foss.module.pickup.waybill.shared.vo.ESCValueAddVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.EscWayBillValueAddDetaillVo;
import com.deppon.foss.util.define.ESBHeaderConstant;

@Controller
//请求数据格式为json
@Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN })
@Produces("application/json;charset=UTF-8") 
@RequestMapping("/v1/ecs")
public class EcsWaybillValueAddServiceImpl {
	
	Log logger = LogFactory.getLog(EcsWaybillValueAddServiceImpl.class);
	/**
	 * ECS系统查询增值服务信息
	 */
	@Resource
	private IWaybillValueAddServiceForECS waybillValueAddServiceForECS;
	
	/**
	 * 根据请求参数查询 悟空系统所需要的增值服务费明细
	 * @param appWaybillInfosRequest 请求参数
	 * @param response 
	 * @return 查询到的结果集
	 * @author 272311
	 * 2016.04.20
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getValueAddDetailInfosForECS",method = RequestMethod.POST)
	/*@POST
	@Path("/getValueAddDetailInfosForECS")*/
	public WaybillValueAddResponse getValueAddDetailInfosForECS(
			@RequestBody WaybillValueAddRequest waybillValueAddRequest,
			HttpServletResponse response) {
//		logger.info("进入getValueAddDetailInfosForECS");
		WaybillValueAddResponse waybillValueAddResponse = new WaybillValueAddResponse();
		try {
			logger.info(new StringBuffer("请求参数：").append(JSON.toJSONString(waybillValueAddRequest)));
			Map<String, Object> resultMap = waybillValueAddServiceForECS
					.queryWaybillInfosValueAddEcs(translatParam(waybillValueAddRequest));
			int count = (Integer) resultMap.get("count");
//			logger.info("返回的记录数：" + count);
			
			List<EscWayBillValueAddDetaillVo> lists = (List<EscWayBillValueAddDetaillVo>) resultMap
					.get("list");
			// waybillValueAddResponse.setCount(count);
			waybillValueAddResponse.setResult("1");
			waybillValueAddResponse.setData(lists);
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
//			response.setContentType("text/json;charset=UTF-8");  
//	        response.setCharacterEncoding("UTF-8");  
			logger.info(new StringBuffer("返回参数:").append(JSON.toJSONString(waybillValueAddResponse)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			waybillValueAddResponse.setResult("0");
			waybillValueAddResponse.setMessage(e.getMessage());
			response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
			
		}
		
		return waybillValueAddResponse;

	}

//	private void getWaybillDetails(WaybillValueAddRequest waybillValueAddRequest) {
//		
//		
//	}

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
