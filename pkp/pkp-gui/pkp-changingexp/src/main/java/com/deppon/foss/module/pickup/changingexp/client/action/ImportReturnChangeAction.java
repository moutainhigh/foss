package com.deppon.foss.module.pickup.changingexp.client.action;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.EditorConfig;
import com.deppon.foss.framework.client.core.workbench.IEditor;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.vo.ExpReturnedGoodsWaybillVo;
import com.deppon.foss.module.pickup.changingexp.client.vo.TransportRecordVo;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.ICustomerService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.CustomerService;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.ExpCommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmActiveInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TransportRecordDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcImportDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.util.define.FossConstants;
/*import com.deppon.foss.module.pickup.creating.client.service.IWaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.service.impl.WaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpReturnedGoodsWaybillUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.vo.ExpReturnedGoodsWaybillVo;*/

public class ImportReturnChangeAction {
	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ImportReturnChangeAction.class);
	
	/**
	 * 接送货运单服务
	 * 提供接送货运单服务接口
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 结算运单开单服务
	 * 提供结算运单开单服务接口
	 * 
	 * 
	 * 业务规则：
	 * 一、代收货款
	 * 1、	代收货款金额处于代收货款金额范围之内
	 * 2、	如果代收货款类型为“即日退或三日退”，则收款人且开户行信息不能为空
	 * 3、	如果代收货款类型为“即日退”，
	 * 则其开户银行必须在即日退所属银行范围内
	 * （即日退所属银行范围已经提交给综合管理做基础资料了）
	 * 4、	如果代收货款类型为“即日退”，则代收货款的退款账号类型不能为“对公帐号”
	 * 5、 	应付单为有效版本且非红单
	 * 6、	应付单单据类型为“应付代收货款”
	 * 7、	应付单的来源单据编号等于传入的运单号
	 */
	private IWaybillPickupService waybillPickupService;

	
	private OpenWaybillRFCUIAction openWaybillRFCUIAction;
	private WaybillRfcImportDto rfcImportDto ;
	private WaybillRFCUI waybillRFCUI;
	/**
	 * 应用实例类
	 */
	private IApplication application;
	private WaybillImportAction waybillImportAction=new WaybillImportAction() ;
	
	private IWaybillRfcService rfcService = WaybillRfcServiceFactory.getWaybillRfcService();
	private IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);
	private ICustomerService customerService = GuiceContextFactroy.getInjector().getInstance(CustomerService.class);
	
	
	
	/**
	 * 国际化
	 * 提供i18n国际化相关的服务接口
	 */
	private static II18n i18n = I18nManager.getI18n(ImportReturnChangeAction.class);
	// 工具类获取branchVo对象
		BusinessUtils bu = new BusinessUtils();
		
		/**
		 * 导入返货管理运单
		 * 
		 * @author 199293
		 * @date 2015-2-4 
		 */
		public void importWaybillEditUI(Object object) {
			IEditor editor = null ;
			try {
				if (null == object) {
					throw new WaybillImportException(i18n.get("foss.gui.creating.importWaybillEditUIAction.exception.nullObject"));
				}
				
				WaybillInfoVo waybillInfoVo = new WaybillInfoVo();
				//转寄退回VO
				ExpReturnedGoodsWaybillVo value = (ExpReturnedGoodsWaybillVo)object;
				final String waybillno=value.getWaybillNo();
				rfcImportDto = null;
				// 查询远程运单信息
			    rfcImportDto = rfcService.importWaybillBy(waybillno);
				application = ApplicationContext.getApplication();
				EditorConfig editConfig = new EditorConfig();
				editConfig.setTitle(i18n.get("waybill.action.waybillrfc"));
				editConfig.setPluginId("com.deppon.foss.module.pkp-changingexp");
				editor = application
						.openEditorAndRetrun(editConfig,
								"com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI");
				waybillRFCUI= (WaybillRFCUI) editor.getComponent();
				//waybillRFCUI.setEditor(editor);
				//waybillRFCUI.openUI();
//				waybillRFCUI.getWaybillInfoPanel().getConsigneePanel().getTxtConsigeeName().setEditable(false);
//				waybillRFCUI.getWaybillInfoPanel().getConsigneePanel().getTxtConsigneePhone().setEditable(false);
//				waybillRFCUI.getWaybillInfoPanel().getConsigneePanel().getTxtConsigneeMobile().setEditable(false);
				waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getTxtDestination().setEditable(false);
				waybillRFCUI.getImportPanel().getTxtWaybillNo().setText(waybillno);
			    waybillInfoVo = translateToVO(rfcImportDto);
			    waybillRFCUI.importWaybill(waybillInfoVo, true);
			    waybillInfoVo.setOldreturnBillType(waybillInfoVo.getReturnBillType());
			    waybillInfoVo.setOldReceiveOrgCode(waybillInfoVo.getCustomerPickupOrgCode().getCode());
			    waybillInfoVo.setOldReceiveOrgName(waybillInfoVo.getCustomerPickupOrgName());
			    waybillInfoVo.setOldreceiveCustomerAddress(waybillInfoVo.getReceiveCustomerAddress());
			    waybillInfoVo.setTransportFeeOrg(waybillInfoVo.getTransportFee()!=null ? waybillInfoVo.getTransportFee() : BigDecimal.ZERO);
			    waybillInfoVo.setTransportFeeCanvasOrg(StringUtils.isNotBlank(waybillInfoVo.getTransportFeeCanvas()) ? waybillInfoVo.getTransportFeeCanvas() : "0");
			    waybillRFCUI.getCompareListener().clearRfcChangeDetailMap();
			    if(StringUtils.equals(value.getReturnReason(), "CUSTOMER_REASON")){
			    waybillInfoVo.setIsChangeReason(1);
			    waybillRFCUI.getImportPanel().getRdoCustomerRequire().setSelected(true);  
			      }else if (StringUtils.equals(value.getReturnReason(),"COMPANY_REASON")){
			    	  waybillInfoVo.setIsChangeReason(2);
			    	  waybillRFCUI.getImportPanel().getRdoInsideRequire().setSelected(true);
			    }
			    waybillRFCUI.getImportPanel().getCboRfcType().setSelectedIndex(0);
				
			} catch (BusinessException ex) {
				try {
					LOGGER.error("导入异常", ex);
					 MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+ex.getMessage());
				} catch (Exception e) {
					LOGGER.error("导入异常", e);
				}
		}}

		/**
		 * 
		 * 转换运单信息DTO到VO
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2012-11-2 上午8:36:05
		 */
		public WaybillInfoVo translateToVO(WaybillRfcImportDto rfcImportDto) {
			WaybillInfoVo waybillInfoVo = new WaybillInfoVo();
			if (rfcImportDto == null) {
				waybillInfoVo = waybillRFCUI.getBinderWaybill();
				waybillInfoVo.setWaybillNo(null);
				return waybillInfoVo;
			}
			try {

				List<ProductEntity> list = WaybillRfcServiceFactory.getWaybillRfcService().queryTransType(rfcImportDto.getWaybillDto().getWaybillEntity().getReceiveOrgCode());
				DefaultComboBoxModel productTypeModel = waybillRFCUI.getWaybillInfoPanel().getTransferPanel().getTransportInfoPanel().getProductTypeModel();
				productTypeModel.removeAllElements();

				WaybillExpressEntity exp = rfcImportDto.getWaybillExpressEntity();
				
				if(exp!=null){
					
					waybillInfoVo.setGoodsVolumePreviousTotal(exp.getVolumeWeight());
				}
				for (ProductEntity dataDictionary : list) {
					ProductEntityVo vo = new ProductEntityVo();
					try {
						BeanUtils.copyProperties(vo, dataDictionary);
					} catch (IllegalAccessException e) {
						LOGGER.error(e.getMessage());
					} catch (InvocationTargetException e) {
						LOGGER.error(e.getMessage());
					}
					productTypeModel.addElement(vo);
				}

				// 原始运单
				waybillInfoVo.setWaybillDto(rfcImportDto.getWaybillDto());
				//wutao
				WaybillDto wdto = rfcImportDto.getWaybillDto();
				if (wdto != null) {
					ActualFreightEntity acf = wdto.getActualFreightEntity();
					if (acf != null) {
						// 设置【是否统一结算】
						if (acf.getStartCentralizedSettlement()!=null && !acf.getStartCentralizedSettlement().equals("") && FossConstants.YES.equals(acf.getStartCentralizedSettlement())) {
							waybillInfoVo
									.setStartCentralizedSettlement(WaybillConstants.IS_NOT_NULL_FOR_AI);
						} else {
							waybillInfoVo
									.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
						}
						if (acf.getArriveCentralizedSettlement()!= null && !acf.getArriveCentralizedSettlement().equals("") && FossConstants.YES.equals(acf.getArriveCentralizedSettlement())) {
							waybillInfoVo
									.setArriveCentralizedSettlement(WaybillConstants.IS_NOT_NULL_FOR_AI);
						} else {
							waybillInfoVo
									.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
						}
						// 【合同部门】
						if (acf.getStartContractOrgCode() != null
								&& !acf.getStartContractOrgCode().equals("")) {
							waybillInfoVo.setStartContractOrgName(ExpCommonUtils
									.queryContractOrgName(acf
											.getStartContractOrgCode()));
						} else {
							waybillInfoVo.setStartContractOrgName(null);
						}
						if (acf.getArriveContractOrgCode() != null
								&& !acf.getArriveContractOrgCode().equals("")) {
							waybillInfoVo.setArriveContractOrgName(ExpCommonUtils
									.queryContractOrgName(acf
											.getArriveContractOrgCode()));
						} else {
							waybillInfoVo.setArriveContractOrgName(null);
						}
						// 【催款部门编码】
						waybillInfoVo.setStartReminderOrgCode(acf
								.getStartReminderOrgCode());
						waybillInfoVo.setArriveReminderOrgCode(acf
								.getArriveReminderOrgCode());
						// end
						/**
						 * 将交易流水号带出
						 * @author:218371-foss-zhaoyanjun
						 * @date:2015-03-26下午14:18
						 */
						waybillInfoVo.setTransactionSerialNumber(acf.getTransactionSerialNumber());
						/**
						 * 内部发货类型和工号
						 */
						waybillInfoVo.setInternalDeliveryType(dataDictionaryValueEntityToVo(
								WaybillConstants.INTERNAL_DELIVERY_TYPE,
								acf.getInternalDeliveryType()));
						waybillInfoVo.setEmployeeNo(acf.getEmployeeNo());
						if(StringUtil.isNotBlank(acf.getEmployeeNo())) {
							waybillInfoVo.setOriginalEmployeeNo(acf.getEmployeeNo());
						}
					}
				}

				WaybillEntity waybillEntity = rfcImportDto.getWaybillDto().getWaybillEntity();
				/**
				 * 
				 */
				waybillInfoVo.setOriginalFee(waybillEntity.getPromotionsFee());
				waybillInfoVo.getWaybillDto().getWaybillEntity().setOriginalEmployeeNo(waybillInfoVo.getOriginalEmployeeNo());
				waybillInfoVo.getWaybillDto().getWaybillEntity().setOriginalFee(waybillInfoVo.getOriginalFee());
				
				// 运输性质
				ProductEntityVo productEntityVo = getProductTypeByCode(waybillEntity.getProductCode(), waybillEntity.getCreateTime());

				WoodenRequirementsEntity woodenRequirementsEntity = rfcImportDto.getWaybillDto().getWoodenRequirementsEntity();
				// 货物状态
				if (rfcImportDto.getStockStatus() != null) {
					waybillInfoVo.setGoodsStatus(dataDictionaryValueEntityToVo(WaybillRfcConstants.STOCK_STATUS, rfcImportDto.getStockStatus().getResult()));
					waybillInfoVo.setStockStatus(rfcImportDto.getStockStatus());
				}
				// 转货记录
				List<TransportRecordDto> transportRecordDtos = rfcImportDto.getTransportRecordDtos();
				List<TransportRecordVo> transferRecords = new ArrayList<TransportRecordVo>();
				List<TransportRecordVo> returnRecords = new ArrayList<TransportRecordVo>();
				List<TransportRecordVo> transportRecords = new ArrayList<TransportRecordVo>();
				TransportRecordVo finalTransportRecordBeforeChange = null;
				for (TransportRecordDto recordDto : transportRecordDtos) {
					TransportRecordVo recordVo = new TransportRecordVo();
					recordVo.setBillingType(dataDictionaryValueEntityToVo(WaybillConstants.BILLING_WAY, recordDto.getBillingType()));

					// 提货网点
					if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(recordDto.getProductCode())
							|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(recordDto.getProductCode())) {
						// 偏线空运
						OuterBranchEntity selectedOuterBranchEntity = rfcService.queryAgencyBranchCompanyInfo(recordDto.getCustomerPickupOrgCode());
						if (selectedOuterBranchEntity != null) {
							BranchVo branchVO = new BranchVo();
							// 网点编号
							branchVO.setCode(selectedOuterBranchEntity.getAgentDeptCode());
							// 网点名称
							branchVO.setName(selectedOuterBranchEntity.getAgentDeptName());
							// 是否送货上门
							branchVO.setDelivery(selectedOuterBranchEntity.getPickupToDoor());
							// 是否可自提
							branchVO.setPickupSelf(selectedOuterBranchEntity.getPickupSelf());

							// 部门城市code
							branchVO.setCityCode(selectedOuterBranchEntity.getCityCode());
							// 提货网点
							recordVo.setCustomerPickupOrgCode(branchVO);
							// 提货网点名称
							recordVo.setCustomerPickupOrgName(branchVO.getName());

						}
					} else {
						// 自有营业部
						SaleDepartmentEntity selectedSaleDepartmentInfo = rfcService.querySaleDepartmentByCode(recordDto.getCustomerPickupOrgCode());
						if (selectedSaleDepartmentInfo != null) {
							BranchVo branchVO = new BranchVo();
							// 网点编号
							branchVO.setCode(selectedSaleDepartmentInfo.getCode());
							// 网点名称
							branchVO.setName(selectedSaleDepartmentInfo.getName());
							// 单票重量上限
							branchVO.setSingleBillLimitkg(selectedSaleDepartmentInfo.getSingleBillLimitkg());
							// 单票体积上限
							branchVO.setSingleBillLimitvol(selectedSaleDepartmentInfo.getSingleBillLimitvol());
							// 单件重量上限
							branchVO.setSinglePieceLimitkg(selectedSaleDepartmentInfo.getSinglePieceLimitkg());
							// 单件体积上限
							branchVO.setSinglePieceLimitvol(selectedSaleDepartmentInfo.getSinglePieceLimitvol());
							// 是否送货上门
							branchVO.setDelivery(selectedSaleDepartmentInfo.getDelivery());
							// 是否可自提
							branchVO.setPickupSelf(selectedSaleDepartmentInfo.getPickupSelf());
							// 所属城市
							OrgAdministrativeInfoEntity infoEntity = rfcService.queryByCode(selectedSaleDepartmentInfo.getCode());
							if (infoEntity != null) {
								branchVO.setCityCode(infoEntity.getCityCode());
							}
							// 提货网点
							recordVo.setCustomerPickupOrgCode(branchVO);
							// 提货网点名称
							recordVo.setCustomerPickupOrgName(branchVO.getName());
						}
					}
					// 预配航班
					recordVo.setFlightNumberType(dataDictionaryValueEntityToVo(WaybillConstants.AIR_FLIGHT_TYPE, recordDto.getFlightNumberType()));
					// 合票方式
					recordVo.setFreightMethod(dataDictionaryValueEntityToVo(WaybillConstants.MAKE_WAYBILL_WAY, recordDto.getFreightMethod()));
					// 航班时间
					recordVo.setFlightShift(recordDto.getFlightShift());
					// 运输性质
					recordVo.setProductCode(getProductTypeByCode(recordDto.getProductCode(), waybillEntity.getCreateTime()));
					// 提货方式
					// 提货方式
					if (productEntityVo != null) {
						if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productEntityVo.getCode())) {
							recordVo.setReceiveMethod(dataDictionaryValueEntityToVo(WaybillConstants.PICKUP_GOODS_AIR, waybillEntity.getReceiveMethod()));
						} else {
							recordVo.setReceiveMethod(dataDictionaryValueEntityToVo(WaybillConstants.PICKUP_GOODS_HIGHWAYS, waybillEntity.getReceiveMethod()));
						}
					}

					// 目的站
					recordVo.setTargetOrgCode(recordDto.getTargetOrgCode());

					// 运费
					recordVo.setTransportFee(recordDto.getTransportFee());
					// 费率
					recordVo.setUnitPrice(recordDto.getUnitPrice());
					if (WaybillRfcConstants.TRANSFER.equals(recordDto.getRfcType())) {// 转货
						transferRecords.add(recordVo);
						transportRecords.add(recordVo);
					} else if (WaybillRfcConstants.RETURN.equals(recordDto.getRfcType())) {
						returnRecords.add(recordVo);
						transportRecords.add(recordVo);
					} else {
						// 运费
						recordVo.setTransportFee(waybillEntity.getTransportFee());
						// 费率
						recordVo.setUnitPrice(waybillEntity.getUnitPrice());

						finalTransportRecordBeforeChange = recordVo;
					}
				}

				List<WaybillDiscountVo> data = new ArrayList<WaybillDiscountVo>();
				if (rfcImportDto.getWaybillDto().getWaybillDisDtlEntity() != null) {
					for (WaybillDisDtlEntity waybillDisDtlEntity : rfcImportDto.getWaybillDto().getWaybillDisDtlEntity()) {
						WaybillDiscountVo vo = new WaybillDiscountVo();
						// 折扣ID
						vo.setDiscountId(waybillDisDtlEntity.getDicountId());
						// 费用类型id
						vo.setChargeDetailId(waybillDisDtlEntity.getWaybillChargeDetailId());
						// 优惠项目名称
						vo.setFavorableItemName(waybillDisDtlEntity.getPricingEntryName());
						// 优惠项目CODE
						vo.setFavorableItemCode(waybillDisDtlEntity.getPricingEntryCode());
						// 优惠项目名称
						vo.setFavorableTypeName(waybillDisDtlEntity.getTypeName());
						// 优惠项目CODE
						vo.setFavorableTypeCode(waybillDisDtlEntity.getType());
						// 优惠项目名称
						vo.setFavorableSubTypeName(waybillDisDtlEntity.getSubTypeName());
						// 优惠项目CODE
						vo.setFavorableSubTypeCode(waybillDisDtlEntity.getSubType());
						//营销活动
						vo.setActiveCode(waybillDisDtlEntity.getActiveCode());
						vo.setActiveName(waybillDisDtlEntity.getActiveName());
						vo.setActiveStartTime(waybillDisDtlEntity.getActiveStartTime());
						vo.setActiveEndTime(waybillDisDtlEntity.getActiveEndTime());
						vo.setOptionsCrmId(waybillDisDtlEntity.getOptionsCrmId());
						if (waybillDisDtlEntity.getRate() != null) {
							// 优惠折扣率
							vo.setFavorableDiscount(waybillDisDtlEntity.getRate().toString());
						} 
						// 优惠折扣金额
						if (waybillDisDtlEntity.getAmount() != null) {
							// 优惠金额
							vo.setFavorableAmount(waybillDisDtlEntity.getAmount().toString());
						} 
						data.add(vo);
					}
				}

				waybillInfoVo.setWaybillDiscountVos(data);
				//导入时的折扣信息
				waybillInfoVo.setImportWaybillDiscountVos(data);
				waybillInfoVo.setTransferRecordList(transferRecords);
				// 返货记录
				waybillInfoVo.setReturnRecordList(returnRecords);
				// 变更记录
				transportRecords.add(0, finalTransportRecordBeforeChange);
				waybillInfoVo.setTransportRecordList(transportRecords);

				// 运单实体ID
				waybillInfoVo.setId(waybillEntity.getId());
				// 运单号
				waybillInfoVo.setWaybillNo(waybillEntity.getWaybillNo());
				// 订单号
				waybillInfoVo.setOrderNo(waybillEntity.getOrderNo());

				waybillInfoVo.setTransferStartOrgCode(waybillEntity.getTransferStartOrgCode());
				waybillInfoVo.setTransferStartOrgName(waybillEntity.getTransferStartOrgName());
				
				//判断是否是快递的标志
				waybillInfoVo.setIsExpress(waybillEntity.getIsExpress());

				/**
				 * 订单渠道
				 */
				waybillInfoVo.setOrderChannel(waybillEntity.getOrderChannel());

				/**
				 * // 订单付款方式
				 */
				waybillInfoVo.setOrderPayment(waybillEntity.getOrderPaidMethod());

				// 发货客户ID
				waybillInfoVo.setDeliveryCustomerId(waybillEntity.getDeliveryCustomerId());
				//liyongfei 获取发货客户ID，根据客户ID查询客户的详细信息，主要是查询客户是否可以精确代收
				
				//客户编码不为空
				if(waybillEntity.getDeliveryCustomerCode()!=null && !"".equals(waybillEntity.getDeliveryCustomerCode())){
					// 查询客户信息
					String accurateCollection = customerService.queryAccurateCollectionByCustCode(waybillEntity.getDeliveryCustomerCode());
					if(waybillEntity.getCodAmount()!=null && !"".equals(waybillEntity.getCodAmount())){
						if(waybillEntity.getCodAmount().toString().contains(".")){//如果开单时候代收货款带有小数点，即可以精确代收，则更改的时候也可以精确代收
							accurateCollection =FossConstants.YES;
						}
					}
					waybillInfoVo.setAccurateCollection(accurateCollection);
				}else{
					waybillInfoVo.setAccurateCollection(FossConstants.NO);
				}
				
				// 发货客户联系人ID
				waybillInfoVo.setDeliveryCustomerContactId(waybillEntity.getDeliverCustContactId());
				// 发货客户编码
				waybillInfoVo.setDeliveryCustomerCode(waybillEntity.getDeliveryCustomerCode());
				// 大客户标记
				waybillInfoVo.setDeliveryBigCustomer(waybillEntity.getDeliveryBigCustomer());
				// 发货客户名称
				waybillInfoVo.setDeliveryCustomerName(waybillEntity.getDeliveryCustomerName());
				// 发货客户手机
				waybillInfoVo.setDeliveryCustomerMobilephone(waybillEntity.getDeliveryCustomerMobilephone());
				// 发货客户电话
				waybillInfoVo.setDeliveryCustomerPhone(waybillEntity.getDeliveryCustomerPhone());
				// 发货客户联系人
				waybillInfoVo.setDeliveryCustomerContact(waybillEntity.getDeliveryCustomerContact());
				if(exp !=null){
					//内部带货费用承担部门
					waybillInfoVo.setInnerPickupFeeBurdenDept(exp.getInnerPickupFeeBurdenDept());
				}
				// 发货国家
				waybillInfoVo.setDeliveryCustomerNationCode(waybillEntity.getDeliveryCustomerNationCode());
				// 发货省份
				waybillInfoVo.setDeliveryCustomerProvCode(waybillEntity.getDeliveryCustomerProvCode());
				// 发货市
				waybillInfoVo.setDeliveryCustomerCityCode(waybillEntity.getDeliveryCustomerCityCode());
				// 发货区
				waybillInfoVo.setDeliveryCustomerDistCode(waybillEntity.getDeliveryCustomerDistCode());
				// 省市区
				AddressFieldDto deliveryAddress = getProvCityCounty(waybillEntity.getDeliveryCustomerProvCode(), waybillEntity.getDeliveryCustomerCityCode(),
						waybillEntity.getDeliveryCustomerDistCode());
				waybillInfoVo.setDeliveryCustomerAreaDto(deliveryAddress);
				waybillInfoVo.setDeliveryCustomerArea(getAddressText(deliveryAddress));

				// 发货具体地址
				waybillInfoVo.setDeliveryCustomerAddress(waybillEntity.getDeliveryCustomerAddress());

				// 收货客户ID
				waybillInfoVo.setReceiveCustomerId(waybillEntity.getReceiveCustomerId());
				// 收货联系人ID
				waybillInfoVo.setReceiveCustomerContactId(waybillEntity.getReceiverCustContactId());
				// 收货客户编码
				waybillInfoVo.setReceiveCustomerCode(waybillEntity.getReceiveCustomerCode());
				// 大客户标记
				waybillInfoVo.setReceiveBigCustomer(waybillEntity.getReceiveBigCustomer());
				// 收货客户名称
				waybillInfoVo.setReceiveCustomerName(waybillEntity.getReceiveCustomerName());
				// 收货客户手机
				waybillInfoVo.setReceiveCustomerMobilephone(waybillEntity.getReceiveCustomerMobilephone());
				// 收货客户电话
				waybillInfoVo.setReceiveCustomerPhone(waybillEntity.getReceiveCustomerPhone());
				// 收货客户联系人
				waybillInfoVo.setReceiveCustomerContact(waybillEntity.getReceiveCustomerContact());
				// 收货国家
				waybillInfoVo.setReceiveCustomerNationCode(waybillEntity.getReceiveCustomerNationCode());
				// 收货省份
				waybillInfoVo.setReceiveCustomerProvCode(waybillEntity.getReceiveCustomerProvCode());
				// 收货市
				waybillInfoVo.setReceiveCustomerCityCode(waybillEntity.getReceiveCustomerCityCode());
				// 收货区
				waybillInfoVo.setReceiveCustomerDistCode(waybillEntity.getReceiveCustomerDistCode());
				//新增的收货乡镇(街道)
				waybillEntity.setReceiveCustomerVillageCode(wdto.getActualFreightEntity().getReceiveCustomerVillageCode());
				waybillInfoVo.setReceiveCustomerVillageCode(waybillEntity.getReceiveCustomerVillageCode());
				// 省市区
				AddressFieldDto receiveAddress = getProvCityCountyVillage(waybillEntity.getReceiveCustomerProvCode(), waybillEntity.getReceiveCustomerCityCode(),
						waybillEntity.getReceiveCustomerDistCode(),waybillEntity.getReceiveCustomerVillageCode());
				waybillInfoVo.setReceiveCustomerAreaDto(receiveAddress);
				waybillInfoVo.setReceiveCustomerArea(getAddressText(receiveAddress));

				// 收货具体地址
				waybillInfoVo.setReceiveCustomerAddress(waybillEntity.getReceiveCustomerAddress());

				// 收货部门
				waybillInfoVo.setReceiveOrgCode(waybillEntity.getReceiveOrgCode());
				// 货部门省份编码
				if (StringUtil.isNotEmpty(waybillEntity.getReceiveOrgCode())) {
					waybillInfoVo.setReceiveOrgProvCode(CommonUtils.getReceiveOrgProvCode(waybillEntity.getReceiveOrgCode()));
				}
				// 约车编号
				waybillInfoVo.setVehicleNumber(waybillEntity.getOrderVehicleNum());
				// 收货部门名称
				waybillInfoVo.setReceiveOrgName(rfcService.queryDeptNameByCode(waybillEntity.getReceiveOrgCode()));

				waybillInfoVo.setProductCode(productEntityVo);
				// 提货方式
				if (productEntityVo != null) {
					if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productEntityVo.getCode())) {
						waybillInfoVo.setReceiveMethod(dataDictionaryValueEntityToVo(WaybillConstants.PICKUP_GOODS_AIR, waybillEntity.getReceiveMethod()));
					} else {
						waybillInfoVo.setReceiveMethod(dataDictionaryValueEntityToVo(WaybillConstants.PICKUP_GOODS_HIGHWAYS, waybillEntity.getReceiveMethod()));
					}

					// 提货网点
					if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productEntityVo.getCode())
							|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productEntityVo.getCode())) {
						// 偏线空运
						OuterBranchEntity selectedOuterBranchEntity = rfcService.queryExsitAgencyBranchCompanyInfo(waybillEntity.getCustomerPickupOrgCode());
						if (selectedOuterBranchEntity != null) {
							BranchVo branchVO = new BranchVo();
							// 网点编号
							branchVO.setCode(selectedOuterBranchEntity.getAgentDeptCode());
							// 网点名称
							branchVO.setName(selectedOuterBranchEntity.getAgentDeptName());
							// 是否送货上门
							branchVO.setDelivery(selectedOuterBranchEntity.getPickupToDoor());
							// 是否可自提
							branchVO.setPickupSelf(selectedOuterBranchEntity.getPickupSelf());

							// 部门城市code
							branchVO.setCityCode(selectedOuterBranchEntity.getCityCode());
							// 提货网点
							waybillInfoVo.setCustomerPickupOrgCode(branchVO);
							// 提货网点名称
							waybillInfoVo.setCustomerPickupOrgName(branchVO.getName());
							// 设置是否可以开代收货款
							waybillInfoVo.setCanAgentCollected(selectedOuterBranchEntity.getHelpCharge());
							// 设置是否可以货到付款
							waybillInfoVo.setArriveCharge(selectedOuterBranchEntity.getArriveCharge());
						}
					} else {
						// 自有营业部
						SaleDepartmentEntity selectedSaleDepartmentInfo = rfcService.queryExsitSaleDepartmentByCode(waybillEntity.getCustomerPickupOrgCode());
						if (null == selectedSaleDepartmentInfo) {
							OuterBranchExpressEntity entity = rfcService.queryLdpAgencyDeptByCode(waybillEntity.getCustomerPickupOrgCode());
							SaleDepartmentEntity saleDept = new SaleDepartmentEntity();
							// 部门编码
							saleDept.setCode(entity.getAgentDeptCode());
							// 部门名称
							saleDept.setName(entity.getAgentDeptName());
							// 拼音
							saleDept.setPinyin(entity.getPinyin());
							// 可出发
							saleDept.setLeave(entity.getLeave());
							// 可到达
							saleDept.setArrive(entity.getArrive());
							// 可自提
							saleDept.setPickupSelf(entity.getPickupSelf());
							// 可派送
							saleDept.setDelivery(entity.getPickupToDoor());
							// 自提区域描述
							saleDept.setPickupAreaDesc(entity.getPickupArea());
							// 派送区域描述
							saleDept.setDeliveryAreaDesc(entity.getDeliverArea());
							// 是否启用
							saleDept.setActive(entity.getActive());
							// 是否可返回签单
							saleDept.setCanReturnSignBill(entity.getReturnBill());
							// 是否可货到付款
							saleDept.setCanCashOnDelivery(entity.getArriveCharge());
							// 是否可代收货款
							saleDept.setCanAgentCollected(entity.getHelpCharge());
							// 可快递返回签单
							saleDept.setCanExpressReturnSignBill(entity.getReturnBill());
							// 可快递接货
							saleDept.setCanExpressPickupToDoor(entity.getPickupToDoor());
							// 可快递派送
							saleDept.setCanExpressDelivery(entity.getPickupToDoor());
							// 可快递自提
							saleDept.setCanExpressPickupSelf(entity.getPickupSelf());
							// 快递派送区域描述
							saleDept.setExpressDeliveryAreaDesc(entity.getDeliverArea());
							// 快递自提区域描述
							saleDept.setExpressPickupAreaDesc(entity.getPickupArea());
							selectedSaleDepartmentInfo = saleDept;
						}

						if (selectedSaleDepartmentInfo != null) {
							OrgAdministrativeInfoEntity infoEntity = rfcService.queryByCode(waybillEntity.getCustomerPickupOrgCode());
							BranchVo branchVO = new BranchVo();
							// 网点编号
							branchVO.setCode(selectedSaleDepartmentInfo.getCode());
							// 网点名称
							branchVO.setName(selectedSaleDepartmentInfo.getName());
							// 单票重量上限
							branchVO.setSingleBillLimitkg(selectedSaleDepartmentInfo.getSingleBillLimitkg());
							// 单票体积上限
							branchVO.setSingleBillLimitvol(selectedSaleDepartmentInfo.getSingleBillLimitvol());
							// 单件重量上限
							branchVO.setSinglePieceLimitkg(selectedSaleDepartmentInfo.getSinglePieceLimitkg());
							// 单件体积上限
							branchVO.setSinglePieceLimitvol(selectedSaleDepartmentInfo.getSinglePieceLimitvol());
							// 是否送货上门
							branchVO.setDelivery(selectedSaleDepartmentInfo.getDelivery());
							// 是否可自提
							branchVO.setPickupSelf(selectedSaleDepartmentInfo.getPickupSelf());
							// 自由提货网点所属的城市code
							if (infoEntity != null) {
								branchVO.setCityCode(infoEntity.getCityCode());
							}
							// 提货网点
							waybillInfoVo.setCustomerPickupOrgCode(branchVO);
							// 提货网点名称
							waybillInfoVo.setCustomerPickupOrgName(branchVO.getName());
							// 设置是否可以开代收货款
							waybillInfoVo.setCanAgentCollected(selectedSaleDepartmentInfo.getCanAgentCollected());
							// 设置是否可以货到付款
							waybillInfoVo.setArriveCharge(selectedSaleDepartmentInfo.getCanCashOnDelivery());
						}
					}
				}

				if (waybillInfoVo.getCustomerPickupOrgCode() == null) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.msgBox.nullCustomerPickupOrgCode", waybillEntity.getCustomerPickupOrgCode()));
				}

				// 配载类型
				waybillInfoVo.setLoadMethod(waybillEntity.getLoadMethod());
				// 目的站
				waybillInfoVo.setTargetOrgCode(waybillEntity.getTargetOrgCode());
				// 是否上门接货
				waybillInfoVo.setPickupToDoor(FossConstants.YES.equals(waybillEntity.getPickupToDoor()));
				// 司机
				waybillInfoVo.setDriverCode(waybillEntity.getDriverCode());
				// 是否集中接货
				waybillInfoVo.setPickupCentralized(FossConstants.YES.equals(waybillEntity.getPickupCentralized()));
				// 配载线路
				waybillInfoVo.setLoadLineCode(waybillEntity.getLoadLineCode());
				// 配载部门
				waybillInfoVo.setLoadOrgCode(waybillEntity.getLoadOrgCode());
				// 最终配载部门
				waybillInfoVo.setLastLoadOrgCode(waybillEntity.getLastLoadOrgCode());
				// 预计出发时间
				waybillInfoVo.setPreDepartureTime(waybillEntity.getPreDepartureTime());
				// 预计派送/提货时间
				waybillInfoVo.setPreCustomerPickupTime(waybillEntity.getPreCustomerPickupTime());
				// 是否大车直送
				waybillInfoVo.setCarDirectDelivery(FossConstants.YES.equals(waybillEntity.getCarDirectDelivery()));
				// 货物名称
				waybillInfoVo.setGoodsName(waybillEntity.getGoodsName());
				// 货物总件数
				waybillInfoVo.setGoodsQtyTotal(waybillEntity.getGoodsQtyTotal());
				// 货物总重量
				waybillInfoVo.setGoodsWeightTotal(waybillEntity.getGoodsWeightTotal());
				// 货物总体积
				waybillInfoVo.setGoodsVolumeTotal(waybillEntity.getGoodsVolumeTotal());
				// 货物尺寸
				waybillInfoVo.setGoodsSize(waybillEntity.getGoodsSize());

				// 航空货物类型
				if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode())) {
					// 设置空运类型
					setAirGoodType(waybillInfoVo, waybillEntity);

				} else {
					// 普通
					// 货物类型
					waybillInfoVo.setGoodsType(waybillEntity.getGoodsTypeCode());
				}

				// 是否贵重物品
				waybillInfoVo.setPreciousGoods(FossConstants.YES.equals(waybillEntity.getPreciousGoods()));
				// 是否异形物品
				waybillInfoVo.setSpecialShapedGoods(FossConstants.YES.equals(waybillEntity.getSpecialShapedGoods()));
				// 对外备注
				waybillInfoVo.setOuterNotes(dataDictionaryValueEntityToVo(WaybillConstants.OUTER_REMARK, waybillEntity.getOuterNotes()));
				// 对内备注
				waybillInfoVo.setInnerNotes(waybillEntity.getInnerNotes());
				// 储运事项
				if (waybillEntity.getTransportationRemark() == null) {
					waybillInfoVo.setTransportationRemark("");
				} else {
					waybillInfoVo.setTransportationRemark(String.valueOf(waybillEntity.getTransportationRemark()));
				}
				// 货物包装
				waybillInfoVo.setGoodsPackage(waybillEntity.getGoodsPackage());
				// 纸
				waybillInfoVo.setPaper(waybillEntity.getPaperNum());
				// 木
				waybillInfoVo.setWood(waybillEntity.getWoodNum());
				// 纤
				waybillInfoVo.setFibre(waybillEntity.getFibreNum());
				// 托
				waybillInfoVo.setSalver(waybillEntity.getSalverNum());
				// 膜
				waybillInfoVo.setMembrane(waybillEntity.getMembraneNum());
				// 其他
				waybillInfoVo.setOtherPackage(waybillEntity.getOtherPackage());

				// 保价声明价值
				if (waybillEntity.getInsuranceAmount() != null) {
					waybillInfoVo.setInsuranceAmount(waybillEntity.getInsuranceAmount());
				}
				// 保价费率
				// 将保险费率转换成千分率的格式
				BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
				BigDecimal feeRate = waybillEntity.getInsuranceRate();
				feeRate = feeRate.multiply(permillage);
				waybillInfoVo.setInsuranceRate(feeRate);
				// 保价费
				if (waybillEntity.getInsuranceFee() != null) {
					waybillInfoVo.setInsuranceFee(waybillEntity.getInsuranceFee());
				}
				// 代收货款
				if (waybillEntity.getCodAmount() != null) {
					waybillInfoVo.setCodAmount(waybillEntity.getCodAmount());
				}
				// 代收费率
				BigDecimal codRate = waybillEntity.getCodRate();
				codRate = codRate.multiply(permillage);
				waybillInfoVo.setCodRate(codRate);
				// 代收货款手续费
				if (waybillEntity.getCodFee() != null) {
					waybillInfoVo.setCodFee(waybillEntity.getCodFee());
				}

				DataDictionaryValueVo refundType = dataDictionaryValueEntityToVo(WaybillConstants.REFUND_TYPE, waybillEntity.getRefundType());
				/**
				 * 退款类型即日退和三日退可以更改为审核退， 但是审核退不能更改为即日退或三日退， 即日退和三日退之间也不能相互更改，
				 */
				if (refundType == null) {
					setNullRefundType();
				} else if (WaybillConstants.REFUND_TYPE_THREE_DAY.equals(refundType.getValueCode())) {
					// 三日退
					setThreeDayRefundType();
				} else if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(refundType.getValueCode())) {
					// 即日退
					setOneDayRefundType();
				} else if (WaybillConstants.REFUND_TYPE_VALUE.equals(refundType.getValueCode())) {
					// 审核退
					setValueRefundType();
				}

				// 退款类型
				waybillInfoVo.setRefundType(refundType);
				// 返单类别
				waybillInfoVo.setReturnBillType(dataDictionaryValueEntityToVo(WaybillConstants.RETURN_BILL_TYPE, waybillEntity.getReturnBillType()));
				// 预付费保密
				waybillInfoVo.setSecretPrepaid(FossConstants.YES.equals(waybillEntity.getSecretPrepaid()));
				// 到付金额
				if (waybillEntity.getToPayAmount() != null) {
					waybillInfoVo.setToPayAmount(waybillEntity.getToPayAmount());
				}
				// 预付金额
				if (waybillEntity.getPrePayAmount() != null) {
					waybillInfoVo.setPrePayAmount(waybillEntity.getPrePayAmount());
				}
				// 送货费
				if (waybillEntity.getDeliveryGoodsFee() != null) {
					waybillInfoVo.setDeliveryGoodsFee(waybillEntity.getDeliveryGoodsFee());
				}
				// 其他费用
				if (waybillEntity.getOtherFee() != null) {
					waybillInfoVo.setOtherFee(waybillEntity.getOtherFee());
				}
				// 包装手续费
				if (waybillEntity.getPackageFee() != null) {
					waybillInfoVo.setPackageFee(waybillEntity.getPackageFee());
				}
				// 优惠费用
				if (waybillEntity.getPromotionsFee() != null) {
					waybillInfoVo.setPromotionsFee(waybillEntity.getPromotionsFee());
				}
				// 运费计费类型
				waybillInfoVo.setBillingType(dataDictionaryValueEntityToVo(WaybillConstants.BILLING_WAY, waybillEntity.getBillingType()));
				// 运费计费费率
				waybillInfoVo.setUnitPrice(waybillEntity.getUnitPrice());
				// 公布价运费
				if (waybillEntity.getTransportFee() != null) {
					waybillInfoVo.setTransportFee(waybillEntity.getTransportFee());
				}// 增值费用
				if (waybillEntity.getValueAddFee() != null) {
					waybillInfoVo.setValueAddFee(waybillEntity.getValueAddFee());
				}
				// 开单付款方式
				waybillInfoVo.setPaidMethod(dataDictionaryValueEntityToVo(WaybillConstants.PAYMENT_MODE, waybillEntity.getPaidMethod()));
				initCombPaymentMode(waybillInfoVo);
				//优惠活动类型
				String code  =rfcImportDto.getWaybillDto().getActualFreightEntity().getSpecialOffer();
				String deptCode = rfcImportDto.getWaybillDto().getWaybillEntity().getReceiveOrgCode();
				Date date =  rfcImportDto.getWaybillDto().getWaybillEntity().getCreateTime();
				CityMarketPlanEntity cityMarketPlanEntity = rfcService.getCityMarketPlanEntityCode(code,deptCode,date);
				DataDictionaryValueVo specialOffer = new DataDictionaryValueVo();
				if(null!=cityMarketPlanEntity){
					specialOffer.setId(cityMarketPlanEntity.getId());
					specialOffer.setValueCode(cityMarketPlanEntity.getCode());
					specialOffer.setValueName(cityMarketPlanEntity.getName());
				}
				waybillInfoVo.setSpecialOffer(specialOffer);
//				setSpecialOfferModel(specialOffer);
				//收货人地址备注
				if(StringUtils.isNotEmpty(rfcImportDto.getWaybillDto().getActualFreightEntity().getReceiveCustomerAddressNote())){
					waybillInfoVo.setReceiveCustomerAddressNote(rfcImportDto.getWaybillDto().getActualFreightEntity().getReceiveCustomerAddressNote());
				}
				//发货人地址备注
				if(StringUtils.isNotEmpty(rfcImportDto.getWaybillDto().getActualFreightEntity().getDeliveryCustomerAddressNote())){
					waybillInfoVo.setDeliveryCustomerAddressNote(rfcImportDto.getWaybillDto().getActualFreightEntity().getDeliveryCustomerAddressNote());
				}
				// 到达类型
				waybillInfoVo.setArriveType(waybillEntity.getArriveType());
				// 运单状态
				waybillInfoVo.setActive(waybillEntity.getActive());
				// 禁行
				waybillInfoVo.setForbiddenLine(waybillEntity.getForbiddenLine());
				// 合票方式
				waybillInfoVo.setFreightMethod(dataDictionaryValueEntityToVo(WaybillConstants.MAKE_WAYBILL_WAY, waybillEntity.getFreightMethod()));
				// 航班类型
				waybillInfoVo.setFlightNumberType(dataDictionaryValueEntityToVo(WaybillConstants.AIR_FLIGHT_TYPE, waybillEntity.getFlightNumberType()));// 航班类型
				// 航班时间
				waybillInfoVo.setFlightShift(waybillEntity.getFlightShift());
				// 总费用
				if (waybillEntity.getTotalFee() != null) {
					waybillInfoVo.setTotalFee(waybillEntity.getTotalFee());
				}
				// 优惠编码
				waybillInfoVo.setPromotionsCode(waybillEntity.getPromotionsCode());
				// 运单处理状态
				waybillInfoVo.setWaybillstatus(waybillEntity.getPendingType());
				// 开单时间
				waybillInfoVo.setCreateTime(waybillEntity.getCreateTime());
				// 更新时间
				waybillInfoVo.setModifyTime(waybillEntity.getModifyTime());
				// 开单时间
				waybillInfoVo.setBillTime(waybillEntity.getBillTime());
				// 开单人
				waybillInfoVo.setCreateUserCode(waybillEntity.getCreateUserCode());
				// 更新人
				waybillInfoVo.setModifyUserCode(waybillEntity.getModifyUserCode());
				// 开单组织
				waybillInfoVo.setCreateOrgCode(waybillEntity.getCreateOrgCode());
				// 更新组织
				waybillInfoVo.setModifyOrgCode(waybillEntity.getModifyOrgCode());
				// 原运单ID
				waybillInfoVo.setRefId(waybillEntity.getRefId());
				// 原运单号
				waybillInfoVo.setRefCode(waybillEntity.getRefCode());
				// 币种
				waybillInfoVo.setCurrencyCode(waybillEntity.getCurrencyCode());
				// 是否整车运单
				waybillInfoVo.setIsWholeVehicle(FossConstants.YES.equals(waybillEntity.getIsWholeVehicle()));
				// 是否经过营业部
				waybillInfoVo.setIsPassDept(FossConstants.YES.equals(waybillEntity.getIsPassOwnDepartment()));
				// 整车开单报价
				if (waybillEntity.getWholeVehicleActualfee() != null) {
					waybillInfoVo.setWholeVehicleActualfee(waybillEntity.getWholeVehicleActualfee());
				}
				// 整车约车报价
				if (waybillEntity.getWholeVehicleAppfee() != null) {
					waybillInfoVo.setWholeVehicleAppfee(waybillEntity.getWholeVehicleAppfee());
				}
				// 返款帐户开户名称
				waybillInfoVo.setAccountName(waybillEntity.getAccountName());
				// 返款帐户开户账户
				waybillInfoVo.setAccountCode(waybillEntity.getAccountCode());
				// 返款帐户开户银行
				waybillInfoVo.setAccountBank(waybillEntity.getAccountBank());
				// 计费重量
				waybillInfoVo.setBillWeight(waybillEntity.getBillWeight());
				// 接货费
				if (waybillEntity.getPickupFee() != null) {
					waybillInfoVo.setPickupFee(waybillEntity.getPickupFee());
				}
				// 装卸费
				if (waybillEntity.getServiceFee() != null) {
					waybillInfoVo.setServiceFee(waybillEntity.getServiceFee());
				}
				// 预计到达时间
				waybillInfoVo.setPreArriveTime(waybillEntity.getPreArriveTime());
				// 运输类型
				waybillInfoVo.setTransportType(waybillEntity.getTransportType());
				// 打印次数
				waybillInfoVo.setPrintTimes(waybillEntity.getPrintTimes());
				// 新增时间
				waybillInfoVo.setAddTime(waybillEntity.getAddTime());
				// 公里数
				waybillInfoVo.setKilometer(waybillEntity.getKilometer());
				//初始化市场营销活动
				initActiveInfo(waybillInfoVo);
				if (woodenRequirementsEntity != null) {
					// 代打木架部门
					waybillInfoVo.setPackageOrgCode(woodenRequirementsEntity.getPackageOrgCode());
					// 打木架货物件数
					waybillInfoVo.setStandGoodsNum(woodenRequirementsEntity.getStandGoodsNum());
					// 代打木架要求
					waybillInfoVo.setStandRequirement(woodenRequirementsEntity.getStandRequirement());
					// 打木架货物尺寸
					waybillInfoVo.setStandGoodsSize(woodenRequirementsEntity.getStandGoodsSize());
					// 打木架货物体积
					waybillInfoVo.setStandGoodsVolume(woodenRequirementsEntity.getStandGoodsVolume());
					// 打木箱货物件数
					waybillInfoVo.setBoxGoodsNum(woodenRequirementsEntity.getBoxGoodsNum());
					// 代打木箱要求
					waybillInfoVo.setBoxRequirement(woodenRequirementsEntity.getBoxRequirement());
					// 打木箱货物尺寸
					waybillInfoVo.setBoxGoodsSize(woodenRequirementsEntity.getBoxGoodsSize());
					// 打木箱货物体积
					waybillInfoVo.setBoxGoodsVolume(woodenRequirementsEntity.getBoxGoodsVolume());
					// 获取代打木架费用
					getYokeCharge(waybillInfoVo);
				} else {
					// 打木架费用
					waybillInfoVo.setStandCharge(BigDecimal.ZERO);
					// 打木箱费用
					waybillInfoVo.setBoxCharge(BigDecimal.ZERO);
				}

				// 查询走货路径
				OrgInfoDto dto = null;
				try {

					if (waybillInfoVo.getPickupCentralized() != null && waybillInfoVo.getPickupCentralized()) {
						dto = rfcService.queryLodeDepartmentInfo(waybillInfoVo.getPickupCentralized(), waybillInfoVo.getCreateOrgCode(), waybillInfoVo.getCustomerPickupOrgCode()
								.getCode(), waybillInfoVo.getProductCode().getCode());
					} else {
						dto = rfcService.queryLodeDepartmentInfo(waybillInfoVo.getPickupCentralized(), waybillInfoVo.getReceiveOrgCode(), waybillInfoVo.getCustomerPickupOrgCode()
								.getCode(), waybillInfoVo.getProductCode().getCode());
					}
					waybillInfoVo.setOrgInfoDto(dto);
				} catch (BusinessException e) {
					LOGGER.error("WaybillValidateException", e);
				}

				// 整车不允许修改木架
				if (waybillInfoVo.getIsWholeVehicle() != null && waybillInfoVo.getIsWholeVehicle()) {
					// 不能打木架
					waybillInfoVo.setDoPacking(FossConstants.NO);
				} else if (waybillInfoVo.getStockStatus() != null && waybillInfoVo.getCustomerPickupOrgCode() != null && waybillInfoVo.getProductCode() != null) {
					if (dto == null || dto.getFreightRoute() == null) {
						// 不能打木架
						waybillInfoVo.setDoPacking(FossConstants.NO);
//						waybillInfoVo.setGoodsTypeIsAB(false);// 是否AB货
					} else {
						if (waybillInfoVo.getPackageOrgCode() == null) {
							waybillInfoVo.setPackageOrgCode(dto.getFreightRoute().getPackingOrganizationCode());// 代打木架部门编码
							waybillInfoVo.setPackingOrganizationName(dto.getFreightRoute().getPackingOrganizationName());// 代打木架部门名称
						} else {
							waybillInfoVo.setPackingOrganizationName(rfcService.queryDeptNameByCode(waybillInfoVo.getPackageOrgCode()));
						}
						waybillInfoVo.setDoPacking(dto.getFreightRoute().getDoPacking());// 是否可以打木架
						waybillInfoVo.setLastOutLoadOrgCode(dto.getLastOutLoadOrgCode());// 最终配置外场
//						waybillInfoVo.setGoodsTypeIsAB(dto.getGoodsTypeIsAB());// 是否AB货
					}
				}
				// 附加增值服务费
				List<WaybillOtherChargeDto> chargeDtlEntities = rfcImportDto.getOtherChargeDtos();
				List<OtherChargeVo> otherChargeVos = new ArrayList<OtherChargeVo>();
				if (chargeDtlEntities != null) {
					OtherChargeVo vo = null;
					for (WaybillOtherChargeDto entity : chargeDtlEntities) {
						vo = new OtherChargeVo();
						vo.setCode(entity.getCode());
						vo.setId(entity.getId());
						vo.setMoney(entity.getAmount());
						vo.setOldMoney(entity.getAmount());
						vo.setChargeName(entity.getChargeName());
						vo.setDescrition(entity.getDescrition());
						vo.setIsDelete(FossConstants.YES.equals(entity.getIsDelete()));
						vo.setIsUpdate(FossConstants.YES.equals(entity.getIsUpdate()));
						vo.setLowerLimit(entity.getLowerLimit());
						vo.setType(entity.getType());
						vo.setUpperLimit(entity.getUpperLimit());
						/**
						 * 是否初始化值，为了判断，多次转运和多次返货时，需要对转货费和转运费进行判断，
						 * 是否是导入时的值，如果是，需要重新在界面表格里面重新加入一个值,如果不是，重新改值
						 */
						vo.setIsInit(Boolean.TRUE);
						// 内部更改修改走货路径，触发中转费的修改，便于定位修改了哪条记录
						vo.setIsEdit(Boolean.FALSE);
						otherChargeVos.add(vo);
					}
				}
				// 其他费用明细
				waybillInfoVo.setOtherChargeVos(otherChargeVos);
				// 变更前流水号
				waybillInfoVo.setLabeledGoodEntities(rfcImportDto.getLabeledGoodEntities());

				// 更改单修改件数和打木架数量修改详细信息冗余保存对象
				waybillInfoVo.setLabeledGoodChangeHistoryDtoList(rfcImportDto.getLabeledGoodChangeHistoryDtoList());

				// 需要打木架的流水号
				waybillInfoVo.setSelectedLabeledGoodEntities(getSelectedLabeledGoodEntities(waybillInfoVo));
				// 转运起始网点
				waybillInfoVo.setTfrStartOrgCode(getTfrStartOrgCode(waybillInfoVo));
				// 返货起始网点
				waybillInfoVo.setRtnStartOrgCode(getRtnStartOrgCode(waybillInfoVo));

				// 是否月结
				waybillInfoVo.setChargeMode(isChargeMode(waybillInfoVo.getDeliveryCustomerCode()));
				CusBargainEntity cusBargainEntity = getCusBargainEntity(waybillInfoVo.getDeliveryCustomerCode());
				if (cusBargainEntity != null) {
					waybillInfoVo.setPreferentialType(cusBargainEntity.getPreferentialType());// 获取优惠类型
				}

				// 设置客户其他属性
				setCustomerQueryConditionValue(waybillInfoVo);

				// 长短途
				EffectiveDto effectiveDto = rfcService.searchPreSelfPickupTime(waybillInfoVo.getReceiveOrgCode(), waybillInfoVo.getLastLoadOrgCode(), waybillInfoVo.getProductCode()
						.getCode(), waybillInfoVo.getPreDepartureTime(), waybillInfoVo.getBillTime());
				if (effectiveDto != null) {
					waybillInfoVo.setLongOrShort(effectiveDto.getLongOrShort());
				} else {
					// waybillInfoVo.setLongOrShort(PricingConstants.LONG_OR_SHORT_L);
				}

				// 整车
				if (waybillInfoVo.getIsWholeVehicle()) {
					UserEntity user = (UserEntity) SessionContext.getCurrentUser();

					// 调用接口查询整车价格波动范围
					ConfigurationParamsEntity param = baseDataService.queryByConfCode(WaybillConstants.WHOLEVEHICLE_FEE_RANGE_UP, user.getEmployee().getDepartment().getCode());

					if (param != null) {
						waybillInfoVo.setWholeVehicleActualfeeFlowRangeUp(new BigDecimal(param.getConfValue()));
					}

					// 调用接口查询整车价格波动范围
					ConfigurationParamsEntity param2 = baseDataService.queryByConfCode(WaybillConstants.WHOLEVEHICLE_FEE_RANGE_LOW, user.getEmployee().getDepartment().getCode());

					if (param2 != null) {
						waybillInfoVo.setWholeVehicleActualfeeFlowRangeLow(new BigDecimal(param2.getConfValue()));
					}
				}

				/*
				 * if(waybillEntity .getDeliveryCustomerId()!=null){
				 * CustomerQueryConditionDto condition = new
				 * CustomerQueryConditionDto(); condition.setCustId(waybillEntity
				 * .getDeliveryCustomerId()); List<CustomerQueryConditionDto>
				 * dtoList = customerService.queryCustomerByCondition(condition);
				 * if(dtoList ==null || dtoList.size()==0){
				 * waybillInfoVo.setChargeMode(false);// 是否月结 }else{
				 * CustomerQueryConditionDto dtoCust = dtoList.get(0);
				 * 
				 * waybillInfoVo.setChargeMode(DictionaryValueConstants.
				 * CLEARING_TYPE_MONTH.equals(dtoCust.getChargeType()) ?
				 * Boolean.valueOf(true) : Boolean.valueOf(false)); } }else{
				 * 
				 * waybillInfoVo.setChargeMode(false);// 是否月结 }
				 */

				waybillInfoVo.setCalculateDeliveryGoodsFee(BigDecimal.ZERO);// 计算后的送货费

				// 根据部门是否开装卸费设置编辑状态
				if (departPropertyServiceFee(waybillInfoVo)) {
					// 设置该部门不允许修改装卸费
					waybillInfoVo.setDepartServiceFeeEnable(true);
				} else {
					waybillInfoVo.setDepartServiceFeeEnable(false);
				}
				// 清空调整费用
				waybillInfoVo.setAdjustFee(BigDecimal.ZERO);
			} catch (IllegalAccessException e) {
				LOGGER.error("exception", e);
			} catch (InvocationTargetException e) {
				LOGGER.error("exception", e);
			} catch (NoSuchMethodException e) {
				LOGGER.error("exception", e);
			}
			return waybillInfoVo;
		}
		
		/**
		 * 初始化市场营销活动信息
		 * @创建时间 2014-4-22 下午8:52:22   
		 * @创建人： WangQianJin
		 */
		private void initActiveInfo(WaybillInfoVo waybillInfoVo){
			//获取当前登陆部门code
			UserEntity user = (UserEntity)SessionContext.getCurrentUser();
			String currentOrgCode = user.getEmployee().getDepartment().getCode();
			//获取运单营销活动
			DataDictionaryValueVo activeInfo=new DataDictionaryValueVo();
			WaybillDisDtlEntity entity=rfcService.queryActiveInfoByNoAndType(waybillInfoVo.getWaybillNo());
			if(entity!=null){
				activeInfo.setValueCode(entity.getActiveCode());
				activeInfo.setValueName(entity.getActiveName());			
			}else{			
				activeInfo.setValueName("");
			}
			//设置市场营销活动
			waybillInfoVo.setActiveInfo(activeInfo);
			//获取开单时的营销活动列表
			CrmActiveParamVo param=new CrmActiveParamVo();
			param.setCurrentDate(waybillInfoVo.getBillTime());
			param.setActivityCategory(DictionaryValueConstants.BUSINESS_EXPRESS);
			param.setActive(FossConstants.ACTIVE);
			param.setCurrentOrgCode(currentOrgCode);
			CrmActiveInfoDto crmDto=rfcService.getActiveInfoList(param);
			if(crmDto!=null && crmDto.getActiveList()!=null && crmDto.getActiveList().size()>0){
				DefaultComboBoxModel activeInfoModel = waybillRFCUI.getWaybillInfoPanel().getIncrementPanel().getActiveInfoModel();
				if(activeInfoModel!=null){
					activeInfoModel.removeAllElements();
				}else{
					activeInfoModel=new DefaultComboBoxModel();
				}
				//添加空活动
				DataDictionaryValueVo nullVo = new DataDictionaryValueVo();
				nullVo.setValueName("");
				activeInfoModel.addElement(nullVo);
				//添加开单选择的活动
				if(activeInfo.getValueCode()!=null){
					activeInfoModel.addElement(activeInfo);
				}
				List<MarkActivitiesQueryConditionDto> activeList=crmDto.getActiveList();	
				for (MarkActivitiesQueryConditionDto active : activeList) {
					if(active!=null && active.getCode()!=null && !active.getCode().equals(activeInfo.getValueCode())){
						DataDictionaryValueVo vo=new DataDictionaryValueVo();					
						vo.setValueCode(active.getCode());
						vo.setValueName(active.getName());
						activeInfoModel.addElement(vo);					
					}			
				}
				//选中营销活动
				activeInfoModel.setSelectedItem(waybillInfoVo.getActiveInfo());
			}		
			
		}

		/**
		 * 
		 * 数据字典Entity转换为VO
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-25 下午12:18:48
		 */
		private DataDictionaryValueVo entityToVo(
				DataDictionaryValueEntity dataDictionary) {
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			try {
				//属性拷贝
				BeanUtils.copyProperties(vo, dataDictionary);
			} catch (IllegalAccessException e) {
				LOGGER.error(e.getMessage());
			} catch (InvocationTargetException e) {
				LOGGER.error(e.getMessage());
			}
			return vo;
		}

		/**
		 * 
		 * 开单付款方式
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2012-10-16 下午06:27:17
		 * @see
		 */
	private void initCombPaymentMode(WaybillInfoVo waybillInfoVo) {
		List<DataDictionaryValueEntity> list = rfcService.queryPaymentMode();
		// 下拉框模型
		JComboBox comBox = waybillRFCUI.billingPayPanel.getCombPaymentMode();
		DefaultComboBoxModel combPaymentModeModel = waybillRFCUI
				.getWaybillInfoPanel().getBillingPayPanel()
				.getPaymentModeModel();
		combPaymentModeModel.removeAllElements();
		for (DataDictionaryValueEntity dataDictionary : list) {
			if (!WaybillConstants.TELEGRAPHIC_TRANSFER.equals(dataDictionary
					.getValueCode())
					&& !WaybillConstants.CHECK.equals(dataDictionary
							.getValueCode())
					// 小件新加代码
					&& !WaybillConstants.TEMPORARY_DEBT.equals(dataDictionary
							.getValueCode())) {
				/**
				 * 付款方式限制为 现金可更改为： 月结（需判断是否月结客户）和到付； 月结可更改为：现金和到付； 银行卡和网上支付可更改为
				 * ：现金； 到付可更改为： 现金和月结（需判断是否月结客户）；
				 * 
				 */
				// Entity转换为VO
				DataDictionaryValueVo vo = entityToVo(dataDictionary);

				if (WaybillConstants.CASH_PAYMENT.equals(waybillInfoVo
						.getPaidMethod().getValueCode())) {
					if (WaybillConstants.ARRIVE_PAYMENT.equals(dataDictionary
							.getValueCode())
							|| WaybillConstants.MONTH_PAYMENT
									.equals(dataDictionary.getValueCode())
							|| WaybillConstants.CASH_PAYMENT
									.equals(dataDictionary.getValueCode())) {
						
						combPaymentModeModel.addElement(vo);
					} else {
						combPaymentModeModel.removeElement(vo);
					}

				} else if (WaybillConstants.MONTH_PAYMENT.equals(waybillInfoVo
						.getPaidMethod().getValueCode())) {

					if (WaybillConstants.ARRIVE_PAYMENT.equals(dataDictionary
							.getValueCode())
							|| WaybillConstants.CASH_PAYMENT
									.equals(dataDictionary.getValueCode())
							|| WaybillConstants.MONTH_PAYMENT
									.equals(dataDictionary.getValueCode())) {
						combPaymentModeModel.addElement(vo);
					} else {
						combPaymentModeModel.removeElement(vo);
					}

				} else if (WaybillConstants.CREDIT_CARD_PAYMENT
						.equals(waybillInfoVo.getPaidMethod().getValueCode())) {

					if (WaybillConstants.CASH_PAYMENT.equals(dataDictionary
							.getValueCode())
							|| WaybillConstants.CREDIT_CARD_PAYMENT
									.equals(dataDictionary.getValueCode())
							|| WaybillConstants.ARRIVE_PAYMENT
									.equals(dataDictionary.getValueCode())) {
						combPaymentModeModel.addElement(vo);
					} else {
						combPaymentModeModel.removeElement(vo);
					}
				} else if (WaybillConstants.ONLINE_PAYMENT.equals(waybillInfoVo
						.getPaidMethod().getValueCode())) {

					if (WaybillConstants.CASH_PAYMENT.equals(dataDictionary
							.getValueCode())
							|| WaybillConstants.ONLINE_PAYMENT
									.equals(dataDictionary.getValueCode())
							|| WaybillConstants.ARRIVE_PAYMENT
									.equals(dataDictionary.getValueCode())) {
						combPaymentModeModel.addElement(vo);
					} else {
						combPaymentModeModel.removeElement(vo);
					}
				}else if (WaybillConstants.ARRIVE_PAYMENT.equals(waybillInfoVo
						.getPaidMethod().getValueCode())) {

					if (WaybillConstants.CASH_PAYMENT.equals(dataDictionary
							.getValueCode())
							|| WaybillConstants.MONTH_PAYMENT
									.equals(dataDictionary.getValueCode())
				            || WaybillConstants.ARRIVE_PAYMENT.equals(dataDictionary
							.getValueCode())) {
						combPaymentModeModel.addElement(vo);
					} else {
						combPaymentModeModel.removeElement(vo);
					}

				}

			}
		}
	}
		
		
		
		/**
		 * 设置客户其他属性
		 * 
		 * @param waybillInfoVo
		 */
		private void setCustomerQueryConditionValue(WaybillInfoVo waybillInfoVo) {
			String deliveryCustomerCode = waybillInfoVo.getDeliveryCustomerCode();

			if (StringUtil.isNotEmpty(deliveryCustomerCode)) {
				// 查询客户信息
				CusBargainEntity cusBargainEntity = rfcService.queryCusBargainByCustCode(deliveryCustomerCode);

				if (cusBargainEntity != null) {
					// 合同编号
					waybillInfoVo.setAuditNo(cusBargainEntity.getBargainCode());
				}
			}

		}

		/**
		 * 
		 * 退款类型为三日退
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2013-3-26 下午4:46:54
		 */
		private void setThreeDayRefundType() {
			// 查询退款类型
			List<DataDictionaryValueEntity> list = rfcService.queryRefundType();
			// 下拉框模型
			DefaultComboBoxModel combRefundTypeModel = waybillRFCUI.getWaybillInfoPanel().getIncrementPanel().getRefundTypeModel();
			combRefundTypeModel.removeAllElements();
			combRefundTypeModel.addElement(null);
			for (DataDictionaryValueEntity dataDictionary : list) {
				// 即日退和三日退可以更改为审核退，即日退和三日退之间也不能相互更改
				if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(dataDictionary.getValueCode())) {
					continue;
				}
				// Entity转换为VO
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				// 填充至 数据模型
				combRefundTypeModel.addElement(vo);
			}
		}

		/**
		 * 
		 * 退款类型为即日退
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2013-3-26 下午4:46:54
		 */
		private void setOneDayRefundType() {
			// 查询退款类型
			List<DataDictionaryValueEntity> list = rfcService.queryRefundType();
			// 下拉框模型
			DefaultComboBoxModel combRefundTypeModel = waybillRFCUI.getWaybillInfoPanel().getIncrementPanel().getRefundTypeModel();
			combRefundTypeModel.removeAllElements();
			combRefundTypeModel.addElement(null);
			for (DataDictionaryValueEntity dataDictionary : list) {
				// 即日退和三日退可以更改为审核退，即日退和三日退之间也不能相互更改
				if (WaybillConstants.REFUND_TYPE_THREE_DAY.equals(dataDictionary.getValueCode())) {
					continue;
				}
				// Entity转换为VO
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				// 填充至 数据模型
				combRefundTypeModel.addElement(vo);
			}
		}

		/**
		 * 
		 * 退款类型为审核退
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2013-3-26 下午4:46:54
		 */
		private void setValueRefundType() {
			// 查询退款类型
			List<DataDictionaryValueEntity> list = rfcService.queryRefundType();
			// 下拉框模型
			DefaultComboBoxModel combRefundTypeModel = waybillRFCUI.getWaybillInfoPanel().getIncrementPanel().getRefundTypeModel();
			combRefundTypeModel.removeAllElements();
			combRefundTypeModel.addElement(null);
			for (DataDictionaryValueEntity dataDictionary : list) {
				// 审核退不能更改为即日退或三日退
				if (WaybillConstants.REFUND_TYPE_THREE_DAY.equals(dataDictionary.getValueCode()) || WaybillConstants.REFUND_TYPE_ONE_DAY.equals(dataDictionary.getValueCode())) {
					continue;
				}
				// Entity转换为VO
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				// 填充至 数据模型
				combRefundTypeModel.addElement(vo);
			}
		}

		
		
		
		
		/**
		 * 
		 * 退款类型为审核退
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2013-3-26 下午4:46:54
		 */
//		private void setSpecialOfferModel(DataDictionaryValueVo vo ) {
//			// 下拉框模型
//			DefaultComboBoxModel combRefundTypeModel = waybillRFCUI.getWaybillInfoPanel().getBasicPanel().getCboSpecialOfferModel();
//			combRefundTypeModel.removeAllElements();
//			combRefundTypeModel.setSelectedItem(vo);
//			combRefundTypeModel.addElement(vo);
//		}

		
		
		
		
		
		
		
		
		
		
		/**
		 * 
		 * 退款类型为空
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2013-3-26 下午4:46:54
		 */
		private void setNullRefundType() {
			// 查询退款类型
			List<DataDictionaryValueEntity> list = rfcService.queryRefundType();
			// 下拉框模型
			DefaultComboBoxModel combRefundTypeModel = waybillRFCUI.getWaybillInfoPanel().getIncrementPanel().getRefundTypeModel();
			combRefundTypeModel.removeAllElements();
			combRefundTypeModel.addElement(null);
			for (DataDictionaryValueEntity dataDictionary : list) {
				// Entity转换为VO
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				// 填充至 数据模型
				combRefundTypeModel.addElement(vo);
			}
		}

		
		
			/**
		 * 
		 * 退款类型为空
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2013-3-26 下午4:46:54
		 */
		private void setSpecialOffer() {
			// 查询退款类型
			List<DataDictionaryValueEntity> list = rfcService.queryRefundType();
			// 下拉框模型
			DefaultComboBoxModel combRefundTypeModel = waybillRFCUI.getWaybillInfoPanel().getIncrementPanel().getRefundTypeModel();
			combRefundTypeModel.removeAllElements();
			combRefundTypeModel.addElement(null);
			for (DataDictionaryValueEntity dataDictionary : list) {
				// Entity转换为VO
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				// 填充至 数据模型
				combRefundTypeModel.addElement(vo);
			}
		}
		 
		
		
		
		
		
		/**
		 * 
		 * 设置空运类型
		 * 
		 * @param waybillInfoVo
		 * @param waybillEntity
		 */
		private void setAirGoodType(WaybillInfoVo waybillInfoVo, WaybillEntity waybillEntity) {
			List<GoodsTypeEntity> list = rfcService.findGoodsTypeByCondiction();
			for (GoodsTypeEntity goodsType : list) {
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(goodsType, vo);

				if (vo.getValueCode().equals(waybillEntity.getGoodsTypeCode())) {
					waybillInfoVo.setAirGoodsType(vo);
				}
			}
		}

		/**
		 * 否可以开装卸费的依据取决于部门的业务属性（即部门属性基础资料中增加是否可开装卸费的字段）
		 * 
		 * @param bean
		 * @return
		 */
		private boolean departPropertyServiceFee(WaybillInfoVo bean) {

			// 收货部门
			String orgCode = bean.getReceiveOrgCode();

			// org code is not null;
			if (StringUtils.isNotEmpty(orgCode)) {
				// 根据编码查询
				SaleDepartmentEntity entity = rfcService.querySaleDeptByCode(orgCode);
				if (entity != null) {
					// 不允许开装卸费
					if (!FossConstants.YES.equals(entity.getCanPayServiceFee())) {
						return false;
					}
				}
			}
			return true;
		}

		/**
		 * 
		 * 根据客户编码判断是否月结客户
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2013-1-5 下午5:05:13
		 */
		private boolean isChargeMode(String deliveryCustomerCode) {
			if (deliveryCustomerCode == null) {
				return false;
			}
			// 根据客户编码查询客户
			CustomerDto customerDto = rfcService.queryCustInfoByCode(deliveryCustomerCode);
			if (customerDto == null) {
				return false;
			}
			// 客户合同
			List<CusBargainEntity> cusBargainEntities = customerDto.getBargainList();
			if(CollectionUtils.isNotEmpty(cusBargainEntities)){
				for (CusBargainEntity cusBargainEntity : cusBargainEntities) {
					if (DictionaryValueConstants.CLEARING_TYPE_MONTH.equals(cusBargainEntity.getChargeType())) {
						return true;
					}
				}
			}		
			return false;
		}

		/**
		 * 根据客户编码查询获得客户类型
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2013-5-21 下午3:22:31
		 */
		public CusBargainEntity getCusBargainEntity(String custCode) {
			if (StringUtils.isNotEmpty(custCode)) {
				// 获得合同信息
				CusBargainEntity cusBargain = rfcService.queryCusBargainByCustCode(custCode);
				return cusBargain;
			} else {
				return null;
			}
		}

		/**
		 * 
		 * 获取返货起始网点
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-25 上午10:35:36
		 */
		private String getRtnStartOrgCode(WaybillInfoVo bean) {
			// 货物状态
			DataDictionaryValueVo goodsStatus = bean.getGoodsStatus();
			if (goodsStatus == null) {
				return null;
			}
			// 返货起始网点默认为原目的站
			String rtnStartOrgCode = bean.getCustomerPickupOrgCode() == null ? null : bean.getCustomerPickupOrgCode().getCode();
			rtnStartOrgCode = rfcService.queryStationDeliverOrgCode(rtnStartOrgCode);

			String inventory = goodsStatus.getValueCode();
			// 未出第一外场返货从第一外场开始
			if (WaybillRfcConstants.RECEIVE_STOCK.equals(inventory) || WaybillRfcConstants.RECEIVE_STOCK_OUT.equals(inventory)
					|| (WaybillRfcConstants.TRANSFER_STOCK.equals(inventory) && bean.getLoadOrgCode().equals(bean.getStockStatus().getCurrentStockOrgCode()))) {
				rtnStartOrgCode = rfcService.queryStationDeliverOrgCode(bean.getLoadOrgCode());
			}

			// 原运输性质
			String procuctCode = bean.getProductCode().getCode();

			// 已出最终配载且是空运、偏线运输性质
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(procuctCode) || ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(procuctCode)) {
				rtnStartOrgCode = rfcService.queryStationDeliverOrgCode(bean.getLastLoadOrgCode());
				if (WaybillRfcConstants.DELIVERY_STOCK_OUT.equals(inventory)) {
					// 若货物已出原最终配载部门库存，转运运输性质只能为偏线或空运
					bean.setRtnNeedFilter(true);
				}
			}

			// rtnStartOrgCode = bean.getCreateOrgCode();

			return rtnStartOrgCode;
		}

		/**
		 * 
		 * 获取转运起始网点
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-25 上午10:35:59
		 */
		private String getTfrStartOrgCode(WaybillInfoVo bean) {
			// 货物状态
			DataDictionaryValueVo goodsStatus = bean.getGoodsStatus();
			if (goodsStatus == null) {
				return null;
			}

			String tfrStartOrgCode = rfcService.queryStationDeliverOrgCode(bean.getLastLoadOrgCode());

			String inventory = goodsStatus.getValueCode();
			// 原运输性质
			String procuctCode = bean.getProductCode().getCode();

			// 已出最终配载且是空运、偏线运输性质
			if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(procuctCode) || ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(procuctCode)) {

				if (WaybillRfcConstants.DELIVERY_STOCK_OUT.equals(inventory)) {
					// 若货物已出原最终配载部门库存，转运运输性质只能为偏线或空运
					bean.setTfrNeedFilter(true);
				}
			}
			// tfrStartOrgCode = bean.getCreateOrgCode();
			return tfrStartOrgCode;
		}

		/**
		 * 
		 * 获取选中的流水号
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-19 下午2:00:09
		 */
		private List<LabeledGoodChangeHistoryDto> getSelectedLabeledGoodEntities(WaybillInfoVo bean) {
			// 代打包装总数量
			int count = 0;
			if (bean.getStandGoodsNum() != null) {
				// 打木架件数
				count += bean.getStandGoodsNum();
			}
			if (bean.getBoxGoodsNum() != null) {
				// 打木箱件数
				count += bean.getBoxGoodsNum();
			}
			List<LabeledGoodChangeHistoryDto> entitys = bean.getLabeledGoodChangeHistoryDtoList();
			// 如果打木架件数与木箱件数之和大于
			if (count > entitys.size()) {
				count = entitys.size();
			}
			// 依据流水号从小到大挑选代打包装流水号
			for (int i = 0; i < entitys.size(); i++) {

				LabeledGoodChangeHistoryDto dto = entitys.get(i);

				if (Integer.valueOf(1).toString().equals(dto.getChangeTimes())) {
					if (i < count) {
						dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD);
					}
				} else {

					// 上一次的更改结果
					if (LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD.equals(dto.getChangeType())
							|| LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD_NEW.equals(dto.getChangeType())) {
						dto.setChangeType(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_WOODEN_ADD);

					}
				}

			}
			return entitys;
		}

		/**
		 * 
		 * 省市区
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-4 上午8:54:29
		 */
		private AddressFieldDto getProvCityCounty(String provCode, String cityCode, String distCode) {
			AddressFieldDto addressFieldDto = new AddressFieldDto();
			if (StringUtil.isNotEmpty(provCode)) {
				// 填充省份名称
				addressFieldDto.setProvinceId(provCode);
				String province = rfcService.queryProvinceByCode(provCode);
				if (StringUtil.isNotEmpty(province)) {
					addressFieldDto.setProvinceName(province);
				}
			}
			if (StringUtil.isNotEmpty(cityCode)) {
				// 填充城市名称
				addressFieldDto.setCityId(cityCode);
				String city = rfcService.queryCityByCode(cityCode);
				if (StringUtil.isNotEmpty(city)) {
					addressFieldDto.setCityName(city);
				}
			}
			if (StringUtil.isNotEmpty(distCode)) {
				// 填充区域名称
				addressFieldDto.setCountyId(distCode);
				String county = rfcService.queryCountyByCode(distCode);
				if (StringUtil.isNotEmpty(county)) {
					addressFieldDto.setCountyName(county);
				}
			}
			return addressFieldDto;
		}

		/**
		 * 省市区乡镇文本
		 * @author 218459-foss-dongsiwei
		 */
		private AddressFieldDto getProvCityCountyVillage(String provCode, String cityCode, String distCode,String villageCode) {
			AddressFieldDto addressFieldDto = new AddressFieldDto();
			if (StringUtil.isNotEmpty(provCode)) {
				// 填充省份名称
				addressFieldDto.setProvinceId(provCode);
				String province = rfcService.queryProvinceByCode(provCode);
				if (StringUtil.isNotEmpty(province)) {
					addressFieldDto.setProvinceName(province);
				}
			}
			if (StringUtil.isNotEmpty(cityCode)) {
				// 填充城市名称
				addressFieldDto.setCityId(cityCode);
				String city = rfcService.queryCityByCode(cityCode);
				if (StringUtil.isNotEmpty(city)) {
					addressFieldDto.setCityName(city);
				}
			}
			if (StringUtil.isNotEmpty(distCode)) {
				// 填充区域名称
				addressFieldDto.setCountyId(distCode);
				String county = rfcService.queryCountyByCode(distCode);
				if (StringUtil.isNotEmpty(county)) {
					addressFieldDto.setCountyName(county);
				}
			}
			if(StringUtils.isNotEmpty(villageCode)){
				//填充乡镇(街道)名称
				addressFieldDto.setVillageTownId(villageCode);
				String village=rfcService.queryCountyByCode(villageCode);
				if(StringUtils.isNotEmpty(village)){
					addressFieldDto.setVillageTownName(village);
				}
			}
			return addressFieldDto;
		}
		
		/**
		 * 
		 * 省市区文本
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-23 上午10:19:50
		 */
		private String getAddressText(AddressFieldDto addressFieldDto) {
			if (addressFieldDto == null) {
				return "";
			}
			StringBuffer sb = new StringBuffer();
			if (StringUtil.isNotEmpty(addressFieldDto.getProvinceName())) {
				sb.append(addressFieldDto.getProvinceName());
				sb.append("-");
			}
			if (StringUtil.isNotEmpty(addressFieldDto.getCityName())) {
				sb.append(addressFieldDto.getCityName());
				sb.append("-");
			}
			if (StringUtil.isNotEmpty(addressFieldDto.getCountyName())) {
				sb.append(addressFieldDto.getCountyName());
				sb.append("-");
			}
			if (StringUtil.isNotEmpty(addressFieldDto.getVillageTownName())) {
				sb.append(addressFieldDto.getVillageTownName());
				sb.append("-");
			}		
			if (sb.length() > 0) {
				return sb.substring(0, sb.length() - 1);
			} else {
				return sb.toString();
			}
		}

		/**
		 * 数据字典Entity转换VO
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2012-11-6 上午11:22:15
		 */
		private DataDictionaryValueVo dataDictionaryValueEntityToVo(String termsCode, String valueCode) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
			if (StringUtil.isNotEmpty(valueCode)) {
				DataDictionaryValueEntity entity = rfcService.queryDataDictoryValueEntity(termsCode, valueCode);
				if (entity == null) {
					return null;
				}
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				PropertyUtils.copyProperties(vo, entity);
				return vo;
			} else {
				return null;
			}
		}

		/**
		 * 数据字典Entity转换VO
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2012-11-6 上午11:22:15
		 */
		private ProductEntityVo getProductTypeByCode(String productCode, Date billDate) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
			if (StringUtil.isNotEmpty(productCode)) {
				ProductEntity entity = rfcService.queryTransTypeByCode(productCode, billDate);
				if (entity == null) {
					return null;
				}
				ProductEntityVo vo = new ProductEntityVo();
				PropertyUtils.copyProperties(vo, entity);
				return vo;
			} else {
				return null;
			}
		}

		/**
		 * 
		 * 获取代打木架费用
		 * 
		 * @author 025000-FOSS-helong
		 * @date 2012-11-29 下午05:21:48
		 */
		private void getYokeCharge(WaybillInfoVo bean) {
			// 获取木架费用
			getStandCharge(bean);
			// 获取木箱费用
			getBoxCharge(bean);

			BigDecimal calculatedPackageFee = BigDecimal.ZERO;
			if (bean.getStandCharge() != null) {
				calculatedPackageFee = calculatedPackageFee.add(bean.getStandCharge());
			}
			if (bean.getBoxCharge() != null) {
				calculatedPackageFee = calculatedPackageFee.add(bean.getBoxCharge());
			}
			// 计算值
			bean.setCalculatedPackageFee(calculatedPackageFee);
		}

		/**
		 * 
		 * 打木架费用
		 * 
		 * @author 025000-FOSS-helong
		 * @date 2012-11-30 下午03:29:21
		 */
		private void getStandCharge(WaybillInfoVo bean) {
			ValueAddDto dto = new ValueAddDto();

			if (bean.getStandGoodsNum() != null && bean.getStandGoodsNum() > 0) {
				// 打木架
				List<ValueAddDto> frameList = rfcService.queryValueAddPriceList(getQueryYokeParam(bean, DictionaryValueConstants.PACKAGE_TYPE__FRAME, bean.getStandGoodsVolume()));

				if (frameList != null) {
					if (!frameList.isEmpty()) {
						dto = frameList.get(0);
						bean.setStandCharge(dto.getCaculateFee());
					} else {
						throw new WaybillValidateException(i18n.get("foss.gui.changingexp.waybillImportAction.exception.nullPackingVolumeFee"));
					}
				} else {
					throw new WaybillValidateException(i18n.get("foss.gui.changingexp.waybillImportAction.exception.nullPackingVolumeFee"));
				}
			}
		}

		/**
		 * 
		 * 打木箱费用
		 * 
		 * @author 025000-FOSS-helong
		 * @date 2012-11-30 下午03:29:21
		 */
		private void getBoxCharge(WaybillInfoVo bean) {
			ValueAddDto dto = new ValueAddDto();
			if (bean.getBoxGoodsNum() != null && bean.getBoxGoodsNum() > 0) {
				// 打木箱
				List<ValueAddDto> boxList = rfcService.queryValueAddPriceList(getQueryYokeParam(bean, DictionaryValueConstants.PACKAGE_TYPE__BOX, bean.getBoxGoodsVolume()));

				if (boxList != null) {
					if (!boxList.isEmpty()) {
						dto = boxList.get(0);
						bean.setBoxCharge(dto.getCaculateFee());
					} else {
						throw new WaybillValidateException(i18n.get("foss.gui.changingexp.waybillImportAction.exception.nullPackingBoxFee"));
					}
				} else {
					throw new WaybillValidateException(i18n.get("foss.gui.changingexp.waybillImportAction.exception.nullPackingBoxFee"));
				}
			}
		}

		/**
		 * 
		 * 获取查询参数
		 * 
		 * @author 025000-FOSS-helong
		 * @date 2012-11-16 上午11:02:10
		 */
		private QueryBillCacilateValueAddDto getQueryYokeParam(WaybillInfoVo bean, String subType, BigDecimal volume) {
			QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
			queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
			queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE
			queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
			queryDto.setGoodsTypeCode(null);// 货物类型CODE
			queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
			queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
			queryDto.setVolume(volume);// 体积
			queryDto.setOriginnalCost(null);// 原始费用
			queryDto.setFlightShift(null);// 航班号、

			queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）

			queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
			queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
			queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BZ);// 计价条目CODE
			queryDto.setPricingEntryName(null);// 计价名称
			return queryDto;
		}
	
		/**
		 * 
		 * UI注入
		 * 
		 * @author 102246-foss-shaohongliang
		 * @date 2012-12-24 下午4:48:26
		 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
		 */
		public void setInjectUI(WaybillRFCUI ui) {
			waybillRFCUI = ui;
		}

		public OpenWaybillRFCUIAction getOpenWaybillRFCUIAction() {
			return openWaybillRFCUIAction;
		}

		public void setOpenWaybillRFCUIAction(
				OpenWaybillRFCUIAction openWaybillRFCUIAction) {
			this.openWaybillRFCUIAction = openWaybillRFCUIAction;
		}

		public WaybillRfcImportDto getRfcImportDto() {
			return rfcImportDto;
		}

		public void setRfcImportDto(WaybillRfcImportDto rfcImportDto) {
			this.rfcImportDto = rfcImportDto;
		}

		public WaybillRFCUI getWaybillRFCUI() {
			return waybillRFCUI;
		}

		public void setWaybillRFCUI(WaybillRFCUI waybillRFCUI) {
			this.waybillRFCUI = waybillRFCUI;
		}

		public WaybillImportAction getWaybillImportAction() {
			return waybillImportAction;
		}

		public void setWaybillImportAction(WaybillImportAction waybillImportAction) {
			this.waybillImportAction = waybillImportAction;
		}

		public IWaybillRfcService getRfcService() {
			return rfcService;
		}

		public void setRfcService(IWaybillRfcService rfcService) {
			this.rfcService = rfcService;
		}

		public ICustomerService getCustomerService() {
			return customerService;
		}

		public void setCustomerService(ICustomerService customerService) {
			this.customerService = customerService;
		}

		public IWaybillManagerService getWaybillManagerService() {
			return waybillManagerService;
		}

		public void setWaybillManagerService(
				IWaybillManagerService waybillManagerService) {
			this.waybillManagerService = waybillManagerService;
		}

		public IWaybillPickupService getWaybillPickupService() {
			return waybillPickupService;
		}

		public void setWaybillPickupService(IWaybillPickupService waybillPickupService) {
			this.waybillPickupService = waybillPickupService;
		}

		
		
}
