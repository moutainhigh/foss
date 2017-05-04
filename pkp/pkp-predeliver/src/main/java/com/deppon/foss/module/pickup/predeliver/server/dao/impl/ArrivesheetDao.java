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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/ArrivesheetDao.java
 * 
 * FILE NAME        	: ArrivesheetDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.pda.api.shared.dto.ValidateArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetActualFreightDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillAddPropertyDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivesheetDeliverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AutoTaskDTO;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * <p>
 * 到达联管理Dao实现类<br />
 * </p>
 * 
 * @title ArrivesheetDao.java
 * @package com.deppon.foss.module.pickup.predeliver.server.dao.impl
 * @author ibm-lizhiguo
 * @version 0.1 2013-3-19
 */
public class ArrivesheetDao extends iBatis3DaoImpl implements IArrivesheetDao {

	//到达联管理Name space
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity.";

	/**
	 * 生成到达联
	 * 
	 * @author dp-dengtingting
	 * @param waybillNo; 运单号
	 * @param arrivesheetNo; 到达联编号
	 * @param deliverymanName; 提货人名称
	 * @param identifyType; 证件类型
	 * @param identifyCode; 证件号码
	 * @param situation; 签收情况
	 * @param arriveSheetGoodsQty; 到达联件数
	 * @param signGoodsQty; 签收件数
	 * @param signNote; 签收备注
	 * @param signTime; 签收时间
	 * @param isPrinted; 是否打印
	 * @param printtimes; 打印次数
	 * @param createUserName; 创建人
	 * @param createUserCode; 创建人编码
	 * @param createOrgName; 创建部门
	 * @param createOrgCode; 创建部门编码
	 * @param createTime; 创建时间
	 * @param status; 到达联状态
	 * @param isPdaSign; 是否PDA签到
	 * @param active; 是否有效
	 * @param isSentRequired; 是否必送货
	 * @param isNeedInvoice; 是否需要发票
	 * @param preNoticeContent; 提前通知内容
	 * @param deliverRequire; 送货要求
	 * @param isRfcing; 是否审批中
	 * @param tagNumber; 标签编号
	 * @param operateTime; 签收操作时间
	 * @param operator; 操作人
	 * @param operatorCode; 操作人编码
	 * @param operateOrgName; 操作部门名称
	 * @param operateOrgCode; 操作部门编码
	 * @param destroyed; 是否作废
	 * @param modifyTime; 有效日期
	 * @param oldArriveSheetGoodsQty; 并发控制使用
	 * @param oldStatus; 并发控制使用
	 * @date 2012-10-13 上午10:51:42
	 */
	@Override
	public int addArriveSheetData(ArriveSheetEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		if(entity != null && entity.getCreateTime() != null){
			entity.setModifyTime(entity.getCreateTime());
		}else{
			entity.setCreateTime(new Date());
			entity.setModifyTime(entity.getCreateTime());
		}
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", entity);
	}

	/**
	 * 作废、激活、修改到达联
	 * 
	 * @author dp-dengtingting
	 * @param waybillNo; 运单号
	 * @param arrivesheetNo; 到达联编号
	 * @param deliverymanName; 提货人名称
	 * @param identifyType; 证件类型
	 * @param identifyCode; 证件号码
	 * @param situation; 签收情况
	 * @param arriveSheetGoodsQty; 到达联件数
	 * @param signGoodsQty; 签收件数
	 * @param signNote; 签收备注
	 * @param signTime; 签收时间
	 * @param isPrinted; 是否打印
	 * @param printtimes; 打印次数
	 * @param createUserName; 创建人
	 * @param createUserCode; 创建人编码
	 * @param createOrgName; 创建部门
	 * @param createOrgCode; 创建部门编码
	 * @param createTime; 创建时间
	 * @param status; 到达联状态
	 * @param isPdaSign; 是否PDA签到
	 * @param active; 是否有效
	 * @param isSentRequired; 是否必送货
	 * @param isNeedInvoice; 是否需要发票
	 * @param preNoticeContent; 提前通知内容
	 * @param deliverRequire; 送货要求
	 * @param isRfcing; 是否审批中
	 * @param tagNumber; 标签编号
	 * @param operateTime; 签收操作时间
	 * @param operator; 操作人
	 * @param operatorCode; 操作人编码
	 * @param operateOrgName; 操作部门名称
	 * @param operateOrgCode; 操作部门编码
	 * @param destroyed; 是否作废
	 * @param modifyTime; 有效日期
	 * @param oldArriveSheetGoodsQty; 并发控制使用
	 * @param oldStatus; 并发控制使用
	 * @date 2012-10-13 上午10:53:03
	 */
	@Override
	public int updateArriveSheetData(ArriveSheetEntity entity) {
		entity.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE + "updateArriveById", entity);
	}

	/**
	 * 根据条件查询到达联
	 * 
	 * @author dp-dengtingting
	 * @param waybillNo; 运单号
	 * @param arrivesheetNo; 到达联编号
	 * @param deliverymanName; 提货人名称
	 * @param identifyType; 证件类型
	 * @param identifyCode; 证件号码
	 * @param situation; 签收情况
	 * @param arriveSheetGoodsQty; 到达联件数
	 * @param signGoodsQty; 签收件数
	 * @param signNote; 签收备注
	 * @param signTime; 签收时间
	 * @param isPrinted; 是否打印
	 * @param printtimes; 打印次数
	 * @param createUserName; 创建人
	 * @param createUserCode; 创建人编码
	 * @param createOrgName; 创建部门
	 * @param createOrgCode; 创建部门编码
	 * @param createTime; 创建时间
	 * @param status; 到达联状态
	 * @param isPdaSign; 是否PDA签到
	 * @param active; 是否有效
	 * @param isSentRequired; 是否必送货
	 * @param isNeedInvoice; 是否需要发票
	 * @param preNoticeContent; 提前通知内容
	 * @param deliverRequire; 送货要求
	 * @param isRfcing; 是否审批中
	 * @param tagNumber; 标签编号
	 * @param operateTime; 签收操作时间
	 * @param operator; 操作人
	 * @param operatorCode; 操作人编码
	 * @param operateOrgName; 操作部门名称
	 * @param operateOrgCode; 操作部门编码
	 * @param destroyed; 是否作废
	 * @param modifyTime; 有效日期
	 * @param oldArriveSheetGoodsQty; 并发控制使用
	 * @param oldStatus; 并发控制使用
	 * @date 2012-10-13 上午10:54:42
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveSheetDto> queryArriveSheetData(ArriveSheetDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<ArriveSheetDto>) this.getSqlSession().selectList(NAMESPACE + "queryArriveSheetData", dto, rowBounds);
	}

	/***
	 * 
	 * <p>
	 * 签收出库---根据条件查询到达联
	 * </p>
	 * 
	 * @author foss-meiying
	 * @date 2012-10-17 下午3:58:39
	 * @param dto
	 * @param start
	 * @param limit
	 * @param waybillNo; 运单号
	 * @param arrivesheetNo; 到达联编号
	 * @param deliverymanName; 提货人名称
	 * @param identifyType; 证件类型
	 * @param identifyCode; 证件号码
	 * @param situation; 签收情况
	 * @param arriveSheetGoodsQty; 到达联件数
	 * @param signGoodsQty; 签收件数
	 * @param signNote; 签收备注
	 * @param signTime; 签收时间
	 * @param isPrinted; 是否打印
	 * @param printtimes; 打印次数
	 * @param createUserName; 创建人
	 * @param createUserCode; 创建人编码
	 * @param createOrgName; 创建部门
	 * @param createOrgCode; 创建部门编码
	 * @param createTime; 创建时间
	 * @param status; 到达联状态
	 * @param isPdaSign; 是否PDA签到
	 * @param active; 是否有效
	 * @param isSentRequired; 是否必送货
	 * @param isNeedInvoice; 是否需要发票
	 * @param preNoticeContent; 提前通知内容
	 * @param deliverRequire; 送货要求
	 * @param isRfcing; 是否审批中
	 * @param tagNumber; 标签编号
	 * @param operateTime; 签收操作时间
	 * @param operator; 操作人
	 * @param operatorCode; 操作人编码
	 * @param operateOrgName; 操作部门名称
	 * @param operateOrgCode; 操作部门编码
	 * @param destroyed; 是否作废
	 * @param modifyTime; 有效日期
	 * @param oldArriveSheetGoodsQty; 并发控制使用
	 * @param oldStatus; 并发控制使用
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<SignArriveSheetDto> queryArriveSheetInfoByParams(SignDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<SignArriveSheetDto>) this.getSqlSession().selectList(NAMESPACE + "queryArrivesheetList", dto, rowBounds);
	}

	/***
	 * 
	 * <p>
	 * 签收出库---根据条件分页查询到达联总数
	 * </p>
	 * 
	 * @author foss-meiying
	 * @date 2012-10-18 下午2:16:05
	 * @param dto
	 * @param id; id
	 * @param waybillNo; 运单号
	 * @param arrivesheetNo; 到达联编号
	 * @param receiveCustomerName; 收货人
	 * @param receiveCustomerPhone; 收货人电话
	 * @param receiveCustomerMobilephone; 收货人手机号码
	 * @param status; 到达联状态
	 * @param active; 到达联是否有效
	 * @param[] ArriveSheetstatus; 存放多个到达联状态
	 * @param settleStatus; 运单结清状态
	 * @param settleTimeStart; 结清时间起
	 * @param settleTimeEnd; 结清时间止
	 * @param lastLoadOrgCode; 最终配载部门（判断是否为本部门）
	 * @param isWholeVehicle; 是否整车运单
	 * @param productCode; 运输性质
	 * @param deliverymanName; 提货人名称
	 * @param identifyType; 证件类型
	 * @param identifyCode; 证件号码
	 * @param destroyed; 是否作废
	 * @param arriveSheetGoodsQty; 到达联件数
	 * @param endStockOrgCode; 最后库存code
	 * @param goodsAreaCode; 库区
	 * @param orderNo; 订单号
	 * @return
	 * @see
	 */
	public Long queryArriveSheetInfoCountByParams(SignDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotalCount", dto);
	}

	/**
	 * 查询到达联分页模式
	 * 
	 * @author dp-dengtingting
	 * @param id; 主键id
	 * @param waybillNo; 运单号
	 * @param arrivesheetNo; 到达联编号
	 * @param printtimes; 打印次数
	 * @param status; 到达联状态
	 * @param arriveSheetGoodsQty; 到达联件数
	 * @param createUserName; 创建人
	 * @param goodsQtyTotal; 货物总件数
	 * @param goodsName; 货物名称
	 * @param arrangeGoodsGty; 派送单件数
	 * @param receiveCustomerName; 收货客户名称
	 * @param receiveCustomerMobilephone; 收货客户手机
	 * @param createBeginTime; 创建开始时间
	 * @param createEndTime; 创建结束时间
	 * @param isPrinted; 是否打印
	 * @param arriveNotoutGoodsQty; 到达未出库件数 == 库存件数
	 * @param active; 是否有效
	 * @param waybillActive; 运单是否有效
	 * @param isRfcing; 是否审批中
	 * @param arriveSheetStatus; 到达联状态集合
	 * @param destroyed; 是否作废
	 * @param deliverbillNo; 派送单编号
	 * @param lastLoadOrgCode; 最终配载部门
	 * @date 2012-10-19 下午6:04:44
	 */
	@Override
	public Long getArriveSheetCount(ArriveSheetDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getArriveSheetCount", dto);
	}

	/**
	 * 根据运单号查询已到达运单信息
	 * 
	 * @author 097972-foss-dengtingting
	 * 
	 * @date 2012-10-29 下午2:26:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveSheetWaybillDto> queryWaybillDetailByWaybill(ArriveSheetWaybillDto dto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryArriveSheetByWaybill", dto);
	}

	/**
	 * 根据交接单号查询已到达运单信息
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-10-29 下午2:29:00
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveSheetWaybillDto> queryWaybillDetailByHandoverNo(ArriveSheetWaybillDto dto) {
		return (List<ArriveSheetWaybillDto>) this.getSqlSession().selectList(NAMESPACE + "queryArriveSheeetByHandoverNo", dto);
	}

	/**
	 * 根据预派送单号查询已到达运单信息
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-10-29 下午2:30:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveSheetWaybillDto> queryWaybillDetailByDeliverbillId(ArriveSheetWaybillDto dto) {
		return (List<ArriveSheetWaybillDto>) this.getSqlSession().selectList(NAMESPACE + "queryArriveSheeetByDeliverbillId", dto);
	}

	/**
	 * 根据客户信息查询已到达运单信息
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-10-29 下午2:31:25
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveSheetWaybillDto> queryWaybillDetailByCustomer(ArriveSheetWaybillDto dto) {
		return (List<ArriveSheetWaybillDto>) this.getSqlSession().selectList(NAMESPACE + "queryArriveSheeetByCustomer", dto);
	}

	/***
	 * 根据运单号判断到达联表里是否存在签收。
	 * 
	 * @author foss-meiying
	 * @date 2012-10-25 上午11:38:56
	 * @return
	 * @see
	 */
	@Override
	public Long queryArriveExistSign(ArriveSheetEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryArriveExistSign", entity);
	}

	/***
	 * 修改到达联。根据到达联编号 active = 'Y' AND DESTROYED是否作废为N 未作废
	 * 
	 * @author foss-meiying
	 * @date 2012-10-26 下午5:34:25
	 * @param entity
	 * @return
	 * @see
	 */
	@Override
	public int updateArriveSheetByArrivesheetNo(ArriveSheetEntity entity) {
		entity.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE + "updateByArrivesheetNo", entity);
	}

	/**
	 * 根据条件查询到达联信息。
	 * 
	 * @author foss-meiying
	 * @date 2012-11-2 上午10:31:55
	 * @return
	 * @see
	 */
	@Override
	public ArriveSheetEntity queryArriveSheetByEntity(ArriveSheetEntity entity) {
		List<ArriveSheetEntity> arriveSheetEntitys = queryArriveSheetListByEntity(entity);
		return CollectionUtils.isEmpty(arriveSheetEntitys) ? null : arriveSheetEntitys.get(0);
	}

	/**
	 * 根据条件查询到达联集合
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-25 上午11:02:56
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveSheetEntity> queryArriveSheetListByEntity(ArriveSheetEntity entity) {
		if(StringUtils.isEmpty(entity.getWaybillNo())&&StringUtils.isEmpty(entity.getId())&&StringUtils.isEmpty(entity.getArrivesheetNo())){
			return null;
		}
		return this.getSqlSession().selectList(NAMESPACE + "queryArriveSheetByEntity", entity);
	}

	/**
	 * 根据运单号查询到达联信息集合
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-13 上午11:17:52
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveSheetEntity> queryArriveSheetByWaybillNo(ArriveSheetEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE + "queryArriveSheetByWayBillNo", entity);
	}

	/**
	 * 根据运单号查询最大值到达联编号
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-13 上午11:17:52
	 */
	@Override
	public Long queryMaxArriveSheetNoByWayBillNo(String waybillNo) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryMaxArriveSheetNoByWayBillNo", waybillNo);
	}

	/**
	 * 根据到达联状态 生命周期查询到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-14 上午11:46:18
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArriveSheetEntity> queryArriveSheetByLifeCycle(ArriveSheetDto dto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryArriveSheetByLifeCycle", dto);
	}

	/**
	 * 根据到达联编号、状态获得到达联条数
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-27 下午5:45:38
	 */
	@Override
	public Integer countArriveSheetByNo(ArriveSheetEntity entity) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "getCountArriveSheetByNo", entity);
	}

	/**
	 * 根据到达联编号更改打印次数
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-4 上午10:40:19
	 */
	@Override
	public Integer updatePrintTimesByArrivesheetNo(ArriveSheetEntity entity) {
		entity.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE + "updatePrintTimesByArrivesheetNo", entity);
	}

	/**
	 * 根据运单号查询已打印达到联的条数
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-7 下午1:51:36
	 */
	@Override
	public Integer getCountArriveSheetByWaybillNo(ArriveSheetEntity entity) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "getCountArriveSheetByWaybillNo", entity);
	}

	/**
	 * 根据到达联编号，查询运单信息
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-12 下午3:03:06
	 */
	@Override
	public ValidateArriveSheetDto queryWaybillByArriveSheetNo(ArriveSheetDto condition) {
		return (ValidateArriveSheetDto) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillByArriveSheetNo", condition);
	}

	/**
	 * 根据运单号、最终库存部门CODE 查询到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-14 上午11:30:16
	 */
	@Override
	public Integer queryArriveSheetByWaybillNo(ArriveSheetActualFreightDto dto) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "queryArriveSheetByWayblillNo", dto);
	}

	/**
	 * 根据ID更新arrivesheet
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-14 下午2:15:09
	 */
	@Override
	public Integer updateByPrimaryKeySelective(ArriveSheetEntity entity) {
		entity.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective", entity);
	}

	/**
	 * 
	 * <p>
	 * 根据运单号更新到达联的证件号，提货人等信息<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-8
	 * @param entity
	 * @return Integer
	 */
	@Override
	public Integer updateArriveSheetByWaybillNo(ArriveSheetEntity entity) {
		entity.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE + "updateArriveSheetByWaybillNo", entity);
	}

	/**
	 * 根据条件查询到达联的派送记录 返回 运单、派送单、到达联上相关信息
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-18 下午8:33:05
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArrivesheetDeliverDto> queryArriveSheetByDriverCode(ArrivesheetDeliverDto dto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryArriveSheetByDriverCode", dto);
	}
	
	/**
	 * 根据条件查询到达联的派送记录 返回 运单、派送单、到达联上相关信息（快递）
	 * 
	 * @author WangQianJin
	 * @date 2014-06-06
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArrivesheetDeliverDto> queryExpressArriveSheetByDriverCode(ArrivesheetDeliverDto dto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressArriveSheetByDriverCode", dto);
	}	

	/*
	 * 根据条件查询到达联的派送记录 返回 运单、派送单、到达联上相关信息
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-18 下午8:33:05
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ActualFreightDto> queryWaybillAllQty(ArriveSheetWaybillDto dto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillAllQty", dto);
	}

	/**
	 * 
	 * 根据到达联编号查运单运输性质 是否整车运单, 到达联id,提货人证件号码,证件类型,到达联件数, 最终配载部门,订单号，提货方式等运单的部分信息
	 * 
	 * @author foss-meiying
	 * @date 2013-1-6 下午7:20:07
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao
	 *      #queryPartWaybillpartArrivesheet
	 *      (com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Override
	public SignDto queryPartWaybillpartArrivesheet(ArriveSheetEntity entity) {
		return (SignDto) this.getSqlSession().selectOne(NAMESPACE + "queryPartWaybillPartArriveSheet", entity);
	}

	/**
	 * 
	 * <p>
	 * 通过ID获得到达联<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-20
	 * @param id
	 * @return ArriveSheetEntity
	 */
	@Override
	public ArriveSheetEntity queryArriveSheetById(String id) {
		return (ArriveSheetEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

	/**
	 * 
	 * <p>
	 * 获得初次到达联信息<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-25
	 * @param arrivesheetNo
	 * @return ArriveSheetEntity
	 */
	@Override
	public ArriveSheetEntity queryArriveSheetCreateTime(String arrivesheetNo) {
		return (ArriveSheetEntity) this.getSqlSession().selectOne(NAMESPACE + "queryArriveSheetCreateTime", arrivesheetNo);
	}

	/**
	 * 生成到达联
	 * 
	 * @author foss-meiying
	 * @date 2013-1-29 上午9:54:42
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao#addArriveSheet(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Override
	public ArriveSheetEntity addArriveSheet(ArriveSheetEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		if(entity != null && entity.getCreateTime() != null){
			entity.setModifyTime(entity.getCreateTime());
		}else{
			entity.setCreateTime(new Date());
			entity.setModifyTime(entity.getCreateTime());
		}
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", entity) > 0 ? entity : null;
	}

	@Override
	public ArriveSheetWaybillAddPropertyDto queryPrintInfoByWaybillNo(WaybillEntity waybillEntity) {
		return (ArriveSheetWaybillAddPropertyDto) this.getSqlSession().selectOne(NAMESPACE + "queryPrintInfoByWaybillNo", waybillEntity);
	}

	/**
	 * 
	 * 更改到达部门插入临时表表记录
	 * 
	 * @param dto
	 * @author ibm-wangfei
	 * @date Mar 5, 2013 10:42:56 AM
	 */
	@Override
	public void insertONTempForPKP(AutoTaskDTO dto) {
		dto.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAMESPACE + "insertONTempForPKP", dto);
	}

	/**
	 * 
	 * 单件出入库插入pkp临时表记录
	 * 
	 * @param dto
	 * @author ibm-wangfei
	 * @date Feb 25, 2013 1:42:22 PM
	 */
	@Override
	public void insertIOTempForPKP(AutoTaskDTO dto) {
		dto.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAMESPACE + "insertIOTempForPKP", dto);
	}

	/**
	 * 
	 * 新增到达联操作日志表
	 * 
	 * @param arriveSheetLogEntity
	 * @author ibm-wangfei
	 * @date Jul 8, 2013 4:06:03 PM
	 */
	@Override
	public void insertArriveSheetLogEntity(ArriveSheetLogEntity arriveSheetLogEntity) {
		arriveSheetLogEntity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAMESPACE + "insertArriveSheetLogEntity", arriveSheetLogEntity);
	}

	/**
	 * 
	 * 按照入库时间进行查询可打印到达联
	 * 
	 * @param dto
	 * @return
	 * @author ibm-wangfei
	 * @date Jul 9, 2013 11:41:29 AM
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ArriveSheetWaybillDto> queryWaybillDetailByInStockTime(ArriveSheetWaybillDto dto) {
		return (List<ArriveSheetWaybillDto>) this.getSqlSession().selectList(NAMESPACE + "queryWaybillDetailByInStockTime", dto);
	}

	/**
	 * 根据到达时间查询可打印到达联
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ArriveSheetWaybillDto> queryWaybillDetailByArriveTime(
			ArriveSheetWaybillDto dto) {
		String sql = NAMESPACE + "queryWaybillDetailByArriveTime";
		return (List<ArriveSheetWaybillDto>)getSqlSession().selectList(sql, dto);
	}	
	/**
	 * 根据运单号集合修改合送编码 跟送货要求
	 * @param entity
	 * @return
	 */
	@Override
	public Integer updateTogetherSendCodeByWaybillNos(ArriveSheetDto dto) {
		return this.getSqlSession().update(NAMESPACE + "updateTogetherSendCodeByWaybillNos", dto);
	}
	
	
	/***
	 * 
	 * <p>
	 * 签收出库---根据条件分页查询到达联总数 快递相关
	 * </p>
	 * 
	 * @author foss-yuting
	 * @date 2014-10-09 下午2:16:05
	 * @param dto
	 * @param id; id
	 * @param waybillNo; 运单号
	 * @param arrivesheetNo; 到达联编号
	 * @param receiveCustomerName; 收货人
	 * @param receiveCustomerPhone; 收货人电话
	 * @param receiveCustomerMobilephone; 收货人手机号码
	 * @param status; 到达联状态
	 * @param active; 到达联是否有效
	 * @param[] ArriveSheetstatus; 存放多个到达联状态
	 * @param settleStatus; 运单结清状态
	 * @param settleTimeStart; 结清时间起
	 * @param settleTimeEnd; 结清时间止
	 * @param lastLoadOrgCode; 最终配载部门（判断是否为本部门）
	 * @param isWholeVehicle; 是否整车运单
	 * @param productCode; 运输性质
	 * @param deliverymanName; 提货人名称
	 * @param identifyType; 证件类型
	 * @param identifyCode; 证件号码
	 * @param destroyed; 是否作废
	 * @param arriveSheetGoodsQty; 到达联件数
	 * @param endStockOrgCode; 最后库存code
	 * @param goodsAreaCode; 库区
	 * @param orderNo; 订单号
	 * @return
	 * @see
	 */
	@Override
	public Long queryArriveSheetInfoCountByParamsExp(SignDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotalCountExp", dto);
	}


	/***
	 * 
	 * <p>
	 * 签收出库---根据条件查询到达联  快递相关
	 * </p>
	 * 
	 * @author foss-yuting
	 * @date 2014-10-9 下午3:58:39
	 * @param dto
	 * @param start
	 * @param limit
	 * @param waybillNo; 运单号
	 * @param arrivesheetNo; 到达联编号
	 * @param deliverymanName; 提货人名称
	 * @param identifyType; 证件类型
	 * @param identifyCode; 证件号码
	 * @param situation; 签收情况
	 * @param arriveSheetGoodsQty; 到达联件数
	 * @param signGoodsQty; 签收件数
	 * @param signNote; 签收备注
	 * @param signTime; 签收时间
	 * @param isPrinted; 是否打印
	 * @param printtimes; 打印次数
	 * @param createUserName; 创建人
	 * @param createUserCode; 创建人编码
	 * @param createOrgName; 创建部门
	 * @param createOrgCode; 创建部门编码
	 * @param createTime; 创建时间
	 * @param status; 到达联状态
	 * @param isPdaSign; 是否PDA签到
	 * @param active; 是否有效
	 * @param isSentRequired; 是否必送货
	 * @param isNeedInvoice; 是否需要发票
	 * @param preNoticeContent; 提前通知内容
	 * @param deliverRequire; 送货要求
	 * @param isRfcing; 是否审批中
	 * @param tagNumber; 标签编号
	 * @param operateTime; 签收操作时间
	 * @param operator; 操作人
	 * @param operatorCode; 操作人编码
	 * @param operateOrgName; 操作部门名称
	 * @param operateOrgCode; 操作部门编码
	 * @param destroyed; 是否作废
	 * @param modifyTime; 有效日期
	 * @param oldArriveSheetGoodsQty; 并发控制使用
	 * @param oldStatus; 并发控制使用
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignArriveSheetDto> queryArriveSheetInfoByParamsExp(
			SignDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<SignArriveSheetDto>) this.getSqlSession().selectList(NAMESPACE + "queryArrivesheetListExp", dto, rowBounds);
	}
	
	/** 根据条件查询到最新一次 达联信息 (分批签收用)。
	 * DMANA-9716
	 * @author 231438-foss-chenjunying
	 * @date 2015-03-25 上午09:06:55
	 * @return ArriveSheetEntity
	 * @see
	 */
	public ArriveSheetEntity queryArriveSheetBySignTimeDesc(ArriveSheetDto dto) {
		@SuppressWarnings("unchecked")
		List<ArriveSheetEntity> arriveSheetEntitys = this.getSqlSession().selectList(NAMESPACE + "queryArriveSheetBySignTimeDesc", dto);
		return CollectionUtils.isEmpty(arriveSheetEntitys) ? null : arriveSheetEntitys.get(0);
	}
	
	/***
	 * 
	 * <p>
	 * 合伙人签收出库---根据条件分页查询到达联总数
	 * </p>
	 * 
	 * @author foss-239284
	 * @param dto
	 * @param id; id
	 * @param waybillNo; 运单号
	 * @param arrivesheetNo; 到达联编号
	 * @param receiveCustomerName; 收货人
	 * @param receiveCustomerPhone; 收货人电话
	 * @param receiveCustomerMobilephone; 收货人手机号码
	 * @param status; 到达联状态
	 * @param active; 到达联是否有效
	 * @param[] ArriveSheetstatus; 存放多个到达联状态
	 * @param settleStatus; 运单结清状态
	 * @param settleTimeStart; 结清时间起
	 * @param settleTimeEnd; 结清时间止
	 * @param lastLoadOrgCode; 最终配载部门（判断是否为本部门）
	 * @param isWholeVehicle; 是否整车运单
	 * @param productCode; 运输性质
	 * @param deliverymanName; 提货人名称
	 * @param identifyType; 证件类型
	 * @param identifyCode; 证件号码
	 * @param destroyed; 是否作废
	 * @param arriveSheetGoodsQty; 到达联件数
	 * @param endStockOrgCode; 最后库存code
	 * @param goodsAreaCode; 库区
	 * @param orderNo; 订单号
	 * @return
	 * @see
	 */
	public Long queryPtpArriveSheetInfoCountByParams(SignDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getPtpTotalCount", dto);
	}
	
	/***
	 * 
	 * <p>
	 *合伙人零担 签收出库---根据条件查询到达联
	 * </p>
	 * 
	 * @author foss-239284
	 * @date 2012-10-17 下午3:58:39
	 * @param dto
	 * @param start
	 * @param limit
	 * @param waybillNo; 运单号
	 * @param arrivesheetNo; 到达联编号
	 * @param deliverymanName; 提货人名称
	 * @param identifyType; 证件类型
	 * @param identifyCode; 证件号码
	 * @param situation; 签收情况
	 * @param arriveSheetGoodsQty; 到达联件数
	 * @param signGoodsQty; 签收件数
	 * @param signNote; 签收备注
	 * @param signTime; 签收时间
	 * @param isPrinted; 是否打印
	 * @param printtimes; 打印次数
	 * @param createUserName; 创建人
	 * @param createUserCode; 创建人编码
	 * @param createOrgName; 创建部门
	 * @param createOrgCode; 创建部门编码
	 * @param createTime; 创建时间
	 * @param status; 到达联状态
	 * @param isPdaSign; 是否PDA签到
	 * @param active; 是否有效
	 * @param isSentRequired; 是否必送货
	 * @param isNeedInvoice; 是否需要发票
	 * @param preNoticeContent; 提前通知内容
	 * @param deliverRequire; 送货要求
	 * @param isRfcing; 是否审批中
	 * @param tagNumber; 标签编号
	 * @param operateTime; 签收操作时间
	 * @param operator; 操作人
	 * @param operatorCode; 操作人编码
	 * @param operateOrgName; 操作部门名称
	 * @param operateOrgCode; 操作部门编码
	 * @param destroyed; 是否作废
	 * @param modifyTime; 有效日期
	 * @param oldArriveSheetGoodsQty; 并发控制使用
	 * @param oldStatus; 并发控制使用
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<SignArriveSheetDto> queryPtpArriveSheetInfoByParams(SignDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<SignArriveSheetDto>) this.getSqlSession().selectList(NAMESPACE + "queryPtpArrivesheetList", dto, rowBounds);
	}

	/* 
	 * 合伙人快递自提签收出库---根据条件查询到达联总数
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao#queryPtpExpArriveSheetInfoCountByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto)
	 */
	@Override
	public Long queryPtpExpArriveSheetInfoCountByParams(SignDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryPtpExpArriveSheetInfoCountByParams", dto);
	}

	/* 
	 * 合伙人快递自提签收出库---根据条件查询到达联信息集合
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao#queryPtpExpArriveSheetInfoByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignArriveSheetDto> queryPtpExpArriveSheetInfoByParams(
			SignDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryPtpExpArriveSheetInfoByParams", dto,rowBounds);
	}
	
}