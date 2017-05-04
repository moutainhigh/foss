/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-package
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/server/dao/impl/PackOutDao.java
 *  
 *  FILE NAME          :PackOutDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-package
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.server.dao.impl
 * FILE    NAME: PackOutDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPackOutDao;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireDetailsEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.WaybillNoLogingDateDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-10-26 上午11:16:02
 */
public class PackOutDao extends iBatis3DaoImpl implements IPackOutDao {

	private final Logger LOG = Logger.getLogger(getClass());

	/**
	 * 更新运单号
	 * @param origWaybillNo
	 * @param newWaybillNo
	 * @return
	 * @date 2013-03-18 下午6:00:00
	 */
	@Override
	public int updateWaybillNo(Map<String, Object> map) {
		map.put("modifyDate",new Date());//增加修改时间
		return getSqlSession().insert(PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"updateWaybillNo", map);
	}



	/** 
	 * 用于接送货开单时调用，插入需要包装的运单信息
	 * @param packagingRequireEntity 包装需求数据
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-26 上午11:16:02
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IPackOutDao#addPackagingRequire(com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireEntity)
	 */
	@Override
	public int addPackagingRequire(PackagingRequireEntity packagingRequireEntity) {
		//增加创建时间和修改时间
		Date date = new Date();
		packagingRequireEntity.setCreateDate(date);
		packagingRequireEntity.setModifyDate(date);
		return getSqlSession().insert(
				PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"insertPackagingRequireEntity", 
				packagingRequireEntity);
	}
	
	

	/**
	 * 
	 * 保存包装需求明细
	 * @param packagingRequireDetails 包装需求明细
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-5 下午3:19:30
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IPackOutDao#addPackagingRequireDetails(java.util.List)
	 */
	@Override
	public int addPackagingRequireDetails(List<PackagingRequireDetailsEntity> packagingRequireDetails) {
		final String sqlInsert = "INSERT INTO tfr.t_opt_package_request_detail (id, waybill_no, serial_no, billing_time, packagerequest_id,is_packed,package_type,actual_package_id,create_time,modify_time) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Date date = new Date();

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getSqlSession().getConfiguration().getEnvironment().getDataSource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sqlInsert);

			for (PackagingRequireDetailsEntity info : packagingRequireDetails) {
				ps.setString(1, info.getId());
				ps.setString(2, info.getWaybillNo());
				ps.setString(PackagingConstants.SONAR_NUMBER_3, info.getSerialNo());
				ps.setTimestamp(PackagingConstants.SONAR_NUMBER_4, new java.sql.Timestamp(info.getWaybillCreateDate().getTime()));
				ps.setString(PackagingConstants.SONAR_NUMBER_5, info.getRequireId());
				ps.setString(PackagingConstants.SONAR_NUMBER_6, info.getIsPacked());
				ps.setString(PackagingConstants.SONAR_NUMBER_7, info.getPackageType());
				ps.setString(PackagingConstants.SONAR_NUMBER_8, info.getActualPackageId());
				ps.setTimestamp(PackagingConstants.SONAR_NUMBER_9, new java.sql.Timestamp(date.getTime()));
				ps.setTimestamp(PackagingConstants.SONAR_NUMBER_10, new java.sql.Timestamp(date.getTime()));

				ps.addBatch();
			}

			ps.executeBatch();
			con.commit();

		} catch (SQLException e) {
			LOG.error("PackOutDao.addPackagingRequireDetails error", e);
			try {
				//sonar 判空 218427
				if(con==null){
					throw new TfrBusinessException("con为空");
				}
				con.rollback();
			} catch (SQLException e1) {
				LOG.error("PackOutDao.addPackagingRequireDetails rollback error", e1);
				throw new RuntimeException(e1);
			}
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					LOG.error("PackOutDao.addPackagingRequireDetails ps.close() error", e);
					throw new RuntimeException(e);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LOG.error("PackOutDao.addPackagingRequireDetails con.close() error", e);
					throw new RuntimeException(e);
				}
			}
		}

		return FossConstants.SUCCESS;

	}
	
	/**
	 * 
	 * 删除包装需求明细
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午7:54:40
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IPackOutDao#deletePackagingRequireDetails(java.util.List)
	 */
	@Override
	public int deletePackagingRequireDetails(
			List<PackagingRequireDetailsEntity> packagingRequireDetails) {
		
		return getSqlSession().delete(
				PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"deleteSerialList", 
				packagingRequireDetails);
	}


	/** 
	 * 用于接送货发更改时调用，修改需要包装的运单信息
	 * @param packagingRequireEntity 包装需求数据
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-26 上午11:16:02
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IPackOutDao#addPackagingRequire(com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireEntity)
	 */
	@Override
	public int updatePackagingRequire(
			PackagingRequireEntity packagingRequireEntity) {
		//增加修改时间
		packagingRequireEntity.setModifyDate(new Date());
		return getSqlSession().update(
				PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"updatePackagingRequireEntity", 
				packagingRequireEntity);
	}
	
	/**
	 * 
	 * 用于接送货发更改时调用，修改需要包装的运单信息
	 * @param waybillNo 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-26 上午11:11:34
	 */
	@Override
	public int disablePackagingRequire(String waybillNo) {
		Map<String,Object> paramMap  = new HashMap<String,Object>();
		//增加修改时间
		paramMap.put("modifyDate",new Date());
		paramMap.put("waybillNo",waybillNo);
		
		return getSqlSession().update(
				PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"disablePackagingRequireEntity", 
				paramMap);
	}

	/**
	 * 
	 * 插入包装需求前查看是否已存在该运单
	 * @param waybillNo 运单号
	 * @return 符合该运单号的包装需求个数
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-13 上午11:11:20
	 */
	@Override
	public int queryPackagingRequire(String waybillNo) {
		
		return Integer.parseInt(getSqlSession().
				selectOne(PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"selectRequireCount", 
						waybillNo).toString());
	}
	
	/**
	 * 返回包装需求主表信息
	 * @param waybillNo 运单号
	 * @return 符合该运单号的包装需求个数
	 * @author 134019-foss-于永祥
	 * @date 2013年11月30日 11:51:35
	 */
	@Override
	public PackagingRequireEntity queryPackagingRequireWithId(String waybillNo) {
		
		return (PackagingRequireEntity) getSqlSession().
				selectOne(PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"selectPackagingRequireWithId", 
						waybillNo);
	}
	/**
	 * 修改包装需求中需要包装件数
	 * @author 046130-foss-xuduowei
	 * @param waybillNo
	 * @return
	 * @date 2013-04-26 下午6:00:00
	 */
	@Override
	public int updateNeedPackNum(String waybillNo) {
		Map<String,Object> paramMap  = new HashMap<String,Object>();
		//增加修改时间
		paramMap.put("modifyDate",new Date());
		paramMap.put("waybillNo",waybillNo);
			
		return getSqlSession().update(PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"updateNeedPackNum", paramMap);
	}


	/**
	 * 插入包装货物登入包装货去或登出包装货区的时
	 * @return
	 * @date 2013-03-18 下午6:00:00
	 */
	@Override
	public int insertWaybillNoLogingDate(
			WaybillNoLogingDateDto waybillNoLogingDateDto) {
		//增加修改时间
				waybillNoLogingDateDto.setModifyDate(new Date());
		//登入
		if(StringUtils.equals(waybillNoLogingDateDto.getType(), PackagingConstants.PACKAGING_LOGING_IN)){
			
			return getSqlSession().update(PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"insertWaybillNoLoginTime", waybillNoLogingDateDto);
		}else{//登出
			
			return getSqlSession().update(PackagingConstants.PACKAGING_OUT_IBATIS_NAMESAPCE+"insertWaybillNoLogoutTime", waybillNoLogingDateDto);
		}
		
		
	}
	
	
}