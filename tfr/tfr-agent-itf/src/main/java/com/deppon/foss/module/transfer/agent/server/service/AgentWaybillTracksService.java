package com.deppon.foss.module.transfer.agent.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillResponseDto;
import com.deppon.foss.module.transfer.partialline.api.server.service.IAgentWaybillTrackService;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.AgentWaybillTrackDto;
import com.deppon.foss.util.define.ESBHeaderConstant;

/**
 * @author niuly
 * @function  快递100与FOSS轨迹交互 服务端service
 * @date 2015年2月2日上午8:35:01
 */
public class AgentWaybillTracksService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentWaybillTracksService.class);
	
	private IAgentWaybillTrackService agentWaybillTrackService;
	
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	/**
	 * @author nly
	 * @date 2015年2月2日 上午10:12:59
	 * @function 保存快递100推送的代理轨迹
	 * @param jsonStr
	 * @return 
	 */
	@POST
	@Path("/addWaybillTracks")
	public String addWaybillTracksFromEsb(String requestStr) {
		LOGGER.error("快递100推送轨迹服务开始...");
		LOGGER.error("requestStr===" + requestStr);
//		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
//		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_PUSH_TRAJECTORY2FOSS");
//		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
//		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
//		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
//		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
//		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
//		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
//		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		
		String responseStr = "";
		AgentWaybillResponseDto responseDto = new AgentWaybillResponseDto();
		//代理单号
		String agentWaybillNo = "";
		String agentCompany = ""; 
		String flag = "";
//		String status = "";
//		String state = "";
//		String isSign = "";
		
		List<AgentWaybillTrackDto> trackList = new ArrayList<AgentWaybillTrackDto>();
		try {
			/*监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。
			 * 其中当快递单为已签收时status=shutdown，
			 * 当message为“3天查询无记录”或“60天无变化时”status= abort ，
			 * 对于stuatus=abort的状度，需要增加额外的处理逻辑
			 */
			JSONObject obj = JSON.parseObject(requestStr);
			JSONObject resultObj = obj.getJSONObject("lastResult");
			//status = obj.getString("status");
			
			/*
			 * 0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单
			 */
			//state = resultObj.getString("state");
			/*
			 * 是否签收标记，明细状态请参考state字段
			 */
			//isSign = resultObj.getString("ischeck");
			//代理单号
			agentWaybillNo= resultObj.getString("nu");
			//代理公司
			agentCompany = resultObj.getString("com");
			//轨迹str
			String trackStr = resultObj.getJSONArray("data").toString();
			//
		    flag = resultObj.getString("condition");
			//轨迹list
			trackList = JSON.parseArray(trackStr, AgentWaybillTrackDto.class);
		} catch (Exception e) {
			responseDto.setResult("false");
			responseDto.setReturnCode("601");
			responseDto.setMessage("轨迹获取失败");
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		} 
		
		try{
			LOGGER.error("快递100推送轨迹，开始同步轨迹...代理单号：" + agentWaybillNo);
			if("GJJ".equals(flag)){
				agentWaybillTrackService.addWaybillTrackForInternal(agentWaybillNo, trackList);
			}else{
				agentWaybillTrackService.addWaybillTrack(agentWaybillNo,agentCompany,trackList);
			}
			LOGGER.error("快递100推送轨迹，同步轨迹成功...代理单号：" + agentWaybillNo);
			responseDto.setResult("true");
			responseDto.setReturnCode("200");
			responseDto.setMessage("成功");
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		} catch(Exception e) {
			LOGGER.error("快递100推送轨迹，同步轨迹异常...代理单号：" + agentWaybillNo);
			responseDto.setResult("false");
			responseDto.setReturnCode("600");
			responseDto.setMessage("业务异常");
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		}
		LOGGER.error("快递100推送轨迹服务结束...");
		responseStr = JSON.toJSONString(responseDto);
		LOGGER.error("responseStr===" + responseStr);
		return responseStr;
	}
	
	
	/**
	 * @author nly
	 * @date 2015年2月2日 上午10:13:07
	 * @function 保存快递100要查询轨迹的运单号
	 * @param jsonStr
	 * @return
	 */
	@POST
	@Path("/addWaybillNos")
	public String addWaybillNosFromEsb(String requestStr) {
		String resultStr = "";
		List<String> waybillNoList = new ArrayList<String>();
		agentWaybillTrackService.addWaybillNos(waybillNoList);
		return resultStr;
	}
	public void setAgentWaybillTrackService(
			IAgentWaybillTrackService agentWaybillTrackService) {
		this.agentWaybillTrackService = agentWaybillTrackService;
	}
	
}
