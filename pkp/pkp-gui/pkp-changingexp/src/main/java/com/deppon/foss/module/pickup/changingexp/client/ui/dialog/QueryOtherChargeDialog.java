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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/dialog/QueryOtherChargeDialog.java
 * 
 * FILE NAME        	: QueryOtherChargeDialog.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-changingexp
 * PACKAGE NAME: com.deppon.foss.module.pickup.changingexp.client.ui.customer
 * FILE    NAME: QueryOtherChargePanel.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.changingexp.client.ui.dialog;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.pickup.changingexp.client.action.OtherChargeQueryAction;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.ui.internal.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.changingexp.client.utils.Common;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
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
public class QueryOtherChargeDialog extends JDialog {
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(QueryOtherChargeDialog.class); 

	/**
	 * width
	 */
	private static final float WIDTH = 0.5f;

	/**
	 * 12
	 */
	private static final int TWELVE = 12;

	/**
	 * DEFAULT CSS
	 */
	private static final String CSSGROW = "50dlu:grow";

	/**
	 * 序列货标识
	 */
	private static final long serialVersionUID = -1422237527226994454L;

	private static final int TEN = 10;

	/**
	 * 更改单Service
	 */
	IWaybillRfcService waybillService = WaybillRfcServiceFactory.getWaybillRfcService();

	/**
	 *  优惠名称
	 */
	private JTextField txtPrivilegeName;

	/**
	 * 运单界面
	 */
	WaybillRFCUI waybillEditUI;

	/**
	 * 其他费用表格
	 */
	JXTable table;

	/**
	 * 查询按钮
	 */
	@ButtonAction(icon = "preview.png", shortcutKey = "", type = OtherChargeQueryAction.class)
	JButton btnQuery;

	/**
	 * 构造方法
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-17 上午10:29:14
	 */
	public QueryOtherChargeDialog(WaybillRFCUI waybillEditUI) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		this.waybillEditUI = waybillEditUI;
		init();
		bind();
	}
	

	/**
	 * 初始化界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-17 上午10:40:34
	 */
	private void init() {
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("10dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("35dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(CSSGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(CSSGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(CSSGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(CSSGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(CSSGROW),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("10dlu"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("60dlu"),}));

		//其它费用查询
		JLabel label = new JLabel(i18n.get("foss.gui.changingexp.queryOtherChargePanel.title"));
		label.setFont(new Font("宋体", Font.BOLD, TWELVE));
		getContentPane().add(label, "2, 2, 7, 1");
		
		//优惠名称
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.changingexp.queryOtherChargePanel.privilegeName.label")+"：");
		getContentPane().add(lblNewLabel, "4, 4, 2, 1, left, default");

		txtPrivilegeName = new JTextField();
		getContentPane().add(txtPrivilegeName, "6, 4, 3, 1, fill, default");
		txtPrivilegeName.setColumns(TEN);

		//查询
		btnQuery = new JButton(i18n.get("foss.gui.changingexp.waybillRFCUI.common.query"));
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
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午07:12:39
	 */
	private void bind()
	{
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
		table.setModel(new ChangeInfoDetailTableModel());
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setEditable(false);
		table.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);

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
		List<ValueAddDto> list = waybillService
				.queryValueAddPriceList(getQueryParam());
		List<OtherChargeVo> voList = getOtherChargeList(list);
		setChangeDetail(voList,voList);
	}

	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getQueryParam() {
		WaybillPanelVo bean = waybillEditUI.getBinderWaybill();

		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		queryDto.setGoodsTypeCode(bean.getGoodsType());// 货物类型CODE
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

		if(list != null)
		{
			
			for (ValueAddDto dto : list) {
				OtherChargeVo vo = new OtherChargeVo();
				vo.setCode(dto.getSubType());// 费用编码
				vo.setChargeName(dto.getSubTypeName());// 名称
				vo.setType(dto.getPriceEntityName());// 归集类别
				vo.setDescrition(dto.getDescription());// 描述 zxy 20131121 KDTE-5995 修改：PriceEntityCode 改成 Description
				vo.setMoney(dto.getFee().toString());// 金额
				vo.setUpperLimit(dto.getMaxFee().toString());// 上限
				vo.setLowerLimit(dto.getMinFee().toString());// 下限
				vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto.getCanmodify()));// 是否可修改
				vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete()));// 是否可删除
				vo.setId(dto.getId());
				
				if(" ZYSCZJH".equals(vo.getCode())){
					
				}else{
					voList.add(vo);
				}
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
		WaybillPanelVo bean = waybillEditUI.getBinderWaybill();

		JXTable otherTable = waybillEditUI.incrementPanel.getTable();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		OtherChargeVo otherVo = getOtherCharge();
		addOtherCharge(data,otherVo,bean);
		waybillEditUI.incrementPanel.setChangeDetail(data);

	}
	
	/**
	 * 
	 * 比较是否存在当前选的费用，不存在则添加费用到表格中，并且进行其他费用合计
	 * @author 025000-FOSS-helong
	 * @date 2012-11-21 下午07:06:33
	 */
	private void addOtherCharge(List<OtherChargeVo> data,OtherChargeVo newOtherCharge,WaybillPanelVo bean)
	{
		boolean bool = true;
		OtherChargeVo oldVo = null;
		for(int i=0;i<data.size();i++)
		{
			OtherChargeVo otherVo = data.get(i);
			//比较费用名称，判断是否存在重复的返单费用
			if(otherVo.getChargeName().equals(newOtherCharge.getChargeName()))
			{
				bool = false;
				oldVo = data.get(i);
				data.remove(i);
				data.add(i, newOtherCharge);
			}
		}
		
		//如果选择的其他费用，则直接添加
		if(bool)
		{
			data.add(newOtherCharge);
			// 累计其他费用合计
			BigDecimal otherChargeSum = bean.getOtherFee();
			BigDecimal newMoney = new BigDecimal(newOtherCharge.getMoney());
			otherChargeSum = otherChargeSum.add(newMoney);
			bean.setOtherFee(otherChargeSum);
			bean.setOtherFeeCanvas(bean.getOtherFee().toString());
		}else
		{
			// 累计其他费用合计
			BigDecimal otherChargeSum = bean.getOtherFee();
			BigDecimal oldMoney = new BigDecimal(oldVo.getMoney());
			BigDecimal newMoney = new BigDecimal(newOtherCharge.getMoney());
			BigDecimal money = newMoney.subtract(oldMoney);
			otherChargeSum = otherChargeSum.add(money);
			bean.setOtherFee(otherChargeSum);
			bean.setOtherFeeCanvas(bean.getOtherFee().toString());
		}
	}

	/**
	 * 
	 * 从表格中获取双击的其他费用数据
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 下午01:49:04
	 */
	private OtherChargeVo getOtherCharge() {
		int row = table.getSelectedRow();
		ChangeInfoDetailTableModel model = (ChangeInfoDetailTableModel) table
				.getModel();
		List<OtherChargeVo> data = model.getData();
		OtherChargeVo otherVo = data.get(row);
		return otherVo;
	}

	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setChangeDetail(List<OtherChargeVo> changeDetailList,List<OtherChargeVo> bakList) {
		ChangeInfoDetailTableModel changeInfoDetailTableModel = ((ChangeInfoDetailTableModel) table
				.getModel());
		changeInfoDetailTableModel.setBakList(bakList);//设置备份数据
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

		/**
		 * 2
		 */
		private static final int TWO = 2;

		@Override
		public void mouseClicked(MouseEvent e) {
			// 双击
			if (e.getClickCount() == TWO) {
				setOtherCharge();
				// 关闭界面，释放资源
				dispose();
			} 
		}
	}

	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class ChangeInfoDetailTableModel extends DefaultTableModel {

		private static final int EIGHT = 8;
		
		private static final int SEVEN = 7;


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
		 * 0
		 */
		private static final int ZERO = 0;

		/**
		 * 1
		 */
		private static final int ONE = 1;

		private static final long serialVersionUID = 5883365603131625962L;

		private List<OtherChargeVo> changeDetailList;
		
		//此集合用于在内存中查询数据
		private List<OtherChargeVo> bakList;
		
		public List<OtherChargeVo> getBakList() {
			return bakList;
		}
		
		public void setBakList(List<OtherChargeVo> bakList) {
			this.bakList = bakList;
		}

		public List<OtherChargeVo> getData() {
			return changeDetailList;
		}

		public void setData(List<OtherChargeVo> changeDetailList) {
			this.changeDetailList = changeDetailList;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case ZERO:
				return i18n.get("foss.gui.changingexp.queryOtherChargePanel.column.zero");
			case ONE:
				return i18n.get("foss.gui.changingexp.queryOtherChargePanel.column.one");
			case TWO:
				return i18n.get("foss.gui.changingexp.queryOtherChargePanel.column.two");
			case THREE:
				return i18n.get("foss.gui.changingexp.queryOtherChargePanel.column.three");
			case FOUR:
				return i18n.get("foss.gui.changingexp.queryOtherChargePanel.column.four");
			case FIVE:
				return i18n.get("foss.gui.changingexp.queryOtherChargePanel.column.five");
			case SIX:
				return i18n.get("foss.gui.changingexp.queryOtherChargePanel.column.six");
			case SEVEN:
				return i18n.get("foss.gui.changingexp.queryOtherChargePanel.column.seven");
			default:
				return "";
			}
		}

		@Override
		public int getColumnCount() {
			return EIGHT;
		}

		@Override
		public int getRowCount() {
			return changeDetailList == null ? ZERO : changeDetailList.size();
		}

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
	}
	
	public JTextField getTxtPrivilegeName() {
		return txtPrivilegeName;
	}
	
	public JXTable getTable() {
		return table;
	}

}