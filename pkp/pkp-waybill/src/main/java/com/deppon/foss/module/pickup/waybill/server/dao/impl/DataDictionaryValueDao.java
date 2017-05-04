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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/DataDictionaryValueDao.java
 * 
 * FILE NAME        	: DataDictionaryValueDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IAddressServiceHessianRemoting;
import com.deppon.foss.util.define.FossConstants;

/**
 * 数据字典值
 * 
 * @author 105089-foss-yangtong
 * @date 2012-10-31 下午2:45:39
 */
public class DataDictionaryValueDao implements IDataDictionaryValueDao {
	/**
	 * 名称空间
	 */

	IAddressServiceHessianRemoting iAddressServiceHessianRemoting;
	/**
	 * 行政区域 Service接口
	 */
	IAdministrativeRegionsService administrativeRegionsService;

	private static final String NAMESPACE = "foss.pkp.DataDictionaryValueEntityMapper.";
	/**
	 * 数据库连接
	 */
	private SqlSession sqlSession;

	/**
	 * 数据库连接
	 * 
	 * @return void
	 * @since:1.6
	 */
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/**
	 * 
	 * <p>
	 * 通过主键获取
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-16 下午5:33:04
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IDataDictionaryDao#queryByPrimaryKey(java.lang.String)
	 */
	public DataDictionaryValueEntity queryByPrimaryKey(String id) {
		return (DataDictionaryValueEntity) sqlSession.selectOne(NAMESPACE
				+ "selectDataDictionaryByPrimaryKey", id);
	}

	/**
	 * 
	 * <p>
	 * 通过词条代码查询
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-16 下午5:33:08
	 * @param termsCode
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IDataDictionaryDao#queryByTermsCode(java.lang.String)
	 */
	public List<DataDictionaryValueEntity> queryByTermsCode(String termsCode) {
		DataDictionaryValueEntity dataDic = new DataDictionaryValueEntity();
		dataDic.setActive(FossConstants.ACTIVE);
		dataDic.setTermsCode(termsCode);
		return sqlSession.selectList(NAMESPACE
				+ "selectDataDictValueByTermsCode", dataDic);
	}

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addDataDictionaryValue(
			DataDictionaryValueEntity dataDictionaryValue) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", dataDictionaryValue.getId());
		String id = (String) sqlSession
				.selectOne(NAMESPACE + "selectById", map);
		if (ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(id,
				ObjectUtils.NULL))) {
			sqlSession.insert(NAMESPACE + "insertDataDictionaryValue",
					dataDictionaryValue);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * 功能：更新条记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void updateDataDictionaryValue(
			DataDictionaryValueEntity dataDictionaryValue) {
		sqlSession.update(NAMESPACE + "updateDataDictionaryValue",
				dataDictionaryValue);

	}

	/**
	 * 
	 * 根据Code查找数据字典
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-4 上午11:46:28
	 */
	public DataDictionaryValueEntity queryDataDictoryValueByCode(
			String termsCode, String valueCode) {
		DataDictionaryValueEntity dataDic = new DataDictionaryValueEntity();
		dataDic.setActive(FossConstants.ACTIVE);
		dataDic.setTermsCode(termsCode);
		dataDic.setValueCode(valueCode);
		return (DataDictionaryValueEntity) sqlSession.selectOne(NAMESPACE
				+ "selectDataDictValueByValueCode", dataDic);
	}

	/**
	 * @param termsCode
	 * @param valueCode
	 *            兼容航班 晚班类型
	 * @author 218459-foss-dongsiwei
	 * @return
	 */
	public DataDictionaryValueEntity queryDataDictoryValueByValueCode(
			String termsCode, String valueCode) {
		DataDictionaryValueEntity dataDic = new DataDictionaryValueEntity();
		dataDic.setTermsCode(termsCode);
		dataDic.setValueCode(valueCode);
		List<DataDictionaryValueEntity> list = sqlSession.selectList(NAMESPACE
				+ "selectDataDictValueByValueCode", dataDic);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 删除
	 * 
	 * @param dataDictionaryValue
	 */
	public void delete(DataDictionaryValueEntity dataDictionaryValue) {
		sqlSession.delete(NAMESPACE + "delete", dataDictionaryValue);
	}

	@Override
	public String getReceivingAddress(String code) {
		if (StringUtil.isBlank(code)) {
			return null;
		}
		List<AddressFieldDto> list = sqlSession.selectList(NAMESPACE
				+ "selectReceivingAddress", code);
		if (!list.isEmpty() && list.size() == 1) {
			String provCode = null;
			String cityCode = null;
			String distCode = null;
			String receiveCode = null;
			String address = null;
			
			AddressFieldDto addressFieldDto = list.get(0);
			if (addressFieldDto != null) {
				if (StringUtil.isNotBlank(addressFieldDto.getProvinceId())) {
					provCode = addressFieldDto.getProvinceId();
				}
				if (StringUtil.isNotBlank(addressFieldDto.getCityId())) {
					cityCode = addressFieldDto.getCityId();
				}
				if (StringUtil.isNotBlank(addressFieldDto.getCountyId())) {
					distCode = addressFieldDto.getCountyId();
				}
				if (StringUtil.isNotBlank(addressFieldDto.getVillageTownId())) {
					receiveCode = addressFieldDto.getVillageTownId();
				}
				if (StringUtil.isNotBlank(addressFieldDto.getAddress())) {
					address = addressFieldDto.getAddress();
				}
			}
			
			return getCompleteAddressNew(provCode, cityCode, distCode, address,
					receiveCode);
		} else {
			return "";
		}
	}
	/**
	 * 
	 * 根据传入参数，获取完整的地址信息
	 * 
	 * @param provCode
	 *            省code
	 * @param cityCode
	 *            市code
	 * @param distCode
	 *            区code
	 * @param receiveCode
	 *            镇code
	 * @param address
	 *            地址
	 * @return 完整地址
	 * @author ibm-wangfei
	 * @date Mar 7, 2013 9:53:28 AM
	 */
	public String getCompleteAddressNew(String provCode, String cityCode,
			String distCode, String address, String receiveCode) {
		StringBuffer sb = new StringBuffer();

		AdministrativeRegionsEntity entity;
		// 根据编码查询 -- 省
		if (StringUtil.isNotBlank(provCode)) {

			// administrativeRegionsService.queryCountyByCode(provCode);
			entity = administrativeRegionsService
					.queryAdministrativeRegionsByCode(provCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
			}
		}
		// 根据编码查询 -- 市
		if (StringUtil.isNotBlank(cityCode)) {
			entity = administrativeRegionsService
					.queryAdministrativeRegionsByCode(cityCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
			}
		}
		// 根据编码查询 -- 区
		if (StringUtil.isNotBlank(distCode)) {
			entity = administrativeRegionsService
					.queryAdministrativeRegionsByCode(distCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
			}
		}
		// 根据编码查询 -- 镇
		if (StringUtil.isNotBlank(receiveCode)) {
			entity = administrativeRegionsService
					.queryAdministrativeRegionsByCode(receiveCode);
			if (entity != null && StringUtil.isNotBlank(entity.getName())) {
				sb.append(entity.getName());
				sb.append(NotifyCustomerConstants.SPLIT_CHAR_DASH);
			}
		}

		if (StringUtil.isNotBlank(sb.toString()) && sb.toString().length() > 0) {
			return StringUtil.isNotBlank(address) ? sb.append(address)
					.toString() : sb.substring(0, sb.length() - 1);
		} else {
			return StringUtil.isNotBlank(address) ? address : "";
		}
	}

	public IAdministrativeRegionsService getAdministrativeRegionsService() {
		return administrativeRegionsService;
	}

}