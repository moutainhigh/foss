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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/SignDetailDao.java
 * 
 * FILE NAME        	: SignDetailDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignDetailDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 签收明细Dao实现
 * 
 * @author foss-meiying
 * @date 2012-11-28 上午11:50:01
 * @since
 * @version
 */
public class SignDetailDao extends iBatis3DaoImpl implements ISignDetailDao {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity.";

	/**
	 * 根据到达联编号查询签收明细
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:49:12
	 * @param arrivesheetNo
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignDetailDao#queryByArrivesheetId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignDetailEntity> queryByArrivesheetNo(String arrivesheetNo) {
		return (List<SignDetailEntity>) this.getSqlSession().selectList(NAMESPACE + "selectByArriveSheetNo", arrivesheetNo);
	}

	/**
	 * 保存签收流水号到签收明细表里
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:13:30
	 * @param list
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignDetailDao
	 * #addSignDetail(java.util.List)
	 */
	@Override
	public int addSignDetail(List<SignDetailEntity> list) {
		for (SignDetailEntity signDetailEntity : list) {//循环签收明细集合
			signDetailEntity.setId(UUIDUtils.getUUID());//主键
			//创建时间
			signDetailEntity.setCreateDate(new Date());
			//修改时间
			signDetailEntity.setModifyDate(signDetailEntity.getCreateDate());
		}
		return this.getSqlSession().insert(NAMESPACE + "saveSerialNoList", list);
	}

	/**
	 * 根据主键id查询签收明细
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午8:13:46
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignDetailDao#queryByPrimaryKey(java.lang.String)
	 */
	@Override
	public SignDetailEntity queryByPrimaryKey(String id) {
		return (SignDetailEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}
	/**
	 * 单条添加签收明细信息
	 * @author foss-meiying
	 * @date 2012-12-20 上午9:40:02
	 * @param signDetailEntity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.ISignDetailDao#addSignDetailInfo(com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity)
	 */
	@Override
	public int addSignDetailInfo(SignDetailEntity signDetailEntity) {
		signDetailEntity.setId(UUIDUtils.getUUID());//主键
		//创建时间
		signDetailEntity.setCreateDate(new Date());
		//修改时间
		signDetailEntity.setModifyDate(signDetailEntity.getCreateDate());
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", signDetailEntity);
	}
	 /**
     * 给中转组提供 判断货物是否已签收的接口，参数（运单号、流水号）
     * @author foss-meiying
     * @date 2013-6-12 上午16:12:15
     * @param waybillNo 运单号
     * @param serialNo 流水号
     * @return String
     * @see
     */
	public String querySerialNoIsSign(StockDto dto){
		if((Integer)this.getSqlSession().selectOne(NAMESPACE + "selectSerialNoIsSign", dto)>0){
			return FossConstants.YES;
		}else {
			return FossConstants.NO;
		}
	}
	 /**
     * 给综合查询提供 已签收的流水号接口，参数（运单号，有效，是否作废，到达联状态是签收）
     * @author foss-meiying
     * @date 2013-6-12 上午16:12:15
     * @param waybillNo 运单号
     * @param serialNo 流水号
     * @return String
     * @see
     */
	@SuppressWarnings("unchecked")
	public List<String> querySerialNoByWaybillNo(StockDto dto){
		return (List<String>)this.getSqlSession().selectList(NAMESPACE + "selectSerialNoByWaybillNo", dto);
		
	}
	  /**
     * 
     * 根据到达联编号，流水号满足条件的信息
     * @author 159231-foss-meiying
     * @date 2013-11-22 下午6:23:44
     */
	@SuppressWarnings("unchecked")
	public List<SignDetailEntity> querySignDetailByParams(SignDetailEntity dto) {
		return (List<SignDetailEntity>) this.getSqlSession().selectList(NAMESPACE + "selectByParams", dto);
	}

	/**
	 * 根据主键删除数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月14日 09:59:09
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", id);
		return this.getSqlSession().delete(NAMESPACE+"deleteByPrimaryKey", map);
	}
	/**
  	 * 新增数据
  	 * @author Foss-105888-Zhangxingwang
  	 * @date 2014年3月14日 09:59:09
  	 */
	@Override
	public int insert(SignDetailEntity dto) {
		//id
		dto.setId(UUIDUtils.getUUID());
		//创建时间
		dto.setCreateDate(new Date());
		//修改时间
		dto.setModifyDate(dto.getCreateDate());
		return this.getSqlSession().insert(NAMESPACE+"insert", dto);
	}
	/**
  	 * 根据主键可选的更新数据
  	 * @author Foss-105888-Zhangxingwang
  	 * @date 2014年3月14日 09:59:09
  	 */
	@Override
	public int updateByPrimaryKeySelective(SignDetailEntity dto) {
		//修改时间
		dto.setModifyDate(new Date());
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective", dto);
	}
	/**
   	 * 根据主键更新数据
   	 * @author Foss-105888-Zhangxingwang
   	 * @date 2014年3月14日 09:59:09
   	 */
	@Override
	public int updateByPrimaryKey(SignDetailEntity dto) {
		//修改时间
		dto.setModifyDate(new Date());
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKey", dto);
	}
	
	/**
  	 * 根据到达联编号、流水号、旧的签收情况可选的更新数据
  	 * @author 
  	 * @date 
  	 */
	@Override
	public int updateByParams(SignDetailEntity dto) {
		//修改时间
		dto.setModifyDate(new Date());
		return this.getSqlSession().update(NAMESPACE+"updateByParams", dto);
	}
	
	/**
     * 根据到达联编号更新数据 (签收详情表的situation)
     * @author Foss-chenjunying DMANA-9716
     * @date 2015-03-25 15:36:50
     */
	public int updateByArrivesheetNo(SignDetailEntity entity){
		//修改时间
		entity.setModifyDate(new Date());
		return this.getSqlSession().update(NAMESPACE+"updateByArrivesheetNo",entity);
	}

	/**
     * 根据运单号查询异常签收明细
     * @author Foss-bieyexiong
     * @date 2016-02-22 14:10:36
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignDetailEntity> querySignDetailByWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE+"querySignDetailByWaybillNo",waybillNo);
	}
}