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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/dao/impl/RegularTruckSignBillDao.java
 *  
 *  FILE NAME          :RegularTruckSignBillDao.java
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
import com.deppon.foss.module.transfer.management.api.server.dao.IRegularTruckSignBillDao;
import com.deppon.foss.module.transfer.management.api.shared.domain.RegularTruckSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.RegularTruckSignBillDto;

/****
 * 专线对发签单Dao接口
 * @author ibm-zhangyixin
 * @date 2013-3-18 下午2:54:58
 */
public class RegularTruckSignBillDao extends iBatis3DaoImpl implements IRegularTruckSignBillDao {
	private static final String NAMESPACE = "foss.management.regulartrucksignbill.";
	
	/****
	 * 新增专线对发签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:55:08
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IRegularTruckSignBillDao#addRegularTruckSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.RegularTruckSignBillEntity)
	 */
	@Override
	public int addRegularTruckSignBill(RegularTruckSignBillEntity regularTruckSignBillEntity) {
		//新增专线对发签单
		return this.getSqlSession().insert(NAMESPACE+"addRegularTruckSignBill", regularTruckSignBillEntity);		
	}

	/****
	 * 删除专线对发签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:55:25
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IRegularTruckSignBillDao#deleteRegularTruckSignBill(java.lang.String)
	 */
	@Override
	public int deleteRegularTruckSignBill(String id) {
		//删除专线对发签单
		return this.getSqlSession().delete(NAMESPACE+"deleteRegularTruckSignBill", id);
	}

	/****
	 * 修改专线对发签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:55:39
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IRegularTruckSignBillDao#updateRegularTruckSignBill(com.deppon.foss.module.transfer.management.api.shared.domain.RegularTruckSignBillEntity)
	 */
	@Override
	public int updateRegularTruckSignBill(RegularTruckSignBillEntity regularTruckSignBillEntity) {
		//修改专线对发签单
		return this.getSqlSession().update(NAMESPACE+"updateRegularTruckSignBill", regularTruckSignBillEntity);	
	}

	/****
	 * 查询专线对发签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:55:57
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IRegularTruckSignBillDao#queryRegularTruckSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.RegularTruckSignBillDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RegularTruckSignBillEntity> queryRegularTruckSignBill(RegularTruckSignBillDto regularTruckSignBillDto, int start,int limit) {
		//设置分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//查询专线对发签单
		return this.getSqlSession().selectList(NAMESPACE + "queryRegularTruckSignBill", regularTruckSignBillDto, rowBounds);	
		
	}

	/****
	 * 查询专线对发签单总数
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:56:22
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IRegularTruckSignBillDao#queryCount(com.deppon.foss.module.transfer.management.api.shared.dto.RegularTruckSignBillDto)
	 */
	@Override
	public Long queryCount(RegularTruckSignBillDto regularTruckSignBillDto) {
		//查询专线对发签单总数
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryCount", regularTruckSignBillDto);
	}

	/****
	 * 查询费用和提成
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:56:36
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IRegularTruckSignBillDao#queryRegularTruckSignBillByFee(com.deppon.foss.module.transfer.management.api.shared.dto.RegularTruckSignBillDto)
	 */
	@Override
	public RegularTruckSignBillDto queryRegularTruckSignBillByFee(RegularTruckSignBillDto regularTruckSignBillDto) {
		//查询费用和提成
		return (RegularTruckSignBillDto)this.getSqlSession().selectOne(NAMESPACE+"queryRegularTruckSignBillByFee", regularTruckSignBillDto);
	}

	/****
	 * 根据Id查询专线对发签单
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:56:56
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IRegularTruckSignBillDao#queryRegularTruckSignBillById(java.lang.String)
	 */
	@Override
	public RegularTruckSignBillEntity queryRegularTruckSignBillById(String id) {
		//根据Id查询专线对发签单
		return (RegularTruckSignBillEntity)this.getSqlSession().selectOne(NAMESPACE+"queryRegularTruckSignBillById", id);
	}
	
	/****
	 * 导出excel
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:57:10
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IRegularTruckSignBillDao#queryExportRegularTruckSignBill(com.deppon.foss.module.transfer.management.api.shared.dto.RegularTruckSignBillDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RegularTruckSignBillEntity> queryExportRegularTruckSignBill(RegularTruckSignBillDto regularTruckSignBillDto) {
		//导出excel
		return this.getSqlSession().selectList(NAMESPACE+"queryExportRegularTruckSignBill", regularTruckSignBillDto);	
		
	}

}