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
 *  FILE PATH          :/IAirWaybillDetailDao.java
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
 * 分单合票DAO
 * @author 099197-foss-zhoudejun
 * @date 2012-10-19 下午5:20:08
 */
public interface IAirWaybillDetailDao {

	/**
	 * 查询运单明细(未入库)
	 * @param ticketDto
	 * @return TfrWaybillEntity 运单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-19 下午5:30:01
	 */
	List<AirWaybillDetailEntity> queryWaybillEntity(AirWaybillDetailDto ticketDto);
	
	/**
	 * 查询运单明细(库存中)
	 * @param ticketDto
	 * @return TfrWaybillEntity 运单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-19 下午5:30:01
	 */
	List<AirWaybillDetailEntity> queryStockWaybillEntity(AirWaybillDetailDto ticketDto);
	
	/**
	 * 查询航空正单明细list和流水list
	 * @param waybillNo 运单号
	 * @return List<AirWaybillSerialNoEntity>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-27 下午5:09:46
	 */
	List<AirWaybillSerialNoEntity> queryWaybillSerialNoList(List<String> waybillNo);
	
	/**
	 * 保存航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-22 下午2:52:46
	 */
	int addAirWaybillEntity(AirWaybillEntity waybillEntity);
	
	/**
	 * 保存航空正单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-22 下午2:53:35
	 */
	int addAirWaybillDetailEntity(List<AirWaybillDetailEntity> airWaybillDetailList);
	
	/**
	 * 保存航空正单流水明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-22 下午2:57:57
	 */
	int addAirWaybillSerialNoEntity (List<AirWaybillSerialNoEntity> airSerialNoDetailSerialno);
	
	/**
	 * 单一运单号查询流水明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-24 下午3:33:07
	 */
	List<AirCargovolumeSerialnoEntity> queryWaybillNo(AirWaybillDetailDto ticketDto);
	
	/**
	 *  根据航空正单号查询运单流水明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-24 下午3:33:07
	 */
	List<AirSerialNoDetail> queryAirSerialNoDetail(String waybillNo);
	
	/**
	 *  根据库存状态查询流水
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-8 下午3:24:33
	 */
	List<AirSerialNoDetail> queryStockAirSerialNoDetail(AirWaybillDetailDto dto);
	
	/**
	 *  根据航空正单号查询 航空正单流水明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-24 下午3:33:07
	 */
	List<AirWaybillSerialNoEntity> queryAirWaybillSerialNoEntityList(String waybillNo);
	
	/**
	 * 批量打印运单明细 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-7 上午9:52:35
	 */
	List<AirWaybillDetailEntity> queryAirWaybillBatchPrint(String[] ids);
	
	/**
	 * 根据ID查询航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-27 下午6:51:03
	 */
	AirWaybillEntity queryAirWaybillEntity(String id);
	
	/**
	 * 修改航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-28 上午10:42:11
	 */
	int updateAirWaybillEntity(AirWaybillEntity waybillEntity);
	
	/**
	 * 根据航空正单id查询对应的航空正单明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-28 上午11:34:27
	 */
	List<AirWaybillDetailEntity> queryHistoryAirWaybillDetail (String airWaybillId);
	
	/**
	 * 根据运单号查询航空正单明细表中是否存在与之匹配的记录
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-28 下午1:35:58
	 */
	boolean queryAirWaybillDetailEntityIsNotNull(String id);
	
	/**
	 * 根据id删除运单流水明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-29 上午9:49:56
	 */
	int deleteAirWaybillDetail(Object[] ids);
	
	/**
	 * 根据id删除航空正单流水明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-29 上午9:59:18
	 */
	int deleteAirWaybillSerialNo(Object[] ids);
	
	/**
	 * 修改航空正单明细(毛重、体积)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-29 上午11:29:03
	 */
	int updateAirWaybillDetailList(List<AirWaybillDetailEntity> airWaybillDetailList);
	
	/**
	 * 根据运单号合id查询流水号条数
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-7 上午11:22:19
	 */
	List<AirSerialNoDetail> queryAirWaybillDetailIdList(String waybillNo,String airWaybillDetailId);
	
	/**
	 * 根据航空正单明细id查询所有流水号
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-10 上午9:21:30
	 */
	List<AirWaybillSerialNoEntity> queryAirwayBillSerailNoDetailList (List<String> airWaybillDetailIds);
	
	/**
	 * 根据航空正单明细主键id删除流水号list 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-11 下午6:41:29
	 */
	int deleteModifyAirWaybillSerialNo(List<String> serialNoList , String airWaybillDetailId);
	
	/**
	 * 根据航空正单明细id、运单号查询流水明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-12 上午8:35:03
	 */
	List<AirWaybillSerialNoEntity> queryAirWaybillSerialNoList(String airWaybillDetailId);
	
	/**
	 * 根据正单号查询运单明细list
	 * @param  airWaybillNo 正单号
	 * @return List<String> 运单号list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-24 上午9:40:34
	 */
	List<String> queryWaybillNoList(String airWaybillNo);
	
	/**
	 * 根据正单号查询待打印的航空正单
	 * @param airWaybillNo 正单号
	 * @return 航空正单实体 
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-7 下午5:25:50
	 */
	AirWaybillEntity queryWidthPrintAirWaybill(String airWaybillNo);
	
	/**
	 * 根据运单号查询运单走货轨迹
	 * @param waybillNo 运单号
	 * @return WayBillAssembleDto
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-10 下午1:59:42
	 */
	List<WayBillAssembleDto> queryWaybillPath(String waybillNo);
	
	/**
	 * 处理运单流水件数比对
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-10 上午10:28:13
	 */
	List<AirSerialNoDetail> waybillComparison(AirWaybillDetailDto searchConditionDto);
	
	/**
	 * 查询需要制作唐翼的运单集合
	 * @param airWaybillNo 正单号
	 * @return 返回运单明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-15 下午2:16:41
	 */
	List<AirWaybillDetailEntity> queryRequireMakeTangYiTargetWaybillList(String airWaybillNo);
	
	/**
	 * 根据航空正单号查询所属运单的出发部门
	 * @param airWaybillNo 正单号
	 * @author liuzhaowei
	 * @date 2013-7-10 下午2:07:04
	 */
	List<WaybillInfoForNoticeDto> queryWaybillInfoByAirwaybillNo(String airWaybillNo);
	
	/*
	 * 根据航空正单id和运单号删除某个航正单中的运单明细
	 * @ airWaybillId 正单ID
	 * @ airWaybillNo 运单号
	 * @ author wqh
	 * @ date    2013-07-29 上午 08:44
	 * */
	public int deleteByAirWaybillDetail(String airWaybillId,List waybillNoList);
	
	/*
	 * 根据运单号查询运单详细信息
	 * @ waybillNo 运单号
	 * @ author wqh
	 * @ date    2013-09-11
	 * */
	WaybillDetailDto queryWaybillDetailByWaybillNo(String waybillNo);
	
	/*
	 * 根据正单id查询运单明细
	 * @ airWaybillId id
	 * @ author wqh
	 * @ date    2013-10-27
	 * */
	List<String> queryWaybillNosByAirWaybillId(String airWaybillId);

    /**
     *根据条件查询快递空运单据
     * @author  lianghaisheng
     * @param ticketDto
     * @date 2014-09-25
     * @return
     */
    List<AirWaybillDetailEntity> queryPackageWaybillEntity(
            AirWaybillDetailDto ticketDto);
    
    /**
   	 * 根据正单号查询运单明细list
   	 * @param  WaybillNo 运单号
   	 * @return List<String> 运单号list
   	 * @author 268220-chenmin
   	 * @date 2016-09-08 上午8:10:10
   	 */
   	List<AirWaybillDetailEntity> queryWaybillDetailEntityList(String waybillNo);
	
}