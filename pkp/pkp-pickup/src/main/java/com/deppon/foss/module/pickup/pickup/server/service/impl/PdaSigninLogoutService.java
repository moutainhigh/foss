/**
 *  initial comments.
 *   * 
 * 修订记录 
 * 
 * 日期 	
 * 
 * 修订内容 
 * 
 * 修订人员 	
 * 
 * 
 * 版本号 
 * 
 * 2012-05-11 	
 * 
 * 新增	
 * 
 * 王辉	V0.1
 * 
 * 2012-07-25	
 * 
 * Jira审批通过，
 * 
 * 升至0.9版本	
 * 
 * 王辉	V0.9
 * 
 * 2013-1-8
 * 
 * 根据ISSUE-1422修改	王辉	
 * 
 *   	  	  	 
 * 
 * 1.	SUC-344-签到接口
 * 
 * 1.1	相关业务用例
 * 
 * BUC_FOSS_5.10.10_510 
 * 
 * 司机确认使用PDA
 * 
 * 1.2	用例描述
 * 
 * 司机输入车牌号、工号、密码，
 * 
 * 登陆PDA接送货系统，
 * 
 * 调用签到接口。
 * 
 * 1.3	用例条件
 * 
 * 条件类型	
 * 
 * 描述	引用系统用例
 * 
 * 前置条件	
 * 
 * 无	
 * 
 * 后置条件	
 * 
 * 无	
 * 
 * 1.4	操作用户角色
 * 
 * 操作用户	
 * 
 * 描述
 * 
 * 无	
 * 
 * 1.5	
 * 
 * 界面要求
 * 
 * 1.5.1	
 * 
 * 表现方式
 * 
 * 接口
 * 
 * 1.5.2	
 * 
 * 界面原型
 * 
 * 无
 * 
 * 1.5.3	
 * 
 * 界面描述
 * 
 * 无
 * 
 * 1.6	
 * 
 * 操作步骤
 * 
 * 序号	基本步骤	相关数据	补充步骤
 * 
 * 1	传入参数	签到信息	
 * 
 * 2	校验车牌号	签到信息	车牌号存在，继续步骤4，参考SR3
 * 
 * 3	返回结果信息	结果信息	1、	系统建立绑定关系，参考SR1,SR3
 * 
 * 2、	系统修改PDA状态为“使用中”
 * 
 * 3、	系统返回结果信息，结果信息中包括结果码、结果详情、绑定信息（系统自动生成的唯一标识）
 * 
 * 扩展事件写非典型或异常操作过程
 * 
 * 序号	扩展事件	相关数据	备注
 * 
 * 3a	车牌号不存在，返回结果信息“车牌号不存在，请重新输入！”	结果信息	
 * 
 * 3b	工号、车牌号、PDA设备号与系统中存在绑定关系非三者完全相同：
 * 
 * 若工号存在，则返回“工号已绑定，请解绑！”
 * 
 * 若车牌号存在，则返回“车牌号已绑定，请解绑！”
 * 
 * 若PDA设备号存在，则返回“PDA设备号已绑定，请解绑！”
 * 
 * 若工号、车牌号存在，则返回“工号、车牌号已绑定，请解绑！”
 * 
 * 若工号、PDA设备号存在，则返回“工号、PDA设备号已绑定，请解绑！”
 * 
 * 若车牌号、PDA设备号存在，则返回“车牌号、PDA设备号已绑定，请解绑！”	结果信息	
 * 
 * 4a	返回结果PDA未收到，PDA再次发送请求，
 * 
 * 若PDA设备号、工号、车牌与上次请求的PDA设备号、工号、车牌相同，则直接返回“登陆成功”	结果信息	
 * 
 * 1.7	
 * 
 * 业务规则
 * 
 * 
 * 序号	
 * 
 * 描述
 * 
 * SR1	
 * 
 * 1、	工号对应司机基础资料的工号；
 * 
 * 2、	车牌号对应定人定区基础资料中车牌号
 * 
 * 3、	设备号对应PDA基础资料；
 * 
 * SR2	
 * 
 * 1、	工号、车牌号与排班表中冲突时，覆盖原有工号、车牌号
 * 
 * SR3	
 * 
 * 1.	用户类型为司机时才判断车牌号
 * 
 * 1.8	
 * 
 * 数据元素
 * 
 * 1.8.1	
 * 
 * 签到信息 
 * 
 * 字段名称 	
 * 
 * 说明 	
 * 
 * 输入限制	
 * 
 * 输入项提示文本	
 * 
 * 长度	是否必填	备注
 * 
 * 设备号	
 * 
 * PDA设备唯一标识	
 * 
 * 文本		
 * 
 * 40
 * 		
 * 工号		
 * 
 * 文本		
 * 
 * 20		
 * 
 * 车牌号		
 * 
 * 文本		
 * 
 * 20		
 * 
 * 密码		
 * 
 * 文本		
 * 
 * 20		
 * 
 * 1.8.2	结果信息
 * 
 * 字段名称 	
 * 
 * 说明 	
 * 
 * 输入限制	
 * 
 * 输入项提示文本	
 * 
 * 长度	是否必填	备注
 * 
 * 结果码		
 * 
 * 文本	5			
 * 
 * 结果详情		
 * 
 * 文本	200			
 * 
 * 
 * 绑定信息		
 * 
 * 文本	20			
 * 
 * 
 * 1.9	
 * 
 * 非功能性需求
 * 
 * 使用量	
 * 
 * 一天登陆2次，2*2000=4000次/天
 * 
 * 2012年全网估计用户数	
 * 
 * 1000-2000用户
 * 
 * 响应要求（如果与全系统要求 不一致的话）	
 * 
 * 3s内响应
 * 
 * 使用时间段	
 * 
 * 07:00-12:00
 * 
 * 
 * 高峰使用时间段	
 * 
 * 08:00-10:00
 * 
 * 
 * 1.10	
 * 
 * 接口描述：
 * 
 * 接口名称 	
 * 
 * 对方系统（外部系统或内部其他模块）	
 * 
 * 接口描述
 * 
 * 无
 * 
 * 
 * 
 * 
 */
/*******************************************************************************
 * Copyright 2013  PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME : pkp-pickup
 * 
 * FILE PATH         : src/main/java/com/deppon/foss/module/pickup/pickup/server/service/impl/PdaSigninLogoutService.java
 * 
 * FILE NAME         : PdaSigninLogoutService.java
 * 
 * AUTHOR : FOSS接送货系统开发组
 * 
 * HOME PAGE : http://www.deppon.com
 * 
 * COPYRIGHT : Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IExpressWorkerStatusDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IPdaAddressCollectionService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.ExpressWorkerStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSigninLogoutService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSigninDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 提供PDA签到(长短途）与注销的功能。
 * 
 * @author 097972-foss-dengtingting
 */
public class PdaSigninLogoutService implements IPdaSigninLogoutService {

	// PDA签到DAO
	private IPdaSignEntityDao pdaSignEntityDao;
	// 调度订单DAO
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	// 到达联DAO
	private IArrivesheetDao arrivesheetDao;
	// 派送排单明细DAO
	private IDeliverbillDetailDao deliverbillDetailDao;
	// 人员service
	private IEmployeeService employeeService;
	//定义外请车司机工号常量
	private static final String ZERO="000000";
	/**
	 * 快递车辆service
	 */
	private IExpressVehiclesService expressVehiclesService;
	
	private IPdaAddressCollectionService pdaAddressCollectionService;

	/**
	 * 校验登录司机
	 */
	private IExpressWorkerStatusDao expressWorkerStatusDao;
	
	private ILeasedVehicleService leasedVehicleService ;
	/**
	 * PDA签到接口.
	 * 
	 * @param deviceNo 
	 * 
	 * 			设备号
	 * 
	 * @param driverCode 
	 * 
	 * 			司机工号
	 * 
	 * @param vehicleNo 
	 * 
	 * 			车牌号
	 * 
	 * @param signTime 
	 * 
	 * 			签到时间
	 * 
	 * @param userType 
	 * 
	 * 			用户类型
	 * 
	 * @author 097972-foss-dengtingting
	 * 
	 * @date 2012-11-27 上午9:11:56
	 * @update zxy 20140718 AUTO-170 零担 改成车牌号 做为与工作状态表的主键关联，对车辆进行开启暂停
	 */
	@Transactional
	@Override
	public void signIn(PdaSigninDto pdaSigninDto) {
		//签到类型-快递/零担
		String businessArea = null;	
		if (StringUtils.isEmpty(pdaSigninDto.getUserType())) {
			throw new PdaProcessException("请传入用户类别！");
		}
		//liding add for NCI
		boolean isPosSign = PdaSignStatusConstants.USER_TYPE_NCI.equals(pdaSigninDto.getUserType());
		if (PdaSignStatusConstants.USER_TYPE_DRIVER.equals(pdaSigninDto.getUserType())) {
			businessArea = ExpressWorkerStatusConstants.ORDER_BUSINESS;
			// 校验车牌号是否存在 调用综合接口
			if (StringUtils.isEmpty(pdaSigninDto.getVehicleNo())) {
				throw new PdaProcessException("请传入车牌号！");
			} else {
//				ownTruck.setVehicleNo(pdaSigninDto.getVehicleNo());
//				OwnTruckEntity truckEntity = ownVehicleService.queryOwnVehicleBySelective(ownTruck, null);
				
				//ownVehicle为公司车，leasedVehicle为外请车，expressVehicle为快递车辆，如果这三种都不是就返回"未知车辆种类"
				String vehicleType = leasedVehicleService.queryVehicleType(pdaSigninDto.getVehicleNo()) ;

				if (StringUtils.isBlank(vehicleType)) {
					throw new PdaProcessException("该车牌号不存在，请重试！");
				}
			}
		} else if (PdaSignStatusConstants.USER_TYPE_COURIER.equals(pdaSigninDto.getUserType())) {
			businessArea = ExpressWorkerStatusConstants.EXPRESS_ORDER_BUSINESS;
			// 校验快递车牌号是否存在 调用综合接口
			ExpressVehiclesEntity expressVehiclesEntity = new ExpressVehiclesEntity();
			if (StringUtils.isEmpty(pdaSigninDto.getVehicleNo())) {
				throw new PdaProcessException("请传入车牌号！");
			} else {
				expressVehiclesEntity.setVehicleNo(pdaSigninDto.getVehicleNo());
				Long count = expressVehiclesService.queryRecordCount(expressVehiclesEntity);
				if (count <= 0) {
					throw new PdaProcessException("该快递车辆不存在，请重试！");
				}
			}
		}
		//liding add for NCI
		else if (isPosSign) {
			businessArea = ExpressWorkerStatusConstants.ORDER_BUSINESS;
		}
		int addNum = 0;
		//liding add for NCI
		//add by 329757 判断是否是外请车
		List<PdaSignEntity> pdaSignList = null;
		if(!pdaSigninDto.getDriverCode().equals(ZERO)){
			PdaSignEntity queryParam = new PdaSignEntity(pdaSigninDto.getDeviceNo(), pdaSigninDto.getDriverCode(), pdaSigninDto.getVehicleNo(), PdaSignStatusConstants.BUNDLE);
			queryParam.setUserType(pdaSigninDto.getUserType());
			pdaSignList = pdaSignEntityDao.querySignedDV(queryParam);
		}else{
			PdaSignEntity queryParam = new PdaSignEntity(pdaSigninDto.getDeviceNo(),pdaSigninDto.getVehicleNo(), PdaSignStatusConstants.BUNDLE);
			queryParam.setUserType(pdaSigninDto.getUserType());
			//如果是外请车  排除司机工号查询签到信息
			pdaSignList = pdaSignEntityDao.querySignedByVehicleNo(queryParam);
		}
		if (CollectionUtils.isNotEmpty(pdaSignList)) {
			PdaSignEntity pdaSign = pdaSignList.get(0);
			// 判空
			if (pdaSign != null) {
				// 判断司机工号和设备好是否相同
				if (StringUtils.isNotEmpty(pdaSigninDto.getDeviceNo()) 
						&& StringUtils.isNotEmpty(pdaSigninDto.getDriverCode()) 
						&& pdaSigninDto.getDeviceNo().equals(pdaSign.getDeviceNo()) 
						&& pdaSigninDto.getDriverCode().equals(pdaSign.getDriverCode())
						&&StringUtils.isNotEmpty(pdaSign.getUserType()) &&
						pdaSigninDto.getUserType().equals(pdaSign.getUserType())) {
					// 若为司机，则还需判断是否车牌号也相同，相同则直接返回
					// 273279-liding add若是POS机签到，车牌号为空则不验证车牌号
					if(isPosSign || (StringUtils.isNotEmpty(pdaSigninDto.getVehicleNo()) 
						&& pdaSigninDto.getVehicleNo().equals(pdaSign.getVehicleNo()))) {
						if(StringUtils.isBlank(pdaSign.getId())){
							throw new PdaProcessException("签到失败，请稍后再试！");
						}
						PdaSignEntity updatepdaSign = new PdaSignEntity();
						updatepdaSign.setId(pdaSign.getId());
						updatepdaSign.setUnbundleTime(new Date());
						updatepdaSign.setStatus(PdaSignStatusConstants.UNBUNDLE);//已解绑
						if(pdaSignEntityDao.updateByPrimaryKeySelective(updatepdaSign)>0){
							// 是则 签到
							PdaSignEntity entity = new PdaSignEntity();
							// 创建时间
							entity.setCreateTime(pdaSigninDto.getSignTime());
							// 设备号
							entity.setDeviceNo(pdaSigninDto.getDeviceNo());
							// 司机编号
							entity.setDriverCode(pdaSigninDto.getDriverCode());
							// 主键
							entity.setId(UUIDUtils.getUUID());
							// 状态
							entity.setStatus(PdaSignStatusConstants.BUNDLE);
							// 车牌号
							entity.setVehicleNo(pdaSigninDto.getVehicleNo());
							//add by 329757 
							//如果司机工号是“000000” 说明是外请车司机  用车牌号查询用户信息
							String driverName =null;
							if(pdaSigninDto.getDriverCode().equals(ZERO)){
								//如果是外請車司机姓名寫死‘外请车司机’
								driverName = "外请车司机";
							}else{
								// 查询用户信息
								EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(pdaSigninDto.getDriverCode());
								// 司机姓名
								driverName = employeeEntity != null ? employeeEntity.getEmpName() : "";
							}
							entity.setDriverName(driverName);
							// 用户类型
							entity.setUserType(pdaSigninDto.getUserType());
							// 组织code
							entity.setOrgCode(pdaSigninDto.getOrgCode());
							// 插入签到记录
							addNum = pdaSignEntityDao.insertSelective(entity);
							//推送状态给oms系统
							try {
								pdaAddressCollectionService.sendModifySignInfo(entity);
							} catch (Exception e) {
								// TODO: 
							}
							// 签到失败
							if (addNum <= 0) {
								throw new PdaProcessException("签到失败，请稍后再试！");
							}
							int addRow = 0;
							//zxy 20140718 AUTO-170 start 修改:零担 改成车牌号 做为与工作状态表的主键关联，对车辆进行开启暂停
							//快递
							if(ExpressWorkerStatusConstants.EXPRESS_ORDER_BUSINESS.equals(businessArea)){
								// PDA登录时，新增一条工作状态数据
								addRow = addWorkRecord(entity,businessArea);
							}
							//零担
							else if(ExpressWorkerStatusConstants.ORDER_BUSINESS.equals(businessArea)){
								addRow = addOrderWorkRecord(entity,businessArea);
							}
							//zxy 20140718 AUTO-170 end 修改:零担 改成车牌号 做为与工作状态表的主键关联，对车辆进行开启暂停
							if(addRow <= 0){
								throw new PdaProcessException("签到成功之后，添加司机工作状态失败！");
							}
						}
						return ;
					} else {
						throw new PdaProcessException("司机工号跟设备已绑定，对应的车牌号未绑定");
					}
				}
				// 校验设备号
				else if (StringUtils.isNotEmpty(pdaSigninDto.getDeviceNo()) && pdaSigninDto.getDeviceNo().equals(pdaSign.getDeviceNo())) {
					throw new PdaProcessException("该设备已登录！不能重复登录！");
				}
				// 校验司机工号
				else if (StringUtils.isNotEmpty(pdaSigninDto.getDriverCode()) && pdaSigninDto.getDriverCode().equals(pdaSign.getDriverCode()) 
						&& !pdaSigninDto.getDriverCode().equals(ZERO)) {
					throw new PdaProcessException("工号已登录！不能重复登录！");
				}// 校验车牌号
				else if(StringUtils.isNotEmpty(pdaSigninDto.getVehicleNo()) && pdaSigninDto.getVehicleNo().equals(pdaSign.getVehicleNo())){
					throw new PdaProcessException("车牌号已登录！不能重复登录！");
				}
				else if (!pdaSigninDto.getUserType().equals(pdaSign.getUserType())) {
					throw new PdaProcessException("用户类型不对，不能登录！");
				}
			}
		}
		// 是则 签到
		PdaSignEntity entity = new PdaSignEntity();
		// 创建时间
		entity.setCreateTime(pdaSigninDto.getSignTime());
		// 设备号
		entity.setDeviceNo(pdaSigninDto.getDeviceNo());
		// 司机编号
		entity.setDriverCode(pdaSigninDto.getDriverCode());
		// 主键
		entity.setId(UUIDUtils.getUUID());
		// 状态
		entity.setStatus(PdaSignStatusConstants.BUNDLE);
		// 车牌号
		entity.setVehicleNo(pdaSigninDto.getVehicleNo());
		// 查询用户信息
		EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(pdaSigninDto.getDriverCode());
		// 司机姓名
		String driverName = employeeEntity != null ? employeeEntity.getEmpName() : "";
		entity.setDriverName(driverName);
		// 用户类型
		entity.setUserType(pdaSigninDto.getUserType());
		// 组织code
		entity.setOrgCode(pdaSigninDto.getOrgCode());
		// 插入签到记录
		addNum = pdaSignEntityDao.insertSelective(entity);
		//推送状态给oms系统
		try {
			pdaAddressCollectionService.sendModifySignInfo(entity);
		} catch (Exception e) {
			// TODO: 
		}
		// 签到失败
		if (addNum <= 0) {
			throw new PdaProcessException("签到失败，请稍后再试！");
		}
		int addRow = 0;
		// PDA登录时，新增一条工作状态数据
		//快递
		if(ExpressWorkerStatusConstants.EXPRESS_ORDER_BUSINESS.equals(businessArea)){
			addRow = addWorkRecord(entity,businessArea);
		}
		//零担
		else if(ExpressWorkerStatusConstants.ORDER_BUSINESS.equals(businessArea)){
			addRow = addOrderWorkRecord(entity,businessArea);
		}
		if(addRow <= 0){
			throw new PdaProcessException("签到成功之后，添加司机工作状态失败！");
		}
	}

	/**
	 * PDA注销接口.
	 * 
	 * @param deviceNo 
	 * 
	 * 			设备号
	 * 
	 * @param driverCode 
	 * 
	 * 			司机工号
	 * 
	 * @param vehicleNo 
	 * 
	 * 			车牌号
	 * 
	 * @param unbundleTime 
	 * 
	 * 			解绑时间
	 * 
	 * @author 097972-foss-dengtingting
	 * 
	 * @date 2012-11-27 下午2:56:10
	 * 
	 * @update zxy 20140718 AUTO-170 零担 改成车牌号 做为与工作状态表的主键关联，对车辆进行开启暂停
	 * @update by liding for NCI 20160317 增加用户类型参数
 * 
	 */
	@Transactional
	@Override
	public void loginOut(String deviceNo, String driverCode, String vehicleNo,String userType, Date unbundleTime) {
		// 校验PDA是否绑定
		PdaSignEntity pdaSignParam = new PdaSignEntity(deviceNo, driverCode, vehicleNo, PdaSignStatusConstants.BUNDLE);
		int num = pdaSignEntityDao.querySignCountByDV(pdaSignParam);
		//zxy 20140718 AUTO-170 start 新增:
		//获取当前签到信息主体
		PdaSignEntity pdaSignEntity = pdaSignEntityDao.querySignEntityByDV(pdaSignParam);
		if(pdaSignEntity == null){
			return;
		}
		//zxy 20140718 AUTO-170 end 新增:
		Integer arriveNum = 0;
		if (num <= 0) {
			return;
		}
		//liding add for NCI
		//不是POS机签到时 才检查是否有接货任务
		if(!PdaSignStatusConstants.USER_TYPE_NCI.equals(pdaSignEntity.getUserType())){			
		
			ExpressVehiclesEntity expressVehiclesEntity = new ExpressVehiclesEntity();
			expressVehiclesEntity.setVehicleNo(vehicleNo);
			Long count = expressVehiclesService.queryRecordCount(expressVehiclesEntity);
			Boolean isExpress = true;
			if (count <= 0) {
				isExpress =false;
			}
			// 校验是否有接货中的任务
			List<String> defaultOrderStatus = new ArrayList<String>();
			defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
			defaultOrderStatus.add(DispatchOrderStatusConstants.STATUS_PICKUPING);
			DispatchOrderConditionDto dto = new DispatchOrderConditionDto();
			dto.setDeviceNo(deviceNo);
			dto.setDriverCode(driverCode);
			dto.setVehicleNo(vehicleNo);
			dto.setOperateDate(new Date());
			dto.setDefaultOrderStatus(defaultOrderStatus);
			int pkpOrderNum = dispatchOrderEntityDao.queryOrdersBy(dto);
			// 有则注销失败，存在接货任务
			if (pkpOrderNum > 0) {
				throw new PdaProcessException("注销失败，存在未完成的接货任务");
			}
			// 校验是否有送货中的任务
			DeliverbillDetailDto dtoDeliver = new DeliverbillDetailDto ();
			dtoDeliver.setVehicleNo(vehicleNo);
			dtoDeliver.setDriverCode(driverCode);
			dtoDeliver.setStatus(DeliverbillConstants.STATUS_PDA_DOWNLOADED);
			if(isExpress){
				dtoDeliver.setIsExpress("YES");
			}
			// 根据车辆、司机工号查询派送单内到达联编号
			List<DeliverbillDetailDto> detailList = deliverbillDetailDao.queryDeliverbillDetailBy(dtoDeliver);
			if (CollectionUtils.isNotEmpty(detailList)) {
				for (DeliverbillDetailDto deliverbillDetailDto : detailList) {
					ArriveSheetEntity entity = new ArriveSheetEntity();
					entity.setArrivesheetNo(deliverbillDetailDto.getArrivesheetNo());
					entity.setStatus(ArriveSheetConstants.STATUS_DELIVER);
					entity.setActive(FossConstants.YES);
					entity.setDestroyed(FossConstants.NO);
					arriveNum += arrivesheetDao.countArriveSheetByNo(entity);
				}
			}
			// 有则注销失败，存在送货任务
			if (arriveNum > 0) {
				throw new PdaProcessException("注销失败，存在未完成的送货任务");
			}
	}
		// 解绑
		PdaSignDto pdaSignDto = new PdaSignDto();
		// 司机编码
		pdaSignDto.setDriverCode(driverCode);
		// 车牌号
		pdaSignDto.setVehicleNo(vehicleNo);
		// 已绑定
		pdaSignDto.setStatus(PdaSignStatusConstants.UNBUNDLE);
		// 订单原始状态
		pdaSignDto.setOriginStatus(PdaSignStatusConstants.BUNDLE);
		// 解绑时间
		pdaSignDto.setUnbundleTime(unbundleTime);
		//liding add for NCI
		// 用户类型
		pdaSignDto.setUserType(userType);
		//14.7.16 gcl 查询签到信息
		String pdaSignId=pdaSignEntityDao.querySignIdByDV(pdaSignDto);
		int upNum = pdaSignEntityDao.updateStatusByDv(pdaSignDto);
		if (upNum <= 0) {
			throw new PdaProcessException("注销失败，请稍后再试");
		}
		try {
			// 解绑
			PdaSignEntity pdaEntity = new PdaSignEntity();
			pdaEntity.setId(pdaSignId);
			pdaEntity.setDriverCode(driverCode);
			pdaEntity.setVehicleNo(vehicleNo);
			pdaEntity.setStatus(PdaSignStatusConstants.UNBUNDLE);
			pdaEntity.setUnbundleTime(unbundleTime);
			pdaEntity.setUserType(userType);
			pdaAddressCollectionService.sendModifySignInfo(pdaEntity);
		} catch (Exception e) {
			//to do nothing
		}
		
		int updateRow = 0;
		//快递
		if(PdaSignStatusConstants.USER_TYPE_COURIER.equals(pdaSignEntity.getUserType())){
			updateRow = updateWorkRecord(pdaSignDto,pdaSignId,ExpressWorkerStatusConstants.EXPRESS_ORDER_BUSINESS);
		}
		//零担
		else if(PdaSignStatusConstants.USER_TYPE_DRIVER.equals(pdaSignEntity.getUserType())){
			updateRow = updateOrderWorkRecord(pdaSignEntity,ExpressWorkerStatusConstants.ORDER_BUSINESS);
		}
		//POS机签到 
		//liding add
		else if(PdaSignStatusConstants.USER_TYPE_NCI.equals(pdaSignEntity.getUserType())){
			updateRow = updateWorkRecord(pdaSignDto,pdaSignId,ExpressWorkerStatusConstants.ORDER_BUSINESS);
		}
		
		if(updateRow <= 0){
			throw new PdaProcessException("注销成功后，修改司机工作状态失败");
		}
		
	}
	
	/**
	 * 新增司机工作状态.
	 * 
	 * @param deviceNo 
	 * 
	 * 			设备号
	 * 
	 * @param driverCode 
	 * 
	 * 			司机工号
	 * 
	 * @param vehicleNo 
	 * 
	 * 			车牌号
	 * 
	 * @param unbundleTime 
	 * 
	 * 			解绑时间
	 * 
	 * 
	 * @date 2014-06-27 下午2:56:10
	 * 
	 */
	private int addWorkRecord(PdaSignEntity pdaSignEntity,String businessArea){
		// 初始定义返回行数
		int row = 0;
		// 取得司机工号
		String dirverCode = pdaSignEntity.getDriverCode();
		try{
			// 根据司机工号
			ExpressWorkerStatusEntity worker = expressWorkerStatusDao.selectOneByEmpcode(dirverCode);
			// 判断是否为空
			if(null != worker){
				worker.setActive(FossConstants.YES);
				worker.setWorkStatus(WaybillConstants.WORK_STATUS_OPEN);
				worker.setModifyCode(pdaSignEntity.getDriverCode());
				worker.setModifyUser(pdaSignEntity.getDriverName());
				worker.setModifyTime(new Date());
				worker.setPdaSignId(pdaSignEntity.getId()); //14.7.16 gcl 
				row = expressWorkerStatusDao.updateByPrimaryKeySelective(worker);
			}else {
				worker = new ExpressWorkerStatusEntity();
				worker.setId(UUIDUtils.getUUID());
				worker.setEmpCode(pdaSignEntity.getDriverCode());
				worker.setEmpName(pdaSignEntity.getDriverName());
				worker.setPadNo(pdaSignEntity.getDeviceNo());
				worker.setWorkStatus(WaybillConstants.WORK_STATUS_OPEN);
				worker.setActive(FossConstants.YES);
				worker.setCreateTime(new Date());
				worker.setCreateUsercode(pdaSignEntity.getDriverCode());
				worker.setCreateUser(pdaSignEntity.getDriverName());
				worker.setModifyTime(new Date());//zxy 20140708 AUTO-112 新增
				worker.setPdaSignId(pdaSignEntity.getId());//14.7.16
				worker.setBusinessArea(businessArea);
				worker.setVehicleNo(pdaSignEntity.getVehicleNo());
				row = expressWorkerStatusDao.insertSelective(worker);
			}
			
		}catch(Exception e){
			throw new PdaProcessException("登录时，添加司机工作状态失败"+e.getMessage());
		}
		
		return row;
	}
	
	/**
	 * 新增零担工作状态数据
	 * addOrderWorkRecord: AUTO-170 零担已车牌为外关联主键,对车辆进行暂停开启<br/>
	 * 
	 * Date:2014-7-17下午3:37:11
	 * @author 157229-zxy
	 * @param pdaSignEntity
	 * @param businessArea
	 * @return
	 * @since JDK 1.6
	 */
	private int addOrderWorkRecord(PdaSignEntity pdaSignEntity,String businessArea){
		// 初始定义返回行数
		int row = 0;
		try{
			// 根据司机工号
			ExpressWorkerStatusEntity queryEntity = new ExpressWorkerStatusEntity();
			queryEntity.setActive(FossConstants.YES);
			queryEntity.setVehicleNo(pdaSignEntity.getVehicleNo());
			queryEntity.setBusinessArea(businessArea);
			ExpressWorkerStatusEntity worker = expressWorkerStatusDao.queryOneByVehicleNo(queryEntity);
			// 判断是否为空
			if(worker != null){
				worker.setModifyCode(pdaSignEntity.getDriverCode());
				worker.setModifyUser(pdaSignEntity.getDriverName());
				worker.setModifyTime(new Date());
				worker.setPdaSignId(pdaSignEntity.getId());
				worker.setEmpCode(pdaSignEntity.getDriverCode());
				worker.setEmpName(pdaSignEntity.getDriverName());
				worker.setPadNo(pdaSignEntity.getDeviceNo());
				worker.setBusinessArea(businessArea);
				worker.setVehicleNo(pdaSignEntity.getVehicleNo());
				row = expressWorkerStatusDao.updateByPrimaryKeySelective(worker);
			}else {
				worker = new ExpressWorkerStatusEntity();
				worker.setId(UUIDUtils.getUUID());
				worker.setEmpCode(pdaSignEntity.getDriverCode());
				worker.setEmpName(pdaSignEntity.getDriverName());
				worker.setPadNo(pdaSignEntity.getDeviceNo());
				worker.setWorkStatus(WaybillConstants.WORK_STATUS_OPEN);
				worker.setActive(FossConstants.YES);
				worker.setCreateTime(new Date());
				worker.setCreateUsercode(pdaSignEntity.getDriverCode());
				worker.setCreateUser(pdaSignEntity.getDriverName());
				worker.setModifyTime(new Date());
				worker.setPdaSignId(pdaSignEntity.getId());
				worker.setBusinessArea(businessArea);
				worker.setVehicleNo(pdaSignEntity.getVehicleNo());
				row = expressWorkerStatusDao.insertSelective(worker);
			}
			
		}catch(Exception e){
			throw new PdaProcessException("登录时，添加司机工作状态失败"+e.getMessage());
		}
		
		return row;
	}
	
	/**
	 * 更新司机工作状态.
	 * 
	 * @param deviceNo 
	 * 
	 * 			设备号
	 * 
	 * @param driverCode 
	 * 
	 * 			司机工号
	 * 
	 * @param vehicleNo 
	 * 
	 * 			车牌号
	 * 
	 * @param unbundleTime 
	 * 
	 * 			解绑时间
	 * 
	 * 
	 * @date 2014-06-27 下午2:56:10
	 * 
	 */
	private int updateWorkRecord(PdaSignDto pdaSignDto,String pdaSignId, String businessArea){
		int row = 0 ;
		String dirverCode = pdaSignDto.getDriverCode();
		try{
			//14.7.16 gcl AUTO-163 根据签到id 查询工作状态
			ExpressWorkerStatusEntity worker = expressWorkerStatusDao.selectOneByPdaSignid(pdaSignId);
			// 根据司机工号
//			ExpressWorkerStatusEntity worker = expressWorkerStatusDao.selectOneByEmpcode(dirverCode);
			// 判断是否为空
			if(null != worker){
				worker.setActive(FossConstants.NO);
				worker.setWorkStatus(WaybillConstants.WORK_STATUS_STOP);
				worker.setModifyCode(dirverCode);
				worker.setModifyUser(pdaSignDto.getDriverName());
				worker.setModifyTime(new Date());
				worker.setPdaSignId(pdaSignId);
				row = expressWorkerStatusDao.updateByPrimaryKeySelective(worker);
			}else {
				worker = new ExpressWorkerStatusEntity();
				worker.setId(UUIDUtils.getUUID());
				worker.setEmpCode(pdaSignDto.getDriverCode());
				worker.setEmpName(pdaSignDto.getDriverName());
				worker.setPadNo(pdaSignDto.getDeviceNo());
				worker.setWorkStatus(WaybillConstants.WORK_STATUS_STOP);
				worker.setActive(FossConstants.NO);
				worker.setCreateTime(new Date());
				worker.setModifyTime(new Date());//zxy 20140708 AUTO-112 新增
				worker.setCreateUsercode(pdaSignDto.getDriverCode());
				worker.setCreateUser(pdaSignDto.getDriverName());
				worker.setPdaSignId(pdaSignId);
				row = expressWorkerStatusDao.insertSelective(worker);
			}
			
		}catch(Exception e){
			throw new PdaProcessException("退出时，更新司机工作状态失败"+e.getMessage());
		}
		return row;
	}
	
	/**
	 * 零担更新工作状态表
	 * updateOrderWorkRecord: <br/>
	 * 
	 * Date:2014-7-18上午9:18:47
	 * @author 157229-zxy
	 * @param pdaSignDto
	 * @param pdaSignId
	 * @return
	 * @since JDK 1.6
	 */
	private int updateOrderWorkRecord(PdaSignEntity pdaSignDto, String businessArea){
		int row = 0 ;
		try{
			//根据车牌号查询工作状态信息
			ExpressWorkerStatusEntity expressWorkerStatusParam = new ExpressWorkerStatusEntity();
			expressWorkerStatusParam.setActive(FossConstants.YES);
			expressWorkerStatusParam.setVehicleNo(pdaSignDto.getVehicleNo());
			expressWorkerStatusParam.setBusinessArea(businessArea);
			ExpressWorkerStatusEntity worker = expressWorkerStatusDao.queryOneByVehicleNo(expressWorkerStatusParam);
			// 判断是否为空
			if(worker != null){
				worker.setModifyCode(pdaSignDto.getDriverCode());
				worker.setModifyUser(pdaSignDto.getDriverName());
				worker.setModifyTime(new Date());
				worker.setPdaSignId(pdaSignDto.getId());
				row = expressWorkerStatusDao.updateByPrimaryKeySelective(worker);
			}else{
				worker = new ExpressWorkerStatusEntity();
				worker.setId(UUIDUtils.getUUID());
				worker.setEmpCode(pdaSignDto.getDriverCode());
				worker.setEmpName(pdaSignDto.getDriverName());
				worker.setPadNo(pdaSignDto.getDeviceNo());
				worker.setWorkStatus(WaybillConstants.WORK_STATUS_OPEN);
				worker.setActive(FossConstants.YES);
				worker.setCreateTime(new Date());
				worker.setModifyTime(new Date());//zxy 20140708 AUTO-112 新增
				worker.setCreateUsercode(pdaSignDto.getDriverCode());
				worker.setCreateUser(pdaSignDto.getDriverName());
				worker.setPdaSignId(pdaSignDto.getId());
				//设置工作状态类型 司机则是零担
				if(PdaSignStatusConstants.USER_TYPE_DRIVER.equals(pdaSignDto.getUserType()))
					worker.setBusinessArea(ExpressWorkerStatusConstants.ORDER_BUSINESS);
				else{
					worker.setBusinessArea(ExpressWorkerStatusConstants.EXPRESS_ORDER_BUSINESS);
				}
				//车牌号
				worker.setVehicleNo(pdaSignDto.getVehicleNo());
				row = expressWorkerStatusDao.insertSelective(worker);
			}
			
		}catch(Exception e){
			throw new PdaProcessException("退出时，更新司机工作状态失败"+e.getMessage());
		}
		return row;
	}
	
	/**
	 * Sets the pdaSignEntityDao.
	 * 
	 * @param pdaSignEntityDao the pdaSignEntityDao to see
	 */
	public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
		this.pdaSignEntityDao = pdaSignEntityDao;
	}

	/**
	 * Sets the dispatchOrderEntityDao.
	 * 
	 * @param dispatchOrderEntityDao the dispatchOrderEntityDao to see
	 */
	public void setDispatchOrderEntityDao(IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	/**
	 * Sets the arrivesheetDao.
	 * 
	 * @param arrivesheetDao the arrivesheetDao to see
	 */
	public void setArrivesheetDao(IArrivesheetDao arrivesheetDao) {
		this.arrivesheetDao = arrivesheetDao;
	}

	/**
	 * Sets the deliverbillDetailDao.
	 * 
	 * @param deliverbillDetailDao the deliverbillDetailDao to see
	 */
	public void setDeliverbillDetailDao(IDeliverbillDetailDao deliverbillDetailDao) {
		this.deliverbillDetailDao = deliverbillDetailDao;
	}

	/**
	 * Sets the employeeService.
	 * 
	 * @param employeeService the employeeService to see
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setExpressVehiclesService(IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}

	public void setExpressWorkerStatusDao(
			IExpressWorkerStatusDao expressWorkerStatusDao) {
		this.expressWorkerStatusDao = expressWorkerStatusDao;
	}

	public ILeasedVehicleService getLeasedVehicleService() {
		return leasedVehicleService;
	}

	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		this.leasedVehicleService = leasedVehicleService;
	}

	public void setPdaAddressCollectionService(
			IPdaAddressCollectionService pdaAddressCollectionService) {
		this.pdaAddressCollectionService = pdaAddressCollectionService;
	}

}
