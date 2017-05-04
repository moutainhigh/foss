package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ctc.wstx.util.ExceptionUtil;
import com.deppon.foss.module.pickup.waybill.api.server.service.IAutoAddWSCWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWSCWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.AddWSCWaybillProcessEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WSCWaybillProcessVo;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCWayBillManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillParamEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.util.CollectionUtils;
/**
 * @author 350909   郭倩云
 * 从数据库表PKP.T_SRV_ADD_ASYN_WAYBILL中查询出运单信息和代刷卡数据信息,然后调用结算的接口将数据推送给结算
 *
 */
public class AutoAddWSCWaybillProcessService implements IAutoAddWSCWaybillProcessService{
	private Logger logger = LoggerFactory.getLogger(AutoAddWSCWaybillProcessService.class);
	private IWSCWaybillProcessService wSCWaybillProcessService;
	private IWaybillPickupService waybillPickupService;
	private IWSCWayBillManageService wscWayBillManageService;

	public IWSCWayBillManageService getWscWayBillManageService() {
		return wscWayBillManageService;
	}

	public void setWscWayBillManageService(
			IWSCWayBillManageService wscWayBillManageService) {
		this.wscWayBillManageService = wscWayBillManageService;
	}

	public IWSCWaybillProcessService getwSCWaybillProcessService() {
		return wSCWaybillProcessService;
	}

	public void setwSCWaybillProcessService(
			IWSCWaybillProcessService wSCWaybillProcessService) {
		this.wSCWaybillProcessService = wSCWaybillProcessService;
	}

	public IWaybillPickupService getWaybillPickupService() {
		return waybillPickupService;
	}

	public void setWaybillPickupService(IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}
	//根据jobId执行具体的业务逻辑
	public void process(String jobId) {
			//根据jobId从数据库表PKP.T_SRV_ADD_ASYN_WAYBILL中查询相关信息
		List<AddWSCWaybillProcessEntity>  list=wSCWaybillProcessService.queryInfoByJobId(jobId);
			if(list!=null&&CollectionUtils.isNotEmpty(list)){
				for(AddWSCWaybillProcessEntity addWSCWaybillProcessEntity:list){
					if(addWSCWaybillProcessEntity!=null){
						try {
							if(WaybillConstants.ADD_SETTLE_BILL.equals(addWSCWaybillProcessEntity.getRequestType())){
								//总的参数requestBody
								String requestBody = addWSCWaybillProcessEntity.getRequestBody();
								logger.info("调用结算生成财务单据请求参数汇总:"+requestBody);
								String[] splits = requestBody.split("}#");
								splits[0]=splits[0]+"}";
								logger.info("请求参数gainWaybillPickupInfo:"+splits[0]);
								logger.info("请求参数currentInfo:"+splits[1]);
								//参数WaybillPickupInfoDto
								WaybillPickupInfoDto gainWaybillPickupInfo=JSONObject.parseObject(splits[0], WaybillPickupInfoDto.class);
								//参数CurrentInfo
								CurrentInfo currentInfo=JSONObject.parseObject(splits[1], CurrentInfo.class);
								//调用结算接口生成财务单据
								logger.info("调用结算接口生成财务单据开始:",addWSCWaybillProcessEntity.getId());
								waybillPickupService.addWaybill(gainWaybillPickupInfo,currentInfo);
								logger.info("调用结算接口生成财务单据结束:",addWSCWaybillProcessEntity.getId());
								WSCWaybillProcessVo  wSCWaybillProcessVo=new WSCWaybillProcessVo();
								wSCWaybillProcessVo.setId(addWSCWaybillProcessEntity.getId());
								wSCWaybillProcessVo.setFailReason("成功:success");
								wSCWaybillProcessVo.setModifyTime(new Date());
								//假如生成财务单据成功,将成功标识记录到表PKP.T_SRV_ADD_ASYN_WAYBILL中的fail_reason字段中
								wSCWaybillProcessService.updateJobIdToNA(wSCWaybillProcessVo);
							}else if (WaybillConstants.ADD_WAYBILL.equals(addWSCWaybillProcessEntity.getRequestType())) {
								String requestBody = addWSCWaybillProcessEntity.getRequestBody();
								WSCWayBillParamEntity wSCWayBillParamEntity = JSONObject.parseObject(requestBody,WSCWayBillParamEntity.class);
								//推送待刷卡数据
								logger.info("调用结算添加待刷卡数据请求参数:"+requestBody);
								logger.info("调用结算添加待刷卡数据开始:",addWSCWaybillProcessEntity.getId());
								wscWayBillManageService.addWSCWayBill(wSCWayBillParamEntity);
								logger.info("调用结算添加待刷卡数据结束:",addWSCWaybillProcessEntity.getId());
								WSCWaybillProcessVo  wSCWaybillProcessVo=new WSCWaybillProcessVo();
								wSCWaybillProcessVo.setId(addWSCWaybillProcessEntity.getId());
								wSCWaybillProcessVo.setFailReason("成功:success");
								wSCWaybillProcessVo.setModifyTime(new Date());
								//假如生成待刷卡数据成功,将成功标识记录到表PKP.T_SRV_ADD_ASYN_WAYBILL中的fail_reason字段中
								wSCWaybillProcessService.updateJobIdToNA(wSCWaybillProcessVo);
							}
						} catch (Exception e) {
							if(e instanceof SettlementException){
								SettlementException se = (SettlementException)e;
								logger.info("调用结算接口失败:"+addWSCWaybillProcessEntity.getId()+"--------"+se.getErrorCode()+"------"+se.getMessage());
								WSCWaybillProcessVo  wSCWaybillProcessVo=new WSCWaybillProcessVo();
								wSCWaybillProcessVo.setId(addWSCWaybillProcessEntity.getId());
								wSCWaybillProcessVo.setFailReason(se.getErrorCode()+"------"+se.getMessage());
								wSCWaybillProcessVo.setModifyTime(new Date());
								//假如线程执行失败,将异常原因记录到表PKP.T_SRV_ADD_ASYN_WAYBILL中的fail_reason字段中
								wSCWaybillProcessService.updateJobIdToNA(wSCWaybillProcessVo);
							}else{
								logger.info("系统异常:"+addWSCWaybillProcessEntity.getId()+"--------"+ExceptionUtils.getFullStackTrace(e));
								WSCWaybillProcessVo  wSCWaybillProcessVo=new WSCWaybillProcessVo();
								wSCWaybillProcessVo.setId(addWSCWaybillProcessEntity.getId());
								wSCWaybillProcessVo.setJobId(WaybillConstants.UNKNOWN);
								wSCWaybillProcessVo.setFailReason("异常:"+ExceptionUtils.getFullStackTrace(e));
								wSCWaybillProcessVo.setModifyTime(new Date());
								//假如线程执行失败,将异常原因记录到表PKP.T_SRV_ADD_ASYN_WAYBILL中的fail_reason字段中
								wSCWaybillProcessService.updateJobIdToNA(wSCWaybillProcessVo);
							}
						}
					}
				}
			}
		} 
	}

