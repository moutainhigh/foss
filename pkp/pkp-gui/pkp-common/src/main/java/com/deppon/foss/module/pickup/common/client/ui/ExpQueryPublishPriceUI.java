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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.java.plugin.Plugin;
import org.jdesktop.swingx.JXTable;
import org.jfree.util.Log;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.binding.BindingFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.binding.annotation.Bind;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.action.ExpExitAction;
import com.deppon.foss.module.pickup.common.client.action.ExpQueryPublishPriceAction;
import com.deppon.foss.module.pickup.common.client.action.ExpQuerySalesDepartmentCreateAction;
import com.deppon.foss.module.pickup.common.client.service.AddressServicesFactory;
import com.deppon.foss.module.pickup.common.client.service.IAddressService;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.IOrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.impl.OrgInfoService;
import com.deppon.foss.module.pickup.common.client.ui.addressfield.ExpJAddressField;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.QueryPublishPriceVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
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
 * 
 * @author 105089-FOSS-shixw
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class ExpQueryPublishPriceUI extends JFrame {

	/**
	 * 退出
	 */
	private static final String INCOMMONQUIT = "foss.gui.common.waybillEditUI.common.quit";

	/**
	 * 布局列宽
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 国际化对象查询结果
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

	// 15
	private static final int FIFIVETEEN = 15;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(this.getClass());

	/**
	 * 区域服务接口
	 */
	IAddressService districtService = AddressServicesFactory.getAddressService();

	/**
	 * 基础数据查询服务类 用于查询基础数据等信息
	 */
	private IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);

	/**
	 * 出发部门是指货物运输的出发部门
	 */
	@Bind("createOrgName")
	private JTextField txtCreateOrgName;

	/**
	 * 出发部门 编码
	 */
	@Bind("createOrgCode")
	private JTextField txtCreateOrgCode;

	@ButtonAction(icon = IconConstants.QUERY, shortcutKey = "", type = ExpQuerySalesDepartmentCreateAction.class)
	private JButton btnQueryCreateOrgCode;

	/**
	 * 出发部门是指货物运输的出发部门
	 */
	@Bind("targetOrgName")
	private ExpJAddressField txtTargetOrgName;

	/**
	 * 出发部门 编码
	 */
	@Bind("targetOrgCode")
	private JTextField txtTargetOrgCode;

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
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpQueryPublishPriceAction.class)
	private JButton searchButton = new JButton(i18n.get(INCOMMONQUERY));

	/**
	 * 绑定接口
	 */
	private IBinder<QueryPublishPriceVo> queryPublishPriceBinder;

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
	@ButtonAction(icon = SEARCH16GIF, shortcutKey = "", type = ExpExitAction.class)
	private JButton btnExit;

	/**
	 * 数据字典服务类
	 */
	private IDataDictionaryValueService dataDictionaryValueService = GuiceContextFactroy.getInjector().getInstance(DataDictionaryValueService.class);

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
	private List<DataDictionaryValueEntity> dataDictionaryValueEntity;

	/**
	 * 构造方法
	 */
	public ExpQueryPublishPriceUI() {
		init();// 初始化界面信息
		// 初始化产品类型
		initComboBox();// 产品类型
		bind();
		registToRespository();
		initVo();
	}

	/**
	 * s 初始化下拉框
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
	private void initCombProductType() {
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		productTypeModel = new DefaultComboBoxModel();
		// 根据当前用户所在部门查询部门所属产品
		setProductTypeModel(dept.getCode());
	}

	/**
	 * 
	 * 设置产品到数据模型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-16 下午02:18:34
	 */
	public void setProductTypeModel(String deptCode) {
		List<ProductEntity> list = baseDataService.queryTransType(deptCode);
		for (ProductEntity product : list) {
			if (CommonUtils.directDetermineIsExpressByProductCode(product.getCode())) {
				ProductEntityVo vo2 = new ProductEntityVo();
				// 将数据库查询出的产品对象进行转换，转成VO使用的对象
				ValueCopy.entityValueCopy(product, vo2);
				vo2.setDestNetType(product.getDestNetType());
				productTypeModel.addElement(vo2);
				return;
			}
		}
	}

	/**
	 * init vo
	 */
	protected void initVo() {
		Log.debug(vo);
		// 获得数据字典 service
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
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						RowSpec.decode("fill:max(75dlu;default):grow"), }));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, i18n.get(INQUERYCONDITION), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel, "1, 2, fill, default");
		FormLayout flpanel = new FormLayout(new ColumnSpec[] { ColumnSpec.decode("max(53dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("center:max(19dlu;default):grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(47dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(53dlu;default):grow"), ColumnSpec.decode("15dlu"), }, new RowSpec[] { RowSpec.decode("max(10dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(10dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(16dlu;default)"), });
		panel.setLayout(flpanel);

		JLabel label1 = new JLabel("出发部门：");
		panel.add(label1, "1, 3, right, default");

		txtCreateOrgName = new JTextField();
		panel.add(txtCreateOrgName, "3, 3");
		txtCreateOrgName.setColumns(FIFIVETEEN);
		txtCreateOrgName.setEditable(false);

		// 出发部门编码
		txtCreateOrgCode = new JTextField();
		panel.add(txtCreateOrgCode,"2, 3, left, default");
		txtCreateOrgCode.setVisible(false);

		btnQueryCreateOrgCode = new JButton();
		btnQueryCreateOrgCode.setMargin(new Insets(-2, 1, -2, 1));
		panel.add(btnQueryCreateOrgCode, "4, 3, left, default");

		JLabel label = new JLabel("到达城市：");
		panel.add(label, "6, 3, right, default");

		// 到达城市编码
		txtTargetOrgCode = new JTextField();
		panel.add(txtTargetOrgCode,"7, 3, left, default");
		txtTargetOrgCode.setVisible(false);

		txtTargetOrgName = new ExpJAddressField();
		panel.add(txtTargetOrgName, "8, 3, fill, default");
		txtTargetOrgName.setColumns(FIFIVETEEN);
		txtTargetOrgName.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// 城市名称
				String cityName = txtTargetOrgName.getText().trim();
				// 判断目的城市是否为空
				if (StringUtils.isNotEmpty(cityName)) {
					AddressFieldDto cityDto = districtService.queryCityByName(cityName);
					if (null != cityDto) {
						//设置目的城市编码
						vo.setTargetOrgCode(cityDto.getCityId());
						txtTargetOrgCode.setText(cityDto.getCityId());
						//设置目的城市名称
						txtTargetOrgName.setText(cityDto.getCityName());
					} else {
						//设置目的城市编码
						vo.setTargetOrgCode("");
						txtTargetOrgCode.setText("");
						//设置目的城市名称
						vo.setTargetOrgName("");
						//设置目的城市名称获得焦点
						txtTargetOrgName.requestFocus();
					}
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// 不做业务处理
			}
		});

		btnExit = new JButton(i18n.get(INCOMMONQUIT));
		btnExit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// 不做业务处理
			}
		});

		searchButton = new JButton(i18n.get(INCOMMONQUERY));
		searchButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnExit, "3, 7, center, default");

		panel.add(searchButton, "8, 7, center, default");

		tablePanel = new JPanel();
		tablePanel.setBorder(new TitledBorder(null, i18n.get(INQUERYRESULT), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(tablePanel, "1, 3, fill, default");
		tablePanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC, FormFactory.DEFAULT_COLSPEC, ColumnSpec.decode("4dlu:grow"),
				FormFactory.DEFAULT_COLSPEC, FormFactory.LABEL_COMPONENT_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] { RowSpec.decode("9px"), FormFactory.DEFAULT_ROWSPEC, RowSpec.decode(DEFAULTGROW), FormFactory.DEFAULT_ROWSPEC, }));

		initTable();
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		tablePanel.add(scrollPane, "1, 3, 8, 1, fill, fill");

		// Dialog监听ESC事件
		panel.registerKeyboardAction(new EscHandler(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

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
	private void initTable() {
		table.setModel(new ExpPublishPriceLinkTableMode(null));
		refreshTable(table);

	}

	/**
	 * 刷新界面
	 * 
	 * @param table
	 */
	public static void refreshTable(JXTable table) {
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setColumnControlVisible(true);
		// 设置表头不伸缩模式：如果手工调整一个表头栏目，其他的不会跟随着变的
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setHorizontalScrollEnabled(true);
		// 设置表头的宽度
		LinkTableMode.adjustColumnPreferredWidths(table);
	}

	/**
	 * bind vo and ui object
	 */
	private void bind() {
		// FocusPolicyFactory.getIntsance().setFocusTraversalPolicy(this);
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		// 如果需要在输入后失去焦点的时候进行绑定，可以用下面的方法
		queryPublishPriceBinder = BindingFactory.getIntsance().moderateBind(QueryPublishPriceVo.class, this, true);

		vo = queryPublishPriceBinder.getBean();

		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		if (user == null || user.getEmployee() == null) {
			return;
		}
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		if (dept != null) {
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
	 * @return the table
	 */
	public JXTable getTable() {
		return table;
	}

	/**
	 * @param table
	 *            the table to set
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
	 * @param scrollPane
	 *            the scrollPane to set
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
	 * @param tablePanel
	 *            the tablePanel to set
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
	 * @param searchButton
	 *            the searchButton to set
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
	 * @param queryPublishPriceBinder
	 *            the queryPublishPriceBinder to set
	 */
	public void setQueryPublishPriceBinder(IBinder<QueryPublishPriceVo> queryPublishPriceBinder) {
		this.queryPublishPriceBinder = queryPublishPriceBinder;
	}

	/**
	 * @return the bindersMap
	 */
	public Map<String, IBinder<QueryPublishPriceVo>> getBindersMap() {
		return bindersMap;
	}

	/**
	 * @param bindersMap
	 *            the bindersMap to set
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
	 * 
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
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object[][] getArray(List list) {
		if (CollectionUtils.isNotEmpty(list)) {
			Object[][] objtemp = new Object[list.size()][]; // 转换为二维数组
			Object[] q;
			for (int i = 0; i < list.size(); i++) {
				PublishPriceExpressEntity dto = (PublishPriceExpressEntity) list.get(i);
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
	 * 
	 * @param object
	 * @return
	 */
	private Object[] getRowValue(PublishPriceExpressEntity dto) {
		//目的城市
	    String arrivedCity = StringUtil.defaultIfNull(dto.getDestinationCity());

	    //产品信息
	    String productName = StringUtil.defaultIfNull(dto.getProductName()); 
	    
		//首重区间-下限
		BigDecimal weightLowLimit = CommonUtils.defaultIfNull(dto.getWeightLowLimit());
		
		//首重区间-上限
		BigDecimal weightHighLimit = CommonUtils.defaultIfNull(dto.getWeightHighLimit());

		//首重区间
		String headWeightArea = "(" + weightLowLimit +" , "+ weightHighLimit +"]";
		
		//首重固定费用
		BigDecimal firstWeight  = CommonUtils.defaultIfNull(dto.getFirstWeight());
		
		
		//重量上线1
		BigDecimal weightOnline1 = CommonUtils.defaultIfNull(dto.getWeightOnline1());		
		//重量下线1
		BigDecimal weightOffline1 = CommonUtils.defaultIfNull(dto.getWeightOffline1());
		
		//续重区域1
		String weightArea1 = "(" + weightOffline1 +" , "+ weightOnline1 +"]";
		
	    //费率1
	    BigDecimal feeRate1 = CommonUtils.defaultIfNull(dto.getFeeRate1());
	    
		//重量上线2
		BigDecimal weightOnline2 = CommonUtils.defaultIfNull(dto.getWeightOnline2());
		
		//重量下线2
		BigDecimal weightOffline2 = CommonUtils.defaultIfNull(dto.getWeightOffline2());
		
		//续重区域2
		String weightArea2 = "(" + weightOffline2 +" , "+ weightOnline2 +"]";
		
	    //费率2
	    BigDecimal feeRate2 = CommonUtils.defaultIfNull(dto.getFeeRate2());
	    
	    //派送时间
	    String time = StringUtil.defaultIfNull(dto.getDeliveryTime());

		// 获取对象成员保存至一维数组
		Object[] obj = { false, arrivedCity, StringUtil.defaultIfNull(productName), String.valueOf(firstWeight.doubleValue()),
				String.valueOf(feeRate1.doubleValue()), String.valueOf(feeRate2.doubleValue()),time, headWeightArea, weightArea1, weightArea2, "" };
		return obj;
	}

	/**
	 * get org name from service
	 * 
	 * @param dataService
	 * @param activeTmp
	 * @return
	 */
	protected static String getOrgNameFromOrgService(String code) {
		IOrgInfoService orgService = GuiceContextFactroy.getInjector().getInstance(OrgInfoService.class);

		OrgAdministrativeInfoEntity e = orgService.queryByCode(code);
		if (e != null) {
			return e.getName();
		} else {
			if (code != null) {
				return code;
			} else {
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
	 * @param productTypeModel
	 *            the productTypeModel to set
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
	 * @param pikcModeModel
	 *            the pikcModeModel to set
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
	public ExpJAddressField getTxtTargetOrgName() {
		return txtTargetOrgName;
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

	public JTextField getTxtCreateOrgCode() {
		return txtCreateOrgCode;
	}

	public JTextField getTxtTargetOrgCode() {
		return txtTargetOrgCode;
	}

}