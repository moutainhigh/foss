package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

import com.deppon.foss.framework.entity.IUser;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceIndustryEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedEntity;
import com.deppon.foss.module.pickup.pricing.server.util.TestUtil;
import com.deppon.foss.util.UUIDUtils;

public class ValueAddedDaotest extends TestCase {
	static PriceValueAddedEntity record = new PriceValueAddedEntity();
	static IPopValueAddedDao popValueAddedDao;
	static {
		// PriceValueAddedEntity record = new PriceValueAddedEntity();
		popValueAddedDao = TestUtil.popValueAddedDao;
		record.setActive("Y");
		record.setArrvRegionId("232423");
		record.setArrvRegionName("1312312");
		record.setBeginTime(new Date());
		record.setBusinessBeginTime("11");
		record.setBusinessEndTime("1");
		record.setCode("ZZFW");
		record.setCreateDate(new Date());
		record.setCreateOrgCode("w121312");
		record.setCreateUser("106138");
		record.setCreateUserName("new name");
		record.setCurrencyCode("RMB");
		record.setDeptRegionId("123465");
		record.setDeptRegionName("香港大区");
		record.setRemark("1234567");
		record.setEndTime(new Date());
		record.setId(UUIDUtils.getUUID());
		record.setModifyDate(new Date());
		record.setModifyOrgCode("w0101");
		record.setModifyUser("106138");
		record.setModifyUserName("123456");
		record.setName("增值服务");
		record.setPricingEntryCode("1111");
		record.setSubType("subtype");
		record.setVersionNo(new Date().getTime());
		record.setArrvRegionId("2111231");
		record.setBeginTime(new Date());
		record.setActive("Y");
		record.setCode("BF");
		record.setType("ZZFW");
		record.setPricingEntryCode("zzzz");
	}

	@Test
	public void testInsert() {
		// PriceValueAddedEntity record = new PriceValueAddedEntity();
		// record.setArrvRegionId("2111231");
		// record.setBeginTime(new Date());
		// record.setId("adsdasdas");
		// record.setActive("Y");
		// record.setCode("BF");
		popValueAddedDao.insertSelective(record);
	}

	@Test
	public void testUpdate() {
		PriceValueAddedEntity record = new PriceValueAddedEntity();
		record.setArrvRegionId("2111231");
		record.setBeginTime(new Date());
		record.setId("31a65256-61c0-49b9-83a5-f0385558e0bd");
		record.setActive("N");
		record.setCode("ZZFWW");
		popValueAddedDao.updateValueAdded(record);

	}

	@Test
	public void testSelectByPrimaryKey() {
		PriceValueAddedEntity recordAddedEntity = popValueAddedDao.selectByPrimaryKey("31a65256-61c0-49b9-83a5-f0385558e0bd");

		// System.err.println(recordAddedEntity.toString());

	}

	@Test
	public void testSelectByCoditionSq() {
		PriceValueAddedEntity record = new PriceValueAddedEntity();
		// record.setArrvRegionId("2111231");
		// record.setBeginTime(new Date());
		// record.setId("31a65256-61c0-49b9-83a5-f0385558e0bd");
		record.setActive("N");
		record.setCode("ZZFWW");
		List<PriceValueAddedEntity> recordAddedEntity = popValueAddedDao.selectByCoditionSq(record);
		System.err.println(recordAddedEntity.toString());

	}

	@Test
	public void testSelectByCodition() {
		PriceValueAddedEntity record = new PriceValueAddedEntity();
		List<String> valueAddedIds = new ArrayList<String>();
		valueAddedIds.add("31a65256-61c0-49b9-83a5-f0385558e0bd");
		valueAddedIds.add("adsdasds");

		record.setValueAddedIds(valueAddedIds);
		// record.setArrvRegionId("2111231");
		// record.setBeginTime(new Date());
		// record.setId("31a65256-61c0-49b9-83a5-f0385558e0bd");
		// record.setActive("N");
		// record.setCode("ZZFWW");
		List<PriceValueAddedEntity> recordAddedEntity = popValueAddedDao.selectByCodition(record);
		System.err.println(recordAddedEntity.toString());

	}

	@Test
	public void testCountByCodition() {
		PriceValueAddedEntity record = new PriceValueAddedEntity();
		List<String> valueAddedIds = new ArrayList<String>();
		valueAddedIds.add("31a65256-61c0-49b9-83a5-f0385558e0bd");
		valueAddedIds.add("adsdasds");

		record.setValueAddedIds(valueAddedIds);
		// record.setArrvRegionId("2111231");
		// record.setBeginTime(new Date());
		// record.setId("31a65256-61c0-49b9-83a5-f0385558e0bd");
		// record.setActive("N");
		// record.setCode("ZZFWW");
		long recordAddedEntity = popValueAddedDao.countByCodition(record);
		System.err.println(recordAddedEntity);

	}

	@Test
	public void testDeleteValueAdded() {
		List<String> valueAddedIds = new ArrayList<String>();
		valueAddedIds.add("adsdasds");
		popValueAddedDao.deleteValueAdded(valueAddedIds);

	}

	@Test
	public void testActiveValueAdded() {
		List<String> valueAddedIds = new ArrayList<String>();
		valueAddedIds.add("adsdasds");
		popValueAddedDao.activeValueAdded(valueAddedIds);

	}

	@Test
	public void testSelectByName() {
		PriceValueAddedEntity record = new PriceValueAddedEntity();
		record.setName("增值服务");
		record.setType("ZZFW");
		List<PriceValueAddedEntity> r = popValueAddedDao.selectByName(record);
		System.err.println(r);

	}

	@Test
	public void testSelectByCoditionNew() {
		PriceValueAddedEntity record = new PriceValueAddedEntity();
		record.setName("增值服务");
		record.setType("ZZFW");
		List<PriceValueAddedEntity> r = popValueAddedDao.selectByCoditionNew(record, 0, 10);
		System.err.println(r);

	}

	@Test
	public void testinsertPriceProductEntity() {
		PriceProductEntity product = new PriceProductEntity();
		product.setCode("FLF");
		product.setName("卡航");
		product.setId(UUIDUtils.getUUID());
		product.setTableId("10111");
		product.setTableName("10111");

		popValueAddedDao.insertPriceProductEntity(product);
	}

	@Test
	public void testselectPriceProductEntityByValueAddedId() {
		popValueAddedDao.selectPriceProductEntityByValueAddedId("10111", "10111");
	}

	@Test
	public void testinsertPriceIndustryEntity() {
		PriceIndustryEntity industry = new PriceIndustryEntity();
		industry.setTableId("10111");
		industry.setTableName("10111");
		industry.setFirstTradecode("1");
		industry.setSencondTradecode("1");		
		industry.setSencondTradeName("1");
		industry.setId(UUIDUtils.getUUID());


		popValueAddedDao.insertPriceIndustryEntity(industry);
	}

	@Test
	public void testselectPriceIndustryEntityByValueAddedId(){
		popValueAddedDao.selectPriceIndustryEntityByValueAddedId("10111", "10111");
	}
	@Test
	public void testdeleteIndustryEntityById(){
		popValueAddedDao.deleteIndustryEntityById("10111");
	}
	@Test
	public void testdeleteProductEntityById(){
		popValueAddedDao.deleteProductEntityById("10111");
	}
}
