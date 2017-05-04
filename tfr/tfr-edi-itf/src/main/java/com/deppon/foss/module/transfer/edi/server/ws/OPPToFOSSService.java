package com.deppon.foss.module.transfer.edi.server.ws;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirJointTicketService;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirQueryFlightArriveDao;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirQueryModifyPickupbillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirChangeInventoryService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpDetialInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpSerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirRevisebillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceDetailVolumeEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.OppLocusEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirChangeInventoryException;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.edi.server.domain.OPPLocusRequest;
import com.deppon.foss.module.transfer.edi.server.domain.OPPLocusResponse;
import com.deppon.foss.module.transfer.edi.server.domain.OppWaybillNoRequest;
import com.deppon.foss.module.transfer.edi.server.domain.RfcPushDataToFossRequst;
import com.deppon.foss.module.transfer.edi.server.domain.SerialNoByOppWaybillNoResponse;
import com.deppon.foss.module.transfer.edi.server.domain.WQSConstans;
import com.deppon.foss.module.transfer.edi.server.inter.IOPPToFOSSService;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
* @description OPP推送数据至FOSS foss作为服务端
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月12日 上午11:46:05
 */
public class OPPToFOSSService implements IOPPToFOSSService {
	
	static final Logger LOGGER = LoggerFactory.getLogger(OPPToFOSSService.class);
	/**接送货接口 根据运单号查询 运单所有流水号*/
	private ILabeledGoodService  labeledGoodService;
	/**空运到达 */
	private IAirQueryFlightArriveDao airQueryFlightArriveDao;
	/****接送货接口：获取运单基础信息****/
	private IWaybillManagerService waybillManagerService;
	/*****推送接受合大票信息 Dao********/
	private IPushAirPickUpInfoDao pushAirPickUpInfoDao;
	/**
	 * 新增合大票service
	 */
	private IAirTransPickupBillService airTransPickupBillService;
	/**
	 * 注入查询修改合大票dao
	 */
	private IAirQueryModifyPickupbillDao airQueryModifyPickupbillDao;
	/**
	 * 运单签收service
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * 结算合票预收应付接口
	 */
	private IAirJointTicketService airJointTicketService;
	/** 注入变更服务. */
	private IAirChangeInventoryService airChangeInventoryService;
	
	/**
	 * 货物轨迹推送接口
	 */
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;
	
	
	/**
	 * 通过城市Code查到城市中文名称
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	private String trackInfo;
	private String destinationCityName;
	
	/**
	 * 
	* @description 根据OPP传输的运单号查询该运单对应的流水
	* @see com.deppon.foss.module.transfer.edi.server.inter.IOPPToFOSSService#getSerialNoByOppWaybillNo(OppWaybillNoRequest)
	* @author 269701-foss-lln
	* @param 运单号
	* @update 2016年5月12日 上午11:46:00
	* @version V1.0
	 */
	@Override
	public @ResponseBody SerialNoByOppWaybillNoResponse getSerialNoByOppWaybillNo(@RequestBody OppWaybillNoRequest requstStr,HttpServletResponse resp) {
		
		LOGGER.error("=====FOSS接收OPP推送运单号开始："+new Date());
		//返回给OPP数据
		SerialNoByOppWaybillNoResponse response=new SerialNoByOppWaybillNoResponse();
    	//请求参数 运单号
    	String waybillNo=requstStr.getWaybillNo();
	    try {   
				//校验数据
				if(StringUtils.isEmpty(waybillNo)){
					response.setBeSuccess(false);
					response.setReturnType("getSerialNoByOppWaybillNo");
					response.setWaybillNo(waybillNo);
					response.setFailureReason("传入的运单号为空！");
					return response;
				}
				//运单号不为空，调用接送货现有接口 获得流水号
				LOGGER.error("=====FOSS接收OPP推送运单号，调用接送货接口，获取流水开始;运单号："+waybillNo);
				List<LabeledGoodEntity> labeledGoodsList = labeledGoodService.queryAllSerialByWaybillNo(waybillNo);
				LOGGER.error("=====FOSS接收OPP推送运单号，调用接送货接口，获取流水结束;运单号："+waybillNo);
				
				LOGGER.error("=====FOSS接收OPP推送运单号，调用接送货接口，获取运单基础信息开始;运单号："+waybillNo);
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
				LOGGER.error("=====FOSS接收OPP推送运单号，调用接送货接口，获取运单基础信息结束;运单号："+waybillNo);
				//接送货查询流水list
				List<String> serialList=new ArrayList<String>();
				if(labeledGoodsList.size()>0){
					//为流水号赋值
					for(LabeledGoodEntity entity:labeledGoodsList){
						serialList.add(entity.getSerialNo());
					}
				}
			//运单基础信息
			response.setWaybillEntity(waybillEntity);
			response.setSerialNoList(serialList);
			response.setBeSuccess(true);
			response.setReturnType("getSerialNoByOppWaybillNo");
			response.setWaybillNo(waybillNo);
			resp.setHeader("ESB-ResultCode", "1");
			LOGGER.error("=====FOSS接收OPP推送运单号结束："+new Date());
		} catch (Exception e1) {
			response.setBeSuccess(false);
			response.setReturnType("getSerialNoByOppWaybillNo");
			response.setWaybillNo(waybillNo);
			resp.setHeader("ESB-ResultCode", "0");
			response.setFailureReason(e1.getMessage());
			LOGGER.error("=====FOSS接收OPP推送运单号出现异常："+new Date());
			return response;
		}
		return response;
	}
	/**
	 * 
	* @description OPP推送 出发到达以及返货轨迹至FOSS综合查询
	* @see com.deppon.foss.module.transfer.edi.server.inter.IOPPToFOSSService#getOppLocusForBse(com.deppon.foss.module.transfer.airfreight.api.shared.domain.OppLocusEntity)
	* @author 269701-foss-lln
	* @update 2016年5月16日 下午8:03:56
	* @version V1.0
	 */
	@Override
	public @ResponseBody OPPLocusResponse getOppLocusForBse(@RequestBody OPPLocusRequest requstStr,HttpServletResponse resp) {
		LOGGER.error("=====FOSS接收OPP推送出发到达返货轨迹开始："+new Date());
		LOGGER.error("=====FOSS接收OPP推送出发到达返货轨迹 参数json："+requstStr);
		//返回给OPP数据
		OPPLocusResponse response=new OPPLocusResponse();
		//运单list
		List<String> waybillNoList=new ArrayList<String>();
		//清单list
		List<String> airwaybillList=new ArrayList<String>();

		if(null==requstStr){
			response.setBeSuccess(false);
			response.setReturnType("getOppLocusForBse");
			response.setFailureReason("推送数据为空");
			return response;
		}
		//OPP推送轨迹list
		List<OppLocusEntity> oppLocus=requstStr.getOppLocus();
		try{
		for(OppLocusEntity entity:oppLocus){
			//运单号
			String waybillNo=entity.getWaybillNo();
			//清单号
			String airWaybillNo=entity.getAirWaybillNo();
			waybillNoList.add(waybillNo);
			airwaybillList.add(airWaybillNo);
				//校验运单是否为空
				if(StringUtils.isEmpty(waybillNo)){
					response.setBeSuccess(false);
					response.setReturnType("getOppLocusForBse");
					response.setAirWaybillNo(airwaybillList);
					response.setWaybillNo(waybillNoList);
					response.setFailureReason("传入的运单号为空！");
					return response;
				}
				
				LOGGER.error("=====FOSS接收OPP推送出发到达返货轨迹，保存数据至FOS开始;运单号："+waybillNo+"清单号:"+airWaybillNo);
				//id
				entity.setId(UUIDUtils.getUUID());
				//货物状态：运输中
				entity.setCurrentStatus("运输中");
				//操作状态：OPP_DDEPART出发代理已出发 OPP_DARRIVEL出发代理已到达  OPP_AARRIVEL到达代理已到达 OPP_RETURN 已返货
				if(StringUtils.equals("20",entity.getOperStatus())){
					//出发代理已到达
					entity.setOperStatus("OPP_DARRIVEL");
				}else if(StringUtils.equals("30",entity.getOperStatus())){
					//出发代理已出发
					entity.setOperStatus("OPP_DDEPART");
				}else if(StringUtils.equals("40",entity.getOperStatus())){
					//到达代理已到达 
					entity.setOperStatus("OPP_AARRIVEL");
				}else if(StringUtils.equals("OPERATE_TYPE_RETURN",entity.getOperStatus())){
					//已返货
					entity.setOperStatus("OPP_RETURN");
				}
				airQueryFlightArriveDao.saveOppLocus(entity);
				LOGGER.error("=====FOSS接收OPP推送出发到达返货轨迹，保存数据至FOSS开始;运单号："+waybillNo+"清单号:"+airWaybillNo);
				
				
				SynTrackingEntity trackingEntity =new SynTrackingEntity();
				LOGGER.error("=====OPP推送给WQS开始;运单号："+waybillNo+"清单号:"+airWaybillNo);
				trackingEntity.setEventType(WQSConstans.ARRIVAL_STATE);
				trackingEntity.setId(UUIDUtils.getUUID());
				/*//通过运单号查询到达城市名
				//通过一个运单号查询对应的运单信息
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
				String receiveCustomerCityCode=waybillEntity.getReceiveCustomerCityCode();
				//通过城市code查询城市名 
				String name = administrativeRegionsService.queryAdministrativeRegionsNameByCode(receiveCustomerCityCode);
				trackingEntity.setNextCity(name);  NamearrvRegionName   destinationCityName*/
				if (null != entity.getOperTime() ) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					trackingEntity.setOperateTime(format.parse(entity.getOperTime()));
					};
				trackingEntity.setWayBillNo(waybillNo);
				//trackingEntity.setTrackInfo(entity.getCurrentStatus());
				/*trackingEntity.setDestinationCityName(entity.getOperationOrgName());
				AirWaybillDetailEntity airEntity =new AirWaybillDetailEntity();
				trackingEntity.setDestinationCityName(airEntity.getArrvRegionName());*/
				
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
				String targetOrgCode=waybillEntity.getTargetOrgCode();
				trackingEntity.setDestinationCityName(waybillEntity.getTargetOrgCode());
				
				trackingEntity.setTrackInfo(waybillEntity.getTargetOrgCode()+"已到达");
				
				pushTrackForCaiNiaoService.addSynTrack(trackingEntity);	
				response.setBeSuccess(true);
				response.setReturnType("getOppLocusForBse");			
				response.setAirWaybillNo(airwaybillList);
				response.setWaybillNo(waybillNoList);
				LOGGER.error("=====FOSS接收OPP推送出发到达返货轨迹结束："+new Date());	
		}
			resp.setHeader("ESB-ResultCode", "1");
			return response;
		} catch (Exception e) {
			response.setBeSuccess(false);
			response.setReturnType("getOppLocusForBse");
			response.setFailureReason(e.getMessage());
			LOGGER.error("=====FOSS接收OPP推送运单号出现异常："+new Date());
			resp.setHeader("ESB-ResultCode", "0");
			return response;
		}
	}

	
		/**
		 * 
		* @description OPP系统更改 同步至FOSS--FOSS同步修改
		* @param requstStr
		* @return
		* @version 1.0
		* @author 269701-foss-lln
		* @update 2016年5月19日 下午4:38:13
		 */
	@Override
	public @ResponseBody OPPLocusResponse updateAirPickUpInFromOPP(@RequestBody RfcPushDataToFossRequst requstStr,HttpServletResponse resp) {
		LOGGER.error("=====FOSS接收OPP推送更改合大票相关数据表开始："+new Date());
		//返回给OPP数据
		OPPLocusResponse response=new OPPLocusResponse();
		//运单号
		String waybillNo=requstStr.getWaybillNo();
		//清单号
		String airWaybillNo=requstStr.getAirWaybillNo();
		//合大票清单主表id
		 String airPickId=requstStr.getAirPickId();
		//合大票清单明细表id
		 String airPickDetialId = requstStr.getAirPickDetialId();
		try{

			//校验清单主表id是否为空
			if(StringUtils.isEmpty(airPickId)){
				response.setBeSuccess(false);
				response.setReturnType("updateAirPickUpInFromOPP");
				response.setFailureReason("传入的清单主表id为空！");
			}
			
			//校验合大票清单明细表id是否为空
			if(StringUtils.isEmpty(airPickDetialId)){
				response.setBeSuccess(false);
				response.setReturnType("updateAirPickUpInFromOPP");
				response.setFailureReason("传入的清单号为空！");
			}
			//校验操作类型是否为空
			if(StringUtils.isEmpty(requstStr.getOperType())){
				response.setBeSuccess(false);
				response.setReturnType("updateAirPickUpInFromOPP");
				response.setFailureReason("操作类型为空！");
			}
			
			/***********判断更改的类型：件数更改：AMOUNT 费用更改：FEE*************/
			if(StringUtils.equals("AMOUNT", requstStr.getOperType())){
				LOGGER.error("=====FOSS接收OPP推送件数更改处理开始："+new Date());
				//流水list
				List<AirPickUpSerialEntity> serialList=requstStr.getSerialList();
				//合大票明细表数据
				AirPickUpDetialInfoEntity airpickDetialEntity=requstStr.getDetialInfo();
				//件数更改
				//判断件数更改类型：20 多货 30 少货
				if(StringUtils.equals("30", requstStr.getExceptionType())){
					LOGGER.error("=====FOSS接收OPP推送件数更改处理少货开始："+new Date());
					//判断少货类型：部分少货 整体少货
					//异常运单件数 :-1~ 运单整体丢失;0 ~部分少货; 1~ 运单整体增加;2运单部分丢失 
					if(-1==requstStr.getWaybillExcCount()){
						//运单整体少货
						//根据合大票清单明细id 以及运单号 删除合大票流水数据
						pushAirPickUpInfoDao.deleteAirPickSerial(waybillNo,airPickDetialId);
						//根据合大票清单明细id 以及运单号 删除合大票明细数据
						pushAirPickUpInfoDao.deleteAirPickDetial(waybillNo,airPickDetialId);

					}else if(0==requstStr.getWaybillExcCount()){
						//运单部分少货
						//根据运单号 删除 少货部分流水
						// 修改合大票清单流水数据
						Map<String, Object> paramSerial = new HashMap<String, Object>();
						for(int i=0;i<serialList.size();i++){
							paramSerial.put("waybillNo", serialList.get(i).getWayBillNo());
							paramSerial.put("serialNo", serialList.get(i).getSerialNo());
							pushAirPickUpInfoDao.deleteAirPickSerialPart(paramSerial);
						}
						// 修改合大票清单明细 sql参数
						Map<String, Object> paramDetial = new HashMap<String, Object>();
						paramDetial.put("airPickDetialId", airPickDetialId);
						// 合_件数
						paramDetial.put("goodQty", airpickDetialEntity.getGoodsQty());
						//根据运单号 清单明id 修改明细件数
						pushAirPickUpInfoDao.updateAirPickDetialData(paramDetial);
					}else{
						//不做处理
					}
					LOGGER.error("=====FOSS接收OPP推送件数更改处理少货结束："+new Date());
				}else if(StringUtils.equals("20", requstStr.getExceptionType())){
					LOGGER.error("=====FOSS接收OPP推送件数更改处理多货开始："+new Date());
					//1. 判断多货类型：运单整体多 部分多
					//异常运单件数 :-1~ 运单整体丢失;0 ~部分少货; 1~ 运单整体增加;2运单部分丢失 
					if(1==requstStr.getWaybillExcCount()){
						//运单整体多货
						//清单明细 新增一条数据
						pushAirPickUpInfoDao.addAirPickUpDetial(airpickDetialEntity);
						//清单流水新增数据
						for(AirPickUpSerialEntity entity:serialList){
							entity.setSerialID(UUIDUtils.getUUID());
							pushAirPickUpInfoDao.addAirPickUpSerial(entity);
						}
						
					}else if(2==requstStr.getWaybillExcCount()){
						//运单部分多货
						//根据明细id 修改明细合——件数
						// 修改合大票清单明细 sql参数
						Map<String, Object> paramDetial = new HashMap<String, Object>();
						paramDetial.put("airPickDetialId", airPickDetialId);
						// 合_件数
						paramDetial.put("goodQty", airpickDetialEntity.getGoodsQty());
						// 修改合大票清单明细表 件数 
						pushAirPickUpInfoDao.updateAirPickDetialData(paramDetial);
						//新增 流水数据
						//清单流水新增数据
						for(AirPickUpSerialEntity entity:serialList){
							entity.setSerialID(UUIDUtils.getUUID());
							pushAirPickUpInfoDao.addAirPickUpSerial(entity);
						}
					}else{
						//方便以后拓展 不处理
					}
					LOGGER.error("=====FOSS接收OPP推送件数更改处理多货结束："+new Date());
				}else{
					//方便以后拓展 不处理
				}
				// 修改合大票清单主表 sql参数
				AirPickUpInfoEntity airPickEntity=requstStr.getAirPickInfo();
				Map<String, Object> param = new HashMap<String, Object>();
				//清单id
				param.put("airPickId", airPickId);
				//清单中流水件数--传入的是差异值
				param.put("goodQty", airPickEntity.getGoodsQtyTotal());
				//清单总票数--传入的是差异值
				param.put("waybillExcCount", airPickEntity.getWaybillQtyTotal());
				//根据合大票清单主表id 清单号 修改合大票清单总票数 以及总件数
				pushAirPickUpInfoDao.updateAirPickData(param);
				
				LOGGER.error("=====FOSS接收OPP推送件数更改处理结束："+new Date());
				
				/************费用更改处理****************/
			}else if(StringUtils.equals("FEE", requstStr.getOperType())){
				LOGGER.error("=====FOSS接收OPP推送费用更改处理开始："+new Date());
				//费用更改
				Map<String,Object> params=new HashMap<String, Object>();
				params.put("airPickId", airPickId);
				params.put("airPickDetialId", airPickDetialId);
				params.put("exceptionFee", requstStr.getDeliverFee());
				//根据正单号获取原始合大票清单
				AirPickupbillEntity oldAirPickupbillEntity = airTransPickupBillService.queryAirPickupbillEntity(airWaybillNo);
				//新合大票清单信息
				AirPickupbillEntity newAirPickupbillEntity = new AirPickupbillEntity();
				//给新合大票清单赋值
				BeanUtils.copyProperties(oldAirPickupbillEntity, newAirPickupbillEntity);
				//送货费
				newAirPickupbillEntity.setDeliverFeeTotal(oldAirPickupbillEntity.getDeliverFeeTotal().add(requstStr.getDeliverFee()));
				//设置修改时间
				newAirPickupbillEntity.setModifyTime(new Date());
				//设置修改人
				newAirPickupbillEntity.setModifyUserCode(requstStr.getModifyUserCode());
				//设置修改人名称
				newAirPickupbillEntity.setModifyUserName(requstStr.getModifyUserName());
				
				//修改列表,用于调用结算接口
				List<AirPickupbillDetailEntity> stlModifyList = new ArrayList<AirPickupbillDetailEntity>();
				//修改合大票明细流水集合
				List<String> modifyWaybillNoList = new ArrayList<String>();
				//根据合大票清单ID 查询合大票清单明细信息
				List<AirPickupbillDetailEntity> oldAirPickUpDetialList=airQueryModifyPickupbillDao.queryAirPickupbillDetailListByPrimaryId(airPickId);
				for(AirPickupbillDetailEntity detialEntiy:oldAirPickUpDetialList){
					if(StringUtils.equals(detialEntiy.getId(),airPickDetialId)){
						//设置修改后的送货费
						detialEntiy.setDeliverFee(detialEntiy.getDeliverFee().add(requstStr.getDeliverFee()));
						stlModifyList.add(detialEntiy);
						//需要修改运单的集合 用于校验运单是否被签收
						modifyWaybillNoList.add(detialEntiy.getWaybillNo());
					}
				}
				//校验运单是否已签收
				if(modifyWaybillNoList.size() > 0){
					//查询已删除运单是否已签收
					LOGGER.info("OPP修改合大票 信息 调签收接开始时间："+new Date());

					List<String> modifyWaybillNoSignList = waybillSignResultService.queryWaybillSignResultWaybillNos(modifyWaybillNoList);
					
					LOGGER.info("OPP修改合大票 信息 调签收接口结束时间："+new Date());

					if(modifyWaybillNoSignList != null && modifyWaybillNoSignList.size() > 0){
						//拼接已经签收的运单号
						StringBuffer modifySignNos = new StringBuffer();
						for(int i = 0;i<modifyWaybillNoSignList.size();i++){
							modifySignNos.append(modifyWaybillNoSignList.get(i)).append(",");
						}
						throw new TfrBusinessException("修改合大票不能修改已签收的运单！" + modifySignNos.substring(0,modifySignNos.length()-1));
					}
				}
				
				//根据合大票明细id 修改合大票明细表 合_送货费
				LOGGER.error("=====FOSS接收OPP推送更改费，保存数据至FOSS明细表开始;运单号："+waybillNo+"清单号:"+airWaybillNo);
				pushAirPickUpInfoDao.updateAirPickDetialFeeData(params);
				LOGGER.error("=====FOSS接收OPP推送更改费，保存数据至FOSS明细表成功结束;运单号："+waybillNo+"清单号:"+airWaybillNo);

				//根据合大票主表id 修改合大票主表 总送货费
				LOGGER.error("=====FOSS接收OPP推送更改费，保存数据至FOSS合大票主表表开始;运单号："+waybillNo+"清单号:"+airWaybillNo);
				pushAirPickUpInfoDao.updateAirPickFeeData(params);
				LOGGER.error("=====FOSS接收OPP推送更改费，保存数据至FOSS合大票主表成功结束;运单号："+waybillNo+"清单号:"+airWaybillNo);
				LOGGER.error("=====FOSS接收OPP推送费用更改处理结束："+new Date());
				
				//用户信息
				UserEntity user=new UserEntity();
				user.setUserName(requstStr.getModifyUserName());
				EmployeeEntity emp=new EmployeeEntity();
				//修改人编码
				emp.setEmpCode(requstStr.getModifyUserCode());
				//修改人姓名
				emp.setEmpName(requstStr.getModifyUserName());
				user.setEmployee(emp);
				//修改人所在部门信息
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=new OrgAdministrativeInfoEntity();
				//修改部门编码
				orgAdministrativeInfoEntity.setCode(requstStr.getModifyDeptCode());
				//修改部门名称
				orgAdministrativeInfoEntity.setName(requstStr.getModifyDeptName());
				//修改变更日志
				List<AirRevisebillDetailEntity> airRevisebillDetailList = new ArrayList<AirRevisebillDetailEntity>();
				//OPP更改数据 同步修改FOSS数据 变更日志记载
				getOriginalAirPickupbillList(stlModifyList,oldAirPickUpDetialList,airRevisebillDetailList,new CurrentInfo(user,orgAdministrativeInfoEntity));
				//判断变更明细列表非空
				if(!CollectionUtils.isEmpty(airRevisebillDetailList)){
					airChangeInventoryService.addaddAirRevisebillDetailList(airRevisebillDetailList);
				}
				try {
					
					LOGGER.info("修改合大票清单调用结算接口开始时间："+new Date());

				airJointTicketService.modifyAirJointTicketDetail(oldAirPickupbillEntity,
							newAirPickupbillEntity,
							null,
							stlModifyList,
							null,
							new CurrentInfo(user,orgAdministrativeInfoEntity));
					
					LOGGER.info("修改合大票清单调用结算接口结束时间："+new Date());

				} catch (SettlementException e) {
					LOGGER.error("修改合票清单调用结算接口出现异常，异常信息：" + e.getErrorCode());
					throw new  TfrBusinessException("修改合票清单调用结算接口出现异常:"+e.getErrorCode());
				}
			}else{
				//方便以后拓展 不处理
			}
			LOGGER.error("=====FOSS接收OPP推送费用更改处理结束："+new Date());
			response.setBeSuccess(true);
			response.setReturnType("updateAirPickUpInFromOPP");
			resp.setHeader("ESB-ResultCode", "1");
			LOGGER.error("=====FOSS接收OPP推送更改合大票相关数据表结束："+new Date());
			return response;
		} catch (Exception e) {
			response.setBeSuccess(false);
			response.setReturnType("updateAirPickUpInFromOPP");
			resp.setHeader("ESB-ResultCode", "0");
			response.setFailureReason(e.getMessage());
			LOGGER.error("=====FOSS接收OPP推送更改合大票相关数据表出现异常："+new Date()+"异常信息"+e.getMessage());
			return response;
		}
	}


	/**
	 * 
	* @description OPP更改数据 同步修改FOSS数据 变更日志记载
	* @param airPickupbillDetailList
	* @param airRevisebillDetailList
	* @param SignWaybillNoMap
	* @return
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年6月12日 下午3:27:01
	 */
	private List<AirPickupbillDetailEntity> getOriginalAirPickupbillList(
			List<AirPickupbillDetailEntity> stlModifyList,
			List<AirPickupbillDetailEntity> oldAirPickUpDetialList,
			List<AirRevisebillDetailEntity> airRevisebillDetailList,
			CurrentInfo currentInfo){
		//调用结算    修改列表
		List<AirPickupbillDetailEntity> modifyAirTransPickupbillDetailList =  new ArrayList<AirPickupbillDetailEntity>();
		//遍历原始送货费列表
		for (AirPickupbillDetailEntity origDetailEntity : oldAirPickUpDetialList) {
			//遍历现有的合大票清单明细列表
			for (AirPickupbillDetailEntity newDetailEntity : stlModifyList) {
				//原运单号=现有清单列表中的运单号
				if(origDetailEntity.getWaybillNo().equals(newDetailEntity.getWaybillNo())){
					//变更内容
					StringBuffer reviseContent = new StringBuffer();
					if(origDetailEntity.getDeliverFee().compareTo(newDetailEntity.getDeliverFee()) != 0){
						
						//修改送货费
						reviseContent.append("送货费：" + newDetailEntity.getDeliverFee());
						reviseContent.append("；");
					}
					//修改计费重量
					if(newDetailEntity.getBillingWeight().compareTo(origDetailEntity.getBillingWeight()) != 0){
						reviseContent.append("计费重量：" + newDetailEntity.getBillingWeight());
						reviseContent.append("；");
					}
					//修改件数
					if(newDetailEntity.getGoodsQty().intValue() != origDetailEntity.getGoodsQty().intValue()){
						reviseContent.append("件数：" + newDetailEntity.getGoodsQty());
						reviseContent.append("；");
					}
					//修改收货人
					if(!StringUtils.equals(newDetailEntity.getReceiverName(), origDetailEntity.getReceiverName())){
						if(!newDetailEntity.getReceiverName().equals("") || origDetailEntity.getReceiverName() != null){
							reviseContent.append("收货人：" + newDetailEntity.getReceiverName());
							reviseContent.append("；");
						}
					}
					//修改收货人电话
					if(!StringUtils.equals(newDetailEntity.getReceiverContactPhone(), origDetailEntity.getReceiverContactPhone())){
						if(!newDetailEntity.getReceiverContactPhone().equals("") || origDetailEntity.getReceiverContactPhone() != null){
							reviseContent.append("收货人电话：" + newDetailEntity.getReceiverContactPhone());
							reviseContent.append("；");
						}
					}
					//修改收货人地址
					if(!StringUtils.equals(newDetailEntity.getReceiverAddress(), origDetailEntity.getReceiverAddress())){
						if(!newDetailEntity.getReceiverAddress().equals("") || origDetailEntity.getReceiverAddress() != null){
							reviseContent.append("收货人地址：" + newDetailEntity.getReceiverAddress());
							reviseContent.append("；");
						}
					}
					
					if(!StringUtils.equals(origDetailEntity.getNotes(),newDetailEntity.getNotes())){
						if(!newDetailEntity.getNotes() .equals("") || origDetailEntity.getNotes() != null){
							reviseContent.append("备注：" + newDetailEntity.getNotes());
							reviseContent.append("；");
						}
					}
					
					int length = reviseContent.length();
					if(length >= ConstantsNumberSonar.SONAR_NUMBER_1000){
						throw new AirChangeInventoryException("备注长度过长！");
					}
					if(length > 0){
						//变更明细
						AirRevisebillDetailEntity airRevisebillDetailEntity = new AirRevisebillDetailEntity();
						//设置合大票明细id
						airRevisebillDetailEntity.setAirPickupbillDetailId(newDetailEntity.getId());
						//中转提货明细id
						airRevisebillDetailEntity.setAirTransPickupbillDetailId(UUIDUtils.getUUID());
						//变更清单ID
						airRevisebillDetailEntity.setId(UUIDUtils.getUUID());
						//创建时间
						airRevisebillDetailEntity.setCreateTime(new Date());
						//操作时间
						airRevisebillDetailEntity.setOperationTime(new Date());
						//当前操作部门
						airRevisebillDetailEntity.setOperationOrgCode(currentInfo.getCurrentDeptCode());
						//当前操作部门名称
						airRevisebillDetailEntity.setOperationOrgName(currentInfo.getCurrentDeptName());
						//操作人姓名
						airRevisebillDetailEntity.setOperatorName(currentInfo.getEmpName());
						//操作人工号
						airRevisebillDetailEntity.setOperatorCode(currentInfo.getEmpCode());
						//记录
						airRevisebillDetailEntity.setReviseContent(reviseContent.toString());
						//添加单条变更清单至列表中
						airRevisebillDetailList.add(airRevisebillDetailEntity);
					}
				}
			}
		}
		return modifyAirTransPickupbillDetailList;
	}
	
	/**
	 * @param labeledGoodService : set the property labeledGoodService.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:58:09
	 * @version V1.0
	 */
	
	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}
	
	/**
	 * @param waybillManagerService : set the property waybillManagerService.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:25:09
	 * @version V1.0
	 */
	
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setAirQueryFlightArriveDao(
			IAirQueryFlightArriveDao airQueryFlightArriveDao) {
		this.airQueryFlightArriveDao = airQueryFlightArriveDao;
	}
	/**
	 * @param pushAirPickUpInfoDao : set the property pushAirPickUpInfoDao.
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午4:54:26
	 * @version V1.0
	 */
	/**
	 * @param pushAirPickUpInfoDao the pushAirPickUpInfoDao to set
	 */
	public void setPushAirPickUpInfoDao(IPushAirPickUpInfoDao pushAirPickUpInfoDao) {
		this.pushAirPickUpInfoDao = pushAirPickUpInfoDao;
	}
	/**
	 * @param airTransPickupBillService the airTransPickupBillService to set
	 */
	public void setAirTransPickupBillService(
			IAirTransPickupBillService airTransPickupBillService) {
		this.airTransPickupBillService = airTransPickupBillService;
	}
	/**
	 * @param airQueryModifyPickupbillDao the airQueryModifyPickupbillDao to set
	 */
	public void setAirQueryModifyPickupbillDao(
			IAirQueryModifyPickupbillDao airQueryModifyPickupbillDao) {
		this.airQueryModifyPickupbillDao = airQueryModifyPickupbillDao;
	}
	/**
	 * @param waybillSignResultService the waybillSignResultService to set
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	/**
	 * @param airJointTicketService the airJointTicketService to set
	 */
	public void setAirJointTicketService(
			IAirJointTicketService airJointTicketService) {
		this.airJointTicketService = airJointTicketService;
	}
	/**
	 * @param airChangeInventoryService the airChangeInventoryService to set
	 */
	public void setAirChangeInventoryService(
			IAirChangeInventoryService airChangeInventoryService) {
		this.airChangeInventoryService = airChangeInventoryService;
	}
	/**
	 * @param pushTrackForCaiNiaoService the pushTrackForCaiNiaoService to set
	 */
	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}
	
	/**
	 * @param administrativeRegionsService
	 */
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	
}
