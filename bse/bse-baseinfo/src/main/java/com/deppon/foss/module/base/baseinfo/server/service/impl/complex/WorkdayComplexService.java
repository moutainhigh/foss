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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/complex/WorkdayComplexService.java
 * 
 * FILE NAME        	: WorkdayComplexService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
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
/**
修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-07-13 	新增 	周春来	V0.1
2012-07-14	根据赵鹏意见进行修改	周春来	V0.2
2012-08-02	通过业务部门审核签字版本升级到V0.9	周春来	V0.9

1.	SUC-788-新增_修改_作废_查询工作日
1.1	相关业务用例
无
1.2	用例描述
此用例用于维护工作日及休息日，结算退运费、服务补救等业务用例需要根据工作日来定义相关业务规则。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件		
后置条件	1、	为结算退运费、服务补救等提供工作日基础资料查询	suc-22-新增服务补救
suc-652-新增退运费申请

1.4	操作用户角色
操作用户	描述
系统管理员	系统管理员对工作日信息进行新增，修改，查询操作。
1.5	界面要求
1.5.1	表现方式
Web页面
1.5.2	界面原型-主界面
 
  
图一：工作日主界面
1.5.3	界面描述-主界面
1.	日期选择区
1）   日期选择区可以通过下拉框选择年和月份，也可以通过点击“左移” 和“右移” 按钮来调整年份和月份。
2）     双击日历事件：双击日期后当前日期背景色变为黄色，表示不是工作日，再双击日期背景色变为初始状态，表示是工作日。
2.	日期展示区
1）      列表区域默认显示当前日期，点击左右图标或下拉列表选择日期查询条件。
功能按钮区域功能按钮区域
3.	功能按钮区
1）   重置按钮：点击重置按钮，重置查询条件和对日历的初始化操作。
2）   保存按钮：点击保存按钮，成功保存界面信息至数据库。

1.6	操作步骤

1.6.1	修改工作日操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入工作日管理主界面	【工作日当前月信息】	
2	点击日期，进行修改工作日参见业务规则SR-1，SR-2		成功保存至数据库

1.6.2	查询工作日操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入工作日管理主界面	【工作日当前月信息】	
2	选择年，月进行查询。	【工作日查询条件】	系统返回查询结果
1.7	业务规则
序号	描述
SR-1	工作日默认从周一到周五；
SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。

1.8	数据元素
1.8.1	工作日修改信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
日期	选择的日期	文本		10	是	
工作日状态	工作日标识	文本		1	否	
是否有效数据	是否有效数据	文本		1	否	
1.8.2	工作日查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
年	年份	文本	10	否	
月	月份	文本	10	否	

1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
查工作日信息	结算系统	根据传入签收时间(格式如：yyyy-mm-dd)和工作日天数（N）返回从签收时间第2天起到第N个工作日的工作日日期
修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-07-13 	新增 	周春来	V0.1
2012-07-14	根据赵鹏意见进行修改	周春来	V0.2
2012-08-02	通过业务部门审核签字版本升级到V0.9	周春来	V0.9

1.	SUC-788-新增_修改_作废_查询工作日
1.1	相关业务用例
无
1.2	用例描述
此用例用于维护工作日及休息日，结算退运费、服务补救等业务用例需要根据工作日来定义相关业务规则。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件		
后置条件	1、	为结算退运费、服务补救等提供工作日基础资料查询	suc-22-新增服务补救
suc-652-新增退运费申请

1.4	操作用户角色
操作用户	描述
系统管理员	系统管理员对工作日信息进行新增，修改，查询操作。
1.5	界面要求
1.5.1	表现方式
Web页面
1.5.2	界面原型-主界面
 
  
图一：工作日主界面
1.5.3	界面描述-主界面
1.	日期选择区
1）   日期选择区可以通过下拉框选择年和月份，也可以通过点击“左移” 和“右移” 按钮来调整年份和月份。
2）     双击日历事件：双击日期后当前日期背景色变为黄色，表示不是工作日，再双击日期背景色变为初始状态，表示是工作日。
2.	日期展示区
1）      列表区域默认显示当前日期，点击左右图标或下拉列表选择日期查询条件。
功能按钮区域功能按钮区域
3.	功能按钮区
1）   重置按钮：点击重置按钮，重置查询条件和对日历的初始化操作。
2）   保存按钮：点击保存按钮，成功保存界面信息至数据库。

1.6	操作步骤

1.6.1	修改工作日操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入工作日管理主界面	【工作日当前月信息】	
2	点击日期，进行修改工作日参见业务规则SR-1，SR-2		成功保存至数据库

1.6.2	查询工作日操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入工作日管理主界面	【工作日当前月信息】	
2	选择年，月进行查询。	【工作日查询条件】	系统返回查询结果
1.7	业务规则
序号	描述
SR-1	工作日默认从周一到周五；
SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。

1.8	数据元素
1.8.1	工作日修改信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
日期	选择的日期	文本		10	是	
工作日状态	工作日标识	文本		1	否	
是否有效数据	是否有效数据	文本		1	否	
1.8.2	工作日查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
年	年份	文本	10	否	
月	月份	文本	10	否	

1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
查工作日信息	结算系统	根据传入签收时间(格式如：yyyy-mm-dd)和工作日天数（N）返回从签收时间第2天起到第N个工作日的工作日日期
修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-07-13 	新增 	周春来	V0.1
2012-07-14	根据赵鹏意见进行修改	周春来	V0.2
2012-08-02	通过业务部门审核签字版本升级到V0.9	周春来	V0.9

1.	SUC-788-新增_修改_作废_查询工作日
1.1	相关业务用例
无
1.2	用例描述
此用例用于维护工作日及休息日，结算退运费、服务补救等业务用例需要根据工作日来定义相关业务规则。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件		
后置条件	1、	为结算退运费、服务补救等提供工作日基础资料查询	suc-22-新增服务补救
suc-652-新增退运费申请

1.4	操作用户角色
操作用户	描述
系统管理员	系统管理员对工作日信息进行新增，修改，查询操作。
1.5	界面要求
1.5.1	表现方式
Web页面
1.5.2	界面原型-主界面
 
  
图一：工作日主界面
1.5.3	界面描述-主界面
1.	日期选择区
1）   日期选择区可以通过下拉框选择年和月份，也可以通过点击“左移” 和“右移” 按钮来调整年份和月份。
2）     双击日历事件：双击日期后当前日期背景色变为黄色，表示不是工作日，再双击日期背景色变为初始状态，表示是工作日。
2.	日期展示区
1）      列表区域默认显示当前日期，点击左右图标或下拉列表选择日期查询条件。
功能按钮区域功能按钮区域
3.	功能按钮区
1）   重置按钮：点击重置按钮，重置查询条件和对日历的初始化操作。
2）   保存按钮：点击保存按钮，成功保存界面信息至数据库。

1.6	操作步骤

1.6.1	修改工作日操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入工作日管理主界面	【工作日当前月信息】	
2	点击日期，进行修改工作日参见业务规则SR-1，SR-2		成功保存至数据库

1.6.2	查询工作日操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入工作日管理主界面	【工作日当前月信息】	
2	选择年，月进行查询。	【工作日查询条件】	系统返回查询结果
1.7	业务规则
序号	描述
SR-1	工作日默认从周一到周五；
SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。

1.8	数据元素
1.8.1	工作日修改信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
日期	选择的日期	文本		10	是	
工作日状态	工作日标识	文本		1	否	
是否有效数据	是否有效数据	文本		1	否	
1.8.2	工作日查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
年	年份	文本	10	否	
月	月份	文本	10	否	

1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
查工作日信息	结算系统	根据传入签收时间(格式如：yyyy-mm-dd)和工作日天数（N）返回从签收时间第2天起到第N个工作日的工作日日期

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-07-13 	新增 	周春来	V0.1
2012-07-14	根据赵鹏意见进行修改	周春来	V0.2
2012-08-02	通过业务部门审核签字版本升级到V0.9	周春来	V0.9

1.	SUC-788-新增_修改_作废_查询工作日
1.1	相关业务用例
无
1.2	用例描述
此用例用于维护工作日及休息日，结算退运费、服务补救等业务用例需要根据工作日来定义相关业务规则。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件		
后置条件	1、	为结算退运费、服务补救等提供工作日基础资料查询	suc-22-新增服务补救
suc-652-新增退运费申请

1.4	操作用户角色
操作用户	描述
系统管理员	系统管理员对工作日信息进行新增，修改，查询操作。
1.5	界面要求
1.5.1	表现方式
Web页面
1.5.2	界面原型-主界面
 
  
图一：工作日主界面
1.5.3	界面描述-主界面
1.	日期选择区
1）   日期选择区可以通过下拉框选择年和月份，也可以通过点击“左移” 和“右移” 按钮来调整年份和月份。
2）     双击日历事件：双击日期后当前日期背景色变为黄色，表示不是工作日，再双击日期背景色变为初始状态，表示是工作日。
2.	日期展示区
1）      列表区域默认显示当前日期，点击左右图标或下拉列表选择日期查询条件。
功能按钮区域功能按钮区域
3.	功能按钮区
1）   重置按钮：点击重置按钮，重置查询条件和对日历的初始化操作。
2）   保存按钮：点击保存按钮，成功保存界面信息至数据库。

1.6	操作步骤

1.6.1	修改工作日操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入工作日管理主界面	【工作日当前月信息】	
2	点击日期，进行修改工作日参见业务规则SR-1，SR-2		成功保存至数据库

1.6.2	查询工作日操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入工作日管理主界面	【工作日当前月信息】	
2	选择年，月进行查询。	【工作日查询条件】	系统返回查询结果
1.7	业务规则
序号	描述
SR-1	工作日默认从周一到周五；
SR-2	修改数据后，操作员如果未保存就离开当前界面，询问是否要保存已修改的记录。

1.8	数据元素
1.8.1	工作日修改信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
日期	选择的日期	文本		10	是	
工作日状态	工作日标识	文本		1	否	
是否有效数据	是否有效数据	文本		1	否	
1.8.2	工作日查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
年	年份	文本	10	否	
月	月份	文本	10	否	

1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
查工作日信息	结算系统	根据传入签收时间(格式如：yyyy-mm-dd)和工作日天数（N）返回从签收时间第2天起到第N个工作日的工作日日期

*/

package com.deppon.foss.module.base.baseinfo.server.service.impl.complex;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IWorkdayComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 工作日 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-28 下午5:19:25
 */
public class WorkdayComplexService implements IWorkdayComplexService {
    /**
     * 
     * 日志
     * 
     */
    private static final Logger log = Logger.getLogger(WorkdayComplexService.class);
    /**
     * 
     * 下面是dao对象的声明及get,set方法：
     * 
     */
    private IWorkdayDao workdayDao;
    /**
     * 
     * 
     * @date Mar 13, 2013 3:58:47 PM
     * @param workdayDao
     * @see
     */
    public void setWorkdayDao(IWorkdayDao workdayDao) {
	this.workdayDao = workdayDao;
    }
    /**
     * 
     * 
     * 判断给定的日期是否是工作
     * 
     * 
     * @author foss-zhujunyong
     * @date 2012-11-28 下午5:19:26
     */
    @Override
    public boolean isWorkday(Date date) {
	//检查参数的合法性
	if (null == date) {
	    return false;
	}
	SimpleDateFormat yearMonthDF = new SimpleDateFormat("yyyyMM");
	WorkdayEntity entity = workdayDao.queryWorkdayByWorkMonth(yearMonthDF.format(date));
	if( entity == null ){
	    return false;
	}
	int day = DateUtils.toCalendar(date).get(Calendar.DATE);
	return isWorkDay(entity, day);
    }
    
    /**
     * 
     * 
     * 查询给定的两个日期之间的工作日天数
     * 
     * 
     * @author foss-zhujunyong
     * @date 2012-11-28 下午9:40:53
     */
    @Override
    public int countWorkday(Date start, Date end){
	// 检查参数
	if (start == null || end == null || end.before(start)) {
	    return 0;
	}

	// 设置年月格式，用于查找数据库
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

	// 开始计算日期差值
	int count = 0;
	// 当两个日期非同月时，循环计算开始日期到结束月份之间的工作日差值
	while (BooleanUtils.isFalse(isSameYearMonth(start, end))) {
	    WorkdayEntity workday = workdayDao.queryWorkdayByWorkMonth(sdf.format(start));
	    if (workday != null) {
		int day1 = DateUtils.toCalendar(start).get(Calendar.DATE);
		count += workDayNum(workday, day1, NumberConstants.NUMBER_31);
	    }
	    start = DateUtils.addMonths(start, 1);
	    start = DateUtils.setDays(start, 1);
	}
	// 运行到这里说明两个日期已经同月
	int day1 = DateUtils.toCalendar(start).get(Calendar.DATE);
	int day2 = DateUtils.toCalendar(end).get(Calendar.DATE);
	WorkdayEntity workday = workdayDao.queryWorkdayByWorkMonth(sdf.format(start));
	if (workday != null) {
	    count += workDayNum(workday, day1, day2);
	}
	return count;
    }
    
    /**
     * 
     * 
     * <p>判断两个日期是否属于同一个月</p> 
     * 
     * 
     * @author foss-zhujunyong
     * @date Mar 13, 2013 4:08:53 PM
     * @param date1
     * @param date2
     * @return
     * @see
     */
    public static boolean isSameYearMonth(Date date1, Date date2){
	if(date1 == null || date2 == null){
	    return false;
	}
	
	Calendar c1 = DateUtils.toCalendar(date1);
	Calendar c2 = DateUtils.toCalendar(date2);
	return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);
    }

    /**
     * 
     * 
     * 判断给定的日子是否是工作
     * 
     * 
     * @author foss-zhujunyong
     * @date 2012-11-28 下午8:57:55
     */
    public static boolean isWorkDay(WorkdayEntity entity, int day) {
	String value = null;
	try {
	    Method m = WorkdayEntity.class.getMethod("getDay" + day);
	    value = (String) m.invoke(entity);
	} catch (Exception e) {
	    log.error("工作日反射出错,workDayEntity = " + entity.getWorkMonth() + " day = " + day, e);
	}
	return StringUtils.equals(value, FossConstants.YES);
    }
    
    /**
     * 
     * 判断给定的工作月份有几天是工作的
     * 
     * 
     * @author foss-zhujunyong
     * @date 2012-11-28 下午8:57:55
     */
    public static int workDayNum(WorkdayEntity entity, int start, int end) {
	int count = 0;
	for (int i = start; i <= end; i++) {
	    if (isWorkDay(entity, i)){
		count++;
	    }
	}
	return count;
    }
}
