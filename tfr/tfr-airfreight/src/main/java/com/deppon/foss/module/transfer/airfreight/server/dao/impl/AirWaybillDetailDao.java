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
 *  FILE PATH          :/AirWaybillDetailDao.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDetailDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirCargovolumeSerialnoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSerialNoDetail;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWaybillDetailDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.WaybillDetailDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.WaybillInfoForNoticeDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.WayBillAssembleDto;

/**
 * 录入航空正单dao实现类 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-25 下午6:03:17
 */
public class AirWaybillDetailDao extends iBatis3DaoImpl implements IAirWaybillDetailDao {

	private static final String NAMESPACE = "foss.airfreight.";

	/**
	 * 查询运单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-19 下午5:30:01
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillDetailEntity> queryWaybillEntity(
			AirWaybillDetailDto ticketDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybill", ticketDto);
	}
	
	/**
	 * 查询运单明细(库存中)
	 * @param ticketDto
	 * @return TfrWaybillEntity 运单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-19 下午5:30:01
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AirWaybillDetailEntity> queryStockWaybillEntity(
			AirWaybillDetailDto ticketDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryStockWaybillEntity", ticketDto);
	}

	/**
	 * 查询航空正单明细list和流水list
	 * @param waybillNo 运单号
	 * @return List<AirWaybillSerialNoEntity>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 下午5:09:46
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillSerialNoEntity> queryWaybillSerialNoList(
			List<String> waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillSerialNoList",waybillNo);
	}

	/**
	 * 保存航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-22 下午2:52:46
	 */
	@Override
	public int addAirWaybillEntity(AirWaybillEntity billEntity) {
		return this.getSqlSession().insert(NAMESPACE + "addAirWaybillEntity",billEntity);
	}

	/**
	 * 保存航空正单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-22 下午2:53:35
	 */
	@Override
	public int addAirWaybillDetailEntity(List<AirWaybillDetailEntity> airWaybillDetailList) {
		return this.getSqlSession().insert(NAMESPACE + "addAirWaybillDetailEntity",airWaybillDetailList);
	}

	/**
	 * 航空正单流水明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-22 下午2:57:57
	 */
	@Override
	public int addAirWaybillSerialNoEntity(List<AirWaybillSerialNoEntity> airSerialNoDetailSerialno) {
		return this.getSqlSession().insert(NAMESPACE + "AirWaybillSerialNoEntity",airSerialNoDetailSerialno);
	}
	
	/**
	 * 
	 * 单一运单号查询流水明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-29 下午5:02:43
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDetailDao#waybillNoQuery(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWaybillDetailDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirCargovolumeSerialnoEntity> queryWaybillNo(
			AirWaybillDetailDto ticketDto) {
		return this.getSqlSession().selectList(NAMESPACE + "waybillNoQuery",ticketDto);
	}
	
	/**
	 *  根据航空正单号查询流水明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-29 下午5:02:15
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirSerialNoDetail> queryAirSerialNoDetail(
			String  waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillSerialnoDetail",waybillNo);
	}

	/**
	 * 批量打印运单明细 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-7 上午9:52:35
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillDetailEntity> queryAirWaybillBatchPrint(String[] ids) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirWaybillBatchPrint",ids);
	}

	/**
	 *  根据航空正单号查询 航空正单流水明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-24 下午3:33:07
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillSerialNoEntity> queryAirWaybillSerialNoEntityList(
			String waybillNo ) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirCargovolumeSerialnoEntity",waybillNo);
	}

	/**
	 * 根据ID查询航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-28 上午8:29:10
	 */
	@Override
	public AirWaybillEntity queryAirWaybillEntity(String id) {
		return (AirWaybillEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAirWaybillEntity",id);
	}

	/**
	 * 修改航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-28 上午10:43:38
	 */
	@Override
	public int updateAirWaybillEntity(AirWaybillEntity waybillEntity) {
		return this.getSqlSession().update(NAMESPACE + "updateAirWaybillEntity",waybillEntity);
	}

	/**
	 * 根据航空正单id查询对应的航空正单明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-28 上午11:34:27
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillDetailEntity> queryHistoryAirWaybillDetail(
			String airWaybillId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryHistoryAirWaybillDetail" ,airWaybillId);
	}
	
	/**
	 * 根据运单号查询航空正单明细表中是否存在与之匹配的记录
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-28 下午1:35:58
	 */
	public boolean queryAirWaybillDetailEntityIsNotNull(String id){
		int existsWaybillNo = (Integer)this.getSqlSession().selectOne(NAMESPACE + "queryAirWaybillDetailEntityIsNotNull" ,id);
		if(existsWaybillNo==0){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 根据id删除航空正单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-29 上午9:50:32
	 */
	@Override
	public int deleteAirWaybillDetail(Object[] ids) {
		return this.getSqlSession().delete(NAMESPACE + "deleteAirWaybillDetail",ids);
	}

	/**
	 * 根据id删除航空正单流水明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-29 上午9:59:18
	 */
	@Override
	public int deleteAirWaybillSerialNo(Object[] ids) {
		return this.getSqlSession().delete(NAMESPACE + "deleteAirWaybillSerialNo",ids);
	}

	/**
	 * 修改航空正单明细(毛重、体积)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-29 上午11:29:03
	 */
	@Override
	public int updateAirWaybillDetailList(
			List<AirWaybillDetailEntity> airWaybillDetailList) {
		return this.getSqlSession().update(NAMESPACE + "updateAirWaybillDetailList",airWaybillDetailList);
	}

	/**
	 * 根据运单号合id查询流水号条数
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-7 上午11:22:19
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirSerialNoDetail> queryAirWaybillDetailIdList(
			String waybillNo, String airWaybillDetailId) {
		Map<String,String> dataMap = new HashMap<String, String>();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("airWaybillDetailId", airWaybillDetailId);
		return this.getSqlSession().selectList(NAMESPACE + "queryAirWaybillDetailIdList",dataMap);
	}
	
	/**
	 * 根据航空正单明细id查询所有流水号
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-10 上午9:21:30
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillSerialNoEntity> queryAirwayBillSerailNoDetailList(
			List<String> airWaybillDetailIds) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirwayBillSerailNoDetailList",airWaybillDetailIds);
	}

	/**
	 * 根据航空正单明细主键id删除流水号list 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-11 下午6:43:58
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDetailDao#deleteModifyAirWaybillSerialNo(java.util.List, java.lang.String)
	 */
	@Override
	public int deleteModifyAirWaybillSerialNo(List<String> serialNoList,
			String airWaybillDetailId) {
		Map<String,Object> dataMap = new HashMap<String, Object>();
		dataMap.put("serialNoList", serialNoList);
		dataMap.put("airWaybillDetailId", airWaybillDetailId);
		return this.getSqlSession().delete(NAMESPACE + "deleteModifyAirWaybillSerialNo" ,dataMap);
	}

	/**
	 * 根据航空正单明细id、运单号查询流水明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-12 上午8:35:03
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AirWaybillSerialNoEntity> queryAirWaybillSerialNoList(String airWaybillDetailId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirWaybillSerialNoList",airWaybillDetailId);
	}

	/**
	 * 根据正单号查询运单明细list
	 * @param  airWaybillNo 正单号
	 * @return List<String> 运单号list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-24 上午9:40:34
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> queryWaybillNoList(String airWaybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillNoList",airWaybillNo);
	}

	/**
	 * 根据正单号查询待打印的航空正单
	 * @param airWaybillNo 正单号
	 * @return 航空正单实体 
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-7 下午5:25:50
	 */
	@Override
	public AirWaybillEntity queryWidthPrintAirWaybill(String airWaybillNo) {
		return (AirWaybillEntity)this.getSqlSession().selectOne(NAMESPACE + "queryWidthPrintAirWaybill",airWaybillNo);
	}

	/**
	 * 根据运单号查询运单走货轨迹
	 * @param waybillNo 运单号
	 * @return WayBillAssembleDto
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-10 下午1:59:42
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<WayBillAssembleDto> queryWaybillPath(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillPath",waybillNo);
	}
	
	/**
	 *  根据库存状态查询流水
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-8 下午3:24:33
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AirSerialNoDetail> queryStockAirSerialNoDetail(AirWaybillDetailDto dto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryStockAirSerialNoDetail",dto);
	}

	/**
	 * 处理运单流水件数比对
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-10 上午10:28:13
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AirSerialNoDetail> waybillComparison(AirWaybillDetailDto searchConditionDto) {
		 return this.getSqlSession().selectList(NAMESPACE + "queryWaybillTotalNumber",searchConditionDto);
	}

	/**
	 * 查询需要制作唐翼的运单集合
	 * @param airWaybillNo 正单号
	 * @return 返回运单明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-15 下午2:16:41
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AirWaybillDetailEntity> queryRequireMakeTangYiTargetWaybillList(
			String airWaybillNo) {
		return getSqlSession().selectList(NAMESPACE + "queryRequireMakeTangYiTargetWaybillList",airWaybillNo);
	}
	
	/**
	 * 根据航空正单号查询所属运单的出发部门
	 * @param airWaybillNo 正单号
	 * @author liuzhaowei
	 * @date 2013-7-10 下午2:07:04
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<WaybillInfoForNoticeDto> queryWaybillInfoByAirwaybillNo(String airWaybillNo){
		return getSqlSession().selectList(NAMESPACE + "queryWaybillInfoByAirwaybillNo",airWaybillNo);
	}
	
	/*
	 * 根据航空正单id和运单号删除某个航正单中的运单明细
	 * @ airWaybillId 正单ID
	 * @ airWaybillNo 运单号
	 * @ author wqh
	 * @ date    2013-07-29 上午 08:44
	 * */
	public int deleteByAirWaybillDetail(String airWaybillId,List waybillNoList){
		Map map=new HashMap();
		map.put("airWaybillId", airWaybillId);
		map.put("waybillNoList", waybillNoList);
		return getSqlSession().delete(NAMESPACE + "deleteByAirWaybillDetail", map);
	}
	
	/*
	 * 根据运单号查询运单详细信息
	 * @ waybillNo 运单号
	 * @ author wqh
	 * @ date    2013-09-11
	 * */
	@SuppressWarnings("unchecked")
	public WaybillDetailDto queryWaybillDetailByWaybillNo(String waybillNo){
		
		List<WaybillDetailDto> waybillDetailDtoList=new ArrayList<WaybillDetailDto>();
		
		waybillDetailDtoList=getSqlSession().selectList(NAMESPACE+"queryWaybillDetailByWaybillNo",waybillNo);
		if(!CollectionUtils.isEmpty(waybillDetailDtoList)){
			return waybillDetailDtoList.get(0);
		}
		return null;
		
	}

	/*
	 * 根据正单id查询运单明细
	 * @ airWaybillId id
	 * @ author wqh
	 * @ date    2013-10-27
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryWaybillNosByAirWaybillId(String airWaybillId) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(NAMESPACE + "queryWaybillNosByAirWaybillId",airWaybillId);
	}

    /**
     * 根据条件查询快递用运单明细
     * @author  lianghaisheng
     * @param ticketDto
     * @return
     */
    @Override
    public List<AirWaybillDetailEntity> queryPackageWaybillEntity(AirWaybillDetailDto ticketDto) {
        return getSqlSession().selectList(this.NAMESPACE+"queryPackageWaybill",ticketDto);
    }
    
    /**
   	 * 根据正单号查询运单明细list
   	 * @param  WaybillNo 运单号
   	 * @return List<String> 运单号list
   	 * @author 268220-chenmin
   	 * @date 2016-09-08 上午8:10:10
   	 */
	@SuppressWarnings("unchecked")
	public List<AirWaybillDetailEntity> queryWaybillDetailEntityList(
			String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillDetailEntityList",waybillNo);
	}
	
}