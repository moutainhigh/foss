package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.load.api.server.dao.IVehicleEmptyBillDao;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.IvehiclEmptyBillService;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehiclEmptyBillDto;
import com.deppon.foss.util.define.FossConstants;

public class VehiclEmptyBillAndService implements IvehiclEmptyBillService {
    private IVehicleEmptyBillDao vehicleEmptyBillDao;
    private  ITfrCommonService tfrCommonService;
    private  IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    private ITruckTaskService truckTaskService;
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	private static final Logger LOGGER = Logger.getLogger(VehiclEmptyBillAndService.class);
     public static Logger getLogger() {
		return LOGGER;
	}
	
	public void setVehicleEmptyBillDao(IVehicleEmptyBillDao vehicleEmptyBillDao) {
		this.vehicleEmptyBillDao = vehicleEmptyBillDao;
	}
	/**
	 * 空驶单查询
	 *  @author zhangpeng
	 * @date 2015-10-10 上午10:51:04
	 */
	@Override
	public List<VehiclEmptyBillDto> queryVehicleEmptyBill(VehiclEmptyBillDto vehiclEmDto,
			int limit, int start) {
	
		return vehicleEmptyBillDao.queryVehicleEmptyBill(vehiclEmDto, limit, start);
	}
	/**
	 * 空驶单查询 获取查询的总记录数
	 * @author zhangpeng
	 * @date 2015-10-10 上午10:51:04
	 */
	@Override
	public Long queryVehiclEmptyBillCount(VehiclEmptyBillDto vehiclEmDto) {
		// TODO Auto-generated method stub
		return vehicleEmptyBillDao.getVehiclEmptyBillCount(vehiclEmDto);
	}
	
	/**
 	 * 空驶单保存
 	 * @author zhangpeng
 	 * @date 2015-10-10 下午18:10:14
 	 */
	@SuppressWarnings("unused")
	@Override
	public String saveVehiclEmptyBill(VehiclEmptyBillDto vehiclEmDto) {
		// 自动生成空驶单号K+8
		String currentOrgCode = null;
		String currentOrgName = null;
		String vehiclEmptyBillNo = tfrCommonService
				.generateSerialNumber(TfrSerialNumberRuleEnum.KSD);
		UserEntity user = FossUserContext.getCurrentUser();
		// 获取制单人
		String userName = user.getEmployee().getEmpName();
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(orgCode);
		String salesDepartment = orgAdministrativeInfoEntity
				.getSalesDepartment();
		if (salesDepartment != null
				&& StringUtils.equals(salesDepartment, FossConstants.YES)) {
			currentOrgCode = orgAdministrativeInfoEntity.getCode();
			currentOrgName = orgAdministrativeInfoEntity.getName();
		} else {
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity2 = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoOfConnOutfieldCenterByCode(orgCode);
			if (orgAdministrativeInfoEntity2 != null) {
				if (StringUtils.isNotBlank(orgAdministrativeInfoEntity2
						.getTransferCenter())) {
					currentOrgCode = orgAdministrativeInfoEntity2.getCode();
					currentOrgName = orgAdministrativeInfoEntity2.getName();
				}
			}
		}
		// 获取当前部门编号名称，若为营业部则为营业部 若不为营业部则获取当前营业部的外场
		Date nowDate = new Date();
		vehiclEmDto.setVehiclEmptyBillNo(vehiclEmptyBillNo);
		vehiclEmDto.setCreaterUserName(userName);
		vehiclEmDto.setCreateTime(nowDate);
		vehiclEmDto.setOrigOrgCode(currentOrgCode);
		vehiclEmDto.setOrigOrgName(currentOrgName);
		// 获取当前车辆状态
	     String vehicleNo=vehiclEmDto.getVehicleNo();
         //根据当前车辆号获取当前车辆状态
	     Long list=searchStatusByVehicleNo(vehicleNo);
//		 int ksd=0;
		 if(list==0){
			 //sonar-352203-值未使用
			 /*ksd= */truckTaskService.createTruckTaskByVehiclEmDto(vehiclEmDto);
		 }
		 return vehiclEmptyBillNo;
     }
	
	/**
 	 * 空驶单保存
 	 * @author zhangpeng
 	 * @date 2015-10-10 下午18:10:14
 	 */
	@Override
	@Transactional
	public int deleteTruckTaskByVehiclEmptyBill(String vehiclEmptyBillNo) {
		//根据交接单查询任务车辆ID，任务车辆明细ID
		VehiclEmptyBillDto vehiclEmptyBillDto = vehicleEmptyBillDao.queryTruckTaskIdByVehiclEmptyBillNo(vehiclEmptyBillNo);
		//任务车辆id不为空
		//任务车辆明细不为空
		if(vehiclEmptyBillDto != null){ 
		  if(StringUtils.isNotBlank(vehiclEmptyBillDto.getTruckTaskId()) && StringUtils.isNotBlank(vehiclEmptyBillDto.getTruckTaskDettailId()) && StringUtils.isNotBlank(vehiclEmptyBillDto.getTruckTaskBillId())){
				//任务车辆id
				String truckTaskId = vehiclEmptyBillDto.getTruckTaskId();
				//任务车辆明细id
				String truckTaskDetailId = vehiclEmptyBillDto.getTruckTaskDettailId();
				//任务车辆单据id
				if(StringUtils.isNotBlank(truckTaskId) && StringUtils.isNotBlank(truckTaskDetailId)){
					//删除车辆任务 
					vehicleEmptyBillDao.deleteTruckTask(truckTaskId);
					//删除任务车辆明细
					vehicleEmptyBillDao.deleteTruckTaskDetail(truckTaskDetailId);
							/*if(StringUtils.isNotBlank(truckTask.getTruckGPSTaskId())){
								String truckGPSTaskId = truckTask.getTruckGPSTaskId();
								//GPS任务列表
								TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
								truckGPSTask.setId(truckGPSTaskId);
								truckGPSTask.setOperateType(TaskTruckConstant.GSP_OPERATE_TYPE_DELETE);
								truckTaskDao.deleteTruckTaskGPSDetail(truckGPSTaskId);
								//调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败
								//this.synDeleteTruckGPSTask(truckGPSTask);
							}	*/
				}
				//删除任务车辆单据
				
				return vehicleEmptyBillDao.deleteTruckTaskBill(vehiclEmptyBillNo) ;
						
			}
		}
		return 0;
	}
	
	/**
	 * 根据车牌号查询车辆状态是否在途，未发车（新增）
	 * @author zhangpeng
	 * @date 2015-10-10 上午10:51:04
	 */
	public Long searchStatusByVehicleNo(String vehicleNo) {

		return vehicleEmptyBillDao.searchStatusByVehicleNoIsDepart(vehicleNo);
	}
	
	/**
	 * 根据空驶单号查询车辆状态是否在途已到达
	 * @author zhangpeng
	 * @date 2015-10-10 上午10:51:04
	 */
	public Long searchStatusByVehiclEmptyBillNo(String vehicleEmpNo) {

		return vehicleEmptyBillDao.searchStatusByVehiclEmptyBillNo(vehicleEmpNo);
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
    public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	
	
	

	
	
	

}
