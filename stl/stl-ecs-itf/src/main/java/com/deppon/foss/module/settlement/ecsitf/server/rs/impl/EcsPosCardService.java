/**
 * 
 */
package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.deppon.ecs.inteface.domain.SynPosCardDetailEntity;
import com.deppon.ecs.inteface.domain.SynPosCardRequest;
import com.deppon.ecs.inteface.domain.SynPosCardResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.ecsitf.server.rs.IEcsPosCardService;

/** 
 * 悟空pos待机刷卡同步接口
 * @author  354658-duyijun 
 * @date 创建时间：2016-10-21 上午9:41:12 
 */
public class EcsPosCardService implements IEcsPosCardService {
	
	//日志
	private final Logger logger = LogManager.getLogger(EcsPosCardService.class);
	
	Date date = new Date();
    /**
     * 待刷卡service
     */
    private IWSCManageService wscManageService;
    private ILogEcsFossService logEcsFossService;
	
	@Context
	HttpServletResponse res;
	
	@SuppressWarnings("finally")
	@Override
	public @ResponseBody String ecsPosCard(@RequestBody String jsonStr) {
		res.setHeader("ESB-ResultCode", "1");
		res.setCharacterEncoding("utf-8");
		logger.info("开始待刷卡操作。");
		// 接口返回信息
		SynPosCardResponse resDto = new SynPosCardResponse();
		// 生成返回实体
		String response = "";
		SynPosCardRequest param = JSONObject.parseObject(jsonStr,
				SynPosCardRequest.class);
		Boolean flag = null;
		try {
			if (null == param.getTradeSerialNo()) {
				String str = "传入交易流水为空！";
				throw new SettlementException(str);
			}
			PosCardEntity posCardEntity = new PosCardEntity();
			BeanUtils.copyProperties(param, posCardEntity);
			List<PosCardDetailEntity> list = new ArrayList<PosCardDetailEntity>();
			posCardEntity.setPosCardDetailEntitys(list);
			for (SynPosCardDetailEntity entity : param
					.getPosCardDetailEntitys()) {
				PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
				BeanUtils.copyProperties(entity, posCardDetailEntity);
				list.add(posCardDetailEntity);
			}
			wscManageService.addPosCardAndDetail(posCardEntity);
			flag = true;
			resDto.setIsSuccess("Y");
			response = JSONObject.toJSONString(resDto);
		} catch (BusinessException e) {
			String str = e.getErrorCode();
			response = this.errMsg(response, str,resDto);
			flag = false;
		} catch (Exception e) {
			String str = e.getMessage();
			response = this.errMsg(response, "未知异常" + str,resDto);
			flag = false;
		} finally {
			// 添加日志
			logEcsFossService.setLog(param, response,
					"FOSS_ESB2FOSS_ECS_FOSS_SEND_POSCARD", StringUtils
							.isBlank(param.getTradeSerialNo()) ? "传入交易流水为空！"
							: param.getTradeSerialNo(), flag, date);
			logger.info("待刷卡操作结束。");
			return response;
		}
	}
	
	private String errMsg(String response, String str,SynPosCardResponse resDtos) {
		// 接口返回信息
		logger.info(str);
		resDtos.setIsSuccess("N");
		resDtos.setMessage(str);
		return JSONObject.toJSONString(resDtos);
	}
	


	public void setWscManageService(IWSCManageService wscManageService) {
		this.wscManageService = wscManageService;
	}

	public void setLogEcsFossService(ILogEcsFossService logEcsFossService) {
		this.logEcsFossService = logEcsFossService;
	}
	

}
