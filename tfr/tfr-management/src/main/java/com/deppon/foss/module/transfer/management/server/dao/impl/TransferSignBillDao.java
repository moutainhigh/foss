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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/dao/impl/TransferSignBillDao.java
 *  
 *  FILE NAME          :TransferSignBillDao.java
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
import com.deppon.foss.module.transfer.management.api.server.dao.ITransferSignBillDao;
import com.deppon.foss.module.transfer.management.api.shared.domain.TransferSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.TransferSignBillDto;

/**
 *  转货车签单Dao
 * 
 * @author dp-liming
 * @date 2012-11-29 上午10:58:18
 */
@SuppressWarnings("unchecked")
public class TransferSignBillDao extends iBatis3DaoImpl implements ITransferSignBillDao {
	private static final String NAMESPACE = "foss.management.transfersignbill.";	

	/****
	 * 新增转货车签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:59:40
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ITransferSignBillDao#addTransferSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.TransferSignBillEntity)
	 */
	@Override
	public int addTransferSignBill(TransferSignBillEntity transferSignBillEntity) {
		//新增转货车签单
		return this.getSqlSession().insert(NAMESPACE+"addTransferSignBill", transferSignBillEntity);
	}

	/****
	 * 删除转货车签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:59:59
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ITransferSignBillDao#deleteTransferSignBill(java.lang.String)
	 */
	@Override
	public int deleteTransferSignBill(String id) {
		//删除转货车签单
		return this.getSqlSession().delete(NAMESPACE+"deleteTransferSignBill",id);
	}

	/****
	 * 修改转货车签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:00:13
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ITransferSignBillDao#updateTransferSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.TransferSignBillEntity)
	 */
	@Override
	public int updateTransferSignBill(TransferSignBillEntity transferSignBillEntity) {
		//修改转货车签单
		return this.getSqlSession().update(NAMESPACE+"updateTransferSignBill", transferSignBillEntity);
	}

	/****
	 * 转货车签单entity
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:00:28
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ITransferSignBillDao#queryTransferSignBillById(java.lang.String)
	 */
	@Override
	public TransferSignBillEntity queryTransferSignBillById(String id) {
		//转货车签单entity
		return (TransferSignBillEntity)this.getSqlSession().selectOne(NAMESPACE+"queryTransferSignBillById",id);		
	}

	/****
	 * 查询转货车签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:00:45
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ITransferSignBillDao#queryTransferSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.TransferSignBillDto, int, int)
	 */
	@Override
	public List<TransferSignBillEntity> queryTransferSignBill(TransferSignBillDto transferSignBillDto, int start, int limit) {
		//设置分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//查询转货车签单
		return this.getSqlSession().selectList(NAMESPACE+"queryTransferSignBill", transferSignBillDto, rowBounds);	
	}

	/****
	 * 查询转货车签单总数
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:01:01
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ITransferSignBillDao#queryCount(com.deppon.foss.module.transfer.management.api.shared.dto.TransferSignBillDto)
	 */
	@Override
	public Long queryCount(TransferSignBillDto transferSignBillDto) {
		//查询转货车签单总数
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryCount", transferSignBillDto);	
	}

	/****
	 * 查询费用和提成
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:01:15
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ITransferSignBillDao#queryTransferSignBillByFee(com.deppon.foss.module.transfer.management.api.shared.dto.TransferSignBillDto)
	 */
	@Override
	public TransferSignBillDto queryTransferSignBillByFee(TransferSignBillDto transferSignBillDto) {
		//查询费用和提成
		return (TransferSignBillDto)this.getSqlSession().selectOne(NAMESPACE+"queryTransferSignBillByFee", transferSignBillDto);
	}

	/****
	 * 导出excel
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午3:01:31
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ITransferSignBillDao#queryExportTransferSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.TransferSignBillDto)
	 */
	@Override
	public List<TransferSignBillEntity> queryExportTransferSignBill(TransferSignBillDto transferSignBillDto) {
		//导出excel
		return  this.getSqlSession().selectList(NAMESPACE+"queryExportTransferSignBill", transferSignBillDto);
	}
}