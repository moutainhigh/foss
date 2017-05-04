package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopValueAddedDetailDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedDetailEntity;
import com.deppon.foss.module.pickup.pricing.server.util.TestUtil;
import com.deppon.foss.util.UUIDUtils;

public class ValueAddedDetailDaotest extends TestCase {
	static PriceValueAddedDetailEntity record=new PriceValueAddedDetailEntity(); 
	static	IPopValueAddedDetailDao popValueAddedDetailDao = TestUtil.popValueAddedDetailDao;
	static {
		record.setActive("Y");
		record.setBusinessLeftrange("111");
		record.setBusinessRightrange("22222");
		record.setBusinessType("1111");
		record.setCaculateType("1111");
		record.setCanModify("Y");
		record.setCanNotCharge("Y");
		record.setCreateDate(new Date());
		record.setCreateUser("1056138");
		record.setDefaultFee(new BigDecimal(11.1));
		record.setFeeRate("FEE");
		record.setId(UUIDUtils.getUUID());
		record.setLeftrange(new BigDecimal(11.1));
		record.setMaxFee(11.1);
		record.setMinFee(11.1);
		record.setMinInsuranceFee(11.1);
		record.setModifyDate(new Date());
		record.setMinVote(110.1);
		record.setMaxVote(110.1);

		record.setModifyUser("106138");
		record.setPackageType("31231");
		record.setPriceRuleId("3131");
		record.setRefundType("refundType");
		record.setReturnbillType("returnbillType");
		record.setRightrange(new BigDecimal(11.1));
		record.setSubType("subType");
		record.setTogeterCategory("togeterCategory");
		record.setValueaddId("1111");
		record.setValueaddSubType("123123");
		record.setValueaddType("313131");
		record.setVersionNo(new Date().getTime());
		
		
		
		
	} 	
	@Test
	public void testInsert() {
		popValueAddedDetailDao.insertSelective(record);
	}
	@Test
	public void testUpdate(){
		
		popValueAddedDetailDao.updateValueAddedDetailByPrimaryKey(record);
		
	}
	@Test
	public void testDeleteByPrimaryKey(){
		popValueAddedDetailDao.deleteByPrimaryKey("11");
	}
	@Test
	public void testActiveValueAddedDetailByValueAddedIds(){
		List<String> valueAddedIds=new ArrayList<String>();
		valueAddedIds.add("1");
		valueAddedIds.add("2");
		popValueAddedDetailDao.activeValueAddedDetailByValueAddedIds(valueAddedIds);
		
	}
	@Test
	public void testDeleteValueAddedDetailByValueAddedIds(){
		List<String> valueAddedIds=new ArrayList<String>();
		valueAddedIds.add("1");
		valueAddedIds.add("2");
		popValueAddedDetailDao.deleteValueAddedDetailByValueAddedIds(valueAddedIds);
	}
	@Test
	public void testSelectByValueAddedId(){
	List<PriceValueAddedDetailEntity>  detailEntities=	popValueAddedDetailDao.selectByValueAddedId("1111");
		System.err.println(detailEntities);
	}

}
