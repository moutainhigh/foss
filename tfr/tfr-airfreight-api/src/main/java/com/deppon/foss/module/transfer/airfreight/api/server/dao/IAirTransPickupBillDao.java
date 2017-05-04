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
 *  FILE PATH          :/IAirTransPickupBillDao.java
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

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.SerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
/**
 * 制作中转提货清单dao接口 
 * @author 099197-foss-zhoudejun
 * @date 2012-11-12 下午1:53:03
 */
public interface IAirTransPickupBillDao {
	
	/**
	 * 根据航空公司,正单号查询航空正单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 下午1:39:51
	 * @param (airLineName airWaybillNo)
	 */
	public List<AirWaybillDetailEntity> queryAirWaybillDetailList(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 根据运单号新增航空正单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 下午7:33:02
	 */
	public List<AirWaybillDetailEntity> addWaybillNoInfo(String waybillNo,String deptCode);
	
	/**
	 * 根据航空正单id查询收货人姓名、电话、地址
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-13 下午4:02:51
	 */
	public AirWaybillEntity queryAirWaybillReceiverInfo(String airWaybillId);
	
	/**
	 * 新增合大票清单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-14 下午3:30:35
	 */
	public int addAirPickupBill(AirPickupbillEntity airPickupbillEntity);
	
	/**
	 * 新增合大票清单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-14 下午3:30:40
	 */
	public int addAirWaybillDetail(List<AirPickupbillDetailEntity> list);

	/**
	 * 根据航空公司、运单号查询合大票清单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-15 上午10:22:21
	 */
	public List<AirPickupbillDetailEntity> queryAirPickupbillList(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 根据运单号添加一条合票清单明细信息
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-15 下午5:36:01
	 */
	AirPickupbillDetailEntity waybillNoAddToTransferDetail(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 新增中转提货清单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-15 下午5:36:01
	 */
	int addAirPickupbillDetailList(List<AirPickupbillDetailEntity> list);
	
	/**
	 * 根据正单号获取合大票清单 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-16 上午9:01:38
	 */
	AirPickupbillEntity queryAirPickupbillEntity(String airWaybillNo);
	
	/**
	 * 根据正单id号获取合大票清单 
	 * @author 099197-foss-liuzhaowei
	 * @date 2013-4-2 上午9:01:38
	 */
	AirPickupbillEntity queryAirPickupbillEntityById(String id);
	
	/**
	 * 根据dto获取合大票清单 
	 * @author 099197-foss-liuzhaowei
	 * @date 2013-04-22 上午9:01:38
	 */
	AirPickupbillEntity queryAirPickupbillEntityByDto(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 新增中转提货清单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-19 下午4:20:42
	 */
	int addAirTransPickupBill (AirTransPickupbillEntity airTransPickupbillEntity);
	
	/**
	 * 根据正单号查询中转提过货清单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-20 上午9:40:31
	 */
	AirTransPickupbillEntity queryAirTransPickupbillEntity(String airWaybillNo);
	
	/**
	 * 根据中转单号查询中转提过货清单
	 * @author 046130-foss-xuduowei
	 * @date 2013-04-03 上午9:40:31
	 */
	AirTransPickupbillEntity queryAirTransPickupbillEntityByNo(String transferNo);
	
	/**
	 * 根据正单号查询中转提过货清单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-20 上午9:40:31
	 */
	List<AirTransPickupDetailEntity> queryAirTransPickupDetailList(String airWaybillNo);
	/**
	 * 根据中转单号查询中转提过货清单明细
	 * @author 046130-foss-xuduowei
	 * @date 2013-04-03 上午9:40:31
	 */
	List<AirTransPickupDetailEntity> queryAirTransPickupDetailListByNo(String transferNo);
	
	/**
	 * 根据 制单时间,航空公司,正单号,目的站,到达网点,空运总调
	 * 查询合票清单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-1 下午2:49:49
	 */
	List<AirPickupbillEntity> queryMakePickGoods(AirTransPickupBillDto airTransPickupBillDto,int limit ,int start);
	
	/**
	 * 获取合票信息总记录数
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-1 下午3:37:12
	 */
	Long getMakePickGoodsCount(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 根据正单号查询合大票清单中是否存在记录
	 * @param airWaybillNo
	 * @return 返回(true 存在 false 不存在)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-15 下午3:42:27
	 */
	boolean checkExistAirTransPickupBill(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 根据正单号查询合大票明细
	 * @param airWaybillNo
	 * @return List<AirPickupbillDetailEntity> queryAirPickupbillDetailList 返回(合票明细list)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-15 下午4:48:01
	 */
	List<AirPickupbillDetailEntity> queryAirPickupbillDetailList (String airWaybillNo); 
	
	/**
	 * 根据id查询待导出的合大票明细list
	 * @param  idsList
	 * @return List<AirPickupbillDetailEntity> 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 下午1:14:03
	 */
	List<AirPickupbillDetailEntity> queryAirPickupbillDetailList(List<String> idsList);
	
	/**
	 * 根据id查询待导出的合中转提货清单明细list
	 * @param idsList
	 * @return List<AirTransPickupDetailEntity>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-20 上午9:40:31
	 */
	List<AirTransPickupDetailEntity> queryAirTransPickupDetailList(List<String> idsList);
	
	/**
	 * 批量更新合大票明细中的到付费 代收货款
	 * @param List<String> waybillNoList 运单号list
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-10 下午6:02:18
	 */
	void updateAirupbillNoList(List<String> waybillNoList);
	
	/**
	 * 根据正单号list批量查询合大票清单信息 
	 * @author 099197-foss-zhoudejun
	 * @param List<String> airWaybillNoList 正单号list
	 * @date 2013-3-11 上午8:11:10
	 */
	AirPickupbillEntity batchSearchPickupbill(String airWaybillNo);
	/**
	 * 
	* @description 校验运单号是否存在
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#checkWayBillIsExtis(java.lang.String)
	* @author 269701-foss-lln
	* @update 2016年4月20日 上午11:49:26
	* @version V1.0
	 */
	boolean checkWayBillIsExits(String waybillNo);
	/**
	 * 
	* @description 根据流水号查询已制作流水信息
	* @param airTransPickupBillDto
	* @return
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年4月24日 下午4:11:46
	 */
	public List<SerialEntity> findRightSerialForRight(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 
	* @description 根据流水号查询已制作流水信息
	* @param airTransPickupBillDto
	* @return
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年4月24日 下午4:11:46
	 */
	public List<SerialEntity> findRightSerialForLeft(AirTransPickupBillDto airTransPickupBillDto);
	/**
	 * 
	* @description 合大票新增 根据流水号 查询未制作流水信息
	* @param airTransPickupBillDto
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年4月24日 下午4:13:01
	 */
	public List<SerialEntity> findLeftSerial(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 
	* @description 合大票修改 根据流水号 查询未制作流水信息
	* @param airTransPickupBillDto
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年4月24日 下午4:13:01
	 */
	public List<SerialEntity> findLeftSerialForModify(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 *根据流水号查询未制作合大票的流水信息
	 * @param airTransPickupBillDto
	 * @author 352204-foss-wx
	 * @return
	 */
	public List<SerialEntity> findLeftSerialForModifyNot(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	* @description 保存制作合大票流水信息
	* @param entity
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年4月24日 下午7:51:21
	 */
	 int saveSerialNo(SerialEntity entity);
	 	/**
		* @description 校验流水号是否已经存在合大票流水表
		* @param String waybillNo, String serialNo
		* @version 1.0
		* @author 269701-foss-lln
		* @update 2016年4月24日 下午7:51:21
		 */
	 boolean querySerialIsExist(String waybillNo, String serialNo);
	 
		/**
		* @description 批量删除合大票清单流水信息
		* @param entity
		* @version 1.0
		* @author 269701-foss-lln
		* @update 2016年4月24日 下午7:51:21
		 */
		int deleteAirPickupbillSerialist(List<SerialEntity> serialList);

		/**
		 * 
		* @description 根据运单号 查询航空正单流水信息表 得到流水列表
		* @param waybillNo
		* @return
		* @version 1.0
		* @author 269701-foss-lln
		* @update 2016年5月28日 下午2:59:41
		 */
	 List<String> getAirWaySerialListByWaybill(String waybillNo,String airWaybillNo);
	 /***
	  * 
	 * @description 根据运单号查询合大票清单明细
	 * @param waybillNo
	 * @return
	 * @version 1.0
	 * @author 269701-foss-lln
	 * @update 2016年6月3日 下午10:20:29
	  */
	 AirPickupbillDetailEntity getAirPickupbillDetailInfo(String waybillNo);
	 
	 /**
	  * 根据运单查询正单流水
	  * @param waybillNo
	  * @author 352204-foss-wx
	  * @return
	  */
	 List<SerialEntity> findPickupbillSerial(String waybillNo);
	 
	 /**
	  * 根据运单查询已使用的流水
	  * @param waybillNo
	  * @author 352204-foss-wx
	  * @return
	  */
	 List<SerialEntity> findWaybillSerial(String waybillNo);	 
	 /**
	  * 根据合大票明细id查询合大票明细
	  */
	 AirPickupbillDetailEntity getAirPickupbillDetailInfoById(String id);	 
	 
}