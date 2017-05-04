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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/DeliverHandlerDao.java
 * 
 * FILE NAME        	: DeliverHandlerDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;


import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.IDeliverHandlerDao;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 派送处理dao
 * @author foss-meiying
 * @date 2012-11-28 上午11:45:42
 * @since
 * @version
 */
public class DeliverHandlerDao extends iBatis3DaoImpl implements IDeliverHandlerDao {
	/**
	 * 派送处理命名空间
	 */
	private static final String NAMESPACE="com.deppon.foss.module.pickup.sign.api.shared.domain.deliver.";
	/**
	 * 派送处理      根据（派送编号）查询运单编号,到达联编号
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:11:02
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IDeliverHandlerDao#queryDeliverbillWaybillNo(com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliverbillDetailDto> queryDeliverbillWaybillNo(
			DeliverbillDetailDto dto) {
		return this.getSqlSession().selectList(NAMESPACE+"getDeliverbillWaybillNo",dto);
	}
	/**
	 * 根据运单号查询运单里的财务信息
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:12:08
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IDeliverHandlerDao#queryWaybillByWaybillNo(java.lang.String)
	 */
	@Override
	public FinancialDto queryWaybillByWaybillNo(String waybillNo) {
		WaybillEntity waybillEntity=new WaybillEntity();
		waybillEntity.setActive(FossConstants.ACTIVE);//生效
		waybillEntity.setWaybillNo(waybillNo);//运单号
		return (FinancialDto)this.getSqlSession().selectOne(NAMESPACE+"getFinancePartByWaybillNo",waybillEntity);
	}
	/**
	 * 查询当前派送单中所有的运单到达联是否都完成了签收确认
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:12:28
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IDeliverHandlerDao#queryArrivesheetIsSign(com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliverbillDetailDto> queryArrivesheetIsSign(
			DeliverbillDetailDto dto) {
		return this.getSqlSession().selectList(NAMESPACE+"getNotSignWaybillNo",dto);
	}
	/**
	 * 送货确认---根据派送单查询货物拉回信息
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:12:50
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IDeliverHandlerDao#queryStayHandoverBydeliverbillNo(com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StayHandoverDto> queryStayHandoverBydeliverbillNo(
			DeliverbillDetailDto dto) {
		return this.getSqlSession().selectList(NAMESPACE+"getStayHandoverBydeliverbillNo",dto);
		}
	/**
	 * 
	 * 更新派送单
	 * 
	 * @param deliverbill
	 *            派送单
	 * @return 
	 * @author foss-meiying
	 * @date 2013-06-29 下午2:54:18
	 */
	@Override
	public int updateDeliverbill(DeliverbillDetailDto dto) {
		//添加时间字段，派送单修改时更新该时间字段
		dto.setModifyTime(new Date());
		
		return this.getSqlSession().update(
				NAMESPACE + "updateDeliverbillStatusById", dto);

	}
	/**
	 * 
	 * 根据运单号查询部分派送单信息（综合查询提供到达联派送中接口）
	 * 
	 * @return 
	 * @author foss-meiying
	 * @date 2013-08-7 下午2:54:18
	 */
	public List<DeliverbillDetailDto> queryPartDeliverBillByWaybillNo(DeliverbillDetailDto dto){
		return this.getSqlSession().selectList(NAMESPACE+"serachPartDeliverBillByWaybillNo",dto);
	}
	/**
	 * 派送处理      根据（派送编号）查询运单编号,到达联编号 快递相关
	 * @author foss-yuting
	 * @date 2014-10-09 下午8:11:02
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IDeliverHandlerDao#queryDeliverbillWaybillNo(com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliverbillDetailDto> queryDeliverbillWaybillNoExp(
			DeliverbillDetailDto dto) {
		return this.getSqlSession().selectList(NAMESPACE+"getDeliverbillWaybillNoExp",dto);
	}
}