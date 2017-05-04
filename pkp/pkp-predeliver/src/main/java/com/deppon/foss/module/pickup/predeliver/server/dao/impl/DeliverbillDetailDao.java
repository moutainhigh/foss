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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/DeliverbillDetailDao.java
 * 
 * FILE NAME        	: DeliverbillDetailDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverHandTaskConditionDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverHandTaskDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskConditionDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskDetailsDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliveryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectiveExpressPlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveExpressPlanDetailEntity;
import com.deppon.foss.module.pickup.sign.api.server.dao.IDeliverHandlerDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCQueryReceivableAmountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * 派送单明细DAO实现
 * 
 * @author ibm-wangxiexu
 * @date 2012-10-24 上午10:07:01
 */
@SuppressWarnings("unchecked")
public class DeliverbillDetailDao extends iBatis3DaoImpl implements
		IDeliverbillDetailDao {
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.predeliver.server.dao.impl.DeliverbillDetailDao";
	private ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService;
	public void setCubcQueryReceivableAmountService(
			ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService) {
		this.cubcQueryReceivableAmountService = cubcQueryReceivableAmountService;
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeliverbillDetailDao.class);
	
	// 派送单明细name space
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao";

	private static final long NUMBER_60 = 60;

	/**
	 * 到达联DAO
	 */
	private IArrivesheetDao arrivesheetDao;
	
	/**
	 * 时效方案明细Service
	 */
	private IEffectiveExpressPlanDetailService effectiveExpressPlanDetailService;
	
	/**
	 *  结算
	 *  应收单服务接口
	 */
	private IBillReceivableService billReceivableService;
	
	/**
	 * 区域服务类
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	
	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	
	/**
	 * Sets 
	 * 		the 结算应收单服务.
	 *
	 * @param billReceivableService
	 * 	 the new 结算应收单服务
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}
	
	/**
	 *  派送处理Dao
	 *  接口
	 */
	private IDeliverHandlerDao deliverHandlerDao;
	public void setDeliverHandlerDao(IDeliverHandlerDao deliverHandlerDao) {
		this.deliverHandlerDao = deliverHandlerDao;
	}
	
	private IWaybillExpressService waybillExpressService;
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	
	private IRepaymentService repaymentService;
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}
	/**
	 * 获取子母件接口
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * 获取子母件接口 注入
	 */
	
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	
	
	/**
	 * 
	 * 根据派送单ID查找派送单明细列表
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @return 派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2012-10-24 上午10:05:28
	 */
//	@Override
//	public List<DeliverbillDetailEntity> queryByDeliverbillId(
//			String deliverbillId, int start, int limit) {
//		RowBounds rowBounds = new RowBounds(start, limit);
//
//		return this.getSqlSession().selectList(
//				NAMESPACE + ".selectByDeliverbillId", deliverbillId, rowBounds);
//	}

	/**
	 * 
	 * 根据派送单ID查找派送单明细列表
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @return 派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2012-10-24 上午10:05:28
	 * update by 231438 2015-04-16 用于快递100轨迹推送（查询派送单对应的派送单详情） 
	 */
	@Override
	public List<DeliverbillDetailEntity> queryByDeliverbillId(
			String deliverbillId) {
		//old
		//return this.getSqlSession().selectList(NAMESPACE + ".selectByDeliverbillId", deliverbillId);
		//new
		return this.getSqlSession().selectList(NAMESPACE 
				+ ".selectDetailListByDeliverbillId", deliverbillId);
	}

	/**
	 * 
	 * 添加派送单明细
	 * 
	 * @param deliverbillDetail
	 *            派送单明细
	 * @return 若成功，则返回派送单明细；否则返回null
	 * @author ibm-wangxiexu
	 * @date 2012-10-28 下午5:30:01
	 */
	@Override
	public DeliverbillDetailEntity add(DeliverbillDetailEntity deliverbillDetail) {
		deliverbillDetail.setId(UUIDUtils.getUUID());
		//创建时间
		deliverbillDetail.setCreateDate(new Date());
		//修改时间
		deliverbillDetail.setModifyDate(deliverbillDetail.getCreateDate());
		int result = this.getSqlSession().insert(
				NAMESPACE + ".insertSelective", deliverbillDetail);

		return result == 1 ? deliverbillDetail : null;
	}

	/**
	 * 
	 * 删除派送单明细
	 * 
	 * @param deliverbillDetailId
	 *            派送单明细ID
	 * @return 删除的行数
	 * @author ibm-wangxiexu
	 * @date 2012-10-29 上午11:13:15
	 */
	@Override
	public int delete(String deliverbillDetailId) {
		return this.getSqlSession().delete(NAMESPACE + ".deleteByPrimaryKey",
				deliverbillDetailId);
	}

	/**
	 * 
	 * 根据ID查找派送单明细
	 * 
	 * @param id
	 *            派送单明细ID
	 * @return 派送单明细
	 * @author ibm-wangxiexu
	 * @date 2012-10-24 下午4:22:17
	 */
	@Override
	public DeliverbillDetailEntity queryById(String id) {
		return (DeliverbillDetailEntity) this.getSqlSession().selectOne(
				NAMESPACE + ".selectByPrimaryKey", id);
	}

	/**
	 * 
	 * 根据派送单ID查找派送单明细列表大小
	 *  
	 * @param deliverbillId
	 *            派送单ID
	 * @return 派送单明细列表大小
	 * 
	 * @return 派送单明细列表大小
	 * @author ibm-wangxiexu
	 * @date 2012-10-29 下午5:01:01
	 */
	@Override
	public Long queryCountByDeliverbillId(String deliverbillId) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + ".selectCountByDeliverbillId", deliverbillId);
	}

	/**
	 * 
	 * 更新派送单明细
	 * 
	 * @param deliverbillDetail
	 *            派送单明细
	 * @return 若成功，返回更新后的派送单明细；否则返回null
	 * @author ibm-wangxiexu
	 * @date 2012-10-29 下午2:54:18
	 */
	@Override
	public DeliverbillDetailEntity update(
			DeliverbillDetailEntity deliverbillDetail) {
		//修改时间
		deliverbillDetail.setModifyDate(new Date());
		int result = this.getSqlSession().update(
				NAMESPACE + ".updateByPrimaryKeySelective", deliverbillDetail);

		return result == 1 ? deliverbillDetail : null;
	}

	/**
	 * 
	 * 查询派送单下指定序号的派送单明细ID
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @param serialNo
	 *            派送单明细序号
	 * @return 指定序号的派送单明细ID
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午3:00:19
	 */
	@Override
	public String queryIdBySerialNo(String deliverbillId, Integer serialNo) {
		DeliverbillDetailEntity deliverbillDetail = new DeliverbillDetailEntity();
		deliverbillDetail.settSrvDeliverbillId(deliverbillId);
		deliverbillDetail.setSerialNo(serialNo);
		return (String) this.getSqlSession().selectOne(
				NAMESPACE + ".selectIdBySerialNo", deliverbillDetail);
	}

	/**
	 * 
	 * 更新序号大于指定派送单明细序号的的序号
	 * 
	 * @param deliverbillDetail
	 *            派送单明细
	 * @return 更新的行数
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 上午10:39:38
	 */
	@Override
	public int updateSerialNos(DeliverbillDetailEntity deliverbillDetail) {
		return this.getSqlSession().update(NAMESPACE + ".updateSerialNos",
				deliverbillDetail);
	}

	/**
	 * 批量删除派送单明细
	 * 
	 * @param deliverbillDetailIdArray
	 *            派送单明细ID数组
	 * @return 批量删除的行数
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午6:53:51
	 */
	@Override
	public int deleteBatch(String[] deliverbillDetailIdArray) {
		int result = 0;

		for (String deliverbillDetailId : deliverbillDetailIdArray) {
			result += this.delete(deliverbillDetailId);
		}

		return result;
	}

	/**
	 * 
	 * 根据查询条件查询待排运单
	 * 
	 * @param waybillToArrangeDto
	 *            查询条件
	 * @author ibm-wangxiexu
	 * @date 2012-11-4 下午4:32:27
	 */
	@Override
	public List<WaybillToArrangeDto> queryWaybillToArrangeByCondition(
			WaybillToArrangeDto waybillToArrangeDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);

		return this.getSqlSession().selectList(
				NAMESPACE + ".selectWaybillToArrangeByCondition",
				waybillToArrangeDto, rowBounds);
	}

	/**
	 * 
	 * 根据查询条件查询待排运单数量
	 * 
	 * @param waybillToArrangeDto
	 *            查询条件
	 * @return 满足查询条件的查询待排运单数量
	 * @author ibm-wangxiexu
	 * @date 2012-11-12 上午10:33:08
	 */
	@Override
	public Long queryWaybillToArrangeCountByCondition(
			WaybillToArrangeDto waybillToArrangeDto) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + ".selectWaybillToArrangeCountByCondition",
				waybillToArrangeDto);
	}

	/**
	 * 
	 * 根据查询条件查询派送单明细
	 * 
	 * @param waybillToArrangeDto
	 *            查询条件，查询条件包括运单号和派送单ID
	 * @return 符合条件的派送单明细
	 * @author ibm-wangxiexu
	 * @date 2012-11-13 下午4:13:19
	 */
	@Override
	public DeliverbillDetailEntity queryByCondition(
			WaybillToArrangeDto waybillToArrangeDto) {
		List<DeliverbillDetailEntity> list = this.getSqlSession().selectList(
				NAMESPACE + ".selectByCondition", waybillToArrangeDto);
		if(CollectionUtils.isNotEmpty(list) && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 
	 * 根据派送单ID查询最大的派送单明细编号
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @return 最大的派送单明细编号
	 * @author ibm-wangxiexu
	 * @date 2012-11-17 下午6:15:10
	 */
	@Override
	public Integer queryMaxSerialNoByDeliverbillId(String deliverbillId) {
		Integer maxSerialNo = (Integer) this.getSqlSession().selectOne(
				NAMESPACE + ".selectMaxSerialNoByDeliverbillId", deliverbillId);
		return maxSerialNo == null ? 0 : maxSerialNo;
	}

	/**
	 * 根据条件查询派送单明细内 到达联编号
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-27 下午5:17:20
	 */
	@Override
	public List<DeliverbillDetailDto> queryDeliverbillDetailBy(
			DeliverbillDetailDto dto) {
		return this.getSqlSession().selectList(
				NAMESPACE + ".queryDeliverbillDetailBy", dto);
	}

	/**
	 * 
	 * 根据派送单ID查找已生成到达联的派送明细列表
	 * 
	 * @param deliverbillDetailDto
	 *            包含派送单ID的查询条件
	 * @return 已生成到达联的派送明细列表
	 * @author ibm-wangxiexu
	 * @date 2012-12-9 下午10:04:39
	 */
	@Override
	public List<DeliverbillDetailEntity> queryArrivesheetListByDeliverbillId(
			DeliverbillDetailDto deliverbillDetailDto) {
		// 参数Map
		Map<String, String> map = new HashMap<String, String>();
		map.put("deliverbillId", deliverbillDetailDto.getDeliverbillId());
		map.put("preaudit", WaybillRfcConstants.PRE_AUDIT);
		map.put("preaccept", WaybillRfcConstants.PRE_ACCECPT);
		map.put("astatus", ArriveSheetConstants.STATUS_GENERATE);
		map.put("adestroyed", FossConstants.NO);
		map.put("active", FossConstants.YES);
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectArrivesheetByDeliverbillId",
				map);
	}

	/**
	 * 根据司机工号、车牌号 查询司机送货任务
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-12 下午8:56:18
	 */
	@Override
	public List<PdaDeliverTaskDetailsDto> queryDeliverDetailByDriverCode(
			PdaDeliverTaskConditionDto dto) {
		return this.getSqlSession().selectList(
				NAMESPACE + ".queryDeliverDetailByDriverCode", dto);
	}

	/**
	 * 
	 * 为中转提供的接口DAO，检验运单是否在指定部门安排过派送
	 * 
	 * @param deliverbillDetailDto
	 *            包含运单号，派送部门，派送单状态的查询条件
	 * @return 运单是否在指定部门安排过派送
	 * @author ibm-wangxiexu
	 * @date 2012-12-14 下午4:20:53
	 */
	@Override
	public Long queryWaybillArrangedFlag(
			DeliverbillDetailDto deliverbillDetailDto) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + ".selectWaybillArrangedFlag", deliverbillDetailDto);
	}

	/**
	 * 根据司机、车牌号 查询派送单ID
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-22 下午7:26:23
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao#queryDeliverNoByDriverCode(com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskConditionDto)
	 */
	@Override
	public List<String> queryDeliverNoByDriverCode(
			PdaDeliverTaskConditionDto dto) {
		return this.getSqlSession().selectList(
				NAMESPACE + ".queryDeliverNoByDriverCode", dto);
	}

	/**
	 * 
	 * 根据运单号查询运单派送信息列表，用于运单轨迹查询
	 * 
	 * @param waybillDeliveryDto
	 *            包含运单号的查询条件
	 * @return 运单派送信息列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-8 下午8:21:35
	 */
	@Override
	public List<WaybillDeliveryDto> queryWaybillDeliveryListByWaybillNo(
			WaybillDeliveryDto waybillDeliveryDto) {
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectWaybillDeliveryListByWaybillNo",
				waybillDeliveryDto);
	}
	
	/**
	 * 
	 * 查询未通知客户的派送单明细列表
	 * 
	 * @param deliverbillDto
	 *            派送单明细查询条件，包含了派送单ID和通知状态查询条件
	 * @return 未通知客户的派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-14 下午1:40:15
	 */
	@Override
	public List<DeliverbillDetailDto> queryUnnotifiedDeliverbillDetailList(
			DeliverbillDetailDto deliverbillDetailDto) {
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectUnnotifiedDeliverbillDetailList",
				deliverbillDetailDto);
	}

	/**
	 * 
	 * 查询排单件数与到达联件数不一致的派送单明细列表
	 * 
	 * @param deliverbillDetailDto
	 *            包含派送单ID和到达联状态(生成)的查询条件
	 * @return 排单件数与到达联件数不一致的派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-17 下午12:03:23
	 */
	@Override
	public List<DeliverbillDetailDto> queryArrivesheetQtyInconsistentListByDeliverbillId(
			DeliverbillDetailDto deliverbillDetailDto) {
		return this
				.getSqlSession()
				.selectList(
						NAMESPACE
								+ ".selectArrivesheetQtyInconsistentListByDeliverbillId",
						deliverbillDetailDto);
	}

	/**
	 * 
	 * 查询排单件数与库存件数不一致的派送单明细列表
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @return 排单件数与库存件数不一致的派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-17 下午12:03:23
	 */
	@Override
	public List<DeliverbillDetailDto> queryInStockQtyInconsistentDeliverbillDetailList(
			String deliverbillId) {
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectInStockQtyInconsistentListByDeliverbillId",
				deliverbillId);
	}
	/**
	 * 根据到达联编号，派送单状态查询派送单编号
	 * @author foss-meiying
	 * @date 2013-1-30 下午4:14:47
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao#queryDeliverNoByArriveSheetNo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto)
	 */
	@Override
	public String queryDeliverNoByArriveSheetNo(DeliverbillDetailDto dto) {
		return (String) this.getSqlSession().selectOne(
				NAMESPACE + ".queryDeliverNoByArriveSheetNo", dto);
	}
	/**
	 * 根据司机、车牌号、派送单状态查询派送单号
	 * @author foss-meiying
	 * @date 2013-3-27 下午5:52:58
	 * @param pdaDeliverTaskConditionDto
	 * @return 
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao
	 * #queryPdaDeliverTaskDtoByCondition(com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskConditionDto)
	 */
	@Override
	public List<PdaDeliverTaskDto> queryPdaDeliverTaskDtoByCondition(PdaDeliverTaskConditionDto pdaDeliverTaskConditionDto) {
		List<PdaDeliverTaskDto> listDeliverTask=new ArrayList<PdaDeliverTaskDto>();
		listDeliverTask=this.getSqlSession().selectList(NAMESPACE + ".queryPdaDeliverTaskDtoByCondition",pdaDeliverTaskConditionDto);
		if(CollectionUtils.isNotEmpty(listDeliverTask)){
			for(int i=0;i<listDeliverTask.size();i++){
				List<PdaDeliverTaskDetailsDto>  listDeliverTaskDetails=listDeliverTask.get(i).getDeliverTaskDetailsDtos();
				for(int x=0;x<listDeliverTaskDetails.size();x++){
					//送货任务明细DTO
					PdaDeliverTaskDetailsDto deliverTaskDetailsDto=new PdaDeliverTaskDetailsDto();
					deliverTaskDetailsDto=listDeliverTaskDetails.get(x);
					//只有Product_Code为快递时判断运行时效是否超时
					if(deliverTaskDetailsDto!=null&&
							waybillExpressService.onlineDetermineIsExpressByProductCode
											(deliverTaskDetailsDto.getProductCode().trim(),
													deliverTaskDetailsDto.getBillTime())){
						//到达联被拉回的次数
						int refuseCount=0;
						ArriveSheetEntity arriveSheetEntity=new ArriveSheetEntity(deliverTaskDetailsDto.getWaybillNo(), ArriveSheetConstants.STATUS_REFUSE, FossConstants.YES, FossConstants.NO);
						List<ArriveSheetEntity> listArriveSheet=arrivesheetDao.queryArriveSheetByWaybillNo(arriveSheetEntity);
						refuseCount=listArriveSheet.size();
						//"快递时效管理”内对应线路的承诺最大时效
						List<EffectiveExpressPlanDetailEntity> alist=effectiveExpressPlanDetailService.queryCuurentEffectiveExpressPlanDetailInfo(deliverTaskDetailsDto.getReceiveOrgCode(),
								deliverTaskDetailsDto.getCustomerPickupOrgCode(),deliverTaskDetailsDto.getProductCode(),new Date());
						if(CollectionUtils.isEmpty(alist)){
							throw new PdaProcessException("从"+deliverTaskDetailsDto.getReceiveOrgCode()+"到"+deliverTaskDetailsDto.getCustomerPickupOrgCode()+"没有有效的时效方案！");
						}
						long maxTime=alist.get(0).getMaxTime(); //承诺最大时间
						String maxTimeUnit=alist.get(0).getMaxTimeUnit();//承诺最大时间 的单位:  DAY,HOURS
						//如果承诺最大时间的单位是DAY,则转换成HOURS
						if(maxTimeUnit.equals("DAY")){
							maxTime = maxTime * NumberConstants.NUMBER_24;
						}
						//将HOURS单位转换为毫秒
						maxTime = maxTime * NUMBER_60 * NUMBER_60 * NumberConstants.NUMBER_1000;
						//开单日期
						Date billTime=deliverTaskDetailsDto.getBillTime();
						//最新一次创建派送单的时间
						Date submitTime=deliverTaskDetailsDto.getSubmitTime();
						//运行时效: 运单从开单日期至最新一次建立派送任务时中间间隔的天数
//					long time=billTime.getTime()-submitTime.getTime();
						long time=submitTime.getTime()-billTime.getTime();
						//运单每因为“客户不在无人收货”被派送拉回过1次，则运行时效减1
						time=time-refuseCount* NumberConstants.NUMBER_24 * NUMBER_60  * NUMBER_60 * NumberConstants.NUMBER_1000;
						//当对应运单的运行时效大于“快递时效管理”内对应线路的承诺最大时效时，视为对应运单运行时效超时
						if(time>maxTime){
							deliverTaskDetailsDto.setIsDeryOvertime(FossConstants.ACTIVE);
						}else{
							deliverTaskDetailsDto.setIsDeryOvertime(FossConstants.INACTIVE);
						}
						deliverTaskDetailsDto.setIsNewWaybillNo(FossConstants.NO); //是否返货新开单的运单
						List<WaybillExpressEntity> expList = waybillExpressService.queryWaybillListByWaybillNo(deliverTaskDetailsDto.getWaybillNo());
						WaybillExpressEntity newWaybillNoEntity = CollectionUtils.isNotEmpty(expList) ? expList.get(0) : null;
						if(newWaybillNoEntity!=null && StringUtils.isNotEmpty(newWaybillNoEntity.getOriginalWaybillNo())){
						//  判断当前单号是否子母件。如果是子母件，判断是否是子件或者母件，母件直接取当前应收代收款 跟到付金额  不是 取对应母件上的金额
							Map<String,Object> params = new HashMap<String,Object>();
							params.put("waybillNo", newWaybillNoEntity.getOriginalWaybillNo());
							params.put("active", FossConstants.YES);
							TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
							if(oneDto!= null && FossConstants.YES.equals(oneDto.getIsTwoInOne())){//判断是否是子母件
								if(StringUtils.isNotEmpty(oneDto.getMainWaybillNo())){//判断母件单号是否为空
									newWaybillNoEntity.setOriginalWaybillNo(oneDto.getMainWaybillNo());//返货新开单  原单号设置为母件单号
								}
							}
							BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(newWaybillNoEntity.getOriginalWaybillNo());
							// 根据运单号查询客户的应收单到付金额和应收代收货款金额 --结算接口
							BigDecimal total = new BigDecimal(0);
							//财务单据查询，灰度改造   353654 ---------------------------start 
							//快递不需要修改---注释 by 353654
							List<BillReceivableEntity> billReceivableEntities = null;
							/*List<String> waybillNos = new ArrayList<String>();
							waybillNos.add(newWaybillNoEntity.getOriginalWaybillNo());
							String vestSystemCode = null;
							//灰度服务地址，上线之后需要修改。不走ESB
							String grayByWaybillNoUrl = "http://10.230.28.87:8093/ashy-web/webservice/v1/ashy/vestService/vestAscription";
		                    try {
		                    	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(waybillNos,
		                    			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+"queryPdaDeliverTaskDtoByCondition",
		                    			SettlementConstants.TYPE_FOSS);
		                    	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		                    	List<VestBatchResult> list = response.getVestBatchResult();
		                    	vestSystemCode = list.get(0).getVestSystemCode();		
		        			} catch (Exception e) {
		        				LOGGER.info("灰度分流失败,"+"运单号："+ newWaybillNoEntity.getOriginalWaybillNo());
								throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
		        			}
							if(vestSystemCode.equals(SettlementConstants.TYPE_FOSS)){*/
								try {
									billReceivableEntities = billReceivableService.queryReceivableAmountByCondition(billReceiveable);
								} catch (Exception e) {
									LOGGER.error("调用FOSS结清查询财务单据异常信息为："+e.getMessage());
								}
							/*}
							if(vestSystemCode.equals(SettlementConstants.TYPE_CUBC)){
								try {
									billReceivableEntities = cubcQueryReceivableAmountService.queryReceivableAmount(newWaybillNoEntity.getOriginalWaybillNo());			
								} catch (Exception e) {
									LOGGER.error("调用CUBC结清查询财务单据异常信息为："+e.getMessage());
								}
							}*/
							//财务单据查询，灰度改造   353654 ---------------------------end
							if(!CollectionUtils.isEmpty(billReceivableEntities)){
								for (BillReceivableEntity billReceivableEntity : billReceivableEntities) {
									// 到达应收单
									if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
										// 应收到付款
										deliverTaskDetailsDto.setOldReceiveablePayAmoout(billReceivableEntity.getUnverifyAmount());
										if(deliverTaskDetailsDto.getOldReceiveablePayAmoout()!=null){
											total = total.add(deliverTaskDetailsDto.getOldReceiveablePayAmoout());
										}
									}
								}				
							}
							FinancialDto financeSignRookieNew = repaymentService.queryFinanceSign(deliverTaskDetailsDto.getWaybillNo()); //新单号
							if(financeSignRookieNew!=null){
								if(financeSignRookieNew.getReceiveablePayAmoout()!=null){
									total = total.add(financeSignRookieNew.getReceiveablePayAmoout());
									//应收到付款   -- 243921
									deliverTaskDetailsDto.setToPayAmount(financeSignRookieNew.getReceiveablePayAmoout().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
								}else{
									deliverTaskDetailsDto.setToPayAmount(BigDecimal.ZERO);
								}
								if(financeSignRookieNew.getReceiveableAmount() !=null){
									total = total.add(financeSignRookieNew.getReceiveableAmount());
									//应收代收款    -- 243921
									deliverTaskDetailsDto.setCodAmount(financeSignRookieNew.getReceiveableAmount().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
								}else{
									deliverTaskDetailsDto.setCodAmount(BigDecimal.ZERO);
								}
							}
							deliverTaskDetailsDto.setTotalMoney(total);
							deliverTaskDetailsDto.setIsNewWaybillNo(FossConstants.YES);//是否返货新开单的运单
						}else{
							//  判断当前单号是否子母件。如果是子母件，判断是否是子件或者母件，母件直接取当前应收代收款 跟到付金额  不是 取对应母件上的金额
							Map<String,Object> params = new HashMap<String,Object>();
							params.put("waybillNo", deliverTaskDetailsDto.getWaybillNo());
							params.put("active", FossConstants.YES);
							TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);

							if(FossConstants.YES.equals(oneDto.getIsTwoInOne())){//判断是否是子母件
								if(StringUtils.isNotEmpty(oneDto.getMainWaybillNo())){//判断母件单号是否为空
									//母件单号
									deliverTaskDetailsDto.setFemaleWaybillNo(oneDto.getMainWaybillNo());
									//子母件总件数
									deliverTaskDetailsDto.setTwoInOneQty(oneDto.getWaybillNoList().size());
									//获取运单的最新的支付信息  -- 243921
									FinancialDto finance = repaymentService.queryFinanceSign(oneDto.getMainWaybillNo()); //母件单号
									if(null != finance){
										BigDecimal amount = new BigDecimal(0);
										
										amount = amount.add(finance.getReceiveableAmount()==null ? BigDecimal.ZERO : finance.getReceiveableAmount().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
										//应收代收款
										deliverTaskDetailsDto.setCodAmount(amount);
										
										//应收到付款
										amount = amount.add(finance.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : finance.getReceiveablePayAmoout().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
										//到付金额：应收代收款+应收到付款
										deliverTaskDetailsDto.setToPayAmount(amount);
									}
								}else{
									throw new PdaProcessException("子母件运单："+deliverTaskDetailsDto.getWaybillNo()+"，母件单号为空！");
								}
							}else{
								//获取运单的最新的支付信息  -- 243921
								FinancialDto finance = repaymentService.queryFinanceSign(deliverTaskDetailsDto.getWaybillNo()); //单号
								if(null != finance){
									BigDecimal amount = new BigDecimal(0);
									
									amount = amount.add(finance.getReceiveableAmount()==null ? BigDecimal.ZERO : finance.getReceiveableAmount().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
									//应收代收款
									deliverTaskDetailsDto.setCodAmount(amount);
									
									//应收到付款
									amount = amount.add(finance.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : finance.getReceiveablePayAmoout().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
									//到付金额：应收代收款+应收到付款
									deliverTaskDetailsDto.setToPayAmount(amount);
								}
							}
						}
						//设置原始单号的地址信息
						StringBuffer sb = new StringBuffer();
						//省份
						if(StringUtils.isNotEmpty(deliverTaskDetailsDto.getDeliveryCustomerProvCode())){
							AdministrativeRegionsEntity provRegion = administrativeRegionsService.queryAdministrativeRegionsByCode(deliverTaskDetailsDto.getDeliveryCustomerProvCode());
							if(provRegion != null){
								sb.append(provRegion.getName());
							}
						}
						//市级单位
						if(StringUtils.isNotEmpty(deliverTaskDetailsDto.getDeliveryCustomerCityCode())){
							AdministrativeRegionsEntity cityRegion = administrativeRegionsService.queryAdministrativeRegionsByCode(deliverTaskDetailsDto.getDeliveryCustomerCityCode());
							if(cityRegion != null){
								sb.append(cityRegion.getName());
							}
						}
						//区县
						if(StringUtils.isNotEmpty(deliverTaskDetailsDto.getDeliveryCustomerDistCode())){
							AdministrativeRegionsEntity countyRegion = administrativeRegionsService.queryAdministrativeRegionsByCode(deliverTaskDetailsDto.getDeliveryCustomerDistCode());
							if(countyRegion != null){
								sb.append(countyRegion.getName());
							}
						}
						//详细地址
						if(StringUtils.isNotEmpty(deliverTaskDetailsDto.getDeliveryCustomerAddress())){
							sb.append(deliverTaskDetailsDto.getDeliveryCustomerAddress());
						}
						//地址备注
						if(StringUtils.isNotEmpty(deliverTaskDetailsDto.getDeliveryCustomerAddressNote())){
							sb.append("("+deliverTaskDetailsDto.getDeliveryCustomerAddressNote()+")");
						}
						if(StringUtils.isNotEmpty(sb.toString())){
							deliverTaskDetailsDto.setDeliveryCustomerAddress(sb.toString());
							deliverTaskDetailsDto.setDeliveryCustomerProvCode(null);
							deliverTaskDetailsDto.setDeliveryCustomerCityCode(null);
							deliverTaskDetailsDto.setDeliveryCustomerDistCode(null);
							deliverTaskDetailsDto.setDeliveryCustomerAddressNote(null);
						}
					}else{//零担
						FinancialDto finance = repaymentService.queryFinanceSign(deliverTaskDetailsDto.getWaybillNo()); //单号
						//应收代收款
						deliverTaskDetailsDto.setCodAmount(finance.getReceiveableAmount()==null ? BigDecimal.ZERO : finance.getReceiveableAmount().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
						//应收到付款
						deliverTaskDetailsDto.setToPayAmount( finance.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : finance.getReceiveablePayAmoout().multiply(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
					}
				}
			}
		}
		return listDeliverTask;
	}
	
	/**
	 * 根据司机、车牌号查询快递派送交接明细
	 * @author 243921-foss-zhangtingting
	 * @date 2015-4-15 下午3:42:58
	 * @param pdaDeliverHandTaskConditionDto
	 * @return 
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao
	 * #queryPdaDeliverHandTaskDtoByCondition(com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverHandTaskConditionDto)
	 */
	@Override
	public List<PdaDeliverHandTaskDto> queryPdaDeliverHandTaskDtoByCondition(
			PdaDeliverHandTaskConditionDto pdaDeliverHandTaskConditionDto) {
		List<PdaDeliverHandTaskDto> pdaDeliverHandTaskDtoList = new ArrayList<PdaDeliverHandTaskDto>();
		pdaDeliverHandTaskDtoList = this.getSqlSession().selectList(NAMESPACE + ".queryPdaDeliverHandTaskDtoByCondition", pdaDeliverHandTaskConditionDto);
		
		return pdaDeliverHandTaskDtoList;
	}
	
	/**
	 * 根据派送单集合查询派送单明细
	 * @author foss-meiying
	 * @date 2013-5-7 下午5:52:19
	 * @param deliverbillNos
	 * @return
	 * @see
	 */
	@Override
	public List<DeliverbillDetailDto> queryByDeliverbillNos(DeliverbillDto deliverbillDto){
		return this.getSqlSession().selectList(NAMESPACE + ".selectByDeliverbillNos",deliverbillDto);
	}
	
	/**
	 * 
	 * 根据查询条件查询待排运单
	 * @author 043258-foss-zhaobin
	 * @date 2013-6-25 下午4:50:56
	 */
	@Override
	public DeliverbillDto queryWaybillToArrangeTotal(WaybillToArrangeDto waybillToArrangeDto) {
		return (DeliverbillDto)this.getSqlSession().selectOne(
				NAMESPACE + ".selectWaybillToArrangeTotal",
				waybillToArrangeDto);
	}

	@Override
	public List<DeliverbillDetailEntity> queryByDeliverbillId(
			Map<Object, Object> map, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectByDeliverbillId", map,rowBounds);
	}
	
	@Override
	public List<DeliverbillDetailEntity> selectByDeliverbillReturn_bill_type_Sort(
			Map<Object, Object> map, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectByDeliverbillReturn_bill_type_Sort", map,rowBounds);
	}
	
	@Override
	public List<DeliverbillDetailEntity> queryByDeliverbillIdForPrint(Map<Object, Object> map, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectByDeliverbillIdForPrint", map,rowBounds);
	}

	@Override
	public List<DeliverbillDetailEntity> queryByDeliverbillId(
			Map<Object, Object> map) {
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectByDeliverbillId", map);
	}

	/**
	 * 
	 * 查询库位中数据
	 * @author 043258-foss-zhaobin
	 * @date 2013-7-13 下午1:59:02
	 */
	@Override
	public Long queryWaybillToArrangeCountByStoringCondition(
			WaybillToArrangeDto waybillToArrangeDto) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + ".selectWaybillToArrangeCountByStoringCondition",
				waybillToArrangeDto);
	}

	/**
	 * 
	 * 查询库位中数据
	 * @author 043258-foss-zhaobin
	 * @date 2013-7-13 下午1:59:02
	 */
	@Override
	public List<WaybillToArrangeDto> queryWaybillToArrangeByStoringCondition(
			WaybillToArrangeDto waybillToArrangeDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);

		return this.getSqlSession().selectList(
				NAMESPACE + ".selectWaybillToArrangeByStoringCondition",
				waybillToArrangeDto, rowBounds);
	}

	/**
	 * 
	 *  统计库位中信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-6-25 下午4:50:56
	 */
	@Override
	public DeliverbillDto queryWaybillToStoringArrangeTotal(
			WaybillToArrangeDto waybillToArrangeDto) {
		return (DeliverbillDto)this.getSqlSession().selectOne(
				NAMESPACE + ".selectWaybillToStoringArrangeTotal",
				waybillToArrangeDto);
	}
	
	/**
	 * 
	 * 更新派送单明细
	 * 
	 * @param deliverbillDetail
	 *            派送单明细
	 * @return 若成功，返回更新后的派送单明细；否则返回null
	 * @author ibm-wangxiexu
	 * @date 2012-10-29 下午2:54:18
	 */
	@Override
	public void updateDetailByWaybillNo(DeliverbillDetailEntity deliverbillDetail) {
		this.getSqlSession().update(NAMESPACE + ".upDetailByWaybillNo", deliverbillDetail);
	}

	public void setArrivesheetDao(IArrivesheetDao arrivesheetDao) {
		this.arrivesheetDao = arrivesheetDao;
	}

	public void setEffectiveExpressPlanDetailService(
			IEffectiveExpressPlanDetailService effectiveExpressPlanDetailService) {
		this.effectiveExpressPlanDetailService = effectiveExpressPlanDetailService;
	}
	/**
	 * 根据运单号查询派送明细
	 * @author 269871-foss-zhuliangzhi
	 * @param waybillId
	 */
	@Override
	public List<DeliverbillDetailEntity> queryByWaybillNo(String waybillId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE 
				+ ".selectDetailListByWaybillNo", waybillId);
	}

	/**
	 * 根据拖动后更新派送单明细运单的序号
	 * @author 239284
	 * @param detailId
	 * @param serialNo
	 */
	@SuppressWarnings({"rawtypes" })
	public int updateDeliverDetailSeriNoByDrag(String detailId, int serialNo) {
		Map map = new HashMap<String, Object>();
		map.put("detailId", detailId);
		map.put("serialNo", serialNo);
		return this.getSqlSession().update(NAMESPACE + ".updateDetailSerialNoByDrag", map);
	}

}