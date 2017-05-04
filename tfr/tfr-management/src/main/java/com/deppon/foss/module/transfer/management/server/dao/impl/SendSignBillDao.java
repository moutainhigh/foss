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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/dao/impl/SendSignBillDao.java
 *  
 *  FILE NAME          :SendSignBillDao.java
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
import com.deppon.foss.module.transfer.management.api.server.dao.ISendSignBillDao;
import com.deppon.foss.module.transfer.management.api.shared.domain.SendSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto;

/**
 *  派送签单Dao 
 * @author dp-liming
 * @date 2012-11-29 下午13:50:47
 */
@SuppressWarnings("unchecked")
public class SendSignBillDao extends iBatis3DaoImpl implements ISendSignBillDao {
	private static final String NAMESPACE = "foss.management.sendsignbill.";
	
	/****
	 * 新增派送签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:57:32
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ISendSignBillDao#addSendSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.SendSignBillEntity)
	 */
	@Override
	public int addSendSignBill(SendSignBillEntity sendSignBillEntity) {
		//新增派送签单
		return this.getSqlSession().insert(NAMESPACE+"addSendSignBill",sendSignBillEntity);
	}

	/****
	 * 删除派送签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:57:50
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ISendSignBillDao#deleteSendSignBill(java.lang.String)
	 */
	@Override
	public int deleteSendSignBill(String id) {
		//删除派送签单
		return this.getSqlSession().delete(NAMESPACE+"deleteSendSignBill",id);
	}

	/****
	 * 修改派送签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:58:03
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ISendSignBillDao#updateSendSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.SendSignBillEntity)
	 */
	@Override
	public int updateSendSignBill(SendSignBillEntity sendSignBillEntity) {
		//修改派送签单
		return this.getSqlSession().update(NAMESPACE+"updateSendSignBillEntity", sendSignBillEntity);
	}

	/****
	 * 根据Id查询派送签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:58:17
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ISendSignBillDao#querySendSignBillById(java.lang.String)
	 */
	@Override
	public SendSignBillEntity querySendSignBillById(String id) {
		//根据Id查询派送签单
		return (SendSignBillEntity)this.getSqlSession().selectOne(NAMESPACE+"querySendSignBillEntityById",id);		
	}

	/****
	 * 查询派送签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:58:31
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ISendSignBillDao#querySendSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto, int, int)
	 */
	@Override
	public List<SendSignBillEntity> querySendSignBill(SendSignBillDto sendSignBillDto, int start, int limit) {
		//设置分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//查询派送签单
		return this.getSqlSession().selectList(NAMESPACE+"querySendSignBill", sendSignBillDto, rowBounds);
	}

	/****
	 * 查询派送签单总数
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:58:52
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ISendSignBillDao#queryCount(com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto)
	 */
	@Override
	public Long queryCount(SendSignBillDto sendSignBillDto) {
		//查询派送签单总数
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryCount", sendSignBillDto);
	}

	/****
	 * 查询费用和提成
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:59:04
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ISendSignBillDao#querySendSignBillByFee(com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto)
	 */
	@Override
	public SendSignBillDto querySendSignBillByFee(SendSignBillDto sendSignBillDto) {
		//查询费用和提成
		return (SendSignBillDto)this.getSqlSession().selectOne(NAMESPACE+"querySendSignBillByFee", sendSignBillDto);
		
	}

	/****
	 * 导出excel
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:59:18
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.ISendSignBillDao#queryExportSendSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto)
	 */
	@Override
	public List<SendSignBillEntity> queryExportSendSignBill(SendSignBillDto sendSignBillDto) {
//		//导出excel
//		return this.getSqlSession().selectList(NAMESPACE+"queryExportSendSignBill", sendSignBillDto);	
		//查询派送签单
		return this.getSqlSession().selectList(NAMESPACE+"querySendSignBill", sendSignBillDto);
	}
}