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
 *  FILE PATH          :/IAirTransPickupBillService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTranDataCollectionEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.SerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
/**
 * 制作中转提货清单service接口
 * @author 099197-foss-zhoudejun
 * @date 2012-11-12 上午9:34:23
 */
public interface IAirTransPickupBillService extends IService {
	
	/**
	 * 生成中转单号
	 * @param null
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 上午9:35:21
	 */
	 String generateTransfersNumber();
	
	/**
	 * 根据航空公司,正单号查询航空正单明细
	 * @param airTransPickupBillDto 
	 * @return List<AirWaybillDetailEntity> queryAirWaybillDetailList 返回(航空正单明细list)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 下午1:39:51
	 * @param (airLineName airWaybillNo)
	 */
	 List<AirWaybillDetailEntity> queryAirWaybillDetailList(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 根据运单号新增航空正单明细
	 * @param waybillNo
	 * @return AirWaybillDetailEntity 返回(航空正单明细)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 下午7:33:02
	 */
	 List<AirWaybillDetailEntity> addWaybillNoInfo(String waybillNo);
	
	/**
	 * 根据航空正单id查询收货人姓名、电话、地址
	 * @param airWaybillId
	 * @return AirWaybillEntity 航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-13 下午4:02:51
	 */
	 AirWaybillEntity queryAirWaybillReceiverInfo(String airWaybillId);
	
	/**
	 * 新增合大票清单、合大票清单明细
	 * @param List <AirPickupbillDetailEntity> list 航空正单明细list
	 * @param AirWaybillEntity airWaybillEntity 航空正单
	 * @param AirTranDataCollectionEntity airTranDataCollectionEntity
	 * @return String  返回(成功失败)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-14 下午2:28:09
	 */
	 AirPickupbillEntity addAirTransPickBILLAirPickupBill(List<AirPickupbillDetailEntity> list,AirWaybillEntity airWaybillEntity,AirTranDataCollectionEntity airTranDataCollectionEntity);
	
	/**
	 * 新增合大票清单
	 * @param airPickupbillEntity
	 * @return 返回0或1
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-14 下午3:30:35
	 */
	 int addAirPickupBill(AirPickupbillEntity airPickupbillEntity);
	
	/**
	 * 新增中转提货明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-14 下午3:30:40
	 */
	 int addAirTransferWaybillDetail(AirTranDataCollectionEntity entity,List<AirPickupbillDetailEntity> list);
	
	/**
	 * 根据航空公司、运单号查询合大票清单明细
	 * @param airTransPickupBillDto
	 * @return  List<AirPickupbillDetailEntity> queryAirPickupbillLis 返回合大票明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-15 上午10:22:21
	 */
	 List<AirPickupbillDetailEntity> queryAirPickupbillList(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 根据运单号添加一条合票清单明细信息
	 * @param waybillNo 运单号
	 * @return AirPickupbillDetailEntity 返回航空正单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-15 下午5:36:01
	 */
	AirPickupbillDetailEntity waybillNoAddToTransferDetail(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 根据正单号获取合大票清单 
	 * @param airWaybillNo 正单号
	 * @return  AirPickupbillEntity 返回(合大票清单)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-16 上午8:58:58
	 */
	AirPickupbillEntity queryAirPickupbillEntity(String airWaybillNo);
	
	/**
	 * 根据dto获取合大票清单 
	 * @param airTransPickupBillDto 
	 * @return  AirPickupbillEntity 返回(合大票清单)
	 * @author 099197-foss-liuzhaowei
	 * @date 2013-04-22 上午8:58:58
	 */
	AirPickupbillEntity queryAirPickupbillEntityByDto(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 上传合票信息上传给EDI 
	 * @param airWaybillNo 正单号
	 * @return InputStream 返回(明细)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-19 下午2:36:34
	 */
	InputStream uploadPickupCallEdi(List<String> idList , String airWaybillNo ,String callIsNotEdiFlag);
	
	/**
	 * 上传中转提货清单给EDI 
	 * @param airWaybillNo 正单号
	 * @return InputStream 返回(明细)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-19 下午2:36:34
	 */
	InputStream uploadTranPickupCallEdi(List<String> idList , String airWaybillNo ,String callIsNotEdiFlag);
	
	/**
	 * 根据正单号查询中转提过货清单
	 * @param airWaybillNo 正单号
	 * @return AirTransPickupbillEntity 返回(合大票清单)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-20 上午9:40:31
	 */
	AirTransPickupbillEntity queryAirTransPickupbillEntity(String airWaybillNo);
	
	/**
	 * 根据正单号查询中转提过货清单明细
	 * @param airWaybillNo 正单号
	 * @return List<AirTransPickupDetailEntity> queryAirTransPickupDetailList 返回(合大票明细list)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-20 上午9:40:31
	 */
	List<AirTransPickupDetailEntity> queryAirTransPickupDetailList(String airWaybillNo);
	
	/**
	 * 根据 制单时间,航空公司,正单号,目的站,到达网点,空运总调
	 * 查询合票清单明细
	 * @param airTransPickupBillDto 
	 * @param limit
	 * @param start
	 * @return AirPickupbillEntity 返回se(
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-1 下午2:49:49
	 */
	List<AirPickupbillEntity> queryMakePickGoods(AirTransPickupBillDto airTransPickupBillDto,int limit ,int start);
	
	/**
	 * 获取合票信息总记录数
	 * @param  airTransPickupBillDto
	 * @return 返回(总记录数)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-1 下午3:35:46
	 */
	Long getMakePickGoodsCount(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 根据航空正单正单号查询合大票清单、明细
	 * @param airWaybillNo
	 * @return AirTransPickupBillDto 返回(AirTransPickupBillDto)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-3 下午4:43:25
	 */
	AirTransPickupBillDto queryAirTransPickupBillOrDetail(String airWaybillNo);
	
	/**
	 * 根据正单号查询合大票清单中是否存在记录
	 * @param airWaybillNo
	 * @return 返回(true 存在 false 不存在)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-15 下午3:42:27
	 */
	boolean checkExistAirTransPickupBill (AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 根据idsList<String>查询合大票明细 
	 * @param idsList 明细id
	 * @return List<AirPickupbillDetailEntity> 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-31 下午3:27:11
	 */
	List<AirPickupbillDetailEntity> queryAirPickupbillDetailList(List<String> idsList);
	/**
	 * 
	* @description 根据运单号查询 未制作流水信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService#findLeftSerial(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto)
	* @author 269701-foss-lln
	* @update 2016年4月20日 上午10:11:37
	* @version V1.0
	 */
	List<SerialEntity> findLeftSerial(AirTransPickupBillDto airTransPickupBillDto);
	/**
	 * 合大票修改时 根据运单号查询未制作合大票流水号
	 * @param airTransPickupBillDto
	 * @return
	 */
	List<SerialEntity> findLeftSerialForModify(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 
	* @description 根据流水号 查询已制作流水信息
	* @param airTransPickupBillDto
	* @return List<SerialEntity>
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年4月24日 下午4:10:31
	 */
	List<SerialEntity> findRightSerial(AirTransPickupBillDto airTransPickupBillDto);

	/**
	 * 
	* @description 保存制作合大票流水信息
	* @param serialList
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年4月24日 下午7:43:11
	 */
	List<SerialEntity> saveSerialNo(List<SerialEntity> serialList);
	
}