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
 *  PROJECT NAME  : tfr-package
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/server/service/impl/PDAPackagingService.java
 *  
 *  FILE NAME          :PDAPackagingService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-package
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.server.service.impl
 * FILE    NAME: PDAPackagingService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.packaging.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPackBIService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.NeedCreateTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPDAPackagingDao;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPackOutDao;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackCommonService;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackageMainPriceService;
import com.deppon.foss.module.transfer.packaging.api.server.service.IQueryPackedService;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants.packing;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireDetailsEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackedPersonEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryWaybillPackEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.SerialRelationEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.WaybillPackEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.ValidatePackedDto;
import com.deppon.foss.module.transfer.packaging.api.shared.exception.PackagingException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAPackagingService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAPackagingInfoEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryPDAUnpackConEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryPDAUnpackResEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.FinishPackResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SerialNoAreaDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SerialNoStatusDto;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * PDA包装录入service实现类
 * 
 * SUC-643查询代包装信息（PDA接口）
 * 
 * 步骤  
 * 		外场代包装人员使用PDA登陆查询本外场已入库的代包装货物；
 * 
 *		FOSS根据PDA传入的“开单时间”、
 *
 *		“货物状态”、
 *
 *		“代包装部门”、
 *
 *		“营业部”、
 *
 *		“运单号”查询条件、返回查询结果；
 *
 *		FOSS返回待包装货物信息给PDA参见1.8.2【FOSS响应参数】；
 *
 * 业务规则
 *      查询本外场已录入系统且待包装信息的清单。
 *      
 *		显示待包装货物时，按货物时效优先级排序：
 *
 *		发车时间、空运、城际、卡货、普货，优先级依次递减，
 *
 *		排序在FOSS中定义并返回排好序的货物信息，
 *
 *		PDA直接显示。
 *
 *SUC-642完成包装（PDA接口）
 *	步骤
 *		PDA请求FOSS需提供，
 *
 *		“运单号”、 
 *
 *		“旧标签”、
 *
 *		“包装类型”、
 *
 *		“登录人编号”、 
 *
 *		“PDA编号”、
 *
 *		“包装体积”，
 *
 *		“加托个数”，
 *
 *		“备注”，
 *
 *		“包装人”；
 *
 *		FOSS保存完成包装数据；
 *
 *		FOSS响应PDA请求；
 *
 *  业务规则
 *  
 *  	木工依据营业部对代包装货物的特定编号和对代包装货物标识的货物合打一件；
 *  
 *		木工录入包装货物的实际件数和实际体积，
 *
 *		并打印新标签粘贴，
 *
 *		新旧标签的库存状态在FOSS端根据完成包装时传入的参数完成；
 *
 *		木工在系统中根据运单号查询货物，
 *	
 *		确认货物已完成包装，
 *
 *		标记货物由未包装状态改为包装完成状态，
 *
 *		货物包装信息自动默认为木包装;
 *
 *
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-11-3 上午9:31:36
 * 
 * 
 * 
 * 
 */
public class PDAPackagingService implements IPDAPackagingService {
	
	/**
	 * PDA与PC端公共service
	 * */
	private IPackCommonService packCommonService;
	/**
	 * 接送货修改运单件数接口
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 *  接送货新增、修改流水号接口
	 */
	private ILabeledGoodService labeledGoodService;
	/**
	 *  PDA包装录入DAO接口
	 */
	private IPDAPackagingDao pdaPackagingDao;
	/**
	 *  DAO接口
	 */
	private IPackOutDao packOutDao;
	/**
	 *  PC包装录入SERVICE接口
	 */
	private IQueryPackedService queryPackedService;
	/**
	 * 走货路径接口
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	/**
	 *  职员信息接口
	 */
	private IEmployeeService employeeService;
	/**
	 *  PC包装录入DAO
	 */
	private IQueryPackedDao queryPackedDao;
	/**
	 *  获取指定部门接口
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 *  库存接口
	 */
	private IStockService stockService;
	/**
	 * 货区接口
	 */
	private IGoodsAreaService goodsAreaService;
	
	/**
	 * 业务锁service
	 */
	private IBusinessLockService businessLockService;

	//配合BI修改运单件数后续调接送接口修改modifytime
	private IWaybillPackBIService waybillPackBIService;
	private IPackageMainPriceService packageMainPriceService;
	/**
	 
	 * pda查询营业部待包装货物
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-3 上午9:31:36
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAPackagingService#queryPDAUnpackResult(java.lang.String)
	 */
	@Override
	public List<QueryPDAUnpackResEntity> queryPDAUnpackResult(String packDept) {
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//原始组织信息
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoByCode(packDept,
						bizTypes);
		if(org == null){
			throw new PackagingException("当前直属部门无外场部门");
		}
		//新曾查询条件实体
		QueryPDAUnpackConEntity queryPDAUnpackConEntity = new QueryPDAUnpackConEntity();
		//设置包装部门
		queryPDAUnpackConEntity.setPackDept(org.getCode());
		
		/**
		 * 设置当前部门编码，货区编码
		 */
		// 查找该包装部门的打木架货区
		List<GoodsAreaEntity> areaList = goodsAreaService
				.queryGoodsAreaListByType(org.getCode(),
						DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		//判断当前部门是否有打木架区
		if (areaList == null || areaList.isEmpty()) {
			//抛出异常
			throw new PackagingException("系统中当前部门无打木架货区");
		}
		//获取包装货区
		GoodsAreaEntity goodsArea = areaList.get(0);
		//判断包装货区是否为空
		if (goodsArea == null) {
			//抛出异常
			throw new PackagingException("系统中当前部门无打木架货区");
		}
		// 设置外场打木架货区
		queryPDAUnpackConEntity.setAreaCode(goodsArea.getGoodsAreaCode());
		//新装PDA包装信息集合
		List<QueryPDAUnpackResEntity> pdaUnpackList;
		//调用DAO，执行查询操作
		pdaUnpackList = pdaPackagingDao
				.queryPDAUnpackResult(queryPDAUnpackConEntity);
		//变量查询结果，根据运单号查询每件货物的状态
		for(int i = 0;i < pdaUnpackList.size();i++){
			List<SerialNoAreaDto> sna = pdaUnpackList.get(i).getSerialNoAreaDto();
			int packStockNum = 0;
			//待变量后设为返回结果的变量
			List<SerialNoStatusDto> statusList = new ArrayList<SerialNoStatusDto>();
			for(SerialNoAreaDto serialNoAreaDto : sna){
				SerialNoStatusDto statusDto = new SerialNoStatusDto();
				statusDto.setSerialNo(serialNoAreaDto.getSerialNo());
			statusDto.setPackageType(packing.getName(serialNoAreaDto.getPackageType()));
				
				if(StringUtils.equals(serialNoAreaDto.getOrgCode(), org.getCode())&&
						StringUtils.equals(serialNoAreaDto.getIsPacked(), FossConstants.NO)){
					if(StringUtils.equals(goodsArea.getGoodsAreaCode(), serialNoAreaDto.getAreaCode())){
						//包装区库存
						statusDto.setStatus(TransferPDADictConstants.PACKAGING_SERIAL_WOOD_STOCK);
						packStockNum = packStockNum +1;
					}else{//在部门库存中
						//部门库存
						statusDto.setStatus(TransferPDADictConstants.PACKAGING_SERIAL_ORG_STOCK);
					}
					statusList.add(statusDto);
				}else{
					//无操作
				}
			}
			//加到返回集合中
			pdaUnpackList.get(i).setPackStockNum(packStockNum + "");
			pdaUnpackList.get(i).setSerialNoStatusDto(statusList);
			/*
			//运单号
			String waybillNo = pdaUnpackList.get(i).getWayBillNumber();
			Map<String,Object> map = new HashMap<String,Object>();
			//查询条件，运单号
			map.put("waybillNo", waybillNo);
			//查询条件，
			//只需显示未包装的货物状态
			map.put("isPacked", FossConstants.NO);
			//查询每件货物状态
			List<SerialNoAreaDto> areaDto = pdaPackagingDao.querySerialArea(map);
			//创建货物状态集合，
			//待变量后设为返回结果的变量
			List<SerialNoStatusDto> statusList = new ArrayList<SerialNoStatusDto>();
			for(int j = 0;j<areaDto.size();j++){
				//货件状态类
				SerialNoStatusDto statusDto = new SerialNoStatusDto();
				//流水号，
				String serialNo = areaDto.get(j).getSerialNo();
				//设置到状态DTO中
				statusDto.setSerialNo(serialNo);
				//货物所在部门编码				
				String orgCode = areaDto.get(j).getOrgCode();
				//货物所在货区编码
				String areaCode = areaDto.get(j).getAreaCode();	
				//首先判断货物所在部门与PDA查询条件的部门是否相同，如果不同，则表明货物不在本部门，因此状态为空
				if(StringUtils.equals(orgCode, org.getCode())){
					//在包装区库存中
					if(StringUtils.equals(areaCode, goodsArea.getGoodsAreaCode())){
						//包装区库存
						statusDto.setStatus(TransferPDADictConstants.PACKAGING_SERIAL_WOOD_STOCK);
					}else{//在部门库存中
						//部门库存
						statusDto.setStatus(TransferPDADictConstants.PACKAGING_SERIAL_ORG_STOCK);
					}
				}else{
					//无操作，状态为空
				}
				//加到集合中
				statusList.add(statusDto);
				
			}
			//加到返回集合中
			pdaUnpackList.get(i).setSerialNoStatusDto(statusList);
			
		*/}
		//返回
		return pdaUnpackList;
		
	}

	/**
	 * PDA包装录入信息
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-3 上午9:31:36
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAPackagingService#addPackagingInfo(com.deppon.foss.module.transfer.pda.api.shared.domain.PDAPackagingInfoEntity)
	 */
	@Transactional
	@Override
	public FinishPackResultDto addPackagingInfo(
			PDAPackagingInfoEntity pdaPackagingInfoEntity) {
		// 首先判断包装录入对象是否为空
		if (pdaPackagingInfoEntity == null) {
			//抛出异常
			throw new PackagingException("传入的包装信息为空");
		}
		//获取包装材料--->根据包装材料获取包装类型
		if(packing.MAKE_WOODEN_STOCK.getValue().equals(pdaPackagingInfoEntity.getPackedMate())){
			pdaPackagingInfoEntity.setPackageType(packing.MAKE_WOODEN_STOCK.getValue());
			pdaPackagingInfoEntity.setUnPackageType(packing.WOODEN_FRAME.getValue());
		}else{
			pdaPackagingInfoEntity.setPackageType(packing.WOODEN_FRAME.getValue());
			pdaPackagingInfoEntity.setUnPackageType(packing.MAKE_WOODEN_STOCK.getValue());
		}
		
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//原始组织信息
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoByCode(pdaPackagingInfoEntity.getOrgCode(),
						bizTypes);
		if(org == null){
			throw new PackagingException("当前直属部门无外场部门");
		}
		pdaPackagingInfoEntity.setOrgCode(org.getCode());
		//根据运单号查询运单基本信息
		WaybillEntity waybillEntity=waybillManagerService.queryWaybillBasicByNo(pdaPackagingInfoEntity.getWayBillNumber());
		if(waybillEntity==null)
		{
			throw new TfrBusinessException("运单不存在");
		}
		/**
		 * @author 105795-foss中转-wqh
		 * @desc  打木托个数限制校验
		 * @date  2014-08-19 
		 * */
		//校验加托个数
		ValidatePackedDto validatePackeddto=new ValidatePackedDto();
		//运单号
		validatePackeddto.setWaybillNo(waybillEntity.getWaybillNo());
		//加托个数
		if(pdaPackagingInfoEntity.getPlusNum()==null){
			validatePackeddto.setMaskQty(0);
		}else{
			validatePackeddto.setMaskQty(pdaPackagingInfoEntity.getPlusNum());
		}
		//调校验方法
		packCommonService.checkWoodMask(waybillEntity, validatePackeddto);
		
		
		//包装完成返回对象
		FinishPackResultDto resultDto = new FinishPackResultDto();
		
		// 再判断主信息是否已录入
		String packedId = getPackedId(pdaPackagingInfoEntity);
		// 如果未包装，则需要执行包装主信息录入操作
		if (StringUtils.isEmpty(packedId)) {
			/**
			 * 包装信息录入
			 */
			packedId = savePackage(pdaPackagingInfoEntity);
		}

		/**
		 * 需要录入包装人员信息
		 */
		List<String> empList = pdaPackagingInfoEntity.getEmpCode();
		//判断包装人员信息是否存在
		if(empList != null && empList.size() > 0){
			//调用接口进行分析保存
			savepackedPerson(empList,packedId);
		}
		
		/**
		 * 判断录入的流水号是否已包装
		 * （不需要包装或者已包装都要抛异常）
		 */
		//流水号集合
		List<String> serialList = pdaPackagingInfoEntity.getSerialNo();
		//运单号
		String waybillNo = pdaPackagingInfoEntity.getWayBillNumber();
		//部门
		String packDept = org.getCode();
		
		//新增MAP对象
		//封装查询条件
		Map<String,Object> map = new HashMap<String,Object>(PackagingConstants.SONAR_NUMBER_4);
		//运单号
		map.put("waybillNo", waybillNo);
		//是否已包装
		//FossConstants.NO 未包装
		//map.put("isPacked", FossConstants.NO);
		map.put("packDept", packDept);
		//查询符合条件个数
		List<SerialNoAreaDto> areaList = pdaPackagingDao.querySerialArea(map);
		if(areaList == null || areaList.isEmpty()){
			throw new PackagingException("该运单需要包装的流水号都不在当前部门库存" );
		}
		
		//遍历流水号
		//如果流水号不在包装需求明细或者状态已包装
		int serialTemp = 0;
		for(int i = 0;i<serialList.size();i++){
			//流水号
			String serialNo = serialList.get(i).toString();
			//是否存在
			boolean isExist = false;
			//是否已包装
			String isPacked = "";
			//包装部门
			String deptCode = "";
			/*for(SerialNoAreaDto st : areaList){
				if(StringUtils.equals(serialNo, st.getSerialNo())){
					isPacked = st.getIsPacked();
					deptCode = st.getOrgCode();
					isExist = true;
				}								
			}*/
			
			for(int j = 0 ; j < areaList.size();j++){
				if(StringUtils.equals(serialNo, areaList.get(j).getSerialNo())
						&& pdaPackagingInfoEntity.getPackageType().equals(areaList.get(j).getPackageType())){
					isPacked = areaList.get(j).getIsPacked();
					deptCode = areaList.get(j).getOrgCode();
					isExist = true;
				}
				//判定当前的要打的流水号是不是全部都还有其他的包装需求
				if(StringUtils.equals(serialNo, areaList.get(j).getSerialNo())
						&& pdaPackagingInfoEntity.getUnPackageType().equals(areaList.get(j).getPackageType())){
					serialTemp++;
			}
			
			}
			//等于0，表示此流水号不符合条件
			if(!isExist){
				throw new PackagingException("流水号：" + serialNo + "不存在当前包装材料的包装需求!" );
			}else{
				if(StringUtils.equals(isPacked, FossConstants.YES)){
					throw new PackagingException("流水号：" + serialNo + "已打"+packing.getName(pdaPackagingInfoEntity.getPackageType()));
				}
				if(!StringUtils.equals(deptCode, packDept)){
					throw new PackagingException("流水号：" + serialNo + "不在当前部门库存中" );
				}
			}
		}
		// 录入流水号关系信息
		if (StringUtils.isEmpty(packedId)) {
			//抛出异常，异常原因还未想好
			throw new PackagingException("");
		} else {
			//新流水号
			String newSerialNo = saveSerialRelation(pdaPackagingInfoEntity,
					packedId, waybillEntity);
			//修改包装状态
			queryPackedDao.updatePacakageRequirePackedStatus(waybillNo);
			//单独包装一个流水号的时候
			if(serialTemp != 0 && serialTemp != serialList.size()){
 	            throw new PackagingException("包装信息存在错误");
			}else if(serialTemp == serialList.size() &&
					serialList.size() == 1){
				map.clear();
				map.put("actualPackageId", packedId);
				map.put("waybillNo", pdaPackagingInfoEntity.getWayBillNumber());
				map.put("oldSerialNo", serialList.get(0).toString()); 
				map.put("packageType", pdaPackagingInfoEntity.getUnPackageType());	
				queryPackedDao.updateSerialdetail(map);
			//合并包装流水号的时候
			}else if(serialTemp == serialList.size() &&
					serialList.size() > 1){
				
				PackagingRequireEntity pre =packOutDao.queryPackagingRequireWithId(pdaPackagingInfoEntity.getWayBillNumber().trim());
				List<PackagingRequireDetailsEntity> addPackageRequireSerialRelationList = new ArrayList<PackagingRequireDetailsEntity>(1);

				PackagingRequireDetailsEntity prd=new PackagingRequireDetailsEntity();
	            	
	            prd.setId(UUIDUtils.getUUID());
				//运单号
				prd.setWaybillNo(pdaPackagingInfoEntity.getWayBillNumber());
				//流水号
				prd.setSerialNo(newSerialNo);
				//包装需求主表id
				prd.setRequireId(pre.getId());
				//开单时间
				prd.setWaybillCreateDate(pre.getWaybillCreateDate());
				//默认为未包装
				prd.setIsPacked(FossConstants.NO);
				//设置包装需求类型
	            prd.setPackageType(pdaPackagingInfoEntity.getUnPackageType());
	            	//获取实际包装主表ID
            	prd.setActualPackageId(packedId);
            	addPackageRequireSerialRelationList.add(prd);
				
				//生成新的包装需求
				packOutDao.addPackagingRequireDetails(addPackageRequireSerialRelationList);
				
				//作废原有的包装需求
				for(String serial : serialList){
					map.clear();
					map.put("active", "N");
					map.put("waybillNo", pdaPackagingInfoEntity.getWayBillNumber());
					map.put("oldSerialNo", serial); 
					map.put("packageType", pdaPackagingInfoEntity.getUnPackageType());	
					queryPackedDao.updateSerialdetail(map);
				}
				
			}
			//未全打包装生成代办
			unpackByAllBringTodo(pdaPackagingInfoEntity,waybillEntity);
			
			/**
			 * @desc 增加主要包装金额——不明觉历
			 * @date 2014-6-26 上午8:33:30
			 */
			packageMainPriceService.addPackageMainPrice(pdaPackagingInfoEntity);
			
			//将新流水号设置到返回对象中
			resultDto.setNewSerialNo(newSerialNo);
			//返回成功
			resultDto.setSuccess(true);
			//返回
			return resultDto;
		}
		
	}
	/**
	 * 
	 * 保存包装录入主信息
	 * 
	 * @param pdaPackagingInfoEntity
	 *            PDA包装录入信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-3 下午2:58:38
	 */
	private String savePackage(PDAPackagingInfoEntity pdaPackagingInfoEntity) {
		// 获取已录入的包装需求信息，
		//如果没有则表明该运单没有要求打包装
		List<WaybillPackEntity> waybillPackList = queryPackedDao
				.queryUnWaybillPack(pdaPackagingInfoEntity.getWayBillNumber());
		//表明该运单没有要求打木架
		if (waybillPackList == null || waybillPackList.isEmpty()) {
			throw new PackagingException("该运单没有要求打木架");
		} else {
			WaybillPackEntity waybillPackEntity = (WaybillPackEntity) waybillPackList
					.get(0);
			// 当前操作部门
			List<String> bizTypes = new ArrayList<String>();
			// 设置外场类型
			bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			// 获取外场组织
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoByCode(
							pdaPackagingInfoEntity.getOrgCode(), bizTypes);
			EmployeeEntity employee = employeeService
					.queryEmployeeByEmpCode(pdaPackagingInfoEntity
							.getOperatorCode());
			if (employee == null) {
				throw new PackagingException("请校验创建人工号是否存在");
			}
			String userName = employee.getEmpName();
			waybillPackEntity.setOrgName(org.getName());
			//PDA
			waybillPackEntity.setIsPDA(FossConstants.YES);
			// 备注
			waybillPackEntity.setRemark(pdaPackagingInfoEntity.getRemark());
			// 加托个数
			waybillPackEntity.setPlusNum(pdaPackagingInfoEntity.getPlusNum());
			// 包装体积
			waybillPackEntity.setPackedVolume(pdaPackagingInfoEntity
					.getPackedVolume());
			// 包装材料
			waybillPackEntity.setPackedMate(pdaPackagingInfoEntity
					.getPackedMate());
			// 包装部门编码
			waybillPackEntity.setOrgCode(pdaPackagingInfoEntity.getOrgCode());
			// 包装体积
			waybillPackEntity.setPackedVolume(pdaPackagingInfoEntity
					.getPackedVolume());
			// 创建人
			waybillPackEntity.setCreateUser(pdaPackagingInfoEntity
					.getOperatorCode());
			// 创建人姓名
			waybillPackEntity.setCreateUserName(userName);
			// 创建时间
			waybillPackEntity.setCreateDate(new Date());
			// 最后修改人
			waybillPackEntity.setModifyUser(pdaPackagingInfoEntity
					.getOperatorCode());
			// 最后修改人姓名
			waybillPackEntity.setModifyUserName(userName);
			// 修改时间
			waybillPackEntity.setModifyDate(new Date());
			// id
			waybillPackEntity.setId(UUIDUtils.getUUID());
			//保存包装类型
			waybillPackEntity.setPackageType(pdaPackagingInfoEntity.getPackageType());
			// 调用PC端保存包装信息的DAO进行保存
			queryPackedDao.insertWaybillPackEntity(waybillPackEntity);

			return waybillPackEntity.getId();
		}

	}
	
	/**
	 * 保存包装人
	 * @param empList
	 * @param packedId
	 * @author 046130-foss-xuduowei
	 * @date 2013-02-28 上午9:56:10
	 * @return
	 */
	private int savepackedPerson(List<String> empList,String packedId){
		//新增包装人集合
		List<PackedPersonEntity> packedPersonList = new ArrayList<PackedPersonEntity>();
		//系统中已
		//存在的包装人
	    //信息
		List<PackedPersonEntity> packedList = queryPackedDao.queryPackedPerson(packedId);
		//包装人个数
		int length = packedList.size();
		//已存在的则不进行再次录入
		if(length != 0){
			//循环除去已录入的包装人工号
			for(int j = 0 ;j < length;j++){
				//工号
				String empCode = packedList.get(j).getEmpCode();
				//移除已录入的包装人工号
				empList.remove(empCode);
			}
		}
		//对包装人员进行设值
		for(int i = 0;i<empList.size();i++){
			//包装人工号
			String empCode = empList.get(i).toString();
			//新增包装人对象
			PackedPersonEntity packedPersonEntity = new PackedPersonEntity();
			//获取包装人的员工信息
			EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(empCode);
			//员工信息对象是否为空
			if(employee!=null){
				//设置工号
				packedPersonEntity.setEmpCode(empCode);
				//设置姓名
				packedPersonEntity.setEmpName(employee.getEmpName());
				//外键
				packedPersonEntity.setPackedId(packedId);
				//主键
				packedPersonEntity.setId(UUIDUtils.getUUID());
				//创建日期
				packedPersonEntity.setCreateDate(new Date());
				//加入到集合中
				packedPersonList.add(packedPersonEntity);
			}else{
				//不存在时抛出异常
				throw new PackagingException("系统不存在工号为" + empCode + "的人员");
			}		
		}	
		if(packedPersonList.size() > 0){
			//调用接口进行保存
			queryPackedDao.insertPackedPersonList(packedPersonList);
		}	
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 保存新旧流水号关系
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-3 上午9:56:10
	 */
	private String saveSerialRelation(
			PDAPackagingInfoEntity pdaPackagingInfoEntity, String packedId,WaybillEntity waybillEntity) {
		//运单号
		String waybillNo = pdaPackagingInfoEntity.getWayBillNumber().trim();
		//部门编码
		String orgCode = pdaPackagingInfoEntity.getOrgCode();
		// 得到流水号集合
		List<String> serialNoList = pdaPackagingInfoEntity.getSerialNo();
		// new一个封装需要
		//保存新旧流水号关系的集合
		List<SerialRelationEntity> addSerialRelation = new ArrayList<SerialRelationEntity>();
		// 变更货签信息状态为作废状态
		List<String> diableSerialList = new ArrayList<String>();
		// 入库流水号列表
		Set<String> inStockSerialRelationSet = new HashSet<String>();
		// 出库流水号列表
		Set<String> outStockSerialRelationSet = new HashSet<String>();

		// 新流水号
		String newSerialNo = "";
		// 单个流水号包装的
		if (serialNoList.size() == 1) {
			//新增一个流水号关系的集合
			SerialRelationEntity sre = new SerialRelationEntity();
			//旧流水号
			String oldSerialNo = serialNoList.get(0).toString().trim();
			// 运单号
			sre.setWaybillNo(waybillNo);
			// 新流水号
			sre.setNewSerialNo(oldSerialNo);
			// 原流水号
			sre.setOldSerialNo(oldSerialNo);
			// 创建日期
			sre.setCreateDate(new Date());
			// id
			sre.setId(UUIDUtils.getUUID());
			// 关联ID
			sre.setPackedId(packedId);
			// 添加包装类型
			sre.setPackageType(pdaPackagingInfoEntity.getPackageType());
			// 加入到集合中
			addSerialRelation.add(sre);
			// 多个流水号合并为1个
		} else if (serialNoList.size() > 1) {
			//新流水号
			//从数据库中获取的
			newSerialNo = getNewSerial(waybillNo);
			// 新流水号要做入库操作
			inStockSerialRelationSet.add(newSerialNo);
			//遍历流水号集合
			for (int i = 0; i < serialNoList.size(); i++) {
				//新增流水号关系对象
				SerialRelationEntity sre = new SerialRelationEntity();
				//旧流水号
				String oldSerialNo = serialNoList.get(i).toString().trim();
				// 运单号
				sre.setWaybillNo(waybillNo);
				// 新流水号
				sre.setNewSerialNo(newSerialNo);
				// 原流水号
				sre.setOldSerialNo(oldSerialNo);
				// 创建日期
				sre.setCreateDate(new Date());
				// id
				sre.setId(UUIDUtils.getUUID());
				// 关联ID
				sre.setPackedId(packedId);
				// 添加包装类型
				sre.setPackageType(pdaPackagingInfoEntity.getPackageType());
				// 加入到集合中
				addSerialRelation.add(sre);
				// 只有合并的流水号才需要作废原流水号
				diableSerialList.add(oldSerialNo);
				// 同时也需要出库
				outStockSerialRelationSet.add(oldSerialNo);
			}
		} else {
			//返回空值
			return null;
		}

		// 需要插入新流水号
		if (!StringUtils.isEmpty(newSerialNo)) {
			// 运单号
			// 原流水号集合
			List<String> oldSerialNoList = pdaPackagingInfoEntity.getSerialNo();
			// 标签列表
			List<LabeledGoodDto> labeledGoodList = new ArrayList<LabeledGoodDto>();
			// 标签对象
			LabeledGoodDto labeledGoodDto = new LabeledGoodDto();
			int length = oldSerialNoList.size();
			// 关联原运单流水号
			StringBuffer labelSb = new StringBuffer();
			// 变动事项
			StringBuffer itemsSb = new StringBuffer();
			//遍历旧流水号集合
			for (int i = 0; i < length; i++) {
				//旧流水号
				String oSerialNo = oldSerialNoList.get(i);
				//追加到字符串后面
				labelSb.append(oSerialNo);
				//增加斜杠
				//在合适的未知添加
				//避免了后期做删除处理
				if (length > 1 && i != length - 1) {
					//添加斜杠
					labelSb.append(PackagingConstants.PACKAGING_LABEL_SLASH);
				}
				int a = Integer.parseInt(oSerialNo);
				//增加到变动事项
				itemsSb.append(a);
				//增加斜杠
				//在合适的未知添加
				//避免了后期做删除处理
				if (length > 1 && i != length - 1) {
					//添加斜杠
					itemsSb.append(PackagingConstants.PACKAGING_LABEL_SLASH);
				}
			}
			/**
			 * 为变更对象设置属性信息
			 */
			
			//运单号
			labeledGoodDto.setWaybillNo(waybillNo);
			//新流水号
			labeledGoodDto.setSerialNo(newSerialNo);
			//原流水号
			labeledGoodDto.setOldSerialNo(labelSb.toString());
			//拼接相关信息
			labeledGoodDto.setNumberChangItems(
			// 打木架：第
					PackagingConstants.PACKAGING_CHANGE_ITEMS_BEFORE
							+ itemsSb.toString() +
							// 件合并
							PackagingConstants.PACKAGING_CHANGE_ITEMS_BACK);

			labeledGoodList.add(labeledGoodDto);
			// 调用接送货接口插入新增的流水号
			ResultDto resultDto = labeledGoodService.insertSerialNo(waybillNo,
					labeledGoodList);
			// 获取成功标示
			String code = resultDto.getCode();
			// 判断是否失败，
			//如果失败抛出失败原因
			if (StringUtils.equals(code, PackagingConstants.PACKAGING_FAILURE)) {
				throw new PackagingException(resultDto.getMsg());
			}
		}
		/**
		 * 变更已包装合并的流水号状态为作废
		 */
		if (diableSerialList.size() > 0) {
			for (int i = 0; i < diableSerialList.size(); i++) {
				// 调用接送货接口修改流水号状态为作废
				ResultDto resultDto = labeledGoodService.updateSerialNo(
						waybillNo,
						diableSerialList.get(i).toString(), FossConstants.INACTIVE);
				// 获取成功标示
				String code = resultDto.getCode();
				// 判断是否失败，
				//如果失败抛出失败原因
				if (StringUtils.equals(code, PackagingConstants.PACKAGING_FAILURE)) {
					throw new PackagingException(resultDto.getMsg());
				}
			}
		}
		
		/**
		 * 此处需要调用运单接口，更新运单开单件数
		 */
		// 首先获取运单号件数
		int serialNum = queryPackedDao.querySerialNoCount(waybillNo);
		// 调用接口修改运单开单件数
		ResultDto resultDto = waybillManagerService.updateGoodsNum(waybillNo,
				serialNum,pdaPackagingInfoEntity.getOperatorCode(),pdaPackagingInfoEntity.getOrgCode());
		// 获取成功标示
		String code = resultDto.getCode();
		// 判断是否失败，如果失败抛出失败原因
		if (StringUtils.equals(code, PackagingConstants.PACKAGING_FAILURE)) {
			throw new PackagingException(resultDto.getMsg());
		}

	/**
		 * 新增表“异常运单信息表”，表结构与运单表保持一致，用于记录： 
         * ①有打木架操作的运单信息。 
		 * 
		 * **/
		try {
			
			waybillEntity=waybillManagerService.queryWaybillBasicByNo(pdaPackagingInfoEntity.getWayBillNumber());

			waybillPackBIService.addWaybillPackBIEntity(waybillEntity);
			
		} catch (BusinessException e) {
			throw new TfrBusinessException("调接送货接口，新增表 异常运单信息表 异常："+e.getErrorCode());
		}
		
		//修改包装需求开单件数
		Map<String,Object> map = new HashMap<String,Object>();
		//运单号
		map.put("waybillNo", waybillNo);
		//件数
		map.put("num", serialNum);
		queryPackedDao.updateWaybillNum(map);
		
		//当有多个流水号合并时，调用走货路径更新走货路径
		if(serialNoList.size() > 1){
			//新增合并后的流水号集合
			List<String> afterMerge = new ArrayList<String>();
			//将新流水号加到集合中
			afterMerge.add(newSerialNo);
			//调用走货路径接口
			calculateTransportPathService.packing(waybillNo,
					orgCode, serialNoList, afterMerge);
		}
		/**
		 * 进行出入库操作
		 */
		handleSerialStock(inStockSerialRelationSet, outStockSerialRelationSet,
				pdaPackagingInfoEntity);
		// 调用PC端包装录入的DAO完成对包装信息的录入
		queryPackedDao.insertSerialRelation(addSerialRelation);
		// 同时需要更新包装货的状态
		queryPackedDao.addSerialStatus(addSerialRelation);	
		// 更新流水号状态后，同步更新包装需求表中的已包装件数
		queryPackedDao.updatePackedNum(waybillNo);
		//返回新流水号
		return newSerialNo;
	}
	
	/**
	 * 做出入库操作（未复用packedserivce，
	 * 因为packedserivce存在getCurrentUser，
	 * 此处可用PDA传入的操作人）
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-25 下午4:58:05
	 */
	private int handleSerialStock(Set<String> inStockSerialRelationSet,
			Set<String> outStockSerialRelationSet,
			PDAPackagingInfoEntity pdaPackagingInfoEntity) {
		//用户编码
		String userCode = pdaPackagingInfoEntity.getOperatorCode();
		//职员信息
		EmployeeEntity employee = employeeService
				.queryEmployeeByEmpCode(userCode);
		if (employee == null) {
			//抛出异常
			throw new PackagingException("请校验创建人工号是否存在");
		}
		//用户名称
		String userName = employee.getEmpName();

		/**
		 * 1、获取货区编号，
		 * 名称，
		 * 操作人工号，
		 * 操作人姓名，
		 * 出入库类型类型，
		 * 设备类型
		 */
		//新增入库对象
		InOutStockEntity ioStockEntity = new InOutStockEntity();
		// 运单号
		ioStockEntity.setWaybillNO(pdaPackagingInfoEntity.getWayBillNumber());
		// 操作人工号
		ioStockEntity.setOperatorCode(userCode);
		// 操作人姓名
		ioStockEntity.setOperatorName(userName);
		// 外场编码
		ioStockEntity.setOrgCode(pdaPackagingInfoEntity.getOrgCode());
		// 出入库事件
		ioStockEntity.setInOutStockTime(new Date());

		/**
		 * 入库操作
		 */
		//迭代器
		Iterator<String> iteratorIn = inStockSerialRelationSet.iterator();
		//遍历迭代器
		while (iteratorIn.hasNext()) {
			// 入库类型类型
			ioStockEntity
					.setInOutStockType(StockConstants.AFTER_PACKAGE_NEW_GOODS_IN_STOCK_TYPE);
			//入库流水号
			String serialNo = iteratorIn.next().toString();
			if (!StringUtils.isEmpty(serialNo)) {
				//设置id
				ioStockEntity.setId(UUIDUtils.getUUID());
				//设置流水号
				ioStockEntity.setSerialNO(serialNo);
				try {
					//调用库存接口做入库操作
					stockService.inStockPC(ioStockEntity);
				} catch (BusinessException e) {
					//抛出库存接口业务异常
					throw new PackagingException(e.getErrorCode());
				}
			}
		}
		/**
		 * 出库操作
		 */

		Iterator<String> iteratorOut = outStockSerialRelationSet.iterator();
		while (iteratorOut.hasNext()) {
			// 出库类型类型
			ioStockEntity
					.setInOutStockType(StockConstants.AFTER_PACKAGE_OLD_GOODS_OUT_STOCK_TYPE);
			//出库流水号
			String serialNo = iteratorOut.next().toString();
			if (!StringUtils.isEmpty(serialNo)) {				
				//设置id
				ioStockEntity.setId(UUIDUtils.getUUID());
				//设置流水号
				ioStockEntity.setSerialNO(serialNo);
				try {
					//调用库存接口做出库操作
					stockService.outStockPC(ioStockEntity);
				} catch (BusinessException e) {
					//抛出库存接口业务异常
					throw new PackagingException(e.getErrorCode());
				}

			}
		}
		//返回成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 获取新流水号
	 * 
	 * @param waybillNo
	 *            获取新流水号
	 * @return 新流水号
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-3 上午9:58:24.queryMaxSerialNo(waybillno)
	 */
	private String getNewSerial(String waybillNo) {

		MutexElement mutex = null;

		try {

			String newSerial = "";

			mutex = new MutexElement(waybillNo, "打包装获取最大流水号",MutexElementType.TFR_PACKAGE_NEWSERIAL_MAX);

			// 锁定
			boolean flag = businessLockService.lock(mutex, 0);
			if (flag) {

				// 调用PC端包装录入DAO获取新流水号
				String serialNo = queryPackedDao.queryMaxSerialNo(waybillNo);
				// 返回新流水号
				newSerial = queryPackedService.obtainMaxSerialNo(serialNo, 1);

				// 解锁
				businessLockService.unlock(mutex);

			} else {
				throw new PackagingException(waybillNo+ " : 当前有人和您一起打包装同一票货并且在获取流水号,请他操作完成在操作!!!");
			}

			return newSerial;
		} catch (PackagingException e) {
			// 解锁
			businessLockService.unlock(mutex);
			throw new PackagingException(e.toString());
			// 捕捉业务异常
		} catch (BusinessException e) {
			// 解锁
			businessLockService.unlock(mutex);
			throw new PackagingException(e.toString());
		} // 捕捉异常
		catch (Exception e) {
			// 解锁
			businessLockService.unlock(mutex);
			throw new PackagingException(e.getMessage());
		}

	}

	/**
	 * 判断需要包装信息是否已包装
	 * 
	 * @param pdaPackagingInfoEntity
	 *            包装录入信息
	 * @author 046130-foss-xuduowei
	 * 
	 * @date 2012-11-3 上午9:42:50
	 */
	private String getPackedId(PDAPackagingInfoEntity pdaPackagingInfoEntity) {
		// 获取运单号
		String waybillNo = pdaPackagingInfoEntity.getWayBillNumber().toString()
				.trim();
		String packageType=null;
		
		if(packing.MAKE_WOODEN_STOCK.getValue().equals(pdaPackagingInfoEntity.getPackedMate())){
			packageType = packing.MAKE_WOODEN_STOCK.getValue();
		}else{
			packageType = packing.WOODEN_FRAME.getValue();
		}
		// 获取已包装货物信息
		List<WaybillPackEntity> waybillPackList = queryPackedDao
				.queryWaybillPack(waybillNo,packageType);
		// 如果运单号已包装，则需要返回ID
		if (waybillPackList.size() > 0) {
			//包装录入信息
			WaybillPackEntity waybillPackEntity = (WaybillPackEntity) waybillPackList
					.get(0);
			//返回包装录入信息id
			return waybillPackEntity.getId().trim();
		} else {
			//返回空值
			return null;
		}
	}

	/*
	 * @desc 在代包装信息录入完成后，进行检验，若符合以下条件，则调用接送货接口生成待办事项（参考RULE-A2）：
     *1、开单件数<=30且开单件数>代打包装件数（非全包装）
     *2、提货方式为送货（包含送货上楼/送货（不含上楼）/送货进仓/免费送货）
     *3、货物需要打木架，且有合打记录
     *4、包装后货物总件数<开单件数的运单（有合打）
     *5、系统后台生成一条更改记录，更改明细为件数变更；
     *6、生成待办事项，待办类型为更改单标签重打，需要打标签的货物为未打包装的货物（通过开单时标记的流水号区分）
	 *@author foss-105795-wqh
	 *@date  2014-04-10
	 *@parm  pdaPackagingInfoEntity
	 * */
	private void unpackByAllBringTodo(PDAPackagingInfoEntity pdaPackagingInfoEntity,WaybillEntity waybillEntity){
		
		//获取打包后的件数
		int packedQty=packCommonService.queryPackedQtyByWaybillNo(pdaPackagingInfoEntity.getWayBillNumber(),
				pdaPackagingInfoEntity.getOrgCode());
		//所有的送货方式
		List<String> deliverWayList=new ArrayList<String>();
		
		/*初始化送货方式*/
		//汽运送货(不含上楼)
		deliverWayList.add(WaybillConstants.DELIVER_NOUP);
		//汽运免费派送
		deliverWayList.add(WaybillConstants.DELIVER_FREE);
		//汽运送货进仓
		deliverWayList.add(WaybillConstants.DELIVER_STORAGE);
		//汽运送货（上楼）
		deliverWayList.add(WaybillConstants.DELIVER_UP);
		//判断提货方式为送货
		boolean isDeliver=false;
		if(deliverWayList.contains(waybillEntity.getReceiveMethod()))
		{
			isDeliver=true;
		}
		
		//获取开单件数
		int goodsTotalQty=waybillEntity.getGoodsQtyTotal();
		//获取包装需求中需要打包的件数
		int rePackQty=0;
		QueryWaybillPackEntity waybillPackEntity=
				packCommonService.queryRePackByWaybillNo(pdaPackagingInfoEntity.getWayBillNumber(),pdaPackagingInfoEntity.getOrgCode());
		if(waybillPackEntity!=null){
			rePackQty=Integer.parseInt(waybillPackEntity.getPackedNum());
		}
		//包装后运单总件数
		int packedTotalQty=0;
		if(waybillPackEntity!=null){
			packedTotalQty=Integer.parseInt(waybillPackEntity.getWaybillNum());
		}		
		//是否合打，根据旧流水号件数大于新流水号件数
		boolean isJoinPacked=false;
		if(packedQty<rePackQty)
		{
			isJoinPacked=true;
		}
		EmployeeEntity employee = employeeService
				.queryEmployeeByEmpCode(pdaPackagingInfoEntity
						.getOperatorCode());
		/*
		 * 满足以下条件，调用接送代办接口：
		 *1、开单件数<=30且开单件数>代打包装件数（非全包装）
         *2、提货方式为送货（包含送货上楼/送货（不含上楼）/送货进仓/免费送货）
         *3、货物需要打木架，且有合打记录
         *4、包装后货物总件数<开单件数的运单（有合打）
		 * */
	    if(goodsTotalQty<=PackagingConstants.PACKAGE_BILL_GOODSQTY
	    		&&goodsTotalQty>rePackQty
	    		&&isDeliver
	    		&&isJoinPacked
	    		&&packedQty<goodsTotalQty)
	    {
	    	//获取当前用户及部门信息
	    	OrgAdministrativeInfoEntity org=getOutFieldCode(pdaPackagingInfoEntity.getOrgCode());
	    	//获取需生成代办的流水号
			List<String> toDoList=
					packCommonService.queryToDoSeriasByWaybillNo(pdaPackagingInfoEntity.getWayBillNumber(),pdaPackagingInfoEntity.getOrgCode());
	    	//CurrentInfo currentInfo=FossUserContext.getCurrentInfo();
	    	//包装NeedCreateTodoDto ,提供给接送货
	    	NeedCreateTodoDto needToDo=new NeedCreateTodoDto();
	    	//运单号
	    	needToDo.setWaybillNo(pdaPackagingInfoEntity.getWayBillNumber());
	    	//需要生成待办的流水号集合
	    	needToDo.setNeedCreateSerialList(toDoList);
	    	//用户所在部门编码
	    	needToDo.setUserOrgCode(pdaPackagingInfoEntity.getOrgCode());
	    	//用户所在部门名称
	    	needToDo.setUserOrgName(org.getName());
	    	//用户编码
	    	needToDo.setUserCode(employee.getEmpCode());
	    	//用户姓名
	    	needToDo.setUserName(employee.getEmpName());
	    	//修改运单对应的件数
	    	needToDo.setNewGoodNum(packedTotalQty);
	    	//运单原来的件数
	    	needToDo.setOldGoodNum(goodsTotalQty);
	    	try {
				
	    		waybillManagerService.createNotAllPackageTodo(needToDo);
			} catch (BusinessException e) {
				throw new TfrBusinessException("调用接送货接口出错,错误信息：{"+e.getErrorCode()+"}");
			}
	    }
		
	}
	
	/*
	 * @desc 获取当前外场部门的code
	 * @author foss-105795-wqh
	 * @param 
	 * @ date 2014-04-11
	 * 
	 * */
    private OrgAdministrativeInfoEntity getOutFieldCode(String orgCode){
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		
		//组织对象
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoByCode(orgCode,
							bizTypes);
	   if(org==null)
	   {
		   throw new TfrBusinessException("当前部门不存在");
		   
	   }
	   return org;
     }
	
	/**
	 * 设置 pDA包装录入DAO接口.
	 *
	 * @param pdaPackagingDao the new pDA包装录入DAO接口
	 */
	public void setPdaPackagingDao(IPDAPackagingDao pdaPackagingDao) {
		this.pdaPackagingDao = pdaPackagingDao;
	}
	/**
	 * 设置 接送货新增、修改流水号接口.
	 *
	 * @param labeledGoodService the new 接送货新增、修改流水号接口
	 */
	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}

	/**
	 * 设置 pC包装录入SERVICE接口.
	 *
	 * @param queryPackedService the new pC包装录入SERVICE接口
	 */
	public void setQueryPackedService(IQueryPackedService queryPackedService) {
		this.queryPackedService = queryPackedService;
	}

	/**
	 * 
	 *
	 * @param queryPackedDao 
	 */
	public void setPackedDao(IQueryPackedDao queryPackedDao) {
		this.queryPackedDao = queryPackedDao;
	}

	/**
	 * 设置 pC包装录入DAO.
	 *
	 * @param queryPackedDao the new pC包装录入DAO
	 */
	public void setQueryPackedDao(IQueryPackedDao queryPackedDao) {
		this.queryPackedDao = queryPackedDao;
	}

	/**
	 * 设置 获取指定部门接口.
	 *
	 * @param orgAdministrativeInfoComplexService the new 获取指定部门接口
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 设置 职员信息接口.
	 *
	 * @param employeeService the new 职员信息接口
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * 设置 货区接口.
	 *
	 * @param goodsAreaService the new 货区接口
	 */
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}

	/**
	 * 设置 库存接口.
	 *
	 * @param stockService the new 库存接口
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * 设置 接送货修改运单件数接口.
	 *
	 * @param waybillManagerService the new 接送货修改运单件数接口
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 * 设置 走货路径接口.
	 *
	 * @param calculateTransportPathService the new 走货路径接口
	 */
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	
	public void setPackOutDao(IPackOutDao packOutDao) {
		this.packOutDao = packOutDao;
	}

	public void setPackCommonService(IPackCommonService packCommonService) {
		this.packCommonService = packCommonService;
	}
	
	public void setWaybillPackBIService(IWaybillPackBIService waybillPackBIService) {
		this.waybillPackBIService = waybillPackBIService;
	}
	private IPackagingSupplierService packagingSupplierService;
	
	public void setPackagingSupplierService(
			IPackagingSupplierService packagingSupplierService) {
		this.packagingSupplierService = packagingSupplierService;
	}
	
	public void setPackageMainPriceService(
			IPackageMainPriceService packageMainPriceService) {
		this.packageMainPriceService = packageMainPriceService;
	}
	
	/**
	     *
		 * @desc  
		 * @author 042795-duyi
		 * @date 2014-6-25 下午7:38:51
		 * @see queryPackagingSupplierListByEmpCode
	 */
	@Override
	public List<PackagingSupplierEntity> queryPackagingSupplierListByEmpCode(
			String empCode) {
		if(StringUtils.isNotBlank(empCode)){
			return packagingSupplierService.queryPackagingSupplierListByEmpCode(empCode);
		}else{
			return null;
		}
	}

}