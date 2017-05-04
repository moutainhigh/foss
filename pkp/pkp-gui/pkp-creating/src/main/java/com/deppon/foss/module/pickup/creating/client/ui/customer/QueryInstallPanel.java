package com.deppon.foss.module.pickup.creating.client.ui.customer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.action.InstallQueryAction;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.BatchCreateWaybillTableModel;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creating.client.ui.editui.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JTextArea;

/**
 * 查询安装费界面
 * 
 * @author MaBinliang
 * 
 */

public class QueryInstallPanel extends JDialog {

	private WaybillPanelVo bean = null;

	/**
	 * 10
	 */
	private static final int TEN = 10;

	/**
	 * 12
	 */
	private static final int FONTSIZE12 = 12;


	/**
	 * 序列货标识
	 */
	private static final long serialVersionUID = -1422237527226994454L;

	private static final int NUM_50000 = 50000;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(QueryInstallPanel.class);

	/**
	 * service
	 */
	private IWaybillService waybillService = WaybillServiceFactory
			.getWaybillService();

	// 安装品名
	private JTextField txtPrivilegeName;


	public JTextField getTxtPrivilegeName() {
		return txtPrivilegeName;
	}

	public void setTxtPrivilegeName(JTextField txtPrivilegeName) {
		this.txtPrivilegeName = txtPrivilegeName;
	}



	// 运单界面
	private WaybillEditUI waybillEditUI;

	/**
	 * 选中的checkbox
	 */
	private List<JCheckBox> list;
	
	/**
	 * 选中的列
	 */
	private InstallCheckBoxColumn checkBoxColumn;

	private static JXTable table;

	// String newvalue;
	public JXTable getTable() {
		return table;
	}

	public void setTable(JXTable table) {
		this.table = table;
	}

	@ButtonAction(icon = "preview.png", shortcutKey = "", type = InstallQueryAction.class)
	private JButton btnQuery;// 查询
	
	private JTextArea textArea;
	private JCheckBox allSelectCheckBox;
	private JButton button;

	public JCheckBox getAllSelectCheckBox() {
		return allSelectCheckBox;
	}

	public void setAllSelectCheckBox(JCheckBox allSelectCheckBox) {
		this.allSelectCheckBox = allSelectCheckBox;
	}

	public QueryInstallPanel(WaybillEditUI waybillEditUI) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		this.waybillEditUI = waybillEditUI;

		HashMap<String, IBinder<WaybillPanelVo>> map = waybillEditUI
				.getBindersMap();
		IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
		bean = waybillBinder.getBean();
		init();
		bind();
		EnterKeyQueryOtherCharge enter=new EnterKeyQueryOtherCharge(btnQuery);
		txtPrivilegeName.addKeyListener(enter);
		EnterKeyQueryOtherCharge enterTable=new EnterKeyQueryOtherCharge(this);
		table.addKeyListener(enterTable);
	}

	/**
	 * 初始化界面
	 * 
	 */
	private void init() {
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("10dlu"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("35dlu"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("50dlu:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("50dlu:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("50dlu:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("50dlu:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("50dlu:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("10dlu"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("172dlu"), }));

		// 特殊增值服务安装费查询
		JLabel label = new JLabel("特殊增值服务安装费查询");
		label.setFont(new Font("宋体", Font.BOLD, FONTSIZE12));
		getContentPane().add(label, "2, 2, 7, 1");

		// 安装品名
		JLabel lblNewLabel = new JLabel("安装品名：");
		getContentPane().add(lblNewLabel, "4, 4, 2, 1, left, default");

		txtPrivilegeName = new JTextField();
		getContentPane().add(txtPrivilegeName, "6, 4, 3, 1, fill, default");
		txtPrivilegeName.setColumns(TEN);

		// 查询
		btnQuery = new JButton(
				i18n.get("foss.gui.creating.waybillEditUI.common.query"));
		getContentPane().add(btnQuery, "10, 4, left, default");
		
		allSelectCheckBox = new JCheckBox("全选");
		getContentPane().add(allSelectCheckBox, "12, 4");

		AllListener allListener = new AllListener();
		allSelectCheckBox.addItemListener(allListener);

		button = new JButton("确定");
		getContentPane().add(button, "16, 4");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setOtherCharge();
				dispose();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "4, 6, 13, 1, default, fill");
		initTable();
		scrollPane.setViewportView(table);

		textArea = new JTextArea();
		scrollPane.setColumnHeaderView(textArea);

		// 设置模态
		setModal(true);
		pack();
	}


	/**
	 * 
	 * 绑定
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 下午07:12:39
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}

	
	private Object[][] getDataMessage(List<OtherChargeVo> voList)
	{
		Object[][] dataN = null;
		if (voList != null && voList.size() > 0) {
			dataN = new Object[voList.size()][NumberConstants.NUMBER_14];
			for (int j = 0; j < voList.size(); j++) {
				if (!"".equals(voList.get(j).getCode())) {
					if (voList.get(j).getCode().startsWith(WaybillConstants.instCode)) {
						dataN[j][1] = j + 1;
						dataN[j][2] = voList.get(j).getChargeName();
						dataN[j][NumberConstants.NUMBER_3] = voList.get(j).getType();
						dataN[j][NumberConstants.NUMBER_4] = voList.get(j).getMoney();
						dataN[j][NumberConstants.NUMBER_5] = 1;
						dataN[j][NumberConstants.NUMBER_6] = "";
						dataN[j][NumberConstants.NUMBER_7] = "";
						dataN[j][NumberConstants.NUMBER_8] = voList.get(j).getUpperLimit();
						dataN[j][NumberConstants.NUMBER_9] = voList.get(j).getLowerLimit();
						dataN[j][NumberConstants.NUMBER_10] = voList.get(j).getIsUpdate();
						dataN[j][NumberConstants.NUMBER_11] = voList.get(j).getIsDelete();
						dataN[j][NumberConstants.NUMBER_12] = voList.get(j).getCode();
						dataN[j][NumberConstants.NUMBER_13] = voList.get(j).getId();
					}
				}
				if (!"".equals(voList.get(j).getCode())) {
					if (voList.get(j).getCode().startsWith(WaybillConstants.instsCode)) {
						dataN[j][1] = j + 1;
						dataN[j][2] = voList.get(j).getChargeName();
						dataN[j][NumberConstants.NUMBER_3] = voList.get(j).getType();
						dataN[j][NumberConstants.NUMBER_4] = voList.get(j).getMoney();
						dataN[j][NumberConstants.NUMBER_5] = "";
						dataN[j][NumberConstants.NUMBER_6] = 1;
						dataN[j][NumberConstants.NUMBER_7] = 1;
						dataN[j][NumberConstants.NUMBER_8] = voList.get(j).getUpperLimit();
						dataN[j][NumberConstants.NUMBER_9] = voList.get(j).getLowerLimit();
						dataN[j][NumberConstants.NUMBER_10] = voList.get(j).getIsUpdate();
						dataN[j][NumberConstants.NUMBER_11] = voList.get(j).getIsDelete();
						dataN[j][NumberConstants.NUMBER_12] = voList.get(j).getCode();
						dataN[j][NumberConstants.NUMBER_13] = voList.get(j).getId();
					}
				}
				if (!"".equals(voList.get(j).getCode())) {
					if (voList.get(j).getCode().startsWith(WaybillConstants.instqCode)) {
						dataN[j][1] = j + 1;
						dataN[j][2] = voList.get(j).getChargeName();
						dataN[j][NumberConstants.NUMBER_3] = voList.get(j).getType();
						dataN[j][NumberConstants.NUMBER_4] = voList.get(j).getMoney();
						dataN[j][NumberConstants.NUMBER_5] = "";
						dataN[j][NumberConstants.NUMBER_6] = "";
						dataN[j][NumberConstants.NUMBER_7] = "";
						dataN[j][NumberConstants.NUMBER_8] = voList.get(j).getUpperLimit();
						dataN[j][NumberConstants.NUMBER_9] = voList.get(j).getLowerLimit();
						dataN[j][NumberConstants.NUMBER_10] = voList.get(j).getIsUpdate();
						dataN[j][NumberConstants.NUMBER_11] = voList.get(j).getIsDelete();
						dataN[j][NumberConstants.NUMBER_12] = voList.get(j).getCode();
						dataN[j][NumberConstants.NUMBER_13] = voList.get(j).getId();
					}
				}
				//送货费
				if (!"".equals(voList.get(j).getCode())) {
					if (voList.get(j).getCode().startsWith(WaybillConstants.SHAZ)) {
						dataN[j][1] = j + 1;
						dataN[j][2] = voList.get(j).getChargeName();
						dataN[j][NumberConstants.NUMBER_3] = voList.get(j).getType();
						dataN[j][NumberConstants.NUMBER_4] = voList.get(j).getMoney();
						dataN[j][NumberConstants.NUMBER_5] = "";
						dataN[j][NumberConstants.NUMBER_6] = "";
						dataN[j][NumberConstants.NUMBER_7] = "";
						dataN[j][NumberConstants.NUMBER_8] = voList.get(j).getUpperLimit();
						dataN[j][NumberConstants.NUMBER_9] = voList.get(j).getLowerLimit();
						dataN[j][NumberConstants.NUMBER_10] = voList.get(j).getIsUpdate();
						dataN[j][NumberConstants.NUMBER_11] = voList.get(j).getIsDelete();
						dataN[j][NumberConstants.NUMBER_12] = voList.get(j).getCode();
						dataN[j][NumberConstants.NUMBER_13] = voList.get(j).getId();
					}
				}
				//送货上楼（家装）送货费
				if (!"".equals(voList.get(j).getCode())) {
					if (voList.get(j).getCode().startsWith(WaybillConstants.SHSL)) {
						dataN[j][1] = j + 1;
						dataN[j][2] = voList.get(j).getChargeName();
						dataN[j][NumberConstants.NUMBER_3] = voList.get(j).getType();
						dataN[j][NumberConstants.NUMBER_4] = voList.get(j).getMoney();
						dataN[j][NumberConstants.NUMBER_5] = "";
						dataN[j][NumberConstants.NUMBER_6] = "";
						dataN[j][NumberConstants.NUMBER_7] = "";
						dataN[j][NumberConstants.NUMBER_8] = voList.get(j).getUpperLimit();
						dataN[j][NumberConstants.NUMBER_9] = voList.get(j).getLowerLimit();
						dataN[j][NumberConstants.NUMBER_10] = voList.get(j).getIsUpdate();
						dataN[j][NumberConstants.NUMBER_11] = voList.get(j).getIsDelete();
						dataN[j][NumberConstants.NUMBER_12] = voList.get(j).getCode();
						dataN[j][NumberConstants.NUMBER_13] = voList.get(j).getId();
					}
				}
				//送货不上楼（家装）送货费
				if (!"".equals(voList.get(j).getCode())) {
					if (voList.get(j).getCode().startsWith(WaybillConstants.SHBSL)) {
						dataN[j][1] = j + 1;
						dataN[j][2] = voList.get(j).getChargeName();
						dataN[j][NumberConstants.NUMBER_3] = voList.get(j).getType();
						dataN[j][NumberConstants.NUMBER_4] = voList.get(j).getMoney();
						dataN[j][NumberConstants.NUMBER_5] = "";
						dataN[j][NumberConstants.NUMBER_6] = "";
						dataN[j][NumberConstants.NUMBER_7] = "";
						dataN[j][NumberConstants.NUMBER_8] = voList.get(j).getUpperLimit();
						dataN[j][NumberConstants.NUMBER_9] = voList.get(j).getLowerLimit();
						dataN[j][NumberConstants.NUMBER_10] = voList.get(j).getIsUpdate();
						dataN[j][NumberConstants.NUMBER_11] = voList.get(j).getIsDelete();
						dataN[j][NumberConstants.NUMBER_12] = voList.get(j).getCode();
						dataN[j][NumberConstants.NUMBER_13] = voList.get(j).getId();
					}
				}
			}
				int j=0;
				if(dataN.length>0)
				{
					for(int k=0;k<dataN.length;k++)
					{
						if(dataN[k][NumberConstants.NUMBER_12]!=null)
						{
						   j++;
						}
					}
				
					if(j>0)
					{
						Object[][] array = new Object[j][NumberConstants.NUMBER_13];
						//int s=1;
						for(int k=0;k<dataN.length;k++)
						{
							if(dataN[k][NumberConstants.NUMBER_12]!=null)
							{
								
								if(j>=0)
								{
									array[j-1]=dataN[k];
									array[j-1][1]=j;
									j--;
								}
							}
						}
						return array;
					}
					
				}
			
		}
		return dataN;
	}
	/**
	 * 
	 * 初始化表格
	 * 
	 */
	private void initTable() {

		List<ValueAddDto> list = waybillService
				.queryValueAddPriceList(getQueryParam());

		List<OtherChargeVo> voList = getOtherChargeList(list);
		//根据安装品名进行排序  foss-254615-mabinliang
		Collections.sort(voList, new Comparator<OtherChargeVo>() {
			@Override
			public int compare(OtherChargeVo o1, OtherChargeVo o2) {
				return Collator.getInstance(java.util.Locale.CHINA).compare(
						((OtherChargeVo) o1).getChargeName(),
						((OtherChargeVo) o2).getChargeName());
			}

		});
		Collections.reverse(voList);
		Object[][] object=getDataMessage(voList);
		
		
		final ChangeInfoDetailTableModel changeInfoDetailTableModel = new ChangeInfoDetailTableModel(
				object);

		table = new JXTable() {
			private static final long serialVersionUID = 6965760807055236003L;

			@Override
			public boolean isCellEditable(int row, int column) {
				boolean ret = changeInfoDetailTableModel.isCellEditable(
						convertRowIndexToModel(row),
						convertColumnIndexToModel(column));

				return ret;
			}			
		};
		table.setModel(new ChangeInfoDetailTableModel(object));
		//将数据备份
		
		ChangeInfoDetailTableModel changeInfo=(ChangeInfoDetailTableModel)table.getModel();
		changeInfo.setBakList(voList);
		table.setToolTipText("");
		table.setAutoscrolls(true);
		table.setHorizontalScrollEnabled(true);
		table.setColumnControlVisible(true);
		table.setSortable(false);
		table.setEditable(false);
		InstallCheckBoxColumn checkColumn = new InstallCheckBoxColumn(
				table.getColumn(i18n
						.get("foss.gui.creating.BatchCreateWaybillQueryAction.BatchCheckBoxColumn.choice")),
				this);
		List<JCheckBox> lit = checkColumn.getRenderButtonList();
		this.setList(lit);
		this.setCheckBoxColumn(checkColumn);
		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
	}

	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setChangeDetail(List<OtherChargeVo> changeDetailList,
			List<OtherChargeVo> bakList) {
		ChangeInfoDetailTableModel changeInfoDetailTableModel = ((ChangeInfoDetailTableModel) table
				.getModel());
		changeInfoDetailTableModel.setData(getDataMessage(changeDetailList));
		changeInfoDetailTableModel.setBakList(bakList);
		// 刷新表格数据
		changeInfoDetailTableModel.fireTableDataChanged();
	}

	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getQueryParam() {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode()
				.getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		if ("A".equals(bean.getGoodsType()) || "B".equals(bean.getGoodsType())) {
			// 如果是A B货，则设置为全部，因为增值服务里面没有A B类型
			queryDto.setGoodsTypeCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);// 货物类型CODE
		} else {
			queryDto.setGoodsTypeCode(bean.getGoodsType());// 货物类型CODE
		}
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(Common.nullBigDecimalToZero(bean
				.getGoodsWeightTotal()));// 重量
		queryDto.setVolume(Common.nullBigDecimalToZero(bean
				.getGoodsVolumeTotal()));// 体积
		queryDto.setOriginnalCost(null);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setBillTime(bean.getBillTime());// 开单时间
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}

	/**
	 * 
	 * 将查询出的其他费用设置到表格list中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:00:49
	 */
	public List<OtherChargeVo> getOtherChargeList(List<ValueAddDto> list) {
		List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();
		int i = 1;
		if (list != null) {
			for (ValueAddDto dto : list) {

				// 开单的时候不能增加更改费
				if (PriceEntityConstants.PRICING_CODE_GGF.equals(dto
						.getSubType())
						|| PriceEntityConstants.PRICING_CODE_SHJC.equals(dto
								.getSubType())) {
					continue;
				}
				/**
				 * 至尊宝费用冲减判断是否显示
				 * 
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-04-14下午14:40
				 */
				if (bean.getInsuranceAmount() != null
						&& bean.getInsuranceAmount().compareTo(
								new BigDecimal(NUM_50000)) < 0
						&& PriceEntityConstants.PRICING_CODE_ZZB
								.equalsIgnoreCase(dto.getSubType())) {
					continue;
				}
				OtherChargeVo vo = new OtherChargeVo();
				vo.setI(i++);
				vo.setCode(dto.getSubType());// 费用编码
				vo.setChargeName(dto.getSubTypeName());// 安装品名
				vo.setType(dto.getPriceEntityName());// 归集类别
				// vo.setDescrition(dto.getDescription());//zxy 20131118
				// KDTE-5995 修改：改成把code 改成 Description
				vo.setMoney(dto.getFee().toString());// 金额
				vo.setNum(1);// 安装件数
				/*
				 * vo.setVolume("0"); vo.setExcessiveFloorNum("0");
				 */
				vo.setUpperLimit(dto.getMaxFee().toString());// 最高收费
				vo.setLowerLimit(dto.getMinFee().toString());// 最低收费
				vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto
						.getCanmodify()));// 是否可修改
				vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto
						.getCandelete()));// 是否可删除
				vo.setId(dto.getId());
				voList.add(vo);
			}
		}
		return voList;
	}

	/**
	 * 
	 * 设置其他费用到运单开单界面的其他费用表格中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午08:41:48
	 */
	private void setOtherCharge() {
		JXTable otherTable = QueryInstallPanel.table;
		ChangeInfoDetailTableModel model = (ChangeInfoDetailTableModel) otherTable
				.getModel();
		Object[][] data = model.getData();
		for (int k = 0; k < data.length; k++) {
			if (data[k][NumberConstants.NUMBER_12].toString().startsWith(WaybillConstants.instCode)) {
				data[k][NumberConstants.NUMBER_4] = Integer.parseInt(data[k][NumberConstants.NUMBER_4].toString())
						* Integer.parseInt(data[k][NumberConstants.NUMBER_5].toString());
			}
			if (data[k][NumberConstants.NUMBER_12].toString().startsWith(WaybillConstants.instsCode)) {
				String[] str = data[k][NumberConstants.NUMBER_6].toString().split("//.");
				// 计算体积的问题
				Double intg;
				if (str.length > 1) {
					if(Integer.parseInt(str[0])<1)
					{
					intg = Double.valueOf(str[0]) + 1;
					data[k][NumberConstants.NUMBER_4] = Double.valueOf(data[k][NumberConstants.NUMBER_4].toString()) * intg
							* Double.valueOf( ((data[k][NumberConstants.NUMBER_7]==null||"".equals(data[k][NumberConstants.NUMBER_7]))?"0":data[k][NumberConstants.NUMBER_7]).toString());
					}
					else
					{
						data[k][NumberConstants.NUMBER_4] = Double.valueOf(data[k][NumberConstants.NUMBER_4].toString()) * Double.valueOf(str[0])
								* Double.valueOf(data[k][NumberConstants.NUMBER_6].toString())
								* Double.valueOf( ((data[k][NumberConstants.NUMBER_7]==null||"".equals(data[k][NumberConstants.NUMBER_7]))?"0":data[k][NumberConstants.NUMBER_7]).toString());
					}
				} else {
					data[k][NumberConstants.NUMBER_4] = Double.valueOf(data[k][NumberConstants.NUMBER_4].toString())
							* Double.valueOf(data[k][NumberConstants.NUMBER_6].toString())
							* Double.valueOf( ((data[k][NumberConstants.NUMBER_7]==null||"".equals(data[k][NumberConstants.NUMBER_7]))?"0":data[k][NumberConstants.NUMBER_7]).toString());
				}

			}
		}
		
		//bean.getSpecialValueAddedServiceType();特殊增值服务
//		throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.woodListener.ten"));

		//提货方式
		if(bean.getReceiveMethod()!=null&&otherTable.getSelectedRows().length>0)
		{
			//对数据code进行封装
			List<String> list=new ArrayList<String>();
			/*public static final String instCode = "JZ";//安装code
			public static final String instsCode = "RGBL";//上楼code
			public static final String instqCode = "TS-SHF";//区域code
*/			//对选择信息进行分装
			for(int i :otherTable.getSelectedRows())
			{
				if(!"".equals(String.valueOf(data[i][NumberConstants.NUMBER_12])))
				{
					//对安装费和上楼费就行过滤
					if(String.valueOf(data[i][NumberConstants.NUMBER_12]).startsWith(WaybillConstants.instCode))
					{
						list.add(WaybillConstants.instCode);
					}
					if(String.valueOf(data[i][NumberConstants.NUMBER_12]).startsWith(WaybillConstants.instsCode))
					{
						list.add(WaybillConstants.instsCode);
					}
					else if(!String.valueOf(data[i][NumberConstants.NUMBER_12]).startsWith(WaybillConstants.instsCode)&&!String.valueOf(data[i][NumberConstants.NUMBER_12]).startsWith(WaybillConstants.instCode))
					{
					list.add(String.valueOf(data[i][NumberConstants.NUMBER_12]));
					}
				}
				
			}
		    //送货上楼(家装)
			if(WaybillConstants.SEND_UPSTAIRS_EQUIP.equals(bean.getReceiveMethod().getValueCode()))
			{
				//送货安装//送货不上楼(家装)存在这两个提示错误
				if(list.contains(WaybillConstants.SHAZ)||list.contains(WaybillConstants.SHBSL)
						||!list.contains(WaybillConstants.SHSL))
				{
					MsgBox.showInfo(i18n.get("foss.gui.creating.QueryInstallPeanel.MsgBox.shsl.t"));
					return;
				}
			}
			//送货安装
			if(WaybillConstants.SEND_AND_EQUIP.equals(bean.getReceiveMethod().getValueCode()))
			{
				
				//判断是否送货上楼，送货费
				if(!list.contains(WaybillConstants.SHAZ)||list.contains(WaybillConstants.SHBSL)
						||list.contains(WaybillConstants.SHSL)||!list.contains(WaybillConstants.instCode))
				{
					MsgBox.showInfo(i18n.get("foss.gui.creating.QueryInstallPeanel.MsgBox.shaz.t"));
					return;
				}
			}
			//送货不上楼(家装)
			if(WaybillConstants.SEND_NO_UPSTAIRS.equals(bean.getReceiveMethod().getValueCode()))
			{
				//判断是否送货上楼，送货费
				if(list.contains(WaybillConstants.SHAZ)||!list.contains(WaybillConstants.SHBSL)
						||list.contains(WaybillConstants.SHSL))
				{
					MsgBox.showInfo(i18n.get("foss.gui.creating.QueryInstallPeanel.MsgBox.shbsl.t"));
					return;
				}
			}
		}
		
		setDataTble(getOtherChargeVo(data,otherTable.getSelectedRows()));
		/*waybillEditUI.incrementPanel.setChangeDetail(getOtherChargeVoList(data,
				otherTable.getSelectedRows()));*/
		bean.setInstallationEntityList(getInstallationEntity(data,otherTable.getSelectedRows()));
		
	}
		/**
		 * 
		 * 其他費用累計
		 * @author 025000-FOSS-helong
		 * @date 2013-4-18 下午04:17:58
		 */
		private void otherSum(List<OtherChargeVo> data)
		{
			BigDecimal otherCharge = BigDecimal.ZERO;
			if (data != null && !data.isEmpty()) {
				for (int i = 0; i < data.size(); i++) {
					OtherChargeVo otherVo = data.get(i);
					otherCharge = otherCharge.add(new BigDecimal(otherVo.getMoney()));
				}
			}
			bean.setOtherFee(otherCharge);
			bean.setOtherFeeCanvas(otherCharge.toString());
		}
	    /**
	     * 将选择的数据加入到前端table  选择的数据      多写的重复代码，
	     */
		public void setDataTble(List<OtherChargeVo> otherChargeVoList)
		{
			//获取页面中已经有的数据
			JXTable otherTable = waybillEditUI.incrementPanel.getTblOther();
			WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
			List<OtherChargeVo> data = model.getData();
			List<OtherChargeVo> dataN = model.getData();
			if(dataN.size()>0)
        	{
            for(OtherChargeVo otherChargeVo:otherChargeVoList)
            {
            	int j=1;
            	//遍历
            	for(int i=0;i<dataN.size();i++)
            	{
            		if(!"".equals(otherChargeVo.getCode())&&otherChargeVo.getCode()!=null)
            		{
            			if(otherChargeVo.getCode().equals(dataN.get(i).getCode()))
            			{
            				data.remove(dataN.get(i));
            				data.add(otherChargeVo);
            			}
            			else
                		{
            				if(j==1)
            				{
            				data.add(otherChargeVo);
            				j++;
            				}
                		}
            		}
            	}	
            	}
            }
        	else
        	{
        		for(OtherChargeVo otherChargeVo:otherChargeVoList)
                {
        		data.add(otherChargeVo);
                }
        	}
          //去重
			for(int i=0;i<data.size();i++)
			{
				  for (int j = data.size() - 1 ; j > i; j--)  //内循环是 外循环一次比较的次数
	                {

	                    if (data.get(i).getCode() == data.get(j).getCode())
	                    {
	                    	data.remove(j);
	                    }

	                }
			}

            for(OtherChargeVo otherChargeV:data)
    	    {
    	    	if(!"".equals(otherChargeV.getCode())&&otherChargeV.getCode()!=null)
    	    	{
    	    		if(WaybillConstants.SHAZ.equals(otherChargeV.getCode())||WaybillConstants.SHBSL.equals(otherChargeV.getCode())
    	    				||WaybillConstants.SHSL.equals(otherChargeV.getCode()))
    	    		{
    	    			//计算
    	    		Double doubl=	Double.valueOf(otherChargeV.getMoney())*(bean.getGoodsVolumeTotal().doubleValue());
    	    		Double doubleS=Double.valueOf((otherChargeV.getLowerLimit()==null||"".equals(otherChargeV.getLowerLimit())
    		    			   ?"":otherChargeV.getLowerLimit()));
    	    	    if(doubl.compareTo(doubleS)<0)
    	    		   {
    	    			otherChargeV.setMoney(otherChargeV.getLowerLimit());
    	    		   }
	    	    	   else
	    	    	   {
	    	    		   otherChargeV.setMoney(String.valueOf(doubl)); 
	    	    	   }
    	    		}
    	    	}
    	    }
            otherSum(data);
            this.waybillEditUI.incrementPanel.setChangeDetail(data);
        	this.waybillEditUI.billingPayPanel.getBtnSubmit().setEnabled(false);// 提交为不可编辑
			
		}
		 /**
	     * 对选择的数据进行list转换(q前端页面)
	     * @param objects
	     * @param rows
	     * @return
	     */
		public List<OtherChargeVo> getOtherChargeVo(Object[][] objects,int[] rows){
	    	List<OtherChargeVo> list=new ArrayList<OtherChargeVo>();
			for(int i:rows)
			{
				OtherChargeVo otherChargeVo=new OtherChargeVo();
				otherChargeVo.setChargeName((objects[i][2]==null||"".equals(objects[i][2]))?"":String.valueOf(objects[i][2]));
				otherChargeVo.setMoney((objects[i][NumberConstants.NUMBER_4]==null||"".equals(objects[i][NumberConstants.NUMBER_4]))?"":objects[i][NumberConstants.NUMBER_4].toString());
				otherChargeVo.setNum((objects[i][NumberConstants.NUMBER_5]==null||"".equals(objects[i][NumberConstants.NUMBER_5]))?0:Integer.valueOf(objects[i][NumberConstants.NUMBER_5].toString()));
				otherChargeVo.setVolume((objects[i][NumberConstants.NUMBER_6]==null||"".equals(objects[i][NumberConstants.NUMBER_6]))?"":String.valueOf(objects[i][NumberConstants.NUMBER_6]));
				otherChargeVo.setExcessiveFloorNum((objects[i][NumberConstants.NUMBER_7]==null||"".equals(objects[i][NumberConstants.NUMBER_7]))?"":String.valueOf(objects[i][NumberConstants.NUMBER_7]));
				otherChargeVo.setUpperLimit((objects[i][NumberConstants.NUMBER_8]==null||"".equals(objects[i][NumberConstants.NUMBER_8]))?"":objects[i][NumberConstants.NUMBER_8].toString());
				otherChargeVo.setLowerLimit((objects[i][NumberConstants.NUMBER_9]==null||"".equals(objects[i][NumberConstants.NUMBER_9]))?"":objects[i][NumberConstants.NUMBER_9].toString());
				otherChargeVo.setCode((objects[i][NumberConstants.NUMBER_12]==null||"".equals(objects[i][NumberConstants.NUMBER_12]))?"":String.valueOf(objects[i][NumberConstants.NUMBER_12]));
				otherChargeVo.setIsDelete((Boolean)objects[i][NumberConstants.NUMBER_11]);
				otherChargeVo.setIsUpdate((Boolean)objects[i][NumberConstants.NUMBER_10]);
				otherChargeVo.setId((objects[i][NumberConstants.NUMBER_13]==null||"".equals(objects[i][NumberConstants.NUMBER_13]))?"":String.valueOf(objects[i][NumberConstants.NUMBER_13]));
				list.add(otherChargeVo);
			}	
			return list;   	
	    }
    /**
     * 对选择的数据进行list转换
     * @param objects
     * @param rows
     * @return
     */
    public List<InstallationEntity> getInstallationEntity(Object[][] objects,int[] rows){
    	List<InstallationEntity> list=new ArrayList<InstallationEntity>();
		for(int i:rows)
		{
			InstallationEntity installation=new InstallationEntity();
			installation.setInstallationName(String.valueOf(objects[i][2]));
			installation.setAmount(BigDecimal.valueOf(Double.parseDouble(objects[i][NumberConstants.NUMBER_4].toString())));
			installation.setInstallPackages(objects[i][NumberConstants.NUMBER_5]==null?"":String.valueOf(objects[i][NumberConstants.NUMBER_5]));
			installation.setHandlingVolume(objects[i][NumberConstants.NUMBER_5]==null?"":String.valueOf(objects[i][NumberConstants.NUMBER_6]));
			installation.setExceedNumberFloors(objects[i][NumberConstants.NUMBER_5]==null?"":String.valueOf(objects[i][NumberConstants.NUMBER_7]));
			installation.setUpperLimit(BigDecimal.valueOf(Double.parseDouble(objects[i][NumberConstants.NUMBER_8].toString())));
			installation.setLowerLimit(BigDecimal.valueOf(Double.parseDouble(objects[i][NumberConstants.NUMBER_9].toString())));
			installation.setInstallationCode(String.valueOf(objects[i][NumberConstants.NUMBER_12]));
			list.add(installation);
		}	
		return list;   	
    }

	/**
	 * 数组转成list对数据的更改
	 */
	public List<OtherChargeVo> getOtherChargeVoList(Object[][] objects,
			int[] rows) {

		List<OtherChargeVo> list = new ArrayList<OtherChargeVo>();
		for (int i = 0; i < rows.length; i++) {
			OtherChargeVo otherChargeVo = new OtherChargeVo();
			otherChargeVo.setChargeName((String) objects[i][2]);
			otherChargeVo.setMoney(objects[i][NumberConstants.NUMBER_4].toString());
			otherChargeVo.setCode(objects[i][NumberConstants.NUMBER_12].toString());
			list.add(otherChargeVo);
		}

		return list;
	}
	
	/**
	 * table表格的Enter键监控
	 * 
	 * @author WangQianJin
	 * @date 2013-5-20 下午3:25:28
	 */
	/*
	 * public void tableListenter(){ setOtherCharge(); // 关闭界面，释放资源 dispose(); }
	 */
	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class ChangeInfoDetailTableModel extends DefaultTableModel implements
			Serializable {

		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 国际化对象
		 */
		private II18n i18n = I18nManager
				.getI18n(ChangeInfoDetailTableModel.class);

		private Object[][] data;
		
		private List<OtherChargeVo> bakList;

		public List<OtherChargeVo> getBakList() {
			return bakList;
		}
		public void setBakList(List<OtherChargeVo> bakList) {
			this.bakList = bakList;
		}
		
		// log object
		private Log log = LogFactory.getLog(ChangeInfoDetailTableModel.class);

		// 常量配置区
		private String[] tableHeader = {
				i18n.get("foss.gui.creating.BatchCreateWaybillQueryAction.BatchCheckBoxColumn.choice"),

				i18n.get("foss.gui.creating.queryOtherChargePanel.column.eight"),

				i18n.get("foss.gui.creating.queryOtherChargePanel.column.ten"),

				i18n.get("foss.gui.creating.queryOtherChargePanel.column.one"),

				i18n.get("foss.gui.creating.queryOtherChargePanel.column.three"),

				i18n.get("foss.gui.creating.queryOtherChargePanel.column.eleven"),

				i18n.get("foss.gui.creating.queryOtherChargePanel.column.twelve"),

				i18n.get("foss.gui.creating.queryOtherChargePanel.column.thirteen"),

				i18n.get("foss.gui.creating.queryOtherChargePanel.column.fourteen"),

				i18n.get("foss.gui.creating.queryOtherChargePanel.column.fifteen"),

				i18n.get("foss.gui.creating.queryOtherChargePanel.column.six"),

				i18n.get("foss.gui.creating.queryOtherChargePanel.column.seven")

		};

		public ChangeInfoDetailTableModel(Object[][] data) {
            	this.data=data;
		}

		/**
		 * get column count
		 */
		@Override
		public int getColumnCount() {
			return tableHeader.length;
		}

		@Override
		public int getRowCount() {
			if (data != null) {
				return data.length;
			} else {
				return 0;
			}
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (data != null) {
				try {
					return data[rowIndex][columnIndex];
				} catch (Exception e) {
					return null;
				}
			} else {
				return null;
			}

		}

		@Override
		public String getColumnName(int column) {

			return tableHeader[column];
		}

		/**
		 * 默认情况下这个方法不用重新实现的，但是这样就会造成如果这个列式boolean的类型，就当做string来处理了
		 * 如果是boolean的类型那么用checkbox来显示
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			Object o = getValueAt(0, columnIndex);
			if (o != null) {
				return o.getClass();
			} else {
				return Null.class;
			}

		}
		
		/**
		 * 来判断当前选中的单元格是够可以被编辑，因为我是从第二列需要可以编辑的，也就是复选框的列可以编辑的，故 我有个逻辑判断的哈
		 */
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == NumberConstants.NUMBER_6 || columnIndex == NumberConstants.NUMBER_7 || columnIndex == 0
					|| columnIndex == NumberConstants.NUMBER_5) {
				return true;
			}
			return false;
		}

		/**
		 * 如果这个列可以被编辑的话，但是没有这个方法，当回车后是恢复之前的内容的，只有在这个地方通过对新值的 获取然后再设置进去才会被更改的。
		 */
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			log.info("[row: " + rowIndex + ", column: " + columnIndex
					+ ", value: " + value + " ]");

			data[rowIndex][columnIndex] = value;
		}

		public void adjustColumnPreferredWidths(JXTable table) {
			// strategy - get max width for cells in column and
			// make that the preferred width
			TableColumnModel columnModel = table.getColumnModel();
			for (int col = 0; col < table.getColumnCount(); col++) {

				TableColumn column = columnModel.getColumn(col);
				if (col >= NumberConstants.NUMBER_13 && col <= NumberConstants.NUMBER_19) {
					column.setMinWidth(0);
					column.setMaxWidth(0);
					column.setWidth(0);
					column.setPreferredWidth(0);
					continue;
				}
				if (col == 0 || col == NumberConstants.NUMBER_4) {
					column.setPreferredWidth(NumberConstants.NUMBER_50);
					column.setMaxWidth(NumberConstants.NUMBER_50);
				} else if (col == 1) {
					column.setPreferredWidth(NumberConstants.NUMBER_330);// 250
					column.setMaxWidth(NumberConstants.NUMBER_330);//
				} else if (col == NumberConstants.NUMBER_6) {
					column.setPreferredWidth(NumberConstants.NUMBER_130);
					column.setMaxWidth(NumberConstants.NUMBER_130);
				} else if (col == NumberConstants.NUMBER_10) {
					column.setPreferredWidth(NumberConstants.NUMBER_180);
					column.setMaxWidth(NumberConstants.NUMBER_180);
				} else if (col == NumberConstants.NUMBER_20) {
					column.setPreferredWidth(NumberConstants.NUMBER_150);
					column.setMaxWidth(NumberConstants.NUMBER_150);
				} else {
					column.setPreferredWidth(NumberConstants.NUMBER_100);
				}
			} // for col
		}

		public Object[][] getData() {
			return data;
		}

		public void setData(Object[][] data) {
			this.data = data;
		}
		
		/*	*//**
		 * table界面移除行，可自动更新序号值和table界面
		 * 
		 * @param serialNum
		 *            serialNum 开始序号值；如果serialNum小于0则不会自动更新序号
		 * @param row
		 *            要移除的行，从0开始
		 */

	}

	/**
	 * 全选
	 * 
	 * @author shixw
	 * 
	 */
	class AllListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			Object obj = e.getSource();
			if (obj.equals(allSelectCheckBox)) {
				if (allSelectCheckBox.isSelected()) {
					// /全选
					if (list != null) {
						int i = 0;
						for (JCheckBox c : list) {
							checkBoxColumn.setRow(i);
							c.setSelected(true);
							i++;
						}
					}
				} else {
					// 全不选
					if (list != null) {
						int i = 0;
						for (JCheckBox c : list) {
							checkBoxColumn.setRow(i);
							c.setSelected(false);
							i++;
						}
					}
				}

				if (table != null) {
					refreshTable(table);
				}
			}
		}
	}

	class SelectListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			int index = table.getSelectedRow();
			if (index > -1) {
				int row = table.convertRowIndexToView(index);
				JCheckBox ch = list.get(row);
				checkBoxColumn.setRow(row);
				if (ch.isSelected()) {
					ch.setSelected(false);
				} else {
					ch.setSelected(true);
				}

			}

		}
	}

	/**
	 * 刷新table
	 * 
	 * @param table
	 */
	public static void refreshTable(JXTable table) {
		table.setAutoscrolls(true);
		table.setColumnControlVisible(true);
		// 设置表头不伸缩模式：如果手工调整一个表头栏目，其他的不会跟随着变的
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setHorizontalScrollEnabled(true);
		// 设置表头的宽度
		BatchCreateWaybillTableModel.adjustColumnPreferredWidths(table);
		table.setSortable(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
	}

	public List<JCheckBox> getList() {
		return list;
	}

	public void setList(List<JCheckBox> list) {
		this.list = list;
	}

	public InstallCheckBoxColumn getCheckBoxColumn() {
		return checkBoxColumn;
	}

	public void setCheckBoxColumn(InstallCheckBoxColumn checkBoxColumn) {
		this.checkBoxColumn = checkBoxColumn;
	}
}
