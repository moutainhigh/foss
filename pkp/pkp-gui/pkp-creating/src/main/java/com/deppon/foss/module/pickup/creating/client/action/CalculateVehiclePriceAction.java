package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.creating.client.ui.popupdialog.CalculateVehicleDialog;
import com.deppon.foss.module.pickup.creating.client.vo.VehiclePriceVo;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * @author DP-FOSS-YANGKANG
 * 整车报价校验总运费
 */
public class CalculateVehiclePriceAction implements IButtonActionListener<CalculateVehicleDialog> {
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(CalculateVehiclePriceAction.class);
	
	 /**
	  * 主界面
	  */
	private CalculateVehicleDialog ui;
	/**
	 * 校验总运费
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//获取绑定的变量
		Map<String, IBinder<VehiclePriceVo>> map = ui.getBindersMap();
		IBinder<VehiclePriceVo> vehiclePriceBinder = map.get("vehiclePriceBinder");
		VehiclePriceVo bean = vehiclePriceBinder.getBean();
		//验证所需变量是否满足条件
		if(bean!=null){
			//验证是否满足条件
			bean.setError(true);
			validate(bean);
			if(!bean.isError()){
				return;
			}
		}else{
			return;
		}
		//获取当前登录部门，根据部门编码和发票标记查询整车价格波动参数方案
		CarloadPricePlanDto  entity = new CarloadPricePlanDto();
		List<CarloadPricePlanDto> list = new ArrayList<CarloadPricePlanDto>();
		
		//通过当前登录部门编码 查询本级部门以及所有上级部门信息
		List<OrgAdministrativeInfoEntity>  orgEntitys = BaseDataServiceFactory.getBaseDataService().queryOrgAdministrativeInfoEntityAllUpNOCache(bean.getLoginDeptCode());
		if(CollectionUtils.isNotEmpty(orgEntitys)){
			//查询整车价格方案，从当前部门开始，以此往上类推，查询到价格方案则跳出循环，否则继续执行循环
			for(int i=0; i<orgEntitys.size();i++){
				//通过组织  发票标记查询对应的整车价格波动参数方案
				entity.setActive(FossConstants.YES);
				entity.setOrganizationCode(orgEntitys.get(i).getCode());
				entity.setInvoiceType(bean.getVehicleInvoiceMarkType().getCode());
				list = BaseDataServiceFactory.getBaseDataService().selectCarloadPricePlanDetailByOrganizationCode(entity);
				if(CollectionUtils.isNotEmpty(list)){
					break;
				}
			}
		}
		
		
		/**
		 * 校验整车报价是否符合规则
		 * 校验公式“（开单总运费—整车运费冲减）—约车报价—装卸费- （开单总运费—整车运费冲减）*其他成本参数—包装费*包装费参数—货物重量*重量参数+约车报价*车价参数—人力成本参数＞0
		 */
		if(CollectionUtils.isNotEmpty(list)){
			entity = list.get(0);
			//最终价格
			BigDecimal finalPrice = BigDecimal.ZERO;
			//通过反推计算出来的开单总运费的值
			BigDecimal totalPrice = BigDecimal.ZERO;
			
			//开单总运费-整车运费冲减
			BigDecimal tempPriceOne = bean.getVehicleTotalFee().subtract(bean.getVehicleChangeFee());
			//开单总运费-整车运费冲减-约车报价-装卸费
			BigDecimal tempPriceTwo = tempPriceOne.subtract(bean.getVehicleReserveCarFee()).subtract(bean.getHandlingFee());
			//(开单总运费-整车运费冲减)*其他成本参数
			BigDecimal tempPriceThree = tempPriceOne.multiply(entity.getOtherCostParameter()==null ?BigDecimal.ZERO:entity.getOtherCostParameter());
			//包装费*包装费参数+货物重量*重量参数
			BigDecimal tempPriceFour = (bean.getVehiclePackageFee().multiply(entity.getPackageFeeParameter()==null?BigDecimal.ZERO:entity.getPackageFeeParameter())).add(bean.getVehicleProductWeight().multiply(entity.getWeightParameter()==null ?BigDecimal.ZERO:entity.getWeightParameter()));
			//(约车报价*车价参数)-人力成本参数
			BigDecimal tempPriceFive = (bean.getVehicleReserveCarFee().multiply(entity.getCarCostParameter()==null?BigDecimal.ZERO:entity.getCarCostParameter())).subtract(entity.getHumanCostParameter()==null?BigDecimal.ZERO:entity.getHumanCostParameter());
			
			finalPrice = tempPriceTwo.subtract(tempPriceThree).subtract(tempPriceFour).add(tempPriceFive);
			//对计算出的结果四舍五入 保留两位小数
			finalPrice = finalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			if(finalPrice.compareTo(BigDecimal.ZERO)>0){
				/**
				 * 通过校验公式（开单总运费—整车运费冲减）—约车报价—装卸费-（开单总运费—整车运费冲减）*其他成本参数—包装费*包装费参数—货物重量*重量参数+约车报价*车价参数—人力成本参数=0
				 * 计算获得开单总运费
				 */
				totalPrice = (tempPriceFour.subtract(tempPriceFive).add(bean.getVehicleReserveCarFee().add(bean.getHandlingFee()))).divide(BigDecimal.ONE.subtract(entity.getOtherCostParameter()==null?BigDecimal.ZERO:entity.getOtherCostParameter()),2,BigDecimal.ROUND_HALF_DOWN);
				totalPrice =totalPrice.add(bean.getVehicleChangeFee());
				//对计算出的结果四舍五入 保留两位小数
				totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
				MsgBox.showInfo("你输入的总运费高于"+totalPrice+",可以开单!");
				
			}else{
				/**
				 * 通过校验公式（开单总运费—整车运费冲减）—约车报价-装卸费—（开单总运费—整车运费冲减）*其他成本参数—包装费*包装费参数—货物重量*重量参数+约车报价*车价参数—人力成本参数=0
				 * 计算获得开单总运费
				 */
				totalPrice = (tempPriceFour.subtract(tempPriceFive).add(bean.getVehicleReserveCarFee().add(bean.getHandlingFee()))).divide(BigDecimal.ONE.subtract(entity.getOtherCostParameter()==null ? BigDecimal.ZERO:entity.getOtherCostParameter()),2,BigDecimal.ROUND_HALF_DOWN);
				totalPrice =totalPrice.add(bean.getVehicleChangeFee());			
				//对计算出的结果四舍五入 保留两位小数
				totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
				MsgBox.showInfo("总运费必须高于"+totalPrice+",否则无法开单!");
				return;
			}
			
		}else{
			//没有查询到有效的整车价格波动参数方案
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateVehicleDialog.isNoPricePlan"));
			return ;
		}
		
		
	}

	@Override
	public void setInjectUI(CalculateVehicleDialog ui) {
		
		this.ui = ui;
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.creating.client.action.CalculateVehiclePriceAction.validate
	 * @Description:验证变量是否满足条件
	 *
	 * @param bean
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-15 下午4:10:08
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-11-15    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	public void validate(VehiclePriceVo bean){
		//整车报价界面所有字段都不允许为空
		if(bean.getVehicleBillFee()==null
				||bean.getVehicleChangeFee()==null
				||bean.getVehicleInvoiceMarkType()==null
				||bean.getVehiclePackageFee()==null
				||bean.getVehicleProductCode()==null
				||bean.getVehicleProductWeight()==null
				||bean.getVehicleReserveCarFee()==null
				||bean.getVehicleTotalFee()==null
				||bean.getHandlingFee()==null){
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateVehicleDialog.isNotNull"));
			bean.setError(false);
			return;
		}
		//货物重量 开单报价  总运费都必须大于0
		//DEFECT-5834:整车报价界面报错
		if(bean.getVehicleProductWeight().compareTo(BigDecimal.ZERO)<=0
				||bean.getVehicleBillFee().compareTo(BigDecimal.ZERO)<=0
				||bean.getVehicleTotalFee().compareTo(BigDecimal.ZERO)<=0){
			
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateVehicleDialog.isGreaterThanZero"));
			bean.setError(false);
			return;
		}
		//总运费必须大于等于开单报价
		if(bean.getVehicleTotalFee().compareTo(bean.getVehicleBillFee())<0){
			MsgBox.showInfo(i18n.get("foss.gui.creating.calculateVehicleDialog.isGreaterThanVehicleBillFee"));
			bean.setError(false);
			return;
		}
	
	}
}
