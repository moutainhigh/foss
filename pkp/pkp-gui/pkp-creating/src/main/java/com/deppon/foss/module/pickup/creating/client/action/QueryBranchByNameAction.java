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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/QueryBranchByNameAction.java
 * 
 * FILE NAME        	: QueryBranchByNameAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-creating
 * PACKAGE NAME: com.deppon.foss.module.pickup.creating.client.action
 * FILE    NAME: WaybillSaveAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepotAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.vo.BranchQueryVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.branch.PickupGoodsBranchDialog;
import com.deppon.foss.module.pickup.creating.client.ui.branch.PickupGoodsBranchDialog.BranchDataModel;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * （查询发货客户银行账号信息）
 * 
 * @author 025000-foss-helong
 * @date 2012-11-1 下午7:59:52
 */
public class QueryBranchByNameAction implements IButtonActionListener<PickupGoodsBranchDialog> {

	
	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(QueryBranchByNameAction.class);
	
	// 主界面
	private static PickupGoodsBranchDialog ui;
	
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(QueryBranchByNameAction.class);
	
	
	private BranchQueryVo branchQuery = new BranchQueryVo();

	@Override
	public void actionPerformed(ActionEvent e) {
		//获得查询条件数据
		BranchQueryVo vo = ui.getBindersMap().get("branchQuery").getBean();
		List<BranchQueryVo> branchQueryList = null;
		//String queryName = vo.getQueryName();
		//用VO获取网点名称时，如果是Enter键查询，则获取不到最新的
		String queryName = ui.getTxtQueryName().getText();
		if(StringUtils.isNotEmpty(queryName))
		{
			Boolean chbPickup = vo.getChbPickup();
			Boolean chbDeliver = vo.getChbDeliver();
			String chbType = vo.getBranchTypeSeach();
			Boolean isBigGoods = vo.getIsBigGoods();
			//汽运专线查询
			if (WaybillConstants.HIGHWAYS.equals(vo.getBranchTypeSeach())) {
				SaleDepartmentEntity queryCondition = new SaleDepartmentEntity();
				queryCondition.setName(ui.getTxtQueryName().getText());
				if (vo.getChbPickup() != null) {
					queryCondition.setPickupSelf(vo.getChbPickup()? FossConstants.YES:null);
				}
				if (vo.getChbDeliver()!= null) {
					queryCondition.setDelivery(vo.getChbDeliver()?FossConstants.YES:null);
				}
				queryCondition.setActive(FossConstants.YES);
				//设置开业日期
				queryCondition.setOpeningDate(new java.util.Date());
				//查 自有网点
				if(isBigGoods != null && isBigGoods){
					queryCondition.setIsBigGoods(FossConstants.YES);
				}
				branchQueryList = waybillService.queryListByDepartment(queryCondition);
				if(branchQueryList != null && !branchQueryList.isEmpty())
				{
					for (BranchQueryVo branchQueryVo : branchQueryList) {
						//设置网点类型显示中文专线
						branchQueryVo.setBranchTypeCH(WaybillConstants.BRANCH_TYPE_QYZX);
						//设置网点类型CODE
						branchQueryVo.setBranchType(WaybillConstants.ZX);
						setPickDeliveryArea(branchQueryVo);
					}
				}else
				{
					cleanData(vo);
				}

			}else {
				OuterBranchEntity entity = new OuterBranchEntity();
				entity.setAgentDeptName(ui.getTxtQueryName().getText());
				entity.setActive(FossConstants.YES);
				if (vo.getChbDeliver()!=null) {
					entity.setPickupToDoor(vo.getChbDeliver()?FossConstants.YES:null);
				}
				if (vo.getChbPickup() != null) {
					entity.setPickupSelf(vo.getChbPickup()?FossConstants.YES:null);
				}
				if (WaybillConstants.PX.equals(vo.getBranchTypeSeach())) {
					//汽运偏线查询
					entity.setBranchtype(WaybillConstants.PX);
				}else if (WaybillConstants.KY.equals(vo.getBranchTypeSeach())) {
					entity.setBranchtype(WaybillConstants.KY);
				}
				//查询汽运偏线、空运网点
				branchQueryList = waybillService.queryListByBranch(entity);
				if(branchQueryList != null && !branchQueryList.isEmpty())
				{
					for (BranchQueryVo branchQueryVo : branchQueryList) {
						if (WaybillConstants.PX.equals(branchQueryVo.getBranchType())) {
							//设置网点类型显示中文 汽运偏线
							branchQueryVo.setBranchTypeCH(WaybillConstants.BRANCH_TYPE_QYPX);
						}else if (WaybillConstants.KY.equals(branchQueryVo.getBranchType())) {
							//设置显示中文空运
							branchQueryVo.setBranchTypeCH(WaybillConstants.BRANCH_TYPE_KYPX);
						}
						
						/*
						 * 进仓区域扩展
						 * update by 354805 (taodongguo)
						 * 2016-9-7 11:29:20
						 */
						StringBuffer warehouseAreaDesc = new StringBuffer();
						warehouseAreaDesc.append(getWarehouseArea(branchQueryVo.getCode()));
						branchQueryVo.setWarehouseAreaDesc(warehouseAreaDesc.toString());
					}
				}else
				{
					cleanData(vo);
				}
			}
			
			BranchDataModel tableModel = (BranchDataModel)ui.getTable().getModel();
			tableModel.setRowCount(0);// 清除原有行
			tableModel.setData(branchQueryList);//set 表格的值
			tableModel.fireTableDataChanged();	
			if (CollectionUtils.isNotEmpty(branchQueryList)) {
				try {
					PropertyUtils.copyProperties(vo, branchQueryList.get(0));
				} catch (Exception ee) {
					// TODO Auto-generated catch block
					LOGGER.error("copyProperties异常", ee);
				} 
			}
			//获取省市、产品类型
			getComboxValue(vo);
			
			vo.setQueryName(queryName); //查询条件：名称
			vo.setChbPickup(chbPickup); //查询条件：是否自提
			vo.setChbDeliver(chbDeliver);//查询条件：是否送货
			vo.setBranchTypeSeach(chbType);	
			vo.setIsBigGoods(isBigGoods);
		}else
		{
			MsgBox.showInfo(i18n.get("foss.gui.creating.QueryBranchByNameAction.showInfo"));
		}
		//默认选中查询结果的第一行
		if(ui.getTable()!=null && ui.getTable().getRowCount()>0){
			ui.getTable().requestFocus();
			ui.getTable().setRowSelectionAllowed(true);
			ui.getTable().setRowSelectionInterval(0,0);
		}
	}
	
	/**
	 * 获取派送、自提、进仓区域信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-6-26
	 */
	private void setPickDeliveryArea(BranchQueryVo branchQueryVo)
	{
		if(FossConstants.YES.equals(branchQueryVo.getPickUpAreaIsExpand()))
		{
			StringBuffer pickupAreaDesc = new StringBuffer();
			pickupAreaDesc.append(branchQueryVo.getPickupAreaDesc());
			pickupAreaDesc.append(getPickUpArea(branchQueryVo.getCode()));
			branchQueryVo.setPickupAreaDesc(pickupAreaDesc.toString());
		}
		
		if(FossConstants.YES.equals(branchQueryVo.getDeliveryAreaIsExpand()))
		{
			StringBuffer deliveryAreaDesc = new StringBuffer();
			deliveryAreaDesc.append(branchQueryVo.getDeliveryAreaDesc());
			deliveryAreaDesc.append(getDeliveryArea(branchQueryVo.getCode()));
			branchQueryVo.setDeliveryAreaDesc(deliveryAreaDesc.toString());
		}
		
		/*
		 * 进仓区域扩展
		 * update by 354805 (taodongguo)
		 * 2016-9-7 10:25:30
		 */
		StringBuffer warehouseAreaDesc = new StringBuffer();
		warehouseAreaDesc.append(getWarehouseArea(branchQueryVo.getCode()));
		branchQueryVo.setWarehouseAreaDesc(warehouseAreaDesc.toString());
	}
	
	/**
	 * 获取网点提货区域
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-6-26
	 */
	private String getPickUpArea(String code)
	{
		StringBuffer pickUpArea = new StringBuffer("");
		List<String> listDesc = waybillService.queryByCodeAndPickup(code);
		if(listDesc != null && !listDesc.isEmpty())
		{
			for(int i=0; i<listDesc.size(); i++)
			{
				String descArea = listDesc.get(i);
				pickUpArea.append(descArea);
			}
		}
		return pickUpArea.toString();
	}
	
	/**
	 * 获取网点提货区域
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-6-26
	 */
	private String getDeliveryArea(String code)
	{
		StringBuffer deliveryArea = new StringBuffer("");
		List<String> listDesc = waybillService.queryByCodeAndDelivery(code);
		if(listDesc != null && !listDesc.isEmpty())
		{
			for(int i=0; i<listDesc.size(); i++)
			{
				String descArea = listDesc.get(i);
				deliveryArea.append(descArea);
			}
		}
		return deliveryArea.toString();
	}
	
	/**
	 * 
	 * <p>设置进仓区域仓库信息</p> 
	 * @author 354805 
	 * @date 2016年8月22日 下午4:59:17
	 * @param code 查询网点编码
	 * @return
	 * @see
	 */
	public String getWarehouseArea(String code){
		//查询状态为“已确认”的进仓地址信息
		List<DepotAddressEntity> list = waybillService.queryDepotAddressByDepCode(code);
		
		//拼接要显示的数据。
		StringBuffer buf = new  StringBuffer("");
		if(CollectionUtils.isNotEmpty(list)){
			for(DepotAddressEntity depotInfo : list){
				buf.append(depotInfo.getDepotName()).append("•").append(depotInfo.getDepotTypeStr()).append("•").append(depotInfo.getProvCodeStr()).append(depotInfo.getCityCodeStr()).append(depotInfo.getCountyCodeStr()).append(depotInfo.getAddress()).append(";\n");
			}
		}else{
			buf.append("无");
		}
		return buf.toString();
	}
	
	/**
	 * 获取省市、产品类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-5-14
	 */
	private static void getComboxValue(BranchQueryVo vo){
		//获得 省市名称
		List<String> codes = new ArrayList<String>();
		codes.add(vo.getProvince());
		codes.add(vo.getCity());
		
		Map<String, String> map = WaybillServiceFactory.getAdministrativeRegionsService().
				queryAdministrativeRegionsByCodeActive(codes, FossConstants.YES);
		
		
		vo.setProvince(map.get(vo.getProvince()));//设置省名称
		vo.setCityName(map.get(vo.getCity()));//设置市名称
		vo.setChbDeliverUi(FossConstants.YES.equals(vo.getChbDeliverTwo())?true:null);//设置回选  派送复选框
		vo.setChbPickupUi(FossConstants.YES.equals(vo.getChbPickupTwo())?true:null);//设置回选  自提复选框
		//设置 增值服务复选框 回选
		//代收货款
		vo.setChbCodUi(FossConstants.YES.equals(vo.getChbCod())?true:null);
		//货到付款
		vo.setChbArrivePaymentUi(FossConstants.YES.equals(vo.getChbArrivePayment())?true:null);
		//返单签收
		vo.setChbReturnBillUi(FossConstants.YES.equals(vo.getChbReturnBill())?true:null);
		
		//根据营业部CODE 查询所属产品   3代表 三级产品
		//List<ProductEntity> productList = WaybillServiceFactory.getProductService().queryByArriveDept(vo.getCode(), "3");
		//根据营业部CODE 查询所属产品   3代表 三级产品
		List<ProductEntity> productList = WaybillServiceFactory.getProductService().searchByArriveDept(vo.getCode(), "3");
		if (CollectionUtils.isNotEmpty(productList)) {
			for (ProductEntity productEntity : productList) {
				//精准卡航
				if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(productEntity.getCode())) {
					vo.setChbFLF(true);
				}else if(ProductEntityConstants.PRICING_PRODUCT_PCP.equals(productEntity.getCode())){
					//精准包裹
					vo.setChbPCP(true);
				}else if (ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(productEntity.getCode()) 
						||ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG.equals(productEntity.getCode())) {
					//精准城运
					vo.setChbFSF(true);
				}else if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(productEntity.getCode())) {
					//精准汽运(长途)
					vo.setChbLRF(true);
				}else if (ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(productEntity.getCode()) 
						||ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG.equals(productEntity.getCode())) {
					//精准汽运(短途)
					vo.setChbSRF(true);
				}else if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productEntity.getCode())) {
					//精准空运
					vo.setChbAF(true);
				}else if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG.equals(productEntity.getCode())) {
					//精准大票卡航
					vo.setChbBGFLF(true);
				}else if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG.equals(productEntity.getCode())) {
					//精准大票汽运(长)
					vo.setChbBGLRF(true);
				}else if(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(productEntity.getCode())){ 
					//门到门
					vo.setChbDTD(true);
				}else if(ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(productEntity.getCode())){
					//场到场
					vo.setChbYTY(true);
				}
		}
	}
		//设置派送自提区域
		ui.getTextAreaDelivery().setText(vo.getDeliveryAreaDesc());
		ui.getTextAreaPickup().setText(vo.getPickupAreaDesc());
		ui.getTextAreaWarehouse().setText(vo.getWarehouseAreaDesc());
	}
	
	public static void getRowDate(BranchQueryVo vo,BranchQueryVo selectVo,int row){
		String queryName = ui.getTxtQueryName().getText();
		Boolean chbPickup = vo.getChbPickup();
		Boolean chbDeliver = vo.getChbDeliver();
		String key = vo.getKey();
		String chbType = vo.getBranchTypeSeach();
		//MsgBox.showInfo("单击");
		Boolean isBigGoods = vo.getIsBigGoods();
		//BranchQueryVo selectVo = tableModel.getData().get(modelRow);
		try {
			PropertyUtils.copyProperties(vo, selectVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("copyProperties异常", e);
		} 
		//获得省市 以及 是否自提送货、代收货款等转换
		getComboxValue(vo);
		
		vo.setQueryName(queryName); //查询条件：名称
		vo.setChbPickup(chbPickup); //查询条件：是否自提
		vo.setChbDeliver(chbDeliver);//查询条件：是否送货
		vo.setBranchTypeSeach(chbType);
		vo.setKey(key);
		vo.setRowIndex(row);
		vo.setIsBigGoods(isBigGoods);
 }
	
	/**
	 * 清理数据
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-4-28
	 */
	private void cleanData(BranchQueryVo vo)
	{
		//网点编码
		vo.setCode("");
		//网点名称
		vo.setName("");
		//目的站
		vo.setTargetOrgName("");
		//省份
		vo.setProvince("");
		//城市
		vo.setCityName("");
		//城市
		vo.setCityName("");
		//网点类型
		vo.setBranchTypeCH("");
		//正单名称
		vo.setAirBillName("");
		//正单联系电话
		vo.setAirBillPhone("");
		//网点联系电话
		vo.setPhone("");
		//网点地址
		vo.setBranchAddress("");
		//搜索关键字
		vo.setKey("");
		//自提
		vo.setChbPickupUi(false);
		//派送
		vo.setChbDeliverUi(false);
		//返单签收
		vo.setChbArrivePaymentUi(false);
		//代收货款
		vo.setChbCodUi(false);
		//精准卡航
		vo.setChbFLF(false);
		//精准城运
		vo.setChbFSF(false);
		//精准汽运（长）
		vo.setChbLRF(false);
		//精准汽运（短）
		vo.setChbSRF(false);
		//精准空运
		vo.setChbAF(false);
		//自提区域--update by 354805(taodongguo) 2016-9-9 14:23:48
		vo.setPickupAreaDesc("");
		//派送区域--update by 354805(taodongguo) 2016-9-9 14:23:51
		vo.setDeliveryAreaDesc("");
		//进仓区域--update by 354805(taodongguo) 2016-9-9 14:23:59
		vo.setWarehouseAreaDesc("");
	}
	
	

	@SuppressWarnings("static-access")
	@Override
	public void setInjectUI(PickupGoodsBranchDialog ui) {
		this.ui = ui;
	}

	public void setBranchQuery(BranchQueryVo branchQuery) {
		this.branchQuery = branchQuery;
	}

	public BranchQueryVo getBranchQuery() {
		return branchQuery;
	}
	
	
}