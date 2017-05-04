package com.deppon.foss.module.pickup.order.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAutoScheduleManageService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AutoScheduleManageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoDispathchOrderService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderBusinessLockService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.OrderMutexElementConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderMutexElement;
import com.deppon.foss.util.define.FossConstants;

public class AutoDispathchOrderService implements IAutoDispathchOrderService {
	private AutoOrderHandleService autoOrderHandleService;
	private IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IAutoScheduleManageService autoScheduleManageService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IMotorcadeService motorcadeService;
	private IOrderBusinessLockService orderBusinessLockService;
	private IConfigurationParamsService configurationParamsService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressOrderTaskHandleService.class);
	/**
	 * 加入线程池
	 * @param changeList
	 */
	@Transactional
	@Override
	public void aotoDispatchOrderList(List<DispatchOrderChangeEntity> changeList){
		long begin = System.currentTimeMillis();
		// 获得待处理的订单TODO异常处理
		List<DispatchOrderEntity> orderList = dispatchOrderChangeEntityDao.queryOrder(changeList);
		LOGGER.info("自动调度获取待处理订单,共耗时:{}ms", System.currentTimeMillis() - begin);
		//获取订单业务锁自动释放时间
		String orderLockTtl = configurationParamsService.queryConfValueByCode(DispatchOrderStatusConstants.ORDER_LOCK_TTL);
		//将每一条订单都放入线程池
		for (DispatchOrderEntity dispatchOrderEntity : orderList) {
			long begin1 = System.currentTimeMillis();
			OrderMutexElement mutexElement = null;
			if (StringUtils.isBlank(orderLockTtl)) {
				mutexElement = new OrderMutexElement(dispatchOrderEntity.getOrderNo(), "订单更新_自动调度", 
						OrderMutexElementConstants.DISPATCHORDERNO_LOCK);
	     		LOGGER.info("自动受理订单"+dispatchOrderEntity.getOrderNo()+"时订单业务锁自动释放时间采用默认值");
			} else {
				mutexElement = new OrderMutexElement(dispatchOrderEntity.getOrderNo(), "订单更新_自动调度",
						OrderMutexElementConstants.DISPATCHORDERNO_LOCK,Integer.valueOf(orderLockTtl));
			}
			//互斥锁定
	     	boolean isLocked = orderBusinessLockService.lock(mutexElement, DispatchOrderStatusConstants.ZERO);
	     	LOGGER.info("自动调度获取业务锁,共耗时:{}ms", System.currentTimeMillis() - begin1);
	     	//如果订单已经锁定直接走预处理建议
	     	if(!isLocked){
	     		dispatchOrderChangeJobId(dispatchOrderEntity);
	     		LOGGER.info("自动受理订单"+dispatchOrderEntity.getOrderNo()+"时订单已锁定");
	     		continue;
	     	}
	     	try {
	     		long begin2 = System.currentTimeMillis();
				aotoDispatchOrder(dispatchOrderEntity);
				LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+",共耗时:{}ms", System.currentTimeMillis() - begin2);
			} catch (Exception e) {
				long begin3 = System.currentTimeMillis();
				dispatchOrderChangeJobId(dispatchOrderEntity);
				LOGGER.info("预处理订单"+dispatchOrderEntity.getOrderNo()+",共耗时:{}ms", System.currentTimeMillis() - begin3);
				e.printStackTrace(); 
			}finally{
				orderBusinessLockService.unlock(mutexElement);
				LOGGER.info("自动受理订单"+dispatchOrderEntity.getOrderNo()+"结束");
			}
		}
	}
	
	/**
	 * 处理单条预处理订单,加入线程池
	 * todo最好用一个service去处理
	 * @param dispatchOrderEntity
	 */
	private void aotoDispatchOrder(DispatchOrderEntity dispatchOrderEntity) {
		long begin = System.currentTimeMillis();
		//调用综合车队开关 若车队开关开 则执行
		String deptCode = dispatchOrderEntity.getFleetCode();//获取的部门code
		OrgAdministrativeInfoEntity  motorcadeOrg = null;//有开关车队	
		//车队
		OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(deptCode);
		if(null==orgInfo){//不存在部门
			dispatchOrderChangeJobId(dispatchOrderEntity);//未开启开关标记
			return;//不处理
		}
		//获取开关
		AutoScheduleManageEntity autoScheduleManageEntity= autoScheduleManageService.queryAutoScheduleManageBydeptCode(deptCode,FossConstants.YES);
		if(null!=autoScheduleManageEntity&&matchAutoSchedule(dispatchOrderEntity,autoScheduleManageEntity)){//存在开关并且在开关开启范围内
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"获取车队开关"+dispatchOrderEntity.getOrderNo()+",共耗时:{}ms", System.currentTimeMillis() - begin);
			autoOrderHandleService.pushThreadsPool(dispatchOrderEntity);
			return;
		}else if(StringUtils.equals(FossConstants.NO,orgInfo.getTransDepartment())){//不是车队，查询所属车队
			motorcadeOrg= orgAdministrativeInfoComplexService.getOrgAdministrativeInfoMotorcadeByCode(deptCode);//车队
		}else{
			motorcadeOrg=orgInfo;
		}
		if(null==motorcadeOrg){
			dispatchOrderChangeJobId(dispatchOrderEntity);//未开启开关标记
			return;//不处理
		}else{
			deptCode= motorcadeOrg.getCode();//车队CODE
		}
		//车队组织对应的车队实体
		MotorcadeEntity motorcadeEntity= motorcadeService.queryMotorcadeByCodeClean(deptCode);
		String topFleetCode = null;//顶级车队编码
		//zxy 20140723 AUTO-197 修改:增加motorcadeEntity空检验
		if(motorcadeEntity != null && FossConstants.YES.equals(motorcadeEntity.getIsTopFleet())){
			topFleetCode = deptCode;
		}else {
			OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(deptCode);//顶级车队
			if(null!=topFleet){
				topFleetCode = topFleet.getCode();
			}
		}
		if(null==topFleetCode){ //不存在顶级车队，说明没有对应车队
			dispatchOrderChangeJobId(dispatchOrderEntity);//未开启开关标记
			return;
		}
		while (true){//从下向上查询有开关的车队
			if(StringUtils.isEmpty(deptCode)){//不存在车队
				dispatchOrderChangeJobId(dispatchOrderEntity);//未开启开关标记			
				return;
			}
			//获取车队锁
			autoScheduleManageEntity= autoScheduleManageService.queryAutoScheduleManageBydeptCode(deptCode,FossConstants.YES);
			if(null!=autoScheduleManageEntity&&matchAutoSchedule(dispatchOrderEntity,autoScheduleManageEntity)){//存在锁并且在开关开启范围内
				LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"获取车队开关"+dispatchOrderEntity.getOrderNo()+",共耗时:{}ms", System.currentTimeMillis() - begin);
				autoOrderHandleService.pushThreadsPool(dispatchOrderEntity);
				return;
			}else if(deptCode.equals(topFleetCode)){//是顶级车队，停止向上查找
				dispatchOrderChangeJobId(dispatchOrderEntity);//未开启开关标记	
				return;		
			}else{									
				//查询上级组织
				List<OrgAdministrativeInfoEntity> entitys = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpDown(deptCode,true);
				if(null!=entitys&&entitys.size()>0){//存在上级部门
					deptCode = entitys.get(0).getCode();
				}else{//没有上级部门
					dispatchOrderChangeJobId(dispatchOrderEntity);//未开启开关标记	
					return;				
				}
			}
		}
	}
	
	/**
	 * 订单在开关时间范围内
	 * @param dispatchOrderEntity
	 * @param autoScheduleManageEntity
	 * @return
	 */
	private boolean matchAutoSchedule(DispatchOrderEntity dispatchOrderEntity,
			AutoScheduleManageEntity autoScheduleManageEntity) {
		//如果在开启范围加入线程池
		Date orderTime = dispatchOrderEntity.getOrderVehicleTime();
		if(null==orderTime){
			return false;
		}
		String startTimeStr = autoScheduleManageEntity.getStartTime();
		String endTimeStr = autoScheduleManageEntity.getEndTime();
        SimpleDateFormat hmsFmt  =   new  SimpleDateFormat( "HH:mm" ); 
        SimpleDateFormat dateFmt  =   new  SimpleDateFormat( "yyyy-MM-dd HH:mm" ); 
        String dateStr = "2014-01-01 ";
        Date startTime = null;
        Date endTime = null;
        try {
        	String orderTimeStr = hmsFmt.format( orderTime); 
			startTime = dateFmt.parse( dateStr+startTimeStr); //order时间
			endTime = dateFmt.parse( dateStr+endTimeStr);//开关时间
			orderTime = dateFmt.parse( dateStr+orderTimeStr);//开关时间
			if(orderTime.before(endTime)&&!orderTime.before(startTime)){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return false;
	}
	
	/**
	 * 未开启开关标记
	 * @param dispatchOrderEntity
	 */
	private void dispatchOrderChangeJobId(
			DispatchOrderEntity dispatchOrderEntity) {
		//更新状态B执行原来job
		//标记B关闭状态
		DispatchOrderChangeEntity dispatchOrderChangeEntity = new DispatchOrderChangeEntity();
		dispatchOrderChangeEntity.setChangeId(dispatchOrderEntity.getId());
		dispatchOrderChangeEntity.setJobId("B");//todo B的值
		dispatchOrderChangeEntityDao.updateChangebyOrderId(dispatchOrderChangeEntity);
	}
	
	public void setAutoOrderHandleService(
			AutoOrderHandleService autoOrderHandleService) {
		this.autoOrderHandleService = autoOrderHandleService;
	}

	public void setDispatchOrderChangeEntityDao(
			IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao) {
		this.dispatchOrderChangeEntityDao = dispatchOrderChangeEntityDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setAutoScheduleManageService(
			IAutoScheduleManageService autoScheduleManageService) {
		this.autoScheduleManageService = autoScheduleManageService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	public void setOrderBusinessLockService(
			IOrderBusinessLockService orderBusinessLockService) {
		this.orderBusinessLockService = orderBusinessLockService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
}
