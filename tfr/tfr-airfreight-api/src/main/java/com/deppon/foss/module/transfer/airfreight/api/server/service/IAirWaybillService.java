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
 *  FILE PATH          :/IAirWaybillService.java
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
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWayBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWaybillDetailDto;

/**
 * 查询航空正单service接口 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-25 下午7:27:15
 */
public interface  IAirWaybillService extends IService {
	
	/**
	 * 查询航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:36:32
	 */
	List<AirWaybillEntity> queryAirWayBillList(AirWayBillDto airWayBillDto,int limit ,int start);
	
	/**
	 * 根据ID查询航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:36:43
	 */
	AirWaybillEntity queryResultEntity(AirWayBillDto airWayBillDto);
	
	/**
	 * 获取总记录数
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:36:56
	 */
	Long getCount(AirWayBillDto airWayBillDto);
	
	/**
	 * 根据ID获取当前航空正单付款状态
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:37:22
	 */
	String queryStatus(AirWayBillDto airWayBillDto);
	
	/**
	 * 根据ID获取打印数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-3 下午3:33:16
	 */
	List<AirWaybillDetailEntity> queryAirWaybillListByPrint(String id);
	
	/**
	 * 根据ID查询空运外发清单打印数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-5 上午10:53:41
	 */
	AirWaybillEntity queryAirWaybillEntityPrint(String id);
	
	/**
	 * 根据ID获取航空正单批量打印数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-6 下午2:30:08
	 */
	List<AirWaybillEntity> queryAirWaybillListPrint(String[] ids);
	
	/**
	 * 根据航空正单号和代理编码校验是否存在于空运配载单、合大票清单和中转清单中是否存在记录，
	 * 在任何一个表中存在即可返回TRUE,否则返回FALSE
	 * @param airwaybillNo 正单号
	 * @param agenctCode 代理编码
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-17 下午3:58:41
	 */
	boolean queryAirWaybillNoPickupBilllJoinTransferBillNo(String airwaybillNo, String agenctCode);
	/**
	 * 更新航空正单跟踪的相关信息: 实际出发时间、实际到达时间、跟踪状态、修改人、修改时间 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-21 下午6:25:51
	 */
	void updateAirWayBillTrack(List<AirWaybillEntity> airWaybillEntityList);
	
	/**
	 * 根据航空正单id查询航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-28 上午8:20:40
	 */
	AirWayBillDto airWayBillDto (String id);
	
	/**
	 * 调用航空正单基础费率
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-30 下午4:29:20
	 */
	AirlinesValueAddEntity queryRate (String flightCode,
		    String loadOrgCode,String deptAirfieldCode,Date billDate);
	
	/**
	 * 根据运单号查询该运单是否在航空正单明细中存在记录
	 * @param waybillNo 运单号
	 * @param destOrgCode 操作部门
	 * @return (true/false)true表示存在与之匹配的记录 false未找到与之匹配的记录
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-24 上午11:28:48
	 */
	boolean queryWaybillNoExists(String waybillNo, String destOrgCode);
	
	
	/**
	 * 根据运单号查询该运单是否有航空正单交接单出库记录
	 * @param waybillNo 运单号
	 * @return (true/false)true表示存在与之匹配的记录 false未找到与之匹配的记录
	 * @author 099197-foss-shixiaowei
	 * @date 2012-12-24 上午11:28:48
	 */
	boolean queryWaybillNoStockExists(String waybillNo);
	
	/**
	 * 根据航空正单号和代理编码批量校验是否存在于空运配载单、
	 * 合大票清单和中转清单中是否存在记录：不匹配的数据返回标示FALSE和正单号及代理编码,
	 * 否则在任何一个表中存在即可返回标示TRUE和正单号及代理编码 
	 * @param List<BillRecAndPayImportDto>
	 * @return List<BillRecAndPayImportDto>
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-30 上午9:48:24
	 */
	@SuppressWarnings("rawtypes")
	List batchCheckAirWaybillNoisExist(List list);

	/**
	 * 根据ID获取打印数据.
	 * @param id the id
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-3 下午3:33:16
	 */
	List<AirWaybillDetailEntity> queryAirWaybillListForPrint(String id);
	
	/**
	 * 查询航空正单（不分页）.
	 * @param airWayBillDto the air way bill dto
	 * @return the list
	 * @author 099197-foss-liuzhaowei
	 * @date 2013-05-21 上午11:36:32
	 */
	List<AirWaybillEntity> queryAirWayBillListNoPage(AirWayBillDto airWayBillDto);
	
	/**
	 * 导出航空正单.
	 * @param airWayBillDto the air way bill dto
	 * @return InputStream
	 * @author 099197-foss-liuzhaowei
	 * @date 2012-10-16 下午3:36:32
	 */
	InputStream queryAirWaybillForExport(AirWayBillDto airWayBillDto);
	/**
	 * 判断运单在0或1中是否存在
	 * @param waybillNo 运单号，type 0表示正单，1表示合票
	 * @return boolean
	 * @author 046130-foss-xuduowei
	 * @date 2013-07-12 下午3:16:23
	 */
	boolean judgeWaybillInAir(String waybillNo,String type);
	/**
	 * 获取运单的正单制作部门
	 * @param waybillNo 运单号
	 * @return String
	 * @author 046130-foss-xuduowei
	 * @date 2013-07-12 下午3:16:23
	 */
	String queryAirWaybillDept(String waybillNo);
	
	/**
	 * 根据运单号查询所在航空正单的配载类型
	 * @param AirWaybillDetailDto 
	 * @return the list
	 * @author 200968  zwd 
	 * @date 2015-04-24 上午15:36:32
	 */
	List<AirWaybillEntity> queryAirWayBillListByWayBill(AirWaybillDetailDto airWaybillDetailDto);
	
}