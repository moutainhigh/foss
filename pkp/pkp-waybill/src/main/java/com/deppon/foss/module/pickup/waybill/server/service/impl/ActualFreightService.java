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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/ActualFreightService.java
 * 
 * FILE NAME        	: ActualFreightService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jfree.util.Log;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.StockMoveDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 运单状态服务
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-30 下午6:44:10
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-30 下午6:44:10
 * @since
 * @version
 */
public class ActualFreightService implements IActualFreightService {

	/**
	 * 运单冗余数据处理DAO
	 */
	private IActualFreightDao actualFreightDao;

	/**
	 * 
	 * 注入ActualFreight的Dao
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-25 上午9:02:40
	 */
	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}

	/**
	 * 
	 * <p>
	 * 更新货物数量
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午6:46:37
	 * @param waybillNo
	 * @param goodNum
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService#updateGoodsNum(java.lang.String,
	 *      int)
	 */
	@Override
	public ResultDto updateGoodsNum(String waybillNo, int goodNum) {
		// 标识更新状态为成功
		String code = "1";
		// 初始化消息
		String msg = "";
		// 根据运单号查询ActualFreightEntity对象
		ActualFreightEntity actualFreight = actualFreightDao.queryByWaybillNo(waybillNo);
		// 判空操作
		if (actualFreight != null) {
			actualFreight.setGoodsQty(goodNum);
			actualFreight.setArriveGoodsQty(goodNum);
			actualFreight.setArriveNotoutGoodsQty(goodNum);
			int retValue = actualFreightDao.updateByPrimaryKeySelective(actualFreight);
			if (retValue == 0) {
				code = "0";
				msg = "货物数量更新失败";
			}
		} else {
			code = "0";
			msg = "未通过运单号找到有效记录";
		}
		// 封装返回的结果信息
		ResultDto res = new ResultDto();
		res.setCode(code);
		res.setMsg(msg);
		return res;
	}

	/**
	 * 按运单号码查询记录
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-14 上午11:42:20
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService#queryByWaybillNo(java.lang.String)
	 */
	@Override
	public ActualFreightEntity queryByWaybillNo(String waybillNo) {

		return actualFreightDao.queryByWaybillNo(waybillNo);
	}

	/**
	 * 
	 * <p>
	 * 新增运单附加信息<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-29
	 * @param ActualFreightEntity
	 *            void
	 */
	@Override
	public int insertWaybillActualFreight(ActualFreightEntity actualFreightEntity) {
		if (StringUtils.isEmpty(actualFreightEntity.getId())) {
			actualFreightEntity.setId(UUIDUtils.getUUID());
		}
		int insertNumber = actualFreightDao.insertActualFreightEntity(actualFreightEntity);
		Log.warn("insert actualFreight record insertNum: " +  insertNumber );
		Log.warn("insert actualFreight record actualFreightEntity: " + ToStringBuilder.reflectionToString(actualFreightEntity)  );
		return insertNumber;
	}

	/**
	 * 更新(ActualFreightEntity actualFreightEntity)
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-5
	 * @param actualFreightEntity
	 * @return
	 * @return int
	 * @see
	 */
	@Override
	public int updateWaybillActualFreight(ActualFreightEntity actualFreightEntity) {
		return actualFreightDao.updateByPrimaryKeySelective(actualFreightEntity);
	}
	/**
     * 根据运单号更新该运单到达未出库件数
     * @author foss-meiying
     * @date 2012-12-5 下午3:17:47
     * @param actualFreightEntity
     * @return
     * @see
     */
	@Override
	public int updateArriveNotoutGoodsQtyByWaybillNo(ActualFreightDto dto) {
		return actualFreightDao.updateArriveNotoutGoodsQtyByWaybillNo(dto);
	}

	/**
     * 根据运单号更新运单运单已生成的到达联,到达未出库件数,排单件数
     * @author foss-meiying
     * @date 2012-12-5 下午3:17:47
     * @param actualFreightEntity
     * 
     * @return
     * @see
     */
	@Override
	public int updateActualFreightPartByWaybillNo(ActualFreightEntity actualFreightEntity) {
		return actualFreightDao.updateActualFreightPartByWaybillNo(actualFreightEntity);
	}

	/**
	 * 
	 * 根据单号更新结清状态
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-20 下午7:34:20
	 */
	@Override
	public void updateActualFreightSettleStatusByNo(ActualFreightEntity actualFreightEntity) {
		actualFreightDao.updateActualFreightSettleStatusByNo(actualFreightEntity);
	}
	
	/**
	 * 根据老运单号更新数据
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-25 下午2:07:11
	 */
	@Override
	public int updateByWaybillNo(ActualFreightEntity record, String oldWaybillNo){
		return actualFreightDao.updateByWaybillNo(record, oldWaybillNo);
	}
	
	/**
	 * 根据运单号判断ActualFreightEntity是否已存在
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-19 下午3:33:42
	 */
	@Override
	public boolean isExistActualFreight(String waybillNo){
		//根据运单号查询实体对象信息
		ActualFreightEntity entity = actualFreightDao.queryByWaybillNo(StringUtil.defaultIfNull(waybillNo));
		//判断对象是否为空
		if(null != entity){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 根据运单号修改ActualFreight部分信息 （并发控制）
	 * @author foss-meiying
	 * @date 2013-4-19 下午5:23:13
	 * @param dto
	 * @return
	 * @see
	 */
	public int updatePartGoodsQtyControlByWaybillNo(ActualFreightDto dto){
		return actualFreightDao.updatePartGoodsQtyControlByWaybillNo(dto);
	}
	
	/**
	 * 删除
	 * 
	 * @param waybillNo
	 * @return
	 * 
	 *  版本		 用户			时间				内容
	 *  0001	zxy				20130916		新增：BUG-54827  用于6月1号前的运单号失效，删除此数据
	 */
	@Override
	public int deleteActualFreightByWaybillNo(String waybillNo) {
		return actualFreightDao.deleteActualFreightByWaybillNo(waybillNo);
	}
	
	/**
	 * 批量激活实际城运信息--电子运单
	 * @author liyongfei--DMANA-2035
	 * @param waybillNoList
	 * @return
	 */
	@Override
	public int setActualFreightActive(List<String> waybillNoList) {
		// TODO Auto-generated method stub
		return actualFreightDao.setActualFreightActive(waybillNoList);
	}

	/**
	 * 更新城运表状态
	 * @author liyongfei
	 * @param waybillNo
	 * @return
	 */
	@Override
	public int setActualFreightStatus(String waybillNo,String status) {
		// TODO Auto-generated method stub
		return actualFreightDao.setActualFreightStatus(waybillNo, status);
	}

	@Override
	public int updateActualById(ActualFreightEntity actualFreightEntity) {
		// TODO Auto-generated method stub
		return actualFreightDao.updateActualById(actualFreightEntity);
	}
	/**
	 * 货物迁移
	 * @param StockMoveDto
	 */
	@Override
	public void stockMove(StockMoveDto sto) {
		actualFreightDao.stockMove(sto);
	}

	/**
	 * 根据运单号进行数据的更新
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-3-15 14:39:52
	 * @param actualFreightEntity
	 * @return
	 */
	@Override
	public int updateByWaybillNoSelective(ActualFreightEntity actualFreightEntity) {
		return actualFreightDao.updateByWaybillNoSelective(actualFreightEntity);
	}
	
		/**
     * 批量更新
     * @param entitys
     */
   @Override
   public void updateByActualFreightEntitys(List<ActualFreightEntity> entitys){
		if(CollectionUtils.isNotEmpty(entitys)){
			for(ActualFreightEntity entity:entitys){
				actualFreightDao.updateByWaybillNo(entity);
			}
		}
   }
   	/**
   	 * 根据运单号更新部分信息
   	 * @author 159231 meiying
   	 * 2015-5-4  下午6:26:59
   	 * @param actualFreightEntity
   	 * @return
   	 */
	@Override
	public int updatePartNotifyByWaybillNo(
			ActualFreightEntity actualFreightEntity) {
		return actualFreightDao.updatePartByWaybillNo(actualFreightEntity);
	}
	/**
	 * 修改预计送货日期
	 * @author 159231 meiying
	 * 2015-5-5  上午10:20:16
	 * @param dto
	 * @return
	 */
	public int updateDeliverDateByWaybillNos(ActualFreightDto dto){
		return actualFreightDao.updateDeliverDateByWaybillNos(dto);
	}
}