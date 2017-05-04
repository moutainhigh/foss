/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-agency
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/agency/test/service/StlAirJointTicketServiceTest.java
 * 
 * FILE NAME        	: StlAirJointTicketServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.agency.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.service.IAirJointTicketService;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirTransferService;
import com.deppon.foss.module.settlement.agency.test.BaseTestCase;
import com.deppon.foss.module.settlement.agency.test.util.AgencyTestUtil;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;

/**
 * 结算合大票清单Service测试类 和结算中转提货清单Service测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-17 上午8:56:26
 * @since
 * @version
 */
public class StlAirJointTicketServiceTest extends BaseTestCase {

	/**
	 * 合大票Service
	 */
	@Autowired
	private IAirJointTicketService airJointTicketService;

	@Autowired
	private ISettlementCommonService settlementCommonService;

	@Autowired
	private IBillReceivableService billReceivableService;

	/**
	 * 中转提货清单Service
	 */
	@Autowired
	IAirTransferService airTransferService;

	private List<String> list = new ArrayList<String>();

	@Before
	public void setUp() {

	}

	/**
	 * 添加合大票清单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 上午10:54:12
	 */
	@Test
	@Rollback(true)
	public void testAddAirJointTicket() {
		String airWaybillNo = this.getAirWaybillNo();
		AirPickupbillEntity entity = this.getAirPickupbillEntity(airWaybillNo);
		List<AirPickupbillDetailEntity> list = this
				.getAirPickupbillDetailEntitys(airWaybillNo);
		try {
			this.airJointTicketService.addAirJointTicket(entity, list,
					AgencyTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.info(e.getMessage(), e);
		}
	}

	/**
	 * 修改合大票清单内容
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 下午2:12:32
	 */
	@Test
	@Rollback(true)
	public void testModifyAirJointTicketDetail() {
		String airWaybillNo = this.getAirWaybillNo();
		AirPickupbillEntity oldEntity = this
				.getAirPickupbillEntity(airWaybillNo);
		List<AirPickupbillDetailEntity> liste = this
				.getAirPickupbillDetailEntitys(airWaybillNo);
		try {
			this.airJointTicketService.addAirJointTicket(oldEntity, liste,
					AgencyTestUtil.getCurrentInfo());
			List<String> deleteWaybillNos = new ArrayList<String>();

			// 修改，造部分合票中删除的信息
			int i = 1;
			for (String waybillNo : list) {
				if ((i % 2) == 0) {
					deleteWaybillNos.add(waybillNo);
				}
				i += 1;
			}

			// 修改时，模拟新增的合票单据明细
			List<AirPickupbillDetailEntity> addDetals = this
					.getAirPickupbillDetailEntitysTwo(airWaybillNo);
			List<AirPickupbillDetailEntity> modifiedDetails = new ArrayList<AirPickupbillDetailEntity>();
			boolean dl = false;
			for (AirPickupbillDetailEntity entity : liste) {
				dl = false;
				for (String waybillNo : deleteWaybillNos) {
					if (waybillNo.equals(entity.getWaybillNo())) {
						dl = true;
						list.remove(entity.getWaybillNo());
					}
				}
				if (!dl) {
					modifiedDetails.add(entity);
				}
			}
			this.airJointTicketService.modifyAirJointTicketDetail(oldEntity,
					oldEntity, addDetals, modifiedDetails, deleteWaybillNos,
					AgencyTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 制造合大票总体信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 上午10:56:12
	 * @return
	 */
	private AirPickupbillEntity getAirPickupbillEntity(String airWaybillNo) {
		AirPickupbillEntity entity = new AirPickupbillEntity();
		entity.setAirLineCode("123456");
		entity.setAirLineName("上海东方航空公司");
		entity.setAirWaybillNo(airWaybillNo);// 航空正单号
		entity.setDestOrgCode("XZ123456");// 到达代理编码
		entity.setDestOrgName("上海");// 到达代理名称
		entity.setFlightNo("HK123456");// 航空单号
		entity.setArrvRegionCode("MDZ11111");// 目的站编号
		entity.setArrvRegionName("西藏");// 目的站名称
		entity.setOrigOrgCode("GS00002");// 制单部门编码
		entity.setOrigOrgName("123456");// 制单部门名称
		entity.setCreateTime(new Date());
		entity.setDeliverFeeTotal(new BigDecimal("10000"));
		return entity;
	}

	/**
	 * 获取批量合票清单明细数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 上午11:33:48
	 * @return
	 */
	private List<AirPickupbillDetailEntity> getAirPickupbillDetailEntitys(
			String airWaybillNo) {
		List<AirPickupbillDetailEntity> list = new ArrayList<AirPickupbillDetailEntity>();
		list.add(this.getAirPickupbillDetailEntity(airWaybillNo));
		list.add(this.getAirPickupbillDetailEntity(airWaybillNo));
		list.add(this.getAirPickupbillDetailEntity(airWaybillNo));
		list.add(this.getAirPickupbillDetailEntity(airWaybillNo));
		list.add(this.getAirPickupbillDetailEntity(airWaybillNo));
		list.add(this.getAirPickupbillDetailEntity(airWaybillNo));
		return list;
	}

	/**
	 * 获取批量合票清单明细数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 下午2:20:20
	 * @param airWaybillNo
	 * @return
	 */
	private List<AirPickupbillDetailEntity> getAirPickupbillDetailEntitysTwo(
			String airWaybillNo) {
		List<AirPickupbillDetailEntity> list = new ArrayList<AirPickupbillDetailEntity>();
		list.add(this.getAirPickupbillDetailEntity(airWaybillNo));
		list.add(this.getAirPickupbillDetailEntity(airWaybillNo));
		list.add(this.getAirPickupbillDetailEntity(airWaybillNo));
		list.add(this.getAirPickupbillDetailEntity(airWaybillNo));
		list.add(this.getAirPickupbillDetailEntity(airWaybillNo));
		return list;
	}

	/**
	 * 获取合大票清单明细实体
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 上午11:34:06
	 * @return
	 */
	private AirPickupbillDetailEntity getAirPickupbillDetailEntity(
			String airWaybillNo) {
		AirPickupbillDetailEntity entity = new AirPickupbillDetailEntity();
		entity.setAirPickupbillId("111");
		String waybillNo = this.getWaybillNo();
		list.add(waybillNo);
		entity.setWaybillNo(waybillNo);
		entity.setAirWaybillNo(airWaybillNo);
		entity.setArrvRegionName("西藏");
		entity.setGoodsName("111");
		entity.setDeliverFee(new BigDecimal("1000"));
		/**
		 * 为运单插入财务数据（到达运费应收单、代收货款应收单）
		 */
		addBillReceivable(
				waybillNo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
		addBillReceivable(
				waybillNo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);

		return entity;
	}

	/**
	 * 添加应收单方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 下午7:22:23
	 * @param waybillNo
	 * @param billType
	 */
	private void addBillReceivable(String waybillNo, String billType) {
		try {
			String receivableNo = this.getReceivableNO(billType);
			BillReceivableEntity entity = AgencyTestUtil
					.getBillReceivableEntity(
							waybillNo,
							receivableNo,
							billType,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);
			this.billReceivableService.addBillReceivable(entity,
					AgencyTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.debug(e.getMessage(), e);
		}
	}

/********************************************************************* 中转提货清单 *******************************/

	/**
	 * 新增：中转提货清单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 下午5:16:48
	 */
	@Test
	@Rollback(true)
	public void testAddAirTransfer() {
		try {
			String airWaybillNo = this.getAirWaybillNo();

			AirPickupbillEntity entity = this
					.getAirPickupbillEntity(airWaybillNo);
			List<AirPickupbillDetailEntity> list = this
					.getAirPickupbillDetailEntitys(airWaybillNo);

			// 先新增合大票清单记录
			this.airJointTicketService.addAirJointTicket(entity, list,
					AgencyTestUtil.getCurrentInfo());

			String airTransferPickupbillNo = this.getAirTransferPickupbillNo();
			String destOrgCode = "XZ2234562222";
			AirTransPickupbillEntity master = this.getAirTransPickupbillEntity(
					airWaybillNo, airTransferPickupbillNo, destOrgCode);
			List<AirTransPickupDetailEntity> details = this
					.getAirTransPickupDetailEntitys(airWaybillNo,
							airTransferPickupbillNo);

			// 保存中转提货清单数据
			this.airTransferService.addAirTransfer(master, details,
					AgencyTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 修改中转提货清单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 下午5:44:20
	 */
	@Test
	@Rollback(true)
	public void testModifyAirTransferDetail() {
		try {
			String airWaybillNo = this.getAirWaybillNo();

			AirPickupbillEntity entity = this
					.getAirPickupbillEntity(airWaybillNo);
			List<AirPickupbillDetailEntity> list = this
					.getAirPickupbillDetailEntitys(airWaybillNo);

			// 先新增合大票清单记录
			this.airJointTicketService.addAirJointTicket(entity, list,
					AgencyTestUtil.getCurrentInfo());

			String airTransferPickupbillNo = this.getAirTransferPickupbillNo();
			String destOrgCode = "XZ2234562222";
			AirTransPickupbillEntity master = this.getAirTransPickupbillEntity(
					airWaybillNo, airTransferPickupbillNo, destOrgCode);
			List<AirTransPickupDetailEntity> details = this
					.getAirTransPickupDetailEntitys(airWaybillNo,
							airTransferPickupbillNo);

			// 保存中转提货清单数据
			this.airTransferService.addAirTransfer(master, details,
					AgencyTestUtil.getCurrentInfo());

			// 修改中转提货清单数据
			// 修改单据中新增加的运单信息
			List<AirTransPickupDetailEntity> addDetials = new ArrayList<AirTransPickupDetailEntity>();
			for (int i = 0; i < 3; i++) {
				addDetials.add(this.getAirTransPickupDetailEntityTwo(
						airWaybillNo, this.getWaybillNo(),
						airTransferPickupbillNo));
			}

			// 修改单据中--删除数据
			List<String> deleteDeatils = new ArrayList<String>();
			List<AirTransPickupDetailEntity> modifyDeatil = new ArrayList<AirTransPickupDetailEntity>();
			for (int i = 0; i < details.size(); i++) {
				if (i % 2 == 0) {
					deleteDeatils.add(details.get(i).getWaybillNo());
				} else {
					modifyDeatil.add(details.get(i));
				}
			}

			// 修改中转提货清单接口
			this.airTransferService.modifyAirTransferDetail(master, master,
					addDetials, modifyDeatil, deleteDeatils,
					AgencyTestUtil.getCurrentInfo());

		} catch (SettlementException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取中转提货清单数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 下午8:19:23
	 * @param airWaybillNo
	 * @return
	 */
	private AirTransPickupbillEntity getAirTransPickupbillEntity(
			String airWaybillNo, String airTransferPickupbillNo,
			String destOrgCode) {
		AirTransPickupbillEntity entity = new AirTransPickupbillEntity();
		entity.setDeliverFeeTotal(new BigDecimal("100"));
		entity.setAirTransferPickupbillNo(airTransferPickupbillNo);
		entity.setAirWaybillNo(airWaybillNo);// 航空正单号
		entity.setCreateOrgCode("123456");// 制单部门编码
		entity.setDestOrgCode(destOrgCode);// 空运代理编码
		entity.setDestOrgName("空运中转代理名称");// 空运代理编码
		entity.setCreateTime(new Date());
		return entity;
	}

	/**
	 * 制作中转提货清单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 下午8:41:57
	 * @param airWaybillNo
	 * @param airTransferPickupbillNo
	 * @return
	 */
	private List<AirTransPickupDetailEntity> getAirTransPickupDetailEntitys(
			String airWaybillNo, String airTransferPickupbillNo) {
		List<AirTransPickupDetailEntity> listTwo = new ArrayList<AirTransPickupDetailEntity>();
		for (String waybillNo : this.list) {
			listTwo.add(this.getAirTransPickupDetailEntity(airWaybillNo,
					waybillNo, airTransferPickupbillNo));
		}
		return listTwo;
	}

	/**
	 * 获取中转提货清单实体
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 下午4:49:43
	 * @param airWaybillNo
	 * @param airTransferPickupbillNo
	 * @return
	 */
	private AirTransPickupDetailEntity getAirTransPickupDetailEntity(
			String airWaybillNo, String waybillNo,
			String airTransferPickupbillNo) {
		AirTransPickupDetailEntity entity = new AirTransPickupDetailEntity();
		entity.setAirTransferPickupbillId(airTransferPickupbillNo);
		entity.setWaybillNo(waybillNo);
		entity.setDeliverFee(new BigDecimal("100"));
		return entity;
	}

	/**
	 * 修改--设置新的中转提货清单明细信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 下午5:46:44
	 * @param airWaybillNo
	 * @param waybillNo
	 * @return
	 */
	private AirTransPickupDetailEntity getAirTransPickupDetailEntityTwo(
			String airWaybillNo, String waybillNo,
			String airTransferPickupbillNo) {
		AirTransPickupDetailEntity entity = new AirTransPickupDetailEntity();
		entity.setAirTransferPickupbillId(airTransferPickupbillNo);
		entity.setWaybillNo(waybillNo);
		entity.setDeliverFee(new BigDecimal("100"));
		list.add(waybillNo);
		return entity;
	}

	/********************************************************* 辅助方法 **********************************************/

	private String getReceivableNO(String billType) {
		if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS2);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
				.equals(billType)
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
						.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS6);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS1);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS4);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS5);
		}

		return "";
	}

	/**
	 * 获取航空正单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 上午11:03:50
	 * @return
	 */
	private String getAirWaybillNo() {
		return "HKZD" + new Date().getTime();
	}

	/**
	 * 获取运单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 上午11:47:41
	 * @return
	 */
	private String getWaybillNo() {
		return "YD" + new Date().getTime();
	}

	/**
	 * 获取中转提货清单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 下午8:23:45
	 * @return
	 */
	private String getAirTransferPickupbillNo() {
		return "ZZTH" + new Date().getTime();
	}

	/**
	 * @return the list
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<String> list) {
		this.list = list;
	}
}
