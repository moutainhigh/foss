package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopValueAddedDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricingValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductItemService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionValueAddService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceIndustryEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricingCommonException;
import com.deppon.foss.util.define.FossConstants;
import com.primeton.das.entity.impl.hibernate.mapping.Array;

import junit.framework.TestCase;

/**
 * 
 * <p>
 * Description：增值服务service测试类<br />
 * </p>
 * 
 * @title PopValueAddServiceTest.java
 * @package com.deppon.foss.module.pickup.pricing.server.service.impl
 * @author xx
 * @version 0.1 2014年10月24日
 */
public class PopValueAddServiceTest extends TestCase {
	/**
	 * 增值服务对象
	 */
	public static PopValueAddedService valueAddedService = new PopValueAddedService();

	@Before
	protected void setUp() throws Exception {
		popValueAddedDaoJMock();
		popValueAddedDaoDetailJMock();
		employeeServiceJMock();
		regionValueAddServiceJmock();
		productItemServiceJMock();
		productServiceJmock();
		goodsTypeServiceJmock();
		pricingValueAddedDaoJmock();
		contextInit();
	}

	private void popValueAddedDaoDetailJMock() {
		Mockery popValueAddDaoDeailMockery = new Mockery();
		final IPopValueAddedDetailDao popValueAddedDetailDao = popValueAddDaoDeailMockery
				.mock(IPopValueAddedDetailDao.class);
		popValueAddDaoDeailMockery.checking(new Expectations() {
			{

				allowing(popValueAddedDetailDao).selectByValueAddedId("test");
				List<PriceValueAddedDetailEntity> oldPriceValueAddedDetailEntityList = new ArrayList<PriceValueAddedDetailEntity>();
				PriceValueAddedDetailEntity p = new PriceValueAddedDetailEntity();
				p.setValueaddSubType("aaa");
				oldPriceValueAddedDetailEntityList.add(p);
				will(returnValue(oldPriceValueAddedDetailEntityList));

				allowing(popValueAddedDetailDao).insertSelective(with(any(PriceValueAddedDetailEntity.class)));
				allowing(popValueAddedDetailDao).updateValueAddedDetailByPrimaryKey(with(any(PriceValueAddedDetailEntity.class)));
				allowing(popValueAddedDetailDao).updateValueAddedDetailByValueAddedId(with(any(PriceValueAddedDetailEntity.class)));
				allowing(popValueAddedDetailDao).deleteValueAddedDetailByValueAddedIds(with(any(ArrayList.class)));
				allowing(popValueAddedDetailDao).deleteByPrimaryKey(with(any(String.class)));
				allowing(popValueAddedDetailDao).activeValueAddedDetailByValueAddedIds(with(any(ArrayList.class)));
				allowing(popValueAddedDetailDao).deleteValueAddedDetailByValueAddedIds(with(any(ArrayList.class)));

				;

			}
		});
		valueAddedService.setPopValueAddedDetailDao(popValueAddedDetailDao);
	}

	private void pricingValueAddedDaoJmock() {
		Mockery pricingValueAddedDaoMockery = new Mockery();
		final IPricingValueAddedDao pricingValueAddedDao = pricingValueAddedDaoMockery
				.mock(IPricingValueAddedDao.class);
		pricingValueAddedDaoMockery.checking(new Expectations() {
			{

				allowing(pricingValueAddedDao).searchValueAddedType(with(any(PriceEntity.class)));
				will(returnValue(null));

				;

			}
		});
		valueAddedService.setPricingValueAddedDao(pricingValueAddedDao);
		
	}

	private void goodsTypeServiceJmock() {
		Mockery goodsTypeServiceMockery = new Mockery();
		final IGoodsTypeService goodsTypeService = goodsTypeServiceMockery
				.mock(IGoodsTypeService.class);
		goodsTypeServiceMockery.checking(new Expectations() {
			{

				allowing(goodsTypeService).findGoodsTypeByCondiction(with(any(GoodsTypeEntity.class)));
				will(returnValue(null));

				;

			}
		});
		valueAddedService.setGoodsTypeService(goodsTypeService);
	}

	private void productServiceJmock() {
		Mockery goodsTypeServiceMockery = new Mockery();
		final IProductService productService = goodsTypeServiceMockery
				.mock(IProductService.class);
		goodsTypeServiceMockery.checking(new Expectations() {
			{

				allowing(productService).findExternalProductByCondition(with(any(ProductDto.class)), with(any(Date.class)));
				will(returnValue(null));

				;

			}
		});
		valueAddedService.setProductService(productService);
	}

	private void productItemServiceJMock() {
		Mockery productItemServiceMockery = new Mockery();
		final IProductItemService productItemService = productItemServiceMockery.mock(IProductItemService.class);
		productItemServiceMockery.checking(new Expectations() {
			{
				allowing(productItemService).findProductItemByCondiction(with(any(ProductItemEntity.class)));
				will(returnValue(null));

			}
		});
		valueAddedService.setProductItemService(productItemService);
	}

	private void regionValueAddServiceJmock() {
		Mockery regionValueAddServiceMockery = new Mockery();
		final IRegionValueAddService regionValueAddService = regionValueAddServiceMockery.mock(IRegionValueAddService.class);
		regionValueAddServiceMockery.checking(new Expectations() {
			{
				allowing(regionValueAddService).searchRegionByID("1111",PricingConstants.VALUEADD_REGION);
				PriceRegionValueAddEntity priceRegionEntity =new  PriceRegionValueAddEntity();
				priceRegionEntity.setRegionName("xxx");
				will(returnValue(priceRegionEntity));

			}
		});
		valueAddedService.setRegionValueAddService(regionValueAddService);
	}

	private void employeeServiceJMock() {
		Mockery employeeServiceMockery = new Mockery();
		final IEmployeeService employeeService = employeeServiceMockery.mock(IEmployeeService.class);
		employeeServiceMockery.checking(new Expectations() {
			{
				allowing(employeeService).queryEmployeeByEmpCode("106138");
				EmployeeEntity employeeEntity=new EmployeeEntity();
				employeeEntity.setEmpName("xx");
				will(returnValue(employeeEntity));

			}
		});
		valueAddedService.setEmployeeService(employeeService);
	}

	private void popValueAddedDaoJMock() {
		Mockery popValueAddDaoMockery = new Mockery();
		final IPopValueAddedDao popValueAddedDao = popValueAddDaoMockery.mock(IPopValueAddedDao.class);
		popValueAddDaoMockery.checking(new Expectations() {
			{
				PriceValueAddedEntity priceValueAddedEntity = new PriceValueAddedEntity();
				priceValueAddedEntity.setName("增值服务方案名称测试");
				allowing(popValueAddedDao).selectByName(priceValueAddedEntity);
				priceValueAddedEntity.setName("增值服务方案名称测试");
				priceValueAddedEntity.setId("test");
				List<PriceValueAddedEntity> priceValueAddedEntities = new ArrayList<PriceValueAddedEntity>();
				priceValueAddedEntities.add(priceValueAddedEntity);
				will(returnValue(priceValueAddedEntities));

				PriceValueAddedEntity priceValueAddedEntitynew = new PriceValueAddedEntity();
				priceValueAddedEntitynew.setName("增值服务方案名称测试1");
				allowing(popValueAddedDao).selectByName(priceValueAddedEntitynew);
				// priceValueAddedEntity.setName("增值服务方案名称测试");
				// priceValueAddedEntity.setId("test");
				// List<PriceValueAddedEntity> priceValueAddedEntities1 = new
				// ArrayList<PriceValueAddedEntity>();
				// priceValueAddedEntities.add(priceValueAddedEntity);
				will(returnValue(new ArrayList<PriceValueAddedEntity>()));

				PriceValueAddedEntity priceValueAddedEntity1 = new PriceValueAddedEntity();
				priceValueAddedEntity1.setCode("YYYY");
				allowing(popValueAddedDao).selectByCoditionSq(priceValueAddedEntity1);
				priceValueAddedEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);
				priceValueAddedEntities.add(priceValueAddedEntity);
				will(returnValue(priceValueAddedEntities));

				PriceValueAddedEntity newAddedEntity = new PriceValueAddedEntity();
				allowing(popValueAddedDao).selectByCoditionSq(with(any(PriceValueAddedEntity.class)));
				will(returnValue(priceValueAddedEntities));

				allowing(popValueAddedDao).updateValueAdded(with(any(PriceValueAddedEntity.class)));
				allowing(popValueAddedDao).insertSelective(with(any(PriceValueAddedEntity.class)));
				allowing(popValueAddedDao).insertPriceProductEntity(with(any(PriceProductEntity.class)));
				allowing(popValueAddedDao).insertPriceIndustryEntity(with(any(PriceIndustryEntity.class)));

				allowing(popValueAddedDao).selectPriceProductEntityByValueAddedId("test", "T_POP_VALUEADDED");
				will(returnValue(new ArrayList<PriceProductEntity>()));
				allowing(popValueAddedDao).selectPriceIndustryEntityByValueAddedId("test", "T_POP_VALUEADDED");
				will(returnValue(new ArrayList<PriceIndustryEntity>()));
				
				allowing(popValueAddedDao).selectPriceProductEntityByValueAddedId("test", "T_POP_VALUEADDED");
				allowing(popValueAddedDao).selectByCoditionNew(with(any(PriceValueAddedEntity.class)),with(any(int.class)),with(any(int.class)));
				List<PriceValueAddedEntity> priceValueAddedEntities2= new ArrayList<PriceValueAddedEntity>();
				PriceValueAddedEntity  priceValueAddedEntitytest= new PriceValueAddedEntity();
				priceValueAddedEntitytest.setModifyUser("106138");
				priceValueAddedEntitytest.setArrvRegionId("1111");
				priceValueAddedEntitytest.setDeptRegionId("1111");
				// priceValueAddedEntity.setName("增值服务方案名称测试");
				// priceValueAddedEntity.setId("test");
				// List<PriceValueAddedEntity> priceValueAddedEntities1 = new
				// ArrayList<PriceValueAddedEntity>();
				priceValueAddedEntities2.add(priceValueAddedEntitytest);
				will(returnValue(priceValueAddedEntities2));
				allowing(popValueAddedDao).countByCodition(with(any(PriceValueAddedEntity.class)));
				will(returnValue(100L));
				allowing(popValueAddedDao).selectByCodition(with(any(PriceValueAddedEntity.class)));
				List<PriceValueAddedEntity> priceValueAddedEntities3= new ArrayList<PriceValueAddedEntity>();

				PriceValueAddedEntity  priceValueAddedEntitytest3= new PriceValueAddedEntity();
				priceValueAddedEntitytest3.setBeginTime(new Date());
				priceValueAddedEntities3.add(priceValueAddedEntitytest3);
				priceValueAddedEntitytest3.setId("test");
				will(returnValue(priceValueAddedEntities3));
				allowing(popValueAddedDao).activeValueAdded(with(any(ArrayList.class)));
				allowing(popValueAddedDao).deleteValueAdded(with(any(ArrayList.class)));

				allowing(popValueAddedDao).selectByPrimaryKey("test");
				will(returnValue(priceValueAddedEntitytest));


			}
		});
		valueAddedService.setPopValueAddedDao(popValueAddedDao);
	}

	private void contextInit() {
		OrgAdministrativeInfoEntity org = new OrgAdministrativeInfoEntity();
		org.setCode("W110");
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setDepartment(org);
		employeeEntity.setEmpCode("106138");
		UserEntity user = new UserEntity();
		user.setEmployee(employeeEntity);
		UserContext.setCurrentUser(user);

	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * 
	 * <p>
	 * Description:增值服务新增<br />
	 * </p>
	 ***
	 * scan1：增值服务方案名称存在,前台传递OperateTypeForAddAndUpdateVersion=upgradedVersion
	 * 
	 * @author xx
	 * @version 0.1 2014年10月24日 void
	 */
	@Test
	public void testAddValueAdded_scan1() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		entity.setId("test");
		entity.setName("增值服务方案名称测试");
		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		try {

			valueAddedService.addValueAdded(entity, detailEntities);
		} catch (PricingCommonException e) {
			assertEquals("增值方案名称已存在!", e.getErrorCode());
		}

	}

	/**
	 * 
	 * <p>
	 * Description:增值服务新增<br />
	 * </p>
	 ***
	 * scan2：增值服务方案名称存在,前台传递OperateTypeForAddAndUpdateVersion!=upgradedVersion
	 * 抛出异常 用户未登陆
	 * 
	 * @author xx
	 * @version 0.1 2014年10月24日 void
	 */
	@Test
	public void testAddValueAdded_scan2() {
		UserContext.setCurrentUser(null);
		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		entity.setId("test");
		entity.setName("增值服务方案名称测试");
		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		try {

			valueAddedService.addValueAdded(entity, detailEntities);
		} catch (PricingCommonException e) {
			assertEquals(PricingCommonException.NOT_LOGIN, e.getErrorCode());
		}

	}

	/**
	 * 
	 * <p>
	 * Description:增值服务新增<br />
	 * </p>
	 ***
	 * scan2：增值服务方案名称,前台传递OperateTypeForAddAndUpdateVersion!=upgradedVersion
	 * 
	 * @author xx
	 * @version 0.1 2014年10月24日 void
	 */
	@Test
	public void testAddValueAdded_scan3() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		// entity.setId("test");
		// entity.setName("增值服务方案名称测试");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setBeginTime(new Date());
		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aa");
		detailEntities.add(priceValueAddedDetailEntity);
		valueAddedService.addValueAdded(entity, detailEntities);

	}

	/**
	 * 
	 * <p>
	 * Description:增值服务新增<br />
	 * </p>
	 ***
	 * scan4：增值服务方案名称,是否激活为否
	 * 
	 * @author xx
	 * @version 0.1 2014年10月24日 void
	 */
	@Test
	public void testAddValueAdded_scan4() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		// entity.setId("test");
		// entity.setName("增值服务方案名称测试");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.INACTIVE);
		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setBeginTime(new Date());
		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		valueAddedService.addValueAdded(entity, detailEntities);

	}

	/**
	 * 
	 * <p>
	 * Description:增值服务新增<br />
	 * </p>
	 ***
	 * scan4：包含产品及行业
	 * 
	 * @author xx
	 * @version 0.1 2014年10月24日 void
	 */
	@Test
	public void testAddValueAdded_scan5() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		// entity.setId("test");
		// entity.setName("增值服务方案名称测试");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setBeginTime(new Date());
		List<PriceProductEntity> productEntities = new ArrayList<PriceProductEntity>();
		PriceProductEntity priceProductEntity = new PriceProductEntity();
		productEntities.add(priceProductEntity);
		entity.setProductList(productEntities);
		List<PriceIndustryEntity> industryEntities = new ArrayList<PriceIndustryEntity>();
		PriceIndustryEntity priceIndustryEntity = new PriceIndustryEntity();
		industryEntities.add(priceIndustryEntity);
		entity.setIndustryList(industryEntities);

		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aaa");
		priceValueAddedDetailEntity.setDefaultFee(new BigDecimal(1000));
		priceValueAddedDetailEntity.setLeftrange(new BigDecimal(1000));
		priceValueAddedDetailEntity.setRightrange(new BigDecimal(1000));
		detailEntities.add(priceValueAddedDetailEntity);

		valueAddedService.addValueAdded(entity, detailEntities);

	}

	/**
	 * 
	 * <p>
	 * Description:增值服务新增<br />
	 * </p>
	 ***
	 * scan4：包含产品及行业
	 * 
	 * @author xx
	 * @version 0.1 2014年10月24日 void
	 */
	@Test
	public void testAddValueAdded_scan6() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		// entity.setId("test");
		// entity.setName("增值服务方案名称测试");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setBeginTime(new Date());
		entity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_SHSL);
		List<PriceProductEntity> productEntities = new ArrayList<PriceProductEntity>();
		PriceProductEntity priceProductEntity = new PriceProductEntity();
		productEntities.add(priceProductEntity);
		entity.setProductList(productEntities);
		List<PriceIndustryEntity> industryEntities = new ArrayList<PriceIndustryEntity>();
		PriceIndustryEntity priceIndustryEntity = new PriceIndustryEntity();
		industryEntities.add(priceIndustryEntity);
		entity.setIndustryList(industryEntities);

		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aaa");
		priceValueAddedDetailEntity.setDefaultFee(new BigDecimal(1000));
		priceValueAddedDetailEntity.setLeftrange(new BigDecimal(1000));
		priceValueAddedDetailEntity.setRightrange(new BigDecimal(1000));
		detailEntities.add(priceValueAddedDetailEntity);

		valueAddedService.addValueAdded(entity, detailEntities);

	}

	/**
	 * 
	 * <p>
	 * Description:增值服务新增<br />
	 * </p>
	 ***
	 * scan4：BF
	 * 
	 * @author xx
	 * @version 0.1 2014年10月24日 void
	 */
	@Test
	public void testAddValueAdded_scan7() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		// entity.setId("test");
		// entity.setName("增值服务方案名称测试");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setBeginTime(new Date());
		List<PriceProductEntity> productEntities = new ArrayList<PriceProductEntity>();
		PriceProductEntity priceProductEntity = new PriceProductEntity();
		productEntities.add(priceProductEntity);
		entity.setProductList(productEntities);
		List<PriceIndustryEntity> industryEntities = new ArrayList<PriceIndustryEntity>();
		PriceIndustryEntity priceIndustryEntity = new PriceIndustryEntity();
		industryEntities.add(priceIndustryEntity);
		entity.setIndustryList(industryEntities);

		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aaa");
		priceValueAddedDetailEntity.setDefaultFee(new BigDecimal(1000));
		priceValueAddedDetailEntity.setLeftrange(new BigDecimal(1000));
		priceValueAddedDetailEntity.setRightrange(new BigDecimal(1000));
		detailEntities.add(priceValueAddedDetailEntity);

		valueAddedService.addValueAdded(entity, detailEntities);

	}

	/**
	 * 
	 * <p>
	 * Description:增值服务新增<br />
	 * </p>
	 ***
	 * scan4：BF
	 * 
	 * @author xx
	 * @version 0.1 2014年10月24日 void
	 */
	@Test
	public void testAddValueAdded_scan8() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		// entity.setId("test");
		// entity.setName("增值服务方案名称测试");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_SH);

		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setBeginTime(new Date());
		List<PriceProductEntity> productEntities = new ArrayList<PriceProductEntity>();
		PriceProductEntity priceProductEntity = new PriceProductEntity();
		productEntities.add(priceProductEntity);
		entity.setProductList(productEntities);
		List<PriceIndustryEntity> industryEntities = new ArrayList<PriceIndustryEntity>();
		PriceIndustryEntity priceIndustryEntity = new PriceIndustryEntity();
		industryEntities.add(priceIndustryEntity);
		entity.setIndustryList(industryEntities);

		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aaa");
		priceValueAddedDetailEntity.setDefaultFee(new BigDecimal(1000));
		priceValueAddedDetailEntity.setLeftrange(new BigDecimal(1000));
		priceValueAddedDetailEntity.setRightrange(new BigDecimal(1000));
		detailEntities.add(priceValueAddedDetailEntity);

		valueAddedService.addValueAdded(entity, detailEntities);

	}

	@Test
	public void testAddValueAdded_scan9() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		// entity.setId("test");
		// entity.setName("增值服务方案名称测试");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_JH);

		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setBeginTime(new Date());
		List<PriceProductEntity> productEntities = new ArrayList<PriceProductEntity>();
		PriceProductEntity priceProductEntity = new PriceProductEntity();
		productEntities.add(priceProductEntity);
		entity.setProductList(productEntities);
		List<PriceIndustryEntity> industryEntities = new ArrayList<PriceIndustryEntity>();
		PriceIndustryEntity priceIndustryEntity = new PriceIndustryEntity();
		industryEntities.add(priceIndustryEntity);
		entity.setIndustryList(industryEntities);

		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aaa");
		priceValueAddedDetailEntity.setDefaultFee(new BigDecimal(1000));
		priceValueAddedDetailEntity.setLeftrange(new BigDecimal(1000));
		priceValueAddedDetailEntity.setRightrange(new BigDecimal(1000));
		detailEntities.add(priceValueAddedDetailEntity);

		valueAddedService.addValueAdded(entity, detailEntities);

	}

	@Test
	public void testAddValueAdded_scan10() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		// entity.setId("test");
		// entity.setName("增值服务方案名称测试");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BZ);

		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setBeginTime(new Date());
		List<PriceProductEntity> productEntities = new ArrayList<PriceProductEntity>();
		PriceProductEntity priceProductEntity = new PriceProductEntity();
		productEntities.add(priceProductEntity);
		entity.setProductList(productEntities);
		List<PriceIndustryEntity> industryEntities = new ArrayList<PriceIndustryEntity>();
		PriceIndustryEntity priceIndustryEntity = new PriceIndustryEntity();
		industryEntities.add(priceIndustryEntity);
		entity.setIndustryList(industryEntities);

		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aaa");
		priceValueAddedDetailEntity.setDefaultFee(new BigDecimal(1000));
		priceValueAddedDetailEntity.setLeftrange(new BigDecimal(1000));
		priceValueAddedDetailEntity.setRightrange(new BigDecimal(1000));
		detailEntities.add(priceValueAddedDetailEntity);

		valueAddedService.addValueAdded(entity, detailEntities);

	}

	@Test
	public void testAddValueAdded_scan11() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		// entity.setId("test");
		// entity.setName("增值服务方案名称测试");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_CCF);

		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setBeginTime(new Date());
		List<PriceProductEntity> productEntities = new ArrayList<PriceProductEntity>();
		PriceProductEntity priceProductEntity = new PriceProductEntity();
		productEntities.add(priceProductEntity);
		entity.setProductList(productEntities);
		List<PriceIndustryEntity> industryEntities = new ArrayList<PriceIndustryEntity>();
		PriceIndustryEntity priceIndustryEntity = new PriceIndustryEntity();
		industryEntities.add(priceIndustryEntity);
		entity.setIndustryList(industryEntities);

		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aaa");
		priceValueAddedDetailEntity.setDefaultFee(new BigDecimal(1000));
		priceValueAddedDetailEntity.setLeftrange(new BigDecimal(1000));
		priceValueAddedDetailEntity.setRightrange(new BigDecimal(1000));

		detailEntities.add(priceValueAddedDetailEntity);

		valueAddedService.addValueAdded(entity, detailEntities);

	}

	@Test
	public void testAddValueAdded_scan12() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		// entity.setId("test");
		// entity.setName("增值服务方案名称测试");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);

		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setBeginTime(new Date());
		List<PriceProductEntity> productEntities = new ArrayList<PriceProductEntity>();
		PriceProductEntity priceProductEntity = new PriceProductEntity();
		productEntities.add(priceProductEntity);
		entity.setProductList(productEntities);
		List<PriceIndustryEntity> industryEntities = new ArrayList<PriceIndustryEntity>();
		PriceIndustryEntity priceIndustryEntity = new PriceIndustryEntity();
		industryEntities.add(priceIndustryEntity);
		entity.setIndustryList(industryEntities);

		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aaa");
		priceValueAddedDetailEntity.setDefaultFee(new BigDecimal(1000));
		priceValueAddedDetailEntity.setLeftrange(new BigDecimal(1000));
		priceValueAddedDetailEntity.setRightrange(new BigDecimal(1000));
		detailEntities.add(priceValueAddedDetailEntity);

		valueAddedService.addValueAdded(entity, detailEntities);

	}

	@Test
	public void testAddValueAdded_scan13() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		// entity.setId("test");
		// entity.setName("增值服务方案名称测试");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_HK);

		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setBeginTime(new Date());
		List<PriceProductEntity> productEntities = new ArrayList<PriceProductEntity>();
		PriceProductEntity priceProductEntity = new PriceProductEntity();
		productEntities.add(priceProductEntity);
		entity.setProductList(productEntities);
		List<PriceIndustryEntity> industryEntities = new ArrayList<PriceIndustryEntity>();
		PriceIndustryEntity priceIndustryEntity = new PriceIndustryEntity();
		industryEntities.add(priceIndustryEntity);
		entity.setIndustryList(industryEntities);

		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aaa");
		priceValueAddedDetailEntity.setDefaultFee(new BigDecimal(1000));
		priceValueAddedDetailEntity.setLeftrange(new BigDecimal(1000));
		priceValueAddedDetailEntity.setRightrange(new BigDecimal(1000));
		detailEntities.add(priceValueAddedDetailEntity);

		valueAddedService.addValueAdded(entity, detailEntities);

	}

	@Test
	public void testAddValueAdded_scan14() {

		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		// entity.setId("test");
		// entity.setName("增值服务方案名称测试");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);

		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setBeginTime(new Date());
		List<PriceProductEntity> productEntities = new ArrayList<PriceProductEntity>();
		PriceProductEntity priceProductEntity = new PriceProductEntity();
		productEntities.add(priceProductEntity);
		entity.setProductList(productEntities);
		List<PriceIndustryEntity> industryEntities = new ArrayList<PriceIndustryEntity>();
		PriceIndustryEntity priceIndustryEntity = new PriceIndustryEntity();
		industryEntities.add(priceIndustryEntity);
		entity.setIndustryList(industryEntities);

		List<PriceValueAddedDetailEntity> detailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aaa");
		priceValueAddedDetailEntity.setDefaultFee(new BigDecimal(1000));
		priceValueAddedDetailEntity.setLeftrange(new BigDecimal(1000));
		priceValueAddedDetailEntity.setRightrange(new BigDecimal(1000));
		detailEntities.add(priceValueAddedDetailEntity);

		valueAddedService.addValueAdded(entity, detailEntities);

	}

	/**
	 * 
	 * <p>
	 * Description:测试修改增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月25日 void
	 */
	@Test
	public void testUpdateValueAdded_scan1() {
		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		entity.setId("test");
		entity.setName("增值服务方案名称测试1");
		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);
		List<PriceProductEntity> productEntities = new ArrayList<PriceProductEntity>();
		PriceProductEntity priceProductEntity = new PriceProductEntity();
		priceProductEntity.setCode("aaa");
		productEntities.add(priceProductEntity);
		entity.setProductList(productEntities);
		List<PriceIndustryEntity> industryEntities = new ArrayList<PriceIndustryEntity>();
		PriceIndustryEntity priceIndustryEntity = new PriceIndustryEntity();
		priceIndustryEntity.setId("in");
		industryEntities.add(priceIndustryEntity);
		entity.setIndustryList(industryEntities);
		List<PriceValueAddedDetailEntity> addedDetailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aaa");
		priceValueAddedDetailEntity.setDefaultFee(new BigDecimal(1000));
		priceValueAddedDetailEntity.setLeftrange(new BigDecimal(1000));
		priceValueAddedDetailEntity.setRightrange(new BigDecimal(1000));
		addedDetailEntities.add(priceValueAddedDetailEntity);
		List<PriceValueAddedDetailEntity> updatedDetailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity updatePriceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setValueaddSubType("aaa");
		priceValueAddedDetailEntity.setDefaultFee(new BigDecimal(1000));
		priceValueAddedDetailEntity.setLeftrange(new BigDecimal(1000));
		priceValueAddedDetailEntity.setRightrange(new BigDecimal(1000));
		updatedDetailEntities.add(updatePriceValueAddedDetailEntity);

		List<PriceValueAddedDetailEntity> deleteDetailEntities = new ArrayList<PriceValueAddedDetailEntity>();
		PriceValueAddedDetailEntity delete = new PriceValueAddedDetailEntity();
		priceValueAddedDetailEntity.setId("test");
		deleteDetailEntities.add(delete);

		valueAddedService.updateValueAdded(entity, addedDetailEntities, updatedDetailEntities, deleteDetailEntities);

	}

	public void testUpdatePriceValueAdded() {
		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		entity.setId("test");
		entity.setName("增值服务方案名称测试1");
		entity.setOperateTypeForAddAndUpdateVersion("upgradedVersion");
		entity.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);
		valueAddedService.updatePriceValueAdded(entity);
	}
	@Test
	public void testSearchValueAddedType() {
		PriceEntity priceEntity=new PriceEntity();
		priceEntity.setActive("Y");
		valueAddedService.searchValueAddedType(priceEntity);
	}
	@Test
	public void testSelectByValueAddedId() {
		valueAddedService.selectByValueAddedId("test");
	}
	@Test
	public void testSelectByCodition() {
		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		entity.setActive("ALL");
		entity.setBeginTime(new Date());
		entity.setType(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED);
		valueAddedService.selectByCodition(entity, 0, 0);
	}
	@Test
	public void testCountByCodition() {
		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		entity.setActive("ALL");
		entity.setBeginTime(new Date());
		entity.setType(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED);
		valueAddedService.countByCodition(entity);
	}
	@Test
	public void testActiveValueAdded() {
		List<String> ids=new ArrayList<String>();
		ids.add("test");
		valueAddedService.activeValueAdded(ids);
	}
	@Test
	public void testDeleteValueAdded() {
		List<String> ids=new ArrayList<String>();
		ids.add("test");
		valueAddedService.deleteValueAdded(ids);
	}
	@Test
	public void testFindProductItemByCondiction() {
		valueAddedService.findProductItemByCondiction();
		
	}
	@Test
	public void testFindProductByCondition() {
		valueAddedService.findProductByCondition();
		}
	@Test
	public void testFindGoodsTypeByCondiction() {
		valueAddedService.findGoodsTypeByCondiction();
		}
	@Test
	public void testSearchValueAddedPricingValuationByCondition() {
//		valueAddedService.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
	}
	@Test
	public void siftValuationBillingRuleService() {
		// TODO Auto-generated method stub
	}
	@Test
	public void testfindTwoLevelProduct() {
		valueAddedService.findTwoLevelProduct();
		}
	@Test
	public void testfindOneLevelProduct() {
		valueAddedService.findOneLevelProduct();
		}
	@Test
	public void testfindThreeLevelProduct() {
		valueAddedService.findThreeLevelProduct();
	}
	@Test
	public void testActiveFast() {
		PriceValueAddedEntity entity = new PriceValueAddedEntity();
		entity.setId("test");
		entity.setBeginTime(new Date());
		valueAddedService.activeFast(entity);
	}
	@Test
	public void siftValueAddRuleService() {
	}

}
