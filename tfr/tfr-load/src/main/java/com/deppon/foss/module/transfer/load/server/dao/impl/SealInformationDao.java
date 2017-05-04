package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.ISealInformationDao;
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
public class SealInformationDao extends iBatis3DaoImpl implements ISealInformationDao {

	/**
	 * 命名空间常量
	 */
	private static final String NAMESPACE="com.deppon.foss.module.transfer.load.server.META-INF.ibatis.sealInfo.";
	                                      
	
	/**
	 * 用来保存装车封签的namespace
	 */
	private static final String NSPACE="foss.load.seal.";
	
	/**
	 * @param  
	 * @note   根据PDA传过来的部门信息，查询装车任务的车辆信息
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OptTruckTaskResponseEntity> queryTruckTaskByCarNumDao(
			OptTruckDepartArriveEntity ent) {
		/**
		 * 查询装车任务信息,返回给PDA显示
		 */
		List<OptTruckTaskResponseEntity>  list = this.getSqlSession().selectList(NAMESPACE+"queryTruckTaskByCarNum", ent);
		return list;
	}
	
	/**
	 * @param  OptTruckDepartArriveEntity
	 * @note   根据PDA传过来的部门信息，查询装车任务的车辆信息
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OptTruckTaskResponseEntity> queryTruckTaskByDeptCodeDao(
			OptTruckDepartArriveEntity ent) {
		/**
		 * 查询装车任务信息,返回给PDA显示
		 */
		List<OptTruckTaskResponseEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryTruckTaskByDeptCode", ent);
		return list;
	}
	
	/**
	 * @param  eirNum 交接单号
	 * @note   根据快递传过来的交接单号，获取封签号返回
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> querySealNumListByEIRNum(String eirNum) {
		List<String> selectList = this.getSqlSession().selectList(NAMESPACE+"querySealNumByEIRNum", eirNum);	
		return selectList;
	}

	/**
	 * @param  
	 * @note   根据PDA传过来的车牌号,是否绑定封车
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	@Override
	public List<String> isCheckBindSeal(String taskId) {
		@SuppressWarnings("unchecked")
		List<String> list = this.getSqlSession().selectList(NAMESPACE+"checkIsBindSeal",taskId);
		return list;
	}	
	
	/**
	 * @param  
	 * @note   根据PDA传过来的车牌号,修改该车发车状态
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	@Override
	@Transactional
	public int updateDepartureState(OptTruckTaskEntity obj) {
		//1.操作装车任务明细表
		return this.getSqlSession().update(NAMESPACE+"updateDepartureState", obj);
	}

	/**
	 * @param  
	 * @note   根据PDA传过来的车牌号,修改该车是否到达状态
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	@Override
	public int updateArrivedState(OptTruckTaskEntity obj) {
	    //2.修改装车到达状态
		return this.getSqlSession().update(NAMESPACE+"updateArriveState", obj);
	}

	/**
	 * @see   com.deppon.foss.module.transfer.load.server.dao.impl.SealInformationDao.updateCancledState(OptTruckTaskEntity obj)
	 * @param  OptTruckTaskEntity
	 * @note   根据PDA传过来的车牌号,修改该车发车取消状态
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	@Override
	public int updateCancledState(OptTruckTaskEntity obj) {
		//3.取消发车状态
		return this.getSqlSession().update(NAMESPACE+"updateCancleState", obj);
	}

	/**
	 * @see   com.deppon.foss.module.transfer.load.server.dao.impl.SealInformationDao.updateTruckTaskCarState(Map<String, String> map)
	 * @param  
	 * @note   根据PDA传过来的修改装车任务表的车辆状态信息
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	@Override
	public int updateTruckTaskCarState(Map<String, String> map) {
		//4.为了和装车任务车辆明细保持一致,修改装车任务表车辆信息
		return this.getSqlSession().update(NAMESPACE+"updateTruckTaskTableState", map);
	}

	/**
	* @description 校验当前的车牌号对应的车辆是否被校验
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.server.dao.impl.SealInformationDao.checkCarIsSealDao(String vehicleNo)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	* @paramers String vehicleNo
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<String> checkCarIsSealDao(String vehicleNo) {
		List<String> list = this.getSqlSession().selectList(NAMESPACE+"queryCarIsSeal", vehicleNo);
		return list;
	}
	
	/**
	* @description 配合(快递)校验快递交接单生成
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.server.dao.impl.SealInformationDao.checkExpressEIRCarGenerateDao(Map<String, String> paramMap)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	* @param   String vehicleNo,String deptCode,String status
	*/
	@SuppressWarnings("unchecked")
	@Override
	public Object checkExpressEIRCarGenerateDao(Map<String,String> paramMap) {
		List<String> list = this.getSqlSession().selectList(NAMESPACE+"checkExpressEIRCar", paramMap);
		return list;
	}

	/**
	* @description 配合(快递)绑定封签
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.server.dao.impl#bindSealForExpressDao(SealEntity sealEnt,List<SealOrigDetailEntity> sealOrigs)
	* @author 106162-foss-liping
	* @update 2016年5月5日 上午10:07:04
	* @version V1.0
	*/
	@Transactional
	@Override
	public Object bindSealForExpressDao(SealEntity sealEnt,
			List<SealOrigDetailEntity> sealOrigs) {
		//1.保存在封签表
		int result=this.getSqlSession().insert(NSPACE+"insertSeal", sealEnt);
		if(result > 0){
			//2.保存封签明细表
			result = this.getSqlSession().insert(NSPACE+"insertSealOrigDetail", sealOrigs);
		}
		return result;
		
	}

	/**
	* @description 配合(快递)校验封签
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.server.dao.impl#checkOutSealForExpressDao(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月5日 上午10:07:04
	* @version V1.0
	*/
	@Transactional
	@Override
	public Object checkOutSealForExpressDao(SealEntity sealEnt,
			List<SealDestDetailEntity> sealDetails) {
		//1、更新封签操作    updateSealByPrimaryKey
		this.getSqlSession().update(NSPACE+"updateSealById", sealEnt);
		//2.插入到达封签明细
		int cnt = this.getSqlSession().insert(NSPACE+"insertSealDestDetail", sealDetails);
		return cnt;
	}
	
	/**
	* @description 校验车牌号是否正确(1、查询是否是自有车辆2、公司外请车)
	* @note  当两者都没有查询到的时候,就默认为非法车辆
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public String isDoQueryVehicleNo(String vehicleNo) {
		List<String> list=null;
		String flag="0";// "0" 标示非法车辆   "1"标示合法车辆
		//1.首先校验是否是公司自有车辆
		list = this.getSqlSession().selectList(NAMESPACE+"queryOwnerCar", vehicleNo);
		if(list!=null && list.size()>0){
			flag="1";
			return flag;
		}
		//2.其次校验车辆是否是外请车
		list = this.getSqlSession().selectList(NAMESPACE+"queryOutSiteCar", vehicleNo);
		if(list!=null && list.size()>0){
			flag="1";
			return flag;
		}
		return flag;
	}
	
	/**
	 * @param  vehicleNo  车牌号
	 * @note   根据PDA传过来的车牌号,查询发车状态
	 * @author 106162-gis-liping
	 * @date 2016-6-28 下午4:05:21
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryTruckTaskState(String vehicleNo) {
		List<String> stateList = this.getSqlSession().selectList(NAMESPACE+"queryTruckTaskStatus", vehicleNo); 
		return stateList;
	}	

	/**
	* @description 配合(快递)校验校验临时租车标记
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0 [忘删掉，不使用]
	*/
	@Override
	public Object tempLeaseCarStateDao(String handOverBillNo) {
		return null;
	}

	/**
	 * @param  vehicleNo  车牌号,车辆任务明细ID封装在Map中
	 * @note   根据PDA传过来的车牌号,查询发车状态
	 * @author 106162-gis-liping
	 * @date 2016-7-4 下午4:05:21
	 */
	@Override
	public WKTruckTaskCreateInfoEntity queryTruckTaskdetailEntInfo(
			Map<String, String> map) {
		WKTruckTaskCreateInfoEntity ent =(WKTruckTaskCreateInfoEntity) this.getSqlSession().selectOne(NAMESPACE+"queryTruckTaskDetailInfo", map);
		return ent;
	}

	/**
	 * @param  WKTruckTaskOperRecordsEntity  车牌号
	 * @note   将WKTruckTaskOperRecordsEntity 实体字段保存到车辆放行表中去
	 * @author 106162-gis-liping
	 * @date 2016-7-4 下午4:05:21
	 */
	@Override
	public int insertTruckDepart(WKTruckTaskOperRecordsEntity ent) {
		int cnt = this.getSqlSession().insert(NAMESPACE+"insertDepartToData", ent);
		return cnt;
	}

	/**
	 * @param  WKTruckTaskOperRecordsEntity  车牌号
	 * @note   将WKTruckTaskOperRecordsEntity 实体字段保存到车辆到达的记录表中去
	 * @author 106162-gis-liping
	 * @date 2016-7-4 下午4:05:21
	 */
	@Override
	public int insertTruckArrive(WKTruckTaskOperRecordsEntity ent) {
		int cnt = this.getSqlSession().insert(NAMESPACE+"insertArriveToData", ent);
		return cnt;
	}

	/**
	 * @param  truckDepartId  出发记录
	 * @note   将tfr.t_opt_truck_task_detail 字段修改truck_depart_id
	 * @author 106162-gis-liping
	 * @date 2016-7-4 下午4:05:21
	 */
	@Override
	public int updateTruckTaskDepartId(Map<String,String> map) {
		int updateCnt = this.getSqlSession().update(NAMESPACE+"updateDeaprtId", map);
		return updateCnt;
	}

	/**
	 * @param  truckArriveId  到达记录表
	 * @note   将tfr.t_opt_truck_task_detail 字段修改truck_arrive_id
	 * @author 106162-gis-liping
	 * @date 2016-7-4 下午4:05:21
	 */
	@Override
	public int updateTruckTaskArriveId(Map<String,String> map) {
		int updateCnt = this.getSqlSession().update(NAMESPACE+"updateArriveId", map);
		return updateCnt;
	}

	/**
	 * @param  VehicleNum
	 * @note   根据PDA传过来的车牌号,判断当前车是否已绑定封签
	 * @author 106162-gis-liping
	 * @date 2016-11-24 下午4:05:21
	 */
	@Override
	public List<String> checkSealForCarInfo(String vehicleNum) {
		List<String> sealOrg = this.getSqlSession().selectList(NAMESPACE+"checkSealForCar", vehicleNum);
		return sealOrg;
	}

}
