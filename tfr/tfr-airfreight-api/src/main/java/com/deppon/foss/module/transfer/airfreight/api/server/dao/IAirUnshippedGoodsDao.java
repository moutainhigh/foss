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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/IAirUnshippedGoodsDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedGoodsEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedNoticeEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirUnshippedGoodsDto;

/**
 * 拉货相关操作
 * @author foss-liuxue(for IBM)
 * @date 2012-11-29 下午2:38:42
 */
public interface IAirUnshippedGoodsDao {

	/**
	 * 根据正单号查询拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-3 下午2:24:15
	 */
	List<AirUnshippedGoodsEntity> queryAirUnshippedGoodsByAirWaybillNo(AirUnshippedGoodsDto airUnshippedGoodsDto, int start,int limit);
	
	/**
	 * 根据正单号获取总记录条数
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-3 下午2:51:38
	 */
	Long getCountByAirWaybillNo(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 根据运单号查询拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-3 下午2:24:15
	 */
	List<AirUnshippedGoodsEntity> queryAirUnshippedGoodsByWaybillNo(AirUnshippedGoodsDto airUnshippedGoodsDto, int start,int limit);
	
	/**
	 * 根据运单号获取总记录条数
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-3 下午2:51:38
	 */
	Long getCountByWaybillNo(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 根据代单号获得流水号
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-11 下午2:52:41
	 */
	List<AirWaybillSerialNoEntity> querySerialNoByWaybillNo(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 根据正单号获取拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-11 下午8:03:04
	 */
	List<AirUnshippedGoodsEntity> queryUnshippedGoodsByAirWaybillNo(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 根据代单号获取拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-11 下午8:03:52
	 */
	List<AirUnshippedGoodsEntity> queryUnshippedGoodsByWaybillNo(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 录入拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-12 下午4:42:39
	 */
	int addAirUnshippedGoods(AirUnshippedGoodsEntity airUnshippedGoodsEntity);
	
	/**
	 * 录入拉货流水号
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-18 下午3:16:42
	 */
	int addAirUnshippedSerialNo(List<AirUnshippedSerialNoEntity> airUnshippedSerialNoList);
	
	/**
	 * 修改拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-13 下午5:21:58
	 */
	int updateAirUnshippedGoods(AirUnshippedGoodsEntity airUnshippedGoodsEntity);
	
	/**
	 * 获取正单下所有未配载的运单号和流水号
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-14 下午4:08:33
	 */
	List<AirUnshippedSerialNoEntity> querySerialNoByAirWaybillNo (AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 删除拉货记录
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-17 下午3:09:25
	 */
	int deleteAirUnshippedGoods(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 根据ID查找拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-19 9:57:19
	 */
	List<AirUnshippedGoodsEntity> queryAirUnshippedGoodsById(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 根据ID查找拉货明细
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-19 9:57:19
	 */
	List<AirUnshippedSerialNoEntity> querySerialNoByUnshippedGoodsId(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 根据运单号查找开单部门编码
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-27 上午11:50:47
	 */
	String queryCreateOrgCode(String waybillNo);
	
	/**
	 * 获取正单号下面所有的运单号
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-27 下午2:09:35
	 */
	List<AirUnshippedSerialNoEntity> queryWaybillNoByAirWaybillNo(String waybillNo);

	/**
	 * 根据单号查询制单部门
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-18 上午9:31:02
	 */
	String queryCreateOrgCodeByBillNo(AirUnshippedGoodsDto airUnshippedGoodsDto);

	/**
	 * 校验是否已签收
	 * @author foss-liuxue(for IBM)
	 * @date 2013-2-1 下午5:53:29
	 */
	Long validateIsSign(AirUnshippedGoodsDto airUnshippedGoodsDto);

	/**
	 * 增加拉货通知
	 * @author foss-liuxue(for IBM)
	 * @date 2013-3-7 下午7:42:46
	 */
	int addNotice(AirUnshippedNoticeEntity airUnshippedNoticeEntity);

	/**
	 * 修改拉货通知
	 * @author foss-liuxue(for IBM)
	 * @date 2013-3-7 下午7:42:46
	 */
	int updateNotice(AirUnshippedNoticeEntity airUnshippedNoticeEntity);

	/**
	 * 根据unshippedGoodsId查询通知日志
	 * @author foss-liuxue(for IBM)
	 * @date 2013-3-7 下午8:19:10
	 */
	String queryNoticId(String unshippedGoodsId);

	/**
	 * 查询是否拉货过
	 * @author foss-liuxue(for IBM)
	 * @date 2013-3-11 下午7:37:16
	 */
	Long queryIsEmptyUnshippedGoods(String billNo);

	/**
	 * 拉货成功后删除运单流水号中的数据，恢复为可合票状态 
	 * @author foss-liuxue(for IBM)
	 * @date 2013-3-13 下午2:31:46
	 */
	int deleteAirWaybillSerialNo(
			AirUnshippedSerialNoEntity airUnshippedNoticeEntity);

	/**
	 * 根据拉货ID删除所有拉货明细 
	 * @author foss-liuxue(for IBM)
	 * @date 2013-3-22 下午5:23:11
	 */
	int deleteAirUnshippedSerialNo(String unshippedGoodsId);

	/**
	 * 校验代单是否做过正单
	 * @author foss-liuxue(for IBM)
	 * @date 2013-5-16 下午7:10:16
	 */
	Long waybillIsValidate(String waybillNo);
}