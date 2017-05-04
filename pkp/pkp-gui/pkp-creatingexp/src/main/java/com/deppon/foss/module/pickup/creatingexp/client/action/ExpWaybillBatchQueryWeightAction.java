package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.common.CopyContent;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillBatchChangeWeightUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ewaybill.BatchChangeInfoTableModel;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * 批量更改重量查询
 * @author 136334-foss-bailei
 * @date 2015-1-29 下午1:54:16
 */
public class ExpWaybillBatchQueryWeightAction implements IButtonActionListener<ExpWaybillBatchChangeWeightUI> {
	//国际化
	private static final II18n i18n = I18nManager.getI18n(ExpWaybillBatchQueryWeightAction.class);
	
	private static final Logger LOG = Logger.getLogger(ExpWaybillBatchQueryWeightAction.class);
	/**
	 * 调用综合的上报工单
	 */
	private IReturnGoodsRequestEntityService returnGoodsRequestEntityService;
	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}

	//初始化运单服务对象
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	//UI
	private ExpWaybillBatchChangeWeightUI ui;
	
	/**
	 * 执行更改查询
	 * @author 136334-foss-bailei
	 * @date 2015-1-24 下午5:27:34
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
			//运单号长度的判定
			if(StringUtils.isNotBlank(waybillNo)){
				if(waybillNo.length() < NumberConstants.NUMBER_8 || waybillNo.length() >12){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.errorBillNo"));
				}
			}
			//发货客户编码长度的设定
			if(StringUtils.isNotBlank(deliverCustomerCode)){
				if(deliverCustomerCode.length() > NumberConstants.NUMBER_50){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.expEWaybillTableMode.column.eleven")+i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"));
				}
			}
			//进行时间的判定
			if (ui.getImportStartTime().getDate() != null && ui.getImportEndTime().getDate() != null) {
				// 制单时间天数
				long zdiff = ui.getImportEndTime().getDate().getTime() - ui.getImportStartTime().getDate().getTime();
				// 查询天数
				double days = zdiff / (1000.0 * 60.0 * 60.0 * 24.0);
				// 查询天数不能超过7天
				if (days > NumberConstants.NUMBER_7) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.comboWaybillStatusModel.importTime")
							+ i18n.get("foss.gui.creating.salesDeptSearchAction.exception.overTimeSpan"));
				}
			} /*else if ((ui.getImportEndTime().getDate() == null && ui.getImportStartTime().getDate() == null)) {
				// 不做业务处理，因为允许提交时间为空，例如PDA、暂存时就没有提交时间
			}*/ else {
				// 要求提交时间完整，或全部为空
				throw new WaybillValidateException(i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.comboWaybillStatusModel.importTime")+i18n.get("foss.gui.creating.printSignUI.msgbox.inputError"));
			}
			ExpBatchChangeWeightQueryDto dto = new ExpBatchChangeWeightQueryDto();
			//运单号
			dto.setWaybillNo(waybillNo);
			//客户编码
			dto.setCustomerCode(deliverCustomerCode);
			//更改单状态
			if(rfcStatusVo != null){
				dto.setChangeStatus(rfcStatusVo.getValueCode());
			}
			//任务部门
			if(rfcTaskDept != null){
				dto.setCurrentDeptCode(rfcTaskDept.getValueCode());
			}
			//导入开始时间
			dto.setStartTime(ui.getImportStartTime().getDate());
			//导入结束时间
			dto.setEndTime(ui.getImportEndTime().getDate());//查询参数的封装
			String currentDeptCode = "";
			if(dto.getCurrentDeptCode() != null && !"".equals(dto.getCurrentDeptCode())){
				//当前部门
				UserEntity user = (UserEntity) SessionContext.getCurrentUser();
				//判定当前登陆人信息是否为空
				if(user == null){
					throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillOffLinePendingService.exception.nullPreUserinfo")+i18n.get("foss.gui.creating.salesDeptEWaybillUI.query.queryMoreThanFifty"));
				}
				currentDeptCode = user.getEmployee().getDepartment().getCode();
			}
			//当前部门
			dto.setCurrentDeptCode(currentDeptCode);
			//执行后台数据查询
			List<ExpBatchChangeWeightDto> expBatchChangeWeightDtoList = waybillService.queryExpBatchChangeWeightResult(dto);
			if(CollectionUtils.isEmpty(expBatchChangeWeightDtoList)){
				MsgBox.showInfo(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.nullListQuery"));
			}
			
			//ui.getTableModel().setData(expBatchChangeWeightDtoList);
			/**
			 * 下面被修改
			 * @author 270293-foss-zhangfeng
			 * @date 2015-07-11 18:40
			 */
			ui.getAllSelectCheckBox().setSelected(false);
			ui.getTable().setModel(new BatchChangeInfoTableModel(expBatchChangeWeightDtoList));
			CopyContent.makeFace(ui.getTable());
			ui.getTableModel().fireTableDataChanged();
			//居中显示
			DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
			cr.setHorizontalAlignment(JLabel.CENTER);
			ui.getTable().setDefaultRenderer(Object.class, cr);
			ui.getTable().getColumnModel().getColumn(0).setPreferredWidth(NumberConstants.NUMBER_5);
			ui.getTable().getColumnModel().getColumn(1).setPreferredWidth(NumberConstants.NUMBER_10);
		} catch (BusinessException e1) {
			LOG.error(e1.getMessage());
			MsgBox.showInfo(e1.getMessage());
		}
	}

	@Override
	public void setInjectUI(ExpWaybillBatchChangeWeightUI ui) {
		this.ui = ui;
	}
}