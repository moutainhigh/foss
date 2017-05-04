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
 *  PROJECT NAME  : tfr-departure
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/service/impl/UpdateTaskStatusService.java
 *  
 *  FILE NAME          :UpdateTaskStatusService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.server.service.IInStockForJobService;
import com.deppon.foss.module.transfer.departure.api.shared.dto.WayBillRefershDTO;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 车辆到达放行之后更新一些状态的公用方法.
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:58:01
 */
public class InStockForJobService implements IInStockForJobService {
	
	/** *********** 日志 **************. */
	private static final Logger LOGGER = LogManager
			.getLogger(InStockForJobService.class);
	@Transactional 
	@Override
	public void refreshInStorage(List<WayBillRefershDTO> waybilllist,LoaderParticipationEntity loaderParticipationEntity) {
		// 入库
		for (WayBillRefershDTO wayBillRefershDTO : waybilllist) {
			try {
				if (wayBillRefershDTO.getBeScanInstock() == null
						|| FossConstants.NO.equals(wayBillRefershDTO
								.getBeScanInstock())) {
					// 已扫描过的不能入库
//					LOGGER.info("开始进行入库操作");
					InOutStockEntity inOutStockEntity = new InOutStockEntity();
					inOutStockEntity.setWaybillNO(wayBillRefershDTO
							.getWaybillNo());
					inOutStockEntity.setSerialNO(wayBillRefershDTO
							.getSerialNo());
					inOutStockEntity.setOrgCode(wayBillRefershDTO
							.getOrigOrgCode());
					inOutStockEntity.setOperatorCode(loaderParticipationEntity
							.getLoaderCode());
					inOutStockEntity.setOperatorName(loaderParticipationEntity
							.getLoaderName());
					inOutStockEntity.setInOutStockBillNO(wayBillRefershDTO
							.getBillNo());
					if ("NORMAL".equals(wayBillRefershDTO.getGoodsState())) {
						// 正常入库
						inOutStockEntity
								.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
					} else {
						// 异常入库
						inOutStockEntity
								.setInOutStockType(StockConstants.UNLOAD_GOODS_MORE_IN_STOCK_TYPE);
					}
					stockService.inStockUnload(inOutStockEntity);
//					LOGGER.info("入库成功");
				}
			}catch(TfrBusinessException e) 
			{
				LOGGER.error("非入库失败,入库失败、出错的运单号："+wayBillRefershDTO.getWaybillNo(), e);
			}catch (StockException e) {
				LOGGER.error("入库失败、出错的运单号："+wayBillRefershDTO.getWaybillNo(), e);
				throw new TfrBusinessException("入库失败、出错的运单号："+wayBillRefershDTO.getWaybillNo(),e);
			}
		}
	}
	
	private IStockService stockService;

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

}