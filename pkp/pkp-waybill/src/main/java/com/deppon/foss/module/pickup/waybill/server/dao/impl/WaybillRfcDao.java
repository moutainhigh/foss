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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WaybillRfcDao.java
 * 
 * FILE NAME        	: WaybillRfcDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.ProductDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeChargeEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LeaveChangeByWaybillNoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyWriteOffStatus;
import com.deppon.foss.module.pickup.waybill.shared.dto.TransportRecordDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillFRcQueryByWaybillNoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillFRcQueryByWaybillNosDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcForAccountServiceDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 更改单数据查询DAO
 * @author 102246-foss-shaohongliang
 * @date 2013-1-22 下午2:31:12
 */
public class WaybillRfcDao extends iBatis3DaoImpl implements IWaybillRfcDao {

	private static final String RFC_NAMESPACE = "foss.pkp.WaybillRfcEntityMapper.";

	private static final String RFC_CHARGE_NAMESPACE = "foss.pkp.WaybillRfcChangeChargeEntityMapper.";

	private static final String RFC_DETAIL_NAMESPACE = "foss.pkp.WaybillRfcChangeDetailEntityMapper.";

	/**
	 * 
	 * 查询运单签收结果
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午6:23:29
	 */
	@Override
	public String queryWaybillSignSituation(String waybillNo) {
		return (String) getSqlSession().selectOne(
				RFC_NAMESPACE + "queryWaybillSignSituation", waybillNo);
	}

	/**
	 * 
	 * 通过原始运单id查询更改单
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-24 下午6:21:56
	 */
	@Override
	public WaybillRfcEntity queryRfcEntityByWaybillId(String id,
			List<String> waybillRfcStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("waybillRfcStatus", waybillRfcStatus);
		return (WaybillRfcEntity) getSqlSession().selectOne(
				RFC_NAMESPACE + "queryRfcEntityByWaybillId", map);
	}
	
	/**
	 * 
	 * 查询运单更改记录
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-30 下午2:27:03
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TransportRecordDto> queryWaybillRfcRecord(String waybillNo,
			List<String> rfcTypes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createTime", new Date());
		map.put("waybillNo", waybillNo);
		map.put("rfcTypes", rfcTypes);
		return getSqlSession().selectList(
				RFC_NAMESPACE + "queryTransportRecord", map);
	}

	/**
	 * 通过运单号集合拿到待处理的更改单
	 * 
	 * @param WaybillFRcQueryByWaybillNosDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryWaybillRfcByWaybillNos(
			WaybillFRcQueryByWaybillNosDto waybillFRcQueryByWaybillNosDto) {
		return getSqlSession().selectList(
				RFC_NAMESPACE + "queryWaybillRFcByWaybillNos",
				waybillFRcQueryByWaybillNosDto);
	}

	/**
	 * 通过运单号来查询未受理更改单
	 * 
	 * @param waybillFRcQueryByWaybillNoDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryWaybillRfcByWaybillNo(
			WaybillFRcQueryByWaybillNoDto waybillFRcQueryByWaybillNoDto) {
		List<String> waybills = getSqlSession().selectList(
				RFC_NAMESPACE + "queryWaybillRFcByWaybillNo",
				waybillFRcQueryByWaybillNoDto);
		if (waybills != null && waybills.size() > 0) {
			return waybills.get(0);
		}
		return null;
	}
	
	/**
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * 
	 * @param modifyBillWriteoffDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ModifyBillWriteoffResultDto> queryModifyBillWriteoffResult(
			ModifyBillWriteoffDto modifyBillWriteoffDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(
				RFC_NAMESPACE + "queryModifyBillWriteoffResult",
				modifyBillWriteoffDto, rowBounds);
	}
	
	
	/**
	 * 
	 * 快递接口
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号、产品集合）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * @author 025000-foss-helong
	 * @date 2013-7-26
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ModifyBillWriteoffResultDto> queryExpressModifyBillWriteoffResult(
			ModifyBillWriteoffDto modifyBillWriteoffDto, int start, int limit) {
//		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(
				RFC_NAMESPACE + "queryExpressModifyBillWriteoffResult",
				modifyBillWriteoffDto);
	}
	
	
	/**
	 * 
	 * 快递接口
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号、产品集合）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * 备注：查询记录总条数
	 * @author 025000-foss-helong
	 * @date 2013-7-30
	 */
	public long queryExpressModifyBillWriteoffResultCount(
			ModifyBillWriteoffDto modifyBillWriteoffDto, int start, int limit) {
			long totalNumber = (Long) getSqlSession().selectOne(
					RFC_NAMESPACE + "queryExpressModifyBillWriteoffResultTotalNumber",
					modifyBillWriteoffDto);
				
			return totalNumber;
	}

	/**
	 * 更新核销状态
	 * 
	 * @param modifyWriteOffStatus
	 */
	@Override
	public void updateWriteOffStatus(ModifyWriteOffStatus modifyWriteOffStatus) {
		getSqlSession().update(RFC_NAMESPACE + "updateWriteOffStatus",
				modifyWriteOffStatus);
	}

	/**
	 * 查询已经处理的更改单
	 * 
	 * @param WaybillRFcNos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryAlreadyHandleWaybillRfcNos(
			ModifyWriteOffStatus modifyWriteOffStatus) {
		return getSqlSession().selectList(
				RFC_NAMESPACE + "queryAlreadyHandleWaybillRfcNos",
				modifyWriteOffStatus);

	}

	/**
	 * 查询未处理的更改单
	 * 
	 * @param WaybillRFcNos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryNoHandleWaybillRfcNos(
			ModifyWriteOffStatus modifyWriteOffStatus) {
		return getSqlSession().selectList(
				RFC_NAMESPACE + "queryNoHandleWaybillRfcNos",
				modifyWriteOffStatus);
	}

	/**
	 * 更新在线更改单打印次数 +1
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-15 下午11:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@Override
	public int updateWaybillPrintTimes(String waybillID) {
		return getSqlSession().update(
				RFC_NAMESPACE + "updateWaybillRfcPrintTime", waybillID);
	}
	
	/**
	 * 
	 * 新增更改单
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-19 下午2:17:50
	 */
	@Override
	public void addWaybillRfcEntity(WaybillRfcEntity rfcEntity) {
		// 设置UUID
		rfcEntity.setId(UUIDUtils.getUUID());

		getSqlSession().insert(RFC_NAMESPACE + "insertSelective", rfcEntity);
	}

	/**
	 * 
	 * 新增更改明细
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-19 下午2:18:01
	 */
	@Override
	public void addRfcChangeDetailEntity(WaybillRfcChangeDetailEntity entity) {
		// 设置UUID
		entity.setId(UUIDUtils.getUUID());
		//创建时间
		entity.setCreateDate(new Date());
		//修改时间
		entity.setModifyDate(entity.getCreateDate());
		getSqlSession()
				.insert(RFC_DETAIL_NAMESPACE + "insertSelective", entity);
	}
	
	/**
	 * 
	 * 新增更改金额明细
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-19 下午2:18:18
	 */
	@Override
	public void addRfcChangeChargeEntity(WaybillRfcChangeChargeEntity entity) {
		// 设置UUID
		entity.setId(UUIDUtils.getUUID());
		getSqlSession().insert(RFC_CHARGE_NAMESPACE + "insertSelective", entity);
	}
	
	/**
	 * 
	 * <p>
	 * 根据id查询更改单<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param id
	 * @return WaybillRfcEntity
	 */
	@Override
	public WaybillRfcEntity selectByPrimaryKey(String id) {
		return (WaybillRfcEntity) this.getSqlSession().selectOne(
				RFC_NAMESPACE + "selectByPrimaryKey", id);
	}
	
	/**
	 * 
	 * <p>
	 * 更新更改单实体<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param waybillRfcEntity
	 *            void
	 */
	@Override
	public void updateByPrimaryKeySelective(WaybillRfcEntity waybillRfcEntity) {
		this.getSqlSession()
				.update(RFC_NAMESPACE + "updateByPrimaryKeySelective",
						waybillRfcEntity);
	}
	/**
	 * 
	 * 查询审核总条数
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-7 上午8:51:57
	 */
	@Override
	public long queryModifyBillWriteoffResultTotalNumber(
			ModifyBillWriteoffDto modifyBillWriteoffDto) {
		long totalNumber = (Long) getSqlSession().selectOne(
				RFC_NAMESPACE + "queryModifyBillWriteoffResultTotalNumber",
				modifyBillWriteoffDto);
		return totalNumber;
	}

	/**
	 * 
	 * 查询其他费用明细
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-19 下午8:47:42
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		parms.put("type1", PriceEntityConstants.PRICING_CODE_QT);
		parms.put("type2", PriceEntityConstants.PRICING_CODE_QS);
		return this.getSqlSession().selectList(
				RFC_NAMESPACE + "queryOtherChargeByNo", parms);
	}

	/**
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * 
	 * @param modifyBillWriteoffDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ModifyBillWriteoffResultDto> queryModifyBillWriteoffResult(
			ModifyBillWriteoffDto modifyBillWriteoffDto) {
		return getSqlSession().selectList(
				RFC_NAMESPACE + "queryModifyBillWriteoffResult",
				modifyBillWriteoffDto);

	}
	
	
	
	/**
	 * 
	 * 快递接口
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * @author 025000-foss-helong
	 * @date 2013-7-27
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ModifyBillWriteoffResultDto> queryExpressModifyBillWriteoffResult(
			ModifyBillWriteoffDto modifyBillWriteoffDto) {
		return getSqlSession().selectList(
				RFC_NAMESPACE + "queryExpressModifyBillWriteoffResult",
				modifyBillWriteoffDto);

	}

	/**
	 * 
	 * <p>
	 * 是否自动受理
	 * </p>
	 * 
	 * @author foss-gengzhe
	 * @date 2012-12-20 上午11:11:01
	 * @param parms
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao#updateIsLabourHandle(java.util.Map)
	 */
	@Override
	public void updateIsLabourHandle(Map<String, Object> parms) {
		this.getSqlSession().update(RFC_NAMESPACE + "updateIsLabourHandle",
				parms);
	}

	/**
	 * 
	 * <p>
	 * 根据运单号查询运单出发更改单信息
	 * </p>
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-24 上午11:11:01
	 * @param parms
	 *            waybillNo
	 */
	@Override
	public List<LeaveChangeByWaybillNoResultDto> queryLeaveChangeByWaybillNo(
			String waybillNo) {
		@SuppressWarnings("unchecked")
		List<LeaveChangeByWaybillNoResultDto> leaveChangeByWaybillNoResultDtos = this
				.getSqlSession().selectList(
						RFC_NAMESPACE + "queryLeaveChangeByWaybillNo",
						waybillNo);
		return leaveChangeByWaybillNoResultDtos;
	}

	/**
	 * 
	 * 查询更改明细
	 * 
	 * @date 2012-11-19 下午2:18:01
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcChangeDetailEntity> queryRfcChangeDetail(
			String waybillRfcId) {
		 
		return getSqlSession().selectList(
				RFC_DETAIL_NAMESPACE + "queryRfcChangeDetail",	waybillRfcId);
	}
	
	/**
     * 
     * 根据Code查询价格DTO
     * @author 102246-foss-shaohongliang
     * @date 2013-1-22 下午2:27:16
     * @see com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcRemotingService#queryValueAddPriceByCode(java.lang.String)
     */
	@Override
	public ValueAddDto queryValueAddPriceByCode(String pricingCodeZz) {
		return null;
	}

	/**
	 * 
	 * 官网变更单申请
	 * @author foss-gengzhe
	 * @date 2013-1-23 下午4:56:34
	 * @param waybillRfcForAccountServiceDto
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao#applyChangeOrder(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcForAccountServiceDto)
	 */
	@Override
	public void applyChangeOrder(WaybillRfcForAccountServiceEntity waybillRfcForAccountServiceEntity) {
		// 设置UUID
		waybillRfcForAccountServiceEntity.setId(UUIDUtils.getUUID());
		getSqlSession().insert(RFC_NAMESPACE + "applyChangeOrder", waybillRfcForAccountServiceEntity);
	}

	/**
	 * 
	 * 官网变更单查询
	 * @author foss-gengzhe
	 * @date 2013-1-23 下午4:56:40
	 * @param waybillRfcForAccountServiceCondition
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao#queryChangeOrder(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceCondition)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcForAccountServiceDto> queryChangeOrder(WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition) {
		
		//是否分页
		if(waybillRfcForAccountServiceCondition.getCurrentPage() != null
				&& waybillRfcForAccountServiceCondition.getPageSize() != null){
    		int start = waybillRfcForAccountServiceCondition.getCurrentPage();
    		int limit = waybillRfcForAccountServiceCondition.getPageSize();
    		RowBounds rb = new RowBounds(start, limit);
    		return getSqlSession().selectList(RFC_NAMESPACE + "queryChangeOrder", waybillRfcForAccountServiceCondition, rb);
    	}
		else{
			return getSqlSession().selectList(RFC_NAMESPACE + "queryChangeOrder", waybillRfcForAccountServiceCondition);
		}
	}
	
	/**
	 * 
	 * 分页查询更改单
	 * 
	 * @param waybillRfcForAccountServiceCondition
	 * @param start
	 * @param limit
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 10:11:21 AM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcForAccountServiceDto> queryChangeOrderList(WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(RFC_NAMESPACE + "queryChangeOrder", waybillRfcForAccountServiceCondition, rowBounds);
	}
	
	/**
	 * 
	 * 查询更改单的记录总数
	 * 
	 * @param waybillRfcForAccountServiceCondition
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 10:11:55 AM
	 */
	@Override
	public Long queryChangeOrderCount(WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition) {
		return (Long) this.getSqlSession().selectOne(RFC_NAMESPACE + "queryChangeOrderCount", waybillRfcForAccountServiceCondition);
	}
	
	/**
	 * 
	 * 更新更改单处理状态
	 * 
	 * @param waybillRfcForAccountServiceEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 10:12:17 AM
	 */
	@Override
	public int updateWaybillRfcIds(WaybillRfcForAccountServiceEntity waybillRfcForAccountServiceEntity) {
		return this.getSqlSession().update(RFC_NAMESPACE + "updateByKeys", waybillRfcForAccountServiceEntity);
	}
	
	/**
     * 
     * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息 
     * @author 102246-foss-shaohongliang
     * @date 2013-4-12 下午4:52:38
     */
	@Override
	public OuterBranchEntity queryAgencyBranchCompanyInfo(String agencyBranchCode){
		return (OuterBranchEntity)this.getSqlSession().selectOne(RFC_NAMESPACE + "queryAgencyBranchCompanyInfo", agencyBranchCode);
	}
	
	/**
	 * 
	 *  通过 标识编码查询
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 下午4:56:44
	 */
	@Override
	public SaleDepartmentEntity querySaleDepartmentByCode(String customerPickupOrgCode){
		return (SaleDepartmentEntity)this.getSqlSession().selectOne(RFC_NAMESPACE + "querySaleDepartmentByCode", customerPickupOrgCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransportRecordDto> queryFirstRecord(String waybillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createTime", new Date());
		map.put("waybillNo", waybillNo);

		return getSqlSession().selectList(
				RFC_NAMESPACE + "queryFirstRecord", map);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao#queryWaybillRfcEntityByNewVersionId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRfcEntity> queryWaybillRfcEntityByNewVersionId(
			String oldVersionWaybillId) {
		return (List<WaybillRfcEntity>) this.getSqlSession().selectList(
				RFC_NAMESPACE + "queryWaybillRfcEntityByNewVersionId", oldVersionWaybillId);
	}
	
	/**
	 * 通过营业部查询所属产品
	 * @author WangQianJin
	 * @date 2013-7-22 下午8:57:58
	 */
	@Override
	public List<ProductEntity> queryBySalesDept(String salesDeptId,String productLevel){
		ProductDto product = new ProductDto();
		product.setSalesDeptCode(salesDeptId);
		product.setLevels(productLevel);
		//product.setSalesType(DictionaryValueConstants.ORG_DEPARTURE);
		product.setActive(FossConstants.ACTIVE);
		
		List<ProductEntity> list = this.getSqlSession().selectList(RFC_NAMESPACE + "selectProductBySalesDept",product);
		
		List<ProductEntity> resultList = new ArrayList<ProductEntity>(); 
		
		if(list!=null && list.size()>0){
			for (Iterator<ProductEntity> iterator = list.iterator(); iterator.hasNext();) {
				ProductEntity productEntity = iterator.next();
				boolean hasThisProduct = false;
				for(int i =0;i<resultList.size();i++){
					ProductEntity p2 = resultList.get(i);
					if(p2!=null && p2.getCode()!=null && p2.getCode().equals(productEntity.getCode())){
						hasThisProduct = true;
					}
				}
				if(!hasThisProduct){
					resultList.add(productEntity);
				}
			}
		}
		
		return resultList;
	}
	
	/**
	 * 根据到达部门编码查询所属产品
	 * @author WangQianJin
	 * @date 2013-7-22 下午9:29:20
	 */
	@Override
	public List<ProductEntity> queryByArriveDeptProduct(String arriveDept, String productLevel){
		ProductDto product = new ProductDto();
		product.setSalesDeptCode(arriveDept);
		product.setLevels(productLevel);
		product.setSalesType(DictionaryValueConstants.ORG_ARRIVE);
		product.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(RFC_NAMESPACE + "selectProductBySalesDept",product);
	}
	
	/**
	 * 查询到达部门产品
	 * 
	 * @author 076234-FOSS-pgy
	 * @date 2014-3-5
	 */
	@Override
	public List<ProductEntity> searchByArriveDeptProduct(String arriveDept, String productLevel){
		ProductDto product = new ProductDto();
		product.setSalesDeptCode(arriveDept);
		product.setLevels(productLevel);
		product.setSalesType(DictionaryValueConstants.ORG_ARRIVE);
		product.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(RFC_NAMESPACE + "searchProductBySalesDept",product);
	}
	/**
	 * 通过单号查询所有更改信息
	 * @date 2013-11-19 8:47:07
	 * @author Foss-105888-Zhangxingwang
	 */
	@SuppressWarnings({ "unchecked"})
	@Override
	public List<WaybillRfcEntity> queryWaybillRfcAcceptByWaybillNo(String waybillNo,
			String status) {
		Map<Object, Object> maps = new HashMap<Object, Object>();
		maps.put("waybillNo", waybillNo);
		maps.put("status", status);
		List<WaybillRfcEntity> list = this.getSqlSession().selectList(RFC_NAMESPACE+"queryWaybillRfcAcceptByWaybillNo", maps);
		return list;
	}
	/**
	 * 通过单号查询手动添加超重费
	 * @date 2014-01-09 8:47:07
	 * @author pgy
	 */
	@Override
	public List<WaybillOtherChargeDto> queryCZHCZFWFSDTJByNo(String waybillNo){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		parms.put("type1", PriceEntityConstants.PRICING_CODE_QT);
		parms.put("type2", PriceEntityConstants.PRICING_CODE_QS);
		return this.getSqlSession().selectList(
				RFC_NAMESPACE + "queryCZHCZFWF_SDTJByNo", parms);
	}
	
	/**
	 * 根据卫星点部门编码获取所属营业部的负责人CODE
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-3-27 上午10:59:46
	 */
	@Override
	public String queryPrincipalNoBySatellite(String sateCode){		
		return (String) getSqlSession().selectOne(RFC_NAMESPACE + "queryPrincipalNoBySatellite", sateCode);				
	}

	@Override
	public void addWaybillRfcInfoNoId(WaybillRfcEntity waybillRfcEntity) {
		getSqlSession().insert(RFC_NAMESPACE + "insertSelective", waybillRfcEntity);
	}

	@Override
	public List<String> queryUnActiveRfcWaybillNo(List<String> waybillNoList) {
		// TODO Auto-generated method stub
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNoList", waybillNoList);
		return this.getSqlSession().selectList(RFC_NAMESPACE+"queryUnActiveRfcWaybillNo", parms);
	}
	
	public List<ExpBatchChangeWeightDto> queryExpBatchChangeWeightResultByCondition(
			ExpBatchChangeWeightQueryDto dto){
		return this.getSqlSession().selectList(
				RFC_NAMESPACE+"queryExpBatchChangeWeightResultByCondition"+dto);
	}

	@Override
	public int insertWaybillRfcChangeChargeBatch(List<WaybillRfcChangeChargeEntity> rfcChangeChargeList) {
		return this.getSqlSession().insert(RFC_CHARGE_NAMESPACE+"insertWaybillRfcChangeChargeBatch", rfcChangeChargeList);
	}
	
	/**
	 * 插入转运或返货目的站记录
	 * 
	 * @author foss-206860
	 */
	@Override
	public void addWaybillRfcTransferEntity(WaybillRfcTranferEntity rfcTranferEntity) {
		rfcTranferEntity.setId(UUIDUtils.getUUID());
		getSqlSession().insert(RFC_NAMESPACE + "addWaybillRfcTransfer", rfcTranferEntity);
	}

	@Override
	public List<WaybillRfcTranferEntity> queryWaybillRfcTransferEntity(
			WaybillRfcTranferEntity waybillRfcTranferEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(waybillRfcTranferEntity.getWaybillRfcId() == null){
			map.put("waybillNo", waybillRfcTranferEntity.getWaybillNo());
			map.put("goodsRange", waybillRfcTranferEntity.getGoodsRange());
			map.put("active", FossConstants.YES);
		}else{
			map.put("waybillRfcId", waybillRfcTranferEntity.getWaybillRfcId());
		}
		return this.getSqlSession().selectList(
				RFC_NAMESPACE+"queryWaybillRfcTransferEntity",map);
	}
	
	@Override
	public List<WaybillRfcTranferEntity> queryRfcTransferHistory(
			WaybillRfcTranferEntity waybillRfcTranferEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillRfcTranferEntity.getWaybillNo());
		map.put("goodsRange", waybillRfcTranferEntity.getGoodsRange());
		map.put("active", FossConstants.YES);
//		map.put("rfcType", waybillRfcTranferEntity.getRfcType());
		return this.getSqlSession().selectList(
				RFC_NAMESPACE+"queryRfcTransferHistory",map);
	}

	@Override
	public int updateRfcTranferEntity(WaybillRfcTranferEntity rfcTranfer) {
			Map<String, Object> map = new HashMap<String, Object>();
			if(StringUtils.isNotEmpty(rfcTranfer.getWaybillNo()) && StringUtils.isNotEmpty(rfcTranfer.getGoodsRange())){
				map.put("waybillNo", rfcTranfer.getWaybillNo());
				map.put("goodsRange", rfcTranfer.getGoodsRange());
				map.put("active", FossConstants.NO);
			}else{
				map.put("waybillRfcId", rfcTranfer.getWaybillRfcId());
				map.put("active", FossConstants.YES);
			}
			return getSqlSession().update(RFC_NAMESPACE + "updateRfcTranferEntity",  map);
	}
	
	@Override
	public int updateRfcTranferRfcId(WaybillRfcTranferEntity rfcTranfer) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("waybillNo", rfcTranfer.getWaybillNo());
			map.put("id", rfcTranfer.getId());
			map.put("waybillRfcId", rfcTranfer.getWaybillRfcId());
			return getSqlSession().update(RFC_NAMESPACE + "updateRfcTranferRfcId",  map);
	}
	
	/**
	 * @项目：家装项目
	 * @功能：根据单号查询交货确认状态
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-10-17下午16:18
	 */
	@Override
	public String queryWayBillState(String waybillNo) {
		// TODO Auto-generated method stub
		return (String)getSqlSession().selectOne(RFC_NAMESPACE + "homeRenovationWaybillState",  waybillNo);
	}

	@Override
	public List<WaybillRfcEntity> queryRecentRfc(String waybillNo) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		return  getSqlSession().selectList(RFC_NAMESPACE + "queryRecentRfc",  map);
	}
	
	/**
     * 根据更改单id,查询更改单变更信息
     * @author 311417 wangfeng
     * @date 2016-4-12
     */
    public String queryRfcChangeItemsByWaybillRfcId(String waybillRfcId) {
         return  (String) getSqlSession().selectOne(
                RFC_NAMESPACE + "queryRfcChangeItemsByWaybillRfcId",waybillRfcId);
    }
    
    /**
     * 查询是否可开装卸费
     * @author 354805
     * @date 2016-12-6 16:22:19
     * @param code 装卸费开单部门
     * @param billTime 开单时间
     * @return 是否可开装卸费
     */
    @Override
    public String queryCanPayServiceFeeByCodeAndTime(String code, Date billTime ){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("code", code);
    	map.put("billTime", billTime);
    	return (String)getSqlSession().selectOne(RFC_NAMESPACE + "queryCanPayServiceFeeByCodeAndTime", map);
    }

}