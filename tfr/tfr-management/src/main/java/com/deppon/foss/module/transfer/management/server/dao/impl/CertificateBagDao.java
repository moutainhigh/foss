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
 *  PROJECT NAME  : tfr-management
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/dao/impl/CertificateBagDao.java
 *  
 *  FILE NAME          :CertificateBagDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.management.api.server.dao.ICertificateBagDao;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagQueryEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagReturnEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagTakeEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagDto;

/**
 *  证件包dao
 * @author dp-liming
 * @date 2012-10-15 下午16:17:41
 */
@SuppressWarnings("unchecked")
public class CertificateBagDao extends iBatis3DaoImpl implements ICertificateBagDao {
	private static final String NAMESPACE = "foss.management.certificatebag.";

	/**
	 *   分页查询证件包信息
	 * @author  dp-liming
	 * @date 2012-10-19 上午11:00:47
	 */
	@Override
	public List<CertificatebagEntity> queryCertificateBagList(CertificatebagDto certificatebagDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryCertificateBagList", certificatebagDto, rowBounds);		
	}

	/**
	 *   查询证件包总条数
	 * @author  dp-liming
	 * @date 2012-10-19 上午11:00:47
	 */
	@Override
	public Long queryCount(CertificatebagDto certificatebagDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryAirCargoVolumeCount", certificatebagDto);
	
	}

	@Override
	public List<CertificatebagEntity> displayCertificateBagDetail(CertificatebagDto certificatebagDto) {
		return this.getSqlSession().selectList(NAMESPACE+"displayCertificateBagDetail", certificatebagDto);
		 
	}

	@Override
	public int updateCertificateBagStatus(List<CertificatebagReturnEntity> certificatebagReturnList) {
		return this.getSqlSession().insert(NAMESPACE+"updateCertificateBagStatus",certificatebagReturnList);
	}



	@Override
	public int takeCertificateBag(List<CertificatebagTakeEntity> certificatebagTakeList) {
		return this.getSqlSession().insert(NAMESPACE+"takeCertificateBag",certificatebagTakeList);		
	}

	@Override
	public int addCertificateBagAction(CertificatebagEntity certificatebagEntity) {
		//System.out.println(certificatebagEntity.getId()+"   ====    "+certificatebagEntity.getHandOverStatus()+"   ****   "+certificatebagEntity.getCertificatebagStatus()+"   **==**   "+certificatebagEntity.getStatus());
		return this.getSqlSession().insert(NAMESPACE+"addCertificateBagAction",certificatebagEntity);		
	}

	@Override
	public CertificatebagEntity queryCertificateBagById(String id) {
		return (CertificatebagEntity) this.getSqlSession().selectOne(NAMESPACE+ "queryCertificateBagById", id);
	}

	@Override
	public  List<TruckTaskEntity> verifyTruckDepartPlan(String vehicleNo) {
		return this.getSqlSession().selectList(NAMESPACE+"verifyTruckDepartPlan",vehicleNo);
		 
	}

	@Override
	public int addTruckTask(TruckTaskEntity truckTaskEntity) {
		return this.getSqlSession().insert(NAMESPACE+"addTruckTask",truckTaskEntity);	
	}

	

	@Override
	public List<CertificatebagQueryEntity> queryTakeRefId(
			CertificatebagDto certificatebagDto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryTakeRefId", certificatebagDto);
		
	}

	@Override
	public List<CertificatebagQueryEntity> queryReturnByRefId(CertificatebagDto certificatebagDto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryReturnByRefId", certificatebagDto);
	}

	@Override
	public CertificatebagEntity queryByTruckTaskId(String truckTaskId) {
		return (CertificatebagEntity) this.getSqlSession().selectOne(NAMESPACE+ "queryByTruckTaskId", truckTaskId);
	}

	@Override
	public int updateCertificateBagAction(CertificatebagEntity certificatebagEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateCertificateBagAction", certificatebagEntity);		 
	}

	@Override
	public CertificatebagEntity queryCertificatebag(CertificatebagDto certificatebagDto) {
		return (CertificatebagEntity) this.getSqlSession().selectOne(NAMESPACE+ "queryCertificatebag", certificatebagDto);
	}

	  
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ICertificateBagDao#isOwnTruck(java.lang.String)
	 */

	@Override
	public int isOwnTruck(String newVehicleNo) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "isOwnTruck", newVehicleNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ICertificateBagDao#isLeasedTruck(java.lang.String)
	 */

	@Override
	public int isLeasedTruck(String newVehicleNo) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "isLeasedTruck", newVehicleNo);
	}

	  
	    /* (non-Javadoc)   
	     * @see com.deppon.foss.module.transfer.management.api.server.dao.ICertificateBagDao#isOwnTruckWithoutActive(java.lang.String)   
	     */   
	    
	@Override
	public int isOwnTruckWithoutActive(String newVehicleNo) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "isOwnTruckWithoutActive", newVehicleNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ICertificateBagDao#isLeasedTruckWithoutActive(java.lang.String)
	 */

	@Override
	public int isLeasedTruckWithoutActive(String newVehicleNo) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "isLeasedTruckWithoutActive", newVehicleNo);
	}
	
	
}