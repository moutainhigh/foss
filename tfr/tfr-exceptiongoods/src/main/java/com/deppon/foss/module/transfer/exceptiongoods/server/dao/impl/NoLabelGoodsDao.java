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
 *  PROJECT NAME  : tfr-exceptiongoods
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/exceptiongoods/server/dao/impl/NoLabelGoodsDao.java
 *  
 *  FILE NAME          :NoLabelGoodsDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 实现无标签多货的基本操作
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:17:31
 */
public class NoLabelGoodsDao extends iBatis3DaoImpl implements INoLabelGoodsDao {
	
	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.exceptiongoods.nolabelgoods.";
	
	/**
	 * 保存无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-19 上午10:30:27
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao#addNoLabelGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity)
	 */
	@Override
	public int addNoLabelGoods(NoLabelGoodsEntity noLabelGoods) {
		noLabelGoods.setId(UUIDUtils.getUUID());
		noLabelGoods.setCreateDate(new Date());
		this.getSqlSession().insert(NAME_SPACE + "insertNoLabelGoods", noLabelGoods);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 查询无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-20 下午3:22:59
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao#queryNoLabelGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NoLabelGoodsEntity> queryNoLabelGoods(
			NoLabelGoodsEntity noLabelGoods, int limit, int start) {
		return this.getSqlSession().selectList(NAME_SPACE + "queryNoLabelGoods", noLabelGoods, new RowBounds(start, limit));
	}
	
	/**
	 * 查询无标签货物总记录数
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午7:23:35
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao#queryNoLabelGoodsCount(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity)
	 */
	@Override
	public Long queryNoLabelGoodsCount(NoLabelGoodsEntity noLabelGoods) {
		
		return (Long) this.getSqlSession().selectOne(NAME_SPACE + "queryNoLabelGoodsCount", noLabelGoods);
	
	}
    /**
     * 更新无标签货物
     * @author 097457-foss-wangqiang
     * @date 2012-11-27 下午7:27:29
     * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao#updateNoLabelGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity)
     */
	@Override
	public int updateNoLabelGoods(NoLabelGoodsEntity noLabelGoods) {
		this.getSqlSession().update(NAME_SPACE + "updateNoLabelGoods", noLabelGoods);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 根据OA差错编号更新无标签货物OA处理结果：找货状态、原运单号、流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午3:49:14
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao#updateProcessStatus(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity)
	 */
	@Override
	public int updateProcessStatus(NoLabelGoodsEntity noLabelGoods) { 
		return this.getSqlSession().update(NAME_SPACE + "updateProcessStatus", noLabelGoods);
	}
	/**
	 * 根据无标签单号更新该单号下的所有无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-10 下午4:50:33
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao#updateNoLabelGoodsByBillNo(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity)
	 */
	@Override
	public int updateNoLabelGoodsByBillNo(NoLabelGoodsEntity noLabelGoods){
		this.getSqlSession().update(NAME_SPACE + "updateNoLabelGoodsByBillNo", noLabelGoods);
		return FossConstants.SUCCESS;
	}
	/**
	 * 删除无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-14 下午1:48:20
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao#deleteNoLabelGoods(java.lang.String)
	 */
	@Override
	public int deleteNoLabelGoods(String noLabelGoodsId) {
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("noLabelGoodsId", noLabelGoodsId);
		this.getSqlSession().delete(NAME_SPACE + "deleteNoLabelGoods", paramsMap);
		return FossConstants.SUCCESS;
	}
	/**
	 * 更新无标签件数
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-14 下午6:26:42
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao#updateGoodsQty(java.lang.String, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateGoodsQty(String noLabelBillNo, int goodsQty){
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("noLabelBillNo", noLabelBillNo);
		paramsMap.put("goodsQty", goodsQty);
		this.getSqlSession().update(NAME_SPACE + "updateGoodsQty", paramsMap);
		return FossConstants.SUCCESS;
	}
	/**
	 * 根据无标签运单号、流水号查询
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-22 下午4:03:32
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NoLabelGoodsEntity> queryUniqNoLabelGoods(String noLabelBillNo, String noLabelSerialNo){
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("noLabelBillNo", noLabelBillNo);
		paramsMap.put("noLabelSerialNo", noLabelSerialNo);
		return this.getSqlSession().selectList(NAME_SPACE + "queryUniqNoLabelGoods", paramsMap);
	}

	
	/**
	* @description 根据运单号,流水号查询始发外场code
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao#selectFirstOrgCode(java.lang.String, java.lang.String, int)
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午5:51:49
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public String selectFirstOrgCode(String waybillNo, String serialNo , int routeNo) { 
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("serialNo", serialNo);
		paramsMap.put("routeNo", routeNo);
		return (String)this.getSqlSession().selectOne(NAME_SPACE + "selectFirstOrgCode", paramsMap);
	}

	
	/**
	* @description 根据部门code判断是否是快递分部
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao#selectIsExpressBranch(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年7月11日 下午2:22:08
	* @version V1.0
	*/
	@Override
	public String selectIsExpressBranch(String orgCode) {
		return (String)this.getSqlSession().selectOne(NAME_SPACE + "selectIsExpressBranch", orgCode);
	}
	/**
	 * @author nly
	 * @date 2015年6月16日 上午9:59:46
	 * @function 查询未上报差错的无标签信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NoLabelGoodsEntity> queryNoLabelReportList(Date date) {
		return this.getSqlSession().selectList(NAME_SPACE + "queryNoLabelReportList",date);
	}

	
	/**
	* @description 查询运单是否分批
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao#selectIsPartial(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年7月16日 下午4:17:37
	* @version V1.0
	*/
	@Override
	public String selectIsPartial(String waybillNo) {
		return (String)this.getSqlSession().selectOne(NAME_SPACE + "selectIsPartial", waybillNo);
	}

	
	/**
	* @description 如果不是分批配载,按运单号查询第一外场
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao#selectFirstOrgCodeIsPartial(java.lang.String, int)
	* @author 218381-foss-lijie
	* @update 2015年7月16日 下午4:54:45
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public String selectFirstOrgCodeIsPartial(String waybillNo, int routeNo) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("routeNo", routeNo);
		return (String)this.getSqlSession().selectOne(NAME_SPACE + "selectFirstOrgCodeIsPartial", paramsMap);
	}
}