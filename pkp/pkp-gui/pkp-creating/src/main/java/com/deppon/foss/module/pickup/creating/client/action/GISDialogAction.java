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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/GISDialogAction.java
 * 
 * FILE NAME        	: GISDialogAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.limewire.cef.CefChromeBrowserManager;
import org.limewire.cef.IChromeBrowserCallBack;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.impl.AdministrativeRegionsService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.OrgInfoService;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.common.Common;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.waybill.shared.define.GisConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.google.inject.Injector;

/**
 * 
 * 提货网点查询（嵌入GIS系统页面）
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2013-3-7 上午10:59:00,content: </p>
 * @author foss-sunrui
 * @date 2013-3-7 上午10:59:00
 * @since
 * @version
 */
public class GISDialogAction implements IButtonActionListener<WaybillEditUI> {
	
	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(GISDialogAction.class);
	
	private static final Log log = LogFactory.getLog(GISDialogAction.class);
	
    WaybillEditUI ui;
    
    ShowPickupStationDialogAction action;
    
    WaybillPanelVo vo;
    
    private static final String KEY_FIELD_DEPTNO = "deptNo";
    
    private static final String KEY_FIELD_DISTANCE = "distance";
    
    /**
     * 运单服务接口
     */
    IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
    
    
    /**
     * 
     * 获得 AdministrativeRegionsService
     * @author 097972-foss-dengtingting
     * @date 2013-3-8 上午10:40:38
     */
    public static AdministrativeRegionsService getAdministrativeRegionsService(){
    	Injector injector = GuiceContextFactroy.getInjector();
    	return injector.getInstance(AdministrativeRegionsService.class);
    }
    
	public static OrgInfoService getOrgInfoService() {
		Injector injector = GuiceContextFactroy.getInjector();
		return injector.getInstance(OrgInfoService.class);
	}
	
	/**
	 * 
	 * 字符转码
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-7 下午4:40:51
	 */
	private String encoded(String str) throws UnsupportedEncodingException{
		if (str == null) {
			return "";
		}else{
			return URLEncoder.encode(str,"UTF-8");
		}
	}
	
	private Map<String,String> loadParamsAsMap(String[] params){
		Map<String,String> retMap = new HashMap<String,String>();
		if(params!=null){
			for(int i=0;i<params.length;i++){
				//根据“=”分隔字符串
				String[] strs = params[i].split("=");
				//获得key
				String key = strs[0];
				//定义value对象
				String value = "";
				//判断“=”号后面是否有值
				if(strs.length > 1){
					//获得value值 
					value = strs[1];
				}
				//将deptNo的值存入集合中
				if(KEY_FIELD_DEPTNO.equals(key)){
					retMap.put(KEY_FIELD_DEPTNO, value);
				}
				//将DISTANCE的值存入集合中
				if(KEY_FIELD_DISTANCE.equals(key)){
					retMap.put(KEY_FIELD_DISTANCE, value);
				}
			}
		}
		return retMap;
	}
	
	/**
	 * 
	 * 获取URL参数
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-7 下午4:40:16
	 */
	private String getUrl(String gisurl) throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(gisurl)){
			return null;
		}
		StringBuffer url = new StringBuffer(gisurl);
		WaybillPanelVo panelVo = ui.getBindersMap().get("waybillBinder").getBean();
		if (panelVo != null) {
			url.append("?");
			//订单号 appNum
			if(StringUtils.isNotEmpty(panelVo.getOrderNo())){
				url.append("appNum=").append(encoded(panelVo.getOrderNo())).append(GisConstants.PARA);
			}
			//收货客户省份 province
			if (StringUtils.isNotEmpty(panelVo.getReceiveCustomerProvCode())) {
				String province = getAdministrativeRegionsService().queryAdministrativeRegionsNameByCode(panelVo.getReceiveCustomerProvCode());
				/*if (GisConstants.BEIJING.equals(province) || GisConstants.TIANJIN.equals(province)
						|| GisConstants.CHONGQING.equals(province) || GisConstants.SHANGHAI.equals(province)) {
					province = province +"省";
				}*/
				url.append("province=").append(encoded(province)).append(GisConstants.PARA);
			}
			//收货市 city
			if (StringUtils.isNotEmpty(panelVo.getReceiveCustomerCityCode())) {
				String city = getAdministrativeRegionsService().queryAdministrativeRegionsNameByCode(panelVo.getReceiveCustomerCityCode());
				url.append("city=").append(encoded(city)).append(GisConstants.PARA);
			}
			//收货区 county
			if (StringUtils.isNotEmpty(panelVo.getReceiveCustomerDistCode())) {
				String dist = getAdministrativeRegionsService().queryAdministrativeRegionsNameByCode(panelVo.getReceiveCustomerDistCode());
				url.append("county=").append(encoded(dist)).append(GisConstants.PARA);
			}
			//收货具体地址 otherAddress
			if (StringUtils.isNotEmpty(panelVo.getReceiveCustomerAddress())) {
				url.append("otherAddress=").append(encoded(panelVo.getReceiveCustomerAddress())).append(GisConstants.PARA);
			}
			//收货客户手机号码  phone
			if (StringUtils.isNotEmpty(panelVo.getReceiveCustomerMobilephone())) {
				url.append("phone=").append(encoded(panelVo.getReceiveCustomerMobilephone())).append(GisConstants.PARA);
			}
			//收货客户电话号码  tel
			if (StringUtils.isNotEmpty(panelVo.getReceiveCustomerPhone())) {
				url.append("tel=").append(encoded(panelVo.getReceiveCustomerPhone())).append(GisConstants.PARA);
			}
			//运输类型  transportType
			String transportType = panelVo.getProductCode()==null?null:panelVo.getProductCode().getCode();
			if (StringUtils.isNotEmpty(transportType)) {
				//空运
				if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(transportType)) {
					transportType = GisConstants.GIS_TRANS_AIR;
				}
				//zxy 20131212 DEFECT-682 新增：偏线类型
				else if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(transportType)){
					transportType = GisConstants.GIS_TRANS_AGENCY;
				}else {
					//汽运
					transportType = GisConstants.GIS_TRANS_HIGHWAYS;
				}
				url.append("transportType=").append(encoded(transportType)).append(GisConstants.PARA);
			}
			//提货方式   deliveryType
			String deliveryType = panelVo.getReceiveMethod()==null?null:panelVo.getReceiveMethod().getValueCode();
			if (StringUtils.isNotEmpty(deliveryType)) {
				//送货
				/**
				 * DEFECT-6897，根据业务要求增加判断条件，其它不变
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-01-19下午18:07
				 */
				if (WaybillConstants.DELIVER_NOUP.equals(deliveryType) || WaybillConstants.DELIVER_FREE.equals(deliveryType)
						|| WaybillConstants.DELIVER_UP.equals(deliveryType) || WaybillConstants.DELIVER_FREE_AIR.equals(deliveryType)
						|| WaybillConstants.DELIVER_NOUP_AIR.equals(deliveryType) || WaybillConstants.DELIVER_UP_AIR.equals(deliveryType)
						|| WaybillConstants.DELIVER_INGA_AIR.equals(deliveryType) || WaybillConstants.DELIVER_STORAGE.equals(deliveryType)
						|| WaybillConstants.LARGE_DELIVER_UP.equals(deliveryType) || WaybillConstants.LARGE_DELIVER_UP_AIR.equals(deliveryType)) {
					deliveryType = GisConstants.GIS_MATCH_DELIVER;
				}else {
					//自提
					deliveryType = GisConstants.GIS_MATCH_PICKUP;
				}
				url.append("deliveryType=").append(encoded(deliveryType)).append(GisConstants.PARA);
			}
			//德邦家装查询Gis
			 if(panelVo.getSpecialValueAddedServiceType()!=null)
			   {
				String  valueName=panelVo.getSpecialValueAddedServiceType().getValueName();
				if(!"".equals(valueName)&&valueName!=null)
				{
					url.append("onlyType=homeImproveSend");
				}
			   }
		}
		if (url.lastIndexOf("?") == url.length()-1) {
			url.deleteCharAt(url.lastIndexOf("?"));
		}
		if(url.lastIndexOf("&") == url.length()-1){
			url.deleteCharAt(url.lastIndexOf("&"));
		}
		//url.insert(0, "http://");
		return url.toString();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			String url = getUrl(GisConstants.SALES_DEPT_SEARCH);
			//String url2 = "gis.deppon.com/gis-biz/biz-destination/stationSearch.action?aa="+aa+"&bb="+bb;
			IChromeBrowserCallBack myCallBack = new IChromeBrowserCallBack() {
				@Override
				public boolean doCallBack(String[] params) throws Exception { 
					Map<String,String> retMap = loadParamsAsMap(params);
					log.info("进入电子地图"+params);
					if(retMap!=null && !retMap.isEmpty()){
						BaseDataService vBaseDataService = BaseDataServiceFactory.getBaseDataService();
						String deptNo = retMap.get(KEY_FIELD_DEPTNO);
						log.info("deptNo部门编号："+deptNo+" ");
						if(StringUtils.isNotEmpty(deptNo))
						{
							//根据组织标杆编码查询部门编码
		    				OrgAdministrativeInfoEntity org = waybillService.queryOrgAdministrativeInfoByUnifiedCodeClean(deptNo);
		    				//偏给代理网点
		    				OuterBranchEntity agent = null;
		    				//提货网点对象
		    				BranchVo branchVO = null;
		    				
		    				HashMap<String, IBinder<WaybillPanelVo>> map = ui.getBindersMap();
							IBinder<WaybillPanelVo> waybillBinder = map.get("waybillBinder");
							vo = waybillBinder.getBean();
							try {	
								// 判断对象是否为空
								if (null == org) {									
									// 查询是否为偏线
									agent = waybillService.queryAgencyBranchInfo(deptNo);
									// 判断对象是否为空
									if (null == agent) {
										MsgBox.showInfo(i18n.get("foss.gui.creating.gisDialogAction.MsgBox.nullOrgDept") + "[ " + deptNo + " ]");
										return false;
									} else {
										OuterBranchEntity outerBranch = vBaseDataService.queryOuterBranchByCode(agent.getAgentDeptCode(), new Date());
										CommonUtils.validateAgentGisDept(vo, agent);
										branchVO = new BranchVo();
										// 代理编号
										branchVO.setCode(outerBranch.getAgentDeptCode());
										// 代理名称
										branchVO.setName(outerBranch.getAgentDeptName());
										// 目的站
										branchVO.setTargetOrgName(outerBranch.getSimplename());
										// 是否代收货款
										branchVO.setCanAgentCollected(outerBranch.getHelpCharge());
										// 是否可自提
										branchVO.setPickupSelf(outerBranch.getPickupSelf());
										// 是否送货上门
										branchVO.setDelivery(outerBranch.getPickupToDoor());
										// 所属城市code
										branchVO.setCityCode(outerBranch.getCityCode());
										// 代收货款
										branchVO.setCanAgentCollected(outerBranch.getHelpCharge());
										// 是否支持货到付款
										branchVO.setArriveCharge(outerBranch.getArriveCharge());
										// 是否签收单返单
										branchVO.setCanReturnSignBill(outerBranch.getReturnBill());
									}
								} else {
									// 查询营业部信息
									SaleDepartmentEntity vSaleDepartmentEntity = vBaseDataService.querySaleDepartmentByCode(StringUtil.defaultIfNull(org.getCode()), new Date());
									if (vSaleDepartmentEntity == null) {
										MsgBox.showInfo(i18n.get("foss.gui.creating.gisDialogAction.MsgBox.nullDepartmentEntity.one") + deptNo
												+ i18n.get("foss.gui.creating.gisDialogAction.MsgBox.nullDepartmentEntity.two"));
									} else {
										CommonUtils.validateOwerGisDept(vo, vSaleDepartmentEntity);
										branchVO = new BranchVo();
										branchVO.setCode(vSaleDepartmentEntity.getCode());
										branchVO.setName(vSaleDepartmentEntity.getName());
										branchVO.setSingleBillLimitkg(vSaleDepartmentEntity.getSingleBillLimitkg());
										branchVO.setSingleBillLimitvol(vSaleDepartmentEntity.getSingleBillLimitvol());
										branchVO.setSinglePieceLimitkg(vSaleDepartmentEntity.getSinglePieceLimitkg());
										branchVO.setSinglePieceLimitvol(vSaleDepartmentEntity.getSinglePieceLimitvol());
										branchVO.setCanAgentCollected(vSaleDepartmentEntity.getCanAgentCollected());
										// 是否可自提
										branchVO.setPickupSelf(vSaleDepartmentEntity.getPickupSelf());
										// 是否送货上门
										branchVO.setDelivery(vSaleDepartmentEntity.getDelivery());
										// 所属城市被临时存放在PICKUP_AREA_DESC列当中传来出来
										branchVO.setCityCode(vSaleDepartmentEntity.getPickupAreaDesc());
										// 取消到达日期
										branchVO.setCancelArrivalDate(vSaleDepartmentEntity.getCancelArrivalDate());
										// 转货部门
										branchVO.setTransferGoodDept(vSaleDepartmentEntity.getTransferGoodDept());
										// 是否支持货到付款
										branchVO.setArriveCharge(vSaleDepartmentEntity.getCanCashOnDelivery());
										// 是否签收单返单
										branchVO.setCanReturnSignBill(vSaleDepartmentEntity.getCanReturnSignBill());
									}
								}

								// 提货网点
								vo.setCustomerPickupOrgCode(branchVO);
								// 提货网点名称
								vo.setCustomerPickupOrgName(StringUtil.defaultIfNull(branchVO.getName()));

								action = new ShowPickupStationDialogAction();
								action.setInjectUI(ui);
								action.setDialogData(branchVO, vo);
								
								
								// 从电子地图获取公里数
								String k = retMap.get(KEY_FIELD_DISTANCE);
								if (StringUtils.isNotEmpty(k)) {
									// 注意：此处不能国际化，因为该值是从GIS传过来的值（公里），若国际化后了，则在后面无法替换汉字，导致new
									// BigDecimal(k)出错
									String str = "公里";
									if (k.indexOf(str) != -1) {
										k = k.replaceAll(str, "");
									}
									BigDecimal kilometer = new BigDecimal(k);
									/**
									 * 如果非送货时，公里数不可录入，且要清空
									 */
									if (WaybillConstants.SELF_PICKUP.equals(vo.getReceiveMethod().getValueCode())
											|| WaybillConstants.INNER_PICKUP.equals(vo.getReceiveMethod().getValueCode())
											|| WaybillConstants.AIR_PICKUP_FREE.equals(vo.getReceiveMethod().getValueCode())
											|| WaybillConstants.AIR_SELF_PICKUP.equals(vo.getReceiveMethod().getValueCode())
											|| WaybillConstants.AIRPORT_PICKUP.equals(vo.getReceiveMethod().getValueCode())) {
										vo.setKilometer(null);
									} else {
										// 公里数
										vo.setKilometer(kilometer);
									}
									if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
											WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
										// 公里数不可编辑
										ui.pictureTransferInfoPanel.getTxtKilometer().setEditable(false);
									}else{
										// 公里数不可编辑
										ui.transferInfoPanel.getTxtKilometer().setEditable(false);
									}
								} else {
									vo.setKilometer(null);
									//ui.transferInfoPanel.getTxtKilometer().setEditable(true);
									if(StringUtils.isNotBlank(ui.getPictureWaybillType()) && 
											WaybillConstants.WAYBILL_PICTURE.equals(ui.getPictureWaybillType())){
										// 公里数不可编辑
										ui.pictureTransferInfoPanel.getTxtKilometer().setEditable(true);
									}else{
										// 公里数不可编辑
										ui.transferInfoPanel.getTxtKilometer().setEditable(true);
									}
								}
								
								List<Future<Object>> futures = new ArrayList<Future<Object>>();
								ExecutorService executorService = Executors.newFixedThreadPool(2);
								Future<Object> future  = executorService.submit(new Callable<Object>(){
									@Override
									public Object call() throws Exception {
										// 整车的提货网点不需要设置线路和空运配载及时效
										if (!ui.basicPanel.getChbWholeVehicle().isSelected()) {
											// 设置空运配载
											action.setAirDeptEnabled(vo);
											// 设置线路
											action.setLoadLine(vo);
										}else{
											if(vo.getCustomerPickupOrgCode()!=null && vo.getCustomerPickupOrgCode().getCode()!=null){
												//设置整车的信息
												Common.setWholeVehicleByCode(vo.getCustomerPickupOrgCode().getCode(), vo);
												//获取整车费率范围
												GuiResultBillCalculateDto gDto=Common.getInsuranceRate(vo);
												Common.setRate(gDto,vo,ui);
											}
										}
										return null;
									}

								});
								
								futures.add(future);
								
								future  = executorService.submit(new Callable<Object>(){
									@Override
									public Object call() throws Exception {
										//修改重量重新获取报价费率与报价费率范围---206860
										 if(vo.getGoodsVolumeTotal() == null){
												vo.setGoodsVolumeTotal(BigDecimal.ZERO);
										 }
										 if(vo.getGoodsWeightTotal() == null){
											 	vo.setGoodsWeightTotal(BigDecimal.ZERO);
										 }
										 if(vo.getGoodsWeightTotal() != null && vo.getGoodsVolumeTotal() != null 
												 && vo.getCustomerPickupOrgCode() != null
												 && vo.getProductCode() != null){
											 Common.getInsuranceCharge(vo, ui);
										 }
										return null;
									}
								});
								
								futures.add(future);
								
								executorService.shutdown();
								
								for(Future<Object> ft : futures){
									try{
										ft.get();
									}catch(Exception e){
										MsgBox.showITServiceInfo(e.getCause().getMessage());
										return true;
									}
								}
								
							} catch (BusinessException e) {
									MsgBox.showError(e.getMessage());
									return false;
							}
							return true;
							}
						}
					return false;
				}
			};
			
			double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			CefChromeBrowserManager.PopChromeBrowserAsDialog(url, (int)width- NumberConstants.NUMBER_60, (int)height- NumberConstants.NUMBER_110, myCallBack,false);
			//调用的电子地图窗口大小有所变动，所以需要修改宽和高
			//CefChromeBrowserManager.PopChromeBrowserAsDialog(url, 1280, 1024, myCallBack,false);
		}catch(Exception exp){
			log.error("[ GISDialogAction open GIS dialog error ]",exp);
		}
	}
		
    @Override
    public void setInjectUI(WaybillEditUI ui) {
    	this.ui = ui;
    }
    
}