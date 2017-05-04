package com.deppon.foss.module.transfer.partialline.server.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.module.base.common.api.server.dao.IMsgOnlineDao;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.transfer.partialline.api.server.service.IDOPTOFOSSService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IDpjzSignInMsgService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.DpjzSignInDetialBillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.SignInfoAuditRequest;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.SignInfoAuditResponse;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.DpjzSignInDetailDto;
import com.deppon.foss.util.DateUtils;

/**
 * 德邦家装
 * 家装送装明细及签收确认
 * 
 * @author foss-lln-269701
 * @date 2015-12-03 上午11:01:32
 */
public class DOPTOFOSSService implements IDOPTOFOSSService{
	
	static final Logger LOGGER = LoggerFactory.getLogger(DOPTOFOSSService.class);
	/**
	 * 德邦家装-家装送装明细及签收确认
	 */
	private IDpjzSignInMsgService dpjzSignInMsgService;
	
	/**
	 * 在线通知
	 */
	private IMsgOnlineDao msgOnlineDao;
	/**
	 * 德邦家装
	 * 家装送装明细及签收确认
	 * 
	 * @author foss-lln-269701
	 * @date 2015-12-03 上午11:01:32
	 */
	@Transactional
	@Override
	public @ResponseBody SignInfoAuditResponse addDpjzSignInDetialBillsFromDop(@RequestBody SignInfoAuditRequest requestParam) {
		
		LOGGER.error("=====FOSS接收DOP推送家装送装明细及签收确认信息开始：");
		System.out.println("=====唯一识别id："+requestParam.getUniqueRequestId());
		System.out.println("======运单号："+requestParam.getWaybillNo());
		//返回实体
		SignInfoAuditResponse response = new SignInfoAuditResponse();
		//获取传入参数
		String uniqueRequestId = requestParam.getUniqueRequestId();
		String waybillNo= requestParam.getWaybillNo();
		LOGGER.error("=====FOSS接收DOP推送家装送装明细及签收确认信息开始："+"======运单号："+waybillNo+"=====唯一识别id："+uniqueRequestId);
		LOGGER.info("=====FOSS接收DOP推送家装送装明细及签收确认信息开始："+"======运单号："+waybillNo+"=====唯一识别id："+uniqueRequestId);
		try{
			
		if(StringUtils.isBlank(waybillNo)){
			response.setBeSuccess(false);
			response.setReturnType("addDpjzSignInDetialBillsFromDop");
			response.setWaybillNo(waybillNo);
			response.setFailureReason("传入的运单号为空！");
			return response;
		}
		if(StringUtils.isBlank(uniqueRequestId) ){
			response.setBeSuccess(false);
			response.setReturnType("addDpjzSignInDetialBillsFromDop");
			response.setWaybillNo(waybillNo);
			response.setFailureReason("传入的id为空！");
			return response;
		}
		if(StringUtils.isBlank(requestParam.getTranscargoName())){
			response.setBeSuccess(false);
			response.setReturnType("addDpjzSignInDetialBillsFromDop");
			response.setWaybillNo(waybillNo);
			response.setFailureReason("传入的德邦开单安装明细为空！");
			return response;
		}
		if(StringUtils.isBlank(requestParam.getRealInstallInfo())){
			response.setBeSuccess(false);
			response.setReturnType("addDpjzSignInDetialBillsFromDop");
			response.setWaybillNo(waybillNo);
			response.setFailureReason("传入的供应商送装明细为空！");
			return response;
		}
				DpjzSignInDetialBillEntity dpjzSignEntity = new DpjzSignInDetialBillEntity();
				//DOP推送数据唯一ID
				dpjzSignEntity.setDopId(requestParam.getUniqueRequestId());
				//运单号
				dpjzSignEntity.setWaybillNo(requestParam.getWaybillNo());
				//德邦开单安装明细
				dpjzSignEntity.setTranscargoName(requestParam.getTranscargoName());
				//供应商送装明细
				dpjzSignEntity.setRealInstallInfo(requestParam.getRealInstallInfo());
				//供应商反馈时间
				dpjzSignEntity.setFeedBackTime(requestParam.getFeedBackTime());
				
				/*
				 * 01正常签收;
				 * 02异常-破损; 
				 * 03异常-潮湿; 
				 * 04异常-污染; 
				 * 05异常-内物短少 ;
				 * 06异常-其他 ;
				 * 07同票多类异常;
				 * 08货物及费用与运单信息不符;
				 */
				//签收状态转换为汉字用
				String signState=requestParam.getSignState();
				if(StringUtils.equals(signState,"01")){
					signState="正常签收";
				}else if(StringUtils.equals(signState,"02")){
					signState="异常-破损";
				}else if(StringUtils.equals(signState,"03")){
					signState="异常-潮湿";
				}else if(StringUtils.equals(signState,"04")){
					signState="异常-污染";
				}else if(StringUtils.equals(signState,"05")){
					signState="异常-内物短少";
				}else if(StringUtils.equals(signState,"06")){
					signState="异常-其他";
				}else if(StringUtils.equals(signState,"07")){
					signState="同票多类异常";
				}else if(StringUtils.equals(signState,"08")){
					signState="货物及费用与运单信息不符";
				}
				//供应商签收信=签收状态+签收备注+签收件数+供应商签收时间+供应商名称
				dpjzSignEntity.setSignInMsg("签收状态:"+signState+","//签收状态
								+"签收备注:"+requestParam.getSignMemo()+","//签收备注
								+"签收件数:"+requestParam.getSignNumber()+","//签收件数
								+"签收时间:"+DateUtils.convert(requestParam.getSignTime(),DateUtils.DATE_TIME_FORMAT)+","//签收时间
								+"供应商名称:"+requestParam.getSuppName());//供应商名称
				//供应商code
				dpjzSignEntity.setSuppCode(requestParam.getSuppCode());
				
				//将接收到的数据插入家装送装明细及签收确认信息表
				dpjzSignInMsgService.addDpjzSignResultForTfr(dpjzSignEntity);
				
				//269701--lln--2016-01-22 begin
				//在线通知使用
				MsgOnlineEntity msgOnlineEntity=new MsgOnlineEntity();
				//0代表界面新增数据
				int status = 0;
				LOGGER.error("------------对应收货部门自动发在线通知开始-----------------");
				/**向对应的收货部门，同时给收货部门发送在线通知，提醒出发部门及时核对供应商推送的信息**/
				/*在线通知内容：家装运单：******，供应商已反馈完安装明细信息，请及时进行核对*/
				msgOnlineEntity.setWaybillNo(requestParam.getWaybillNo());
				StringBuffer msg=new StringBuffer();
				msg.append("家装运单：****运单号: ")
				.append(requestParam.getWaybillNo())
				.append(" , 供应商已反馈完安装明细信息，请及时进行核对");
				//消息内容
				msgOnlineEntity.setContext(msg.toString());
				//根据运单号查询运单表，获取收货部门信息
				DpjzSignInDetailDto entity=dpjzSignInMsgService.queryDpjzReceiveMsgWaybill(requestParam.getWaybillNo());
				
				//收货部门code
				msgOnlineEntity.setReceiveOrgCode(entity.getReceiveOrgCode());
				//收货部门name
				msgOnlineEntity.setReceiveOrgName(entity.getReceiveOrgName());
				//受理部门code
				msgOnlineEntity.setSendOrgCode(entity.getReceiveOrgCode());
				//受理部门name
				msgOnlineEntity.setSendOrgName(entity.getReceiveOrgName());
				//起草人
				msgOnlineEntity.setCreateUser("系统自动起草在线通知");
				msgOnlineEntity.setAcceptStatus(MessageConstants.MSG__STATUS__PROCESSING);
				//推送在线通知
				msgOnlineDao.addOnlineMsg(msgOnlineEntity,status);
				LOGGER.error("------------对应收货部门自动发在线通知结束-----------------");
				//269701--lln--2016-01-22 end
			
		response.setBeSuccess(true);
		response.setReturnType("addDpjzSignInDetialBillsFromDop");
		response.setWaybillNo(waybillNo);
		LOGGER.info("FOSS接收DOP推送家装送装明细及签收确认信息结束："+"======运单号："+waybillNo+"=====唯一识别id："+uniqueRequestId);
		}catch(Exception e){
			LOGGER.error("=====异常 FOSS接收DOP推送家装送装明细及签收确认信息结束"+"======运单号："+waybillNo+"=====唯一识别id："+uniqueRequestId);
			response.setBeSuccess(false);
			response.setReturnType("addDpjzSignInDetialBillsFromDop");
			response.setFailureReason(e.getMessage());
			e.printStackTrace();
			return response;
		}
		return response;
	}
	/**
	 * @param dpjzSignInMsgService the dpjzSignInMsgService to set
	 */
	public void setDpjzSignInMsgService(IDpjzSignInMsgService dpjzSignInMsgService) {
		this.dpjzSignInMsgService = dpjzSignInMsgService;
	}

	/**
	 * @param msgOnlineDao the msgOnlineDao to set
	 */
	public void setMsgOnlineDao(IMsgOnlineDao msgOnlineDao) {
		this.msgOnlineDao = msgOnlineDao;
	}

}
