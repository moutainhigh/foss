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
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/SaleBookingSpaceDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.ISaleBookingSpaceDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.SaleBookingSpaceDto;
/**
 * 营业部订舱dto
 * @author 038300-foss-pengzhen
 * @date 2012-12-25 下午8:08:38
 */
public class SaleBookingSpaceDao extends iBatis3DaoImpl implements ISaleBookingSpaceDao {
	/**
	 * 定义局部命名空间
	 */
	public static final String NAMESPACE = "foss.airfreight.airspace.";
	/**
	 * 查询营业部订舱信息, 参数1：entity 封装的查询对象
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-3 上午9:33:42
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SaleBookingSpaceDto> queryBookingSpace(
			SaleBookingSpaceDto dto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		//查询营业部订舱信息, 参数1：entity 封装的查询对象
		return this.getSqlSession().selectList(NAMESPACE + "selectBookingSpace", dto, rowBounds);
	}
	
	/**
	 * 新增订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-3 下午4:41:16
	 */
	@Override
	public void addBookingSpace(SaleBookingSpaceDto dto) {
		//新增订舱信息
		this.getSqlSession().insert(NAMESPACE + "insertBookingSpace", dto);
	}

	/**
	 * 受理营业部订舱
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-5 下午3:46:43
	 */
	@Override
	public void acceptBookingSpace(SaleBookingSpaceDto dto) {
		//受理营业部订舱
		this.getSqlSession().update(NAMESPACE + "acceptBookingSpace", dto);
	}
	
	/**
	 * 通过id查询营业部订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-12 上午10:34:18
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.ISaleBookingSpaceDao#queryBookingSpaceById()
	 */
	@Override
	public SaleBookingSpaceDto queryBookingSpaceById(String id) {
		//通过id查询营业部订舱信息
		return (SaleBookingSpaceDto)this.getSqlSession().selectOne(NAMESPACE + "selecBookingSpaceById", id);
	}

	/**
	 * 更新营业部订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-12 上午11:29:27
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.ISaleBookingSpaceDao#updateBookingSpace(com.deppon.foss.module.transfer.airfreight.api.shared.dto.SaleBookingSpaceDto)
	 */
	@Override
	public void updateBookingSpace(SaleBookingSpaceDto dto) {
		//更新营业部订舱信息
		this.getSqlSession().update(NAMESPACE + "updateBookingSpace", dto);
	}
	
	/**
	 * 获取总调未受理订舱总条数
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-12 下午5:31:51
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.ISaleBookingSpaceDao#queryNoAcceptCount()
	 */
	@Override
	public Long queryNoAcceptCount(SaleBookingSpaceDto dto) {
		//获取总调未受理订舱总条数
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "selectNoAcceptCount", dto);
	}
	
	/**
	 * 获取订舱列表总记录数
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-13 下午5:24:01
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.ISaleBookingSpaceDao#queryCount(com.deppon.foss.module.transfer.airfreight.api.shared.dto.SaleBookingSpaceDto)
	 */
	@Override
	public Long queryCount(SaleBookingSpaceDto dto) {
		//获取订舱列表总记录数
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "selectCount", dto);
	}
	/**
	 * 根据运单号查询需要订舱的信息
	 * @author foss-liuxue(for IBM)
	 * @date 2013-5-21 下午4:35:38
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SaleBookingSpaceDto> queryWaybillInfo(String waybillNo){
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillInfo", waybillNo);
	}

}