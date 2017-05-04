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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/NotifyCustomerDao.java
 * 
 * FILE NAME        	: NotifyCustomerDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.InvoiceInfomationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyMultibillDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @功能： 运单通知DAO实现类
 * @author ibm-wangfei
 * @date 2012-10-11 15:21:39
 * @version 0.1
 */
public class NotifyCustomerDao extends iBatis3DaoImpl implements INotifyCustomerDao {
	/**
	 * 运单通知实体命名空间
	 */
	private static final String NOTIFYCATION_NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity.";
	/**
	 * 运单通知发票实体命名空间
	 */
	private static final String INVOICE_INFOMATION_ENTITY_NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.InvoiceInfomationEntity.";
	/**
	 * 运单综合查询命名空间
	 */
	private static final String PREDELIVER_NOTIFY_NAMESPACE = "foss.pickup.predeliver.notify.";
	/**
	 * 运单通知INSERT
	 */
	private static final String INSERT = "insertSelective";
	/**
	 * 运单记录查询 -- 运单号
	 */
	private static final String SELECT_BY_WAYBILLNO = "selectByWaybillNo";
	/**
	 * 运单记录查询 -- 交接单号
	 */
	private static final String SELECT_BY_HANDOVERNO = "selectByHandoverNo";
	/**
	 * 运单记录查询 -- 车次、预计到货时间
	 */
	private static final String SELECT_BY_VEHICLEASSEMBLENO = "selectByVehicleAssembleNo";
	/**
	 * 运单记录查询 -- 默认
	 */
	private static final String SELECT_BY_STOCKINFO = "selectByStockInfo";
	/**
	 * 运单记录查询 -- 默认
	 */
	private static final String SELECT_DETAIL_BY_WAYBILLNO = "selectDetailByWayBillNo";
	/**
	 * 更新运单附属表 -- 根据运单编号
	 */
	private static final String UPDATE_BY_WAYBILLNO = "updateByWaybillNo";

	/**
	 * 插入运单通知记录.
	 * 
	 * @param notificationEntity the notification entity
	 * @return the int
	 * @author ibm-wangfei
	 * @date Oct 22, 2012 12:35:57 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#addNotificationEntity(com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity)
	 */
	@Override
	public int addNotificationEntity(NotificationEntity notificationEntity) {
		notificationEntity.setId(UUIDUtils.getUUID());// id
		//判断操作时间是否为空
		if(notificationEntity.getOperateTime() == null){
			//创建时间
			notificationEntity.setOperateTime(new Date());
			//修改时间
			notificationEntity.setModifyDate(notificationEntity.getOperateTime());
		}else{
			//修改时间
			notificationEntity.setModifyDate(notificationEntity.getOperateTime());
		}
		return this.getSqlSession().insert(NOTIFYCATION_NAMESPACE + INSERT, notificationEntity);
	}

	/**
	 * 插入运单通知发票信息.
	 * 
	 * @param invoiceInfomationEntity the invoice infomation entity
	 * @return the int
	 * @author ibm-wangfei
	 * @date Oct 22, 2012 12:36:09 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#addInvoiceInfomationEntity(com.deppon.foss.module.pickup.predeliver.api.shared.domain.InvoiceInfomationEntity)
	 */
	@Override
	public int addInvoiceInfomationEntity(InvoiceInfomationEntity invoiceInfomationEntity) {
		invoiceInfomationEntity.setId(UUIDUtils.getUUID()); // ID
		return this.getSqlSession().insert(INVOICE_INFOMATION_ENTITY_NAMESPACE + INSERT, invoiceInfomationEntity);
	}

	/**
	 * 根据运单号，查询运单通知记录列表.
	 * 
	 * @param notifycation the notifycation
	 * @return the list
	 * @author ibm-wangfei
	 * @date Oct 22, 2012 12:36:18 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#selectByParams(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationEntity> queryNotificationsByParam(NotificationEntity notifycation) {
		return this.getSqlSession().selectList(NOTIFYCATION_NAMESPACE + "selectByParam", notifycation);
	}
	/**
	 * 根据运单号，查询运单通知记录列表.
	 * 
	 * @param notifycation the notifycation
	 * @return the list
	 * @author ibm-wangfei
	 * @date Oct 22, 2012 12:36:18 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#selectByParams(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationEntity> queryNotificationsByParam1(NotificationEntity notifycation) {
		return this.getSqlSession().selectList(NOTIFYCATION_NAMESPACE + "selectByParam1", notifycation);
	}

	/**
	 * 查询运单明细信息.
	 * 
	 * @param conditionDto the condition dto
	 * @return the notify customer dto
	 * @author ibm-wangfei
	 * @date Oct 26, 2012 4:20:19 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryNotifyCustomer(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	public NotifyCustomerDto queryNotifyCustomer(NotifyCustomerConditionDto conditionDto) {
		return (NotifyCustomerDto) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + SELECT_DETAIL_BY_WAYBILLNO, conditionDto);
	}

	/**
	 * 根据运单号查询运单通知列表.
	 * 
	 * @param conditionDto the condition dto
	 * @return the list
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:05:34 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryNotifyCustomerListForWaybillNo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryNotifyCustomerListForWaybillNo(NotifyCustomerConditionDto conditionDto) {
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + SELECT_BY_WAYBILLNO, conditionDto);
	}

	/**
	 * 根据交接单号查询运单通知列表.
	 * 
	 * @param conditionDto the condition dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:05:48 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryNotifyCustomerListForHandoverNo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryNotifyCustomerListForHandoverNo(NotifyCustomerConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + SELECT_BY_HANDOVERNO, conditionDto, rowBounds);
	}

	/**
	 * 根据车次号、预计到达时间查询运单通知列表.
	 * 
	 * @param conditionDto the condition dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:06:08 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryNotifyCustomerListForVehicleAssembleNo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryNotifyCustomerListForVehicleAssembleNo(NotifyCustomerConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + SELECT_BY_VEHICLEASSEMBLENO, conditionDto, rowBounds);
	}

	/**
	 * 根据库存查询运单通知列表.
	 * 
	 * @param conditionDto the condition dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:06:15 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryNotifyCustomerListForStock(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryNotifyCustomerListForStock(NotifyCustomerConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + SELECT_BY_STOCKINFO, conditionDto, rowBounds);
	}

	/**
	 * 根据运单编号，更新运单附属表的通知信息.
	 * 
	 * @param actualFreightEntity the actual freight entity
	 * @return the int
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:06:27 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#updateActualFreightEntityByWaybillNo(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
	 */
	@Override
	public int updateActualFreightEntityByWaybillNo(ActualFreightEntity actualFreightEntity) {
		actualFreightEntity.setModifyTime(new Date());
		return this.getSqlSession().update(PREDELIVER_NOTIFY_NAMESPACE + UPDATE_BY_WAYBILLNO, actualFreightEntity);
	}

	/**
	 * update:查询添加字段  货物重量   和   货物体积
	 * @author yuting
	 * @date   2014-07-24 10::06
	 * 根据运单号列表，查询运单相关信息.
	 * 
	 * @param waybillNos the waybill nos
	 * @return the list
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:06:39 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryWaybillsByNos(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryWaybillsByNos(String[] waybillNos) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "selectWaybillsForNos", map);
	}

	/**
	 * 根据交接单号查询运单通知数量.
	 * 
	 * @param conditionDto the condition dto
	 * @return the long
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:06:52 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryNotifyCustomerCountForHandoverNo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	public NotifyCustomerConditionDto queryNotifyCustomerCountForHandoverNo(NotifyCustomerConditionDto conditionDto) {
		return (NotifyCustomerConditionDto) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "getTotalCountForHandoverNo", conditionDto);
	}

	/**
	 * 根据车次号查询运单通知数量.
	 * 
	 * @param conditionDto the condition dto
	 * @return the long
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:07:04 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryNotifyCustomerCountForVehicleAssembleNo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	public NotifyCustomerConditionDto queryNotifyCustomerCountForVehicleAssembleNo(NotifyCustomerConditionDto conditionDto) {
		return (NotifyCustomerConditionDto) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "getTotalCountForVehicleAssembleNo", conditionDto);
	}

	/**
	 * 根据库存查询运单通知数量.
	 * 
	 * @param conditionDto the condition dto
	 * @return the long
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:07:15 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryNotifyCustomerCountForStock(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	public NotifyCustomerConditionDto queryNotifyCustomerCountForStock(NotifyCustomerConditionDto conditionDto) {
		return (NotifyCustomerConditionDto) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "getTotalCountForStock", conditionDto);
	}

	/**
	 * 初步查询出需要统计仓储费的运单信息.
	 * 
	 * @param productCodes the product codes
	 * @return the list
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:07:32 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryWaybillsByCFF(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryWaybillsByCFF(String[] productCodes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productCodes", productCodes);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "selectWaybillsForCCF", map);
	}

	/**
	 * 更新运单通知信息.
	 * 
	 * @param notificationEntity the notification entity
	 * @return the int
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:07:46 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#updateNotificationEntity(com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity)
	 */
	@Override
	public int updateNotificationEntity(NotificationEntity notificationEntity) {
		//更新修改时间
		notificationEntity.setModifyDate(new Date());
		return this.getSqlSession().update(NOTIFYCATION_NAMESPACE + "updateByPrimaryKeySelective", notificationEntity);
	}

	/**
	 * 批量更新短信状态.
	 * 
	 * @param notificationEntity the notification entity
	 * @return the int
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:07:59 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#batchUpdateNotificationEntity(com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity)
	 */
	@Override
	public int batchUpdateNotificationEntity(NotificationEntity notificationEntity) {
		//更新修改时间
		notificationEntity.setModifyDate(new Date());
		return this.getSqlSession().update(NOTIFYCATION_NAMESPACE + "updateByParam", notificationEntity);
	}

	/**
	 * 查询符合生成仓储异常的运单.
	 * 
	 * @param productCodes the product codes
	 * @param warehouseTimeoutDate the warehouse timeout date
	 * @return the list
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:08:29 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryWaybillsWithWarehousingTimeout(java.lang.String[],
	 *      int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryWaybillsWithWarehousingTimeout(String[] productCodes, int warehouseTimeoutDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storageDay", warehouseTimeoutDate);
		map.put("productCodes", productCodes);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "queryWaybillsWithWarehousingTimeout", map);
	}

	/**
	 * 批量更新运单附加表通知状态
	 * @param notificationEntity
	 * @return 更新数量
	 * @author ibm-wangfei
	 * @date Jan 6, 2013 9:25:22 AM
	 */
	@Override
	public int batchUpdateActualFreightEntity(NotificationEntity notificationEntity) {
		return this.getSqlSession().update(PREDELIVER_NOTIFY_NAMESPACE + "updateAfByParam", notificationEntity);
	}

	@Override
	public ActualFreightEntity queryActualFreightByWaybillNo(ActualFreightEntity actualFreightEntity) {
		return (ActualFreightEntity) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "queryActualFreightByWaybillNo", actualFreightEntity);
	}

	@Override
	public int queryVoiceNoticeSuccessByNo(NotificationEntity notificationEntity) {
		return (Integer) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "queryVoiceNoticeSuccessByNo", notificationEntity);
	}

	@Override
	public int querySmsNoticeSuccessByNo(NotificationEntity notificationEntity) {
		return (Integer) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "querySmsNoticeSuccessByNo", notificationEntity);
	}

	
	/**
	 * 
	 *根据车次号查询运单通知数量.
	 *  @author 159231-foss-meiying
	 * @date 2014-1-1 下午3:00:10
	 */
	@Override
	public Long queryBeforeNoticeCountForVehicleAssembleNo(NotifyCustomerConditionDto conditionDto) {
		return (Long) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "getBeforeNoticeTotalCountForVehicleAssembleNo", conditionDto);
	}
	/**
	 * 
	 * 根据车次号、预计到达时间查询运单通知列表.
	 *  @author 159231-foss-meiying
	 * @date 2014-1-1 下午4:02:48
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryBeforeNoticeListForVehicleAssembleNo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryBeforeNoticeListForVehicleAssembleNo(NotifyCustomerConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "selectBeforeNoticeByVehicleAssembleNo", conditionDto, rowBounds);
	}
	/**
	 * 根据运单号查询运单通知数量.
	 *  @author 159231-foss-meiying
	 * @date 2014-1-22 下午3:46:46
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryBeforeNoticeCountForWaybillNos(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	public Long queryBeforeNoticeCountForWaybillNos(NotifyCustomerConditionDto conditionDto) {
		return (Long) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "getBeforeNoticeCountForWaybillNo", conditionDto);
	}
	/**
	 * 根据交接单号查询运单通知数量.
	 * 
	 * @param conditionDto the condition dto
	 * @return the long
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:06:52 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryNotifyCustomerCountForHandoverNo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	public Long queryBeforeNoticeCountForHandoverNo(NotifyCustomerConditionDto conditionDto) {
		return (Long) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "getBeforeNoticeCountForHandoverNo", conditionDto);
	}
	
	/**
	 * 根据运单号查询运单通知数量.
	 *  @author 159231-foss-meiying
	 * @date 2014-1-22 下午3:52:49
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryNotifyCustomerListForStock(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryBeforeNoticeListForWaybillNos(NotifyCustomerConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "getBeforeNoticeForWaybillNos", conditionDto, rowBounds);
	}
	/**
	 * 根据交接单号查询运单通知列表.
	 *  @author 159231-foss-meiying
	 * @date 2014-1-22 下午3:56:05
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao#queryBeforeNoticeListForHandoverNo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryBeforeNoticeListForHandoverNo(NotifyCustomerConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "selectBeforeNoticeByHandoverNo", conditionDto, rowBounds);
	}
	/**
	 * 查询派送提前通知-运单明细信息
	 *  @author 159231-foss-meiying
	 * @date 2014-2-20 下午2:16:24
	 */
	@Override
	public NotifyCustomerDto queryBeforeNoticeDetailByWaybillNo(NotifyCustomerConditionDto conditionDto) {
		List<NotifyCustomerDto> selectList = this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "selectBeforeNoticeDetailByWayBillNo", conditionDto);
		if (CollectionUtils.isNotEmpty(selectList)) {
			return selectList.get(0);
		}
		return null;
	}
	/**
	 * 根据传入的批量运单号查询不在到达部门库存的运单
	 *  @author 159231-foss-meiying
	 * @date 2014-2-27 上午8:59:20
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryNoWaybillStockByNos(NotifyCustomerConditionDto conditionDto) {
		return (List<String>) this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "waybillStockQuery", conditionDto);
	}

	/**
	 * 我只想把所有添加、更新操作写完
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-3-31 11:20:14
	 */
	@Override
	public int updateByPrimaryKey(NotificationEntity notificationEntity) {
		//修改时间
		notificationEntity.setModifyDate(new Date());
		return this.getSqlSession().update(NOTIFYCATION_NAMESPACE+"updateByPrimaryKey", notificationEntity);
	}
	/** 
	 * @Description		 
	 * @param bills
	 * @return			
	 * @author			mujun	
	 * @date 			2014-4-10 上午9:51:51 
	 * @version			1.0
	 */
	
	@Override
	public int mergeNoticeWaybill(Map<String,String> mp) {

		return this.getSqlSession().update(PREDELIVER_NOTIFY_NAMESPACE + "mergeNoticeWaybill", mp);

	}

	/** 
	 * @param code
	 * @return			
	 * @author			mujun	
	 * @date 			2014-4-10 上午9:51:51 
	 * @version			1.0
	 */
	
	@Override
	public int relieveNoticeWaybill(String[] bills) {
		List<String> waybillNos=Arrays.asList(bills);
		 return this.getSqlSession().update(PREDELIVER_NOTIFY_NAMESPACE + "relieveNoticeWaybill", waybillNos);
	}
	
	
	 /**
	  * 
	  * @Description			通过客户编码查询多票信息列表 
	  * @param CustomerCode
	  * @return  		
	  * @author				mujun
	  * @date 				2014-4-10 下午3:22:47 
	  * @version				1.0
	  */
	@Override
	 public List<NotifyMultibillDto> getMultibillListByCustomer(NotifyCustomerConditionDto notifyCustomerConditionDto){
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "getMultibillListByCustomer", notifyCustomerConditionDto);
	 }
	 /**
	  * 根据运单号查询最后一次通知的信息
	  * @author 159231 meiying
	  * 2015-4-29  下午8:05:08
	  * @param notify
	  * @return
	  */
	@Override
	public NotificationEntity queryLastNotifyByWaybillNo(NotificationEntity notify){
		@SuppressWarnings("unchecked")
		List<NotificationEntity> entitys=this.getSqlSession().selectList(NOTIFYCATION_NAMESPACE + "queryLastNotifyByWaybillNo", notify);
		if(CollectionUtils.isNotEmpty(entitys)&& entitys.size()>0){
			return entitys.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据ID修改通知的部分信息
	 * @author 159231 meiying
	 * 2015-5-4  下午2:27:17
	 * @param notify
	 * @return
	 */
	@Override
	public int updatePartByPrimaryKeySelective(NotificationEntity notify) {
		return  this.getSqlSession().update(NOTIFYCATION_NAMESPACE + "updatePartByPrimaryKeySelective", notify);
	}

	 /**
	  * 根据运单号取通知记录表的送货要求(最新的一条)
	  * @author 239284
	  * @param waybillNo
	  * @return 送货要求
	  */
	public String queryDeliverRequire(String waybillNo) {
		return (String)this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "selectDeliverRequireByWaybillNo", waybillNo);
	}
	/**
	 * 根据运单号查询计划提前通知信息
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-23 下午3:28:54
	* @param @param conditionDto
	* @param @param start
	* @param @param limit
	* @param @return    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryArriveNoticeListForWaybillNos(
			NotifyCustomerConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "getArriveNoticeForWaybillNos", conditionDto, rowBounds);
	}

	/**
	 * 根据运单号查询计划提前通知信息总条数
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-24 上午9:03:03
	* @param @param conditionDto
	* @param @return    设定文件
	 */
	@Override
	public Long queryArriveNoticeCountForWaybillNos(
			NotifyCustomerConditionDto conditionDto) {
		return (Long) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "getArriveNoticeCountForWaybillNo", conditionDto);
	}

	/**
	 * 根据到达时间查询计划提前通知信息总条数
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-25 上午11:01:16
	* @param @param conditionDto
	* @param @return    设定文件
	 */
	@Override
	public Long queryArriveNoticeCountForPlanArriveTime(
			NotifyCustomerConditionDto conditionDto) {
		return (Long) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "getArriveNoticeCountForPlanArriveTime", conditionDto);
	}

	/**
	 * 根据实际到达时间查询计划提前通知信息总条数
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-25 下午12:48:10
	* @param @param conditionDto
	* @param @return    设定文件
	 */
	@Override
	public Long queryArriveNoticeCountForArriveTime(
			NotifyCustomerConditionDto conditionDto) {
		return (Long) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "getArriveNoticeCountForArriveTime", conditionDto);
	}

	/**
	 * 根据通知时间查询计划提前通知信息总条数
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-25 下午12:53:51
	* @param @param conditionDto
	* @param @return    设定文件
	 */
	@Override
	public Long queryArriveNoticeCountForNoticeTime(
			NotifyCustomerConditionDto conditionDto) {
		return (Long) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE + "getArriveNoticeCountForNoticeTime", conditionDto);
	}

	/**
	 * 根据计划到达时间查询计划提前通知信息
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-25 下午2:30:12
	* @param @param conditionDto
	* @param @param start
	* @param @param limit
	* @param @return    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryArriveNoticeListForPlanArriveTime(
			NotifyCustomerConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "getArriveNoticeForPlanArriveTime", conditionDto, rowBounds);
	}

	/**
	 * 根据实际到达时间查询计划提前通知信息
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-7-1 上午11:16:58
	* @param @param conditionDto
	* @param @param start
	* @param @param limit
	* @param @return    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryArriveNoticeListForArriveTime(
			NotifyCustomerConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "getArriveNoticeForArriveTime", conditionDto, rowBounds);
	}

	/**
	 * 根据通知时间查询计划提前通知信息
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-7-1 上午11:17:28
	* @param @param conditionDto
	* @param @param start
	* @param @param limit
	* @param @return    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryArriveNoticeListForNoticeTime(
			NotifyCustomerConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(PREDELIVER_NOTIFY_NAMESPACE + "getArriveNoticeForNoticeTime", conditionDto, rowBounds);
	}

	/**
	 * add by 329757
	 * 根据运单号查询运单是否在库存表中
	 */
	@Override
	public String queryOrgNameByNo(String waybillNo) {
		return (String)this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE +"getOrgNameByWaybillNo",waybillNo);
	}

	/**
	 * 根据运单号查询最新一条交接单记录
	* @author 329757-liuxiangcheng   
	* @date 2016-8-30 下午4:55:43
	 */
	@Override
	public Integer selectOneHandoverBillState(String waybillNo) {
		return (Integer)this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE +"selectOneHandoverBillState",waybillNo);
	}

	/**
	 * 根据运单号查询交接单中的始发部门
	* @author 329757-liuxiangcheng   
	* @date 2016-8-30 下午5:35:01
	 */
	@Override
	public String selectOneOrigOrgName(String waybillNo) {
		return (String)this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE +"selectOneOrigOrgName",waybillNo);
	}

	/**
	 * 根据运单号查询交接单中的到达部门
	* @author 329757-liuxiangcheng   
	* @date 2016-8-30 下午5:37:53
	 */
	@Override
	public String selectOneDestOrgName(String waybillNo) {
		return (String)this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE +"selectOneDestOrgName",waybillNo);
	}

	/**
	 * 查询实际到达时间
	* @author 329757-liuxiangcheng   
	* @date 2016-8-30 下午5:37:53
	 */
	@Override
	public Date selectOneActualArriveTime(String waybillNo) {
		return (Date)this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE +"selectOneActualArriveTime",waybillNo);
	}

	/**
	 * 查询到达联状态
	 */
	@Override
	public String selectArriveSheetStatus(String waybillNo) {
		return (String) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE +"selectArriveSheetStatus",waybillNo);
	}

	/**
	 * 查询到达联部门
	 */
	@Override
	public String selectArriveSheetAddress(String waybillNo) {
		return (String) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE +"selectArriveSheetAddress",waybillNo);
	}

	/**
	 * 查询到达联时间
	 */
	@Override
	public Date selectArriveSheetTime(String waybillNo) {
		return (Date) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE +"selectArriveSheetTime",waybillNo);
	}

	/**
	 * 查询所属部门的外场
	 */
	@Override
	public String selectOutNameByOrgName(String orgCode) {
		return (String) this.getSqlSession().selectOne(PREDELIVER_NOTIFY_NAMESPACE +"selectOutNameByOrgName",orgCode);
	}
}