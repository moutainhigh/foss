package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintException;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GUIPrintLabelDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.SwiftNumberInfoDto;
import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintManager;
import com.deppon.foss.print.labelprint.service.LblPrtServiceConst;
import com.deppon.foss.prt.PrintUtil;

public class LabelWoodBatchprintingpiAction extends
		AbstractButtonActionListener<PrintSignUI> {

	private PrintSignUI ui;

	// 日志
	private static final Log LOG = LogFactory
			.getLog(LabelWoodBatchprintingpiAction.class);

	// 国际化
	private static final II18n i18n = I18nManager
			.getI18n(LabelWoodBatchprintingpiAction.class);

	// 查询Service
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	// 选中的流水号
	private List<String> selectExportWaybillNoList = new ArrayList<String>();

	// 选中的checkbox
	private List<JCheckBox> list;

	// 存储打印数据
	private BarcodePrintLabelDto printLabelBean = new BarcodePrintLabelDto();

	public BarcodePrintLabelDto getPrintLabelBean() {
		return printLabelBean;
	}

	public void setPrintLabelBean(BarcodePrintLabelDto printLabelBean) {
		this.printLabelBean = printLabelBean;
	}

	public PrintSignUI getUi() {
		return ui;
	}

	public void setUi(PrintSignUI ui) {
		this.ui = ui;
	}

	public IWaybillService getWaybillService() {
		return waybillService;
	}

	public void setWaybillService(IWaybillService waybillService) {
		this.waybillService = waybillService;
	}

	public List<String> getSelectExportWaybillNoList() {
		return selectExportWaybillNoList;
	}

	public void setSelectExportWaybillNoList(
			List<String> selectExportWaybillNoList) {
		this.selectExportWaybillNoList = selectExportWaybillNoList;
	}

	public List<JCheckBox> getList() {
		return list;
	}

	public void setList(List<JCheckBox> list) {
		this.list = list;
	}

	// 打木标签打印
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			/**
			 * 根据单号获取总件数，打木架件数，打木箱件数，打木托件数
			 */
			// 获取页面的单号
			String woodWaybillNo = String.valueOf(ui.getWoodWaybillNo().getText());
			if (StringUtils.isEmpty(woodWaybillNo)) {
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 打木实体信息
			WoodenRequirementsEntity woodenRequirementsEntity = new WoodenRequirementsEntity();
			// 查询打木架需求表获取打木相关数据
			woodenRequirementsEntity = waybillService.queryWoodenRequirement(woodWaybillNo);
			// 如果查询打木相关数据不为空就进行打印数据封装
			if (woodenRequirementsEntity == null) {
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.woodWaybillNo"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 打木架货物尺寸
			printLabelBean.setStandGoodsSize(woodenRequirementsEntity.getStandGoodsSize());
			// 打木架货物件数
			printLabelBean.setStandGoodsNum(woodenRequirementsEntity.getStandGoodsNum());
			// 打木架货物体积
			printLabelBean.setStandGoodsVolume(woodenRequirementsEntity.getStandGoodsVolume());
			//打木架要求
			printLabelBean.setStandRequirement(woodenRequirementsEntity.getStandRequirement());
			// 打木箱货物件数
			printLabelBean.setBoxGoodsNum(woodenRequirementsEntity.getBoxGoodsNum());
			// 打木箱货物尺寸
			printLabelBean.setBoxGoodsSize(woodenRequirementsEntity.getBoxGoodsSize());
			// 打木箱货物体积
			printLabelBean.setBoxGoodsVolume(woodenRequirementsEntity.getBoxGoodsVolume());
			//打木箱要求
			printLabelBean.setBoxRequirement(woodenRequirementsEntity.getBoxRequirement());
			// 打木托件数
			printLabelBean.setSalverGoodsNum(Integer.parseInt(ui.getTsalverGoodsNum().getText()));
			//打木托要求
			printLabelBean.setSalverRequirement(woodenRequirementsEntity.getSalverRequirement());
			// 运单信息
			WaybillEntity waybillEntity = waybillService.queryNewWaybillByNumber(woodWaybillNo);
			if (waybillEntity == null) {
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.waybillNo"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 总重量
			printLabelBean.setWeight(String.valueOf(waybillEntity.getGoodsWeightTotal() ==null ? "" : waybillEntity.getGoodsWeightTotal()));
			//总件数
			printLabelBean.setTotalPieces(String.valueOf(waybillEntity.getGoodsQtyTotal() ==null ? "" : waybillEntity.getGoodsQtyTotal()));
			// 运单号
			printLabelBean.setWaybillNumber(waybillEntity.getWaybillNo());
			if(StringUtils.isEmpty(waybillEntity.getProductCode())){
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.ProductCode"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			//运输性质
			String transtype = waybillService.queryWoodenProductByCache(waybillEntity.getProductCode());
			if(StringUtils.isEmpty(transtype)){
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.ProductCode"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			printLabelBean.setTranstype(transtype);
			// 从页面获取是否打印德邦物流
			JCheckBox chckbxPrintCheckBox = ui.getChckbxPrintWoodCheckBox();
			printLabelBean.setIsPrintLogo(chckbxPrintCheckBox.isSelected() ? WaybillConstants.YES : WaybillConstants.NO);
			/**
			 * 查询所有流水号
			 */
			List<SwiftNumberInfoDto> labeledlist=null;
			labeledlist = waybillService.queryUnpackDetailss(woodWaybillNo);
			if (labeledlist==null) {
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			Map<String ,String> totalPiecesMap = new LinkedHashMap<String ,String>();
			Collections.sort(labeledlist, new Comparator<SwiftNumberInfoDto>() {
				@Override
				public int compare(SwiftNumberInfoDto q1,SwiftNumberInfoDto q2) {
					int k =Integer.parseInt(q1.getSerialNo()) - Integer.parseInt(q2.getSerialNo());
					return k == 0 ? q2.getPackageType().compareTo(q1.getPackageType()) : k;
				}
			});
			// 如果查询的流水号不为空就添加到totalPiecesList
			if (CollectionUtils.isNotEmpty(labeledlist)) {
				for (SwiftNumberInfoDto q : labeledlist) {
					if (StringUtils.isNotBlank(q.getSerialNo())) {
						totalPiecesMap.put(q.getSerialNo(), q.getPackageType());
					}
				}
			}
			
			/**
			 * 循环抓取页面流水号与拼接后的木架和木箱流水号做对比，如果符合其中一种就将其中的那种流水号循环抓取出来与改流水号对比，符合就打印数据
			 */
			// 获取页面选中的流水号
			selectExportWaybillNoList = ui.getSelectExportWaybillNoList();
			if (selectExportWaybillNoList != null
					&& !selectExportWaybillNoList.isEmpty()) {
				// 循环获取选中的流水号
				for (int i = 0; i < selectExportWaybillNoList.size(); i++) {
					for (String s : totalPiecesMap.keySet()) {
						/**
						 * 打印一次就清空流水號和包裝類型
						 */
						printLabelBean.setPrintSerialnos(null);
						printLabelBean.setPacking(null);
						// 判断获取的流水号是打木架还是木箱,如果小于等于打木架件数就为打木架
						serial(s , totalPiecesMap,i);	
					}
				}
			}
		} catch (Exception exp) {
			LOG.error("标签打印异常" + exp.toString(), exp);
			JOptionPane.showMessageDialog(null, exp.toString(),
					i18n.get("foss.gui.creating.waybillEditUI.common.error"),
					JOptionPane.WARNING_MESSAGE);
		}

	}

	@SuppressWarnings("rawtypes")
	private void serial(String s, Map<String, String> map,int i) throws Exception {
		int num1=printLabelBean.getStandGoodsNum() ==null ? 0 : printLabelBean.getStandGoodsNum();
		int num2=printLabelBean.getBoxGoodsNum() ==null ? 0 : printLabelBean.getBoxGoodsNum();
		if (Integer.parseInt(selectExportWaybillNoList.get(i)) <= printLabelBean
				.getStandGoodsNum()) {
			// 判断页面取出的数据和循环拿出的数据一样（流水号）才打印
			if (StringUtils.isNotBlank(s) && s.equals(selectExportWaybillNoList.get(i))) {
				// 判断是否有打托
				if ("MAKE_WOODEN_STOCK".equals(map.get(s))) {
					// 打木包装类型
					printLabelBean.setPacking(i18n.get("foss.gui.creating.cargoInfoPanel.wood.buttonLabel"));
				} else {
					printLabelBean.setPacking(i18n.get("foss.gui.creating.cargoInfoPanel.wood.button"));
				}
				//将页面的流水号添加到printLabelBean
				printLabelBean.setPrintSerialnos(selectExportWaybillNoList.get(i));
				PrintUtil printUtil = new PrintUtil();
				LabelPrintContext ctx = printUtil.printWoodenLabelData(printLabelBean);
				try {
					LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_woodelReturnPrintWorker,ctx);
					UserEntity userEntity = (UserEntity) SessionContext
							.get(SessionContext.KEY_USER);
						GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
						printLabelEntity.setWaybillNo(printLabelBean
								.getWaybillNumber());
						printLabelEntity.setSerialNo(selectExportWaybillNoList.get(i));
						printLabelEntity.setPrintTime(new Date());
						printLabelEntity.setPrintUserCode(userEntity.getEmployee()
								.getEmpCode());
						printLabelEntity.setPrintUserName(userEntity.getEmployee()
								.getEmpName());
						printLabelEntity.setLableType(2);
						waybillService.addPrintLabelInfo(printLabelEntity);
				} catch (PrintException e1) {
					JOptionPane
							.showMessageDialog(
									null,
									i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"),
									i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
									JOptionPane.WARNING_MESSAGE);
				}
			}
			// 打木箱
		} else if (Integer.parseInt(selectExportWaybillNoList.get(i)) > printLabelBean.getStandGoodsNum()
				&& Integer.parseInt(selectExportWaybillNoList.get(i)) <= (num1+num2)) {
			// 判断页面取出的数据和循环拿出的数据一样（流水号）才打印
			if (StringUtils.isNotBlank(s) && s.equals(selectExportWaybillNoList.get(i))) {
				// 判断是否有打托
				if ("MAKE_WOODEN_STOCK".equals(map.get(s))) {
					// 打木包装类型
					printLabelBean
							.setPacking(i18n
									.get("foss.gui.creating.cargoInfoPanel.wood.buttonLabel.case"));
				} else {
					printLabelBean
							.setPacking(i18n
									.get("foss.gui.creating.cargoInfoPanel.wood.button.case"));
				}
				//将页面的流水号添加到printLabelBean
				printLabelBean.setPrintSerialnos(selectExportWaybillNoList.get(i));
				PrintUtil printUtil = new PrintUtil();
				LabelPrintContext ctx = printUtil
						.printWoodenLabelData(printLabelBean);
				try {
					LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_woodelReturnPrintWorker,ctx);
					UserEntity userEntity = (UserEntity) SessionContext
							.get(SessionContext.KEY_USER);
						GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
						printLabelEntity.setWaybillNo(printLabelBean
								.getWaybillNumber());
						printLabelEntity.setSerialNo(selectExportWaybillNoList.get(i));
						printLabelEntity.setPrintTime(new Date());
						printLabelEntity.setPrintUserCode(userEntity.getEmployee()
								.getEmpCode());
						printLabelEntity.setPrintUserName(userEntity.getEmployee()
								.getEmpName());
						printLabelEntity.setLableType(2);
						waybillService.addPrintLabelInfo(printLabelEntity);
				} catch (PrintException e1) {
					JOptionPane
							.showMessageDialog(
									null,
									i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"),
									i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
									JOptionPane.WARNING_MESSAGE);
				}
			}
			// 打木托
		} else {
			// 判断页面取出的数据和循环拿出的数据一样（流水号）才打印
			if (StringUtils.isNotBlank(s) && StringUtils.isNotBlank(s) && s.equals(selectExportWaybillNoList.get(i)) && "MAKE_WOODEN_STOCK".equals(map.get(s)) && ! (Integer.parseInt(selectExportWaybillNoList.get(i)) <= printLabelBean.getStandGoodsNum() && Integer.parseInt(selectExportWaybillNoList.get(i)) > printLabelBean.getStandGoodsNum() && Integer.parseInt(selectExportWaybillNoList.get(i)) <= (num1+num2))) {
				printLabelBean
						.setPacking(i18n
								.get("foss.gui.creating.cargoInfoPanel.wood.button.pallet"));
				//将页面的流水号添加到printLabelBean
				printLabelBean.setPrintSerialnos(selectExportWaybillNoList.get(i));
				PrintUtil printUtil = new PrintUtil();
				LabelPrintContext ctx = printUtil
						.printWoodenLabelData(printLabelBean);
				try {
					LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_woodelReturnPrintWorker,ctx);
					UserEntity userEntity = (UserEntity) SessionContext
							.get(SessionContext.KEY_USER);
						GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
						printLabelEntity.setWaybillNo(printLabelBean
								.getWaybillNumber());
						printLabelEntity.setSerialNo(selectExportWaybillNoList.get(i));
						printLabelEntity.setPrintTime(new Date());
						printLabelEntity.setPrintUserCode(userEntity.getEmployee()
								.getEmpCode());
						printLabelEntity.setPrintUserName(userEntity.getEmployee()
								.getEmpName());
						printLabelEntity.setLableType(2);
						waybillService.addPrintLabelInfo(printLabelEntity);
				} catch (PrintException e1) {
					JOptionPane
							.showMessageDialog(
									null,
									i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"),
									i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
									JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	@Override
	public void setIInjectUI(PrintSignUI ui) {
		this.ui = ui;
	}

}
