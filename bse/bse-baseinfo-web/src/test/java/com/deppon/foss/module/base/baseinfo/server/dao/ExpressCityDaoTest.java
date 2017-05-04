package com.deppon.foss.module.base.baseinfo.server.dao;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressCityDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.ExpressCityDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class ExpressCityDaoTest {

    private JdbcTemplate jdbc;
    
    private IExpressCityDao expressCityDao;
    private ExpressCityDao entity = new ExpressCityDao();
   
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	expressCityDao = SpringTestHelper.get().getBeanByInterface(ExpressCityDao.class);
    }

   @After
    public void tearDown() throws Exception {
	   
    }
   
   /**
    * 查询
    * @author foss-qiaolifeng
    * @date 2013-9-6 下午4:18:52
    */
   @Ignore
   @Test
   public void testQueryExpressCityListByCondition(){
	   	ExpressCityQueryDto expressCityQueryDto = new ExpressCityQueryDto();
	   	expressCityQueryDto.setDistrictCode("310000-1");
	   	
	   	long count = expressCityDao.queryExpressCityCountByCondition(expressCityQueryDto);
	   	if(count>0){
	   		List<ExpressCityEntity> rtnList = expressCityDao.queryExpressCityListByCondition(expressCityQueryDto, 0, 1);
	   		Assert.assertNotNull(rtnList);
	   	}
	   
   }
   
   /**
    * 根据ID查询一条落地配/试点城市
    * @author foss-qiaolifeng
    * @date 2013-9-6 下午4:25:10
    */
   @Ignore
   @Test
   public void testQueryOneExpressCityById(){
	   	ExpressCityQueryDto expressCityQueryDto = new ExpressCityQueryDto();
	   	expressCityQueryDto.setId("123123213123");
	   	
	   	ExpressCityEntity entity = expressCityDao.queryOneExpressCityById(expressCityQueryDto);
	   	Assert.assertNotNull(entity);
   }
   
   /**
    * 根据城市编码和有效状态查询落地配/试点城市信息
    * @author foss-qiaolifeng
    * @date 2013-9-6 下午4:25:10
    */
   @Ignore
   @Test
   public void testQueryOneExpressCityListByCodeAndType(){
	   	ExpressCityQueryDto expressCityQueryDto = new ExpressCityQueryDto();
	   	expressCityQueryDto.setDistrictCode("310000-1");
	   	expressCityQueryDto.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP);
	   	expressCityQueryDto.setActive(FossConstants.ACTIVE);
	   	
	   	List<ExpressCityEntity> rtnList = expressCityDao.queryOneExpressCityListByCodeAndType(expressCityQueryDto);
	   	Assert.assertNotNull(rtnList);
   }
   
   /**
    * 新增落地配/试点城市
    * @author foss-qiaolifeng
    * @date 2013-9-6 下午4:25:10
    */
   @Ignore
   @Test
   public void testAddExpressCityEntity(){
	   
	   	Date date = new Date();
	    ExpressCityEntity expressCityEntity = new ExpressCityEntity();
	    expressCityEntity.setDistrictCode("310000-1");
	    expressCityEntity.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP);
	    expressCityEntity.setActive(FossConstants.ACTIVE);
	    expressCityEntity.setCreateTime(date);
	    expressCityEntity.setCreateUserCode("092444");
	    expressCityEntity.setId(UUIDUtils.getUUID());
	    expressCityEntity.setModifyTime(date);
	    expressCityEntity.setModifyUserCode("092444");
	    expressCityEntity.setVersionNo(date.getTime());
	   	
	   	int i = expressCityDao.addExpressCityEntity(expressCityEntity);
	   	Assert.assertEquals(i, 1);
   }
   
   /**
    * 修改落地配/试点城市
    * @author foss-qiaolifeng
    * @date 2013-9-6 下午4:25:10
    */
   @Ignore
   @Test
   public void testUpdateExpressCityEntity(){
	   
	    //新增
	    Date date = new Date();
	    ExpressCityEntity expressCityEntity = new ExpressCityEntity();
	    expressCityEntity.setDistrictCode("310000-1");
	    expressCityEntity.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP);
	    expressCityEntity.setActive(FossConstants.ACTIVE);
	    expressCityEntity.setCreateTime(date);
	    expressCityEntity.setCreateUserCode("092444");
	    expressCityEntity.setId(UUIDUtils.getUUID());
	    expressCityEntity.setModifyTime(date);
	    expressCityEntity.setModifyUserCode("092444");
	    expressCityEntity.setVersionNo(date.getTime());
	    expressCityDao.addExpressCityEntity(expressCityEntity);
	   
	    //修改
	   	Date date1 = new Date();
	    expressCityEntity.setDistrictCode("110000-1");
	    expressCityEntity.setModifyTime(date1);
	    expressCityEntity.setModifyUserCode("042366");
	    expressCityEntity.setVersionNo(date1.getTime());
	   	
	   	int i = expressCityDao.updateExpressCityEntity(expressCityEntity);
	   	Assert.assertEquals(i, 1);
   }
   
   /**
    * 根据营业部网点编码获取该营业网点所在城市的落地配/试点城市
    * @author foss-qiaolifeng
    * @date 2013-9-6 下午4:25:10
    */
   @Ignore
   @Test
   public void testQueryExpressCityByOrgCode(){
	   	String orgCode = "110000-1";
	    String active = FossConstants.ACTIVE;
	   	
	   	List<ExpressCityEntity> rtnList = expressCityDao.queryExpressCityByOrgCode(orgCode, active);
	   	Assert.assertNotNull(rtnList);
   }
   
}
