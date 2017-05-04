package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.common.CopyContent;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillImportWeightUI;
import com.deppon.foss.module.pickup.creating.client.ui.ewaybill.LTLEWaybillTableModel;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * 批量导入重量体积的信息查询
 * @author 305082
 *
 */
public class LTLEWaybillQueryWeightAction implements IButtonActionListener<LTLEWaybillImportWeightUI> {
	//国际化
	private static final II18n i18n = I18nManager.getI18n(LTLEWaybillQueryWeightAction.class);
	
	private static final Logger LOG = Logger.getLogger(LTLEWaybillQueryWeightAction.class);

	/**
	 * 定义常量数字
	 */
	private static final Integer ZERO = 0;
	
	private static final Integer ONE = 1;
	
	private static final Integer FIVE = 5;
	
	private static final Integer TEN = 10;
	
	private static final Integer SEVEN = 7;
	
	private static final Integer EIGHT = 8;
	
	private static final Integer TWELVE = 12;
	
	private static final Integer FIFTY = 50;
	
	private static final Double TWENTY_FOUR = 24.0;
	
	private static final Double SIXTY = 60.0;
	
	private static final Double ONE_THOUSAND = 1000.0;
	//初始化运单服务对象
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	//UI
	private LTLEWaybillImportWeightUI ui;
	
	/**
	 * 查询按钮
	 * 此查询只能查询出批量导入重量体积的更改信息查询
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			//运单号
			String waybillNo = ui.getTxtMixNo().getText();
			//客户编码
			String deliverCustomerCode = ui.getTxtCustomerCode().getText();
			//更改状态
			DataDictionaryValueVo rfcStatusVo = (DataDictionaryValueVo) ui.getComboWaybillStatusModel().getSelectedItem();
			
			//任务部门
			DataDictionaryValueVo rfcTaskDept = (DataDictionaryValueVo) ui.getComboWaybillTaskDept().getSelectedItem();
			//查询参数
			LTLEWaybillChangeWeightQueryDto dto = new LTLEWaybillChangeWeightQueryDto();
			//部门编码
			String currentDeptCode = "";
			//当前部门
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			//判定当前登陆人信息是否为空
			if(user == null){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillOffLinePendingService.exception.nullPreUserinfo"));
			}
			currentDeptCode = user.getEmployee().getDepartment().getCode();
			//查询当前部门，当选择全选时不设置部门编码，
			if(null != rfcTaskDept && !rfcTaskDept.getValueCode().equals("all")){
				dto.setCurrentDeptCode(currentDeptCode);
			}
			//查询结果
			List<LTLEWaybillChangeWeightDto> expBatchChangeWeightDtoList = null;
			//运单号精准查询
			if(StringUtils.isNotBlank(waybillNo)){
				if(waybillNo.length() < EIGHT || waybillNo.length() >TWELVE){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.errorBillNo"));
				}
				//运单号
				dto.setWaybillNo(waybillNo);
				expBatchChangeWeightDtoList = waybillService.queryLTLEWaybillChangeWeightResult(dto);
			}else{
				//发货客户编码长度的设定
				if(StringUtils.isNotBlank(deliverCustomerCode)){
					if(deliverCustomerCode.length() > FIFTY){
						throw new WaybillValidateException(i18n.get("foss.gui.creating.consignerPanel.deliveryCustomerCode.label")+i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"));
					}
				}
				//进行时间的判定
				if (ui.getImportStartTime().getDate() != null && ui.getImportEndTime().getDate() != null) {
					// 制单时间天数
					long zdiff = ui.getImportEndTime().getDate().getTime() - ui.getImportStartTime().getDate().getTime();
					// 查询天数
					double days = zdiff / (ONE_THOUSAND * SIXTY * SIXTY * TWENTY_FOUR);
					// 查询天数不能超过7天
					if (days > SEVEN) {
						throw new WaybillValidateException(i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.importTime")
								+ i18n.get("foss.gui.creating.salesDeptSearchAction.exception.overTimeSpan"));
					}
				} else {
					// 要求提交时间完整，或全部为空
					throw new WaybillValidateException(i18n.get("foss.gui.creating.LTLEWaybillChangeWeightUI.comboWaybillStatusModel.importTime")+i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"));
				}
				//客户编码
				dto.setCustomerCode(deliverCustomerCode);
				//更改单状态
				if(rfcStatusVo != null){
					dto.setChangeStatus(rfcStatusVo.getValueCode());
				}
				//导入开始时间
				dto.setStartTime(ui.getImportStartTime().getDate());
				//导入结束时间
				dto.setEndTime(ui.getImportEndTime().getDate());//查询参数的封装
				
				//执行后台数据查询
				expBatchChangeWeightDtoList = waybillService.queryLTLEWaybillChangeWeightResult(dto);
			}
			//判断查询结果
			if(CollectionUtils.isEmpty(expBatchChangeWeightDtoList)){
				MsgBox.showInfo(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.nullListQuery"));
			}
			
			/**
			 * 下面被修改
			 */
			ui.getAllSelectCheckBox().setSelected(false);
			ui.getTable().setModel(new LTLEWaybillTableModel(expBatchChangeWeightDtoList));
			CopyContent.makeFace(ui.getTable());
			ui.getTableModel().fireTableDataChanged();
			//居中显示
			DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
			cr.setHorizontalAlignment(JLabel.CENTER);
			ui.getTable().setDefaultRenderer(Object.class, cr);
			ui.getTable().getColumnModel().getColumn(ZERO).setPreferredWidth(FIVE);
			ui.getTable().getColumnModel().getColumn(ONE).setPreferredWidth(TEN);
		} catch (BusinessException e1) {
			LOG.error(e1.getMessage());
			MsgBox.showInfo(e1.getMessage());
		}
	}

	@Override
	public void setInjectUI(LTLEWaybillImportWeightUI ui) {
		this.ui = ui;
	}
}