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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/complex/OrgAdministrativeInfoComplexServiceTest.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoComplexServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.complex;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.OrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;

//@Ignore
public class OrgAdministrativeInfoComplexServiceTest {
    
    /**
     * 根据CODE查询组织及组织的上组织或者下级， 如果传入的code为空或者空白，则返回空白.
     * 返回时不包含此组织
     * 
     * isUp 为true时查本级和上级，为false时，查级和下级
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午11:23:58
     */
    
    @Test
    public void queryOrgAdministrativeInfoUpDown() {
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, active) " +
		"values('"+a+"', '"+a+"', '"+a+"','"+a+"', '"+a+"','"+a+"', '"+b+"', 'Y')");
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, active) " +
		"values('"+b+"', '"+b+"', '"+b+"','"+b+"', '"+b+"','"+b+"','"+c+"', 'Y')");
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, TRANS_DEPARTMENT, active) " +
		"values('"+c+"', '"+c+"', '"+c+"','"+c+"', '"+c+"','"+c+"', 'Y', 'Y')");

	List<OrgAdministrativeInfoEntity> entityResults;
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpDown(a,false);
	Assert.assertTrue(CollectionUtils.isEmpty(entityResults));
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpDown(b,true);
	Assert.assertTrue(!CollectionUtils.isEmpty(entityResults));
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpDown(b,false);
	Assert.assertTrue(!CollectionUtils.isEmpty(entityResults));
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpDown(c,true);
	Assert.assertTrue(CollectionUtils.isEmpty(entityResults));


	deleteById(a);
	deleteById(b);
	deleteById(c);
    }    
    
    public void testQueryOrgAdministrativeInfoUpDown() {
	String code = "0101J003";
	List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpDown(code, false);
	Assert.assertNotNull(list);
    }
    
    
    /**
     * 通过传入一个车队或车队下调度组的code，查询出车队下的所有接送货组
     * 经过跟王辉沟通，实际返回为车队组，如果车队下面没有车队组，则返回为空
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午11:23:58
     */
    
    @Test
    public void querySubOrgByCode() {
	/**
	 * 准备测试数据
	 */
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	// 添加车队 公共信息
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, TRANS_DEPARTMENT, active) " +
		"values('"+a+"', '"+a+"', '"+a+"', '"+a+"', '"+c+"', 'Y', 'Y')");
	// 添加车队组 公共信息
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, TRANS_TEAM, active) " +
		"values('"+b+"', '"+b+"', '"+b+"', '"+b+"', '"+a+"', 'Y', 'Y')");
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, TRANS_DEPARTMENT, active) " +
		"values('"+c+"', '"+c+"', '"+c+"','"+c+"', '"+c+"','"+c+"', 'Y', 'Y')");
	
	// 添加车队详情
	jdbc.execute("insert into T_BAS_TRANS_DEPARTMENT (id, CODE, NAME, active) " +
		"values('"+a+"', '"+a+"', '"+a+"', 'Y')");
	// 添加车队组详情
	jdbc.execute("insert into T_BAS_TRANS_DEPARTMENT (id, CODE, NAME, PARENT_ORG_CODE, active) " +
		"values('"+b+"', '"+b+"', '"+b+"','"+a+"', 'Y')");

	// 开始测试
	List<OrgAdministrativeInfoEntity> entityResults;
	entityResults=orgAdministrativeInfoComplexService.querySubOrgByCode(a);
	//车队下面有车队组，能查到：
	Assert.assertTrue(!CollectionUtils.isEmpty(entityResults));
	entityResults=orgAdministrativeInfoComplexService.querySubOrgByCode(b);
	// 车队组可以直接查到
	Assert.assertTrue(!CollectionUtils.isEmpty(entityResults));
	entityResults=orgAdministrativeInfoComplexService.querySubOrgByCode(c);
	// 下面没有车队组，所以查不到
	Assert.assertTrue(CollectionUtils.isEmpty(entityResults));
	
	deleteById(a);
	deleteById(b);
	deleteById(c);
    }
    
    
    
    /**
     * 下面测试给张继恒提供的方法
     */
    
    /**
     * 根据部门编码获取所属及下属部门信息
     * 此部门及下属的所有部门。
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午11:23:58
     */
    
    @Test
    public void queryOrgAdministrativeInfoEntityAllSubByCode() {
	/**
	 * 准备测试数据
	 */
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	String d=a+(++count);
	// 添加车队 公共信息
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, TRANS_DEPARTMENT, active) " +
		"values('"+a+"', '"+a+"', '"+a+"', '"+a+"', '"+b+"', 'Y', 'Y')");
	// 添加车队组 公共信息
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, TRANS_TEAM, active) " +
		"values('"+b+"', '"+b+"', '"+b+"', '"+b+"', '"+c+"', 'Y', 'Y')");
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, TRANS_DEPARTMENT, active) " +
		"values('"+c+"', '"+c+"', '"+c+"', '"+c+"', '"+d+"', 'Y', 'Y')");

	// 开始测试
	List<OrgAdministrativeInfoEntity> entityResults;
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(b);
	Assert.assertTrue(!CollectionUtils.isEmpty(entityResults));

	deleteById(a);
	deleteById(b);
	deleteById(c);
	deleteById(d);
    }     
    /**
     * 根据部门编码获取所属及上级部门信息
     * 此部门及上级的所有部门。
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午11:23:58
     */
    
    @Test
    public void queryOrgAdministrativeInfoEntityAllUpByCode() {
	/**
	 * 准备测试数据
	 */
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	String d=a+(++count);
	// 添加车队 公共信息
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, TRANS_DEPARTMENT, active) " +
		"values('"+a+"', '"+a+"', '"+a+"', '"+a+"', '"+b+"', 'Y', 'Y')");
	// 添加车队组 公共信息
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, TRANS_TEAM, active) " +
		"values('"+b+"', '"+b+"', '"+b+"', '"+b+"', '"+c+"', 'Y', 'Y')");
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, TRANS_DEPARTMENT, active) " +
		"values('"+c+"', '"+c+"', '"+c+"', '"+c+"', '"+d+"', 'Y', 'Y')");

	// 开始测试
	List<OrgAdministrativeInfoEntity> entityResults;
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpByCode(b);
	Assert.assertTrue(!CollectionUtils.isEmpty(entityResults));

	deleteById(a);
	deleteById(b);
	deleteById(c);
	deleteById(d);
    }      
    
    /**
     * 根据财务部门编码获取管辖大区信息
     * 大区 是营业大区域
     * 财务部门，不是财务组织的部门，不是一个虚拟的部门，是OA中一个实际存在的部门
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午11:23:58
     */
    
    @Test
    public void queryOrgAdministrativeInfoEntityBigAreaByFinance() {
	/**
	 * 准备测试数据
	 */
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	String d=a+(++count);
	String e=a+(++count);
	String f=a+(++count);
	// 添加营业部，它的实体财务部门的编码是d
	jdbc.execute("insert into t_bas_org " +
		"(id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, ENTITY_FINANCE, SALES_DEPARTMENT, active) " +
		"values('"+a+"', '"+a+"', '"+a+"', '"+a+"', '"+b+"', '"+d+"', 'Y', 'Y')");
	// 添加小区
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, SMALL_REGION, active) " +
		"values('"+b+"', '"+b+"', '"+b+"', '"+b+"', '"+c+"', 'Y', 'Y')");
	// 添加大区
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, BIG_REGION, active) " +
		"values('"+c+"', '"+c+"', '"+c+"', '"+c+"', '"+f+"', 'Y', 'Y')");
	// 添加查询无结果的数据
	jdbc.execute("insert into t_bas_org " +
		"(id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, ENTITY_FINANCE,active) " +
		"values('"+e+"', '"+e+"', '"+e+"', '"+e+"', '"+f+"', 'Y', 'Y')");

	
	// 开始测试
	List<OrgAdministrativeInfoEntity> entityResults;
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityBigAreaByFinance(d);
	// d在财务上负责的部门a的上级有大区c
	Assert.assertTrue(!CollectionUtils.isEmpty(entityResults));
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityBigAreaByFinance(f);
	// f在财务上负责的部门e的上级没有大区
	Assert.assertTrue(CollectionUtils.isEmpty(entityResults));
	

	deleteById(a);
	deleteById(b);
	deleteById(c);
	deleteById(d);
	deleteById(e);
	deleteById(f);
    }    
    
    /**
     * 根据大区编码获取下属小区信息
     * 这里的大区，小区均是营业区域，不是定人定区中的大小区。
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午11:23:58
     */
    
    @Test
    public void queryOrgAdministrativeInfoEntitySmallAreaByBig() {
	/**
	 * 准备测试数据
	 */
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	String d=a+(++count);
	String e=a+(++count);
	String f=a+(++count);
	// 添加营业部，它的实体财务部门的编码是d
	jdbc.execute("insert into t_bas_org " +
		"(id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, ENTITY_FINANCE, SALES_DEPARTMENT, active) " +
		"values('"+a+"', '"+a+"', '"+a+"', '"+a+"', '"+b+"', '"+d+"', 'Y', 'Y')");
	// 添加小区
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, SMALL_REGION, active) " +
		"values('"+b+"', '"+b+"', '"+b+"', '"+b+"', '"+c+"', 'Y', 'Y')");
	// 添加大区
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, BIG_REGION, active) " +
		"values('"+c+"', '"+c+"', '"+c+"', '"+c+"', '"+f+"', 'Y', 'Y')");
	// 添加查询无结果的数据
	jdbc.execute("insert into t_bas_org " +
		"(id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, ENTITY_FINANCE,active) " +
		"values('"+e+"', '"+e+"', '"+e+"', '"+e+"', '"+f+"', 'Y', 'Y')");

	
	// 开始测试
	List<OrgAdministrativeInfoEntity> entityResults;
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntitySmallAreaByBig(c);
	// c下面有小区
	Assert.assertTrue(!CollectionUtils.isEmpty(entityResults));
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntitySmallAreaByBig(a);
	// a下面没有小区
	Assert.assertTrue(CollectionUtils.isEmpty(entityResults));
	

	deleteById(a);
	deleteById(b);
	deleteById(c);
	deleteById(d);
	deleteById(e);
	deleteById(f);
    }
    
    

    /**
     * 根据大区编码获取下属营业部部门信息
     * 这里的大区，小区均是营业区域，不是定人定区中的大小区。
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    
    @Test
    public void  queryOrgAdministrativeInfoEntitySalesByBig(){
	/**
	 * 准备测试数据
	 */
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	String d=a+(++count);
	String e=a+(++count);
	String f=a+(++count);
	String g=a+(++count);
	// 添加营业部，它的实体财务部门的编码是d
	jdbc.execute("insert into t_bas_org " +
		"(id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, ENTITY_FINANCE, SALES_DEPARTMENT, active) " +
		"values('"+a+"', '"+a+"', '"+a+"', '"+a+"', '"+b+"', '"+d+"', 'Y', 'Y')");
	// 添加小区
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, SMALL_REGION, active) " +
		"values('"+b+"', '"+b+"', '"+b+"', '"+b+"', '"+c+"', 'Y', 'Y')");
	// 添加大区
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, BIG_REGION, active) " +
		"values('"+c+"', '"+c+"', '"+c+"', '"+c+"', '"+g+"', 'Y', 'Y')");
	// 添加查询无结果的数据
	jdbc.execute("insert into t_bas_org " +
		"(id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, ENTITY_FINANCE,active) " +
		"values('"+e+"', '"+e+"', '"+e+"', '"+e+"', '"+f+"', 'Y', 'Y')");

	
	// 开始测试
	List<OrgAdministrativeInfoEntity> entityResults;
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntitySalesByBig(c);
	// c下面有营业部
	Assert.assertTrue(!CollectionUtils.isEmpty(entityResults));
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntitySalesByBig(f);
	// f下面没有营业部
	Assert.assertTrue(CollectionUtils.isEmpty(entityResults));
	
	deleteById(a);
	deleteById(b);
	deleteById(c);
	deleteById(d);
	deleteById(e);
	deleteById(f);
	deleteById(g);
    }
   
    /**
     * 根据小区编码获取下属营业部部门信息
     * 这里的大区，小区均是营业区域，不是定人定区中的大小区。
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    
    @Test
    public void queryOrgAdministrativeInfoEntitySalesBySmall(){
	/**
	 * 准备测试数据
	 */
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	String d=a+(++count);
	String e=a+(++count);
	String f=a+(++count);
	String g=a+(++count);
	// 添加营业部，它的实体财务部门的编码是d
	jdbc.execute("insert into t_bas_org " +
		"(id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, ENTITY_FINANCE, SALES_DEPARTMENT, active) " +
		"values('"+a+"', '"+a+"', '"+a+"', '"+a+"', '"+b+"', '"+d+"', 'Y', 'Y')");
	// 添加小区
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, SMALL_REGION, active) " +
		"values('"+b+"', '"+b+"', '"+b+"', '"+b+"', '"+c+"', 'Y', 'Y')");
	// 添加大区
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, BIG_REGION, active) " +
		"values('"+c+"', '"+c+"', '"+c+"', '"+c+"', '"+g+"', 'Y', 'Y')");
	// 添加查询无结果的数据
	jdbc.execute("insert into t_bas_org " +
		"(id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, ENTITY_FINANCE,active) " +
		"values('"+e+"', '"+e+"', '"+e+"', '"+e+"', '"+f+"', 'Y', 'Y')");

	
	// 开始测试
	List<OrgAdministrativeInfoEntity> entityResults;
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntitySalesByBig(b);
	// b下面有营业部
	Assert.assertTrue(!CollectionUtils.isEmpty(entityResults));
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntitySalesByBig(f);
	// f下面没有营业部
	Assert.assertTrue(CollectionUtils.isEmpty(entityResults));

	deleteById(a);
	deleteById(b);
	deleteById(c);
	deleteById(d);
	deleteById(e);
	deleteById(f);
	deleteById(g);
    }
    
    
    /**
     * 查询上级部门中指定集中中类型的部门，找到上级中有一个在bizTypes的即返回
     * 
     * bizTypes请看BizTypeConstants类，目前包括：    
    ORG_TRANS_DEPARTMENT="TRANS_DEPARTMENT";
    ORG_TRANSFER_CENTER="TRANSFER_CENTER";
    ORG_AIR_DISPATCH="AIR_DISPATCH";
    ORG_DIVISION="DIVISION";
    ORG_BIG_REGION="BIG_REGION";
    ORG_SMALL_REGION="SMALL_REGION";
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-23 下午2:00:07
     */
    
    @Test
    public void queryOrgAdministrativeInfoByCode(){
	/**
	 * 准备测试数据
	 */
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	String d=a+(++count);
	String e=a+(++count);
	String f=a+(++count);
	String g=a+(++count);
	// 添加营业部，它的实体财务部门的编码是d
	jdbc.execute("insert into t_bas_org " +
		"(id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, ENTITY_FINANCE, SALES_DEPARTMENT, active) " +
		"values('"+a+"', '"+a+"', '"+a+"', '"+a+"', '"+b+"', '"+d+"', 'Y', 'Y')");
	// 添加小区
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, SMALL_REGION, active) " +
		"values('"+b+"', '"+b+"', '"+b+"', '"+b+"', '"+c+"', 'Y', 'Y')");
	// 添加大区
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, BIG_REGION, active) " +
		"values('"+c+"', '"+c+"', '"+c+"', '"+c+"', '"+g+"', 'Y', 'Y')");
	// 添加查询无结果的数据
	jdbc.execute("insert into t_bas_org " +
		"(id, CODE, NAME, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, ENTITY_FINANCE,active) " +
		"values('"+e+"', '"+e+"', '"+e+"', '"+e+"', '"+f+"', 'Y', 'Y')");

	
	// 开始测试
	List<String> bizTypes= new ArrayList<String>();
	
	// 查询大区或者小区
	bizTypes.add(BizTypeConstants.ORG_BIG_REGION);
	bizTypes.add(BizTypeConstants.ORG_SMALL_REGION);	
	OrgAdministrativeInfoEntity entityResult = null;
	try{
	    entityResult=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(a,bizTypes);
	}catch(Exception ex){
	}
	// a上面有大小区
	Assert.assertTrue(entityResult != null);
	
	// 查询外场或者车队
	bizTypes.clear();
	bizTypes.add(BizTypeConstants.ORG_TRANS_DEPARTMENT);
	bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
	entityResult=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(a,bizTypes);
	// a上面没有外场或者车队
	Assert.assertTrue( entityResult == null );

	deleteById(a);
	deleteById(b);
	deleteById(c);
	deleteById(d);
	deleteById(e);
	deleteById(f);
	deleteById(g);
    }
    
    
    @Test
    public void testQueryOrgAdministrativeInfoByCode() {
	List<String> list = new ArrayList<String>();
	list.add(BizTypeConstants.ORG_TRANSFER_CENTER);
	list.add(BizTypeConstants.ORG_AIR_DISPATCH);
//	list.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
//	list.add(BizTypeConstants.ORG_SMALL_REGION);	
	OrgAdministrativeInfoEntity entity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode("W010002020120", list);
	System.out.println(entity.getName());
	System.out.println(entity.getCode());
    }
    
    /**
     * 下面是提供给高鹏的方法
     */
    
    /**
     * 查询车队，如果当前部门不是车队，查上级，如果上级也不是，再向上查，直到查到到，或者查完所有级，或者超过递归数
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午11:23:58
     */
    
    @Test
    public void getOrgAdministrativeInfoMotorcadeByCode() {
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	String d=a+(++count);
	String e=a+(++count);
	String f=a+(++count);
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, active) " +
		"values('"+a+"', '"+a+"', '"+a+"','"+a+"', '"+a+"','"+a+"', '"+b+"', 'Y')");
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, active) " +
		"values('"+b+"', '"+b+"', '"+b+"','"+b+"', '"+b+"','"+b+"','"+c+"', 'Y')");
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, TRANS_DEPARTMENT, active) " +
		"values('"+c+"', '"+c+"', '"+c+"','"+c+"', '"+c+"','"+c+"','"+d+"', 'Y', 'Y')");
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, active) " +
		"values('"+d+"', '"+d+"', '"+d+"','"+d+"', '"+d+"','"+d+"','"+e+"', 'Y')");
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, active) " +
		"values('"+e+"', '"+e+"', '"+e+"','"+e+"', '"+e+"','"+e+"','"+f+"', 'Y')");
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, active) " +
		"values('"+f+"', '"+f+"', '"+f+"','"+f+"', '"+f+"','"+f+"','Y')");
	
	OrgAdministrativeInfoEntity entityResult;
	entityResult=orgAdministrativeInfoComplexService.getOrgAdministrativeInfoMotorcadeByCode(a);
	Assert.assertNotNull(entityResult);
	entityResult=orgAdministrativeInfoComplexService.getOrgAdministrativeInfoMotorcadeByCode(b);
	Assert.assertNotNull(entityResult);
	entityResult=orgAdministrativeInfoComplexService.getOrgAdministrativeInfoMotorcadeByCode(c);
	Assert.assertNotNull(entityResult);
	entityResult=orgAdministrativeInfoComplexService.getOrgAdministrativeInfoMotorcadeByCode(d);
	Assert.assertNull(entityResult);
	entityResult=orgAdministrativeInfoComplexService.getOrgAdministrativeInfoMotorcadeByCode(e);
	Assert.assertNull(entityResult);
	entityResult=orgAdministrativeInfoComplexService.getOrgAdministrativeInfoMotorcadeByCode(f);
	Assert.assertNull(entityResult);

	deleteById(a);
	deleteById(b);
	deleteById(c);
	deleteById(d);
	deleteById(e);
	deleteById(f);
    }
    
    
    /**
     * 查询车队，如果当前部门不是车队，查上级，如果上级也不是，再向上查，直到查到到，或者查完所有级，或者超过递归数
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午11:23:58
     */
    @Test
    public void queryOrgAdministrativeInfoSubByBizType() {
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	String d=a+(++count);
	try{
            	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, active, CREATE_USER_CODE, TRANS_TEAM) " +
            		"values('"+a+"', '"+a+"', '"+a+"','"+a+"', '"+a+"','"+a+"', '"+b+"', 'Y', '087584-test-toDelete', 'Y')");
            	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, active, CREATE_USER_CODE) " +
            		"values('"+b+"', '"+b+"', '"+b+"','"+b+"', '"+b+"','"+b+"','"+c+"', 'Y', '087584-test-toDelete')");
            	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, TRANS_DEPARTMENT, active, CREATE_USER_CODE) " +
            		"values('"+c+"', '"+c+"', '"+c+"','"+c+"', '"+c+"','"+c+"','"+d+"', 'Y', 'Y', '087584-test-toDelete')");
            	
            	List<OrgAdministrativeInfoEntity> entityResults;
            	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByBizType(c, BizTypeConstants.ORG_TRANS_TEAM);
            	Assert.assertTrue(CollectionUtils.isNotEmpty(entityResults));
	}finally{
	    deleteById(a);
	    deleteById(b);
	    deleteById(c);
	}
    }
    
    @Test
    public void testQueryOrgAdministrativeInfoEntityAllUpByCode(){
	List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpByCode("W01130804");
	Assert.assertNotNull(list);
    }
    
    
    @Test
    public void testQueryMotorcadeCodeListByOrgCode(){
	List<String> x = orgAdministrativeInfoComplexService.queryMotorcadeCodeListByOrgCode("W31000206090602");
	Assert.assertNotNull(x);
    }
    
    /**
     * 下面是测试用的工具
     */
    
    /**
     * 删除测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteById(String id){
	jdbc.execute("delete from BSE.T_BAS_ORG where id = '" + id + "'");
	
    }

    static int count=0;
    private JdbcTemplate jdbc;
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	// jdbc.execute("delete from t_bas_storage");
	orgAdministrativeInfoComplexService = (IOrgAdministrativeInfoComplexService) SpringTestHelper
		.get().getBeanByClass(OrgAdministrativeInfoComplexService.class);
    }
    
    @After
    public void teardown() {
//	jdbc.execute("delete from t_bas_storage");
    }
}
