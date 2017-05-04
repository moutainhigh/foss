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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/action/QueryPublishPriceAction.java
 * 
 * FILE NAME        	: QueryPublishPriceAction.java
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
package com.deppon.foss.module.pickup.common.client.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillFreightRouteService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.WaybillFreightRouteService;
import com.deppon.foss.module.pickup.common.client.ui.PublishPriceLinkTableMode;
import com.deppon.foss.module.pickup.common.client.ui.QueryPublishPriceUI;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PublishPriceService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;



/**
 * 
 * 查询公布价 action
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-shixiaowei
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class QueryPublishPriceAction extends
	AbstractButtonActionListener<QueryPublishPriceUI> {
	
	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory.getLog(QueryPublishPriceAction.class);
	private II18n i18n = I18nManager.getI18n(QueryPublishPriceAction.class);
	
	/**
	 * ui
	 */
	private QueryPublishPriceUI ui;
	
	/**
	 * query result list
	 */
	private List<PublishPriceEntity> list;
	
	
	/**
	 * 查询公布价 dao
	 */
	@Inject
	private IPublishPriceService publishPriceService;
	
	/**
	 * 运单基础资料服务
	 */
	private IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);
	/**
	 * GUI查询走货路径相关服务接口
	 */
	private IWaybillFreightRouteService waybillFreightRouteService = GuiceContextFactroy.getInjector().getInstance(WaybillFreightRouteService.class);

	/**
	 * 设置ui
	 */
	public void setIInjectUI(QueryPublishPriceUI ui) {
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
	@SuppressWarnings({ "static-access" })
	public void actionPerformed(ActionEvent e) {
		JXTable table = ui.getTable();

		list = queryPublishPricing(ui);
	
		// 刷新表格
		PublishPriceLinkTableMode tableModel = new PublishPriceLinkTableMode(ui.getArray(list));

		table.setModel(tableModel);
		
		//设置换行
		table.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());   
		
		LOG.debug(tableModel);
		
		ui.refreshTable(table);
		
		//默认选中查询结果的第一行
		if(ui.getTable()!=null && ui.getTable().getRowCount()>0){
			ui.getTable().requestFocus();
			ui.getTable().setRowSelectionAllowed(true);
			ui.getTable().setRowSelectionInterval(0,0);
		}
	}
	
	/**
	 * 定价优化项目---公布价查询优化
	 * 
	 * 分段公布价查询
	 * 
	 * @author Foss-206860
	 * */
	//为了分段价格换行添加的方法
	class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer { 
	    public TableCellTextAreaRenderer() { 
	        setLineWrap(true);   
	        setWrapStyleWord(false);
	    } 
	    public Component getTableCellRendererComponent(JTable table, Object value, 
	            boolean isSelected, boolean hasFocus, int row, int column) { 
	        // 计算当下行的最佳高度 
	        int maxPreferredHeight = 0; 
	        for (int i = 0; i < table.getColumnCount(); i++) { 
	            setText("" + table.getValueAt(row, i)); 
	            setSize(table.getColumnModel().getColumn(column).getWidth(), 0); 
	            maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height); 
	        } 
	        if (table.getRowHeight(row) != maxPreferredHeight)  // 这行一定不能少..
	            table.setRowHeight(row, maxPreferredHeight); 
	        
	        setText(value == null ? "" : value.toString()); 
	        
	        //选中一个，则改变整行的颜色
	        if (isSelected) {
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());

            } else {
                   setForeground(table.getForeground());
                   setBackground(table.getBackground());
            }
	        return this; 
	    } 
	} 

	/**
	 * 查询公布价格
	 * @param ui2
	 * @return
	 */
	private List<PublishPriceEntity> queryPublishPricing(QueryPublishPriceUI ui) {
		 //始发区域code
		String createOrgCode = ui.getTxtCreateOrgCode().getText();
		//到达区域code
		String targetOrgCode =  ui.getTxtTargetOrgCode().getText();
		
		//判断是否偏线网点
		boolean flagOuter=false;
		QueryBillCacilateDto queryBillCacilateDto=new QueryBillCacilateDto();
		OuterBranchEntity outer=baseDataService.queryOuterBranchByCode(targetOrgCode,new Date());
		if(outer!=null && WaybillConstants.PX.equals(outer.getBranchtype())){
			flagOuter=true;
			//运输性质
			queryBillCacilateDto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE);
		}else if(outer!=null && WaybillConstants.KY.equals(outer.getBranchtype())){
			flagOuter=false;
			//运输性质
			queryBillCacilateDto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT);
		}
		//封装偏线查询参数
		queryBillCacilateDto.setOriginalOrgCode(createOrgCode);
		queryBillCacilateDto.setDestinationOrgCode(targetOrgCode);
		queryBillCacilateDto.setReceiveDate(new Date());
		queryBillCacilateDto.setAgentDeptName(ui.getTxtTargetOrgName().getText());
		//获取最终配载部门
		queryLastLoadOrgCode(queryBillCacilateDto);
		//get service		
		publishPriceService=GuiceContextFactroy.getInjector().getInstance(PublishPriceService.class);
		
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			// 获得远程HessianRemoting接口
			IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);					
			if(flagOuter){	
				return waybillRemotingService.queryPublishPriceDetailForPX(queryBillCacilateDto);
//				return revertToTranportType(waybillRemotingService.queryPublishPriceDetailForPX(queryBillCacilateDto), portType);				
			}else{
				return waybillRemotingService.queryPublishPriceDetail(createOrgCode, targetOrgCode, new Date());
//				return revertToTranportType(waybillRemotingService.queryPublishPriceDetail(createOrgCode, targetOrgCode, new Date()), portType);	
			}			
		} else {
			if(flagOuter){
				return publishPriceService.queryPublishPriceDetailForPX(queryBillCacilateDto);
//				return revertToTranportType(publishPriceService.queryPublishPriceDetailForPX(queryBillCacilateDto), portType);
			}else{
				return publishPriceService.queryPublishPriceDetail(createOrgCode, targetOrgCode, new Date());
//				return revertToTranportType(publishPriceService.queryPublishPriceDetail(createOrgCode, targetOrgCode, new Date()), portType);
			}			
		}
		//查询公布价价格区域 
		//测试数据 XZQY-000000003   XZQY-000000002
		//GS00002   W01060302
		//W011302020106 W011305080202
		
	}
	
	/**
	 * 将最终查询出来的数据进行转换，不用太在意，只是为了数据的好看
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-11 20:03:58
	 * @param publishPriceList
	 * @param portType
	 * @return
	 */
	private List<PublishPriceEntity> revertToTranportType(List<PublishPriceEntity> publishPriceList, String portType) {
		if(CollectionUtils.isNotEmpty(publishPriceList)){
			List<PublishPriceEntity> publishPriceTempList = new ArrayList<PublishPriceEntity>();
			if(WaybillConstants.PX.equals(portType) || WaybillConstants.KY.equals(portType)){
				for(PublishPriceEntity entity : publishPriceList){
					if(WaybillConstants.PX.equals(portType)){
						entity.setProductItemCode(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE);
						entity.setProductItemName(i18n.get("foss.gui.common.product.partial"));
					}else{
						entity.setProductItemCode(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT);
						entity.setProductItemName(i18n.get("foss.gui.common.product.accurateAir"));
					}
					publishPriceTempList.add(entity);
				}
				return publishPriceTempList;
			}else{
				return publishPriceList;
			}
		}
		return publishPriceList;
	}

	/**
	 * 
	 * 获取最终配载部门
	 * 
	 * @author WangQianJin
	 * @date 2014-01-01
	 */
	private void queryLastLoadOrgCode(QueryBillCacilateDto queryBillCacilateDto) {
		OrgInfoDto dto = null;
		try {
			//获取出发部门是否是集中开单组
			boolean flagGroup=false;
			OrgAdministrativeInfoEntity orgInfo=baseDataService.queryOrgAdministrativeInfoEntityByCode(queryBillCacilateDto.getOriginalOrgCode());
			if(orgInfo!=null && FossConstants.YES.equals(orgInfo.getBillingGroup())){
				flagGroup=true;
			}
			//判断是否在线
			if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
				dto = waybillFreightRouteService.queryLodeDepartmentInfoOnline(flagGroup, queryBillCacilateDto.getOriginalOrgCode(), queryBillCacilateDto.getDestinationOrgCode(), queryBillCacilateDto.getProductCode());		
			} else {
				dto = waybillFreightRouteService.queryLodeDepartmentInfoLocal(flagGroup, queryBillCacilateDto.getOriginalOrgCode(), queryBillCacilateDto.getDestinationOrgCode(), queryBillCacilateDto.getProductCode());		
			}						
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("queryLastLoadOrgCode获取最终配载部门出错=========="+e.getMessage());
		}
		//设置最终配载部门
		if(dto!=null){
			queryBillCacilateDto.setLastOrgCode(dto.getLastLoadOrgCode());			
		}
	}	 

	/**
	 * @return the list
	 */
	public List<PublishPriceEntity> getList() {
		return list;
	}
	
	
}