package com.deppon.foss.module.transfer.agent.server.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.IExpSignToTfrService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IAgentWaybillTrackDao;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.define.TrackingEventTypeEnum;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.CommonInterTrackingRequest;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.CommonInterTrackingResponse;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InterTrackingEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InternationalTrackingEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.service.waybilltrackservice.CommonException;

/**
 * 日本国际件出口轨迹 
 * 
 * @author 352203
 * 
 */
@Controller
public class InternationalTrackingService implements
		IInternationalTrackingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InternationalTrackingService.class);

	private IAgentWaybillTrackDao agentWaybillTrackDao;
	private IExpSignToTfrService expSignToTfrService;
	private IPrintAgentWaybillService printAgentWaybillService;
	private IWaybillManagerService waybillManagerService;
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;
	private IExternalBillDao externalBillDao;

	/**
	 * 1)保存轨迹信息 DOP收到代理返回的轨迹之后,调用该接口将轨迹信息推给FOSS。
	 * 2)轨迹中存在签收操作类型时，调用签收接口进行签收
	 * 3)全量推送轨迹，需要先删除原有轨迹，再进行插入，使用map保存上次插入的轨迹，WQS推送时，
	 * 已经推送过的轨迹不再推送，只有未进行推送过的轨迹才进行推送
	 */
	@Override
	public @ResponseBody CommonInterTrackingResponse addInternationalTracking(@RequestBody CommonInterTrackingRequest commonInterTrackingRequest) throws CommonException {
		// TODO Auto-generated method stub
		//返回对
		CommonInterTrackingResponse response = new CommonInterTrackingResponse();
		
		//运单号
		String waybillNo = commonInterTrackingRequest.getWaybillNo();
		//代理单号
		String agencyNo = commonInterTrackingRequest.getAgencyNo();
		//代理公司编码
		String agencyCompCode = commonInterTrackingRequest.getAgencyCompCode();
		//代理公司名称
		String agencyCompName = commonInterTrackingRequest.getAgencyCompName();
		//目的国
		String destCountry = commonInterTrackingRequest.getDestCountry();
		
		//设置返回运单号
		response.setWaybillNo(waybillNo);
		
		//DOP推送过来的轨迹
		List<InterTrackingEntity> interTrackingEntity = commonInterTrackingRequest.getInterTrackingEntitylist();
		
		//判断推送过来的轨迹是否为空
		if(interTrackingEntity == null || interTrackingEntity.isEmpty()){
			response.setSuccess(false);
			response.setErrorMsg("运单轨迹为空!");
			return response;
		}
		
		//查询运单信息
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		//判断运单信息是否存在
		if(waybillEntity == null){
			response.setSuccess(true);
			response.setErrorMsg("运单信息不存在!");	
			return response;
		}

		//用于保存签收轨迹
		InterTrackingEntity iTracking = null;
		//保存签收待同步轨迹
		SynTrackingEntity trackSigned = null;
		
		// 上次DOP推送过来的数据
		List<InternationalTrackingEntity> listLastInternationalTracking = agentWaybillTrackDao.queryInterTrackingsEntity(waybillNo);
		
		//使用Map保存上次的轨迹信息，用于WQS推送时的比较
		Map<String, InternationalTrackingEntity> mapOperateTypeAndTime = new HashMap<String, InternationalTrackingEntity>();
		if (listLastInternationalTracking != null) {
			for (InternationalTrackingEntity e : listLastInternationalTracking) {
				//将操作类型和操作时间拼接成一个字符串
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String sDate=sdf.format(e.getOperateTime());
				mapOperateTypeAndTime.put(e.getOperateType()+sDate, e);
			}
			//全量推送,先删除原有轨迹
			agentWaybillTrackDao.deleteInternationTrackings(waybillNo);
		}
		
		Map<String,InterTrackingEntity> map = new HashMap<String, InterTrackingEntity>();
		// 保存轨迹信息
		for (InterTrackingEntity ite : interTrackingEntity) {
			InternationalTrackingEntity internationalTrackingEntity = new InternationalTrackingEntity();
			//操作类型不为空
			if(ite.getOperateType().isEmpty()){
				throw new CommonException("运单号："+waybillNo+" 操作类型为空!");
			}
			//去除重复轨迹
			if(map.isEmpty()){
				map.put(ite.getOperateType()+ite.getOperateTime().toString(), ite);
			}else{
				//已经保存过该轨迹
				InterTrackingEntity e = map.get(ite.getOperateType()+ite.getOperateTime().toString());
				if(e != null){
					continue;
				}
				map.put(ite.getOperateType()+ite.getOperateTime().toString(), ite);
			}
			internationalTrackingEntity.setId(UUIDUtils.getUUID());
			internationalTrackingEntity.setWaybillNo(waybillNo);
			internationalTrackingEntity.setChannel(Constants.INTERNATIONAL_TRACK_OTHER);
			internationalTrackingEntity.setAgencyNo(agencyNo);
			internationalTrackingEntity.setAgencyCompCode(agencyCompCode);
			internationalTrackingEntity.setAgencyCompName(agencyCompName);
			internationalTrackingEntity.setDestCountry(destCountry);
			internationalTrackingEntity.setNote(ite.getNote());
			internationalTrackingEntity.setOperateTime(DateUtils.convert(ite.getOperateTime()));
			internationalTrackingEntity.setCityName(ite.getCityName());
			internationalTrackingEntity.setOperateType(ite.getOperateType());
			//保存轨迹到国际外发轨迹表中,最后操作签收轨迹
			if(StringUtils.equals(Constants.INTERNATIONAL_TRACK_SIGNED, ite.getOperateType())){
				iTracking = ite;
			}
			try {
				//保存轨迹
				agentWaybillTrackDao.addInternationTrackings(internationalTrackingEntity);
			} catch (Exception e) {
				LOGGER.error(waybillNo + ":日本国际件轨迹保存失败!");
				throw new CommonException(e.getMessage());
			}
			/*
			 * 比较本次和上次DOP传送过来的轨迹，将新的记录插入到给WQS轨迹表
			 */
			if(isSend(mapOperateTypeAndTime,ite)){
				SynTrackingEntity track = new SynTrackingEntity();
				
				track.setId(UUIDUtils.getUUID());
				// 运单
				track.setWayBillNo(waybillNo);
				// 发生时间
				track.setOperateTime(DateUtils.convert(ite.getOperateTime()));
				//创建时间
				track.setCreateDate(new Date());
				//更新时间
				track.setModifyDate(new Date());
				TrackingEventTypeEnum trackEnum = getTrackingEventTypeEnum(ite.getOperateType());
				//跟踪信息描述
				track.setTrackInfo(trackEnum == null ? "" : trackEnum.getBizName());
				//事件
				track.setEventType(trackEnum == null ? "" : trackEnum.getWqsTrackCode());
				//目的部门所在城市
				track.setOperateCity(ite.getCityName());
				//目的部门名称
				track.setDestinationDeptName(waybillEntity.getCustomerPickupOrgName());
				//代理单号
				track.setNextMailNos(agencyNo);
				//代理公司编码
				track.setNextTpCode(agencyCompCode);
				//代理公司名称
				track.setAgentCompanyName(agencyCompName);
				//目的国
				track.setDestCountryName(destCountry);
				//如果为签收轨迹,则跳过,在调用签收成功后再推签收轨迹给wqs
				if(StringUtils.equals(Constants.INTERNATIONAL_TRACK_SIGNED, ite.getOperateType())){
					trackSigned = track;
					continue;
				}
				try {
					pushTrackForCaiNiaoService.addSynTrack(track);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("插入中转轨迹推送表失败，原因：" + e.getMessage());
					throw new CommonException(e.getMessage());
				}
			}
		}
		
		//收到的轨迹中有签收操作类型，调用FOSS签收接口，运单自动签收
		if(iTracking != null){
			// 根据代理单号和代理公司查询绑定记录
			// 结算接口必须字段waybillNo situation signGoodsQty signTime
			// operator operatorCode operateOrgName operateOrgCode
			ExternalBillDto dto = new ExternalBillDto ();
			dto.setExternalBillNo(agencyNo);
			dto.setAgentCompanyCode(agencyCompCode);
			//偏线外发
			List<ExternalBillDto> listExternalBillDto = externalBillDao.queryByExternalBillNo(dto);
			//快递外发
			List<PrintAgentWaybillRecordEntity> recordList = printAgentWaybillService.queryRecordByAgentWaybillNo(agencyNo);
			if(listExternalBillDto != null && !listExternalBillDto.isEmpty()){
				for (ExternalBillDto record : listExternalBillDto) {
					ArriveSheetEntity entity = new ArriveSheetEntity();
					entity.setWaybillNo(waybillNo); // 运单号
					entity.setSituation(SignConstants.NORMAL_SIGN);// 正常签收
					//偏线一次签收所有件数
					entity.setSignGoodsQty(waybillEntity.getGoodsQtyTotal()); // 签收件数
					entity.setOperatorCode(record.getExternalUserCode()); // 绑定人工号
					entity.setOperateOrgCode(record.getExternalOrgCode()); // 绑定部门编码
					entity.setOperateOrgName(record.getExternalOrgName()); // 绑定部门名称
					entity.setSignNote(TransferConstants.AUTO_SIGN);// 签收备注
					entity.setSignTime(DateUtils.convert(iTracking.getOperateTime()));// 签收时间
					try {
						expSignToTfrService.addExpressArrivesheetForTfr(entity);
						//推送签收轨迹给wqs
						pushTrackForCaiNiaoService.addSynTrack(trackSigned);
					}catch (Exception e) {
						e.printStackTrace();
						LOGGER.error("调用签收接口异常!" + e.getMessage());
						throw new CommonException(e.getMessage());
					}
				}
			}else if((recordList != null && !recordList.isEmpty())){
				for (PrintAgentWaybillRecordEntity record : recordList) {
					ArriveSheetEntity entity = new ArriveSheetEntity();
					entity.setWaybillNo(waybillNo); // 运单号
					entity.setSituation(SignConstants.NORMAL_SIGN);// 正常签收
					//快递按件签收
					entity.setSignGoodsQty(1); // 签收件数 按件签收 件数为1
					entity.setOperatorCode(record.getOperatorCode()); // 绑定人工号
					entity.setOperateOrgCode(record.getOrgCode()); // 绑定部门编码
					entity.setOperateOrgName(record.getOrgName()); // 绑定部门名称
					entity.setSignNote(TransferConstants.AUTO_SIGN);// 签收备注
					entity.setSignTime(DateUtils.convert(iTracking.getOperateTime()));// 签收时间
					try {
						expSignToTfrService.addExpressArrivesheetForTfr(entity);
						//推送签收轨迹给wqs
						pushTrackForCaiNiaoService.addSynTrack(trackSigned);
					}catch (Exception e) {
						e.printStackTrace();
						LOGGER.error("调用签收接口异常!" + e.getMessage());
						throw new CommonException(e.getMessage());
					}
				}
			}else{
				response.setSuccess(false);
				response.setErrorMsg("未查到代理单号绑定记录!");	
				return response;
			}
		}
		//全部插入成功
		response.setSuccess(true);
		return response;
	}
	
	private TrackingEventTypeEnum getTrackingEventTypeEnum(String status) {
		/**
		 * 清关到达 ARRIVAL_CLEARANCE   10
		 * 转运场到达 ARRIVETRANFIELD   11
		 * 到达目的站 ARRIVE_DESTINATION 12
		 * 派送 LDP_TRACK_DELIVER   13
		 * 签收 SIGNED 14
		 * 派送异常 LDPDELIVERERROR  20 
		 * 已退回 RETURN 21
		 * 派送拉回-地址更改 LDPDELIVERERROR 22 
		 * 派送拉回-派送未完成 LDPDELIVERERROR 23
		 * 派送拉回-地址错误 LDPDELIVERERROR 24
		   */
		return PartiallineConstants.enumMap.get(PartiallineConstants.TRACK_PREFIX + status);
	}
	
	/**
	 * 用于判断轨迹是否已经推送过
	 * @param mapOperateTypeAndTime
	 * @return 
	 */
	private boolean isSend(Map<String, InternationalTrackingEntity> mapOperateTypeAndTime,InterTrackingEntity ite){
		//该运单还没有推送过轨迹
		if(mapOperateTypeAndTime == null){
			return true;
		}
		//该运单还没有推送过该操作类型的轨迹
		if(mapOperateTypeAndTime.get(ite.getOperateType()+ite.getOperateTime().toString())==null){
			return true;
		}
		return false;
	}

	public void setExpSignToTfrService(IExpSignToTfrService expSignToTfrService) {
		this.expSignToTfrService = expSignToTfrService;
	}

	public void setAgentWaybillTrackDao(
			IAgentWaybillTrackDao agentWaybillTrackDao) {
		this.agentWaybillTrackDao = agentWaybillTrackDao;
	}

	public void setPrintAgentWaybillService(
			IPrintAgentWaybillService printAgentWaybillService) {
		this.printAgentWaybillService = printAgentWaybillService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}

	public void setExternalBillDao(IExternalBillDao externalBillDao) {
		this.externalBillDao = externalBillDao;
	}
}
