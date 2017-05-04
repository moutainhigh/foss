/**  
 * Project Name:pkp-creatingexp  
 * File Name:ExpReturnGoodsApplyAction.java  
 * Package Name:com.deppon.foss.module.pickup.creatingexp.client.action  
 * Date:2015-2-5下午2:51:05  
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.  
 *  
 */

package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.JAddressField;
import com.deppon.foss.module.pickup.common.client.utils.LetterDocument;
import com.deppon.foss.module.pickup.common.client.utils.MobileDocument;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpReturnedGoodsWaybillUI;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IAddressServiceHessianRemoting;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Injector;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * ClassName:ExpReturnGoodsApplyAction <br/>
 * Function: 点击新建按钮的功能. <br/>
 * Date: 2015-2-5 下午2:51:05 <br/>
 * 
 * @author 146831 刘俏
 * @version
 * @since JDK 1.6
 * @see
 */
public class ExpReturnGoodsApplyAction extends
		AbstractButtonActionListener<ExpReturnedGoodsWaybillUI> {
	
	public static final int _TEN = 10;
	
	public static final int ELEVEN = 11;
	
	private static final int NUM_750 = 750;
	
	private static final int NUM_473 = 473;
	
	private static final int NUM_157 = 157;
	
	private static final int NUM_185 = 185;
	
	private static final int NUM_127 = 127;
	
	private static final int TEN = 10;
	
	private static final int FIVE = 5;
	
	private static final int NINE = 9;
	
	// 这个是注入的主面板，就是这个button所在的面板
	@SuppressWarnings("unused")
	private ExpReturnedGoodsWaybillUI ui;
	// 日志记录器，如有日志显示会打印出这个类的全路径名，方便定位和跟踪日志
	public static Logger logger = LoggerFactory
			.getLogger(ExpReturnGoodsApplyAction.class);
	// 国际化对象
	private static final II18n i18n = I18nManager
			.getI18n(ExpReturnedGoodsWaybillUI.class);
	// 申请事由里面允许输入的最多的汉字为500个，就是1000个字符了
	public static final int _THOUSAND = 1000;
	
	// 这个是点击新建的时候弹出来的对话框
	public JDialog jDialog = new JDialog();
	// 下面这个文本框 用于输入运单号
	public JTextFieldValidate textField;
	// 下面这个框用于输入申请事由
	public LimitNumTextArea textArea;
	// 标签 运单号
	public JLabel lblNewLabel;
	// 标签 申请事由
	public JLabel label_1;
	// 标签 最多不超过500字
	public JLabel label_2;
	//标签  该单号已上报返货工单，请勿重复上报！
	public JLabel label_tip;
	// 提交按钮
	public JButton btnNewButton_1;//提交
	// 返回按钮
	public JButton btnNewButton;
	// 查询运单信息的service
	public IWaybillService waybillService = WaybillServiceFactory
			.getWaybillService();
	//查询区域 hessian接口
	private IAddressServiceHessianRemoting onlineAddressServiceHessianRemoting = DefaultRemoteServiceFactory.getService(IAddressServiceHessianRemoting.class);
	private WaybillEntity waybillEntity;
	public String UIStatus = "1";
	private JPanel panel;
	private JLabel label_3;
	private JComboBox comboBox;
	private JLabel lblNewLabel_3;
	private JComboBox comboBox_1;
	private JLabel lblNewLabel_4;
	private JTextField textField_4;
	private JTextField textField_5;
	private JPanel panel_1;
	private JLabel lblNewLabel_5;
	private JTextField textField_6;
	private JLabel lblNewLabel_6;
	private JTextField textField_7;
	private JLabel label_4;
	private JTextField textField_8;
	private JLabel label_5;
	private JLabel label_6;
	private JTextField textField_10;
	private JLabel label_7;
	private JTextField textField_11;
	private JAddressField textField_9;
	private JXTextField textField_12;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblNewLabel_1;//返货方式
	private JComboBox comboBox_2;//返货方式
	
	private JLabel label_r;
	
	private JComboBox comboBox_r;
	
	// 注入这个ui
	@Override
	public void setIInjectUI(ExpReturnedGoodsWaybillUI ui) {
		this.ui = ui;
	}

	// 重写这个action方法
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// 初始化界面
		this.initDialog();
		initComboBox();
		// 添加监听器
		this.addListener();
		//jDialog.setUndecorated(true);
		jDialog.setModal(true);
		WindowUtil.centerAndShow(jDialog);
	}

	// 添加监听器 当文本框里面输入数值之后点击回车键的时候执行这个方法
	private void addListener() {
		// 首先给运单号文本框添加监听器
		textField.addKeyListener(new KeyAdapter() {
			@Override
			// 检查用户是否输入的回车键
			public void keyReleased(KeyEvent e) {
				
				
				
				// 如果不是回车键，直接的跳过
				if (e.getKeyCode() != Event.ENTER) {
					return;
				}
				// 取出输入的运单号
				String waybillNo = textField.getText().trim();
				DataDictionaryValueVo returnType = (DataDictionaryValueVo)comboBox_1.getSelectedItem();
				
				// 判断非空
				if (StringUtil.isEmpty(waybillNo)) {
					// 如果为空，则弹出对话框
					JOptionPane.showMessageDialog(
							jDialog,
							i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.NoWayBillNo"),
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
							JOptionPane.INFORMATION_MESSAGE);
					// 置空
					reSet();
					return;
					// 若果非空，则查询相关的信息到
				} 
				/*else if(StringUtil.isNotEmpty(waybillNo) && 
						WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE.equals(returnType.getValueCode())){*/
				TwoInOneWaybillDto twoInOneWaybillDto=waybillService.queryWaybillRelateByWaybillOrOrderNo(waybillNo);
				 if(WaybillConstants.RETURN_BACK.equals(returnType.getValueCode())||
							WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE.equals(returnType.getValueCode())||
							WaybillConstants.RETURNTYPE_SAME_CITY.equals(returnType.getValueCode())||
							WaybillConstants.RETURNTYPE_OTHER_CITY.equals(returnType.getValueCode())){
					// 查询运单信息
					 waybillEntity = waybillService
							.queryWaybillByNumber(waybillNo);
					if (null == waybillEntity) {
						// 置空
						reSet();
						JOptionPane.showMessageDialog(
								jDialog,
								i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.NoWayBillInfo"),
								i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
								JOptionPane.INFORMATION_MESSAGE);
						return;
					} else {
						//配合悟空做灰度，限制悟空单在FOSS创建转寄退回--zoushengli
						if("Y".equals(waybillEntity.getIsecs())){
							// 如果是悟空运单，运单号置为空，其他信息置空，重新输入运单号并获取焦点 151211 yth
							textField.setText("");
							reSet();
							textField.requestFocus();
							// 如果为空，则弹出对话框
							JOptionPane.showMessageDialog(
									jDialog,
									i18n.get("foss.pkp.waybill.waybillManagerService.exception.waybillIsECSChanged"),
									i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						
						
						if(waybillService.queryReturnGoodsRequestEntityByCondition(waybillNo)){
							reSet();
							label_tip.setText(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.Rehandled"));
							btnNewButton_1.setEnabled(false);
							return;
						}else{
							reSet();
							label_tip.setText("");
							btnNewButton_1.setEnabled(true);
							oldWaybillNoAddress(waybillEntity);
							customerRefuse(waybillEntity,twoInOneWaybillDto.getIsTwoInOne());
						}
					}
				}
				
				
				if(StringUtils.equals(twoInOneWaybillDto.getIsTwoInOne(), FossConstants.ACTIVE)){
				 
					comboBox_2.setEnabled(true);
					textField_7.setEnabled(true);
					
					
				}else{
					//判断如果是非字母件就不能编辑
					comboBox_2.setEnabled(false);
					textField_7.setEnabled(false);
					comboBox_2.removeAllItems();
				}
			}
		});

		// 添加失焦点事件
		textField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				// 如果是界面销毁之后调用的这个监听器，直接忽略掉
				if ("2".equals(UIStatus)) {
					return;
				}
				label_tip.setText("");
				// 取出输入的运单号
				String waybillNo = textField.getText();
				DataDictionaryValueVo returnType = (DataDictionaryValueVo)comboBox_1.getSelectedItem();
				// 判断非空
				if (StringUtil.isEmpty(waybillNo)) {
					// 如果为空，则弹出对话框
					JOptionPane.showMessageDialog(
							jDialog,
							i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.NoWayBillNo"),
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
							JOptionPane.INFORMATION_MESSAGE);
					// 置空
					reSet();
					return;
					// 若果非空，则查询相关的信息到
				}  
					TwoInOneWaybillDto twoInOneWaybillDto=waybillService.queryWaybillRelateByWaybillOrOrderNo(waybillNo);
					if(WaybillConstants.RETURN_BACK.equals(returnType.getValueCode())||
							WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE.equals(returnType.getValueCode())||
							WaybillConstants.RETURNTYPE_SAME_CITY.equals(returnType.getValueCode())||
							WaybillConstants.RETURNTYPE_OTHER_CITY.equals(returnType.getValueCode())){
					// 查询运单信息
					 waybillEntity = waybillService.queryWaybillByNumber(waybillNo);
					if (null == waybillEntity) {
						// 置空
						reSet();
						JOptionPane.showMessageDialog(
								jDialog,
								i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.NoWayBillInfo"),
								i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
								JOptionPane.INFORMATION_MESSAGE);
						return;
					} else {
						//配合悟空做灰度，限制悟空单在FOSS创建转寄退回--zoushengli
						if("Y".equals(waybillEntity.getIsecs())){
							// 如果是悟空运单，运单号置为空，其他信息置空，重新输入运单号并获取焦点 151211 yth
							textField.setText("");
							reSet();
							textField.requestFocus();
							// 如果为空，则弹出对话框
							JOptionPane.showMessageDialog(
									jDialog,
									i18n.get("foss.pkp.waybill.waybillManagerService.exception.waybillIsECSChanged"),
									i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						
						if(waybillService.queryReturnGoodsRequestEntityByCondition(waybillNo)){
							reSet();
							label_tip.setText(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.Rehandled"));
							btnNewButton_1.setEnabled(false);
							return;
						}else{
							reSet();
							label_tip.setText("");
							btnNewButton_1.setEnabled(true);
							oldWaybillNoAddress(waybillEntity);
							customerRefuse(waybillEntity,twoInOneWaybillDto.getIsTwoInOne());
						}
					}
					
					if(StringUtils.equals(twoInOneWaybillDto.getIsTwoInOne(), FossConstants.ACTIVE)){
						 
						comboBox_2.setEnabled(true);
						textField_7.setEnabled(true);
						
						
					}else{
						//判断如果是非字母件就不能编辑
						comboBox_2.setEnabled(false);
						textField_7.setEnabled(false);
						comboBox_2.removeAllItems();
					}
					
				}
			}
		});
		//--
		//给返货类型添加监听
		comboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				DataDictionaryValueVo returnType = (DataDictionaryValueVo)comboBox.getSelectedItem();
				initReasonsMode(returnType);
			}
			
		});
		
		//--
		
		
		
		//给返货类型添加监听
		comboBox_1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				DataDictionaryValueVo returnType = (DataDictionaryValueVo)comboBox_1.getSelectedItem();
				String waybillNo = textField.getText();	
				// 判断非空
				if (StringUtil.isEmpty(waybillNo)) {
					// 如果为空，则弹出对话框
					JOptionPane.showMessageDialog(
							jDialog,
							i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.NoWayBillNo"),
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
							JOptionPane.INFORMATION_MESSAGE);
					// 置空
					reSet();
					return;
					// 若果非空，则查询相关的信息到
				}  
					TwoInOneWaybillDto twoInOneWaybillDto=waybillService.queryWaybillRelateByWaybillOrOrderNo(waybillNo);
					// //客户拒收\非同城转寄\同城转寄
					if (WaybillConstants.RETURN_BACK.equals(returnType.getValueCode())||
							WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE.equals(returnType.getValueCode())||
							WaybillConstants.RETURNTYPE_SAME_CITY.equals(returnType.getValueCode())||
							WaybillConstants.RETURNTYPE_OTHER_CITY.equals(returnType.getValueCode())
							) {
						oldWaybillNoAddress(waybillEntity);
						customerRefuse(waybillEntity,twoInOneWaybillDto.getIsTwoInOne());
						textField_7.setEnabled(false);
						// 外发3天返货 和 7天返货
					} else {
						textField_4.setText("");
						textField_4.setEnabled(false);
						textField_5.setText("");
						textField_5.setEnabled(false);
						textField_6.setText("");
						textField_6.setEnabled(false);
						textField_7.setText("");
						textField_7.setEnabled(false);
						textField_8.setText("");
						textField_8.setEnabled(false);
						textField_9.setText("");
						textField_9.setEnabled(false);
						textField_10.setText("");
						textField_10.setEnabled(false);
						textField_11.setText("");
						textField_11.setEnabled(false);
						textField_12.setText("");
						textField_12.setEnabled(false);
					}
			}
			
		});
			
		// 给返回按钮添加监听器
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 设置ui的界面状态为2
				UIStatus = "2";
				// 销毁对话框
				jDialog.dispose();
				// 完全的销毁jDialog的资源
				jDialog = null;
				// 重新new一个新的jdialog出来
				jDialog = new JDialog();

			}
		});
		
		// 给提交按钮添加
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				long begin = System.nanoTime() ;
				String oriWayBillNo = textField.getText().trim();
				logger.info("转寄退回件 工单上报 "+oriWayBillNo+" 提交开始...") ;
				//子母件--查询不是返单的记录--206860
				List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo = waybillService.queryReturnWaybillNoInfo(oriWayBillNo);
				if (org.apache.commons.collections.CollectionUtils.isNotEmpty(queryWaybillByOriginalWaybillNo)) {
					JOptionPane.showMessageDialog(
							jDialog,
							"运单号是转寄退回件开单的运单不允许上报工单！",
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
							JOptionPane.INFORMATION_MESSAGE);
					return; 
				}
				WaybillEntity entity  = waybillService.queryWaybillByNumber(oriWayBillNo);
				//返货原因
				DataDictionaryValueVo returnReason = (DataDictionaryValueVo)comboBox.getSelectedItem();
				//返货类型
				DataDictionaryValueVo returnType = (DataDictionaryValueVo)comboBox_1.getSelectedItem();
				//原因明细
				DataDictionaryValueVo returnDetail = (DataDictionaryValueVo)comboBox_r.getSelectedItem();
				
				if(StringUtils.isEmpty(returnReason.getValueCode())){
					JOptionPane.showMessageDialog(
							jDialog,
							"返货原因不能为空！",
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				if(StringUtils.isEmpty(returnDetail.getValueCode())){
					JOptionPane.showMessageDialog(
							jDialog,
							"原因明细不能为空！",
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				// 校验运单号 如果为空
				if (StringUtil.isEmpty(oriWayBillNo)) {
					JOptionPane.showMessageDialog(
							jDialog,
							i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.NoWayBillNo"),
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
							JOptionPane.INFORMATION_MESSAGE);
					reSet();
					return;
					// 校验运单号的准备性
				} else if (null == entity) {
					JOptionPane.showMessageDialog(
							jDialog,
							i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.NoWayBillInfo"),
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
							JOptionPane.INFORMATION_MESSAGE);
					reSet();
					return;
					// 校验 申请事由 如果为空
				} else if (StringUtil.isEmpty(textArea.getText())) {

					JOptionPane.showMessageDialog(
							jDialog,
							i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.ApplyReason"),
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
							JOptionPane.INFORMATION_MESSAGE);
					return;
					// 判断是否开过返货单(按票返)，开过不允许再次申请返货工单
				} else if (null != waybillService
						.queryWaybillByOriginalWaybillNo(
								oriWayBillNo,
								WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO)) {
					JOptionPane.showMessageDialog(
							jDialog,
							i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.repeatCreating"),
							i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
							JOptionPane.INFORMATION_MESSAGE);
					return;
				 // 这个时候可以调用service来发送相关的信息了
				}else {
					/**
					 * @author foss-206860
					 * 
					 * 子母件项目
					 * */
					//----子母件校验----begin
					//获取返货方式
					DataDictionaryValueVo returnMode = (DataDictionaryValueVo)comboBox_2.getSelectedItem();
					if(StringUtil.isNotEmpty(oriWayBillNo)){
						//根据运单号查询子母件关系表
						TwoInOneWaybillDto twoInOneWaybillDto = waybillService.queryWaybillRelateByWaybillOrOrderNo(oriWayBillNo);
						if(twoInOneWaybillDto != null){
							/**
							 * 签收的运单不能进行转寄退回件上报
							 */
							boolean isSign=waybillService.queryWaybillSignResultByWaybillNo(oriWayBillNo);
							if(isSign){
								showMessageDialog("该票货物已签收，不允许上报工单!");
								return;
							}
							
							//当货物在派送中时不允许进行返货申请
							//转寄更改和返货工单上报在派送中的时候不允许上报工单--FOSS20160116
							Boolean isDelivery=false;
							List<String> waybillNos= twoInOneWaybillDto.getWaybillNoList();
							if(CollectionUtils.isEmpty(waybillNos)){
								waybillNos=new ArrayList<String>();	
							}
							waybillNos.add(oriWayBillNo);
							if(CollectionUtils.isNotEmpty(waybillNos)){
								for(String waybillNo:waybillNos){
									ArriveSheetEntity arriveSheetEntity=new ArriveSheetEntity();
									arriveSheetEntity.setWaybillNo(waybillNo);
									isDelivery=waybillService.queryArriveSheetByWaybillNo(arriveSheetEntity);
									if(isDelivery){
										showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.IsTwoInOne.hasGoods.NoReturn"));
										return;
									}
								}
							}
							
							//不是子母件
							if(FossConstants.NO.equals(twoInOneWaybillDto.getIsTwoInOne())){
								//当返货方式为按件返时，提示"抱歉，按件返只允许子件和母件单号上报"--FOSS20160116版本取消掉普通运单返货
								/*if(returnMode != null && WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE.equals(returnMode.getValueCode())){
									//抱歉，按件返只允许子件和母件单号上报
									showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.notIsTwoInOne.returnPiece"));
									return;
								}*/
								//1、是否以及上报工单申请
								List<ReturnGoodsRequestEntity> queryReturnGoodsWorkOrder=
										validateGoodSWorkOrderIng(oriWayBillNo);
								if(org.apache.commons.collections.CollectionUtils.isNotEmpty(queryReturnGoodsWorkOrder)){
									//该单号已经申请上报返货，不能再次申请--适用于所有快递运单
									showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.IsTwoInOne.returnAgain"));
									return;
								}
								//是子母件
							}else{
								if(returnMode != null && StringUtils.isEmpty(returnMode.getValueCode())){
									showMessageDialog("字母件工单上报转寄退回件方式不能为空！");
									return;
								}
								
								if(StringUtils.equals(returnType.getValueCode(),WaybillConstants.RETURNTYPE_SAME_CITY)||
										StringUtils.equals(returnType.getValueCode(),WaybillConstants.RETURNTYPE_OTHER_CITY)){
									showMessageDialog("子母件上报工单只可选择转寄退回件类型为：退回件，7天转寄退回件和外发3天转寄退回件！");
									return;
								}
								
								
								//通过运单号校验是否已经申请过返单且没有作废或者是中止
								String validateReturn = validateReturnReport(oriWayBillNo);
								//通过运单号校验是否有无丢货找到
								String  isFlag=validateLoseFound(oriWayBillNo);
								//返回的返货类型不为空，说明已经申请过工单或者是已经开过返货
								if(StringUtil.isNotEmpty(validateReturn) && !FossConstants.YES.equals(isFlag)){
									//该单号已经申请上报返货，不能再次申请--适用于所有快递运单
									showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.IsTwoInOne.returnAgain"));
									return;
								}
								//母件单号
								String mainWaybillNo = twoInOneWaybillDto.getMainWaybillNo();
								//子件单号
								List<String> waybillNoList = twoInOneWaybillDto.getWaybillNoList();
								//判断子母单应收单是否结清
								boolean existRepayment = false;
								if(StringUtil.isNotEmpty(mainWaybillNo)){
									existRepayment = waybillService.isExistRepayment(mainWaybillNo);
								}
								//判断是否存在丢货
								boolean loseGroupFlag = validateLoseGroup(twoInOneWaybillDto);
								if(returnMode != null && WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(returnMode.getValueCode())){
									//-------------------按票返的校验
									//只有母件允许上报按票返
									if(!oriWayBillNo.equals(mainWaybillNo)){
										//抱歉，子母件上报按票返，只允许母件上报
										showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.IsTwoInOne.returnCargo.noParent"));
										return;
									}
									//应收单已结清，不允许按票返
									if(existRepayment){
										//母单应收单已结清，不可按票返
										showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.IsTwoInOne.ExistRepayment.NoReturnCargo"));
										return;
									}
									//判断是否存在母件其子件已经操作了按件返
									boolean validateWaybillReturnPiece = validateWaybillReturnPiece(twoInOneWaybillDto);
									if(validateWaybillReturnPiece){
										//已经存在子母件操作了按件返，不可操作按票返
										showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.IsTwoInOne.OneReturnPiece.NoReturnCargo"));
										return;
									}
									//判断所有子母件是否在到达部门库存（validateStock为true表示不在到达部门库存）
									boolean validateStock = validateStock(mainWaybillNo);
									boolean flag=validateLoseGroup(mainWaybillNo);
									if(validateStock && !flag){
										//子母件不存在到达部门库存，不允许返货
										showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.IsTwoInOne.noExistStock.NoReturn"));
										return;
									}
									//查询子件是否在库存
									if(CollectionUtils.isNotEmpty(waybillNoList)){
										for (int i = 0; i < waybillNoList.size(); i++) {
											if(StringUtil.isNotBlank(waybillNoList.get(i))){
												//判断是否在库存
												boolean stock = validateStock(waybillNoList.get(i));
												//若不在库存且没有丢货则提示“子母件不存在到达部门库存，不允许返货”
												if(stock && !loseGroupFlag){
													//子母件不存在到达部门库存，不允许返货
													showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.IsTwoInOne.noExistStock.NoReturn"));
													return;
												}
											}
										}
									}
									//判断用母件单号上报工单时，母件是否丢货找到，且子件单号有无已经返货开单成功，
									//如果是此种情况则不允许丢货找到的母件单号上报按票返dongsiwei
									String flagParent=validateLoseFound(oriWayBillNo);//母件丢货找到
									boolean flagParents= validateWaybillReturnCARGO(twoInOneWaybillDto);//子母件内子件有无按票返开单
									if(FossConstants.YES.equals(flagParent)){
										if(flagParents){
											showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.notIsTwoInOne.returnCARGO"));
											return;
										}
									}
									
								}else{
									//--------------------按件返的校验
									//判断该单号是够是丢货找到--待写
									if(FossConstants.NO.equals(validateLoseFound(oriWayBillNo))){
										//没有丢货的情况下，应收单结清才可以按件返（没有丢货没有结清应收单不让按件返）
										if(!existRepayment){
											//母单应收单未结清，不可按件返
											showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.IsTwoInOne.noExistRepayment.NoReturnPiece"));
											return;
										}
										//判断子件其母件是否已经操作了按票返（丢货找到除外）
										//查询母件的返货方式
										String mainWaybillNoReturnMode = validateReturn(mainWaybillNo);
										//母件已经操作了按票返，则其他子母件不允许按件返
										if(StringUtil.isNotEmpty(mainWaybillNoReturnMode)
												&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(mainWaybillNoReturnMode)){
											//该单母件已经按票返，不能再次申请按件返
											showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.IsTwoInOne.mainWaybillNoReturn.returnPiece"));
											return;
										}
									}
									//校验当前返货运单的库存
									boolean stock = validateStock(oriWayBillNo);
									//若不在库存且没有丢货则提示“子母件不存在到达部门库存，不允许返货”
									if(stock){
										//子母件不存在到达部门库存，不允许返货
										showMessageDialog(i18n.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.IsTwoInOne.noExistStock.NoReturn"));
										return;
									}
								}
								
							}
							
						}
					}
					
					//----子母件校验----end
					if (WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE.equals(returnType.getValueCode())) {
						if (StringUtil.isEmpty(textField_7.getText()) && entity.getInsuranceAmount() != null) {
							JOptionPane.showMessageDialog(
									jDialog,
									i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.insuranceAmount"),
									i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
									JOptionPane.INFORMATION_MESSAGE);
							return;
						} else if(StringUtil.isNotEmpty(textField_7.getText()) && entity.getInsuranceAmount() != null){
							if (new BigDecimal(textField_7.getText())
									.compareTo(entity.getInsuranceAmount()) < 0) {
								JOptionPane
										.showMessageDialog(
												jDialog,
												i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.insuranceAmountChanged")
														+ entity.getInsuranceAmount(),
												i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
												JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						}
					}
					try {
						// 到达更改审核、受理中不能返货开单--受理中未签收
						waybillService.checkWayBillRfcStatus(oriWayBillNo);

						// 出发更改审核、受理中不能返货开单--更改未受理
						waybillService.checkWayBillChange(entity.getId());
					} catch (BusinessException bizException) {
						JOptionPane.showMessageDialog(
								jDialog,
								bizException.getMessage(),
								i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					WaybillEntity waybillEntity = new WaybillEntity();
					//返货原因
					waybillEntity.setReturnReason(returnReason.getValueCode());
					//返货类型
					waybillEntity.setReturnType(returnType.getValueCode());
					//原因明细
					waybillEntity.setReturnDetail(returnDetail.getValueCode());
					//上报通道
					waybillEntity.setReturnChanle("PC_ACTIVE");
					//代收货款
					if(StringUtil.isNotEmpty(textField_6.getText())){
						waybillEntity.setCodAmount(new BigDecimal(textField_6.getText()));
					} 
					//保价
					if(StringUtil.isNotEmpty(textField_7.getText())){
						waybillEntity.setInsuranceAmount(new BigDecimal(textField_7.getText()));
					}
					//返货方式
					if(null!=returnMode&&StringUtils.isNotEmpty(returnMode.getValueCode())){
						waybillEntity.setReturnMode(returnMode.getValueCode());
					}
					if (true == waybillService.submitReturnGoodsApplyToCRM(
							textField.getText(), textArea.getText(),waybillEntity)) {
						logger.info("转寄退回件 工单上报 提交完成 "+oriWayBillNo+" 总耗时:"+((System.nanoTime()-begin)/(1000*1000))) ;
						// 将UI中的填写信息重置再重新查询-CZM-11
						ui.getResetButton().doClick();
						ui.getSearchButton().doClick();
						// 如果创建成功那么就销毁这个对话框
						jDialog.dispose();
						// 完全的销毁jDialog的资源
						jDialog = null;
						// 重新new一个新的jdialog出来
						jDialog = new JDialog();
						JOptionPane.showMessageDialog(
								jDialog,
								i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.Success"),
								i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
								JOptionPane.INFORMATION_MESSAGE);

					} else {
						logger.info("转寄退回件 工单上报 提交失败 "+oriWayBillNo+" 总耗时:"+((System.nanoTime()-begin)/(1000*1000))) ;
						JOptionPane.showMessageDialog(
								jDialog,
								i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.CreateTip.Failure"),
								i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
								JOptionPane.INFORMATION_MESSAGE);
					}

				}
			}
		});

		// 给对话框销毁的时候添加监听器
		jDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// 在销毁对话框的时候，将ui的界面状态设置成2
				UIStatus = "2";
				jDialog.dispose();
				// 完全的销毁jDialog的资源
				jDialog = null;
				// 重新new一个新的jdialog出来
				jDialog = new JDialog();
			}
		});

	}

	/**
	 * 
	 * reSet:(这里用一句话描述这个方法的作用). <br/>
	 * 清空所有输入控件的值
	 * 
	 * @author 146831
	 * @since JDK 1.6
	 */
	private void reSet() {
		//清空原单收货地址
		textField_4.setText("");
		textField_5.setText("");
	}
	/**
	 * 设置原单地址
	 */
	private void oldWaybillNoAddress(WaybillEntity waybillEntity) {
		if (waybillEntity != null) {
			StringBuilder receiveAddress = new StringBuilder();
			// 重新设置值
			if (StringUtil.isNotEmpty(waybillEntity
					.getReceiveCustomerProvCode())) {
				// 省
				String provice = onlineAddressServiceHessianRemoting
						.queryProviceByCode(waybillEntity
								.getReceiveCustomerProvCode());
				receiveAddress.append(provice);
				if (StringUtil.isNotEmpty(provice)) {
					receiveAddress.append("-");
				}
			}
			if (StringUtil.isNotEmpty(waybillEntity
					.getReceiveCustomerCityCode())) {
				// 市
				String city = onlineAddressServiceHessianRemoting
						.queryCityByCode(waybillEntity
								.getReceiveCustomerCityCode());
				receiveAddress.append(city);
				if (StringUtil.isNotEmpty(city)) {
					receiveAddress.append("-");
				}
			}
			if (StringUtil.isNotEmpty(waybillEntity
					.getReceiveCustomerDistCode())) {
				// 区
				String county = onlineAddressServiceHessianRemoting
						.queryCountyByCode(waybillEntity
								.getReceiveCustomerDistCode());
				receiveAddress.append(county);
				if (StringUtil.isNotEmpty(county)) {
					receiveAddress.append("-");
				}
			}
			if (receiveAddress.lastIndexOf("-") != -1) {
				receiveAddress.deleteCharAt(receiveAddress.lastIndexOf("-"));
			}
			textField_4.setText(receiveAddress.toString());
			if (StringUtil
					.isNotEmpty(waybillEntity.getReceiveCustomerAddress())) {
				// 具体地址
				textField_5.setText(waybillEntity.getReceiveCustomerAddress());
			}
		}
	}
	/**
	 * 设置返单信息
	 * @param waybillEntity
	 */
	private void customerRefuse(WaybillEntity waybillEntity,String flag) {
		if (waybillEntity != null) {
			if(!StringUtils.equals(FossConstants.ACTIVE, flag)){
				textField_6.setText(waybillEntity.getCodAmount() + "");
				textField_6.setEnabled(false);
				textField_7.setText(waybillEntity.getInsuranceAmount() + "");
				textField_7.setEnabled(true);
				textField_8.setText(waybillEntity.getReceiveCustomerContact());
				textField_8.setEnabled(false);
				textField_10
						.setText(waybillEntity.getReceiveCustomerMobilephone());
				textField_10.setEnabled(false);
				textField_11.setText(waybillEntity.getReceiveCustomerPhone());
				textField_11.setEnabled(false);
	
				StringBuilder receiveAddress = new StringBuilder();
				// 重新设置值
				if (StringUtil.isNotEmpty(waybillEntity.getReceiveCustomerProvCode())) {
					// 省
					String provice = onlineAddressServiceHessianRemoting.queryProviceByCode(waybillEntity
									.getReceiveCustomerProvCode());
					receiveAddress.append(provice);
					if (StringUtil.isNotEmpty(provice)) {
						receiveAddress.append("-");
					}
				}
				if (StringUtil.isNotEmpty(waybillEntity.getReceiveCustomerCityCode())) {
					// 市
					String city = onlineAddressServiceHessianRemoting
							.queryCityByCode(waybillEntity.getReceiveCustomerCityCode());
					receiveAddress.append(city);
					if (StringUtil.isNotEmpty(city)) {
						receiveAddress.append("-");
					}
				}
				if (StringUtil.isNotEmpty(waybillEntity.getReceiveCustomerDistCode())) {
					// 区
					String county = onlineAddressServiceHessianRemoting
							.queryCountyByCode(waybillEntity.getReceiveCustomerDistCode());
					receiveAddress.append(county);
					if (StringUtil.isNotEmpty(county)) {
						receiveAddress.append("-");
					}
				}
				if (receiveAddress.lastIndexOf("-") != -1) {
					receiveAddress.deleteCharAt(receiveAddress.lastIndexOf("-"));
				}
				textField_9.setText(receiveAddress.toString());
				textField_9.setEnabled(false);
				if (StringUtil.isNotEmpty(waybillEntity.getReceiveCustomerAddress())) {
					// 具体地址
					textField_12.setText(waybillEntity.getReceiveCustomerAddress());
				}
				textField_12.setEnabled(false);
			}else{ 
				textField_6.setText("0");
				textField_6.setEnabled(false);
				textField_7.setText(waybillEntity.getInsuranceAmount() + "");
				textField_7.setEnabled(true);
				textField_8.setText(waybillEntity.getDeliveryCustomerContact());
				textField_8.setEnabled(false);
				textField_10
						.setText(waybillEntity.getDeliveryCustomerMobilephone());
				textField_10.setEnabled(false);
				textField_11.setText(waybillEntity.getDeliveryCustomerPhone());
				textField_11.setEnabled(false);
	
				StringBuilder receiveAddress = new StringBuilder();
				// 重新设置值
				if (StringUtil.isNotEmpty(waybillEntity
						.getDeliveryCustomerProvCode())) {
					// 省
					String provice = onlineAddressServiceHessianRemoting
							.queryProviceByCode(waybillEntity
									.getDeliveryCustomerProvCode());
					receiveAddress.append(provice);
					if (StringUtil.isNotEmpty(provice)) {
						receiveAddress.append("-");
					}
				}
				if (StringUtil.isNotEmpty(waybillEntity
						.getDeliveryCustomerCityCode())) {
					// 市
					String city = onlineAddressServiceHessianRemoting
							.queryCityByCode(waybillEntity
									.getDeliveryCustomerCityCode());
					receiveAddress.append(city);
					if (StringUtil.isNotEmpty(city)) {
						receiveAddress.append("-");
					}
				}
				if (StringUtil.isNotEmpty(waybillEntity
						.getDeliveryCustomerDistCode())) {
					// 区
					String county = onlineAddressServiceHessianRemoting
							.queryCountyByCode(waybillEntity
									.getDeliveryCustomerDistCode());
					receiveAddress.append(county);
					if (StringUtil.isNotEmpty(county)) {
						receiveAddress.append("-");
					}
				}
				if (receiveAddress.lastIndexOf("-") != -1) {
					receiveAddress.deleteCharAt(receiveAddress.lastIndexOf("-"));
				}
				textField_9.setText(receiveAddress.toString());
				textField_9.setEnabled(false);
				if (StringUtil.isNotEmpty(waybillEntity
						.getDeliveryCustomerAddress())) {
					// 具体地址
					textField_12
							.setText(waybillEntity.getDeliveryCustomerAddress());
				}
				textField_12.setEnabled(false);
		}
	 }
	}
	/**
	 * 
	 * initDialog:(这里用一句话描述这个方法的作用). <br/>
	 * 这个方法的作用是排版这个jDialog.<br/>
	 * 
	 * @author 146831
	 * @since JDK 1.6
	 */
	private void initDialog() {
		// 设置运单号的最大的输入大小为10
		NumberDocument numberDocument = new NumberDocument(_TEN);
		MobileDocument mobileDocument = new MobileDocument(ELEVEN);
		MobileDocument document1 = new MobileDocument(ELEVEN);
		MobileDocument document2 = new MobileDocument(ELEVEN);
		// 设置jDialog永远在最前面
		// this.jDialog.setAlwaysOnTop(true);
		// 设置对话框的大小
		jDialog.setSize(NUM_750, NUM_473);
		// 最多可以输入500个汉字
		textArea = new LimitNumTextArea(_THOUSAND);
		// 设置自动换行
		textArea.setLineWrap(true);
		textArea.setBorder(new LineBorder(new java.awt.Color(NUM_127, NUM_157, NUM_185), 1,
				false));

		// 提交按钮
		Dimension preferredSize = new Dimension(NumberConstants.NUMBER_100, NumberConstants.NUMBER_20);// 设置尺寸
		
		panel_2 = new JPanel();
		/*panel_2.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.title"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));*/
		panel_2.setBorder(new TitledBorder(null, "转寄退回件申请",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jDialog.getContentPane().add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("391dlu:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		panel_3 = new JPanel();
		panel_2.add(panel_3, "2, 1, fill, fill");
		panel_3.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("max(41dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(99dlu;default)"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(79dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("300px"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		lblNewLabel = new JLabel(
				i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.Butten.create.wayBillNO"));
		panel_3.add(lblNewLabel, "2, 2, right, default");
		// 首先new出运单号
		textField = new JTextFieldValidate();
		panel_3.add(textField, "4, 2");
		//--转寄退回件方式
//		lblNewLabel_1 = new JLabel("返货方式");
		lblNewLabel_1 = new JLabel("转寄退回件方式");
		panel_3.add(lblNewLabel_1, "6, 2, right, default");
		
		comboBox_2 = new JComboBox();
		panel_3.add(comboBox_2, "8, 2, fill, default");
		label_tip = new JLabel();
		label_tip.setForeground(Color.red);
		panel_3.add(label_tip, "10, 2");
		// 清空运单号的信息
		textField.setText("");
		textField.setDocument(numberDocument);
		textField.setColumns(TEN);
		
		panel = new JPanel();
		panel_2.add(panel, "2, 3, fill, center");
		/*panel.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.returnGoodsReason"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));*/
		panel.setBorder(new TitledBorder(null, "转寄退回件原因",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("111px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(64dlu;default)"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("max(4dlu;default)"),
				ColumnSpec.decode("max(64dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(28dlu;default)"),
				ColumnSpec.decode("max(4dlu;default)"),
				ColumnSpec.decode("max(64dlu;default)"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("21px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
//		label_3 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.returnGoodsReason")+":");
		label_3 = new JLabel("转寄退回件原因"+":");

		panel.add(label_3, "2, 2, right, default");
		
		comboBox = new JComboBox();
		panel.add(comboBox, "4, 2");
		
		//--
		label_r = new JLabel("原因明细"+":");

		panel.add(label_r, "6, 2, right, default");
		
		comboBox_r = new JComboBox();
		panel.add(comboBox_r, "8, 2");
		//--
		
		
//		lblNewLabel_3 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.returnType"));
		lblNewLabel_3 = new JLabel("转寄退回件类型");
		panel.add(lblNewLabel_3, "10, 2");
		
		comboBox_1 = new JComboBox();
		//panel.add(comboBox_1, "12, 2, fill, default");
		panel.add(comboBox_1, "12, 2");

		lblNewLabel_4 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.oldWaybillNoAddress"));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_4, "2, 4, right, default");
		
		textField_4 = new JTextField();
		textField_4.setEnabled(false);
		panel.add(textField_4, "4, 4, 3, 1, fill, default");
		textField_4.setColumns(FIVE);
		
		textField_5 = new JTextField();
		textField_5.setEnabled(false);
		panel.add(textField_5, "8, 4, 3, 1, fill, default");
		textField_5.setColumns(TEN);
		
		panel_1 = new JPanel();
		panel_2.add(panel_1, "2, 4, fill, center");
		/*panel_1.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.returnGoodsWaybillNoInfo"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));*/
		panel_1.setBorder(new TitledBorder(null, "转寄退回件单号信息",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(64dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(52dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(64dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(45dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(55dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblNewLabel_5 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.codAmount"));
		panel_1.add(lblNewLabel_5, "2, 1, right, default");
		
		textField_6 = new JTextField();
		panel_1.add(textField_6, "4, 1, fill, default");
		textField_6.setDocument(document1);
		textField_6.setColumns(TEN);
		
		lblNewLabel_6 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.insuranceAmount"));
		panel_1.add(lblNewLabel_6, "6, 1, right, default");
		
		textField_7 = new JTextField();
		panel_1.add(textField_7, "8, 1, fill, default");
		textField_7.setDocument(document2);
		textField_7.setColumns(TEN);
		
		label_4 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.receiveCustomer"));
		panel_1.add(label_4, "2, 3, right, default");
		
		textField_8 = new JTextField();
		panel_1.add(textField_8, "4, 3, fill, default");
		textField_8.setColumns(TEN);
		
		label_6 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.receiveCustomerMobilephone"));
		panel_1.add(label_6, "6, 3, right, default");
		
		textField_10 = new JTextField();
		panel_1.add(textField_10, "8, 3, fill, default");
		
		textField_10.setDocument(mobileDocument);
		textField_10.setColumns(TEN);
		
		label_7 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.receiveCustomerPhone"));
		panel_1.add(label_7, "10, 3, right, default");
		
		textField_11 = new JTextField();
		panel_1.add(textField_11, "12, 3, 3, 1, fill, default");
		NumberDocument telePhoneDocument = new NumberDocument(NumberConstants.NUMBER_20);
		textField_11.setDocument(telePhoneDocument);
		textField_11.setColumns(TEN);
		
		label_5 = new JLabel(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.receiveAddress"));
		panel_1.add(label_5, "2, 5, right, default");
		
		textField_9 = new JAddressField();
		panel_1.add(textField_9, "4, 5, 3, 1, fill, default");
		LetterDocument letterDocument=new LetterDocument();
		textField_9.setDocument(letterDocument);	
		
		textField_12 = new JXTextField();
		panel_1.add(textField_12, "8, 5, 7, 1, fill, default");
		textField_12.setColumns(TEN);
								// 标签 申请事由
								label_1 = new JLabel(
										i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.Butten.create.applyReason"));
								// 标签 最多不超过500字
								label_2 = new JLabel(
										i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.Butten.create.limit"));
								// 提交按钮
								btnNewButton_1 = new JButton(
										i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.Butten.create.submit"));
								// 返回按钮
								btnNewButton = new JButton(
										i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.Butten.create.return"));
								// new一个panel出来，准备放入jDialog中
								JPanel jPanel = new JPanel();
								panel_2.add(jPanel, "2, 5, fill, center");
								jPanel.setLayout(new FormLayout(new ColumnSpec[] {
										FormFactory.RELATED_GAP_COLSPEC,
										FormFactory.DEFAULT_COLSPEC,
										FormFactory.RELATED_GAP_COLSPEC,
										ColumnSpec.decode("max(93dlu;default):grow"),
										FormFactory.RELATED_GAP_COLSPEC,
										FormFactory.DEFAULT_COLSPEC,
										FormFactory.RELATED_GAP_COLSPEC,
										ColumnSpec.decode("default:grow"),
										FormFactory.RELATED_GAP_COLSPEC,
										FormFactory.DEFAULT_COLSPEC,
										FormFactory.RELATED_GAP_COLSPEC,
										ColumnSpec.decode("default:grow"),
										FormFactory.RELATED_GAP_COLSPEC,
										FormFactory.DEFAULT_COLSPEC,},
									new RowSpec[] {
										FormFactory.RELATED_GAP_ROWSPEC,
										RowSpec.decode("45dlu:grow"),
										FormFactory.RELATED_GAP_ROWSPEC,
										FormFactory.DEFAULT_ROWSPEC,
										FormFactory.RELATED_GAP_ROWSPEC,
										FormFactory.DEFAULT_ROWSPEC,
										FormFactory.DEFAULT_ROWSPEC,}));
								
										jPanel.add(label_1, "2, 2");
										JScrollPane jScrollPane = new JScrollPane(textArea);
										jPanel.add(jScrollPane, "4, 2, 9, 1, fill, fill");
										
												label_2.setForeground(Color.red);
												jPanel.add(label_2, "4, 4");
												btnNewButton_1.setPreferredSize(preferredSize);
												btnNewButton_1.setSize(NumberConstants.NUMBER_100, NumberConstants.NUMBER_20);
												jPanel.add(btnNewButton_1, "4, 6");
												
														// 返回按钮
														jPanel.add(btnNewButton, "8, 6");
		// 把ui的界面状态重新初始化
		UIStatus = "1";
	}
	public void initComboBox() {
		//返货原因
		initReturnGoodsReason();
		//返货类型
		initReturnGoodsType();
		//返货方式
		initReturnGoodsMode();
		//原因明细
//		initReasonsMode();
	}
	
	//原因明细:转寄退回件原因内新增原因明细：发货人原因，收货人原因，贴错单和其他
	private void initReasonsMode(DataDictionaryValueVo returnType){
		
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
	    if (WaybillConstants.RETURNREASON_COMPANY_REASON.equals(returnType.getValueCode())) {
			//我司原因--贴错单
			DataDictionaryValueVo errorBillReason = new DataDictionaryValueVo();
			errorBillReason.setValueCode(WaybillConstants.REASON_DETAIL_ERROR_REASON);
			errorBillReason.setValueName("贴错单");
			comboModel.addElement(errorBillReason); 
			
			//其他
			DataDictionaryValueVo otherReason = new DataDictionaryValueVo();
			otherReason.setValueCode(WaybillConstants.REASON_DETAIL_OTHER_REASON);
			otherReason.setValueName("其他");
			comboModel.addElement(otherReason); 
			
		} else {
			//客户原因--
			//发货人原因，
			DataDictionaryValueVo sendReason = new DataDictionaryValueVo();
			sendReason.setValueCode(WaybillConstants.REASON_DETAIL_SEND_REASON);
			sendReason.setValueName("发货人原因");
			comboModel.addElement(sendReason);
			//收货人原因
			DataDictionaryValueVo reviceReason = new DataDictionaryValueVo();
			reviceReason.setValueCode(WaybillConstants.REASON_DETAIL_REVICE_REASON);
			reviceReason.setValueName("收货人原因");
			comboModel.addElement(reviceReason);
		}
		comboBox_r.setModel(comboModel);
	}
	//初始化返货方式
	private void initReturnGoodsMode(){
		Injector injector = GuiceContextFactroy.getInjector();
		//数据字典服务类
		DataDictionaryValueService dictionaryValueSerivce = injector.getInstance(DataDictionaryValueService.class);
		//返货方式
		DefaultComboBoxModel combWaibillReturnModeModel = new DefaultComboBoxModel();
		if(dictionaryValueSerivce != null){
			//获取运单开单类型的实体类
			List<DataDictionaryValueEntity> list = dictionaryValueSerivce.queryByTermsCode(DictionaryConstants.PKP_WAYBILLEXPRESS_TYPE);
			if(CollectionUtils.isNotEmpty(list)){
				DataDictionaryValueVo vo1 = new DataDictionaryValueVo();
				vo1.setValueName("");
				combWaibillReturnModeModel.addElement(vo1);
				for (DataDictionaryValueEntity dataDictionary : list) {
					//排除返单这种类型
					if(!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(dataDictionary.getValueCode())){
						DataDictionaryValueVo vo = new DataDictionaryValueVo();
						ValueCopy.valueCopy(dataDictionary, vo);
						combWaibillReturnModeModel.addElement(vo);
					}
				}
			}
		}
		//当从数据字典中获取不到数据时，获取自定义的返货方式
		if(combWaibillReturnModeModel.getSize() == 0){
			//null
			DataDictionaryValueVo nullReturnCargo = new DataDictionaryValueVo();
			nullReturnCargo.setValueCode(null);
		/*	returnCargo.setValueName(i18n
					.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.combWaibillReturnModeModel.returnCargo"));*/
			nullReturnCargo.setValueName("");
			combWaibillReturnModeModel.addElement(nullReturnCargo);
			//按票返
			DataDictionaryValueVo returnCargo = new DataDictionaryValueVo();
			returnCargo.setValueCode(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
		/*	returnCargo.setValueName(i18n
					.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.combWaibillReturnModeModel.returnCargo"));*/
			returnCargo.setValueName("按票返转寄退回");
			combWaibillReturnModeModel.addElement(returnCargo);
			
			//按件返
			DataDictionaryValueVo returnPiece = new DataDictionaryValueVo();
			returnPiece.setValueCode(WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE);
			/*returnPiece.setValueName(i18n
					.get("foss.gui.creatingexp.ExpReturnGoodsApplyAction.combWaibillReturnModeModel.returnPiece"));*/
			returnPiece.setValueName("按件返转寄退回");
			combWaibillReturnModeModel.addElement(returnPiece);
		}
		//将返货方式添加至控件中
		comboBox_2.setModel(combWaibillReturnModeModel);
	}
	
	private void initReturnGoodsReason(){
		DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
		
		DataDictionaryValueVo nullReason = new DataDictionaryValueVo();
		nullReason.setValueCode("");
		nullReason.setValueName("");
		comboModel.addElement(nullReason);
		
		//我司原因
		DataDictionaryValueVo companyReason = new DataDictionaryValueVo();
		companyReason.setValueCode(WaybillConstants.RETURNREASON_COMPANY_REASON);
		companyReason.setValueName(i18n
				.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.returnReason.companyReason"));
		comboModel.addElement(companyReason);
		
		//客户原因
		DataDictionaryValueVo customerReason = new DataDictionaryValueVo();
		customerReason.setValueCode(WaybillConstants.RETURNREASON_CUSTOMER_REASON);
		customerReason.setValueName(i18n
				.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.returnReason.customerReason"));
		comboModel.addElement(customerReason);
		
		comboBox.setModel(comboModel);
	}

	private void initReturnGoodsType() {

		DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
		
		//退回
		DataDictionaryValueVo back = new DataDictionaryValueVo();
		back.setValueCode(WaybillConstants.RETURN_BACK);
		back.setValueName("退回件");
		comboModel.addElement(back);
		/*// 客户拒收
		DataDictionaryValueVo ddv = new DataDictionaryValueVo();
		ddv.setValueCode(WaybillConstants.RETURNTYPE_CUSTORMER_REFUSE);
		ddv.setValueName(i18n
				.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.refuse"));
		ddv.setValueName("退回件");
		comboModel.addElement(ddv);*/
		//返货类型：同城转寄
		DataDictionaryValueVo obsolete = new DataDictionaryValueVo();
		obsolete.setValueCode(WaybillConstants.RETURNTYPE_SAME_CITY);
		obsolete.setValueName(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.same"));
		comboModel.addElement(obsolete);
		
		////返货类型：非同城转寄
		DataDictionaryValueVo abort = new DataDictionaryValueVo();
		abort.setValueCode(WaybillConstants.RETURNTYPE_OTHER_CITY);
		abort.setValueName(i18n.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.other"));
		comboModel.addElement(abort);
		// 7天返货
		DataDictionaryValueVo ret = new DataDictionaryValueVo();
		ret.setValueCode(WaybillConstants.RETURNTYPE_SEVEN_DAYS_RETURN);
		/*ret.setValueName(i18n
				.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.sevenDaysReturn"));*/
		ret.setValueName("7天转寄退回件");

		comboModel.addElement(ret);
		// 外发3天返货
		DataDictionaryValueVo wf = new DataDictionaryValueVo();
		wf.setValueCode(WaybillConstants.RETURNTYPE_SEND_OUT_THREE_DAYS_RETURN);
		/*wf.setValueName(i18n
				.get("foss.gui.creating.ExpReturnedGoodsWaybillUI.ExpReturnedGoodsWorkOrderDialog.sendOutThreeDaysReturn"));*/
		wf.setValueName("外发3天转寄退回件");
		comboModel.addElement(wf);

		comboBox_1.setModel(comboModel);
	}

	/**
	 * 
	 * ClassName: LimitNumTextArea <br/>
	 * Function: 这个内部类的作用是为了创建一个可以限制字数的换行的JTextArea. <br/>
	 * 好像只有我一个人这边用到了，所以 date: 2015-2-5 下午3:09:19 <br/>
	 * 
	 * @author 146831
	 * @version ExpReturnGoodsApplyAction
	 * @since JDK 1.6
	 */
	class LimitNumTextArea extends JTextArea {
		private static final long serialVersionUID = 1L;

		public LimitNumTextArea(int length) {
			setDocument(new LimitNumDocument(length));
		}

		private class LimitNumDocument extends PlainDocument {
			private static final long serialVersionUID = 1L;
			private int fLength = -1; // 可任意输入	

			public LimitNumDocument(int length) {
				fLength = length;
			}

			public void insertString(int offs, String str, AttributeSet attr)
					throws BadLocationException {
				int originalLength = getLength();
				if (originalLength <= 0) {
					super.insertString(offs, str, attr);
					return;
				}

				char[] input = str.toCharArray();
				int inputLength = 0;
				for (int i = 0; i < input.length; i++) {
					if (originalLength + inputLength >= fLength) {
						break;
					}
					inputLength++;
				}

				super.insertString(offs, new String(input, 0, inputLength),
						attr);
			}
		}
	}
	/**
	 * 校验手机号码格式
	 * @param mobileNo
	 * @return
	 */
	public static boolean isMobileNo(String mobileNo){
		Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");  
		Matcher m = p.matcher(mobileNo); 
		return m.matches();
	}
	/**
	 * 校验座机号码格式
	 * @param str
	 * @return
	 */
	public static boolean isPhone(String str) {   
        Pattern p1 = null,p2 = null;  
        Matcher m = null;  
        boolean b = false;    
        p1 = Pattern.compile("^[0][0-9]{2,3}-[0-9]{7,8}$");  // 验证带区号的  
        p2 = Pattern.compile("^[1-9]{1}[0-9]{6,7}$");         // 验证没有区号的  
        if(str.length() > NINE)  
        {   m = p1.matcher(str);  
            b = m.matches();    
        }else{  
            m = p2.matcher(str);  
            b = m.matches();   
        }    
        return b;  
    }
	
	/**
	 * 通过运单号校验是否已经申请过返单且没有作废或者是中止
	 * @param oriWayBillNo 原始运单号
	 * @return 返货方式
	 * @author foss-206860
	 * */
	private String validateReturn(String oriWayBillNo) {
		//不管是不是子母件，先校验是否已经申请过返货和已经返货开单，已开过提示"该单号已经申请上报返货，不能再次申请"
		String returnMode = "";
		//检验是否已经返货开单（中止和作废的不算）
		// 根据原单号查询返货开单信息
		if(StringUtil.isNotEmpty(oriWayBillNo)){
			//查询该单号是否已经开过返货
			List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo = waybillService
					.queryWaybillByOriginalWaybillNo(oriWayBillNo);
			if(CollectionUtils.isNotEmpty(queryWaybillByOriginalWaybillNo)){
				//开过返货，返回返货类型
				return queryWaybillByOriginalWaybillNo.get(0).getReturnType();
				//没有开过返货，查询是否申请过返货工单
			}else{
				//查询综合的返货工单
				List<ReturnGoodsRequestEntity> list = waybillService.queryReturnGoodsWorkOrder(oriWayBillNo);
				//取最后一条上报的工单数据，如果状态为待受理中，则不用许再次上报
				ReturnGoodsRequestEntity entity=null;
				int index=0;
				if(CollectionUtils.isNotEmpty(list)){
					for(int i = 1;i<list.size();i++){
						if(list.get(index).getTimeReport().getTime()<
								list.get(i).getTimeReport().getTime()){
							index = i;
						}
					}
					entity = list.get(index);
					if(entity !=null&&WaybillConstants.ACCEPTSTATUS_REFUSED.equals(entity.getReturnStatus())){
						return entity.getReturnType();
					} 
				}
			}
		}
		return returnMode;
	}
	
	private String validateReturnReport(String oriWayBillNo) {
		//不管是不是子母件，先校验是否已经申请过返货和已经返货开单，已开过提示"该单号已经申请上报返货，不能再次申请"
		String returnMode = "";
		//检验是否已经返货开单（中止和作废的不算）
		// 根据原单号查询返货开单信息
		if(StringUtil.isNotEmpty(oriWayBillNo)){
			//查询该单号是否已经开过返货
			List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo = waybillService
					.queryWaybillByOriginalWaybillNo(oriWayBillNo);
			if(CollectionUtils.isNotEmpty(queryWaybillByOriginalWaybillNo)){
				//开过返货，返回返货类型
				return queryWaybillByOriginalWaybillNo.get(0).getReturnType();
				//没有开过返货，查询是否申请过返货工单
			}else{
				//查询综合的返货工单
				List<ReturnGoodsRequestEntity> list = waybillService.queryReturnGoodsWorkOrder(oriWayBillNo);
				List<ReturnGoodsRequestEntity> queryReturnGoodsWorkOrder = new ArrayList<ReturnGoodsRequestEntity>();
				//已经申请过返货工单，不允许再次申请
				for (int i = 0; i < list.size(); i++) {
					ReturnGoodsRequestEntity entity = list.get(i);
					//查询所有的未受理的运单
					if(entity != null && WaybillConstants.ACCEPTSTATUS_REFUSED.equals(entity.getReturnStatus())){
						queryReturnGoodsWorkOrder.add(entity);
					}
				}
				if(CollectionUtils.isNotEmpty(queryReturnGoodsWorkOrder)){
					//申请过返货工单，返回返货类型--待写
					return queryReturnGoodsWorkOrder.get(0).getReturnMode();
				}
			}
		}
		return returnMode;
	}
	//验证单号是否已经上报且未受理
	private List<ReturnGoodsRequestEntity> validateGoodSWorkOrderIng(String oriWayBillNo){
		//查询综合的返货工单
		List<ReturnGoodsRequestEntity> list = waybillService.queryReturnGoodsWorkOrder(oriWayBillNo);
		List<ReturnGoodsRequestEntity> queryReturnGoodsWorkOrder = new ArrayList<ReturnGoodsRequestEntity>();
		//已经申请过返货工单，不允许再次申请
		for (int i = 0; i < list.size(); i++) {
			ReturnGoodsRequestEntity entity = list.get(i);
			//查询所有的未受理的运单
			if(entity != null && WaybillConstants.ACCEPTSTATUS_REFUSED.equals(entity.getReturnStatus())){
				queryReturnGoodsWorkOrder.add(entity);
			}
		}
		return queryReturnGoodsWorkOrder;
	}
	
	
	/**
	 * 校验运单是否已经返货开单成功
	 * @author Foss-218459 dongsiwei
	 */
	private String validateReturnParent(String oriWayBillNo) {
		//不管是不是子母件，先校验是否已经申请过返货和已经返货开单，已开过提示"该单号已经申请上报返货，不能再次申请"
		String returnMode = "";
		//检验是否已经返货开单（中止和作废的不算）
		// 根据原单号查询返货开单信息
		if(StringUtil.isNotEmpty(oriWayBillNo)){
			//查询该单号是否已经开过返货
			List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo = waybillService
					.queryWaybillByOriginalWaybillNo(oriWayBillNo);
			if(CollectionUtils.isNotEmpty(queryWaybillByOriginalWaybillNo)){
				//开过返货，返回返货类型
				return queryWaybillByOriginalWaybillNo.get(0).getReturnType();
			}
		}
		return returnMode;
	}
	
	/**
	 * 封装提示信息
	 * @param title
	 * @author foss-206860
	 * */
	private void showMessageDialog(String title){
		JOptionPane.showMessageDialog(
				jDialog,
				title,
				i18n.get("foss.gui.creating.waybillEditUI.common.prompt"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 *判断子母单号是否已经操作了按件返 
	 *@param twoInOneWaybillDto 
	 *@author foss-206860
	 */
	private boolean validateWaybillReturnPiece(
			TwoInOneWaybillDto twoInOneWaybillDto) {
		boolean pieceFlag = false;
		//母单号
		String mainWaybillNo = twoInOneWaybillDto.getMainWaybillNo();
		//子单号集合
		List<String> waybillNoList = twoInOneWaybillDto.getWaybillNoList();
		String validateReturn = validateReturn(mainWaybillNo);
		if(StringUtil.isNotEmpty(validateReturn)
				&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE.equals(validateReturn)){
			return true;
		}
		if(CollectionUtils.isNotEmpty(waybillNoList)){
			for (int i = 0; i < waybillNoList.size(); i++) {
				String validateReturn2 = validateReturn(waybillNoList.get(i));
				if(StringUtil.isNotEmpty(validateReturn2)
						&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE.equals(validateReturn2)){
					return true;
				}
			}
		}
		return pieceFlag;
	}
	
	/**
	 * 校验子母件内子件有无按票返货
	 * @param twoInOneWaybillDto
	 * @return
	 */
	private boolean validateWaybillReturnCARGO(
			TwoInOneWaybillDto twoInOneWaybillDto) {
		boolean pieceFlag = false;
		//子单号集合
		List<String> waybillNoList = twoInOneWaybillDto.getWaybillNoList();
//		String validateReturn = validateReturn(mainWaybillNo);
//		if(StringUtil.isNotEmpty(validateReturn)
//				&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE.equals(validateReturn)){
//			return true;
//		}
		if(CollectionUtils.isNotEmpty(waybillNoList)){
			for (int i = 0; i < waybillNoList.size(); i++) {
				String validateReturn2 = validateReturnParent(waybillNoList.get(i));
				if(StringUtil.isNotEmpty(validateReturn2)
						&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(validateReturn2)){
					return true;
				}
			}
		}
		return pieceFlag;
	}
	/**
	 * 是否在到达部门库存
	 * @param oriWayBillNo
	 * @author foss-206860
	 * */
	private boolean validateStock(String oriWayBillNo){
		//若原单号存在落地配外发交接单（作废和有效），则返货开单时不校验该货物是否在到达部门库存
		boolean isWaiFa = waybillService
				.queryBeLdpHandOveredByWaybillNo(oriWayBillNo);
		if (!isWaiFa) {
			boolean stock = true;
			if(StringUtil.isNotEmpty(oriWayBillNo)){
				WaybillEntity entity = waybillService
						.queryWaybillByNumber(oriWayBillNo);
				// 单号不在到达部门库存中不能返货开单
				List<StockEntity> stockStatus = waybillService
						.queryStockByWaybillNo(oriWayBillNo);
				if (CollectionUtils.isNotEmpty(stockStatus)) {
					String destOrgCode = entity.getCustomerPickupOrgCode();
					// 查询营业部的部门信息
					SaleDepartmentEntity dept = waybillService
							.querySaleDepartmentByCode(destOrgCode);
					// 是不是驻地营业部
					if (dept != null
							&& dept.getStation().equals(FossConstants.ACTIVE)) {
						// 驻地营业部对应外场
						String transferCenter = dept.getTransferCenter();
						for (StockEntity stockStatu : stockStatus) {
							String orgCode = stockStatu.getOrgCode();
							if (transferCenter.equals(orgCode)) {
								stock = false;
							}
						}
					} else {
						for (StockEntity stockStatu : stockStatus) {
							String orgCode = stockStatu.getOrgCode();
							if (destOrgCode.equals(orgCode)) {
								stock = false;
							}
						}
					}
					
				}
			}
			return stock;
		}
		return false;
	}
	
	/**
	 * 是否存在丢货
	 * @param twoInOneWaybillDto
	 * @author foss-206860
	 * */
	private boolean validateLoseGroup(TwoInOneWaybillDto twoInOneWaybillDto){
		Boolean loseGroupFlag = false;
		//查询所有子件
		List<String> noList = twoInOneWaybillDto.getWaybillNoList();
		for (int i = 0; i < noList.size(); i++) {
			if(StringUtil.isNotBlank(noList.get(i))){
				//判断子件是否丢货
				QmsYchExceptionReportEntity loseGroup = waybillService.isLoseGroup(noList.get(i));
				//存在丢货组
				if(loseGroup != null && loseGroup.getIsSuccess() == 1 && "Y".equals(loseGroup.getIsInLoseGroup())){
					loseGroupFlag = true;
					return loseGroupFlag;
				}
			}
		}
		//查询母件
		String mainWaybillNo = twoInOneWaybillDto.getMainWaybillNo();
		if(StringUtil.isNotEmpty(mainWaybillNo)){
			//判断子件是否丢货
			QmsYchExceptionReportEntity loseGroup = waybillService.isLoseGroup(mainWaybillNo);
			//存在丢货组
			if(loseGroup != null && loseGroup.getIsSuccess() == 1 && "Y".equals(loseGroup.getIsInLoseGroup())){
				loseGroupFlag = true;
				return loseGroupFlag;
			}
		}
		return loseGroupFlag;
	}
	
	//是否存在丢货
	private boolean validateLoseGroup(String waybillNo){
		Boolean loseGroupFlag = false;
		if(StringUtil.isNotEmpty(waybillNo)){
			//判断子件是否丢货
			QmsYchExceptionReportEntity loseGroup = waybillService.isLoseGroup(waybillNo);
			//存在丢货组
			if(loseGroup != null && loseGroup.getIsSuccess() == 1 && "Y".equals(loseGroup.getIsInLoseGroup())){
				loseGroupFlag = true;
				return loseGroupFlag;
			}
		}
		return loseGroupFlag;
	}
	
	/**
	 * 是否丢货找到
	 * @param oriWayBillNo
	 * @author foss-206860
	 * */
	private String validateLoseFound(String oriWayBillNo){
		return waybillService.queryLostFindGoods(oriWayBillNo);
	}

}
