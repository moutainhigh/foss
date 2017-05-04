/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.print;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.PrintException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.PrintSignUI;
import com.deppon.foss.module.pickup.creating.client.ui.print.BarcodePrintDialog;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpDeliveryBigCustomerColumn;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GUIPrintLabelDto;
import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintManager;
import com.deppon.foss.print.labelprint.gui.LblPrtSetupWindow;
import com.deppon.foss.print.labelprint.service.LblPrtServiceConst;
import com.deppon.foss.print.labelprint.util.PropertiesUtil;
import com.deppon.foss.prt.PrintUtil;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author ibm-foss-sxw
 *
 */
public class ExpBarcodePrintDialog extends JDialog {

	private static final String COMMA = ",";

	private static final String FORMATSTR = "%04d";

	private static final int THREEZEROSEVEN = 307;

	private static final int FIVESEVENSIX = 576;

	private static final int SIX = 6;

	private static final int TEN = 10;

	private static final int THREEFIVEZERO = 350;

	private static final int SIXHUNDRED = 600;

	// 日志
	private static final Log LOG = LogFactory.getLog(ExpBarcodePrintDialog.class);

	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(ExpBarcodePrintDialog.class);

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -7959544878097277415L;

	private static final int NUM_110 = 110;

	private static final int NUM_340 = 340;

	private JTextField txtWaybillNo;

	// 收货人：收货联系人。
	private JTextField txtReceiver;
	
	//发货客户列
	private ExpDeliveryBigCustomerColumn deliveryBigCustomerColumn;
	
	private JLabel lblConsignee;

	private JTextField txtGoodsNum;

	private JTextField txtTargetOrg;

	private JTextField txtStartOrg;

	private JTextField txtTransTpye;
	private JTextField txtBeginGoodNum;
	private JTextField txtEndGoodNum;

	// 是否异形货物
	private JCheckBox chckbxNewCheckBox;

	// 文本区 流水号
	private JTextArea textArea;

	// 打印设置
	private JButton btnPrintConfigure;

	// 打印
	private JButton btnPrint;

	// 退出按钮
	private JButton btnExt;

	// 判断在线还是离线 true 在线 ，false 离线
	private Boolean isOnOrOffLIne;

	// 判断是第一次打印还是重打 true 标签打印 ，false 标签重打
	private Boolean isPrintOrRePrint = false;

	// 运输类型/运输性质 transtype[中文]

	// * 货物类型 goodstype[A/B/ ]
	// * 航班早班 preassembly[早班]
	private BarcodePrintLabelDto printLabelBean = new BarcodePrintLabelDto();
	
	private List<BarcodePrintLabelDto> printLabelBeans = new ArrayList<BarcodePrintLabelDto>();

	// 标签信息
	private List<LabeledGoodEntity> labeledlist;

	// 通过工厂类获得运单服务类
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	private String waybillstatus; //运单处理状态

	/**
	 * 构造方法
	 * 
	 * @param waybillNo
	 */
	public ExpBarcodePrintDialog(String waybillNo, String waybillstatus) {
		this();
		this.waybillstatus = waybillstatus;
		// 判断是否在线打印
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			isOnOrOffLIne = true;
		} else {
			isOnOrOffLIne = false;
		}
		// 初始化界面
		init();
		if (StringUtils.isNotEmpty(waybillNo)) {
			// 不为空 就是标签打印 否则就应该是重打 进行界面初始赋值 因为标签打印会传来流水号
			
			//如果运单相同，不再查询数据库，较少交互
			if(waybillNo.equals(printLabelBean.getWaybillNumber())){
				return;
				//这边如果运单号不一致这个就需要清理了，不然真的还是打印之前的
			}else if(CollectionUtils.isNotEmpty(printLabelBeans)){
				printLabelBeans.clear();
				printLabelBean = null;
			}
			// 判断是否存在货签信息数据
			// 先查询货签信息表中数据
			labeledlist = waybillService.queryAllSerialByWaybillNo(waybillNo);

			if (labeledlist == null || labeledlist.size() == 0) {
				// 离线或者暂存暂时打印
				printLabelBean = waybillService.getCommonLabelPrintInfoExpress(waybillNo,
						waybillstatus);
				// 如果没有就报错
				if (printLabelBean == null || StringUtils.isBlank(printLabelBean.getWaybillNumber())) {
					JOptionPane.showMessageDialog(null,i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
									i18n.get("foss.gui.creating.printSignUI.msgbox.error"),JOptionPane.WARNING_MESSAGE);
					return;
				}
				//先判定是否对应的总件数<0
				int totalPieces = Integer.parseInt(printLabelBean.getTotalPieces());
				if(totalPieces < 0){
					JOptionPane.showMessageDialog(null,i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
							i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					return;
				}
				//离线或者暂存的还是拼装号对应的货签信息流水号吧
				LabeledGoodEntity labelGoodTemp = null;
				labeledlist = new ArrayList<LabeledGoodEntity>();
				for(int i=1;i<=totalPieces;i++){
					labelGoodTemp = new LabeledGoodEntity();
					labelGoodTemp.setWaybillNo(printLabelBean.getWaybillNumber());//运单号
					labelGoodTemp.setSerialNo(String.format(FORMATSTR, i));//对应的流水号
					labeledlist.add(labelGoodTemp);//添加到货签信息数据里面
				}
				printLabelBeans.add(printLabelBean);
			} else {
				// 这里肯定是存在货签信息的状态，这时需要判断是否进行过目的站更改
				// 得到流水号
				List<String> serialNos = new ArrayList<String>();
				for (int i = 0; i < labeledlist.size(); i++) {
					serialNos.add(labeledlist.get(i).getSerialNo());
				}

				// 运单信息
				WaybillEntity waybillEntity = waybillService
						.queryWaybillByNumber(waybillNo);
				if(!CommonUtils.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())){
					MsgBox.showInfo(i18n.get("foss.gui.creatingexp.expPrintSignAction.MsgBox.notExpressProduct"));
					return;
				}
				// 判断是否存在更改单未受理情况
				String destinationAndTodo = waybillService
						.isExistDestinationAndTodoRfc(waybillEntity, serialNos);
				// destinationAndTodo为true表示所有都通过了，但是目的站可能未改变
				if ("WAY_UNDO".equals(destinationAndTodo)) {
					String xx = i18n.get("foss.gui.creating.printSignUI.msgbox.warming.tipbusiness");
					String xx1 = xx.replaceAll("XX", printLabelBeans.get(0).getDestination());
					// 线提示用户存在更改情况
					JOptionPane
							.showMessageDialog(
									null,
									xx1,
									i18n.get("foss.gui.creating.printSignUI.msgbox.warming.tipnext"),
									JOptionPane.WARNING_MESSAGE);
					return;
				}else if("WAY_BSE".equals(destinationAndTodo)){
					//增加异常的处理，如果综合查询走货路径报错，走中转的走货路径,如果还报错，那我就无能为力了
					try{
						printLabelBean = waybillService.getCommonLabelPrintInfoExpress(waybillNo,
								waybillstatus);
						// 如果没有就报错
						if (printLabelBean == null) {
							JOptionPane
									.showMessageDialog(
											null,
											i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
											i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
											JOptionPane.WARNING_MESSAGE);
							return;
						}
						printLabelBeans.add(printLabelBean);
					}catch (Exception e) {
						printLabelBeans = waybillService.getLabelPrintInfo(waybillNo, serialNos);
						// 如果没有就报错
						if (printLabelBeans == null) {
							JOptionPane
									.showMessageDialog(
											null,
											i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
											i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
											JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					
				}else{
					printLabelBeans = waybillService.getLabelPrintInfo(waybillNo, serialNos);
					// 如果没有就报错
					if (printLabelBeans == null) {
						JOptionPane
								.showMessageDialog(
										null,
										i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
										i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
										JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				addNumListener();
				printLabelBean = printLabelBeans.get(0);
			}
			initValue(printLabelBean);
			this.isPrintOrRePrint = true;
			LOG.info(isPrintOrRePrint);
		}
		addListener();
		this.setVisible(true);
	}

	/*
	 * // 第一次界面赋值 private void setValueForFirstTime(String waybillNo) { if
	 * (isOnOrOffLIne) {
	 * initValue(this.getPrintBeanDetailInfoOnLine(waybillNo)); } else {
	 * initValue(this.getPrintBeanDetailInfoOffLine(waybillNo)); } }
	 */

	public ExpBarcodePrintDialog() {

	}

	private Boolean validateNum() {
		if (StringUtils.isEmpty(txtBeginGoodNum.getText()) || Integer.parseInt(txtBeginGoodNum.getText()) <= 0) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.MessageDialog.startingNum.nullException"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (StringUtils.isEmpty(txtEndGoodNum.getText())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.MessageDialog.endGoodNum.nullException"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (StringUtils.isEmpty(txtGoodsNum.getText())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.MessageDialog.goodsNum.nullException"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (Integer.parseInt(txtBeginGoodNum.getText()) > Integer.parseInt(txtEndGoodNum.getText())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.MessageDialog.outofEndGoodsNum.exception"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (Integer.parseInt(txtEndGoodNum.getText()) > Integer.parseInt(txtGoodsNum.getText())) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.MessageDialog.outofGoodsNum.exception"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else {
			return true;
		}
	}

	private void addNumListener() {

		// 联动监听
		txtBeginGoodNum.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (validateNum()) {
					StringBuffer tempAreaInfo = new StringBuffer("");
					
					for (int i = Integer.parseInt(txtBeginGoodNum.getText()) - 1; i < Integer.parseInt(txtEndGoodNum.getText()); i++) {
						//删除运单为暂存状态的情况
						if (isOnOrOffLIne && !WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(waybillstatus)) {
							// 在线操作
							// "%04d" means "%0" + 4 + "d"
							tempAreaInfo.append(String.format(FORMATSTR, Integer.parseInt(labeledlist.get(i).getSerialNo()))).append(COMMA);
						} else {
							// 离线操作
							tempAreaInfo.append(String.format(FORMATSTR, i + 1)).append(COMMA);
						}

					}
					textArea.setText(subStringAreaInfo(tempAreaInfo));
				}
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});

		txtEndGoodNum.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (validateNum()) {
					// 文本区的赋值 补成4位的
					// 补打的情况
					StringBuffer tempAreaInfo = new StringBuffer("");
					for (int i = Integer.parseInt(txtBeginGoodNum.getText()) - 1; i < Integer.parseInt(txtEndGoodNum.getText()); i++) {
						if (isOnOrOffLIne && !WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(waybillstatus)) {
							// 在线操作
							// "%04d" means "%0" + 4 + "d"
							tempAreaInfo.append(String.format(FORMATSTR, Integer.parseInt(labeledlist.get(i).getSerialNo()))).append(COMMA);
						} else {
							// 离线操作
							tempAreaInfo.append(String.format(FORMATSTR, i + 1)).append(COMMA);

						}
					}
					textArea.setText(subStringAreaInfo(tempAreaInfo));
				}
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});
	}

	/**
	 * <p>
	 * 事件监听
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-29 上午10:46:46
	 * @see
	 */
	private void addListener() {
		txtWaybillNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				initValue(printLabelBean);
			}

		});
		txtWaybillNo.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				//运单为空
				String waybillNo = txtWaybillNo.getText();
				if(StringUtil.isBlank(waybillNo)){
					JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					return;
				}
				//如果运单相同，不再查询数据库，较少交互
				if(printLabelBean != null && waybillNo.equals(printLabelBean.getWaybillNumber())){
					return;
					//这边如果运单号不一致这个就需要清理了，不然真的还是打印之前的
				}else if(CollectionUtils.isNotEmpty(printLabelBeans)){
					printLabelBeans.clear();
				}
				if(CollectionUtils.isNotEmpty(labeledlist)){
					labeledlist.clear();
				} 
				// 判断是否存在货签信息数据
				// 先查询货签信息表中数据
				labeledlist = waybillService.queryAllSerialByWaybillNo(waybillNo);

				// 这里存在暂存/离线没有货签信息
				if (labeledlist == null || labeledlist.size() == 0) {
					// 离线或者暂存暂时打印
					printLabelBean = waybillService.getCommonLabelPrintInfoExpress(waybillNo,
							waybillstatus);
					// 如果没有就报错
					if (printLabelBean == null || StringUtils.isBlank(printLabelBean.getWaybillNumber())) {
						JOptionPane.showMessageDialog(null,i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
										i18n.get("foss.gui.creating.printSignUI.msgbox.error"),JOptionPane.WARNING_MESSAGE);
						return;
					}
					//先判定是否对应的总件数<0
					int totalPieces = Integer.parseInt(printLabelBean.getTotalPieces());
					if(totalPieces < 0){
						JOptionPane.showMessageDialog(null,i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
								i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
						return;
					}
					//还是拼装号对应的货签信息流水号吧
					LabeledGoodEntity labelGoodTemp = null;
					labeledlist = new ArrayList<LabeledGoodEntity>();
					for(int i=1;i<=totalPieces;i++){
						labelGoodTemp = new LabeledGoodEntity();
						labelGoodTemp.setWaybillNo(printLabelBean.getWaybillNumber());//运单号
						labelGoodTemp.setSerialNo(String.format(FORMATSTR, i));//对应的流水号
						labeledlist.add(labelGoodTemp);//添加到货签信息数据里面
					}
					printLabelBeans.add(printLabelBean);
				} else {
					// 这里肯定是存在货签信息的状态，这时需要判断是否进行过目的站更改
					// 得到流水号
					List<String> serialNos = new ArrayList<String>();
					for (int i = 0; i < labeledlist.size(); i++) {
						serialNos.add(labeledlist.get(i).getSerialNo());
					}

					// 运单信息
					WaybillEntity waybillEntity = waybillService.queryWaybillByNumber(labeledlist.get(0).getWaybillNo());
					//是否为经济快递的运单号
					if(!CommonUtils.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())){
						MsgBox.showInfo(i18n.get("foss.gui.creatingexp.expPrintSignAction.MsgBox.notExpressProduct"));
						printLabelBeans.clear();
						printLabelBean = null;
						initPanelData();
						return;
					}
					// 判断是否存在更改单未受理情况
					String destinationAndTodo = waybillService
							.isExistDestinationAndTodoRfc(waybillEntity, serialNos);
					// destinationAndTodo为true表示所有都通过了，但是目的站可能未改变
					if ("WAY_UNDO".equals(destinationAndTodo)) {
						String xx = i18n
								.get("foss.gui.creating.printSignUI.msgbox.warming.tipbusiness");
						String xx1 = xx.replaceAll("XX", printLabelBeans.get(0)
								.getDestination());
						// 线提示用户存在更改情况
						JOptionPane
								.showMessageDialog(
										null,
										xx1,
										i18n.get("foss.gui.creating.printSignUI.msgbox.warming.tipnext"),
										JOptionPane.WARNING_MESSAGE);
						return;
					}else if("WAY_BSE".equals(destinationAndTodo)){
						//增加异常的处理，如果综合查询走货路径报错，走中转的走货路径,如果还报错，那我就无能为力了
						try{
							printLabelBean = waybillService.getCommonLabelPrintInfoExpress(waybillNo,waybillstatus);
							// 如果没有就报错
							if (printLabelBean == null) {
								JOptionPane
										.showMessageDialog(
												null,
												i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
												i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
												JOptionPane.WARNING_MESSAGE);
								return;
							}
							printLabelBeans.add(printLabelBean);
						}catch (Exception e1) {
							printLabelBeans = waybillService.getLabelPrintInfo(waybillNo, serialNos);
							// 如果没有就报错
							if (printLabelBeans == null) {
								JOptionPane
										.showMessageDialog(
												null,
												i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
												i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
												JOptionPane.WARNING_MESSAGE);
								return;
							}
						}
						
					}else{
						printLabelBeans = waybillService.getLabelPrintInfo(waybillNo, serialNos);
						// 如果没有就报错
						if (printLabelBeans == null) {
							JOptionPane
									.showMessageDialog(
											null,
											i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"),
											i18n.get("foss.gui.creating.printSignUI.msgbox.error"),
											JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					
					// 加上起始件数 结束件数失去焦点监听事件
					addNumListener();
					printLabelBean = printLabelBeans.get(0);
				}
				initValue(printLabelBean);
				isPrintOrRePrint = true;
				LOG.info(isPrintOrRePrint);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		// 打印按钮
		btnPrint.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (StringUtils.isEmpty(txtWaybillNo.getText()) || StringUtils.isEmpty(printLabelBean.getWaybillNumber())) {
					JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.MessageDialog.canNotPrint.Exception"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.error"), JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					// 异常货物 标识 "异"
//					printLabelBean.setUnusual(chckbxNewCheckBox.isSelected() ? i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.different") : "");
					// WaybillEntity waybillEntity =
					// waybillService.queryWaybillByNumber(printLabelBean.getWaybillNumber());
					
					//获取所有的流水号
					String[] seriesNo = textArea.getText().split(COMMA);
					if(seriesNo.length == 0){
						//输入截止件数已获得流水号
						JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.endGoodNum.toolTipText"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					UserEntity userEntity = (UserEntity)SessionContext.get(SessionContext.KEY_USER);
					if(CollectionUtils.isEmpty(printLabelBeans)){
						JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.waybillOffLinePendingService.exception.waybillCheck.one"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.inputError"), JOptionPane.WARNING_MESSAGE);
						return;
					}
					//备注：当前只是针对走货路径是综合的设置的，因为综合没有返回流水号，需要自己设置
					if("".equals(printLabelBeans.get(0).getPrintSerialnos()) || null == printLabelBeans.get(0).getPrintSerialnos()){
						printLabelBean.setPrintSerialnos(textArea.getText());
						// 异常货物 标识 "异"
						printLabelBean.setUnusual(chckbxNewCheckBox.isSelected() ? i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.different") : "");
						//设置单个流水号
						PrintUtil printUtil = new PrintUtil();
						LabelPrintContext ctx = printUtil.printLabelData(printLabelBean);
						try {
							LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_ExpCommLabelPrintWorker, ctx);
							for (String  serieNo : seriesNo) {
								//记录打印日志
								GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
								printLabelEntity.setWaybillNo(txtWaybillNo.getText());
								printLabelEntity.setSerialNo(serieNo);
								printLabelEntity.setPrintTime(new Date());
								printLabelEntity.setPrintUserCode(userEntity.getEmployee().getEmpCode());
								printLabelEntity.setPrintUserName(userEntity.getEmployee().getEmpName());
								waybillService.addPrintLabelInfo(printLabelEntity);
							}
						} catch (PrintException e2) {
							JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
						}
						
						/**
						 * KDTE-4773标签打印异常
						 * 
						 * 打完一次后将该值清空，防止走else里的逻辑
						 */
						printLabelBean.setPrintSerialnos(null);
					}else{
						//根据流水号跟后台查出的数据进行比较，如果匹配正确，则进行打印,因为每一个流水号所对应外场走货路径有可能是不一样的。 
						BarcodePrintLabelDto bean = null;
						for(int i = 0; i<seriesNo.length;i++){
							for(int j = 0;j<printLabelBeans.size();j++){
								bean = printLabelBeans.get(j);
								if(seriesNo[i].equals(bean.getPrintSerialnos())){
									// 异常货物 标识 "异"
									bean.setUnusual(chckbxNewCheckBox.isSelected() ? i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.different") : "");
									//设置单个流水号
									bean.setPrintSerialnos(seriesNo[i]);
									PrintUtil printUtil = new PrintUtil();
									LabelPrintContext ctx = printUtil.printLabelData(bean);
									try {
										LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_ExpCommLabelPrintWorker, ctx);
										//记录打印日志
										GUIPrintLabelDto printLabelEntity = new GUIPrintLabelDto();
										printLabelEntity.setWaybillNo(txtWaybillNo.getText());
										printLabelEntity.setSerialNo(seriesNo[i]);
										printLabelEntity.setPrintTime(new Date());
										printLabelEntity.setPrintUserCode(userEntity.getEmployee().getEmpCode());
										printLabelEntity.setPrintUserName(userEntity.getEmployee().getEmpName());
										waybillService.addPrintLabelInfo(printLabelEntity);
										//MsgBox.showInfo(i18n.get("foss.gui.creating.printSignUI.msgbox.printSuccess"));
									} catch (PrintException e2) {
										JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
									}
									continue;
								}else{
									
								}
							}
						}
					}
					MsgBox.showInfo(i18n.get("foss.gui.creating.printSignUI.msgbox.printSuccess"));
//					printLabelBean.setPrintSerialnos(textArea.getText());
//
//					PrintUtil printUtil = new PrintUtil();
//					LabelPrintContext ctx = printUtil.printLabelData(printLabelBean);
//					try {
//						LabelPrintManager.doLabelPrintAction(LblPrtServiceConst.key_lblprt_program_CommLabelPrintWorker, ctx);
//						MsgBox.showInfo(i18n.get("foss.gui.creating.printSignUI.msgbox.printSuccess"));
//					} catch (PrintException e2) {
//						JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.printerNotConnected"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
//					}

				} catch (Exception exp) {

					LOG.error(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.printException") + exp.toString(), exp);
					JOptionPane.showMessageDialog(null, exp.toString(), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		// 打印设置
		btnPrintConfigure.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// print label setup
				try {
					PropertiesUtil.initProperties();
					LblPrtSetupWindow setupwindow = LblPrtSetupWindow.getInstance();
					setupwindow.openWindow();
				} catch (Exception exp) {

					LOG.error(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.printSetException") + exp.toString(), exp);
				}
			}
		});
		btnExt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				dispose();
			}
		});
		addNumListener();
	}

	/**
	 * 清除界面上所有信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-10-2 13:43:11
	 */
	private void initPanelData() {
		txtWaybillNo.setText("");
		txtReceiver.setText("");
		lblConsignee.setIcon(CommonUtils.createIcon(PrintSignUI.class, "", 1, 1));
		txtTargetOrg.setText("");
		txtStartOrg.setText("");
		txtTransTpye.setText("");
		txtBeginGoodNum.setText("");
		txtGoodsNum.setText("");
		txtEndGoodNum.setText("");
		chckbxNewCheckBox.setSelected(false);
		textArea.setText("");
	}
	
	/**
	 * 截取流水号
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-23 下午2:59:40
	 */
	private String subStringAreaInfo(StringBuffer tempAreaInfo) {
		String resultString = "";
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
				serialnos.append(String.format(FORMATSTR, i)).append(COMMA);
			}
		}
		return serialnos;
	}

	/**
	 * 获得流水号 字符串
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-23 下午2:59:23
	 */
	public String getSerialNos(String waybillNo) {
		StringBuffer tempAreaInfo = new StringBuffer("");
		if (isOnOrOffLIne && !WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(waybillstatus)) {
			// 在线流水号
//			labeledlist = waybillService.queryAllSerialByWaybillNo(waybillNo);
			if (CollectionUtils.isEmpty(labeledlist)) {
				return "";
			}
			// 文本区的赋值 补成4位的
			for (int i = 0; i < labeledlist.size(); i++) {
				tempAreaInfo.append(labeledlist.get(i).getSerialNo()).append(COMMA);
			}
		} else {
			// 离线流水号
			tempAreaInfo = getPrintSerialnos(Integer.parseInt(txtGoodsNum.getText()));
		}
		return subStringAreaInfo(tempAreaInfo);
	}

	/**
	 * 界面初始化
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-24 下午5:31:25
	 */
	private void initValue(BarcodePrintLabelDto printLabelBean) {
		if (printLabelBean.getWaybillNumber() == null) {
			JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.waybillNotExist.error"), i18n.get("foss.gui.creating.barcodePrintDialog.msgbox.error"), JOptionPane.WARNING_MESSAGE);
			// return;
		}
		// 运单号
		txtWaybillNo.setText(printLabelBean.getWaybillNumber());
		// 收货人：收货联系人。
		txtReceiver.setText(printLabelBean.getReceiveCustomerContact());
		//大客户标记
		if(FossConstants.ACTIVE.equals(printLabelBean.getReceiveBigCustomer())){
			// 收货大客户
			lblConsignee.setIcon(CommonUtils.createIcon(BarcodePrintDialog.class,IconConstants.BIG_CUSTOMER, 1, 1));
		}else{
			// 收货大客户
			lblConsignee.setIcon(CommonUtils.createIcon(BarcodePrintDialog.class,"", 1, 1));
		}
		
		//增加货签信息与运单件数的校验
		if(Integer.parseInt(printLabelBean.getTotalPieces())>labeledlist.size()){
			// 件数
			txtGoodsNum.setText(String.valueOf(labeledlist.size()));
			// 结束件数
			txtEndGoodNum.setText(String.valueOf(labeledlist.size()));
		}else{
			// 件数
			txtGoodsNum.setText(printLabelBean.getTotalPieces());
			// 结束件数
			txtEndGoodNum.setText(printLabelBean.getTotalPieces());
		}
		// 件数
		txtGoodsNum.setText(printLabelBean.getTotalPieces());
		// 目的站
		txtTargetOrg.setText(printLabelBean.getDestination());
		// 始发站
		txtStartOrg.setText(printLabelBean.getLeavecity());
		// 运输性质
		txtTransTpye.setText(printLabelBean.getTranstype());
		// 开始件数
		txtBeginGoodNum.setText("1");
		
		// 是否异形
		chckbxNewCheckBox.setSelected(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.different").equals(printLabelBean.getUnusual()) ? true : false);
		// 流水号
		textArea.setText(StringUtils.isEmpty(printLabelBean.getWaybillNumber()) ? "" : getSerialNos(printLabelBean.getWaybillNumber()));
	}

	/**
	 * <p>
	 * (界面初始化)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-29 上午10:46:43
	 * @see
	 */
	private void init() {
		setTitle(i18n.get("foss.gui.creating.barcodePrintDialog.title"));
		setSize(SIXHUNDRED, THREEFIVEZERO);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);

		JPanel panel1 = new JPanel();
		panel1.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(25dlu;default):grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(39dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(64dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(84dlu;default):grow"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(33dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(24dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(13dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(70dlu;default)"), }));
		// 运单号
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.barcodePrintDialog.waybillNo.label") + "：");
		panel1.add(lblNewLabel, "2, 2, right, default");

		txtWaybillNo = new JTextField();
		panel1.add(txtWaybillNo, "4, 2, fill, default");
		txtWaybillNo.setColumns(TEN);
		txtWaybillNo.setDocument(new NumberDocument(TEN));
		// 收货人
		lblConsignee = new JLabel(i18n.get("foss.gui.creating.barcodePrintDialog.consignee.label") + "：");
		panel1.add(lblConsignee, "6, 2, right, default");

		txtReceiver = new JTextField();
		panel1.add(txtReceiver, "8, 2, fill, default");
		txtReceiver.setColumns(TEN);

		JPanel panel2 = new JPanel();
		panel1.add(panel2, "10, 2, 1, 5");
		panel2.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(13dlu;default)"), }));
		// 起始件数库号
		JLabel lblNewLabel6 = new JLabel(i18n.get("foss.gui.creating.barcodePrintDialog.startingNum.label") + "：");
		panel2.add(lblNewLabel6, "2, 2");
		// 截至件数库号
		JLabel lblNewLabel7 = new JLabel(i18n.get("foss.gui.creating.barcodePrintDialog.endNum.label") + "：");
		panel2.add(lblNewLabel7, "8, 2");

		txtBeginGoodNum = new JTextField();
		panel2.add(txtBeginGoodNum, "2, 4, fill, default");
		txtBeginGoodNum.setColumns(TEN);
		// 输入起始件数回车获得对应流水号
		txtBeginGoodNum.setToolTipText(i18n.get("foss.gui.creating.barcodePrintDialog.beginGoodNum.toolTipText"));
		txtBeginGoodNum.setDocument(new NumberDocument(SIX));

		txtEndGoodNum = new JTextField();
		panel2.add(txtEndGoodNum, "8, 4, fill, default");
		txtEndGoodNum.setColumns(TEN);
		// 输入截止件数回车获得对应流水号
		txtEndGoodNum.setToolTipText(i18n.get("foss.gui.creatingbarcodePrintDialog.endGoodNum.toolTipText"));
		txtEndGoodNum.setDocument(new NumberDocument(SIX));

		// JLabel lblNewLabel8 new JLabel 补打件数
		// 异形货物
		chckbxNewCheckBox = new JCheckBox(i18n.get("foss.gui.creating.barcodePrintDialog.alienCargo.label"));
		panel2.add(chckbxNewCheckBox, "8, 6");
		// 件数
		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.creating.barcodePrintDialog.goodsNum.label") + "：");
		panel1.add(lblNewLabel2, "2, 4, right, default");

		txtGoodsNum = new JTextField();
		panel1.add(txtGoodsNum, "4, 4, fill, default");
		txtGoodsNum.setColumns(TEN);
		// 目的站
		JLabel lblNewLabel3 = new JLabel(i18n.get("foss.gui.creating.barcodePrintDialog.destinationStation.label") + "：");
		panel1.add(lblNewLabel3, "6, 4, right, default");

		txtTargetOrg = new JTextField();
		panel1.add(txtTargetOrg, "8, 4, fill, default");
		txtTargetOrg.setColumns(TEN);
		// 始发站
		JLabel lblNewLabel4 = new JLabel(i18n.get("foss.gui.creating.barcodePrintDialog.originatingStation.label") + "：");
		panel1.add(lblNewLabel4, "2, 6, right, default");

		txtStartOrg = new JTextField();
		panel1.add(txtStartOrg, "4, 6, fill, default");
		txtStartOrg.setColumns(TEN);

		// 运输性质
		JLabel lblNewLabel5 = new JLabel(i18n.get("foss.gui.creating.barcodePrintDialog.transportNature.label") + "：");
		panel1.add(lblNewLabel5, "6, 6, right, default");

		txtTransTpye = new JTextField();
		panel1.add(txtTransTpye, "8, 6, fill, default");
		txtTransTpye.setColumns(TEN);
		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGap(SIX).addComponent(panel1, GroupLayout.DEFAULT_SIZE, FIVESEVENSIX, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGap(7).addComponent(panel1, GroupLayout.DEFAULT_SIZE, THREEZEROSEVEN, Short.MAX_VALUE).addContainerGap()));
		// 最终打印流水号
		JLabel lblNewLabel10 = new JLabel(i18n.get("foss.gui.creating.barcodePrintDialog.printSerialNum.label") + "：");
		panel1.add(lblNewLabel10, "2, 8, 3, 1");

		textArea = new JTextArea();
		textArea.setRows(SIX);
		// 设置自动换行。
		textArea.setLineWrap(true);
		textArea.setSize(NUM_340, NUM_110);
		JScrollPane jScrollPane = new JScrollPane(textArea);

		panel1.add(jScrollPane, "2, 10, 7, 1");

		JPanel panel3 = new JPanel();
		panel1.add(panel3, "9, 10, 2, 1, fill, fill");
		panel3.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(41dlu;default)"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));
		// 打印
		btnPrintConfigure = new JButton(i18n.get("foss.gui.creating.barcodePrintDialog.printSettings.buttonText"));
		panel3.add(btnPrintConfigure, "8, 4");
		// 打印设置
		btnPrint = new JButton(i18n.get("foss.gui.creating.barcodePrintDialog.print.buttonText"));
		panel3.add(btnPrint, "8, 6");
		// 退出
		btnExt = new JButton(i18n.get("foss.gui.creating.barcodePrintDialog.quit.buttonText"));
		panel3.add(btnExt, "8, 8");
		panel.setLayout(groupLayout);
		txtReceiver.setEditable(false);
		txtGoodsNum.setEditable(false);
		txtTargetOrg.setEditable(false);
		txtStartOrg.setEditable(false);
		txtTransTpye.setEditable(false);
		textArea.setEditable(false);
		setModal(true);
	}

	/**
	 * <p>
	 * Date 转成 yy-MM-dd HH:mm:ss格式
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-28 下午4:49:39
	 * @param date
	 * @return
	 * @see
	 */
	@SuppressWarnings("finally")
	public static String getDate(Date date) {
		String str = "";
		SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd");
		try {
			str = sf.format(date);

		} catch (Exception e) {
			LOG.error(i18n.get("foss.gui.creating.ui.print.SignLabelPrintDialog.chinese.printConvertDateException"), e);
		} finally {
			return str;
		}
	}

}
