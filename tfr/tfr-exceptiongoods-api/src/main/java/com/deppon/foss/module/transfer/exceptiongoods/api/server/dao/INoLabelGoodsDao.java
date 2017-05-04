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
 *  PROJECT NAME  : tfr-exceptiongoods-api
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/INoLabelGoodsDao.java
 * 
 *  FILE NAME          :INoLabelGoodsDao.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity;
/**
 * 定义了无标签多货的基本操作
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:03:43
 */
public interface INoLabelGoodsDao {
	
	/**
	 * 保存无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-19 上午10:29:48
	 */
	int addNoLabelGoods(NoLabelGoodsEntity noLabelGoods);
	
	/**
	 * 查询无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-20 下午3:22:23
	 */
	List<NoLabelGoodsEntity> queryNoLabelGoods(NoLabelGoodsEntity noLabelGoods, int limit, int start);
	
	/**
	 * 查询无标签货物总记录数
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-20 下午3:43:37
	 */
	Long queryNoLabelGoodsCount(NoLabelGoodsEntity noLabelGoods);
	
	/**
	 * 更新无标签货物信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-27 下午7:09:28
	 */
	int updateNoLabelGoods(NoLabelGoodsEntity noLabelGoods);
	
	/**
	 * 根据OA差错编号更新无标签货物OA处理结果：找货状态、原运单号、流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午3:46:45
	 */
	int updateProcessStatus(NoLabelGoodsEntity noLabelGoods);
	/**
	 * 根据无标签单号更新该单号下的所有无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-10 下午4:44:55
	 */
	int updateNoLabelGoodsByBillNo(NoLabelGoodsEntity noLabelGoods);
	
	/**
	 * 删除无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-14 下午1:44:47
	 */
	int deleteNoLabelGoods(String noLabelGoodsId);
	
	/**
	 * 更新无标签总件数
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-14 下午6:22:45
	 */
	int updateGoodsQty(String noLabelBillNo, int goodsQty);
	/**
	 * 根据无标签运单号、流水号查询
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-22 下午4:03:32
	 */
	List<NoLabelGoodsEntity> queryUniqNoLabelGoods(String noLabelBillNo, String noLabelSerialNo);
	/**
	 * @author nly
	 * @date 2015年6月16日 上午9:59:46
	 * @function 查询未上报差错的无标签信息
	 * @return
	 */
	List<NoLabelGoodsEntity> queryNoLabelReportList(Date date);
	
	
	/**
	* @description 根据运单号,流水号查询始发外场code
	* @param waybillNo
	* @param serialNo
	* @param rountNo
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午5:47:59
	*/
	String selectFirstOrgCode(String waybillNo , String serialNo , int routeNO);
	
	
	/**
	* @description 如果不是分批配载,按运单号查询第一外场
	* @param waybillNo
	* @param routeNO
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月16日 下午4:52:51
	*/
	String selectFirstOrgCodeIsPartial(String waybillNo,int routeNO);
	/**
	* @description 根据部门code判断是否是快递分部
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月11日 下午2:20:50
	*/
	String selectIsExpressBranch(String orgCode);
	
	
	/**
	* @description 查询运单是否分批
	* @param waybill
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月16日 下午4:17:14
	*/
	String selectIsPartial(String waybillNo);
}