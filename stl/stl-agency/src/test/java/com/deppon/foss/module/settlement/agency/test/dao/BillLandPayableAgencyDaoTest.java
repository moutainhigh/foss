package com.deppon.foss.module.settlement.agency.test.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.dao.IBillLandPayableAgencyDao;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto;
import com.deppon.foss.module.settlement.agency.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.util.define.FossConstants;

public class BillLandPayableAgencyDaoTest extends BaseTestCase {
	@Autowired
	protected IBillLandPayableAgencyDao billLandPayableAgencyDao;
	
	/**
	 * 根据传入获取一到多条应付单信息(分页)
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 上午11:04:53
	 */
	@Test
	@Rollback(true)
	public void testQueryBillPayableEntityParamsForPage(){
		List<BillPayableEntity> list = billLandPayableAgencyDao.queryBillPayableEntityParams(getDto(), 1, 20);
		Assert.assertTrue(list.size()>0);
	}
	
	/**
	 * 根据传入获取一到多条应付单信息(不分页)
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 上午11:04:53
	 */
	@Test
	@Rollback(true)
	public void testQueryBillPayableEntityParams(){
		List<BillPayableEntity> list = billLandPayableAgencyDao.queryBillPayableEntityParams(getDto());
		Assert.assertTrue(list.size()>0);
	}
	
	/**
	 * 根据传入获取一到多条应收单总数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 上午11:06:32
	 */
	@Test
	@Rollback(true)
	public void testQueryBillPayableEntityParamsCount(){
		long i = billLandPayableAgencyDao.queryBillPayableEntityParamsCount(getDto());
		Assert.assertTrue(i>=0);
	}
	
	/**
	 * 根据传入获取一到多条应收单总数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 上午11:06:32
	 */
	@Test
	@Rollback(true)
	public void testQueryByPayableNOs(){
		List<BillPayableEntity> list = billLandPayableAgencyDao.queryByPayableNOs(getDto(), 1, 20);
		Assert.assertTrue(list.size()>0);
	}
	
	/**
	 * 根据传入的一到多个应收单号，获取一到多条应收单总数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 上午11:06:32
	 */
	@Test
	@Rollback(true)
	public void testQueryByPayableNOsCount(){
		long i = billLandPayableAgencyDao.queryByPayableNOsCount(getDto());
		Assert.assertTrue(i>=0);
	}
	
	/**
	 * 根据传入获取一到多条应收单总数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 上午11:06:32
	 */
	@Test
	@Rollback(true)
	public void testQueryBySourceBillNOsForPage(){
		List<BillPayableEntity> list = billLandPayableAgencyDao.queryBySourceBillNOs(getDto(), 1, 20);
		Assert.assertTrue(list.size()>0);
	}
	
	/**
	 * 根据传入获取一到多条应收单总数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 上午11:06:32
	 */
	@Test
	@Rollback(true)
	public void testQueryBySourceBillNOs(){
		List<BillPayableEntity> list = billLandPayableAgencyDao.queryBySourceBillNOs(getDto());
		Assert.assertTrue(list.size()>0);
	}
	
	/**
	 * 根据传入获取一到多条应收单总数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 上午11:06:32
	 */
	@Test
	@Rollback(true)
	public void testQueryBySourceBillNOsCount(){
		long i = billLandPayableAgencyDao.queryBySourceBillNOsCount(getDto());
		Assert.assertTrue(i>=0);
	}
	
	/**
	 * 
	 * 获取查询参数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 下午3:56:09
	 * @return
	 */
	private BillPayableAgencyDto getDto(){
		BillPayableAgencyDto dto = new BillPayableAgencyDto();
		dto.setActive(FossConstants.ACTIVE);
		dto.setIsRedBack(FossConstants.NO);
		dto.setCustomerCode("LDP00009");
		dto.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);
		dto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);
		List<String> reNos = new ArrayList<String>();
		reNos.add("YF9300000124");
		reNos.add("YF9300000126");
		dto.setPayableNos(reNos);
		
		List<String> sources = new ArrayList<String>();
		sources.add("9909021301");
		dto.setSourceBillNos(sources);
		return dto;
	}
}
