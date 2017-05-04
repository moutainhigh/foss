package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IExpressWorkerStatusDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IExpressWorkerStatusLogDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IExpressWorkerStatusService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderExpressService;
import com.deppon.foss.module.pickup.order.api.shared.define.ExpressWorkerStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerBillCountDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerCompleteDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerStatusDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderQueryHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.WorkerbillCountQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.vo.ExpressWorerStatusConditionVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

public class ExpressWorkerStatusService implements IExpressWorkerStatusService {

	private IExpressWorkerStatusDao expressWorkerStatusDao;
	private IExpressWorkerStatusLogDao expressWorkerStatusLogDao; 
	private IEmployeeService employeeService;
	private IPdaSignEntityDao pdaSignEntityDao;
	private IOrderExpressService orderExpressService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressWorkerStatusService.class);
	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：查询快递员工作状态结果
	 * @date:2014-4-24 下午2:29:38
	 */
	@Override
	public List<ExpressWorkerCompleteDto> queryExpressWorkerComplete(
			ExpressWorerStatusConditionVo vo) {
		//设置基本查询条件
		WorkerbillCountQueryDto dto = new WorkerbillCountQueryDto();
		dto.setStartDate(vo.getStartDate());
		dto.setEndDate(vo.getEndDate());
		//14.10.20 gcl 去掉 暂时没用
//		ArrayList<String> productCode = new ArrayList<String>();
//		productCode.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
//		dto.setProductCode(productCode);
		//定义查询签到快递DTO
		CourierQueryDto courierQueryDto = new CourierQueryDto();
		//进行赋值
		courierQueryDto.setCourierCode(vo.getExpressWorkerCode());
		//14.9.9 gcl 默认查询用户机构所属上级机构大区下所有点部
		if(CollectionUtils.isNotEmpty(vo.getOperateBigRegionCode())&&CollectionUtils.isEmpty(vo.getBigRegionCode())
				&&CollectionUtils.isEmpty(vo.getSmallRegionCode())
				&&StringUtils.isEmpty(vo.getExpressWorkerCode())){
			List<OrgAdministrativeInfoEntity> elist=orgAdministrativeInfoComplexService.queryOrgAdminExpressPartsBybig(vo.getOperateBigRegionCode().get(0));
		    List<String> list = new ArrayList<String>();
		    for ( OrgAdministrativeInfoEntity entity : elist ){
		    	list.add(entity.getCode());
		    }
		    courierQueryDto.setPartCodes(list);
		}else{
			courierQueryDto.setBigZoneCodes(vo.getBigRegionCode());
		}
		courierQueryDto.setSmallZoneCodes(vo.getSmallRegionCode());
		//先取得当前登录的调度所属行政区域集合
//		List<String> districtCodes = new ArrayList<String>();
		OrderQueryHandleDto orderQueryHandleDto = orderExpressService.setOrderQueryHandleDto(null);
		//zxy 20140703 AUTO-80 start 修改:去掉 districtCodes为空判断
		//如果不存在数据，则返回空
		if(null == orderQueryHandleDto){
			return null;
		}	
		//zxy 20140703 AUTO-80 end 修改:去掉 districtCodes为空判断
		//取得对应行政区域
		List<String> expressOrderNewCountyCodes = orderQueryHandleDto.getExpressOrderNewCountyCodes();
		List<String> expressOrderCountyCodes = orderQueryHandleDto.getExpressOrderCountyCodes();
		//如果不存在数据，则返回空
		if(CollectionUtils.isEmpty(expressOrderNewCountyCodes)
				&& CollectionUtils.isEmpty(expressOrderCountyCodes)){
			return null;
		}
		courierQueryDto.setExpressOrderCountyCodes(expressOrderCountyCodes);
		courierQueryDto.setExpressOrderNewCountyCodes(expressOrderNewCountyCodes);
		// 设置司机接收状态 10.8 gcl
		if(PdaSignStatusConstants.DRIVER_RECEIVE_ALL.equals(vo.getRecieveOrderStatus())){
			courierQueryDto.setRecieveOrderStatus(null);
		}else if(PdaSignStatusConstants.DRIVER_RECEIVE_OPEN.equals(vo.getRecieveOrderStatus())){
			courierQueryDto.setRecieveOrderStatus(PdaSignStatusConstants.DRIVER_RECEIVE_OPEN);
		}else if(PdaSignStatusConstants.DRIVER_RECEIVE_STOP.equals(vo.getRecieveOrderStatus())){
			courierQueryDto.setRecieveOrderStatus(PdaSignStatusConstants.DRIVER_RECEIVE_STOP);
		}
		//取得快递员签到返回结果集
		List<CourierSignDto> courierSignList = pdaSignEntityDao.queryExpressDriverBySignDto(courierQueryDto);
		List<ExpressWorkerCompleteDto> resultList = new ArrayList<ExpressWorkerCompleteDto>();
		if(CollectionUtils.isNotEmpty(courierSignList)){
			// 循环得到对应存在的快递员工号
			List<CourierSignDto> courierSignDtoList = new ArrayList<CourierSignDto>();
			for(CourierSignDto courierSignDto :courierSignList){
				if(null != courierSignDto){
					courierSignDtoList.add(courierSignDto);
				}
			}
			//循环遍历出每个集合中存在的工号
			for(CourierSignDto courierSignDto :courierSignList){
				dto.setCourierSignDtoList(courierSignDtoList);
				dto.setBigZoneName(courierSignDto.getBigZoneName());
				dto.setSmallZoneName(courierSignDto.getSmallZoneName());
				//zxy 20140703 AUTO-98 start 修改:重复累加数据问题
				ExpressWorkerCompleteDto expressWorkerCompleteDto = bulidExpressWorkerCompleteDto(dto,courierSignDto);
				resultList.add(expressWorkerCompleteDto);
//				CollectionUtils.addAll(resultList, tmplist.iterator());
				//zxy 20140703 AUTO-98 end 修改:重复累加数据问题
			}
		}
		return resultList;
	}
    /**
     * 
     * @author:lianghaisheng
     * @Description：开启接口
     * @date:2014-4-24 下午2:31:32
     */
	@Override
	@Transactional (propagation = Propagation.REQUIRED)
	public int openExpressWorker(ExpressWorkerStatusDto dto) {
		LOGGER.info("openExpressWorker start : "
				+ ReflectionToStringBuilder.toString(dto));
		if(CollectionUtils.isEmpty(dto.getExpressEmpCodes())){
			throw new DispatchException("开启或者暂停的工号不能为空！");
		}
		if(!ExpressWorkerStatusConstants.PDA_SYSTEM.equals(dto.getOperateSystem())
				&&!ExpressWorkerStatusConstants.FOSS_SYSTEM.equals(dto.getOperateSystem())){
			throw new DispatchException("发起系统不合法！");
		}
		//zxy 20140707 内部优化  start 修改:删除无用字段
//		if(!ExpressWorkerStatusConstants.EXPRESS_BUSINESS.equals(dto.getBusinessArea())&&
//				!ExpressWorkerStatusConstants.LAND_LOAD_BUSINESS.equals(dto.getBusinessArea())){
//			throw new DispatchException("传入的员工类型既不是快递也不是零担！");
//		}
		//zxy 20140707 内部优化 end 修改:删除无用字段
		
		//剔除重复的员工编号
		List<String> empCodes = dto.getExpressEmpCodes();		
		Set<String> empCodesSet = new HashSet<String>();
		CollectionUtils.addAll(empCodesSet, empCodes.toArray());
		empCodes.removeAll(empCodes);
		CollectionUtils.addAll(empCodes, empCodesSet.iterator());
		dto.setExpressEmpCodes(empCodes);
		List<CourierSignDto> courierQueryList = new ArrayList<CourierSignDto>();
		if(CollectionUtils.isNotEmpty(dto.getExpressEmpCodes())){
			for(String courierCode : dto.getExpressEmpCodes()){
				CourierSignDto queryCourier = new CourierSignDto();
				queryCourier.setCourierCode(courierCode);
				courierQueryList.add(queryCourier);
			}
			
		}
		//获取需要操作的快递员工作状态集合
		ExpressWorkerStatusEntity queryWorkerStatusEntity = new ExpressWorkerStatusEntity();
		queryWorkerStatusEntity.setActive("Y");
		//zxy 20140709 AUTO-112 修改:只查询Y的数据
		List<ExpressWorkerStatusEntity> expressWorkerstatusEntitys = expressWorkerStatusDao.selectByEmployeeCodesByEntity(queryWorkerStatusEntity, courierQueryList);
		ExpressWorkerStatusEntity expressWorkerStatusEntity =null;
		int changeRows = 0;
	    //如果是PDA开启或者关闭则只能操作一条
		if(ExpressWorkerStatusConstants.PDA_SYSTEM.equals(dto.getOperateSystem())){
			//校验开启或者关闭的工号是否大于1个
			if(dto.getExpressEmpCodes().size()>1){
				throw new DispatchException("开启的工号过多！");	
			}	
			//新增快递员工作状态
			if(CollectionUtils.isEmpty(expressWorkerstatusEntitys)){
				PdaSignEntity pdaSignParam = new PdaSignEntity("", dto.getExpressEmpCodes().get(0), "", PdaSignStatusConstants.BUNDLE);
				PdaSignEntity pdaSignEntity = pdaSignEntityDao.querySignEntityByDV(pdaSignParam);
				if(pdaSignEntity == null)
					throw new DispatchException("工号:" + dto.getExpressEmpCodes().get(0) + "未签到！");	
				expressWorkerStatusEntity = new ExpressWorkerStatusEntity();
				//构建实体
				buildWorkerStatusEntity(expressWorkerStatusEntity,dto,pdaSignEntity);
				//插入数据库中
				changeRows = expressWorkerStatusDao.insertSelective(expressWorkerStatusEntity);
				//将日志的状态修改为新增状态
				dto.setOperateType(ExpressWorkerStatusConstants.ADD_STATUS);
			}else {
				//更新实体
				expressWorkerStatusEntity = expressWorkerstatusEntitys.get(0);
				//14.7.25 gcl AUTO-201
				PdaSignEntity pdaSignParam = new PdaSignEntity("", dto.getExpressEmpCodes().get(0), "", PdaSignStatusConstants.BUNDLE);
				PdaSignEntity pdaSignEntity = pdaSignEntityDao.querySignEntityByDV(pdaSignParam);
				if(pdaSignEntity !=null){
					if(PdaSignStatusConstants.USER_TYPE_DRIVER.equals(pdaSignEntity.getUserType())){
						//零担 查询车辆的工作状况
						ExpressWorkerStatusEntity queryEntity = new ExpressWorkerStatusEntity();
						queryEntity.setActive(FossConstants.YES);
						queryEntity.setVehicleNo(pdaSignEntity.getVehicleNo());
						queryEntity.setBusinessArea(ExpressWorkerStatusConstants.ORDER_BUSINESS);
						queryEntity=expressWorkerStatusDao.queryOneByVehicleNo(queryEntity);
						if (queryEntity ==null || queryEntity.getWorkStatus() == null) {
							throw new DispatchException("没有可用的车辆工作状态！");						
						}
						if(ExpressWorkerStatusConstants.OPEN_STATUS.
								equals(queryEntity.getWorkStatus())){
							throw new DispatchException("车牌号为："+queryEntity.getVehicleNo()+"的状态已经为开启状态！"); //zxy 20140708 AUTO-112 修改:提示内容修改
						}
						queryEntity.setModifyTime(new Date());
						queryEntity.setModifyCode(dto.getCreatorCode());
						queryEntity.setWorkStatus(dto.getOperateType());
						changeRows = expressWorkerStatusDao
								.updateByPrimaryKeySelective(queryEntity);
					} else {
						if(ExpressWorkerStatusConstants.OPEN_STATUS.
								equals(expressWorkerStatusEntity.getWorkStatus())){
							throw new DispatchException("员工工号为："+expressWorkerStatusEntity.getEmpCode()+"的状态已经为开启状态！");
						}
						expressWorkerStatusEntity.setModifyTime(new Date());
						expressWorkerStatusEntity.setModifyCode(dto.getCreatorCode());
						expressWorkerStatusEntity.setWorkStatus(dto.getOperateType());
						changeRows = expressWorkerStatusDao.updateByPrimaryKeySelective(expressWorkerStatusEntity);
					}
				}else{
					throw new DispatchException("员工工号为："+expressWorkerStatusEntity.getEmpCode()+"无签到！");
				}
			}
		}else if(ExpressWorkerStatusConstants.FOSS_SYSTEM.equals(dto.getOperateSystem())){
			//校验是否正确
		    //将员工转化为map
			Map<String,ExpressWorkerStatusEntity> ExpressWorkermap = CollectionUtils.
					asMap(expressWorkerstatusEntitys, String.class, ExpressWorkerStatusEntity.class, "getEmpCode");
			
			//遍历数据进行校验 
			for(String empCode :dto.getExpressEmpCodes()){
				if(ExpressWorkermap.containsKey(empCode)){
					expressWorkerStatusEntity = ExpressWorkermap.get(empCode);
					//判断是否已经开启
					if(ExpressWorkerStatusConstants.OPEN_STATUS.
							equals(expressWorkerStatusEntity.getWorkStatus())){
						throw new DispatchException("员工工号为："+empCode+"的状态已经为开启状态！");
					}
				}else {
					throw new DispatchException("员工工号为："+empCode+"没有在PDA登陆记录！");
				}
			}
		    //更新快递员状态
			changeRows = expressWorkerStatusDao.updateByEmployeeCodes(dto);			
		}
		//添加日志
		addExpressWorkerLog(dto);
		//更新状态
		return changeRows;
	}
    /**
     * 
     * @author:lianghaisheng
     * @Description：关闭接口
     * @date:2014-4-24 下午2:32:06
     * liding mod for NCI 2016-05-11
     * 添加用户类型参数
     */
	@Override
	public int stopExrpessWorker(ExpressWorkerStatusDto dto,String userType) {
		LOGGER.info("stopExrpessWorker start : "
				+ ReflectionToStringBuilder.toString(dto));
		if(CollectionUtils.isEmpty(dto.getExpressEmpCodes())){
			throw new DispatchException("开启或者暂停的工号不能为空！");
		}
		if(!ExpressWorkerStatusConstants.PDA_SYSTEM.equals(dto.getOperateSystem())
				&&!ExpressWorkerStatusConstants.FOSS_SYSTEM.equals(dto.getOperateSystem())){
			throw new DispatchException("发起系统不合法！");
		}
		//zxy 20140707 内部优化 start 修改:删除无用字段
//		if(!ExpressWorkerStatusConstants.EXPRESS_BUSINESS.equals(dto.getBusinessArea())&&
//				!ExpressWorkerStatusConstants.LAND_LOAD_BUSINESS.equals(dto.getBusinessArea())){
//			throw new DispatchException("传入的员工类型既不是快递也不是零担！");
//		}
		//zxy 20140707 内部优化  end 修改:删除无用字段
		//剔除重复的员工编号
		List<String> empCodes = dto.getExpressEmpCodes();
		Set<String> empCodesSet = new HashSet<String>();
		CollectionUtils.addAll(empCodesSet, empCodes.toArray());
		empCodes.removeAll(empCodes);
		CollectionUtils.addAll(empCodes, empCodesSet.iterator());
		dto.setExpressEmpCodes(empCodes);
		//设置查询快递
		List<CourierSignDto> courierQueryList = new ArrayList<CourierSignDto>();
		if(CollectionUtils.isNotEmpty(dto.getExpressEmpCodes())){
			for(String courierCode : dto.getExpressEmpCodes()){
				CourierSignDto queryCourier = new CourierSignDto();
				queryCourier.setCourierCode(courierCode);
				courierQueryList.add(queryCourier);
			}
			
		}
		//获取需要操作的快递员工作状态集合
		ExpressWorkerStatusEntity queryWorkerStatusEntity = new ExpressWorkerStatusEntity();
		queryWorkerStatusEntity.setActive("Y");
		//zxy 20140709 AUTO-112 修改:只查询Y的数据
		List<ExpressWorkerStatusEntity> expressWorkerstatusEntitys = expressWorkerStatusDao.selectByEmployeeCodesByEntity(queryWorkerStatusEntity, courierQueryList);
		ExpressWorkerStatusEntity expressWorkerStatusEntity =null;
		int changeRows = 0;
		//zxy 20140709 AUTO-112 start 新增:增加校验
		if(expressWorkerstatusEntitys == null || expressWorkerstatusEntitys.size() <= 0)
			throw new DispatchException("当前员工工作状态未开启！"); 
		//zxy 20140709 AUTO-112 end 新增:增加校验
	    //如果是PDA开启或者关闭则只能操作一条
		if(ExpressWorkerStatusConstants.PDA_SYSTEM.equals(dto.getOperateSystem())){
			//校验开启或者关闭的工号是否大于1个
			if (dto.getExpressEmpCodes().size() > 1) {
				throw new DispatchException("暂停的工号过多！");
			}
			expressWorkerStatusEntity = expressWorkerstatusEntitys.get(0);
			////14.7.25 gcl AUTO-201
			PdaSignEntity pdaSignEntity=new PdaSignEntity();
			pdaSignEntity.setDriverCode(expressWorkerStatusEntity.getEmpCode().toString());
			pdaSignEntity.setStatus(PdaSignStatusConstants.BUNDLE);
			//liding add for NCI
			//用户类型
			pdaSignEntity.setUserType(userType);
			List<PdaSignEntity> pdaSignEntitys=pdaSignEntityDao.querySignedDV(pdaSignEntity);
			if(pdaSignEntitys!=null&&pdaSignEntitys.size()>0){
				pdaSignEntity=pdaSignEntitys.get(0);
				if(PdaSignStatusConstants.USER_TYPE_DRIVER.equals(pdaSignEntity.getUserType())){
					//零担 查询车辆的工作状况
					ExpressWorkerStatusEntity queryEntity = new ExpressWorkerStatusEntity();
					queryEntity.setActive(FossConstants.YES);
					queryEntity.setVehicleNo(pdaSignEntity.getVehicleNo());
					queryEntity.setBusinessArea(ExpressWorkerStatusConstants.ORDER_BUSINESS);
					queryEntity=expressWorkerStatusDao.queryOneByVehicleNo(queryEntity);
					if (queryEntity ==null || queryEntity.getWorkStatus() == null) {
						throw new DispatchException("没有可用的车辆工作状态！");						
					}
					if(ExpressWorkerStatusConstants.STOP_STATUS.
							equals(queryEntity.getWorkStatus())){
						throw new DispatchException("车牌号为："+queryEntity.getVehicleNo()+"的状态已经为暂停状态！"); //zxy 20140708 AUTO-112 修改:提示内容修改
					}
					queryEntity.setModifyTime(new Date());
					queryEntity.setModifyCode(dto.getCreatorCode());
					queryEntity.setWorkStatus(dto.getOperateType());
					changeRows = expressWorkerStatusDao
							.updateByPrimaryKeySelective(queryEntity);
				}else{
					// 快递员工作状态
					// 更新实体
					if(ExpressWorkerStatusConstants.STOP_STATUS.
							equals(expressWorkerStatusEntity.getWorkStatus())){
						throw new DispatchException("员工工号为："+expressWorkerStatusEntity.getEmpCode()+"的状态已经为暂停状态！"); //zxy 20140708 AUTO-112 修改:提示内容修改
					}
					expressWorkerStatusEntity.setModifyTime(new Date());
					expressWorkerStatusEntity.setModifyCode(dto.getCreatorCode());
					expressWorkerStatusEntity.setWorkStatus(dto.getOperateType());
					changeRows = expressWorkerStatusDao
							.updateByPrimaryKeySelective(expressWorkerStatusEntity);
				}
			}else{
				throw new DispatchException("员工工号为："+expressWorkerStatusEntity.getEmpCode()+"无签到！");
			}
		}else if(ExpressWorkerStatusConstants.FOSS_SYSTEM.equals(dto.getOperateSystem())){
			//校验是否正确
		    //将员工转化为map
			Map<String,ExpressWorkerStatusEntity> ExpressWorkermap = CollectionUtils.
					asMap(expressWorkerstatusEntitys, String.class, ExpressWorkerStatusEntity.class, "getEmpCode");
			
			//遍历数据进行校验 
			for(String empCode :dto.getExpressEmpCodes()){
				if(ExpressWorkermap.containsKey(empCode)){
					expressWorkerStatusEntity = ExpressWorkermap.get(empCode);
					//判断是否已经开启
					if(ExpressWorkerStatusConstants.STOP_STATUS.
							equals(expressWorkerStatusEntity.getWorkStatus())){
						throw new DispatchException("员工工号为："+empCode+"的状态已经为暂停状态！");
					}
				}else {
					throw new DispatchException("员工工号为："+empCode+"没有在PDA登陆记录！");
				}
			}
		    //更新快递员状态
			changeRows = expressWorkerStatusDao.updateByEmployeeCodes(dto);			
		}
		//添加日志
		addExpressWorkerLog(dto);
		//更新状态
		return changeRows;
	
	}
	/**
	 * 查询车辆当前状态 14.7.24 gcl
	 * @param vehicleNo
	 * @return
	 */
	public String queryVehicleNoStatus(String vehicleNo){
		ExpressWorkerStatusEntity queryWorkerStatusEntity = new ExpressWorkerStatusEntity();
		queryWorkerStatusEntity.setActive("Y");
		queryWorkerStatusEntity.setVehicleNo(vehicleNo);
		queryWorkerStatusEntity.setBusinessArea(ExpressWorkerStatusConstants.ORDER_BUSINESS);
		ExpressWorkerStatusEntity expressWorkerstatusEntity = expressWorkerStatusDao.queryOneByVehicleNo(queryWorkerStatusEntity);
		if(expressWorkerstatusEntity!=null&&ExpressWorkerStatusConstants.STOP_STATUS.equals(expressWorkerstatusEntity.getWorkStatus())){
			return "1";
		}
		return "0";
	}
    /**
     * 
     * @author:lianghaisheng
     * @Description：构建日志实体
     * @date:2014-4-21 上午9:17:04
     * @return void
     */
	private void addExpressWorkerLog( ExpressWorkerStatusDto dto){
		//构建并且插入日志
		ExpressWorkerStatusLogEntity logEntity = new ExpressWorkerStatusLogEntity();
		logEntity.setOperateType(dto.getOperateType());
		logEntity.setOperateSystem(dto.getOperateSystem());
		logEntity.setCreateTime(new Date());
		logEntity.setCreateUsercode(dto.getCreatorCode());
		logEntity.setModifyTime(new Date());
		logEntity.setPdaNo(dto.getPDANum());
		logEntity.setModifyUsercode(dto.getCreatorCode());
//		logEntity.setBusinessArea(dto.getBusinessArea()); //zxy 20140707 内部优化  修改:删除无用字段
		//封装并且插入数据库
		for(int i=0;i< dto.getExpressEmpCodes().size();i++){
		logEntity.setId(UUID.randomUUID().toString());
		logEntity.setEmpCode(dto.getExpressEmpCodes().get(i));
		expressWorkerStatusLogDao.insertSelective(logEntity);
		}
	}
	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：拼装员工状态实体
	 * @date:2014-4-21 下午2:52:05
	 * @return void
	 */
	private void buildWorkerStatusEntity(ExpressWorkerStatusEntity expressWorkerStatusEntity ,
			ExpressWorkerStatusDto dto, PdaSignEntity pdaSignEntity){
		expressWorkerStatusEntity.setId(UUID.randomUUID().toString());
		expressWorkerStatusEntity.setActive(FossConstants.YES);
		expressWorkerStatusEntity.setCreateTime(new Date());
		expressWorkerStatusEntity.setModifyTime(new Date());//zxy 20140708 AUTO-112 新增
		expressWorkerStatusEntity.setCreateUsercode(dto.getCreatorCode());
		String empCode = dto.getExpressEmpCodes().get(0);
		expressWorkerStatusEntity.setEmpCode(empCode);
		//获取员工姓名
		EmployeeEntity empEntity = employeeService.queryEmployeeByEmpCode(empCode);
		if(empEntity != null){
		expressWorkerStatusEntity.setEmpName(empEntity.getEmpName());
		}
		expressWorkerStatusEntity.setPadNo(dto.getPDANum());
		expressWorkerStatusEntity.setWorkStatus(dto.getOperateType());
		//签到signId
		expressWorkerStatusEntity.setPdaSignId(pdaSignEntity.getId());
		//车牌号
		expressWorkerStatusEntity.setVehicleNo(pdaSignEntity.getVehicleNo());
		//业务类型
		if(PdaSignStatusConstants.USER_TYPE_COURIER.equals(pdaSignEntity.getUserType()))
			expressWorkerStatusEntity.setBusinessArea(ExpressWorkerStatusConstants.EXPRESS_ORDER_BUSINESS);
		else if(PdaSignStatusConstants.USER_TYPE_DRIVER.equals(pdaSignEntity.getUserType()))
			expressWorkerStatusEntity.setBusinessArea(ExpressWorkerStatusConstants.ORDER_BUSINESS);
	}
	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：根据查询条件封装对应的拼装结果DTO
	 * @date:2014-4-23 上午9:04:31
	 * @return List<ExpressWorkerCompleteDto>
	 * @update 20140703 zxy AUTO-98 重复累加数据问题修改
	 */
	private ExpressWorkerCompleteDto bulidExpressWorkerCompleteDto(WorkerbillCountQueryDto queryDto,CourierSignDto courierSignDto){
		//订单完成情况
		Map<String,ExpressWorkerBillCountDto> orderCompelete = null;
		//运单完成情况
		Map<String,ExpressWorkerBillCountDto> waybillCompelete = null;
		//无订单开单完成情况
		Map<String,ExpressWorkerBillCountDto> noOrderWaybill = null;
		//无订单开单完成情况
		Map<String,ExpressWorkerBillCountDto> noOrderWaybillPending = null;
		//快递员工作状态
		Map<String,ExpressWorkerStatusEntity> expressWorkerStatus = null;
		//对三个map进行初始化
		List<ExpressWorkerBillCountDto> tmpDto = null;
		
		tmpDto = expressWorkerStatusDao.queryOrderBillCount(queryDto);
		orderCompelete = CollectionUtils.asMap(tmpDto, String.class, ExpressWorkerBillCountDto.class, "getEmployeeCode");
		
		tmpDto = expressWorkerStatusDao.queryWaybillCount(queryDto);
		waybillCompelete =  CollectionUtils.asMap(tmpDto, String.class, ExpressWorkerBillCountDto.class, "getEmployeeCode");
		
		tmpDto = expressWorkerStatusDao.queryNoOrderBillCount(queryDto);
		noOrderWaybill = CollectionUtils.asMap(tmpDto, String.class, ExpressWorkerBillCountDto.class, "getEmployeeCode");		
		// YB 2014.7.17 AUTO-124 根据工号查询快递员工作状态的最新一条数据
		tmpDto = expressWorkerStatusDao.queryNoOrderPendingCount(queryDto);
		noOrderWaybillPending = CollectionUtils.asMap(tmpDto, String.class, ExpressWorkerBillCountDto.class, "getEmployeeCode");		
		
		///gcl 2014.7.9 AUTO-133 156681无订单开单9544789306，快递员工作状态里统计的无订单开单为0票，应该为1票
		List<ExpressWorkerStatusEntity> expressWokertmp = expressWorkerStatusDao.selectUpDateByEmployeeCodes(queryDto.getCourierSignDtoList());
		expressWorkerStatus = CollectionUtils.asMap(expressWokertmp, String.class, ExpressWorkerStatusEntity.class, "getEmpCode");
		
		CourierSignDto employee = courierSignDto;
			String empcode = employee.getCourierCode();
			ExpressWorkerCompleteDto completeDto = new ExpressWorkerCompleteDto();
			completeDto.setEmp_code(empcode);
			completeDto.setEmp_name(employee.getCourierName());
			completeDto.setEmptype(employee.getCourierType());
			completeDto.setBigRegion(queryDto.getBigZoneName());
			completeDto.setSmallRegion(queryDto.getSmallZoneName());
			//状态和最后更新时间
			if(expressWorkerStatus.get(empcode) != null){
				completeDto.setModifyTime(expressWorkerStatus.get(empcode).getModifyTime());
				completeDto.setEmpStatus(expressWorkerStatus.get(empcode).getWorkStatus().toString());
				completeDto.setActive(expressWorkerStatus.get(empcode).getActive());//14.7.17 gcl AUTO-177
			}else{
				completeDto.setModifyTime(new Date(0));
				completeDto.setEmpStatus(null);
				completeDto.setActive(FossConstants.NO);//14.7.17 gcl AUTO-177
			}
			//订单数
			if(orderCompelete.get(empcode) != null){
				completeDto.setGetOrder(orderCompelete.get(empcode).getReceiveCount());
				completeDto.setReceiveOrder(orderCompelete.get(empcode).getCompleteCount());
			}else {
				completeDto.setGetOrder(Integer.valueOf("0"));
				completeDto.setReceiveOrder(Integer.valueOf("0"));
			}
			//运单数
			if(waybillCompelete.get(empcode) != null){
				completeDto.setGetWaybill(waybillCompelete.get(empcode).getReceiveCount());
				completeDto.setDeliverWaybill(waybillCompelete.get(empcode).getCompleteCount());
			}else {
				completeDto.setGetWaybill(Integer.valueOf("0"));
				completeDto.setDeliverWaybill(Integer.valueOf("0"));
			}
			//无订单开单
			if(noOrderWaybill.get(empcode) != null
					&& noOrderWaybillPending.get(empcode) != null){
				Integer countWayBill = noOrderWaybill.get(empcode).getOtherCount();
				Integer countWayBillPending = noOrderWaybillPending.get(empcode).getOtherCount();
				int noCount = countWayBill.intValue()+countWayBillPending.intValue();
				completeDto.setNoOrderWaybill(Integer.valueOf(noCount));
			}else if(noOrderWaybill.get(empcode) != null
					&& noOrderWaybillPending.get(empcode) == null){
				Integer countWayBill = noOrderWaybill.get(empcode).getOtherCount();
				completeDto.setNoOrderWaybill(countWayBill);
			}else if(noOrderWaybill.get(empcode) == null
					&& noOrderWaybillPending.get(empcode) != null){
				Integer countWayBill = noOrderWaybillPending.get(empcode).getOtherCount();
				completeDto.setNoOrderWaybill(countWayBill);
			}else if(noOrderWaybill.get(empcode) == null
					&& noOrderWaybillPending.get(empcode) == null){
				completeDto.setNoOrderWaybill(Integer.valueOf("0"));
			}
		return completeDto;
	}	
	
	/**
	 * @author:lianghaisheng
	 * @Description：根据工号查询员工状态
	 * @date:2014-4-29 下午2:25:19
	 */
	@Override
	public ExpressWorkerStatusEntity queryOneByEmpcode(String emp_Code) {
		//校验工号是否合法
		if(emp_Code == null || "".equals(emp_Code)){
			throw new DispatchException("员工工号为空！");
		}
		return expressWorkerStatusDao.selectOneByEmpcode(emp_Code);
	}
	
	public void setOrderExpressService(IOrderExpressService orderExpressService) {
		this.orderExpressService = orderExpressService;
	}
	public IExpressWorkerStatusDao getExpressWorkerStatusDao() {
		return expressWorkerStatusDao;
	}
	public void setExpressWorkerStatusDao(
			IExpressWorkerStatusDao expressWorkerStatusDao) {
		this.expressWorkerStatusDao = expressWorkerStatusDao;
	}
	public IExpressWorkerStatusLogDao getExpressWorkerStatusLogDao() {
		return expressWorkerStatusLogDao;
	}
	public void setExpressWorkerStatusLogDao(
			IExpressWorkerStatusLogDao expressWorkerStatusLogDao) {
		this.expressWorkerStatusLogDao = expressWorkerStatusLogDao;
	}
	public IEmployeeService getEmployeeService() {
		return employeeService;
	}
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
		this.pdaSignEntityDao = pdaSignEntityDao;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

}
