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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/WaybillSignResultDao.java
 * 
 * FILE NAME        	: WaybillSignResultDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.esb.inteface.domain.gis.HisSignDataRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.esb.inteface.domain.foss2ptp.PartnerUpdateTakeEffectTimeRequest;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillCondition;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ContrabandInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 运单签收结果Dao实现
 * @author foss-meiying
 * @date 2012-11-28 上午11:53:30
 * @since
 * @version
 */
public class WaybillSignResultDao extends iBatis3DaoImpl implements IWaybillSignResultDao {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity.";
	/**
	 * 修改运单签收结果(作废当前运单)
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:42:43
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#updateWaybillSignResult(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
	 */
	@Override
	public int updateWaybillSignResult(WaybillSignResultEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"updateById",entity);
	}
	/**
	 * 通过ID更新运单签收结果
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:05:25
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#updateWaybillSignResultById(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
	 */
	@Override
	public int updateWaybillSignResultById(WaybillSignResultEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"updateWaybillSignResultById",entity);
	}
	/**
	 * 保存运单签收结果信息
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:04:21
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#addWaybillSignResult(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
	 */
	@Override
	public int addWaybillSignResult(WaybillSignResultEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE+"insertWaybillsignResult", entity);
	}
	/**
	 * 根据运单号,是否有效 查询运单表里的货物总件数
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:01:31
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#queryWaybillQty(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
	 */
	@Override
	public Integer queryWaybillQty(WaybillSignResultEntity entity) {
		return (Integer)this.getSqlSession().selectOne(NAMESPACE+"queryWaybillQty",entity);
	}
	/**
	 * 据运单号,active = 'Y'查询运单签结果里的运单信息
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:03:01
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#queryWaybillSignResult(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
	 */
	@Override
	public WaybillSignResultEntity queryWaybillSignResult(WaybillSignResultEntity entity) {
		List<WaybillSignResultEntity> waybillSignResultEntitys =this.getSqlSession().selectList(NAMESPACE+"queryWaybillSignResult",entity);
		return CollectionUtils.isEmpty(waybillSignResultEntitys) ? null : waybillSignResultEntitys.get(0);
	}
	
	/**
	 * 根据运单号，active = 'Y'，和签收状态，查询第一次全部签收的签收结果信息
	 * @author foss-bieyexiong
	 * @date 2015-04-21 下午16:28:01
	 * @param entity
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillSignResultEntity queryFirstSignAllByEntity(WaybillSignResultEntity entity){
		entity.setSignStatus(SignConstants.SIGN_STATUS_ALL);
		List<WaybillSignResultEntity> entitys = this.getSqlSession().selectList(NAMESPACE+"queryFirstSignAllByEntity",entity);
		return CollectionUtils.isEmpty(entitys)?null : entitys.get(0);
	}
	
	/**
	 * 根据运单号查询运单部分信息
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:04:39
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#queryWaybillActualFreightPartByWaybillNo(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
	 */
	@Override
	public WaybillDto queryWaybillActualFreightPartByWaybillNo(String waybillNo) {
		WaybillEntity entity = new WaybillEntity();
		entity.setWaybillNo(waybillNo);
		entity.setActive(FossConstants.YES);
		return (WaybillDto)this.getSqlSession().selectOne(NAMESPACE+"selectWaybillActualPartByWaybillNo",entity);
	}
	/**
	 * 根据运单号查询运单部分信息
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:04:52
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#queryWaybillSignResultById(java.lang.String)
	 */
	@Override
	public WaybillSignResultEntity queryWaybillSignResultById(String id) {
		return (WaybillSignResultEntity)this.getSqlSession().selectOne(NAMESPACE+"selectByPrimaryKey",id);
	}
	/**
	 * 根据运单号查询运单签收结果里第一次添加的签收时间
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:05:05
	 * @param waybillNo 运单号
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#queryFirstSignTimeByWaybillNo(java.lang.String)
	 */
	@Override
	public Date queryFirstSignTimeByWaybillNo(String waybillNo) {
		return (Date)this.getSqlSession().selectOne(NAMESPACE+"queryFirstsignTime",waybillNo);
	}
	/***
	 * 根据传入的待撤销组织CODE,返回当前组织的未签收票数
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:05:46
	 * @param lastLoadOrgCode 最终配载部门
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#queryNotSignGoodsQtyByOrgCode(java.lang.String)
	 */
	@Override
	public int queryNotSignGoodsQtyByOrgCode(String lastLoadOrgCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		//运单签收结果的active = 'Y'
		map.put("resultaActive", FossConstants.ACTIVE);
		//运单的active 有效
		map.put("active", FossConstants.ACTIVE);
		//最终配载部门
		map.put("lastLoadOrgCode", lastLoadOrgCode); 
		return (Integer)this.getSqlSession().selectOne(NAMESPACE+"queryNotSignGoodsQty",map);
	}
	/**
	 * 根据条件查询运单签收结果里的运单号
	 * @author foss-meiying
	 * @date 2012-12-24 下午2:24:59
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#queryWaybillNoByCondition(com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryWaybillNoByCondition(WaybillSignResultDto dto) {
		return (List<String>)this.getSqlSession().selectList(NAMESPACE+"queryByCondition",dto);
	}
	
	/**
	 * 
	 * 根据运单号查询运单信息
	 * @param waybillNo 运单号
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 下午9:05:50
	 */
	@Override
	public AirWaybillDto queryWaybillInfoForEdi(AirWaybillCondition condition) {
		return (AirWaybillDto) getSqlSession().selectOne(NAMESPACE + "queryWaybillInfoForEdi", condition);
	}
	
	/**
	 * 查询中转库存流水号  通过运单编号,部门编码
	 * @author foss-meiying
	 * @date 2013-1-28 下午4:37:47
	 * @param dto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignDetailEntity> queryOptStockSerinalNo(ContrabandInfoDto dto) {
		return this.getSqlSession().selectList(NAMESPACE+"stockQuery",dto);
	}
	/**
	 * 据运单号集合,active = 'Y'查询运单签结果里的运单编号
	 * @author foss-meiying
	 * @date 2013-2-26 下午4:32:15
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#queryWaybillSignResultWaybillNos(com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryWaybillSignResultWaybillNos(WaybillSignResultDto dto) {
		return (List<String>)this.getSqlSession().selectList(NAMESPACE+"queryWaybillSignResultWaybillNos",dto);
	}
	   /**
     * 根据签收时间范围查询运单签结果里的运单信息
     * 
     * @author foss-caodajun 
     * FOSS设置定时任务将签收结果表同步到PTP，每天 05:00,23:00 点进行签收时间同步,
	 * 同步的为前一天签收的数据， PTP保存并更新最新的签收时间.
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerUpdateTakeEffectTimeRequest> queryWaybillSignTimeResult(Date signTimeStart,Date signTimeEnd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("signTimeStart", signTimeStart);
		map.put("signTimeEnd", signTimeEnd);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryWaybillSignTimeResult", map);
	}
	/**
	 *根据传入的运单号,入库时间起止查询满足条件的运单信息
	 * @author foss-meiying
	 * @date 2013-2-26 下午4:32:09
	 * @param dto
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#queryLostCargoInfoByCondition(com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LostCargoInfoDto> queryLostCargoInfoByCondition(LostCargoInfoDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<LostCargoInfoDto>)this.getSqlSession().selectList(NAMESPACE + "queryLostCargoInfoByCondition", dto,
				rowBounds);
	}
	/**
	 * 根据传入的运单号,入库时间起止查询满足条件的运单信息总数
	 * @author foss-meiying
	 * @date 2013-2-26 下午4:32:05
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao#queryLostCargoCountByCondition(com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto)
	 */
	@Override
	public Long queryLostCargoCountByCondition(LostCargoInfoDto dto) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "queryLostCargoCountByCondition",dto);
	}
	/**
	 * 据运单号查询运单签结果里的运单信息
	 * @author foss-meiying
	 * @date 2013-07-02 上午8:03:01
	 * @param entity
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillSignResultEntity> queryWaybillSignResultLit(WaybillSignResultEntity entity) {
		return (List<WaybillSignResultEntity>)this.getSqlSession().selectList(NAMESPACE+"queryWaybillSignResult",entity);
	}


	/**
	 * 查询运单签结果里的运单信息用于传给GIS
	 * @author foss-chengjing
	 * @date 2017-03-02 上午8:03:01
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HisSignDataRequest> queryLTLSigfnResultInfoDuringAllYesterday() {
		return (List<HisSignDataRequest>)this.getSqlSession().selectList(NAMESPACE+"queryLTLSigfnResultInfoDuringAllYesterday");
	}

}