package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpPrintSignUI;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintManager;
import com.deppon.foss.print.labelprint.service.LblPrtServiceConst;
import com.deppon.foss.prt.PrintUtil;
import com.google.inject.Injector;


/**
 * 批量打印
 * @author Foss-280747-Zhuxue
 *
 */
public class ExpLabelBatchprintingpiAction extends AbstractButtonActionListener<ExpPrintSignUI>{
	// 日志
	private static final Log LOG = LogFactory.getLog(ExpLabelBatchprintingpiAction.class);

	// 国际化
	private static final II18n i18n = I18nManager
			.getI18n(ExpLabelBatchprintingpiAction.class);

	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	/**
	 * 选中的导出运单号
	 */
	private List<String> selectExportWaybillNoList = new ArrayList<String>();

	Injector injector = GuiceContextFactroy.getInjector();
	
	private ExpPrintSignUI ui;
	
	private BarcodePrintLabelDto printLabelBean = new BarcodePrintLabelDto();
	//存储根据运单号所查询
	private List<BarcodePrintLabelDto> printLabelBeans = new ArrayList<BarcodePrintLabelDto>();
	/**
	 * 选中的checkbox
	 */
	private List<JCheckBox> list;
	
	public List<JCheckBox> getList() {
		return list;
	}


	public void setList(List<JCheckBox> list) {
		this.list = list;
	}


	public BarcodePrintLabelDto getPrintLabelBean() {
		return printLabelBean;
	}


	public void setPrintLabelBean(BarcodePrintLabelDto printLabelBean) {
		this.printLabelBean = printLabelBean;
	}


	public List<BarcodePrintLabelDto> getPrintLabelBeans() {
		return printLabelBeans;
	}


	public void setPrintLabelBeans(List<BarcodePrintLabelDto> printLabelBeans) {
		this.printLabelBeans = printLabelBeans;
	}
	private static final String COMMA = ",";

	private static final String FORMATSTR = "%04d";
	// 标签信息
	private List<LabeledGoodEntity> labeledlist;
	
	public List<LabeledGoodEntity> getLabeledlist() {
		return labeledlist;
	}


	public void setLabeledlist(List<LabeledGoodEntity> labeledlist) {
		this.labeledlist = labeledlist;
	}


	public JCheckBox getChckbxNewCheckBox() {
		return chckbxNewCheckBox;
	}


	public void setChckbxNewCheckBox(JCheckBox chckbxNewCheckBox) {
		this.chckbxNewCheckBox = chckbxNewCheckBox;
	}
	//运单状态
	private String waybillStatus;
	
	private JCheckBox chckbxNewCheckBox;
	public List<String> getSelectExportWaybillNoList() {
		return selectExportWaybillNoList;
	}


	public void setSelectExportWaybillNoList(List<String> selectExportWaybillNoList) {
		this.selectExportWaybillNoList = selectExportWaybillNoList;
	}
	// 文本区 流水号
	private JTextArea textArea;
	
	/*
	 * 打印
	 */
	
	public IWaybillService getWaybillService() {
		return waybillService;
	}


	public void setWaybillService(IWaybillService waybillService) {
		this.waybillService = waybillService;
	}


	public ExpPrintSignUI getUi() {
		return ui;
	}


	public void setUi(ExpPrintSignUI ui) {
		this.ui = ui;
	}


	public String getWaybillStatus() {
		return waybillStatus;
	}


	public void setWaybillStatus(String waybillStatus) {
		this.waybillStatus = waybillStatus;
	}


	public JTextArea getTextArea() {
		return textArea;
	}


	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}


	/**
	 * 批量打印
	 */
	@Override
	public void actionPerformed(ActionEvent e) {	
		/**
		 * foss-zhuxue
		 */
		// 循环单号查询出运单的信息和流水号集合
		waybillService = WaybillServiceFactory.getWaybillService();
		selectExportWaybillNoList=ui.getSelectExportWaybillNoList();
		if (selectExportWaybillNoList != null
				&& ! selectExportWaybillNoList.isEmpty()) {
			for (int i = 0; i < selectExportWaybillNoList.size(); i++) {
				// 运单实体信息不为空
				if (waybillService
						.queryWaybillByNumber(selectExportWaybillNoList.get(i)) != null) {
					WaybillEntity waybillEntity = waybillService
							.queryWaybillByNumber(selectExportWaybillNoList
									.get(i));
					// 运单状态不为空
					if (waybillEntity.getActive() != null) {
						// 获得运单的状态
						waybillStatus = waybillEntity.getActive();
						printLabelBean = waybillService
								.getCommonLabelPrintInfoExpress(
										selectExportWaybillNoList.get(i),
										waybillStatus);
						printLabelBeans.add(printLabelBean);
					} else {
						MsgBox.showInfo(i18n
								.get("foss.gui.creating.printSignUI.msgbox.error.existWaybillNoTow"));
						return;
					}
				} else {
					MsgBox.showInfo(i18n
							.get("foss.gui.creating.printSignUI.msgbox.error.existWaybillNoThrew"));
					return;
				}
			}
		} else {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.printSignUI.msgbox.error.existWaybillNo"));
			return;
		}

		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		// 获取流水号
		String[] seriesNo = null;
		if (printLabelBeans != null && printLabelBeans.size() > 0) {
			for (int i = 0; i < printLabelBeans.size(); i++) {
				printLabelBean = printLabelBeans.get(i);
				// 先判定是否对应的总件数<0
				int totalPieces = Integer.parseInt(printLabelBean
						.getTotalPieces());
				String waybillNo = null;
				if (totalPieces > 0) {
					// 获取单号
					waybillNo = printLabelBean.getWaybillNumber();
					String s =getPrintSerialnoss(totalPieces).toString();
					seriesNo=s.split(",");
					printLabelBean.setPrintSerialnos(s);
				} 
				if (!"".equals(printLabelBeans.get(i).getPrintSerialnos())
						&& null != printLabelBeans.get(i).getPrintSerialnos()) {
					PrintUtil printUtil = new PrintUtil();
					LabelPrintContext ctx = printUtil
							.printLabelData(printLabelBeans.get(i));
						try {
							LabelPrintManager
									.doLabelPrintAction(
											LblPrtServiceConst.key_lblprt_program_ExpCommLabelPrintWorker,
											ctx);

						} catch (Exception e1) {
							JOptionPane
									.showMessageDialog(
											null,
											i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"),
											i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
											JOptionPane.WARNING_MESSAGE);
						}
				}
			}
		} else {
			MsgBox.showInfo(i18n
					.get("foss.gui.creating.printSignUI.msgbox.printSuccessx"));
			return;
		}
		//打印完成后清空运单DTO
		printLabelBeans.clear();
		//打印完成后清空运单
		selectExportWaybillNoList.clear();
		//清空选中列表
		ui.getList().clear();
		//清空全选按钮
		ui.getAllSelectCheckBox().setSelected(false);
	}
	
	/**
	 * 拼接流水号
	 * @param totalGoodsQty
	 * @return
	 */
	private StringBuffer getPrintSerialnoss(int totalGoodsQty) {
		StringBuffer serialnos = new StringBuffer();
		if (totalGoodsQty > 0) {
			for (int i = 1; i <= totalGoodsQty; i++) {
				serialnos.append(String.format(FORMATSTR, i)).append(COMMA);
			}
		}
		return serialnos;
	}
	
	
	
	@Override
	public void setIInjectUI(ExpPrintSignUI ui) {
		this.ui=ui;
	}
}
