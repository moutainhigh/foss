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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/customer/QueryOtherChargePanel.java
 * 
 * FILE NAME        	: QueryOtherChargePanel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-creating
 * PACKAGE NAME: com.deppon.foss.module.pickup.creating.client.ui.customer
 * FILE    NAME: QueryOtherChargePanel.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.creating.client.ui.customer;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.creating.client.action.OtherChargeQueryAction;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.editui.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 定义“查询其它费用”界面
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-10-17 上午10:28:10
 */
public class QueryOtherChargePanel extends JDialog {

	/**
	 * 至尊宝需求优化
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-04-14下午17:39
	 */
	private WaybillPanelVo bean=null;
	
	/**
	 * 10
	 */
	private static final int TEN = 10;

	/**
	 * 12
	 */
	private static final int FONTSIZE12 = 12;

	/**
	 * 序列货标识
	 */
	private static final long serialVersionUID = -1422237527226994454L;

	private static final int NUM_50000 = 50000;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(QueryOtherChargePanel.class); 

	/**
	 * service
	 */
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	// 优惠名称
	private JTextField txtPrivilegeName;

	// 运单界面
	private WaybillEditUI waybillEditUI;

	private JXTable table;

	@ButtonAction(icon = "preview.png", shortcutKey = "", type = OtherChargeQueryAction.class)
	private JButton btnQuery;// 查询

	/**
	 * 构造方法
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-17 上午10:29:14
	 */
	public QueryOtherChargePanel(WaybillEditUI waybillEditUI) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		this.waybillEditUI = waybillEditUI;
		/**
		 * 根据至尊宝需求，优化代码
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-04-15上午08:35
		 */
		HashMap<String, IBinder<WaybillPanelVo>> map = waybillEditUI.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		bean = waybillBinder.getBean();
		init();
		bind();
		
		//监听Enter
		EnterKeyQueryOtherCharge enter=new EnterKeyQueryOtherCharge(btnQuery);
		txtPrivilegeName.addKeyListener(enter);
		EnterKeyQueryOtherCharge enterTable=new EnterKeyQueryOtherCharge(this);
		table.addKeyListener(enterTable);
	}
	/**
	 * 去除安装费子项
	 * @return 
	 */
	private  void removeInstallData(List<OtherChargeVo> voList)
	{
		if (CollectionUtils.isNotEmpty(voList)) {
			for (int j = 0; j < voList.size(); j++) {
				if (!"".equals(voList.get(j).getCode())) {
					if (voList.get(j).getCode().startsWith(WaybillConstants.instCode)||voList.get(j).getCode().startsWith(WaybillConstants.instsCode)
							||voList.get(j).getCode().startsWith(WaybillConstants.instqCode)||voList.get(j).getCode().startsWith(WaybillConstants.SHAZ)
							||voList.get(j).getCode().startsWith(WaybillConstants.SHSL)||voList.get(j).getCode().startsWith(WaybillConstants.SHBSL)) {
						voList.remove(voList.get(j));
					}
			}
			}
		}

	}
	
	/**
	 * 如果是合伙人，需要再移除
	 * VIP/全网活跃线路营销	VIPXLYX
	 * 家居配送折扣	        JJTZXFY
	 * 新点10元优惠券	    XDYHJ
	 * 城际特惠	            DLCJTH
	 * 2016年1月15日 15:06:56 葛亮亮
	 */
	private  void removePartenerItem(List<OtherChargeVo> voList)
	{
		if (voList != null && voList.size() > 0) {
			for (int j = 0; j < voList.size(); j++) {
				if (!"".equals(voList.get(j).getCode())) {
					if (voList.get(j).getCode().startsWith(WaybillConstants.TYPE_OF_VIPXLYX)
						||voList.get(j).getCode().startsWith(WaybillConstants.TYPE_OF_JJTZXFY)
						||voList.get(j).getCode().startsWith(WaybillConstants.TYPE_OF_XDYHJ)
						||voList.get(j).getCode().startsWith(WaybillConstants.TYPE_OF_DLCJTH)) {
						voList.remove(voList.get(j));
					}
			    }
			}
		}
	}
	
	/**
	 * 初始化界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-17 上午10:40:34
	 */
	private void init() {
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("10dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("35dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("10dlu"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("172dlu"),}));

		//其它费用查询
		JLabel label = new JLabel(i18n.get("foss.gui.creating.queryOtherChargePanel.title"));
		label.setFont(new Font("宋体", Font.BOLD, FONTSIZE12));
		getContentPane().add(label, "2, 2, 7, 1");

		//优惠名称
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.queryOtherChargePanel.privilegeName.label")+"：");
		getContentPane().add(lblNewLabel, "4, 4, 2, 1, left, default");

		txtPrivilegeName = new JTextField();
		getContentPane().add(txtPrivilegeName, "6, 4, 3, 1, fill, default");
		txtPrivilegeName.setColumns(TEN);

		//查询
		btnQuery = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
		getContentPane().add(btnQuery, "10, 4, left, default");

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "4, 6, 13, 1, fill, fill");

		initTable();
		initData();
		scrollPane.setViewportView(table);

		// 设置模态
		setModal(true);
		pack();
	}

	/**
	 * 
	 * 绑定
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午07:12:39
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}

	/**
	 * 
	 * 初始化表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午10:47:03
	 */
	private void initTable() {
		table = new JXTable();
		table.setAutoscrolls(true);
		table.setHorizontalScrollEnabled(true);
		table.setColumnControlVisible(true);
		table.setSortable(false);
		table.setEditable(false);
		table.setModel(new ChangeInfoDetailTableModel());
		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

		table.addMouseListener(new ClickTableHandler());
	}

	/**
	 * 
	 * 初始化数据
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:52:18
	 */
	private void initData() {
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(getQueryParam());
		List<OtherChargeVo> voList = getOtherChargeList(list);
		removeInstallData(voList);		
		/*如果是合伙人，需要再移除一部分数据 2016年1月15日 15:09:17 葛亮亮*/
		if(BZPartnersJudge.IS_PARTENER){
			removePartenerItem(voList);
		}
		setChangeDetail(voList, voList);
	}

	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getQueryParam() {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		if("A".equals(bean.getGoodsType()) || "B".equals(bean.getGoodsType())){
			//如果是A B货，则设置为全部，因为增值服务里面没有A B类型
			queryDto.setGoodsTypeCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);// 货物类型CODE
		}else{
			queryDto.setGoodsTypeCode(bean.getGoodsType());// 货物类型CODE
		}	
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(Common.nullBigDecimalToZero(bean.getGoodsWeightTotal()));// 重量
		queryDto.setVolume(Common.nullBigDecimalToZero(bean.getGoodsVolumeTotal()));// 体积
		queryDto.setOriginnalCost(null);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setBillTime(bean.getBillTime());//开单时间
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());//20160509 update by huangwei 解决查不到客户增值服务的问题
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}

	/**
	 * 
	 * 将查询出的其他费用设置到表格list中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:00:49
	 */
	public List<OtherChargeVo> getOtherChargeList(List<ValueAddDto> list) {
		List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();

		if (list != null) {
			for (ValueAddDto dto : list) {
				
				//开单的时候不能增加更改费
				if(PriceEntityConstants.PRICING_CODE_GGF.equals(dto.getSubType())
					|| PriceEntityConstants.PRICING_CODE_SHJC.equals(dto.getSubType())){
					continue;
				}
				/**
				 * 至尊宝费用冲减判断是否显示
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-04-14下午14:40
				 */
				if(bean.getInsuranceAmount()!=null
						&&bean.getInsuranceAmount().compareTo(new BigDecimal(NUM_50000))<0
						&&PriceEntityConstants.PRICING_CODE_ZZB.equalsIgnoreCase(dto.getSubType())){
					continue;
				}
				OtherChargeVo vo = new OtherChargeVo();
				vo.setCode(dto.getSubType());// 费用编码
				vo.setChargeName(dto.getSubTypeName());// 名称
				vo.setType(dto.getPriceEntityName());// 归集类别
				vo.setDescrition(dto.getDescription());//zxy 20131118 KDTE-5995 修改：改成把code 改成 Description
				vo.setMoney(dto.getFee().toString());// 金额
				vo.setUpperLimit(dto.getMaxFee().toString());// 上限
				vo.setLowerLimit(dto.getMinFee().toString());// 下限
				vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto.getCanmodify()));// 是否可修改
				vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete()));// 是否可删除
				vo.setId(dto.getId());
				voList.add(vo);
			}
		}
		return voList;
	}

	/**
	 * 
	 * 设置其他费用到运单开单界面的其他费用表格中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午08:41:48
	 */
	private void setOtherCharge() {
		JXTable otherTable = waybillEditUI.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if (data != null && !data.isEmpty()) {
			OtherChargeVo otherVo = getOtherCharge();
			addOtherCharge(data, otherVo, bean);
		}else{
			data = new ArrayList<OtherChargeVo>();
			OtherChargeVo otherVo = getOtherCharge();
			addOtherCharge(data, otherVo, bean);
		}
		waybillEditUI.incrementPanel.setChangeDetail(data);
	}

	/**
	 * 
	 * 比较是否存在当前选的费用，不存在则添加费用到表格中，并且进行其他费用合计
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-21 下午07:06:33
	 */
	private void addOtherCharge(List<OtherChargeVo> data, OtherChargeVo newOtherCharge, WaybillPanelVo bean) {
		boolean bool = true;

		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				OtherChargeVo otherVo = data.get(i);
				// 比较费用名称，判断是否存在重复的返单费用
				if (otherVo.getChargeName().equals(newOtherCharge.getChargeName())) {
					bool = false;
					data.remove(i);
					data.add(i, newOtherCharge);
					break;
				}
			}
		}
		// 如果选择的其他费用不存在，则直接添加
		if (bool) {
			if(data != null){
				data.add(newOtherCharge);
			}
			// 累计其他费用合计
			otherSum(data,bean);
			//其他费用更改以后需要重新计算运费
			if(this.waybillEditUI.getPictureWaybillType() != null && WaybillConstants.WAYBILL_PICTURE.equals(this.waybillEditUI.getPictureWaybillType().trim())){
				String weight = this.waybillEditUI.pictureCargoInfoPanel.getTxtWeight().getText();
				String volume = this.waybillEditUI.pictureCargoInfoPanel.getTxtVolume().getText();
				if(StringUtils.isNotBlank(weight) && new BigDecimal(weight).compareTo(BigDecimal.ZERO) > 0 
						&& StringUtils.isNotBlank(volume) && new BigDecimal(volume).compareTo(BigDecimal.ZERO) > 0){
					this.waybillEditUI.incrementPanel.getBtnCalculate().setEnabled(true);
					this.waybillEditUI.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
					this.waybillEditUI.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(false);
					this.waybillEditUI.incrementPanel.getJlable().setVisible(false);
					this.waybillEditUI.incrementPanel.getCombServiceRate().setVisible(false);
				}else{
					this.waybillEditUI.incrementPanel.getBtnCalculate().setEnabled(false);
					this.waybillEditUI.billingPayPanel.getBtnSubmit().setEnabled(true);// 提交为不可编辑
					this.waybillEditUI.billingPayPanel.getBtnSubmitAndNextSingle().setEnabled(true);
					this.waybillEditUI.incrementPanel.getJlable().setVisible(true);
					this.waybillEditUI.incrementPanel.getCombServiceRate().setVisible(true);
				}

			}else{
				this.waybillEditUI.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
				this.waybillEditUI.buttonPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
			}
		} else {
			// 累计其他费用合计
			otherSum(data,bean);
		}
	}
	
	/**
	 * 
	 * 其他費用累計
	 * @author 025000-FOSS-helong
	 * @date 2013-4-18 下午04:17:58
	 */
	private void otherSum(List<OtherChargeVo> data,WaybillPanelVo bean)
	{
		BigDecimal otherCharge = BigDecimal.ZERO;
		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				OtherChargeVo otherVo = data.get(i);
				otherCharge = otherCharge.add(new BigDecimal(otherVo.getMoney()));
			}
		}
		bean.setOtherFee(otherCharge);
		bean.setOtherFeeCanvas(otherCharge.toString());
	}
	

	/**
	 * 
	 * 从表格中获取双击的其他费用数据
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 下午01:49:04
	 */
	private OtherChargeVo getOtherCharge() {
		//获取其他费用
		JXTable otherTable = waybillEditUI.incrementPanel.getTblOther();
		WaybillOtherCharge oldmodel = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> olddata = oldmodel.getData();
		int row = table.getSelectedRow();
		ChangeInfoDetailTableModel model = (ChangeInfoDetailTableModel) table.getModel();
		List<OtherChargeVo> data = model.getData();
		OtherChargeVo  otherChargeVo =data.get(row);
		if(olddata!=null&&!olddata.isEmpty()){
			for(OtherChargeVo vo:olddata ){
				if(vo.getCode().equals(otherChargeVo.getCode())){
					return vo;
				}
			}
		}
		if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(otherChargeVo.getCode())){
			otherChargeVo.setCode(PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ);
			//定价体系优化项目POP-439
//			otherChargeVo.setCode(PriceEntityConstants.QT_CODE_CZHCZFWF);
			otherChargeVo.setIsDelete(true);
		}
		return otherChargeVo;
	}

	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setChangeDetail(List<OtherChargeVo> changeDetailList, List<OtherChargeVo> bakList) {
		ChangeInfoDetailTableModel changeInfoDetailTableModel = ((ChangeInfoDetailTableModel) table.getModel());
		changeInfoDetailTableModel.setBakList(bakList);// 设置备份数据
		changeInfoDetailTableModel.setData(changeDetailList);
		// 刷新表格数据
		changeInfoDetailTableModel.fireTableDataChanged();
	}

	/**
	 * 一般内部类：表格的单、双击处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午10:16:42
	 */
	private class ClickTableHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// 双击
			if (e.getClickCount() == 2) {
				setOtherCharge();
				// 关闭界面，释放资源
				dispose();
			}
		}
	}
	
	/**
	 * table表格的Enter键监控
	 * @author WangQianJin
	 * @date 2013-5-20 下午3:25:28
	 */
	public void tableListenter(){
		setOtherCharge();
		// 关闭界面，释放资源
		dispose();
	}

	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public static class ChangeInfoDetailTableModel extends DefaultTableModel {
		// 7
		private static final int EIGHT = 8;
		// 7
		private static final int SEVEN = 7;
		// 6
		private static final int SIX = 6;

		private static final int FIVE = 5;

		private static final int FOUR = 4;

		private static final int THREE = 3;

		private static final int TWO = 2;

		private static final int ONE = 1;

		private static final int ZERO = 0;

		private static final long serialVersionUID = 5883365603131625962L;
		
		/**
		 * 国际化对象
		 */
		private II18n i18n = I18nManager.getI18n(ChangeInfoDetailTableModel.class); 

		private List<OtherChargeVo> changeDetailList;

		private List<OtherChargeVo> bakList;// 此集合用于在内存中查询数据

		/**
		 * get 查询数据
		 * 
		 * @return
		 */
		public List<OtherChargeVo> getBakList() {
			return bakList;
		}

		/**
		 * set 查询数据
		 * 
		 * @param bakList
		 */
		public void setBakList(List<OtherChargeVo> bakList) {
			this.bakList = bakList;
		}

		/**
		 * get 数据
		 * 
		 * @return
		 */
		public List<OtherChargeVo> getData() {
			return changeDetailList;
		}

		/**
		 * set 数据
		 * 
		 * @return
		 */
		public void setData(List<OtherChargeVo> changeDetailList) {
			this.changeDetailList = changeDetailList;
		}

		/**
		 * 获得选中的VALUE列表 name
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case ZERO:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.zero");
			case ONE:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.one");
			case TWO:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.two");
			case THREE:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.three");
			case FOUR:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.four");
			case FIVE:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.five");
			case SIX:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.six");
			case SEVEN:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.seven");
			default:
				return "";
			}
		}

		/**
		 * get column count
		 */
		@Override
		public int getColumnCount() {
			return EIGHT;
		}

		/**
		 * get row column
		 */
		@Override
		public int getRowCount() {
			return changeDetailList == null ? 0 : changeDetailList.size();
		}

		/**
		 * get value
		 */
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case ZERO:
				return changeDetailList.get(row).getChargeName();
			case ONE:
				return changeDetailList.get(row).getType();
			case TWO:
				return changeDetailList.get(row).getDescrition();
			case THREE:
				return changeDetailList.get(row).getMoney();
			case FOUR:
				return changeDetailList.get(row).getUpperLimit();
			case FIVE:
				return changeDetailList.get(row).getLowerLimit();
			case SIX:
				return changeDetailList.get(row).getIsUpdate()? 
						i18n.get("foss.gui.creating.waybillEditUI.common.yes") : 
							i18n.get("foss.gui.creating.waybillEditUI.common.no");
			case SEVEN:
				return changeDetailList.get(row).getIsDelete()? 
						i18n.get("foss.gui.creating.waybillEditUI.common.yes") : 
							i18n.get("foss.gui.creating.waybillEditUI.common.no");
			default:
				return super.getValueAt(row, column);
			}

		}
		/**
		 * 来判断当前选中的单元格是够可以被编辑，因为我是从第二列需要可以编辑的，也就是复选框的列可以编辑的，故
		 * 我有个逻辑判断的哈
		 */
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if(columnIndex == NumberConstants.NUMBER_5 || columnIndex == NumberConstants.NUMBER_6||columnIndex==NumberConstants.NUMBER_7||columnIndex==0) {
				return true;
			}
			return false;
		}

	}
	

	/**
	 * getTxtPrivilegeName
	 * 
	 * @return JTextField
	 */
	public JTextField getTxtPrivilegeName() {
		return txtPrivilegeName;
	}

	/**
	 * getTable
	 * 
	 * @return JXTable
	 */
	public JXTable getTable() {
		return table;
	}

}