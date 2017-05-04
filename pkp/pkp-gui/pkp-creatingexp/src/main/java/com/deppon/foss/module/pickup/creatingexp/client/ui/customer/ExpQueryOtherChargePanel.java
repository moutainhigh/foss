/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.customer;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.annotation.ButtonAction;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.action.ExpOtherChargeQueryAction;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpQueryOtherChargePanel  extends JDialog {


	/**
	 * 10
	 */
	private static final int TEN = 10;

	/**
	 * 12
	 */
	private static final int FONTSIZE12 = 12;

	/**
	 * default css
	 */
	private static final String GROW50DLU = "50dlu:grow";

	/**
	 * 序列货标识
	 */
	private static final long serialVersionUID = -1422237527226994454L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpQueryOtherChargePanel.class); 

	/**
	 * service
	 */
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	// 优惠名称
	private JTextField txtPrivilegeName;

	// 运单界面
	private ExpWaybillEditUI waybillEditUI;

	private JXTable table;
	@ButtonAction(icon = "preview.png", shortcutKey = "", type = ExpOtherChargeQueryAction.class)
	private JButton btnQuery;// 查询

	/**
	 * 构造方法
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-17 上午10:29:14
	 */
	public ExpQueryOtherChargePanel(ExpWaybillEditUI waybillEditUI) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		this.waybillEditUI = waybillEditUI;
		init();
		bind();
		
		//监听Enter
		ExpEnterKeyQueryOtherCharge enter=new ExpEnterKeyQueryOtherCharge(btnQuery);
		txtPrivilegeName.addKeyListener(enter);
		ExpEnterKeyQueryOtherCharge enterTable=new ExpEnterKeyQueryOtherCharge(this);
		table.addKeyListener(enterTable);
	}

	/**
	 * 初始化界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-17 上午10:40:34
	 */
	private void init() {
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("10dlu"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("35dlu"),
						FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(GROW50DLU), FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode(GROW50DLU), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(GROW50DLU), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(GROW50DLU),
						FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(GROW50DLU), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("10dlu"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("60dlu"), }));

		//其它费用查询
		JLabel label = new JLabel(i18n.get("foss.gui.creating.queryOtherChargePanel.title"));
		label.setFont(new Font("宋体", Font.BOLD, FONTSIZE12));
		getContentPane().add(label, "2, 2, 7, 1");

		//优惠名称
		JLabel lblNewLabel = new JLabel(i18n.get("foss.gui.creating.queryOtherChargePanel.privilegeName.label")+"：");
		getContentPane().add(lblNewLabel, "4, 4, 2, 1, left, default");

		txtPrivilegeName = new JTextField();
		getContentPane().add(txtPrivilegeName, "6, 4, 3, 1, fill, default");
		txtPrivilegeName.setColumns(TEN);

		//查询
		btnQuery = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
		getContentPane().add(btnQuery, "10, 4, left, default");

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "4, 6, 13, 1, fill, fill");

		initTable();
		initData();
		scrollPane.setViewportView(table);

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

	/**
	 * 
	 * 初始化表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午10:47:03
	 */
	private void initTable() {
		table = new JXTable();
		table.setModel(new ChangeInfoDetailTableModel());
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setEditable(false);
		table.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

		table.addMouseListener(new ClickTableHandler());
	}

	/**
	 * 
	 * 初始化数据
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:52:18
	 */
	private void initData() {
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(getQueryParam());
		
		if(list!=null && list.size()>0){
			
			List<ValueAddDto> list2 = new ArrayList<ValueAddDto>();
			
			for(int i=0;i<list.size();i++){
				ValueAddDto dto = list.get(i);
				if(
//						"SHJCF".equals(dto.getSubType())
//					||"QT".equals(dto.getSubType())
//					||"GGF".equals(dto.getSubType())
						ExpWaybillConstants.OTHERFEE_SHJCF.equals(dto.getSubType())
					||WaybillConstants.OTHERFEE_RYFJ.equals(dto.getSubType())
					||WaybillConstants.OTHERFEE_ZHXX.equals(dto.getSubType())
					//其他费用中不允许手动添加上门发货优惠
					|| "ZYSCZJH".equals(dto.getSubType())){
					continue;
				}else{
					list2.add(dto);
				}
			}
			
			
			List<OtherChargeVo> voList = getOtherChargeList(list2);
			setChangeDetail(voList, voList);
		}
		
	
	}

	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getQueryParam() {
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = waybillEditUI.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();

		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
		queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		queryDto.setGoodsTypeCode(bean.getGoodsType());// 货物类型CODE
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(Common.nullBigDecimalToZero(bean.getGoodsWeightTotal()));// 重量
		queryDto.setVolume(Common.nullBigDecimalToZero(bean.getGoodsVolumeTotal()));// 体积
		queryDto.setOriginnalCost(null);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
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

		if (list != null) {
			for (ValueAddDto dto : list) {
				
				//开单的时候不能增加更改费
				if(PriceEntityConstants.PRICING_CODE_GGF.equals(dto.getSubType())
					|| PriceEntityConstants.PRICING_CODE_SHJC.equals(dto.getSubType())
						|| PriceEntityConstants.PRICING_CODE_KDBZF.equals(dto.getSubType())){
					continue;
				}
				
				OtherChargeVo vo = new OtherChargeVo();
				vo.setCode(dto.getSubType());// 费用编码
				vo.setChargeName(dto.getSubTypeName());// 名称
				vo.setType(dto.getPriceEntityName());// 归集类别
				vo.setDescrition(dto.getDescription());// 描述 zxy 20131121 KDTE-5995 修改：PriceEntityCode 改成 Description
				vo.setMoney(dto.getFee().toString());// 金额
				vo.setUpperLimit(dto.getMaxFee().toString());// 上限
				vo.setLowerLimit(dto.getMinFee().toString());// 下限
				vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto.getCanmodify()));// 是否可修改
				vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete()));// 是否可删除
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
		HashMap<String, IBinder<ExpWaybillPanelVo>> map = waybillEditUI.getBindersMap();
		IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
		WaybillPanelVo bean = waybillBinder.getBean();

		JXTable otherTable = waybillEditUI.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if (data != null && !data.isEmpty()) {
			OtherChargeVo otherVo = getOtherCharge();
			addOtherCharge(data, otherVo, bean);
		}else{
			data = new ArrayList<OtherChargeVo>();
			OtherChargeVo otherVo = getOtherCharge();
			addOtherCharge(data, otherVo, bean);
		}
		waybillEditUI.incrementPanel.setChangeDetail(data);
	}

	/**
	 * 
	 * 比较是否存在当前选的费用，不存在则添加费用到表格中，并且进行其他费用合计
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-21 下午07:06:33
	 */
	private void addOtherCharge(List<OtherChargeVo> data, OtherChargeVo newOtherCharge, WaybillPanelVo bean) {
		boolean bool = true;
		OtherChargeVo oldVo = null;

		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				OtherChargeVo otherVo = data.get(i);
				// 比较费用名称，判断是否存在重复的返单费用
				if (otherVo.getChargeName().equals(newOtherCharge.getChargeName())) {
					bool = false;
					oldVo = data.get(i);
					data.remove(i);
					data.add(i, newOtherCharge);
					break;
				}
			}
		}
		// 如果选择的其他费用不存在，则直接添加
		if (bool) {
			if(data != null){
				data.add(newOtherCharge);
			}
			// 累计其他费用合计
			otherSum(data,bean);
			//其他费用更改以后需要重新计算运费
			this.waybillEditUI.buttonPanel.getBtnSubmit().setEnabled(false);
			this.waybillEditUI.billingPayPanel.getBtnSubmit().setEnabled(false);
		} else {
			// 累计其他费用合计
			otherSum(data,bean);
		}
	}
	
	/**
	 * 
	 * 其他費用累計
	 * @author 025000-FOSS-helong
	 * @date 2013-4-18 下午04:17:58
	 */
	private void otherSum(List<OtherChargeVo> data,WaybillPanelVo bean){
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
	 * 
	 * 从表格中获取双击的其他费用数据
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 下午01:49:04
	 */
	private OtherChargeVo getOtherCharge() {
		int row = table.getSelectedRow();
		ChangeInfoDetailTableModel model = (ChangeInfoDetailTableModel) table.getModel();
		List<OtherChargeVo> data = model.getData();
		return data.get(row);
	}

	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setChangeDetail(List<OtherChargeVo> changeDetailList, List<OtherChargeVo> bakList) {
		
		
		ChangeInfoDetailTableModel changeInfoDetailTableModel = ((ChangeInfoDetailTableModel) table.getModel());
		changeInfoDetailTableModel.setBakList(bakList);// 设置备份数据
		changeInfoDetailTableModel.setData(changeDetailList);
		// 刷新表格数据
		changeInfoDetailTableModel.fireTableDataChanged();
	}

	/**
	 * 一般内部类：表格的单、双击处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午10:16:42
	 */
	private class ClickTableHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// 双击
			if (e.getClickCount() == 2) {
				setOtherCharge();
				// 关闭界面，释放资源
				dispose();
			}
		}
	}
	
	/**
	 * table表格的Enter键监控
	 * @author WangQianJin
	 * @date 2013-5-20 下午3:25:28
	 */
	public void tableListenter(){
		setOtherCharge();
		// 关闭界面，释放资源
		dispose();
	}

	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public static class ChangeInfoDetailTableModel extends DefaultTableModel {
		// 7
		private static final int EIGHT = 8;
		// 7
		private static final int SEVEN = 7;
		// 6
		private static final int SIX = 6;

		private static final int FIVE = 5;

		private static final int FOUR = 4;

		private static final int THREE = 3;

		private static final int TWO = 2;

		private static final int ONE = 1;

		private static final int ZERO = 0;

		private static final long serialVersionUID = 5883365603131625962L;
		
		/**
		 * 国际化对象
		 */
		private II18n i18n = I18nManager.getI18n(ChangeInfoDetailTableModel.class); 

		private List<OtherChargeVo> changeDetailList;

		private List<OtherChargeVo> bakList;// 此集合用于在内存中查询数据

		/**
		 * get 查询数据
		 * 
		 * @return
		 */
		public List<OtherChargeVo> getBakList() {
			return bakList;
		}

		/**
		 * set 查询数据
		 * 
		 * @param bakList
		 */
		public void setBakList(List<OtherChargeVo> bakList) {
			this.bakList = bakList;
		}

		/**
		 * get 数据
		 * 
		 * @return
		 */
		public List<OtherChargeVo> getData() {
			return changeDetailList;
		}

		/**
		 * set 数据
		 * 
		 * @return
		 */
		public void setData(List<OtherChargeVo> changeDetailList) {
			this.changeDetailList = changeDetailList;
		}

		/**
		 * 获得选中的VALUE列表 name
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case ZERO:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.zero");
			case ONE:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.one");
			case TWO:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.two");
			case THREE:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.three");
			case FOUR:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.four");
			case FIVE:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.five");
			case SIX:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.six");
			case SEVEN:
				return i18n.get("foss.gui.creating.queryOtherChargePanel.column.seven");
			default:
				return "";
			}
		}

		/**
		 * get column count
		 */
		@Override
		public int getColumnCount() {
			return EIGHT;
		}

		/**
		 * get row column
		 */
		@Override
		public int getRowCount() {
			return changeDetailList == null ? 0 : changeDetailList.size();
		}

		/**
		 * get value
		 */
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case ZERO:
				return changeDetailList.get(row).getChargeName();
			case ONE:
				return changeDetailList.get(row).getType();
			case TWO:
				return changeDetailList.get(row).getDescrition();
			case THREE:
				return changeDetailList.get(row).getMoney();
			case FOUR:
				return changeDetailList.get(row).getUpperLimit();
			case FIVE:
				return changeDetailList.get(row).getLowerLimit();
			case SIX:
				return changeDetailList.get(row).getIsUpdate()? 
						i18n.get("foss.gui.creating.waybillEditUI.common.yes") : 
							i18n.get("foss.gui.creating.waybillEditUI.common.no");
			case SEVEN:
				return changeDetailList.get(row).getIsDelete()? 
						i18n.get("foss.gui.creating.waybillEditUI.common.yes") : 
							i18n.get("foss.gui.creating.waybillEditUI.common.no");
			default:
				return super.getValueAt(row, column);
			}

		}
	}

	/**
	 * getTxtPrivilegeName
	 * 
	 * @return JTextField
	 */
	public JTextField getTxtPrivilegeName() {
		return txtPrivilegeName;
	}

	/**
	 * getTable
	 * 
	 * @return JXTable
	 */
	public JXTable getTable() {
		return table;
	}

}
