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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/QueryPublishPriceUI.java
 * 
 * FILE NAME        	: QueryPublishPriceUI.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.ui;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang.StringUtils;
import org.java.plugin.Plugin;
import org.jdesktop.swingx.JXTable;
import org.jfree.util.Log;

import com.deppon.foss.framework.client.commons.binding.validation.annotations.NotNull;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArg;
import com.deppon.foss.framework.client.core.binding.annotation.BindingArgs;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.action.ExitAction;
import com.deppon.foss.module.pickup.common.client.action.QueryPublishPriceAction;
import com.deppon.foss.module.pickup.common.client.action.QuerySalesDepartmentCreateAction;
import com.deppon.foss.module.pickup.common.client.action.QuerySalesDepartmentTargetAction;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.IOrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.impl.OrgInfoService;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.QueryPublishPriceVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PopPublicPriceDto;
import com.deppon.foss.module.pickup.pricing.server.service.impl.ProductService;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 查询公布价界面
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,content:
 * </p>
 * @author 105089-FOSS-shixw
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class QueryPublishPriceUI extends JFrame {

	/**
	 * 国际化对象到达部门
	 */
	private static final String INTARGETORGCODE = "foss.gui.creating.queryPublishPriceUI.targetOrgCode.label";

	/**
	 * 退出
	 */
	private static final String INCOMMONQUIT = "foss.gui.common.waybillEditUI.common.quit";

	/**
	 * 布局列宽
	 */
	private static final String DEFAULTGROW = "default:grow";


	/**
	 * 国际化对象C出发部门
	 */
	private static final String INCREATEORGCODELABEL = "foss.gui.creating.queryPublishPriceUI.createOrgCode.label";

	/**
	 *  国际化对象查询结果
	 */
	private static final String INQUERYRESULT = "foss.gui.creating.queryPublishPriceUI.queryResult.label";

	/**
	 * 国际化对象 查询条件
	 */
	private static final String INQUERYCONDITION = "foss.gui.creating.queryPublishPriceUI.queryCondition.label";

	/**
	 * 国际化对象
	 */
	private static final String INCOMMONQUERY = "foss.gui.common.waybillEditUI.common.query";

	/**
	 * gif picture
	 */
	private static final String SEARCH16GIF = "Search16.gif";
	

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	

	//15
	private static final int FIFIVETEEN = 15;
	
	private static final int THREE = 3;
	
	private static final int FOUR = 4;
	
	private static final int SEVEN = 7;
	
	private static final int ELEVEN = 11;
	
	private static final int NINETEEN = 19;
	
	/**
	 * field name
	 */
	private static final String FIELDNAME = "fieldName";

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(this.getClass()); 
	
	
	/**
	 * 基础数据查询服务类
	 * 用于查询基础数据等信息
	 */
	private IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);
	
	
	/**
	 * 出发部门是指货物运输的出发部门
	 */
	@Bind("createOrgCode")
	@NotNull()
	@BindingArgs({@BindingArg(name=FIELDNAME,value="出发地")})
	private JTextField txtCreateOrgCode;
	
	/**
	 * 出发部门是指货物运输的出发部门
	 */
	@Bind("createOrgName")
	private JTextField txtCreateOrgName;
	
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = QuerySalesDepartmentCreateAction.class)
	private JButton btnQueryCreateOrgCode;
	
	/**
	 * 目的地是指货物运输的目的地
	 */
	@Bind("targetOrgCode")
	@NotNull()
	@BindingArgs({@BindingArg(name=FIELDNAME,value="目的地")})
	private JTextField	txtTargetOrgCode;
	
	/**
	 * 出发部门是指货物运输的出发部门
	 */
	@Bind("targetOrgName")
	private JTextField txtTargetOrgName;
	
	
	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = QuerySalesDepartmentTargetAction.class)
	private JButton btnQueryTargetOrgCode;

	/**
	 * 结果表
	 */
	private JXTable table = new JXTable();
	
	/**
	 * 表外滚动条
	 */
	private JScrollPane scrollPane;
	 
	/**
	 * table panel
	 */
	private JPanel tablePanel;
	
	
	/**
	 * plugin of GUI framework
	 */
	private Plugin plugin;
	
	/**
	 * 查询
	 */
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type =QueryPublishPriceAction.class)
	private JButton searchButton = new JButton(i18n.get(INCOMMONQUERY));

	
	/**
	 * 绑定接口
	 */
	private IBinder<QueryPublishPriceVo>  queryPublishPriceBinder;
		
	/**
	 * 绑定MAP
	 */
	private Map<String, IBinder<QueryPublishPriceVo>> bindersMap = new HashMap<String, IBinder<QueryPublishPriceVo>>();
	
	 /**
	  * 主界面
	  */
	
	/**
	 * vo
	 */
	private QueryPublishPriceVo vo;
	
	/**
	 * 窗口应用程序
	 */
	private IApplication application;
	
	/**
	 * 退出
	 */
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "" , type =ExitAction.class)
	private JButton btnExit;


	/**
	 * 数据字典服务类
	 */
	private IDataDictionaryValueService dataDictionaryValueService =GuiceContextFactroy.getInjector().getInstance(DataDictionaryValueService.class);
	
	/**
	 * 运输性质
	 */
	private DefaultComboBoxModel productTypeModel;
	
	
	/**
	 * 提货方式
	 */
	private DefaultComboBoxModel pikcModeModel;

	/**
	 * 数据字典
	 */
	private List<DataDictionaryValueEntity>  dataDictionaryValueEntity; 
	
	/**
	 * 构造方法
	 */
	public QueryPublishPriceUI() {
		init();//初始化界面信息
		//初始化产品类型
		initComboBox();// 产品类型
		bind();
		registToRespository();
		initVo();
	}
	
	/**
	 * 
	 * 初始化下拉框
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-6 上午10:14:38
	 */
	private void initComboBox() {
		// 产品类型
		initCombProductType();
	}
	
	/**
	 * 
	 * <p>
	 * (初始化产品类型)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-16 下午04:37:50
	 * @see
	 */
	private void initCombProductType(){
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		productTypeModel = new DefaultComboBoxModel();
		//根据当前用户所在部门查询部门所属产品
		setProductTypeModel(dept.getCode());
	}
	
	
	/**
	 * 
	 * 设置产品到数据模型
	 * @author 025000-FOSS-helong
	 * @date 2013-1-16 下午02:18:34
	 */
	public void setProductTypeModel(String deptCode){
		List<ProductEntity> list = baseDataService.queryTransType(deptCode);
		for (ProductEntity product : list) {
			if(!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(product.getCode())){
				ProductEntityVo vo2 = new ProductEntityVo();
				//将数据库查询出的产品对象进行转换，转成VO使用的对象
				ValueCopy.entityValueCopy(product, vo2);
				vo2.setDestNetType(product.getDestNetType());
				productTypeModel.addElement(vo2);
			}
		}
	}

	/**
	 * init vo
	 */
	protected void initVo() {
		Log.debug(vo);
		//获得数据字典 service
		dataDictionaryValueEntity = dataDictionaryValueService.queryByTermsCode(DictionaryConstants.AIR_FLIGHT_TYPE);
	}

	/**
	 * 
	 * @description：保存绑定对象
	 * @date 2012-7-19
	 * @author yangtong
	 */
	private void registToRespository() {
		bindersMap.put("queryPublishPriceBinder", queryPublishPriceBinder);
	}

	/**
	 * 初始化界面信息
	 */
	private void init() {
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("fill:max(75dlu;default):grow"),}));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, i18n.get(INQUERYCONDITION), 
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel, "1, 2, fill, default");
		FormLayout flpanel = new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(53dlu;default)"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("max(116dlu;default)"),
				ColumnSpec.decode("center:max(19dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(47dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(53dlu;default)"),
				ColumnSpec.decode("max(119dlu;default)"),
				ColumnSpec.decode("max(38dlu;default)"),
				ColumnSpec.decode("max(7dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("max(8dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(51dlu;default):grow"),},
			new RowSpec[] {
				RowSpec.decode("max(10dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(16dlu;default)"),});
		flpanel.setColumnGroups(new int[][]{new int[]{NINETEEN, ELEVEN, SEVEN, THREE}});
		panel.setLayout(flpanel);
		
		JLabel label1 = new JLabel(i18n.get(INCREATEORGCODELABEL));
		panel.add(label1, "1, 3, center, default");
		
		txtCreateOrgName = new JTextField();
		panel.add(txtCreateOrgName, "3, 3, 2, 1");
		txtCreateOrgName.setColumns(FIFIVETEEN);
		txtCreateOrgName.setEditable(false);
		
		btnQueryCreateOrgCode = new JButton();
		btnQueryCreateOrgCode.setMargin(new Insets(-2, 1, -2, 1));
		panel.add(btnQueryCreateOrgCode, "5, 3, left, default");
		
		
		txtCreateOrgCode = new JTextFieldValidate();
		panel.add(txtCreateOrgCode, "1, 7, fill, default");
		txtCreateOrgCode.setColumns(FIFIVETEEN);
		txtCreateOrgCode.setVisible(false);
		
		searchButton  = new JButton(i18n.get(INCOMMONQUERY));
		panel.add(searchButton, "14, 7, left, default");
		
		btnExit = new JButton(i18n.get(INCOMMONQUIT));
		
			panel.add(btnExit, "17, 7, left, default");
		
		
		txtTargetOrgCode = new JTextFieldValidate();
		panel.add(txtTargetOrgCode, "19, 7, fill, default");
		txtTargetOrgCode.setColumns(FIFIVETEEN);
		txtTargetOrgCode.setVisible(false);
		
		
		JLabel label = new JLabel(i18n.get(INTARGETORGCODE));
		panel.add(label, "7, 3, center, default");
		
		txtTargetOrgName = new JTextField();
		panel.add(txtTargetOrgName, "9, 3, 2, 1, fill, default");
		txtTargetOrgName.setColumns(FIFIVETEEN);
		txtTargetOrgName.setEditable(false);
		
		btnQueryTargetOrgCode = new JButton();
		btnQueryTargetOrgCode.setMargin(new Insets(-2, 1, -2, 1));
		panel.add(btnQueryTargetOrgCode, "11, 3, left, default");
		
		
		
		tablePanel = new JPanel();
		tablePanel.setBorder(new TitledBorder(null, i18n.get(INQUERYRESULT), 
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(tablePanel, "1, 3, fill, default");
		tablePanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("4dlu:grow"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("9px"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode(DEFAULTGROW),
				FormFactory.DEFAULT_ROWSPEC,}));
		
		initTable();
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		tablePanel.add(scrollPane, "1, 3, 8, 1, fill, fill");
		
		// Dialog监听ESC事件
		panel.registerKeyboardAction(new EscHandler(), 
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		
		// 设置为模态（当弹出窗口打开时，父窗口不能编辑）
		//setModal(true);
		// 让窗口弹出时根据实际需要进行显示
		pack();
	}
	
	/**
	 * 定义一个一般内部类：设置dialog监听esc按键的处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-25 上午10:21:54
	 */
	private class EscHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	/**
	 * init query result table
	 */
	private void initTable(){
		table.setModel(new PublishPriceLinkTableMode(null));
		refreshTable(table);
		
		
		
	}

	/**
	 * 刷新界面
	 * @param table
	 */
	public static void refreshTable(JXTable table){
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setColumnControlVisible(true);
		// 设置表头不伸缩模式：如果手工调整一个表头栏目，其他的不会跟随着变的   
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
        table.setHorizontalScrollEnabled(true);
        // 设置表头的宽度   
        LinkTableMode.adjustColumnPreferredWidths(table);
        
//        //设置重货价格的列宽
//        TableColumn firsetColumn = table.getColumnModel().getColumn(3);
//        firsetColumn.setPreferredWidth(100);
//        //设置轻货价格的列宽
//        TableColumn firsetColumn2 = table.getColumnModel().getColumn(4);
//        firsetColumn2.setPreferredWidth(100);
	}
	
	/**
	 * bind vo and ui object
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		queryPublishPriceBinder = BindingFactory.getIntsance().moderateBind(QueryPublishPriceVo.class, this, true);

		vo = queryPublishPriceBinder.getBean();
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		if(user==null || user.getEmployee()==null){
			return;
		}
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		if(dept!=null){
			vo.setCreateOrgCode(dept.getCode());
			vo.setCreateOrgName(dept.getName());
		}
	}
	
	 
	/**
	 * set plugin
	 */
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

	
	
	/**
	 * @return the txtCreateOrgCode
	 */
	public JTextField getTxtCreateOrgCode() {
		return txtCreateOrgCode;
	}

	/**
	 * @param txtCreateOrgCode the txtCreateOrgCode to set
	 */
	public void setTxtCreateOrgCode(JTextField txtCreateOrgCode) {
		this.txtCreateOrgCode = txtCreateOrgCode;
	}

	/**
	 * @return the txtTargetOrgCode
	 */
	public JTextField getTxtTargetOrgCode() {
		return txtTargetOrgCode;
	}

	/**
	 * @param txtTargetOrgCode the txtTargetOrgCode to set
	 */
	public void setTxtTargetOrgCode(JTextField txtTargetOrgCode) {
		this.txtTargetOrgCode = txtTargetOrgCode;
	}

	/**
	 * @return the table
	 */
	public JXTable getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(JXTable table) {
		this.table = table;
	}

	/**
	 * @return the scrollPane
	 */
	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	/**
	 * @param scrollPane the scrollPane to set
	 */
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	/**
	 * @return the tablePanel
	 */
	public JPanel getTablePanel() {
		return tablePanel;
	}

	/**
	 * @param tablePanel the tablePanel to set
	 */
	public void setTablePanel(JPanel tablePanel) {
		this.tablePanel = tablePanel;
	}

	/**
	 * @return the searchButton
	 */
	public JButton getSearchButton() {
		return searchButton;
	}

	/**
	 * @param searchButton the searchButton to set
	 */
	public void setSearchButton(JButton searchButton) {
		this.searchButton = searchButton;
	}

	/**
	 * @return the queryPublishPriceBinder
	 */
	public IBinder<QueryPublishPriceVo> getQueryPublishPriceBinder() {
		return queryPublishPriceBinder;
	}

	/**
	 * @param queryPublishPriceBinder the queryPublishPriceBinder to set
	 */
	public void setQueryPublishPriceBinder(
			IBinder<QueryPublishPriceVo> queryPublishPriceBinder) {
		this.queryPublishPriceBinder = queryPublishPriceBinder;
	}

	/**
	 * @return the bindersMap
	 */
	public Map<String, IBinder<QueryPublishPriceVo>> getBindersMap() {
		return bindersMap;
	}

	/**
	 * @param bindersMap the bindersMap to set
	 */
	public void setBindersMap(Map<String, IBinder<QueryPublishPriceVo>> bindersMap) {
		this.bindersMap = bindersMap;
	}

	/**
	 * @return the plugin
	 */
	public Plugin getPlugin() {
		return plugin;
	}
	/**
	 * getApplication
	 * @return
	 */
	public IApplication getApplication() {
		return application;
	}

	/**
	 * setApplication
	 */
	public void setApplication(IApplication application) {
		this.application = application;
	}

	/**
	 * 将查询结果展现于jtable
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object[][] getArray(List list) {
		
		if (list != null && !list.isEmpty()) {
			Object[][] objtemp = new Object[list.size()][]; // 转换为二维数组
			Object[] q;
			for (int i = 0; i < list.size(); i++) {
				PublishPriceEntity dto = (PublishPriceEntity) list.get(i);
				q = getRowValue(dto);
				for (int j = 0; j < objtemp.length; j++) {
					objtemp[i] = q;
				}
			}
			return objtemp;
		} else {
			return null;
		}
	}

	/**
	 * getRowValue结果列表
	 * @param object
	 * @return
	 */
	private Object[] getRowValue(PublishPriceEntity dto) {
		
		//目的地name
		String targetOrgName  = dto.getArrvRegionName();
	
		//zxy 20140522 MANA-1253 start 新增:在产品条目里追加合票方式
		if(StringUtils.isNotBlank(dto.getCombBillTypeName()))
			dto.setProductItemName(dto.getProductItemName()+"("+dto.getCombBillTypeName()+")");
		//zxy 20140522 MANA-1253 end 新增:在产品条目里追加合票方式
		
		//产品信息 name
		String productName = dto.getProductItemName();
		
		//运营时效?
		String min = dto.getMinEffectiveValue();
		if(min==null){
			min = "";
		}
		String max = dto.getMaxEffectiveValue();
		if(max==null){
			max = "";
		}
		String unit = dto.getEffectiveUnit();
		
		String finaleffective = "";
		
		//如果当前是偏线产品
//		if(!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(dto.getProductItemCode())){
			if(unit!=null){
				if("DAY".equals(unit)){
					finaleffective = min + "-" +max + i18n.get("foss.gui.common.QueryPublishPriceUI.discription.day");
				}else if("HOURS".equals(unit)){
					finaleffective = min + "-" +max + i18n.get("foss.gui.common.QueryPublishPriceUI.discription.hours");
				}else{
					finaleffective = min + "-" +max + unit;
				}			
			}else{
				finaleffective = min + "-" +max;
			}
//		}		
		
		//取货时间
		String receivedDate = dto.getPickupTime();
		
		if(StringUtil.isNotEmpty(receivedDate) && receivedDate.length() == FOUR)
		{
			String hour = receivedDate.substring(0,2);
			String minute = receivedDate.substring(2,receivedDate.length());
			receivedDate = hour + ":" + minute;
		}
		
		
		//长短途
		String longOrShort = dto.getLongOrShort();
		
		
		BigDecimal lowLevel = dto.getMinFee();
		if(lowLevel==null){
			lowLevel = BigDecimal.ZERO;
		}
		
		
		//早中晚班
		String flightShiftNo = dto.getFlightShiftNo();
		String flightShiftName = "";
		//对所有的数据循环
		for(DataDictionaryValueEntity entity: dataDictionaryValueEntity){
			if(entity.getValueCode().equals(flightShiftNo)){
				flightShiftName = entity.getValueName();
			}
		}
		  /**
	     * 是否接货
	     */
		String pickupCode =dto.getCentralizePickup();
		  /**
	     * 是否接货
	     */
		String pickupName;
		  /**
	     * 是否接货
	     */
		if(FossConstants.YES.equals(pickupCode)){
			pickupName  = i18n.get("foss.gui.common.waybillEditUI.common.yes");//是
			
			//精准包裹是没有接货,所以不显示
		}else if(StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_PCP, dto.getProductItemCode())){
			pickupName=null;
		}else{
			pickupName = i18n.get("foss.gui.common.waybillEditUI.common.no");//否
		}
		
		String desc = "";
		
		//送货起步价
		String deliveryCharges="";
		if(dto.getDeliveryCharges()!=null){
			deliveryCharges=dto.getDeliveryCharges().toString();
		}
		//获取对象成员保存至一维数组
		//ProductEntity product = productService.getProductByCache(dto.getProductItemCode(), null);
		//update  
		if(StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG, dto.getProductItemCode()) 
				|| StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG, dto.getProductItemCode()) 
				|| StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG, dto.getProductItemCode()) 
				|| StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG, dto.getProductItemCode())
				|| StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR, dto.getProductItemCode())
				|| StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD, dto.getProductItemCode())){
			//重货价格
			String heavyFeeRate = dto.getHeavyFeeRateStr();
			if(heavyFeeRate==null || "".equals(heavyFeeRate)){
				heavyFeeRate = "0.0";
			}else{
				if(StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR, dto.getProductItemCode())
						|| StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD, dto.getProductItemCode())){
					heavyFeeRate = heavyFeeRate.replace("</br>", "");
				}else{
					heavyFeeRate = heavyFeeRate.replace("</br>", "/");
				}
			}

			//轻货价格
			String lightFeeRate = dto.getLightFeeRateStr();
			
			if(lightFeeRate==null || "".equals(lightFeeRate)){
				lightFeeRate = "0.0";
			}else{
				if(StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR, dto.getProductItemCode())
						|| StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD, dto.getProductItemCode())){
					lightFeeRate = lightFeeRate.replace("</br>", "");
				}else{
					lightFeeRate = lightFeeRate.replace("</br>", "/");
				}
			}
			
			Object[] obj = { false ,targetOrgName ,productName,heavyFeeRate,lightFeeRate,
					 finaleffective,receivedDate,longOrShort,lowLevel.toString(),flightShiftName,pickupName,desc,deliveryCharges};
			return obj ;
			//精准包裹
		}else if(StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_PCP,dto.getProductItemCode())){
			//重货价格
			String heavyFeeRate = dto.getHeavyFeeRateStr();
			if(StringUtils.isEmpty(heavyFeeRate)){
				heavyFeeRate = "0.0";
			}else{
				heavyFeeRate = heavyFeeRate.replace("</br>", "\n");
			}

			//轻货价格
			String lightFeeRate = dto.getLightFeeRateStr();

			if(StringUtils.isEmpty(lightFeeRate)){
				lightFeeRate = "0.0";
			}else{
					lightFeeRate = lightFeeRate.replace("</br>", "\n");

			}

			Object[] obj = { false ,targetOrgName ,productName,heavyFeeRate,lightFeeRate,
					finaleffective,receivedDate,longOrShort,lowLevel.toString(),flightShiftName,pickupName,desc,deliveryCharges};
			return obj ;
		}else if(StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT, dto.getProductItemCode())//精准汽运长途
				|| StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT, dto.getProductItemCode())//精准汽运短途
				|| StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE, dto.getProductItemCode())//三级产品-汽运偏线
				||StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, dto.getProductItemCode())//三级产品-精准卡航
				||StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT, dto.getProductItemCode())//三级产品-精准城运
				||StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE, dto.getProductItemCode())//三级产品-整车
				){
			/**
			 * 定价优化项目---公布价查询优化
			 * 
			 * 查询公布价分段显示
			 * 
			 * @author Foss-206860
			 * */
			//当产品为汽运(不包含精准大票相关)时，需要设置分段价格
			//GUI端的重货值
			String heavyFeeRateStr = "";
			//GUI端的轻货值
			String lightFeeRateStr = "";
			//获取计价分段明细list
			List<PopPublicPriceDto> popPublicPriceDtoList = dto.getPopPublicPriceDtoList();
			if(popPublicPriceDtoList == null){
				//重货价格
				BigDecimal heavyFeeRate = dto.getHeavyPrice();
				if(heavyFeeRate==null){
					heavyFeeRate = BigDecimal.ZERO;
				}
				//轻货价格
				BigDecimal lightFeeRate = dto.getLightPrice();
				if(lightFeeRate==null){
					lightFeeRate = BigDecimal.ZERO;
				}
				heavyFeeRateStr = heavyFeeRate.doubleValue()+"";
				lightFeeRateStr = lightFeeRate.doubleValue()+"";
			}else if(popPublicPriceDtoList.size() == 1){
				for (int i = 0; i < popPublicPriceDtoList.size(); i++) {
					heavyFeeRateStr = popPublicPriceDtoList.get(i).getHeavyPrice().doubleValue()+"";
					lightFeeRateStr = popPublicPriceDtoList.get(i).getLightPrice().doubleValue()+"";
				}
			}else if(popPublicPriceDtoList.size() > 1){
				
				for (int i = 0; i < popPublicPriceDtoList.size(); i++) {
					heavyFeeRateStr += popPublicPriceDtoList.get(i).getHeavyRange()+":"+popPublicPriceDtoList.get(i).getHeavyPrice().doubleValue();
					lightFeeRateStr += popPublicPriceDtoList.get(i).getLightRange()+":"+popPublicPriceDtoList.get(i).getLightPrice().doubleValue();
					if(i != popPublicPriceDtoList.size()-1){
						heavyFeeRateStr +="\n";
						lightFeeRateStr +="\n";
					}
				}
			}
			Object[] obj = { false ,targetOrgName ,productName,heavyFeeRateStr,lightFeeRateStr,
					 finaleffective,receivedDate,longOrShort,lowLevel.toString(),flightShiftName,pickupName,desc,deliveryCharges};
			return obj ;
		}else{
			//重货价格
			BigDecimal heavyFeeRate = dto.getHeavyPrice();
			if(heavyFeeRate==null){
				heavyFeeRate = BigDecimal.ZERO;
			}
			
			//轻货价格
			BigDecimal lightFeeRate = dto.getLightPrice();
			if(lightFeeRate==null){
				lightFeeRate = BigDecimal.ZERO;
			}
			

			Object[] obj = { false ,targetOrgName ,productName,heavyFeeRate.doubleValue()+"",lightFeeRate.doubleValue()+"",
					 finaleffective,receivedDate,longOrShort,lowLevel.toString(),flightShiftName,pickupName,desc,deliveryCharges};
		
			return obj ;
		}
	}
	
	/**
	 * get org  name from service
	 * @param dataService
	 * @param activeTmp
	 * @return
	 */
	protected static String getOrgNameFromOrgService( 
			 String code) {
		IOrgInfoService orgService = GuiceContextFactroy.getInjector().getInstance(OrgInfoService.class);
		
		OrgAdministrativeInfoEntity e = orgService.queryByCode(code);
		if(e!=null){
			return e.getName();
		}else{
			if(code!=null){
				return code;
			}else{
				return "";
			}
		}
		
	}

	/**
	 * @return the productTypeModel
	 */
	public DefaultComboBoxModel getProductTypeModel() {
		return productTypeModel;
	}

	/**
	 * @param productTypeModel the productTypeModel to set
	 */
	public void setProductTypeModel(DefaultComboBoxModel productTypeModel) {
		this.productTypeModel = productTypeModel;
	}

	/**
	 * @return the pikcModeModel
	 */
	public DefaultComboBoxModel getPikcModeModel() {
		return pikcModeModel;
	}

	/**
	 * @param pikcModeModel the pikcModeModel to set
	 */
	public void setPikcModeModel(DefaultComboBoxModel pikcModeModel) {
		this.pikcModeModel = pikcModeModel;
	}

	/**
	 * @return the txtCreateOrgName
	 */
	public JTextField getTxtCreateOrgName() {
		return txtCreateOrgName;
	}

	/**
	 * @return the btnQueryCreateOrgCode
	 */
	public JButton getBtnQueryCreateOrgCode() {
		return btnQueryCreateOrgCode;
	}

	/**
	 * @return the txtTargetOrgName
	 */
	public JTextField getTxtTargetOrgName() {
		return txtTargetOrgName;
	}

	/**
	 * @return the btnQueryTargetOrgCode
	 */
	public JButton getBtnQueryTargetOrgCode() {
		return btnQueryTargetOrgCode;
	}

	/**
	 * @return the vo
	 */
	public QueryPublishPriceVo getVo() {
		return vo;
	}

	/**
	 * @return the btnExit
	 */
	public JButton getBtnExit() {
		return btnExit;
	}

	/**
	 * @return the dataDictionaryValueService
	 */
	public IDataDictionaryValueService getDataDictionaryValueService() {
		return dataDictionaryValueService;
	}

	/**
	 * @return the dataDictionaryValueEntity
	 */
	public List<DataDictionaryValueEntity> getDataDictionaryValueEntity() {
		return dataDictionaryValueEntity;
	}

	

}