package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.ui.PictureViewButtonColumn;
import com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillSearchButtonColumn;
import com.deppon.foss.module.pickup.creating.client.ui.SearchPictureTableMode;
import com.deppon.foss.module.pickup.creating.client.ui.SearchPictureWaybillUI;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.SearchPictureWaybillCondiction;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.vo.SearchPictureVo;
import com.deppon.foss.util.CollectionUtils;
/**
 * 图片查询 查询action
 * @author hehaisu
 *
 */
public class SearchPictureQueryAction extends AbstractButtonActionListener<SearchPictureWaybillUI> {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(SearchPictureQueryAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(SearchPictureQueryAction.class);
	
	private static final String ALL = "ALL";
	
	/**
	 * 一天毫秒
	 */
	private static final double MS = 86400000.0;

	
	/**
	 * 图片开单远程接口
	 */
	IWaybillHessianRemoting waybillHessianRemoting;

	private SearchPictureWaybillUI ui;
	
	@Override
	public void setIInjectUI(SearchPictureWaybillUI ui) {
		this.ui = ui;
	}

	/**
	 * 查询按钮功能
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void actionPerformed(ActionEvent e) {
		try {
			//通常的查询条件
			SearchPictureWaybillCondiction condiction = createSearchConditionNormal();
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			if(!CollectionUtils.isEmpty(user.getRoleids())){
				for(String roleId:user.getRoleids()){
					if(WaybillConstants.PKP_400_CS_REP.equals(roleId)){
						//全部的图片开单查询条件
						condiction = createSearchConditionAllRole();
						break;
					}
				}
			}
			waybillHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
			
			final JXTable table = ui.getTable();
			SearchPictureTableMode tableModel;

			List<SearchPictureVo> list = waybillHessianRemoting.searchPictureWaybillByCondiction(condiction);
			
			//如果结果集数据小于等于0，则提示查询为空对话框
			if(list.size() <= 0){
				MsgBox.showInfo(i18n.get("foss.gui.creating.SearchPictureQueryAction.MsgBox.nullListQuery"));
			}

			Object[][] datas = ui.getArray(list);
			// 刷新表格
			tableModel = new SearchPictureTableMode(datas);
			table.setModel(tableModel);
			
			//显示总条数
			ui.getTotal().setText("总共" + list.size() + "条数据");
			
			new PictureWaybillSearchButtonColumn(table.getColumn(1), ui, tableModel);
			new PictureViewButtonColumn(table.getColumn(NumberConstants.NUMBER_4), ui, tableModel);

			ui.refreshTable(table);

		} catch (BusinessException ee) {
			LOGGER.error("sale deptsearch BusinessException", ee);
			MsgBox.showError(ee.getMessage());
		}

	}
	/**
	 * 构建所有权限的查询条件
	 */
	private SearchPictureWaybillCondiction createSearchConditionAllRole() {
		SearchPictureWaybillCondiction condiction = new SearchPictureWaybillCondiction();
		// 运单、
		String mixNo = ui.getTxtMixNo().getText().trim();
		// 订单号
		String orderNo = ui.getTxtOrder().getText().trim();
		if(StringUtils.isNotBlank(mixNo)||StringUtils.isNotBlank(orderNo)){
			if (StringUtils.isNotBlank(mixNo)) {
				condiction.setMixNo(mixNo);
			}
			if (StringUtils.isNotBlank(orderNo)) {
				condiction.setOrderNo(orderNo);
			}
		}else{
			//构建查询条件
			createOtherCondiction(condiction);
			
		}
		return condiction;
	}
	
	/**
	 * 构建基本查询条件
	 */
	private SearchPictureWaybillCondiction createSearchConditionNormal() {
		SearchPictureWaybillCondiction condiction = new SearchPictureWaybillCondiction();
		// 运单、
		String mixNo = ui.getTxtMixNo().getText().trim();
		if (StringUtils.isNotBlank(mixNo)) {
			condiction.setMixNo(mixNo);
		}
		// 订单号
		String orderNo = ui.getTxtOrder().getText().trim();
		if (StringUtils.isNotBlank(orderNo)) {
			condiction.setOrderNo(orderNo);
		}
		//构建查询条件
		createOtherCondiction(condiction);
		return condiction;
	}
	
	/**
	 * 构建运输性质/运单图片类型/开单人/收货部门/起止时间
	 */
	private void createOtherCondiction(SearchPictureWaybillCondiction condiction){
		//运输性质
		String transType = ((DataDictionaryValueVo) ui.getTransTypeComboBox().getSelectedItem()).getValueCode();
		if (StringUtils.isNotBlank(transType)) {
			if (ALL.equals(transType)) {
				transType = null;
			}
			condiction.setTransType(transType);
		}
		//运单图片类型
		String waybillPictureType = ((DataDictionaryValueVo) ui.getWaybillPictureTypeComboBox().getSelectedItem()).getValueCode();
		if (StringUtils.isNotBlank(waybillPictureType)) {
			List<String> waybillPictureTypes = new ArrayList<String>();
			if (!ALL.equals(waybillPictureType)) {
				waybillPictureTypes.add(waybillPictureType);
			} else {
				waybillPictureTypes = this.getComboxCodes(ui.getWaybillPictureTypeComboBox());
			}
			condiction.setWaybillPictureType(waybillPictureTypes);
		}
		//开单人
		String operator = ui.getOperator().getText().trim();
		if (StringUtils.isNotBlank(operator)) {
			condiction.setOperator(operator);
		}
		//收货部门
		String receiveOrgCode = ((DataDictionaryValueVo) ui.getReceiveOrgCodeComboBox().getSelectedItem()).getValueCode();
		if (StringUtils.isNotBlank(receiveOrgCode)) {
			if (!ALL.equals(receiveOrgCode)) {
				//查询
				condiction.setIsAll(WaybillConstants.NO);
				condiction.setReceiveOrgCode(receiveOrgCode);
			} else {
				//选择了all,则查pic_pending表 belongorgcode=当前开单组
				condiction.setIsAll(WaybillConstants.YES);
				UserEntity user = (UserEntity) SessionContext.getCurrentUser();
				String currentOrgCode = user.getEmployee().getOrgCode();
				condiction.setReceiveOrgCode(currentOrgCode);
			}
		}
		//起止时间
		Date startTime = ui.getFossStartDate().getDate();
		Date endTime = ui.getFossdEndDate().getDate();
		if (startTime != null && endTime != null) {
			// 提交时间天数
			long submitDays = endTime.getTime() - startTime.getTime();
			// 查询天数
			double days = submitDays / MS;
			// 查询天数不能超过3天
			if (days > NumberConstants.NUMBER_3) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.SearchPictureTableMode.column.submitTime")
						+ i18n.get("foss.gui.creating.SearchPictureWaybillUI.exception.overTimeSpan"));
			}
		}
		if (startTime != null) {
			condiction.setCreateStartTime(startTime);
		} else {
			//请将提交时间填写完整
			throw new WaybillValidateException(i18n.get("foss.gui.creating.SearchPictureWaybillUI.exception.enterFullSubmitTime"));
		}
		
		if (endTime != null) {
			condiction.setCreateEndTime(endTime);
		}
		//司机姓名
		String driverWorkNo = ui.getDriverNoInput().getText().trim();
		if (StringUtils.isNotBlank(driverWorkNo)) {
			condiction.setDriverWorkNo(driverWorkNo);
		}
		//司机工号
		String driverName = ui.getDriverNameInput().getText().trim();
		if (StringUtils.isNotBlank(driverName)) {
			condiction.setDriverName(driverName);
		}
	}
	
	/**
	 * 获得下拉框中全部值的编码
	 */
	private List<String> getComboxCodes(JComboBox comboBox) {
		ComboBoxModel comb = comboBox.getModel();
		// 定义接收集合
		List<String> list = new ArrayList<String>();
		// 下拉框的值
		int count = comb.getSize();
		// 遍历下拉框
		for (int i = 0; i < count; i++) {
			// 获得下拉选项的code
			String code = ((DataDictionaryValueVo) comb.getElementAt(i)).getValueCode();
			// 过滤掉ALL
			if (!"ALL".equals(StringUtil.defaultIfNull(code))) {
				list.add(code);
			}
		}
		return list;
	}
	
}