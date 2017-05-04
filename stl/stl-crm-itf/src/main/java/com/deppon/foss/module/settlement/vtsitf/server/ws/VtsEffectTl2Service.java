package com.deppon.foss.module.settlement.vtsitf.server.ws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IEffectTl2ByVTSInfo;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossPayableToVtsTail;
import com.deppon.foss.module.settlement.common.api.shared.dto.VtsToFossTailDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.vtsitf.server.inter.IVtsEffectTl2Service;
import com.deppon.foss.util.define.ESBHeaderConstant;
/**
 * @author 310970 caopeng
 * @param   VtsToFossTailDto
 * 生效尾款单
 * */
public class VtsEffectTl2Service implements IVtsEffectTl2Service  {
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager.getLogger(SettlementToVtsService.class);
	
	@Context
	HttpServletResponse resp;
	@Context
	HttpServletRequest req;
	
	//注入新增的service
	IEffectTl2ByVTSInfo effectTl2ByVTSInfo;

	
	@Override
	@ResponseBody
	public String EffectTl2ByVtsToFossTailDto(String vtsToFossTailDto) {
		resp.setHeader(ESBHeaderConstant.VERSION, req.getHeader(ESBHeaderConstant.VERSION));
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_EFFECT_TL_BY_VTS_DTO");
		resp.setHeader(ESBHeaderConstant.REQUESTID, req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID, req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT, req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE, req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		
		logger.info("VTS调用foss生效尾款...");	
		
		FossPayableToVtsTail  response = new  FossPayableToVtsTail();
		System.out.println("VTS调用foss生成尾款...");
		try {
			resp.setHeader("ESB-ResultCode", "1");
			//参数判空
			if(StringUtils.isEmpty(vtsToFossTailDto)){
				logger.error("vts传入参数为空！");
				throw new SettlementException("vts传入参数为空！");
			}
			logger.info("---------生效尾款应付单开始-----------");
			//数据类型转换
			JSONObject object = JSONObject.parseObject(vtsToFossTailDto);
			List<VtsToFossTailDto> object2 = object.getObject("requestEntity", List.class);
			// 将JSON字符串转为集合
			JSONArray ja = JSONArray.fromObject(object2.toString());
			Collection<VtsToFossTailDto> coll = JSONArray.toCollection(ja,VtsToFossTailDto.class);
			List<VtsToFossTailDto> list = new ArrayList<VtsToFossTailDto>(coll);
			//调用新建的接口,完成批量生效
			this.effectTl2ByVTSInfo.effectTl2ByVTSInfo(list);
			logger.info("---------生效尾款应付单结束-----------");
			response.setIsSuccess(true);
			response.setMsg("生效尾款应付单成功");
		}catch (BusinessException e) {
			resp.setHeader("ESB-ResultCode", "1");
			response.setIsSuccess(false);
			response.setMsg("生效失败"+e.getErrorCode());
		}catch (Exception e) {
			resp.setHeader("ESB-ResultCode", "1");
			response.setIsSuccess(false);
			response.setMsg("生效异常,异常信息:"+e.getMessage());
		} 
		
		return JSONObject.toJSONString(response);
	}
	public void setEffectTl2ByVTSInfo(IEffectTl2ByVTSInfo effectTl2ByVTSInfo) {
		this.effectTl2ByVTSInfo = effectTl2ByVTSInfo;
	}
	/*private String getResponse(boolean  result,String message){
		FossPayableToVtsTail response = new FossPayableToVtsTail();
		response.setIsSuccess(result);
		response.setMsg(message);
		return JSONObject.toJSONString(response);
	}*/
}
