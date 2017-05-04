/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 * 
 * 
 * 
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/AirChangeInventoryService.java
 *  
 *  FILE NAME          :AirChangeInventoryService.java
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
 1	空运操作员打开中转提货清单界面，输入【中转提货清单表头信息】；		如果先输入目的站，则到达网点自动过滤为该目的站所在城市的到达网点信息，如果先选择到达网点，则目的站为所选到达网点的所在城市；
2	空运操作员按正单号等信息查询一个或多个清单信息添加至中转提货清单的清单明细中；	航空正单信息	
3	空运操作员按运单号添加清单至中转提货清单的清单明细中；	航空正单信息	
4	空运操作员对清单明细中的清单进行单个修改操作；	运单信息	不同步修改接送货系统中的运单信息
5	点击保存，保存中转提货清单信息；	中转提货清单信息	保存中转提货清单信息，
1、	将中转提货清单中运单明细中的清单插入到该清单所属合大票清单的清单明细中，原运单到付款为0，新运单目的站为中转提货清单中的目的站，到付款为原运单到付款；
2、	如果清单为单独添加的，则在合大票清单中清单到付款为0，同时生成在合大票清单生成相同的清单，新清单目的站为中转提货清单中的目的站，到付款为原运单到付款；
3、	调用结算应收应付费用接口，传入中转提货清单数据；
6	点击导出按钮，导出中转提货清单信息；		导出时提示：是否将数据发送到EDI平台；选择是，将数据发送到EDI平台，选择否，不导入EDI平台，无论哪种选择均不影响导出到本地，模板参见界面和业务规则SR-6；

 *  
  SR-1	打开界面：
1、	制单人为当前操作人姓名，制单日期为当天；
2、	中转单号为zz+7位随机数字，不同于其他中转单号，不可编辑；

SR-2	
1、查询条件中正单号、航空公司不能为空；
2、点击查询按钮，将符合航空公司、正单号的合票清单标记为中转清单信息加入到中转提货清单的清单明细中；

SR-3	导出按钮必须在保存之后才能使用；

SR-4	添加的清单来源于制单部门为本部门的合大票信息中的清单；

SR-5	保存时校验中转单号是否唯一，如果有重复，则重新生成新的且不重复的中转单号；

SR-6	导出规则：（以下所述字段均为模板中红色字段）
1、	到达网点为中转提货清单中的到达网点名称；
2、	中转单号为中转提货清单中的中转单号；
3、	航班为中转提货清单中的航班号；
4、	航班日期为中转提货清单中的航班时间；
5、	列表中的相应字段均来自清单明细中字段信息，且列出清单明细每一行数据，合计信息为所有行的部分字段之和，求和字段为件数、毛重、计费重量、派送费、到付款、代收款；
6、	制表人为新增中转提货清单人员姓名；
7、	日期为新增中转提货清单时间；
8、	德邦物流：86769350/51/52/53为固定字样；
9、	模板中根据当前空运总调为广州空运总调则为“德 邦 物 流 中 转 清 单（广州）”，如果是深圳空运总调则为“德 邦 物 流 中 转 清 单（深圳）”； 
10、	文件名称为航班号+“-”+正单号；

SR-7	对运单的修改只限于本次中转提货清单的运单明细；

SR-8	制单部门没有在界面中显示，后台保存时自动保存制单部门（当前操作人所在部门）；

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirChangeService;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirJointTicketService;
import com.deppon.foss.module.settlement.agency.api.shared.domain.AirChangeEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirChangeInventoryDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirChangeInventoryService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IJointTicketAndPickupAndChangeListCallEdiService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirChangeInventoryDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirChangeInventoryEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirRevisebillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirChangeInventoryException;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestBatchResult;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcModifyAirChangeResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 变更清单service实现.
 * @author 099197-foss-zhoudejun
 * @date 2012-12-12 下午4:33:59
 */
public class AirChangeInventoryService implements IAirChangeInventoryService {
	//日志
	private static final Logger LOGGER = LoggerFactory.getLogger(AirChangeInventoryService.class);

	/** 注入变更清单dao. */
	private IAirChangeInventoryDao airChangeInventoryDao;
	
	/** 注入综合预收应付接口. */
	private IAirJointTicketService airJointTicketService;
	
	/** 注入edi接口*/
	private IJointTicketAndPickupAndChangeListCallEdiService jointTicketAndPickupAndChangeListCallEdiService;
	
	/**
	 * 空运代理公司服务
	 */
	private IAirAgencyCompanyService airAgencyCompanyService;
	/**
	 * 结算合票预收应付接口
	 */
	private IAirChangeService airChangeService;
	
	/** 查找空运总调 service*/
	private IAirDispatchUtilService airDispatchUtilService;
	
	/**
	 * 调用cubc常用同步接口
	 */
	private IFossToCubcService fossToCubcService;
	
	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}

	/**
	 * (空运)根据运单号查询合票明细.
	 * @param waybillNO 运单号
	 * @return the air trans pickup bill dto
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-12 下午4:40:50
	 */
	@Override
	public AirTransPickupBillDto queryChangeInventoryDetail(
			String waybillNO,String createOrgCode) {
		AirTransPickupBillDto airTransPickupBillDto = new AirTransPickupBillDto();
		List<AirChangeInventoryEntity> airChangeInventoryList = airChangeInventoryDao
				.queryChangeInventoryDetail(waybillNO,createOrgCode);
		List<AirChangeInventoryDetailEntity> airChangeInventoryDetailList = airChangeInventoryDao
				.queryAirRevisebillDetailList(waybillNO,createOrgCode);
		airTransPickupBillDto.setAirChangeInventoryList(airChangeInventoryList);
		airTransPickupBillDto.setAirChangeInventoryDetailList(airChangeInventoryDetailList);
		return airTransPickupBillDto;
	}

	/**
	 * 新增变更清单明细logger日志.
	 * @param airRevisebillDetailEntity 变更清单明细
	 * @return the int
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-12 下午7:38:38
	 */
	@Override
	public int addAirRevisebillDetailEntity(
			AirRevisebillDetailEntity airRevisebillDetailEntity) {
		return airChangeInventoryDao.addAirRevisebillDetailEntity(airRevisebillDetailEntity);
	}
	
	/**
	 * 批量新增变更清单明细logger日志.
	 *
	 * @param airRevisebillDetailList 变更清单明细list
	 * @return the int
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-13 下午1:01:35
	 */
	@Override
	public int addaddAirRevisebillDetailList(
			List<AirRevisebillDetailEntity> airRevisebillDetailList) {
		return airChangeInventoryDao.addaddAirRevisebillDetailList(airRevisebillDetailList);
	}

	/**
	 * 变更清单,修改清单logger日志.
	 * @param airPickupbillDetailList the air pickupbill detail list
	 * @param parameterMap 备注map
	 * @param stlWayBillNoMap the stl way bill no map
	 * @param delWayBillNoMap the del way bill no map
	 * @return the int
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-13 下午8:28:12
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public List<List> updateAirWaybillDetailOrLogger(
			List<AirPickupbillDetailEntity> airPickupbillDetailList,
			Map<String, String> parameterMap,Map<String,String> stlWayBillNoMap,
			Map<String,String> delWayBillNoMap) {
		List<AirRevisebillDetailEntity> airRevisebillDetailList = 
				new ArrayList<AirRevisebillDetailEntity>();
		
		//此集合为原始数据，用于新旧数据进行对比
		List<AirPickupbillDetailEntity> oldAirPickupbillDetailList = 
				new ArrayList<AirPickupbillDetailEntity>();
		
		
		//查询修改前的合票清单
		if(!stlWayBillNoMap.isEmpty()){
			oldAirPickupbillDetailList = queryOldAirPickupbillDetails(stlWayBillNoMap);
		}
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		//2013-03-06 046130-foss-xuduowei,比较变更清单修改前后的内容，提取变更项
		if(!CollectionUtils.isEmpty(airPickupbillDetailList)){
			//将parameterMap 转换成list对象方便后台操作
			for (AirPickupbillDetailEntity airPickupbillDetailEntity : airPickupbillDetailList){
				if(null==airPickupbillDetailEntity.getAirPickQty()){
					airPickupbillDetailEntity.setAirPickQty(airPickupbillDetailEntity.getGoodsQty());
				}
				for(AirPickupbillDetailEntity oldAirPickupbillDetailEntity : oldAirPickupbillDetailList){
					if(StringUtils.equals(airPickupbillDetailEntity.getId(), oldAirPickupbillDetailEntity.getId())){
						StringBuffer sb = new StringBuffer();
						String id = airPickupbillDetailEntity.getId();
						//String notes = parameterMap.get(airPickupbillDetailEntity.getId().toString());
						AirRevisebillDetailEntity airRevisebillDetailEntity = new AirRevisebillDetailEntity();
						airRevisebillDetailEntity.setAirPickupbillDetailId(id);
						airRevisebillDetailEntity.setAirTransPickupbillDetailId(id);
						airRevisebillDetailEntity.setId(UUIDUtils.getUUID());
						airRevisebillDetailEntity.setCreateTime(new Date());
						airRevisebillDetailEntity.setOperationTime(new Date());
						airRevisebillDetailEntity.setOperationOrgCode(orgAdministrativeInfoEntity.getCode());
						airRevisebillDetailEntity.setOperationOrgName(orgAdministrativeInfoEntity.getName());
						airRevisebillDetailEntity.setOperatorName(currentInfo.getEmpName());
						airRevisebillDetailEntity.setOperatorCode(currentInfo.getEmpCode());
						
						//比较新旧变更清单的差别
						//修改计费重量
						installNotes(airPickupbillDetailEntity, oldAirPickupbillDetailEntity, sb);
						int length = sb.length();
						if(length >= ConstantsNumberSonar.SONAR_NUMBER_1000){
							throw new AirChangeInventoryException("备注长度过长！");
						}
						if(length ==0){
							continue;
						}
						//保存到后台的类设置变更事项
						airRevisebillDetailEntity.setReviseContent(sb.toString());
						//保存到后台的集合
						airRevisebillDetailList.add(airRevisebillDetailEntity);		
					}else{
						//无操作
					}
				}
			}
			/**
			 * @author wqh
			 * @date 2013-08-05
			 * */
			if(!CollectionUtils.isEmpty(airRevisebillDetailList) /*airRevisebillDetailList!=null && !airRevisebillDetailList.isEmpty()*/){
				airChangeInventoryDao.addaddAirRevisebillDetailList(airRevisebillDetailList);
			}
			
		}
		
		if(!CollectionUtils.isEmpty(airPickupbillDetailList)){
			//更新合大票明细
			airChangeInventoryDao.updateAirWaybillDetail(airPickupbillDetailList);
		}
		
		//2013-03-05 046130-foss-xuduowei,变更清单修改后，同时修改中转提货清单明细
		//修改规则，只有是否中转标记的才需要修改中转提货清单
		List<AirPickupbillDetailEntity> transPickupDetailList = new ArrayList<AirPickupbillDetailEntity>();
		LOGGER.info("遍历变更清单明细，判断是否有中转");
		for(AirPickupbillDetailEntity airPickupbillDetailEntity:airPickupbillDetailList){
			String isTrans = airPickupbillDetailEntity.getBeTransfer().toString();
			//判断是否中转标记为Y
			if(StringUtils.equals(isTrans, FossConstants.YES)){
				LOGGER.info("发现中转提货清单，运单号位：" + airPickupbillDetailEntity.getWaybillNo());
				transPickupDetailList.add(airPickupbillDetailEntity);
			}
		}
		//修改中转提货清单
		if(!CollectionUtils.isEmpty(transPickupDetailList)){
			airChangeInventoryDao.updateTransPickupDetail(transPickupDetailList);
		}
		if(!CollectionUtils.isEmpty(oldAirPickupbillDetailList)&& oldAirPickupbillDetailList.size()>0){
			// 调用结算接口用，新增集合
			List<AirChangeEntity> addList = new ArrayList<AirChangeEntity>();
			// 调用结算接口用，删除集合
			List<AirChangeEntity> delList = new ArrayList<AirChangeEntity>();
			// 调用结算接口用，修改集合
			List<AirChangeEntity> modifyList = new ArrayList<AirChangeEntity>();
			//组装数据，为调用变更结算接口做准备
			assambleData(airPickupbillDetailList,oldAirPickupbillDetailList,addList,delList,modifyList);
			
		    
			List<String> modifyWayBillArrayList = new ArrayList<String>();
			for (int i = 0; i < modifyList.size(); i++) {
				modifyWayBillArrayList.add(modifyList.get(i).getWaybillNo());
			}
			if (!CollectionUtils.isEmpty(modifyWayBillArrayList)) {
				// 封装灰度实体，类型是配载单
				GrayParameterDto parDto = new GrayParameterDto();
				parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
				parDto.setSourceBillNos(
						(String[]) modifyWayBillArrayList.toArray(new String[modifyWayBillArrayList.size()]));
				VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());

				List<String> cubcModifyWayBillArrayList = new ArrayList<String>();
				List<String> fossmodifyWayBillArrayList = new ArrayList<String>();
				if (vestResponse.getVestBatchResult().size() > 0) {
					// 循环得到分流的运单号
					for (VestBatchResult vestResult : vestResponse.getVestBatchResult()) {
						if (vestResult.getVestSystemCode().equals(CUBCGrayContants.SYSTEM_CODE_FOSS)) {
							fossmodifyWayBillArrayList = vestResult.getVestObject();
						} else {
							cubcModifyWayBillArrayList = vestResult.getVestObject();
						}
					}
				}

				if (null != fossmodifyWayBillArrayList && fossmodifyWayBillArrayList.size() > 0) {
					List<AirChangeEntity> fossModifyList = new ArrayList<AirChangeEntity>();
					for (int i = 0; i < modifyList.size(); i++) {
						if (fossmodifyWayBillArrayList.contains(modifyList.get(i).getWaybillNo())) {
							fossModifyList.add(modifyList.get(i));
						}
					}

					try {
						// 如果有运单发生送货费变化则调用结算接口
						if (fossModifyList.size() > 0 || addList.size() > 0 || delList.size() > 0) {
							UserEntity user = FossUserContext.getCurrentInfo().getUser();
							// 调用结算接口
							airChangeService.modifyAirChangeDetail(addList, fossModifyList, delList,
									new CurrentInfo(user, orgAdministrativeInfoEntity));
						}
					} catch (BusinessException e) {
						throw new AirChangeInventoryException(e.getErrorCode());
					}
				}

				if (null != cubcModifyWayBillArrayList && cubcModifyWayBillArrayList.size() > 0) {

					List<AirChangeEntity> cubcModifyList = new ArrayList<AirChangeEntity>();
					for (int i = 0; i < modifyList.size(); i++) {
						if (cubcModifyWayBillArrayList.contains(modifyList.get(i).getWaybillNo())) {
							cubcModifyList.add(modifyList.get(i));
						}
					}
					if (cubcModifyList.size() > 0 || addList.size() > 0 || delList.size() > 0) {
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("addedDetails", addList);
						paramMap.put("modifiedDetails", cubcModifyList);
						paramMap.put("removedDetails", delList);
						paramMap.put("empCode", FossUserContext.getCurrentInfo().getEmpCode());
						paramMap.put("empName", FossUserContext.getCurrentInfo().getEmpName());
						paramMap.put("currentDeptName", FossUserContext.getCurrentInfo().getCurrentDeptName());
						paramMap.put("currentDeptCode", FossUserContext.getCurrentInfo().getCurrentDeptCode());

						CubcModifyAirChangeResponse cubcModifyAirChangeResponse = fossToCubcService
								.modifyAirChangeDetail(paramMap);
						if (null == cubcModifyAirChangeResponse) {
							throw new AirChangeInventoryException("调用cubc接口变更清单失败");
						} else {
							if (0 == cubcModifyAirChangeResponse.getResult()) {
								throw new AirChangeInventoryException(
										"调用cubc接口变更清单失败：" + cubcModifyAirChangeResponse.getReason());
							}
						}
					}
				}
			}
		}
		
		//此集合用于保存变更清单修改后的更新记录，返回前端便于实施刷新更新记录
		List<List> allList = new ArrayList<List>();
		//定义运单集合，用于去重
		Set<String> waybillSet = new HashSet<String>();
		//遍历对象取出运单
		for(AirPickupbillDetailEntity airPickupbillDetailEntity : airPickupbillDetailList){
			String waybillNo = airPickupbillDetailEntity.getWaybillNo();
			waybillSet.add(waybillNo);
			
		}
		//转为迭代器
		Iterator<String> iterator = waybillSet.iterator();
		//Iterator<String> iterator = activeSerialSet.iterator();
		//遍历查询
		while(iterator.hasNext()){
			//获取查询修改记录
			List<AirChangeInventoryDetailEntity> airChangeInventoryDetailList =
					 airChangeInventoryDao.queryAirRevisebillDetailList(iterator.next().toString(),currentInfo.getCurrentDeptCode());
			allList.add(airChangeInventoryDetailList);
		}
		
		return allList;
	}
	
	/**
	 * sonar优化 wwb 311396 
	 * @param airPickupbillDetailEntity
	 * @param oldAirPickupbillDetailEntity
	 * @param sb
	 */
	private void installNotes(
			AirPickupbillDetailEntity airPickupbillDetailEntity,
			AirPickupbillDetailEntity oldAirPickupbillDetailEntity,
			StringBuffer sb) {
		if(!airPickupbillDetailEntity.getBillingWeight().equals(oldAirPickupbillDetailEntity.getBillingWeight())){
			sb.append("计费重量：" + airPickupbillDetailEntity.getBillingWeight());
			sb.append("；");
		}
		//修改送货费
		if(!airPickupbillDetailEntity.getDeliverFee().equals(oldAirPickupbillDetailEntity.getDeliverFee())){
			sb.append("送货费：" + airPickupbillDetailEntity.getDeliverFee());
			sb.append("；");
		}
		//修改件数
		if(!airPickupbillDetailEntity.getGoodsQty().equals((Integer)oldAirPickupbillDetailEntity.getGoodsQty())){
			sb.append("件数：" + airPickupbillDetailEntity.getGoodsQty());
			sb.append("；");
		}
		////修改收货人
		if(!StringUtils.equals(airPickupbillDetailEntity.getReceiverName(), oldAirPickupbillDetailEntity.getReceiverName())){
			if(!airPickupbillDetailEntity.getReceiverName().equals("") || oldAirPickupbillDetailEntity.getReceiverName() != null){
				sb.append("收货人：" + airPickupbillDetailEntity.getReceiverName());
				sb.append("；");
			}
			
		}
		//修改收货人电话
		if(!StringUtils.equals(airPickupbillDetailEntity.getReceiverContactPhone(), oldAirPickupbillDetailEntity.getReceiverContactPhone())){
			if(!airPickupbillDetailEntity.getReceiverContactPhone().equals("")|| oldAirPickupbillDetailEntity.getReceiverContactPhone() != null){
				sb.append("收货人电话：" + airPickupbillDetailEntity.getReceiverContactPhone());
				sb.append("；");
			}
			
		}
		//修改收货人地址
		if(!StringUtils.equals(airPickupbillDetailEntity.getReceiverAddress(), oldAirPickupbillDetailEntity.getReceiverAddress())){
			if(!airPickupbillDetailEntity.getReceiverAddress().equals("")|| oldAirPickupbillDetailEntity.getReceiverAddress() != null){
				sb.append("收货人地址：" + airPickupbillDetailEntity.getReceiverAddress());
				sb.append("；");
			}
			
		}
		//修改备注
		if(!StringUtils.equals(airPickupbillDetailEntity.getNotes(), oldAirPickupbillDetailEntity.getNotes())){
			if(!airPickupbillDetailEntity.getNotes().equals("") || oldAirPickupbillDetailEntity.getNotes()!=null){
				sb.append("备注：" + airPickupbillDetailEntity.getNotes());
				sb.append("；");
			}
		}
	}
	
	/**
	 *@param airPickupbillDetailList  修改后的变更list
	 *@param oldAirPickupbillDetailList  修改前的变更list
	 *@param addList 调用结算用新增list
	 *@param delList 调用结算用删除list
	 *@param modifyList 调用结算用修改list
	 */
	private void assambleData(
			List<AirPickupbillDetailEntity> airPickupbillDetailList,
			List<AirPickupbillDetailEntity> oldAirPickupbillDetailList,
			List<AirChangeEntity> addList, List<AirChangeEntity> delList,
			List<AirChangeEntity> modifyList) {
		if(!CollectionUtils.isEmpty(airPickupbillDetailList)){
			for (AirPickupbillDetailEntity airPickupbillDetailEntity : airPickupbillDetailList){
				for(AirPickupbillDetailEntity oldAirPickupbillDetailEntity : oldAirPickupbillDetailList){
					if(StringUtils.equals(airPickupbillDetailEntity.getId(), oldAirPickupbillDetailEntity.getId())){
						AirChangeEntity airChangeEntity = new AirChangeEntity();
						// 通过空运代理网点，查找空运代理公司
						BusinessPartnerEntity partner = airAgencyCompanyService.queryBusinessPartnerByAgencyDeptCode(airPickupbillDetailEntity.getAgentCode());
						if (partner == null) {
							throw new SettlementException("空运网点编码" + airPickupbillDetailEntity.getAgentCode() + "找不到空运公司信息");
						}
						airChangeEntity.setWaybillNo(airPickupbillDetailEntity.getWaybillNo());
						airChangeEntity.setAgentCompanyCode(partner.getAgentCompanyCode());
						airChangeEntity.setAgentCompanyName(partner.getAgentCompanyName());
						airChangeEntity.setCreateTime(airPickupbillDetailEntity.getCreateTime());
						airChangeEntity.setDeliverFee(airPickupbillDetailEntity.getDeliverFee());
						airChangeEntity.setDestOrgCode(airPickupbillDetailEntity.getArrvRegionCode());
						airChangeEntity.setDestOrgName(airPickupbillDetailEntity.getArrvRegionName());
						airChangeEntity.setType(airPickupbillDetailEntity.getBillType());
						//modify 2013-07-02  结算组提出改造接口,把新增的运单放到addlist中,删除的放到deletelist中,金额发生变化的放到modifylist中
						/**
						 * （1）新增变更清单时，送货费为0，修改为大于0，调用结算修改接口需要把运单号集合放在addedDetails里面
						 * （2）新增变更清单时，送货费大于0，修改为0或空，调用结算修改接口需要把运单号放到removedDetails里面
						*/
//						if(oldAirPickupbillDetailEntity.getDeliverFee().doubleValue()<=0 && airPickupbillDetailEntity.getDeliverFee().doubleValue()>0){
//							addList.add(airChangeEntity);
//						}else if(oldAirPickupbillDetailEntity.getDeliverFee().doubleValue()>0 && airPickupbillDetailEntity.getDeliverFee().doubleValue()<=0){
//							delList.add(airChangeEntity);
//						}else{
//							modifyList.add(airChangeEntity);
//						}
						if(oldAirPickupbillDetailEntity.getDeliverFee().compareTo(airPickupbillDetailEntity.getDeliverFee()) != 0){
							modifyList.add(airChangeEntity);
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * 返回合票清单.
	 * @param airPickupbillId 合大票清单主键id
	 * @return AirPickupbillEntity 合大票清单实体
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-14 下午5:42:25
	 */
	@SuppressWarnings("unused")
	private AirPickupbillEntity queryOldAirPickupbillEntity(String airPickupbillId){
		return airChangeInventoryDao.queryOldAirPickupbillEntity(airPickupbillId);
	}
	
	/**
	 * 返回新增的合票清单明细.
	 * @return List<AirPickupbillDetailEntity> 合大票明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-14 下午5:42:14
	 */
	@SuppressWarnings("unused")
	private List<AirPickupbillDetailEntity> addAirPickupbillDetails() {
		return null;
	}
	
	/**
	 * 根据合票明细id查询未修改的明细.
	 * @param stlWayBillNoMap the stl way bill no map
	 * @return List<AirPickupbillDetailEntity> queryOldAirPickupbillDetails 返回合票明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-15 下午2:08:57
	 */
	private List<AirPickupbillDetailEntity> queryOldAirPickupbillDetails(Map<String,String> stlWayBillNoMap){
		List<String> arrayListIds = new ArrayList<String>(stlWayBillNoMap.keySet());
		return airChangeInventoryDao.queryOldAirPickupbillDetails(arrayListIds);
	}
	
	/**
	 * 调用结算预收应付接口
	 * AirPickupbillEntity oldMaster, AirPickupbillEntity newMaster,
	 * List<AirPickupbillDetailEntity> addedDetails,
	 * List<AirPickupbillDetailEntity> modifiedDetails,
	 * List<String> removedDetails, CurrentInfo currentInfo.
	 * @param oldMaster the old master
	 * @param newMaster the new master
	 * @param addedDetails the added details
	 * @param modifiedDetails the modified details
	 * @param removedDetails the removed details
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-14 下午5:42:01
	 */
	@SuppressWarnings("unused")
	private void invoStlAdvanceDealWithFee(AirPickupbillEntity oldMaster, AirPickupbillEntity newMaster,
			List<AirPickupbillDetailEntity> addedDetails, List<AirPickupbillDetailEntity> modifiedDetails,
			List<String> removedDetails){
		UserEntity user = FossUserContext.getCurrentInfo().getUser();
  		//根据当前部门取空运总调
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(deptCode);
		
		airJointTicketService.modifyAirJointTicketDetail(oldMaster, newMaster, addedDetails, modifiedDetails, 
					removedDetails, new CurrentInfo(user,orgAdministrativeInfoEntity) );
	}

	/**
	 * 上传变更清单给EDI 
	 * @param airWaybillNo 正单号
	 * @return InputStream 返回(明细)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-19 下午2:36:34
	 */
	@Override
	public InputStream uploadChangeListCallEdi(List<String> idsList ,String callIsNotEdiFlag,String airWaybillNo) {
		InputStream uploadChangeListCallInputStream = null;
		try {
			uploadChangeListCallInputStream = jointTicketAndPickupAndChangeListCallEdiService.uploadChangeListCallEdi(idsList, callIsNotEdiFlag, airWaybillNo);
		} catch (BusinessException e) {
			throw new AirChangeInventoryException(e.getErrorCode());
		}
		return uploadChangeListCallInputStream;
	}

	/**
	 * 修改清单logger日志.
	 * @param airRevisebillDetailList 变更日志
	 * @return the int
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 下午7:24:18
	 */
	public int updateAirRevisebillDetailLogger (List<AirRevisebillDetailEntity> airRevisebillDetailList){
		return airChangeInventoryDao.updateAirRevisebillDetailLogger(airRevisebillDetailList);
	}
	
	/**
	 * 设置 注入变更清单dao.
	 * @param airChangeInventoryDao the new 注入变更清单dao
	 */
	public void setAirChangeInventoryDao(
			IAirChangeInventoryDao airChangeInventoryDao) {
		this.airChangeInventoryDao = airChangeInventoryDao;
	}

	/**
	 * 设置 注入综合预收应付接口.
	 * @param airJointTicketService the new 注入综合预收应付接口
	 */
	public void setAirJointTicketService(
			IAirJointTicketService airJointTicketService) {
		this.airJointTicketService = airJointTicketService;
	}

	public void setJointTicketAndPickupAndChangeListCallEdiService(
			IJointTicketAndPickupAndChangeListCallEdiService jointTicketAndPickupAndChangeListCallEdiService) {
		this.jointTicketAndPickupAndChangeListCallEdiService = jointTicketAndPickupAndChangeListCallEdiService;
	}
	
	/**
	 * 设置 变更结算service.
	 * @param airChangeService service
	 */
	public void setAirChangeService(
			IAirChangeService airChangeService) {
		this.airChangeService = airChangeService;
	}
	
	/**
	 * @param airAgencyCompanyService
	 */
	public void setAirAgencyCompanyService(
			IAirAgencyCompanyService airAgencyCompanyService) {
		this.airAgencyCompanyService = airAgencyCompanyService;
	}

	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}
	
	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}
}