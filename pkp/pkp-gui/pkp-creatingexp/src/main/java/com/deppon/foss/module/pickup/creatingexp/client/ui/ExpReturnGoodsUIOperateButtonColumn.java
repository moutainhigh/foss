package com.deppon.foss.module.pickup.creatingexp.client.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.swing.AbstractCellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.changingexp.client.action.ImportReturnChangeAction;
import com.deppon.foss.module.pickup.changingexp.client.vo.ExpReturnedGoodsWaybillVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.action.ImportReturnGoodsAction;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 返货管理查询表格操作列
 * 
 * @author 201287
 * 
 */
public class ExpReturnGoodsUIOperateButtonColumn extends AbstractCellEditor
		implements TableCellEditor, TableCellRenderer {

	public ExpReturnGoodsUIOperateButtonColumn(TableColumn column,
			ComboBoxModel handleStatusModel, ComboBoxModel returnTypeModel,
			Map<String, Date> createTimeMap) {
		this.handleStatusModel = handleStatusModel;
		this.returnTypeModel = returnTypeModel;
		this.createTimeMap = createTimeMap;
		column.setCellEditor(this);
		column.setCellRenderer(this);
	}

	public IWaybillService waybillService = WaybillServiceFactory
			.getWaybillService();
	// 日志
	public static final Logger LOGGER = LoggerFactory
			.getLogger(ExpReturnGoodsUIOperateButtonColumn.class);

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager
			.getI18n(ExpReturnGoodsUIOperateButtonColumn.class);

	/**
	 * 返货类型下拉框mode
	 */
	private ComboBoxModel returnTypeModel;

	/**
	 * 受理状态下拉框mode
	 */
	private ComboBoxModel handleStatusModel;

	/**
	 * 每个运单最新的返货申请时间
	 */
	private Map<String, Date> createTimeMap;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		return createOperateButton(table, row);
	}

	/**
	 * 创建操作列按钮
	 * 
	 * @param table
	 * @param row
	 * @return
	 */
	private JButton createOperateButton(JTable table, int row) {
		// 获取当前行的返货类型 
		Object temObject = table
				.getValueAt(
						row,
						table.getColumn(
								i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.linkTableMode.column.seven"))
								.getModelIndex());
		String valueName = (null != temObject) ? temObject.toString() : "";

		DataDictionaryValueVo returnTypeVo = ExpCommon
				.castToDataDictionaryValueVoByValueName(valueName,
						returnTypeModel);

		// 获取当前行的受理状态
		temObject = table
				.getValueAt(
						row,
						table.getColumn(
								i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.linkTableMode.column.eight"))
								.getModelIndex());
		valueName = (null != temObject) ? temObject.toString() : "";

		// 获取运单号
		temObject = table
				.getValueAt(
						row,
						table.getColumn(
								i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.linkTableMode.column.two"))
								.getModelIndex());
		String wayBillNo = (null != temObject) ? temObject.toString() : "";
		
		DataDictionaryValueVo handleStatusVo = ExpCommon
				.castToDataDictionaryValueVoByValueName(valueName,
						handleStatusModel);
		TwoInOneWaybillDto twoInOneWaybillDto = waybillService.queryWaybillRelateByWaybillOrOrderNo(wayBillNo);
		JButton btnOperate =null;
		boolean isBill=false;
		if(null!=twoInOneWaybillDto&&StringUtils.equals(twoInOneWaybillDto.getIsTwoInOne(), FossConstants.ACTIVE)){
			btnOperate = new JButton("转寄退回件开单");
			isBill=true;
		}else{
			btnOperate = new JButton("转寄退回件更改");
		}

		btnOperate.setPreferredSize(new Dimension(NumberConstants.NUMBER_60, NumberConstants.NUMBER_18));
		btnOperate.addActionListener(new InnerButtonActionListener(table, row, isBill));

		// 获取当前行的原运单号
		temObject = table
				.getValueAt(
						row,
						table.getColumn(
								i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.linkTableMode.column.two"))
								.getModelIndex());
		valueName = (null != temObject) ? temObject.toString() : "";

		// 获取当前行的返回申请工单创建时间
		Object dateObject = table
				.getValueAt(
						row,
						table.getColumn(
								i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.linkTableMode.column.ten"))
								.getModelIndex());
		Date createTime = (null != temObject) ? DateUtils.convert(dateObject
				.toString()) : null;

		// 获取当前行的返货类型 
		Object ishandelObject = table
				.getValueAt(
						row,
						table.getColumn("1")
								.getModelIndex());
		String ishandel = (null != ishandelObject) ? ishandelObject.toString() : "";
		// 如果单前行的工单是最新创建的返货申请工单
		if ((null != createTime)
				&& (null != createTimeMap.get(valueName))
				&& (DateUtils.getSecondDiff(createTime,
						createTimeMap.get(valueName)) == 0)) {
			if (!checkIsAllowedReturn(returnTypeVo, handleStatusVo,ishandel)) {
				btnOperate.setEnabled(false);
			}
		} else {
			btnOperate.setEnabled(false);
		}

		return btnOperate;
	}

	/**
	 * 判断是否允许开返货单
	 * 
	 * @param returnType
	 * @param handleStatus
	 * @return
	 */
	private boolean checkIsAllowedReturn(DataDictionaryValueVo returnType,
			DataDictionaryValueVo handleStatus,String isHandle) {
		if ((null == returnType) || (null == handleStatus)) {
			return false;
		}
		if(StringUtils.equals(isHandle, "Y")){
			
			return false;
		}
		/*(WaybillConstants.RETURNTYPE_OTHER_CITY.equals(returnType
				.getValueCode()) || WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE
				.equals(returnType.getValueCode()))
				&& */
		if(WaybillConstants.RETURNTYPE_SEVEN_DAYS_RETURN.equals(returnType
				.getValueCode()) || WaybillConstants.RETURNTYPE_SEND_OUT_THREE_DAYS_RETURN
				.equals(returnType.getValueCode())){
			return false;
		}
		if (WaybillConstants.ACCEPTSTATUS_HANDLED.equals(handleStatus
						.getValueCode())) {
			return true;
		}
		
		return false;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		return createOperateButton(table, row);
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	public ComboBoxModel getReturnTypeModel() {
		return returnTypeModel;
	}

	public void setReturnTypeModel(ComboBoxModel returnTypeModel) {
		this.returnTypeModel = returnTypeModel;
	}

	public ComboBoxModel getHandleStatusModel() {
		return handleStatusModel;
	}

	public void setHandleStatusModel(ComboBoxModel handleStatusModel) {
		this.handleStatusModel = handleStatusModel;
	}

	public Map<String, Date> getCreateTimeMap() {
		return createTimeMap;
	}

	public void setCreateTimeMap(Map<String, Date> createTimeMap) {
		this.createTimeMap = createTimeMap;
	}

	/**
	 * 外部类监听器
	 * 
	 * @author 201287
	 * 
	 */
	class InnerButtonActionListener implements ActionListener {
		private JTable table;
		private int row;
		private boolean isBill;

		/**
		 * 构造函数
		 */
		public InnerButtonActionListener(JTable table, int row,boolean isBill) {
			this.table = table;
			this.row = row;
			this.isBill=isBill;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ExpReturnedGoodsWaybillVo vo = new ExpReturnedGoodsWaybillVo();
			// 原单号
			Object wayBillNoObject = table.getValueAt(row, 2);
			if (null != wayBillNoObject) {
				vo.setWaybillNo(wayBillNoObject.toString());
			}
			// 返单号
			Object returnBillNoObject = table.getValueAt(row, NumberConstants.NUMBER_3);
			if (null != returnBillNoObject) {
				vo.setReturnWaybillNo(returnBillNoObject.toString());
			}
			// 代收货款
			Object goodPayMent = table.getValueAt(row, NumberConstants.NUMBER_5);
			if (null != goodPayMent) {
				vo.setGoodsPayment(new BigDecimal(goodPayMent.toString()));
			}
			// 收货地址
			Object address = table.getValueAt(row, NumberConstants.NUMBER_6);
			if (null != address) {
				vo.setAddress(address.toString());
			}
			// 返货方式
			Object returnModeString = table.getValueAt(row, NumberConstants.NUMBER_7);
			if (null != returnModeString) {
				vo.setReturnModeString(returnModeString.toString());
			}	
			
			// 返货原因
	         Object returnReaSeaon = table.getValueAt(row, NumberConstants.NUMBER_14);
	         if (null != returnReaSeaon) {
	            vo.setReturnReason(returnReaSeaon.toString());
	         }
			
			if(isBill){
				new ImportReturnGoodsAction().importWaybillEditUI(vo);
			}else{
				new ImportReturnChangeAction().importWaybillEditUI(vo);
			} 
		}

	}
}
