package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.settlement.common.api.server.dao.ITruckArriveConfirmDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.ITruckArriveConfirmService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.TruckArriveConfirmEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.TruckArriveConfirmDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

public class TruckArriveConfirmService implements ITruckArriveConfirmService {
	/**
	 * 应付单服务
	 */
	IBillPayableService billPayableService;
	/**
	 * 车辆确认Dao
	 */
	ITruckArriveConfirmDao truckArriveConfirmDao;

	@Override
	public void truckConfirm(TruckArriveConfirmDto dto) {
		//校验传递参数
		if(!SettlementDictionaryConstants.TRUCK_ARRIVE_CONFIRM.equals(dto.getConfirmType())
				&&!SettlementDictionaryConstants.TRUCK_ARRIVE_UNCONFIRM.equals(dto.getConfirmType())){
			throw new SettlementException("车辆到达标记不正确："+dto.getConfirmType());
		}else if(StringUtils.isEmpty(dto.getHandleNo())){
			throw new SettlementException("配载单号为空");
		}
		//到达确认实体
		TruckArriveConfirmEntity entity = new TruckArriveConfirmEntity();
		buildTruckConfirmEntity(entity,dto);
		//如果首款、尾款ID 都为空则不需要记录
		if(StringUtils.isEmpty(entity.getPayablefirstId())
				&&StringUtils.isEmpty(entity.getPayablelastId())){
			return ;
		}
		//插入实体
		truckArriveConfirmDao.insertSelective(entity);

	}
	/**
	 * 构建插入实体
	 * @param entity
	 */
	private void buildTruckConfirmEntity(TruckArriveConfirmEntity entity,TruckArriveConfirmDto dto){
		entity.setId(UUID.randomUUID().toString());
		
		//应付单信息
		BillPayableConditionDto conditionDto = new BillPayableConditionDto();
		conditionDto.setActive(FossConstants.YES);
		conditionDto.setSourceBillNo(dto.getHandleNo());
		//查询整车外请车、首/尾款
		String[] billTypes = new String[]{
		 SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST,
		 SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST,
		 SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST,
		 SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
		}; 
		conditionDto.setBillTypes(billTypes);
		List<BillPayableEntity> list = billPayableService.queryBillPayableByCondition(conditionDto);
		//设置整车首款、尾款的id
		if(CollectionUtils.isNotEmpty(list)){
			for(BillPayableEntity payable :list){
				if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST.equals(payable.getBillType())
						||SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST.equals(payable.getBillType())){
					entity.setPayablefirstId(payable.getId());
				}else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST.equals(payable.getBillType())
						||SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST.equals(payable.getBillType())){
					entity.setPayablelastId(payable.getId());
				}
			}
		}
		
		//其他信息
		if(StringUtils.isEmpty(entity.getPayablefirstId())
				&&StringUtils.isEmpty(entity.getPayablelastId())){
			return;
		}else {
			entity.setConertType(dto.getConfirmType());
			entity.setConvertDate(dto.getConfirmTime());
			entity.setCreateTime(new Date());
			entity.setHandleNo(dto.getHandleNo());
			//设置创建人和部门
			entity = setCreateUserDept(entity);
		}
		
	}
	/**
	 * 根据构建插入实体，直接插入truckArriveConfirmEntity
	 */
	@Override
	public void truckConfirmByEntity(TruckArriveConfirmDto dto) {
		//获取对应的实体，并且调用dao进行插入
		if(dto.getTruckArriveConfirmEntity() != null){
			TruckArriveConfirmEntity entity = dto.getTruckArriveConfirmEntity();
			//判断首款、尾款ID 不能同时为空
			if(StringUtils.isEmpty(entity.getPayablefirstId())
					&&StringUtils.isEmpty(entity.getPayablelastId())){
				return;
			}
			//设置创建人和部门
			entity = setCreateUserDept(entity);
			truckArriveConfirmDao.insertSelective(entity);	
		}
		
	}

	/**
	 * 设置创建人和部门，如果当前登录信息为空，则设为system
	 */
	private TruckArriveConfirmEntity setCreateUserDept(TruckArriveConfirmEntity entity){
		//设置创建人和创建部门
		try {
			UserEntity user = (UserEntity) (UserContext.getCurrentUser());
			//如果当前部门不为空
			if(user != null){
				//设置为当前登录的人和部门
				entity.setCreateUser(user.getEmpCode());
				OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
				entity.setCreateDept(org.getCode());
			}else{
				entity.setCreateUser("system");
				entity.setCreateDept("system");
			}
		} catch (Exception e) {
			entity.setCreateUser("system");
			entity.setCreateDept("system");
		}
		return entity;
	}

	public IBillPayableService getBillPayableService() {
		return billPayableService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}
	public ITruckArriveConfirmDao getTruckArriveConfirmDao() {
		return truckArriveConfirmDao;
	}
	public void setTruckArriveConfirmDao(
			ITruckArriveConfirmDao truckArriveConfirmDao) {
		this.truckArriveConfirmDao = truckArriveConfirmDao;
	}

	
	

}
