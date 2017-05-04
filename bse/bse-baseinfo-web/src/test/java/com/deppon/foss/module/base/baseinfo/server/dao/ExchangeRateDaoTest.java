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
package com.deppon.foss.module.base.baseinfo.server.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IExchangeRateDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 汇率信息单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-15 下午6:37:17 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-15 下午6:37:17
 * @since
 * @version
 */
public class ExchangeRateDaoTest {

    private JdbcTemplate jdbc;
    
    private IExchangeRateDao exchangeRateDao;
    private ExchangeRateEntity entity = new ExchangeRateEntity();
    
    //编码
    private String code;
    

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	exchangeRateDao = SpringTestHelper.get().getBeanByInterface(IExchangeRateDao.class);
    }

    /**
     * <p>清空测试数据 </p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-15 下午6:39:43
     * @throws Exception
     * @see
     */
   @After
    public void tearDown() throws Exception {
//	jdbc.execute("delete from BSE.T_BAS_EXCHANGE_RATE t where t.VIRTUAL_CODE = '"+code+"'");
    }


    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.ExchangeRateDao#addExchangeRate(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity)}.
     */
    @Ignore
    @Test
    public void testAddExchangeRate() {
	code = UUIDUtils.getUUID();
	entity.setId(code);
	entity.setVirtualCode(code);
	entity.setCreateUser("zhangsan");
	entity.setCreateDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setCurrency("美元");
	entity.setExchange(new BigDecimal(0.1616));
	entity.setBeginTime(new Date());
	entity.setEndTime(new Date());
	entity.setModifyDate(new Date());
	entity.setModifyUser("zhangsan");
	
	int result = exchangeRateDao.addExchangeRate(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.ExchangeRateDao#updateExchangeRate(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateExchangeRate() {
	//添加一条信息
	testAddExchangeRate();
	entity.setId(code);
	entity.setVirtualCode(code);
	entity.setCreateUser("zhangsan1");
	entity.setCreateDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setCurrency("美元");
	entity.setExchange(new BigDecimal(0.1617));
	entity.setBeginTime(new Date());
	entity.setEndTime(new Date());
	entity.setModifyDate(new Date());
	entity.setModifyUser("zhangsan1");
	
	int result = exchangeRateDao.updateExchangeRate(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.ExchangeRateDao#deleteExchangeRateByVirtualCode(java.util.List, java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteExchangeRateByVirtualCode() {
	//添加一条信息
	testAddExchangeRate();
	
	List<String> list = new ArrayList<String>();
	list.add(code);
	
	int result = exchangeRateDao.deleteExchangeRateByVirtualCode(list, "zhanglong");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.ExchangeRateDao#queryAllExchangeRate(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryAllExchangeRate() {
	//添加一条信息
	testAddExchangeRate();
	
	List<ExchangeRateEntity> list = exchangeRateDao.queryAllExchangeRate(entity, 10, 0);
	
	Assert.assertTrue(list.size() > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.ExchangeRateDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity)}.
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	//添加一条信息
	testAddExchangeRate();
	
	long result = exchangeRateDao.queryRecordCount(entity);
	
	Assert.assertTrue(result > 0);
    }

}
