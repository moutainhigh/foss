package com.deppon.foss.module.trackings.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWayBillNoLocusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.trackings.api.server.dao.IWaybillTrackingsDao;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsPushService;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsService;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.trackings.api.shared.domain.WaybillTrackingsLogEntity;
import com.deppon.foss.module.trackings.api.shared.dto.OrderWaybillDto;
import com.deppon.foss.module.trackings.api.shared.dto.ResponseDto;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsRequestDto;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsResultDto;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillTrackService;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillTrackDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

public class WaybillTrackingsService implements IWaybillTrackingsService{
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillTrackingsService.class);
	
	private IWaybillTrackingsDao waybillTrackingsDao;
	private IAdministrativeRegionsService administrativeRegionsService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IWaybillManagerService waybillManagerService;
	private IWayBillNoLocusService wayBillNoLocusService;
	private ILdpAgencyDeptService ldpAgencyDeptService;
	private IWaybillTrackingsPushService waybillTrackingsPushService;
	private	ILdpExternalBillTrackService ldpExternalBillTrackService;
	private ILdpExternalBillService ldpExternalBillService;
	
	/**
	 * @author nly
	 * @date 2015年3月16日 下午5:25:10
	 * @function 业务操作时，保存轨迹信息
	 * 			一、不在订阅运单表中，不保存
	 * 			二、在订阅运单号表中
	 * 			1、is_push=N时，不保存
	 * 			2、push_result=300时，不保存
	 * 			3、watch_status=abort/stop时，不保存
	 * 			4、push_result is null或push_result=400时，保存轨迹，is_can_push = N,is_push=N
	 * 			5、push_result=200时，保存轨迹，is_can_push=Y,is_push=N
	 * 			6、取消出发或反签收或外发单录入取消或取消转寄时，则删除所有轨迹，更新订阅信息表为初始值
	 * @param trackDto
	 * 
	 * 全量推送 + 增量推送时，保存轨迹方法
	 */
	@Override
	public void addOneWaybillTrack(WaybillTrackingsDto trackDto) {
		LOGGER.error("推送轨迹给快递100-保存业务操作开始");
		//单号为订阅单号时，保存操作轨迹
		if(null == trackDto) {
			return;
		}
		LOGGER.error("保存业务操作，业务操作类型：" + trackDto.getOperateType());
		List<OrderWaybillDto> list = waybillTrackingsDao.queryWaybillInfoByNo(trackDto.getWaybillNo());
		LOGGER.error("100订阅单号" + list);
		//订阅无此单号时，不保存轨迹操作
		if(CollectionUtils.isEmpty(list)) {
			LOGGER.error("100订阅单号不存在:" + trackDto.getWaybillNo());
			return;
		}
		//已存在时
		OrderWaybillDto dto = list.get(0);
		if(StringUtils.equals("N", dto.getIsPush())) {
			LOGGER.error("100订阅单号:" + trackDto.getWaybillNo() + "isPush=N");
			return;
		}
		if((StringUtils.equals("300", dto.getPushResult()))) {
			LOGGER.error("100订阅单号:" + trackDto.getWaybillNo() + "pushResult=300");
			return;
		}
		if(StringUtils.equals("abort", dto.getWatchStatus())
				|| StringUtils.equals("stop", dto.getWatchStatus())) {
			LOGGER.error("100订阅单号:" + trackDto.getWaybillNo() + "WatchStatus=" + dto.getWatchStatus());
			return;
		}
		
		if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_CANCEL_DEPART, trackDto.getOperateType())
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_SIGN_RFC, trackDto.getOperateType())
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_PARTIAL_LINE_DEAUDIT, trackDto.getOperateType())
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_SD_TRACK_TRANSFER_CANCEL, trackDto.getOperateType())) {
			LOGGER.error("100订阅单号:" + trackDto.getWaybillNo() + "业务操作为：" + trackDto.getOperateType());
			//删除轨迹
			waybillTrackingsDao.deleteTrackByNo(dto.getCode());
			//更新订阅运单号表中的is_push
			waybillTrackingsDao.updateOrderWaybillInfoByNo(dto.getCode(),null);
			
			return;
		}
		
		// 判断轨迹表中是否有该条轨迹
		boolean isTrackExists = this.checkExistsTrack(trackDto);
		if(isTrackExists) {
			LOGGER.error("100订阅单号:" + trackDto.getWaybillNo() + "轨迹已存在。操作类型：" + trackDto.getOperateType()+"操作时间：" + trackDto.getOperateTime()+"操作部门："+trackDto.getOperateDeptName());
			return;
		}
		trackDto = this.makeTrackDto(trackDto);
		waybillTrackingsDao.saveWaybillTrack(trackDto);
		
		//更新最新轨迹时间
		Date lastTrackTime = dto.getLatestTime();
		if(lastTrackTime == null || trackDto.getOperateTime().compareTo(lastTrackTime) >= 0) {
			dto.setLatestTime(trackDto.getOperateTime());
		}
		waybillTrackingsDao.updateOrderWaybillByNo(dto);
		
		LOGGER.error("100订阅单号:" + trackDto.getWaybillNo() + "保存操作轨迹成功。操作类型：" + trackDto.getOperateType()+"操作时间：" + trackDto.getOperateTime()+"操作部门："+trackDto.getOperateDeptName());
	}
	
	/**
	 * @author nly
	 * @date 2015年3月16日 下午5:27:57
	 * @function 批量新增轨迹信息
	 * @param trackDtoList
	 * @return
	 */
	@Override
	public void addWaybillTracks(List<WaybillTrackingsDto> trackDtoList) {
		for(WaybillTrackingsDto dto : trackDtoList) {
			this.addOneWaybillTrack(dto);
		}
	}
	
	
	
	/**
	 * @author nly
	 * @date 2015年3月24日 下午3:12:50
	 * @function 保存订阅单号
	 * 			 1、判断单号是否为正确的单号
	 * 			 2、单号已存在
	 * 				2.1 若订阅方式为order,返回异常
	 * 				2.2 若订阅方式为repush，则删除所有轨迹,更新订阅表为初始值
	 * 			 3、单号不存在
	 * 				新增订阅信息
	 * @param waybillDto
	 */
	@Override
	public ResponseDto saveOrderWaybillNo(OrderWaybillDto waybillDto) {
		
		String orderWaybillNo = waybillDto.getCode();
		ResponseDto responseDto = new ResponseDto();
		LOGGER.error("保存订阅单号开始，单号：" + orderWaybillNo);
		//单号校验
		//单号错误
		if(StringUtils.isEmpty(orderWaybillNo) 
				|| !StringUtils.isNumericSpace(orderWaybillNo) 
				|| StringUtils.trim(orderWaybillNo).length() <=7
				|| StringUtils.trim(orderWaybillNo).length() >= 11) {
			LOGGER.error("订阅单号错误，单号：" + waybillDto.getCode());
			
			this.saveOrderWaybillLog(orderWaybillNo,WaybillTrackingsConstants.CODE_WAYBILLNO_ERROR,WaybillTrackingsConstants.MSG_WAYBILLNO_ERROR,WaybillTrackingsConstants.LOG_DOP2FOSS_WAYBILLNO_FAILURE);
			
			responseDto.setResult(WaybillTrackingsConstants.FLG_FAILURE);
			responseDto.setReturnCode(WaybillTrackingsConstants.CODE_WAYBILLNO_ERROR);
			responseDto.setMessage(WaybillTrackingsConstants.MSG_WAYBILLNO_ERROR);
			return responseDto;
		}
				
		//重复订阅
		boolean isExists = this.checkExists(orderWaybillNo);
		if(StringUtils.equals(WaybillTrackingsConstants.ORDER_TYPE_ORDER, waybillDto.getOperator()) && isExists) {
			LOGGER.error("订阅单号重复，单号：" + waybillDto.getCode());
			
			this.saveOrderWaybillLog(orderWaybillNo,WaybillTrackingsConstants.CODE_WAYBILLNO_REPEAT,WaybillTrackingsConstants.MSG_WAYBILLNO_REPEAT,WaybillTrackingsConstants.LOG_DOP2FOSS_WAYBILLNO_FAILURE);
			
			responseDto.setResult(WaybillTrackingsConstants.FLG_FAILURE);
			responseDto.setReturnCode(WaybillTrackingsConstants.CODE_WAYBILLNO_REPEAT);
			responseDto.setMessage(WaybillTrackingsConstants.MSG_WAYBILLNO_REPEAT);
			return responseDto;
		}
		
		//repush请求
		if(StringUtils.equals(WaybillTrackingsConstants.ORDER_TYPE_REPUSH, waybillDto.getOperator())) {
			if(isExists) {
				//删除轨迹
				waybillTrackingsDao.deleteTrackByNo(waybillDto.getCode());
				
				waybillTrackingsDao.updateOrderWaybillInfoByNo(waybillDto.getCode(),WaybillTrackingsConstants.ORDER_TYPE_REPUSH);
			} else {
				waybillDto.setOrderCmpName(this.getOrgName(waybillDto.getOrdercompany()));
				// 设置所需属性值
				waybillTrackingsDao.addOrderWaybillNo(waybillDto);
			}
		} else {
			waybillDto.setOrderCmpName(this.getOrgName(waybillDto.getOrdercompany()));
			// 设置所需属性值
			waybillTrackingsDao.addOrderWaybillNo(waybillDto);
		}		
		
		LOGGER.error("订阅单号成功，单号：" + waybillDto.getCode());
		
		this.saveOrderWaybillLog(orderWaybillNo,WaybillTrackingsConstants.CODE_SUCCESS,WaybillTrackingsConstants.MSG_SUCCESS,WaybillTrackingsConstants.LOG_DOP2FOSS_WAYBILLNO_SUCCESS);
		
		responseDto.setResult(WaybillTrackingsConstants.FLG_SUCCESS);
		responseDto.setReturnCode(WaybillTrackingsConstants.CODE_SUCCESS);
		responseDto.setMessage(WaybillTrackingsConstants.MSG_SUCCESS);
		
		return responseDto;
		
	}
	
	/**
	 * @author nly
	 * @date 2015年3月24日 下午2:52:40
	 * @function 推送轨迹给代理公司
	 * 全量推送 + 增量推送
	 */
	@Override
	public void pushWaybillTracks() {
		this.overridePushTrack();
		this.appendPushTrack();
		this.stopPushTrack();
		this.abortPushTrack();
	}
	
	/**
	 * @author nly
	 * @date 2015年3月27日 下午5:16:04
	 * @function 处理快递100返回的推送轨迹结果
	 * 			1、DOP->100->DOP->FOSS:
	 * 				1.1 更新订阅运单号表中的watchStatus和push_result
	 * 				1.2 根据推送结果做处理
	 * 					1.2.1 若push_result=200时，更新轨迹表中已推送路由id的is_push=Y，所有轨迹的is_can_send=Y
	 * 					1.2.2 若push_result=300时，则更新轨迹表中的is_can_push=N
	 * 					1.2.3 若push_result=400时，则删除轨迹，更新is_push=N,watch_status=normal,sign_time=null
	 * 				1.3 保存日志
	 * @param rspDto
	 */
	@Override
	public ResponseDto savePushResult(ResponseDto rspDto) {
		LOGGER.error("保存快递100返回结果开始");
		ResponseDto responseDto = new ResponseDto();
		//  处理返回结果
		String msgId = rspDto.getMsgId();
		LOGGER.error("保存快递100返回结果,msgId===" + msgId);
		//msgId为空
		if(StringUtils.isEmpty(msgId)) {
			responseDto.setResult(WaybillTrackingsConstants.FLG_FAILURE);
			responseDto.setReturnCode(WaybillTrackingsConstants.CODE_MSGID_ERROR);
			responseDto.setMessage(WaybillTrackingsConstants.MSG_MSGID_ERROR);
			LOGGER.error("保存快递100返回结果失败-DOP返回msgId为空");
			return responseDto;
		}		
		String[] str = msgId.split(":");
		String waybillNo = str[0];
		String[] str1 = str[1].split(",");
		String watchStatus = str1[0];
		//String maxId = str1[str1.length - 1];
		List<String> routeList = this.convertRouteId(str1);
		
		OrderWaybillDto dto = new OrderWaybillDto();
		dto.setCode(waybillNo);
		dto.setPushResult(rspDto.getReturnCode());
		//dto.setMaxId(maxId);
		dto.setWatchStatus(watchStatus);
		dto.setIsReturn("Y");
		dto.setIsPush("Y");
		dto.setReturnTime(new Date());
		
		//成功
		if(StringUtils.equals("200", rspDto.getReturnCode())) {
			//abort stop不更新轨迹
			if(StringUtils.equals(WaybillTrackingsConstants.WATCH_STATUS_NORMAL, watchStatus)){
				//更新订阅单号和对应路由轨迹信息
				waybillTrackingsDao.updateTrackByNoAndRoute(waybillNo,routeList);
			}
			//更新订阅运单表
			waybillTrackingsDao.updateOrderWaybillByNoAndPush(dto);
		} else if(StringUtils.equals("300", rspDto.getReturnCode())
				|| StringUtils.equals("501", rspDto.getReturnCode())) {
			//更新订阅运单表
			waybillTrackingsDao.updateOrderWaybillByNoAndPush(dto);
		} else if(StringUtils.equals("400", rspDto.getReturnCode())) {
			//删除轨迹
			waybillTrackingsDao.deleteTrackByNo(dto.getCode());
			//更新订阅运单号表中的is_push
			waybillTrackingsDao.updateOrderWaybillInfoByNo(waybillNo,null);
		} 
		
		try{
			//保存日志
			WaybillTrackingsLogEntity logEntity = new WaybillTrackingsLogEntity();
			logEntity.setMsgId(msgId);
			logEntity.setWaybillNo(waybillNo); 
			logEntity.setCreateTime(new Date());
			logEntity.setCode(rspDto.getReturnCode());
			logEntity.setMsg(rspDto.getMessage());
			logEntity.setType(WaybillTrackingsConstants.LOG_DOP2FOSS_RESULT_SUCCESS);
			
			waybillTrackingsDao.addTrackLog(logEntity);
		} catch(Exception e) {
			LOGGER.error("DOP返回100处理结果，保存日志异常");
		}
		
		responseDto.setResult(WaybillTrackingsConstants.FLG_SUCCESS);
		responseDto.setReturnCode(WaybillTrackingsConstants.CODE_SUCCESS);
		responseDto.setMessage(WaybillTrackingsConstants.MSG_SUCCESS);
		
		LOGGER.error("保存快递100返回结果成功");
		LOGGER.error("保存快递100返回结果结束");
		
		return responseDto;
	}
	
	/**
	 * @author nly
	 * @date 2015年4月4日 下午3:26:47
	 * @function 调用查询轨迹接口推送轨迹，全量推送
	 * 					一、现有轨迹查询接口推送轨迹
	 * 					1.2.1 调用现有轨迹查询接口推送轨迹，对轨迹信息进行封装
	 * 						1.2.1.1 查询is_send为N的运单号
	 * 						1.2.1.2 封装要推送的轨迹信息
	 * 						1.2.1.3 推送轨迹
	 * 							1.2.1.3.1 若DOP成功接收到轨迹，则初始化轨迹表,初始化is_push=N
	 * 													 更新订阅运单号表中的is_push=Y
	 * 							1.2.1.3.2 若DOP未收到轨迹，则不做处理
	 * 						1.2.1.3 保存推送日志
	 */
	private void overridePushTrack() {
		LOGGER.error("全量推送轨迹开始");
		//订阅表中需全量推送的运单
		List<OrderWaybillDto> waybillList = waybillTrackingsDao.queryOverridePushWaybillInfo();
		
		LOGGER.error("全量推送轨迹list===" + waybillList);
		
		Map<String,List<WaybillTrackingsDto>> map = new HashMap<String,List<WaybillTrackingsDto>>();
		
		List<WaybillTrackingsLogEntity> logList = new ArrayList<WaybillTrackingsLogEntity>();
		for(OrderWaybillDto dto : waybillList ) {
			try {
				List<WaybillTrackingsDto> trackDtoList = new ArrayList<WaybillTrackingsDto>();
				//最新轨迹
				WayBillNoLocusDTO lastTrackDto = new WayBillNoLocusDTO();
				
				WaybillTrackingsRequestDto requestDto = new WaybillTrackingsRequestDto();
				requestDto.setCompany(dto.getCompany());
				requestDto.setCallback(dto.getCallback());
				requestDto.setCode(dto.getCode());
				//包裹跟踪状态:normal:跟踪，stop:结束，abort:中止(3无结果)
				String watchStatus = WaybillTrackingsConstants.WATCH_STATUS_NORMAL;
				//运单号:watchStatus,路由id0,路由id1,...
				StringBuffer msgId = new StringBuffer().append(dto.getCode()).append(":");
				//判断该单号是否存在
				//boolean isExists = waybillTrackingsDao.checkWaybillNo(dto.getCode());
				//运单是否存在
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(dto.getCode());
				
				if(null == waybillEntity) {
					LOGGER.error("全量推送轨迹，订阅运单号:" + dto.getCode() + "不存在");
					Long seconds = DateUtils.getSecondDiff(dto.getCreateTime(),new Date());
					//跟踪三天未发现该单号
					if(seconds >= 3*24*60*60) {
						LOGGER.error("全量推送轨迹，订阅运单号：" + dto.getCode() + "订阅三天后仍不存在");
						watchStatus = WaybillTrackingsConstants.WATCH_STATUS_ABORT;
						requestDto.setWatchStatus(watchStatus);
						requestDto.setMsgId(msgId.append(watchStatus).toString());
					} else {
						continue;
					}
				} else {
					//封装轨迹list,路由id，类型转换，当前状态判断
					List<WayBillNoLocusDTO> list = wayBillNoLocusService.getWayBillNoLocus(dto.getCode());
					LOGGER.error("全量推送轨迹，运单号：" + dto.getCode() +"的轨迹list===" + list);
					if(CollectionUtils.isEmpty(list)) {
						LOGGER.error("全量推送轨迹，运单号：" + dto.getCode() +"无轨迹");
						continue;
					}
					//TODO 2015.5.25日与需求确认，20150528版本不推送返货轨迹，故过滤掉返货轨迹。后续需求待定
					for(WayBillNoLocusDTO locusDto : list) {
						if(WaybillTrackingsConstants.OPERATE_TYPE_RETURN_CARGO.equals(locusDto.getOperateType())) {
							list.remove(locusDto);
							break;
						}
					}
					LOGGER.error("全量推送最终轨迹，运单号：" + dto.getCode() +"的轨迹list===" + list);
					if(CollectionUtils.isEmpty(list)) {
						LOGGER.error("全量推送轨迹，运单号：" + dto.getCode() +"无轨迹");
						continue;
					}
					//运单号存在时，调用轨迹查询
					requestDto.setOperation(WaybillTrackingsConstants.PUSH_OVERRID);
					
					int id = 0;
					//包裹运输状态，0在途、1揽收、2疑难、3签收、5派件、7转单
					
					lastTrackDto = list.get(list.size()-1);
					//作废
					if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_ABORTED, lastTrackDto.getOperateType())) {
						watchStatus = WaybillTrackingsConstants.WATCH_STATUS_STOP;
					}	
					String status = this.convertOperateType(lastTrackDto.getOperateType());
					requestDto.setStatus(status);
					requestDto.setWatchStatus(watchStatus);
					
					msgId.append(watchStatus);
					
					List<WaybillTrackingsResultDto> resultList = new ArrayList<WaybillTrackingsResultDto>();
					
					for(WayBillNoLocusDTO locusDto : list) { 
						WaybillTrackingsResultDto resultDto = new WaybillTrackingsResultDto();
						WaybillTrackingsDto trackDto = new WaybillTrackingsDto();
						trackDto.setWaybillNo(dto.getCode());
						trackDto.setOperateDeptCode(locusDto.getOperateOrgCode());
						trackDto.setOperateDeptName(locusDto.getOperateOrgName());
						trackDto.setNextDeptCode(locusDto.getNextOrgCode());
						trackDto.setNextDeptName(locusDto.getNextOrgName());
						trackDto.setOperateTime(locusDto.getOperateTime());
						trackDto.setOperateType(locusDto.getOperateType());
						if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_DELIVERY, locusDto.getOperateType())) {
							//派送时，操作人取派送人
							trackDto.setOperatorName(locusDto.getDeliveryName());
						} else {
							trackDto.setOperatorName(locusDto.getOperateName());
						}
						trackDto.setOperatorPhone(locusDto.getDeliveryPhone());
						trackDto.setOperateDesc(locusDto.getOperateContent());
						trackDto.setRouteId(String.valueOf(id));
						//LDP外发部门所在城市
				          if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_PARTIAL_LINE, locusDto.getOperateType())) {
				        	  LdpExternalBillDto ldpDto = new LdpExternalBillDto();
				        	  ldpDto.setWaybillNo(dto.getCode());
				        	  LdpExternalBillDto billDto = ldpExternalBillService.queryLdpExternalBillByWaybillNo(ldpDto);
				        	  if(null != billDto) {
				        		  trackDto.setOperateDeptCode(billDto.getExternalOrgCode());
				        		  locusDto.setOperateOrgCode(billDto.getExternalOrgCode());
				        	  }
				          }
						// LDP取代理网点
						if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_LEAVE, locusDto.getOperateType())
								|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_ARRIVE, locusDto.getOperateType())
								|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_DELIVER, locusDto.getOperateType())) {
							Map<String,String> ldpMap = this.getLdpOperateInfo(dto.getCode(),locusDto.getOperateType(),locusDto.getOperateTime());
							
							if(StringUtils.isNotEmpty(ldpMap.get("deptCode"))) {
								trackDto.setOperateDeptCode(ldpMap.get("deptCode"));
								locusDto.setOperateOrgCode(ldpMap.get("deptCode"));
							}
							trackDto.setOperateDeptName(ldpMap.get("deptName"));
							trackDto.setOperateDesc(ldpMap.get("city"));
						}
						String location = this.getLocation(dto.getCode(), locusDto.getOperateOrgCode());
						String desc = this.getDesc(trackDto);
						trackDto.setOperateLocation(location);
						trackDto.setOperateDesc(desc);
						trackDtoList.add(trackDto);
						msgId.append(",").append(id);
						
						resultDto.setId(String.valueOf(id));
						id++;
						resultDto.setContext(desc);
						resultDto.setLocation(location);
						resultDto.setOperator(locusDto.getOperateName());
						resultDto.setTime(DateUtils.convert(locusDto.getOperateTime(), DateUtils.DATE_TIME_FORMAT));
						
						resultList.add(resultDto);
					}
					
					requestDto.setMsgId(msgId.toString());
					requestDto.setDetail(resultList);
					
					map.put(dto.getCode(), trackDtoList);
				}
				
				
				ResponseDto responseDto = waybillTrackingsPushService.pushWaybillTrack(requestDto);
				
				WaybillTrackingsLogEntity logEntity = new WaybillTrackingsLogEntity();
				logEntity.setMsgId(requestDto.getMsgId());
				logEntity.setWaybillNo(requestDto.getCode()); 
				logEntity.setCreateTime(new Date());
				//ESB异常
				if(null == responseDto) {
					//保存异常信息，标注ESB异常，便于查找问题原因
					//推送失败时，异常信息处理
					logEntity.setCode("500-ESB");
					logEntity.setMsg("FOSS推送轨迹给DOP时ESB发生异常，exceptionCode-S000099");
					logEntity.setType(WaybillTrackingsConstants.LOG_FOSS2DOP_TRACK_FAILURE);
					
					LOGGER.error("推送overrid轨迹ESB返回异常，msgId="+requestDto.getMsgId());
					
					continue;
				}
				if(StringUtils.equals("true", responseDto.getResult())) {
					
					//保存轨迹
					for(WaybillTrackingsDto track : trackDtoList) {
						waybillTrackingsDao.saveWaybillTrack(track);
					}
					//更新订阅运单表
					dto.setIsPush("Y");
					dto.setIsReturn("N");
					dto.setPushTime(new Date());
					if(null != lastTrackDto.getOperateTime()) {
						dto.setLatestTime(lastTrackDto.getOperateTime());
					}
					
					waybillTrackingsDao.updateOrderWaybillByNo(dto);
					
					//推送成功日志
					logEntity.setCode(responseDto.getReturnCode());
					logEntity.setMsg("FOSS推送轨迹给DOP时,DOP接收数据成功");
					logEntity.setType(WaybillTrackingsConstants.LOG_FOSS2DOP_TRACK_SUCCESS);
					
					LOGGER.error("推送overrid轨迹DOP返回成功，msgId="+requestDto.getMsgId());
				} else {
					//推送失败时，异常信息处理
					                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
					logEntity.setCode(responseDto.getReturnCode());
					logEntity.setMsg("FOSS推送轨迹给DOP时,DOP接收数据失败");
					logEntity.setType(WaybillTrackingsConstants.LOG_FOSS2DOP_TRACK_FAILURE);
					
					LOGGER.error("推送overrid轨迹DOP返回失败，msgId="+requestDto.getMsgId());
				}
				logList.add(logEntity);
			
			} catch(Exception e) {
				LOGGER.error("推送overrid轨迹异常，运单号"+dto.getCode());
			}
		}
		
		
		
		//保存推送日志
		for(WaybillTrackingsLogEntity logEntity : logList) {
			waybillTrackingsDao.addTrackLog(logEntity);
		}		
		
		LOGGER.error("全量推送轨迹结束");
	}

	/**
	 * @author nly
	 * @date 2015年4月4日 下午3:38:44
	 * @function 推送轨迹
	 * 					二、增量推送	
	 * 					1.查血订阅表增量推送的运单
	 * 						1.1 is_push=Y and is_return=Y and watch_status=normal and push_result=200的运单
	 * 						1.2 is_push=Y and is_return=Y and watch_status=normal and push_result in (500,501) and push_time <= sysdate - 30/60/24的运单
	 * 						1.3 is_push=Y and is_return=N and watch_status=normal and push_time<=sysdate-30/60/24的运单 
	 * 					2.查询轨迹表中该订单is_push=N的轨迹信息
	 * 					3. 封装推送轨迹信息
	 * 					4.1 存在推送轨迹
	 * 					       4.1.1 若DOP成功接收到轨迹，则更新订阅表中的is_return=N
	 * 						   4.1.2 若DOP未成功收到轨迹，则不做处理
	 * 					4.2 不存在推送轨迹，则不做处理 
	 * 					5.保存推送日志
	 */
	private void appendPushTrack() {
		LOGGER.error("增量推送轨迹开始");
		//查询需增量推送的订阅运单信息
		List<OrderWaybillDto> waybillInfoList = waybillTrackingsDao.queryAppendPushWaybillInfo();
		
		LOGGER.error("推送append轨迹waybillInfoList=" + waybillInfoList);
		
		List<WaybillTrackingsLogEntity> logList = new ArrayList<WaybillTrackingsLogEntity>();
		
		for(OrderWaybillDto waybillDto : waybillInfoList) {
			try{

				LOGGER.error("增量推送运单号：" + waybillDto.getCode());
				//查询要推送的轨迹信息
				List<WaybillTrackingsDto> trackList = waybillTrackingsDao.queryTrackInfoByWaybillNo(waybillDto.getCode());
				LOGGER.error("推送append轨迹trackList=" + trackList);
				//没有要推送的轨迹时，处理下个运单
				if(CollectionUtils.isEmpty(trackList)) {
					continue;
				}
				
				WaybillTrackingsRequestDto requestDto = new WaybillTrackingsRequestDto();
				requestDto.setCompany(waybillDto.getCompany());
				requestDto.setCallback(waybillDto.getCallback());
				requestDto.setCode(waybillDto.getCode());
				requestDto.setOperation(WaybillTrackingsConstants.PUSH_APPEND);
				
				//运单号:watchStatus,路由id0,路由id1,...
				StringBuffer msgId = new StringBuffer().append(waybillDto.getCode()).append(":");
				//包裹跟踪状态:normal:跟踪，stop:结束，abort:中止(3无结果)
				String watchStatus = WaybillTrackingsConstants.WATCH_STATUS_NORMAL;
				msgId.append(watchStatus);
				
				String status;
				WaybillTrackingsDto lastTrackDto = trackList.get(trackList.size() - 1);
				status = this.convertOperateType(lastTrackDto.getOperateType());	
				//作废
				if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_ABORTED, lastTrackDto.getOperateType())) {
					watchStatus = WaybillTrackingsConstants.WATCH_STATUS_STOP;
				}	
				requestDto.setStatus(status);
				requestDto.setWatchStatus(watchStatus);
				
				List<WaybillTrackingsResultDto> resultList = new ArrayList<WaybillTrackingsResultDto>();
				
				for(WaybillTrackingsDto trackDto : trackList) {
					WaybillTrackingsResultDto resultDto = new WaybillTrackingsResultDto();
					msgId.append(",").append(trackDto.getRouteId());
					
					resultDto.setId(trackDto.getRouteId());
					resultDto.setContext(trackDto.getOperateDesc());
					resultDto.setLocation(this.getLocation(waybillDto.getCode(), trackDto.getOperateDeptCode()));
					resultDto.setOperator(trackDto.getOperatorName());
					//resultDto.setTel(locusDto);
					resultDto.setTime(DateUtils.convert(trackDto.getOperateTime(), DateUtils.DATE_TIME_FORMAT));
					
					resultList.add(resultDto);
				}
				
				requestDto.setMsgId(msgId.toString());
				requestDto.setDetail(resultList);
				
				ResponseDto responseDto = waybillTrackingsPushService.pushWaybillTrack(requestDto);
				
				WaybillTrackingsLogEntity logEntity = new WaybillTrackingsLogEntity();
				logEntity.setMsgId(requestDto.getMsgId());
				logEntity.setWaybillNo(requestDto.getCode());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				logEntity.setCreateTime(new Date());
				
				//ESB异常
				if(null == responseDto) {
					//保存异常信息，标注ESB异常，便于查找问题原因
					//推送失败时，异常信息处理
					logEntity.setCode("500-ESB");
					logEntity.setMsg("FOSS推送轨迹给DOP时ESB发生异常，exceptionCode-S000099");
					logEntity.setType(WaybillTrackingsConstants.LOG_FOSS2DOP_TRACK_FAILURE);
					
					LOGGER.error("推送append轨迹ESB返回异常，msgId="+requestDto.getMsgId());
					
					continue;
				}
				if(StringUtils.equals("true", responseDto.getResult())) {
					//更新is_return为N
					waybillDto.setIsPush("Y");
					waybillDto.setIsReturn("N");
					waybillDto.setPushTime(new Date());
					waybillTrackingsDao.updateOrderWaybillByNoAndPush(waybillDto);
					
					//推送成功日志
					logEntity.setCode(responseDto.getReturnCode());
					logEntity.setMsg("FOSS推送轨迹给DOP时,DOP接收数据成功");
					logEntity.setType(WaybillTrackingsConstants.LOG_FOSS2DOP_TRACK_SUCCESS);
					
					LOGGER.error("推送append轨迹DOP返回成功，msgId="+requestDto.getMsgId());
				} else {
					//推送失败时，异常信息处理
					logEntity.setCode(responseDto.getReturnCode());
					logEntity.setMsg("FOSS推送轨迹给DOP时,DOP接收数据失败");
					logEntity.setType(WaybillTrackingsConstants.LOG_FOSS2DOP_TRACK_FAILURE);
					
					LOGGER.error("推送append轨迹DOP返回失败，msgId="+requestDto.getMsgId());
				}
				logList.add(logEntity);
			
			}catch(Exception e){
				LOGGER.error("推送append轨迹DOP异常，运单号:" + waybillDto.getCode());
			}
		}
		
		
		//保存推送日志
		for(WaybillTrackingsLogEntity logEntity : logList) {
			waybillTrackingsDao.addTrackLog(logEntity);
		}
		
		LOGGER.error("增量推送轨迹结束");
	}
	/**
	 * @author nly
	 * @date 2015年4月10日 下午2:21:52
	 * @function 推送abort请求
	 * 					1.查血订阅表abort推送的运单
	 * 						1.1 is_push=Y and is_return=Y and watch_status=normal and latest_time<=sysdate-30的运单 
	 * 						1.2 is_push=Y and is_return=N and watch_status=normal and latest_time<=sysdate-30 and push_time<=sysdate-30/60/24的运单 
	 * 					2.封装推送轨迹信息
	 * 					3.推送轨迹
	 * 						3.1 若DOP成功接收到轨迹，则更新订阅表中的is_return=N
	 * 						3.2 若DOP未成功收到轨迹，则不做处理
	 * 					4.保存推送日志
	 */
	private void abortPushTrack() {
		LOGGER.error("推送abort轨迹开始");
		List<OrderWaybillDto> waybillList = waybillTrackingsDao.queryAbortPushWaybillInfo();
		List<WaybillTrackingsLogEntity> logList = new ArrayList<WaybillTrackingsLogEntity>();
		
		for(OrderWaybillDto dto : waybillList) {
			try {

				String msgId = dto.getCode() + ":";
				WaybillTrackingsRequestDto requestDto = new WaybillTrackingsRequestDto();
				requestDto.setWatchStatus(WaybillTrackingsConstants.WATCH_STATUS_ABORT);
				requestDto.setCompany(dto.getCompany());
				requestDto.setCode(dto.getCode());
				requestDto.setCallback(dto.getCallback());
				requestDto.setMsgId(msgId + WaybillTrackingsConstants.WATCH_STATUS_ABORT);

				ResponseDto responseDto = waybillTrackingsPushService.pushWaybillTrack(requestDto);
				
				WaybillTrackingsLogEntity logEntity = new WaybillTrackingsLogEntity();
				logEntity.setId(UUIDUtils.getUUID());
				logEntity.setMsgId(requestDto.getMsgId());
				logEntity.setWaybillNo(requestDto.getCode());
				logEntity.setCreateTime(new Date());
				
				if(null == responseDto) {
					//保存异常信息，标注ESB异常，便于查找问题原因
					//推送失败时，异常信息处理
					
					logEntity.setCode("500-ESB");
					logEntity.setMsg("FOSS推送轨迹给DOP时ESB发生异常，exceptionCode-S000099");
					logEntity.setType(WaybillTrackingsConstants.LOG_FOSS2DOP_TRACK_FAILURE);
					
					LOGGER.error("推送abort轨迹ESB返回异常，msgId="+requestDto.getMsgId());
					
					continue;
				}
				if(StringUtils.equals("true", responseDto.getResult())) {
					//及时更新状态
					dto.setIsPush("Y");
					dto.setIsReturn("N");
					dto.setPushTime(new Date());
					waybillTrackingsDao.updateOrderWaybillByNoAndPush(dto);
					
					//推送成功日志
					logEntity.setCode(responseDto.getReturnCode());
					logEntity.setMsg("FOSS推送轨迹给DOP时,DOP接收数据成功");
					logEntity.setType(WaybillTrackingsConstants.LOG_FOSS2DOP_TRACK_SUCCESS);
					
					LOGGER.error("推送abort轨迹DOP返回成功，msgId="+requestDto.getMsgId());
				} else {
					//推送失败时，异常信息处理
					logEntity.setCode(responseDto.getReturnCode());
					logEntity.setMsg("FOSS推送轨迹给DOP时,DOP接收数据失败");
					logEntity.setType(WaybillTrackingsConstants.LOG_FOSS2DOP_TRACK_FAILURE);
					
					LOGGER.error("推送abort轨迹DOP返回失败，msgId="+requestDto.getMsgId());
				}
				logList.add(logEntity);
			
			} catch(Exception e) {
				LOGGER.error("推送abort轨迹DOP异常，运单号：" + dto.getCode());
			}
		}
		
		//保存推送日志
		for(WaybillTrackingsLogEntity logEntity : logList) {
			waybillTrackingsDao.addTrackLog(logEntity);
		}		
		
		LOGGER.error("推送abort轨迹结束");
	}
	/**
	 * @author nly
	 * @date 2015年4月9日 上午8:32:38
	 * @function 推送stop
	 * 	签收后3天无轨迹变化时，推送stop
	 * 					1.查血订阅表stop推送的运单
	 * 						1.1 is_push=Y and is_return=Y and watch_status=normal and sign_time<=sysdate-3的运单 
	 * 						1.2 is_push=Y and is_return=N and watch_status=normal and sign_time<=sysdate-3 and push_time<=sysdate-30/60/24的运单 
	 * 					2.封装推送轨迹信息
	 * 					3.推送轨迹
	 * 						3.1 若DOP成功接收到轨迹，则更新订阅表中的is_return=N
	 * 						3.2 若DOP未成功收到轨迹，则不做处理
	 * 					4.保存推送日志
	 */
	private void stopPushTrack() {
		LOGGER.error("推送stop轨迹开始");
		//需推送stop的运单信息
		List<OrderWaybillDto> waybillList = waybillTrackingsDao.queryStopPushWaybillInfo();
		
		LOGGER.error("stop轨迹list" + waybillList);
		
		List<WaybillTrackingsLogEntity> logList = new ArrayList<WaybillTrackingsLogEntity>();
		
		for(OrderWaybillDto dto : waybillList) {
			try{
				String msgId = dto.getCode() + ":";
				WaybillTrackingsRequestDto requestDto = new WaybillTrackingsRequestDto();
				requestDto.setWatchStatus(WaybillTrackingsConstants.WATCH_STATUS_STOP);
				requestDto.setCompany(dto.getCompany());
				requestDto.setCode(dto.getCode());
				requestDto.setCallback(dto.getCallback());
				requestDto.setMsgId(msgId + WaybillTrackingsConstants.WATCH_STATUS_STOP);

				ResponseDto responseDto = waybillTrackingsPushService.pushWaybillTrack(requestDto);
				
				WaybillTrackingsLogEntity logEntity = new WaybillTrackingsLogEntity();
				logEntity.setId(UUIDUtils.getUUID());
				logEntity.setMsgId(requestDto.getMsgId());
				logEntity.setWaybillNo(requestDto.getCode());
				logEntity.setCreateTime(new Date());
				
				if(null == responseDto) {
					//保存异常信息，标注ESB异常，便于查找问题原因
					//推送失败时，异常信息处理
					
					logEntity.setCode("500-ESB");
					logEntity.setMsg("FOSS推送轨迹给DOP时ESB发生异常，exceptionCode-S000099");
					logEntity.setType(WaybillTrackingsConstants.LOG_FOSS2DOP_TRACK_FAILURE);
					
					LOGGER.error("推送stop轨迹ESB返回异常，msgId="+requestDto.getMsgId());
					
					continue;
				}
				if(StringUtils.equals("true", responseDto.getResult())) {
					//及时更新状态
					dto.setIsPush("Y");
					dto.setIsReturn("N");
					dto.setPushTime(new Date());
					waybillTrackingsDao.updateOrderWaybillByNoAndPush(dto);
					
					//推送成功日志
					logEntity.setCode(responseDto.getReturnCode());
					logEntity.setMsg("FOSS推送轨迹给DOP时,DOP接收数据成功");
					logEntity.setType(WaybillTrackingsConstants.LOG_FOSS2DOP_TRACK_SUCCESS);
					
					LOGGER.error("推送stop轨迹DOP返回成功，msgId="+requestDto.getMsgId());
				} else {
					//推送失败时，异常信息处理
					logEntity.setCode(responseDto.getReturnCode());
					logEntity.setMsg("FOSS推送轨迹给DOP时,DOP接收数据失败");
					logEntity.setType(WaybillTrackingsConstants.LOG_FOSS2DOP_TRACK_FAILURE);
					LOGGER.error("推送stop轨迹DOP返回失败，msgId="+requestDto.getMsgId());
				}
				logList.add(logEntity);
			
			} catch(Exception e){
				LOGGER.error("推送stop轨迹DOP异常，运单号=" + dto.getCode());
			}
		}
		
		//保存推送日志
		for(WaybillTrackingsLogEntity logEntity : logList) {
			waybillTrackingsDao.addTrackLog(logEntity);
		}		
		LOGGER.error("推送stop轨迹结束");
	}
	
	
	/**
	 * @author nly
	 * @date 2015年3月31日 上午9:09:43
	 * @function 封装trackDto的desc
	 * @param trackDto
	 * @return
	 */
	private WaybillTrackingsDto makeTrackDto(WaybillTrackingsDto trackDto) {
		String desc = this.getDesc(trackDto);
		String location = this.getLocation(trackDto.getWaybillNo(),trackDto.getOperateDeptCode());
		String routeId = this.getRouteId(trackDto.getWaybillNo());
		trackDto.setRouteId(routeId);
		trackDto.setOperateLocation(location);
		trackDto.setOperateDesc(desc);
		return trackDto;
	}

	/**
	 * @author nly
	 * @date 2015年4月11日 下午2:58:28
	 * @function 判断轨迹是否已存在
	 * @param trackDto
	 * @return
	 */
	private boolean checkExistsTrack(WaybillTrackingsDto trackDto) {
		return waybillTrackingsDao.checkExistsTrack(trackDto);
	}
	/**
	 * @author nly
	 * @date 2015年4月4日 下午1:49:53
	 * @function 获取routeId
	 * @param waybillNo
	 * @return
	 */
	private String getRouteId(String waybillNo) {
		return waybillTrackingsDao.queryRouteId(waybillNo);
	}
	
	/**
	 * @author nly
	 * @date 2015年4月13日 下午2:19:51
	 * @function 获取落地配轨迹的代理网点和操作城市
	 * @param code
	 * @param operateType
	 * @param operateTime
	 * @return
	 */
	private Map<String,String> getLdpOperateInfo(String code, String operateType,	Date operateTime) {
		String deptCode = "";
		String deptName = "";
		String city = "";
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_LEAVE, operateType)) {
			operateType = "2";
		}else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_ARRIVE, operateType)){
			operateType = "1";
		}else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_DELIVER, operateType)) {
			operateType = "3";
		}
		LdpExternalBillTrackDto ldpDto = new LdpExternalBillTrackDto();
		ldpDto.setWaybillNo(code);
		ldpDto.setOperationtype(operateType);
		ldpDto.setOperationTime(operateTime);
		List<LdpExternalBillTrackDto> ldpList = ldpExternalBillTrackService.queryLdpBillTrackList(ldpDto);
		if(CollectionUtils.isNotEmpty(ldpList) && null != ldpList.get(0)) {
			deptCode = ldpList.get(0).getAgentOrgCode();
			deptName = ldpList.get(0).getAgentOrgName();
			city = ldpList.get(0).getOperationcity();
		}	
		map.put("deptCode", deptCode);
		map.put("deptName", deptName);
		map.put("city", city);
		return map;
	}
	/**
	 * @author nly
	 * @date 2015年3月31日 下午3:39:42
	 * @function 获取轨迹location
	 * @param trackDto
	 * @return
	 */
	private String getLocation(String waybillNo,String operateDeptCode) {
		StringBuffer location = new StringBuffer();
		List<String> codes = new ArrayList<String>();
		OrgAdministrativeInfoEntity  entity = null;
		try{
			entity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(operateDeptCode);
		} catch(Exception e){
			LOGGER.error("推送轨迹-查询操作部门信息异常");
		}
		if(null == entity) {
			//BusinessPartnerExpressEntity entity1 = ldpAgencyCompanyService.queryBusinessPartnerByAgencyDeptCode(operateDeptCode, FossConstants.ACTIVE);
			OuterBranchExpressEntity  entity1 = null;
			try{
				entity1 = ldpAgencyDeptService.queryLdpAgencyDeptByCode(operateDeptCode, "Y");
			}catch(Exception e) {
				LOGGER.error("推送轨迹-查询代理网点信息异常");
			}
			if(null == entity1) {
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
				if(null != waybillEntity){
					codes.add(waybillEntity.getReceiveCustomerProvCode());
					codes.add(waybillEntity.getReceiveCustomerCityCode());
					codes.add(waybillEntity.getReceiveCustomerDistCode());
				}
			} else {
				codes.add(entity1.getProvCode());
				codes.add(entity1.getCityCode());
				codes.add(entity1.getCountyCode());
			}
		} else {
			codes.add(entity.getProvCode());
			codes.add(entity.getCityCode());
			codes.add(entity.getCountyCode());
		}
		
		List<AdministrativeRegionsEntity> list = administrativeRegionsService.queryAdministrativeRegionsBatchByCode(codes);
		if(CollectionUtils.isEmpty(list)) {
			return location.toString();
		}
		for(AdministrativeRegionsEntity e : list) {
			location.append(e.getName());
			location.append(",");
		}
		return location.substring(0, location.length() - 2);
	}

	/**
	 * @author nly
	 * @date 2015年5月28日 上午8:40:39
	 * @function 转null为""
	 * @param str
	 * @return
	 */
	private String parseStr(String str) {
		if(StringUtils.isEmpty(str)) {
			return "";
		} else {
			return str;
		}
	}
	
	/**
	 * @author nly
	 * @date 2015年3月31日 上午9:58:39
	 * @function 获取desc
	 * @param trackDto
	 * @return
	 */
	private String getDesc(WaybillTrackingsDto trackDto) {
		String desc = "";
		String cityName = "";
		String operateType = trackDto.getOperateType();
		OrgAdministrativeInfoEntity  entity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(trackDto.getOperateDeptCode());
		
		if(null != entity && StringUtils.isNotEmpty(entity.getCityCode())) {
			cityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(entity.getCityCode());
		}
		
		if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_CREATE, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_CREATE_PDA, operateType)) {
			
			desc = "揽货成功，[" + this.parseStr(cityName) + "]网点库存中";
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_DEPART, operateType)) {
			
			OrgAdministrativeInfoEntity  nextEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(trackDto.getNextDeptCode());
			String nextCityName = "";
			String orgProperty = "运输中心";
			String nextOrgProperty = "运输中心";
			if(null != entity && StringUtils.equals("Y", entity.getSalesDepartment())) {
				orgProperty = "营业网点";
			}
			if(null != nextEntity ) {
				if(StringUtils.equals("Y", nextEntity.getSalesDepartment())) {
					nextOrgProperty = "营业网点";
				}
				if(StringUtils.isNotEmpty(nextEntity.getCityCode())) {
					nextCityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(nextEntity.getCityCode());
				}
			}
			desc = "运输中，离开[" + this.parseStr(cityName) + "]" + orgProperty +"，下一站[" + this.parseStr(nextCityName) + "]"+ nextOrgProperty;
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_ARRIVE, operateType)) {
			
			String orgProperty = "运输中心";
			if(null != entity && StringUtils.equals("Y", entity.getSalesDepartment())) {
				orgProperty = "营业网点";
			}
			desc = "已到达[" + this.parseStr(cityName) + "]" +  orgProperty;
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY_DELIVER, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY_PICKUP, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY_PRE, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY, operateType)) {
			
			desc = this.parseStr(trackDto.getOperateDesc());
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_DELIVERY, operateType)) {
			
			desc = "派送中(派送人员：" + this.parseStr(trackDto.getOperatorName()) + "，电话：" + this.parseStr(trackDto.getOperatorPhone()) + ")";
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_NORMAL_SIGN, operateType)) {
			
			desc = "正常签收，签收人：" + this.parseStr(trackDto.getOperatorName());
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_DELIVERY_RETURN, operateType)) {
			
			desc = this.parseStr(trackDto.getOperateDesc());
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_EXCEPTION_SIGN, operateType)) {
			
			desc = "异常签收，签收人：" + this.parseStr(trackDto.getOperatorName());
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_ABORTED, operateType)) {
			
			desc = "您好，您所查询的单号" + trackDto.getWaybillNo() + "已作废，请联系营业网点";
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_SD_TRACK_TRANSFER, operateType)) {
			
			desc = "已转寄";
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_PARTIAL_LINE, operateType)) {
			
			desc = "运输中，离开[" + this.parseStr(cityName) + "]运输中心，下一站[" + this.parseStr(cityName) + "]分拣站";
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_LEAVE, operateType)) {
			
			//String addr = this.getReceiveAddress(trackDto.getWaybillNo());
			String addr = this.parseStr(trackDto.getOperateDesc());
			desc = "运输中，离开[" + addr + "]分拣站，下一站[" +  addr + "]分站点";
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_ARRIVE, operateType)) {
			
			//String addr = this.getReceiveAddress(trackDto.getWaybillNo());
			String addr = this.parseStr(trackDto.getOperateDesc());
			desc = "已到达[" + addr + "]分站点";
			
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_DELIVER, operateType)) {
			
			String addr = this.getReceiveAddress(trackDto.getWaybillNo());
			desc = "已到达[" + this.parseStr(addr) + "]分站点，货物分拣派送中";
			
		} 
				
		return desc;
	}
	/**
	 * @author nly
	 * @date 2015年3月31日 下午1:58:34
	 * @function 获取收货人地址-省市区
	 * @param waybillNo
	 * @return
	 */
	private String getReceiveAddress(String waybillNo) {
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if(null == waybillEntity) {
			return "";
		}
		StringBuffer addr = new StringBuffer("");
		List<String> codes = new ArrayList<String>();
		codes.add(waybillEntity.getReceiveCustomerProvCode());
		codes.add(waybillEntity.getReceiveCustomerCityCode());
		codes.add(waybillEntity.getReceiveCustomerDistCode());
		List<AdministrativeRegionsEntity> list = administrativeRegionsService.queryAdministrativeRegionsBatchByCode(codes);
		for(AdministrativeRegionsEntity entity : list) {
			addr.append(entity.getName());
		}
		return addr.toString();
	}
	/**
	 * @author nly
	 * @date 2015年3月31日 上午9:16:52
	 * @function 推送轨迹时，转换操作类型
	 * @param operateType
	 * @return
	 */
	private String convertOperateType(String operateType) {
		if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_CREATE, operateType)) {
			operateType = "1"; //揽件
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_DEPART, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_ARRIVE, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY_DELIVER, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY_PICKUP, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY_PRE, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_SIGN_RFC, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_PARTIAL_LINE, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_LEAVE, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_ARRIVE, operateType)) {
			operateType = "0"; //在途中
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_DELIVERY, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_DELIVER, operateType)) {
			operateType = "5"; //同城派送中
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_NORMAL_SIGN, operateType)) {
			operateType = "3"; //已签收
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_DELIVERY_RETURN, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_EXCEPTION_SIGN, operateType)
				|| StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_ABORTED, operateType)) {
			operateType = "2"; //疑难
		} else if(StringUtils.equals(WaybillTrackingsConstants.OPERATE_TYPE_SD_TRACK_TRANSFER, operateType)) {
			operateType = "7"; //转投
		} 
		return operateType;
	}
	
	/**
	 * @author nly
	 * @date 2015年3月27日 下午2:49:28
	 * @function 单号是否已存在
	 * @param waybillNo
	 * @return
	 */
	private boolean checkExists(String waybillNo) {
		List<OrderWaybillDto> list = waybillTrackingsDao.queryWaybillInfoByNo(waybillNo);
		if(CollectionUtils.isNotEmpty(list)) {
			return true;
		}
		return false;
	}
	/**
	 * @author nly
	 * @date 2015年3月27日 下午5:11:29
	 * @function 代理公司名称
	 * @param orderCmpCode
	 * @return
	 */
	private String getOrgName(String orderCmpCode) {
		String orgName = "";
		if(StringUtils.equalsIgnoreCase("YOUSHANG", orderCmpCode)) {
			orgName = "快递100";
		} else if(StringUtils.equalsIgnoreCase("kuaidi100", orderCmpCode)) {
			orgName = "快递100";
		} 
		return orgName;
	}
	/**
	 * @author nly
	 * @date 2015年3月29日 上午10:16:05
	 * @function 根据msgId获取对应的运单号和路由id
	 * @param msgId
	 * @return
	 */
	private List<String> convertRouteId(String[] route) {
		//路由ID
		List<String> routeList = new ArrayList<String>();
		
		for(int i = 1;i < route.length;i++) {
			routeList.add(route[i]);
		}
		return routeList;
	}
	/**
	 * @author nly
	 * @date 2015年4月16日 下午5:06:04
	 * @function 订阅运单保存日志
	 * @param waybillNo
	 * @param msgCode
	 * @param msg
	 * @param type
	 */
	private void saveOrderWaybillLog(String waybillNo,String msgCode, String msg,String type) {
		WaybillTrackingsLogEntity logEntity = new WaybillTrackingsLogEntity();
		logEntity.setWaybillNo(waybillNo); 
		logEntity.setCreateTime(new Date());
		logEntity.setCode(msgCode);
		logEntity.setMsg(msg);
		logEntity.setType(type);
		waybillTrackingsDao.addTrackLog(logEntity);
		
	}
	public void setWaybillTrackingsDao(IWaybillTrackingsDao waybillTrackingsDao) {
		this.waybillTrackingsDao = waybillTrackingsDao;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setWayBillNoLocusService(
			IWayBillNoLocusService wayBillNoLocusService) {
		this.wayBillNoLocusService = wayBillNoLocusService;
	}

	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	public void setWaybillTrackingsPushService(
			IWaybillTrackingsPushService waybillTrackingsPushService) {
		this.waybillTrackingsPushService = waybillTrackingsPushService;
	}

	public void setLdpExternalBillTrackService(
			ILdpExternalBillTrackService ldpExternalBillTrackService) {
		this.ldpExternalBillTrackService = ldpExternalBillTrackService;
	}

	public void setLdpExternalBillService(
			ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}
	
}
