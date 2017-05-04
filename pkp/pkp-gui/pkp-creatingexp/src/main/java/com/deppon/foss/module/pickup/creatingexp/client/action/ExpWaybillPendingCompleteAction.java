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
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.ICustomerService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillPriceExpressService;
import com.deppon.foss.module.pickup.common.client.service.impl.CustomerService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.service.impl.WaybillPriceExpressService;
import com.deppon.foss.module.pickup.common.client.ui.UIUtils;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.BusinessUtils;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.order.PendingCompleteDialog;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExpCommon;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.IconConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpWaybillPendingCompleteAction  implements IButtonActionListener<ExpWaybillEditUI> {
	/**
	 * 
	 */
	private static final String NULL = "NULL";

	//日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpWaybillPendingCompleteAction.class);
	
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpWaybillPendingCompleteAction.class);

	private static final double NUM_2000 = 2000;

	private static final double NUM_300000 = 300000;
	
	// 运单界面
	ExpWaybillEditUI ui;
	@Inject
	private ISalesDeptWaybillService salesDeptWaybillService;
	
	private List<WaybillPendingEntity> pendingList;

	public List getPendingList() {
		return pendingList;
	}

	public void setPendingList(List pendingList) {
		this.pendingList = pendingList;
	}

	IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	private ICustomerService customerService = GuiceContextFactroy.getInjector().getInstance(CustomerService.class);

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// 待补录运单号录入框
			PendingCompleteDialog dialog = new PendingCompleteDialog();
			// 居中显示弹出窗口
			WindowUtil.centerAndShow(dialog);

			WaybillPendingDto waybillDto = dialog.getWaybillPendingDto();
			if (waybillDto == null) {
				return;
			}else{
				if( waybillDto.getWaybillPending()!=null){
					String createOrgCode = waybillDto.getWaybillPending().getCreateOrgCode();
					UserEntity user = (UserEntity)SessionContext.getCurrentUser();
					String currentOrgCode = user.getEmployee().getDepartment().getCode();
					if(createOrgCode!=null &&! createOrgCode.equals(currentOrgCode)){
						//运单创建部门与当前登录人部门不是同一部门，不能导入运单！
						MsgBox.showInfo(i18n.get("foss.gui.creatingexp.expwaybillpendingcompleteaction.createwithcurr.isdiff"));
						LOGGER.warn(i18n.get("foss.gui.creatingexp.expwaybillpendingcompleteaction.createwithcurr.isdiff"));						
						return;
					}
					
					
					//TODO 小件添加  不是快递运单 不能导入的
					if(!CommonUtils.directDetermineIsExpressByProductCode(waybillDto.getWaybillPending().getProductCode())){
						MsgBox.showInfo(i18n.get("foss.gui.creating.waybillPendingCompleteAction.MsgBox.notExpress"));
						return;
					}
					
					//新增用于判断补录 能否选中上门发货
					if(!WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillDto.getWaybillPending().getPendingType())){
						SaleDepartmentEntity  saleDepartmentEntity=waybillService.querySaleDeptByCode(currentOrgCode);
						CusBargainEntity cusBargainEntity=null;
						if(StringUtils.isNotEmpty(currentOrgCode)){							
							cusBargainEntity=customerService.queryCusBargainByCustCode(waybillDto.getWaybillPending().getDeliveryCustomerCode());
						}
						if(WaybillConstants.YES.equals(saleDepartmentEntity.getCanExpressDoorToDoor()) &&
								cusBargainEntity ==null
								){
							ui.basicPanel.getHomeDelivery().setEnabled(true);
						}
					}else{
						ui.basicPanel.getHomeDelivery().setEnabled(false);
					}
				}
								
				initData(waybillDto);
				
				HashMap<String, IBinder<ExpWaybillPanelVo>> map = ui.getBindersMap();
				IBinder<ExpWaybillPanelVo> waybillBinder = map.get("waybillBinder");
				ExpWaybillPanelVo vo = waybillBinder.getBean();
				setPendingWaybill(vo, waybillDto, ui);
				ExpCommon.setOtherPendingData(ui, vo);
				ui.consignerPanel.getTxtConsignerMobile().requestFocus();
				ExpWaybillPanelVo voimport  = new ExpWaybillPanelVo();
				try {//这个是用于提交对比的对象 不能用同一个对象 要对比
					PropertyUtils.copyProperties(voimport, vo);
				} catch (Exception ee) {
					LOGGER.error("copyProperties异常", ee);
				}
				//PDA补录运输性质非商务专递,去掉运输性质中商务专递数据字典,151211,2016/03/20
				ProductEntityVo entityVo =null;
				if(!WaybillConstants.DEAP.equals(waybillDto.getWaybillPending().getProductCode())){
					for (int i = 0; i < ui.getProductTypeModel().getSize(); i++) {
						entityVo = (ProductEntityVo) ui.getProductTypeModel().getElementAt(i);
						//model去掉商务专递运输性质
						if (ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(entityVo.getCode())) {
							ui.getProductTypeModel().removeElement(entityVo);
							break;
						}
					}
				}
				//若运输性质是商务传递,运输性质选择框置灰(不可修改)
				if(WaybillConstants.DEAP.equals(waybillDto.getWaybillPending().getProductCode())){
					ui.getTransferInfoPanel().getCombProductType().setEnabled(false);
				}
				//用于在提交的时候比较老新数据的差异
				ui.setImportWaybillPanelVo(voimport);
				ui.billingPayPanel.getTxtDeliveryCharge().setEditable(true);
				ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(true);
				ui.billingPayPanel.getTxtPickUpCharge().setEditable(false);
				ui.billingPayPanel.getTxtPickUpCharge().setEnabled(false);
				ui.consignerPanel.getTxtConsignerLinkMan().setEditable(false);
		   
			}
		} catch (BusinessException er) {
			LOGGER.error("运单补录异常，原因：" + er.getMessage(), er);
			MsgBox.showInfo(i18n.get("foss.gui.creating.waybillPendingCompleteAction.MsgBox.waybillPendingException")+ er.getMessage());
		}
	}
	
	/**
	 * 初始化控件数据 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-20 上午10:58:35
	 */
	private void initData(WaybillPendingDto waybillDto){
		validateData(waybillDto);
		initCombProductType(waybillDto.getWaybillPending());
	}
	
	/**
	 * 校验导入的数据是否合法
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-21 上午10:37:51
	 */
	private void validateData(WaybillPendingDto waybillDto){
		WaybillPendingEntity waybill = waybillDto.getWaybillPending();
		if(null == waybill){
			LOGGER.error("待处理运单基本信息[WaybillPendingEntity]不能为空！");
			throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.nullWaybillPendingEntity"));
		}
		//产品类型
		String product = waybill.getProductCode();
		//是否外发
		String isOutAgent = waybill.getIsOuterBranch();
		//运单处理类型
		String pendingType = StringUtil.defaultIfNull(waybill.getPendingType());
		
		//运单处理类型不能为空
		if("".equals(pendingType)){
			throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.nullWaybillPendingType"));
		}
		
		//是否外发
		if(BooleanConvertYesOrNo.stringToBoolean(isOutAgent)){
			//偏线
			String partialLine = ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE;
			//空运
			String airFreight = ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT;
			//判断导入数据是否合法
			if (!partialLine.equals(product) && !airFreight.equals(product)) {
				throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.errorProductCode"));
			}
		}
	}

	/**
	 * 根据业务规则来初始化下拉框控件
	 * 1、若补录运单为外发，则产品只能为空运和偏线
	 * 2、....
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-20 上午10:11:48
	 */
	private void initCombProductType(WaybillPendingEntity waybillEntity)
	{
		boolean isOutAgent = BooleanConvertYesOrNo.stringToBoolean(StringUtil.defaultIfNull(waybillEntity.getIsOuterBranch()));
		//若为外发代理，则产品只能为空运或偏给
		if(isOutAgent){
			DefaultComboBoxModel combProduct = ui.getProductTypeModel();
			DefaultComboBoxModel newCombProduct = new DefaultComboBoxModel();
			ProductEntityVo product = null; 
			int count = combProduct.getSize();
			//偏线
			String partialLine = ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE;
			//空运
			String airFreight = ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT;
			for(int i=0; i<count ;i++){
				product = (ProductEntityVo) combProduct.getElementAt(i);
				if(partialLine.equals(product.getCode()) || airFreight.equals(product.getCode())){
					newCombProduct.addElement(product);
				}
			}
			ui.transferInfoPanel.getCombProductType().setModel(newCombProduct);
		}else{
			//重新在运单开单点击“新增”按钮
		}
	}
	
	
	/**
	 * 给运单bean赋值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午5:15:30
	 */
    public void setPendingWaybill(ExpWaybillPanelVo vo, Object object, ExpWaybillEditUI ui) {
	/**
	 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
	 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
	 */
	// 对象非空判断
	if (null == object) {
	    return;
	}

	// 待开单运单DTO
	WaybillPendingDto pendingDto = null;
	// 已处理运单DTO
	WaybillDto waybillDto = null;

	// 提货方式
	String receiveMethod = "";

	// 对象类型转换
	if (object instanceof WaybillPendingDto) {
	    pendingDto = (WaybillPendingDto) object;
	    // 获得基本信息实体
	    WaybillPendingEntity pendingEntity = pendingDto.getWaybillPending();
	    pendingDto = ExpCommon.getOrderCustInfo(pendingDto);
	    // 非空判断
	    if (null == pendingEntity) {
		// 抛出异常信息至前台
		throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.nullWaybillPending"));
	    } else {
	    	// 2015/11/26 liding add 线上IT服务台问题.事件编码：2015112580597
	    	// 设置补录时运单号MixNo
		    pendingDto.setMixNo(pendingEntity.getWaybillNo());
	    	salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
	    	//如果在出发运单管理里面判断了是否次日补录，那么在这里就不用判断了。此处是快递开单界面补录运单是判断的。
	    	if(vo.getIsNextDayPending()==null){
	    		pendingList = salesDeptWaybillService.queryPendingExpress(pendingDto);
	    		for(WaybillPendingEntity pend : pendingList){
	    			if(pend.getWaybillNo()!=null && FossConstants.YES.equals(pend.getActive())&& pendingEntity.getWaybillNo().equals(pend.getWaybillNo())){
			if(pend.getIsBig13()!=null && FossConstants.NO.equals(pend.getIsBig13())){
				vo.setIsNextDayPending("当日补录");
			}else{
				vo.setIsNextDayPending("次日补录");
			}
			break;
	       }
	      }
	    }
			receiveMethod = pendingEntity.getReceiveMethod();
			//liyongfei 获取发货客户ID，根据客户ID查询客户的详细信息，主要是查询客户是否可以精确代收
			
			//客户编码不为空
			if(pendingEntity.getDeliveryCustomerCode()!=null && !"".equals(pendingEntity.getDeliveryCustomerCode())){
				// 查询客户信息
				String accurateCollection = customerService.queryAccurateCollectionByCustCode(pendingEntity.getDeliveryCustomerCode());
				CusBargainEntity cusbarga = customerService.queryCusBargainByCustCode(pendingEntity.getDeliveryCustomerCode());
				if(cusbarga!=null){
					vo.setIsElectricityToEnjoy(cusbarga.getIfElecEnjoy());
				}
				vo.setAccurateCollection(accurateCollection);
			}else{
				vo.setAccurateCollection(FossConstants.NO);
			}
	    }

	    // 设置开户银行信息
	    vo.setOpenBank(pendingDto.getOpenBank());
	    //地址备注
	    if(StringUtils.isNotEmpty(pendingEntity.getDeliveryCustomerAddressNote())){
	    	vo.setDeliveryCustomerAddressNote(pendingEntity.getDeliveryCustomerAddressNote());
	    }
	    if(StringUtils.isNotEmpty(pendingEntity.getReceiveCustomerAddressNote())){
	    	vo.setReceiveCustomerAddressNote(pendingEntity.getReceiveCustomerAddressNote());	
	    }
	    if(WaybillConstants.YES.equals(pendingDto.getWaybillPending().getHomeDelivery())){
	    	vo.setHomeDelivery(true);
	    }
	    
		if(StringUtils.isNotEmpty(pendingDto.getWaybillPending().getOrderNo())){
			//设置【是否统一结算】 
			vo.setStartCentralizedSettlement(pendingDto.getWaybillPending().getStartCentralizedSettlement());
			vo.setArriveCentralizedSettlement(pendingDto.getWaybillPending().getArriveCentralizedSettlement());
			//【合同部门】
			if(StringUtils.isNotEmpty(pendingDto.getWaybillPending().getStartContractOrgCode())){
				vo.setStartContractOrgCode(pendingDto.getWaybillPending().getStartContractOrgCode());
			}else{
				vo.setStartContractOrgCode(null);
			}
			if(StringUtils.isNotEmpty(pendingDto.getWaybillPending().getStartContractOrgCode())){
				vo.setStartContractOrgName(CommonUtils.queryContractOrgName(pendingDto.getWaybillPending().getStartContractOrgCode()));
			}else{
				vo.setStartContractOrgName(null);
			}
			
			if(StringUtils.isNotEmpty(pendingDto.getWaybillPending().getArriveContractOrgCode())){
				vo.setArriveContractOrgCode(pendingDto.getWaybillPending().getArriveContractOrgCode());
			}else{
				vo.setArriveContractOrgCode(null);
			}
			if(StringUtils.isNotEmpty(pendingDto.getWaybillPending().getArriveContractOrgCode())){
				vo.setArriveContractOrgName(CommonUtils.queryContractOrgName(pendingDto.getWaybillPending().getArriveContractOrgCode()));
			}else{
				vo.setArriveContractOrgName(null);
			}
			//【催款部门编码】
			vo.setStartReminderOrgCode(pendingDto.getWaybillPending().getStartReminderOrgCode());
			vo.setArriveReminderOrgCode(pendingDto.getWaybillPending().getArriveReminderOrgCode());
		}
	} else if (object instanceof WaybillDto) {
	    waybillDto = (WaybillDto) object;
	    // 获得基本信息实体
	    WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
	    // 非空判断
	    if (null == waybillEntity) {
		// 抛出异常信息至前台
		throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.nullWaybillPending"));
	    } else {
		receiveMethod = waybillEntity.getReceiveMethod();
	    }
	    
		//设置是否上门发货
	    if(WaybillConstants.YES.equals(waybillDto.getActualFreightEntity().getHomeDelivery())){
	    	vo.setHomeDelivery(true);
	    }	    
	    // 设置开户银行信息
	    vo.setOpenBank(waybillDto.getOpenBank());
	} else {
	    return;
	}

	// 设置待处理运单基本信息
	setWaybillPending(vo, object, ui);
	// 清空其他费用列表
	ExpCommon.cleanOtherCharge(vo, ui);
	// 内部带货
	if (!WaybillConstants.INNER_PICKUP.equals(CommonUtils.defaultIfNull(receiveMethod))) {
	    // 设置待处理运单费用明细信息
	    setWaybillCHDtlPending(vo, object, ui);
	    // 设置待处理运单折扣明细
	    setWaybillDisDtlPending(object, ui);
	    // 设置待处理运单付款明细
	    setWaybillPaymentPending(vo, object, ui);
	}
	// 设置打木架信息
	setWoodenRequirePending(vo, object, ui);
	
	String returnBillType = null;
	//优惠活动
	String specialOffer=null;
	String receiveOrgCode = null; 
	Date billTime = null;
	if(object instanceof WaybillPendingDto){
		pendingDto = (WaybillPendingDto) object;
		// 获得基本信息实体
		WaybillPendingEntity pendingEntity = pendingDto.getWaybillPending();
		// 非空判断
		returnBillType = pendingEntity.getReturnBillType();
		specialOffer=pendingEntity.getSpecialOffer();
		billTime = pendingEntity.getBillTime();
		receiveOrgCode = pendingEntity.getReceiveOrgCode();
	}else{
		waybillDto = (WaybillDto) object;
		// 获得基本信息实体
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		returnBillType = waybillEntity.getReturnBillType();
		specialOffer=waybillDto.getActualFreightEntity().getSpecialOffer();
		billTime = waybillDto.getWaybillEntity().getBillTime();
		receiveOrgCode = waybillDto.getWaybillEntity().getReceiveOrgCode();
	}
	
	if("".equals(StringUtil.defaultIfNull(returnBillType))){
		// 返单类别
		vo.setReturnBillType(ExpCommon.getCombBoxValue(ui.getCombReturnBillTypeModel(),WaybillConstants.NOT_RETURN_BILL ));
	}else{
		// 返单类别
		vo.setReturnBillType(ExpCommon.getCombBoxValue(ui.getCombReturnBillTypeModel(),returnBillType ));
		
	}
	
	
	
	// 优惠类别
	//根据登录部门和当前时间查询并封装
	List<CityMarketPlanEntity> cityMarketPlanEntityList = waybillService.searchCityMarketPlanEntityList(
			receiveOrgCode, billTime);
	
	DefaultComboBoxModel combSpecialOffer = ui.getCombSpecialOffer();
	combSpecialOffer.removeAllElements();
	DataDictionaryValueVo emptyvo = new DataDictionaryValueVo();
	emptyvo.setId("");
	emptyvo.setValueCode("");
	emptyvo.setValueName("");
	combSpecialOffer.addElement(emptyvo);
	
	if(CollectionUtils.isNotEmpty(cityMarketPlanEntityList)){
		for(CityMarketPlanEntity entity:cityMarketPlanEntityList){
			DataDictionaryValueVo vo1 = new DataDictionaryValueVo();
			vo1.setId(entity.getId());
			vo1.setValueCode(entity.getCode());
			vo1.setValueName(entity.getName());
			combSpecialOffer.addElement(vo1);
		}
	}
	//ui.basicPanel.getCboSpecialOffer().setModel(combSpecialOffer);
	//ui.setCombSpecialOffer(combSpecialOffer);
	if("".equals(StringUtil.defaultIfNull(specialOffer))){
		//不做
	}else{

		DataDictionaryValueVo dvo = ExpCommon.getCombBoxValue(ui.getCombSpecialOffer(),specialOffer );
		vo.setSpecialOffer(dvo);
//		ui.basicPanel.getCboSpecialOffer().setSelectedItem(dvo);
	}
	
	if (vo.getCustomerPickupOrgCode() != null) {
		setReturnBillCharge(ui, vo);
	} else {
		if (!WaybillConstants.NOT_RETURN_BILL.equals(vo.getReturnBillType().getValueCode())
				) {
			setReturnBillType(vo);
			MsgBox.showInfo(i18n.get("foss.gui.creating.listener.Waybill.returnBillTypeListener.one"));
		}
	}
	
	// 查询其他费用
	queryOtherChargeData(ui, vo, object);
	// 计算其他费用合计
	calculateOtherCharge(ui, vo);
	// 把装卸费清空
	//vo.setServiceFee(BigDecimal.ZERO);
	ui.billingPayPanel.getTxtDeliveryCharge().setEditable(false);
	ui.billingPayPanel.getTxtDeliveryCharge().setEnabled(false);
	ui.billingPayPanel.getTxtPickUpCharge().setEditable(false);
	ui.billingPayPanel.getTxtPickUpCharge().setEnabled(false);
	
	//liding comment for NCI
	/**
	 * 通过付款方式判断“交易流水号”是否可编辑
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-26上午10:40
	 */
//	ExpCommon.whetherBankCardPayment(vo, ui);

    }
    
    /**
	 * 
	 * 设置返单费用到其他费用中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:50:46
	 */
	private void setReturnBillCharge(ExpWaybillEditUI ui, WaybillPanelVo bean) {
		JXTable otherTable = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) otherTable.getModel();
		List<OtherChargeVo> data = model.getData();
		if (bean.getReturnBillType() != null && !WaybillConstants.NOT_RETURN_BILL.equals(bean.getReturnBillType().getValueCode())&&
				!WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(bean.getReturnBillType().getValueCode())) {
			if (data == null || data.isEmpty()) {
				data = new ArrayList<OtherChargeVo>();
			}

			String type = "";
			// 到达联传真要转成传真类型
			if (WaybillConstants.RETURNBILLTYPE_ARRIVE.equals(bean.getReturnBillType().getValueCode())) {
				type = WaybillConstants.RETURNBILLTYPE_FAX;
			} else {
				type = bean.getReturnBillType().getValueCode();
			}

			List<ValueAddDto> list = waybillService.queryValueAddPriceList(ExpCommon.getQueryOtherChargeParam(bean, type));
			OtherChargeVo otherVo = getReturnBillCharge(bean, list, data);
			// 添加返单费用到其他费用表格
			String chargeName = ExpCommon.addOtherCharge(data, otherVo, bean);
			// 返单费用名称，添加的目的是为了选择了无返单类型删除其他费用中的返单费用
			bean.setReturnBillChargeName(chargeName);
			ui.incrementPanel.setChangeDetail(data);
		} else {
			// 删除返单
			deleteReturnBill(data, bean);
		}
	}
	
	
	/**
	 * 
	 * 获取其他费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-7 上午11:53:53
	 */
	private OtherChargeVo getReturnBillCharge(WaybillPanelVo bean, List<ValueAddDto> list, List<OtherChargeVo> data) {
		ValueAddDto dto = new ValueAddDto();
		OtherChargeVo vo = new OtherChargeVo();
		if (list != null) {
			if (!list.isEmpty()) {
				dto = list.get(0);
				// 费用编码
				vo.setCode(dto.getPriceEntityCode());
				// 名称
				vo.setChargeName(dto.getPriceEntityName());
				// 归集类别
				vo.setType(dto.getBelongToPriceEntityName());
				// 金额
				vo.setMoney(dto.getFee().toString());
				// 上限
				vo.setUpperLimit(dto.getMaxFee().toString());
				// 下限
				vo.setLowerLimit(dto.getMinFee().toString());
				// 是否可修改
				vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto.getCanmodify()));

				/**
				 * 月结
				 */
				Boolean chargeMode = bean.getChargeMode();
				if (chargeMode == null) {
					// 没有填写的情况下 作为非月结处理
					chargeMode = Boolean.FALSE;
				}
				/**
				 * 返单费用 非月结客户不允许进行编辑
				 */
				if (chargeMode) {
					vo.setIsUpdate(true);
				}else{
					vo.setIsUpdate(false);
				}

				// 是否可删除
				vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete()));
				vo.setId(dto.getId());
			} else {
				// 删除返单
				deleteReturnBill(data, bean);
				setReturnBillType(bean);
				throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.two"));
			}
		} else {
			// 删除返单
			deleteReturnBill(data, bean);
			setReturnBillType(bean);
			throw new WaybillValidateException(i18n.get("foss.gui.creating.listener.Waybill.exception.two"));
		}
		return vo;
	}
	
	
	/**
	 * 
	 * 设置返单类型
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-19 下午09:16:51
	 */
	private void setReturnBillType(WaybillPanelVo bean) {
		int size = ui.getCombReturnBillTypeModel().getSize();
		for (int i = 0; i < size; i++) {
			DataDictionaryValueVo vo = (DataDictionaryValueVo) ui.getCombReturnBillTypeModel().getElementAt(i);
			if (WaybillConstants.NOT_RETURN_BILL.equals(vo.getValueCode())) {
				ui.incrementPanel.getCombReturnBillType().setSelectedItem(vo);
				bean.setReturnBillType(vo);
			}
		}
	}
	
	/**
	 * 
	 * 删除返单
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-30 下午03:52:12
	 */
	private void deleteReturnBill(List<OtherChargeVo> data, WaybillPanelVo bean) {
		if (data != null && !data.isEmpty()) {
			// 将已有的返单费用从其他费用表格中删除
			ExpCommon.deleteOtherCharge(data, bean, bean.getReturnBillChargeName());
			ui.incrementPanel.setChangeDetail(data);
		}
	}
	
	/**
	 * 给运单基本信息赋值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午5:16:37
	 */
	public void setWaybillPending(	ExpWaybillPanelVo vo,Object object,ExpWaybillEditUI ui) {
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */
		//定义待处理运单实体
		WaybillPendingEntity waybillPending = null;
		//对象类型判断
		if(object instanceof WaybillDto){
			WaybillDto waybillDto = (WaybillDto)object;
			WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
			waybillPending = new WaybillPendingEntity();
			vo.setReceiveCustomerVillageCode(waybillDto.getActualFreightEntity().getReceiveCustomerVillageCode());
			try{
				//拷贝属性值
				PropertyUtils.copyProperties(waybillPending, waybillEntity);
				if(waybillDto.getActualFreightEntity() != null){
					//地址备注的拷贝
					if(StringUtils.isNotEmpty(waybillDto.getActualFreightEntity().getDeliveryCustomerAddressNote())){
						vo.setDeliveryCustomerAddressNote(waybillDto.getActualFreightEntity().getDeliveryCustomerAddressNote());
					}
					if(StringUtils.isNotEmpty(waybillDto.getActualFreightEntity().getReceiveCustomerAddressNote())){
						vo.setReceiveCustomerAddressNote(waybillDto.getActualFreightEntity().getReceiveCustomerAddressNote());
					}
				}
			}catch (Exception e) {
				//添加异常日志
				LOGGER.error("对象拷贝失败！\n原因："+e.getMessage());
				//抛出异常信息
				throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.copyErorr")+e.getMessage(), e.getMessage());
			}
		}else if(object instanceof WaybillPendingDto){
			WaybillPendingDto pendingDto = (WaybillPendingDto)object;
			waybillPending = pendingDto.getWaybillPending();
			vo.setReceiveCustomerVillageCode(waybillPending.getReceiveCustomerVillageCode());
		}else{
			//若非WaybillPendingEntity和WaybillEntity类型的对象，则抛出错误信息
			throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.paramErorr"));
		}
		//针对快递的实体
		WaybillExpressEntity expressEntity = 
				waybillService.getWaybillExpressByWaybillNo(waybillPending.getWaybillNo());
		
		//业务工具类
		BusinessUtils businessUtils = new BusinessUtils();
		// 提货方式
		vo.setReceiveMethod(ExpCommon.getCombBoxValue(ui.getPikcModeModel(), waybillPending.getReceiveMethod()));
		//设置提货网点
		if(waybillPending.getCustomerPickupOrgCode()!=null){
			BranchVo pickup = businessUtils.getCustomerPickupOrg(waybillPending.getCustomerPickupOrgCode(),waybillPending.getProductCode(),waybillPending.getBillTime());
			if(pickup!=null){
				vo.setCustomerPickupOrgCode(pickup);// 提货网点
				vo.setCustomerPickupOrgName(pickup.getName());//提货网点名称
				vo.setTargetOrgCode(pickup.getTargetOrgName());// 目的站
				// 设置是否可代收货款和是否可到付款标致
				canAgentCollected(pickup, vo);
			}else{
				pickup = new BranchVo();
				pickup.setCode(waybillPending.getCustomerPickupOrgCode());
			
				OuterBranchExpressEntity BPE= waybillService.queryLdpAgencyDeptByCode(waybillPending.getCustomerPickupOrgCode(), FossConstants.YES);
				
				if(BPE!=null){
					pickup.setName(BPE.getAgentDeptName());
					vo.setTargetOrgCode(BPE.getSimplename());
					vo.setCustomerPickupOrgName(pickup.getName());//提货网点名称
					vo.setCustomerPickupOrgCode(pickup);// 提货网点
					
				}
				
			}
		}
				
		//进行地址备注的写入
		vo.setDeliveryCustomerAddressNote(CommonUtils.defaultIfNull(waybillPending.getDeliveryCustomerAddressNote()));
		vo.setReceiveCustomerAddressNote(CommonUtils.defaultIfNull(waybillPending.getReceiveCustomerAddressNote()));
		
		vo.setOriginalWaybillNo(waybillPending.getOriginalWaybillNo());
		vo.setReturnType(ExpCommon.getCombBoxValue(ui.getCombWaibillReturnModeModel(),
			waybillPending.getReturnType()));
		
		//设置运单号
		ui.numberPanel.getLblNumber().setText(waybillPending.getWaybillNo());
		//设置是PC暂存补录
		vo.setPCPending(true);
		vo.setWaybillNoImported(waybillPending.getWaybillNo());
		//判断订单编码是否为空 
		if(!StringUtil.isEmpty(waybillPending.getOrderNo())){
			//设置网单号为可编辑
			ui.numberPanel.getLblOrderNumber().setVisible(true);
			//设置网单号内容
			ui.numberPanel.getLblOrderNumber().setText(waybillPending.getOrderNo());
		}
		//业务工具类
		//BusinessUtils businessUtils = new BusinessUtils();
		//组织编码
		String orgCode = waybillPending.getReceiveOrgCode();
		// 收货部门
		vo.setReceiveOrgCode(CommonUtils.defaultIfNull(orgCode));
		
		//货部门省份编码
		if(StringUtil.isNotEmpty(orgCode)){
		vo.setReceiveOrgProvCode(CommonUtils.getReceiveOrgProvCode(orgCode));
		}
		//货部门省份编码
		vo.setReceiveOrgProvCode(CommonUtils.getReceiveOrgProvCode(orgCode));
		//非空判断
		if (orgCode != null) {
			
			
			SaleDepartmentEntity sales = waybillService.querySaleDeptByCode(orgCode, new Date());
			
			if(sales==null){
				OrgAdministrativeInfoEntity org = waybillService.queryByCode(orgCode);
				if (org != null) {
					// 始发网点 name
					vo.setReceiveOrgName(CommonUtils.defaultIfNull(org.getName()));
					// 始发网点 create time
					if (org.getBeginTime() != null) {
						vo.setReceiveOrgCreateTime(org.getBeginTime());
					}
				}
	
				// 是否是开的集中接送货运单
				if (businessUtils.isBillGroup(waybillPending.getCreateOrgCode())) {
					//ExpCommon.setSalesDepartmentForCentrial(org, vo, ui);
				}
			}else{
				vo.setReceiveOrgName(CommonUtils.defaultIfNull(sales.getName()));
				// 始发网点 create time
				if (sales.getOpeningDate() != null) {
					vo.setReceiveOrgCreateTime(sales.getOpeningDate());
				}
			}
		}
		//获取开单部门属性
		SaleDepartmentEntity sales = waybillService.querySaleDeptByCode(waybillPending.getCreateOrgCode(), new Date());	
		//DMANA-7422补录单时，不能修改目的站		
		if(FossConstants.YES.equals(sales.getCanUpdateDestination())  
				&& 
			!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(waybillPending.getReturnType())){
			ui.getTransferInfoPanel().getTxtPickBranch().setEnabled(false);
			ui.getTransferInfoPanel().getTxtDestination().setEnabled(false);
			ui.getTransferInfoPanel().getTxtKilometer().setEnabled(false);
			ui.getTransferInfoPanel().getBtnQueryBranch().setEnabled(false);
			ui.getTransferInfoPanel().getTxtPredictLine().setEnabled(false);
		}else{
			ui.getTransferInfoPanel().getTxtPickBranch().setEnabled(true);
			ui.getTransferInfoPanel().getTxtDestination().setEnabled(true);
			ui.getTransferInfoPanel().getTxtKilometer().setEnabled(true);
			ui.getTransferInfoPanel().getBtnQueryBranch().setEnabled(true);
			ui.getTransferInfoPanel().getTxtPredictLine().setEnabled(true);	
		}								
		
		//是否是开的集中接送货运单
		if(businessUtils.isBillGroup(waybillPending.getCreateOrgCode())){
			//设置集中接送货
			vo.setPickupCentralized(false);
		}
		//短信标识
		vo.setIsSMS(waybillPending.getIsSMS());
		//运单基本信息ID
		vo.setId(waybillPending.getId());
		// 运单处理状态（必须放在运单号前面）:之所以将状态的设值放在运单号前是为了在设值运单号后的气泡校验可根据运单状态进行
		vo.setWaybillstatus(waybillPending.getPendingType());
		//运单号
		vo.setWaybillNo(waybillPending.getWaybillNo());
		//设置原运单号
		vo.setOldWaybillNo(waybillPending.getWaybillNo());
		vo.setOrderNo(waybillPending.getOrderNo());// 订单号
		vo.setOrderChannel(waybillPending.getOrderChannel());// 订单来源
		vo.setDeliveryCustomerId(CommonUtils.defaultIfNull(waybillPending.getDeliveryCustomerId()));// 发货客户ID
		vo.setDeliveryCustomerContactId(waybillPending.getDeliveryCustomerContactId());// 发货客户联系人ID
		vo.setDeliveryCustomerCode(CommonUtils.defaultIfNull(waybillPending.getDeliveryCustomerCode()));// 发货客户编码
		vo.setDeliveryBigCustomer(CommonUtils.defaultIfNull(waybillPending.getDeliveryBigCustomer()));// 大客户标记
		//设置大客户标记
		if(FossConstants.ACTIVE.equals(vo.getDeliveryBigCustomer())){
			ui.consignerPanel.getLabel2().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(),IconConstants.BIG_CUSTOMER, 1, 1));
		}else{
			ui.consignerPanel.getLabel2().setIcon(CommonUtils.createIcon(ui.consignerPanel.getClass(), "", 1, 1));
		}
		
		vo.setDeliveryCustomerName(CommonUtils.defaultIfNull(waybillPending.getDeliveryCustomerName()));// 发货客户名称
		vo.setDeliveryCustomerMobilephone(CommonUtils.defaultIfNull(waybillPending.getDeliveryCustomerMobilephone()));// 发货客户手机
		
		vo.setDeliveryCustomerPhone(CommonUtils.defaultIfNull(waybillPending.getDeliveryCustomerPhone()));// 发货客户电话

		if(StringUtils.isNotEmpty(waybillPending.getDeliveryCustomerContact())){
			vo.setDeliveryCustomerContact(CommonUtils.defaultIfNull(waybillPending.getDeliveryCustomerContact()));// 发货客户联系人
			//补录时，如果是内部带货，则展示内部带货费用承担部门
			ExpCommon.transInnerPickupFeeBurdenDeptStat(ui,vo,true);
			if(WaybillConstants.DEPPON_CUSTOMER.equals(waybillPending.getDeliveryCustomerCode())){
				if(expressEntity!=null){
					//hbhk 设置工号和部门
					ui.consignerPanel.getTxtDeliveryEmployeeNo().setText(expressEntity.getDeliveryEmployeeCode());
					String innerOrgCode = expressEntity.getInnerPickupFeeBurdenDept();
					if(StringUtils.isNotEmpty(innerOrgCode)){
						OrgAdministrativeInfoEntity org = waybillService.queryByCode(innerOrgCode);
						vo.setInnerPickupFeeBurdenDept(innerOrgCode);
						if(org!=null){
							ui.consignerPanel.getTxtConsignerLinkMan().setText(org.getName());//与发货联系人共用输入框
						}		
					}
				}				
			}
		}else{
			if(StringUtils.isNotEmpty(waybillPending.getDeliveryCustomerCode())){
				Injector injector = GuiceContextFactroy.getInjector();
				
				IWaybillPriceExpressService waybillPriceExpressService  = injector
						.getInstance(WaybillPriceExpressService.class);
				CustomerQueryConditionDto condition = new CustomerQueryConditionDto();
				condition.setCustCode(waybillPending.getDeliveryCustomerCode());
				List<CustomerQueryConditionDto> dtoList = 
						//customerService
						waybillPriceExpressService.queryCustomerByCondition(condition);
				if(dtoList!=null&& dtoList.size()>0){
					CustomerQueryConditionDto d = dtoList.get(0);
					vo.setDeliveryCustomerContact(d.getContactName());
				}
			}
			
		}
		// 发货具体地址
		vo.setDeliveryCustomerAddress(CommonUtils.defaultIfNull(waybillPending.getDeliveryCustomerAddress()));

		/**
		 * 发货客户省市区
		 */
		AddressFieldDto consignerAddress = businessUtils.getProvCityCounty(waybillPending.getDeliveryCustomerProvCode(), waybillPending.getDeliveryCustomerCityCode(), waybillPending.getDeliveryCustomerDistCode());
		if(null != consignerAddress){
		    // 发货省份
		    vo.setDeliveryCustomerProvCode(consignerAddress.getProvinceId());
		    // 发货市
		    vo.setDeliveryCustomerCityCode(consignerAddress.getCityId());
		    // 发货区
		    vo.setDeliveryCustomerDistCode(consignerAddress.getCountyId());
		    // 发货区域
		    vo.setDeliveryCustomerAreaDto(consignerAddress);
		    vo.setDeliveryCustomerArea(businessUtils.getAddressAreaText(consignerAddress));
		    /**
		     * 为防止其它地方（例如提交前的校验）使用ui获得AddressFieldDto时出现空指针，
		     * 因此在此处获得了AddressFieldDto对象后就一并将其设置到UI界面中
		     */
		    ui.getConsignerPanel().getTxtConsignerArea().setAddressFieldDto(consignerAddress);
		}
		// 发货国家
		vo.setDeliveryCustomerNationCode(waybillPending.getDeliveryCustomerNationCode());
		if(StringUtils.isNotBlank(waybillPending.getDeliveryCustomerCode())){
			// 是否月结客户
			CusBargainEntity cusBargainEntity = ExpCommon.getCusBargainEntity(waybillPending.getDeliveryCustomerCode());
			if(cusBargainEntity != null){
			vo.setChargeMode(ExpCommon.isChargeMode(cusBargainEntity.getExPayWay()));
			}else{
				vo.setChargeMode(false);
			}
			if(cusBargainEntity!=null){
				vo.setPreferentialType(cusBargainEntity.getPreferentialType());//获取优惠类型
			}else{
				vo.setPreferentialType(null);//获取优惠类型
			}
		}else{
			//阿里商城默认月结
			if(WaybillConstants.ALIBABA.equals(waybillPending.getOrderChannel())){
				vo.setChargeMode(true);// 是否月结
			}
		}
		
		
		vo.setReceiveCustomerId(CommonUtils.defaultIfNull(waybillPending.getReceiveCustomerId()));// 收货客户ID
		vo.setReceiveCustomerContactId(waybillPending.getReceiveCustomerContactId());// 收货联系人ID
		vo.setReceiveCustomerCode(CommonUtils.defaultIfNull(waybillPending.getReceiveCustomerCode()));// 收货客户编码
		vo.setReceiveBigCustomer(CommonUtils.defaultIfNull(waybillPending.getReceiveBigCustomer()));// 大客户标记
		//设置大客户标记
		if(FossConstants.ACTIVE.equals(vo.getDeliveryBigCustomer())){
			ui.consigneePanel.getLblNewLabel().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(),IconConstants.BIG_CUSTOMER, 1, 1));
		}else{
			ui.consigneePanel.getLblNewLabel().setIcon(CommonUtils.createIcon(ui.consigneePanel.getClass(), "", 1, 1));
		}
		vo.setReceiveCustomerName(CommonUtils.defaultIfNull(waybillPending.getReceiveCustomerName()));// 收货客户名称
		vo.setReceiveCustomerMobilephone(CommonUtils.defaultIfNull(waybillPending.getReceiveCustomerMobilephone()));// 收货客户手机
		vo.setReceiveCustomerPhone(CommonUtils.defaultIfNull(waybillPending.getReceiveCustomerPhone()));// 收货客户电话
		vo.setReceiveCustomerContact(CommonUtils.defaultIfNull(waybillPending.getReceiveCustomerContact()));// 收货客户联系人
		//接送货地址ID
		vo.setContactAddressId(waybillPending.getContactAddressId());
		// 收货具体地址
		vo.setReceiveCustomerAddress(CommonUtils.defaultIfNull(waybillPending.getReceiveCustomerAddress()));
		
		/**
		 * 收货客户省市区
		 */
		AddressFieldDto consigneeAddress = businessUtils.getProvCityCountyVillage(waybillPending.getReceiveCustomerProvCode(), waybillPending.getReceiveCustomerCityCode(), waybillPending.getReceiveCustomerDistCode(),vo.getReceiveCustomerVillageCode());
		if(null != consigneeAddress){
		    // 收货省份
		    vo.setReceiveCustomerProvCode(consigneeAddress.getProvinceId());
		    // 收货市
		    vo.setReceiveCustomerCityCode(consigneeAddress.getCityId());
		    // 收货区
		    vo.setReceiveCustomerDistCode(consigneeAddress.getCountyId());
		    // 收货区域
		    vo.setReceiveCustomerAreaDto(consigneeAddress);
		    vo.setReceiveCustomerArea(businessUtils.getAddressAreaText(consigneeAddress));
		    /**
		     * 为防止其它地方（例如提交前的校验）使用ui获得AddressFieldDto时出现空指针，
		     * 因此在此处获得了AddressFieldDto对象后就一并将其设置到UI界面中
		     */
		    ui.getConsigneePanel().getTxtConsigneeArea().setAddressFieldDto(consigneeAddress);
		}
		// 收货国家
		vo.setReceiveCustomerNationCode(waybillPending.getReceiveCustomerNationCode());
		
		// 运输性质
		if (null != waybillPending.getProductCode()) {
			// 判断是否为整车
			if (FossConstants.YES.equals(waybillPending.getIsWholeVehicle())) {
				// 清空产品，设置为整车产品
				ExpCommon.cleanProductToWholeVehicle(ui);
				// 设置产品类型为整车
				ExpCommon.setProductCode(ui, vo, CommonUtils.defaultIfNull(ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE));
				// 因为重新设置了运单性质导致界面没有全部不可编辑 ，需要重新设置全不可编辑
				if (WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(waybillPending.getPendingType()) || WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillPending.getPendingType())) {
					UIUtils.disableUI(ui);
				}
			} else {
				// 设置产品类型
				ExpCommon.setProductCode(ui, vo, CommonUtils.defaultIfNull(waybillPending.getProductCode()));
				}
			// 根据运输性质的改变，改变提货方式
			ExpCommon.changePickUpMode(vo, ui);
			// 空运、偏线以及中转下线无法选择签收单返单
			ExpCommon.setReturnBill(vo,ui);
			// 偏线与空运不能选择预付费保密
			ExpCommon.setSecretPrepaid(vo,ui);
		}
		
		// 提货方式
		vo.setReceiveMethod(ExpCommon.getCombBoxValue(ui.getPikcModeModel(), waybillPending.getReceiveMethod()));
				
		
		/**
		 * 判断返回类型是否有值若无，则给一个默认值
		 * 之所以做此判断，是因为导入PDA开单时有可能没有返回类型
		 * 
		 * 由于用到提货网点，所以放在设置提货网点的后面
		 */
		if("".equals(StringUtil.defaultIfNull(waybillPending.getReturnBillType()))){
			// 返单类别
			vo.setReturnBillType(ExpCommon.getCombBoxValue(ui.getCombReturnBillTypeModel(),WaybillConstants.NOT_RETURN_BILL ));
		}else{
			// 返单类别
			vo.setReturnBillType(ExpCommon.getCombBoxValue(ui.getCombReturnBillTypeModel(),waybillPending.getReturnBillType() ));
		}
		
		
		vo.setLoadMethod(CommonUtils.defaultIfNull(waybillPending.getLoadMethod()));// 配载类型	
		vo.setPickupToDoor(false   /**BooleanConvertYesOrNo.stringToBoolean(waybillPending.getPickupToDoor())*/);// 是否上门接货
		vo.setDriverCode( null /**waybillPending.getDriverCode()*/);// 司机
		vo.setPickupCentralized(false /**BooleanConvertYesOrNo.stringToBoolean(waybillPending.getPickupCentralized())*/);// 是否集中接货
		vo.setLoadLineCode(waybillPending.getLoadLineCode());// 配载线路
		vo.setLoadOrgCode(CommonUtils.defaultIfNull(waybillPending.getLoadOrgCode()));// 配载部门
		vo.setLastLoadOrgCode(CommonUtils.defaultIfNull(waybillPending.getLastLoadOrgCode()));// 最终配载部门
		vo.setPreDepartureTime(waybillPending.getPreDepartureTime());// 预计出发时间
		vo.setPreCustomerPickupTime(waybillPending.getPreCustomerPickupTime());//预计派送/提货时间
		vo.setPreCustomerPickupTime(new Date());// 预计派送/提货时间
		vo.setPreDepartureTime(waybillPending.getPreDepartureTime());// 预计派送/提货时间
		vo.setCarDirectDelivery(false  /**BooleanConvertYesOrNo.stringToBoolean(waybillPending.getCarDirectDelivery())*/);// 是否大车直送
		vo.setGoodsName(waybillPending.getGoodsName());// 货物名称

		vo.setGoodsQtyTotal(waybillPending.getGoodsQtyTotal());// 货物总件数
		vo.setGoodsWeightTotal(CommonUtils.defaultIfNull(waybillPending.getGoodsWeightTotal()));// 货物总重量
		
		BigDecimal volume = waybillPending.getGoodsVolumeTotal();
		if(volume!=null){
			BigDecimal volume2  = volume;
			volume = volume.setScale(2, BigDecimal.ROUND_HALF_UP);

			vo.setGoodsVolumeTotal(CommonUtils.defaultIfNull(volume));// 货物总体积
			
			if(volume2.doubleValue()!= volume.doubleValue()){
				vo.setGoodsVolumePreviousTotal(CommonUtils.defaultIfNull(volume2));// 货物总体积
			}
		}else{
			vo.setGoodsVolumeTotal(CommonUtils.defaultIfNull(waybillPending.getGoodsVolumeTotal()));// 货物总体积
		}
		vo.setGoodsSize(waybillPending.getGoodsSize());// 货物尺寸
		vo.setGoodsType(waybillPending.getGoodsTypeCode());// 货物类型
		vo.setIsPassDept(BooleanConvertYesOrNo.stringToBoolean(waybillPending.getIsPassOwnDepartment()));
		vo.setPreciousGoods(BooleanConvertYesOrNo.stringToBoolean(waybillPending.getPreciousGoods()));// 是否贵重物品
		vo.setSpecialShapedGoods(BooleanConvertYesOrNo.stringToBoolean(waybillPending.getSpecialShapedGoods()));// 是否异形物品
		
		DataDictionaryValueVo outerNotes = new DataDictionaryValueVo();
		outerNotes.setValueCode(waybillPending.getOuterNotes());
		vo.setOuterNotes(outerNotes);//对外备注
		vo.setInnerNotes(waybillPending.getInnerNotes());// 对内备注
		vo.setTransportationRemark(waybillPending.getTransportationRemark());// 储运事项
		vo.setGoodsPackage(waybillPending.getGoodsPackage());// 货物包装
		vo.setPaper(waybillPending.getPaperNum());// 纸
		vo.setWood(waybillPending.getWoodNum());// 木
		vo.setFibre(waybillPending.getFibreNum());// 纤
		vo.setSalver(waybillPending.getSalverNum());// 托
		vo.setMembrane(waybillPending.getMembraneNum());// 膜
		if(NULL.equalsIgnoreCase(waybillPending.getOtherPackage())){
			vo.setOtherPackage("");// 其他
		}else{
			vo.setOtherPackage(waybillPending.getOtherPackage());// 其他
		}
		
		//退款类型
		vo.setRefundType(ExpCommon.getCombBoxValue(ui.getCombRefundTypeModel(),waybillPending.getRefundType() ));
		vo.setSecretPrepaid(BooleanConvertYesOrNo.stringToBoolean(waybillPending.getSecretPrepaid()));// 预付费保密
		vo.setToPayAmount(CommonUtils.defaultIfNull(waybillPending.getToPayAmount()));// 到付金额
		vo.setPrePayAmount(CommonUtils.defaultIfNull(waybillPending.getPrePayAmount()));// 预付金额
		
		// 优惠费用
		vo.setPromotionsFee(CommonUtils.defaultIfNull(waybillPending.getPromotionsFee()));
		// 优惠费用画布
		vo.setPromotionsFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(waybillPending.getPromotionsFee())));
		
		// 运费计费类型
		if(StringUtil.isEmpty(waybillPending.getBillingType())){
			vo.setBillingType(ExpCommon.getCombBoxValue(ui.getCombBillingType(),WaybillConstants.BILLINGWAY_WEIGHT));
		}else{
			vo.setBillingType(ExpCommon.getCombBoxValue(ui.getCombBillingType(),waybillPending.getBillingType()));
		}
		// 运费计费费率
		vo.setUnitPrice(CommonUtils.defaultIfNull(waybillPending.getUnitPrice()));
		
		// 公布价运费
		vo.setTransportFee(CommonUtils.defaultIfNull(waybillPending.getTransportFee()));
		// 公布价运费画布
		vo.setTransportFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(waybillPending.getTransportFee())));
		
		vo.setValueAddFee(CommonUtils.defaultIfNull(waybillPending.getValueAddFee()));// 增值费用
		// 开单付款方式
		vo.setPaidMethod(ExpCommon.getCombBoxValue(ui.getCombPaymentModeModel(),waybillPending.getPaidMethod()));
		// 根据设置的付款方式，设置接送货是否可编辑
		ExpCommon.selfPickup(vo, ui);
		vo.setArriveType(CommonUtils.defaultIfNull(waybillPending.getArriveType()));// 到达类型
		vo.setActive(waybillPending.getActive());// 运单状态
		vo.setWaybillstatus(waybillPending.getPendingType());	//运单处理状态
		
		// 总费用
		vo.setTotalFee(CommonUtils.defaultIfNull(waybillPending.getTotalFee()));
		// 总费用画布
		vo.setTotalFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(waybillPending.getTotalFee())));
		
		vo.setForbiddenLine(waybillPending.getForbiddenLine());// 禁行
		if(StringUtils.isNotEmpty(waybillPending.getProductCode()) && WaybillConstants.DEAP.equals(waybillPending.getProductCode())){
			waybillPending.setFreightMethod("HDP");
			waybillPending.setFlightNumberType("MIDDLE_FLIGHT");
		}
		vo.setFreightMethod(ExpCommon.getCombBoxValue(ui.getFreightMethod(),waybillPending.getFreightMethod()));// 合票方式
		//设置航班类型
		vo.setFlightNumberType(ExpCommon.getCombBoxValue(ui.getFlightNumberType(), waybillPending.getFlightNumberType()));
		vo.setFlightShift(CommonUtils.defaultIfNull(waybillPending.getFlightShift()));// 航班时间
		vo.setPromotionsCode(waybillPending.getPromotionsCode());// 优惠编码
		vo.setCreateTime(new Date());// 创建时间
		vo.setModifyTime(waybillPending.getModifyTime());// 更新时间
		
		Boolean isPda = WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillPending.getPendingType()) ? Boolean.TRUE:Boolean.FALSE;
		vo.setIsPdaBill(isPda);// 是否为PDA运单
		if(isPda){
			vo.setBillTime(waybillPending.getBillTime());// 开单时间
		}else{
			vo.setBillTime(new Date());// 开单时间
		}

		vo.setCreateUserCode(CommonUtils.defaultIfNull(waybillPending.getCreateUserCode()));// 开单人
		vo.setModifyUserCode(CommonUtils.defaultIfNull(waybillPending.getModifyUserCode()));// 更新人
		vo.setCreateOrgCode(CommonUtils.defaultIfNull(waybillPending.getCreateOrgCode()));// 开单组织
		vo.setModifyOrgCode(CommonUtils.defaultIfNull(waybillPending.getModifyOrgCode()));// 更新组织
		vo.setCurrencyCode(waybillPending.getCurrencyCode());// 币种
		vo.setIsWholeVehicle(BooleanConvertYesOrNo.stringToBoolean(waybillPending.getIsWholeVehicle()));// 是否整车运单
		//是否经过营业部
		vo.setIsPassDept(BooleanConvertYesOrNo.stringToBoolean(waybillPending.getIsPassOwnDepartment()));
		//整车约车编号
		vo.setVehicleNumber(StringUtil.defaultIfNull(waybillPending.getOrderVehicleNum()));   
		vo.setWholeVehicleActualfee(CommonUtils.defaultIfNull(waybillPending.getWholeVehicleActualfee()));// 整车开单报价
		vo.setWholeVehicleAppfee(CommonUtils.defaultIfNull(waybillPending.getWholeVehicleAppfee()));// 整车约车报价
		vo.setAccountName(waybillPending.getAccountName());// 返款帐户开户名称
		vo.setAccountCode(waybillPending.getAccountCode());// 返款帐户开户账户
		vo.setAccountBank(waybillPending.getAccountBank());// 返款帐户开户银行
		// 计费重量
		vo.setBillWeight(CommonUtils.defaultIfNull(waybillPending.getBillWeight()));
		vo.setPreArriveTime(waybillPending.getPreArriveTime());// 预计到达时间
		vo.setAddTime(waybillPending.getAddTime());// 开单时长	
		
		// 送货费
		vo.setDeliveryGoodsFee(CommonUtils.defaultIfNull(waybillPending.getDeliveryGoodsFee()));
		// 送货费画布
		vo.setDeliveryGoodsFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(waybillPending.getDeliveryGoodsFee())));
		
		// 其他费用
		vo.setOtherFee( CommonUtils.defaultIfNull(waybillPending.getOtherFee()));
		// 其他费用画布
		vo.setOtherFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(waybillPending.getOtherFee())));
		
		// 包装手续费
		vo.setPackageFee( CommonUtils.defaultIfNull(waybillPending.getPackageFee()));
		// 包装手续费画布
		vo.setPackageFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(waybillPending.getPackageFee())));
		
		// 接货费
		vo.setPickupFee( CommonUtils.defaultIfNull(waybillPending.getPickupFee()));
		// 接货费画布
		vo.setPickUpFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(waybillPending.getPickupFee())));
		// 保价声明价值
		vo.setInsuranceAmount(CommonUtils.defaultIfNull(waybillPending.getInsuranceAmount()));
		// 保价声明价值画布
		vo.setInsuranceAmountCanvas(String.valueOf(CommonUtils.defaultIfNull(waybillPending.getInsuranceAmount())));
		// 保价费率
		//将保险费率转换成千分率的格式
		BigDecimal permillageIns = new BigDecimal(WaybillConstants.PERMILLAGE);
		BigDecimal feeRate = CommonUtils.defaultIfNull(waybillPending.getInsuranceRate()).multiply(permillageIns);
		vo.setInsuranceRate(feeRate);
		// 保价费
		vo.setInsuranceFee(CommonUtils.defaultIfNull( waybillPending.getInsuranceFee()));
		// 代收货款
		vo.setCodAmount(CommonUtils.defaultIfNull(waybillPending.getCodAmount()));
		// 代收货款画布
		vo.setCodAmountCanvas(String.valueOf(CommonUtils.defaultIfNull(waybillPending.getCodAmount())));
		// 代收费率
		//将代收货款费率转换成千分率的格式
		BigDecimal permillageCod = new BigDecimal(WaybillConstants.PERMILLAGE);
		BigDecimal codRate = CommonUtils.defaultIfNull(waybillPending.getCodRate()).multiply(permillageCod);
		// 代收货款费率
		vo.setCodRate(codRate);
		// 代收货款手续费
		vo.setCodFee( CommonUtils.defaultIfNull(waybillPending.getCodFee()));
		
		// 装卸费
		vo.setServiceFee( CommonUtils.defaultIfNull(waybillPending.getServiceFee()));
		// 装卸费画布
		vo.setServiceFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(waybillPending.getServiceFee())));
		
		//若为PDA导入运单，则设置ChangeColorTxt属性
		if(WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillPending.getPendingType())){
			vo.setChangeColorTxt(ExpCommon.getChangeColorTxt(ui,vo));
			//pda需要输入 pda计算总金额
			vo.setTotalCountPDA(  CommonUtils.defaultIfNull(waybillPending.getTotalFee()));
			//pda需要录入手写现付金额 所以输入框可以编辑
			ui.getBillingPayPanel().getTxtHandWriteMoney().setEnabled(true);
		}
		//公里数
		vo.setKilometer(waybillPending.getKilometer());
		
		// CRM营销活动
		String activeCode=null;
		WaybillDisDtlEntity disEntity=waybillService.queryActiveInfoByNoAndType(waybillPending.getWaybillNo());
		if(disEntity!=null && disEntity.getActiveCode()!=null){
			activeCode=disEntity.getActiveCode();
		}
		vo.setActiveInfo(ExpCommon.getCombBoxValue(ui.getCombActiveInfoModel(),activeCode));
		
		if(vo.getReceiveMethod()!=null){
		/**
		 * 如果非送货时，公里数不可录入，且要清空
		 */
		if (WaybillConstants.SELF_PICKUP.equals(vo.getReceiveMethod().getValueCode()) || 
				WaybillConstants.INNER_PICKUP.equals(vo.getReceiveMethod().getValueCode()) || 
				WaybillConstants.AIR_PICKUP_FREE.equals(vo.getReceiveMethod().getValueCode()) || 
				WaybillConstants.AIR_SELF_PICKUP.equals(vo.getReceiveMethod().getValueCode()) || 
				WaybillConstants.AIRPORT_PICKUP.equals(vo.getReceiveMethod().getValueCode())){
			vo.setKilometer(null);
			// 公里数不可编辑
			ui.transferInfoPanel.getTxtKilometer().setEditable(false);
		}else{
			// 公里数可编辑
			ui.transferInfoPanel.getTxtKilometer().setEditable(true);
		}
		}
		
		if( vo.getPaper()== null){
			vo.setPaper(Integer.valueOf(0));
		}
		
		
		

		if( vo.getWood()== null){
			vo.setWood(Integer.valueOf(0));
		}
		
		if( vo.getFibre()== null){
			vo.setFibre(Integer.valueOf(0));
		}
		if( vo.getSalver()== null){
			vo.setSalver(Integer.valueOf(0));
		}
		
		if( vo.getMembrane()== null){
			vo.setMembrane(Integer.valueOf(0));
		}
		
		int all = vo.getPaper().intValue()+vo.getWood().intValue()+
				vo.getFibre().intValue()+ vo.getSalver().intValue()+
				vo.getMembrane().intValue();
		
		if(all<=0){
			vo.setPaper(vo.getGoodsQtyTotal());
		}
		
//		vo.setPaper(vo.getGoodsQtyTotal());
//		vo.setWood(Integer.valueOf(0));
//		vo.setFibre(Integer.valueOf(0));
//		vo.setSalver(Integer.valueOf(0));
//		vo.setMembrane(Integer.valueOf(0));
//		
//		Integer intPaper = vo.getPaper();
//		Integer intWood = vo.getWood();
//		Integer intFibre = vo.getFibre();
//		Integer intSalver = vo.getSalver();
//		Integer intMembrane = vo.getMembrane();
//		
//		if (null == intPaper || null ==intWood || 
//				null == intFibre || null == intSalver || null ==intMembrane) {
//			
//			
//		}
		
		
		//TODO 小件添加 -------------------------------------
		vo.setIsAddCode(waybillPending.getIsAddCode());
		vo.setAddCodeTime(waybillPending.getAddCodeTime());
		vo.setExpressEmpCode(waybillPending.getExpressEmpCode());
		vo.setExpressEmpName(waybillPending.getExpressEmpName());
		vo.setExpressOrgCode(waybillPending.getExpressOrgCode());
		vo.setExpressOrgName(waybillPending.getExpressOrgName());
		vo.setPdaSerial(waybillPending.getPdaSerial());
		vo.setBankTradeSerail(waybillPending.getBankTradeSerail());
	
		//Z这里需要把工号转化为ExpressOrgCode
		
		//设置提货网点
		if(waybillPending.getCustomerPickupOrgCode()!=null){
			BranchVo pickup = businessUtils.getCustomerPickupOrg(waybillPending.getCustomerPickupOrgCode(),waybillPending.getProductCode(),waybillPending.getBillTime());
			if(pickup!=null){
				vo.setCustomerPickupOrgCode(pickup);// 提货网点
				vo.setCustomerPickupOrgName(pickup.getName());//提货网点名称
				vo.setTargetOrgCode(pickup.getTargetOrgName());// 目的站
				

				SalesDepartmentService salesDepartmentService 
					= DownLoadDataServiceFactory.getSalesDepartmentService();
				SalesDepartmentCityDto dto  = new SalesDepartmentCityDto();
				dto.setSalesDepartmentCode(pickup.getCode());
				SalesDepartmentCityDto result = 
						salesDepartmentService.querySalesDepartmentCityInfo(dto);
				//querySalesDepartmentCityInfo
				SaleDepartmentEntity saleDepartmentEntity = WaybillServiceFactory.getWaybillService()
						.querySaleDeptByCode(pickup.getCode());
				if(saleDepartmentEntity!=null && result!=null){
					//营业部是否可以快递接货，如果是的话 就是试点营业部
					result.setTestSalesDepartment(saleDepartmentEntity.getCanExpressPickupToDoor());
				}
				
				
				
				if(waybillPending!=null && (CommonUtils.directDetermineIsExpressByProductCode(waybillPending.getProductCode()))
						&& waybillPending.getReceiveMethod()!=null && !CommonUtils.verdictPickUpSelf( waybillPending.getReceiveMethod())){
					if(result != null && StringUtils.isNotEmpty(result.getCityName())){
						vo.setTargetOrgCode(result.getCityName());
					}else{
						//TODO---无试点城市
						vo.setTargetOrgCode("无试点城市");
					}
					
					
				}else{
					
					String simpleName = businessUtils.getSimpleName(pickup.getCode(),
							waybillPending.getBillTime());
					if (!"".equals(simpleName)) {
						vo.setTargetOrgCode(simpleName);
					} else {
						vo.setTargetOrgCode(pickup.getTargetOrgName());
					}
					
				}
				vo.setTargetSalesDepartmentCityDto(result);
				
				
			}
			
			
			
		}
		
		
		
		
		if(expressEntity!=null && expressEntity.getReturnType()!=null){
			vo.setReturnType(ExpCommon.getCombBoxValue(ui.getCombWaibillReturnModeModel(),
					expressEntity.getReturnType()));
			vo.setOriginalWaybillNo(expressEntity.getOriginalWaybillNo());
		}
		
		if(waybillPending.getInnerNotes()!=null &&
				StringUtils.contains(waybillPending.getInnerNotes(), "发货人工号：")){
			String inner = StringUtils.substringAfter(waybillPending.getInnerNotes(), "发货人工号：");
			
			inner = StringUtils.substring(inner , 0, NumberConstants.NUMBER_6);
			
			if(!StringUtils.contains(inner, ";")){
				String outer = StringUtils.substringAfterLast(waybillPending.getInnerNotes(), inner);
				String newinner = "发货人工号："+inner+";"+outer;
				waybillPending.setInnerNotes(newinner);
			}
			
			vo.setDeliveryEmployeeNo(inner);
		}
     
		
		/**
		 * 将waybillPending中的是否电子发票传值入vo中
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-10-24下午18:44
		 */
		vo.setIsElectronicInvoice(waybillPending.getIsElectronicInvoice());
		/**
		 * 将waybillPending中的发票手机号码传值入vo中
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-10-24下午18:44
		 */
		vo.setInvoiceMobilePhone(waybillPending.getInvoiceMobilePhone());
		/**
		 * 将waybillPending中的发票邮箱传值入vo中
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-10-24下午18:44
		 */
		vo.setEmail(waybillPending.getEmail());
		
		/**
		 * 将特安客户保价上限值存入waybillpanelvo
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-11-19下午18:26
		 */
		vo.setVipInsuranceAmount(waybillPending.getVipInsuranceAmount());
		
		/**
		 * 将代收货款上限值存入waybillpanelvo
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-11-19下午18:26
		 */
		vo.setVipCollectionPaymentLimit(waybillPending.getVipCollectionPaymentLimit());
		/**
		 * 根据Dmana-9885将crm运费传入waybillpanelvo
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-03-10上午10:14
		 */
		vo.setCrmTransportFee(waybillPending.getCrmTransportFee());
		/**
		 * 根据Dmana-9885将crm重量传入waybillpanelvo
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-03-10上午10:14
		 */
		vo.setCrmWeight(waybillPending.getCrmWeight());
		/**
		 * 根据Dmana-9885将crm运费传入waybillpanelvo
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-03-10上午10:14
		 */
		vo.setCrmVolume(waybillPending.getCrmVolume());
		/**
		 * Dmana-9885通过订单来源判断如果是巨商网或者阿里巴巴来源，付款方式不可更改
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-02-03下午13:55
		 */
		if(WaybillConstants.ALIBABA.equals(waybillPending.getOrderChannel())){
			List<DataDictionaryValueEntity> list = waybillService.queryPaymentMode();
			for (DataDictionaryValueEntity dataDictionary : list) {
				DataDictionaryValueVo ddvo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, ddvo);
				if (WaybillConstants.MONTH_PAYMENT.equals(ddvo.getValueCode()))
				{
					vo.setPaidMethod(ddvo);
				}
			}
			ui.incrementPanel.getCombPaymentMode().setEnabled(false);
		}
		/**
		 * 将交易流水号值带入VO中
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-28下午19:18
		 */
		vo.setTransactionSerialNumber(waybillPending.getBankTradeSerail());
	}
	
	
	
	/**
	 * 
	 * 初始化其他费用合计
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
	 * 查询其他费用
	 */
	private void queryOtherChargeData(ExpWaybillEditUI ui, WaybillPanelVo bean,
			Object object) {
		List<ValueAddDto> list = waybillService.queryValueAddPriceList(getQueryOtherChargeParam(bean));

		List<OtherChargeVo> voList = getOtherChargeList(list);
		
		

		// 待处理运单费用信息中的非其它费用
		List<WaybillCHDtlPendingEntity> chDtlList = null;
		// 运单号
//		String waybillNo = "";

		// 类型转换
		if (object instanceof WaybillDto) {
		    // 获得WaybillDto对象
		    WaybillDto waybillDto = (WaybillDto) object;
//		    waybillNo = waybillDto.getWaybillEntity().getWaybillNo();
		    // 获得费用集合
		    List<WaybillChargeDtlEntity> chargeList = waybillDto.getWaybillChargeDtlEntity();
		    List<WaybillCHDtlPendingEntity> chargePendingList = new ArrayList<WaybillCHDtlPendingEntity>();
		    // 遍历并转换
		    for (WaybillChargeDtlEntity chargeDtl : chargeList) {
			WaybillCHDtlPendingEntity chargePending = copyToWaybillCHDtlPendingEntity(chargeDtl);
			chargePendingList.add(chargePending);
		    }
		    chDtlList = chargePendingList;
		} else if (object instanceof WaybillPendingDto) {
		    WaybillPendingDto pendingDto = (WaybillPendingDto) object;
		    // 待处理运单基本信息
//		    WaybillPendingEntity waybillPending = copyToWaybillPendingEntity(object);
		    // 运单号
//		    waybillNo = waybillPending.getWaybillNo();
		    chDtlList = pendingDto.getWaybillChargeDtlPending();		   
		} else {
		    return;
		}
		//如果没有勾选上门发货，则从查询到的其他费用中将上门发货的费用剔除掉
    	if(bean instanceof ExpWaybillPanelVo){
        	ExpWaybillPanelVo vo=(ExpWaybillPanelVo)bean;
    		if(!vo.isHomeDelivery()){
    			for (int i = 0; i < voList.size(); i++) {
					if("ZYSCZJH".equals(voList.get(i).getCode())){
						voList.remove(i);
					}
				}
    		}
    	}
		
		if (CollectionUtils.isNotEmpty(chDtlList)) {
		    for (WaybillCHDtlPendingEntity entity : chDtlList) {
			String code = entity.getPricingEntryCode();
			// 代收货款费用
			if (PriceEntityConstants.PRICING_CODE_QT.equals(code)) {
				
				boolean hasFee= false;
				for(int i = 0; i <voList.size();i++){
					
					OtherChargeVo vo = voList.get(i);
					
					if(ExpWaybillConstants.OTHERFEE_KDBZF.equals(vo.getCode())){
						
						// 金额
						vo.setMoney(entity.getAmount().toPlainString());
						// 上限
						vo.setUpperLimit(entity.getAmount().toPlainString());
						// 下限
						vo.setLowerLimit(entity.getAmount().toPlainString());
						// 是否可修改
						vo.setIsUpdate(false);
						// 是否可删除
						vo.setIsDelete(false);
						hasFee = true;
					}
				}
				
//				if(!hasFee){
//					OtherChargeVo vo = new OtherChargeVo();
//						// 费用编码 TODO
//						vo.setCode(ExpWaybillConstants.OTHERFEE_KDBZF);
//						// 名称
//						vo.setChargeName("快递包装费");
//						// 归集类别
//						vo.setType(ExpWaybillConstants.OTHERFEE_KDBZF);
//						// 描述
//						vo.setDescrition(code);
//						// 金额
//						vo.setMoney(entity.getAmount().toPlainString());
//						// 上限
//						vo.setUpperLimit(entity.getAmount().toPlainString());
//						// 下限
//						vo.setLowerLimit(entity.getAmount().toPlainString());
//						// 是否可修改
//						vo.setIsUpdate(false);
//						// 是否可删除
//						vo.setIsDelete(false);
//						vo.setId(UUIDUtils.getUUID());
//						voList.add(vo);
//					}
				}
			
		    }
		    
		}
		
		
		if (voList != null) {
			if (!voList.isEmpty()) {
				ui.incrementPanel.setChangeDetail(otherChargeCompare(voList));
			}
		}
	}
	
	
	/**
	 * 
	 * 将原有其他费用与新查询出来其他费用进行比较，然后删除重复的项
	 */
	private List<OtherChargeVo> otherChargeCompare(List<OtherChargeVo> voList) {
		JXTable table = ui.incrementPanel.getTblOther();
		WaybillOtherCharge model = (WaybillOtherCharge) table.getModel();
		List<OtherChargeVo> data = model.getData();
		boolean flag=false;
		if (CollectionUtils.isNotEmpty(data)&& CollectionUtils.isNotEmpty(voList)) {
			for (int i = 0; i < voList.size(); i++) {
				if("ZYSCZJH".equals(voList.get(i).getCode())){	
					for (int j = 0; j < data.size(); j++) {
						if(data.get(j).getCode().equals(voList.get(i).getCode())){
							flag=true;
						}
					}
				}else{
					data.add(voList.get(i));
				}
				if(!flag){
					data.add(voList.get(i));
				}
			}
			return data;
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
				// 开单的时候不能增加更改费和中转费
				if (PriceEntityConstants.PRICING_CODE_GGF.equals(dto.getSubType()) || PriceEntityConstants.PRICING_CODE_ZZ.equals(dto.getSubType())) {
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
	 * 判断此网点是否可以开代收货款
	 */
	private void canAgentCollected(BranchVo branchVo, WaybillPanelVo bean) {
		//是否可代收货款
		bean.setCanAgentCollected(branchVo.getCanAgentCollected());
		//是否可货到付款
		bean.setArriveCharge(branchVo.getArriveCharge());
        /**
         * 选择目的站为外发虚拟快递营业部且有代收货款时，校验代收货款是否超限制
         * 如果代收货款大于代收上限就给出提示
         * @author:218371-foss-zhuxue
		 * @date 2015-10-301下午04:35:17
         */
		if (bean.getCustomerPickupOrgCode() != null) {
			if (bean.getCodAmount() != null
					&& waybillService.querySaleDepartmentByCodeNoCache(bean
							.getCustomerPickupOrgCode().getCode()) != null) {
				// 获取代收货款
				BigDecimal codAmount = bean.getCodAmount();
				// 获取提货网点的名称
				String loadName = bean.getCustomerPickupOrgName();
				// 通过提货网点返回含有代收货款上限的实体
				SaleDepartmentEntity IsaleDepartmentEntity = waybillService
						.querySaleDepartmentByCodeNoCache(bean
								.getCustomerPickupOrgCode().getCode());
				// BigDecimal codeAmount=new
				// BigDecimal(saleDepartmentEntity.getAgentCollectedUpperLimit());
				if (IsaleDepartmentEntity.getAgentCollectedUpperLimit() != null
						&& codAmount.compareTo(new BigDecimal(
								IsaleDepartmentEntity
										.getAgentCollectedUpperLimit())) > 0) {
					if (loadName != null || "".equals(loadName)
							&& loadName.endsWith("远郊快递营业部")) {
						// 超过该营业部代收上限，请重新选择目的站.
						throw new WaybillValidateException(
								i18n.get("foss.gui.creating.calculateAction.exception.validateCod.limit"));
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * 给运单费用信息赋值
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午5:16:53
	 */
    private void setWaybillCHDtlPending(WaybillPanelVo vo, Object object, ExpWaybillEditUI ui) {
	// 非空判断
	if (null == object) {
	    return;
	}

	// 待处理运单费用信息中的非其它费用
	List<WaybillCHDtlPendingEntity> chDtlList = null;
	// 运单号
	String waybillNo = "";

	// 类型转换
	if (object instanceof WaybillDto) {
	    // 获得WaybillDto对象
	    WaybillDto waybillDto = (WaybillDto) object;
	    waybillNo = waybillDto.getWaybillEntity().getWaybillNo();
	    // 获得费用集合
	    List<WaybillChargeDtlEntity> chargeList = waybillDto.getWaybillChargeDtlEntity();
	    List<WaybillCHDtlPendingEntity> chargePendingList = new ArrayList<WaybillCHDtlPendingEntity>();
	    // 遍历并转换
	    for (WaybillChargeDtlEntity chargeDtl : chargeList) {
		WaybillCHDtlPendingEntity chargePending = copyToWaybillCHDtlPendingEntity(chargeDtl);
		chargePendingList.add(chargePending);
	    }
	    chDtlList = chargePendingList;
	} else if (object instanceof WaybillPendingDto) {
	    WaybillPendingDto pendingDto = (WaybillPendingDto) object;
	    // 待处理运单基本信息
	    WaybillPendingEntity waybillPending = copyToWaybillPendingEntity(object);
	    // 运单号
	    waybillNo = waybillPending.getWaybillNo();
	    chDtlList = pendingDto.getWaybillChargeDtlPending();
	} else {
	    return;
	}

	// 遍历集合，并设置增值费用
	if (CollectionUtils.isNotEmpty(chDtlList)) {
	    for (WaybillCHDtlPendingEntity entity : chDtlList) {
		String code = entity.getPricingEntryCode();
		// 代收货款费用
		if (PriceEntityConstants.PRICING_CODE_HK.equals(code)) {
		    vo.setCodCode(code);
		    vo.setCodFee(entity.getAmount());
		    vo.setCodId(entity.getId());
		}

		// 送货费：它包括送货费+送货XXX费+超远派送费（公里数超过一定范围时才有超远派送费）
		setDeliverCharge(vo, code, entity);

		// 保险费
		if (PriceEntityConstants.PRICING_CODE_BF.equals(code)) {
		    vo.setInsuranceCode(code);
		    vo.setInsuranceFee(CommonUtils.defaultIfNull(entity.getAmount()));
		    vo.setInsuranceId(entity.getId());
		}

		// 包装费
		if (PriceEntityConstants.PRICING_CODE_BZ.equals(code)) {
			if(entity.getAmount().doubleValue() >= NUM_2000){
				int n = JOptionPane.showConfirmDialog(null, "确定包装费开单金额为2000以上？", "确认删除框", JOptionPane.YES_NO_OPTION); 
				if (n == JOptionPane.YES_OPTION) { 
	        	if(entity.getAmount().doubleValue() > NUM_300000){
	        		vo.setPackageFee(BigDecimal.ZERO);
	        	ExpCommon.getYokeCharge(vo, ui);
				CalculateFeeTotalUtils.calculateFee(vo);
				vo.setPackageFeeCanvas(vo.getPackageFee().toString());
	        	throw new WaybillValidateException("超过300000的上限值,请重新输入");
	        	}
	        
	        }else{ 
	        	vo.setPackageFee(BigDecimal.ZERO);
	        	ExpCommon.getYokeCharge(vo, ui);
				CalculateFeeTotalUtils.calculateFee(vo);
				vo.setPackageFeeCanvas(vo.getPackageFee().toString());
				return;
	        }}
		    vo.setPackageFee(CommonUtils.defaultIfNull(entity.getAmount()));
		}

		// 接货费
		if (PriceEntityConstants.PRICING_CODE_JH.equals(code)) {
		    vo.setPickupFee(CommonUtils.defaultIfNull(entity.getAmount()));
		    vo.setPickUpFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(entity.getAmount())));
		}
		
		

		// 公布价运费
		if (PriceEntityConstants.PRICING_CODE_FRT.equals(code)) {
		    vo.setTransportFeeCode(code);
		    vo.setTransportFeeId(entity.getId());
		    vo.setTransportFee(CommonUtils.defaultIfNull(entity.getAmount()));
		    vo.setTransportFeeCanvas(String.valueOf(CommonUtils.defaultIfNull(entity.getAmount())));
		}

		// 木架费
		if (DictionaryValueConstants.PACKAGE_TYPE__FRAME.equals(code)) {
		    vo.setStandChargeCode(code);
		    vo.setStandChargeId(entity.getId());
		    vo.setStandCharge(CommonUtils.defaultIfNull(entity.getAmount()));
		}

		// 木箱费
		if (DictionaryValueConstants.PACKAGE_TYPE__BOX.equals(code)) {
		    vo.setBoxChargeCode(code);
		    vo.setBoxChargeId(entity.getId());
		    vo.setBoxCharge(CommonUtils.defaultIfNull(entity.getAmount()));
		}
	    }

	    setWaybillDeliverDtl(vo.getDeliverList());
	}

	// 获得其它费用集合
	List<OtherChargeVo> otherList = getOtherCharge(waybillNo, object);
	if (CollectionUtils.isNotEmpty(otherList)) {
	    // 计算其它费用合计
	    BigDecimal otherChargeSum = BigDecimal.ZERO;
	    for (OtherChargeVo other : otherList) {
		BigDecimal money = new BigDecimal(other.getMoney());
		otherChargeSum = otherChargeSum.add(money);
	    }
	    vo.setOtherFee(CommonUtils.defaultIfNull(otherChargeSum));
	    // 画布其他费用表格
	    ui.incrementPanel.setChangeDetail(otherList);
	} else {
	    // 画布其他费用表格:删除其它费用栏中数据
	    ui.incrementPanel.setChangeDetail(null);
	}

	// 设置“详细计价信息”画板中送货费表格的值
	if (CollectionUtils.isNotEmpty(vo.getDeliverList())) {
	    ui.getCanvasContentPanel().getOtherCost().setChangeDetail(vo.getDeliverList());
	}
    }
	
	/**
	 * 设置“详细计价信息”画板中送货费表格的值
	 * 
	 * 由于WaybillDisDtlPendingEntity表和WaybillDisDtlEntity表中没有费用对应的名称
	 * 所以还需要重新在数据库中再查询 一遍
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-12 下午4:50:20
	 */
	private void setWaybillDeliverDtl(List<DeliverChargeEntity> deliveryList){
		//判断集合非空
		if(CollectionUtils.isEmpty(deliveryList)){
			return;
		}
		
		//费用条目编码集合
		List<String> entryCodes = new ArrayList<String>();
		//遍历集合
		for (DeliverChargeEntity charge : deliveryList) {
			entryCodes.add(StringUtil.defaultIfNull(charge.getCode()));
		}
	
		//获得code对应的name
		Map<String,String> map = waybillService.queryPriceEntryNameByEntryCodes(entryCodes);
		
		//遍历集合
		for (DeliverChargeEntity disEntity : deliveryList) {
			//获得名称值
			String name = map.get(disEntity.getCode());
			//若值不为空，则设置名称
			if(StringUtil.isNotEmpty(name)){
				disEntity.setName(name);
			}
		}
	}
	
	
	
	
	/**
	 * 设置运单折扣明细
	 * （主要是设置“详细计价信息”面板中表格的值，因为与折扣相关的值在WaybillPanelVo中已经有了）
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-19 上午9:05:06
	 */
	private void setWaybillDisDtlPending(Object object, ExpWaybillEditUI ui) {
		// 非空判断
		if (null == object) {
			return;
		}

		// 获得待处理折扣集合
		List<WaybillDisDtlPendingEntity> disDtlPendingList = null;

		// 类型转换
		if (object instanceof WaybillDto) {
			// 获得WaybillDto对象
			WaybillDto waybillDto = (WaybillDto) object;

			// 获得折扣集合
			List<WaybillDisDtlEntity> disList = waybillDto.getWaybillDisDtlEntity();
			// 获得待处理折扣集合
			List<WaybillDisDtlPendingEntity> disPendingList = new ArrayList<WaybillDisDtlPendingEntity>();
			// 遍历并转换
			for (WaybillDisDtlEntity disDtl : disList) {
				WaybillDisDtlPendingEntity disPending = copyToWaybillDisDtlPendingEntity(disDtl);
				disPendingList.add(disPending);
			}

			disDtlPendingList = disPendingList;
		} else if (object instanceof WaybillPendingDto) {
			WaybillPendingDto pendingDto = (WaybillPendingDto) object;
			disDtlPendingList = pendingDto.getWaybillDisDtlPending();
		} else {
			return;
		}

		// 判断集合是否为空
		if (CollectionUtils.isNotEmpty(disDtlPendingList)) {
			List<WaybillDiscountVo> disCountList = getWaybillDiscountVoList(disDtlPendingList);
			if (CollectionUtils.isNotEmpty(disCountList)) {
				ui.canvasContentPanel.getConcessionsPanel().setChangeDetail(disCountList);
			}
		}
	}
	
	/**
	 * 设置运单付款明细
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-19 上午9:05:08
	 */
	private void setWaybillPaymentPending(WaybillPanelVo vo,Object object,ExpWaybillEditUI ui){
		//TODO
	}
	
	/**
	 * 将WaybillDisDtlPendingEntity对象转换为WaybillDiscountVo
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-12 上午11:51:48
	 */
	private List<WaybillDiscountVo> getWaybillDiscountVoList(List<WaybillDisDtlPendingEntity> disList){
		//集合非空判断
		if(CollectionUtils.isEmpty(disList)){
			return null;
		}
		
		//定义转换对象的集合
		List<WaybillDiscountVo> voList = new ArrayList<WaybillDiscountVo>();
		
		for (WaybillDisDtlPendingEntity disEntity : disList) {
			//定义转换对象
			WaybillDiscountVo vo = new WaybillDiscountVo();
			//优惠项目
			vo.setFavorableItemName(disEntity.getPricingEntryName());
			//优惠项目
			vo.setFavorableItemCode(disEntity.getPricingEntryCode());
			//优惠类别
			vo.setFavorableTypeName(disEntity.getTypeName());
			//优惠类别
			vo.setFavorableTypeCode(disEntity.getType());
			//优惠子类别
			vo.setFavorableSubTypeName(disEntity.getSubTypeName());
			//优惠子类别
			vo.setFavorableSubTypeCode(disEntity.getSubType());
			//优惠折扣率
			
			if (disEntity.getRate() != null) {
				// 优惠折扣率
				vo.setFavorableDiscount(String.valueOf(disEntity.getRate()));
			} else {
				// 优惠折扣率
				vo.setFavorableDiscount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableDiscount"));
			}
			//设置快递续重折扣率
			if(disEntity.getExpressContinueRate()!=null){
				//设置快递续重折扣率
				vo.setContinueFavorableDiscount(String.valueOf(disEntity.getExpressContinueRate()));
			}
			//优惠金额
			if (disEntity.getAmount() != null) {
				//优惠金额
				vo.setFavorableAmount(String.valueOf(disEntity.getAmount()));
			} else {
				vo.setFavorableAmount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableAmount"));
			}

			
			voList.add(vo);
		}

		return voList;
	}
	
	
	/**
	 * 
	 * 设置送货费
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 上午08:15:55
	 */
	private void setDeliverCharge(WaybillPanelVo vo,String code,WaybillCHDtlPendingEntity entity)
	{
		List<DeliverChargeEntity> deliverCharge = vo.getDeliverList();
		if(deliverCharge == null)
		{
			deliverCharge = new ArrayList<DeliverChargeEntity>();
		}
		
		//送货费
		if(PriceEntityConstants.PRICING_CODE_SH.equals(code)){
			//添加送货费
			deliverCharge.add(getDeliverCharge(vo,code,entity));
		}else if(PriceEntityConstants.PRICING_CODE_SHSL.equals(code)){
			//添加送货上楼
			deliverCharge.add(getDeliverCharge(vo,code,entity));
		}else if(PriceEntityConstants.PRICING_CODE_SHJC.equals(code)){
			//添加送货进仓
			deliverCharge.add(getDeliverCharge(vo,code,entity));
		}else if(PriceEntityConstants.PRICING_CODE_CY.equals(code)){
			//添加超远派送费
			deliverCharge.add(getDeliverCharge(vo,code,entity));
		}
		vo.setDeliverList(deliverCharge);
	}
	
	/**
	 * 
	 * 将查询出来的送货费添加到送货费集合中，并且对送货费进行合计
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 上午08:36:20
	 */
	private DeliverChargeEntity getDeliverCharge(WaybillPanelVo vo,String code,WaybillCHDtlPendingEntity entity)
	{
		DeliverChargeEntity deliverChargeEntity = new DeliverChargeEntity();
		BigDecimal deliverFee = vo.getDeliveryGoodsFee();
		if(deliverFee == null)
		{
			deliverFee = BigDecimal.ZERO;
		}
		//是否有效
		deliverChargeEntity.setActive(FossConstants.ACTIVE);
		//金额
		deliverChargeEntity.setAmount(entity.getAmount());
		//币种
		deliverChargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		//费用ID
		deliverChargeEntity.setId(entity.getId());
		//运单号
		deliverChargeEntity.setWaybillNo(vo.getWaybillNo());
		//费用编码
		deliverChargeEntity.setCode(code);
		//送货费(不能对送货费进行累加，只能对非送货费进行累，参见BUG-7877)
		if(!PriceEntityConstants.PRICING_CODE_SH.equals(code)){
			deliverFee = deliverFee.add(entity.getAmount());
		}
		//送货费
		vo.setDeliveryGoodsFee(deliverFee);
		//画布-送货费
		vo.setDeliveryGoodsFeeCanvas(deliverFee.toString());
		return deliverChargeEntity;
	}
	
	/**
	 * 将查询出的其它费用转换成VO
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午4:15:18
	 */
	private List<OtherChargeVo> getOtherCharge(String waybillNo,Object object) {
		//非空判断
		if(null == object){
			return null;
		}
		
		//定义其它费用对象集合
		List<WaybillOtherChargeDto> list = null;
		
		//类型判断
		if(object instanceof WaybillDto){
			//已补录运单类型
			list = waybillService.queryWaybillOtherChargeByNo(waybillNo);
		}else if(object instanceof WaybillPendingDto){
			//待补录运单类型
			list = waybillService.queryOtherChargeByNo(waybillNo);
		}else{
			return null;
		}
		
		//集合非空判断
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();
			if ( list.size()>0) {
				for (WaybillOtherChargeDto dto : list) {
					OtherChargeVo vo = new OtherChargeVo();
					vo.setCode(dto.getCode());// 费用编码
					vo.setChargeName(dto.getChargeName());// 名称
					vo.setType(dto.getType());// 归集类别
					vo.setDescrition(dto.getDescrition());// 描述
					vo.setMoney(dto.getAmount());// 金额
					vo.setUpperLimit(dto.getUpperLimit());// 上限
					vo.setLowerLimit(dto.getLowerLimit());// 下限
					vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto.getIsUpdate()));// 是否可修改
					vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto.getIsDelete()));// 是否可删除
					vo.setId(dto.getId());
					voList.add(vo);
				}
			}
			return voList;
		}
	}
	
	/**
	 * 给打木架信息赋值
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-6 下午3:23:54
	 */
	private void setWoodenRequirePending(WaybillPanelVo vo,Object object ,ExpWaybillEditUI ui){
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */
		
		// 获得代打木架信息对象
		WoodenRequirePendingEntity woodenEntity = copyToWoodenRequirementsEntity(object);
		// 对象非空判断
		if(null == woodenEntity){
			return;
		}
		// 代打木架部门编码
		vo.setPackageOrgCode(woodenEntity.getPackageOrgCode());
		// 打木架货物件数
		vo.setStandGoodsNum(woodenEntity.getStandGoodsNum());
		// 代打木架要求
		vo.setStandRequirement(woodenEntity.getStandRequirement());
		// 打木架货物尺寸
		vo.setStandGoodsSize(woodenEntity.getStandGoodsSize());
		// 打木架货物体积
		vo.setStandGoodsVolume(woodenEntity.getStandGoodsVolume());
		// 打木箱货物件数
		vo.setBoxGoodsNum(woodenEntity.getBoxGoodsNum());
		// 代打木箱要求
		vo.setBoxRequirement(woodenEntity.getBoxRequirement());
		// 打木箱货物尺寸
		vo.setBoxGoodsSize(woodenEntity.getBoxGoodsSize());
		// 打木箱货物尺寸
		vo.setBoxGoodsVolume(woodenEntity.getBoxGoodsVolume());
	}
	
	/**
	 * 将WaybillDto内的值拷贝到WaybillPendingDto中
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午6:01:20
	 */
	private WaybillPendingEntity copyToWaybillPendingEntity(Object object){
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */
		//定义待处理运单实体
		WaybillPendingEntity waybillPending = null;
		//对象类型判断
		if(object instanceof WaybillDto){
			WaybillDto waybillDto = (WaybillDto)object;
			WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
			waybillPending = new WaybillPendingEntity();
			try{
				//拷贝属性值
				PropertyUtils.copyProperties(waybillPending, waybillEntity);
			}catch (Exception e) {
				//添加异常日志
				LOGGER.error("对象拷贝失败！\n原因："+e.getMessage());
				//抛出异常信息
				throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.copyErorr")+e.getMessage(), e.getMessage());
			}
		}else if(object instanceof WaybillPendingDto){
			return ((WaybillPendingDto)object).getWaybillPending();
		}else{
			//若非WaybillPendingEntity和WaybillEntity类型的对象，则抛出错误信息
			throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.paramErorr"));
		}
		
		return waybillPending;
	}
	
	/**
	 *  将WaybillDto内的值拷贝到WaybillPendingDto中
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-12 上午11:46:01
	 */
	private WaybillDisDtlPendingEntity copyToWaybillDisDtlPendingEntity(WaybillDisDtlEntity disDtl){
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */
		//非空判断
		if(null == disDtl){
			return null;
		}
		
		//定义对象用例拷贝
		WaybillDisDtlPendingEntity disPending = new WaybillDisDtlPendingEntity();
		
		//对象类型判断
		try{
			//拷贝属性值
			PropertyUtils.copyProperties(disPending, disDtl);
		}catch (Exception e) {
			//添加异常日志
			LOGGER.error("对象拷贝失败！\n原因："+e.getMessage());
			//抛出异常信息
			throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.copyErorr")+e.getMessage(), e.getMessage());
		}
		
		return disPending;
	}
	
	/**
	 * 将WaybillDto内的值拷贝到WaybillPendingDto中
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午6:01:20
	 */
	private WaybillCHDtlPendingEntity copyToWaybillCHDtlPendingEntity(WaybillChargeDtlEntity chargeDtl){
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */
		//非空判断
		if(null == chargeDtl){
			return null;
		}
		
		//定义对象用例拷贝
		WaybillCHDtlPendingEntity chargePending = new WaybillCHDtlPendingEntity();
		
		//对象类型判断
		try{
			//拷贝属性值
			PropertyUtils.copyProperties(chargePending, chargeDtl);
		}catch (Exception e) {
			//添加异常日志
			LOGGER.error("对象拷贝失败！\n原因："+e.getMessage());
			//抛出异常信息
			throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.copyErorr")+e.getMessage(), e.getMessage());
		}
		
		return chargePending;
	}
	
	/**
	 * 将WaybillDto内的值拷贝到WaybillPendingDto中
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午6:01:20
	 */
	private WoodenRequirePendingEntity copyToWoodenRequirementsEntity(Object object){
		/**
		 * 之所以传入object对象，是为了在导入已补录运单时，对界面和bean的赋值可以共用该方法
		 * 前提是待处理运单对象与已处理运单对象属性名称一样，这样才可以使用属性拷贝的方法
		 */
		//非空判断
		if(null == object){
			return null;
		}
		//定义待处理运单实体
		WoodenRequirePendingEntity wooden = null;
		//对象类型判断
		if(object instanceof WaybillDto){
			WaybillDto waybillDto = (WaybillDto)object;
			WoodenRequirementsEntity entity = waybillDto.getWoodenRequirementsEntity();
			if(null != entity){
				wooden = new WoodenRequirePendingEntity();
				try{
					//拷贝属性值
					PropertyUtils.copyProperties(wooden, entity);
				}catch (Exception e) {
					//添加异常日志
					LOGGER.error("对象拷贝失败！\n原因："+e.getMessage());
					//抛出异常信息
					throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.copyErorr")+e.getMessage(), e.getMessage());
				}
			}else{
				return null;
			}
		}else if(object instanceof WaybillPendingDto){
			WaybillPendingDto pendingDto = (WaybillPendingDto)object;
			return pendingDto.getWoodenRequirePending();
		}else{
			//若非WaybillPendingEntity和WaybillEntity类型的对象，则抛出错误信息
			throw new WaybillImportException(i18n.get("foss.gui.creating.waybillPendingCompleteAction.exception.paramErorr"));
		}
		
		return wooden;
	}
	
	@Override
	public void setInjectUI(ExpWaybillEditUI ui) {
		this.ui = ui;
	}

}
