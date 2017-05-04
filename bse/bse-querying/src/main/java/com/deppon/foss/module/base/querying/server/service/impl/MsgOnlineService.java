package com.deppon.foss.module.base.querying.server.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.common.api.server.dao.IMsgOnlineDao;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineDto;
import com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineEcsDto;
import com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineEcsDtoResponse;
import com.deppon.foss.module.base.common.api.shared.exception.OnlineMessageException;
import com.deppon.foss.module.base.querying.server.service.IMsgOnlineService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillDetailService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.WaybillInfoForNoticeDto;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.util.CollectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MsgOnlineService implements IMsgOnlineService {
	
	/**
	 * 记录日志
	 */
	public static final Logger logger = LoggerFactory
			.getLogger(MsgOnlineService.class);
	
	private IMsgOnlineDao msgOnlineDao;
	private IWaybillManagerService waybillManagerService;
	private IAirWaybillDetailService pointsSingleJointTicketService;
	private IHandOverBillService handOverBillService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	public String msgOnlineFromEcsAddress;

	public String getMsgOnlineFromEcsAddress() {
		return msgOnlineFromEcsAddress;
	}

	public void setMsgOnlineFromEcsAddress(String msgOnlineFromEcsAddress) {
		this.msgOnlineFromEcsAddress = msgOnlineFromEcsAddress;
	}
		/**
	 * 根据条件查询消息
	 * 
	 * @author 130346-foss-lifanghong
     * @date 2013-07-08 
	 */
	public List<MsgOnlineEntity> queryOnlineMsgByEntity(MsgOnlineEntity entity,
			int start, int limit) { 
		//获取查询站内消息的结果集
		List<MsgOnlineEntity>  msgOnlineList= msgOnlineDao.queryOnlineMsgByEntity(entity, start, limit);
		return msgOnlineList;
	}
	/**
	 * 根据条件查询消息总数
	 * 
	 * @author 130346-foss-lifanghong
     * @date 2013-07-08 
	 */
	@Override
	public Long countQueryOnlineMsgByEntity(MsgOnlineEntity entity) {
		return msgOnlineDao.countQueryOnlineMsgByEntity(entity);
	}
	
	
	
	/**
	 * 根据运单号查询部门
	 * 登陆部门如果是收货部门默认带出到达部门，登陆部门如果不是收货部门默认带出收货部门
	 * @author: lifanghong
	 * @date:	2013-7-10上午8:56:58
	 * @param:	String id
	 * @return:	boolean
	 */
	public MsgOnlineDto queryOrgByBillNo(String billNo) {
	//	WaybillEntity waybillEntity = new WaybillEntity();
		MsgOnlineDto msgOnlineDto = new MsgOnlineDto();
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(billNo);
	
		if(waybillEntity != null){
		String deptCode = FossUserContext.getCurrentDeptCode();
			if(deptCode.equals(waybillEntity.getReceiveOrgCode())){
				msgOnlineDto.setReceiveOrgCode(waybillEntity.getLastLoadOrgCode());
				msgOnlineDto.setReceiveOrgName(waybillEntity.getLastLoadOrgName());
			}else{
				msgOnlineDto.setReceiveOrgCode(waybillEntity.getReceiveOrgCode());
				//如果表里没有收获部门名称则通过组织编码查询
				if(StringUtils.isEmpty(waybillEntity.getReceiveOrgName())&&StringUtils.isNotEmpty(waybillEntity.getReceiveOrgCode())){
					waybillEntity.setReceiveOrgName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybillEntity.getReceiveOrgCode()));
				}
				msgOnlineDto.setReceiveOrgName(waybillEntity.getReceiveOrgName());
			}
		}else{
			throw new OnlineMessageException("无此单号！");
		}
		return msgOnlineDto;
	}
	/**
	 * 根据正单号交接单好查询运单信息
	 * 按正单号/交接单号时，接收部门默认是收货部门 
	 * @author: lifanghong
	 * @date:	2013-7-15上午8:56:58
	 */
	public  List<MsgOnlineDto> queryBillNoByNo(MsgOnlineDto msgOnlineDto) {
		List<MsgOnlineDto> msgOnlineDtos = new ArrayList<MsgOnlineDto>();
	//	 List<WaybillInfoForNoticeDto> waybillInfoForNoticeDtos = new ArrayList<WaybillInfoForNoticeDto>();
		 
		if(StringUtils.isNotEmpty(msgOnlineDto.getAirBillNo())){
			List<WaybillInfoForNoticeDto> waybillInfoForNoticeDtos = pointsSingleJointTicketService.queryWaybillInfoByAirwaybillNo(msgOnlineDto.getAirBillNo());
			if(CollectionUtils.isNotEmpty(waybillInfoForNoticeDtos)){
				for(WaybillInfoForNoticeDto waybillInfoForNoticeDto:waybillInfoForNoticeDtos){
					MsgOnlineDto msgDto = new MsgOnlineDto();
				//	WaybillEntity waybillEntity = new WaybillEntity();
					WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillInfoForNoticeDto.getWaybillNo());
					if(waybillEntity != null){
								msgDto.setBillNo(waybillEntity.getWaybillNo());
								msgDto.setBillSendOrgCode(waybillEntity.getReceiveOrgCode());
								msgDto.setBillSendOrgName(waybillEntity.getReceiveOrgName());
						}
					msgOnlineDtos.add(msgDto);
				}
			}else{
				throw new OnlineMessageException("无此单号！");
			}
			//查询快递交接单273311
		}else if (StringUtils.isNotEmpty(msgOnlineDto.getHandOverBillNo())&&(msgOnlineDto.getHandOverBillNo().startsWith("K")
				||msgOnlineDto.getHandOverBillNo().startsWith("L")
				||msgOnlineDto.getHandOverBillNo().startsWith("S")
				||msgOnlineDto.getHandOverBillNo().startsWith("T"))){
			   msgOnlineDtos=this.queryMsgOnlineFromEcs(msgOnlineDto.getHandOverBillNo());
		}else{
		//	List<HandOverBillDetailEntity> handOverBillDetailEntitys = new ArrayList<HandOverBillDetailEntity>(); 
			List<HandOverBillDetailEntity> handOverBillDetailEntitys = handOverBillService.queryHandOverBillDetailByNo(msgOnlineDto.getHandOverBillNo());
			if(CollectionUtils.isNotEmpty(handOverBillDetailEntitys)){
				for(HandOverBillDetailEntity handOverBillDetailEntity:handOverBillDetailEntitys){
					MsgOnlineDto msgDto = new MsgOnlineDto();
				//	WaybillEntity waybillEntity = new WaybillEntity();
					WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(handOverBillDetailEntity.getWaybillNo());
					if(waybillEntity != null){
								msgDto.setBillNo(waybillEntity.getWaybillNo());
								msgDto.setBillSendOrgCode(waybillEntity.getReceiveOrgCode());
								msgDto.setBillSendOrgName(waybillEntity.getReceiveOrgName());
						}
					msgOnlineDtos.add(msgDto);
//					msgDto.setBillNo(handOverBillDetailEntity.getWaybillNo());
//					msgDto.setBillSendOrgName(handOverBillDetailEntity.getReceiveOrgName());
//					msgOnlineDtos.add(msgDto);
				}
			}else{
				throw new OnlineMessageException("无此单号！");
			}
		}
		return msgOnlineDtos;
	}

	/**
	 * <p>查询快递交接单 </p>
	 * @author 273311
	 * @date 2017-2-20 下午6:04:01
	 * @param handOverBillNo
	 * @return
	 * @throws MalformedURLException
	 * @see
	 */
	private List<MsgOnlineDto> queryMsgOnlineFromEcs(String handoverBillNo) {
		logger.info("从快递查询运单开始");
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(this.getMsgOnlineFromEcsAddress());
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
				NumberConstants.NUMBER_5000);
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(NumberConstants.NUMBER_5000);
		method.getParams().setContentCharset("UTF-8");
		method.getParams().setHttpElementCharset("UTF-8");
		// String requestData = "{handoverBillNo:'" + handoverBillNo + "'}";
		String requestData = "{\"handoverBillNo\":\"" + handoverBillNo + "\"}";
		RequestEntity entity = null;
		MsgOnlineEcsDtoResponse msgOnlineEcsDtos = new MsgOnlineEcsDtoResponse();
		List<MsgOnlineDto> msgOnlineDtos = new ArrayList<MsgOnlineDto>();
		try {
			entity = new StringRequestEntity(requestData, "application/json","UTF-8");
			method.setRequestEntity(entity);
			method.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			int statuCode = client.executeMethod(method);
			if (statuCode == HttpStatus.SC_OK) {
				String responseData = method.getResponseBodyAsString();
				ObjectMapper mapper = new ObjectMapper();
				msgOnlineEcsDtos = mapper.readValue(responseData,
						new TypeReference<MsgOnlineEcsDtoResponse>() {
						});
				if (null != msgOnlineEcsDtos) {
					if (msgOnlineEcsDtos.getStatus() == 1
							&& CollectionUtils.isNotEmpty(msgOnlineEcsDtos
									.getData())) {
						for (MsgOnlineEcsDto msgOnlineEcs : msgOnlineEcsDtos
								.getData()) {
							MsgOnlineDto msgDto = new MsgOnlineDto();
							msgDto.setBillNo(msgOnlineEcs.getWaybillNo());
							msgDto.setBillSendOrgCode(msgOnlineEcs
									.getReceiveOrgCode());
							msgDto.setBillSendOrgName(msgOnlineEcs
									.getReceiveOrgName());
							msgOnlineDtos.add(msgDto);
						}
						return msgOnlineDtos;
					}
					if (msgOnlineEcsDtos.getStatus() == 0) {
						throw new OnlineMessageException(
								msgOnlineEcsDtos.getExMsg());
					}
				}
			} else {
				throw new OnlineMessageException("接口异常，操作失败，稍后重试");
			}
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			logger.info("接口异常1" + e.getMessage());
		} catch (HttpException e) {
			logger.info("接口异常2" + e.getMessage());
		} catch (IOException e) {
			// e.printStackTrace();
			logger.info("接口异常3" + e.getMessage());
		}
		return null;

	}

	
	public void setPointsSingleJointTicketService(
			IAirWaybillDetailService pointsSingleJointTicketService) {
		this.pointsSingleJointTicketService = pointsSingleJointTicketService;
	}
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	
	public void setMsgOnlineDao(IMsgOnlineDao msgOnlineDao) {
		this.msgOnlineDao = msgOnlineDao;
	}
	
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public IMsgOnlineDao getMsgOnlineDao() {
		return msgOnlineDao;
	}
	
	
}
