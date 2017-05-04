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
 *  FILE PATH          :/AirTransPickupBillDao.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.SerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 制作中转提货清单dao实现
 * @author 099197-foss-zhoudejun
 * @date 2012-11-12 下午1:53:44
 */
public class AirTransPickupBillDao extends iBatis3DaoImpl implements IAirTransPickupBillDao {
	
	private static final String NAMESPACE = "foss.airfreight.";
	
	/**
	 * （ 合大票清单查询）
	 * 根据航空公司,正单号查询航空正单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 下午1:39:51
	 * @param (airLineName airWaybillNo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillDetailEntity> queryAirWaybillDetailList(AirTransPickupBillDto airTransPickupBillDto) {
		List<String> waybillNoList =null;
		waybillNoList = this.queryWaybillNoList(airTransPickupBillDto.getAirWaybillNo());
		boolean f = false;
		if(!CollectionUtils.isEmpty(waybillNoList)){
			for(String WaybillNo : waybillNoList){
				if(WaybillNo.substring(0, 1).equals("B")){
					f = true;
					break;
				}
			}
		}
		if(f){
			return this.getSqlSession().selectList(NAMESPACE + "queryAirPackageList",airTransPickupBillDto);
		}else{
			return this.getSqlSession().selectList(NAMESPACE + "queryAirPickupbillList",airTransPickupBillDto);
		}
	}

	private List<String> queryWaybillNoList(String airWaybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillNoList1",airWaybillNo);
		

	}

	/**
	 * （添加合大票清单明细）
	 * 根据运单号新增航空正单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 下午7:33:02
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillDetailEntity> addWaybillNoInfo(String waybillNo,String deptCode) {
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("deptCode", deptCode);
		if((waybillNo.substring(0, 1)).equals("B")){
			return this.getSqlSession().selectList(NAMESPACE + "addPackageNoInfo",dataMap);
		}else{
			return this.getSqlSession().selectList(NAMESPACE + "addWaybillNoInfo",dataMap);
		}
	}

	/**
	 * （获取正单主表信息）根据航空正单id查询收货人姓名、电话、地址
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-13 下午4:02:51
	 */
	@Override
	public AirWaybillEntity queryAirWaybillReceiverInfo(String airWaybillId) {
		return (AirWaybillEntity)this.getSqlSession().selectOne(NAMESPACE + "queryReceiverNameInfo",airWaybillId);
	}

	/**
	 * 新增合大票清单（将航空正单、明细保存到合大票清单表中）
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-14 下午3:30:35
	 */
	@Override
	public int addAirPickupBill(AirPickupbillEntity airPickupbillEntity) {
		return this.getSqlSession().insert(NAMESPACE + "addAirPickupBill",airPickupbillEntity);
	}

	/**
	 * （将航空正单明细保存到合大票清单明细表中）
	 * 新增合大票清单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-14 下午3:30:40
	 */
	@Override
	public int addAirWaybillDetail(List<AirPickupbillDetailEntity> list) {
		
		//批量插入改为循环插入
		for(AirPickupbillDetailEntity airPickupbillDetailEntity : list){
			getSqlSession().insert(NAMESPACE + "addAirPickupbillDetailEntity",airPickupbillDetailEntity);
		}
		return FossConstants.SUCCESS;	
		/*return this.getSqlSession().insert(NAMESPACE + "addAirWaybillDetail",list);*/
	}
	
	/**
	 * 根据航空公司、运单号查询合大票清单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-15 上午10:22:21
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirPickupbillDetailEntity> queryAirPickupbillList(
			AirTransPickupBillDto airTransPickupBillDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryMakeJointlargeList",airTransPickupBillDto);
	}
	
	/**
	 * 新增中转提货清单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-15 上午10:22:21
	 */
	@Override
	public AirPickupbillDetailEntity waybillNoAddToTransferDetail(
			AirTransPickupBillDto airTransPickupBillDto) {
		return (AirPickupbillDetailEntity)this.getSqlSession().selectOne(NAMESPACE + "waybillNoAddToTransferDetail",airTransPickupBillDto);
	}

	/**
	 * 根据正单号获取合大票清单信息
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-16 上午9:02:19
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#queryAirPickupbillEntity(java.lang.String)
	 */
	@Override
	public AirPickupbillEntity queryAirPickupbillEntity(String airWaybillNo) {
		return (AirPickupbillEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAirPickupbillEntity",airWaybillNo);
	}
	
	/**
	 * 根据dto获取合大票清单信息
	 * @author 099197-foss-liuzhaowei
	 * @date 2013-04-22 上午9:02:19
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#queryAirPickupbillEntityByDto(airTransPickupBillDto)
	 */
	@Override
	public AirPickupbillEntity queryAirPickupbillEntityByDto(AirTransPickupBillDto airTransPickupBillDto) {
		return (AirPickupbillEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAirPickupbillEntityByDto",airTransPickupBillDto);
	}
	
	/**
	 * 根据正单ID获取合大票清单信息
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-16 上午9:02:19
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#queryAirPickupbillEntity(java.lang.String)
	 */
	@Override
	public AirPickupbillEntity queryAirPickupbillEntityById(String id) {
		return (AirPickupbillEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAirPickupbillEntityById",id);
	}

	/**
	 * 新增中转提货清单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-15 下午5:36:01
	 */
	@Override
	public int addAirPickupbillDetailList(
			List<AirPickupbillDetailEntity> list) {
		for(AirPickupbillDetailEntity airPickupbillDetailEntity : list){
			getSqlSession().insert(NAMESPACE + "addAirTransPickupDetailEntity",airPickupbillDetailEntity);
		}
		return FossConstants.SUCCESS;
		//return this.getSqlSession().insert(NAMESPACE + "addAirTransPickupDetail",list);
	}

	/**
	 * 新增中转提货清单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-19 下午5:04:38
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#addAirTransPickupBill(com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity)
	 */
	@Override
	public int addAirTransPickupBill(AirTransPickupbillEntity airTransPickupbillEntity) {
		return this.getSqlSession().insert(NAMESPACE + "addAirTransPickupBill",airTransPickupbillEntity);
	}

	/**
	 * 根据正单号查询中转提过货清单
	 * 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-20 上午9:40:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AirTransPickupbillEntity queryAirTransPickupbillEntity(
			String airWaybillNo){
		List<AirTransPickupbillEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryAirTransPickupbillEntity", airWaybillNo);
		if (list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据中转单号查询中转提过货清单
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-20 上午9:40:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AirTransPickupbillEntity queryAirTransPickupbillEntityByNo(
			String transferNo) {		
		List<AirTransPickupbillEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryAirTransPickupbillEntityByNo", transferNo);
		if (list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据正单号查询中转提过货清单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-20 上午9:40:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirTransPickupDetailEntity> queryAirTransPickupDetailList(
			String airWaybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirTransPickupDetailList",airWaybillNo);
	}
	
	/**
	 * 根据中转单号查询中转提过货清单明细
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-20 上午9:40:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirTransPickupDetailEntity> queryAirTransPickupDetailListByNo(
			String transferNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirTransPickupDetailListByNo",transferNo);
	}

	/**
	 * 根据 制单时间,航空公司,正单号,目的站,到达网点,空运总调
	 * 查询合票清单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-1 下午2:52:01
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#queryMakePickGoods(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirPickupbillEntity> queryMakePickGoods(
			AirTransPickupBillDto airTransPickupBillDto,int limit ,int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryMakePickGoods",airTransPickupBillDto,rowBounds);
	}
	
	/**
	 * 获取合票信息总记录数
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-1 下午3:38:29
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#getCount(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto)
	 */
	@Override
	public Long getMakePickGoodsCount(AirTransPickupBillDto airTransPickupBillDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "getMakePickGoodsCount",airTransPickupBillDto);
	}

	/**
	 * 根据正单号查询合大票清单中是否存在记录
	 * @param airWaybillNo
	 * @return 返回(true 存在 false 不存在)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-15 下午3:42:27
	 */
	@Override
	public boolean checkExistAirTransPickupBill(AirTransPickupBillDto airTransPickupBillDto) {
		 Integer count =(Integer) this.getSqlSession().selectOne(NAMESPACE + "checkExistAirTransPickupBill",airTransPickupBillDto);
		 return count > 0 ? true : false;
	}

	/**
	 * 根据正单号查询合大票明细
	 * @param airWaybillNo
	 * @return List<AirPickupbillDetailEntity> queryAirPickupbillDetailList 返回(合票明细list)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-15 下午4:48:01
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirPickupbillDetailEntity> queryAirPickupbillDetailList(
			String airWaybillNo) {
		//判断传包时的取值方式
		List<AirPickupbillDetailEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryAirPickupbillDetailList1",airWaybillNo);
		boolean f=false;
		for(AirPickupbillDetailEntity airPickupbillDetail : list){
			if((airPickupbillDetail.getWaybillNo().substring(0, 1)).equals("B")){
				f=true;
				break;
			}
		}
		if(f){
			return list;
		}else{
			return this.getSqlSession().selectList(NAMESPACE + "queryAirPickupbillDetailList",airWaybillNo);
		}
	}
	
	/**
	 * 根据id查询待导出的合大票明细list
	 * @param  idsList
	 * @return List<AirPickupbillDetailEntity> 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 下午1:14:03
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirPickupbillDetailEntity> queryAirPickupbillDetailList(
			List<String> idsList) {
		return this.getSqlSession().selectList(NAMESPACE +"queryPickupbillDetailWithExportEdi",idsList);
	}

	/**
	 * 根据id查询待导出的合中转提货清单明细list
	 * @param idsList
	 * @return List<AirTransPickupDetailEntity>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-20 上午9:40:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirTransPickupDetailEntity> queryAirTransPickupDetailList(
			List<String> idsList) {
		return this.getSqlSession().selectList(NAMESPACE +"queryTransPickupDetailWithExportEdi",idsList);
	}

	/**
	 * 批量更新合大票明细中的到付费 代收货款
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-10 下午6:02:18
	 */
	public void updateAirupbillNoList(List<String> waybillNoList){
		this.getSqlSession().update(NAMESPACE +"updateAirupbillNoList",waybillNoList);
	}
	
	/**
	 * 根据正单号list批量查询合大票清单信息 
	 * @author 099197-foss-zhoudejun
	 * @param List<String> airWaybillNoList 正单号list
	 * @date 2013-3-11 上午8:11:10
	 */
	public AirPickupbillEntity batchSearchPickupbill(String airWaybillNo){
		return (AirPickupbillEntity)getSqlSession().selectOne(NAMESPACE + "batchSearchPickupbill",airWaybillNo);
	}

	/**
	 * 
	* @description 校验运单号是否存在
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#checkWayBillIsExtis(java.lang.String)
	* @author 269701-foss-lln
	* @update 2016年4月20日 上午11:49:26
	* @version V1.0
	 */
	@Override
	public boolean checkWayBillIsExits(String waybillNo) {
		Integer count =(Integer) this.getSqlSession().selectOne(NAMESPACE + "checkWayBillIsExits",waybillNo);
		 return count > 0 ? true : false;
	}

	/**
	 * 
	* @description 根据运单号查询已制作合大票流水信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#findRightSerial(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto)
	* @author 269701-foss-lln
	* @update 2016年4月24日 下午4:23:39
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialEntity> findRightSerialForRight(AirTransPickupBillDto airTransPickupBillDto) {
		Map<String,String> param=new HashMap<String, String>();
		//运单号
		param.put("waybillNo", airTransPickupBillDto.getWaybillNo());
		//正单单号
		param.put("airWaybillNo", airTransPickupBillDto.getAirWaybillNo());
		return this.getSqlSession().selectList(NAMESPACE + "findRightSerial_Right",param);
	}
	/**
	 * 
	* @description 根据运单号查询已制作合大票流水信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#findRightSerialForLeft(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto)
	* @author 269701-foss-lln
	* @update 2016年4月24日 下午4:23:39
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialEntity> findRightSerialForLeft(AirTransPickupBillDto airTransPickupBillDto) {
		Map<String,String> param=new HashMap<String, String>();
		//运单号
		param.put("waybillNo", airTransPickupBillDto.getWaybillNo());
		//正单单号
		param.put("airWaybillNo", airTransPickupBillDto.getAirWaybillNo());
		return this.getSqlSession().selectList(NAMESPACE + "findRightSerial_Left",param);
	}
	/**
	 * 
	* @description 根据运单号查询未制作合大票流水信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#findLeftSerial(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto)
	* @author 269701-foss-lln
	* @update 2016年4月24日 下午4:23:39
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialEntity> findLeftSerial(AirTransPickupBillDto airTransPickupBillDto) {
		Map<String,String> param=new HashMap<String, String>();
		//运单号
		param.put("waybillNo", airTransPickupBillDto.getWaybillNo());
		//正单单号
		param.put("airWaybillNo", airTransPickupBillDto.getAirWaybillNo());
		return this.getSqlSession().selectList(NAMESPACE + "findLeftSerial",param);
	}
	
	/**
	 * 
	* @description 合大票修改时 根据运单号查询未制作合大票流水信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#findLeftSerialForModify(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto)
	* @author 269701-foss-lln
	* @update 2016年4月24日 下午4:23:39
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialEntity> findLeftSerialForModify(AirTransPickupBillDto airTransPickupBillDto) {
		Map<String,String> param=new HashMap<String, String>();
		//运单号
		param.put("waybillNo", airTransPickupBillDto.getWaybillNo());
		//正单单号
		param.put("airWaybillNo", airTransPickupBillDto.getAirWaybillNo());
		return this.getSqlSession().selectList(NAMESPACE + "findLeftSerialForModify",param);
	}
	
	/**
	 * 
	* @description 保存合大票流水信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#saveSerialNo(com.deppon.foss.module.transfer.airfreight.api.shared.domain.SerialEntity)
	* @author 14022-foss-songjie
	* @update 2016年4月24日 下午7:54:03
	* @version V1.0
	 */
	@Transactional
	@Override
	public int saveSerialNo(SerialEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "saveSerialNo", entity);
	}

	/**
	* @description 校验流水号是否已经制作合大票
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#querySerialIsExist(java.lang.String, java.lang.String)
	* @author 269701-foss-lln
	* @update 2016年4月26日 上午10:04:14
	* @version V1.0
	* @return true 存在 false 不存在
	 */
	@Override
	public boolean querySerialIsExist(String waybillNo, String serialNo) {
		Map<String,String> param=new HashMap<String, String>();
		//运单号
		param.put("waybillNo", waybillNo);
		//流水号
		param.put("serialNo", serialNo);
		Integer count =(Integer) this.getSqlSession().selectOne(NAMESPACE + "querySerialIsExist",param);
		 return count > 0 ? true : false;
	}

	/**
	 * 
	* @description 批量删除合大票清单流水信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#deleteAirPickupbillSerialist(java.util.List)
	* @author 269701-foss-lln
	* @update 2016年5月28日 上午11:27:15
	* @version V1.0
	 */
	@Override
	public int deleteAirPickupbillSerialist(List<SerialEntity> serialList) {
		return this.getSqlSession().delete(NAMESPACE + "deleteAirPickupbillSerialist",serialList);
	}

	/**
	 * 
	* @description 根据运单号 查询航空正单流水信息表 得到流水列表
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#getAirWaySerialListByWaybill(java.lang.String)
	* @author 269701-foss-lln
	* @update 2016年5月28日 下午3:00:03
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAirWaySerialListByWaybill(String waybillNo,String airWaybillNo) {
		Map<String,String> params=new HashMap<String, String>();
		params.put("waybillNo", waybillNo);
		params.put("airWaybillNo", airWaybillNo);
		return this.getSqlSession().selectList(NAMESPACE + "getAirWaySerialListByWaybill",params);
	}

	/**
	 * 
	* @description 根据运单号查询合大票清单明细 校验该运单是否制作过合大票
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao#getAirPickupbillDetailInfo(java.lang.String)
	* @author 269701-foss-lln
	* @update 2016年6月3日 下午10:21:26
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AirPickupbillDetailEntity getAirPickupbillDetailInfo(String waybillNo) {
		List<AirPickupbillDetailEntity> resultList=this.getSqlSession().selectList(NAMESPACE + "getAirPickupbillDetailInfo",waybillNo);
		if(resultList.size()>0){
			return resultList.get(0);
		}else{
			return null;
		}
			
	}

	/**
	 * 合大票新增时 根据运单号查询未制作合大票流水信息
	 * @author 352203-foss-wx
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialEntity> findLeftSerialForModifyNot(
			AirTransPickupBillDto airTransPickupBillDto) {
		// TODO Auto-generated method stub
		Map<String,String> param=new HashMap<String, String>();
		//运单号
		param.put("waybillNo", airTransPickupBillDto.getWaybillNo());
		//正单单号
		param.put("airWaybillNo", airTransPickupBillDto.getAirWaybillNo());
		return this.getSqlSession().selectList(NAMESPACE + "findLeftSerialForModify1",param);
	}

	/**
	 * 根据运单查询正单的流水
	 * @author 352203-foss-wx
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialEntity> findPickupbillSerial(String waybillNo) {
		// TODO Auto-generated method stub
		Map<String,String> param=new HashMap<String, String>();
		//运单号
		param.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(NAMESPACE + "findPickupbillSerial",param);
	}

	/**
	 * 根据运单查询已使用的流水
	 * @author 352203-foss-wx
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialEntity> findWaybillSerial(String waybillNo) {
		// TODO Auto-generated method stub
		Map<String,String> param=new HashMap<String, String>();
		//运单号
		param.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(NAMESPACE + "findWaybillSerial",param);
	}
	
	/**
	 * 
	* @description 根据合大票明细id查询合大票明细信息
	* @author 311396-foss-wwb
	* @update 2016年9月9日09:57:28
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AirPickupbillDetailEntity getAirPickupbillDetailInfoById(String id) {
		List<AirPickupbillDetailEntity> resultList=this.getSqlSession().selectList(NAMESPACE + "getAirPickupbillDetailInfoById",id);
		if(resultList.size()>0){
			return resultList.get(0);
		}else{
			return null;
		}
			
	}
}