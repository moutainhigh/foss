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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/AirAgencySignDao.java
 * 
 * FILE NAME        	: AirAgencySignDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.IAirAgencySignDao;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.util.define.FossConstants;
/**
 * 签收偏线空运货物Dao实现
 * @author foss-meiying
 * @date 2012-11-28 上午11:49:24
 * @since
 * @version
 */
public class AirAgencySignDao extends iBatis3DaoImpl implements IAirAgencySignDao {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "foss.sign.airAgencySign.";
	/**
	 * 根据运单号查询运单基本信息
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:08:47
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IAirAgencySignDao#queryByWaybillNo(java.lang.String)
	 */
	@Override
	public WaybillDto queryByWaybillNo(String waybillNo) {
		//传入运单号,active 有效
		AirAgencyQueryDto dto =new AirAgencyQueryDto(waybillNo,FossConstants.ACTIVE);
		return (WaybillDto)this.getSqlSession().selectOne(NAMESPACE+"selectWaybillInfoByWaybillNo", dto);
	}
	/**
	 * 根据查询条件(单号，收货人，收货人电话，收货人手机,运输性质)查询空运运单
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:24:37
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IAirAgencySignDao#queryAirInfobyParams(com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirAgencyQueryDto> queryAirInfobyParams(AirAgencyQueryDto entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryWaybillNoByAir", entity);
	}
	/**
	 * 签收偏线  根据查询条件(单号，收货人，收货人电话，收货人手机,运输性质) 运输性质为 汽运偏线  partial line 查询运单号,外发单号
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:09:34
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IAirAgencySignDao#queryWaybillNoByPartialLine(com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirAgencyQueryDto> queryWaybillNoByPartialLine(AirAgencyQueryDto entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryWaybillNoByPartialLine", entity);
	}
	
	/**
	 * 签收快递代理理： 根据查询条件(单号，收货人，收货人电话，收货人手机,运输性质) 运输性质为 经济快递  partial line 查询运单号,外发单号
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-20 下午9:29:01
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirAgencyQueryDto> queryExpressByPartialLine(AirAgencyQueryDto entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressByPartialLine", entity);
	}
	
	/**
	 * 根据运单号查询运单的最终配载部门,运输性质,运单签收结果的运单号
	 * @author foss-meiying
	 * @date 2013-1-12 上午10:41:19
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IAirAgencySignDao#queryWaybillPartByCondition(com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto)
	 */
	@Override
	public AirAgencyDto queryWaybillPartByCondition(AirAgencyQueryDto dto) {
		return (AirAgencyDto)this.getSqlSession().selectOne(NAMESPACE+"queryWaybillPartByCondition", dto);
		
	}
	/**
	 * 根据运单号查询该运单是否录外发单
	 * @author foss-meiying
	 * @date 2013-1-12 下午1:33:06
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IAirAgencySignDao#queryIsTransferExternalByWaybillNo(com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto)
	 */
	@Override
	public Long queryIsTransferExternalByWaybillNo(AirAgencyQueryDto dto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryIsTransferExternalByWaybillNo", dto);
	}
	
	/**
	 * 根据运单号查询该运单是否录快递代理外发单
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-20 下午9:15:42
	 */
	@Override
	public Long queryIsExpressExternalByWaybillNo(AirAgencyQueryDto dto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryIsExpressExternalByWaybillNo", dto);
	}
	
	/**
	 * 根据运单号查询该快递代理递代理外发单审核状态
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-29 下午7:24:38
	 */
	@Override
	public String queryExpressExternalBillStatusByNo(AirAgencyQueryDto dto){
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryExpressExternalBillStatusByNo", dto);
	}
	
	/**
	 * 根据运单号,部门判断登录部门与做航空正单的部门是否一致
	 * @author foss-meiying
	 * @date 2013-7-12 下午1:33:06
	 * @param dto
	 * @return 
	 */
	@Override
	public Long queryairWaybillOrgCodeIsSameByWaybillNo(AirAgencyQueryDto dto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryairWaybillOrgCodeIsSameByWaybillNo", dto);
		
		
	}
	@SuppressWarnings("unchecked")
	@Override 
	public List<String> queryAirTransferCenter() {
		String active=FossConstants.ACTIVE;
		return (List<String>)this.getSqlSession().selectList(NAMESPACE+"queryAirTransferCenter", active);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AirAgencyQueryDto> queryExpressByImportWaybillNo(AirAgencyQueryDto dto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressByImportWaybillNo", dto);
	}
	
	/**
	 * 签收快递代理理： 根据查询条件(单号，收货人，收货人电话，收货人手机,运输性质) 运输性质为 经济快递  partial line 查询运单号,外发单号
	 * 一次可以查询多个运单号
	 * @author foss-WeiXing
	 * @date 2015-01-15 下午3:29:01
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirAgencyQueryDto> queryExpressByPartialLineWaybillNos(AirAgencyQueryDto entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressByPartialLineWaybillNos", entity);
	}

	/**
	 * 根据运单号查询该快递代理递代理外发单审核状态集合
	 * @author foss-sunyanfei
	 * @date 2015-12-18 上午午8:38:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryExpressExternalBillStatusListByNo(AirAgencyQueryDto dto){
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressExternalBillStatusByNo", dto);
	}
}