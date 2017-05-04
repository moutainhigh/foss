package com.deppon.foss.module.settlement.pay.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;

public class PDAPosCardManageTest extends BaseTestCase {

	private static final Logger logger = LogManager
			.getLogger(PDAPosCardManageTest.class);

	@Autowired
	private IPdaPosManageService pdaPosManageService;

	/**
	 * 插入刷卡数据
	 * 
	 * @Title: getPdaPosManageService
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Rollback(false)
	//@Test
	public void insertPdaCardMessage() {
		try {
			List<PosCardEntity> list = new ArrayList<PosCardEntity>();

			PosCardEntity entity = new PosCardEntity();
			entity.setTradeSerialNo("W0113080403");
			entity.setSerialAmount(BigDecimal.valueOf(2000));
			entity.setCardDeptCode("W0113080403");
			entity.setCardDeptName("上海派送中心");
			entity.setBelongModule("对账单");

			entity.setCreateUser("时安生");
			entity.setCreateUserCode("086299");
			entity.setCardTime(new Date());
			entity.setIsDriver("false");
			entity.setIsKd("false");

			PosCardDetailEntity d = new PosCardDetailEntity();
			d.setTradeSerialNo("111111115");
			d.setInvoiceNo("DZ3243243");
			d.setInvoiceType("对账单");
			d.setAmount(BigDecimal.valueOf(500));

			List<PosCardDetailEntity> entitys = new ArrayList<PosCardDetailEntity>();
			entitys.add(d);

			entity.setPosCardDetailEntitys(entitys);

			list.add(entity);
			pdaPosManageService.insertPosCardData(list);
		} catch (SettlementException e) {
			logger.info(e.getErrorCode());
		}
	}

	/**
	 * 插入明细
	 * 
	 * @Title: getPdaPosManageService
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	// @Test
	// @Rollback(false)
	public void insertDetail() {
		try {
			/*
			 * PosCardDetailEntity d = new PosCardDetailEntity();
			 * d.setTradeSerialNo("111111112"); d.setInvoiceNo("3243243");
			 * d.setInvoiceType("小票"); d.setAmount(BigDecimal.valueOf(500));
			 * d.setOccupateAmount(BigDecimal.valueOf(500));
			 * d.setUnVerifyAmount(BigDecimal.valueOf(500));
			 * 
			 * PosCardDetailEntity d1 = new PosCardDetailEntity();
			 * d1.setTradeSerialNo("111111112"); d1.setInvoiceNo("YS09243243");
			 * d1.setInvoiceType("预收单"); d1.setAmount(BigDecimal.valueOf(500));
			 * d1.setOccupateAmount(BigDecimal.valueOf(500));
			 * d1.setUnVerifyAmount(BigDecimal.valueOf(500));
			 * 
			 * List<PosCardDetailEntity> entitys = new
			 * ArrayList<PosCardDetailEntity>(); entitys.add(d);
			 * entitys.add(d1); PosCardManageDto dto = new PosCardManageDto();
			 * dto.setPosCardDetailEntitys(entitys);
			 * pdaPosManageService.insertPosCardDetail(dto);
			 */
		} catch (SettlementException e) {
			logger.info(e.getErrorCode());
		}
	}

	@Rollback(false)
	// @Test
	public void updatePdaCardMessage() {
		try {
			PosCardEntity entity = new PosCardEntity();
			entity.setTradeSerialNo("111111114");
			// entity.setSerialAmount(BigDecimal.valueOf(6000));
			entity.setUnUsedAmount(BigDecimal.valueOf(1000));
			entity.setUsedAmount(BigDecimal.valueOf(1000));
			entity.setModifyUser("石安生");
			entity.setModifyUserCode("086299");
			pdaPosManageService.updatePosCardMessageAmount(entity);
		} catch (SettlementException e) {
			logger.info(e.getErrorCode());
		}
	}

	/**
	 * 批量更行
	 * 
	 * @Title: getPdaPosManageService
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	// @Test
	@Rollback(false)
	public void updateDetail() {
		try {
			PosCardDetailEntity d = new PosCardDetailEntity();
			d.setInvoiceNo("DZ09243243");
			d.setAmount(BigDecimal.valueOf(3000));
			d.setOccupateAmount(BigDecimal.valueOf(3000));
			d.setUnVerifyAmount(BigDecimal.valueOf(3000));

			PosCardDetailEntity d1 = new PosCardDetailEntity();
			d1.setInvoiceNo("3243243");
			d1.setAmount(BigDecimal.valueOf(3000));
			d1.setOccupateAmount(BigDecimal.valueOf(3000));
			d1.setUnVerifyAmount(BigDecimal.valueOf(3000));

			List<PosCardDetailEntity> entitys = new ArrayList<PosCardDetailEntity>();
			entitys.add(d);
			entitys.add(d1);
			PosCardManageDto dto = new PosCardManageDto();
			dto.setPosCardDetailEntitys(entitys);
			// pdaPosManageService.updatePosCardDetailAmount(dto);
		} catch (SettlementException e) {
			logger.info(e.getErrorCode());
		}
	}

	/**
	 * 插入单条数据
	 * 
	 * @Title: getPdaPosManageService
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	// @Test
	@Rollback(false)
	public void insertSingle() {
		/*
		 * PosCardDetailEntity d = new PosCardDetailEntity();
		 * d.setTradeSerialNo("232132321"); d.setInvoiceNo("YS3243243");
		 * d.setInvoiceType("预收单"); d.setAmount(BigDecimal.valueOf(2000));
		 * d.setOccupateAmount(BigDecimal.valueOf(2000));
		 * d.setUnVerifyAmount(BigDecimal.valueOf(2000));
		 * pdaPosManageService.addPosCardDetail(d);
		 */
//		pdaPosManageService.insertPosCardDetail(new ArrayList(), "", "");
	}

	// @Test
	@Rollback(false)
	public void updaeteSingle() {
		PosCardDetailEntity d = new PosCardDetailEntity();
		d.setTradeSerialNo("232132321");
		d.setInvoiceNo("YS3243243");
		d.setInvoiceType("预收单");
		d.setAmount(BigDecimal.valueOf(2));
		d.setOccupateAmount(BigDecimal.valueOf(2));
		d.setUnVerifyAmount(BigDecimal.valueOf(2));
		// pdaPosManageService.updateSinglePosCardDetail(d);
	}

	/**
	 * FOSS后台查询刷卡数据
	 * 
	 * @Title: getPdaPosManageService
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Rollback(false)
	// @Test
	public void selectList() {
		try {
			PosCardManageDto dto = new PosCardManageDto();
			dto.setTradeSerialNo("111111115");
			dto.setBelongMoudleCode("对账单");
			dto = pdaPosManageService.queryPosCardData(dto);
			logger.info(dto.getPosCardEntitys());
		} catch (SettlementException e) {
			logger.info(e.getErrorCode());
		}
	}

	/**
	 * 更具单据号去更新明细
	 * 
	 * @Title: getPdaPosManageService
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Rollback(false)
	// @Test
	public void test() {
		PosCardDetailEntity detail = new PosCardDetailEntity();
		detail.setInvoiceNo("DZ3243243");
		detail.setOccupateAmount(BigDecimal.valueOf(500));
		// pdaPosManageService.updateSinglePosCardDetail(detail);
		pdaPosManageService.updatePosCardByNum(detail);
	}

	/**
	 * 存在则更新，不存在则插入数据
	 */
	// @Test
	@Rollback(false)
	public void insertOrupdate() {
		PosCardDetailEntity d = new PosCardDetailEntity();
		d.setTradeSerialNo("232132321");
		d.setInvoiceNo("YS11111");
		d.setInvoiceType("预收单");
		// d.setAmount(BigDecimal.valueOf(2000));
		d.setOccupateAmount(BigDecimal.valueOf(2000));
		d.setUnVerifyAmount(BigDecimal.valueOf(2000));
		pdaPosManageService.insertOrUpdateDetail(d);
	}

	//@Test
	@Rollback(false)
	public void testQueryPosCardData() {
		PosCardManageDto dto = new PosCardManageDto();
		dto.setTradeSerialNo("092331623438");
		pdaPosManageService.queryPosCardData(dto);
	}

	/**
	 * 单元测试数据
	 */
	/*
	 * @Override public String query() {
	 *//**
	 * 设置类型List
	 */
	/*
	 * List<String> bizTypes = new ArrayList<String>();
	 *//**
	 * ORG_TRANS_DEPARTMENT="TRANS_DEPARTMENT";
	 * ORG_TRANSFER_CENTER="TRANSFER_CENTER"; ORG_AIR_DISPATCH="AIR_DISPATCH";
	 * ORG_DIVISION="DIVISION"; ORG_BIG_REGION="BIG_REGION";
	 * ORG_SMALL_REGION="SMALL_REGION";
	 * ORG_IS_DELIVER_SCHEDULE="IS_DELIVER_SCHEDULE";
	 */
	/*
	 * bizTypes.add("SALES_DEPARTMENT");//营业部 //bizTypes.add("SMALL_REGION");
	 * //bizTypes.add("BIG_REGION"); // 调用接口获取数据 OrgAdministrativeInfoEntity
	 * entityInfo = orgAdministrativeInfoComplexService
	 * .queryOrgAdministrativeInfoByCode("W0113080404",bizTypes);
	 * 
	 * // 获取刷卡部门的组织标杆编码 String cardDeptBMCode = entityInfo.getUnifiedCode();
	 * 
	 * // 获取营业区名称：上级组织名称 String businessAreaName =
	 * entityInfo.getParentOrgName();
	 * 
	 * // 获取营业区编码：上级组织编码 String businessAreaCode =
	 * entityInfo.getParentOrgCode();
	 * 
	 * // 获取营业区标杆编码：上级组织标杆编码 String businessAreaBMCode =
	 * entityInfo.getParentOrgUnifiedCode();
	 * 
	 * // 获取财务部门名称：实体财务部名称 String financeDeptName =
	 * entityInfo.getEntityFinanceName();
	 * 
	 * // 获取财务部门编码 String financeDeptCode = entityInfo.getEntityFinance();
	 * 
	 * // 更具财务部编码获取财务部组织标杆编码 OrgAdministrativeInfoEntity entityInfo3 =
	 * orgAdministrativeInfoComplexService
	 * .queryOrgAdministrativeInfoByCode(financeDeptCode); String
	 * financeDeptBMCode = entityInfo3.getUnifiedCode();
	 * 
	 * // 再次调用 // 根据营业区去获取上级部门
	 *//**
	 * 设置营业区的bizTypes
	 */
	/*
	 * bizTypes.clear();//清空所有的值 bizTypes.add("SMALL_REGION");
	 * OrgAdministrativeInfoEntity entityInfo2 =
	 * orgAdministrativeInfoComplexService
	 * .queryOrgAdministrativeInfoByCode(businessAreaCode,bizTypes);
	 * 
	 * // 获取大区名称：上级组织名称 String belongRegionName =
	 * entityInfo2.getParentOrgName();
	 * 
	 * // 获取大区编码：上级组织编码 String belongRegionCode =
	 * entityInfo2.getParentOrgCode();
	 * 
	 * // 获取大区标杆编码：上级组织标杆编码 String belongRegionBMCode =
	 * entityInfo2.getParentOrgUnifiedCode(); return null; }
	 */
	public IPdaPosManageService getPdaPosManageService() {
		return pdaPosManageService;
	}

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}

}
