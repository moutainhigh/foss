/**
 *  initial comments.
 */
/*******************************************************************************
 * 
 * 
 * 
 * 
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/AirTransPickupBillService.java
 *  
 *  FILE NAME          :AirTransPickupBillService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  一、	制作合大票清单界面
1、	航空公司：正单所属航空公司；
2、	正单号：正单新增时录入的正单号；
3、	运单号：航空正单中运单明细的运单编号；
4、	到达网点：正单新增时的到达网点；
5、	航班号：正单新增时的航班号；
6、	目的站：正单新增时的目的站；
7、	日期：正单新增时航班日期；
8、	制单人：当前操作人姓名；
9、	制单时间：当前操作时间；
10、	运单明细列表：新增时默认为输入的正单的运单明细，修改时为新增清单时的运单明细；
11、	运单明细列表编辑控件：用于修改当前行的运单明细；
12、	添加按钮：添加运单信息；
13、	删除按钮：删除选中运单明细；
14、	中转按钮：标记运单需要中转；
15、	保存按钮：保存合大票清单信息；
16、	导出按钮：导出合大票清单信息；
二、	修改合大票清单中运单信息界面
1、	运单号：为选中运单的运单号；
2、	毛量：为选中运单的重量；
3、	计费重量：为选中运单的计费重量；
4、	送货费：为选中运单的送货费；
5、	件数：为选中运单的件数；
6、	收货人：为选中运单的收货人；
7、	电话：为选中运单的电话；
8、	地址：为选中运单的地址；
9、	备注：为选中运单的备注；
10、	确定按钮：保存修改记录；
11、	取消按钮：取消本次修改操作；

 *  
 *  
 *  
SR-1 显示数据规则：
1、	如果输入的航空公司和正单号已经生成合大票清单，则清单明细、到达网点、航班号、日期和目的站来源于已有的合大票清单，可以修改；
2、	如果输入的航空公司和正单号还没有生成合大票清单，则清单明细、到达网点、航班号、日期和目的站来源于正单信息，可以修改，清单明细中的是否中转默认为否；

SR-2	合大票清单中新添加的清单必须是制单部门为本部门的正单信息中的运单，可以为已合票过的清单；
 *  
 *  SR-3	导出按钮必须在保存之后才能使用；
 *  
 *  
 *  
 *  SR-4	导出规则：（以下所述字段均为模板中红色字段）
1、	到达网点为合大票清单中的到达网点名称；
2、	正单号为合大票清单中的正单号；
3、	航班为合大票清单中的航班号；
4、	航班日期为合大票清单中的航班时间；
5、	列表中的相应字段均来自清单明细中字段信息，且列出清单明细每一行数据，其中收货人为：收货人姓名+联系方式，合计信息为所有行的部分字段之和，求和字段为件数、毛重、计费重量、派送费、到付款、代收款；
6、	制表人为新增合大票清单人员姓名；
7、	日期为新增合大票清单时间；
8、	德邦物流：86769350/51/52/53为固定字样；
9、	模板中根据当前空运总调为广州空运总调则为“德 邦 物 流 合 票 提 货 清 单（广州）”，如果是深圳空运总调则为“德 邦 物 流 合 票 提 货 清 单（深圳）”；
10、	文件名称为航班号+“-”+正单号；
SR-5	对清单的任何修改只限于本次合大票清单的清单明细；
SR-6	制单部门没有在界面中显示，后台保存时自动保存制单部门（当前操作人所在部门）；

 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/

package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirJointTicketService;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirTransferService;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirQueryModifyPickupbillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirTransPickupBillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirChangeInventoryService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillToCubcService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IJointTicketAndPickupAndChangeListCallEdiService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IPushAirPickUpInfoService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirRevisebillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirRevisebillToCubcEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTranDataCollectionEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransferWaybillToCubcEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillTempEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.SerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirPickupBillJointlargeListException;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirTransPickupBillException;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirWayBillException;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.PointsSingleJointTicketException;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestBatchResult;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 制作中转提货清单service实现类.
 * @author 099197-foss-zhoudejun
 * @date 2012-12-25 下午5:55:07
 * 
 */
public class AirTransPickupBillService implements IAirTransPickupBillService {

	private static final org.apache.log4j.Logger LOGGER = LogManager.getLogger(AirTransPickupBillService.class);
	
	/** 注入中转提货清单dao */
	private IAirTransPickupBillDao airTransPickupBillDao;
	
	/** 注入中转公共服务. */
	private ITfrCommonService tfrCommonService;
	
	/** 注入变更清单service. */
	private IAirChangeInventoryService airChangeInventoryService;
	
	/** 注入综合预收应付接口. */
	private IAirJointTicketService airJointTicketService;
	
	/** 注入结算服务*/
	private IAirTransferService airTransferService;
	
	/** 注入导出服务*/
	private IJointTicketAndPickupAndChangeListCallEdiService jointTicketAndPickupAndChangeListCallEdiService;
	
	/**注入行政区域服务*/
	private IAdministrativeRegionsService administrativeRegionsService;
	
	/**  运单签收service */
	IWaybillSignResultService waybillSignResultService;
	
	/** 查找空运总调 service*/
	private IAirDispatchUtilService airDispatchUtilService;

	/**
	 * 运单Service
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**FOSS推送数据至OPP Service**/
	private IPushAirPickUpInfoService pushAirPickUpInfoService;
	/**
	 * 注入查询修改合大票dao
	 */
	private IAirQueryModifyPickupbillDao airQueryModifyPickupbillDao;
	
	/** 制作中转提货清单service */
	private IAirTransPickupBillService airTransPickupBillService;
	public void setAirTransPickupBillService(
			IAirTransPickupBillService airTransPickupBillService) {
		this.airTransPickupBillService = airTransPickupBillService;
	}

	/** FOSS异步推送数据至CUBC Service*/
	private IAirWaybillToCubcService airWaybillToCubcService;

	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}

	public void setAirWaybillToCubcService(
			IAirWaybillToCubcService airWaybillToCubcService) {
		this.airWaybillToCubcService = airWaybillToCubcService;
	}

	/**
 * 生成中转单号.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 上午9:44:14
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService#generateTransfersNumber()
	 */
	@Override
	public String generateTransfersNumber() {
		return tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZZ);
	}

	/**
	 * 根据航空公司,正单号查询航空正单明细.
	 * @param airTransPickupBillDto the air trans pickup bill dto
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 下午1:39:51
	 */
	@Override
	public List<AirWaybillDetailEntity> queryAirWaybillDetailList(
			AirTransPickupBillDto airTransPickupBillDto) {
/*  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
        
		airTransPickupBillDto.setDeptCode(orgAdministrativeInfoEntity.getCode());
		//根据航空公司,正单号查询航空正单明细
		List<AirWaybillDetailEntity> listAirWaybillDetailEntity = airTransPickupBillDao.queryAirWaybillDetailList(airTransPickupBillDto);
		//可以添加的航空正单明细
		List<AirWaybillDetailEntity> insertListAirWaybillDetailEntity = new ArrayList<AirWaybillDetailEntity>();
		//判断航空正单明细中是否存在流水号已使用完，使用完流水号的正单明细不再添加
		for(AirWaybillDetailEntity ade : listAirWaybillDetailEntity){
			//判断该运单的流水号是否已使用完,通过判断正单流水是否与已制作的流水相等来判断是否已使用完毕
			//总的流水
			List<SerialEntity> listSerialEntity = airTransPickupBillDao.findPickupbillSerial(ade.getWaybillNo());
			//该运单已使用的流水
			 List<SerialEntity> listWaybill = airTransPickupBillDao.findWaybillSerial(ade.getWaybillNo());
			//判断该运单已使用的流水和总的流水是否相等,若相等，则不把运单信息传到前台
			 if(listSerialEntity != null){
				 if(listWaybill != null && listSerialEntity.size() == listWaybill.size()){
					 continue;
				 }
				 insertListAirWaybillDetailEntity.add(ade);
			 }
		}		
		return convertAddress(insertListAirWaybillDetailEntity);*/
		
		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		        
		airTransPickupBillDto.setDeptCode(orgAdministrativeInfoEntity.getCode());
		/**
		* 046130-foss-xuduowei,2013-03-20,
		* 增加对清单明细的地址转换
		*/
		return convertAddress(airTransPickupBillDao.queryAirWaybillDetailList(airTransPickupBillDto));
	}
	
	/**
	 * 对集合结果进行转换
	 * @author 046130-foss-xuduowei
	 * @date 2013-03-20 下午1:39:51
	 * @return
	 */
	private List<AirWaybillDetailEntity> convertAddress(List<AirWaybillDetailEntity> airWaybillDetailList){
		if(CollectionUtils.isEmpty(airWaybillDetailList)){
			return airWaybillDetailList;
		}else{
			//遍历结果集
			for(int i = 0;i<airWaybillDetailList.size();i++){
				//将原始地址经过转换变成格式化地址
				if(airWaybillDetailList.get(i) == null){
					continue;
				}
				if(!StringUtils.isEmpty(airWaybillDetailList.get(i).getReceiverAddress())){
				String address = obtainFormatAddress(airWaybillDetailList.get(i).getReceiverAddress());
				//更换地址
				airWaybillDetailList.get(i).setReceiverAddress(address);
				}
				
			}
			//返回结果
			return airWaybillDetailList;
		}	
	}
	
	/**
	 * 
	 * 将原始地址经过变化转化为用户需要的格式化地址
	 * @author 046130-foss-xuduowei
	 * @date 2013-03-20 下午1:39:51
	 * @return
	 */
	private String obtainFormatAddress(String address){
		String[] addressArray = address.split("#");

		if(addressArray.length == 1){
			return address;
		}
		//省
		String provName = "";
		//市
		String cityName = "";
		//区
		String distName = "";	
		//长度等于1时默认只有省
		if(addressArray.length >0){
			provName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(addressArray[0]);
		}
		//长度等于2时，默认有省，市
		if(addressArray.length >1){
			cityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(addressArray[1]);
		}
		//长度等于3时，默认省市区都有
		if(addressArray.length >2){
			distName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(addressArray[2]);
		}
		//定义字符对象
		StringBuffer sb = new StringBuffer();
		//省份名称不为空
		if(StringUtil.isNotEmpty(provName)){
			sb.append(provName);
			sb.append(AirfreightConstants.AIRFREIGHT_ADDRESS_WHIPPLETREE);
		}
		//城市名称不为空
		if(StringUtil.isNotEmpty(cityName)){
			sb.append(cityName);
			sb.append(AirfreightConstants.AIRFREIGHT_ADDRESS_WHIPPLETREE);
		}
		//区县名称不为空
		if(StringUtil.isNotEmpty(distName)){
			sb.append(distName);
			sb.append(AirfreightConstants.AIRFREIGHT_ADDRESS_WHIPPLETREE);
		}
		//追加具体地址
		if(addressArray.length >ConstantsNumberSonar.SONAR_NUMBER_3){
			sb.append(addressArray[3]);
			sb.append(AirfreightConstants.AIRFREIGHT_ADDRESS_WHIPPLETREE);
		}
		//判断长度是否为0
		if(sb.length()>0){
			return sb.substring(0, sb.length() - 1);
		}else{
			return sb.toString();
		}
	}

	/**
	 * 根据运单号新增航空正单明细.
	 * @param waybillNo the waybill no
	 * @return the air waybill detail entity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 下午7:33:02
	 */
	@Override
	public List<AirWaybillDetailEntity> addWaybillNoInfo(String waybillNo) {
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		if(!((waybillNo.substring(0, 1)).equals("B"))){
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillNo(waybillNo);
			
			/**
			 * author:106162 date:2016-12-1
			 * @description 先判断一个waybillEntity是否为空，防止报空指针异常,只是添加了一个外层逻辑判断
			 */
			if(waybillEntity!=null){
				//如果不是快递，校验流水号
				if(FossConstants.NO.equals(waybillEntity.getIsExpress())){
					//311396 wwb 2016年9月9日08:26:42 增加对零担未保存已提交合票流水号校验
					AirTransPickupBillDto airTransPickupBillDto = new AirTransPickupBillDto();
					airTransPickupBillDto.setDeptCode(orgAdministrativeInfoEntity.getCode());
					airTransPickupBillDto.setWaybillNo(waybillNo);
					//获取未录入合票的流水号
					List<SerialEntity>  resultList = airTransPickupBillService.findLeftSerialForModify(airTransPickupBillDto);
					if(CollectionUtils.isEmpty(resultList)){
						throw new AirTransPickupBillException("运单已无可用流水号");
					}
				}
			}
			
		}
		
		/**
		 * 046130-foss-xuduowei,2013-03-20,
		 * 增加对清单明细的地址转换
		 */
		//获取返回对象
		List<AirWaybillDetailEntity> airWaybillDetailList = airTransPickupBillDao.addWaybillNoInfo(waybillNo, orgAdministrativeInfoEntity.getCode());
		//设置转换后的地址
		if(airWaybillDetailList == null){
			return null;
		}
		/*****************校验运单是否已经制作过合大票清单 开始******************/
		//@date 2016-06-03
		//@author 269701--lln
		//根据运单号查询合大票清单明细
		AirPickupbillDetailEntity airPickUpDetialEntity=airTransPickupBillDao.getAirPickupbillDetailInfo(waybillNo);
		//合大票清单明细表清单件数
		BigDecimal airPickQty=new BigDecimal(0);
		if(airPickUpDetialEntity!=null){
			airPickQty=airPickQty.add(new BigDecimal(airPickUpDetialEntity.getAirPickQty()));
		}
		/************************************/
		for (AirWaybillDetailEntity airWaybillDetailEntity : airWaybillDetailList) {
			//wwb 311396 修改，设置新增的合大票明细id为N/A，避免用户修改该明细
			airWaybillDetailEntity.setId(AirfreightConstants.NEW_AIR_PICKUP_DETAIL_ID);
			
			if(!StringUtils.isEmpty(airWaybillDetailEntity.getReceiverAddress())){
				airWaybillDetailEntity.setReceiverAddress(obtainFormatAddress(airWaybillDetailEntity.getReceiverAddress()));
			}
			if(!airWaybillDetailEntity.getTransportType().equals(AirfreightConstants.DEAP)){
				BigDecimal airWayQty = null;
				//sonal,对象不可能为"" 352203
				if(airWaybillDetailEntity.getAirPickQty()==null){
					airWayQty=new BigDecimal(0);
				}else{
				//航空正单明细 制作件数
					airWayQty =new BigDecimal(airWaybillDetailEntity.getAirPickQty());
				}
				airPickQty=airWayQty.subtract(airPickQty);
				//界面 清单件数显示
				airWaybillDetailEntity.setAirPickQty(airPickQty.intValue());
			}
		}
		//返回对象
		return airWaybillDetailList;
	}

	/**
	 * 根据航空正单id查询收货人姓名、电话、地址.
	 * @param airWaybillId the air waybill id
	 * @return the air waybill entity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-13 下午4:02:51
	 */
	@Override
	public AirWaybillEntity queryAirWaybillReceiverInfo(String airWaybillId) {
		return airTransPickupBillDao.queryAirWaybillReceiverInfo(airWaybillId);
	}

	/**
	 * 新增/暂存合大票清单、合大票清单明细.
	 * @param list the list
	 * @param airWaybillEntity the air waybill entity
	 * @param airTranDataCollectionEntity the air tran data collection entity
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-14 下午2:28:09
	 */
	@Override
	@Transactional
	public AirPickupbillEntity addAirTransPickBILLAirPickupBill(
			List<AirPickupbillDetailEntity> list,
			AirWaybillEntity airWaybillEntity,
			AirTranDataCollectionEntity airTranDataCollectionEntity) {
		if (CollectionUtils.isEmpty(list)) {
			LOGGER.info("合票信息为空");
			throw new AirPickupBillJointlargeListException(
					AirfreightConstants.AIRFREIGHT_EXCEPTION_AIRPICKUPBILLDETAIL);
		}
		
		Long startTime=new Date().getTime();

		LOGGER.info("添加合票信息开始.................."+airWaybillEntity.getAirWaybillNo());
		LOGGER.info("添加合票信息开始时间："+new Date());

		
		//判断正单号是否已做过合大票
		AirPickupbillEntity existAirPickupbillEntity = queryAirPickupbillEntity(airWaybillEntity.getAirWaybillNo());
		if(existAirPickupbillEntity != null){
			throw new AirPickupBillJointlargeListException("正单号["+airWaybillEntity.getAirWaybillNo()+"]已存在,不能重复保存!");
		}
	     //BUG-56921 零担&快递集成环境-修改合大票清单界面一个未签收的单据可以同时添加在多个合大票清单里。	
		//KDTE-4376 由于存在不是正单的运单一也可以一起做进和票，取消限制
		//查询出当前正单中的所有运单
	/*	    List<String> waybillNoList=pointsSingleJointTicketService
		    		.queryWaybillNoList(airWaybillEntity.getAirWaybillNo());
		   if(!CollectionUtils.isEmpty(waybillNoList))
		   {
			   for(int i=0;i<list.size();i++)
			   {
				   //判断明细中的正单号是否与当前的正单号相同
				   if(!waybillNoList.contains(list.get(i).getWaybillNo()))
				   {
					   throw new TfrBusinessException("运单："+list.get(i).getWaybillNo()	+"不能添加在合票："
								+airWaybillEntity.getAirWaybillNo()+" 中");
				   }
				   
			   }
			   
		   } */
		
		/**
		 * BUG-56663 运单发更改后还能做合票，导致财务单据不对,
		 * @desc 保存合票之前判断运输性质是否为空运
		 * @author wqh 
		 * @date   2013-10-11
		 * 
		 * */
		List<String> waybillNos=new ArrayList<String>(); 
		
		if(!CollectionUtils.isEmpty(list)&&list.size()>0){
			for(int i=0;i<list.size();i++){
				String waybillNo=list.get(i).getWaybillNo().trim();
				
				//优化 这里做一次性查询运单信息 
				waybillNos.add(waybillNo);
			
			}
			if(waybillNos.size()<0){
				LOGGER.info("运单不存在!..................");
				throw new TfrBusinessException("运单不存在!");
			}else{
				
				LOGGER.info("查询接送货运单信息开始时间："+new Date());
				toJudgeAirInf(waybillNos);
			}
			
		}
		
		
		
		
		// 当前操作员工所属部门
		BigDecimal collectionFeeTotal = BigDecimal.ZERO;
		AirPickupbillEntity airPickupbillEntity = new AirPickupbillEntity(
				airWaybillEntity, airTranDataCollectionEntity,
				collectionFeeTotal);
		airPickupbillEntity.setId(UUIDUtils.getUUID());
		List<AirRevisebillDetailEntity> airRevisebillDetailList = new ArrayList<AirRevisebillDetailEntity>();
		//制作合大票运单集合（用于查询这些运单是否已签收）
		List<String> detailWaybillNoList = new ArrayList<String>();

  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		CurrentInfo currentInfo= FossUserContext.getCurrentInfo();
		
		for (int i = 0; i < list.size(); i++) {
			detailWaybillNoList.add(list.get(i).getWaybillNo());
			AirRevisebillDetailEntity airRevisebillDetailEntity = new AirRevisebillDetailEntity();
			list.get(i).setId(UUIDUtils.getUUID());
			list.get(i).setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			list.get(i).setCreateTime(new Date());
			list.get(i).setAirPickupbillId(airPickupbillEntity.getId());
			collectionFeeTotal = collectionFeeTotal.add(list.get(i).getCollectionFee());
			airRevisebillDetailEntity.setAirPickupbillDetailId(list.get(i).getId());
			airRevisebillDetailEntity.setAirTransPickupbillDetailId(UUIDUtils.getUUID());
			airRevisebillDetailEntity.setId(UUIDUtils.getUUID());
			airRevisebillDetailEntity.setCreateTime(new Date());
			airRevisebillDetailEntity.setOperationTime(new Date());
			airRevisebillDetailEntity.setOperationOrgCode(orgAdministrativeInfoEntity.getCode());
			airRevisebillDetailEntity.setOperationOrgName(orgAdministrativeInfoEntity.getName());
			airRevisebillDetailEntity.setOperatorName(currentInfo.getEmpName());
			airRevisebillDetailEntity.setOperatorCode(currentInfo.getEmpCode());
			airRevisebillDetailEntity.setReviseContent("(新增)大票清单");
			airRevisebillDetailList.add(airRevisebillDetailEntity);
			//@author 269701-lln
			//@date 2016-06-19
			//流水信息列表
			List<SerialEntity> serialList=new ArrayList<SerialEntity>();
			//流水信息列表
			List<SerialEntity> serialListAC=new ArrayList<SerialEntity>();
			//流水号列表
			List<String> airSerialNoList=new ArrayList<String>();
			//根据运单号查询合大票流水表 校验该运单是否已经制作过合大票清单
			AirTransPickupBillDto airTransPickupBillDto=new AirTransPickupBillDto();
			//运单号
			airTransPickupBillDto.setWaybillNo(list.get(i).getWaybillNo());
			//根据运单号查询合大票清单明细表 校验运单是否已经制作过合大票清单
			AirPickupbillDetailEntity airPickUpDetialEntity=airTransPickupBillDao.getAirPickupbillDetailInfo(list.get(i).getWaybillNo());
			if(null==airPickUpDetialEntity){
				//该运单未制作过合大票
				//流水信息=航空正单流水
				//根据运单号 查询航空正单流水信息表 得到流水列表
				airSerialNoList= airTransPickupBillDao.getAirWaySerialListByWaybill(list.get(i).getWaybillNo(),list.get(i).getAirWaybillNo());
				if(null!=airSerialNoList){
					for(int j=0;j<airSerialNoList.size();j++){
						//需要保存的流水信息列表
						SerialEntity serialEntity=new SerialEntity();
						//明细id
						serialEntity.setAirPickUpDetialId(list.get(i).getId());
						serialEntity.setWaybillNo(list.get(i).getWaybillNo());
						//流水id
						serialEntity.setId(UUIDUtils.getUUID());
						serialEntity.setSerialNo(airSerialNoList.get(j));
						serialList.add(serialEntity);
					}	
				}
				
			}else{
				//该运单制作过合大票
				//流水信息=运单流水-已制作合大票流水
				serialListAC=airTransPickupBillDao.findLeftSerialForModifyNot(airTransPickupBillDto);	
				if(null!=serialListAC){
					for(int k=0;k<serialListAC.size();k++){
						//需要保存的流水信息列表
						SerialEntity serialEntity=new SerialEntity();
						//明细id
						serialEntity.setAirPickUpDetialId(list.get(i).getId());
						serialEntity.setWaybillNo(list.get(i).getWaybillNo());
						//流水id
						serialEntity.setId(UUIDUtils.getUUID());
						serialEntity.setSerialNo(serialListAC.get(k).getSerialNo());
						serialList.add(serialEntity);
					}
				}
			}
			
			//新增合大票清单明细流水 
			//新增流水不为空
			if(serialList.size()>0){
				//校验清单件数
				//如果清单件数不等于开单件数 保存清单件数
				list.get(i).setGoodsQty(serialList.size());
				airQueryModifyPickupbillDao.addAirPickupbillSerialist(serialList);
			}
		}
		//add  2013-07-03 制作合大票时加判断运单是否已签收，如果包含有已签收的运单则抛出异常
		//查询已签收运单信息
		
		LOGGER.info("查询运单签收接口开始时间："+new Date());
		List<String> waybillNoSignList = waybillSignResultService.queryWaybillSignResultWaybillNos(detailWaybillNoList);
		LOGGER.info("查询运单签收接口结束时间："+new Date());

		if(waybillNoSignList != null && waybillNoSignList.size() > 0){
			//拼接已经签收的运单号
			StringBuffer signNos = new StringBuffer();
			for(int i = 0;i<waybillNoSignList.size();i++){
				signNos.append(waybillNoSignList.get(i)).append(",");
			}
			throw new TfrBusinessException("制作合大票包含已签收的运单！ " + signNos.substring(0,signNos.length()-1));
		}
		
		
		
		//@date 2016-07-06
		//@author 269701
		//清单总件数=运单流水总件数
		airPickupbillEntity.setGoodsQtyTotal(airTranDataCollectionEntity.getAirPickQtyTotal());
		
		airPickupbillEntity.setCollectionFeeTotal(collectionFeeTotal);
		airPickupbillEntity.setOrigOrgCode(orgAdministrativeInfoEntity.getCode());
		airPickupbillEntity.setOrigOrgName(orgAdministrativeInfoEntity.getName());
		airPickupbillEntity.setStatus(airTranDataCollectionEntity.getStatus());
		
		// 保存合大票清单
		LOGGER.info("新增合大票清单开始时间："+new Date());
		addAirPickupBill(airPickupbillEntity);
		// 新增合大票清单明细
		
		airTransPickupBillDao.addAirWaybillDetail(list);

		LOGGER.info("新增合大票清单结束时间："+new Date());

		
		//新增变更清单日志
		addaddAirRevisebillDetailEntity(airRevisebillDetailList);
		//BUG-49036 
		//空运开单查询组部门人员作合大票操作会生成应收应付部门为开单查询组的财务单据，影响结算凭证和报表 
		//@author wqh
		//@date 2013-08-02
		//当点击的是提交按钮时，才调用结算接口
		if("Y".equals(airTranDataCollectionEntity.getStatus())){
			//// cubcgray 335284-316759
			GrayParameterDto parDto = new GrayParameterDto();
			parDto.setSourceBillType("W");
			parDto.setSourceBillNos(detailWaybillNoList.toArray(new String[detailWaybillNoList.size()]));
			// 调用灰度
			VestResponse vestResponseDto = cubcUtil.getUcbcGrayData(parDto, new Throwable());
			List<VestBatchResult> batchResult = vestResponseDto.getVestBatchResult();
			List<AirPickupbillDetailEntity> stlList = new ArrayList<AirPickupbillDetailEntity>();
			List<AirPickupbillDetailEntity> cubcList = new ArrayList<AirPickupbillDetailEntity>();
			for (VestBatchResult result : batchResult) {
				if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(result.getVestSystemCode())) {
					List<String> object = result.getVestObject();
					for (AirPickupbillDetailEntity e : list) {
						for (String s : object) {
							if (e.getWaybillNo() != null && e.getWaybillNo().equals(s)) {
								stlList.add(e);
								object.remove(s);
								break;
							}
						}
					}
				} else {
					List<String> object = result.getVestObject();
					for (AirPickupbillDetailEntity e : list) {
						for (String s : object) {
							if (e.getWaybillNo() != null && e.getWaybillNo().equals(s)) {
								cubcList.add(e);
								object.remove(s);
								break;
							}
						}
					}
				}
			}
			if (stlList.size() > 0) {
				try {
					//调用结算预收应付接口
					LOGGER.info("新增合大票清单【调用结算预收应付接口】开始时间："+new Date());
					airJointTicketService.addAirJointTicket(airPickupbillEntity, stlList,new CurrentInfo(currentInfo.getUser(),orgAdministrativeInfoEntity));
					LOGGER.info("新增合大票清单【调用结算预收应付接口】结束时间："+new Date());
				} catch (SettlementException e) {
					LOGGER.info("{新增合票清单调用结算接口出现异常，异常信息：}"+e.getErrorCode());
					throw new AirPickupBillJointlargeListException("{新增合票清单调用结算接口出现异常，异常信息：}"+e.getErrorCode());
				}
			}
			if (cubcList.size() > 0) {
				/**
				 * 同步合大票信息至CUBC
				 * @author 316759-foss-RuipengWang
				 * @date 2016-10-21 15:18:32 PM
				 */
				try {
					AirRevisebillToCubcEntity airRevisebillToCubcEntity = new AirRevisebillToCubcEntity();
					airRevisebillToCubcEntity.setAirPickupbillEntity(airPickupbillEntity);
					airRevisebillToCubcEntity.setStlModifyList(cubcList);
					if (null != airRevisebillToCubcEntity) {
						String requestStr = JSONObject.toJSONString(airRevisebillToCubcEntity);
						LOGGER.info("推送给CUBC的参数 requestStr = " + requestStr);
						airWaybillToCubcService.pushAddAirRevisebill(requestStr);
					}
				} catch (Exception e) {
					// 推送失败
					throw new PointsSingleJointTicketException("推送合大票信息至CUBC失败：" + e.getMessage());
				}
			}
			////end cubcgray 
		}
		Long endTime=new Date().getTime();
		LOGGER.info("添加合票信息结束：" + airWaybillEntity.getAirWaybillNo() + "。查新增合大票清单共用去时间：" + (endTime - startTime));

		//将新增的合大票数据以及合大票明细数据同步至OPP系统
		//同步方式：数据暂存至临时表 然后使用job每五分钟推送一次
		//@author 269701--lln
		//@data 2016-05-11
		//当点击提交按钮时才把数据推送给OPP
		if("Y".equals(airTranDataCollectionEntity.getStatus())){
			AirWaybillTempEntity temEntity=new AirWaybillTempEntity();
			temEntity.setAirPickUpId(airPickupbillEntity.getId());
			//清单号
			temEntity.setAirPickNo(airPickupbillEntity.getAirWaybillNo());
			//推送中
			temEntity.setPushStatus(FossConstants.NO);
			//清单：20 正单 10
			temEntity.setBillType("20");
			//新增数据
			temEntity.setOperateStatus("INSERT");
			pushAirPickUpInfoService.addAirPickToTemp(temEntity);
		}
/*//TODO		
		pushAirPickUpInfoService.doPushAirPickUpInfo();*/
		return airPickupbillEntity;
	}
	
	/**
	 * sonar优化 311396 wwb 2016年12月24日10:26:43  
	 * @param waybillNos
	 */
	private void toJudgeAirInf(List<String> waybillNos) {
		boolean f = false;
		for(String waybillNo : waybillNos){
			if(waybillNo.substring(0, 1).equals("B")){
				f=true;
				break;
			}
		}
		if(!f){
			List<WaybillEntity> waybillEntityList=waybillManagerService.queryWaybillBasicByNoList(waybillNos);
			LOGGER.info("查询接送货运单信息结束时间："+new Date());
			if(!CollectionUtils.isEmpty(waybillEntityList)&&waybillEntityList.size()>0){
				/**
				 * 增加商务专递产品类型
				 * by wqh
				 * 2015-09-06
				 * */
				List<String> airProductCodes=new ArrayList<String>();
				airProductCodes.add(AirfreightConstants.AIR_PROCDUCT_CODE);
				airProductCodes.add(AirfreightConstants.AIR_PACKAGE_PROCDUCT_CODE);
			  for(WaybillEntity waybillEntity:waybillEntityList){

				  if(!airProductCodes.contains(waybillEntity.getProductCode())){
					  LOGGER.info("运单：{"+waybillEntity.getWaybillNo()+"} 不是空运运单，删除后再保存合票单据");
					  throw new TfrBusinessException("运单：{"+waybillEntity.getWaybillNo()+"} 不是空运运单，删除后再保存合票单据");
				  }
			  }
			}else{
				LOGGER.info("查询运单信息失败!..................");
				throw new TfrBusinessException("查询运单信息失败!");
			}
		}
	}

	/**
	 * 批量新增变更清单明细logger日志.
	 * @param airRevisebillDetailList 变更清单明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-13 下午1:10:08
	 */
	private void addaddAirRevisebillDetailEntity(List<AirRevisebillDetailEntity> airRevisebillDetailList){
		airChangeInventoryService.addaddAirRevisebillDetailList(airRevisebillDetailList);
	}
	
	/**
	 * 新增合大票清单.
	 *
	 * @param airPickupbillEntity the air pickupbill entity
	 * @return the int
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-14 下午3:30:35
	 */
	@Override
	public int addAirPickupBill(AirPickupbillEntity airPickupbillEntity) {
		return airTransPickupBillDao.addAirPickupBill(airPickupbillEntity);
	}

	/**
	 * 新增中转提货清单明细.
	 * @param list the list
	 * @return the int
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-14 下午3:30:40
	 */
	@Override
	@Transactional
	public int addAirTransferWaybillDetail(AirTranDataCollectionEntity entity,List<AirPickupbillDetailEntity> list) {
		if (CollectionUtils.isEmpty(list)) {
			throw new AirPickupBillJointlargeListException(
					AirfreightConstants.AIRFREIGHT_EXCEPTION_AIRPICKUPBILLDETAIL);
		}
		// 获取合大票清单
		AirPickupbillEntity airPickupbillEntity = airTransPickupBillDao
				.queryAirPickupbillEntity(list.get(0).getAirWaybillNo());
		// 根据合大票清单构造中转提过货清单数据
		//airPickupbillEntity
		AirTransPickupbillEntity airTransPickupbillEntity = new AirTransPickupbillEntity(
				airPickupbillEntity, entity.getAirTransferPickupbillNo());
		airTransPickupbillEntity.setId(UUIDUtils.getUUID());
		airTransPickupbillEntity.setArrvRegionCode(entity.getArrvRegionCode());
		airTransPickupbillEntity.setArrvRegionName(entity.getArrvRegionName());
		airTransPickupbillEntity.setDestOrgCode(entity.getDestOrgCode());
		airTransPickupbillEntity.setDestOrgName(entity.getDestOrgName());
		airTransPickupbillEntity.setTransferDate(entity.getTransferDate());
		airTransPickupbillEntity.setTransferFlightNo(entity.getTransferFlightNo());
		airTransPickupBillDao.addAirTransPickupBill(airTransPickupbillEntity);
		List<AirPickupbillDetailEntity> details = new ArrayList<AirPickupbillDetailEntity>();
		List<String> waybillNoList = new ArrayList<String>();
		List<String> cubcOrfossWBs = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			cubcOrfossWBs.add(list.get(i).getWaybillNo());
			list.get(i).setCreateTime(new Date());
			list.get(i).setAirPickupbillId(airTransPickupbillEntity.getId());
			list.get(i).setId(UUIDUtils.getUUID());
			//当flag标记为Y的运单表示是新增的运单需要将到付费和代收货款清0
			if(StringUtils.equals(FossConstants.ACTIVE,list.get(i).getFlag())){
				list.get(i).setArrivalFee(BigDecimal.ZERO);
				list.get(i).setCollectionFee(BigDecimal.ZERO);
				waybillNoList.add(list.get(i).getWaybillNo());
				details.add(list.get(i));
			}else{
				waybillNoList.add(list.get(i).getWaybillNo());
			}
		}
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		UserEntity user = FossUserContext.getCurrentInfo().getUser();
		
		
		/////335284 cubc-gray
		GrayParameterDto parDto_ = new GrayParameterDto();
		parDto_.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
		parDto_.setSourceBillNos( cubcOrfossWBs.toArray(new String[cubcOrfossWBs.size()]) );
		VestResponse vestResponseDto_ = cubcUtil.getUcbcGrayData(parDto_, new Throwable());
		List<String> fossWBs = new ArrayList<String>(), cubcWBs = new ArrayList<String>();
		for (VestBatchResult r : vestResponseDto_.getVestBatchResult()) {
			if (CUBCGrayContants.SYSTEM_CODE_FOSS.equals(r.getVestSystemCode())) {
				fossWBs = r.getVestObject();
			} else {
				cubcWBs = r.getVestObject();
			}
		}
		//调用结算
		//调用合大票服务
		if(!CollectionUtils.isEmpty(details)){
			for (int i = 0; i < details.size() ; i++) {
				List<AirPickupbillDetailEntity> detailList = new ArrayList<AirPickupbillDetailEntity>();
				AirPickupbillEntity pickupEntity = airTransPickupBillDao.batchSearchPickupbill(details.get(i).getAirWaybillNo());
				detailList.add(details.get(i));
				if (fossWBs.contains(details.get(i).getWaybillNo())) {
					try {
						//调用结算服务
						airJointTicketService.addAirJointTicket(pickupEntity, detailList, new CurrentInfo(user,orgAdministrativeInfoEntity));
					} catch (BusinessException e) {
						throw new AirTransPickupBillException(e.getErrorCode());
					}
				} else if (cubcWBs.contains(details.get(i).getWaybillNo())) {
					AirRevisebillToCubcEntity airRevisebillToCubcEntity = new AirRevisebillToCubcEntity();
					airRevisebillToCubcEntity.setAirPickupbillEntity(pickupEntity);
					airRevisebillToCubcEntity.setStlModifyList(detailList);
					String requestStr = JSONObject.toJSONString(airRevisebillToCubcEntity);
					LOGGER.info("addAirTransferWaybillDetail111推送给CUBC的参数 requestStr = " + requestStr);
					try {
						//同步
						airWaybillToCubcService.pushAddAirRevisebill(requestStr);
					} catch (BusinessException e) {
						throw new AirTransPickupBillException(e.getErrorCode());
					}
				} else {
					AirTransPickupBillException exception = new AirTransPickupBillException("cubc灰度分流异常：未知的运单归属=" + details.get(i).getWaybillNo());
					LOGGER.error("找不到运单归属系统", exception);
					throw exception;
				}
			}
		}
		//更新合票明细中的到付费和代收货款
		// 新增中转提货清单明细
		List<AirPickupbillDetailEntity> ls = new ArrayList<AirPickupbillDetailEntity>();
		airTransPickupBillDao.updateAirupbillNoList(waybillNoList);
		if (fossWBs.size() > 0) {
			List<AirPickupbillDetailEntity> fList = matchAirList(list, fossWBs);
			ls.addAll(fList);
			try {
				airTransferService.addAirTransfer(airTransPickupbillEntity,getConvertTransfrDetail(fList), new CurrentInfo(user,orgAdministrativeInfoEntity));
			} catch (BusinessException e) {
				throw new AirTransPickupBillException(e.getErrorCode());
			}
		}
		if (cubcWBs.size() > 0) {
			List<AirPickupbillDetailEntity> cList = matchAirList(list, cubcWBs);
			ls.addAll(cList);
			/**
			 * 同步中转提货清单至CUBC
			 * @author 316759-foss-RuipengWang
			 */
			AirTransferWaybillToCubcEntity airTransferWaybillToCubcEntity = new AirTransferWaybillToCubcEntity();
			airTransferWaybillToCubcEntity.setAirTransPickupbillEntity(airTransPickupbillEntity);
			airTransferWaybillToCubcEntity.setAirTransPickupDetailEntities(getConvertTransfrDetail(cList));
			String requestStr = JSONObject.toJSONString(airTransferWaybillToCubcEntity);
			LOGGER.info("addAirTransferWaybillDetail222推送给CUBC的参数 requestStr = " + requestStr);
			try {
				airWaybillToCubcService.pushAddTransferWaybill(requestStr);
			} catch (BusinessException e) {
				throw new AirTransPickupBillException(e.getErrorCode());
			}
		}
		////end cubc
		return airTransPickupBillDao.addAirPickupbillDetailList(ls);
	}
	/**
	 * 返回list中被wbs命中的元素列表
	 * @param list 大列表
	 * @param fossWBs 匹配列表
	 * @return 命中列表
	 */
	private List<AirPickupbillDetailEntity> matchAirList(List<AirPickupbillDetailEntity> list, List<String> wbs) {
		List<AirPickupbillDetailEntity> rst = new ArrayList<AirPickupbillDetailEntity>();
		l: for (String wb : wbs) {
			for (AirPickupbillDetailEntity ae : list) {
				if (ae.getWaybillNo() != null && ae.getWaybillNo().equals(wb)) {
					rst.add(ae);
					list.remove(ae);
					continue l;
				}
			}
		}
		return rst;
	}

	/**
	 * 将合票明细转换为中转提货清单明细 
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-9 上午11:22:15
	 */
	private List<AirTransPickupDetailEntity> getConvertTransfrDetail(List<AirPickupbillDetailEntity> list){
		List<AirTransPickupDetailEntity> newAirTransPickupDetailList = new ArrayList<AirTransPickupDetailEntity>();
		if(!CollectionUtils.isEmpty(list)){
			for (int i = 0; i <list.size(); i++) {
				AirTransPickupDetailEntity transPickupDetailEntity = new AirTransPickupDetailEntity ();
				AirPickupbillDetailEntity pickupbillDetail = (AirPickupbillDetailEntity)list.get(i);
				transPickupDetailEntity.setAirWaybillNo(pickupbillDetail.getAirWaybillNo());
				transPickupDetailEntity.setWaybillNo(pickupbillDetail.getWaybillNo());
				transPickupDetailEntity.setArrivalFee(pickupbillDetail.getArrivalFee());
				transPickupDetailEntity.setArrvRegionName(pickupbillDetail.getArrvRegionName());
				transPickupDetailEntity.setBeTransfer(pickupbillDetail.getBeTransfer());
				transPickupDetailEntity.setBillingWeight(pickupbillDetail.getBillingWeight());
				transPickupDetailEntity.setCollectionFee(pickupbillDetail.getCollectionFee());
				transPickupDetailEntity.setCreateDate(new Date());
				transPickupDetailEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				transPickupDetailEntity.setDeliverFee(pickupbillDetail.getDeliverFee());
				transPickupDetailEntity.setGoodsName(pickupbillDetail.getGoodsName());
				transPickupDetailEntity.setGoodsQty(pickupbillDetail.getGoodsQty());
				transPickupDetailEntity.setId(UUIDUtils.getUUID());
				transPickupDetailEntity.setNotes(pickupbillDetail.getNotes());
				transPickupDetailEntity.setPickupType(pickupbillDetail.getPickupType());
				transPickupDetailEntity.setReceiverAddress(pickupbillDetail.getReceiverAddress());
				transPickupDetailEntity.setReceiverContactPhone(pickupbillDetail.getReceiverContactPhone());
				transPickupDetailEntity.setReceiverName(pickupbillDetail.getReceiverName());
				transPickupDetailEntity.setModifyDate(new Date());
				transPickupDetailEntity.setModifyUser(FossUserContext.getCurrentUser().getUserName());
				newAirTransPickupDetailList.add(transPickupDetailEntity);
			}
		}
		return newAirTransPickupDetailList;
	}

	/**
	 * 根据航空公司、运单号查询合大票清单明细.
	 * @param airTransPickupBillDto the air trans pickup bill dto
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-15 上午10:22:21
	 */
	@Override
	public List<AirPickupbillDetailEntity> queryAirPickupbillList(
			AirTransPickupBillDto airTransPickupBillDto) {
		return airTransPickupBillDao
				.queryAirPickupbillList(airTransPickupBillDto);

	}

	/**
	 * 根据运单号添加一条合票清单明细信息.
	 * @param waybillNo the waybill no
	 * @return the air pickupbill detail entity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-15 下午5:36:01
	 */
	@Override
	public AirPickupbillDetailEntity waybillNoAddToTransferDetail(
			AirTransPickupBillDto airTransPickupBillDto) {
		return airTransPickupBillDao.waybillNoAddToTransferDetail(airTransPickupBillDto);
	}

	/**
	 * 根据正单号获取合大票清单.
	 *
	 * @param airWaybillNo the air waybill no
	 * @return the air pickupbill entity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-16 上午9:00:33
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService#queryAirPickupbillEntity(java.lang.String)
	 */
	@Override
	public AirPickupbillEntity queryAirPickupbillEntity(String airWaybillNo) {
		return airTransPickupBillDao.queryAirPickupbillEntity(airWaybillNo);
	}
	
	  /**
	   * 根据正单号获取合大票清单.
	   *
	   * @param airTransPickupBillDto
	   * @return the air pickupbill entity
	   * @author 099197-foss-liuzhaowei
	   * @date 2013-04-22上午9:00:33
	   * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService#queryAirPickupbillEntityByDto(AirTransPickupBillDto)
	   */
	  @Override
	  public AirPickupbillEntity queryAirPickupbillEntityByDto(AirTransPickupBillDto airTransPickupBillDto) {
	    return airTransPickupBillDao.queryAirPickupbillEntityByDto(airTransPickupBillDto);
	  }
	
	/**
	 * 上传合票信息上传给EDI 
	 * @param airWaybillNo 正单号
	 * @return InputStream 返回(明细)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-19 下午2:36:34
	 */
	@Override
	public InputStream uploadPickupCallEdi(List<String> idsList , String airWaybillNo ,String callIsNotEdiFlag){
		InputStream inputStream = null;
		try {
			inputStream = jointTicketAndPickupAndChangeListCallEdiService.uploadPickupCallEdi(idsList ,airWaybillNo ,callIsNotEdiFlag);
		} catch (BusinessException e) {
			throw new AirPickupBillJointlargeListException(e.getErrorCode());
		}
		return inputStream;
	}
	
	/**
	 * 上传中转提货清单给EDI 
	 * @param airWaybillNo 正单号
	 * @return InputStream 返回(明细)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-19 下午2:36:34
	 */
	@Override
	public InputStream uploadTranPickupCallEdi(List<String> idsList , String airWaybillNo ,String callIsNotEdiFlag){
		InputStream inputStream = null;
		try {
			inputStream = jointTicketAndPickupAndChangeListCallEdiService.uploadTranPickupCallEdi(idsList ,airWaybillNo ,callIsNotEdiFlag);
		} catch (BusinessException e) {
			throw new AirPickupBillJointlargeListException(e.getErrorCode());
		}
		return inputStream;
	}
	
	/**
	 * 根据正单号查询中转提过货清单.
	 * @param airWaybillNo the air waybill no
	 * @return the air trans pickupbill entity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-20 上午9:40:31
	 */
	@Override
	public AirTransPickupbillEntity queryAirTransPickupbillEntity(
			String airWaybillNo) {
		return airTransPickupBillDao
				.queryAirTransPickupbillEntity(airWaybillNo);
	}

	/**
	 * 根据正单号查询中转提过货清单明细.
	 * @param airWaybillNo the air waybill no
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-20 上午9:40:31
	 */
	@Override
	public List<AirTransPickupDetailEntity> queryAirTransPickupDetailList(
			String airWaybillNo) {
		return airTransPickupBillDao
				.queryAirTransPickupDetailList(airWaybillNo);
	}

	/**
	 * 根据 制单时间,航空公司,正单号,目的站,到达网点,空运总调 查询合票清单明细.
	 * @param airTransPickupBillDto the air trans pickup bill dto
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-1 下午2:51:11
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService#queryMakePickGoods(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto)
	 */
	@Override
	public List<AirPickupbillEntity> queryMakePickGoods(
			AirTransPickupBillDto airTransPickupBillDto, int limit, int start) {
		return airTransPickupBillDao.queryMakePickGoods(airTransPickupBillDto,
				limit, start);
	}

	/**
	 * 获取合票信息总记录数.
	 * @param airTransPickupBillDto the air trans pickup bill dto
	 * @return the make pick goods count
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-1 下午3:36:20
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService#getCount(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto)
	 */
	@Override
	public Long getMakePickGoodsCount(
			AirTransPickupBillDto airTransPickupBillDto) {
		return airTransPickupBillDao
				.getMakePickGoodsCount(airTransPickupBillDto);
	}

	/**
	 * 根据航空正单id查询合大票清单、明细.
	 * @param airWaybillNo the air waybill no
	 * @return the air trans pickup bill dto
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-3 下午4:49:14
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService#queryAirTransPickupBillOrDetail(java.lang.String)
	 */
	@Override
	public AirTransPickupBillDto queryAirTransPickupBillOrDetail(String id) {
		AirTransPickupBillDto resultAirTransPickupBillDto = new AirTransPickupBillDto();
		//查询合大票清单
		AirPickupbillEntity airPickupbillEntity = airTransPickupBillDao.queryAirPickupbillEntityById(id);
		//查询合大票清单明细
		List<AirPickupbillDetailEntity> queryAirPickupbillList = new ArrayList<AirPickupbillDetailEntity>();
		if(airPickupbillEntity != null){
			queryAirPickupbillList = airTransPickupBillDao
					.queryAirPickupbillDetailList(airPickupbillEntity.getAirWaybillNo());
		}
		resultAirTransPickupBillDto.setAirPickupbillEntity(airPickupbillEntity);
		resultAirTransPickupBillDto.setAirPickupbillDetailList(queryAirPickupbillList);
		return resultAirTransPickupBillDto;
	}
	
	/**
	 * 根据正单号查询合大票清单中是否存在记录.
	 * @param airWaybillNo the air waybill no
	 * @return 返回(true 存在 false 不存在)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-15 下午3:42:27
	 */
	@Override
	public boolean checkExistAirTransPickupBill(AirTransPickupBillDto airTransPickupBillDto) {
		return airTransPickupBillDao.checkExistAirTransPickupBill(airTransPickupBillDto);
	}
	/**
	 * 
	* @description 根据运单号查询 未制作流水信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService#findLeftSerial(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto)
	* @author 269701-foss-lln
	* @update 2016年4月20日 上午10:11:37
	* @version V1.0
	 */
	@Override
	public List<SerialEntity> findLeftSerial(AirTransPickupBillDto airTransPickupBillDto) {
			//返回结果
			List<SerialEntity> serialList=new ArrayList<SerialEntity>();
			//1. 校验参数是否为空
			if(null==airTransPickupBillDto){
				throw new AirWayBillException("查询参数为空");
			}
			if(StringUtils.isEmpty(airTransPickupBillDto.getWaybillNo())){
				throw new AirWayBillException("运单号为空");
			}
			//2.校验运单是否存在
			boolean wayBillNoIsExtis=airTransPickupBillDao.checkWayBillIsExits(airTransPickupBillDto.getWaybillNo());
			if(wayBillNoIsExtis){
				 //3.如果合大票清单流水表里面没有数据 显示未选信息为空
				serialList=airTransPickupBillDao.findRightSerialForLeft(airTransPickupBillDto);
				if(serialList.size()<=0){
					return null;
				}else{
					//4.根据运单号 查询合大票清单流水不在运单流水里面的
					//运单流水：A 正单流水 B 合票流水 C
					//合大票修改时 未选流水=A-C
					//合大票新增时 未选流水=A-B-C
					serialList=airTransPickupBillDao.findLeftSerial(airTransPickupBillDto);	
				}
			
			}else{
				throw new AirWayBillException("运单号不存在");
			}
		//根据运单号获取新旧流水号关系
		return serialList;
	}
	
	/**
	 * 
	* @description 合大票修改时 根据运单号查询未制作合大票流水号
	* @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService#findLeftSerialForModify(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto)
	* @author 269701-foss-lln
	* @update 2016年4月20日 上午10:11:37
	* @version V1.0
	 */
	@Override
	public List<SerialEntity> findLeftSerialForModify(AirTransPickupBillDto airTransPickupBillDto) {
			//返回结果
			List<SerialEntity> serialList=new ArrayList<SerialEntity>();
			//1. 校验参数是否为空
			if(null==airTransPickupBillDto){
				throw new AirWayBillException("查询参数为空");
			}
			if(StringUtils.isEmpty(airTransPickupBillDto.getWaybillNo())){
				throw new AirWayBillException("运单号为空");
			}
			//2.校验运单是否存在
			boolean wayBillNoIsExtis=airTransPickupBillDao.checkWayBillIsExits(airTransPickupBillDto.getWaybillNo());
			if(wayBillNoIsExtis){
					//4.根据运单号 查询合大票清单流水不在运单流水里面的*/
					//运单流水：A 正单流水 B 合票流水 C
					//合大票修改时 未选流水=A-C
				serialList=airTransPickupBillDao.findLeftSerialForModify(airTransPickupBillDto);	
				/*}*/
			}else{
				throw new AirWayBillException("运单号不存在");
			}
		//返回流水
		return serialList;
	}
	/**
	 * 
	* @description 根据运单号查询 已制作流水信息
	*  合大票流水表中 没有该运单对应的流水 则显示航空正单流水信息
	*  合大票流水表 存在该运单对应的流水 则显示合大票流水信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService#findLeftSerial(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto)
	* @author 269701-foss-lln
	* @update 2016年4月20日 上午10:11:37
	* @version V1.0
	 */
	@Override
	public List<SerialEntity> findRightSerial(AirTransPickupBillDto airTransPickupBillDto) {
			//返回结果
			List<SerialEntity> serialList=new ArrayList<SerialEntity>();
			//1. 校验参数是否为空
			if(null==airTransPickupBillDto){
				throw new AirWayBillException("查询参数为空");
			}
			if(StringUtils.isEmpty(airTransPickupBillDto.getWaybillNo())){
				throw new AirWayBillException("运单号为空");
			}
			//是包号的时候不走
			if(!airTransPickupBillDto.getWaybillNo().substring(0, 1).equals("B")){
				//2.校验运单是否存在
				boolean wayBillNoIsExtis=airTransPickupBillDao.checkWayBillIsExits(airTransPickupBillDto.getWaybillNo());
				if(wayBillNoIsExtis){
					//3.根据运单号以及正单号 查询合大票流水信息
					serialList=airTransPickupBillDao.findRightSerialForRight(airTransPickupBillDto);				
					//流水号列表
					//List<String> serialNoList=new ArrayList<String>();
					//4.如果该运单未保存过流水 默认为航空正单全部流水
					/*if(serialList.size()<=0){
						// 根据运单号查询合大票清单流水
						//oldSerialList=airTransPickupBillDao.findRightSerialForOldSerial(airTransPickupBillDto);
						serialNoList= airTransPickupBillDao.getAirWaySerialListByWaybill(airTransPickupBillDto.getWaybillNo(),airTransPickupBillDto.getAirWaybillNo());
						//根据运单号 查询航空正单流水信息表 得到流水列表
						if(serialNoList.size()>0){
							for(int j=0;j<serialNoList.size();j++){
								SerialEntity serial=new SerialEntity();
								//流水号
								serial.setSerialNo(serialNoList.get(j));
								//运单号
								serial.setWaybillNo(airTransPickupBillDto.getWaybillNo());
								serialList.add(serial);
							}
						}else{
							//运单 12件流水 8件在合大票A1 剩下4件要做进合大票A2里面
							//运单流水：A 正单流水 B 合票流水 C
							//合大票新增时 未选流水=A-C
							//已选流水=运单总件数-合票流水
							serialList=airTransPickupBillDao.findLeftSerialForModify(airTransPickupBillDto);
							//此时设置未选流水为空
							leftSerialList=findLeftSerialForModify(airTransPickupBillDto);
							leftSerialList=new ArrayList<SerialEntity>();
						}
					}*/
				}else{
					throw new AirWayBillException("运单号不存在");
				}
			}
			//返回流水
		return serialList;
	}
	/**
	 * 
	* @description 保存制作合大票流水信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService#saveSerialNo(java.util.List)
	* @author 269701-foss-lln
	* @update 2016年4月24日 下午7:44:24
	* @version V1.0
	 */
	@Override
	public List<SerialEntity> saveSerialNo(List<SerialEntity> serialList) {
		//1. 参数为空
		/*if(serialList.size()<=0){
			throw new AirWayBillException("流水信息不存在");
		}else{*/
		if(!(serialList.size()<=0)){
			//2.保存流水信息
			//修改后的流水信息
			List<SerialEntity> newSerialList=serialList;
			//合大票清单流水原有数据 根据运单号查询
			List<SerialEntity> oldSerialList=new ArrayList<SerialEntity>();
			//需要删除的流水信息集合
			List<SerialEntity> deleteSerialList=new ArrayList<SerialEntity>();
			//新增流水信息集合
			List<SerialEntity> addSerialList=new ArrayList<SerialEntity>();
			String detailId = serialList.get(0).getAirPickUpDetialId();
			//根据明细id查询明细
			AirPickupbillDetailEntity airPickupbillDetailEntity = airTransPickupBillDao.getAirPickupbillDetailInfoById(detailId);
			
			//查询原有流水信息列表
			AirTransPickupBillDto dto=new AirTransPickupBillDto();
			dto.setWaybillNo(serialList.get(0).getWaybillNo());
			//增加正单号保证为同一合大票
			dto.setAirWaybillNo(airPickupbillDetailEntity.getAirWaybillNo());
			oldSerialList=airTransPickupBillDao.findRightSerialForRight(dto);
			//新流水Map
			Map<String,SerialEntity> oldSerialMap = new HashMap<String, SerialEntity>();
			//旧流水map
			Map<String,SerialEntity> newSerialMap = new HashMap<String, SerialEntity>();

			//把newSerialList转换成map
			for(SerialEntity newSerialEntity : newSerialList){
				newSerialMap.put(newSerialEntity.getSerialNo(), newSerialEntity);
			}
			//把list转换成map
			for(SerialEntity oldSerialEntity : oldSerialList){
				//把oldList转换成map
				oldSerialMap.put(oldSerialEntity.getSerialNo(), oldSerialEntity);
				//如果新newSerialList流水里不包含oldSerialList里的合票流水，说明该流水被删除
				if(!newSerialMap.containsKey(oldSerialEntity.getSerialNo())){
					//此次合大票明细操作删除的流水
					SerialEntity deletSerialEntity=new SerialEntity();
					//流水号
					deletSerialEntity.setSerialNo(oldSerialEntity.getSerialNo());
					//运单号
					deletSerialEntity.setWaybillNo(oldSerialEntity.getWaybillNo());
					//明细id
					deletSerialEntity.setAirPickUpDetialId(detailId);
					//需要删除的流水列表
					deleteSerialList.add(deletSerialEntity);
				}
			}
			
			for(SerialEntity newSerialEntity : newSerialList){
				//如果oldList流水里不包含newList里的合票流水，说明该流水新增
				if(!oldSerialMap.containsKey(newSerialEntity.getSerialNo())){
					SerialEntity addSerialEntity=new SerialEntity();
					//运单号
					addSerialEntity.setWaybillNo(newSerialEntity.getWaybillNo());
					//流水号
					addSerialEntity.setSerialNo(newSerialEntity.getSerialNo());
					//明细id
					addSerialEntity.setAirPickUpDetialId(newSerialEntity.getAirPickUpDetialId());
					//id
					addSerialEntity.setId(UUIDUtils.getUUID());
					//中转的新增运单集合
					addSerialList.add(addSerialEntity);
				}
			}
			//需要删除流水列表不为空 进行删除流水
			if(!CollectionUtils.isEmpty(deleteSerialList)){
				airTransPickupBillDao.deleteAirPickupbillSerialist(deleteSerialList);
			}
			//需要新增的流水列表不为空 进行新增流水
			if(!CollectionUtils.isEmpty(addSerialList)){
				for(SerialEntity entity:addSerialList){
					airTransPickupBillDao.saveSerialNo(entity);
				}
			}
			
		}
		return null;
	}
	
	/**
	 * 根据idsList<String>查询合大票明细 
	 * @param idsList 明细id
	 * @return List<AirPickupbillDetailEntity> 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-31 下午3:27:11
	 */
	@Override
	public List<AirPickupbillDetailEntity> queryAirPickupbillDetailList(
			List<String> idsList) {
		return airTransPickupBillDao.queryAirPickupbillDetailList(idsList);
	}
	
	/**
	 * 获取 注入中转提货清单dao.
	 * @return the 注入中转提货清单dao
	 */
	public IAirTransPickupBillDao getAirTransPickupBillDao() {
		return airTransPickupBillDao;
	}
	
	/**
	 * 设置 注入中转提货清单dao.
	 * @param airTransPickupBillDao the new 注入中转提货清单dao
	 */
	public void setAirTransPickupBillDao(
			IAirTransPickupBillDao airTransPickupBillDao) {
		this.airTransPickupBillDao = airTransPickupBillDao;
	}
	
	/**
	 * 设置 注入中转公共服务.
	 * @param tfrCommonService the new 注入中转公共服务
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	
	/**
	 * 设置 注入变更清单service.
	 * @param airChangeInventoryService the new 注入变更清单service
	 */
	public void setAirChangeInventoryService(
			IAirChangeInventoryService airChangeInventoryService) {
		this.airChangeInventoryService = airChangeInventoryService;
	}

	/**
	 * 设置 注入变更清单service.
	 * @param airJointTicketService the new 注入变更清单service
	 */
	public void setAirJointTicketService(
			IAirJointTicketService airJointTicketService) {
		this.airJointTicketService = airJointTicketService;
	}

	/**
	 * 设置 注入导出服务.
	 * @param jointTicketAndPickupAndChangeListCallEdiService the new 注入导出服务
	 */
	public void setJointTicketAndPickupAndChangeListCallEdiService(
			IJointTicketAndPickupAndChangeListCallEdiService jointTicketAndPickupAndChangeListCallEdiService) {
		this.jointTicketAndPickupAndChangeListCallEdiService = jointTicketAndPickupAndChangeListCallEdiService;
	}

	public void setAirTransferService(IAirTransferService airTransferService) {
		this.airTransferService = airTransferService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	
	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}
	
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @param pushAirPickUpInfoService : set the property pushAirPickUpInfoService.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 下午2:32:12
	 * @version V1.0
	 */
	
	public void setPushAirPickUpInfoService(
			IPushAirPickUpInfoService pushAirPickUpInfoService) {
		this.pushAirPickUpInfoService = pushAirPickUpInfoService;
	}

	/**
	 * @param airQueryModifyPickupbillDao : set the property airQueryModifyPickupbillDao.
	 * @author 269701-foss-lln
	 * @update 2016年5月28日 下午3:10:43
	 * @version V1.0
	 */
	
	public void setAirQueryModifyPickupbillDao(
			IAirQueryModifyPickupbillDao airQueryModifyPickupbillDao) {
		this.airQueryModifyPickupbillDao = airQueryModifyPickupbillDao;
	}


}