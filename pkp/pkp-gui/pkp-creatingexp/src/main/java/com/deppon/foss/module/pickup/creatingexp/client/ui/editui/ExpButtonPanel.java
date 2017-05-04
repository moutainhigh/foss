/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.editui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpCreatePrintTimesDialogAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpGISDialogAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpLoadPickupStationAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpNewAddAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpOpenCrmNonfixedDialogAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpQueryMemberDialogAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpQueryPublicPriceLocalUIAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpQuerySamplePriceUIAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpWaybillPendingCompleteAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpWaybillSubmitAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpWaybillTempSaveAction;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpWebOrderAction;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpButtonPanel  extends JPanel {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -5616693037458986938L;
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpButtonPanel.class);
	
	/**
	 * 新建
	 */
	@ButtonAction(icon = IconConstants.NEW, shortcutKey = "", type = ExpNewAddAction.class)
	JButton btnNew = new JButton(i18n.get("foss.gui.creating.buttonPanel.new.label"));
	
	/**
	 * 暂存
	 */
	@ButtonAction(icon = IconConstants.SAVE, shortcutKey = "F2", type = ExpWaybillTempSaveAction.class)
	JButton btnSave = new JButton(i18n.get("foss.gui.creating.buttonPanel.save.label"));
	/**
	 * 提交
	 */
	@ButtonAction(icon = IconConstants.SUBMIT, shortcutKey = "F3", type = ExpWaybillSubmitAction.class)
	JButton btnSubmit = new JButton(i18n.get("foss.gui.creating.buttonPanel.submit.label"));
	/**
	 * 补录运单
	 */
	@ButtonAction(icon = IconConstants.SUPPLYWWAYBILL, shortcutKey = "F4", type = ExpWaybillPendingCompleteAction.class)
	JButton btnSupplyWaybill = new JButton(i18n.get("foss.gui.creating.buttonPanel.supplyWaybill.label"));

	/**
	 * 网上订单
	 */
	@ButtonAction(icon = IconConstants.ORDER, shortcutKey = "F5", type = ExpWebOrderAction.class)
	JButton btnOnLineOrder = new JButton(i18n.get("foss.gui.creating.buttonPanel.onLineOrder.label"));
	/**
	 * 运单打印
	 */
	@ButtonAction(icon = IconConstants.PRINT, shortcutKey = "", type = ExpCreatePrintTimesDialogAction.class)
	JButton btnPrint = new JButton(i18n.get("foss.gui.creating.buttonPanel.print.label"));
	/**
	 * 打印预览
	 */
	@ButtonAction(icon = IconConstants.PREVIEWPNG, shortcutKey = "", type = ExpCreatePrintTimesDialogAction.class)
	JButton btnPreview = new JButton(i18n.get("foss.gui.creating.buttonPanel.preview.label"));

	/**
	 * 打印标签
	 */
	@ButtonAction(icon = IconConstants.PRINTLABEL, shortcutKey = "", type = ExpCreatePrintTimesDialogAction.class)
	JButton btnPrintLabel = new JButton(i18n.get("foss.gui.creating.buttonPanel.printLabel.label"));

	/**
	 * 查询客户
	 */
	@ButtonAction(icon = IconConstants.SEARCHMEMBER, shortcutKey = "", type = ExpQueryMemberDialogAction.class)
	JButton btnSearchMember = new JButton(i18n.get("foss.gui.creating.buttonPanel.searchMember.label"));
	
	/**
	 * 新增CMR散客 
	 */
	@ButtonAction(icon = IconConstants.SEARCHMEMBER, shortcutKey = "", type = ExpOpenCrmNonfixedDialogAction.class)
	JButton btnAddCrmCustomer = new JButton(i18n.get("foss.gui.creating.buttonPanel.addCrmCustomer.label"));
	/**
	 * 查询网点
	 */
	@ButtonAction(icon = IconConstants.SEARCHBRANCH, shortcutKey = "F6", type = ExpLoadPickupStationAction.class)
	JButton btnSearchBranch = new JButton(i18n.get("foss.gui.creating.buttonPanel.searchBranch.label"));
	
	/**
	 * 电子地图
	 */
	@ButtonAction(icon = IconConstants.SEARCHBRANCH, shortcutKey = "F7", type = ExpGISDialogAction.class)
	JButton btnGIS = new JButton(i18n.get("foss.gui.creating.buttonPanel.gis.label"));
	/**
	 * 公布价查询
	 */
	@ButtonAction(icon = IconConstants.PUBLIC, shortcutKey = "F8", type = ExpQueryPublicPriceLocalUIAction.class)
	JButton btnSearchPrice = new JButton(i18n.get("foss.gui.creating.buttonPanel.searchPrice.label"));
	
	/**
	 * 简单报价
	 */
	@ButtonAction(icon = IconConstants.PUBLIC, shortcutKey = "F9", type = ExpQuerySamplePriceUIAction.class)
	private final JButton btnSamplePrice = new JButton(i18n.get("foss.gui.creating.buttonPanel.samplePrice.label"));

	/**
	 * 构造方法
	 */
	public ExpButtonPanel() {
		init();
	}

	/**
	 * 
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 上午10:27:04
	 * @see
	 */
	private void init() {
		setLayout(new FlowLayout(FlowLayout.LEFT, NumberConstants.NUMBER_5, NumberConstants.NUMBER_5));

		add(btnNew);// 新建
		add(btnSave);// 暂存
		btnSave.setEnabled(false);
		add(btnSubmit);// 提交
		btnSubmit.setEnabled(false);
		add(btnSupplyWaybill);// 补录运单
		if (!"ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())) {
			btnOnLineOrder.setEnabled(false);
		}
		add(btnOnLineOrder);// 网上订单
		add(btnPrint);// 运单打印
		btnPrint.setEnabled(false);
		add(btnPreview);// 打印预览
		btnPreview.setEnabled(false);
		add(btnPrintLabel);// 打印标签
		btnPrintLabel.setEnabled(false);
		// 查找会员
		add(btnSearchMember);
		// 新增CRM散客
		btnAddCrmCustomer.setEnabled(false);
		add(btnAddCrmCustomer); 
		add(btnSearchBranch);// 查询网点
		add(btnGIS);// 电子地图
		add(btnSearchPrice);// 公布价查询
		//简单报价窗口设置图标及监听事件
		add(btnSamplePrice);
	}
	
	/**
	 * getBtnNew
	 * @return JButton
	 */
	public JButton getBtnNew() {
		return btnNew;
	}
	
	/**
	 * getBtnSave
	 * @return JButton
	 */
	public JButton getBtnSave() {
		return btnSave;
	}
	
	/**
	 * getBtnSubmit
	 * @return JButton
	 */
	public JButton getBtnSubmit() {
		return btnSubmit;
	}

	/**
	 * getBtnPrint
	 * @return JButton
	 */ 
	public JButton getBtnPrint() {
		return btnPrint;
	}

	/**
	 * getBtnPreview
	 * @return JButton
	 */
	public JButton getBtnPreview() {
		return btnPreview;
	}

	/**
	 * getBtnPrintLabel
	 * @return JButton
	 */
	public JButton getBtnPrintLabel() {
		return btnPrintLabel;
	}

	/**
	 * getBtnSearchBranch
	 * @return JButton
	 */
	public JButton getBtnSearchBranch() {
		return btnSearchBranch;
	}

	/**
	 * getBtnGIS
	 * @return JButton
	 */
	public JButton getBtnGIS() {
		return btnGIS;
	}

	/**
	 * getBtnSearchPrice
	 * @return JButton
	 */
	public JButton getBtnSearchPrice() {
		return btnSearchPrice;
	}
	
	/**
	 * 
	 * 获取查询会员
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 上午09:21:37
	 * @return
	 */
	public JButton getBtnSearchMember() {
		return btnSearchMember;
	}

	/**
	 * 获取新增散客的按钮  
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午10:24:37
	 */
	public JButton getBtnAddCrmCustomer() {
		return btnAddCrmCustomer;	
	}
	
	
}