/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/vo/AbandonedGoodsModel.java
 * 
 * FILE NAME        	: AbandonedGoodsModel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.creating.client.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsDto;

/**
 * 弃货数据模型
 * @author admin
 * 1.1	相关业务用例
 *BUC_FOSS_5.60.10_650  弃货处理
 *1.2	用例描述
 *外场理货员根据不同的查询条件查询异常类型为“客户弃货”、
 *
 *“存储超时”预弃货信息记录，
 *
 *可选中预弃货信息起草弃货工作流，
 *
 *查看弃货信息和弃货申请工作流审批进度，
 *
 *并导入弃货信息至开单界面，开内部带货运单至大区办公室。
 *
 *1.3	用例条件
 *条件类型	描述	引用系统用例
 *前置条件	
 *1、	在派送异常处理中已将异常货处理为客户弃货、存储超时
 *2、	客户弃货类型的运单已上传相关客户签字的弃货凭证	
 *
 *1、DP-FOSS-接送货系统用例-集中、非集中送货-派送异常处理
 *后置条件	
 *1、	导入OA运单信息至开单界面开内部带货运单至大区办公室
 *2、	导入内部带货开单
 *3、	调用结算红冲接口	
 *1、	DP-FOSS-接送货系统用例-运单生成 –运单开单
 *2、	SUC-486 生成运单
 *3、	SUC-159-异常出库
 *1.4	操作用户角色
 *操作用户	描述
 *外场经理/理货员	
 *1、	起草和查看弃货处理工作流处理状态、弃货凭证和导入开内部带货运单
 *1.5	界面要求
 *1.5.1	表现方式
 *Web页面
 *1.5.2	界面原型 
 *界面描述
 *    外场经理或理货员可在该界面录入条件查询出待处理仓库异常货物运单，
 *    并对选中的运单起草仓库异常货物处理工作流，
 *    查看处理凭证，查看工作流审批进度，导入运单开单。 
 *界面主要由两部分组成：[仓库异常货物处理]界面、
 *[查看仓库异常货物]界面。
 *一、仓库异常货物处理
 *1.	查询条件：运单号、处理状态、仓库异常货物类型、
 *发货部门、发货人、预处理人、预处理时间、查询、重置。
 *a)	运单号：客户要求更改的运单号。
 *b)	处理状态：仓库异常货物处理的处理状态分为四种：
 *待处理、申请中、申请未通过、申请通过、已处理（内部带货）、客户要求变更。
 *c)	仓库异常货物类型：派送异常货拉回转仓库异常货物的，
 *包含客户弃货、存储超时。
 *d)	发货人姓名：发货客户的名字。
 *e)	预处理人：新增预处理仓库异常货物的操作人姓名
 *f)	预处理时间：新增预处理仓库异常货物时间
 *g)	查询：根据录入条件查询仓库异常货物运单
 *h)	重置：清空查询条件至默认状态
 *2.	处理仓库异常货物：工作流、仓库异常货物信息、
 *运单号、处理状态、
 *预处理人、收货人姓名、收货人电话、预处理时间、
 *总重量、总体积、总件数、导入内部带货
 *二、查看仓库异常货物
 *	该界面包含单号、货物名称、重量、体积、发货人、
 *发货人电话、发货部门、
 *所属区域、收货人、收货人电话、到达部门、仓储部门、
 *代收金额、保险金额、
 *预付金额、到付金额、仓库异常货物类型、处理状态、
 *预处理时间、预处理人、仓储时长。
 *需要提供的相关用例链接或提示信息：
 *链接：
 *1）工作流已处理后，可导入运单信息开内部带货，
 *点击“导入”连接到“运单生成”界面。
 *2）若未起草弃货工作流，
 *则外场经理或理货员可进入起草弃货处理工作流界面。
 *提示信息：
 *1）内部带货：“对不起，工作流未审批完，不能导入开单！”。
 *2）弃货处理时间：“查询时间不能超过30天！”
 *二、界面标题：查询弃货处理
 *1.	查询弃货信息：运单号、货物名称、
 *重量、体积、发货人、发货人电话、
 *发货部门、所属区域、收货人、收货人电话、
 *到达部门、仓储部门、代收金额、
 *保险金额、预付金额、到付金额、弃货类型、
 *处理状态、弃货事由、预弃货人、
 *预弃货时间、仓储时长。
 *1.6	操作步骤
 *序号	基本步骤	相关数据	补充步骤
 *1	打开[仓库异常货物处理]界面，在[查询条件]栏录入查询条件	
 *无	显示默认查询条件
 *(参见SR1)
 *2	点击[查询]后，在“开单选择”查询列表中显示符合查询条件的记录
 *无	查询的信息显示在[处理仓库异常货物]栏中
 *(参见SR2)
 *3	选中[处理仓库异常货物]栏中的一条记录点击[起草]链接
 *仓库异常货物信息
 *[起草]和[查询]仓库异常货物信息
 *(参见SR3)
 *4	点击选中行的仓库异常货物信息列的[查看]按钮
 *仓库异常货物信息
 *弹出[查看仓库异常货物信息]界面图2
 *（参见SR4）
 *选中一条记录或多条记录，点击[导入内部带货]按钮
 *仓库异常货物信息
 *系统将选中的记录信息保存到临时表中
 *（参见SR5）
 *开打[运单新增]界面，点击[仓库异常货物处理]按钮
 *仓库异常货物信息
 *系统显示本部门要处理的所有仓库异常货物信息
 *（参见SR6）
 *7	选择要内部带货开单的记录，导入开单。参见步骤4
 *仓库异常货物信息
 *将弃货运单导入开单，开单方式为内部带货
 *(引用“DP-FOSS-接送货系统用例-运单生成 –运单开单”)
 *（参见SR7）8			
 *9		无	
 *扩展事件
 *序号	扩展事件	相关数据	备注
 *1a
 *1、	根据查询条件无法查询出数据时，
 *在[仓库异常货物处理]栏中显示“无记录”
 *2、	当需要重新录入查询条件时，
 *点击[重置]按钮界面查询数据重置回默认数值。
 *无	进入[仓库异常货物处理]界面时，
 *默认[处理仓库异常货物]栏任何记录
 *6a	“查询弃货信息”界面显示详细弃货信息，
 *用户可查看凭证		
 *1.7	业务规则
 *序号	描述
 *SR1
 *1、	默认仓库异常货物类型与处理状态都为“全部”，
 *其余为空。
 *SR2	1、	系统默认查询起止时间为当天的00：00：00到23：59：59，
 *查询时间段不能超过30天。若超过30天则提示“对不起，
 *查询时间段不能超过30天！”
 *2、	处理状态：
 *1）	待处理：未对预处理运单进行起草申请的处理，
 *即初始状态
 *2）	申请中：在OA中已成功提交仓库异常货物处理工作流，等待审批
 *3）	申请未通过：因无凭证等原因起草的工作流被退回，
 *此时可再点击[起草]重新起草工作流
 *4）	申请通过：为OA工作流审批通过时的状态
 *5）	已处理（内部带货）：点击“导入”后开内部带货单提交成功时反写后的状态，
 *此时不可再起草（起草链接点击后无反应）
 *6）	客户要求变更：呼叫中心审批工作流时联系发货人是否弃货，
 *发货人要求返货，
 *呼叫中心将工作流变更为拒绝的状态
 *3、	外场理货员根据查询条件只能查询到货物库存状态在本部门库存中的货物信息
 *不根据电话来查询，但查询出的结果会将电话与手机拼接成字符串，中间用"/"隔开，
 *若只有一个联系方式则不需要拼接。
 *SR3	1、	若处理状态为[待处理]、[申请未通过]时，[起草]链接可用，
 *[导入内部带货]的按钮不可用。
 *2、	若处理状态为[申请中]、[申请通过]、[已处理（内部带货）]、
 *[客户要求变更]状态时，[起草]链接为不可用状态。
 *处理状态为[申请通过]时导入内部带货按钮才可用，
 *其它状态为不可用。
 *3、	仓库异常货物列的[查看]按钮在任何时候都可点击。
 *4、	发货人名字在查询时可模糊匹配。
 *5、	系统将起草弃货工作流的信息通过OA接口提交至OA系统中，
 *成功生成弃货工作流后返回成功状态，
 *FOSS系统自动更新状态为[申请中]；
 *若OA返回状态显示弃货工作生成失败时，
 *FOSS系统自动中提示“处理仓库异常货物工作流失败”，
 *状态仍为[待申请]
 *6、	如果工作流处理结果是同意，
 *仓库异常货物状态改变为：申请通过，
 *FOSS系统调用签收出库接口(参见SUC-789 签收出库)对此票货自动签收出库，
 *并标示此货为仓库异常货物，
 *如果此货有到付款、应收款等财务数据，系统自动清零，
 *引用结算用例（参见SUC-159-异常出库）
 *SR4	1、	在弹出的[查看仓库异常货物信息]界面，
 *可点击[查看]链接在浏览器中查看客户上传的弃货凭证。
 *SR5	1、	当选中表头的全选框时，
 *系统自动选中所有可选择的记录（即[申请已通过]状态的记录）。
 *2、	系统自动计算所有已选中记录的总重量、总体积、总件数，
 *并显示在表格下方。
 *3、	点击[导入内部带货]时系统将选中运单信息保存到处理仓库异常货物临时表中。
 *SR6	1、	在运单开单界面查询出处理仓库异常货物临时表中数据，
 *用户选要开内部带货的记录导入开单。
 *2、	系统自动计算所有已选中记录的总重量、总体积、总件数，并显示在表格下方，可参见SR5
 *SR7	1、	导入开单时：开单提货方式为“内部带货”，
 *其它界面填充信息参见“DP-FOSS-接送货系统用例-运单生成 –运单开单”
 *2、	导入开单成功后：
 *1）	系统自动更新所有选中记录对应运单的“储运事项”，
 *内容为“该运单已弃货处理成功，
 *内部带货至大区办公室，运单号为XXXXXXXX”，XXXXXXXX为内部带货开单运单号。
 *2）	系统自动更新所有选中记录的处理状态为“已处理（内部带货）”，
 *且记录复选框重置为未选中且为不可编辑状态。
 *3）	系统更新内部带货运单的“储运事项”，内容为“申请弃货处理的运单号为XXX、XXX、XXX”，
 *其中“XXX”为所有批量导入开单的运单号。
 *
 */
public class AbandonedGoodsModel extends DefaultTableModel {
	/**
	 * 6
	 */
	private static final int SIX = 6;

	/**
	 * 5
	 */
	private static final int FIVE = 5;

	/**
	 * 4
	 */
	private static final int FOUR = 4;

	/**
	 * 3
	 */
	private static final int THREE = 3;

	/**
	 * 2
	 */
	private static final int TWO = 2;

	/**
	 * 1
	 */
	private static final int ONE = 1;

	/**
	 * 0
	 */
	private static final int ZERO = 0;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 7347815570275789337L;
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(AbandonedGoodsModel.class); 
	
	/**
	 * 弃货 dto
	 */
	private List<AbandonedGoodsDto> abandonedGoodsList;
	
	/**
	 * 弃货 dto for selected
	 */
	private List<AbandonedGoodsDto> selectedValues = new ArrayList<AbandonedGoodsDto>();
	
	/**
	 * 改变的列表
	 */
	private List<ChangeListener> changeListeners = new Vector<ChangeListener>();
	
	/**
	 * 
	 * 添加事件
	 * @author 025000-FOSS-helong
	 * @date 2013-3-19 下午08:18:08
	 * @param changeListener
	 */
	public void addChangeListener(ChangeListener changeListener){
		changeListeners.add(changeListener);
	}
	
	/**
	 * 
	 * 去掉添加事件
	 * @author 025000-FOSS-helong
	 * @date 2013-3-19 下午08:18:22
	 * @param changeListener
	 */
	public void removeChangeListener(ChangeListener changeListener){
		changeListeners.remove(changeListener);
	}
	
	/**
	 * 
	 * 去掉添加事件
	 * @author 025000-FOSS-helong
	 * @date 2013-3-19 下午08:18:30
	 */
	public void fireSelectedChanged(){
		Iterator<ChangeListener> iterator = changeListeners.iterator();
		ChangeEvent changeEvent = new ChangeEvent(this);
		while(iterator.hasNext()){
			ChangeListener changeListener =	iterator.next();
			changeListener.stateChanged(changeEvent);
		}
	}
	
	/**
	 * 
	 * getAbandonedGoodsList
	 * @author 025000-FOSS-helong
	 * @date 2013-3-19 下午08:18:44
	 * @return
	 */
	public List<AbandonedGoodsDto> getAbandonedGoodsList() {
		return abandonedGoodsList;
	}

	/**
	 * 
	 * setAbandonedGoodsList
	 * @author 025000-FOSS-helong
	 * @date 2013-3-19 下午08:18:44
	 * @return
	 */
	public void setAbandonedGoodsList(List<AbandonedGoodsDto> abandonedGoodsList) {
		this.abandonedGoodsList = abandonedGoodsList;
		selectedValues.clear();
		fireSelectedChanged();
	}

	/**
	 * 获得列名
	 * @param column
	 * @return
	 */
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case ONE:
			return i18n.get("foss.gui.creating.submitConfirmDialog.waybillNo.label");
		case TWO:
			return i18n.get("foss.gui.creating.abandonedGoodsModel.predeal");
		case THREE:
			return i18n.get("foss.gui.creating.submitConfirmDialog.consignee.label");
		case FOUR:
			return i18n.get("foss.gui.creating.submitConfirmDialog.consigneePhone.label");
		case FIVE:
			return i18n.get("foss.gui.creating.abandonedGoodsModel.dealTime");
		default:
			return "";
		}
	}

	/**
	 * 获得列 NUMBER
	 * @return
	 */
	@Override
	public int getColumnCount() {
		return SIX;
	}
	
	/**
	 * 获得列 Count
	 * @return
	 */
	@Override
	public int getRowCount() {
		return abandonedGoodsList == null ? 0 : abandonedGoodsList.size();
	}

	/**
	 * 获得列 VALUE
	 * @param row
	 * @param column
	 * @return
	 */
	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case ZERO:
			return selectedValues.contains(abandonedGoodsList.get(row));
		case ONE:
			return abandonedGoodsList.get(row).getWaybillNo();
		case TWO:
			return abandonedGoodsList.get(row).getPreTreatPerson();
		case THREE:
			return abandonedGoodsList.get(row).getReceiverName();
		case FOUR:
			return abandonedGoodsList.get(row).getReceiverTel();
		case FIVE:
			return abandonedGoodsList.get(row).getTreatTime();
		default:
			 return "";
		}

	}
	
	/**
	 * 设置表格中CheckBox
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午9:50:52
	 * @see javax.swing.JTable#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		switch (column) {
		case 0:
			return Boolean.class;
		default:
			return super.getColumnClass(column);
		}

	
	}
	
	/**
	 * 获得列 VALUE
	 * @param aValue
	 * @param row
	 * @param column
	 */
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		switch (column) {
		case 0:
			Boolean isSelected = (Boolean)aValue;
			if(isSelected){
				selectedValues.add(abandonedGoodsList.get(row));
			}else{
				selectedValues.remove(abandonedGoodsList.get(row));
			}
		default:
			 break;
		}
		fireSelectedChanged();
	}
	
	/**
	 * 是否可以编辑
	 * @param row
	 * @param column
	 * @return
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return column == 0;
	}

	/**
	 * 获得选中的VALUE列表
	 * @return
	 */
	public List<AbandonedGoodsDto> getSelectedValues() {
		return selectedValues;
	}
	
}