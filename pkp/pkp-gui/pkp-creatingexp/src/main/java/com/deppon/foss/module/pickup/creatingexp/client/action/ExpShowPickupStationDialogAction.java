/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.server.service.impl.ConfigurationParamsService;
import com.deppon.foss.module.mainframe.client.common.CommonContents;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.QueryPickupStationDialog;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.CustomerImpOperLogHandler;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.BaseInfoInvokeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpShowPickupStationDialogAction implements IButtonActionListener<ExpWaybillEditUI> {

	
	/**
	 * 参数配置
	 */
	private IConfigurationParamsService configurationParamsService =  GuiceContextFactroy.getInjector().getInstance(ConfigurationParamsService.class);

	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory.getLog(ExpShowPickupStationDialogAction.class);

	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpShowPickupStationDialogAction.class);

	/**
	 * 主界面
	 */
	private ExpWaybillEditUI ui;

	/**
	 * open waybill service
	 */
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 
	 * 功能：openUIAction
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
			IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
			ExpWaybillPanelVo bean = waybillBinder.getBean();

			if (bean.getProductCode() != null) {
				// 显示提货网点查询窗口
				showPickupStationDialog(bean);
				// 加载走货线路
				setLoadLine(bean);
				// 根据运输性质是否空运决定配载部门是否可以编辑
				setAirDeptEnabled(bean);
				
				//修改了目的站以后要重新计算保费
				if(bean.getInsuranceFee()!=null && bean.getInsuranceFee().doubleValue()>0){
					MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.changeInsuranceFee"));
					
					// 保险声明值最大值
					//bean.setMaxInsuranceAmount(BigDecimal.ZERO);
					// 保险费率
					bean.setInsuranceRate(BigDecimal.ZERO);
					// 保险手续费
					bean.setInsuranceFee(BigDecimal.ZERO);
					// 保险费ID
					bean.setInsuranceId("");
					// 保险费CODE
					bean.setInsuranceCode("");
				}
				
			} else {
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.noProduct"));
			}
		} catch (BusinessException w) {
			if (!"".equals(w.getMessage())) {
				MsgBox.showInfo(w.getMessage());
			}
		}
	}

	/**
	 * 设置预配线路和预计出发时间与预计到达时间 （摄取方法供GIS地图匹配网点使用）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-14 上午8:49:17
	 */
	public void setLoadLine(WaybillPanelVo bean) {
		if (bean.getCustomerPickupOrgCode() != null) {
			String waybill_log = bean.getWaybillNo();
			long begin = System.currentTimeMillis();
			if(CommonContents.logToggle){
				LOG.info("运单号："+waybill_log+" 加载目的站、提货网点、查询时效 、走货线路开始...");
				CustomerImpOperLogHandler.setLogger(bean.getWaybillNo()).loadMdzDataStart();
			}
			// 查询始发配载部门、最终配载部门以及线路
			queryLodeDepartmentInfo(bean);
			if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(bean.getProductCode().getCode()) && !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())) {
				if(CommonContents.logToggle){
				  LOG.info("运单号："+waybill_log+" 处理时效相关信息 开始...");
				}
				Date leaveTime = getLeaveTime(bean);
				if (leaveTime != null) {
					bean.setPreDepartureTime(leaveTime);// 预计出发时间
					bean.setPreCustomerPickupTime(getPickupDeliveryTime(bean));// 预计派送/自提时间
				} else {
					cleanTargetEmpty(bean);
					LOG.debug("未查询到对应的时效  ");
					MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.nullRelativeTime"));
				}
				if(CommonContents.logToggle){
					LOG.info("处理时效相关信息 结束. 运单号："+waybill_log);
				}
			}
			if(CommonContents.logToggle){
				LOG.info(" 加载目的站、提货网点、查询时效 、走货线路 结束。运单号："+waybill_log+" ;耗时："+(System.currentTimeMillis()-begin));
				CustomerImpOperLogHandler.setLogger(bean.getWaybillNo()).loadMdzDataEnd();
			}
		}
	}
	/**
	 * 
	 * 清空目的站以及预配线路
	 * 
	 * @author yangkang
	 * @date 2014-9-4 下午05:53:27
	 * @param bean
	 */
	private void cleanTargetEmpty(WaybillPanelVo bean) {
		// 清空提货网点
		bean.setCustomerPickupOrgCode(null);
		// 清空提货网点名称
		bean.setCustomerPickupOrgName("");
		// 清空目的站
		bean.setTargetOrgCode("");
		// 清空长短途
		bean.setLongOrShort(null);
		// 清空预配线路
		bean.setLoadLineName("");
	}
	/**
	 * 
	 * 查询始发配载部门、最终配载部门以及线路
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-22 下午03:54:30
	 */
	private void queryLodeDepartmentInfo(WaybillPanelVo bean) {
		OrgInfoDto dto = null;
		try {
			// 运输性质非空判断
			if (null == bean.getProductCode()) {
				LOG.error("运输性质不能为空！");
				throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDepartmentDialog.exception.transType"));
			}
			if (bean.getReceiveMethod() != null && WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())
					&& (CommonUtils.directDetermineIsExpressByProductCode(bean.getProductCode().getCode()))) {
				// 根据编码查询
				SaleDepartmentEntity entity = waybillService.querySaleDeptByCode(bean.getCustomerPickupOrgCode().getCode());
				if (entity != null) {
					if (!FossConstants.YES.equals(entity.getCanExpressPickupSelf())) {
						throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.cannotExpressPickupSelf"));
					}
				} else {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.notSearchSaleDepartment"));
				}
			}

			if(bean.getPickupCentralized()!=null&&bean.getPickupCentralized()){
				dto = waybillService.queryLodeDepartmentInfo(bean.getPickupCentralized(), bean.getCreateOrgCode(), bean.getCustomerPickupOrgCode().getCode(), bean.getProductCode().getCode());
			}else{
				//快递这里非常特殊
				dto = waybillService.queryLodeDepartmentInfo(bean.getPickupCentralized(), bean.getCreateOrgCode() /**bean.getReceiveOrgCode()*/, bean.getCustomerPickupOrgCode().getCode(), bean.getProductCode().getCode());
			}
			if (dto == null || dto.getFreightRoute() == null) {
				ui.cargoInfoPanel.getBtnWood().setEnabled(false);
				throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute"));
			} else {
				FreightRouteEntity freightRoute = dto.getFreightRoute();
				LOG.info("查询走货路径成功。。。");
				bean.setLoadLineName(dto.getRouteLineName());// 配载线路名称
				LOG.info("配载线路名称:" + dto.getRouteLineName());
				if (freightRoute != null) {
					bean.setLoadLineCode(freightRoute.getVirtualCode()==null?"NOTEXISTS":freightRoute.getVirtualCode());// 配载线路编码
					LOG.info("配载线路编码:" + freightRoute.getVirtualCode());
					bean.setPackageOrgCode(freightRoute.getPackingOrganizationCode());// 代打木架部门编码
					LOG.info("代打木架部门编码:" + freightRoute.getPackingOrganizationCode());
					bean.setPackingOrganizationName(freightRoute.getPackingOrganizationName());// 代打木架部门名称
					LOG.info("代打木架部门名称:" + freightRoute.getPackingOrganizationName());
					bean.setDoPacking(freightRoute.getDoPacking());// 是否可以打木架
					LOG.info("是否可以打木架:" + freightRoute.getDoPacking());
				} else {
					bean.setLoadLineCode("NOTEXISTS");// 配载线路编码
					LOG.info("配载线路编码:获取到的走货路径实体为空");
					bean.setPackageOrgCode("");// 代打木架部门编码
					LOG.info("代打木架部门编码:");
					bean.setPackingOrganizationName("");// 代打木架部门名称
					LOG.info("代打木架部门名称:");
					bean.setDoPacking("");// 是否可以打木架
					LOG.info("是否可以打木架:");
				}
				bean.setLoadOrgCode(dto.getFirstLoadOrgCode());// 配载部门编号
				LOG.info("配载部门编号:" + dto.getFirstLoadOrgCode());
				bean.setLoadOrgName(dto.getFirstLoadOrgName());// 配载部门名称
				LOG.info("配载部门名称:" + dto.getFirstLoadOrgName());
				bean.setLastLoadOrgCode(dto.getLastLoadOrgCode());// 最终配载部门编号
				LOG.info("最终配载部门编号:" + dto.getLastLoadOrgCode());
				bean.setLastLoadOrgName(dto.getLastLoadOrgName());// 最终配载部门名称
				LOG.info("最终配载部门名称:" + dto.getLastLoadOrgName());

				bean.setLastOutLoadOrgCode(dto.getLastOutLoadOrgCode());// 最终配置外场
				LOG.info("最终配置外场:" + dto.getLastOutLoadOrgCode());
//				bean.setGoodsTypeIsAB(dto.getGoodsTypeIsAB());// 是否AB货
//				LOG.info("是否可区分AB货:" + dto.getGoodsTypeIsAB());
				// 设置AB货编辑状态
//				setGoodsTypeAB(bean);
				// 如果路径可以打打木架则设置打木架按钮可点击
				if (FossConstants.YES.equals(bean.getDoPacking())) {
					ui.cargoInfoPanel.getBtnWood().setEnabled(false);//小件不能打木架
				} else {
					ui.cargoInfoPanel.getBtnWood().setEnabled(false);
				}
				bean.setPickupCentralizedDeptCode(dto.getPickupCentralizedDeptCode());
				LOG.info("集中接货开单组所在外场的驻地营业部编码:" + dto.getPickupCentralizedDeptCode());
			}
		} catch (BaseInfoInvokeException e) {
			throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute") /**
			 * 
			 * e.getMessage())
			 */
			);
		} catch (BusinessException w) {
			bean.setLoadLineName("");// 配载线路名称
			bean.setLoadLineCode("");// 配载线路编码
			bean.setLoadOrgCode("");// 配载部门编号
			bean.setLoadOrgName("");// 配载部门名称
			bean.setLastLoadOrgCode("");// 最终配载部门编号
			bean.setLastLoadOrgName("");// 最终配载部门名称
			bean.setPackageOrgCode("");// 代打木架部门编码
			bean.setPackingOrganizationName("");// 代打木架部门名称
			bean.setDoPacking("");// 是否可以打木架
			bean.setLastOutLoadOrgCode("");// 最终配置外场

			if (bean.getReceiveMethod() != null && WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())
					&& (CommonUtils.directDetermineIsExpressByProductCode(bean.getProductCode().getCode()))) {
				bean.setTargetOrgCode("");
				bean.setCustomerPickupOrgName("");
				throw new WaybillValidateException(w.getMessage());
			}

			ui.cargoInfoPanel.getBtnWood().setEnabled(false);// 代打木架按钮不可点击
			throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryFreightRoute") /**
			 * 
			 * e.getMessage())
			 */
			);
			// throw w;
		}
	}

	/**
	 * 
	 * 设置空运配载部门
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-22 上午08:35:03
	 */
	public void setAirDeptEnabled(WaybillPanelVo bean) {
		ProductEntityVo productVo = bean.getProductCode();
		if(null == productVo){
		    return;
		}
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
			ui.canvasContentPanel.getCargoRoutePanel().getBtnQueryAirDept().setEnabled(true);
		} else {
			ui.canvasContentPanel.getCargoRoutePanel().getBtnQueryAirDept().setEnabled(false);
		}

	}

	/**
	 * 
	 * 设置AB货是否可以编辑
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-17 下午03:53:40
	 */
	/*private void setGoodsTypeAB(WaybillPanelVo bean) {
		if (bean.getGoodsTypeIsAB()) {
//			ui.cargoInfoPanel.getRbnA().setEnabled(true);
//			ui.cargoInfoPanel.getRbnB().setEnabled(true);
			ui.cargoInfoPanel.getRbnA().setEnabled(false);
			
			ui.cargoInfoPanel.getRbnB().setEnabled(false);
			 bean.setGoodsType(null);
		} else {
			ui.cargoInfoPanel.getRbnA().setEnabled(false);
			
			ui.cargoInfoPanel.getRbnB().setEnabled(false);
	        bean.setGoodsType(null);
		}
	}
*/
	/**
	 * 显示提货网点查询窗口
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-12 下午07:54:48
	 */
	public void showPickupStationDialog(ExpWaybillPanelVo bean) {
		QueryPickupPointDto dto = new QueryPickupPointDto();
		// 网点类型标志
		dto.setDestNetType(bean.getProductCode().getDestNetType());
		if(bean.getReceiveMethod()!=null){
			// 提货方式
			dto.setPickUpType(bean.getReceiveMethod().getValueCode());
		}		
		// 产品编码
		dto.setTransType(bean.getProductCode().getCode());
		// 目的站（PS：此处获取必须从UI中获取而不应从bean获得，因为ui获得的是新值，而bean获得的是旧值-在清空目的站时bean值未被清空）
		dto.setOrgSimpleName(ui.getTransferInfoPanel().getTxtDestination().getText());
		// 出发营业部
		dto.setReceiveOrgCode(bean.getReceiveOrgCode());
		// 设置来源为开单
		dto.setSource(WaybillConstants.WAYBILL);
		dto.setCurDate(new Date());
		// 设置营业部的到达属性
		dto.setArrive(FossConstants.YES);
		
		/**
		 * 判断是否为空运，且配载类型是否为单独开单，如果是，则提货网点只显示机场
		 */
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())) {
			// 非空判断（无意中发现的一个空提针bug，顺手就加了一个非校验）
			if (null == bean.getFreightMethod() || null == bean.getFreightMethod().getId()) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.exception.notnull"));
			}
			/**
			 * 单独开单只能选择机场、合大票只能选择代理
			 */
			if (ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean.getFreightMethod().getValueCode())) {
				dto.setIsAirport(FossConstants.YES);
			}else if(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP.equals(bean.getFreightMethod().getValueCode())){
				dto.setIsAirport(FossConstants.NO);
			}
		}
		dto.setDestNetType(DictionaryValueConstants.DEPPON_OWN_ORG);
		
		if(bean.getReceiveCustomerAreaDto()!=null  && bean.getReceiveCustomerAreaDto().getCityName()!=null ){
			dto.setOrgSimpleName(bean.getReceiveCustomerAreaDto().getCityName());
		}else{
			//导入或补录订单时ReceiveCustomerAreaDto不存在，这里用来解决这种情况
			AddressFieldDto receiverAddressDto = ExpCommon.getAddressFieldInfoByCode
					(ui, bean.getReceiveCustomerProvCode(), bean.getReceiveCustomerCityCode(), bean.getReceiveCustomerDistCode());
			dto.setOrgSimpleName(receiverAddressDto.getCityName());
		}
		// 创建弹出窗口
		QueryPickupStationDialog dialog = new QueryPickupStationDialog(dto);
		// 剧中显示弹出窗口
		WindowUtil.centerAndShow(dialog);
		// 获得提货网点对象
		BranchVo branchVO = dialog.getBranchVO();
		//根据提货网点来校验 开返货时，不能选择外发营业部
		if(branchVO !=null && bean.getReturnType() !=null && WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(bean.getReturnType().getValueCode())){
			if(branchVO.getName().contains("远郊")){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.showPickupStationDialogAction.exception.notchangeouterhair"));
			}
		}
		setDialogData(branchVO, bean);
	}

	/**
	 * 给相关控件赋值：设置提货网点、目的站、代收货款、其它费用、装卸费、送货进仓等
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-14 下午5:20:07
	 */
	public void setDialogData(BranchVo branchVO, ExpWaybillPanelVo bean) {
		/**
         * 选择目的站为外发虚拟快递营业部且有代收货款时，校验代收货款是否超限制
         * 如果代收货款大于代收上限就给出提示
         * @author:280747-foss-zhuxue
		 * @date 2015-10-301下午04:35:17
         *//*
		if (bean.getCodAmount() != null) {
			//获取代收货款
			BigDecimal codAmount = bean.getCodAmount();
			if (bean.getCustomerPickupOrgCode() != null) {
				if (waybillService.querySaleDepartmentByCodeNoCache(bean.getCustomerPickupOrgCode().getCode()) != null) {
					//通过提货网点返回含有代收货款上限的实体
					SaleDepartmentEntity IsaleDepartmentEntity = waybillService
							.querySaleDepartmentByCodeNoCache(bean
									.getCustomerPickupOrgCode().getCode());
					// BigDecimal codeAmount=new
					// BigDecimal(IsaleDepartmentEntity.getAgentCollectedUpperLimit());
					if (waybillService.querySaleDepartmentByCodeNoCache(bean.getCustomerPickupOrgCode().getCode()).getAgentCollectedUpperLimit() != null
							&& codAmount.compareTo(new BigDecimal(
									waybillService.querySaleDepartmentByCodeNoCache(bean.getCustomerPickupOrgCode().getCode())
											.getAgentCollectedUpperLimit())) > 0) {
						// String loadName = bean.getCustomerPickupOrgName();
						if (bean.getCustomerPickupOrgName() != null
								|| "".equals(bean.getCustomerPickupOrgName())
								&& bean.getCustomerPickupOrgName().endsWith(
										"远郊快递营业部")) {
							//超过该营业部代收上限，请重新选择目的站.
							throw new WaybillValidateException(
									i18n.get("foss.gui.creating.calculateAction.exception.validateCod.limit"));
						}
					}
				}
			}
		}*/
		if (branchVO != null) {
			bean.setCustomerPickupOrgCode(branchVO);
			bean.setCustomerPickupOrgName(branchVO.getName());
			 
//				else{
//				throw new WaybillValidateException(
//						i18n.get("foss.gui.creating.calculateAction.exception.validateCod.limit3"));
//			}
			

			// 反写目的站:如果目的站不为空，则选择好网点后将其设置为部门简称
			BusinessUtils bu = new BusinessUtils();
			
			//TODO 这里的targetorgcode不一样了 要显示城市
			ProductEntityVo pev = bean.getProductCode();
			
			
			

			// 设置是否可代收货款和是否可到付款标致
			canAgentCollected(branchVO, bean);
			// 清空其他费用列表
			ExpCommon.cleanOtherCharge(bean, ui);
			// 查询其他费用
			queryOtherChargeData(ui, bean);
			// 计算其他费用合计
			calculateOtherCharge(ui, bean);
			// 把装卸费清空
			bean.setServiceFee(BigDecimal.ZERO);

			// 临时使用
			if (bean.getTargetOrgCode() == null || "".equals(bean.getTargetOrgCode())) {
				bean.setTargetOrgCode(i18n.get("foss.gui.creating.showPickupStationDialogAction.forShort.label"));
			}

			/**
			 * 根据提货网点所在标准城市或收货部门所在标准城市为香港，限制代收货款和装卸费的录入；
			 */
			setCanAgentCollectedOrServiceFee(branchVO, bean);
			
			/**
			 * 根据提货网点所在标准城市或收货部门所在标准城市为台湾，提示需提供发货人身份证复印件和收货人身份证号码；
			 */
		/*	setCanAgentCollectedOrTWServiceFee(branchVO, bean);*/
			
			/**
			 * 在网点目的站基础资料中有
			 * '取消到达日期'，如果当前日期在'取消到达日期'之前，那么提示"xx营业部将于xx年xx月xx日临时取消到达，届时货物将转至xx营业部，请做好客户解释工作！"（其中第一个xx营业部，为当前营业部、第二个xx营业部为网点目的站基础资料中的'转货部门'，xx年xx月xx日为'取消到达日期
			 * ' ）
			 */
			
			
			if (branchVO.getCancelArrivalDate() != null && branchVO.getTransferGoodDept() != null) {
				/**
				 * 转货部门
				 */
				SaleDepartmentEntity saleDepartmentEntity = WaybillServiceFactory.getWaybillService()
						.querySaleDeptByCode(branchVO.getTransferGoodDept());
				
				/**
				 * 取消到达日期
				 */
				String cancelArrivalDate = DateUtils.getChDate(branchVO.getCancelArrivalDate());
				/**
				 * 转货部门
				 */
				if (saleDepartmentEntity != null) {
					String transferGoodDeptName = saleDepartmentEntity.getName();
					String message = i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.CancelArrive", new Object[] { branchVO.getName(), cancelArrivalDate, transferGoodDeptName });
					MsgBox.showInfo(message);
				}
			}
			ExpCommon.setSaveAndSubmitFalse(ui);
			//将光标设置到提货网点文本框
			ui.getTransferInfoPanel().getTxtPickBranch().requestFocus();
			
			// 设置是否可返回签单
			canReturnSignBill(branchVO, bean);
			
			SalesDepartmentService salesDepartmentService 
				= DownLoadDataServiceFactory.getSalesDepartmentService();
			SalesDepartmentCityDto dto  = new SalesDepartmentCityDto();
			dto.setSalesDepartmentCode(branchVO.getCode());
			SalesDepartmentCityDto result = 
					salesDepartmentService.querySalesDepartmentCityInfo(dto);
			//querySalesDepartmentCityInfo
			SaleDepartmentEntity saleDepartmentEntity = WaybillServiceFactory.getWaybillService()
					.querySaleDeptByCode(branchVO.getCode());
			if(saleDepartmentEntity!=null && result!=null){
				//营业部是否可以快递接货，如果是的话 就是试点营业部
				result.setTestSalesDepartment(saleDepartmentEntity.getCanExpressPickupToDoor());
			}
			
			
			
			if(pev!=null 
					&& (CommonUtils.directDetermineIsExpressByProductCode(pev.getCode()))
					&& bean.getReceiveMethod()!=null
					&& !CommonUtils.verdictPickUpSelf( bean.getReceiveMethod().getValueCode() )){
				
				if(result!=null && StringUtils.isNotEmpty(result.getCityName())){
					bean.setTargetOrgCode(result.getCityName());
				}else{
					//TODO---无试点城市
					bean.setTargetOrgCode("无试点城市");
				}
				
				
			}else{
				
				String simpleName = bu.getSimpleName(branchVO.getCode(), bean.getBillTime());
				if (!"".equals(simpleName)) {
					bean.setTargetOrgCode(simpleName);
				} else {
					bean.setTargetOrgCode(branchVO.getTargetOrgName());
				}
				
			}
			bean.setTargetSalesDepartmentCityDto(result);
			
		}
		
	}
	
	/**
	 * 
	 * 根据提货网点所在标准城市或收货部门所在标准城市为台湾，提示需提供发货人身份证复印件和收货人身份证号码
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-28 上午10:49:05
	 */
/*	private void setCanAgentCollectedOrTWServiceFee(BranchVo branchVO, WaybillPanelVo bean) {
		
		
		
		 String _HK_PROV = "710000";
															
		 String _HK_CITY = "710100";
		try{
		 ConfigurationParamsEntity cityHKCode= getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_CITY_HK_CODE);//城市
		 ConfigurationParamsEntity proHKCode= getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_PROV_HK_CODE);//省份
		 
		 if(cityHKCode!=null&&StringUtils.isNotEmpty(cityHKCode.getConfValue()))
		 {
			 _HK_CITY= cityHKCode.getConfValue();
		 }
		 
		 if(proHKCode!=null&&StringUtils.isNotEmpty(proHKCode.getConfValue()))
		 {
			 _HK_PROV= proHKCode.getConfValue();
		 }
		 
		}catch(Exception e)
		{
			// do Nothing
		}
															
		*//**
		 * 获取目的站所在城市，如果是台湾，需提供发货人身份证复印件和收货人身份证号码
		 *//*
		if (branchVO.getCityCode() != null && _HK_CITY.equals(branchVO.getCityCode())) {
			MsgBox.showInfo("目的站为台湾，需提供发货人身份证复印件和收货人身份证号码");
			*//**
			 * 由于发货部门能取到省份，可以用省份来进行判断
			 *//*
		} else if (bean.getReceiveOrgProvCode() != null && _HK_PROV.equals(bean.getReceiveOrgProvCode())) {
			MsgBox.showInfo("目的站为台湾，需提供发货人身份证复印件和收货人身份证号码");
		}
	}*/

	
	
	

	/**
	 * 
	 * 根据提货网点所在标准城市或收货部门所在标准城市为香港，限制代收货款和装卸费的录入；
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-28 上午10:49:05
	 */
	private void setCanAgentCollectedOrServiceFee(BranchVo branchVO, WaybillPanelVo bean) {
		
		
		 String _HK_PROV = "810000";

		 String _HK_CITY = "810100";
		try{
		 ConfigurationParamsEntity cityHKCode= getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_CITY_HK_CODE);//城市
		 ConfigurationParamsEntity proHKCode= getConfigurationParamsEntity(ConfigurationParamsConstants.PKP_PROV_HK_CODE);//省份
		 
		 if(cityHKCode!=null&&StringUtils.isNotEmpty(cityHKCode.getConfValue()))
		 {
			 _HK_CITY= cityHKCode.getConfValue();
		 }
		 
		 if(proHKCode!=null&&StringUtils.isNotEmpty(proHKCode.getConfValue()))
		 {
			 _HK_PROV= proHKCode.getConfValue();
		 }
		 
		}catch(Exception e)
		{
			// do Nothing
		}
		
		/**
		 * 获取目的站所在城市，如果是香港，设置代收代收货款和装卸费不可录入
		 */
		if (branchVO.getCityCode() != null && _HK_CITY.equals(branchVO.getCityCode())) {
			setCanAgentCollectedOrServiceFeeFalse(bean);

			/**
			 * 由于发货部门能取到省份，可以用省份来进行判断
			 */
		} else if (bean.getReceiveOrgProvCode() != null && _HK_PROV.equals(bean.getReceiveOrgProvCode())) {
			setCanAgentCollectedOrServiceFeeFalse(bean);
		}
	}
	
	
	/**
	 * 获取系统参数
	 * 
	 * @param type
	 * @return
	 */
	private ConfigurationParamsEntity getConfigurationParamsEntity(String type) {
		/**
		 * 根据组织的配置参数查询系统参数实体
		 * 
		 *  组织配置参数-接送货模块使用：DictionaryConstants.SYSTEM_CONFIG_PARM__PKP
		 *  异常转弃货JOB扫描天数：FossConstants.ROOT_ORG_CODE
		 */
		return configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, type, FossConstants.ROOT_ORG_CODE);

	}
	
	

	/**
	 * 
	 * 限制代收货款和装卸费的录入
	 * 
	 * @param bean
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-28 上午11:08:46
	 */
	private void setCanAgentCollectedOrServiceFeeFalse(WaybillPanelVo bean) {
		bean.setCodAmountCanvas(BigDecimal.ZERO.toString());// 收代收货款
		bean.setCodAmount(BigDecimal.ZERO);//收代收货款
		ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);// 收代收货款不可修改
		ui.incrementPanel.getCombRefundType().setEnabled(false);//返单类型
		ui.incrementPanel.getCombRefundType().setEnabled(false);//退款类型
		bean.setServiceFeeCanvas(BigDecimal.ZERO.toString());// 装卸费
		bean.setServiceFee(BigDecimal.ZERO);// 装卸费
		ui.incrementPanel.getTxtServiceCharge().setEnabled(false);// 装卸费不可修改
	}

	/**
	 * 
	 * 初始化其他费用合计
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 下午05:12:36
	 */
	private void calculateOtherCharge(ExpWaybillEditUI ui, WaybillPanelVo bean) {
		JXTable table = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
		List<OtherChargeVo> data = model.getData();

		if (data != null && !data.isEmpty()) {
			BigDecimal otherChargeSum = BigDecimal.ZERO;
			// 其他费用合计
			for (OtherChargeVo vo : data) {
				BigDecimal money = new BigDecimal(vo.getMoney());
				otherChargeSum = otherChargeSum.add(money);
			}
			// 其他费用
			bean.setOtherFee(otherChargeSum);
			// 画布其他费用
			bean.setOtherFeeCanvas(otherChargeSum.toString());
		}
	}

	/**
	 * 
	 * 查询其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 上午11:22:50
	 */
	private void queryOtherChargeData(ExpWaybillEditUI ui, WaybillPanelVo bean) {
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(getQueryOtherChargeParam(bean));

		List<OtherChargeVo> voList = getOtherChargeList(list);
		if (voList != null) {
			if (!voList.isEmpty()) {
				for (int i = 0; i < voList.size(); i++) {
					if("ZYSCZJH".equals(voList.get(i).getCode())){
						voList.remove(i);
					}
				}
				ui.incrementPanel.setChangeDetail(otherChargeCompare(voList));
			}
		}
	}

	/**
	 * 
	 * 将原有其他费用与新查询出来其他费用进行比较，然后删除重复的项
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 下午03:03:01
	 */
	private List<OtherChargeVo> otherChargeCompare(List<OtherChargeVo> voList) {
		JXTable table = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
		List<OtherChargeVo> data = model.getData();
		if (data != null) {
			if (!data.isEmpty()) {
				for (int i = 0; i < voList.size(); i++) {
					OtherChargeVo queryVo = voList.get(i);
					for (int j = 0; j < data.size(); j++) {
						OtherChargeVo tableVo = data.get(j);
						if (tableVo.getChargeName().equals(queryVo.getChargeName())) {
							data.remove(j);
							data.add(j, queryVo);
						}
					}
				}
				return data;
			} else {
				return voList;
			}
		} else {
			return voList;
		}
	}

	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private QueryBillCacilateValueAddDto getQueryOtherChargeParam(WaybillPanelVo bean) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		// 出发部门CODE
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());
		// 到达部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());
		// 产品CODE
		queryDto.setProductCode(bean.getProductCode().getCode());
		// 货物类型CODE
		queryDto.setGoodsTypeCode(null);
		queryDto.setReceiveDate(new Date());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(BigDecimal.ZERO);// 重量
		queryDto.setVolume(BigDecimal.ZERO);// 体积
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		return queryDto;
	}

	/**
	 * 
	 * 将查询出的其他费用设置到表格list中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:00:49
	 */
	private List<OtherChargeVo> getOtherChargeList(List<ValueAddDto> list) {
		List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();

		if (list != null) {
			for (ValueAddDto dto : list) {
				// 开单的时候不能增加更改费
				if (PriceEntityConstants.PRICING_CODE_GGF.equals(dto.getSubType())) {
					continue;
				}

				OtherChargeVo vo = new OtherChargeVo();
				if (dto.getCandelete() != null && !BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete())) {
					// 费用编码
					vo.setCode(dto.getSubType());
					// 名称
					vo.setChargeName(dto.getSubTypeName());
					// 归集类别
					vo.setType(dto.getBelongToPriceEntityName());
					// 描述
					vo.setDescrition(dto.getPriceEntityCode());
					// 金额
					vo.setMoney(dto.getFee().toString());
					// 上限
					vo.setUpperLimit(dto.getMaxFee().toString());
					// 下限
					vo.setLowerLimit(dto.getMinFee().toString());
					// 是否可修改
					vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto.getCanmodify()));
					// 是否可删除
					vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete()));
					vo.setId(dto.getId());
					voList.add(vo);
				}
			}
		}
		return voList;
	}

	/**
	 * 
	 * 判断此网点是否可以开代收货款
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-7 下午04:07:04
	 */
	private void canAgentCollected(BranchVo branchVo, WaybillPanelVo bean) {
		//是否可代收货款
		bean.setCanAgentCollected(branchVo.getCanAgentCollected());
		//是否可货到付款
		bean.setArriveCharge(branchVo.getArriveCharge());
//		if (FossConstants.YES.equals(canAgentCollected)) {
//			// 代收货款金额
//			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(true);
//			// 代收货款类型
//			ui.incrementPanel.getCombRefundType().setEnabled(true);
//		} else {
//			// 代收货款金额
//			ui.incrementPanel.getTxtCashOnDelivery().setEnabled(false);
//			// 代收货款类型
//			ui.incrementPanel.getCombRefundType().setEnabled(false);
//			// 清理代收货款信息
//			cleanCodInfo(ui, bean);
//		}
	}

	/**
	 * 
	 * 清理代收货款信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 下午05:16:21
	 */
	private void cleanCodInfo(ExpWaybillEditUI ui, WaybillPanelVo bean) {
		// 清理银行信息
		ExpCommon.cleanBankInfo(bean);
		// 代收货款金额
		bean.setCodAmount(BigDecimal.ZERO);
		// 代收货款费率
		bean.setCodRate(BigDecimal.ZERO);
		// 代收货款手续费
		bean.setCodFee(BigDecimal.ZERO);
		// 代收货款ID
		bean.setCodId("");
		// 代收货款编码
		bean.setCodCode("");
		// 画布-代收货款金额
		bean.setCodAmountCanvas(BigDecimal.ZERO.toString());
		// 将退款类型设置为空
		ExpCommon.setRefundType(bean, ui);
	}
	
	/**
	 * 设置返单类别是否可编辑
	 * @author YeTao
	 * @date 2014-7-11 下午19:35
	 */
	
	private void canReturnSignBill(BranchVo branchVo, ExpWaybillPanelVo bean) {
		if(FossConstants.YES.equals(branchVo.getCanExpressReturnSignBill())){			
			ui.incrementPanel.getCombReturnBillType().setEnabled(true);
		}else{
			bean.getReturnBillType().setValueCode(WaybillConstants.NOT_RETURN_BILL);
			ui.incrementPanel.getCombReturnBillType().setEnabled(false);
		}		
	}

	/**
	 * 
	 * 获得预计派送/提货时间
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 下午02:17:31
	 */
	private Date getPickupDeliveryTime(WaybillPanelVo bean) {
//		String strOrgCode = "";
//		if(bean.getPickupCentralized()!=null&&bean.getPickupCentralized()){
//			strOrgCode = bean.getPickupCentralizedDeptCode();
//		}else{
//			strOrgCode = bean.getReceiveOrgCode();
//		}
		EffectiveDto effectiveDto = new EffectiveDto();
		if (isPickup(bean)) {
			effectiveDto = waybillService.searchPreSelfPickupTime(bean.getCreateOrgCode(), bean.getLastLoadOrgCode(), bean.getProductCode().getCode(), bean.getPreDepartureTime(), new Date());
			if (effectiveDto != null) {
				bean.setLongOrShort(effectiveDto.getLongOrShort());
				return effectiveDto.getSelfPickupTime();
			} else {
				cleanTargetEmpty(bean);
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryTime"));
				return null;
			}

		} else {
			effectiveDto = waybillService.searchPreDeliveryTime(bean.getCreateOrgCode(), bean.getLastLoadOrgCode(), bean.getProductCode().getCode(), bean.getPreDepartureTime(), new Date());
			if (effectiveDto != null) {
				bean.setLongOrShort(effectiveDto.getLongOrShort());
				return effectiveDto.getDeliveryDate();
			} else {
				cleanTargetEmpty(bean);
				MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryTime"));
				return null;
			}

		}
	}

	/**
	 * 
	 * 判断提货方式是否自提
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-21 上午09:46:55
	 */
	private Boolean isPickup(WaybillPanelVo bean) {
		/**
		 * 判断提货方式是否为空
		 */
		if(bean.getReceiveMethod()!=null){
			String code = bean.getReceiveMethod().getValueCode();
			if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.INNER_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code) || WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code))

			{
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}		
	}

	/**
	 * 
	 * 获得预计出发时间
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 下午02:17:31
	 */
	private Date getLeaveTime(WaybillPanelVo bean) {
		
		Date leaveTime = waybillService.searchPreLeaveTime(bean.getCreateOrgCode(), bean.getLoadOrgCode(), bean.getProductCode().getCode(), new Date());
		return leaveTime;
	}

	/**
	 * 功能：set ui
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public void setInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;

	}

}
