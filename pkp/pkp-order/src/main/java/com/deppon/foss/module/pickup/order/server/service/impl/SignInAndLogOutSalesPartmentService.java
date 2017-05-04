/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/service/impl/SignInAndLogOutService.java
 * 
 * FILE NAME        	: SignInAndLogOutService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.pickup.order.api.server.dao.IExpressWorkerStatusDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutSalesPartmentService;
import com.deppon.foss.module.pickup.order.api.shared.define.ExpressWorkerStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.util.UUIDUtils;

/**
 * 调度解除POS签到
 * 
 * @author 273279-foss-liding
 */
public class SignInAndLogOutSalesPartmentService implements ISignInAndLogOutSalesPartmentService {

	// PDA签到DAO
	private IPdaSignEntityDao pdaSignEntityDao;
	//快递员工作状态Dao
	private IExpressWorkerStatusDao expressWorkerStatusDao;


	/**
	 * 解除司机签到.
	 * 
	 * @param entity
	 * 			deviceNo
	 * 				设备号
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			createTime
	 * 				创建时间
	 * 			unbundler
	 * 				解绑人
	 * 			unbundlerCode
	 * 				解绑人编码
	 * 			unbundleReason
	 * 				解绑原因
	 * 			unbundleTime
	 * 				解绑时间
	 * 			status
	 * 				状态
	 * 			userType
	 * 				用户类型
	 * @return the int
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-19 下午4:38:38
	 */
	@Transactional
	@Override
	public int handResolveBind(PdaSignEntity pdaEntity) {
		// 将PDA签到信息状态改为"已解绑"
		PdaSignEntity entity=pdaSignEntityDao.selectByPrimaryKey(pdaEntity.getId());
		if(PdaSignStatusConstants.BUNDLE.equals(entity.getStatus())){//14.7.17gcl  防止连续点击多次解绑按钮 重复提交
			entity.setStatus(PdaSignStatusConstants.UNBUNDLE);
			entity.setUnbundleTime(new Date());
			entity.setUnbundlerCode(FossUserContextHelper.getUserCode());
			entity.setUnbundler(FossUserContextHelper.getUserName());
			entity.setUnbundleReason(pdaEntity.getUnbundleReason());
			pdaSignEntityDao.updateByPrimaryKeySelective(entity);
			//2014.7.7 gaochunling 解绑成功后 更新快递员工作状态  native='N'
			entity=pdaSignEntityDao.selectByPrimaryKey(entity.getId());
			//2014.7.14 gcl AUTO-147 解绑成功后如果工作状态没有 执行新增
//		ExpressWorkerStatusEntity worker = expressWorkerStatusDao.selectOneByEmpcode(entity.getDriverCode());
			//14.7.16 根据签到id 查询工作状态
			ExpressWorkerStatusEntity worker = expressWorkerStatusDao.selectOneByPdaSignid(entity.getId());
			// 判断是否为空
			if(null != worker){
				worker.setActive("N");
				worker.setWorkStatus("STOP");
				worker.setModifyCode(entity.getUnbundlerCode());
				worker.setModifyTime(new Date());
				worker.setBusinessArea(ExpressWorkerStatusConstants.EXPRESS_ORDER_BUSINESS);
				worker.setVehicleNo(entity.getVehicleNo());///14.7.17 gcl 设置车牌号 AUTO-170
				expressWorkerStatusDao.updateByPrimaryKeySelective(worker);
			}else {
				worker = new ExpressWorkerStatusEntity();
				worker.setId(UUIDUtils.getUUID());
				worker.setEmpCode(entity.getDriverCode());
				worker.setEmpName(entity.getDriverName());
				worker.setPadNo(entity.getDeviceNo());
				worker.setWorkStatus("STOP");
				worker.setActive("N");
				worker.setCreateTime(new Date());
				worker.setModifyTime(new Date());
				worker.setCreateUsercode(entity.getUnbundlerCode());
				worker.setPdaSignId(entity.getId());
				worker.setBusinessArea(ExpressWorkerStatusConstants.EXPRESS_ORDER_BUSINESS);
				worker.setVehicleNo(entity.getVehicleNo());///14.7.17 gcl 设置车牌号
				expressWorkerStatusDao.insertSelective(worker);
			}
			// 结束  2014.7.7 gaochunling 解绑成功后 更新快递员工作状态  native='N'
			
		}
		// 将将基础资料中PDA设备状态的改为"未使用" 调用综合接口
		return 0;
	}


	/**
	 * Sets the pdaSignEntityDao.
	 * 
	 * @param pdaSignEntityDao the pdaSignEntityDao to set
	 */
	public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
		this.pdaSignEntityDao = pdaSignEntityDao;
	}

	public void setExpressWorkerStatusDao(
			IExpressWorkerStatusDao expressWorkerStatusDao) {
		this.expressWorkerStatusDao = expressWorkerStatusDao;
	}

	/**
	 * 根据条件查询签到信息.
	 * 
	 * @param dto
     * 			empPhone
     * 				司机手机
     * 			regionName
     * 				车辆区域
     * 			signBeginTime
     * 				签到开始时间
     * 			signEndTime
     * 				签到结束时间
     * 			originStatus
     * 				原状态
     * 			active
     * 				是否有效
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 273279-foss-liding
	 * @date 2016-03-0 2下午4:37:34
	 */
	@Override
	public List<PdaSignDto> querySalesPartmentSignedInfoByPage(PdaSignDto dto, int start, int limit) {
		//设置用户类型--NCI
		dto.setUserType(PdaSignStatusConstants.USER_TYPE_NCI);
		return pdaSignEntityDao.querySalesPartmentSignByDVByPage(dto, start, limit);
	}

	/**
	 * 自动解除司机签到.
	 * 
	 * @author 273279-foss-liding
	 * @date 2013-3-15 上午10:15:51
	 */
	@Override
	public void autoUnbundle() {
		PdaSignDto pdaSignDto = new PdaSignDto();
		pdaSignDto.setOriginStatus(PdaSignStatusConstants.BUNDLE);
		pdaSignDto.setStatus(PdaSignStatusConstants.UNBUNDLE);
		pdaSignDto.setUserType(PdaSignStatusConstants.USER_TYPE_NCI);
		pdaSignEntityDao.updatePosUnbundle(pdaSignDto);
	}
	
	/**
	 * 
	 * 查询签到信息的条数
	 * 
	 * @author 273279-foss-liding
	 * @date 2015-03-02 下午3:19:45
	 */
	@Override
	public Long querySalesPartmentSignedInfoCount(PdaSignDto dto) {
		//设置用户类型--NCI
		dto.setUserType(PdaSignStatusConstants.USER_TYPE_NCI);
		return pdaSignEntityDao.querySalesPartmentSignedInfoCount(dto);
		
	}

	@Override
	public List<PdaSignDto> querySalesPartmentInfoByPage(PdaSignDto dto,
			int start, int limit) {
		return pdaSignEntityDao.querySalesPartmentSignByDVByPage(dto, start, limit);
	}
	
}