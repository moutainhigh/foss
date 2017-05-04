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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/complex/WorkdayComplexServiceTest.java
 * 
 * FILE NAME        	: WorkdayComplexServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.complex;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IWorkdayComplexService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.WorkdayComplexService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;

@Ignore
public class WorkdayComplexServiceTest {
    

    /**
     * 判断给定的日期是否是工作
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:19:26
     */
    @Test
    public void isWorkday(){
//	String a=System.currentTimeMillis()+"";
//	String b=a+(++count);
//	String c=a+(++count);
//	Date date1 = DateUtils.strToDate("2032-05-08 12:23:34");
//	// Date date2 = DateUtils.strToDate("2032-06-08 12:23:34");
//	Date date3 = DateUtils.strToDate("2032-07-01 12:23:34");
//	jdbc.execute("insert into BSE.T_BAS_WORKDAY (id, VIRTUAL_CODE, WORK_MONTH, REMARK_INFO, EXTEND_INFO, ACTIVE, DAY1, DAY2" +
//		", DAY3, DAY4, DAY5, DAY6, DAY7, DAY8, DAY9, DAY10, DAY11, DAY12, DAY13, DAY14, DAY15, DAY16, DAY17, DAY18" +
//		", DAY19, DAY20, DAY21, DAY22, DAY23, DAY24, DAY25, DAY26, DAY27, DAY28, DAY29, DAY30, DAY31,CREATE_TIME ) " +
//        	"values('"+a+"', '"+a+"', '203205','087584-test-toDelete', '087584-test-toDelete', 'Y'," +
//        	"'N', 'N', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'" +
//        	", 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y',to_date('2012-11-29 13:23:44','yyyy-mm-dd hh24:mi:ss') )");
//
//	jdbc.execute("insert into BSE.T_BAS_WORKDAY (id, VIRTUAL_CODE, WORK_MONTH, REMARK_INFO, EXTEND_INFO, ACTIVE, DAY1, DAY2" +
//		", DAY3, DAY4, DAY5, DAY6, DAY7, DAY8, DAY9, DAY10, DAY11, DAY12, DAY13, DAY14, DAY15, DAY16, DAY17, DAY18" +
//		", DAY19, DAY20, DAY21, DAY22, DAY23, DAY24, DAY25, DAY26, DAY27, DAY28, DAY29, DAY30, DAY31,CREATE_TIME ) " +
//        	"values('"+b+"', '"+b+"', '203206','087584-test-toDelete', '087584-test-toDelete', 'Y'," +
//        	"'N', 'N', 'N', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'" +
//        	", 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y',to_date('2012-11-29 13:23:44','yyyy-mm-dd hh24:mi:ss') )");
//
//	jdbc.execute("insert into BSE.T_BAS_WORKDAY (id, VIRTUAL_CODE, WORK_MONTH, REMARK_INFO, EXTEND_INFO, ACTIVE, DAY1, DAY2" +
//		", DAY3, DAY4, DAY5, DAY6, DAY7, DAY8, DAY9, DAY10, DAY11, DAY12, DAY13, DAY14, DAY15, DAY16, DAY17, DAY18" +
//		", DAY19, DAY20, DAY21, DAY22, DAY23, DAY24, DAY25, DAY26, DAY27, DAY28, DAY29, DAY30, DAY31,CREATE_TIME ) " +
//        	"values('"+c+"', '"+c+"', '203207','087584-test-toDelete', '087584-test-toDelete', 'Y'," +
//        	"'N', 'N', 'N', 'N', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'" +
//        	", 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y',to_date('2012-11-29 13:23:44','yyyy-mm-dd hh24:mi:ss') )");

	Calendar c = Calendar.getInstance();
	c.set(Calendar.DATE, 13);
	
	// 2032-05-08 是工作日
	boolean isWorkday = workdayComplexService.isWorkday(c.getTime());
	Assert.assertTrue(isWorkday);
	// 2032-07-01不是工作日
	c.set(Calendar.DATE, 16);
	isWorkday = workdayComplexService.isWorkday(c.getTime());
	Assert.assertTrue(!isWorkday);

//	deleteById(a);
//	deleteById(b);
//	deleteById(c);
    }
    

    /**
     * 判断给定的日期是否是工作
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:19:26
     */
    @Test
    public void countWorkday(){
//	String a=System.currentTimeMillis()+"";
//	String b=a+(++count);
//	String c=a+(++count);
//	String d=a+(++count);
//	Date date0 = DateUtils.strToDate("2032-05-02 12:23:34");
//	Date date1 = DateUtils.strToDate("2032-05-08 12:23:34");
//	// Date date2 = DateUtils.strToDate("2032-06-08 12:23:34");
//	// Date date3 = DateUtils.strToDate("2032-07-01 12:23:34");
//	Date date5 = DateUtils.strToDate("2032-10-21 12:23:34");
//	jdbc.execute("insert into BSE.T_BAS_WORKDAY (id, VIRTUAL_CODE, WORK_MONTH, REMARK_INFO, EXTEND_INFO, ACTIVE, DAY1, DAY2" +
//		", DAY3, DAY4, DAY5, DAY6, DAY7, DAY8, DAY9, DAY10, DAY11, DAY12, DAY13, DAY14, DAY15, DAY16, DAY17, DAY18" +
//		", DAY19, DAY20, DAY21, DAY22, DAY23, DAY24, DAY25, DAY26, DAY27, DAY28, DAY29, DAY30, DAY31,CREATE_TIME ) " +
//        	"values('"+a+"', '"+a+"', '203205','087584-test-toDelete', '087584-test-toDelete', 'Y'," +
//        	"'N', 'N', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'" +
//        	", 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y',to_date('2012-11-29 13:23:44','yyyy-mm-dd hh24:mi:ss') )");
//
//	jdbc.execute("insert into BSE.T_BAS_WORKDAY (id, VIRTUAL_CODE, WORK_MONTH, REMARK_INFO, EXTEND_INFO, ACTIVE, DAY1, DAY2" +
//		", DAY3, DAY4, DAY5, DAY6, DAY7, DAY8, DAY9, DAY10, DAY11, DAY12, DAY13, DAY14, DAY15, DAY16, DAY17, DAY18" +
//		", DAY19, DAY20, DAY21, DAY22, DAY23, DAY24, DAY25, DAY26, DAY27, DAY28, DAY29, DAY30, DAY31,CREATE_TIME ) " +
//        	"values('"+b+"', '"+b+"', '203206','087584-test-toDelete', '087584-test-toDelete', 'Y'," +
//        	"'N', 'N', 'N', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'" +
//        	", 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y',to_date('2012-11-29 13:23:44','yyyy-mm-dd hh24:mi:ss') )");
//
//	jdbc.execute("insert into BSE.T_BAS_WORKDAY (id, VIRTUAL_CODE, WORK_MONTH, REMARK_INFO, EXTEND_INFO, ACTIVE, DAY1, DAY2" +
//		", DAY3, DAY4, DAY5, DAY6, DAY7, DAY8, DAY9, DAY10, DAY11, DAY12, DAY13, DAY14, DAY15, DAY16, DAY17, DAY18" +
//		", DAY19, DAY20, DAY21, DAY22, DAY23, DAY24, DAY25, DAY26, DAY27, DAY28, DAY29, DAY30, DAY31,CREATE_TIME ) " +
//        	"values('"+c+"', '"+c+"', '203207','087584-test-toDelete', '087584-test-toDelete', 'Y'," +
//        	"'N', 'N', 'N', 'N', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'" +
//        	", 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y',to_date('2012-11-29 13:23:44','yyyy-mm-dd hh24:mi:ss') )");
//
//	jdbc.execute("insert into BSE.T_BAS_WORKDAY (id, VIRTUAL_CODE, WORK_MONTH, REMARK_INFO, EXTEND_INFO, ACTIVE, DAY1, DAY2" +
//		", DAY3, DAY4, DAY5, DAY6, DAY7, DAY8, DAY9, DAY10, DAY11, DAY12, DAY13, DAY14, DAY15, DAY16, DAY17, DAY18" +
//		", DAY19, DAY20, DAY21, DAY22, DAY23, DAY24, DAY25, DAY26, DAY27, DAY28, DAY29, DAY30, DAY31,CREATE_TIME ) " +
//        	"values('"+d+"', '"+d+"', '203208','087584-test-toDelete', '087584-test-toDelete', 'Y'," +
//        	"'N', 'N', 'N', 'N', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'" +
//        	", 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y',to_date('2012-11-29 13:23:44','yyyy-mm-dd hh24:mi:ss') )");

	Calendar c1 = Calendar.getInstance();
	Calendar c2 = Calendar.getInstance();
//	c2.set(Calendar.DATE, 15);
	c2.add(Calendar.MONTH, 2);
	
	
	// 
	int count = workdayComplexService.countWorkday(c1.getTime(), c2.getTime());
	Assert.assertTrue(count==44);
	// 
//	count = workdayComplexService.countWorkday(date0, date1);
//	Assert.assertTrue(count== 6 );
//	// 
//	count = workdayComplexService.countWorkday(date0, date5);
//	Assert.assertTrue(count>0);
//	// 
//	count = workdayComplexService.countWorkday(date5, date0);
//	Assert.assertTrue(count>0);
//
//	deleteById(a);
//	deleteById(b);
//	deleteById(c);
//	deleteById(d);
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
	jdbc.execute("delete from BSE.T_BAS_WORKDAY where id = '" + id + "'");
	
    }

    private JdbcTemplate jdbc;
    private IWorkdayComplexService workdayComplexService;
    
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);
	// jdbc.execute("delete from t_bas_storage");
	workdayComplexService = (IWorkdayComplexService) SpringTestHelper
		.get().getBeanByClass(WorkdayComplexService.class);
    }
    
    @After
    public void teardown() {
//	jdbc.execute("delete from t_bas_storage");
    }
}
