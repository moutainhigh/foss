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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/AirAgencySignService.java
 * 
 * FILE NAME        	: AirAgencySignService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 *****************************************************************************
 */
package com.deppon.foss.module.pickup.sign.server.service.impl;

import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockJobDao;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignStockJobService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignStockLogService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockLogEntity;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 *签收反签收同步改异步库存接口
 * @author 
 *		foss-meiying
 * @date 
 *      2013-3-23 上午11:39:43
 * @since
 * @version
 */
public class SignStockJobService implements ISignStockJobService {
	/**
	 *  中转出库接口
	 */
	private IStockService stockService;
	/**
	 * 计算&调整走货路径类
	 * 接口
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	/**
	 * 签收反签收同步改异步库存接口
	 */
	private ISignStockJobDao signStockJobDao;
	/**
	 * 签收反签收同步改异步库存日志接口
	 */
	private ISignStockLogService signStockLogService;
	/**
	 * 
	 * 1.查询t_srv_sign_stock 中是否存在status = 0（待处理）的数据，
	 * 		若不存在，停止当前操作。若存在，判断是小于指定的记录数。如果小于本次循环完后，跳出循环。
	 * 2.	更新t_srv_sign_stock中的job_id=参数id和status=执行中(1)
	 * 		条件是 Inout_Type=出库 and status = 0(添加时的状态)
	 * 		and  AND rownum <= v_tempCount (具体数据 50)
	 * 3.	查询t_srv_sign_stock中job_id=参数id和status=执行中和Inout_Type=出库
	 * 		的记录（按时间升序排序）
	 * 4.	处理第2步中取出的记录数（调用中转入库接口），若成功，
	 * 		将记录移至t_srv_sign_stock_log,删除t_srv_sign_stock中status =1和job_id = 参数id的数据；
	 * 		若失败，则更新status为失败，并记录失败原因到exception_Message，记录保留在t_srv_sign_stock
	 * 5.	循环执行第2步直到没有记录，则结束本次job执行。
	 * @author foss-meiying
	 * @date 2013-3-21 下午5:58:34
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignStockJobService
	 * #inOutStock(java.lang.String)
	 */
	@Override
	@Transactional
	public void outStock(SignStockEntity signStock) {
		//添加签收明细信息
		InOutStockEntity inOutStock = new InOutStockEntity();
		// 运单号
		inOutStock.setWaybillNO(signStock.getWaybillNo());
		// 流水号
		inOutStock.setSerialNO(signStock.getSerialNo());
		// 部门编码
		inOutStock.setOrgCode(signStock.getStockOrgCode());
		// 操作人姓名
		inOutStock.setOperatorName(signStock.getOperator());
		// 操作人工号
		inOutStock.setOperatorCode(signStock.getOperatorCode());
		// 出入库类型 签收出库
		inOutStock.setInOutStockType(signStock.getInoutType());
		stockService.outStockPC(inOutStock);
		SignStockLogEntity signStockLogEntity = new SignStockLogEntity();
		BeanUtils.copyProperties(signStock,signStockLogEntity);//把左边的复制到右边
		signStockLogService.add(signStockLogEntity);
		signStockJobDao.deleteById(signStock.getId());//删除t_srv_sign_stock中的数据
	}
	
	/**
	 * 	1.查询t_srv_sign_stock 中是否存在status = 0（待处理）的数据，
	 * 		若不存在，停止当前操作。若存在，判断是小于指定的记录数。如果小于本次循环完后，跳出循环。
	 *  2.	更新t_srv_sign_stock中的job_id=参数id和status=执行中(1)
	 * 		条件是 Inout_Type=入库 and status = 0(添加时的状态)
	 * 		and  AND rownum <= v_tempCount (具体数据 50)
	 *  3.	查询t_srv_sign_stock中job_id=参数id和status=执行中和Inout_Type=入库
	 * 		的记录（按时间升序排序）
	 *  4.	处理第2步中取出的记录数，调用中转入库接口，和中转走货路径接口若成功，
	 * 		将记录移至t_srv_sign_stock_log,删除t_srv_sign_stock中status =1和job_id = 参数id的数据；
	 * 		若失败，则更新status为失败，并记录失败原因到exception_Message，记录保留在t_srv_sign_stock
	 *	5.	循环执行第2步直到没有记录，则结束本次job执行。
	 * @author foss-meiying
	 * @date 2013-3-23 上午9:25:47
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignStockJobService#inStock(java.lang.String)
	 */
	@Override
	@Transactional
	public void inStock(SignStockEntity signStock) {
		//添加签收明细信息
		InOutStockEntity inOutStock = new InOutStockEntity();
		// 运单号
		inOutStock.setWaybillNO(signStock.getWaybillNo());
		// 流水号
		inOutStock.setSerialNO(signStock.getSerialNo());
		// 部门编码
		inOutStock.setOrgCode(signStock.getStockOrgCode());
		// 操作人姓名
		inOutStock.setOperatorName(signStock.getOperator());
		// 操作人工号
		inOutStock.setOperatorCode(signStock.getOperatorCode());
		// 出入库类型 反签收入库
		inOutStock.setInOutStockType(signStock.getInoutType());
		stockService.inStockPC(inOutStock);//反签收入库
		//如果签收状态为全部签收  并且该运单不是整车运单
		if(SignConstants.SIGN_STATUS_ALL.equals
				(signStock.getSignStatus())&&FossConstants.NO.equals(signStock.getIsWholeVehicle())){
			calculateTransportPathService.rollBack(signStock.getWaybillNo(), signStock.getSerialNo(), null, TransportPathConstants.TRANSPORTPATH_STATUS_SIGNIN);
		}
		SignStockLogEntity signStockLogEntity = new SignStockLogEntity();
		BeanUtils.copyProperties(signStock,signStockLogEntity);//把左边的复制到右边
		signStockLogService.add(signStockLogEntity);
		signStockJobDao.deleteById(signStock.getId());//删除t_srv_sign_stock中的数据
			
	}
	/**
	 * 添加一条记录
	 * @author foss-meiying
	 * @date 2013-3-23 上午11:36:51
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignStockJobService
	 * #add(com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity)
	 */
	@Override
	public int add(SignStockEntity record) {
		return signStockJobDao.insert(record);
	}
	/**
	 * 有选择性的添加数据
	 * @author foss-meiying
	 * @date 2013-3-23 上午11:36:54
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignStockJobService
	 * #addSelective(com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity)
	 */
	@Override
	public int addSelective(SignStockEntity record) {
		return signStockJobDao.insertSelective(record);
	}
	/**
	 * Sets the 中转出库接口.
	 *
	 * @param stockService the new 中转出库接口
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * Sets the 计算&调整走货路径类 接口.
	 *
	 * @param calculateTransportPathService the new 计算&调整走货路径类 接口
	 */
	public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	
	/**
	 * Sets the 签收反签收同步改异步库存接口.
	 *
	 * @param signStockJobDao the new 签收反签收同步改异步库存接口
	 */
	public void setSignStockJobDao(ISignStockJobDao signStockJobDao) {
		this.signStockJobDao = signStockJobDao;
	}

	/**
	 * Sets the 签收反签收同步改异步库存日志接口.
	 *
	 * @param signStockLogService the new 签收反签收同步改异步库存日志接口
	 */
	public void setSignStockLogService(ISignStockLogService signStockLogService) {
		this.signStockLogService = signStockLogService;
	}
	
	
	
}