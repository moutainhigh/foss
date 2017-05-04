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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/NoteService.java
 * 
 * FILE NAME        	: NoteService.java
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
2012-6-29	创建版 	武江涛	V0.1
2012-7-11	修改	武江涛	V0.5
2012-7-17	根据系统用例评审修改，修改申请查询页面	武江涛	V0.5
2012-7-23	页面样式修改	武江涛	V0.5
2012-7-24	版本升级，修改版本号	武江涛	V0.9
2013-03-14	增加业务规则：小票票据一次申请只能申请一本	曾斌文	V1.1
1.	SUC-768-申请_审批小票单据
1.1	相关业务用例
     BUC_FOSS_4.7.10.30_040  管理小票单据
1.2	用例描述
     为了规范和跟踪小票单据的使用情况，营业部需要新的小票单据必须通过申请，有审核通过下发。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件		
后置条件	1、	生成一条小票单据申请记录
2、	对已经入库的小票单据申请，进行申请核销操作	
1.4	操作用户角色
操作用户	描述
营业部收银员	查询本部门的小票单据申请记录，和申请小票单据操作。
1.5	界面要求
1.5.1	表现方式
Web
1.5.2	界面原型
	初始化界面
 
申请小票单据录入页面
 

1.5.3	界面描述
	页面初始化组件描述：
  申请小票管理页面
1、	申请部门：默认为当前登陆者所在营业部，不可编辑；
2、	申请日期：默认当前服务系统时间，可选择；
3、	入库日期：默认全部空
4、	单据状态：默认全部
5、	核销状态：默认全部
申请小票单据录入页面
1、	申请部门：默认当前登录者所在营业部；
2、	申请数量：手动录入；
3、	申请时间：默认当前服务系统时间，不可编辑；
	页面输入组件描述
1、	申请部门：默认为当前登陆者所在营业部，不可编辑；
2、	申请日期：小票单据申请日期，默认当前服务系统时间，可选择。
3、	入库日期：小票单据入库日期，方便查询已经入库的小票单据，可进行申请核销操作。
单据状态在“综合管理--数据字典-单据状态”中进行维护，现阶段下拉列表内容为：
	全部
	已提交
	已下发
	已入库
单据状态在“综合管理--数据字典-核销状态”中进行维护，现阶段下拉列表内容为：
	全部
	未核销
	已申请
	已核销
页面提供的功能按钮：
	查询
	重置
	申请小票   点击弹出“申请小票单据页面”
	申请核销   对已经入库的小票单据，才能点击“申请核销”按钮。

申请小票单据录入页面
1、	申请部门：默认为当前登陆者所在营业部，不可编辑；
2、	申请数量：手动输入，单位默认为本；
3、	申请时间：默认当前服务系统时间，不可编辑；
4、	申请人：默认当前登录者，不可编辑；
页面提供按钮：
	提交
	取消
1.6	操作步骤
1.6.1	查询
序号	基本步骤	相关数据	补充步骤
1	页面初始化
	无	
2	选择申请部门	无	1、申请部门默认为当前登陆者所在的营业部。
3	选择申请日期	无	2、详见业务规则SR1
4	申请人	无	可填写姓名模糊查询。
5	入库日期	无	1、详见业务规则SR4
6	单据状态	无	
7	核销状态	无	
8	点击“查询”按钮		1.	系统根据输入/选择的查询条件查询数据，并显示在申请小票单据列表界面
2．查询出记录后，查询条件不改变，方便下一次查询
9	点击“重置”按钮	无	查询页面初始化。
10	点击“申请小票单据”按钮	无	弹出申请小票单据页面
1.6.2	申请小票单据
序号	基本步骤	相关数据	补充步骤
11	页面初始化
	无	
12	申请数量	无	申请数量不能为空，必须大于0；
详见业务规则SR2
13	申请日期	无	申请日期不能为空。
14	点击“提交”按钮	无	1、	提交小票单据申请记录，按照12,13步骤进行验证；
2、	服务后端判断申请数据是否为空或不合法；
3、	验证无误的情况下保存申请小票单据记录
4、	保存申请小票单据记录成功之后，关闭当前页面，刷新查询列表。
5、	申请小票单据时，只能申请一本小票单据
15	点击“取消”按钮		关闭当前页面，不做任何其他操作。

1.6.3	申请核销按钮
序号	基本步骤	相关数据	补充步骤
16	修改申请记录的核销状态为已申请。	无	

1.6.4	扩展事件
序号	扩展事件	相关数据	备注
3a	申请日期的开始日期大于结束日期		红色字体提示“申请开始日期不能大于结束日期，请重新选择下发日期！”
3b	申请日期的开始日期与结束日期两者相差超过30天		红色字体提示“申请开始日期与结束日期两者之间不能大于30天，请重新选择下发日期！”
5a	入库日期的开始日期大于结束日期		红色字体提示“入库开始日期不能大于结束日期，请重新选择入库日期！”
5b	入库日期的开始日期与结束日期两者相差超过30天		红色字体提示“入库开始日期与结束日期两者之间不能大于30天，请重新选择入库日期！”
11a	申请部门为空		红色字体提示“申请部门不能为空，请选择！”
12a	申请数量为空		红色字体提示“申请数量不能为空，请填写！”
12b	申请数量小于0，或不合法的情况下		红色字体提示“你输入的数据不合法，申请数量必须为大于0的正整数，请重新填写！”
13a	申请日期为空的情况下		红色字体提示“申请日期不能为空，请填写！”
1.6.5	业务规则
序号	描述
SR1	1、	页面初始化申请日期的开始日期、结束日期为服务系统当前时间，两者相差0天，开始日期不能大于结束日期，两者相差不能大于30天；
SR2	1、	申请数量不能为空，必须大于0的正整数；
2、	申请数据必须为1
SR3	1、	FOSS审批结果为已同意、不同意、都要修改小票单据申请记录：
（1）	工作流审批结果为已同意，小票单据申请记录的审批状态为“已同意”
（2）	工作流审批结果为未同意，小票单据申请记录的审批状态为“未同意”

SR4	1、页面初始化入库日期的开始日期、结束日期为服务系统当前时间，两者相差0天，开始日期不能大于结束日期，两者相差不能大于30天；
1.7	数据元素
1.7.1	申请小票单据列表页面（输入信息）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
申请部门	申请部门名称	下拉选择	无	100	是	
申请部门编码	申请部门编码	下拉选择自动带出	无	20	是	
申请日期—开始日期		文本输入	无	10	否	
申请日期--结束日期		文本输入	无	10	否	
申请人		文本输入	无	10	否	
下发日期	开始日期	文本输入	无	10	否	
下发日期	结束日期	文本输入	无	10	否	
单据状态	单据状态	下拉选择	无	10	否	单据状态有：已提交，已下发，已入库
核销状态		下拉选择	无	10	否	核销状态有：未核销、已申请、已核销

1.7.2	申请小票单据列表页面（输出信息）
字段名称 	说明 	输出限制	输入项提示文本	长度	是否必填	备注
申请编号	申请编号	文本	无	20	是	
申请部门	申请部门名称	文本	无	100	是	
申请数量		文本	无	10	是	
申请日期		时间	无	10	是	
申请人		文本	无	20	是	
申请人编码		文本	无	20	是	
是否有效	默认有效	文本	无	2	是	
单据状态	初始化已提交	文本	无	10	是	
审批状态		文本	无	10	否	
核销状态		文本	无	10	否	
1.7.3	申请小票单据（输入信息）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
申请部门	申请部门名称	下拉选择	无	100	是	
申请部门编码	申请部门编码	下拉选择自动带出	无	20	是	
申请数量		文本输入	无	10	是	
申请时间		文本输入	无	10	是	
申请人	申请人	文本输入	无	20	是	
申请人编码		文本输入	无	20	是	

1.7.4	申请小票单据（输出信息）
字段名称 	说明 	输出限制	输入项提示文本	长度	是否必填	备注
申请编号		文本		20	是	
申请部门	申请部门名称	文本	无	100	是	
申请部门编码	申请部门编码	文本	无	20	是	
申请数量		文本	无	10	是	
申请时间		时间	无	10	是	
申请人	申请人	文本	无	20	是	
申请人编码		文本	无	20	是	
业务日期	服务系统日期	时间	无	10	否	
是否有效	默认有效	文本	无	2	是	
单据状态	初始化已提交	文本	无	10	是	
审批状态		文本	无	10	否	
审批人		文本	无	20	否	
审批人编码		文本	无	20	否	
审批时间		时间	无	20	否	
审批人部门		文本	无	100	否	
审批人编码		文本	无	20	否	
核销状态		文本	无	10	否	
核销人		文本	无	20	否	
核销人编码		文本	无	20	否	
核销部门		文本	无	100	否	
核销部门编码		文本	无	20	否	
核销时间						
备注		文本	无	1000	否	用于审批未通过描述或核销单据描述。


1.8	非功能性需求
使用量	2012年4月全国每月申请小票单据约为200-500次
2012年全网估计用户数	营业部收银员数量约5644（截止2012-5-4）
响应要求（如果与全系统要求 不一致的话）	提交在5秒内响应；
使用时间段	正常白天上班时间（8:00-17：30）
高峰使用时间段	业务特殊无高峰使用时段
1.9	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		
		


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

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.INoteApplyService;
import com.deppon.foss.module.settlement.consumer.api.server.service.INoteDetailsService;
import com.deppon.foss.module.settlement.consumer.api.server.service.INoteService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteApplicationEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteStockInEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteDetailsDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteStockInDto;

/**
 * 小票申请服务
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-12 上午8:23:01
 */
public class NoteService implements INoteService {

	private static final Logger logger = LogManager
			.getLogger(NoteService.class);

	/**
	 * 小票单据申请Service
	 */
	private INoteApplyService noteApplyService;

	/**
	 * 小票单据明细Service
	 */
	private INoteDetailsService noteDetailsService;

	/**
	 * 小票单据申请
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-14 下午8:42:02
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteService#applicationNote(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto)
	 */
	@Override
	@Transactional
	public void applicationNote(NoteApplyDto noteApplyDto,
			CurrentInfo currentInfo) {
		if (noteApplyDto == null) {
			throw new SettlementException("内部错误，小票申请记录为空！");
		}
		noteApplyService.applicationNote(noteApplyDto, currentInfo);
	}

	/**
	 * 小票数据列表
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午10:55:16
	 */
	@Override
	public List<NoteApplyDto> queryNoteApplyEntityByCondition(
			NoteApplyDto noteApplyDto, int start, int limit) {

		if (noteApplyDto == null) {
			throw new SettlementException("内部错误，小票申请记录为空！");
		}

		return noteApplyService.queryNoteApplyEntityByCondition(noteApplyDto,
				start, limit);
	}

	/**
	 * 
	 * 查询小票申请数据的总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午11:11:18
	 */
	@Override
	public Long countNoteApplyEntityByCondition(NoteApplyDto noteApplyDto) {
		if (noteApplyDto == null) {
			throw new SettlementException("内部错误，小票申请记录为空！");
		}
		return noteApplyService.countNoteApplyEntityByCondition(noteApplyDto);
	}

	/**
	 * 小票单据明细列表
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午10:55:16
	 */
	@Override
	public List<NoteQueryDto> queryNoteById(NoteQueryDto noteQueryDto,
			int start, int limit) {
		if (noteQueryDto == null) {
			throw new SettlementException("内部错误，小票查询对象为空！");
		}
		return noteDetailsService.queryNoteById(noteQueryDto, start, limit);
	}

	/**
	 * 
	 * 查询小票单据明细的总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 上午11:11:18
	 */
	@Override
	public Long countQueryNoteById(NoteQueryDto noteQueryDto) {
		if (noteQueryDto == null) {
			throw new SettlementException("内部错误，小票查询对象为空！");
		}
		return noteDetailsService.countQueryNoteById(noteQueryDto);
	}

	/**
	 * 生成小票单据明细
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午2:22:43
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteService#addStockInNote(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteStockInDto)
	 */
	@Override
	@Transactional
	public void createNoteDetails(NoteStockInDto noteStockInDto,
			CurrentInfo currentInfo) {
		if (noteStockInDto == null) {
			throw new SettlementException("内部错误，小票库存对象为空！");
		}

		// 保存批次单号
		noteDetailsService.createNoteDetails(noteStockInDto, currentInfo);
		// 构造批次明细信息
		NoteApplyDto noteApplyDto = new NoteApplyDto();
		noteApplyDto.setId(noteStockInDto.getId());
		noteApplyDto
				.setStatus(SettlementDictionaryConstants.NOTE_APPLICATION__STATUS__DISTRIBUTE);
		noteApplyDto.setModifyDataType(noteStockInDto.getModifyDataType());
		// 更新下发状态
		this.providerNote(noteApplyDto, currentInfo);
	}

	/**
	 * 受理小票申请
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:28:39
	 */
	@Override
	@Transactional
	public void approvalNote(NoteApplyDto noteApplyDto, CurrentInfo currentInfo) {

		if (noteApplyDto == null) {
			throw new SettlementException("内部错误，小票申请对象为空！");
		}
		noteApplyService.approvalNote(noteApplyDto, currentInfo);
	}

	/**
	 * 发放小票申请
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-12 上午8:28:39
	 */
	@Override
	@Transactional
	public void providerNote(NoteApplyDto noteApplyDto, CurrentInfo currentInfo) {
		if (noteApplyDto == null) {
			throw new SettlementException("内部错误，小票申请对象为空！");
		}
		noteApplyService.providerNote(noteApplyDto, currentInfo);
	}

	/**
	 * 小票入库操作
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午2:46:35
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteService#storageInByIds(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto)
	 */
	@Override
	@Transactional
	public void storageInNote(NoteApplyDto noteApplyDto, CurrentInfo currentInfo) {
		
		if(noteApplyDto == null){
			throw new SettlementException("内部错误，小票申请对象为空！");
		}
		
		logger.trace("entering service...");
		// 更新小票单据申请表入库状态
		noteApplyService.storageInNote(noteApplyDto, currentInfo);
		// 为更新小票单据明细设置条件
		NoteDetailsDto noteDetailsDto = new NoteDetailsDto();
		noteDetailsDto.setNoteAppIds(noteApplyDto.getNoteAppIds());
		noteDetailsDto
				.setStatus(SettlementDictionaryConstants.NOTE_APPLICATION__STATUS__IN);
		// 更新小票单据明细的入库状态
		noteDetailsService.storageInNote(noteDetailsDto);
	}

	/**
	 * 小票单据入库单据数据查询
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-29 下午2:07:36
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteService#queryStockIn(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto)
	 */
	@Override
	public List<NoteStockInEntity> queryStockIn(NoteStockInDto noteStockInDto,
			int start, int limit) {
		if(noteStockInDto == null){
			throw new SettlementException("内部错误，小票库存对象为空！");
		}
		return noteDetailsService
				.queryStockInById(noteStockInDto, start, limit);

	}

	/**
	 * 小票单据入库单据数据查询总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-29 下午2:48:32
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteService#countQueryStockIn(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteStockInDto)
	 */
	@Override
	public Long countQueryStockIn(NoteStockInDto noteStockInDto) {
		if(noteStockInDto == null){
			throw new SettlementException("内部错误，小票库存对象为空！");
		}
		return noteDetailsService.countQueryStockInById(noteStockInDto);
	}

	/**
	 * 票据核销操作
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-25 下午4:02:05
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.INoteService#writeoffNote(com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto)
	 */
	@Override
	@Transactional
	public void writeoffNote(NoteApplyDto noteApplyDto, CurrentInfo currentInfo) {
		
		if(noteApplyDto == null){
			throw new SettlementException("内部错误，小票申请对象为空！");
		}
		
		/* BUG-56654 092036-BOCHENLONG
		 *
		 * 查询小票最新状态，如果为已经核销，则不能收回核销申请 
		 */
		NoteApplicationEntity entity = noteApplyService.queryNoteApplyEntityID(noteApplyDto.getNoteAppIds().get(0));
		
		if(entity.getWriteoffStatus().equals(SettlementDictionaryConstants.NOTE_APPLICATION__WRITEOFF_STATUS__WRITEOFF_DONE)) {
			throw new SettlementException("数据已经发生变化，请重新查询！");
		}
		
		// 更新小票单据申请表核销状态
		noteApplyService.writeoffNote(noteApplyDto, currentInfo);
		// 为更新小票单据明细设置条件
		NoteDetailsDto noteDetailsDto = new NoteDetailsDto();
		noteDetailsDto.setWriteoffStatus(noteApplyDto.getWriteoffStatus());
		noteDetailsDto.setNoteAppIds(noteApplyDto.getNoteAppIds());
		noteDetailsDto.setWriteoffTime(new Date());
		// 更新小票单据明细的核销状态
		noteDetailsService.writeoffNote(noteDetailsDto);
	}

	/**
	 * @param noteApplyService
	 */
	public void setNoteApplyService(INoteApplyService noteApplyService) {
		this.noteApplyService = noteApplyService;
	}

	/**
	 * @param noteDetailsService
	 */
	public void setNoteDetailsService(INoteDetailsService noteDetailsService) {
		this.noteDetailsService = noteDetailsService;
	}

}
