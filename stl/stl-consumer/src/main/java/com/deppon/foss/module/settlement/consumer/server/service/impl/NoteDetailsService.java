/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/NoteDetailsService.java
 * 
 * FILE NAME        	: NoteDetailsService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-04-19 	创建版 	贲佳佳	V0.1
2012-07-16	修改用例名	贲佳佳	V0.5
2012-07-25	修改用例版本由0.5改为0.9	贲佳佳	V0.9
  	  	  	 

1.	SUC-4 -查询-作废小票记录
1.1	相关业务用例
BUC_FOSS_4.7.10.30_010  现金小票
1.2	用例描述
营业部、派送部及配载部门操作员、会计根据录入时间、小票单号、客户类型、客户信息、收款方式、收入类型查询所属当前登录部门的小票信息，并有权对查询到的小票信息进行作废、打印操作。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件		
后置条件	1.	查询到小票信息，并体现在列表中	
1.4	操作用户角色
操作用户	描述
收银员	查询部门所有业务类型的小票信息，无作废小票权限
营业员	查询部门所有业务类型的小票信息，无作废小票权限
配载员	查询偏线代理的小票信息，无作废小票权限
部门经理	查询部门所有业务类型的小票信息，有作废小票权限
部门会计	查询部门所有业务类型的小票信息，有作废小票权限
1.5	界面要求
1.5.1	表现方式
Web
1.5.2	界面原型
	按时间查询界面
 
	按小票单号查询界面
 
	打印小票清单模板
　	　	　		小票收入	　	　	　
收入部门：	　	　	　	　	　	　
日期	小票号码	客户名称	运单号	付款方式	计费类别	金额	制单人
　	　	　	　	　	　	　	　
　	　	　	　	　	　	合计金额：
现金：	　	临时欠款：	月结：	　银行卡：		　
打印时间：		打印人：				页码

1.5.3	界面描述
	页面初始化组件描述：
1、录入开始时间和录入结束时间是指新增小票业务的录入时间，页面初始化时系统自动提供默认值，录入开始时间为当前服务器日期的开始时间，用户也可以通过选择、直接修改的方式修改时间数据。 
2、收入部门、收入公司默认为当前登录用户的所属部门名称及所属子公司，且不可修改；
3.收款方式系统默认为全部；
4.客户类型系统默认为全部。
	 页面输入组件描述：
1、用户可以同时输入多个小票单号，每个单号间用逗号隔开；
2、用户可以在在现金、银行卡、月结、临时欠款、全部中选择收款方式；
3、用户可以在客户、代理、全部中选择客户类型；
4、用户可以在客户信息里面输入客户名称或客户编码；输入后系统自动调用综合管理子系统中的客户信息进行校验。
	页面表格组件功能描述：
1、查询出的列表默认为按照时间的先后顺序进行倒序排序；
2、用户可以对查询出的结果，选择其中一条记录进行作废、打印操作；
3、用户可以分别通过小票单号、客户编码、客户名称、收款方式、收入类别、客户类型、金额、收银人员、收银时间对数据结果集进行升序或者降序排列；
4、用户可以自定义显示结果集中的数据列 
	小票查询界面提供以下按钮：
	查询
	导出
	作废
	打印
	打印清单：按照打印清单模板输出小票清单数据。
1.6操作步骤
1.6.1操作步骤
序号	基本步骤	相关数据	补充步骤
1	页面初始化	网点信息、录入开始时间、录入结束时间信息	1、	系统自动调用综合管理子系统查询网点信息接口填充归属网点信息；
2、	系统默认录入时间，可供直接查询
2	输入小票单号	小票单号
	1、	检查输入小票单号格式合法性（小票单号可以为空）
2、	用户可以同时输入多个小票单号，每个单号间用逗号隔开
3	选择收款方式	收款方式信息	1、	收款方式可以为全部
4	选择客户类型	客户类型信息	1、	客户类型可以为全部
5	输入客户信息	客户信息	1、	用户在输入客户信息时系统自动调用CRM查询客户信息，系统检查输入的合法性 
6	点击“查询”按钮	小票信息	1.	查询出的数据形成页面表格内容；
1.6.2操作步骤
序号	基本步骤	相关数据	补充步骤
7	点击“导出”按钮	小票详细信息	1、调用导出数据接口，导出相对应的小票信息

1.6.3操作步骤
序号	基本步骤	相关数据	补充步骤
8	点击“作废”按钮	标记小票信息	1.提示是否作废，调用作废小票接口，红冲小票信息，同时生成红冲应收单

1.6.4操作步骤
序号	基本步骤	相关数据	补充步骤
9	点击“打印”按钮	打印小票信息	1.提示是否打印

1.6.5操作步骤
序号	基本步骤	相关数据	补充步骤
10	点击“打印清单”按钮	小票清单	1.	提示是否打印
2.	小票清单中的收入部门来源于小票查询列表中的部门名称；
3.	小票清单中的小票号码来源于小票查询列表中的小票单号；
4.	小票清单中的客户名称来源于小票查询列表中的客户名称；
5.	小票清单中的运单号来源于小票查询列表中的运单单号；
6.	小票清单中的付款方式来源于小票查询列表中的收款方式；
7.	小票清单中的计费类别来源于小票查询列表中的收入类别
8.	小票清单中的金额来源于小票查询列表中的金额；
9.	小票清单中的制单人来源于小票查询列表中的录入人员；
10.	系统自动按照查询小票显示在列表中的数据计算总和金额，显示在小票清单中的合计金额中；同时系统按照查询出的收款方式分别按类别计算总和，计算出的收款方式为现金的金额之和、临时欠款的金额之和、月结的金额之和和银行卡的金额之和的数据分别显示在小票清单中；
11.	小票清单中的打印时间默认为当前服务器时间，打印人为当前操作人，同时系统按照打印清单的内容自动进行页码排序。


序号	扩展事件	相关数据	备注
2a	当输入的小票单号格式不合法时，提示格式错误信息		系统提示：您输入的小票单号错误
6a	当查询不到结果时,提示没有符合条件的单据		
8a	作废小票参考“作废小票”系统用例		
9a	打印小票参考“打印小票信息”系统用例		
1.6	业务规则
序号	描述
SR1	1、	归属网点必须为当前登录用户的所属部门名称及所属子公司，且不能修改；
2、	系统初始化的录入开始时间和录入结束时间默认相差0天；
3、	录入结束时间必须大于录入开始时间；
4、	录入开始时间和结束时间相差不能大于30天；
5、	录入开始时间和结束时间必须精确到时分秒，且不能为空，录入开始时间为当前服务器日期的开始时间，格式例如：2012-04-20 00:00:00，录入结束时间为当前服务器日期的最后时间，格式例如：2012-04-20 23:59:59，用户也可以通过选择、直接修改的方式修改时间数据。
SR2	1、	输入的小票单号的个数不能超过10个；
2、	输入的小票单号位数不能少于8位。
SR3	1、	收款方式在现金、银行卡、月结、临时欠款、全部中进行选择。
SR4	1、	客户类型可以在客户、代理、全部中进行选择。
SR5	1、	用户输入客户名称或客户编码时，系统自动调用综合管理子系统的客户信息，检查其合法性；
SR6	1、导出信息与查询到的信息保持一致；
2、导出为EXCEL表格，格式为小票信息列表格式。
SR7	1、	点击“作废”，弹出对话框“是否作废”；
SR8	  1、点击“打印”，弹出对话框“是否打印”。

1.7	数据元素
1.7.1	查询小票（输入）
字段名称 	说明 	输入限制	长度	是否必填	备注
收入部门	部门名称	系统获取	100	是	无
收入公司	部门所属子公司名称	系统获取	100	是	无
录入时间开始时间	时间	系统获取	20	是	无
录入时间结束时间	时间	系统获取	20	是	无
小票单号	小票单号	手工输入	20	是	无
客户类别	客户、代理	手工输入	5	是	无
客户编号	客户编号	手工输入	50	是	无
客户名称	客户名称	手工输入	100	是	无
收款方式	付款方式	手工选择	  15	是	无
收入类别	收入类型	手工选择	  15	是	无

1.7.2	小票信息（输出）
字段名称 	说明 	输入限制	长度	是否必填	备注
收入部门	部门名称	系统获取	100	是	无
收入部门编号		系统获取	100	是	无
收入公司	部门所属子公司名称	系统获取	100	是	无
收入公司编号		系统获取	100	是	无
录入时间开始时间	时间	系统获取	20	是	无
录入时间结束时间	时间	系统获取	20	是	无
小票单号	小票单号	手工输入	20	是	无
运单单号	运单号	手工输入	20	是	界面不显示
客户类别	客户、代理	手工输入	5	是	无
客户编号	客户编号	手工输入	50	是	无
客户名称	客户名称	手工输入	100	是	无
客户电话	客户电话	系统获取	100	是	界面不显示
收款方式	付款方式	手工选择	15	是	无
收入类别	收入类型	手工选择	15	是	无
金额	应付金额	系统获取	10	是	界面不显示
录入人员	收银人姓名	系统获取	10	是	界面不显示
录入人员编号	收银人元工号	系统获取	10	是	界面不显示
版本号	小票的版本号	系统获取	10	是	系统自动默认版本号
是否有效	标记该小票是否有效	系统获取	10	是	选择是或者否
是否作废	标记该条小票是否是红冲单据	系统获取	10	是	选择是或者否
业务时间	业务生成时间	系统获取	10	是	系统自动默认为当前服务器时间

1.7.3	小票清单（输出）
字段名称 	说明 	输入限制	长度	是否必填	备注
收入部门	部门名称	系统获取	100	是	无
日期	日期	系统获取	20	是	无
小票号码	小票单号	系统获取	20	是	无
客户名称	客户名称	系统获取	100	是	无
运单号	运单号	系统获取	20	是	无
付款方式	付款方式	系统获取	15	是	无
计费类别	收入类型	系统获取	15	是	无
金额	应付金额	系统获取	10	是	无
制单人	录入人姓名	系统获取	10	是	无
合计金额	打印出的小票清单金额之和	系统获取	10	是	无
现金	打印出的小票清单的现金金额之和	系统获取	100	是	无
临时欠款	打印出的小票清单的临时欠款金额之和	系统获取	100	是	无
月结	打印出的小票清单的月结金额之和	系统获取	100	是	无
银行卡	打印出的小票清单的银行卡金额之和	系统获取	100	是	无
打印时间	打印小票清单的时间	系统获取	100	是	系统自动默认为当前服务器时间
打印人	打印人姓名	系统获取	100	是	无
页码	页面排序	系统获取	100	是	无

1.8	非功能性需求
使用量	全国大约一个月有30万票
2012年全网估计用户数	收银员数量约2236名(截止2012.4.12，其增长速度与网点增长速度成正比)
响应要求（如果与全系统要求 不一致的话）	查询在3秒内响应；核销在10秒内响应
使用时间段	正常白天上班时间（8:00-17：30）
高峰使用时间段	特殊业务，无高峰使用时间
1.9	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
查询网点信息接口	FOSS-综合管理子系统	根据当前登录用户查询出所属网点信息
查询客户编码信息接口	CRM	根据输入的字符模糊查询出所有的客户编码，校验客户编码的合法性
作废小票接口	结算	作废小票信息
打印小票	结算	打印小票模板

 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.INoteDetailsDao;
import com.deppon.foss.module.settlement.consumer.api.server.dao.INoteQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.dao.INoteStockInDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.INoteDetailsService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteDetailsEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteStockInEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteDetailsDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteStockInDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 小票使用管理服务
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-12 上午8:30:42
 */
public class NoteDetailsService implements INoteDetailsService {

	private static Logger logger = LogManager
			.getLogger(NoteDetailsService.class);

	/**
	 * 小票单据明细Dao
	 */
	private INoteDetailsDao noteDetailsDao;

	/**
	 * 小票单据入库Dao
	 */
	private INoteStockInDao noteStockInDao;

	/**
	 * 小票单据查询Dao
	 */
	private INoteQueryDao noteQueryDao;
	

	/**
	 * 小票核销
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:32:42
	 */
	@Override
	public void writeoffNote(NoteDetailsDto noteDetailsDto) {
		// 输入参数校验
		if (null == noteDetailsDto
				|| CollectionUtils.isEmpty(noteDetailsDto.getNoteAppIds())) {
			throw new SettlementException("内部错误，来源参数为空！");
		}
		
		logger.info("entering service...,noteAppIds:"
				+ noteDetailsDto.getNoteAppIds());

		int i = noteDetailsDao.updateWriteoffByNoteAppIds(noteDetailsDto);
		if (i < SettlementReportNumber.FIFTY) {
			throw new SettlementException("小票核销失败");
		}
		logger.info("successfully exit service,ids:"
				+ noteDetailsDto.getNoteAppIds());
	}

	/**
	 * 生成小票单据数据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:32:52
	 */
	private void addNoteDetails(List<NoteDetailsEntity> noteDetailsLst) {
		noteDetailsDao.addNoteDetails(noteDetailsLst);
	}

	/**
	 * 生成小票入库数据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-20 下午2:57:40
	 */
	private void addStockInNote(List<NoteStockInEntity> stockInLst) {
		noteStockInDao.addStockIn(stockInLst);
	}
	
	

	/**
	 * 检查小票明细是否存在
	 * @author 095793-foss-LiQin
	 * @date 2013-6-18 下午3:22:07
	 */


	private int checkIsNoteDeail(String beginNo,String endNo){
		Map<String,String> map=new HashMap<String,String>();
		map.put("beginNo", beginNo);
		map.put("endNo", endNo);
		return noteDetailsDao.queryIsRepNoteDetailsNos(map);
	}
	
	
	
	/**
	 * 生成小票单据数据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:31:03
	 */
	@Override
	public void createNoteDetails(NoteStockInDto noteStockInDto,
			CurrentInfo currentInfo) {

		if (null == noteStockInDto// 输入参数校验
				|| CollectionUtils.isEmpty(noteStockInDto.getStockInList())) {
			throw new SettlementException("内部错误，来源票据为空，生成小票单据失败");
		}

		logger.info("entering service...");
		List<NoteStockInEntity> stockInEntityLst = noteStockInDto
				.getStockInList();// 获取前端下发列表信息
		NoteStockInDto noteStockInDtoByDto = noteStockInDto;// 复制前端保存参数
		List<NoteStockInEntity> stockInLst = null;// 存储入库信息列表
		List<NoteDetailsEntity> noteDetailsLst = null;// 存储明细信息列表
		StringBuffer checkRlt = new StringBuffer();// 存储提示信息
		int noteNum = 0;

		if (CollectionUtils.isNotEmpty(stockInEntityLst)) {

			stockInLst = new ArrayList<NoteStockInEntity>();// 如果下发列表不为空则实例化集合
			noteDetailsLst = new ArrayList<NoteDetailsEntity>();

			for (NoteStockInEntity noteStockIn : stockInEntityLst) {// 该循环
																	// 生成小票单据的入库信息
				String rlt = checkNoResult(
						noteStockInDao.checkStockInEntity(noteStockIn),
						noteStockIn);// 判断入库单号是否已存在// 并返回提示信息
				if (StringUtils.isNotEmpty(rlt)) {// 如果要保存的下发编码存在于当前库中，则记录该信息并继续第一层循环
					checkRlt.append(rlt);
					continue;
				}

				// 添加发放明细是否重复
				noteNum = checkIsNoteDeail(noteStockIn.getBeginNo().toString(),
						noteStockIn.getEndNo().toString());
				if (noteNum > 1) {
					continue;
				}

				NoteStockInEntity stockInEntity = new NoteStockInEntity(); // 入库实体信息
				stockInEntity.setNoteAppId(noteStockInDtoByDto.getId());
				stockInEntity.setExpressDeliveryNumber(noteStockInDtoByDto
						.getExpressDeliveryNumber());
				stockInEntity
						.setIssuedType(noteStockInDtoByDto.getIssuedType());

				// 添加单据入库信息
				addStockInEntity(stockInLst, stockInEntity,
						noteStockIn.getBeginNo(), noteStockIn.getEndNo(),
						currentInfo);

				// 该循环 生成小票单据的详细信息
				for (int i = noteStockIn.getBeginNo(); i <= noteStockIn
						.getEndNo(); i++) {
					// 添加单据明细信息
					addDetailsEntity(noteDetailsLst, stockInEntity, i);

				}

			}
		}

		if (noteNum > 1) {
			throw new SettlementException("下发失败,以上小票明细编号,已存在!");
		}

		// 如果存在下发信息就抛出异常提示用户
		if (null != checkRlt && checkRlt.length() > 0) {
			checkRlt.append("  下发失败,以上编号已存在!");
			throw new SettlementException(checkRlt.toString());
		} else {
			if (CollectionUtils.isNotEmpty(stockInLst)
					&& CollectionUtils.isNotEmpty(noteDetailsLst)) {
				this.addStockInNote(stockInLst);// 保存入库信息
				this.addNoteDetails(noteDetailsLst);// 保存入库明细信息
			}
		}
		// }
		logger.info("successfully exit service");
	}

	/**
	 * 更新入库操作
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午2:16:02
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteDetailsService#uptDetailsStorageByAppIds()
	 */
	@Override
	public void storageInNote(NoteDetailsDto noteDetailsDto) {
		// 输入参数校验
		if (null == noteDetailsDto
				|| CollectionUtils.isEmpty(noteDetailsDto.getNoteAppIds())) {
			throw new SettlementException("内部错误，来源小票票据为空，更新入库操作失败");
		}
		logger.info("entering service...,noteAppIds:"
				+ noteDetailsDto.getNoteAppIds());
		int i = noteDetailsDao.updateStorageInNoteByAppIds(noteDetailsDto);
		if (i < SettlementReportNumber.FIFTY) {
			throw new SettlementException("更新入库操作失败");
		}
		logger.info("entering service...,noteAppIds:"
				+ noteDetailsDto.getNoteAppIds());
	}

	/**
	 * 查询小票单据入库数据
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-29 下午2:53:06
	 */
	@Override
	public List<NoteStockInEntity> queryStockInById(
			NoteStockInDto noteStockInDto, int start, int limit) {
		// 输入参数校验
		if (null == noteStockInDto
				|| StringUtils.isEmpty(noteStockInDto.getNoteAppId())) {
			throw new SettlementException("内部错误，查询条件为空");
		}
		logger.info("entering service...,noteAppId:"
				+ noteStockInDto.getNoteAppId());
		return noteStockInDao.queryStockInById(noteStockInDto, start, limit);
	}

	/**
	 * 小票单据入库数据总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-29 下午2:52:58
	 */
	@Override
	public Long countQueryStockInById(NoteStockInDto noteStockInDto) {
		// 输入参数校验
		if (null == noteStockInDto) {
			throw new SettlementException("内部错误，查询参数为空！");
		}
		return noteStockInDao.countQueryStockInById(noteStockInDto);
	}

	/**
	 * 小票单据详细列表
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午10:55:16
	 */
	@Override
	public List<NoteQueryDto> queryNoteById(NoteQueryDto noteQueryDto,
			int start, int limit) {
		// 输入参数校验
		if (null == noteQueryDto) {
			throw new SettlementException("内部错误，查询参数为空！");
		}
		return noteQueryDao.queryNoteDetailsById(noteQueryDto, start, limit);
	}

	/**
	 * 小票单据详细总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午11:11:18
	 */
	@Override
	public Long countQueryNoteById(NoteQueryDto noteQueryDto) {
		// 输入参数校验
		if (null == noteQueryDto) {
			throw new SettlementException("内部错误，查询参数为空！");
		}
		return noteQueryDao.countQueryNoteDetailsById(noteQueryDto);
	}

	/**
	 * 追加错误提示信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-14 下午8:20:25
	 */
	private String checkNoResult(List<NoteStockInEntity> stockInLst,
			NoteStockInEntity stockInEntity) {
		String rlt = "";
		if (CollectionUtils.isNotEmpty(stockInLst)) {
			rlt = "起止编号:" + stockInEntity.getBeginNo() + ",终止编号:"
					+ stockInEntity.getEndNo() + "; ";
		}
		return rlt;
	}
	
	
	
	

	/**
	 * 添加单据入库信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-20 下午2:31:20
	 */
	private void addStockInEntity(List<NoteStockInEntity> stockInLst,
			NoteStockInEntity stockInEntity, int beginNo, int endNo,
			CurrentInfo currentInfo) {
		stockInEntity.setId(UUIDUtils.getUUID());
		stockInEntity.setBeginNo(beginNo);
		stockInEntity.setEndNo(endNo);
		stockInEntity.setIssuedTime(new Date());
		stockInEntity.setActive(FossConstants.ACTIVE);
		stockInEntity.setIssuedOrgCode(currentInfo.getCurrentDeptCode());
		stockInEntity.setIssuedOrgName(currentInfo.getCurrentDeptName());
		stockInEntity.setIssuedUserCode(currentInfo.getEmpCode());
		stockInEntity.setIssuedUserName(currentInfo.getEmpName());

		stockInLst.add(stockInEntity);
	}

	/**
	 * 添加单据明细
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-20 下午2:31:05
	 */
	private void addDetailsEntity(List<NoteDetailsEntity> noteDetailsLst,
			NoteStockInEntity stockInEntity, int detailNo) {
		NoteDetailsEntity detailsEntity = new NoteDetailsEntity();
		detailsEntity.setId(UUIDUtils.getUUID());
		// 不满8位自动追加0
		detailsEntity.setNoteDetailsNo(getDetailNo(detailNo));
		detailsEntity.setNoteAppId(stockInEntity.getNoteAppId());
		detailsEntity.setNoteStockinId(stockInEntity.getId());
		detailsEntity.setBusinessDate(new Date());
		detailsEntity
				.setStatus(SettlementDictionaryConstants.NOTE_APPLICATION__STATUS__DISTRIBUTE);
		detailsEntity
				.setWriteoffStatus(SettlementDictionaryConstants.NOTE_APPLICATION__WRITEOFF_STATUS__NOT_WRITEOFF);
		detailsEntity.setActive(FossConstants.ACTIVE);

		noteDetailsLst.add(detailsEntity);
	}

	/**
	 * 不满8位自动追加0
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-20 下午2:00:40
	 */
	public String getDetailNo(int detailNo) {
		return String.format("%08d", detailNo);
	}

	/**
	 * 根据小票明细单号 获取申请人及部门信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-3 下午2:18:41
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteDetailsService#queryNoteApplyInfoByDetailNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public NoteQueryDto queryNoteApplyInfoByDetailNo(String noteDetailNo,
			String applyOrgCode, String status) {
		if (StringUtils.isBlank(noteDetailNo)) {
			throw new SettlementException("小票单号为空！");
		}
		return noteQueryDao.queryNoteApplyInfoByDetailNo(noteDetailNo,
				applyOrgCode, status);
	}

	/**
	 * 更新使用人信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午2:11:43
	 */
	@Override
	public void updateUserInfoByDetailNo(NoteDetailsDto noteDetailsDto) {
		// 校验输入参数
		if (null == noteDetailsDto) {
			throw new SettlementException("内部错误，来源参数为空！");
		}
		int i = noteDetailsDao.updateUserInfoByDetailNo(noteDetailsDto);
		if (1 != i) {
			throw new SettlementException("更新使用人失败");
		}
	}

	/**
	 * 校验上一部门最大结束编号对应的小票单号是否已使用
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-3 下午2:18:13
	 */
	@Override
	public NoteQueryDto queryMixNoUseDetailsNo(String endNo, String applyOrgCode) {
		if (StringUtils.isBlank(endNo)) {
			throw new SettlementException("内部错误，结束单号为空！");
		}

		if (StringUtils.isBlank(applyOrgCode)) {
			throw new SettlementException("申请组织编码为空！");
		}

		return noteQueryDao.queryMixNoUseDetailsNo(endNo, applyOrgCode);
	}

	/**
	 * @param noteDetailsDao
	 */
	public void setNoteDetailsDao(INoteDetailsDao noteDetailsDao) {
		this.noteDetailsDao = noteDetailsDao;
	}

	/**
	 * @param noteStockInDao
	 */
	public void setNoteStockInDao(INoteStockInDao noteStockInDao) {
		this.noteStockInDao = noteStockInDao;
	}

	/**
	 * @param noteQueryDao
	 */
	public void setNoteQueryDao(INoteQueryDao noteQueryDao) {
		this.noteQueryDao = noteQueryDao;
	}

}
