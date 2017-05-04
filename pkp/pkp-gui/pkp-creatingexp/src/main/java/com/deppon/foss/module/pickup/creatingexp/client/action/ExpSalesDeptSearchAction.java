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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/SalesDeptSearchAction.java
 * 
 * FILE NAME        	: SalesDeptSearchAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillTypeVo;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.service.impl.WaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpCheckBoxColumn;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpDeliveryBigCustomerColumn;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpLinkTableMode;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpOfflineButtonColumn;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpPendingButtonColumn;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSalesDeptWaybillUI;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;


@SuppressWarnings("rawtypes")
public class ExpSalesDeptSearchAction extends AbstractButtonActionListener<ExpSalesDeptWaybillUI> {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpSalesDeptSearchAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(ExpSalesDeptSearchAction.class);
	
	@Inject
	private ISalesDeptWaybillService salesDeptWaybillService;

	@Inject
	IWaybillOffLinePendingService offLinePendingService;

	@Inject
	private IWaybillService wayBillService;

	private ExpSalesDeptWaybillUI ui;

	private List list;

	private List pendingList;

	// private List offlineList;

	private List waybillList;

	private DataDictionaryValueVo vo;

	@Override
	public void setIInjectUI(ExpSalesDeptWaybillUI ui) {
		this.ui = ui;
	}

	/**
	 * 查询按钮功能
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午02:46:47
	 * @param e
	 * 
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void actionPerformed(ActionEvent e) {
		try {
			// 清空左边的选择check box框子
			ui.getSelectExportWaybillNoList().clear();
			// 清空选中的导出id
			ui.getSelectIdList().clear();

			boolean all = false;// 是否已全部查询完毕

			// 制单时间
			if (ui.getZdStartDate().getDate() != null && ui.getZdEndDate().getDate() != null) {
				// 制单时间天数
				long zdiff = ui.getZdEndDate().getDate().getTime() - ui.getZdStartDate().getDate().getTime();
				// 查询天数
				double days = zdiff / (1000.0 * 60.0 * 60.0 * 24.0);
				// 查询天数不能超过7天
				if (days > NumberConstants.NUMBER_7) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.exception.createTime")
							+ i18n.get("foss.gui.creating.salesDeptSearchAction.exception.overTimeSpan"));
				}
				// 提交时间
				if ((ui.getFossStartDate().getDate() != null && ui.getFossdEndDate().getDate() != null)) {

					// 提交时间天数
					long fdiff = ui.getFossdEndDate().getDate().getTime() - ui.getFossStartDate().getDate().getTime();
					days = fdiff / (1000.0 * 60.0 * 60.0 * 24.0);
					// 查询天数不能超过7天
					if (days > NumberConstants.NUMBER_7) {
						throw new WaybillValidateException("[" + i18n.get("foss.gui.creating.linkTableMode.column.six") + "]，"
								+ i18n.get("foss.gui.creating.salesDeptSearchAction.exception.overTimeSpan"));
					}
				} else if ((ui.getFossStartDate().getDate() == null && ui.getFossdEndDate().getDate() == null)) {
					// 不做业务处理，因为允许提交时间为空，例如PDA、暂存时就没有提交时间
				} else {
					// 要求提交时间完整，或全部为空
					throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.exception.enterFullSubmitTime"));
				}
				final JXTable table = ui.getTable();
				ExpLinkTableMode tableModel;

				// 运单、
				String mixNo = ui.getTxtMixNo().getText().trim();
				// 订单号
				String orderNo = ui.getTxtOrder().getText().trim();

				salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
				offLinePendingService = GuiceContextFactroy.getInjector().getInstance(WaybillOffLinePendingService.class);
				wayBillService = WaybillServiceFactory.getWaybillService();

				// 是否查询离线运单
				boolean flag = ui.getCheckBox().isSelected();
				int k = 0;

				// 离线运单
				if (flag) {
					// 根据运单号查询离线运单数据
					if (StringUtils.isNotEmpty(mixNo) || StringUtils.isNotEmpty(orderNo)) {
						WaybillPendingEntity pending = offLinePendingService.queryPendingByWaybillNoAndOrderNo(mixNo, orderNo);
						if (pending == null) {
							list = null;
						} else {
							list = queryWaybillPending(ui, flag);
						}
					} else {
						list = queryWaybillPending(ui, flag);
					}
				} else {
					/**
					 * 暂存、PDA导入运单 
					 */
					if ("ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())) {
						// 查询待处理运单表中数据，包含有已开单记录
						list = queryWaybillPending(ui, flag);
						// 运单状态编码
						Object typeCode = ui.getStatusComboBox().getSelectedItem();
						if (typeCode != null) {
							DataDictionaryValueVo vo = (DataDictionaryValueVo) typeCode;
							if ("all".equals(vo.getValueCode())) {
								List<DataDictionaryValueEntity> list2 = salesDeptWaybillService.queryPendingType();

								for (int i = list2.size(); i > 0; i--) {
									DataDictionaryValueEntity dataDictionary = (DataDictionaryValueEntity) list2.get(i - 1);
									DataDictionaryValueVo vo2 = new DataDictionaryValueVo();
									ValueCopy.valueCopy(dataDictionary, vo2);
									// 已开单、PDA已补录运单不查询
									if (WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(vo2.getValueCode())
											|| WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(vo2.getValueCode())) {
										continue;
									}// 查询暂存、PDA待补录
									List list3 = queryWaybillPendingVo(ui, flag, vo2);
									if (list3 != null) {
										list.addAll(list3);
										all = true;
									}
								}

							}
						}
					} else {// 离线状态 “现在离线登录，请在线登录！”
						MsgBox.showInfo(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.offLineTip"));
					}

				}

				List list2 = new ArrayList();
				if (CollectionUtils.isNotEmpty(list)) {
					// 离线开单(WaybillPendingEntity类型且为OFFLINE_PENDING的运单)
					if ((list.get(0)) instanceof WaybillPendingEntity
							&& WaybillConstants.WAYBILL_STATUS_OFFLINE_PENDING.equals(((WaybillPendingEntity) list.get(0)).getPendingType())) {
						k = 1;
					} else if ((list.get(0)) instanceof WaybillPendingEntity) {
						k = 2;// 在线待处理运单对象
					} else if ((list.get(0)) instanceof WaybillEntity) {
						k = NumberConstants.NUMBER_3;// 已处理运单对象
					}

					for (int j = 0; j < list.size(); j++) {
						Object o1 = list.get(j);
						String waybillNo = null;
						boolean hasWaybill = false;
						if (o1 instanceof WaybillPendingEntity) {
							WaybillPendingEntity pending = (WaybillPendingEntity) o1;
							waybillNo = pending.getWaybillNo();
						} else if ((o1) instanceof WaybillEntity) {
							WaybillEntity pending = (WaybillEntity) o1;
							waybillNo = pending.getWaybillNo();
						}
						for (int w = 0; w < list2.size(); w++) {
							Object o2 = list2.get(w);
							String waybillNo2 = null;
							if (o2 instanceof WaybillPendingEntity) {
								WaybillPendingEntity pending = (WaybillPendingEntity) o2;
								waybillNo2 = pending.getWaybillNo();
							} else if ((o2) instanceof WaybillEntity) {
								WaybillEntity pending = (WaybillEntity) o2;
								waybillNo2 = pending.getWaybillNo();
							}

							if (waybillNo != null && waybillNo.equals(waybillNo2)) {
								hasWaybill = true;
							}
						}

						if (!hasWaybill) {
							list2.add(o1);
						}
					}

				} else {
					MsgBox.showInfo(i18n.get("foss.gui.creating.salesDeptSearchAction.MsgBox.nullListQuery"));
				}

				if (all) {
					k = 2;
				}

				Object[][] datas = ui.getArray(list2, k);
				// 刷新表格
				tableModel = new ExpLinkTableMode(datas);
				table.setModel(tableModel);

				ExpCheckBoxColumn checkColumn = new ExpCheckBoxColumn(table.getColumn(i18n.get("foss.gui.creating.salesDeptSearchAction.select")), ui);
				List<JCheckBox> list = checkColumn.getRenderButtonList();
				// send all check box to ui for select all
				ui.setList(list);
				ui.setCheckBoxColumn(checkColumn);
				
				
				//设置发货客户的大客户标记
				new ExpDeliveryBigCustomerColumn(table.getColumn(i18n.get("foss.gui.creating.linkTableMode.column.deliveryCustomer")),ui,tableModel,datas);
				
				if (k == 1) {// 离线
					new ExpOfflineButtonColumn(table.getColumn(i18n.get("foss.gui.creating.salesDeptSearchAction.column.operate")), ui);
				} else if (k == 2 || k == NumberConstants.NUMBER_3) {// 在线
					new ExpPendingButtonColumn(table.getColumn(i18n.get("foss.gui.creating.salesDeptSearchAction.column.operate")), ui, tableModel);
				}
				ui.refreshTable(table);

			} else {
				// 制单时间不能为空
				if (null == ui.getZdStartDate().getDate()) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.exception.beginTimeNotNull"));
				}

				// 制单时间不能为空
				if (null == ui.getZdEndDate().getDate()) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.exception.endTimeNotNull"));
				}
			}
			// 默认选中查询结果的第一行
			if (ui.getTable() != null && ui.getTable().getRowCount() > 0) {
				ui.getTable().requestFocus();
				ui.getTable().setRowSelectionAllowed(true);
				ui.getTable().setRowSelectionInterval(0, 0);
			}
		} catch (BusinessException ee) {
			LOGGER.error("sale deptsearch BusinessException", ee);
			MsgBox.showError(ee.getMessage());
		}

	}

	/**
	 * 查询waybill表 即已提交的运单数据（除更改的数据）
	 */
	private List queryWillBill(ExpSalesDeptWaybillUI ui) {
		// 获得运单服务
		wayBillService = WaybillServiceFactory.getWaybillService();
		WaybillDto waybillDto = new WaybillDto();
		WaybillEntity waybillEntity = new WaybillEntity();

		// 运单、订单号
		String waybillNo = ui.getTxtMixNo().getText();
		String orderNo = ui.getTxtOrder().getText();
		waybillDto.setMixNo(waybillNo);
		waybillDto.setOrderNo(orderNo);

		// 运单状态
		waybillEntity.setActive(FossConstants.ACTIVE);
//		//产品类型为经济快递 
//		waybillEntity.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
		//增加是否快递
		waybillEntity.setIsExpress(FossConstants.YES);
		// 收货部门
		if (null != ui.getReceiveOrgCodeComboBox().getSelectedItem()) {
			String selectCode = ((DataDictionaryValueVo) ui.getReceiveOrgCodeComboBox().getSelectedItem()).getValueCode();
			if ("all".equals(selectCode)) {
				// 获得全部下拉框的值
				List<String> receiveList = getOrgCodes(ui.getReceiveOrgCodeComboBox());
				if (CollectionUtils.isNotEmpty(receiveList)) {
					if (receiveList.size() > 1) {
						waybillEntity.setReceiveOrgCodeList(receiveList);
					} else {
						waybillEntity.setReceiveOrgCode(null);
					}
				}
			} else {
				// 设置查询值
				waybillEntity.setReceiveOrgCode(selectCode);
			}
		}

		// 制单部门
		String createOrgCode = ((DataDictionaryValueVo) ui.getCreateOrgCodeComboBox().getSelectedItem()).getValueCode();
		if ("all".equals(createOrgCode)) {
			// 获得CODE集合
			List<String> codeList = getOrgCodes(ui.getCreateOrgCodeComboBox());
			// 判断制单部门类型
			if (CollectionUtils.isNotEmpty(codeList)) {
				if (codeList.size() > 1) {
					waybillEntity.setCreateOrgCodeList(codeList);
				} else {
					waybillEntity.setCreateOrgCode(codeList.get(0));
				}
			}
		} else {
			waybillEntity.setCreateOrgCode(createOrgCode);
		}
		
		//liyongfei 新增运单类型条件
		String waybillTypeCode = ((WaybillTypeVo)ui.getWaybillTypeComboBox().getSelectedItem()).getValueCode();
		if(WaybillRfcConstants.WAYBILL_TYPE_ALL.equals(waybillTypeCode) || StringUtils.isNotBlank(waybillNo) 
				|| StringUtils.isNotBlank(orderNo)){
				waybillDto.setWaybillType(null);
		}else{
			waybillDto.setWaybillType(waybillTypeCode);
		}	
		/**
		 * 若订单号或运单号不为空，则只根据收货部门、制单部门、运单号或订单号进行查询，忽略其它全部查询条件
		 * BUG-7653（出发运单管理，324234234单号已开单，根据单号查询问题）。
		 */
		if (StringUtil.isEmpty(waybillNo) && StringUtil.isEmpty(orderNo)) {
			// 制单时间
			waybillDto.setBillStartTime(ui.getZdStartDate().getDate());
			waybillDto.setBillEndTime(ui.getZdEndDate().getDate());
			// 提交时间
			waybillDto.setCreateStartTime(ui.getFossStartDate().getDate());
			waybillDto.setCreateEndTime(ui.getFossdEndDate().getDate());

			// 运输性质
			Object productCode = ui.getTransTypeComboBox().getSelectedItem();
			if (productCode != null) {
				ProductEntityVo vo = (ProductEntityVo) productCode;
				waybillEntity.setProductCode(vo.getCode());
			} 
//				else {
//				/**
//				 * DEFECT-504
//				 * 002216登陆GUI客户端，快递出发运单管理界面，查询待补录运单，查询出来零担运单
//				 */
//				waybillEntity.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
//			}

			// 运单状态(待处理类型)
			Object typeCode = ui.getStatusComboBox().getSelectedItem();
			if (typeCode != null) {
				vo = (DataDictionaryValueVo) typeCode;
				//判断是否为作废或中止的处理状态
				if(WaybillRfcConstants.INVALID.equals(vo.getValueCode()) || WaybillRfcConstants.ABORT.equals(vo.getValueCode())){
					//若是，则不设置处理状态，让其可以查询已提交或已补录的运单状态
					waybillEntity.setPendingType(null);
				}else{
					//不是，则设置原来处理状态（已提交或已补录）
					waybillEntity.setPendingType(vo.getValueCode());
				}
				waybillEntity.setActive(FossConstants.YES);
			} else {
				waybillEntity.setActive(null);
			}

			// 开单人
			waybillEntity.setCreateUserCode(ui.getCreateUserCode().getText());
		}
		waybillDto.setWaybillEntity(waybillEntity);
		
		
		// 根据封装的条件进行查询
		List<WaybillDto> waybillDtoList = wayBillService.queryActualFreightAndBasicExpress(waybillDto);
		
		//返回值:若是根据运单号进行查询后得出的结果，则不进行再过滤，直接返回值
		if (StringUtil.isEmpty(waybillNo) && StringUtil.isEmpty(orderNo)) {
			//定义接收对象
			List<WaybillEntity> waybillList = null;
			if(CollectionUtils.isNotEmpty(waybillDtoList)){
				waybillList = new ArrayList<WaybillEntity>();
				for (WaybillDto dto : waybillDtoList) {
					waybillList.add(dto.getWaybillEntity());
				}
			}else{
				return waybillList;
			}
		}
		
		return getLastWaybill(waybillDtoList);
		
	}
	
	/**
	 * 根据处理类型返回对应集合
	 * @author 026123-foss-lifengteng
	 * @date 2013年5月30日 上午11:19:57
	 */
	private List<WaybillEntity> getLastWaybill(List<WaybillDto> dtoList){
		//集合非空判断
		if(CollectionUtils.isEmpty(dtoList)){
			return null;
		}
		//定义接收对象
		List<WaybillEntity> waybillList = new ArrayList<WaybillEntity>();
		//值code
		//为空，则表示查询全部处理类型的运单
		if(null == vo||"all".equals(vo.getValueCode())){
			for (WaybillDto dto : dtoList) {
				waybillList.add(dto.getWaybillEntity());
			}
		}else{
			String typeCode = StringUtil.defaultIfNull(vo.getValueCode());
			//遍历集合
			for (WaybillDto dto : dtoList) {
				WaybillEntity waybill = dto.getWaybillEntity();
				ActualFreightEntity actualEntity = dto.getActualFreightEntity();
				//若运单对应的actualFreight类为空，则路过本次循环
				if(null == actualEntity){
					continue;
				}
				String status = convertDataToPendingType(actualEntity.getStatus());
				//是否为中止或作废类型
				if(typeCode.equals(waybill.getPendingType())){
					//相等的处理类型中，去除中止和作废
					if(!status.equals(WaybillRfcConstants.INVALID) && !status.equals(WaybillRfcConstants.ABORT)){
						waybillList.add(waybill);
					}
				}else{
					//不一致时判断：冗余实体中的状态类型与界面是否一致
					if(typeCode.equals(status)){
						waybillList.add(dto.getWaybillEntity());
					}
				}
			}
		}
		
		return waybillList;
	}
	
	/**
	 * 将运单作废或中止的常量值与数据字典中的valueCode对应起来
	 * @author 026123-foss-lifengteng
	 * @date 2013年5月30日 下午04:04:07
	 */
	private String convertDataToPendingType(String status){
		if(WaybillConstants.OBSOLETE.equals(status)){
			return WaybillRfcConstants.INVALID;
		}else if(WaybillConstants.ABORTED.equals(status)){
			return WaybillRfcConstants.ABORT;
		}else{
			return StringUtil.defaultIfNull(status);
		}
	}
	
	/**
	 * 获得下拉框中全部值的编码
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-21 下午4:29:52
	 */
	private List<String> getOrgCodes(JComboBox comboBox) {
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
			if (!"all".equals(StringUtil.defaultIfNull(code))) {
				list.add(code);
			}
		}
		return list;
	}

	/**
	 * 查询WaybillPending表
	 */
	@SuppressWarnings("unchecked")
	private List queryWaybillPending(ExpSalesDeptWaybillUI ui, boolean flag) {
		List list = null;
		// 待补录运单
		WaybillPendingDto pendingDto = new WaybillPendingDto();
		// 待补录运单基本信息
		WaybillPendingEntity pendingEntity = new WaybillPendingEntity();

		// 运单、订单号
		String waybillNo = ui.getTxtMixNo().getText();
		String orderNo = ui.getTxtOrder().getText();
		pendingDto.setMixNo(waybillNo);
		pendingDto.setOrderNo(orderNo);
		
		// 设置处理状态
		pendingEntity.setActive(FossConstants.ACTIVE);
		// 运单类型
		if (null != ui.getWaybillTypeComboBox().getSelectedItem()) {
			String waybillType = ((WaybillTypeVo) ui.getWaybillTypeComboBox().getSelectedItem()).getValueCode();
			// 是否查询全部开单部门
			if (!"ALL".equals(waybillType)) {// 设置查询值
				pendingDto.setWaybillType(waybillType);
			}
			if(StringUtils.isNotBlank(waybillNo) || StringUtils.isNotBlank(orderNo)){
				pendingDto.setWaybillType(null);
			}
			
		}
//		/**
//		 * DEFECT-504
//		 * 002216登陆GUI客户端，快递出发运单管理界面，查询待补录运单，查询出来零担运单
//		 */
		//使用是否快递字段
		pendingEntity.setIsExpress(FossConstants.YES);
		// 收货部门
		if (null != ui.getReceiveOrgCodeComboBox().getSelectedItem()) {
			// 选择的项
			String selectCode = ((DataDictionaryValueVo) ui.getReceiveOrgCodeComboBox().getSelectedItem()).getValueCode();
			if ("all".equals(selectCode)) {
				// 获得全部下拉框的值
				List<String> receiveList = getOrgCodes(ui.getReceiveOrgCodeComboBox());
				if (CollectionUtils.isNotEmpty(receiveList)) {
					if (receiveList.size() > 1) {
						pendingEntity.setReceiveOrgCodeList(receiveList);
					} else {
						pendingEntity.setReceiveOrgCode(null);
					}
				}
			} else {
				// 设置查询值
				pendingEntity.setReceiveOrgCode(selectCode);
			}
		}

		// 制单部门
		String createOrgCode = ((DataDictionaryValueVo) ui.getCreateOrgCodeComboBox().getSelectedItem()).getValueCode();
		if ("all".equals(createOrgCode)) {
			// 获得CODE集合
			List<String> codeList = getOrgCodes(ui.getCreateOrgCodeComboBox());
			// 判断制单部门类型
			if (CollectionUtils.isNotEmpty(codeList)) {
				if (codeList.size() > 1) {
					pendingEntity.setCreateOrgCodeList(codeList);
				} else {
					pendingEntity.setCreateOrgCode(codeList.get(0));
				}
			}
		} else {
			pendingEntity.setCreateOrgCode(createOrgCode);
		}
		
		
		
		/**
		 * 运单处理状态 由于运单状态关系到是从运单表、待处理表还是离线表中查询，所以要单独处理
		 */
		Object typeCode = ui.getStatusComboBox().getSelectedItem();
		DataDictionaryValueVo vo = null;
		if(typeCode != null){
			vo = (DataDictionaryValueVo) typeCode;
		}
		// 是否查询全部
		if ((vo != null && "all".equals(vo.getValueCode())) || StringUtil.isNotEmpty(waybillNo) || StringUtil.isNotEmpty(orderNo)) {
			// 查询已开单的运单信息
			list = queryWillBill(ui);
		} else if (vo != null && (WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(vo.getValueCode()) || WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(vo.getValueCode())
			|| WaybillRfcConstants.INVALID.equals(vo.getValueCode()) || WaybillRfcConstants.ABORT.equals(vo.getValueCode()))	
		) {
			return queryWillBill(ui);
		}

		/**
		 * 若订单号或运单号不为空，则只根据收货部门、制单部门、运单号或订单号进行查询，忽略其它全部查询条件
		 * BUG-7653（出发运单管理，324234234单号已开单，根据单号查询问题）。
		 */
		if (StringUtil.isEmpty(waybillNo) && StringUtil.isEmpty(orderNo)) {

			// 制单时间
			pendingDto.setBillStartTime(ui.getZdStartDate().getDate());
			pendingDto.setBillEndTime(ui.getZdEndDate().getDate());
			// 提交时间
			Date beginFossTime = ui.getFossStartDate().getDate();
			Date endFossTime = ui.getFossdEndDate().getDate();
			pendingDto.setCreateStartTime(beginFossTime);
			pendingDto.setCreateEndTime(endFossTime);

			// 运输性质编码
			Object productCodeTemp = ui.getTransTypeComboBox().getSelectedItem();
			if (productCodeTemp != null) {
				ProductEntityVo vo2 = (ProductEntityVo) productCodeTemp;
				if(ExpWaybillConstants.QUERY_ALL.equals(vo2.getCode())){
					pendingEntity.setProductCode("");
				}else{
					pendingEntity.setProductCode(vo2.getCode());
				}
			} 
//			else {
//				/**
//				 * DEFECT-504
//				 * 002216登陆GUI客户端，快递出发运单管理界面，查询待补录运单，查询出来零担运单
//				 */
//				pendingEntity.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
//			}

			// 开单人
			pendingEntity.setCreateUserCode(ui.getCreateUserCode().getText());

			// 运单状态(待处理类型)
			if (typeCode != null && vo != null) {
				// 运单状态：已开单、PDA已补录、全部
				if (WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(vo.getValueCode()) || WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(vo.getValueCode())
						|| "all".equals(vo.getValueCode())) {
					// 不做处理
				}
				// 根据值编码为是与否来判断是事查询离线运单
				else if (FossConstants.YES.equals(vo.getValueCode()) || FossConstants.NO.equals(vo.getValueCode())) {
					pendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_OFFLINE_PENDING);
					pendingEntity.setActive(vo.getValueCode());
					// PDA待补录运单、运单暂存运单
				} else {
					pendingEntity.setPendingType(vo.getValueCode());
				}
			} else {
				pendingEntity.setPendingType(null);
			}
		}

		pendingDto.setWaybillPending(pendingEntity);
		if (flag) {// 是否离线运单
			pendingList = offLinePendingService.queryPendingExpress(pendingDto);
		} else {
			pendingList = salesDeptWaybillService.queryPendingExpress(pendingDto);
		}

		// 判断集合是否为空
		if (CollectionUtils.isNotEmpty(list)) {
			list.addAll(pendingList);
		} else {
			list = pendingList;
		}
		return list;
	}

	/**
	 * 查询WaybillPending表
	 */
	private List queryWaybillPendingVo(ExpSalesDeptWaybillUI ui, boolean flag, DataDictionaryValueVo vo) {
		WaybillPendingDto pendingDto = new WaybillPendingDto();
		WaybillPendingEntity pendingEntity = new WaybillPendingEntity();

		// 运单、订单号
		pendingDto.setMixNo(ui.getTxtMixNo().getText());
		pendingDto.setOrderNo(ui.getTxtOrder().getText());
		// 制单时间
		pendingDto.setBillStartTime(ui.getZdStartDate().getDate());
		pendingDto.setBillEndTime(ui.getZdEndDate().getDate());
		// 运输性质编码
		Object productCode = ui.getTransTypeComboBox().getSelectedItem();
		if (productCode != null) {
			ProductEntityVo voTemp = (ProductEntityVo) productCode;
			if(ExpWaybillConstants.QUERY_ALL.equals(voTemp.getCode())){
				pendingEntity.setProductCode("");
			}else{
				pendingEntity.setProductCode(voTemp.getCode());
			}
		} 
		// 处理状态
		pendingEntity.setActive(FossConstants.ACTIVE);
//		//产品类型为经济快递 
//		pendingEntity.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
        //增加是否快递字段
		pendingEntity.setIsExpress(FossConstants.YES);
		Date beginFossTime = ui.getFossStartDate().getDate();
		Date endFossTime = ui.getFossdEndDate().getDate();
		/**
		 * 如果有开始时间，没有结束时间，需要提示：截止提交时间为空！
		 */
		if (beginFossTime != null && endFossTime == null) {
			beginFossTime = null;
		}
		/*
		 * 如果有截止时间，没有开始时间，需要提示：起始提交时间为空！
		 */
		if (beginFossTime == null && endFossTime != null) {
			endFossTime = null;
		}

		// 提交时间
		pendingDto.setCreateStartTime(beginFossTime);
		pendingDto.setCreateEndTime(endFossTime);

		// 收货部门
		// 收货部门编码
		if (null != ui.getReceiveOrgCodeComboBox().getSelectedItem()) {
			String receiveOrgCode = ((DataDictionaryValueVo) ui.getReceiveOrgCodeComboBox().getSelectedItem()).getValueCode();
			// 是否查询全部开单部门
			if ("all".equals(receiveOrgCode)) {
				// 获得全部下拉框的值
				List<String> receiveList = getOrgCodes(ui.getReceiveOrgCodeComboBox());
				// 若为全部，则设置List集合属性
				if (CollectionUtils.isNotEmpty(receiveList)) {
					if (receiveList.size() > 1) {
						pendingEntity.setReceiveOrgCodeList(receiveList);
					} else {
						pendingEntity.setReceiveOrgCode(null);
					}
				}
			} else {
				// 设置查询值
				pendingEntity.setReceiveOrgCode(receiveOrgCode);
			}
		}
		
		// 运单类型
		if (null != ui.getWaybillTypeComboBox().getSelectedItem()) {
			String waybillType = ((WaybillTypeVo) ui.getWaybillTypeComboBox().getSelectedItem()).getValueCode();
			// 是否查询全部开单部门
			if (!"ALL".equals(waybillType)) {// 设置查询值
				if(StringUtils.isNotBlank(pendingDto.getMixNo()) || StringUtils.isNotBlank(pendingDto.getOrderNo())){
					pendingDto.setWaybillType(null);
				}else{
					pendingDto.setWaybillType(waybillType);
				}
			}
		}

		// 运输性质
		Object productCodeTemp = ui.getTransTypeComboBox().getSelectedItem();
		if (productCodeTemp != null) {
			ProductEntityVo voTemp = (ProductEntityVo) productCode;
			if(ExpWaybillConstants.QUERY_ALL.equals(voTemp.getCode())){
				pendingEntity.setProductCode("");
			}else{
				pendingEntity.setProductCode(voTemp.getCode());
			}
		} 
//		else {
//			pendingEntity.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
//		}
		// 运单状态(待处理类型)
		Object typeCode = vo;
		// 判断处理类型是否为空
		if (typeCode != null) {
			// 若查询已补录、已开单、全部时，需要在运单正式表Waybill中查询
			if (WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(vo.getValueCode()) || WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(vo.getValueCode())
					|| "all".equals(vo.getValueCode())) {
				waybillList = queryWillBill(ui);
				return waybillList;
			}
			// 若查询离线运单，则需要设置查询条件中的运单状态为"离线运单"
			else if (FossConstants.YES.equals(vo.getValueCode()) || FossConstants.NO.equals(vo.getValueCode())) {
				pendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PC_PENDING);
				pendingEntity.setActive(vo.getValueCode());
			}
			// 查询补录运单，包括暂存、PDA、弃货
			else {
				pendingEntity.setPendingType(vo.getValueCode());
			}
		} else {
			pendingEntity.setPendingType(null);
		}

		// 开单人
		pendingEntity.setCreateUserCode(ui.getCreateUserCode().getText());

		// 制单部门
		String createOrgCode = ((DataDictionaryValueVo) ui.getCreateOrgCodeComboBox().getSelectedItem()).getValueCode();
		// 是否根据制单部门查询
		if ("all".equals(createOrgCode)) {
			// 获得全部下拉框的值
			List<String> createList = getOrgCodes(ui.getCreateOrgCodeComboBox());
			// 若为全部，则设置List集合属性
			if (CollectionUtils.isNotEmpty(createList)) {
				if (createList.size() > 1) {
					pendingEntity.setCreateOrgCodeList(createList);
				} else {
					pendingEntity.setCreateOrgCode(createList.get(0));
				}
			}
		} else {
			pendingEntity.setCreateOrgCode(createOrgCode);
		}

		// 加入待处理集合对象中
		pendingDto.setWaybillPending(pendingEntity);
		// 根据是否离线，查询本地还是服务器端
		if (flag) {
			return offLinePendingService.queryPendingExpress(pendingDto);
		} else {
			return salesDeptWaybillService.queryPendingExpress(pendingDto);
		}
	}
}