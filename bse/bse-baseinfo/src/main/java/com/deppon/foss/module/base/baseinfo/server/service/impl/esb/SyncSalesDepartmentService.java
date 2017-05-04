/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/esb/SyncSalesDepartmentService.java
 * 
 * FILE NAME        	: SyncSalesDepartmentService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.SyncSalesDepartmentRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncSalesDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.ows.inteface.domain.DepartmentInfo;
import com.deppon.ows.inteface.domain.SyncSalesDepartmentRequest;

/**
 * 同步FOSS的营业部信息给官网系统
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-15 上午11:50:25
 */
public class SyncSalesDepartmentService implements ISyncSalesDepartmentService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncSalesDepartmentService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYNC_SALES_DEPARTMENT";

	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 同步FOSS的营业部信息给OWS系统
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-1-15 上午11:50:25
	 */
	@Override
	public void syncSalesDepartmentDataToOws(
			List<SaleDepartmentEntity> saleDepartmentList) {
		try {
			if (CollectionUtils.isNotEmpty(saleDepartmentList)) {
				SyncSalesDepartmentRequest salesDepartRequest = new SyncSalesDepartmentRequest();
				List<DepartmentInfo> departInfoLst = new ArrayList<DepartmentInfo>();

				StringBuilder versionNos = new StringBuilder();
				StringBuilder codes = new StringBuilder();

				for (SaleDepartmentEntity entity : saleDepartmentList) {
					DepartmentInfo departInfo = this.transFossToEsb(entity);

					departInfoLst.add(departInfo);

					versionNos.append(SymbolConstants.EN_COMMA);
					versionNos.append(entity.getVersionNo());
					codes.append(SymbolConstants.EN_COMMA);
					codes.append(entity.getCode());
				}
				salesDepartRequest.getDepts().addAll(departInfoLst);

				AccessHeader accessHeader = new AccessHeader();
				accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
				accessHeader.setBusinessId(codes.toString());
				accessHeader.setBusinessDesc1("同步 营业部信息");
				accessHeader.setBusinessDesc2(versionNos.toString());
				accessHeader.setVersion("0.1");

				log.info("开始调用 同步营业部：\n"
						+ new SyncSalesDepartmentRequestTrans()
								.fromMessage(salesDepartRequest));

				ESBJMSAccessor.asynReqeust(accessHeader, salesDepartRequest);

				log.info("结束调用 同步营业部：\n"
						+ new SyncSalesDepartmentRequestTrans()
								.fromMessage(salesDepartRequest));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new SynchronousExternalSystemException(" 同步营业部信息",
					" 同步营业部信息 发送数据到ESB错误");
		}
	}

	private DepartmentInfo transFossToEsb(SaleDepartmentEntity fossEntity) {
		if (fossEntity == null) {
			return null;
		}

		DepartmentInfo info = new DepartmentInfo();

		// 组织编码非空验证
		if (StringUtils.isBlank(fossEntity.getCode())) {
			// 打印日志信息
			log.info("SyncSalesDepartmentService 实体类组织编码为空");
			// 返回空
			return null;
		}
		// 查询组织标杆编码
		OrgAdministrativeInfoEntity entity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(fossEntity.getCode());
		// 非空验证
		if (null == entity || StringUtils.isBlank(entity.getUnifiedCode())) {
			// 打印日志信息
			log.info("SyncSalesDepartmentService 根据组织编码查询组织标杆编码失败");
			// 返回空
			return null;
		}

		// ID
		info.setId(fossEntity.getId());
		// 部门编码
		info.setCode(entity.getCode());
		// 部门标杆编码编码
		info.setUnifiedCode(entity.getUnifiedCode());
		// 部门名称
		info.setName(fossEntity.getName());
		// 拼音
		info.setPinyin(fossEntity.getPinyin());
		// 可出发
		info.setLeave(fossEntity.getLeave());
		// 可到达
		info.setArrive(fossEntity.getArrive());
		// 是否驻地部门
		info.setStation(fossEntity.getStation());
		// 广告语
		info.setSlogans(fossEntity.getSlogans());
		// 开业日期
		info.setOpenDate(fossEntity.getOpeningDate());
		// 最大临欠额度
		info.setMaxTempArrears(fossEntity.getMaxTempArrears());
		// 已用临欠额度
		info.setUsedTempArrears(fossEntity.getUsedTempArrears());
		// 所属集中开单组
		// 确定OWS不关注此信息
		// info.setBillingGroup(fossEntity.getBillingGroup());
		// 驻地营业部所属外场
		info.setTransferCenter(fossEntity.getTransferCenter());
		// 取消到达日期
		// esbInfo.setCancelArrivalDate(fossEntity.getCancelArrivalDate());
		// 转货部门
		// esbInfo.setTransferGoodDept(fossEntity.getTransferGoodDept());
		// 可自提
		info.setPickupSelf(fossEntity.getPickupSelf());
		// 可派送
		info.setDelivery(fossEntity.getDelivery());
		// 可空运到达
		info.setAirArrive(fossEntity.getAirArrive());
		// 可汽运到达
		info.setTruckArrive(fossEntity.getTruckArrive());
		// 单件重量上限非空判断
		if (null != fossEntity.getSinglePieceLimitkg()) {
			info.setSinglePieceLimitKG((double) fossEntity
					.getSinglePieceLimitkg());
		}
		// 单票重量上限非空判断
		if (null != fossEntity.getSingleBillLimitkg()) {
			// 单票重量上限
			info.setSingleBillLimitKG((double) fossEntity
					.getSingleBillLimitkg());
		}
		// 单件体积上限非空判断
		if (null != fossEntity.getSingleBillLimitkg()) {
			// 单件体积上限
			info.setSinglePieceLimitVOL((double) fossEntity
					.getSinglePieceLimitvol());
		}
		// 单票体积上限非空判断
		if (null != fossEntity.getSingleBillLimitkg()) {
			// 单票体积上限
			info.setSingleBillLimitVOL((double) fossEntity
					.getSingleBillLimitvol());
		}
		// 自提区域描述
		info.setPickupAreaDesc(fossEntity.getPickupAreaDesc());
		// 非空验证
		if (null != fossEntity.getDeliveryAreaDesc()) {
			// 派送区域描述
			info.setDeliveryAreaDesc(fossEntity.getDeliveryAreaDesc());
		}
		// 非空验证
		if (null != fossEntity.getDeliveryCoordinate()) {
			// 派送区坐标编号
			info.setDeliveryCoordinate(fossEntity.getDeliveryCoordinate());
		}
		// 是否启用
		info.setActive(fossEntity.getActive());
		// 版本号
		info.setVersionNo((double) fossEntity.getVersionNo());
		// 是否在集中接送货范围内
		// esbInfo.setInCentralizedShuttle(fossEntity.getInCentralizedShuttle());
		// 是否可开装卸费canPaySerivceFee
		info.setCanPaySerivceFee(fossEntity.getCanPayServiceFee());
		// 是否可返回签单
		info.setCanReturnSignBill(fossEntity.getCanReturnSignBill());
		// 是否可货到付款
		info.setCanCashOnDelivery(fossEntity.getCanCashOnDelivery());
		// 是否可代收货款
		info.setCanAgentCollected(fossEntity.getCanAgentCollected());

		// 创建时间
		info.setCreatTime(fossEntity.getCreateDate());
		// 更新时间
		info.setModifyTime(fossEntity.getCreateDate());
		// 是否启用
		info.setActive(fossEntity.getActive());
		// 创建人
		info.setCreateUserCode(fossEntity.getCreateUser());
		// 更新人
		info.setModifyUserCode(fossEntity.getModifyUser());
		// 快递新增属性
		// 可快递签单返回

		info.setCanReturnexpressSignBill(fossEntity
				.getCanExpressReturnSignBill());
		// 可快递接货
		info.setCanPickupExpress(fossEntity.getCanExpressPickupToDoor());
		// 可快递自提
		info.setPickupExpressSelf(fossEntity.getCanExpressPickupSelf());
		// 可快递派送
		info.setDeliveryExpress(fossEntity.getCanExpressDelivery());
		// 快递自提区域描述
		info.setExpressPickupAreaDesc(fossEntity.getExpressPickupAreaDesc());
		// 快递派送区域描述
		info.setExpressDeliveryAreaDesc(fossEntity.getExpressDeliveryAreaDesc());

		//设置营业部是否合伙人营业部(FOSS将该字段封装于是否集中接送货区域字段内)
		//配合合伙人项目-dujunhui
		info.setCanCentralizedPickup(fossEntity.getIsLeagueSaleDept());
		/**
		 * 是否二级网点
		 * 配合合伙人项目-308861
		 */
		info.setIsTwoLevelNetwork(fossEntity.getIsTwoLevelNetwork());
		/**
		 * 网点模式
		 * 配合合伙人项目-308861
		 */
		info.setNetworkModel(fossEntity.getNetworkModel());
		return info;
	}

}
