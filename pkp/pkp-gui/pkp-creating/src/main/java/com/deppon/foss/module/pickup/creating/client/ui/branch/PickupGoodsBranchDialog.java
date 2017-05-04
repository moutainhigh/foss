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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/branch/PickupGoodsBranchJPanel.java
 * 
 * FILE NAME        	: PickupGoodsBranchJPanel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.ui.branch;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.common.client.vo.BranchQueryVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.LoadPickupStationAction;
import com.deppon.foss.module.pickup.creating.client.action.QueryBranchByNameAction;
import com.deppon.foss.module.pickup.creating.client.action.QueryKeyAction;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 提货网点查询界面
 * @author 025000-FOSS-helong
 * @date 2013-2-28 下午04:02:16
 */
public class PickupGoodsBranchDialog extends JFrame {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -8989401239513646918L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(PickupGoodsBranchDialog.class);
	
	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory.getLog(LoadPickupStationAction.class);

	private static final int TEN = 10;
	
	
	private  BranchDataModel tableModel;
	
	/**
	 * 查询网点名称
	 */
	@Bind("queryName")
	private JTextField txtQueryName;
	
	/**
	 * 网点编码
	 */
	@Bind("code")
	private JTextField txtBranchNumber;
	/**
	 * 网点名称
	 */
	@Bind("name")
	private JTextField txtBranchName;
	/**
	 * 目的站
	 */
	@Bind("targetOrgName")
	private JTextField txtTargetOrgName;
	/**
	 * 省份
	 */
	@Bind("province")
	private JTextField txtProvince;
	/**
	 * 城市
	 */
	@Bind("cityName")
	private JTextField txtCity;
	/**
	 * 网点类型
	 */
	@Bind("branchTypeCH")
	private JTextField txtBranchType;
	/**
	 * 正单名称
	 */
	@Bind("airBillName")
	private JTextField txtAirBillName;
	/**
	 * 正单联系电话
	 */
	@Bind("airBillPhone")
	private JTextField txtAirBillPhone;
	/**
	 * 网点联系电话
	 */
	@Bind("phone")
	private JTextField txtPhone;
	/**
	 * 网点地址
	 */
	@Bind("branchAddress")
	private JTextField txtBranchAddress;
	/**
	 * 搜索关键字
	 */
	@Bind("key")
	private JTextField txtKey;
	
	/**
	 * 汽运偏线网点
	 */
	@Bind("branchTypeSeach#PX")
	private JRadioButton rbnPLFBranch;
	
	/**
	 * 空运网点
	 */
	@Bind("branchTypeSeach#KY")
	private JRadioButton rbnAirBranch;
	
	/**
	 * 自提
	 */
	@Bind("chbPickup")
	private JCheckBox chbPickup;
	
	/**
	 * 派送
	 */
	@Bind("chbDeliver")
	private JCheckBox chbDeliver;
	
	
	/**
	 * 自提
	 */
	@Bind("chbPickupUi")
	private JCheckBox chbPickupTwo;
	
	
	/**
	 * 派送
	 */
	@Bind("chbDeliverUi")	
	private JCheckBox chbDeliverTwo;
	
	/**
	 * 货到付款
	 */
	@Bind("chbArrivePaymentUi")
	private JCheckBox chbArrivePayment;
	
	/**
	 * 返单签收
	 */
	@Bind("chbReturnBillUi")
	private JCheckBox chbReturnBill;
	
	/**
	 * 代收货款
	 */
	@Bind("chbCodUi")
	private JCheckBox chbCod;
	
	/**
	 * 精准卡航
	 */
	@Bind("chbFLF")
	private JCheckBox chbFLF;
	
	/**
	 * 精准城运
	 */
	@Bind("chbFSF")
	private JCheckBox chbFSF;
	
	/**
	 * 精准汽运（长）
	 */
	@Bind("chbLRF")
	private JCheckBox chbLRF;
	
	
	/**
	 * 精准汽运（短）
	 */
	@Bind("chbSRF")
	private JCheckBox chbSRF;
	
	/**
	 * 精准空运
	 */
	@Bind("chbAF")
	private JCheckBox chbAF;
	
	/**
	 * 表格区域
	 */
	private JScrollPane scrollPane;
	
	/**
	 * 关键字查询
	 */
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = QueryKeyAction.class)
	private JButton btnKeyQuery;
	
	@Bind("index")
	private JTextField indexField;
	
	@Bind("rowIndex")
	private JTextField rowIndexField;
	
	/**
	 * 根据目的站查询网点信息
	 */
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = QueryBranchByNameAction.class)
	private JButton btnQuery;
	
	/**
	 * 结果列表
	 */
	private JXTable table;
	
	private BranchQueryVo branchQueryVo;
	
	private BranchQueryVo returnBranchQueryVo;
	
	public BranchQueryVo getReturnBranchQueryVo() {
		return returnBranchQueryVo;
	}

	// 绑定接口
	private IBinder<BranchQueryVo> branchQuery;
	
	private Map<String, IBinder<BranchQueryVo>> bindersMap = new HashMap<String, IBinder<BranchQueryVo>>();
	
	/**
	 * 自提区域
	 */
	@Bind("pickupAreaDesc")
	private JEditorPane textAreaPickup;
	
	private JScrollPane deliveryPanel;
	
	private JScrollPane areaPickupPanel;
	/**
	 * 派送区域
	 */
	@Bind("deliveryAreaDesc")
	private JEditorPane textAreaDelivery;
	
	/**
	 * 进仓区域 update by tdg
	 * @date 2016年9月7日10:00:15
	 * @author 354805 (taodongguo)
	 */
	@Bind("warehouseAreaDesc")
	private JEditorPane textAreaWarehouse;
	
	private JScrollPane areaWareHousePanel;
	
	@Bind("branchTypeSeach#HIGHWAYS")
	private JRadioButton radioButton;
	
	private JTextField textField;
	private JTextField textField_1;
	/*private JTextField textField;
	private JTextField textField_1;*/
	
	//派送自提切换面板
	private JTabbedPane tabbedPane;
	
	 /**
		 * 精准大票卡航
	 */
	@Bind("chbBGFLF")
	private JCheckBox chbBGFLF;
	/**
	 * 精准大票汽运(长)
	 */
	@Bind("chbBGLRF")
	private JCheckBox chbBGLRF;
	/**
	 * 精准大票城运
	 */
    @Bind("chbBGFSF")
	private JCheckBox chbBGFSF;
	/**
	 *精准大票汽运(短)
	 */
	@Bind("chbBGSRF")
	private JCheckBox chbBGSRF;

	@Bind("chbDTD")
	private JCheckBox chbDTD;
	@Bind("chbYTY")
	private JCheckBox chbYTY;
	/**
	 * Create the panel.
	 */
	public PickupGoodsBranchDialog() {
		//初始化表格
		initTable();
		//初始化界面
		init();
		//绑定
		bind();
		//勾选默认值
		setDefaultValue();
		//注册绑定对象
		registToRespository();
		//监听Enter
		EnterKeyQueryBranch enter=new EnterKeyQueryBranch(btnQuery);
		txtQueryName.addKeyListener(enter);
		EnterKeyQueryBranch enterTable=new EnterKeyQueryBranch(this);
		table.addKeyListener(enterTable);
		//禁用table表格上Enter键转到下一行，因为Enter键是将选中的那条记录详细信息显示在右边，如果转到下一行，则左右两边不符
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");		
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK, false), "Shift+Enter");
	}
	
	/**
	 * 构造函数
	 * @author WangQianJin
	 * @date 2013-7-12 上午10:36:15
	 */
	public PickupGoodsBranchDialog(WaybillPanelVo bean) {
		//初始化表格
		initTable();
		//初始化界面
		init();
		//绑定
		bind();
		//勾选默认值
//		setDefaultValue();
		//注册绑定对象
		registToRespository();
		//监听Enter
		EnterKeyQueryBranch enter=new EnterKeyQueryBranch(btnQuery);
		txtQueryName.addKeyListener(enter);
		EnterKeyQueryBranch enterTable=new EnterKeyQueryBranch(this);
		table.addKeyListener(enterTable);
		//禁用table表格上Enter键转到下一行，因为Enter键是将选中的那条记录详细信息显示在右边，如果转到下一行，则左右两边不符
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");		
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK, false), "Shift+Enter");
		//打开窗口时设置默认运输性质及提货方式
		setDefaultProduct(bean);
	}
	
	/**
	 * 设置默认运输性质及提货方式
	 * @author WangQianJin
	 * @date 2013-7-12 上午10:39:38
	 */
	public void setDefaultProduct(WaybillPanelVo bean){
		//运输性质
		if(bean.getProductCode()!=null){
			if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean.getProductCode().getCode())){
				//偏线
				rbnPLFBranch.setSelected(true);
			}else if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())){
				//空运
				rbnAirBranch.setSelected(true);
			}else{
				radioButton.setSelected(true);
			}
			if(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG.equals(bean.getProductCode().getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG.equals(bean.getProductCode().getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG.equals(bean.getProductCode().getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG.equals(bean.getProductCode().getCode())
					|| ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(bean.getProductCode().getCode())  //门对门
					|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(bean.getProductCode().getCode())){//场对场
				branchQueryVo.setIsBigGoods(true);
			}else{
				branchQueryVo.setIsBigGoods(false);
			}
		}
		//提货方式
		if(bean.getReceiveMethod()!=null){			
			
			/**
			 * DEFECT-6897，根据业务要求增加判断条件，其它不变
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-01-19下午18:07
			 */
			if(WaybillConstants.DELIVER_NOUP.equals(bean.getReceiveMethod().getValueCode())
					 ||	WaybillConstants.DELIVER_FREE.equals(bean.getReceiveMethod().getValueCode())
					 ||	WaybillConstants.DELIVER_STORAGE.equals(bean.getReceiveMethod().getValueCode())
					 ||	WaybillConstants.DELIVER_UP.equals(bean.getReceiveMethod().getValueCode())
					 ||	WaybillConstants.DELIVER_FREE_AIR.equals(bean.getReceiveMethod().getValueCode())
					 ||	WaybillConstants.DELIVER_NOUP_AIR.equals(bean.getReceiveMethod().getValueCode())
					 ||	WaybillConstants.DELIVER_UP_AIR.equals(bean.getReceiveMethod().getValueCode())
					 ||	WaybillConstants.DELIVER_INGA_AIR.equals(bean.getReceiveMethod().getValueCode())
					 ||	WaybillConstants.LARGE_DELIVER_UP.equals(bean.getReceiveMethod().getValueCode())
					 ||	WaybillConstants.LARGE_DELIVER_UP_AIR.equals(bean.getReceiveMethod().getValueCode())){
				//自提
				chbPickup.setSelected(false);
				//派送
				chbDeliver.setSelected(true);
			}else{
				//自提
				chbPickup.setSelected(true);
				//派送
				chbDeliver.setSelected(false);
			}
		}
	}
	
	private void setDefaultValue(){
		radioButton.setSelected(true);
		chbPickup.setSelected(true);
	}
	
	/**
	 * 获得保存对象
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-27 上午10:06:25
	 */
	public HashMap<String, IBinder<BranchQueryVo>> getBindersMap() {
		return( HashMap<String, IBinder<BranchQueryVo>>) bindersMap;
	}
	
	/**
	 * 
	 * 初始化界面
	 * @author 025000-FOSS-helong
	 * @date 2013-3-4 下午02:47:25
	 */
	private void init()
	{
		setSize(NumberConstants.NUMBER_1000, NumberConstants.NUMBER_700);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(90dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(19dlu;pref)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(22dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(42dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(53dlu;default)"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.PickupGoodsBranchDialog.panel.title"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setToolTipText("");
		getContentPane().add(panel, "2, 1, 1, 14, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(0dlu;default):grow"),
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		scrollPane = new JScrollPane(table);
		
		scrollPane.setBackground(Color.ORANGE);
		//scrollPane.setVisible(false);
		panel.add(scrollPane, "1, 1, 10, 16, fill, fill");
		
		JLabel lblNewLabel2 = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.pickup"));
		panel.add(lblNewLabel2, "2, 18, right, default");
		
		textField = new JTextField();
		textField.setBackground(Color.PINK);
		textField.setEnabled(false);
		panel.add(textField, "5, 18, fill, default");
		textField.setColumns(TEN);
		
		JLabel lblNewLabel3 = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.deliver"));
		panel.add(lblNewLabel3, "7, 18, right, default");
		
		textField_1 = new JTextField();
		textField_1.setBackground(Color.GREEN);
		textField_1.setEnabled(false);
		panel.add(textField_1, "9, 18, fill, default");
		textField_1.setColumns(TEN);
		//网点名称：
		JLabel label7 = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.branchname"));
		getContentPane().add(label7, "4, 2, left, default");
		
		txtQueryName = new JTextField();
		getContentPane().add(txtQueryName, "6, 2, 3, 1, fill, default");
		txtQueryName.setColumns(TEN);
		//查询
		btnQuery = new JButton();
		getContentPane().add(btnQuery, "9, 2");
		btnQuery.setMargin(new Insets(-2, 1, -2, 1));
		
		radioButton = new JRadioButton(i18n.get("foss.gui.creating.PickupGoodsBranchDialog.radioButton.title"), true);
		getContentPane().add(radioButton, "13, 2");
		//汽运偏线
		rbnPLFBranch = new JRadioButton(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.PLFBranch"));
		getContentPane().add(rbnPLFBranch, "15, 2");
		//空运
		rbnAirBranch = new JRadioButton(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.airBranch"));
		getContentPane().add(rbnAirBranch, "16, 2");
		//rbnHighwaysBranch.setSelected(true);
		//btnGroupRequire.setSelected(rbnHighwaysBranch.getModel(), true);
		
		/*ButtonGroup btnGroupRequire = new ButtonGroup();
		btnGroupRequire.add(rbnPLFBranch);
		btnGroupRequire.add(rbnAirBranch);
		btnGroupRequire.add(radioButton);*/
		//自提
		chbPickup = new JCheckBox(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.pickup"));
		getContentPane().add(chbPickup, "20, 2");
		
		//派送
		chbDeliver = new JCheckBox(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.deliver"));
		getContentPane().add(chbDeliver, "22, 2");
		
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.branchNumber"));
		getContentPane().add(lblNewLabel, "4, 4, left, default");
		//网点编码
		txtBranchNumber = new JTextField();
		getContentPane().add(txtBranchNumber, "6, 4, 4, 1, fill, default");
		txtBranchNumber.setColumns(TEN);
		txtBranchNumber.setEditable(false);
		
		JLabel lblNewLabel4 = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.branchName"));
		getContentPane().add(lblNewLabel4, "13, 4, left, default");
		
		//网点名称
		txtBranchName = new JTextField();
		getContentPane().add(txtBranchName, "15, 4, 2, 1, fill, default");
		txtBranchName.setColumns(TEN);
		txtBranchName.setEditable(false);
		
		JLabel label = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.targetOrgSimpleName"));
		getContentPane().add(label, "20, 4, left, default");
		
		//目的站
		txtTargetOrgName = new JTextField();
		getContentPane().add(txtTargetOrgName, "22, 4, 3, 1, fill, default");
		txtTargetOrgName.setColumns(TEN);
		txtTargetOrgName.setEditable(false);
		
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.province"));
		getContentPane().add(label1, "4, 6, left, default");
		
		//省份
		txtProvince = new JTextField();
		getContentPane().add(txtProvince, "6, 6, 4, 1, fill, default");
		txtProvince.setColumns(TEN);
		txtProvince.setEditable(false);
		
		JLabel label2 = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.city"));
		getContentPane().add(label2, "13, 6, left, default");
		
		//城市
		txtCity = new JTextField();
		getContentPane().add(txtCity, "15, 6, 2, 1, fill, default");
		txtCity.setColumns(TEN);
		txtCity.setEditable(false);
		
		JLabel label3 = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.branchType"));
		getContentPane().add(label3, "20, 6, left, default");
		
		//网点类别
		txtBranchType = new JTextField();
		getContentPane().add(txtBranchType, "22, 6, 3, 1, fill, default");
		txtBranchType.setColumns(TEN);
		txtBranchType.setEditable(false);
		
		JLabel label4 = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.airBillName"));
		getContentPane().add(label4, "4, 8, left, default");
		
		//正单开单名称
		txtAirBillName = new JTextField();
		getContentPane().add(txtAirBillName, "6, 8, 4, 1, fill, default");
		txtAirBillName.setColumns(TEN);
		txtAirBillName.setEditable(false);
		
		JLabel label5 = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.AirBillPhone"));
		getContentPane().add(label5, "13, 8, left, default");
		
		//正单联系电话
		txtAirBillPhone = new JTextField();
		getContentPane().add(txtAirBillPhone, "15, 8, 2, 1, fill, default");
		txtAirBillPhone.setColumns(TEN);
		txtAirBillPhone.setEditable(false);
		
		JLabel label6 = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchDialog.label6.title"));
		getContentPane().add(label6, "20, 8, left, default");
		
		//联系电话
		txtPhone = new JTextField();
		getContentPane().add(txtPhone, "22, 8, 3, 1, fill, default");
		txtPhone.setColumns(TEN);
		txtPhone.setEditable(false);
		
		JLabel lblNewLabel5 = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchDialog.lblNewLabel5.title"));
		getContentPane().add(lblNewLabel5, "4, 10, left, default");
		
		//提货网点地址
		txtBranchAddress = new JTextField();
		getContentPane().add(txtBranchAddress, "6, 10, 19, 1, fill, default");
		txtBranchAddress.setColumns(TEN);
		txtBranchAddress.setEditable(false);
		
		JPanel panel6 = new JPanel();
		getContentPane().add(panel6, "4, 12, 21, 1, fill, fill");
		panel6.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("130px"),
				ColumnSpec.decode("15dlu:grow"),
				ColumnSpec.decode("260px:grow"),
				ColumnSpec.decode("center:19px"),
				ColumnSpec.decode("350px:grow"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("35dlu"),
				RowSpec.decode("35dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JPanel panel5 = new JPanel();
		panel6.add(panel5, "1, 2, left, top");
		panel5.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.PickupGoodsBranchDialog.panel5.title"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel5.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(29dlu;default)"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		chbPickupTwo = new JCheckBox(i18n.get("foss.gui.creating.PickupGoodsBranchPanel.pickup"));
		chbPickupTwo.setEnabled(false);
		panel5.add(chbPickupTwo, "2, 2");
		
		chbDeliverTwo = new JCheckBox(i18n.get("foss.gui.creating.PickupGoodsBranchDialog.chbDeliverTwo"));
		chbDeliverTwo.setEnabled(false);
		panel5.add(chbDeliverTwo, "4, 2");
		
		JPanel panel7 = new JPanel();
		panel7.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.PickupGoodsBranchDialog.panel7.title"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel6.add(panel7, "3, 2, left, top");
		panel7.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		chbArrivePayment = new JCheckBox(i18n.get("foss.gui.creating.PickupGoodsBranchDialog.chbArrivePayment"));
		chbArrivePayment.setEnabled(false);
		panel7.add(chbArrivePayment, "2, 2");
		
		chbReturnBill = new JCheckBox(i18n.get("foss.gui.creating.PickupGoodsBranchDialog.chbReturnBill"));
		chbReturnBill.setEnabled(false);
		panel7.add(chbReturnBill, "4, 2");
		
		chbCod = new JCheckBox(i18n.get("foss.gui.creating.incrementPanel.cashOnDelivery.label"));
		chbCod.setEnabled(false);
		panel7.add(chbCod, "6, 2");
		
		JPanel panel1 = new JPanel();
		panel1.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.PickupGoodsBranchDialog.panel1"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel6.add(panel1, "5, 2, 1, 2, left, top");
		panel1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		chbFLF = new JCheckBox(i18n.get("foss.gui.creating.product.airTruck"));
		chbFLF.setEnabled(false);
		panel1.add(chbFLF, "2, 2");
		
		chbFSF = new JCheckBox(i18n.get("foss.gui.creating.product.accurateCity"));
		chbFSF.setEnabled(false);
		panel1.add(chbFSF, "4, 2");
		
		chbLRF = new JCheckBox(i18n.get("foss.gui.creating.product.accurateLong"));
		chbLRF.setEnabled(false);
		panel1.add(chbLRF, "6, 2");
		
		chbSRF = new JCheckBox(i18n.get("foss.gui.creating.product.accurateShort"));
		chbSRF.setEnabled(false);
		panel1.add(chbSRF, "2, 4");
		
		chbAF = new JCheckBox(i18n.get("foss.gui.creating.product.accurateAir"));
		chbAF.setEnabled(false);
		panel1.add(chbAF, "4, 4");
		
		chbBGFLF = new JCheckBox(i18n.get("foss.gui.creating.product.bgflf"));
		chbBGFLF.setEnabled(false);
		panel1.add(chbBGFLF, "6, 4");
		
		chbBGFSF = new JCheckBox(i18n.get("foss.gui.creating.product.bgfsf"));
		chbBGFSF.setEnabled(false);
		panel1.add(chbBGFSF, "2, 6");
		
		chbBGLRF = new JCheckBox(i18n.get("foss.gui.creating.product.bglrf"));
		chbBGLRF.setEnabled(false);
		panel1.add(chbBGLRF, "4, 6");
		
		chbBGSRF = new JCheckBox(i18n.get("foss.gui.creating.product.bgsrf"));
		chbBGSRF.setEnabled(false);
		panel1.add(chbBGSRF, "6, 6");
		
		//wutao -- 门对门
		chbDTD = new JCheckBox(i18n.get("foss.gui.creating.product.bgdtd"));
		chbDTD.setEnabled(false);
		panel1.add(chbDTD,"2,8");
		
		//wutao -- 场对场
		chbYTY = new JCheckBox(i18n.get("foss.gui.creating.product.bgyty"));
		chbYTY.setEnabled(false);
		panel1.add(chbYTY,"4,8");
		
		
		JPanel panel8 = new JPanel();
		panel8.setBorder(new TitledBorder(null, i18n.get("foss.gui.creating.PickupGoodsBranchDialog.panel8"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel6.add(panel8, "1, 3, 3, 1, fill, fill");
		panel8.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("80dlu"),
				ColumnSpec.decode("120dlu:grow"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel6 = new JLabel(i18n.get("foss.gui.creating.PickupGoodsBranchDialog.lblNewLabel6"));
		panel8.add(lblNewLabel6, "1, 2");
		
		txtKey = new JTextField();
		panel8.add(txtKey, "2, 2");
		txtKey.setColumns(TEN);
		
		btnKeyQuery = new JButton("");
		btnKeyQuery.setMargin(new Insets(-1, 1, -1, 1));
		panel8.add(btnKeyQuery, "3, 2");
		
		//字符索引隐藏域
		indexField = new JTextField();
		indexField.setVisible(false);
		indexField.setHorizontalAlignment(SwingConstants.LEFT);
		panel6.add(indexField, "2, 5, fill, default");
		indexField.setColumns(TEN);
		
		//行索引隐藏域
		rowIndexField = new JTextField();
		rowIndexField.setVisible(false);
		panel6.add(rowIndexField, "3, 5, left, default");
		rowIndexField.setColumns(TEN);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, "4, 14, 21, 1, fill, fill");
		
		//派送区域
		textAreaDelivery = new JEditorPane(){
			public void setText(String text){
				//替换空格换行
				//DEFECT-8428 查询网点报错
				if(StringUtil.isNotEmpty(text)){
					text = text.replaceAll(" ","&nbsp;&nbsp;");
					text = text.replaceAll("\n","<br/>");
				}
				super.setText(text);
			}
		};		
		textAreaDelivery.setContentType("text/html");
		textAreaDelivery.setEditable(false);
		textAreaDelivery.setBorder(null);
		deliveryPanel = new JScrollPane(textAreaDelivery);
		deliveryPanel.setAutoscrolls(true);
		deliveryPanel.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		deliveryPanel.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);				
		tabbedPane.addTab(i18n.get("foss.gui.creating.PickupGoodsBranchDialog.tabbedPane"), null, deliveryPanel, null);
		
		//进仓区域
		textAreaWarehouse = new JEditorPane(){
			public void setText(String text) {
				//替换空格、换行
				if(StringUtil.isNotEmpty(text)){
					text = text.replaceAll(" ", "&nbsp;&nbsp;");
					text = text.replaceAll("\n", "<br/>");
				}
				super.setText(text);
			};
		};
		textAreaWarehouse.setContentType("text/html");
		textAreaWarehouse.setEditable(false);
		textAreaWarehouse.setBorder(null);
		areaWareHousePanel = new JScrollPane(textAreaWarehouse);
		areaWareHousePanel.setAutoscrolls(true);
		areaWareHousePanel.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		areaWareHousePanel.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
		tabbedPane.addTab(i18n.get("foss.gui.creating.PickupGoodsBranchDialog.tabbedPane3"), null, areaWareHousePanel, null);
				
		
		//自提区域
		textAreaPickup = new JEditorPane(){
			public void setText(String text){
				//替换空格换行
				//DEFECT-8428 查询网点报错
				if(StringUtil.isNotEmpty(text)){
					text = text.replaceAll(" ","&nbsp;&nbsp;");
					text = text.replaceAll("\n","<br/>");
				}
				super.setText(text);
			}
		};
		textAreaPickup.setContentType("text/html");
		textAreaPickup.setEditable(false);
		textAreaPickup.setBorder(null);
		areaPickupPanel = new JScrollPane(textAreaPickup);
		areaPickupPanel.setAutoscrolls(true);
		areaPickupPanel.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		areaPickupPanel.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
		tabbedPane.addTab(i18n.get("foss.gui.creating.PickupGoodsBranchDialog.tabbedPane2"), null, areaPickupPanel, null);
		
	}
	
	
	/**
	 * 
	 * 对按钮、文本框等控件进行绑定
	 * @author 025000-FOSS-helong
	 * @date 2013-3-4 下午06:03:22
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		branchQuery = BindingFactory.getIntsance().moderateBind(
				BranchQueryVo.class, this, null, true);
		branchQueryVo = branchQuery.getBean();
		
	}
	
	/**
	 * 
	 * 保存绑定对象
	 * @author 025000-FOSS-helong
	 * @date 2013-3-4 下午06:03:13
	 */
	public void registToRespository() {
		bindersMap.put("branchQuery", branchQuery);
	}
	
	class JXCellTable  extends JXTable{
		  /**
		 * 
		 */
		private static final long serialVersionUID = 2224404412551404186L;

		public TableCellRenderer getCellRenderer(int row, int column) {
		       return new ColorRender();
		    }
	}
	
	/**
	 * 
	 * 初始化表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午10:47:03
	 */
	private void initTable() {
		//初始化表格
		table = new JXCellTable();
		//设置表格数据模型
		tableModel = new  BranchDataModel();
		table.setModel(tableModel);
		//设置表格可以有滚动条
		table.setAutoscrolls(true);
		//禁止表格排序
		table.setSortable(false);
		//表格设置为不可编辑
		table.setEditable(false);
		//设置列可见状态
		table.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, new ColorRender());
		((DefaultTableCellRenderer) table.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		table.addMouseListener(new ClickTableHandler());
	}
	
	public class ColorRender extends DefaultTableCellRenderer {
		
		/* public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
		       Component cell = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);  
		        this.setColor(cell, table, isSelected, hasFocus, row, column);
		            return cell;
		        }
		        
		         * 设置颜色
		         
		        private void setColor(Component component,JTable table,boolean isSelected,boolean hasFocus,int row,int column){
		        if(isSelected){
		            component.setBackground(Color.green);
		            setBorder(null);//去掉边
		        }else{
		        	
		            if(row%2 == 0){
		               component.setBackground(Color.pink);  
		            }else{
		               component.setBackground(Color.white);
		            }
		        }
		        }*/

		private static final long serialVersionUID = 925311539841066645L;

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
	        Component cell = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);  

			 this.setColor(cell, table, isSelected, hasFocus, row, column);
	            return cell;
	        }
		 
         
        private void setColor(Component component,JTable table,boolean isSelected,boolean hasFocus,int row,int column){
        	//String checkStatus = value.toString();// getChangeDealStatus();
        	int modelRow = table.convertRowIndexToModel(row);
        	BranchQueryVo selectVo = tableModel.getData().get(modelRow);
        	if (!isSelected) {
				if (FossConstants.YES.equals(selectVo.getChbDeliverTwo())) {
					component.setBackground(Color.GREEN);
				} else if (FossConstants.YES.equals(selectVo.getChbPickupTwo())
						&& !FossConstants.YES.equals(selectVo.getChbDeliverTwo())) {
					component.setBackground(Color.PINK);
				} else{
					component.setBackground(Color.white);
				}
			}
        	
			
        }
	}
	
	public static void main(String[] agrs)
	{
	
		(new PickupGoodsBranchDialog()).setVisible(true)
		;
	
	}
	
	/**
	 * 结果表格Enter键监控
	 * @author WangQianJin
	 * @date 2013-5-20 上午11:43:06
	 */
	public void tableEnterListener() {
		int row = table.getSelectedRow();
		BranchQueryVo vo = getBindersMap().get("branchQuery").getBean();
		if (row > -1) {
			int modelRow = table.convertRowIndexToModel(row);
			BranchQueryVo selectVo = ((BranchDataModel)table.getModel()).getData().get(modelRow);
			QueryBranchByNameAction.getRowDate(vo, selectVo,row);			
		}
	}
	
	/**
	 * 
	 * 表格点击时间
	 * @author 025000-FOSS-helong
	 * @date 2013-3-4 下午02:20:56
	 */
	private class ClickTableHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			BranchQueryVo vo = getBindersMap().get("branchQuery").getBean();
			if (row > -1) {
				int modelRow = table.convertRowIndexToModel(row);
				BranchQueryVo selectVo = ((BranchDataModel)table.getModel()).getData().get(modelRow);
				// 单击
				if (e.getClickCount() == 1) {
					QueryBranchByNameAction.getRowDate(vo, selectVo,row);
				}else if (e.getClickCount() == 2) {
					try {
						if(returnBranchQueryVo == null)
						{
							returnBranchQueryVo = new BranchQueryVo();
						}
						PropertyUtils.copyProperties(returnBranchQueryVo, vo);
					} catch (IllegalAccessException e1) {
						LOG.info("PropertyUtils.copyProperties exception:"+e1);
					} catch (InvocationTargetException e1) {
						LOG.info("PropertyUtils.copyProperties exception:"+e1);
					} catch (NoSuchMethodException e1) {
						LOG.info("PropertyUtils.copyProperties exception:"+e1);
					}
					dispose();
				}
			}
		}
	}
	
	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setData(List<BranchQueryVo> branchQueryVO) {
		BranchDataModel model = ((BranchDataModel) table.getModel());
		model.setData(branchQueryVO);
		// 刷新表格数据
		model.fireTableDataChanged();
	}
	
	
	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class BranchDataModel extends DefaultTableModel {

		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 表格数据集合
		 */
		private List<BranchQueryVo> data;
		
		/**
		 * 表格数据集合
		 */
		public List<BranchQueryVo> getData() {
			return data;
		}

		/**
		 * 表格数据集合
		 */
		public void setData(List<BranchQueryVo> data) {
			this.data = data;
		}

		/**
		 * 
		 * 重写表格获取列名称方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:03:18
		 * @param column
		 * @return
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.creating.PickupGoodsBranchPanel.targetOrgSimpleName.column");
			case 1:
				return i18n.get("foss.gui.creating.PickupGoodsBranchPanel.targetOrgName.column");
			default:
				return "";
			}
		}

		/**
		 * 
		 * 重写表格获取列总数方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:03:18
		 * @param column
		 * @return
		 */
		@Override
		public int getColumnCount() {
			return 2;
		}

		
		/**
		 * 
		 * 重写表格获取行总数方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:03:18
		 * @param column
		 * @return
		 */
		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		

		/**
		 * 
		 * 重写获取数据的方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:08:19
		 * @param row
		 * @param column
		 * @return
		 */
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return data.get(row).getTargetOrgName();
			case 1:
				return data.get(row).getName();
			default:
				return super.getValueAt(row, column);
			}
		}
	}


	public JTextField getTxtQueryName() {
		return txtQueryName;
	}

	public JXTable getTable() {
		return table;
	}

	public IBinder<BranchQueryVo> getBranchQuery() {
		return branchQuery;
	}

	public void setBranchQuery(IBinder<BranchQueryVo> branchQuery) {
		this.branchQuery = branchQuery;
	}

	public BranchQueryVo getBranchQueryVo() {
		return branchQueryVo;
	}

	public JEditorPane getTextAreaPickup() {
		return textAreaPickup;
	}

	public JEditorPane getTextAreaDelivery() {
		return textAreaDelivery;
	}

	public JTextField getIndexField() {
		return indexField;
	}

	public JTextField getRowIndexField() {
		return rowIndexField;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public JEditorPane getTextAreaWarehouse() {
		return textAreaWarehouse;
	}
	
	
}