/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.PrintException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.mainframe.client.common.CommonContents;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersContextUtil;
import com.deppon.foss.module.pickup.common.client.utils.BZPartnersJudge;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.CustomerImpOperLogHandler;
import com.deppon.foss.module.pickup.common.client.utils.ExpCommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.PtpWaybillOrgVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.service.impl.WaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpWaybillDtoFactory;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpWaybillTempSaveData;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSubmitConfirmCompareImportDialog;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSubmitConfirmDialog;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpConcessionsPanel.WaybillDiscountCanvas;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GUIPrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PtpWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintManager;
import com.deppon.foss.print.labelprint.service.LblPrtServiceConst;
import com.deppon.foss.prt.PrintUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpWaybillSubmitConfirmAction extends
		AbstractButtonActionListener<JDialog> {
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(ExpWaybillSubmitConfirmAction.class);
	// Logger
	private static final Log LOG = LogFactory
			.getLog(ExpWaybillSubmitConfirmAction.class);
	private static final String COMMA = ",";
	// service object
	private IWaybillService waybillService = WaybillServiceFactory
			.getWaybillService();
	// 离线暂存
	private IWaybillOffLinePendingService waybillOffLinePendingService = GuiceContextFactroy
			.getInjector().getInstance(WaybillOffLinePendingService.class);
	// 确认对话框
	private JDialog confirmDialog;
	// ui
	private ExpWaybillEditUI waybillEditUI;

	private BarcodePrintLabelDto printLabelBean;
	//用于存放 日志记录中的 单号
	private String waybill_log = "" ;

	
	//private boolean changeVolume = false;
	/**
	 * 
	 * <p>
	 * (运单提交确认)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * @see com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(CommonContents.logToggle){
			LOG.info("运单确认开始...");
		}
		try {
			// 运单绑定vo
			ExpWaybillPanelVo bean = null;
//			// 是否打印运单
//			Boolean isPrintWaybill = null;
			// 是否打印标签
			Boolean isPrintLabel = null;
			// 是否新增
			Boolean isNewAdd = null;
			// 打印模板
//			String printTemplates = null;
			// 判断是否为“确认提交”提交对话框
			if (confirmDialog instanceof ExpSubmitConfirmDialog) {
				// 转换对象
				ExpSubmitConfirmDialog d = (ExpSubmitConfirmDialog) confirmDialog;
				// 获得运单开单界面对象
				waybillEditUI = d.getWaybillEditUI();
				// 获得开单界面绑定的vo对象
				bean = d.getVo();
//				// 是否打印运单
//				isPrintWaybill = d.getChbPrintWaybill().isSelected();
				// 是否打印标签
				isPrintLabel = d.getChbPrintLabel().isSelected();
				// 是否新增
				isNewAdd = d.getChbNewAdd().isSelected();
				// 获得打印模板名称
//				printTemplates = ((PrintTemplatesModel) d.getPrintTemplates().getSelectedItem()).getKey();
			} else {
				// 运单值比较的“提交确认”对话框
				ExpSubmitConfirmCompareImportDialog d = (ExpSubmitConfirmCompareImportDialog) confirmDialog;
				// 运单开单界面
				waybillEditUI = d.getWaybillEditUI();
				// 获得开单界面绑定的vo对象
				bean =(ExpWaybillPanelVo) d.getVo();
//				// 是否打印运单
//				isPrintWaybill = d.getChbPrintWaybill().isSelected();
				// 是否打印标签
				isPrintLabel = d.getChbPrintLabel().isSelected();
				// 是否新增
				isNewAdd = d.getChbNewAdd().isSelected();
				// 获得打印模板名称
//				printTemplates = ((PrintTemplatesModel) d.getPrintTemplates().getSelectedItem()).getKey();
			}

			try {
				if(bean != null){
					if (WaybillConstants.SUBMIT_STATE.equals(waybillEditUI.getWaybillState())) {
						submit(bean);
						printInfoInsert(bean);
					} else {
						offLineTempSave(bean);
					}

					// 全局缓存
					cacheData(bean);

//					// 是否打印运单
//					if (isPrintWaybill) {
//						try {
//							waybillPrint(waybillEditUI, printTemplates);
//						} catch (IllegalAccessException e1) {
//							LOG.error("actionPerformed exception", e1);
//							String error = ExceptionUtils.getFullStackTrace(e1);
//							throw new WaybillValidateException(error);
//						} catch (InvocationTargetException e1) {
//							LOG.error("actionPerformed exception", e1);
//							String error = ExceptionUtils.getFullStackTrace(e1);
//							throw new WaybillValidateException(error);
//						} catch (NoSuchMethodException e1) {
//							LOG.error("actionPerformed exception", e1);
//							String error = ExceptionUtils.getFullStackTrace(e1);
//							throw new WaybillValidateException(error);
	//
//						}
//					}

					// 是否打印标签
					if (isPrintLabel) {
						if(CommonContents.logToggle){
							CustomerImpOperLogHandler.setLogger(bean.getWaybillNo()).setInfo("saveWaybillStart", "打印标签开始:");
						}
						waybillLabelPrint(bean.getWaybillNo());
						if(CommonContents.logToggle){
							CustomerImpOperLogHandler.setLogger(bean.getWaybillNo()).setInfo("saveWaybillEnd", "打印标签结束:");
						}
					}
					//存放 日志记录中的 单号
					waybill_log = bean.getWaybillNo() ;
					
					confirmDialog.dispose();

					// 是否新增
					if (isNewAdd) {
						waybillNewAdd();
					}
					
					//清空开始开单时间
					bean.setStartBillTime(null);

					// 设置控件的编辑状态
					componentSetEnable();
					
					if(CommonContents.logToggle){
						  CustomerImpOperLogHandler.setLogger(waybill_log).submitSuccess();
					}
					if(bean.getOriginalWaybillNo()!=null){
						WaybillEntity beanOrg= waybillService.queryWaybillByNumber(bean.getOriginalWaybillNo());
					    waybillService.updateOrderRefuseState(bean.getOriginalWaybillNo(),beanOrg.getOrderNo(),bean.getDeliveryCustomerCode());			
					}
				}
			} catch (WaybillValidateException w) {
				/**
				 * BUG-10031:提示信息出现两行相同的提示信息，请去除重复提示信息
				 */
				String msg = w.getMessage();
				if (StringUtils.isEmpty(msg)) {
					msg = w.getErrorCode();
				}
				MsgBox.showInfo(msg);
			}
		} catch (BusinessException be) {
			LOG.error("actionPerformed exception", be);
			MsgBox.showInfo(MessageI18nUtil.getMessage(be, i18n));
		} catch (Throwable ee) {
			LOG.error("WaybillSubmitConfirmAction exception", ee);
			if (ee instanceof UndeclaredThrowableException) {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.waybillSubmitConfirmAction.Exception"));
			} else {
				MsgBox.showInfo(i18n
						.get("foss.gui.creating.waybillSubmitConfirmAction.Exception")
						+ ee);
			}

		}
		if(CommonContents.logToggle){
			LOG.info("运单确认完成. 运单号："+waybill_log);
		}
	}

	/**
	 * 
	 * 全局缓存
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-26 下午05:15:41
	 */
	private void cacheData(WaybillPanelVo bean) {
		// 将开单成功的运单缓存
		SessionContext.set(WaybillConstants.WAYBILL_NO, bean.getWaybillNo());

		if (bean.getPickupCentralized()) {
			// 缓存集中开单部门编码
			SessionContext.set(WaybillConstants.FOCUS_DEPT_CODE,
					bean.getReceiveOrgCode());
			// 缓存集中开单部门名称
			SessionContext.set(WaybillConstants.FOCUS_DEPT_NAME,
					bean.getReceiveOrgName());
		}
	}

	/**
	 * 
	 * 离线暂存
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-10 下午06:00:05
	 */
	private void offLineTempSave(WaybillPanelVo bean) {
		ExpWaybillTempSaveData tempSave = new ExpWaybillTempSaveData(waybillEditUI);
		//设置是否快递字段  add  by  yangkang
		WaybillPendingDto waybill =tempSave.getWaybillDto(bean);
		waybill.setIsExpress(FossConstants.YES);
		
		waybillService.tempSaveWaybill(waybill);
		MsgBox.showInfo(i18n
				.get("foss.gui.creating.waybillSubmitConfirmAction.msgBox.tempSaveWaybillSuccess"));
		// 设置可以再次暂存
		bean.setPCPending(false);
	}

	/**
	 * 
	 * 运单在线提交
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-10 下午06:00:24
	 */
	private void submit(ExpWaybillPanelVo bean) {
		long begin = System.currentTimeMillis() ;
		if(CommonContents.logToggle){
			 LOG.info("运单号："+bean.getWaybillNo()+" 重量体积不为0直接开单开始... ");
			 CustomerImpOperLogHandler.setLogger(bean.getWaybillNo()).setInfo("saveWaybillStart", "重量体积不为0直接开单开始:");
		}
		WaybillDto waybill = getWaybillDto(bean);
		//零担免费接送货需求在运单表增加字段,导致影响所有快递开单在插入运单表数据时由于值为null报错,151211,杨套红,2016/3/12
		waybill.getWaybillEntity().setFreePickupGoods("N");
		ActualFreightEntity afy= waybill.getActualFreightEntity();
		afy.setInvoice(WaybillConstants.INVOICE_02);
//		afy.setIsNextDayPending(bean.getIsNextDayPending());
		waybill.setActualFreightEntity(afy);
		//设置是否快递字段  add  by  yangkang
		waybill.setIsExpress(FossConstants.YES);
		//
		waybill.setIsGuiSubmit(FossConstants.YES);
		waybillService.submitWaybill(waybill);
		// 业务监控
		try {
			waybillService.businessMonitor(waybill);
		} catch (Exception e) {
			LOG.error("运单提交业务监控数据处理失败：", e);
		}
		if (WaybillConstants.WAYBILL_STATUS_OFFLINE_PENDING.equals(bean.getWaybillstatus())) {
			// 离线运单提交成功后删除本地记录
			waybillOffLinePendingService.deleteByPrimaryKey(bean.getId());
		}
		if(CommonContents.logToggle){
			LOG.info(" 重量体积不为0直接开单开始结束. 运单号："+bean.getWaybillNo()+" ; 耗时："+(System.currentTimeMillis()-begin));
			CustomerImpOperLogHandler.setLogger(bean.getWaybillNo()).setInfo("saveWaybillEnd", "重量体积不为0直接开单结束:");
		}
		MsgBox.showInfo(i18n.get("foss.gui.creating.waybillSubmitConfirmAction.msgBox.submitWaybillSuccess"));
	}

	/**
	 * 
	 * 将开单界面控件全部设置成不可编辑，将新增按钮设置为可编辑
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-1 上午10:49:57
	 */
	private void componentSetEnable() {
		UIUtils.disableUI(waybillEditUI);
		waybillEditUI.buttonPanel.getBtnNew().setEnabled(true);// 新增
		waybillEditUI.buttonPanel.getBtnPrint().setEnabled(true);// 运单打印
		waybillEditUI.buttonPanel.getBtnPreview().setEnabled(true);// 运单打印预览
		waybillEditUI.buttonPanel.getBtnPrintLabel().setEnabled(true);// 打印标签
		waybillEditUI.buttonPanel.getBtnSearchBranch().setEnabled(true);// 查询网点
		// waybillEditUI.buttonPanel.getBtnGIS().setEnabled(true);//电子地图
		waybillEditUI.buttonPanel.getBtnSearchPrice().setEnabled(true);// 公布价查询

		// 把焦点放在这里 防止出现红色的运单已存在的多余提示
		waybillEditUI.buttonPanel.getBtnPrint().grabFocus();
		waybillEditUI.buttonPanel.getBtnPrint().requestFocus();
	}

//	/**
//	 * 
//	 * 运单打印
//	 * 
//	 * @author 025000-FOSS-helong
//	 * @throws NoSuchMethodException
//	 * @throws InvocationTargetException
//	 * @throws IllegalAccessException
//	 * @date 2012-12-1 上午10:54:49
//	 */
//	private void waybillPrint(ExpWaybillEditUI ui, String printTemplates)
//			throws IllegalAccessException, InvocationTargetException,
//			NoSuchMethodException {
//		//waybillService.printWaybillFirstTime(ui, printTemplates);
//	}

	/**
	 * 
	 * 标签打印
	 * 
	 * @author 025000-FOSS-helong
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-12-1 上午10:54:49
	 */
	private void waybillLabelPrint(String waybillNo) {
		try {
			printLabelBean = waybillService.getCommonLabelPrintInfoExpress(waybillNo, null);
			// WaybillEntity waybillEntity =
			// waybillService.queryWaybillByNumber(waybillNo);
			if (printLabelBean.getWaybillNumber() == null) {
				JOptionPane
						.showMessageDialog(
								null,
								i18n.get("foss.gui.creating.waybillSubmitConfirmAction.showMessageDialog.nullWaybillNumber"),
								i18n.get("foss.gui.creating.waybillEditUI.common.error"),
								JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 获得流水号
			printLabelBean.setPrintSerialnos(getSerialNos(waybillNo,
					printLabelBean.getTotalPieces()));
			PrintUtil printUtil = new PrintUtil();
			String serialNosText = getSerialNos(waybillNo,printLabelBean.getTotalPieces());
			String[] serialNos = serialNosText.split(",");
			LabelPrintContext ctx = printUtil.printLabelData(printLabelBean);
			try {
				LabelPrintManager
						.doLabelPrintAction(
								LblPrtServiceConst.key_lblprt_program_ExpCommLabelPrintWorker,
								ctx);
				UserEntity userEntity = (UserEntity)SessionContext.get(SessionContext.KEY_USER);
				for (int i = 0; i < serialNos.length; i++) {
					GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
					printLabelEntity.setWaybillNo(waybillNo);
					printLabelEntity.setSerialNo(serialNos[i]);
					printLabelEntity.setPrintTime(new Date());
					printLabelEntity.setPrintUserCode(userEntity.getEmployee().getEmpCode());
					printLabelEntity.setPrintUserName(userEntity.getEmployee().getEmpName());
					waybillService.addPrintLabelInfo(printLabelEntity);
				}
				// MsgBox.showInfo(i18n.get("foss.gui.creating.printSignUI.msgbox.printSuccess"));
			} catch (PrintException e) {
				JOptionPane
						.showMessageDialog(
								null,
								i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"),
								i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
								JOptionPane.WARNING_MESSAGE);
			}

		} catch (Exception exp) {
			LOG.error("标签打印异常" + exp.toString(), exp);
			JOptionPane.showMessageDialog(null, exp.toString(),
					i18n.get("foss.gui.creating.waybillEditUI.common.error"),
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// 获得流水号 字符串
	public String getSerialNos(String waybillNo, String totalGoodsQty) {
		String resultString = "";
		StringBuffer tempAreaInfo = new StringBuffer("");
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(
				WaybillConstants.LOGIN_TYPE).toString())) {
			List<LabeledGoodEntity> labeledlist = waybillService
					.queryAllSerialByWaybillNo(waybillNo);
			for (int i = 0; i < Integer.parseInt(printLabelBean
					.getTotalPieces()); i++) {
				tempAreaInfo.append(labeledlist.get(i).getSerialNo()).append(
						COMMA);
				// tempAreaInfo.append(
				// String.format(FORMATSTR,Integer.parseInt(labeledlist.get(i).getSerialNo()))).append(COMMA);
			}
		} else {
			// 离线流水号
			tempAreaInfo = getPrintSerialnos(Integer.parseInt(totalGoodsQty));
		}
		if (tempAreaInfo.length() != 0) {
			resultString = tempAreaInfo.substring(0, tempAreaInfo.length() - 1);
		}
		return resultString;
	}

	// 根据货物总件数生成流水号。
	private StringBuffer getPrintSerialnos(int totalGoodsQty) {
		StringBuffer serialnos = new StringBuffer();
		if (totalGoodsQty > 0) {
			for (int i = 1; i <= totalGoodsQty; i++) {
				// 文本区的赋值 补成4位的
				serialnos.append(String.format(WaybillConstants.FORMATSTR, i))
						.append(COMMA);
			}
		}
		return serialnos;
	}

	/**
	 * 
	 * 运单新增
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-1 上午10:55:52
	 */
	public void waybillNewAdd() {
		IApplication application = waybillEditUI.getApplication();
		IEditor editor = waybillEditUI.getEditor();
		editor.close();

		if (WaybillConstants.WAYBILL_FOCUS.equals(waybillEditUI
				.getWaybillType())) {
			openFocusWaybill(application);
		} else if (WaybillConstants.WAYBILL_SALE_DEPARTMENT
				.equals(waybillEditUI.getWaybillType())) {
			openSalesDepartmentWaybill(application);
		}
	}

	/**
	 * 
	 * 打开集中开单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-12 下午07:19:28
	 */
	private void openFocusWaybill(IApplication application) {
		EditorConfig editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.waybillExpress.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creatingexp");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI");
		ExpWaybillEditUI waybillEditUI2 = (ExpWaybillEditUI) editor.getComponent();
		waybillEditUI2.setEditor(editor);

		// 营业部开单
		waybillEditUI2.setWaybillType(WaybillConstants.WAYBILL_FOCUS);
		waybillEditUI2.openUI();
	}

	/**
	 * 
	 * 打开营业部开单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-12 下午07:20:02
	 */
	private void openSalesDepartmentWaybill(IApplication application) {
		EditorConfig editConfig = new EditorConfig();
		editConfig.setTitle(i18n.get("foss.gui.creating.buttonPanel.waybillExpress.label"));
		editConfig.setPluginId("com.deppon.foss.module.pkp-creatingexp");
		IEditor editor = application
				.openEditorAndRetrun(editConfig,
						"com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI");
		ExpWaybillEditUI waybillEditUI = (ExpWaybillEditUI) editor.getComponent();
		waybillEditUI.setEditor(editor);

		// 营业部开单
		waybillEditUI.setWaybillType(WaybillConstants.WAYBILL_SALE_DEPARTMENT);
		waybillEditUI.openUI();
	}

	/**
	 * 
	 * 组装后台DTO
	 * 
	 * @author 025000-FOSS-helong
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-11-3 下午04:06:31
	 */
	private WaybillDto getWaybillDto(ExpWaybillPanelVo vo) {
		WaybillDto dto = new WaybillDto();
		// 设置运单基本信息
		dto.setWaybillEntity(getWaybillEntity(vo));
		
		// 设置原运单号
		String oldWaybillNo = StringUtil.defaultIfNull(vo.getOldWaybillNo());
		if (StringUtil.isNotEmpty(oldWaybillNo)) {
			dto.setOldWaybillNo(oldWaybillNo);
		} else {
			dto.setOldWaybillNo(dto.getWaybillEntity().getWaybillNo());
		}
		// 非内部带货，内部带货不产生任何与费用相关的数据
		if (!WaybillConstants.INNER_PICKUP.equals(vo.getReceiveMethod()
				.getValueCode())) {

			WaybillOtherCharge model = null;
			WaybillDiscountCanvas discountTableModel = null;
			if (confirmDialog instanceof ExpSubmitConfirmDialog) {
				ExpSubmitConfirmDialog d = (ExpSubmitConfirmDialog) confirmDialog;
				model = (WaybillOtherCharge) d.getWaybillEditUI().incrementPanel
						.getTblOther().getModel();
				discountTableModel = (WaybillDiscountCanvas) d
						.getWaybillEditUI().canvasContentPanel
						.getConcessionsPanel().getTblConcessions().getModel();
			} else {
				ExpSubmitConfirmCompareImportDialog d = (ExpSubmitConfirmCompareImportDialog) confirmDialog;
				model = (WaybillOtherCharge) d.getWaybillEditUI().incrementPanel
						.getTblOther().getModel();
				discountTableModel = (WaybillDiscountCanvas) d
						.getWaybillEditUI().canvasContentPanel
						.getConcessionsPanel().getTblConcessions().getModel();
			}

			dto.setWaybillChargeDtlEntity(ExpWaybillDtoFactory
					.getWaybillChargeDtlEntity(model, vo));
			dto.setWaybillDisDtlEntity(getWaybillDisDtlEntity(
					discountTableModel, vo));
			dto.setWaybillPaymentEntity(getWaybillPaymentEntity(vo));
			// 设置开户银行信息
			dto.setOpenBank(vo.getOpenBank());
		}

		if (!StringUtils.isEmpty(vo.getPromotionsCode())) {
			CouponInfoDto couponInfoDto = vo.getCouponInfoDto();
			// 判断优惠券实体是否为空
			if (couponInfoDto != null) {
				dto.setCouponInfoDto(couponInfoDto);
			} else {
				throw new WaybillValidateException(
						i18n.get("foss.gui.creating.waybillSubmitConfirmAction.exception.nullCouponInfo"));
			}
			// 查看是否有优惠券编号
			if (StringUtils.isNotEmpty(vo.getPromotionsCode())) {
				couponInfoDto.setUsed(true);
				dto.setCouponInfoDto(couponInfoDto);
			}
		}

		dto.setWoodenRequirementsEntity(getWoodenRequirementsEntity(vo));
		dto.setActualFreightEntity(getActualFreightEntity(vo));
		
		//添加收货人地址
		dto.setReceiveCustomerAreaDto(vo.getReceiveCustomerAreaDto());
		
		//小件冗余 只有运输方式为包裹的情况下  才会设置该属性
		if(CommonUtils.directDetermineIsExpressByProductCode(vo.getProductCode().getCode())){
			dto.setWaybillExpressEntity(getWaybillExpressEntity(vo,dto));
		}
		// 优惠券不为空
		//判断是否为合伙人
		dto.setPartnerBilling(vo.getPartnerBilling());
		//快递直营也需要将该类付值
		dto.setPtpWaybillDto(getPtpWaybillInfo(vo));

		return dto;
	}

	private PtpWaybillDto getPtpWaybillInfo(ExpWaybillPanelVo vo){
		PtpWaybillDto ptpWaybillDto = new PtpWaybillDto();
		PtpWaybillOrgVo ptpWaybillOrgVo = vo.getPtpWaybillOrgVo();
		//基础送货费
		ptpWaybillDto.setBaseDeliveryGoodsFeeOrg(StringUtils.isNotBlank(ptpWaybillOrgVo.getBaseDeliveryGoodsFee()) ? ptpWaybillOrgVo.getBaseDeliveryGoodsFee():"0");
		//代收货款手续费
		ptpWaybillDto.setCodFeeOrg(ptpWaybillOrgVo.getCodFee()!=null ? ptpWaybillOrgVo.getCodFee() :BigDecimal.ZERO );
		// 送货费+送货上楼费
		ptpWaybillDto.setDeliveryGoodsFeeOrg(StringUtils.isNotBlank(ptpWaybillOrgVo.getDeliveryGoodsFee()) ? ptpWaybillOrgVo.getDeliveryGoodsFee() :"0" );
		//保价费 （修改前的值）
		ptpWaybillDto.setInsuranceFeeOrg(ptpWaybillOrgVo.getInsuranceFee()!=null ? ptpWaybillOrgVo.getInsuranceFee() : BigDecimal.ZERO);
		//其他费用
		ptpWaybillDto.setOtherFeeOrg(ptpWaybillOrgVo.getOtherFee()!=null ? ptpWaybillOrgVo.getOtherFee() : BigDecimal.ZERO);
		//包装费
		ptpWaybillDto.setPackageFeeOrg(StringUtils.isNotBlank(ptpWaybillOrgVo.getPackageFee())? ptpWaybillOrgVo.getPackageFee():"0") ;
		//接货费
		ptpWaybillDto.setPickUpFeeOrg(StringUtils.isNotBlank(ptpWaybillOrgVo.getPickUpFee())?ptpWaybillOrgVo.getPickUpFee():"0");
		// 总费用
		ptpWaybillDto.setTotalFeeOrg(ptpWaybillOrgVo.getTotalFee()!=null ? ptpWaybillOrgVo.getTotalFee() : BigDecimal.ZERO) ;
		// 公布价运费
		ptpWaybillDto.setTransportFeeOrg(ptpWaybillOrgVo.getTransportFee()!=null ? ptpWaybillOrgVo.getTransportFee():BigDecimal.ZERO) ;
		//上楼费
		ptpWaybillDto.setUpFloorFeeOrg(StringUtils.isNotBlank(ptpWaybillOrgVo.getUpFloorFee()) ? ptpWaybillOrgVo.getUpFloorFee() : "0" ) ;
		//运单号
		ptpWaybillDto.setWaybillNo(vo.getWaybillNo());
		//送货进仓费
		ptpWaybillDto.setDeliveryWareHouseFeeOrg(ptpWaybillOrgVo.getDeliveryWareHouseFee()!=null ? ptpWaybillOrgVo.getDeliveryWareHouseFee() : BigDecimal.ZERO );
		//大件上楼费
		ptpWaybillDto.setBigGoodsUpFloorFeeOrg(ptpWaybillOrgVo.getBigGoodsUpFloorFee()!=null ? ptpWaybillOrgVo.getBigGoodsUpFloorFee() : BigDecimal.ZERO) ;
		//出发部门
		ptpWaybillDto.setReceiveOrgCode(vo.getReceiveOrgCode());
		//到达部门 
		if(vo.getCustomerPickupOrgCode()!=null){
			ptpWaybillDto.setArriveOrgCode(vo.getCustomerPickupOrgCode().getCode());
		}else{
			ptpWaybillDto.setArriveOrgCode(vo.getLastLoadOrgCode());
		}
		//判断到达部门是否为合伙人
		BZPartnersContextUtil bzPartnersContextUtil = BZPartnersContextUtil.getBzPatnersContext();
		boolean flag = bzPartnersContextUtil.isPartner(ptpWaybillDto.getArriveOrgCode());
		ptpWaybillDto.setIsArriveDepPartner(flag);
		if(flag && BZPartnersJudge.IS_PARTENER){
			ptpWaybillDto.setFeeType(WaybillConstants.TWO);//0:成本  1：提成  2：成本和提成
		}else if(!flag && BZPartnersJudge.IS_PARTENER){
			ptpWaybillDto.setFeeType(WaybillConstants.ZERO);//0:成本  1：提成  2：成本和提成
		}else if(flag && !BZPartnersJudge.IS_PARTENER){
			ptpWaybillDto.setFeeType(WaybillConstants.ONE);//0:成本  1：提成  2：成本和提成
		}else{
			ptpWaybillDto.setFeeType(WaybillConstants.NEGATIVE_ONE);//-1：出发到达都不是合伙人  
		}
		//出发部门是否合伙人
		if (BZPartnersJudge.IS_PARTENER) {
			ptpWaybillDto.setIsReceiveDepPartner(true);
		} else {
			ptpWaybillDto.setIsReceiveDepPartner(false);
		}
		
		// 是否上门接货
		ptpWaybillDto.setPickupToDoor(vo.getPickupToDoor());
		//送货上楼安装（家装）
		ptpWaybillDto.setPickupToDoorJZFeeOrg(ptpWaybillOrgVo.getPickupToDoorJZFee()!=null ? ptpWaybillOrgVo.getPickupToDoorJZFee() :BigDecimal.ZERO);
		//异常操作服务费 ZZ
		ptpWaybillDto.setExceptionOpreationFeeOrg(ptpWaybillOrgVo.getExceptionOpreationFee()!=null?ptpWaybillOrgVo.getExceptionOpreationFee() : BigDecimal.ZERO);
		//超远派送费 CY
		ptpWaybillDto.setOverDistanceFeeOrg(ptpWaybillOrgVo.getOverDistanceFee()!=null ? ptpWaybillOrgVo.getOverDistanceFee() : BigDecimal.ZERO);
		//签收单
		ptpWaybillDto.setSignBillFeeOrg(ptpWaybillOrgVo.getSignBillFee()!=null ? ptpWaybillOrgVo.getSignBillFee() : BigDecimal.ZERO);
		//是否快递
		ptpWaybillDto.setIsExpress(WaybillConstants.YES);
		
		ptpWaybillDto.setIsChanged(WaybillConstants.NO);//是否更改单 Y：更改单；N:开单
		
		DataDictionaryValueVo dataVo = vo.getReturnBillType();
		if(dataVo!=null){//返单类别
			ptpWaybillDto.setReturnBillType(dataVo.getValueCode());
		}
		ptpWaybillDto.setBillWeight(vo.getBillWeight());//计费重量
		
		//币种
		ptpWaybillDto.setCurrencyCode(vo.getCurrencyCode());
		
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		if(user != null ){
			EmployeeEntity employeeEntity = user.getEmployee();
			if(employeeEntity!=null){
				ptpWaybillDto.setUserCode(user.getEmployee().getEmpCode());//职员编号
				ptpWaybillDto.setUserName(user.getEmployee().getEmpName());// 用户登录名
				ptpWaybillDto.setCreateUserCode(user.getEmployee().getEmpCode());
				OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
				if(dept!=null){
					ptpWaybillDto.setCurrentDeptCode(dept.getCode());//当前登录部门编码
					ptpWaybillDto.setCurrentDeptName(dept.getName());//当前登录部门名称
					ptpWaybillDto.setCreateOrgCode(dept.getCode());
					ptpWaybillDto.setCurrentDeptUnifieldCode(dept.getUnifiedCode());//标杆编码
				}
			}
			
		}
		
		return ptpWaybillDto ;
	}
	
	/**
	 * 小件冗余信息
	 * @param vo
	 * @return
	 */
	private WaybillExpressEntity getWaybillExpressEntity(ExpWaybillPanelVo vo,WaybillDto dto) {
		WaybillExpressEntity entiy = new WaybillExpressEntity();
		entiy.setWaybillNo(vo.getWaybillNo());
		entiy.setDeliveryCustomerProvCode(dto.getWaybillEntity().getDeliveryCustomerProvCode());
		entiy.setDeliveryCustomerCityCode(dto.getWaybillEntity().getDeliveryCustomerCityCode());
		entiy.setDeliveryCustomerDistCode(dto.getWaybillEntity().getDeliveryCustomerDistCode());
		entiy.setInnerPickupFeeBurdenDept(vo.getInnerPickupFeeBurdenDept());//获得内部带货费用承担部门
		entiy.setReceiveCustomerCode(vo.getReceiveCustomerCode());
		entiy.setReceiveCustomerName(vo.getReceiveCustomerName());
		entiy.setReceiveCustomerMobilephone(vo.getReceiveCustomerMobilephone());
		entiy.setReceiveCustomerPhone(vo.getReceiveCustomerPhone());
		entiy.setReceiveCustomerContact(vo.getReceiveCustomerContact());
		entiy.setReceiveCustomerProvCode(dto.getWaybillEntity().getReceiveCustomerProvCode());
		entiy.setReceiveCustomerCityCode(dto.getWaybillEntity().getReceiveCustomerCityCode());
		entiy.setReceiveCustomerDistCode(dto.getWaybillEntity().getReceiveCustomerDistCode());
		
		
		if(vo.getGoodsVolumePreviousTotal()!=null){
			String prarentProductCode = "";
			if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(vo.getProductCode().getCode())
					|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE.equals(vo.getProductCode().getCode())){
				prarentProductCode = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
			}
			BigDecimal volumeWeight = ExpCommonUtils.validateWeightBubbleRate(vo.getDeliveryCustomerCode(),null,prarentProductCode,vo.getGoodsVolumePreviousTotal(),vo.getReceiveOrgCode());

			/*BigDecimal volumeWeight = vo.getGoodsVolumePreviousTotal()
					.multiply(FossConstants.VOLUME_TO_WEIGHT).setScale(2, BigDecimal.ROUND_HALF_UP);*/
		
			entiy.setVolumeWeight(volumeWeight);
		}
		
		
		
		
		//TODO
//		BusinessUtils businessUtils = new BusinessUtils();
//		AddressFieldDto consigneeAddress = businessUtils.getProvCityCounty(
//				vo.getReceiveCustomerProvCode(),
//				vo.getReceiveCustomerCityCode(),
//				vo.getReceiveCustomerDistCode());
		entiy.setReceiveCustomerAddress(StringUtils.substring(vo.getReceiveCustomerAddress(), 0, NumberConstants.NUMBER_100));// 收货具体地址
		
//		if(StringUtils.isEmpty(vo.getReceiveCustomerCode())){
//			if(consigneeAddress!=null){
//				String provName = consigneeAddress.getProvinceName()!=null? consigneeAddress.getProvinceName():"" ;
//				String cityName = consigneeAddress.getCityName()!=null? consigneeAddress.getCityName():"";
//				String distName = consigneeAddress.getCountyName()!=null? consigneeAddress.getCountyName():"";
//				entiy.setReceiveCustomerAddress(StringUtils.substring(provName+cityName+distName+
//						vo.getReceiveCustomerAddress(), 0,100));// 收货具体地址
//			}else{
//				entiy.setReceiveCustomerAddress(StringUtils.substring(vo.getReceiveCustomerAddress(), 0,100));// 收货具体地址
//				
//				
//				
//			
//			}
//		}else{
			entiy.setReceiveCustomerAddress(StringUtils.substring(vo.getReceiveCustomerAddress(), 0, NumberConstants.NUMBER_100));// 收货具体地址
			
//			String provName = consigneeAddress.getProvinceName()!=null? consigneeAddress.getProvinceName():"" ;
//			String cityName = consigneeAddress.getCityName()!=null? consigneeAddress.getCityName():"";
//			String distName = consigneeAddress.getCountyName()!=null? consigneeAddress.getCountyName():"";
//			entiy.setReceiveCustomerAddress(StringUtils.substring(provName+cityName+distName+
//					vo.getReceiveCustomerAddress(), 0,100));// 收货具体地址
	
//		}
		
		
		
		entiy.setReceiveMethod(dto.getWaybillEntity().getReceiveMethod());
		entiy.setTargetOrgCode(dto.getWaybillEntity().getTargetOrgCode());
		entiy.setBillTime(dto.getWaybillEntity().getBillTime());
		entiy.setCreateOrgCode(dto.getWaybillEntity().getCreateOrgCode());
		entiy.setCustomerPickupOrgCode(dto.getWaybillEntity().getCustomerPickupOrgCode());
		entiy.setCustomerPickupOrgName(dto.getWaybillEntity().getCustomerPickupOrgName());
		entiy.setCreateTime(new Date());//PDA补录插入时间(营业部PDA补录时间)
		
		// 是否补码  补码时间  这里我没有传 isAddCode  addCodeTime
		if(StringUtils.isNotEmpty(vo.getIsAddCode())){
			entiy.setIsAddCode(vo.getIsAddCode());
			entiy.setAddCodeTime(vo.getAddCodeTime());
		}else{
			entiy.setIsAddCode(FossConstants.NO);
		}
		
		if(vo.getReturnType()!=null 
				&& StringUtils.isNotEmpty(vo.getReturnType().getValueCode())){
			entiy.setReturnType(vo.getReturnType().getValueCode());
			entiy.setOriginalWaybillNo(vo.getOriginalWaybillNo());
		}
		
		//这里可能要根据工号做营业部计算
		entiy.setExpressEmpCode(vo.getExpressEmpCode());
		entiy.setExpressEmpName(vo.getExpressEmpName());
		entiy.setExpressOrgCode(vo.getExpressOrgCode());
		entiy.setExpressOrgName(vo.getExpressOrgName());
		entiy.setPdaSerial(vo.getPdaSerial());
		entiy.setBankTradeSerail(vo.getBankTradeSerail());
		entiy.setReceiveOrgCode(vo.getReceiveOrgCode());
		entiy.setReceiveOrgName(vo.getReceiveOrgName());
		//entiy.setCanReturnCargo(vo.getCanReturnCargo());
		//所有快递单都能返货
		entiy.setCanReturnCargo(FossConstants.YES);
		if(FossConstants.ACTIVE.equals(vo.getChangeVolume())){
			entiy.setChangeVolume(FossConstants.YES);
		}
		
		return entiy;
	}

	/**
	 * 
	 * <p>
	 * 运单其他信息封装<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-29
	 * @param vo
	 * @return ActualFreightEntity
	 */
	private ActualFreightEntity getActualFreightEntity(ExpWaybillPanelVo vo) {
		WaybillEntity waybillEntity = getWaybillEntity((ExpWaybillPanelVo)vo);
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		//开始开单时间
		actualFreightEntity.setStartBillTime(vo.getStartBillTime());
		// 运单号
		actualFreightEntity.setWaybillNo(vo.getWaybillNo());
		// 货物名称
		if(StringUtils.isEmpty(vo.getGoodsName())){
			actualFreightEntity.setGoodsName(waybillEditUI.cargoInfoPanel.getTxtGoodsName().getText());
		}else{
			actualFreightEntity.setGoodsName(vo.getGoodsName());
		}
	
		// 重量
		actualFreightEntity.setWeight(vo.getBillWeight());
		// 体积
		if(vo.getGoodsVolumeTotal()!=null &&vo.getGoodsVolumeTotal().doubleValue()>0){
			actualFreightEntity.setVolume(vo.getGoodsVolumeTotal());
		}else{
			actualFreightEntity.setVolume(BigDecimal.valueOf(0.01));
		}
		// 件数
		actualFreightEntity.setGoodsQty(vo.getGoodsQtyTotal());
		// 尺寸
		actualFreightEntity.setDimension(StringUtils.isNotEmpty(vo
				.getGoodsSize()) ? vo.getGoodsSize() : "1*1*1*1");
		// 保险声明价值
		actualFreightEntity.setInsuranceValue(vo.getInsuranceAmount());
		// 包装费
		actualFreightEntity.setPackingFee(vo.getPackageFee() != null ? vo
				.getPackageFee() : BigDecimal.valueOf(0));
		// 送货费
		actualFreightEntity.setDeliverFee(vo.getDeliveryGoodsFee() != null ? vo
				.getDeliveryGoodsFee() : BigDecimal.valueOf(0));
		// 装卸费
		actualFreightEntity.setLaborFee(vo.getServiceFee() != null ? vo
				.getServiceFee() : BigDecimal.valueOf(0));
		// 代收货款
		actualFreightEntity.setCodAmount(vo.getCodAmount() != null ? vo
				.getCodAmount() : BigDecimal.valueOf(0));
		// 增值费
		actualFreightEntity.setValueaddFee(vo.getValueAddFee() != null ? vo
				.getValueAddFee() : BigDecimal.valueOf(0));
		// 公布价运费
		actualFreightEntity.setFreight(vo.getTransportFee() != null ? vo
				.getTransportFee() : BigDecimal.valueOf(0));
		//伙伴开单
		boolean b=vo.getPartnerBilling()==null?false:vo.getPartnerBilling();
		if(b){
			actualFreightEntity.setPartnerBillingLogo(FossConstants.YES);
		}else{
			actualFreightEntity.setPartnerBillingLogo(FossConstants.NO);
		}
		if(BZPartnersJudge.IS_PARTENER){
			actualFreightEntity.setPartnerBillingLogo(FossConstants.YES);
		}else{
			actualFreightEntity.setPartnerBillingLogo(FossConstants.NO);
		}
		//伙伴名称
		actualFreightEntity.setPartnerName(vo.getPartnerName());
		//伙伴电话
		actualFreightEntity.setPartnerPhome(vo.getPartnerPhone());
		// 结清状态
		actualFreightEntity.setSettleStatus(FossConstants.NO);
		// 结清时间
		actualFreightEntity.setSettleTime(new Date());
		// 通知状态
		// actualFreightEntity.setNotificationType(vo.get)
		// 通知时间
		// actualFreightEntity.setNotificationTime(vo);
		// 送货时间
		// actualFreightEntity.setDeliverDate(vo.getPreCustomerPickupTime());
		// 实际送货方式
		actualFreightEntity.setActualDeliverType(vo.getReceiveMethod()
				.getValueCode());
		// 运单状态
		actualFreightEntity.setStatus(WaybillConstants.EFFECTIVE);
		// 库存天数
		// actualFreightEntity.setStorageDay(vo.getst)
		// 库存费用
		// actualFreightEntity.setStorageCharge(vo.get);
		actualFreightEntity.setStartStockOrgCode(waybillService
				.queryStartStocksDepartmentService(waybillEntity));
		actualFreightEntity.setEndStockOrgCode(waybillService
				.queryEndStocksDepartmentService(waybillEntity));
		// actualFreightEntity.setActualDeliverType(vo.getdelivery)
		// 已生效
		actualFreightEntity.setStatus(WaybillConstants.EFFECTIVE);

		// 整车开单的时候 对actual Freight表的字段做如下调整
		if (vo.getIsWholeVehicle() != null && vo.getIsWholeVehicle()) {
			// 1 ARRIVE_GOODS_QTY = 开单件数
			actualFreightEntity.setArriveGoodsQty(vo.getGoodsQtyTotal());
			// 2 ARRIVE_NOTOUT_GOODS_QTY 到达未签收件数 = 开单件数
			actualFreightEntity.setArriveNotoutGoodsQty(vo.getGoodsQtyTotal());
			// 3 若预计到达时间 不为空则为预计到达时间，否则为当前时间
			// 预计到达时间
			Date preArrivedTime = vo.getPreCustomerPickupTime();
			if (null != preArrivedTime) {
				actualFreightEntity.setArriveTime(preArrivedTime);
			} else {
				actualFreightEntity.setArriveTime(new Date());
			}
		}
		// 保存官网用户名
		actualFreightEntity.setChannelCustId(vo.getChannelCustId());
		//短信标识
		actualFreightEntity.setIsSMS(vo.getIsSMS());
		//快递优惠类型
		if(null!=vo.getSpecialOffer()&&StringUtil.isNotEmpty(vo.getSpecialOffer().getValueCode())){
			actualFreightEntity.setSpecialOffer(vo.getSpecialOffer().getValueCode());
		}
		//补录时间
		//if (actualFreightEntity.getIsNextDayPending()!=null) {
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(vo
				.getWaybillstatus())){
		if(StringUtils.isNotEmpty(vo.getIsNextDayPending())){
			actualFreightEntity.setIsNextDayPending(vo.getIsNextDayPending());
		}else{
		   actualFreightEntity.setIsNextDayPending("次日补录");
		}
		}
		//if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(vo
		//		.getWaybillstatus())) {
		if (actualFreightEntity.getIsNextDayPending()!=null){
			actualFreightEntity.setPendingTime(new Date());
		} 
		//发货地址备注
		actualFreightEntity.setDeliveryCustomerAddressNote(vo.getDeliveryCustomerAddressNote());
		//收货地址备注
		actualFreightEntity.setReceiveCustomerAddressNote(vo.getReceiveCustomerAddressNote());
		//@author wutao
		// 始发客户是否统一结算
		if (WaybillConstants.IS_NOT_NULL_FOR_AI.equals(vo.getStartCentralizedSettlement())) {
			actualFreightEntity
					.setStartCentralizedSettlement(FossConstants.YES);
		} else {
			actualFreightEntity.setStartCentralizedSettlement(FossConstants.NO);
		}
		actualFreightEntity.setStartContractOrgCode(vo
				.getStartContractOrgCode());
		actualFreightEntity.setStartReminderOrgCode(vo
				.getStartReminderOrgCode());
		// 到达客户是否统一结算
		if (WaybillConstants.IS_NOT_NULL_FOR_AI.equals(vo.getArriveCentralizedSettlement())) {
			actualFreightEntity
					.setArriveCentralizedSettlement(FossConstants.YES);
		} else {
			actualFreightEntity
					.setArriveCentralizedSettlement(FossConstants.NO);
		}
		// 这里面存储的是【合同部门】标杆【催款部门标杆】
		// 注释：催款部门标杆不是所有的都有的
		actualFreightEntity.setArriveContractOrgCode(vo
				.getArriveContractOrgCode());
		actualFreightEntity.setArriveReminderOrgCode(vo
				.getArriveReminderOrgCode());

		/**
		 * 将WayBillPanelVo中的IsElectronicInvoice属性添加到actualFreightEntity中
		 * @author 218371-foss-zhaoyanjun
		 * @date:2014-10-22上午08:47
		 */
		actualFreightEntity.setIsElectronicInvoice(vo.getIsElectronicInvoice());
		/**
		 * 将WayBillPanelVo中的invoiceMobilePhone属性添加到actualFreightEntity中
		 * @author 218371-foss-zhaoyanjun
		 * @date:2014-10-22上午08:47
		 */
		actualFreightEntity.setInvoiceMobilePhone(vo.getInvoiceMobilePhone());
		/**
		 * 将WayBillPanelVo中的email属性添加到actualFreightEntity中
		 * @author 218371-foss-zhaoyanjun
		 * @date:2014-10-22上午08:47
		 */
		actualFreightEntity.setEmail(vo.getEmail());
		//关联运单费用
		actualFreightEntity.setConnectionNumFee(vo.getConnectionNumFee());
		/**
		 * 保存交易流水号
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-24
		 */
		actualFreightEntity.setTransactionSerialNumber(vo.getTransactionSerialNumber());
		/**
		 * 内部发货折扣
		 */
		if(vo.getInternalDeliveryType() != null) {
			actualFreightEntity.setInternalDeliveryType(vo.getInternalDeliveryType().getValueCode());
			actualFreightEntity.setEmployeeNo(vo.getEmployeeNo());
		}
		//添加新增的收货乡镇(街道)
		actualFreightEntity.setReceiveCustomerVillageCode(vo.getReceiveCustomerVillageCode());
		/**
		 * 是否上门发货
		 */
		if(vo.isHomeDelivery()){			
			actualFreightEntity.setHomeDelivery(WaybillConstants.YES);
		}else{
			actualFreightEntity.setHomeDelivery(WaybillConstants.NO);
		}
		actualFreightEntity.setDcServicefee(vo.getDcServicefee());
		return actualFreightEntity;
	}

	/**
	 * 
	 * 获得运单数据
	 * 
	 * @author 025000-FOSS-helong
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-11-3 下午04:15:27
	 */
	private WaybillEntity getWaybillEntity(ExpWaybillPanelVo vo) {
		// 业务工具类
		BusinessUtils businessUtils = new BusinessUtils();
		WaybillEntity entity = new WaybillEntity();
		entity.setGoodsTypeCode(WaybillConstants.GOODS_TYPE_A);
		
		entity.setWaybillNo(vo.getWaybillNo());// 运单号
		entity.setOrderNo(vo.getOrderNo());// 订单号
		entity.setOrderChannel(vo.getOrderChannel());// 订单来源
		entity.setOrderPaidMethod(vo.getOrderPayment());// 订单付款方式
		entity.setDeliveryCustomerId(vo.getDeliveryCustomerId());// 发货客户ID
		entity.setDeliverCustContactId(vo.getDeliveryCustomerContactId());// 发货客户联系人ID
		entity.setDeliveryCustomerCode(vo.getDeliveryCustomerCode());// 发货客户编码
		entity.setDeliveryBigCustomer(vo.getDeliveryBigCustomer());// 大客户标记
		entity.setDeliveryCustomerName(vo.getDeliveryCustomerName());// 发货客户名称
		entity.setDeliveryCustomerMobilephone(vo
				.getDeliveryCustomerMobilephone());// 发货客户手机
		entity.setDeliveryCustomerPhone(vo.getDeliveryCustomerPhone());// 发货客户电话
		entity.setDeliveryCustomerContact(vo.getDeliveryCustomerContact());// 发货客户联系人
		// 发货人地址
		entity.setDeliveryCustomerAddress(StringUtils.substring(vo.getDeliveryCustomerAddress(), 0, NumberConstants.NUMBER_100));
		if(null!=vo.getReturnType()){
		entity.setReturnType(vo.getReturnType().getValueCode());
		}
		/**
		 * 发货客户省市区
		 */
		AddressFieldDto consignerAddress = businessUtils.getProvCityCounty(
				vo.getDeliveryCustomerProvCode(),
				vo.getDeliveryCustomerCityCode(),
				vo.getDeliveryCustomerDistCode());

		// 发货国家
		entity.setDeliveryCustomerNationCode(vo.getDeliveryCustomerNationCode());
		if (null != consignerAddress) {
			// 发货省份
			entity.setDeliveryCustomerProvCode(consignerAddress.getProvinceId());
			// 发货市
			entity.setDeliveryCustomerCityCode(consignerAddress.getCityId());
			// 发货区
			entity.setDeliveryCustomerDistCode(consignerAddress.getCountyId());
		}

		/**
		 * 收货客户省市区
		 */
		AddressFieldDto consigneeAddress = businessUtils.getProvCityCounty(
				vo.getReceiveCustomerProvCode(),
				vo.getReceiveCustomerCityCode(),
				vo.getReceiveCustomerDistCode());
		
		entity.setReceiveCustomerId(vo.getReceiveCustomerId());// 收货客户ID
		entity.setReceiverCustContactId(vo.getReceiveCustomerContactId());// 收货联系人ID
		entity.setReceiveCustomerCode(vo.getReceiveCustomerCode());// 收货客户编码
		entity.setReceiveBigCustomer(vo.getReceiveBigCustomer());// 大客户标记
		entity.setReceiveCustomerName(vo.getReceiveCustomerName());// 收货客户名称
		entity.setReceiveCustomerMobilephone(vo.getReceiveCustomerMobilephone());// 收货客户手机
		entity.setReceiveCustomerPhone(vo.getReceiveCustomerPhone());// 收货客户电话
		entity.setReceiveCustomerContact(vo.getReceiveCustomerContact());// 收货客户联系人
		if(StringUtils.isEmpty(vo.getReceiveCustomerCode())){
			if(consigneeAddress!=null){
				String provName = consigneeAddress.getProvinceName()!=null? consigneeAddress.getProvinceName():"" ;
				String cityName = consigneeAddress.getCityName()!=null? consigneeAddress.getCityName():"";
				String distName = consigneeAddress.getCountyName()!=null? consigneeAddress.getCountyName():"";
				//综合查询地址重复
//				entity.setReceiveCustomerAddress(StringUtils.substring(provName+cityName+distName+
//						vo.getReceiveCustomerAddress(), 0,100));// 收货具体地址
				entity.setReceiveCustomerAddress(StringUtils.substring(vo.getReceiveCustomerAddress(), 0, NumberConstants.NUMBER_100));// 收货具体地址
			}else{
				entity.setReceiveCustomerAddress(StringUtils.substring(vo.getReceiveCustomerAddress(), 0, NumberConstants.NUMBER_100));// 收货具体地址
			}
		}else{
			entity.setReceiveCustomerAddress(StringUtils.substring(vo.getReceiveCustomerAddress(), 0, NumberConstants.NUMBER_100));// 收货具体地址
		}
		// 接送货地址ID
		entity.setContactAddressId(vo.getContactAddressId());

		// 收货国家
		entity.setReceiveCustomerNationCode(vo.getReceiveCustomerNationCode());
		if (null != consigneeAddress) {
			// 收货省份
			entity.setReceiveCustomerProvCode(consigneeAddress.getProvinceId());
			// 收货市
			entity.setReceiveCustomerCityCode(consigneeAddress.getCityId());
			// 收货区
			entity.setReceiveCustomerDistCode(consigneeAddress.getCountyId());
		}

		entity.setReceiveOrgCode(vo.getReceiveOrgCode());// 收货部门
		entity.setOrderVehicleNum(vo.getVehicleNumber());// 约车编号
		entity.setProductId(vo.getProductCode().getId());// 产品ID
		entity.setProductCode(vo.getProductCode().getCode());// 运输性质
		entity.setReceiveMethod(vo.getReceiveMethod().getValueCode());// 提货方式
		entity.setCustomerPickupOrgCode(vo.getCustomerPickupOrgCode().getCode());// 提货网点
		entity.setLoadMethod(vo.getLoadMethod());// 配载类型
		entity.setTargetOrgCode(vo.getTargetOrgCode());// 目的站
		entity.setPickupToDoor(BooleanConvertYesOrNo.booleanToString(vo
				.getPickupToDoor()));// 是否上门接货
		entity.setDriverCode(vo.getDriverCode());// 司机

		// 业务工具类
		// 是否是开的集中接送货运单

		entity.setPickupCentralized(BooleanConvertYesOrNo.booleanToString(vo
				.getPickupCentralized()));// 是否集中接货
		entity.setLoadLineCode(vo.getLoadLineCode());// 配载线路
		entity.setLoadOrgCode(vo.getLoadOrgCode());// 配载部门
		entity.setLastLoadOrgCode(vo.getLastLoadOrgCode());// 最终配载部门
		entity.setPreCustomerPickupTime(vo.getPreCustomerPickupTime());// 预计派送/提货时间
		entity.setPreDepartureTime(vo.getPreDepartureTime());// 预计出发时间
		entity.setCarDirectDelivery(BooleanConvertYesOrNo.booleanToString(vo
				.getCarDirectDelivery()));// 是否大车直送
		
		if(StringUtils.isNotEmpty(vo.getGoodsName())){
			entity.setGoodsName(vo.getGoodsName());// 货物名称
		}else{
			entity.setGoodsName(waybillEditUI.cargoInfoPanel.getTxtGoodsName().getText());
		}

		entity.setGoodsQtyTotal(vo.getGoodsQtyTotal());// 货物总件数
		entity.setGoodsWeightTotal(vo.getGoodsWeightTotal());// 货物总重量
		
		entity.setGoodsVolumeTotal(vo.getGoodsVolumeTotal());// 货物总体积
		
		entity.setWaybillNos(vo.getWaybillNos());
		entity.setPicture(vo.isPicture());
		
//		if(vo.getGoodsVolumeTotal()!=null && vo.getGoodsVolumeTotal().doubleValue()>0){
//			entity.setGoodsVolumeTotal(vo.getGoodsVolumeTotal());// 货物总体积
//			changeVolume = false;
//		}else{
//			entity.setGoodsVolumeTotal(BigDecimal.valueOf(0.01d));// 货物总体积
//			changeVolume = true;
//		}
		entity.setGoodsSize(vo.getGoodsSize());// 货物尺寸
//		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(vo
//				.getProductCode().getCode())) {
//			entity.setGoodsTypeCode(vo.getAirGoodsType().getValueCode());// 货物类型
//		} else {
//			entity.setGoodsTypeCode(vo.getGoodsType());// 货物类型
//		}

		entity.setPreciousGoods(BooleanConvertYesOrNo.booleanToString(vo
				.getPreciousGoods()));// 是否贵重物品
		entity.setSpecialShapedGoods(BooleanConvertYesOrNo.booleanToString(vo
				.getSpecialShapedGoods()));// 是否异形物品
		// 对外备注
		if (vo.getOuterNotes() != null) {
			entity.setOuterNotes(vo.getOuterNotes().getValueCode());// 对外备注
		}

		entity.setInnerNotes(vo.getInnerNotes());// 对内备注
		entity.setTransportationRemark(vo.getTransportationRemark());// 储运事项
		if(vo.getGoodsPackage()!=null){
			entity.setGoodsPackage(
				StringUtils.replace(vo.getGoodsPackage(),"NULL", ""));// 货物包装
		}
		
		// 纸
		entity.setPaperNum(Integer.valueOf(vo.getPaper()));
		// 木
		entity.setWoodNum(Integer.valueOf(vo.getWood()));
		// 纤
		entity.setFibreNum(Integer.valueOf(vo.getFibre()));
		// 托
		entity.setSalverNum(Integer.valueOf(vo.getSalver()));
		// 膜
		entity.setMembraneNum(vo.getMembrane());
		// 其他
		entity.setOtherPackage(vo.getOtherPackage());

		entity.setInsuranceAmount(vo.getInsuranceAmount());// 保价声明价值
		BigDecimal insuranceRate = vo.getInsuranceRate();
		// 将费率转换成千分率的数据
		insuranceRate = insuranceRate.divide(new BigDecimal(
				WaybillConstants.PERMILLAGE));
		entity.setInsuranceRate(insuranceRate);// 保价费率

		entity.setInsuranceFee(vo.getInsuranceFee());// 保价费
		entity.setCodAmount(vo.getCodAmount());// 代收货款
		BigDecimal codReate = vo.getCodRate();
		// 将费率转换成千分率的数据
		codReate = codReate.divide(new BigDecimal(WaybillConstants.PERMILLAGE));
		entity.setCodRate(codReate);// 代收费率

		entity.setCodFee(vo.getCodFee());// 代收货款手续费
		if (vo.getRefundType() != null) {
			entity.setRefundType(vo.getRefundType().getValueCode());// 退款类型
		}

		entity.setReturnBillType(vo.getReturnBillType().getValueCode());// 返单类别
		entity.setSecretPrepaid(BooleanConvertYesOrNo.booleanToString(vo
				.getSecretPrepaid()));// 预付费保密
		entity.setToPayAmount(vo.getToPayAmount());// 到付金额
		entity.setPrePayAmount(vo.getPrePayAmount());// 预付金额
		entity.setDeliveryGoodsFee(vo.getDeliveryGoodsFee());// 送货费
		entity.setOtherFee(vo.getOtherFee());// 其他费用
		entity.setPackageFee(vo.getPackageFee());// 包装手续费
		entity.setPromotionsFee(vo.getPromotionsFee());// 优惠费用
		// 运费计费类型
		String billTypeCode = vo.getBillingType().getValueCode();
		// 判断计费类型是否为空
		if (StringUtil.isEmpty(billTypeCode)) {
			// 为空则默认为重量计费
			entity.setBillingType(WaybillConstants.BILLINGWAY_WEIGHT);
		} else {
			// 不为空则从VO中取值
			entity.setBillingType(vo.getBillingType().getValueCode());
		}
		entity.setUnitPrice(vo.getUnitPrice());// 运费计费费率
		entity.setTransportFee(vo.getTransportFee());// 公布价运费
		entity.setValueAddFee(vo.getValueAddFee());// 增值费用
		if (vo.getPaidMethod() != null) {
			entity.setPaidMethod(vo.getPaidMethod().getValueCode());// 开单付款方式
		}
		entity.setArriveType(vo.getArriveType());// 到达类型
		entity.setActive(FossConstants.YES);// 运单状态
		entity.setForbiddenLine(FossConstants.YES);// 禁行
		if (vo.getFreightMethod() != null) {
			entity.setFreightMethod(vo.getFreightMethod().getValueCode());// 合票方式
		}
		entity.setFlightShift(vo.getFlightShift());// 航班时间
		if (vo.getFlightNumberType() != null) {
			entity.setFlightNumberType(vo.getFlightNumberType().getValueCode());// 航班类型
		}
		entity.setTotalFee(vo.getTotalFee());// 总费用
		entity.setPromotionsCode(vo.getPromotionsCode());// 优惠编码
		entity.setCreateTime(new Date());// 创建时间
		entity.setModifyTime(new Date());// 更新时间
		// 开单时间
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(vo
				.getWaybillstatus())) {
			entity.setBillTime(vo.getBillTime());
		} else {
			entity.setBillTime(new Date());
		}
		// 运单处理状态
		if ("".equals(StringUtil.defaultIfNull(vo.getWaybillstatus()))) {
			if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(
					"user_loginType").toString())) {
				entity.setPendingType(WaybillConstants.WAYBILL_STATUS_PC_PENDING);// 运单暂存
			} else {
				entity.setPendingType(WaybillConstants.WAYBILL_STATUS_OFFLINE_PENDING);// 运单离线暂存
			}
		} else {
			entity.setPendingType(vo.getWaybillstatus());
		}
		entity.setCreateUserCode(vo.getCreateUserCode());// 开单人
		entity.setModifyUserCode(vo.getCreateUserCode());// 更新人
		entity.setCreateOrgCode(vo.getCreateOrgCode());// 开单组织
		entity.setModifyOrgCode(vo.getCreateOrgCode());// 更新组织
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 币种
		entity.setIsWholeVehicle(BooleanConvertYesOrNo.booleanToString(vo
				.getIsWholeVehicle()));// 是否整车运单
		entity.setIsPassOwnDepartment(BooleanConvertYesOrNo.booleanToString(vo
				.getIsPassDept()));// 是否经过营业部
		entity.setWholeVehicleActualfee(vo.getWholeVehicleActualfee());// 整车开单报价
		entity.setWholeVehicleAppfee(vo.getWholeVehicleAppfee());// 整车约车报价
		entity.setAccountName(vo.getAccountName());// 返款帐户开户名称
		entity.setAccountCode(vo.getAccountCode());// 返款帐户开户账户
		entity.setAccountBank(vo.getAccountBank());// 返款帐户开户银行
		entity.setBillWeight(vo.getBillWeight());// 计费重量
		entity.setPickupFee(vo.getPickupFee());// 接货费

		entity.setServiceFee(vo.getServiceFee());// 装卸费
		entity.setPreArriveTime(vo.getPreCustomerPickupTime());// 预计到达时间
		entity.setTransportType(ExpWaybillConstants.TRANS_EXPRESS);// 运输类型
		entity.setPrintTimes(0);// 打印次数
		entity.setAddTime(new Date());// 新增时间

		// 运单冗字段数据填充
		// 运单提交人姓名
		/**
		 * 解决KDTE-2769中“FOSS系统中查询两票单号却显示不同开单人”的问题
		 */
		//判断是否是PDA补录
		if(WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(vo.getWaybillstatus())){
			//获得当前用户信息
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			String empCode = "";
			String empDeptCode = "";
			String empDeptName = "";
			if(user != null){
				EmployeeEntity employee = user.getEmployee();
				if(null != employee){
					empCode = employee.getEmpCode();
					empDeptCode = employee.getOrgCode();
					OrgAdministrativeInfoEntity org = employee.getDepartment();
					if(org != null){
						empDeptName = org.getName();
					}else{
						empDeptName = CommonUtils.getDeptNameByCode(empDeptCode);
					}
				}
			}
			// 运单提交人姓名
			entity.setCreateUserName(Common.getEmployeeNameByCode(empCode));
			// 运单提交人所在部门名称
			entity.setCreateUserDeptName(empDeptName);
		}else{
			// 运单提交人姓名
			entity.setCreateUserName(Common.getEmployeeNameByCode(vo.getCreateUserCode()));
			// 运单提交人所在部门名称
			entity.setCreateUserDeptName(CommonUtils.getDeptNameByCode(vo.getCreateOrgCode()));
		}

		// 收货部门名称
		entity.setReceiveOrgName(CommonUtils.getDeptNameByCode(vo.getReceiveOrgCode()));
		// 提货网点名称
		entity.setCustomerPickupOrgName(CommonUtils.getDeptNameByCode(vo.getCustomerPickupOrgCode().getCode()));
		// 配载部门名称
		entity.setLoadOrgName(CommonUtils.getDeptNameByCode(vo.getLoadOrgCode()));
		// 最终配置部门名称
		entity.setLastLoadOrgName(CommonUtils.getDeptNameByCode(vo.getLastLoadOrgCode()));
		// 公里数
		entity.setKilometer(vo.getKilometer());
		// 是否经济自提件
		entity.setIsEconomyGoods(BooleanConvertYesOrNo.booleanToString(vo.getIsEconomyGoods()));
		// 经济自提件类型
		if(vo.getEconomyGoodsType()!=null){
			entity.setEconomyGoodsType(vo.getEconomyGoodsType().getValueCode());	
		}else{
			entity.setEconomyGoodsType(null);
		}	
		
		
		
		if(WaybillConstants.DEPPON_CUSTOMER.equals(vo.getDeliveryCustomerCode())
				&&
				StringUtils.isNotEmpty(vo.getDeliveryEmployeeNo())){
			
			String inner = entity.getInnerNotes();
			if(null==inner){
				inner = "";
			}else{
				if(StringUtils.contains(inner, "发货人工号：")){
					inner = StringUtils.substringAfter(inner, ";");
				}
				
			}
			
			entity.setInnerNotes(i18n.get("foss.gui.creating.consignerPanel.deliveryEmployeeNo.label")+"："
					
					+vo.getDeliveryEmployeeNo()+";"
					
			+inner);
		}
		
		return entity;
	}

	/**
	 * 
	 * 获取折扣明细
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-15 下午07:28:33
	 */
	private List<WaybillDisDtlEntity> getWaybillDisDtlEntity(
			WaybillDiscountCanvas model, WaybillPanelVo vo) {
		List<WaybillDisDtlEntity> disDtlEntityList = new ArrayList<WaybillDisDtlEntity>();
		List<WaybillDiscountVo> data = model.getData();
		if (data != null && !data.isEmpty()) {
			for (WaybillDiscountVo entity : data) {
				WaybillDisDtlEntity disDtlEntity = new WaybillDisDtlEntity();
				// 优惠项目名称
				disDtlEntity.setPricingEntryName(entity.getFavorableItemName());
				// 优惠项目CODE
				disDtlEntity.setPricingEntryCode(entity.getFavorableItemCode());
				// 优惠类型名称
				disDtlEntity.setTypeName(entity.getFavorableTypeName());
				// 优惠类型CODE
				disDtlEntity.setType(entity.getFavorableTypeCode());
				// 优惠子类型名称
				disDtlEntity.setSubTypeName(entity.getFavorableSubTypeName());
				// 优惠子类型CODE
				disDtlEntity.setSubType(entity.getFavorableSubTypeCode());
				// 折扣费率
				disDtlEntity.setRate(new BigDecimal(entity
						.getFavorableDiscount()));
				//设置快递续重折扣率
				if(entity.getContinueFavorableDiscount()!=null){
					disDtlEntity.setExpressContinueRate(new BigDecimal(entity.getContinueFavorableDiscount()));
				}
				// 折扣金额
				disDtlEntity.setAmount(new BigDecimal(entity
						.getFavorableAmount()));
				// 运单号
				disDtlEntity.setWaybillNo(vo.getWaybillNo());
				if (entity.getDiscountId() != null) {
					disDtlEntity.setDicountId(entity.getDiscountId());
				} else {
					disDtlEntity.setDicountId(UUIDUtils.getUUID());
				}
				if (entity.getChargeDetailId() != null) {
					disDtlEntity.setWaybillChargeDetailId(entity
							.getChargeDetailId());
				} else {
					disDtlEntity.setWaybillChargeDetailId(UUIDUtils.getUUID());
				}
				disDtlEntity.setCreateTime(new Date());
				disDtlEntity.setModifyTime(new Date());
				disDtlEntity.setActive(FossConstants.ACTIVE);
				disDtlEntity.setBillTime(new Date());
				disDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				//营销活动编码
        		disDtlEntity.setActiveCode(entity.getActiveCode());
        		//营销活动名称
        		disDtlEntity.setActiveName(entity.getActiveName());
        		//营销活动开始时间
        		disDtlEntity.setActiveStartTime(entity.getActiveStartTime());
        		//营销活动结束时间
        		disDtlEntity.setActiveEndTime(entity.getActiveEndTime());
        		//营销活动折扣相应的CRM_ID
        		disDtlEntity.setOptionsCrmId(entity.getOptionsCrmId());
				disDtlEntityList.add(disDtlEntity);
			}
		}
		return disDtlEntityList;
	}

	/**
	 * 
	 * 获取打木架信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-15 下午07:31:07
	 */
	private WoodenRequirementsEntity getWoodenRequirementsEntity(
			WaybillPanelVo vo) {
		if (CommonUtils.defaultIfNull(vo.getStandGoodsNum()).intValue() == 0
				&& CommonUtils.defaultIfNull(vo.getBoxGoodsNum()).intValue() == 0) {
			return null;
		} else {
			WoodenRequirementsEntity woodenRequirementsEntity = new WoodenRequirementsEntity();
			woodenRequirementsEntity.setWaybillNo(vo.getWaybillNo());
			woodenRequirementsEntity.setPackageOrgCode(vo.getPackageOrgCode());
			woodenRequirementsEntity.setStandGoodsNum(vo.getStandGoodsNum());
			woodenRequirementsEntity.setStandRequirement(vo
					.getStandRequirement());
			woodenRequirementsEntity.setStandGoodsSize(vo.getStandGoodsSize());
			woodenRequirementsEntity.setStandGoodsVolume(vo
					.getStandGoodsVolume());
			woodenRequirementsEntity.setBoxGoodsNum(vo.getBoxGoodsNum());
			woodenRequirementsEntity.setBoxRequirement(vo.getBoxRequirement());
			woodenRequirementsEntity.setBoxGoodsSize(vo.getBoxGoodsSize());
			woodenRequirementsEntity.setBoxGoodsVolume(vo.getBoxGoodsVolume());
			woodenRequirementsEntity.setActive(FossConstants.ACTIVE);
			woodenRequirementsEntity.setCreateTime(new Date());
			woodenRequirementsEntity.setModifyTime(new Date());
			return woodenRequirementsEntity;
		}
	}

	/**
	 * 
	 * 客户付款明细收集
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-15 下午07:32:36
	 */
	private List<WaybillPaymentEntity> getWaybillPaymentEntity(ExpWaybillPanelVo vo) {
		String returnType = (vo.getReturnType() == null ? null : vo.getReturnType().getValueCode());
		String pickupMode = (vo.getReceiveMethod() == null ? null : vo.getReceiveMethod().getValueCode());
		
		if (!WaybillConstants.INNER_PICKUP.equals(pickupMode) &&
				!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(returnType) ) {
			List<WaybillPaymentEntity> waybillPaymentEntityList = new ArrayList<WaybillPaymentEntity>();
			// 判断是否为PAD导入开单
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(vo
					.getWaybillstatus())) {
				// 判断PDA总金额是否为空
				if (vo.getTotalCountPDA() != null
						&& vo.getTotalCountPDA().compareTo(BigDecimal.ZERO) != 0) {
					WaybillPaymentEntity totalFeePDA = getTotalFeePDA(vo);
					waybillPaymentEntityList.add(totalFeePDA);
				}
			}
			// 判断预付金额是否为空
			if (vo.getPrePayAmount() != null
					&& vo.getPrePayAmount().compareTo(BigDecimal.ZERO) != 0) {
				WaybillPaymentEntity prePayAmount = getPrePayAmount(vo);
				waybillPaymentEntityList.add(prePayAmount);// 预付金额
			}
			// 判断到付金额是否为空
			if (vo.getToPayAmount() != null
					&& vo.getToPayAmount().compareTo(BigDecimal.ZERO) != 0) {
				WaybillPaymentEntity toPayAmount = getToPayAmount(vo);
				waybillPaymentEntityList.add(toPayAmount);// 到付金额
			}
			// 判断手写现付金额是否为空
			if (vo.getHandWriteMoney() != null
					&& vo.getHandWriteMoney().compareTo(BigDecimal.ZERO) != 0) {
				WaybillPaymentEntity handWriteMoney = getHandWriteMoney(vo);
				waybillPaymentEntityList.add(handWriteMoney);// 手写现付金额
			}
			return waybillPaymentEntityList;
		}else{
			return null;
		}
	}

	/**
	 * 获取DPA总金额
	 * 
	 * @author 026113-FOSS-linwensheng
	 * @return WaybillPaymentEntity
	 */
	private WaybillPaymentEntity getTotalFeePDA(WaybillPanelVo vo) {
		WaybillPaymentEntity totalFeePDA = new WaybillPaymentEntity();
		totalFeePDA.setWaybillNo(vo.getWaybillNo());
		totalFeePDA.setType(WaybillConstants.PAYMENT_PDA_TOTAL_PAY);// pda总运费类型
		totalFeePDA.setAmount(vo.getTotalCountPDA());
		totalFeePDA.setActive(FossConstants.ACTIVE);
		totalFeePDA.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// RMB
		totalFeePDA.setPaymentTime(new Date());
		return totalFeePDA;
	}

	/**
	 * 获取预付金额
	 * 
	 * @author 026113-FOSS-linwensheng
	 * @return WaybillPaymentEntity
	 */
	private WaybillPaymentEntity getPrePayAmount(WaybillPanelVo vo) {
		WaybillPaymentEntity prePayAmount = new WaybillPaymentEntity();
		prePayAmount.setWaybillNo(vo.getWaybillNo());
		prePayAmount.setType(WaybillConstants.PAYMENT_PRE_PAY);// 预付金额类型
		prePayAmount.setAmount(vo.getPrePayAmount());// 预付金额
		prePayAmount.setActive(FossConstants.ACTIVE);
		prePayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// RMB
		prePayAmount.setPaymentTime(new Date());
		return prePayAmount;

	}

	/**
	 * 到付金额
	 * 
	 * @author 026113-FOSS-linwensheng
	 * @return WaybillPaymentEntity
	 */
	private WaybillPaymentEntity getToPayAmount(WaybillPanelVo vo) {

		WaybillPaymentEntity toPayAmount = new WaybillPaymentEntity();
		toPayAmount.setWaybillNo(vo.getWaybillNo());
		toPayAmount.setType(WaybillConstants.PAYMENT_TO_PAY);// 到付金额类型
		toPayAmount.setAmount(vo.getToPayAmount());// 到付金额
		toPayAmount.setActive(FossConstants.ACTIVE);
		toPayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// RMB
		toPayAmount.setPaymentTime(new Date());
		return toPayAmount;
	}

	/**
	 * 手写先付金额
	 * 
	 * @author 026113-FOSS-linwensheng
	 * @return WaybillPaymentEntity
	 */
	private WaybillPaymentEntity getHandWriteMoney(WaybillPanelVo vo) {
		WaybillPaymentEntity toPayAmount = new WaybillPaymentEntity();
		toPayAmount.setWaybillNo(vo.getWaybillNo());
		toPayAmount.setType(WaybillConstants.PAYMENT_REAL_PAY);// 手写先付金额类型
		toPayAmount.setAmount(vo.getHandWriteMoney());// 手写先付金额
		toPayAmount.setActive(FossConstants.ACTIVE);
		toPayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// RMB
		toPayAmount.setPaymentTime(new Date());
		return toPayAmount;
	}

	/**
	 * setIInjectUI
	 */
	@Override
	public void setIInjectUI(JDialog confirmDialog) {

		this.confirmDialog = confirmDialog;

	}

	/**
	 * 
	 * 插入一条打印数据
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-15 下午07:32:36
	 */
	public void printInfoInsert(WaybillPanelVo bean) {
		WaybillEntity waybillEntity = waybillService.queryWaybillByNumber(bean
				.getWaybillNo());
		if (waybillEntity != null) {
			PrintInfoEntity record = new PrintInfoEntity();
			record.setPrintType(WaybillConstants.PRINT_INFO_WAYBILL);
			record.setWaybillNo(bean.getWaybillNo());
			record.setWaybillId(waybillEntity.getId());
			// record.setPrintTimes();
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			record.setPrintUserCode(user.getEmployee().getEmpCode());
			record.setPrintUser(user.getEmployee().getEmpName());
			record.setPrintOrgCode(user.getEmployee().getDepartment().getCode());
			record.setPrintOrg(user.getEmployee().getDepartment().getName());
			record.setPrintTime(new Date());
			record.setId(UUIDUtils.getUUID());
			WaybillServiceFactory.getWaybillService().insertSelective(record);
			// WaybillServiceFactory.getWaybillService().updateWaybillPrintTimesOnLine(resourceBean.getWaybillNo());
		}
	}
}
