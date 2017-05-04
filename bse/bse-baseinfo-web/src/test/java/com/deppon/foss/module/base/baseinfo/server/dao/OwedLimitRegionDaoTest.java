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
 * PROJECT NAME	: bse-baseinfo-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/OwedLimitRegionDaoTest.java
 * 
 * FILE NAME        	: OwedLimitRegionDaoTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OwedLimitDto;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 临欠额度区间信息DAO单元测试类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-2-25 下午3:59:30
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-2-25 下午3:59:30
 * @since
 * @version
 */
public class OwedLimitRegionDaoTest {

    private JdbcTemplate jdbc;

    /**
     * 临欠额度区间范围信息DAO接口
     */
    private IOwedLimitRegionDao owedLimitRegionDao;

    private OwedLimitRegionEntity entity = new OwedLimitRegionEntity();

    /**
     * ID
     */
    private String id;

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);
	owedLimitRegionDao = SpringTestHelper.get().getBeanByInterface(
		IOwedLimitRegionDao.class);
    }

    @After
    public void tearDown() throws Exception {
//	jdbc.execute("delete from BSE.T_BAS_OWEDLIMIT_REGION where id = '" + id+ "'");
    }

    /**
     * <p>
     * 添加临欠额度区间范围信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 下午5:13:26
     * @see
     */
    @Ignore
    @Test
    public void testAddInfo() {
	id = UUIDUtils.getUUID();
	entity.setId(id);
	entity.setCreateUser("zhanglong");
	entity.setCreateDate(new Date());
	entity.setModifyDate(entity.getCreateDate());
	entity.setActive(FossConstants.ACTIVE);
	entity.setOwedlimit(new BigDecimal(23));
	entity.setOwedlimitMin(new BigDecimal(25));
	entity.setOwedlimitMax(new BigDecimal(30));

	int result = owedLimitRegionDao.addInfo(entity);

	Assert.assertTrue(result > 0);
    }

    /**
     * <p>
     * 作废临欠额度区间范围信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 下午5:24:55
     * @see
     */
    @Ignore
    @Test
    public void testDeleteInfos() {
	// 添加一条数据
	testAddInfo();
	List<String> list = new ArrayList<String>();
	list.add(id);

	int result = owedLimitRegionDao.deleteInfos(list, "zhanglong");

	Assert.assertTrue(result > 0);
    }

    /**
     * <p>
     * 修改临欠额度区间范围信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 下午5:55:03
     * @see
     */
    @Ignore
    @Test
    public void testUpdateInfo() {
	// 添加一条数据
	testAddInfo();

	entity.setId(id);
	entity.setCreateUser("zhanglong1");
	entity.setCreateDate(new Date());
	entity.setModifyDate(entity.getCreateDate());
	entity.setActive(FossConstants.ACTIVE);

	int result = owedLimitRegionDao.updateInfo(entity);

	Assert.assertTrue(result > 0);
    }
    
    /**
     * <p>分页查询临欠额度区间范围信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 下午6:00:05
     * @see
     */
    @Ignore
    @Test
    public void testQueryAllInfos() {
	// 添加一条数据
	testAddInfo();
	entity.setActive(FossConstants.ACTIVE);

	List<OwedLimitRegionEntity> list = owedLimitRegionDao.queryAllInfos(
		entity, 10, 0);

	Assert.assertTrue(list.size() > 0);
    }
    
    /**
     * <p>查询总记录数</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 下午5:59:52
     * @see
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	// 添加一条数据
	testAddInfo();
	entity.setActive(FossConstants.ACTIVE);
	
	long result = owedLimitRegionDao.queryRecordCount(entity);
	
	Assert.assertTrue(result > 0);
    }
    
    /**
     * <p>根据传入的营业部收入查询营业部最大临欠额度</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-26 上午10:33:10
     * @see
     */
    @Ignore
    @Test
    public void testQueryInfoByTaking(){
	//添加一条数据
	testAddInfo();
	
	OwedLimitRegionEntity entity = owedLimitRegionDao.queryInfoByTaking(new BigDecimal(28),null);
	
	Assert.assertNotNull(entity);
    }
    
    /**
     * <p>更新营业部最大临欠额度</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-26 上午11:40:19
     * @see
     */
    @Ignore
    @Test
    public void testUpdateDepartmentAmountOwed(){
	OwedLimitDto dto = new OwedLimitDto();
	dto.setDeptCode("W33010140");
	dto.setOwedLimit(new BigDecimal(25));
	int result = owedLimitRegionDao.updateDepartmentAmountOwed(dto);
	
	Assert.assertTrue(result > 0);
    }
    
    @Ignore
    @Test
    public void testBatchUpdateDeptAmountOwed(){
	List<OwedLimitDto> list = new ArrayList<OwedLimitDto>();
	OwedLimitDto dto = new OwedLimitDto();
	dto.setDeptCode("W01010449");
	dto.setOwedLimit(new BigDecimal(25));
	OwedLimitDto dto1 = new OwedLimitDto();
	dto1.setDeptCode("W33010151");
	dto1.setOwedLimit(new BigDecimal(25));
	list.add(dto);
	list.add(dto1);
	
	int result = owedLimitRegionDao.batchUpdateDeptAmountOwed(list);
	
	Assert.assertTrue(result == -1);
    }

}
