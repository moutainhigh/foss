package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.OptTruckDepartArriveEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.OptTruckTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.OptTruckTaskResponseEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealDestDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealOrigDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTruckTaskCreateInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTruckTaskOperRecordsEntity;


/**
 * @note  Dao层
 * @description 获取封签信息
 * @version 1.0
 * @author 106162-foss-liping
 * @update 2016年4月25日 上午8:07:27
 */
public interface ISealInformationDao {


	/**
	 * @param  eirNum 交接单号
	 * @note   根据快递传过来的交接单号，获取封签号返回
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	List<String> querySealNumListByEIRNum(String eirNum);

	/**
	 * @param  
	 * @note   根据PDA传过来的部门信息，查询装车任务的车辆信息
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	List<OptTruckTaskResponseEntity> queryTruckTaskByCarNumDao(OptTruckDepartArriveEntity ent);
	
	/**
	 * @param  OptTruckDepartArriveEntity
	 * @note   根据PDA传过来的部门信息，查询装车任务的车辆信息
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	List<OptTruckTaskResponseEntity> queryTruckTaskByDeptCodeDao(OptTruckDepartArriveEntity ent);
	
	/**
	 * @param  
	 * @note   根据PDA传过来的车牌号,修改该车发车状态
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	int updateDepartureState(OptTruckTaskEntity obj);
	
	/**
	 * @param  
	 * @note   根据PDA传过来的车牌号,修改该车是否到达状态
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	int updateArrivedState(OptTruckTaskEntity obj);
	
	/**
	 * @param  
	 * @note   根据PDA传过来的车牌号,修改该车发车取消状态
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	int updateCancledState(OptTruckTaskEntity obj);
	
	/**
	 * @param  
	 * @note   根据PDA传过来任务号,当未绑定不能发车
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	List<String> isCheckBindSeal(String taskId);
	
	/**
	 * @param  
	 * @note   根据PDA传过来的修改装车任务表的车辆状态信息
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	int updateTruckTaskCarState(Map<String,String> map);
	
	/**
	* @description 校验当前的车牌号对应的车辆是否被校验
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkCarIsSeal(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	* @paramers String vehicleNo
	*/
	List<String> checkCarIsSealDao(String vehicleNo);
	/**
	* @description 配合(快递)校验快递交接单生成
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	* @param String vehicleNo,String deptCode,String status
	*/
	Object checkExpressEIRCarGenerateDao(Map<String,String> paramMap);
	

	/**
	* @description 配合(快递)绑定封签
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	*/
	Object bindSealForExpressDao(SealEntity sealEnt,
			List<SealOrigDetailEntity> sealOrigs);
	
	/**
	* @description 配合(快递)校验封签
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	*/
	Object checkOutSealForExpressDao(SealEntity sealEnt,
			List<SealDestDetailEntity> sealDetails);
	
	/**
	* @description 配合(快递)校验校验临时租车标记
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	Object tempLeaseCarStateDao(String handOverBillNo);
	
	
	/**
	* @description 校验车牌号是否正确(1、查询是否是自有车辆2、公司外请车)
	* @note  当两者都没有查询到的时候,就默认为非法车辆
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	String  isDoQueryVehicleNo(String vehicleNo);
	
	/**
	 * @param  vehicleNo  车牌号
	 * @note   根据PDA传过来的车牌号,查询发车状态
	 * @author 106162-gis-liping
	 * @date 2016-6-28 下午4:05:21
	 */
	List<String> queryTruckTaskState(String vehicleNo);
	
	/**
	 * @param  vehicleNo  车牌号,车辆任务明细ID封装在Map中
	 * @note   根据PDA传过来的车牌号,查询发车状态
	 * @author 106162-gis-liping
	 * @date 2016-7-4 下午4:05:21
	 */
	WKTruckTaskCreateInfoEntity queryTruckTaskdetailEntInfo(Map<String,String> map);
	/**
	 * @param  WKTruckTaskOperRecordsEntity 
	 * @note   将WKTruckTaskOperRecordsEntity 实体字段保存到车辆放行表中去
	 * @author 106162-gis-liping
	 * @date 2016-7-4 下午4:05:21
	 */
	int insertTruckDepart(WKTruckTaskOperRecordsEntity ent);
	/**
	 * @param  WKTruckTaskOperRecordsEntity  
	 * @note   将WKTruckTaskOperRecordsEntity 实体字段保存到车辆到达的记录表中去
	 * @author 106162-gis-liping
	 * @date 2016-7-4 下午4:05:21
	 */
	int insertTruckArrive(WKTruckTaskOperRecordsEntity ent);
	
	/**
	 * @param  truckDepartId  出发记录
	 * @note   将tfr.t_opt_truck_task_detail 字段修改truck_depart_id
	 * @author 106162-gis-liping
	 * @date 2016-7-4 下午4:05:21
	 */
	 int updateTruckTaskDepartId(Map<String,String> map);
	/**
	 * @param  truckArriveId  到达记录表
	 * @note   将tfr.t_opt_truck_task_detail 字段修改truck_arrive_id
	 * @author 106162-gis-liping
	 * @date 2016-7-4 下午4:05:21
	 */
	 int updateTruckTaskArriveId(Map<String,String> map);
	
    /**
	 * @param  VehicleNum
	 * @note   根据PDA传过来的车牌号,判断当前车是否已绑定封签
	 * @author 106162-gis-liping
	 * @date 2016-11-24 下午4:05:21
	 */
	 List<String> checkSealForCarInfo(String vehicleNum);
}
