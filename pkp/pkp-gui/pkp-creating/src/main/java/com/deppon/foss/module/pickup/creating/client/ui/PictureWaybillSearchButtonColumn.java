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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/OfflineButtonColumn.java
 * 
 * FILE NAME        	: OfflineButtonColumn.java
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
package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.action.ImportWaybillEditUIAction;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.popupdialog.WaybillPictureEndDialog;
import com.deppon.foss.module.pickup.creating.client.ui.popupdialog.WaybillPictureReturnRecordViewDialog;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillPendingHessianRemoting;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 图片查询表格操作列
 * 
 * @author hehaisu
 * 
 */
public class PictureWaybillSearchButtonColumn extends AbstractCellEditor
		implements TableCellEditor, TableCellRenderer, ActionListener {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager
			.getI18n(PictureWaybillSearchButtonColumn.class);

	// log object
	private Log LOG = LogFactory.getLog(PictureWaybillSearchButtonColumn.class);
	
	/**
	 * 图片开单远程接口
	 */
	IWaybillHessianRemoting waybillHessianRemoting; 
	
	IWaybillPendingHessianRemoting waybillPendingHessianRemoting;
	//
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	private static final String WAYBILL_PC_PENDING_TYPE = "PC_PENDING";

	private static final int NUM_120 = 120;
	
	private JButton lookRenderButton;
	private JButton lookEditorButton;
	private JButton modifyRenderButton;
	private JButton modifyEditorButton;
	private JButton returnRenderButton;
	private JButton returnEditorButton;
	//图片运单中止
	private JButton endEditorButton ;
	private JButton endRenderButton ;
	
	private JPanel panel = new JPanel();
	private SearchPictureTableMode spt;
	private boolean IS_PKP_400_CS_REP  = false;
	/**
	 * ui
	 */
	SearchPictureWaybillUI ui;

	/**
	 * 构造方法
	 * 
	 * @param tc
	 * @param ui2
	 */
	public PictureWaybillSearchButtonColumn(TableColumn tc,
			SearchPictureWaybillUI ui, SearchPictureTableMode tableModel) {
		this.spt = tableModel;
		lookRenderButton = new JButton(ImageUtil.getImageIcon(
				this.getClass(), "query.png"));
		
		lookEditorButton = new JButton(ImageUtil.getImageIcon(
				this.getClass(), "query.png"));
		lookEditorButton.setFocusable(false);
		lookRenderButton.setPreferredSize(new Dimension(NumberConstants.NUMBER_60,NumberConstants.NUMBER_18));
		lookEditorButton.setPreferredSize(new Dimension(NumberConstants.NUMBER_60,NumberConstants.NUMBER_18));
		
		
		modifyRenderButton = new JButton(ImageUtil.getImageIcon(
				this.getClass(), "supplywaybill.png"));
		modifyEditorButton = new JButton(ImageUtil.getImageIcon(
				this.getClass(), "supplywaybill.png"));
		modifyEditorButton.setFocusable(false);
		modifyRenderButton.setPreferredSize(new Dimension(NumberConstants.NUMBER_60,NumberConstants.NUMBER_18));
		modifyEditorButton.setPreferredSize(new Dimension(NumberConstants.NUMBER_60,NumberConstants.NUMBER_18));
		
		
		returnRenderButton = new JButton(ImageUtil.getImageIcon(
				this.getClass(), "searchbranch.png"));
		returnEditorButton = new JButton(ImageUtil.getImageIcon(
				this.getClass(), "searchbranch.png"));
		returnEditorButton.setFocusable(false);
		returnRenderButton.setPreferredSize(new Dimension(NUM_120,NumberConstants.NUMBER_18));
		returnEditorButton.setPreferredSize(new Dimension(NUM_120,NumberConstants.NUMBER_18));
		
		//图片运单中止
		endEditorButton = new JButton("中止");
		endRenderButton = new JButton("中止");
		endEditorButton.setEnabled(false);
		endRenderButton.setEnabled(false);
		endEditorButton.setFocusable(false);
		endEditorButton.setPreferredSize(new Dimension(NumberConstants.NUMBER_60,NumberConstants.NUMBER_18));
		endRenderButton.setPreferredSize(new Dimension(NumberConstants.NUMBER_60,NumberConstants.NUMBER_18));
		
		initListener(ui);
		
		waybillHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
		waybillPendingHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillPendingHessianRemoting.class);
	
		tc.setCellEditor(this);
		tc.setCellRenderer(this);
		this.ui = ui;
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		if(!CollectionUtils.isEmpty(user.getRoleids())){
			for(String roleId:user.getRoleids()){
				if(WaybillConstants.PKP_400_CS_REP.equals(roleId)){
					//则是400客服角色
					IS_PKP_400_CS_REP = true;
					break;
				}
			}
		}
	}

	private void initListener(SearchPictureWaybillUI ui){
		final JTable table = ui.getTable();
		//look
		lookEditorButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// 将UI上的行转成model中的行
				int index = table.getSelectedRow();
				if (index < 0) {
					return;
				}

				int row = table.convertRowIndexToModel(index);
				String reMark = (String)spt.getValueAt(row, NumberConstants.NUMBER_6);
				String active = (String)spt.getValueAt(row, NumberConstants.NUMBER_13);
				String cashPayFlag = (String)spt.getValueAt(row, NumberConstants.NUMBER_18);
				
				if (active != null) {
					
					ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
					String waybillNo = table.getModel().getValueAt(row, 2).toString();
					
					if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(active)
							|| WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(active)) {
						WaybillPendingEntity waybillPendingEntity = waybillPendingHessianRemoting.queryWaybillPendingByNo(waybillNo);
						if (waybillPendingEntity == null) {
							MsgBox.showInfo("获取运单信息失败！");
							return;
						}
						//查询免费接货值,携带免费接货,导入运单信息 add by 306486
						waybillPendingEntity.setFreePickupGoods(waybillHessianRemoting.queryPickupGoodsByWaybillNo(waybillNo));
						uiAction.importPictureWaybillUI(waybillPendingEntity,false,active,reMark,cashPayFlag,"");
					} else if (WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(active)) {
						WaybillPendingEntity waybillPendingEntity = new WaybillPendingEntity();
						waybillPendingEntity.setPendingType(WAYBILL_PC_PENDING_TYPE);
						waybillPendingEntity.setWaybillNo(waybillNo);
						Object orderNoCol =table.getModel().getValueAt(row, NumberConstants.NUMBER_3);
						String orderNo=null;
						if(orderNoCol!=null){
							orderNo = orderNoCol.toString();
						}
						waybillPendingEntity.setOrderNo(orderNo);
						waybillPendingEntity.setCreateTime(DateUtils.convert(table.getModel().getValueAt(row, NumberConstants.NUMBER_6).toString(), "yyyy-MM-dd HH:mm:ss"));
						waybillPendingEntity.setReceiveOrgCode(table.getModel().getValueAt(row, NumberConstants.NUMBER_14).toString());
						waybillPendingEntity.setDriverCode(table.getModel().getValueAt(row, NumberConstants.NUMBER_7).toString());
						waybillPendingEntity.setCreateUserCode(table.getModel().getValueAt(row, NumberConstants.NUMBER_15).toString());
						waybillPendingEntity.setCreateOrgCode(table.getModel().getValueAt(row, NumberConstants.NUMBER_16).toString());
						//查询免费接货值 ,携带免费接货,导入运单信息add by 306486
						waybillPendingEntity.setFreePickupGoods(waybillHessianRemoting.queryPickupGoodsByWaybillNo(waybillNo));
						uiAction.importPictureWaybillUI(waybillPendingEntity,false,active,"",cashPayFlag,"");
					} else {
						//已开单 查运单
						WaybillDto waybillDto = waybillHessianRemoting.queryWaybillByNo(waybillNo);
						uiAction.importPictureWaybillUI(waybillDto,false,active,reMark,cashPayFlag,"");
					}
					
				}
			}
			
		});
		//modify
		modifyEditorButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// 将UI上的行转成model中的行
				int index = table.getSelectedRow();
				if (index < 0) {
					return;
				}

				int row = table.convertRowIndexToModel(index);
				String active = (String)spt.getValueAt(row, NumberConstants.NUMBER_13);
				String reMark = (String)spt.getValueAt(row, NumberConstants.NUMBER_6);
				String cashPayFlag = (String)spt.getValueAt(row, NumberConstants.NUMBER_18);
				String pictureWaybillId = (String)table.getModel().getValueAt(row, NumberConstants.NUMBER_19);
				if (active != null) {
					
					ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
					String waybillNo = table.getModel().getValueAt(row, 2).toString();
					if(WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING.equals(active)){
						openPictureUIActionAndReturn(active,waybillNo);
					}else if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(active)
							|| WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(active)) {
						WaybillPendingEntity waybillPendingEntity = waybillPendingHessianRemoting.queryWaybillPendingByNo(waybillNo);
						if (waybillPendingEntity == null) {
							MsgBox.showInfo("获取运单信息失败！");
							return;
						}
						//查询免费接货值 ,携带免费接货,导入运单信息add by 306486
						waybillPendingEntity.setFreePickupGoods(waybillHessianRemoting.queryPickupGoodsByWaybillNo(waybillNo));
						uiAction.importPictureWaybillUI(waybillPendingEntity,true,active,reMark,cashPayFlag,pictureWaybillId);
					} else if (WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(active)) {
						WaybillPendingEntity waybillPendingEntity = new WaybillPendingEntity();
						waybillPendingEntity.setPendingType(WAYBILL_PC_PENDING_TYPE);
						waybillPendingEntity.setWaybillNo(waybillNo);
						Object orderNoCol =table.getModel().getValueAt(row, NumberConstants.NUMBER_3);
						String orderNo=null;
						if(orderNoCol!=null){
							orderNo = orderNoCol.toString();
						}
						waybillPendingEntity.setOrderNo(orderNo);
						waybillPendingEntity.setCreateTime(DateUtils.convert(table.getModel().getValueAt(row, NumberConstants.NUMBER_6).toString(), "yyyy-MM-dd HH:mm:ss"));
						waybillPendingEntity.setReceiveOrgCode(table.getModel().getValueAt(row, NumberConstants.NUMBER_14).toString());
						waybillPendingEntity.setDriverCode(table.getModel().getValueAt(row, NumberConstants.NUMBER_7).toString());
						waybillPendingEntity.setCreateUserCode(table.getModel().getValueAt(row, NumberConstants.NUMBER_15).toString());
						waybillPendingEntity.setCreateOrgCode(table.getModel().getValueAt(row, NumberConstants.NUMBER_16).toString());
						//查询免费接货值 ,携带免费接货,导入运单信息add by 306486
						waybillPendingEntity.setFreePickupGoods(waybillHessianRemoting.queryPickupGoodsByWaybillNo(waybillNo));
						uiAction.importPictureWaybillUI(waybillPendingEntity,true,active,reMark,cashPayFlag,pictureWaybillId);
					} else {
						//已开单 查运单
						WaybillDto waybillDto = waybillHessianRemoting.queryWaybillByNo(waybillNo);
						uiAction.importPictureWaybillUI(waybillDto,true,active,reMark,cashPayFlag,pictureWaybillId);
					}
					
				}
			}
			
		});
		
		returnEditorButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// 将UI上的行转成model中的行
				int index = table.getSelectedRow();
				if (index < 0) {
					return;
				}

				int row = table.convertRowIndexToModel(index);
				
				//通过运单号查询图片类型为
				String waybillNo = table.getModel().getValueAt(row, 2).toString(); 
				if (StringUtils.isNotBlank(waybillNo)) {
					WaybillPictureEntity entity = new WaybillPictureEntity();
					entity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN);
					entity.setWaybillNo(waybillNo);
					List<WaybillPictureEntity> waybillPictureEntitys = waybillPendingHessianRemoting.queryWaybillPicturesByEntity(entity);
					WaybillPictureReturnRecordViewDialog dialog = new WaybillPictureReturnRecordViewDialog(waybillPictureEntitys);
					WindowUtil.centerAndShow(dialog);
				}
			}
			
		});
		
		//图片运单中止监听
		endEditorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// 将UI上的行转成model中的行
				int index = table.getSelectedRow();
				if (index < 0) {
					return;
				}
//				String inputValue =JOptionPane.showInputDialog(null, "请输入中止原因：", "图片运单中止", JOptionPane.INFORMATION_MESSAGE);
				WaybillPictureEndDialog waybillPictureEndDialog = new WaybillPictureEndDialog();
				String inputValue = waybillPictureEndDialog.getContext();
				
				LOG.info("图片运单中止 输入内容 ："+inputValue);
				if(StringUtils.isNotBlank(inputValue)){
					if(StringUtils.isBlank(inputValue)){
						LOG.debug("图片运单中止监听 --> 输入内容为空 ");
//						endEditorButton.setBorder(BorderFactory.createLineBorder(Color.RED)) ;
//						endEditorButton.setForeground(Color.RED);
						return ;
					}

					int row = table.convertRowIndexToModel(index);
					//业务处理
					//获取运单号
					String id = (String)spt.getValueAt(row, NumberConstants.NUMBER_19);
					String waybillNo = table.getModel().getValueAt(row, 2).toString(); 
					LOG.debug("图片运单中止监听 --> id="+id+" waybillNO="+waybillNo);
					if (StringUtils.isNotBlank(waybillNo) && StringUtils.isNotEmpty(id)) {
						//1.该运单即作废 操作
						WaybillPictureEntity entity = new WaybillPictureEntity();
						Object orderNoCol =table.getModel().getValueAt(row, NumberConstants.NUMBER_3);
						String orderNo="";
						if(orderNoCol!=null){
							orderNo = orderNoCol.toString();
						}
						entity.setOrderNo(orderNo);
						entity.setWaybillNo(waybillNo);
						entity.setRemark(inputValue);
						entity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_DISCONTINUE);
						entity.setId(id);
						Date date = waybillPendingHessianRemoting.discontinueWaybillPicture(entity);
						LOG.debug("图片运单中止监听 --> date="+date);
						if(date != null){
							WaybillPictureEntity quwey = new WaybillPictureEntity();
							quwey.setId(id);
							quwey = waybillService.queryWaybillPictureByEntity(quwey);
							//2.图片运单作废后，APP端运单状态变更为‘已作废’
							backPushMessage(quwey, inputValue, date,WaybillConstants.WAYBILL_PICTURE_TYPE_DISCONTINUE);
							
							//执行刷新操作
							SearchPictureTableMode dtm = (SearchPictureTableMode) table.getModel();
							dtm.fireTableRowsDeleted(index, index);
							dtm.removeRow(1,index);
							table.clearSelection();
						}
						
					}
					
				}else{
					LOG.debug("用户点击了取消按钮");
					return ;
				}//if end
			}
		}) ;
		
	}
	/**
	 * 封装推送的消息
	 * @param pictureWaybill
	 * @param waybillMessage
	 * @param date 
	 * @param type
	 */
	public String backPushMessage(WaybillPictureEntity pictureWaybill,String waybillMessage
			,Date date ,String type){
		LOG.info("进行消息推送 --> date="+date+" type="+type);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		waybillMessage="开单图片中止:"+pictureWaybill.getWaybillNo()+" "+waybillMessage+" 时间:"+format.format(date);
		//获得当前用户信息
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		//修改为实时发短信
		return waybillService.saveWaybillpushmessageSendSms(pictureWaybill.getWaybillNo(),
													waybillMessage, 
													user.getEmployee().getEmpCode(),
													user.getEmployee().getDepartment().getUnifiedCode(),
													pictureWaybill.getMobilephone());
		
	}
	
	/**
	 * 当单元格处于编辑状态时
	 */
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// 当单元格处于编辑状态时
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0,0 ));
		lookEditorButton.setEnabled(true);
		modifyEditorButton.setEnabled(true);
		returnEditorButton.setEnabled(true);
		String active = (String)spt.getValueAt(row,NumberConstants.NUMBER_13);
		
		//判断是否异常单 by 352676
		String reMark=(String)spt.getValueAt(row,NumberConstants.NUMBER_6);
		//判断是否为同一个开单组 来判断是否为异地开单
		boolean isLocalGroup = isSameGroup(row, NumberConstants.NUMBER_20);
		//by 352676
		boolean isSameGroup = isSameGroup(row, NumberConstants.NUMBER_26);
	
		//已录入
		if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(active)) {
			lookEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
			modifyEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.modify"));
			returnEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.returnrecord"));
			endEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end"));
			endEditorButton.setEnabled(true);
			
			//查询员所在开单组对应的车队上传的运单图片；如本地车队上传的图片为异地（非本地）开单组开单，则本地开单组不可进行修订 by 352676
			/*if(!isSameGroup){
				modifyEditorButton.setEnabled(false);
			}*/
			if(IS_PKP_400_CS_REP){
				endEditorButton.setEnabled(false);
				modifyEditorButton.setEnabled(false);
			}
			
		} 
		//已开单
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.equals(active)) {
			lookEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
			returnEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.returnrecord"));
			modifyEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.modify"));
			modifyEditorButton.setEnabled(false);
			endEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end"));
			endEditorButton.setEnabled(false);
			
		}
		//已退回
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(active)) {
			lookEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
			returnEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.returnrecord"));
			modifyEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.modify"));
			modifyEditorButton.setEnabled(false);
			endEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end"));
			endEditorButton.setEnabled(true);
			if(IS_PKP_400_CS_REP){
				endEditorButton.setEnabled(false);
			}
			
		}
		//生成运单异常
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(active)) {
			lookEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
			modifyEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.modify"));
			if(IS_PKP_400_CS_REP){
				modifyEditorButton.setEnabled(false);
			}
			returnEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.returnrecord"));
			returnEditorButton.setEnabled(false);
			endEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end"));
			endEditorButton.setEnabled(false);
			
			//异地异常处理
			//如果不是本地开单组，则不能处理异常
			if(!isLocalGroup){
				editPanelEnabel();
			}

		}
		//待录入
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING.equals(active)) {
			lookEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
			modifyEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.kaidan"));
			returnEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.returnrecord"));
			endEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end"));
			endEditorButton.setEnabled(true);
			lookEditorButton.setEnabled(false);
			//DP-录单中心项目-分单逻辑需求V1.0:查询结果中，对于【运单状态】是【待录入】的信息，【操作列】里的【开单】按钮置灰，不可点击 by 352676
			//如果异常单，则开单按钮可操作 2017年3月28日18:32:53 by 352676
			if(StringUtil.isNotBlank(reMark)){
				modifyEditorButton.setEnabled(true);
			}else{
				modifyEditorButton.setEnabled(false);
			}
			if(IS_PKP_400_CS_REP){
				endEditorButton.setEnabled(false);
				modifyEditorButton.setEnabled(false);
			}
		
		}
		//已分配 2017年4月18日11:51:51  修改 可以查询到已分配运单     by:352676
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(active)) {
			lookEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
			modifyEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.kaidan"));
			returnEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.returnrecord"));
			endEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end"));
			
			endEditorButton.setEnabled(false);
			lookEditorButton.setEnabled(false);
			modifyEditorButton.setEnabled(false);
			returnEditorButton.setEnabled(false);
		
		}
		//对营业部进行判断  取消营业部“图片查询”中“图片修改”功能，营业部只可以查询
		if (FossConstants.NO.equals(getLoginUserEntity().getEmployee().getDepartment().getBillingGroup())) {
			editPanelEnabel();
		}
		
		//对上述类型添加到面包中
		if(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(active)||WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.equals(active)||
				WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(active)||WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(active)
				||WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING.equals(active)||WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(active)){
			
				panel.add(lookEditorButton);
				panel.add(modifyEditorButton);
				panel.add(returnEditorButton);
				panel.add(endEditorButton);
		}
		
		spt = (SearchPictureTableMode) table.getModel();
		return panel;
	}
	
	/**
	 * Describe：根据行列获取开单组是否为本地开单组
	 *
	 * @Date:2015-9-2  上午11:37:01 <br>
	 * @param row    行
	 * @param col     列
	 * @return void  是否为本地开单组
	 */
	private boolean isSameGroup(int row, int col){
		//所属开端组
		String belongGroup = (String)spt.getValueAt(row, col);
		
		String loginGroup = getLoginUserOrg();
		
		//判断是否为同一个开单组 来判断是否为异地开单
		boolean isSameGroup = loginGroup.equals(belongGroup);
		return isSameGroup;
	}
	
	/**
	 * Describe：编辑面板不可编辑
	 *
	 * @Date:2015-9-2  上午11:26:25 <br> 
	 * @return void
	 */
	private void editPanelEnabel(){
		lookEditorButton.setEnabled(true);
		modifyEditorButton.setEnabled(false);
		returnEditorButton.setEnabled(false);
		endEditorButton.setEnabled(false);
	}

	/**
	 * 当单元格的编辑状态结束后调用此方法内的代码
	 */
	@Override
	public Object getCellEditorValue() {
		// 当单元格的编辑状态结束后调用此方法内的代码
		return null;
	}

	/**
	 * getTableCellRendererComponent
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// 当单元格处于展示状态时
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0,0 ));
		lookRenderButton.setEnabled(true);
		modifyRenderButton.setEnabled(true);
		returnRenderButton.setEnabled(true);
		
		String active = (String)spt.getValueAt(row, NumberConstants.NUMBER_13);
		
		//判断是否异常单 by 352676
		String reMark=(String)spt.getValueAt(row,NumberConstants.NUMBER_6);
		//判断当前登录部门是否本地开单组
		boolean isLocalGroup = isSameGroup(row, NumberConstants.NUMBER_20);
		//by 352676  判断当前登录部门是否本属开单组
		boolean isSameGroup = isSameGroup(row, NumberConstants.NUMBER_26);
	
		
		//已录入
		if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(active)) {
			lookRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
			modifyRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.modify"));
			returnRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.returnrecord"));
			endRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end"));
			endRenderButton.setEnabled(true);
			//查询员所在开单组对应的车队上传的运单图片；如本地车队上传的图片为异地（非本地）开单组开单，则本地开单组不可进行修订 by 352676
			/*if(!isSameGroup){
				modifyRenderButton.setEnabled(false);
			}*/
			if(IS_PKP_400_CS_REP){
				modifyRenderButton.setEnabled(false);
				endRenderButton.setEnabled(false);
			}
			
		} 
		//已开单
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.equals(active)) {
			lookRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
			returnRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.returnrecord"));
			modifyRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.modify"));
			modifyRenderButton.setEnabled(false);
			endRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end"));
			endRenderButton.setEnabled(false);
			
		}
		//已退回
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(active)) {
			lookRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
			returnRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.returnrecord"));
			modifyRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.modify"));
			modifyRenderButton.setEnabled(false);
			endRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end"));
			endRenderButton.setEnabled(true);
			if(IS_PKP_400_CS_REP){
				endRenderButton.setEnabled(false);
			}
			
		}
		//生成运单异常
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(active)) {
			lookRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
			modifyRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.modify"));
			if(IS_PKP_400_CS_REP|| !isLocalGroup){//异地开单只能查看
				modifyRenderButton.setEnabled(false);
			}
			returnRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.returnrecord"));
			returnRenderButton.setEnabled(false);
			endRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end"));
			endRenderButton.setEnabled(false);
			//如果不是本地开单组，则不能修改等操作 BY 352676
			if(!isLocalGroup){
				//对营业部进行判断
				setOnlyViewBtn();
			}
		}
		//待录入
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING.equals(active)) {
			lookRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
			modifyRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.kaidan"));
			returnRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.returnrecord"));
			endRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end"));
			endRenderButton.setEnabled(true);
			lookRenderButton.setEnabled(false);
			
			//DP-录单中心项目-分单逻辑需求V1.0:查询结果中，对于【运单状态】是【待录入】的信息，【操作列】里的【开单】按钮置灰，不可点击 by 352676
			
			//如果异常单，则开单按钮可操作 2017年3月28日18:32:53 by 352676
			if(StringUtil.isNotBlank(reMark)){
				modifyRenderButton.setEnabled(true);
			}else{
				modifyRenderButton.setEnabled(false);
			}
			if(IS_PKP_400_CS_REP){
				modifyRenderButton.setEnabled(false);
				endRenderButton.setEnabled(false);
			}
			
		} 
		//已分配 2017年4月18日11:51:51  修改 可以查询到已分配运单     by:352676
		else if (WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(active)) {
			lookRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
			modifyRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.kaidan"));
			returnRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.returnrecord"));
			endRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.end"));
			
			endRenderButton.setEnabled(false);
			lookRenderButton.setEnabled(false);
			modifyRenderButton.setEnabled(false);
			returnRenderButton.setEnabled(false);
			
		} 
		if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.equals(active)||WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.equals(active)
				||WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(active)||WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(active)
				||WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING.equals(active)||WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(active)) {
			
			//对营业部进行判断  取消营业部“图片查询”中“图片修改”功能，营业部只可以查询
			if (FossConstants.NO.equals(getLoginUserEntity().getEmployee().getDepartment().getBillingGroup())) {
				setOnlyViewBtn();
			}
			
			panel.add(lookRenderButton);
			panel.add(modifyRenderButton);
			panel.add(returnRenderButton);
			panel.add(endRenderButton);

		}
		
		return panel;
	}
	
	
	/**
	 * 设置按钮只能查看
	 */
	private void setOnlyViewBtn(){
			//跨区域运单中异地开单部门只有查看权限
			lookRenderButton.setEnabled(true);
			modifyRenderButton.setEnabled(false);
			returnRenderButton.setEnabled(false);
			endRenderButton.setEnabled(false);
	}
	
	/**
	 * 获取登录用户所在的开单部门
	 * @return 开单部门
	 */
	private String getLoginUserOrg(){
		String retStr = "";
		UserEntity user =getLoginUserEntity();
		String currentOrgCode = user.getEmployee().getOrgCode();
		
		if(!StringUtils.isEmpty(currentOrgCode)){
			retStr  = currentOrgCode;
		}
		return retStr;
	}
	
	/**
	 * 获取当前登录用户实体
	 * @return
	 */
	private UserEntity getLoginUserEntity(){
		return  (UserEntity) SessionContext.getCurrentUser();
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
			if (!"all".equals(StringUtil.defaultIfNull(code))) {
				list.add(code);
			}
		}
		return list;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public void openPictureUIActionAndReturn( String active,String waybillNo) {
	    IApplication application = ApplicationContext.getApplication();
	    EditorConfig editConfig = new EditorConfig();
	    editConfig = new EditorConfig();
	    editConfig.setTitle(i18n.get("foss.gui.creating.newAddAction.waybillPicture.label"));
	    editConfig.setPluginId("com.deppon.foss.module.pkp-creating");
	    IEditor editor = application.openEditorAndRetrun(editConfig,
	            "com.deppon.foss.module.pickup.creating.client.ui.PictureWaybillEditUI");
	    PictureWaybillEditUI prictrueWaybillEditUI = (PictureWaybillEditUI) editor
	        .getComponent();
	    prictrueWaybillEditUI.setEditor(editor);
	    if (WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING.equals(active)) {
	      // 图片运单开单状态 新增
	      prictrueWaybillEditUI.setPictureOperateType(null);
	    } else {
	      prictrueWaybillEditUI.setPictureOperateType("VIEWORMODIFY");
	    }
	    // 圖片开单
	    prictrueWaybillEditUI.setWaybillType(WaybillConstants.WAYBILL_FOCUS);
	    prictrueWaybillEditUI
	        .setPictureWaybillType(WaybillConstants.WAYBILL_PICTURE);
	    prictrueWaybillEditUI.setWaybillNo(waybillNo);
	    prictrueWaybillEditUI.setApplication(application);
	    prictrueWaybillEditUI.openUI();
	  }

}
