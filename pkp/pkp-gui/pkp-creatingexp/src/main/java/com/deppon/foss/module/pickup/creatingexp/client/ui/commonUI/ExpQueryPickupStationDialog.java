package com.deppon.foss.module.pickup.creatingexp.client.ui.commonUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections.CollectionUtils;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.common.client.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IOrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.VehicleAgencyDeptServiceFactory;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ExpQueryPickupStationDialog  extends JDialog {

	private static final long serialVersionUID = 7365684689555002395L;

	private static final int NUM_450 = 450;
	
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(ExpQueryPickupStationDialog.class);

	private JLabel queryLabel;

	private JTextField textField;

	// 查询按钮
	private JButton btnNewButton;

	// 显示的查询到的table 信息LIST
	private List<SaleDepartmentEntity> tableDetailListInfo = new ArrayList<SaleDepartmentEntity>();

	private List<OuterBranchEntity> tableInfoOfOuterBranchParamsDto = new ArrayList<OuterBranchEntity>();

	// 接收从主界面传过来的DTO
	private QueryPickupPointDto temp = new QueryPickupPointDto();

	// 标示 判断是 1. false根据条件查询外部网点 空运偏线 2 true根据条件查询本地网点
	private Boolean queryType;
	
	// true：表示是通过gis进行地址匹配带出的提货网点 
	private Boolean isMix = false;

	// 要传递的选中的那一行信息
	private SaleDepartmentEntity selectedSaleDepartmentInfo;

	// 要传递的选中的那一行信息
	private OuterBranchEntity selectedOuterBranchEntity;
	
	// 显示的table
	private JXTable tbltable;
	
	//运单bean
	private WaybillPanelVo bean;
	
	//将选择的网点信息返回
	BranchVo branchVO = null;
	
	/**
	 * 组织信息服务接口
	 */
	IOrgInfoService orgInfoService;

	//业务工具类
	BusinessUtils bu = new BusinessUtils();
	
	
	public BranchVo getBranchVO() {
		return branchVO;
	}

	// 传递接口的函数
	public OuterBranchEntity getSelectedOuterBranchEntity() {
		return selectedOuterBranchEntity;
	}

	// 传递接口的函数
	public SaleDepartmentEntity getSelectedSaleDepartmentInfo() {
		return selectedSaleDepartmentInfo;
	}



	/**
	 * <p>
	 * 有参数的getInstance接收传过来的DTO 创建一个新的实例 QueryPickupStationDialog
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-1 上午12:07:09
	 * @param targetOrg
	 */
	public ExpQueryPickupStationDialog(QueryPickupPointDto dto) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		if (DictionaryValueConstants.OUTERBRANCH_TYPE_PX.equals(dto.getDestNetType())|| DictionaryValueConstants.OUTERBRANCH_TYPE_KY.equals(dto.getDestNetType())) 
		{
			queryType = false;
		} else if (DictionaryValueConstants.DEPPON_OWN_ORG.equals(dto.getDestNetType())) {
			queryType = true;
		}
		this.initData(false,null);
		this.queryPickupStatonInfo(dto);
		this.addTableListener();
		this.addButtonListener();
		
		//监听Enter键
		ExpEnterKeyForQueryPickup enter=new ExpEnterKeyForQueryPickup(btnNewButton);		
		textField.addKeyListener(enter);
		ExpEnterKeyForQueryPickup enterTabel=new ExpEnterKeyForQueryPickup(tbltable,this);		
		tbltable.addKeyListener(enterTabel);
	}
	
	/**
	 * 重载QueryPickupStationDialog方法，传入部门对象集合
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 上午9:43:10
	 */
	public  ExpQueryPickupStationDialog(List<BranchVo> depts,WaybillPanelVo bean) {
		super(ApplicationContext.getApplication().getWorkbench().getFrame());
		//非空判断
		if(CollectionUtils.isNotEmpty(depts)){
			//初始化数据和界面
			this.initData(true,bean);
			//过滤提货网点信息
			this.queryPickupStatonInfo(depts);
			//表格监听事件
			this.addTableListener();
			//按钮监听事件
			this.addButtonListener();
		}else{
			//不做业务处理
		}
	
	}
	
	/**
	 * 填充网点表格数据
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-31 上午9:39:18
	 */
	public void fillPickupOrgData(List<SaleDepartmentEntity> depts){
		//获取提货网点表格模型
		SaleDepartmentInfoModel tableModel = ((SaleDepartmentInfoModel) tbltable.getModel());
		// 清除原有行
		tableModel.setRowCount(0);
		//设置网点数据
		tableModel.setData(depts);
		// 刷新表格数据
		tableModel.fireTableDataChanged();
		
		//表格监听事件
		addTableListener();
		//按钮监听事件
		addButtonListener();
	}


	public Boolean getQueryType() {
		return queryType;
	}

	/**
	 * <p>
	 * (通过目的站过滤 提货网点)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-1 上午11:50:09
	 * @param targetOrg2
	 * @see
	 */
	private void queryPickupStatonInfo(QueryPickupPointDto dto) {
		// queryType == false 去查外部网点表 分为按目的站和提货网点查询
		if (!queryType) { // queryType is false
			// 以下程序包含按提货网点和 目的站查询 ,在sql里面控制是走的哪个查询
			// 判断依据 是根据 传来的 提货网点PickUpPoint和目的站orgSimpleName 哪个为空
			if (dto.getOrgSimpleName() != null) {
				temp.setOrgSimpleName(dto.getOrgSimpleName().trim());
			}
			if (dto.getPickUpPoint() != null) {
				temp.setPickUpPoint(dto.getPickUpPoint().trim());
				//如果提货网点有值那么需要清空目的站查询条件
				temp.setOrgSimpleName("");
			}
			//网点类型：偏线代理网点
			if (DictionaryValueConstants.OUTERBRANCH_TYPE_PX.equals(dto.getDestNetType())) {
				temp.setDestNetType(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
			} 
			//网点类型：空运代理网点
			else if (DictionaryValueConstants.OUTERBRANCH_TYPE_KY.equals(dto.getDestNetType())) {
				temp.setDestNetType(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
			}
			//是否自提
			if (CommonUtils.verdictPickUpSelf(dto.getPickUpType())) {
				temp.setPickUpSelf(FossConstants.YES);
			}
			//是否派送
			if (CommonUtils.verdictPickUpDoor(dto.getPickUpType())) {
				temp.setPickUpDoor(FossConstants.YES);
			}
			//是否机场
			if (dto.getIsAirport() != null) {
				temp.setIsAirport(dto.getIsAirport());			
			}
			//是否到达
			if (dto.getArrive() != null) {
				temp.setArrive(dto.getArrive());
			}
			//是否有效
			temp.setActive(FossConstants.ACTIVE);

			tableInfoOfOuterBranchParamsDto = VehicleAgencyDeptServiceFactory.getVehicleAgencyDeptService().queryOuterBranchs(temp);
			OuterBranchEntityModel tableModel = ((OuterBranchEntityModel) tbltable.getModel());
			tableModel.setRowCount(0);// 清除原有行
			tableModel.setData(tableInfoOfOuterBranchParamsDto);
			// 刷新表格数据
			tableModel.fireTableDataChanged();

		} else {
			temp.setSalesType(DictionaryValueConstants.ORG_ARRIVE);
			//更改单的返货可以查询到收货部门
			if(!WaybillRfcConstants.RETURN.equals(dto.getSource())){
				temp.setReceiveOrgCode(dto.getReceiveOrgCode());
			}
			if (dto.getOrgSimpleName() != null) {
				temp.setOrgSimpleName(dto.getOrgSimpleName().trim());
			}
			if (dto.getPickUpPoint() != null) {
				temp.setPickUpPoint(dto.getPickUpPoint().trim());
				//如果提货网点有值那么需要清空目的站查询条件
				temp.setOrgSimpleName("");
			}
			if (dto.getTransType() != null) {
				temp.setTransType(dto.getTransType());
			}
			//是否自提
			if (CommonUtils.verdictPickUpSelf(dto.getPickUpType())) {
				temp.setPickUpSelf(FossConstants.YES);
			}
			//是否派送
			if (CommonUtils.verdictPickUpDoor(dto.getPickUpType())) {
				temp.setPickUpDoor(FossConstants.YES);
			}
			//是否到达
			if (dto.getArrive() != null) {
				temp.setArrive(dto.getArrive());
			}
			//当前时间(当前日期要大于等于开业日期)
			temp.setCurDate(new java.util.Date());
			//是否有效
			temp.setActive(FossConstants.ACTIVE);
			
		
			tableDetailListInfo = 
						DownLoadDataServiceFactory.getSalesDepartmentService().querySalesDepartmentInfo(temp);
			
			
			
			fillPickupOrgData(tableDetailListInfo);
		}
	}
	
	/**
	 * 根据GIS返回的部门对象，查询提货网点相关信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-29 上午10:11:11
	 */
	private void queryPickupStatonInfo(List<BranchVo> depts) {
		//判断空操作 
		if(CollectionUtils.isEmpty(depts)){
			return ;
		}
		
		//获得表格对象
		BranchVoModel tableModel = ((BranchVoModel) tbltable.getModel());
		tableModel.setRowCount(0);// 清除原有行
		tableModel.setData(depts);
		// 刷新表格数据
		tableModel.fireTableDataChanged();
	}

	/**
	 * <p>
	 * (初始化)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-31 上午9:02:26
	 * @see
	 */
	private void addButtonListener() {
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isMix){
					QueryPickupPointDto dto = new QueryPickupPointDto();
					// 网点类型标志
					dto.setDestNetType(bean.getProductCode().getDestNetType());
					// 提货方式
					dto.setPickUpType(bean.getReceiveMethod().getValueCode());
					// 产品编码
					dto.setTransType(bean.getProductCode().getCode());
					// 目的站 
					dto.setOrgSimpleName(bu.getSimpleName(bean.getTargetOrgCode(),bean.getBillTime()));
					// 出发营业部
					dto.setReceiveOrgCode(bean.getReceiveOrgCode());
					
					if (DictionaryValueConstants.OUTERBRANCH_TYPE_PX.equals(dto.getDestNetType())|| DictionaryValueConstants.OUTERBRANCH_TYPE_KY.equals(dto.getDestNetType())) 
					{
						queryType = false;
					} else if (DictionaryValueConstants.DEPPON_OWN_ORG.equals(dto.getDestNetType())) {
						queryType = true;
					}
					
					queryPickupStatonInfo(dto);
				}
				
				if (!queryType) {
					setValue();// 内部类只能20行方法,这里方法太长 进行抽取 set up value
					tableInfoOfOuterBranchParamsDto = VehicleAgencyDeptServiceFactory.getVehicleAgencyDeptService().queryOuterBranchs(temp);
					setModel();
				} else {
					setValue();// 内部类只能20行方法,这里方法太长 进行抽取 set up value
					tableDetailListInfo = DownLoadDataServiceFactory.getSalesDepartmentService().querySalesDepartmentInfo(temp);
					setSaleDepartmentInfoModel();
				}
				//获取光标
				if(tbltable!=null && tbltable.getRowCount()>0){
					tbltable.requestFocus();
					tbltable.setRowSelectionAllowed(true);
					tbltable.setRowSelectionInterval(0,0);
				}
			}

			private void setSaleDepartmentInfoModel() {
				SaleDepartmentInfoModel tableModel = ((SaleDepartmentInfoModel) tbltable.getModel());
				tableModel.setRowCount(0);// 清除原有行
				tableModel.setData(tableDetailListInfo);
				// 刷新表格数据
				tableModel.fireTableDataChanged();
			}

			/**
			 * 内部类只能20行方法,这里方法太长 进行抽取 set up value
			 * @param tableModel OuterBranchEntityModel
			 */
			private void setModel() {
				OuterBranchEntityModel tableModel = ((OuterBranchEntityModel) tbltable.getModel());
				tableModel.setRowCount(0);// 清除原有行
				tableModel.setData(tableInfoOfOuterBranchParamsDto);
				tableModel.fireTableDataChanged();// 刷新表格数据
			}
			
			private void setValue() {// 内部类只能20行方法,这里方法太长 进行抽取 set up value
				// 目的站 is cityCode
				// 该叶面上的查询条件 is pickupPoint
				temp.setPickUpPoint(textField.getText().trim());
				temp.setOrgSimpleName("");
			}
		});

	}
	


	/**
	 * <p>
	 * (表格的监听事件)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-4 下午9:11:09
	 * @see
	 */
	private void addTableListener() {
		tbltable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					//获得选中行
					int row = tbltable.getSelectedRow();
					if(row>=0){
    					//判断是否为gis匹配的提货网点
    					if(!isMix){
    						if (!queryType) {
    							OuterBranchEntityModel model = (OuterBranchEntityModel) tbltable.getModel();
    							List<OuterBranchEntity> data = model.getData();
    							selectedOuterBranchEntity = data.get(row);
    							setBranchVo();
    							dispose();
    						} else {
    							SaleDepartmentInfoModel model = (SaleDepartmentInfoModel) tbltable.getModel();
    							List<SaleDepartmentEntity> data = model.getData();
    							selectedSaleDepartmentInfo = data.get(row);
    							setBranchVo();
    							dispose();
    						}
    					}else{
    						BranchVoModel model = (BranchVoModel) tbltable.getModel();
    						List<BranchVo> data = model.getData();
    						branchVO = data.get(row);
    						dispose();
    					}
					}
				}
			}
		});
	}
	
	/**
	 * <p>
	 * (表格的监听事件)
	 * </p>
	 * 
	 * @author WangQianJin
	 * @date 2013-05-16
	 * @see
	 */
	public void tableListenerForEnter() {		
		//获得选中行
		int row = tbltable.getSelectedRow();
		if(row>=0){
			//判断是否为gis匹配的提货网点
			if(!isMix){
				if (!queryType) {
					OuterBranchEntityModel model = (OuterBranchEntityModel) tbltable.getModel();
					List<OuterBranchEntity> data = model.getData();
					selectedOuterBranchEntity = data.get(row);
					setBranchVo();
					dispose();
				} else {
					SaleDepartmentInfoModel model = (SaleDepartmentInfoModel) tbltable.getModel();
					List<SaleDepartmentEntity> data = model.getData();
					selectedSaleDepartmentInfo = data.get(row);
					setBranchVo();
					dispose();
				}
			}else{
				BranchVoModel model = (BranchVoModel) tbltable.getModel();
				List<BranchVo> data = model.getData();
				branchVO = data.get(row);
				dispose();
			}
		}				
	}
	
	/**
	 * 
	 * 将自有网点数据以及代理网点数据设置到网点VO中
	 * @author 025000-FOSS-helong
	 * @date 2012-12-13 下午05:58:51
	 */
	private  void setBranchVo()
	{
		if(branchVO == null)
		{
			branchVO = new BranchVo();
		}
		if(queryType)
		{
			branchVO.setCode(selectedSaleDepartmentInfo.getCode());
			branchVO.setName(selectedSaleDepartmentInfo.getName());
			branchVO.setSingleBillLimitkg(selectedSaleDepartmentInfo.getSingleBillLimitkg());
			branchVO.setSingleBillLimitvol(selectedSaleDepartmentInfo.getSingleBillLimitvol());
			branchVO.setSinglePieceLimitkg(selectedSaleDepartmentInfo.getSinglePieceLimitkg());
			branchVO.setSinglePieceLimitvol(selectedSaleDepartmentInfo.getSinglePieceLimitvol());
			branchVO.setCanAgentCollected(selectedSaleDepartmentInfo.getCanAgentCollected());
			//是否可自提
			branchVO.setPickupSelf(selectedSaleDepartmentInfo.getPickupSelf());
			//是否送货上门
			branchVO.setDelivery(selectedSaleDepartmentInfo.getDelivery());
			//所属城市被临时存放在PICKUP_AREA_DESC列当中传来出来
			branchVO.setCityCode(selectedSaleDepartmentInfo.getPickupAreaDesc());
			//取消到达日期
			branchVO.setCancelArrivalDate(selectedSaleDepartmentInfo.getCancelArrivalDate());
			//转货部门
			branchVO.setTransferGoodDept(selectedSaleDepartmentInfo.getTransferGoodDept());
			//是否支持货到付款
			branchVO.setArriveCharge(selectedSaleDepartmentInfo.getCanCashOnDelivery());
			//是否支持返单签收
			branchVO.setCanReturnSignBill(selectedSaleDepartmentInfo.getCanReturnSignBill());
			//是否支持到达
			branchVO.setArrive(selectedSaleDepartmentInfo.getArrive());
		}else{
			//代理编号
			branchVO.setCode(selectedOuterBranchEntity.getAgentDeptCode());
			//代理名称
			branchVO.setName(selectedOuterBranchEntity.getAgentDeptName());
			//目的站
			branchVO.setTargetOrgName(selectedOuterBranchEntity.getSimplename());
			//是否代收货款
			branchVO.setCanAgentCollected(selectedOuterBranchEntity.getHelpCharge());
			//是否可自提
			branchVO.setPickupSelf(selectedOuterBranchEntity.getPickupSelf());
			//是否送货上门
			branchVO.setDelivery(selectedOuterBranchEntity.getPickupToDoor());
			//所属城市code
			branchVO.setCityCode(selectedOuterBranchEntity.getCityCode());
			//是否支持货到付款
			branchVO.setArriveCharge(selectedOuterBranchEntity.getArriveCharge());
			//是否支持返单签收
			branchVO.setCanReturnSignBill(selectedOuterBranchEntity.getReturnBill());
			//是否支持到达
			branchVO.setArrive(selectedOuterBranchEntity.getArrive());
		}
	
	}
	
	/**
	 * (初始化)
	 * @author foss-jiangfei
	 * @date 2012-10-31 上午9:02:24
	 * @see
	 * 
	 * 修改人：李凤腾
	 * 修改时间：2013/1/29
	 * 修改内容：给init增加“是否提货网点”参数，以便在使用GIS匹配到达网点时重用代码，默认值为false
	 */
	private void initData(boolean isPickupOrg,WaybillPanelVo bean) {
		if(!isPickupOrg){
			if (!queryType) {
				tbltable = new JXTable(new OuterBranchEntityModel());
			} else {
				tbltable = new JXTable(new SaleDepartmentInfoModel());
			}
			isMix = false;
		}else{
			tbltable = new JXTable(new BranchVoModel());
			isMix = true;
			this.bean = bean;
		}
		initGui();
	}
	
	/**
	 * 初始化提货网点查询界面
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-29 下午4:16:00
	 */
	private void initGui() {
		setTitle(i18n.get("foss.gui.common.queryPickupStationDialog.pickUpStation.title"));
		setSize(NumberConstants.NUMBER_700, NUM_450);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(43dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(159dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(47dlu;pref)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(188dlu;default)"), },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(16dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(228dlu;default)"), }));

		queryLabel = new JLabel(i18n.get("foss.gui.common.waybillEditUI.common.name"));
		panel.add(queryLabel, "2, 2, fill, fill");

		textField = new JTextField();
		panel.add(textField, "4, 2, fill, default");
		textField.setColumns(10);

		btnNewButton = new JButton(i18n.get("foss.gui.common.buttonPanel.quickQuery.label"));
		panel.add(btnNewButton, "6, 2");

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "2, 4, 7, 1, fill, fill");

		tbltable.setAutoscrolls(true);
		tbltable.setSortable(false);
		tbltable.setEditable(false);
		tbltable.setColumnControlVisible(true);
		scrollPane.setViewportView(tbltable);
		// 设置模态
		setModal(true);
	}
}

/**
 * 
 * 封装表格的数据模型，设置成以对象进行操作
 * 
 * @author FOSS-jiangfei
 * @date 2012-11-3 上午11:30:03
 */
class SaleDepartmentInfoModel extends DefaultTableModel {

	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(SaleDepartmentInfoModel.class);
	
	private static final long serialVersionUID = 5883365603131625962L;

	private static final int THREE = 3;

	private static final int FOUR = 4;

	private List<SaleDepartmentEntity> saleDepartmentInfoList;

	public List<SaleDepartmentEntity> getData() {
		return saleDepartmentInfoList;
	}

	public void setData(List<SaleDepartmentEntity> saleDepartmentInfoList) {
		this.saleDepartmentInfoList = saleDepartmentInfoList;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return i18n.get("foss.gui.common.queryPickupStationDialog.columnName.pickUpStationCode");
		case 1:
			return i18n.get("foss.gui.common.queryPickupStationDialog.columnName.pickUpStationName");
		case 2:
			return i18n.get("foss.gui.common.queryPickupStationDialog.columnName.selfPickUp");
		case THREE:
			return i18n.get("foss.gui.common.queryPickupStationDialog.columnName.homeDelivery");
		default:
			return "";
		}
	}

	@Override
	public int getColumnCount() {
		return FOUR;
	}

	@Override
	public int getRowCount() {
		return saleDepartmentInfoList == null ? 0 : saleDepartmentInfoList.size();
	}

	@Override
	public Class<String> getColumnClass(int column) {
		switch (column) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case THREE:
			return String.class;
		default:
			return null;
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return saleDepartmentInfoList.get(row).getCode();
		case 1:
			return saleDepartmentInfoList.get(row).getName();
		case 2:
			return CommonUtils.dataTransform(saleDepartmentInfoList.get(row).getPickupSelf());
		case THREE:
			return CommonUtils.dataTransform(saleDepartmentInfoList.get(row).getDelivery());
		default:
			return super.getValueAt(row, column);
		}
	}
}

/**
 * 
 * 封装表格的数据模型，设置成以对象进行操作
 * 
 * @author FOSS-jiangfei
 * @date 2012-11-3 上午11:30:03
 */
class OuterBranchEntityModel extends DefaultTableModel {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -5039259275224547907L;

	private static final int THREE = 3;

	private static final int FOUR = 4;
	
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(OuterBranchEntityModel.class);

	/**
	 * 外发网点信息
	 */
	private List<OuterBranchEntity> outerBranchEntityList;

	public List<OuterBranchEntity> getData() {
		return outerBranchEntityList;
	}

	public void setData(List<OuterBranchEntity> outerBranchEntityList) {
		this.outerBranchEntityList = outerBranchEntityList;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return i18n.get("foss.gui.common.queryPickupStationDialog.columnName.pickUpStationCode");
		case 1:
			return i18n.get("foss.gui.common.queryPickupStationDialog.columnName.pickUpStationName");
		case 2:
			return i18n.get("foss.gui.common.queryPickupStationDialog.columnName.selfPickUp");
		case THREE:
			return i18n.get("foss.gui.common.queryPickupStationDialog.columnName.homeDelivery");
		default:
			return "";
		}
	}

	@Override
	public int getColumnCount() {
		return FOUR;
	}

	@Override
	public int getRowCount() {
		return outerBranchEntityList == null ? 0 : outerBranchEntityList.size();
	}

	@Override
	public Class<String> getColumnClass(int column) {
		switch (column) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case THREE:
			return String.class;
		default:
			return null;
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return outerBranchEntityList.get(row).getAgentDeptCode();
		case 1:
			return outerBranchEntityList.get(row).getAgentDeptName();
		case 2:
			return CommonUtils.dataTransform(outerBranchEntityList.get(row).getPickupSelf());
		case THREE:
			return CommonUtils.dataTransform(outerBranchEntityList.get(row).getPickupToDoor());
		default:
			return super.getValueAt(row, column);
		}
	}
}


/**
 * 封装表格的数据模型，设置成以对象进行操作
 * @author 026123-foss-lifengteng
 * @date 2013-1-29 下午2:47:41
 */
class BranchVoModel extends DefaultTableModel {
	/**
	 * 生成序列化标识
	 */
	private static final long serialVersionUID = -3510741085028703745L;

	private static final int THREE = 3;

	private static final int FOUR = 4;
	
	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(BranchVoModel.class);
	
	/**
	 * 提货网点对象
	 */
	private List<BranchVo> branchVos;
	
	/**
	 * 获得表格对象集合
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-29 下午3:19:08
	 */
	public List<BranchVo> getData() {
		return branchVos;
	}

	/**
	 * 设置表格对象集合
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-29 下午3:19:39
	 */
	public void setData(List<BranchVo> branchVos) {
		this.branchVos = branchVos;
	}

	/** 
	 * 获得表格列
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-29 下午3:11:18
	 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return i18n.get("foss.gui.common.queryPickupStationDialog.columnName.pickUpStationCode");
		case 1:
			return i18n.get("foss.gui.common.queryPickupStationDialog.columnName.pickUpStationName");
		case 2:
			return i18n.get("foss.gui.common.queryPickupStationDialog.columnName.selfPickUp");
		case THREE:
			return i18n.get("foss.gui.common.queryPickupStationDialog.columnName.homeDelivery");
		default:
			return "";
		}
	}

	/** 
	 * 表格总列数
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-29 下午3:11:37
	 * @see javax.swing.table.DefaultTableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return FOUR;
	}

	/** 
	 * 获得总行数
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-29 下午3:11:48
	 * @see javax.swing.table.DefaultTableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return CollectionUtils.isEmpty(branchVos) ? 0 : branchVos.size();
	}

	/** 
	 * 获得列对象
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-29 下午3:12:03
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<String> getColumnClass(int column) {
		switch (column) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case THREE:
			return String.class;
		default:
			return null;
		}
	}

	/** 
	 * 根据行列获得数值
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-29 下午3:12:32
	 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return branchVos.get(row).getCode();
		case 1:
			return branchVos.get(row).getName();
		case 2:
			return CommonUtils.dataTransform(branchVos.get(row).getPickupSelf());
		case THREE:
			return CommonUtils.dataTransform(branchVos.get(row).getDelivery());
		default:
			return super.getValueAt(row, column);
		}
	}
	
	/**
	 * @return the branchVos .
	 */
	public List<BranchVo> getBranchVos() {
		return branchVos;
	}
	
	/**
	 *@param branchVos the branchVos to set.
	 */
	public void setBranchVos(List<BranchVo> branchVos) {
		this.branchVos = branchVos;
	}
	
	

}
