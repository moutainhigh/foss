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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/dao/impl/OtherSignBillDao.java
 *  
 *  FILE NAME          :OtherSignBillDao.java
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
import com.deppon.foss.module.transfer.management.api.server.dao.IOtherSignBillDao;
import com.deppon.foss.module.transfer.management.api.shared.domain.OtherSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto;

/****
 * 其他签单Dao
 * @author ibm-zhangyixin
 * @date 2013-3-18 下午2:52:06
 */
@SuppressWarnings("unchecked")
public class OtherSignBillDao extends iBatis3DaoImpl implements		IOtherSignBillDao {
	private static final String NAMESPACE = "foss.management.othersignbill.";
	
	/****
	 * 查询其他签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:52:16
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IOtherSignBillDao#queryOtherSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto, int, int)
	 */
	@Override
	public List<OtherSignBillEntity> queryOtherSignBill(OtherSignBillDto otherSignBillDto, int start, int limit) {
		//设置分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回结果
		return this.getSqlSession().selectList(NAMESPACE+"queryOtherSignBill", otherSignBillDto, rowBounds);
	}

	/****
	 * 查询其他签单总数
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:53:05
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IOtherSignBillDao#queryCount(com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto)
	 */
	@Override
	public Long queryCount(OtherSignBillDto otherSignBillDto) {
		//查询其他签单总数
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryCount", otherSignBillDto);	
	}

	/**
	 * 查询其他签单费用和提成
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:53:20
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IOtherSignBillDao#queryOtherSignBillByFee(com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto)
	 */
	@Override
	public OtherSignBillDto queryOtherSignBillByFee(OtherSignBillDto otherSignBillDto) {
		//查询其他签单费用和提成
		return (OtherSignBillDto)this.getSqlSession().selectOne(NAMESPACE+"queryOtherSignBillByFee", otherSignBillDto);
		
	}
	
	/****
	 * 新增其他签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:53:35
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IOtherSignBillDao#addOtherSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.OtherSignBillEntity)
	 */
	@Override
	public int addOtherSignBill(OtherSignBillEntity otherSignBillEntity) {
		//新增其他签单
		return this.getSqlSession().insert(NAMESPACE+"addOtherSignBill",otherSignBillEntity);
	}

	/****
	 * 删除其他签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:53:49
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IOtherSignBillDao#deleteOtherSignBill(java.lang.String)
	 */
	@Override
	public int deleteOtherSignBill(String id) {
		//删除其他签单
		return this.getSqlSession().delete(NAMESPACE+"deleteOtherSignBill",id);
	}

	/****
	 * 修改其他签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:54:04
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IOtherSignBillDao#updateOtherSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.OtherSignBillEntity)
	 */
	@Override
	public int updateOtherSignBill(OtherSignBillEntity otherSignBillEntity) {
		//修改其他签单
		return this.getSqlSession().update(NAMESPACE+"updateOtherSignBill", otherSignBillEntity);
	}

	/****
	 * 根据Id查询其他签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:54:17
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IOtherSignBillDao#queryOtherSignBillById(java.lang.String)
	 */
	@Override
	public OtherSignBillEntity queryOtherSignBillById(String id) {
		//根据Id查询其他签单
		return (OtherSignBillEntity)this.getSqlSession().selectOne(NAMESPACE+"queryOtherSignBillById",id);		
	}

	/****
	 * 导出excel
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:54:32
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IOtherSignBillDao#queryExportOtherSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto)
	 */
	@Override
	public List<OtherSignBillEntity> queryExportOtherSignBill(OtherSignBillDto otherSignBillDto) {
		//导出excel
		return this.getSqlSession().selectList(NAMESPACE+"queryExportOtherSignBill", otherSignBillDto);
	}
}