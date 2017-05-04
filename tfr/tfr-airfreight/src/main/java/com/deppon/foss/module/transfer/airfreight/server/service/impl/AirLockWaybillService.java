package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirLockWaybillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirLockWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirLockWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
/**
 *  空运锁票service
 * 
 * @author 105795-wqh
 * @date 2014-03-17
 */
public class AirLockWaybillService implements IAirLockWaybillService {

	/** 查找空运总调 service*/
	private IAirDispatchUtilService airDispatchUtilService;
	/**锁票dao*/
	private IAirLockWaybillDao airLockWaybillDao;
	

	/**
  	 * @desc 查询空运锁票运单信息List
  	 * @param waybillList 锁票实体
  	 * @param orgCode
  	 * @author 105795-wqh
  	 * @date 2014-03-17
  	 */
	@Override
	public List<AirLockWaybillDetailEntity> queryLockAirWaybillList(List<String> waybillList) {
		if(CollectionUtils.isEmpty(waybillList)){
			throw new TfrBusinessException("没有运单号");
		}
		OrgAdministrativeInfoEntity org=airDispatchUtilService.queryAirDispatchDept(FossUserContext.getCurrentDeptCode());
		String currDeptCode=org.getCode();
		return airLockWaybillDao.queryLockAirWaybillList(waybillList, currDeptCode);
	}
	
	/**
  	 * @desc 新增锁票记录
  	 * @param airLockWaybillDetailEntity 锁票实体
  	 * @author foss-105795-wqh
  	 * @date 2014-03-19
  	 */
	@Override
	public int addAirLockWaybillDetail(AirLockWaybillDetailEntity airLockWaybillDetailEntity) {
		
		if(airLockWaybillDetailEntity==null){
			throw new TfrBusinessException("参数不能为空");
		}
		airLockWaybillDetailEntity.setId(UUIDUtils.getUUID());
		//获取当期空运总调
		OrgAdministrativeInfoEntity org=airDispatchUtilService.queryAirDispatchDept(FossUserContext.getCurrentDeptCode());
		airLockWaybillDetailEntity.setOrgCode(org.getCode());
		airLockWaybillDetailEntity.setOrgName(org.getName());
		//获取当前操作人信息
		UserEntity userEntity=FossUserContext.getCurrentUser();
		//工号
		airLockWaybillDetailEntity.setCreateUserCode(userEntity.getEmployee().getEmpCode());
		//姓名
		airLockWaybillDetailEntity.setCreateUser(userEntity.getEmployee().getEmpName());
		//创建日期
		airLockWaybillDetailEntity.setCreateDate(new Date());
		//修改人工号
		airLockWaybillDetailEntity.setModifyUserCode(userEntity.getEmployee().getEmpCode());
		//修改人姓名
		airLockWaybillDetailEntity.setModifyUser(userEntity.getEmployee().getEmpName());
		//修改时间
		airLockWaybillDetailEntity.setModifyDate(new Date());
		//设置为加锁状态
		airLockWaybillDetailEntity.setLockStatus(AirfreightConstants.AIR_WAYBILL_LOCK);
		return airLockWaybillDao.addAirLockWaybillDetail(airLockWaybillDetailEntity);
	}

	/**
  	 * @desc 解除空运锁票
  	 * @param airLockWaybillDetailEntity 锁票实体
  	 * @author foss-105795-wqh
  	 * @date 2014-03-19
  	 */
	public int unlockAirWaybill(AirLockWaybillDetailEntity airLockWaybillDetailEntity) {
		
		
	    if(airLockWaybillDetailEntity.getWaybillNo()==null||
	    		"".equals(airLockWaybillDetailEntity.getWaybillNo())){
	    	throw new TfrBusinessException("没有锁票运单");
	    }
		
		//获取当期空运总调
		OrgAdministrativeInfoEntity org=airDispatchUtilService.queryAirDispatchDept(FossUserContext.getCurrentDeptCode());
		airLockWaybillDetailEntity.setOrgCode(org.getCode());
		airLockWaybillDetailEntity.setLockStatus(AirfreightConstants.AIR_WAYBILL_LOCK);
		AirLockWaybillDetailEntity oldAirLockWaybillDetail=
				airLockWaybillDao.queryLockAirWaybill(airLockWaybillDetailEntity);
		
		airLockWaybillDetailEntity.setId(oldAirLockWaybillDetail.getId());
		//获取当前操作人信息
		UserEntity userEntity=FossUserContext.getCurrentUser();
		
		//修改人工号
		airLockWaybillDetailEntity.setModifyUserCode(userEntity.getEmployee().getEmpCode());
		//修改人姓名
		airLockWaybillDetailEntity.setModifyUser(userEntity.getEmployee().getEmpName());
		//修改时间
		airLockWaybillDetailEntity.setModifyDate(new Date());
		//设置为加锁状态
		airLockWaybillDetailEntity.setLockStatus(AirfreightConstants.AIR_WAYBILL_UNLOCK);
		
		return airLockWaybillDao.unlockAirWaybill(airLockWaybillDetailEntity);
	}

	/**
  	 * @desc 查询空运锁票运单信息
  	 * @param airLockWaybillDetailEntity 锁票实体
  	 * @author 105795-wqh
  	 * @date 2014-03-17
  	 */
	@Override
	public AirLockWaybillDetailEntity queryLockAirWaybill(
			AirLockWaybillDetailEntity airLockWaybillDetailEntity) {
		if(airLockWaybillDetailEntity==null){
			throw new TfrBusinessException("参数为空");
		}
		if(airLockWaybillDetailEntity.getWaybillNo()==null ||
				"".equals(airLockWaybillDetailEntity.getWaybillNo())){
			throw new TfrBusinessException("运单号为空");
		}
		//设置总调部门
		OrgAdministrativeInfoEntity org=airDispatchUtilService.queryAirDispatchDept(FossUserContext.getCurrentDeptCode());
		airLockWaybillDetailEntity.setOrgCode(org.getCode());
		return airLockWaybillDao.queryLockAirWaybill(airLockWaybillDetailEntity);
	}
	
	/**
	 * @desc 查询当前空运总调中运单件数
	 * @param orgCode 
	 * @param goodAreaCode
	 * @param waybillNoList
	 * @author 105795-wqh
	 * @date 2014-04-08
	 */ 
   public List<AirWaybillDetailEntity> queryWaybillStockQty(List<String> waybillNoList,String orgCode,String goodAreaCode){
	   
	   return airLockWaybillDao.queryWaybillStockQty(waybillNoList,orgCode,goodAreaCode);
   }
	
  //set
	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}

	public void setAirLockWaybillDao(IAirLockWaybillDao airLockWaybillDao) {
		this.airLockWaybillDao = airLockWaybillDao;
	}

}
