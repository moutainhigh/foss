package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.ecsitf.server.rs.ISignForService;
/**
 * ECS-327090-2016-05-09
 * 同步查询判断判断签收数据是新增还是更改
 * @author 327090
 *
 */
@Controller
@RequestMapping("/v1/foss/stl/signForService")
public class FossSignForService {	

	/**
	 * 日志属性
	 */
	private static final Logger logger = LogManager.getLogger(FossSignForService.class);

	@Resource
	private ISignForService signForService;
	
	@Resource
	private ILogEcsFossService logEcsFossService;
	
	//成功返回1
	private static final String SUCCESS = "1";
		
	//失败返回0
	private static final String FAILURE = "0";
	
	/**
	 * ECS-327090-2016-05-09
	 * @param waybillSignResultEntity
	 * 同步查询判断判断签收数据是新增还是更改
	 * @return
	 */
	@RequestMapping(value="/updateSignFor",method=RequestMethod.POST)
	public @ResponseBody String updateSignFor(@RequestBody String jsonReq,HttpServletResponse res) {
		//ResponseParameter response = new ResponseParameter();
		Date date = new Date();
		res.setHeader("ESB-ResultCode" , "1");
		res.setCharacterEncoding("utf-8");
		String response = "", waybillNo = "null";
		//是否同步运单失败
		boolean flag = true;
		logger.info("--------------签收开始-----------------");
		try{
			WaybillSignResultEntity waybillSignResultEntity=JSONObject.parseObject(jsonReq, WaybillSignResultEntity.class);
			if(waybillSignResultEntity==null){
				response = this.getResponse(FAILURE, "传过来的参数不能没有");
			}
			if(waybillSignResultEntity.getWaybillNo()==null||waybillSignResultEntity.getWaybillNo().equals("")){
				response = this.getResponse(FAILURE, "运单号不能为空");
			}
			//响应为空 说明校验通过
			if (StringUtils.isBlank(response)) {
				waybillNo = waybillSignResultEntity.getWaybillNo(); 
				//签收件数设置为固定值
				waybillSignResultEntity.setSignGoodsQty(1);
				waybillSignResultEntity.setModifyTime(new Date());
				int count=signForService.updateOrInsertSignFor(waybillSignResultEntity);
				if(count==0){
					logger.info("新增："+waybillSignResultEntity.getWaybillNo());
					response = this.getResponse(SUCCESS, "新增成功");
					flag = false;
				}else if(count==1){
					logger.info("更新："+waybillSignResultEntity.getWaybillNo());
					response = this.getResponse(SUCCESS, "更新成功");
					flag = false;
				} else {
					response = this.getResponse(FAILURE, "数据库中有多条重复的信息");
				}
			}
		} catch(Exception e){		
			logger.info("异常："+e.getMessage());	
			response = this.getResponse(FAILURE, "异常："+e.getMessage());
		}		
		if (flag) {
			try{
				//保存日志
				logEcsFossService.setLog(jsonReq, response, 
						SettlementDictionaryConstants.FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT, waybillNo, false, date);
			}catch(Exception e1){
				logger.info("记录同步签收接口异常日志插入失败："+e1.toString());
			}
		}
		return response;
	}
	
	/**
	 * 获取响应信息
	 * @author ECS-327090
	 * @date 2016-5-18 
	 */
	private String getResponse(String result,String message){
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("result", result);
		map.put("message", message);
		String response = JSONObject.toJSONString(map);
		return response;
	}

}
