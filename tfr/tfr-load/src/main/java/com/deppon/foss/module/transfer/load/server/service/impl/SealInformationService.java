package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDetailDao;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.server.service.IWkBillAddTfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.NotifyWkConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.WkHandOverBillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.load.api.server.dao.ISealInformationDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ISealInformationService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.SealConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.OptTruckDepartArriveEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.OptTruckTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.OptTruckTaskResponseEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.RequestSealBindANDCheckOutEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ResponseJsonInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealDestDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealNumANDStateEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealOrigDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTruckTaskCreateInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTruckTaskOperRecordsEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.SealDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
* @description 获取封签信息
* @version 1.0
* @author 106162-foss-liping
* @update 2016年4月25日 上午8:07:27
*/
public class SealInformationService implements ISealInformationService {

	/**
	 * 定义ITFSealInformationDao对象
	 */
	private ISealInformationDao sealInformationDao;
	
	/**---------------------------------------------调用外部service--------------------------------------------*/

	/**
	 * 获取组织信息service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/***
	 * 获取车辆信息service
	 */
	private IVehicleService vehicleService;
	
	/**
	 * 保安组信息securityTfrMotorcadeService
	 */
	private ISecurityTfrMotorcadeService securityTfrMotorcadeService;
	
	/**
	 *  综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 获取车队 Service接口
	 */
	private IMotorcadeService motorcadeService;
	
	/***
	 * 封签Dao
	 * 绑定封签
	 * 校验封签 
	 */
	private IVehicleSealDao vehicleSealDao;
	
	/****
	 * 人员service
	 */
	private IEmployeeService employeeService;
	
	/**
	 * 临时租车状态
	 */
	private IHandOverBillService handOverBillService;

	/**
	 * 保存日志信息
	 */
	private static Logger LOGGER = LogManager.getLogger(SealInformationService.class);
    
	
	/** 查询航空正单dao. */
	private IAirWaybillDetailDao pointsSingleJointTicketDao;
	
	/**
	 *将出发确认到达保存到任务通知表里面去,后续通知给悟空系统 
	 */
	private ITfrNotifyService tfrNotifyService;
	
	/**--------------------------------------------------------------------------------------------------*/
	
	/**
	 * @param  eirNum 交接单号
	 * @note   根据快递传过来的交接单号，获取封签号返回
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	@Override
	public List<String> querySealNumByEIRNumList(String eirNum) {
		List<String> queryList = sealInformationDao.querySealNumListByEIRNum(eirNum);
		return queryList;
	}
	
	// 屏蔽ECS系统接口服务类
	private IConfigurationParamsService configurationParamsService;

	private IWkBillAddTfrNotifyService wkBillAddTfrNotifyService;
	
	/**
	 * @param wkBillAddTfrNotifyService the wkBillAddTfrNotifyService to set
	 */
	public void setWkBillAddTfrNotifyService(IWkBillAddTfrNotifyService wkBillAddTfrNotifyService) {
		this.wkBillAddTfrNotifyService = wkBillAddTfrNotifyService;
	}

	/**
	 * 屏蔽ECS系统接口服务类
	 * @param configurationParamsService
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	/**
	 * @param  
	 * @note   根据PDA传过来的部门信息，查询装车任务的车辆信息
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	@Override
	public List<OptTruckTaskResponseEntity> queryTruckTaskByByDeptCodeORCarNum(
			OptTruckDepartArriveEntity ent) {
		List<OptTruckTaskResponseEntity> list =null;
		
		String vehicleNo = ent.getVehicleNo();//获取车牌号
		
		if(vehicleNo!=null && !"".equals(vehicleNo)){
			/*
			 * 1.当车牌号不为空时,根据车牌号去查装车任务信息返给PDA
			 *   规则：1、如果是出发部门
			 *         车牌号+出发部门+状态为：未出发-UNDEPART+当天时间
			 *       2、如果是到达部门
			 *         车牌号+到达部门+状态为：未出发-UNDEPART+当天时间
			 */
			 list=sealInformationDao.queryTruckTaskByCarNumDao(ent);
		}else{
			/*
			 * 2.当车牌号为空的时,要根据部门code查询装车任务信息返给PDA
			 *   规则：1、如果是出发部门
			 *         出发部门+状态为：未出发-UNDEPART+当天时间
			 *       2、如果是到达部门
			 *         到达部门+状态为：在途-ONTHEWAY+当天时间
			 */
			list=sealInformationDao.queryTruckTaskByDeptCodeDao(ent);
		}
		return list;
	}
	
	
	/**
	 * @param  
	 * @note   根据PDA传过来的车牌号,修改该车是否到达,发车状态
	 * @author 106162-foss-liping
	 * @date 2016-4-24 下午4:05:21
	 * @status 未出发-UNDEPART，在途-ONTHEWAY，已到达-ARRIVED，作废-CANCLED
	 * @description 使用事务,保证在更新装车任务表的同时,也保持一致更新装车任务明细表
	 */
	@Override
	@Transactional
	public ResponseJsonInfoEntity updateDepartureState(OptTruckTaskEntity obj) {
		
		//修改装车任务表的参数map集合
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("taskId", obj.getTruckTaskId());
		paramMap.put("vehicleNo", obj.getVehicleNo());
		
		//修改车辆任务明细的出发到达ID
		Map<String,String> map=null;
		
		//校验任务ID不能为空
		if(obj.getTruckTaskId()==null || "".equals(obj.getTruckTaskId())){
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.NULLEXCEPTION,"【装车任务ID 异常编号："+ResponseJsonInfoEntity.NULLEXCEPTION+"】"+ResponseJsonInfoEntity.FIVETEXTINFO);
		}
		//车牌号不能为空
		if(obj.getVehicleNo()==null || "".equals(obj.getVehicleNo())){
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.NULLEXCEPTION,"【车牌号   异常编号："+ResponseJsonInfoEntity.NULLEXCEPTION+"】"+ResponseJsonInfoEntity.FIVETEXTINFO);
		}
		
		//发车确认和取消发车之前先判断是否有封签
		List<String> sealList =sealInformationDao.isCheckBindSeal(obj.getTruckTaskId());
		int sealCnt = sealList.size();
		if(sealCnt==0){
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.NULLEXCEPTION,"【封签号没有绑定   异常编号："+ResponseJsonInfoEntity.SEALNUMISNOTEXIST+"】"+ResponseJsonInfoEntity.SEVENTEXTINFO);
		}
		
		//获取车辆任务明细中的放行车辆、到达车辆的ID及其车辆任务主表ID
		WKTruckTaskOperRecordsEntity departArriveENT=new WKTruckTaskOperRecordsEntity();
		departArriveENT.setVehicleNo(obj.getVehicleNo());//车牌号
		departArriveENT.setManualUserCode(obj.getCurrentOprCode());//实际操作人
		departArriveENT.setPdaUserCode(obj.getCurrentOprCode());//PDA实际操作人
		departArriveENT.setCreateOrgCode(obj.getCurrentOprDeptCode());//创建部门
		departArriveENT.setCreateUserName(obj.getCurrentOprName());//创建人工号
		departArriveENT.setCreateUserCode(obj.getCurrentOprCode());//创建人姓名
		
		//获取车辆任务主表ID
		WKTruckTaskCreateInfoEntity entt = sealInformationDao.queryTruckTaskdetailEntInfo(paramMap);
		if(entt!=null){
			departArriveENT.setTruckTaskId(entt.getTruckTaskId());//车辆任务主表ID
		}
		
		//记录修改操作计数
		int updateCnt=0;
		//返回结果信息
		String info=ResponseJsonInfoEntity.ONENUMBERTEXTINFO;
		//获取PDA发过来的操作标示
		String deaprtureType = obj.getDepartType();
		if(deaprtureType!=null && !"".equals(deaprtureType)){
			//1.当传递过来的参数值为：1,表示确认发车
			if("1".equals(deaprtureType)){
				/**
				 * 通过传递过来的车牌号、出发部门编码、将状态为未出发-UNDEPART改为在途-ONTHEWAY,锁定数据库对应的数据 进行修改操作
				 */
				//修改装车任务表的参数map集合
				paramMap.put("status", "ONTHEWAY");
				//修改装车任务表
				updateCnt = sealInformationDao.updateTruckTaskCarState(paramMap);
				//修改装车任务明细表
				if(updateCnt>0){
					updateCnt = sealInformationDao.updateDepartureState(obj);
					if(updateCnt>0){	
						//调用ECS系统接口开关
						if (configurationParamsService.queryTfrSwitch4Ecs()) {
							// 插入到通知表，将数据同步到悟空系统
							LOGGER.error("调用插入临时表，通过JOB推送数据给悟空");
							// 根据车辆任务明细ID查询出悟空交接单
							List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService
									.queryWkHandOverBillByTruckTaskDetailId(obj.getTruckTaskId());
							for (WkHandOverBillEntity entity : wkHandoverbillList) {
								// 异步通知任务实体 创建通知快递系统异步通知任务的job实体
								TfrNotifyEntity notifyEntity = new TfrNotifyEntity();
								notifyEntity.setId(UUIDUtils.getUUID());
								notifyEntity.setNotifyParam1(obj.getTruckTaskId());
								notifyEntity.setNotifyType(NotifyWkConstants.NOTIFY_TYPE_TRUCK_DEPARTURE);
								notifyEntity.setNotifyParam2(BusinessSceneConstants.WK_HANDORVERBILL_HAVE_DEPART);
								notifyEntity.setNotifyParam3(obj.getCurrentOprCode() + "," + obj.getCurrentOprName()
										+ "," + obj.getCurrentOprDeptCode());
								notifyEntity
										.setParamJson(entity.getHandoverBillNo() + "," + entity.getOperationOrgCode());
								updateCnt = tfrNotifyService.addTfrNotifyEntity(notifyEntity);
							}
						}   
						
						//插入到车辆放行中去
						String truckDepartId= UUIDUtils.getUUID();
						departArriveENT.setTruckDepartId(truckDepartId);
						departArriveENT.setStatus("9");//“9”标示出发
						sealInformationDao.insertTruckDepart(departArriveENT);
						//修改车辆任务明细的字段“truck_depart_id”
						//修改装车任务表的参数map集合
						map=new HashMap<String,String>();
						map.put("id", obj.getTruckTaskId());
						map.put("truck_depart_id", truckDepartId);
						sealInformationDao.updateTruckTaskDepartId(map);
					}else{
						info="奔溃,确认发车时修改车辆任务明细数据失败!";
					}
				}else{
					info="奔溃,确认发车时修改车辆任务主表数据失败!";
				}
				//保存到车辆放行表中
			}else if("2".equals(deaprtureType)){//2.当传递过来的参数值为：2,表示确认已到达
				/**
				 * 通过传递过来的车牌号、出发部门编码、将状态为在途-ONTHEWAY改为已到达-ARRIVED,锁定数据库对应的数据 进行修改操作
				 */
				//修改装车任务表的参数map集合
				paramMap.put("status", "ARRIVED");
				//修改装车任务表
				updateCnt = sealInformationDao.updateTruckTaskCarState(paramMap);
				//修改装车任务明细表
				if(updateCnt>0){
					updateCnt = sealInformationDao.updateArrivedState(obj);
					if(updateCnt>0){
						//调用ECS系统接口开关
						if (configurationParamsService.queryTfrSwitch4Ecs()) {
							//插入到通知表，将数据同步到悟空系统
							LOGGER.error("调用插入临时表，通过JOB推送数据给悟空");
							// 根据车辆任务明细ID查询出悟空交接单
							List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService
									.queryWkHandOverBillByTruckTaskDetailId(obj.getTruckTaskId());
							for (WkHandOverBillEntity entity : wkHandoverbillList) {
								// 异步通知任务实体 创建通知快递系统异步通知任务的job实体
								TfrNotifyEntity notifyEntity = new TfrNotifyEntity();
								notifyEntity.setId(UUIDUtils.getUUID());
								notifyEntity.setNotifyParam1(obj.getTruckTaskId());
								notifyEntity.setNotifyType(NotifyWkConstants.NOTIFY_TYPE_TRUCK_ARRIVAL);
								notifyEntity.setNotifyParam2(BusinessSceneConstants.WK_HANDORVERBILL_HAVE_ARRIVE);
								notifyEntity.setNotifyParam3(obj.getCurrentOprCode() + "," + obj.getCurrentOprName()
										+ "," + obj.getCurrentOprDeptCode());
								notifyEntity
										.setParamJson(entity.getHandoverBillNo() + "," + entity.getOperationOrgCode());
								updateCnt = tfrNotifyService.addTfrNotifyEntity(notifyEntity);
							}
						}
						
						//插入到车辆到达表中去
						String truckArriveId= UUIDUtils.getUUID();
						departArriveENT.setTruckArriveId(truckArriveId);
						departArriveENT.setStatus("ARRIVED");//"ARRIVED"标示车辆到达
						sealInformationDao.insertTruckArrive(departArriveENT);
						//修改车辆任务明细的字段“truck_arrive_id”
						//修改装车任务表的参数map集合
						map=new HashMap<String,String>();
						map.put("id", obj.getTruckTaskId());
						map.put("truck_arrive_id", truckArriveId);
						sealInformationDao.updateTruckTaskArriveId(map);
					}else{
						info="奔溃,确认到达时修改车辆任务明细数据失败!";
					}
				}else{
					info="奔溃,确认到达时修改车辆任务主表数据失败!";
				}
				
			}else if("0".equals(deaprtureType)){//3.当传递过来的参数值为：0,表示确认取消发车
				/**
				 * 通过传递过来的车牌号、出发部门编码对应的数据将状态改为作废-UNDEPART,锁定数据库对应的数据 进行修改操作
				 */
				//修改装车任务表的参数map集合
				paramMap.put("status", "UNDEPART");
				//修改装车任务表
				updateCnt = sealInformationDao.updateTruckTaskCarState(paramMap);
				//修改装车任务明细表
				if(updateCnt>0){
					updateCnt = sealInformationDao.updateCancledState(obj);
					if(updateCnt>0){
						//调用ECS系统接口开关
						if (configurationParamsService.queryTfrSwitch4Ecs()) {
							//插入到通知表，将数据同步到悟空系统
							LOGGER.error("调用插入临时表，通过JOB推送数据给悟空");
							// 根据车辆任务明细ID查询出悟空交接单
							List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService
									.queryWkHandOverBillByTruckTaskDetailId(obj.getTruckTaskId());
							for (WkHandOverBillEntity entity : wkHandoverbillList) {
								// 异步通知任务实体 创建通知快递系统异步通知任务的job实体
								TfrNotifyEntity notifyEntity = new TfrNotifyEntity();
								notifyEntity.setId(UUIDUtils.getUUID());
								notifyEntity.setNotifyParam1(obj.getTruckTaskId());
								notifyEntity.setNotifyType(NotifyWkConstants.NOTIFY_TYPE_CANCEL_TRUCK_DEPARTURE);
								notifyEntity.setNotifyParam2(BusinessSceneConstants.WK_HANDORVERBILL_CANCEL_HAVE_DEPART);
								notifyEntity.setNotifyParam3(obj.getCurrentOprCode() + "," + obj.getCurrentOprName()
										+ "," + obj.getCurrentOprDeptCode());
								notifyEntity
										.setParamJson(entity.getHandoverBillNo() + "," + entity.getOperationOrgCode());
								updateCnt = tfrNotifyService.addTfrNotifyEntity(notifyEntity);
							}
						}
						
					}else{
						info="奔溃,取消发车时修改车辆任务明细数据失败!";
					}
				}else{
					info="奔溃,取消发车时修改车辆任务主表数据失败!";
				}
				
			}
		}
		if(updateCnt==0){
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,info);
		}else{
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.SUCCESS,info);
		}
	}
	
	/**
	* @description 校验当前的车牌号对应的车辆是否被校验
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkCarIsSeal(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	* @paramers String vehicleNo
	*/
	@Override
	public List<String> checkCarIsSealService(String vehicleNo) {
		List<String> list =sealInformationDao.checkCarIsSealDao(vehicleNo);
		return list;
	}
	
	/**
	* @description 配合(快递)校验快递交接单生成
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public Object checkExpressEIRCarGenerateService(Map<String, String> paramMap) {
		 //1.校验车辆信息
		List<String> list =(List<String>) sealInformationDao.checkExpressEIRCarGenerateDao(paramMap);
		return list;
	}
	
	/**
	* @description 配合PDA,绑定封车封签
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月5日 上午10:07:04
	* @version V1.0
	* @note    1、插入车辆封签表【TFR.T_OPT_SEAL】 
	*          2、插入车辆封签明细表【tfr.t_opt_seal_orig_detail】
	*/
	@Transactional
	@Override
	public ResponseJsonInfoEntity bindSealForExpressService(RequestSealBindANDCheckOutEntity seal) {
		//定义当前部门组织变量
		String currentDept=seal.getDepartmentCode();
		//1.校验封签明细信息是否为空
		if(CollectionUtils.isEmpty(seal.getList())) {
			LOGGER.error("请录入封签号");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"封签号错误, 请确认封签号信息是否填写!");
		}

		//2.判断当前的部门是否合法
		if(StringUtils.isEmpty(currentDept)) {
			LOGGER.error("当前部门不能为空");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"当前部门错误, 请确认当前部门信息是否填写!");
		} else {
			/**
			 * 读取登录部门所属外场/营业部
			 */
			OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentDept);
			if(origOrg != null){
				//当前部门顶级组织
				origOrg = querySuperiorOrgByOrgCode(currentDept);
				currentDept = origOrg.getCode();
			}else{
				LOGGER.error("找不到当前部门(登录部门所属外场/营业部)");
				return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"登录部门所属外场/营业部错误, 请确认登录部门所属外场/营业部信息是否正确!");
			}
		}
		
		//判断绑定封签的当前部门与对应车辆任务明细的出发部门是否一致

		//3.判断目的站的信息是否为空
		if(StringUtils.isEmpty(seal.getDestination())) {
			LOGGER.error("目的站部门不能为空");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"目的站信息不能为空, 请确认目的站部门信息是否正确!");
		}
		//4.判断车辆是否是公司车辆.判定车牌号是否以德开头，若是德开头不校验车辆的准确性
		if(seal.getCarNumber()!=null && !"".equals(seal.getCarNumber())){
			VehicleAssociationDto vehicleAssociation = vehicleService.queryVehicleAssociationDtoByVehicleNo(seal.getCarNumber());
			if(vehicleAssociation == null) {
				LOGGER.error("输入的车牌号有误！");
				return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"车牌号错误, 请确认系统现在还存在该车辆!");
			}	
		}
		//5.判断当前车辆是否属于当前部门
		//根据车牌号, 当前部门, 下一部门找车辆任务明细
		TruckTaskDetailEntity truckTaskDetail = getTruckTaskDetail(seal.getCarNumber(),currentDept, seal.getDestination());
		LOGGER.error("车牌号："+seal.getCarNumber()+",出发部门："+currentDept+",到达部门："+seal.getDestination());
		if(truckTaskDetail == null) {
			LOGGER.error("车牌号："+seal.getCarNumber()+",出发部门："+currentDept+",到达部门："+seal.getDestination()+"  找不到对应的车辆任务明细!");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"车牌号："+seal.getCarNumber()+",出发部门："+seal.getDepartmentCode()+",到达部门："+seal.getDestination()+"  找不到对应的车辆任务明细,数据有问题 请核实!");
		}
		//6.判断当前车辆是否已经封签
		//根据车辆任务id获取车辆任务
		TruckTaskEntity truckTask = vehicleSealDao.getTruckTaskById(truckTaskDetail.getParentId());
		if(truckTask == null) {
			LOGGER.error("车辆任务已被作废.");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"车辆任务已被作废!");
		}
		//7.判断当前车辆的封签状态
		SealEntity sealTemp = new SealEntity();
		sealTemp.setTruckTaskDetailId(truckTaskDetail.getParentId());
		sealTemp.setSealType(SealConstant.SEAL_TYPE_BIND);
		//根据任务车辆id找封签
		List<SealEntity> seals = querySealByTruckTaskId(sealTemp);
		//已绑定的不能有多条, 但是已校验的可以有多条, 因为A==>B==>C==>D  可能出现这样的情况
		if(seals != null) {
			if(seals.size() >= 1) {
				LOGGER.error("当前任务车辆已绑定封签, 不能重复绑定, 请刷新后操作.");
				return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"当前任务车辆已绑定封签, 不能重复绑定!");
			}
		}
		//8.重复封签不能封签
		for(SealNumANDStateEntity sealEnt : seal.getList()) {
			if(StringUtils.isNotEmpty(sealEnt.getSealNumber())) {
				String sealNo = sealEnt.getSealNumber();
				List<SealOrigDetailEntity> sealOrigDetailEntitys = querySealOrigDetailsBySealNo(sealNo);
				if(CollectionUtils.isNotEmpty(sealOrigDetailEntitys)) {
					LOGGER.error("重复的封签.");
					return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"封签号为："+sealNo+" 已绑定封签, 不能重复绑定!");
				}
			}
		}
		
	   //9.封装保存封签任务信息实体
		//封签entity
		SealEntity sealEnt = new SealEntity();
		//封签id
		sealEnt.setId(UUIDUtils.getUUID());
		//车牌号
		sealEnt.setVehicleNo(seal.getCarNumber());
		//封车人
		sealEnt.setSealerCode(seal.getpDAUserEntity().getUserCode());
		//人员entity
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(seal.getpDAUserEntity().getUserCode());
		if(employee == null) {
			sealEnt.setSealerName("");
		}
		//封车人name
		sealEnt.setSealerName(employee.getEmpName());
		//pda机器号
		sealEnt.setPdaDeviceNo(seal.getpDAUserEntity().getPdaCode());
		//封车部门code
		OrgAdministrativeInfoEntity sealOrgSupInfo = querySuperiorOrgByOrgCode(seal.getDepartmentCode());
		if(sealOrgSupInfo != null){
			sealEnt.setSealdOrgCode(sealOrgSupInfo.getCode());
		}else{
			sealEnt.setSealdOrgCode("");
		}
		//封车部门name
		String orgName = getOrgNameByOrgCode(seal.getDepartmentCode());
		//封车部门name
		sealEnt.setSealdOrgName(orgName);
		//车辆任务id
		sealEnt.setTruckTaskDetailId(truckTask.getId());
		//封签类型
		sealEnt.setSealType(SealConstant.SEAL_TYPE_BIND);
		//绑定封签的方式[暂置空]
		sealEnt.setBindType("");
		//封签状态
		sealEnt.setSealState(SealConstant.SEAL_STATE_UNCHECK);
		//操作时间
		sealEnt.setOperateTime(new Date());
		//封车时间
		sealEnt.setSealTime(new Date());

		//封签校验时间
		sealEnt.setCheckTime(null);
		
		//封签检查类型
		sealEnt.setCheckType(null);
		
		//封签明细
		List<SealOrigDetailEntity> sealOrigs = new ArrayList<SealOrigDetailEntity>(seal.getList().size());
		for(SealNumANDStateEntity s : seal.getList()) {
			//封签号
			String sealNo = StringUtils.trim(s.getSealNumber());
			if(StringUtils.isNotEmpty(sealNo)) {
				//出发封签明细
				SealOrigDetailEntity sealOrigDetail = new SealOrigDetailEntity();
				//封签号
				sealOrigDetail.setSealNo(sealNo);
				//封签类型MANA-348 不分侧后门
				sealOrigDetail.setSealType(SealConstant.SEAL_TYPE_DETAIL_NONE);
				//绑定封签方式
				sealOrigDetail.setBindType(s.getSealInputState());
				//封签明细id
				sealOrigDetail.setId(UUIDUtils.getUUID());
				//封签id
				sealOrigDetail.setSealId(sealEnt.getId());
				//封签明细操作时间
				sealOrigDetail.setOperateTime(new Date());
				sealOrigs.add(sealOrigDetail);
			}
		}
		//插入封签,插入封签明细
		 int cnt = vehicleSealDao.insertSeal(sealEnt);
		 int cnt1= vehicleSealDao.insertSealOrigDetail(sealOrigs);
	    //int cnt=(Integer)sealInformationDao.bindSealForExpressDao(sealEnt, sealOrigs);
	    if(cnt>0 && cnt1>0){
	    	return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.SUCCESS,"给力,插入封签明细操作成功!");
	    }else{
	    	return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"失败,插入封签明细操作失败!");
	    }
	}

	/**
	* @description 配合PDA,校验封车封签
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月5日 上午10:07:04
	* @version V1.0
	*/
	@Transactional
	@Override
	public ResponseJsonInfoEntity checkOutSealForExpressService(RequestSealBindANDCheckOutEntity seal) {
		
		//根据车牌号找封签
		SealEntity sealEnt=null;
		String vehicleNo = seal.getCarNumber();
		if(vehicleNo!=null && !"".equals(vehicleNo)){
			sealEnt = getSealByVehicleNo(vehicleNo, false);	
		}
		//封签状态校验
		if(sealEnt == null || StringUtils.equals(sealEnt.getSealType(), SealConstant.SEAL_TYPE_CHECK)) {
			LOGGER.error("封签状态错误, 已被校验.");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"封签状态错误, 已被校验!");
		}
		//组织信息
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(seal.getpDAUserEntity().getDeptCode());
		if(orgAdministrativeInfoEntity == null) {
			LOGGER.error("找不到当前部门");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"找不到当前部门!");
		}
		//当前部门顶级组织
		String checkOrgCode=seal.getDestination();
		orgAdministrativeInfoEntity = querySuperiorOrgByOrgCode(checkOrgCode);
		checkOrgCode = orgAdministrativeInfoEntity.getCode();
		if(StringUtils.equals(sealEnt.getSealdOrgCode(),checkOrgCode)) {
			//ISSUE-4429
			//KDTE-5390转的ISSUE
			//在非到达部门可以校验封签的基础上，新增限制，封签绑定部门不允许校验自己已绑定封签，只允许删除自己已绑封签； 
			LOGGER.error("本部门绑定的封签不能校验");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"本部门绑定的封签不能校验, 如果封签有误请做删除操作!");
		}
		//判断当前封签是否毁坏
		String truckTaskId = sealEnt.getTruckTaskDetailId();
		TruckTaskEntity truckTask = getTruckTaskById(truckTaskId);
		if(truckTask == null) {
			LOGGER.error("找不到相应车辆任务明细, 请刷新后操作.");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"找不到相应车辆任务明细, 请刷新后操作!");
		}
		if(StringUtils.equals(truckTask.getState(), DepartureConstant.ARRIVAL_VEHICLE_CANCLED)) {
			LOGGER.error("该任务已被作废!");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"车辆任务已被作废!");
		}
		
		//车辆状态"在途中"是不能校验封签的（解封签） 2016-06-28
		List<String> truckTaskStateList = sealInformationDao.queryTruckTaskState(vehicleNo);
		if(truckTaskStateList!=null && truckTaskStateList.size()>0){
			LOGGER.error("该车辆任务状态为在途中,是不能封签校验!");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"该车辆任务状态为在途中,是不能封签校验!");
		}
		
		//通过任务id 和目的站查询车辆任务
		TruckTaskDetailEntity truckTaskDetail =  this.qeuryTruckTaskDetailByTaskIdAndDestOrgCode(truckTask.getId(),checkOrgCode,null);
		
		//是否已经到达
		boolean isArrival = true;
		//如果不为空则 当前部门为车辆任务到达部门
		if(truckTaskDetail!=null){
			//车辆任务状态校验
			if(StringUtils.equals(truckTask.getState(), SealConstant.SEAL_TRUCK_STATUS_UNDEPART) 
					|| StringUtils.equals(truckTask.getState(), SealConstant.SEAL_TRUCK_STATUS_ONTHEWAY)
					//必须当前子任务到达后才能校验封签
					&& !StringUtils.equals(truckTaskDetail.getState(),SealConstant.SEAL_TRUCK_STATUS_ARRIVED)
					&& !StringUtils.equals(truckTaskDetail.getState(),SealConstant.SEAL_TRUCK_STATUS_UNLOADED)) {
				//车辆未到达
				isArrival = false;
			}
		}else{
			//为空则当前不是车辆任务到达部门
			//不是当前部门且是营业部则经过当前部门则属于暂存数据类型(因为该车辆之后需要做到达)
			//不经过当前部门, 则说明当前车辆数据两地装车(该类型不需要做到达)，如果是外场则校验不成功
			if(StringUtils.equals(orgAdministrativeInfoEntity.getTransferCenter(),FossConstants.YES)){
				LOGGER.error("非车辆任务到达部门不能校验封签!");
				return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"非车辆任务到达部门不能校验封签!");
			}
	
		}	
		
		//到达封签明细
		List<SealNumANDStateEntity> sealDestDetails = seal.getList();
		List<SealDestDetailEntity> sealDetails = new ArrayList<SealDestDetailEntity>(sealDestDetails.size());
		if(CollectionUtils.isNotEmpty(sealDestDetails)) {
			for(SealNumANDStateEntity sealDestDetail : sealDestDetails) {
				String sealNo = StringUtils.trim(sealDestDetail.getSealNumber());
				if(StringUtils.isNotEmpty(sealNo)) {
					//到达封签明细
					SealDestDetailEntity sealDetail = new SealDestDetailEntity();
					//封签号
					sealDetail.setSealNo(sealNo);
					//封签类型MANA-348 不分侧后门
					sealDetail.setSealType(SealConstant.SEAL_TYPE_DETAIL_NONE);
					//封签校验方式 手输 扫描
					sealDetail.setCheckType(sealDestDetail.getSealInputState());
					//封签明细id
					sealDetail.setId(UUIDUtils.getUUID());
					//封签id
					sealDetail.setSealId(sealEnt.getId());
					//操作时间
					sealDetail.setOperateTime(new Date());
					sealDetails.add(sealDetail);	
				}
			}
		}
		
		List<SealOrigDetailEntity> sealOrigDetails = getSealOrigDetailsBySealId(sealEnt.getId());
		//如果查询出来的绑定封签明细为空, 则说明有bug
		if(CollectionUtils.isEmpty(sealOrigDetails)) {
			LOGGER.error("找不到录入的封签明细");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"找不到录入的封签明细!");
		}
		//获取封签状态
		String sealState = seal.getSealsType();
		if(!StringUtils.equals(SealConstant.SEAL_STATE_DAMAGED, sealState)) {
			//如果为破损就不用再继续校验封签号了
			Boolean isdiff = checkoutSeal(sealOrigDetails, sealDetails);
			//如果有差异
			if(isdiff) {
				sealState = SealConstant.SEAL_STATE_EXCEPTION;
				return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"封签存在异常,请核实!");
			}
		}else{
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"封签存破损,请核实!");
		}
		//车牌号
		sealEnt.setVehicleNo(vehicleNo);
		//封签类型
		if(isArrival) {
			sealEnt.setSealType(SealConstant.SEAL_TYPE_CHECK);
		} else {
			sealEnt.setIsTransientState(FossConstants.YES);
			sealEnt.setSealType(SealConstant.SEAL_TYPE_BIND);
		}
		//校验封签方式[PDA不清楚说,不用管]
		sealEnt.setCheckType("");
		//封签状态
		sealEnt.setSealState(sealState);
		//校验人
		sealEnt.setCheckerCode(seal.getpDAUserEntity().getUserCode());
		//人员信息
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(seal.getpDAUserEntity().getUserCode());
		if(employee == null) {
			LOGGER.error("用户编号错误, 找不到相应用户");
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"用户编号错误, 找不到相应用户!");
		}
		//校验人name
		sealEnt.setCheckerUser(employee.getEmpName());
		//校验部门code
		sealEnt.setCheckOrgCode(checkOrgCode);
		//校验部门name
		String checkOrgName = getOrgNameByOrgCode(checkOrgCode);
		//校验部门name
		sealEnt.setCheckOrgName(checkOrgName);
		//校验时间
		sealEnt.setCheckTime(new Date());
		//操作时间
		sealEnt.setOperateTime(new Date());
		//校验备注
		sealEnt.setCheckOrgMemo("");
		//pda设备号
		sealEnt.setPdaDeviceNo(seal.getpDAUserEntity().getPdaCode());
		//校验类型
		sealEnt.setCheckType(sealEnt.getCheckType());
		//更新封签
		int cnt = vehicleSealDao.updateSeal(sealEnt);
		//插入到达封签明细
		int cnt1 = vehicleSealDao.insertSealDestDetail(sealDetails);
		//int cnt = (Integer)sealInformationDao.checkOutSealForExpressDao(sealEnt, sealDetails);
		if(cnt>0 && cnt1>0){
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.SUCCESS,"给力, 操作成功!");
		}else{
			return new ResponseJsonInfoEntity(ResponseJsonInfoEntity.FAIL,"失败, 插入到达封签明细!");
		}
	}
	
	
	/**
	 * 封签校验操作 
	 * @author foss-Lurker
	 * @date 2016-5-5 下午19:41:49
	 */
	public Boolean checkoutSeal(List<SealOrigDetailEntity> sealOrigDetails, List<SealDestDetailEntity> sealDestDetails) {
		Boolean isdiff = false;
		if(sealDestDetails.size() != sealOrigDetails.size()) {
			//出发到达封签数量不一致
			//true为有差异
			return true;
		}
		for(SealDestDetailEntity sealDestDetail : sealDestDetails) {
			String destSealNo = sealDestDetail.getSealNo();
			for(SealOrigDetailEntity sealOrigDetail : sealOrigDetails) {
				//如果当前封签尚未校验
				if(!sealOrigDetail.getInspected()) {
					String origSealNo = sealOrigDetail.getSealNo();
					//如果封签类型相同并且封签号相同跳出当前循环, 继续校验下一个封签号
					if(StringUtils.equals(destSealNo, origSealNo)) {
						sealOrigDetail.setInspected(true);
						sealDestDetail.setIsdiff(false);
						sealOrigDetail.setIsdiff(false);
						break;
					}
				}
			}
		}
		for(SealDestDetailEntity sealDestDetail : sealDestDetails) {
			if(sealDestDetail.getIsdiff()) {
				isdiff = true;
				break;
			}
		}
		return isdiff;
	}
	
	/**
	 * 根据SealId查询出装车封签 
	 * @author foss-liping
	 * @date 2016-5-5 下午19:17:23
	 */
	public List<SealOrigDetailEntity> getSealOrigDetailsBySealId(String sealId) {
		//根据SealId查询出装车封签 
		return vehicleSealDao.getSealOrigDetailsBySealId(sealId);
	}

	
	/**
	 * 通过任务id 和目的站查询车辆任务
	 * @author 106162
	 * @date 2016年5月5日 18:32:18
	 * @param id
	 * @param currentDeptCode
	 * @param isStatus 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleSealService#qeuryTruckTaskDetailByTaskIdAndDestOrgCode(java.lang.String, java.lang.String, java.lang.String)
	 **/
	public TruckTaskDetailEntity qeuryTruckTaskDetailByTaskIdAndDestOrgCode(String id,String currentDeptCode,String isStatus) {
		return vehicleSealDao.qeuryTruckTaskDetailByTaskIdAndDestOrgCode(id,currentDeptCode,isStatus);
	}
	
	/**
	 * 
	 * 根据车辆任务ID获取车辆任务实体(包括已作废的) 
	 * @author foss-liping
	 * @date 2016-5-5 下午18:49:59
	 * @param id
	 * @return
	 * @see
	 */
	public TruckTaskEntity getTruckTaskById(String id) {
		//包括已作废的
		return vehicleSealDao.getTruckTaskByIdCancled(id);
	}
	
	
	/**
	 * 根据车牌号查询绑定状态的封签
	 * @author 106162-foss-liping
	 * @date 2016-5-5 下午6:22:49
	 */
	private SealEntity getSealByVehicleNo(String vehicleNo, Boolean flag) {
		//根据车牌号查询绑定的封签
		SealEntity seal = vehicleSealDao.getSealByVehicleNo(vehicleNo);
		if(flag && seal == null) {
			LOGGER.error("根据车牌号, 查询不到相应的封签记录");
			throw new TfrBusinessException("根据车牌号, 查询不到相应的封签记录", "");
		}
		return seal;
	}

	/**
	 * @Title: querySuperiorOrgByOrgCode 
	 * @Description: 根据部门code找顶级组织 
	 * @param orgCode
	 * @return    
	 * @return OrgAdministrativeInfoEntity    返回类型 
	 * querySuperiorOrgByOrgCode
	 * @author: 106162-foss-liping
	 * @throws 
	 * Date:2016-5-04下午4:46:59
	 */
	public OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode) {
		//根据当前部门编码查询保安组信息
		SecurityTfrMotorcadeEntity securityTfrMotorcadeEntity = new SecurityTfrMotorcadeEntity();
		securityTfrMotorcadeEntity.setSecurityCode(orgCode);
		securityTfrMotorcadeEntity.setActive("Y");
		List<SecurityTfrMotorcadeEntity> securityTfrMotorcadeEntityList = securityTfrMotorcadeService.querySecurityTfrMotorcadeListBySecurityCode(securityTfrMotorcadeEntity, LoadConstants.SONAR_NUMBER_10, 0);
		//如果是保安组，以保安组服务外场替换当前部门
		if(null != securityTfrMotorcadeEntityList && securityTfrMotorcadeEntityList.size() > 0 ){
			//获取保安组服务外场
			String transCenterCode = securityTfrMotorcadeEntityList.get(0).getTranscenterCode();
			if(StringUtils.isBlank(transCenterCode)){
				LOGGER.error("当前保安组服务外场未找到。");
				throw new TfrBusinessException("当前保安组服务外场未找到。", "");
			}else{
				orgCode = transCenterCode;
			}
		}
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//空运总调类型
		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
		//营业部（派送部）
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		//查询上级部门                                                                   
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			
			OrgAdministrativeInfoEntity fleet = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
			if(fleet!=null){
				MotorcadeEntity motorcadeEntity= motorcadeService.queryMotorcadeByCodeClean(fleet.getCode());
				//若查询出的上级车队是顶级车队
				if(motorcadeEntity !=null&&FossConstants.YES.equals(motorcadeEntity.getIsTopFleet())
						&& StringUtils.isNotEmpty(motorcadeEntity.getTransferCenter())){
					
					OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(motorcadeEntity.getTransferCenter());
					if(orgEntity!=null&&StringUtils.equals(orgEntity.getTransferCenter(), FossConstants.YES)){
						return orgEntity;
					}else{
						//获取上级部门失败
						LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
						return null;
					}
				}else{
					//获取上级部门失败
					LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
					return null;
				}
				
			}else{
				//获取上级部门失败
				LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
				return null;	
			}

		}
	}

	/**
	 * 根据封签号, 出发部门, 到达部门.获取车辆任务明细信息
	 * @author 106162-foss-liping
	 * @date 2016-05-04 上午11:02:32
	 */
	private TruckTaskDetailEntity getTruckTaskDetail(String vehicleNo, String origOrgCode, String destOrgCode) {
		//封签dto
		SealDto sealDto = new SealDto();
		//车牌号
		sealDto.setVehicleNo(vehicleNo);
		//出发部门
		sealDto.setOrigOrgCode(origOrgCode);
		//到达部门
		sealDto.setDestOrgCode(destOrgCode);
		//任务车辆明细
		List<TruckTaskDetailEntity> truckTaskDetails = vehicleSealDao.getTruckTaskDetailsByVehicleNo(sealDto);
		if(truckTaskDetails.size() == 0) {
			return null;
		}
		return truckTaskDetails.get(0);
	}
	
	/**
	 * 根据车辆任务id找封签
	 * 车辆出发确认有调用, 传过来的封签类型为BIND
	 * @Title: querySealByTruckTaskId 
	 * @param seal
	 * @return    
	 * @return List<SealEntity>    返回类型 
	 * querySealByTruckTaskId
	 * @author: 106162-foss-liping
	 * @throws 
	 *Date:2016-5-4上午9:56:44
	 */
	public List<SealEntity> querySealByTruckTaskId(SealEntity seal) {
		if(seal == null) {
			LOGGER.error("参数错误.");
			throw new TfrBusinessException("参数错误.", "");
		}
		if(!StringUtils.equals(seal.getSealType(), SealConstant.SEAL_TYPE_BIND) && !StringUtils.equals(seal.getSealType(), SealConstant.SEAL_TYPE_CHECK)) {
			LOGGER.error("参数错误.");
			throw new TfrBusinessException("参数错误, 只接受封签类型为已绑定, 已校验的类型", "");
		}
		if(StringUtils.isEmpty(seal.getTruckTaskDetailId())) {
			LOGGER.error("参数错误.");
			throw new TfrBusinessException("参数错误, 车辆任务ID为空", "");
		}
		return vehicleSealDao.querySealByTruckTaskId(seal);
	}
	
	
	/**
	 * @Title: querySealOrigDetailsBySealNo 
	 * @Description: 根据封签号查询出所有的出发封签
	 * @param sealNo
	 * @return    
	 * @return List<SealOrigDetailEntity>    返回类型 
	 * querySealOrigDetailsBySealNo
	 * @author: 106162-foss-liping
	 * @throws 
	 * Date:2016-5-4上午9:56:44
	 */
	public List<SealOrigDetailEntity> querySealOrigDetailsBySealNo(String sealNo) {
		return vehicleSealDao.querySealOrigDetailsBySealNo(sealNo);
	}
	
	
	/**
	 * 根据部门号获取部门名称
	 * 
	 * @Title: getOrgNameByOrgCode 
	 *
	 * @Description: 根据部门号获取部门名称
	 * 
	 * @param orgCode
	 * 
	 * @return    设定文件
	 *  
	 * @return String    返回类型
	 *  
	 * @see getOrgNameByOrgCode
	 * 
	 * @author: 106162-foss-liping
	 * 
	 * @throws
	 *  
	 * Date:2016-5-4上午9:56:44
	 */
	private String getOrgNameByOrgCode(String orgCode) {
		//根据部门号获取部门名称
		return vehicleSealDao.getOrgNameByOrgCode(orgCode);
	}
	
	
	/**
	* @description 配合(快递)校验校验临时租车标记
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	@Override
	public Object tempLeaseCarStateService(String handOverBillNo) {
		String logo = this.handOverBillService.queryHoldingState(handOverBillNo);
		return logo;
	}


	/**
	* @description 配合(快递)校验校验临时租车标记
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#queryWaybillNumByPositiveNum(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	@Override
	public List<String> queryWaybillNumByPositiveNum(String positiveNum) {
		List<String> waybillNoList = pointsSingleJointTicketDao.queryWaybillNoList(positiveNum);
		return waybillNoList;
	}
	
	
	/**
	* @description 查询车牌号是否正确(是不是外请车和公司自有车)
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	@Override
	public String isDOVehicleNo(String vehicleNo) {
		return sealInformationDao.isDoQueryVehicleNo(vehicleNo);
	}
	
	/**
	 * @param  VehicleNum
	 * @note   根据PDA传过来的车牌号,判断当前车是否已绑定封签
	 * @author 106162-gis-liping
	 * @date 2016-11-24 下午4:05:21
	 */
	@Override
	public List<String> checkSealForCar(String vehicleNum) {
		List<String> list = sealInformationDao.checkSealForCarInfo(vehicleNum);
		return list;
	}
    
	/**
	 * get...set...
	 */
	public ISealInformationDao getSealInformationDao() {
		return sealInformationDao;
	}
	
	public void setSealInformationDao(ISealInformationDao sealInformationDao) {
		this.sealInformationDao = sealInformationDao;
	}

    /**
     * 获取车辆信息服务接口
     * @return
     */
	public IVehicleService getVehicleService() {
		return vehicleService;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
	/**
	 * 当前组织信息
	 * @return
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 保安组信息securityTfrMotorcadeService
	 */
	public ISecurityTfrMotorcadeService getSecurityTfrMotorcadeService() {
		return securityTfrMotorcadeService;
	}

	public void setSecurityTfrMotorcadeService(
			ISecurityTfrMotorcadeService securityTfrMotorcadeService) {
		this.securityTfrMotorcadeService = securityTfrMotorcadeService;
	}
	/**
	 *  综合管理 组织信息 Service
	 */
	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	/**
	 * 获取车队 Service接口
	 */
	public IMotorcadeService getMotorcadeService() {
		return motorcadeService;
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}
	/***
	 * 封签Dao
	 * 绑定封签
	 * 校验封签 
	 */
	public IVehicleSealDao getVehicleSealDao() {
		return vehicleSealDao;
	}

	public void setVehicleSealDao(IVehicleSealDao vehicleSealDao) {
		this.vehicleSealDao = vehicleSealDao;
	}
	
	/****
	 * 人员service
	 */
	public IEmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public IHandOverBillService getHandOverBillService() {
		return handOverBillService;
	}

	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}

	public IAirWaybillDetailDao getPointsSingleJointTicketDao() {
		return pointsSingleJointTicketDao;
	}

	public void setPointsSingleJointTicketDao(IAirWaybillDetailDao pointsSingleJointTicketDao) {
		this.pointsSingleJointTicketDao = pointsSingleJointTicketDao;
	}

	public ITfrNotifyService getTfrNotifyService() {
		return tfrNotifyService;
	}

	public void setTfrNotifyService(ITfrNotifyService tfrNotifyService) {
		this.tfrNotifyService = tfrNotifyService;
	}

}
