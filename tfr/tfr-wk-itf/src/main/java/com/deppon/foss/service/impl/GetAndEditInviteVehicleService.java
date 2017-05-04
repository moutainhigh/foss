package com.deppon.foss.service.impl;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.transfer.scheduling.api.define.InviteVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleInfoService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEditParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleQueryParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleInfoDto;
import com.deppon.foss.service.IGetAndEditInviteVehicleService;
import com.deppon.foss.shared.request.EditInviteVehicleInfoReqParEntity;
import com.deppon.foss.shared.request.GetInviteVehicleInfoReqParEntity;
import com.deppon.foss.shared.response.EditInviteVehicleInfoRespParEntity;
import com.deppon.foss.shared.response.GetInviteVehicleInfoRespParEntity;
import com.deppon.foss.shared.vo.GetInviteVehicleInfoEntity;
import com.deppon.foss.util.define.ESBHeaderConstant;

/**
 * 
* @description 获取外请车约车信息和修改外请约车信息Service (悟空系统调用接口)
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:14:16
 */
public class GetAndEditInviteVehicleService implements IGetAndEditInviteVehicleService {

	//日志
	private static final Logger logger = LogManager.getLogger(GetAndEditInviteVehicleService.class);
	
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	// 获取外请约车信息Service
	private IInviteVehicleInfoService inviteVehicleInfoService;
	
	public void setInviteVehicleInfoService(
			IInviteVehicleInfoService inviteVehicleInfoService) {
		this.inviteVehicleInfoService = inviteVehicleInfoService;
	}


	/**
	 * 
	* @description 获取外请车约车信息(给悟空系统调用)
	* @see com.deppon.foss.service.IGetAndEditInviteVehicleService#getInviteVehicleInfo(java.lang.String)
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:15:54
	* @version V1.0
	 */
	@Override
	public Object getInviteVehicleInfo(String reqMsg) {
		
		logger.info("WK获取外请车约车信息开始"+reqMsg);
		
		//返回结果定义
		GetInviteVehicleInfoRespParEntity result = new GetInviteVehicleInfoRespParEntity();
		//获取约车信息传入参数实体定义
		GetInviteVehicleInfoReqParEntity reqParEntity = null;
		try {
			// 获取传入参数
			reqParEntity = JSON.parseObject(reqMsg, GetInviteVehicleInfoReqParEntity.class);
			// 判断请求中的车牌号是否为空
			if (StringUtils.isEmpty(reqParEntity.getVehicleNo())) {
				result.setResultFlag(false);
				result.setFailureReason("车牌号不能为空");
				return JSONObject.fromObject(result).toString();
			}
			
			// 封装查询条件实体
			InviteVehicleQueryParmEntity inviteVehicleQueryParmEntity = new InviteVehicleQueryParmEntity();
			inviteVehicleQueryParmEntity.setVehicleNo(reqParEntity.getVehicleNo());
			inviteVehicleQueryParmEntity.setApplyOrgCode(reqParEntity.getOrigOrgCode());
			inviteVehicleQueryParmEntity.setUseStatus(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED);
			inviteVehicleQueryParmEntity.setStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_VERIFY_ARRIVE);
			
			// 调用获取外请约车信息Service 返回已报道且未使用的约车信息。
			InviteVehicleInfoDto infoDto = inviteVehicleInfoService.queryInviteVehicleInfo(inviteVehicleQueryParmEntity);
			// 没有获取到外请车约车信息
			if (infoDto == null) {
				result.setResultFlag(false);
				result.setFailureReason("没有获取到外请车约车信息");
				return JSONObject.fromObject(result).toString();
			}
			
			// 返回外请车约车信息
			GetInviteVehicleInfoEntity entity = new GetInviteVehicleInfoEntity();
			// 总运费
			entity.setTotalFee(infoDto.getInviteCost());
			// 约车编号
			entity.setAboutVehicleNo(infoDto.getInviteNo());
			// 车牌号
			entity.setVehicleNo(infoDto.getVehicleNo());
			// 约车部门名称
			entity.setAboutDeptName(infoDto.getApplyOrgName());
			// 约车部门编码
			entity.setAboutDeptCode(infoDto.getApplyOrgCode());
			// 是否成功标识
			result.setResultFlag(true);
			result.setData(entity);
			return JSONObject.fromObject(result).toString();
		} catch (Exception e) {
			result.setResultFlag(false);
			result.setFailureReason("系统异常"+e.getMessage());
			logger.error("WK获取外请车约车信息异常", e);
			return JSONObject.fromObject(result).toString();
		}
	}

	/**
	 * 
	* @description 修改外请车约车信息(给悟空系统调用)
	* @see com.deppon.foss.service.IGetAndEditInviteVehicleService#editInviteVehicleInfo(java.lang.String)
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:16:40
	* @version V1.0
	 */
	@Override
	public Object editInviteVehicleInfo(String reqMsg) {
		logger.info("WK修改外请车约车信息开始"+reqMsg);
		// esb通用设置相应header
		//ECS系统需求走ESB服务改成直连
//		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
//		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_UPDATE_INVITEVEHICLEINFO_FROMWK");
//		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
//		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
//		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
//		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
//		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
//		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
//		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
//		//统一设置 统一返回响应
//		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		//返回结果定义
		EditInviteVehicleInfoRespParEntity result = new EditInviteVehicleInfoRespParEntity();
		// 修改约车信息传入参数实体定义
		EditInviteVehicleInfoReqParEntity reqParEntity = null;
		try {
			// 获取传入参数
			reqParEntity = JSON.parseObject(reqMsg, EditInviteVehicleInfoReqParEntity.class);
			// 判断输入字段不能为空
			if (StringUtils.isEmpty(reqParEntity.getVehicleNo()) || StringUtils.isEmpty(reqParEntity.getAboutVehicleNo())
					|| StringUtils.isEmpty(reqParEntity.getUseStatus())) {
				
				result.setResultFlag(false);
				result.setFailureReason("车牌号,约车编号,使用状态都不能为空");
				return JSONObject.fromObject(result).toString();
			}
			// 封装更新条件
			InviteVehicleEditParmEntity inviteVehicleEditParmEntity = new InviteVehicleEditParmEntity();
			inviteVehicleEditParmEntity.setInviteNo(reqParEntity.getAboutVehicleNo());
			inviteVehicleEditParmEntity.setVehicleNo(reqParEntity.getVehicleNo());
			inviteVehicleEditParmEntity.setUseStatus(reqParEntity.getUseStatus());
			
			// 执行外请车状态更新
			int returnValue = inviteVehicleInfoService.updateInviteVehicleStatus(inviteVehicleEditParmEntity);
			
			// 更新失败
			if (returnValue == 0) {
				result.setResultFlag(false);
				result.setFailureReason("更新使用状态失败");
				return JSONObject.fromObject(result).toString();
			}
			// 更新成功
			result.setResultFlag(true);
			return JSONObject.fromObject(result).toString();
		} catch (Exception e) {
			result.setResultFlag(false);
			result.setFailureReason("系统异常"+e.getMessage());
			logger.error("系统异常", e);
			return JSONObject.fromObject(result).toString();
		}
	}
}
