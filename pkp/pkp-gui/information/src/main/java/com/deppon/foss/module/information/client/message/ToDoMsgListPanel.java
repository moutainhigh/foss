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
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: information/src/main/java/com/deppon/foss/module/information/client/message/ToDoMsgListPanel.java
 * 
 * FILE NAME        	: ToDoMsgListPanel.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.information.client.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.plugin.PluginLifecycleException;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.component.networkstatus.NetworkMonitor;
import com.deppon.foss.framework.client.component.networkstatus.NetworkStatus;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.component.remote.IRemoteServer;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.jpf.utils.JpfUtils;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.mainframe.client.action.IMainFrame;
import com.deppon.foss.module.mainframe.client.action.OpenMenuAction;
import com.deppon.foss.module.mainframe.client.utills.MenuNodeUtil;
import com.deppon.foss.module.mainframe.client.utills.WebMenuDto;
import com.deppon.foss.module.pickup.common.client.service.IPickupToDoMsgService;
import com.deppon.foss.module.pickup.common.client.service.PickupToDoMsgServiceFactory;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.ToDoMsgConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.PickupToDoMsgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 定时提醒详细提醒条目列表面板
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:31:14, </p>
 * @author niujian
 * @date 2012-12-18
 * @since
 * @version
 */
public class ToDoMsgListPanel extends JPanel {
	
	private static final Log log = LogFactory.getLog(ToDoMsgListPanel.class);
	private static final long serialVersionUID = 8985701408677418549L;
	private static final II18n i18n = I18nManager.getI18n(InfoPanel.class);
	private IconList dataList;
	private DefaultListModel listModel;
	private JDialog parentDialog = null;
	
	public ToDoMsgListPanel() {
		setMinimumSize(new Dimension(NumberConstants.NUMBER_500, NumberConstants.NUMBER_500));
		setBounds(NumberConstants.NUMBER_10, NumberConstants.NUMBER_10, NumberConstants.NUMBER_800, NumberConstants.NUMBER_500);
		setLayout(new BorderLayout());
		initUI();
	}
	
	public ToDoMsgListPanel(JDialog parentDialog) {
		this.parentDialog = parentDialog;
		setMinimumSize(new Dimension(NumberConstants.NUMBER_500, NumberConstants.NUMBER_500));
		setBounds(NumberConstants.NUMBER_10, NumberConstants.NUMBER_10, NumberConstants.NUMBER_800, NumberConstants.NUMBER_500);
		setLayout(new BorderLayout());
		initUI();
	}
	/**
	 * 
	 * 加载布局
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	public void initUI(){
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel(ImageUtil.getImageIcon(this.getClass(),"application16.gif"));
		titlePanel.add(label);
		titlePanel.add(RichJLabel.getOutlineLabel(i18n.get(ToDoMsgConstants.KEY_TODO_MSG_LIST_PANEL_TITLE), NumberConstants.NUMBER_20));
		titlePanel.setBackground(new Color(NumberConstants.NUMBER_102, NumberConstants.NUMBER_102, NumberConstants.NUMBER_102));

		add(titlePanel, BorderLayout.NORTH);

		listModel = new DefaultListModel();
		dataList = new IconList(listModel);
		dataList.setForeground(new Color(NumberConstants.NUMBER_90, NumberConstants.NUMBER_90, NumberConstants.NUMBER_90));
		refreshToDoMsgList(listModel);
		dataList.addMouseListener(new ToDoMsgMouseAdapter());
		JScrollPane msgListPanel = new JScrollPane(dataList);

		msgListPanel.setBackground(Color.black);
		add(msgListPanel, BorderLayout.CENTER);
		
	}
	
	public Dialog getParentDialog(){
		return this.parentDialog;
	}
	/**
	 * 
	 * 鼠标事件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	class ToDoMsgMouseAdapter extends MouseAdapter {
		
		/**
		 * 双击弹出定时提醒的目标列表界面
		 * @author niujian
		 * @date 2012-12-18 上午10:03:50
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount()==2){
				IconList iconlist = (IconList)e.getSource();
				Object[] oArr = (Object[])iconlist.getSelectedValue();
				if(oArr!=null){
					if(getParentDialog()!=null ){
						getParentDialog().setVisible(false);
					}
					openUIByToDoMessageBizTypeCode((String)oArr[2]);
				}
			}
		}
	}
	
	private void openUIByToDoMessageBizTypeCode(String pBizTypeCode){
		MenuNodeUtil util = MenuNodeUtil.getInstance();
		
//		MenuNodeInfo node = util.findNodeOneByToDoMsgBizType(pBizTypeCode);
//		if(node!=null){
//			node.getAction().actionPerformed(null);
//		}
		
		String actionName = util.findTodoActionNameByBizType(pBizTypeCode);
		Object pluginIdObj = util.findClassPluginIdByActionName(actionName);
	 	/**
		 * 根据插件信息获取obj对象
		 */
		try {
			
			if(pluginIdObj instanceof String){
				String pluginId =(String) pluginIdObj;
				Object obj = JpfUtils.createInstance(MenuNodeUtil.plugin.getManager()
						.getPlugin(pluginId),actionName);
				if (!(obj instanceof Action)) {
					return;
				}else{
					
					Action action  = (Action) obj;
					//一个代办只要打开一次就行了
					if (obj instanceof AbstractOpenUIAction) {
						((AbstractOpenUIAction<?>) obj)
						.setOpentime(1);
					}
					// main frame need open time
					/**
					 * 在运行时obj对象是否是IMainFrame的一个实例
					 */		
					if (obj instanceof IMainFrame) {
						((IMainFrame) obj).setOpentime(1);
					}
					action.actionPerformed(null);
				}
			}else{
				
				
				WebMenuDto  dto = (WebMenuDto)pluginIdObj;
				String pluginId = dto.getClassName();
				
				
				OpenMenuAction openMenuAction=JpfUtils.createInstance(MenuNodeUtil.plugin.getManager()
						.getPlugin(pluginId), "com.deppon.foss.module.mainframe.client.action.OpenMenuAction");
				//设置统一资源标识符
				openMenuAction.setUri(dto.getUri());
				//设置统一资源标识符名称
				openMenuAction.setUriName(dto.getName());

				openMenuAction.openUIAction();
			}
			
			
			
		} catch (PluginLifecycleException e) {
			//to do nothing 
		}
	}
	
	public void forceRefreshToDoMsgListUI(){
		refreshToDoMsgList(listModel);
	}
	
	public int countToDoMsg(){
		if(listModel!=null){
			return listModel.size();
		}
		else {
			return 0;
		}
	}
	
	private boolean isNetworkOk(){
		try{
			IRemoteServer remoteServer = DefaultRemoteServiceFactory.getInstance().getRemoteServer();
			NetworkMonitor networkMonitor = remoteServer.getTransport().getNetworkMonitor();
			
			if(NetworkStatus.ONLINE == networkMonitor.getStatus()){
				return true;
			}
		}catch(Exception e){
			log.error("[ isNetworkOk error : "+e.toString() + " ]",e);
		}
		return false;
	}
	
	/**
	 * 
	 * 刷新待办
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private void refreshToDoMsgList(DefaultListModel pListModel) {
		if(pListModel!=null){
			pListModel.removeAllElements();
			IPickupToDoMsgService toDoMsgServive = PickupToDoMsgServiceFactory.getService();
			UserEntity user = (UserEntity)SessionContext.getCurrentUser();
				
			if(toDoMsgServive!=null){
				if(isNetworkOk()){
					PickupToDoMsgDto toDoMsgDto = new PickupToDoMsgDto();
					if(user == null)
						return;
					if(user.getEmployee()!=null && user.getEmployee().getDepartment()!=null){
						pickUpListModel(pListModel, toDoMsgServive, user,
								toDoMsgDto);
					}
				}
				ISalesDeptWaybillService waybillService = WaybillServiceFactory.getSalesDeptWaybillService();
				Integer count = Integer.valueOf(0);
			
				if(user.getEmployee()!=null && user.getEmployee().getDepartment()!=null){
					OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
					String departcode = dept.getCode();
					if(StringUtils.isNotEmpty(departcode)){
						count = waybillService.countOfflineActiveWayBill(departcode);
					}else{
						count = waybillService.countOfflineActiveWayBill();
					}
				}else{
					count = waybillService.countOfflineActiveWayBill();
				}
				
				
				// add not submit offline way bill count number (remind message)
				if(count!=null && count.intValue()>0 ){
					PickupToDoMsgDto o = new PickupToDoMsgDto();
					o.setBusinessType(ToDoMsgConstants.KEY_TODO_MSG_BIZTYPE_PKP_ACTIVE_OFFLINE_WAYBILL);
					o.setNoDealNum(count);
					String msg = formatToDoMessage(o);
					String biztype = o.getBusinessType();
					if(msg!=null){
						pListModel.addElement(new Object[] {
								ImageUtil.getImageIcon(this.getClass(), "list.png"),
								msg,biztype });
					}
				}
			}
			dataList.updateUI();
		}
	}

	private void pickUpListModel(DefaultListModel pListModel,
			IPickupToDoMsgService toDoMsgServive, UserEntity user,
			PickupToDoMsgDto toDoMsgDto) {
		OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
		String departcode = dept.getCode();
		toDoMsgDto.setReceiveOrgCode(departcode);
		toDoMsgDto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE);
		
		List<PickupToDoMsgDto> list = toDoMsgServive.queryToDoMsgList(toDoMsgDto);
		//DMANA-9463 整车运单未签收
		List<PickupToDoMsgDto> waybillNotSignedlist = toDoMsgServive.queryWaybillNotSignDataList(toDoMsgDto);
		//整车未签收的数据的提醒
		if(CollectionUtils.isNotEmpty(waybillNotSignedlist)){
			for(PickupToDoMsgDto o : waybillNotSignedlist){
				String msg = formatToDoMessageForWvh(o);
				String biztype = o.getBusinessType();
				//去除掉处理待办定时器重复生成的数据  BUG-57773
				pListModel.addElement(new Object[] {
						ImageUtil.getImageIcon(this.getClass(), "list.png"),
						msg,biztype});
			}
		}
		//其它类型数据的提醒
		if(CollectionUtils.isNotEmpty(list)){
			for(PickupToDoMsgDto o : list){
				String msg = formatToDoMessage(o);
				String biztype = o.getBusinessType();
				//去除掉处理待办定时器重复生成的数据  BUG-57773
				if(!StringUtils.equals("PKP_WB_LAB_PT", biztype)){
					if(msg!=null){
						pListModel.addElement(new Object[] {
								ImageUtil.getImageIcon(this.getClass(), "list.png"),
								msg,biztype });
					}
				}
				
			}
		}
		//这个才是用户真正需要处理的待办的内容  BUG-57773
		TodoConditionDto todoConditionDto = new TodoConditionDto();
		todoConditionDto.setHandleOrgCode(departcode);
		todoConditionDto.setIsPrinted(FossConstants.NO);

		Long count = toDoMsgServive.queryTodoActionList(todoConditionDto);
		PickupToDoMsgDto o2 = new PickupToDoMsgDto();
		//TODO
		o2.setBusinessType("PKP_WB_LAB_PT");
		o2.setNoDealNum(new Integer(count+""));
		String msg = formatToDoMessage(o2);
		String biztype = o2.getBusinessType();
		if(msg!=null){
			pListModel.addElement(new Object[] {
					ImageUtil.getImageIcon(this.getClass(), "list.png"),
					msg,biztype });
		}
	}
	
	/**
	 * 进行数据的格式化操作
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-30 11:09:04
	 * @param pDto
	 * @return
	 */
	private String formatToDoMessageForWvh(PickupToDoMsgDto pDto) {
		String biztype = pDto.getBusinessType();
		String biztypename = i18n.get("ui.todomsg.biztype."+biztype);
		if(biztypename==null){
			biztypename = "(未知类型)"+biztype;
		}
		if(StringUtils.isNotEmpty(pDto.getNoDealData())){
			//您还有xx个xxx任务未处理,请点击处理
			StringBuffer sb = new StringBuffer();
			sb.append("<html><body>");
			sb.append("<font color=#414141 size=4 face=宋体>&nbsp;");
			sb.append("您还有单号：<font color=#FF0000 size=4 face=宋体>&nbsp;" + pDto.getNoDealData() + "</font>");
			sb.append("&nbsp;"+biztypename+"，请及时签收处理&nbsp;");
			sb.append("</font>");
			sb.append("</body></html>");
			return sb.toString();
		}
		else {
			return null;
		}
	}
	
	/**
	 * 
	 * 格式化代表
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private String formatToDoMessage(PickupToDoMsgDto pDto) {
		String biztype = pDto.getBusinessType();
		String biztypename = i18n.get("ui.todomsg.biztype."+biztype);
		if(biztypename==null){
			biztypename = "(未知类型)"+biztype;
		}
		
		int dealnum = pDto.getNoDealNum();
		if(dealnum>0 ){
			//您还有xx个xxx任务未处理,请点击处理
			StringBuffer sb = new StringBuffer();
			sb.append("<html><body>");
			sb.append("<font color=#414141 size=4 face=宋体>&nbsp;");
			sb.append("您还有<font color=#FF0000 size=4 face=宋体>&nbsp;" + dealnum + "&nbsp;</font>个");
			sb.append("&nbsp;"+biztypename+"任务未处理,请点击处理&nbsp;");
			sb.append("</font>");
			sb.append("</body></html>");
			return sb.toString();
		}
		else {
			return null;
		}
	}
}