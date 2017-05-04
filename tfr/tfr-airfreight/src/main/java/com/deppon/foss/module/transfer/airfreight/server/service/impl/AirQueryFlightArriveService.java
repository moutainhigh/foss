package com.deppon.foss.module.transfer.airfreight.server.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirQueryFlightArriveDao;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDetailDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirQueryFlightArriveService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirQueryFlightArriveDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirQueryFlightArriveEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirQueryFlightArriveSerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirQueryFlightArriveDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWayBillDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class AirQueryFlightArriveService implements IAirQueryFlightArriveService {
	
	
	private IAirQueryFlightArriveDao airQueryFlightArriveDao;
	
	private IAirDispatchUtilService airDispatchUtilService;
	
	private IAirWaybillService airWaybillService;
	/*运单*/
	private IWaybillDao waybillDao;
	/** 查询航空正单dao. */
	private IAirWaybillDetailDao pointsSingleJointTicketDao;
	
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public void setPointsSingleJointTicketDao(
			IAirWaybillDetailDao pointsSingleJointTicketDao) {
		this.pointsSingleJointTicketDao = pointsSingleJointTicketDao;
	}

	public void setAirWaybillService(IAirWaybillService airWaybillService) {
		this.airWaybillService = airWaybillService;
	}

	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}

	public void setAirQueryFlightArriveDao(
			IAirQueryFlightArriveDao airQueryFlightArriveDao) {
		this.airQueryFlightArriveDao = airQueryFlightArriveDao;
	}

	//主界面综合查询 :空运到达
	@Override
	public List<AirQueryFlightArriveDto> queryFlightArrive(
			AirQueryFlightArriveDto airQueryFlightArriveDto, int start,
			int limit) {
	
		List<AirQueryFlightArriveDto> airQFADtoList = airQueryFlightArriveDao.queryFlightArrive(airQueryFlightArriveDto, start, limit);
	    //根据正单号+运单号+空运到达类型 修改已到达的流水个数
		if(CollectionUtils.isNotEmpty(airQFADtoList)){
	    	for (AirQueryFlightArriveDto airQueryFlightArrive : airQFADtoList) {
	    		long num = airQueryFlightArriveDao.getSerialsCount(airQueryFlightArrive);
	    		int count = Integer.parseInt(String.valueOf(num));
	    		WaybillEntity waybill = new WaybillEntity();
				waybill = waybillDao.queryWaybillByNo(airQueryFlightArrive.getWaybillNo());
	    		if(waybill!=null){
	    			//sonal-if-else中内容相同-352203
//	    			int waybillNum = waybill.getGoodsQtyTotal();
/*		    		if(count>waybillNum){
		    			airQueryFlightArrive.setArriveGoodsQty(count);
		    		}else{
		    			airQueryFlightArrive.setArriveGoodsQty(count);
		    		}*/
	    			airQueryFlightArrive.setArriveGoodsQty(count);
	    		}
	    		List<String> note = airQueryFlightArriveDao.getSerialsNote(airQueryFlightArrive);
	    		if(CollectionUtils.isNotEmpty(note)){
	    			airQueryFlightArrive.setNote(note.get(0));
	    		}
	    		
	    		List<Date> arriveTimeList = airQueryFlightArriveDao.getSerialsArriveTime(airQueryFlightArrive);
	    		if(CollectionUtils.isNotEmpty(arriveTimeList)){
	    			airQueryFlightArrive.setArriveTime(arriveTimeList.get(0));
	    		}
	    		
			}
	    }
	    return airQFADtoList;
	}
	
	@Override
	public Long getCount(AirQueryFlightArriveDto airQueryFlightArriveDto) {
		// TODO Auto-generated method stub
		return airQueryFlightArriveDao.getCount(airQueryFlightArriveDto);
	}
	//2015-07-02  新增/修改   空运到达类型:货物到达代理处  根据正单号查询信息
	@Override
	public List<AirQueryFlightArriveDto> queryAirWaybillNo(
			AirQueryFlightArriveDto airQueryFlightArriveDto, int start,
			int limit) {
		return airQueryFlightArriveDao.queryAirWaybillNo(airQueryFlightArriveDto, start, limit);
	}

	@Override
	public Long getCountAirWaybillNo(
			AirQueryFlightArriveDto airQueryFlightArriveDto) {
		return airQueryFlightArriveDao.getCountAirWaybillNo(airQueryFlightArriveDto);
	}

	// 下拉流水
	@Override
	public List<AirQueryFlightArriveDto> queryAirWaybillSerialNo(
			AirQueryFlightArriveDto airQueryFlightArriveDto) {
		return airQueryFlightArriveDao.queryAirWaybillSerialNo(airQueryFlightArriveDto);
	}
    //新增/修改界面  空运到达类型:代理到机场提货  根据正单号查询信息 
	@Override
	public List<AirQueryFlightArriveDto> queryAirFlightArrive(
			AirQueryFlightArriveDto airQueryFlightArriveDto) {
		// TODO Auto-generated method stub
		return airQueryFlightArriveDao.queryAirFlightArrive(airQueryFlightArriveDto);
	}
    //  保存  空运到达类型:代理到机场提货 
	@Override
	public int addAirFlightArrivePickUp(
			AirQueryFlightArriveDto airQueryFlightArriveDto) {

		
		if(airQueryFlightArriveDto == null){
			throw new TfrBusinessException("参数不能为空");
		}
		   AirQueryFlightArriveDto  airQueryFlightArrive = new AirQueryFlightArriveDto();
		   airQueryFlightArrive.setAirWaybillNo(airQueryFlightArriveDto.getAirWaybillNo());
		   airQueryFlightArrive.setFlightArriveType(airQueryFlightArriveDto.getFlightArriveType());
		   // 根据正单号 和 空运到达类型 查询已经录入过了
		   AirQueryFlightArriveEntity airQueryFlightArriveEnty = new AirQueryFlightArriveEntity();
		   airQueryFlightArriveEnty = airQueryFlightArriveDao.queryFlightArriveByAirWaybillNo(airQueryFlightArrive);
		   
		   //一个正单号的信息 空运到达类型为:代理到机场提货时  只能新增一次 否则的话修改
		   if(airQueryFlightArriveEnty == null){
			   AirWayBillDto airWayBillDto = new AirWayBillDto();
				airWayBillDto.setAirWaybillNo(airQueryFlightArriveDto.getAirWaybillNo());
				AirWaybillEntity airWaybillEntity = new AirWaybillEntity();
				/*根据正单号查询航空正单*/
				airWaybillEntity = airWaybillService.queryResultEntity(airWayBillDto);
				
				AirQueryFlightArriveEntity airQueryFlightArriveEntity = new AirQueryFlightArriveEntity();
				airQueryFlightArriveEntity.setId(UUIDUtils.getUUID());
				/*正单号*/
				airQueryFlightArriveEntity.setAirWaybillNo(airQueryFlightArriveDto.getAirWaybillNo());
				/*空运到达类型*/
				airQueryFlightArriveEntity.setFlightArriveType(airQueryFlightArriveDto.getFlightArriveType());
				/*航空-二字码*/
				airQueryFlightArriveEntity.setAirLineCode(airWaybillEntity.getAirLineTwoletter());
				/*航班号*/
				airQueryFlightArriveEntity.setFlightNo(airWaybillEntity.getFlightNo());
				/* 到达件数 */
				airQueryFlightArriveEntity.setArriveGoodsQty(airWaybillEntity.getGoodsQty());
				/*到达重量*/
				airQueryFlightArriveEntity.setArriveGoodsWeight(airWaybillEntity.getGrossWeight());
				/*代理公司编码   查询空运到达的就涉及代理公司 >>>取航空正单的到达网点*/
				airQueryFlightArriveEntity.setAgentCompanyCode(airWaybillEntity.getDestOrgCode());
				/*代理公司名称   取航空正单的到达网点*/
				airQueryFlightArriveEntity.setAgentCompanyName(airWaybillEntity.getDedtOrgName());
				//获取当前空运总调
				OrgAdministrativeInfoEntity org=airDispatchUtilService.queryAirDispatchDept(FossUserContext.getCurrentDeptCode());
				airQueryFlightArriveEntity.setOrgCode(org.getCode());
				airQueryFlightArriveEntity.setOrgName(org.getName());
				//获取当前操作人信息
				UserEntity userEntity=FossUserContext.getCurrentUser();
				//操作人工号
				airQueryFlightArriveEntity.setOperateUserCode(userEntity.getEmployee().getEmpCode());
				//操作人姓名
				airQueryFlightArriveEntity.setOperateUserName(userEntity.getEmployee().getEmpName());
				//备注
				airQueryFlightArriveEntity.setNote(airQueryFlightArriveDto.getNote());
				airQueryFlightArriveEntity.setActive("Y");
				//创建日期 = 到达时间
				airQueryFlightArriveEntity.setCreateDate(airQueryFlightArriveDto.getArriveTime());
				airQueryFlightArriveEntity.setCreateUser(userEntity.getEmployee().getEmpName());
				
				/**
			     * 新增空运到达
			     * @param airQueryFlightArriveEntity
			     */
				airQueryFlightArriveDao.addAirQueryFlightArrive(airQueryFlightArriveEntity);
			
				    
				List<AirWaybillDetailEntity> airWaybillDetailEntityList = new ArrayList<AirWaybillDetailEntity>();
				if(airWaybillEntity != null){
					
					/*根据航空正单id查询对应的航空正单明细list*/
					airWaybillDetailEntityList = pointsSingleJointTicketDao.queryHistoryAirWaybillDetail(airWaybillEntity.getId());
					/*循环遍历明细*/
					if(CollectionUtils.isNotEmpty(airWaybillDetailEntityList)){
						
						for (AirWaybillDetailEntity airWaybillDetailEntity : airWaybillDetailEntityList) {
							
							AirQueryFlightArriveDetailEntity airQueryFlightArriveDetailEntity = new AirQueryFlightArriveDetailEntity();
							airQueryFlightArriveDetailEntity.setId(UUIDUtils.getUUID());
							airQueryFlightArriveDetailEntity.setAirFlightArriveId(airQueryFlightArriveEntity.getId());
							/*创建时间*/
							airQueryFlightArriveDetailEntity.setCreateDate(airQueryFlightArriveEntity.getCreateDate());
							/*运单号*/
							airQueryFlightArriveDetailEntity.setWaybillNo(airWaybillDetailEntity.getWaybillNo());
							/*开单件数*/
							airQueryFlightArriveDetailEntity.setWaybillGoodsQty(airWaybillDetailEntity.getGoodsQty());
							/*开单重量*/
							airQueryFlightArriveDetailEntity.setWaybillWeight(airWaybillDetailEntity.getGrossWeight());
							/**
							 * 新增空运到达明细
							 * @param airQueryFlightArriveDetailEntity
							 */
							airQueryFlightArriveDao.addAirQueryFlightArriveDetail(airQueryFlightArriveDetailEntity);
							
							String airWaybillDetailIds = airWaybillDetailEntity.getId();
							
							managerSerialNo(airQueryFlightArriveEntity, airQueryFlightArriveDetailEntity, airWaybillDetailIds);
							
						} 
					}
					
				}
		
		   }else{
			   //修改    空运到达类型为:代理到机场提货+正单号 已经录入过了
			   modifyAirFlightArrivePickUp(airQueryFlightArriveDto);
			   return 0;
		   }
			
		return 1;
	}

	/**
	 * sonar wwb 311396 2016年12月24日10:03:27 
	 * @param airQueryFlightArriveEntity
	 * @param airQueryFlightArriveDetailEntity
	 * @param airWaybillDetailIds
	 */
	private void managerSerialNo(AirQueryFlightArriveEntity airQueryFlightArriveEntity, AirQueryFlightArriveDetailEntity airQueryFlightArriveDetailEntity,
			String airWaybillDetailIds) {
		if(StringUtils.isNotEmpty(airWaybillDetailIds)){
			List<AirWaybillSerialNoEntity> airWaybillSerialNoEntityList = new ArrayList<AirWaybillSerialNoEntity>();
			/*根据航空正单明细id查询所有流水号*/
			airWaybillSerialNoEntityList =	pointsSingleJointTicketDao.queryAirWaybillSerialNoList(airWaybillDetailIds);
			if(CollectionUtils.isNotEmpty(airWaybillSerialNoEntityList)){
				
				/*循环遍历流水明细*/
				for (AirWaybillSerialNoEntity airWaybillSerialNoEntity : airWaybillSerialNoEntityList) {
					
					AirQueryFlightArriveSerialEntity airQueryFlightArriveSerialEntity = new AirQueryFlightArriveSerialEntity();
					
					airQueryFlightArriveSerialEntity.setId(UUIDUtils.getUUID());
					
					airQueryFlightArriveSerialEntity.setAirFlightArriveDetailId(airQueryFlightArriveDetailEntity.getId());
					/*到达时间*/
					airQueryFlightArriveSerialEntity.setArriveTime(airQueryFlightArriveEntity.getCreateDate());
					/*创建时间*/
					airQueryFlightArriveSerialEntity.setCreateDate(airQueryFlightArriveEntity.getCreateDate());
					/*创建人*/
					airQueryFlightArriveSerialEntity.setCreateUser(airQueryFlightArriveEntity.getOperateUserName());
					/*流水号*/
					airQueryFlightArriveSerialEntity.setSerialNo(airWaybillSerialNoEntity.getSerialNo());
					/*备注*/
					airQueryFlightArriveSerialEntity.setNote(airQueryFlightArriveEntity.getNote());
					/**
				     * 新增空运到达流水
				     * @param airQueryFlightArriveSerialEntity
				     */
					airQueryFlightArriveDao.addAirQueryFlightArriveSerial(airQueryFlightArriveSerialEntity);
				}
			}
		}
	}
	
	/**
	 * 保存           货物到达代理处 
	 * @param airQueryFlightArriveDtos
	 * @return
	 */
	@Override
	public int addAirFlightArriveAgency(
			List<AirQueryFlightArriveDto> airQueryFlightArriveDtos,AirQueryFlightArriveDto airQueryFlightArriveDto){

		Date arriveTime = airQueryFlightArriveDto.getArriveTime();
		String flightArriveType = airQueryFlightArriveDto.getFlightArriveType();
		String note = airQueryFlightArriveDto.getNote();
		/**
		 * 1 插入主表 插入1次 2 插入明细表 几个运单号插入几次 3 插入流水表 插入的循环遍历次数
		 */
		// 1 插入主表 插入1次
		AirQueryFlightArriveDto airQueryFlightArriveDt = airQueryFlightArriveDtos
				.get(0);
		// 设置查询条件

		// 2015-08-12 根据正单号和空运到达类型为货物到达代理处来查询正单号是否已录入过
		// 999
		// 空运到达主表
		airQueryFlightArriveDto.setAirWaybillNo(airQueryFlightArriveDt
				.getAirWaybillNo());

		AirQueryFlightArriveEntity airFlightArriveEntity = new AirQueryFlightArriveEntity();
		// 通过正单号+空运到达类型:货物到达代理处  去查询空运到达表 airWaybillNo + flightArriveType
		airFlightArriveEntity = airQueryFlightArriveDao
				.queryFlightArriveByAirWaybillNo(airQueryFlightArriveDto);
		if (airFlightArriveEntity == null) {
			// 1 正单号第一次录入数据
			AirWayBillDto airWayBillDto = new AirWayBillDto();
			airWayBillDto.setAirWaybillNo(airQueryFlightArriveDt
					.getAirWaybillNo());

			AirWaybillEntity airWaybillEntity = new AirWaybillEntity();
			/* 根据正单号查询 航空正单 */
			airWaybillEntity = airWaybillService
					.queryResultEntity(airWayBillDto);
            //定义空运到达主表
			AirQueryFlightArriveEntity airQueryFlightArriveEntity = new AirQueryFlightArriveEntity();
			airQueryFlightArriveEntity.setId(UUIDUtils.getUUID());
			/* 正单号 */
			airQueryFlightArriveEntity.setAirWaybillNo(airQueryFlightArriveDt
					.getAirWaybillNo());
			/* 空运到达类型 */
			airQueryFlightArriveEntity.setFlightArriveType(flightArriveType);
			/* 航空-二字码 */
			airQueryFlightArriveEntity.setAirLineCode(airWaybillEntity
					.getAirLineTwoletter());
			/* 航班号 */
			airQueryFlightArriveEntity.setFlightNo(airWaybillEntity
					.getFlightNo());
			/* 到达件数取的是: 流水的个数 */
			airQueryFlightArriveEntity
					.setArriveGoodsQty(airQueryFlightArriveDtos.size());
			/* 到达重量 设为0   不予显示:运单的重量和件数之间的重量不好计算*/
			airQueryFlightArriveEntity.setArriveGoodsWeight(BigDecimal.ZERO);
			/* 代理公司编码 */
			airQueryFlightArriveEntity.setAgentCompanyCode(airWaybillEntity
					.getAgenctCode());
			/* 代理公司名称 */
			airQueryFlightArriveEntity.setAgentCompanyName(airWaybillEntity
					.getAgencyName());
			// 获取当前空运总调
			OrgAdministrativeInfoEntity org = airDispatchUtilService
					.queryAirDispatchDept(FossUserContext.getCurrentDeptCode());
			airQueryFlightArriveEntity.setOrgCode(org.getCode());
			airQueryFlightArriveEntity.setOrgName(org.getName());
			// 获取当前操作人信息
			UserEntity userEntity = FossUserContext.getCurrentUser();
			// 操作人工号
			airQueryFlightArriveEntity.setOperateUserCode(userEntity
					.getEmployee().getEmpCode());
			// 操作人姓名
			airQueryFlightArriveEntity.setOperateUserName(userEntity
					.getEmployee().getEmpName());
			// 备注
			airQueryFlightArriveEntity.setNote(note);
			airQueryFlightArriveEntity.setActive(FossConstants.YES);
			// 创建日期 = 到达时间
			airQueryFlightArriveEntity.setCreateDate(arriveTime);
			// 创建人
			airQueryFlightArriveEntity.setCreateUser(userEntity.getEmployee()
					.getEmpName());
			/**
			 * 新增空运到达
			 * 
			 * @param airQueryFlightArriveEntity
			 */
			airQueryFlightArriveDao
					.addAirQueryFlightArrive(airQueryFlightArriveEntity);

			
			
			
			String airFlightArriveId = airQueryFlightArriveEntity.getId();

			// 运单号去重 : waybillNoSet
			Set<String> waybillNoSet = new HashSet<String>();
			for (AirQueryFlightArriveDto airQueryFlightArrive : airQueryFlightArriveDtos) {
				waybillNoSet.add(airQueryFlightArrive.getWaybillNo());
			}
			// 存放 运单号 空运到达明细ID
			Map<String, String> map = new HashMap<String, String>();
            // 2 插入明细表 几个运单号插入几次 :
			for (String waybillNo : waybillNoSet) {
            //定义空运到达明细表
				AirQueryFlightArriveDetailEntity airQueryFlightArriveDetailEntity = new AirQueryFlightArriveDetailEntity();
				airQueryFlightArriveDetailEntity.setId(UUIDUtils.getUUID());
				airQueryFlightArriveDetailEntity
						.setAirFlightArriveId(airFlightArriveId);
				/* 创建时间 */
				airQueryFlightArriveDetailEntity.setCreateDate(arriveTime);
				/* 运单号 */
				airQueryFlightArriveDetailEntity.setWaybillNo(waybillNo);
				/**
				 * 通过运单编号查询运单
				 * 
				 * @param waybill
				 */
				// WaybillEntity queryWaybillByNo(String waybillNo);

				WaybillEntity waybill = new WaybillEntity();
				waybill = waybillDao.queryWaybillByNo(waybillNo);
				if (waybill != null) {
					/* 开单件数 - 保存的是已经录入的流水件数 没有实现  暂取运单开单件数 */
					airQueryFlightArriveDetailEntity
							.setWaybillGoodsQty(waybill.getGoodsQtyTotal());
					/* 开单重量 - 运单表 goodsWeightTotal */
					airQueryFlightArriveDetailEntity.setWaybillWeight(waybill
							.getGoodsWeightTotal());
				}
				/**
				 * 新增空运到达明细
				 * 
				 * @param airQueryFlightArriveDetailEntity
				 */
				airQueryFlightArriveDao
						.addAirQueryFlightArriveDetail(airQueryFlightArriveDetailEntity);
				String airFlightArriveDetailId = airQueryFlightArriveDetailEntity
						.getId();
				map.put(waybillNo, airFlightArriveDetailId);

			}
			// 3 插入流水
			for (AirQueryFlightArriveDto airQueryFlightArriveTT : airQueryFlightArriveDtos) {
                // 定义空运到达流水表
				AirQueryFlightArriveSerialEntity airQueryFlightArriveSerialEntity = new AirQueryFlightArriveSerialEntity();
				airQueryFlightArriveSerialEntity.setId(UUIDUtils.getUUID());
				/* 流水 */
				airQueryFlightArriveSerialEntity
						.setSerialNo(airQueryFlightArriveTT.getSerialNo());
				/* 备注 */
				airQueryFlightArriveSerialEntity.setNote(note);
				/* 到达时间 */
				airQueryFlightArriveSerialEntity.setArriveTime(arriveTime);
				// 获取当前操作人信息
				airQueryFlightArriveSerialEntity.setCreateUser(userEntity
						.getEmployee().getEmpName());

				/*
				 * 两种方法都可以 Iterator<Map.Entry<String, String>> iterator =
				 * map.entrySet().iterator(); while (iterator.hasNext()) {
				 * Map.Entry<String, String> entry = iterator.next();
				 * if(airQueryFlightArriveTT.getWaybillNo() == entry.getKey()){
				 * airQueryFlightArriveSerialEntity
				 * .setAirFlightArriveDetailId(entry.getValue()); break; } }
				 */
				for (Map.Entry<String, String> entry : map.entrySet()) {
					if (StringUtils.equals(
							airQueryFlightArriveTT.getWaybillNo(),
							entry.getKey())) {
						airQueryFlightArriveSerialEntity
								.setAirFlightArriveDetailId(entry.getValue());
						break;
					}
				}
				
				
				List<AirQueryFlightArriveSerialEntity> airSerialList = 
						new ArrayList<AirQueryFlightArriveSerialEntity>();
				/**
				 * 通过空运到达明细表ID+流水号去查询空运到达流水表
				 */
				AirQueryFlightArriveDto airDto = new AirQueryFlightArriveDto();
				airDto.setAirFlightArriveDetailId(airQueryFlightArriveSerialEntity.getAirFlightArriveDetailId());
				airDto.setSerialNo(airQueryFlightArriveSerialEntity.getSerialNo());
				airSerialList = airQueryFlightArriveDao.queryFlightArriveSerial(airDto);
				if(airSerialList.size() == 0){
					airQueryFlightArriveDao
					.addAirQueryFlightArriveSerial(airQueryFlightArriveSerialEntity);
				}
				
			}
			
			

		} else {
			
			//end of airFlightArriveEntity == null
			// 正单号再次 修改/新增
			// 主表修改 到达件数 备注 到达时间
			airFlightArriveEntity.setNote(note);
			airFlightArriveEntity.setArriveGoodsQty(airFlightArriveEntity
					.getArriveGoodsQty() + airQueryFlightArriveDtos.size());
			airFlightArriveEntity.setFlightArriveType(flightArriveType);
			airFlightArriveEntity.setCreateDate(arriveTime);
			airFlightArriveEntity.setAirWaybillNo(airQueryFlightArriveDt.getAirWaybillNo());
			
			airQueryFlightArriveDao
					.modifyAirFlightArrivePickUp(airFlightArriveEntity);
			
			//判断当前运单号是否已经保存到空运到达明细表
			
			
			// airFlightArriveEntity
			// 获取当前操作人信息
			UserEntity userEntity = FossUserContext.getCurrentUser();
			// 2 插入/修改 明细表 :运单号去重 : waybillNoSet
			Set<String> waybillNoSet = new HashSet<String>();
			for (AirQueryFlightArriveDto airQueryFlightArrive : airQueryFlightArriveDtos) {
				waybillNoSet.add(airQueryFlightArrive.getWaybillNo());
			}
			Map<String, String> map = new HashMap<String, String>();
			
			//获得之前所有的waybillNo+airFlightArriveDetailId 
			/**通过空运到达主表Id去查询空运到达明细表*/
			
			AirQueryFlightArriveDto airQFADto = new AirQueryFlightArriveDto();
			airQFADto.setAirFlightArriveId(airFlightArriveEntity.getId());
			//根据空运到达主表Id去查询空运到达明细表
			List<AirQueryFlightArriveDetailEntity> airQFADList = airQueryFlightArriveDao
					.queryFlightArriveDetail(airQFADto);
			if(CollectionUtils.isNotEmpty(airQFADList)){
				for (AirQueryFlightArriveDetailEntity airQFADEntity : airQFADList) {
					//保存之前所有已保存的waybillNo+airFlightArriveDetailId
					map.put(airQFADEntity.getWaybillNo(), airQFADEntity.getId());
				}
			}
			
			
			for (String waybillNo : waybillNoSet) {
				airQueryFlightArriveDto
						.setAirFlightArriveId(airFlightArriveEntity.getId());
				airQueryFlightArriveDto.setWaybillNo(waybillNo);
				// 判断现在的运单号是否录入过 空运到达表ID+运单号 (前面查找空运到达主表已经添加了空运到达类型为货物到达代理处)
				// 查询结果只有一个
				List<AirQueryFlightArriveDetailEntity> airQueryFlightArriveDetailEntityList = airQueryFlightArriveDao
						.queryFlightArriveDetail(airQueryFlightArriveDto);
				// 存放 运单号 空运到达明细ID
			
				if (CollectionUtils
						.isEmpty(airQueryFlightArriveDetailEntityList)) {
                    // 运单信息不存在,说明之前还没有添加过
					AirQueryFlightArriveDetailEntity airQueryFlightArriveDetailEntity = new AirQueryFlightArriveDetailEntity();

					airQueryFlightArriveDetailEntity.setId(UUIDUtils.getUUID());
					airQueryFlightArriveDetailEntity
							.setAirFlightArriveId(airFlightArriveEntity.getId());
					/* 创建时间 */
					airQueryFlightArriveDetailEntity.setCreateDate(arriveTime);
					/* 运单号 */
					airQueryFlightArriveDetailEntity.setWaybillNo(waybillNo);
					/** 通过运单编号查询运单 */
					WaybillEntity waybill = new WaybillEntity();
					waybill = waybillDao.queryWaybillByNo(waybillNo);
					if (waybill != null) {
						/* 开单件数 - 保存的是已经录入的流水件数 */
						airQueryFlightArriveDetailEntity
								.setWaybillGoodsQty(waybill.getGoodsQtyTotal());
						/* 开单重量 - 运单表 goodsWeightTotal */
						airQueryFlightArriveDetailEntity
								.setWaybillWeight(waybill.getGoodsWeightTotal());
					}
					/**
					 * 新增空运到达明细
					 * 
					 * @param airQueryFlightArriveDetailEntity
					 */
					airQueryFlightArriveDao
							.addAirQueryFlightArriveDetail(airQueryFlightArriveDetailEntity);

					String airFlightArriveDetailId = airQueryFlightArriveDetailEntity
							.getId();

					map.put(waybillNo, airFlightArriveDetailId);

				} else {
					// 运单明细信息存在 CollectionUtils.isEmpty(airQueryFlightArriveDetailEntityList)
					if (airQueryFlightArriveDetailEntityList.size() == 1) {
						AirQueryFlightArriveDetailEntity airQueryFlightArriveDetailEntity = new AirQueryFlightArriveDetailEntity();
						// 到达件数累加 *********没有实现
						// airQueryFlightArriveDetailEntity.setWaybillGoodsQty(airQueryFlightArriveDetailEntityList.get(0).getWaybillGoodsQty());
						airQueryFlightArriveDetailEntity
								.setModifyDate(arriveTime);
						airQueryFlightArriveDetailEntity.setCreateDate(arriveTime);
						airQueryFlightArriveDetailEntity
								.setAirFlightArriveId(airFlightArriveEntity
										.getId());
						// 根据空运到达ID修改明细表
						airQueryFlightArriveDao
								.modifyAirFlightArriveDetailPickUp(airQueryFlightArriveDetailEntity);
					}
				}
			}
				
				// 插完运单信息之后,插入流水
				for (AirQueryFlightArriveDto airQueryFlightArriveTT : airQueryFlightArriveDtos) {
                    //定义空运到达流水
					AirQueryFlightArriveSerialEntity airQueryFlightArriveSerialEntity = new AirQueryFlightArriveSerialEntity();
					airQueryFlightArriveSerialEntity.setId(UUIDUtils.getUUID());
					/* 流水 */
					airQueryFlightArriveSerialEntity
							.setSerialNo(airQueryFlightArriveTT.getSerialNo());
					/* 备注 */
					airQueryFlightArriveSerialEntity.setNote(note);
					/* 到达时间 */
					airQueryFlightArriveSerialEntity.setArriveTime(arriveTime);
					// 获取当前操作人信息
					airQueryFlightArriveSerialEntity.setCreateUser(userEntity
							.getEmployee().getEmpName());

					for (Map.Entry<String, String> entry : map.entrySet()) {
						if (StringUtils.equals(
								airQueryFlightArriveTT.getWaybillNo(),
								entry.getKey())) {
							airQueryFlightArriveSerialEntity
									.setAirFlightArriveDetailId(entry
											.getValue());
							break;
						}
					}
					//AIR_FLIGHT_ARRIVE_DETAIL_ID+流水号
					List<AirQueryFlightArriveSerialEntity> airSerialList = 
							new ArrayList<AirQueryFlightArriveSerialEntity>();
					/**
					 * 通过空运到达明细表ID+流水号去查询空运到达流水表
					 */
					AirQueryFlightArriveDto airDto = new AirQueryFlightArriveDto();
					airDto.setAirFlightArriveDetailId(airQueryFlightArriveSerialEntity.getAirFlightArriveDetailId());
					airDto.setSerialNo(airQueryFlightArriveSerialEntity.getSerialNo());
					airSerialList = airQueryFlightArriveDao.queryFlightArriveSerial(airDto);
					if(airSerialList.size() == 0){
						airQueryFlightArriveDao
						.addAirQueryFlightArriveSerial(airQueryFlightArriveSerialEntity);
					}
				}//end of // 插完运单信息之后,插入流水
				

			}
		
		return 0;
	}
	
	
	
	/**
	 * 根据正单号查找  空运到达主表 , 用于修改 空运到达类型 为 代理到机场提货的信息
	 *
	 */
	@Override
	public AirQueryFlightArriveDto loadAirFlightArriveInfo(
			AirQueryFlightArriveDto airQueryFlightArriveDto) {
		return airQueryFlightArriveDao.loadAirFlightArriveInfo(airQueryFlightArriveDto);
	}

	 /**
     * 修改    空运到达类型:代理到机场提货
     * airQueryFlightArriveDto:正单号 + 到达时间  + 备注
     */
	@Override
	public int  modifyAirFlightArrivePickUp(
			AirQueryFlightArriveDto airQueryFlightArriveDto) {

		Date arriveTime = airQueryFlightArriveDto.getArriveTime();
		String note = airQueryFlightArriveDto.getNote();
		
		
		//1   空运到达主表 create_time + note + motify_time存放之前的create_time
		AirQueryFlightArriveEntity airQueryFlightArriveEntity = new AirQueryFlightArriveEntity();
		// 通过正单号去查询空运到达表
		airQueryFlightArriveEntity = airQueryFlightArriveDao.queryFlightArriveByAirWaybillNo(airQueryFlightArriveDto);
		
		if(airQueryFlightArriveEntity != null){
			
			airQueryFlightArriveEntity.setModifyDate(airQueryFlightArriveEntity.getCreateDate());
			airQueryFlightArriveEntity.setModifyUser(airQueryFlightArriveEntity.getCreateUser());
			airQueryFlightArriveEntity.setCreateDate(arriveTime);
			airQueryFlightArriveEntity.setNote(note);
			//修改主表
			airQueryFlightArriveDao.modifyAirFlightArrivePickUp(airQueryFlightArriveEntity);
			
			
			
			/*获得主表ID*/
			String airFlightArriveId = airQueryFlightArriveEntity.getId();
			airQueryFlightArriveDto.setAirFlightArriveId(airFlightArriveId);
			//2  明细表 create_time + motify_time存放之前的create_time
			List<AirQueryFlightArriveDetailEntity> airQueryFlightArriveDetailList 
			= new ArrayList<AirQueryFlightArriveDetailEntity>();
			//通过空运到达主表Id去查询空运到达明细表
			airQueryFlightArriveDetailList = airQueryFlightArriveDao.queryFlightArriveDetail(airQueryFlightArriveDto);
			
			if(CollectionUtils.isNotEmpty(airQueryFlightArriveDetailList)){
				
				for (AirQueryFlightArriveDetailEntity airQueryFlightArriveDetailEntity : airQueryFlightArriveDetailList) {
					
					/**
					 * 修改明细表 空运到达类型:代理到机场提货
	                 * 通过主表ID
					 */
					airQueryFlightArriveDetailEntity.setModifyDate(airQueryFlightArriveDetailEntity.getCreateDate());
					airQueryFlightArriveDetailEntity.setCreateDate(arriveTime);
					airQueryFlightArriveDao.modifyAirFlightArriveDetailPickUp(airQueryFlightArriveDetailEntity);
					
					
					/* 获得明细ID*/
					String airFlightArriveDetailId = airQueryFlightArriveDetailEntity.getId();
					airQueryFlightArriveDto.setAirFlightArriveDetailId(airFlightArriveDetailId);
					List<AirQueryFlightArriveSerialEntity> airQueryFlightArriveSerialList = 
							new ArrayList<AirQueryFlightArriveSerialEntity>();
					/**
					 * 通过空运到达明细表ID去查询空运到达流水表
					 */
					airQueryFlightArriveSerialList = airQueryFlightArriveDao.queryFlightArriveSerial(airQueryFlightArriveDto);
					
					if(CollectionUtils.isNotEmpty(airQueryFlightArriveSerialList)){
						//3 流水表 arrive_time+note + motify_time存放 修改的时间

						for (AirQueryFlightArriveSerialEntity airQueryFlightArriveSerialEntity : airQueryFlightArriveSerialList) {
							/**
							 * 修改流水表 空运到达类型:代理到机场提货
							 * 通过明细ID
							 */
							airQueryFlightArriveSerialEntity.setArriveTime(arriveTime);
							airQueryFlightArriveSerialEntity.setNote(note);
							airQueryFlightArriveSerialEntity.setModifyDate(arriveTime);
							airQueryFlightArriveDao.modifyAirFlightArriveSerialPickUp(airQueryFlightArriveSerialEntity);
						}
					}
					
				}
			}
			
			
		}
		return 0;
	}
    /**
     * 通过正单号去查询空运到达表
     */
	@Override
	public AirQueryFlightArriveEntity queryFlightArriveByAirWaybillNo(
			AirQueryFlightArriveDto airQueryFlightArriveDto) {
		return airQueryFlightArriveDao.queryFlightArriveByAirWaybillNo(airQueryFlightArriveDto);
	}
    /**
     * 通过空运到达主表Id去查询空运到达明细表
     */
	@Override
	public List<AirQueryFlightArriveDetailEntity> queryFlightArriveDetail(
			AirQueryFlightArriveDto airQueryFlightArriveDto) {
		return airQueryFlightArriveDao.queryFlightArriveDetail(airQueryFlightArriveDto);
	}
    /**
     * 通过空运到达明细表ID去查询空运到达流水表
     */
	@Override
	public List<AirQueryFlightArriveSerialEntity> queryFlightArriveSerial(
			AirQueryFlightArriveDto airQueryFlightArriveDto) {
		return airQueryFlightArriveDao.queryFlightArriveSerial(airQueryFlightArriveDto);
	}
	/**
	 * 修改主表 空运到达类型:代理到机场提货
	 */
	@Override
	public int modifyAirFlightArrivePickUp(
			AirQueryFlightArriveEntity airQueryFlightArriveEntity) {
		return airQueryFlightArriveDao.modifyAirFlightArrivePickUp(airQueryFlightArriveEntity);
	}

	/**
	 * 修改明细表 空运到达类型:代理到机场提货
	 */
	@Override
	public int modifyAirFlightArriveDetailPickUp(
			AirQueryFlightArriveDetailEntity airQueryFlightArriveDetailEntity) {
		return airQueryFlightArriveDao.modifyAirFlightArriveDetailPickUp(airQueryFlightArriveDetailEntity);
	}

	/**
	 * 修改流水表 空运到达类型:代理到机场提货
	 */
	@Override
	public int modifyAirFlightArriveSerialPickUp(
			AirQueryFlightArriveSerialEntity airQueryFlightArriveSerialEntity) {
		return airQueryFlightArriveDao.modifyAirFlightArriveSerialPickUp(airQueryFlightArriveSerialEntity);
	}

	@Override
	public List<AirQueryFlightArriveDto> queryAirWaybillSerialNoSelected(
			AirQueryFlightArriveDto airQueryFlightArriveDto) {
		return airQueryFlightArriveDao.queryAirWaybillSerialNoSelected(airQueryFlightArriveDto);
	}	
	
	
}