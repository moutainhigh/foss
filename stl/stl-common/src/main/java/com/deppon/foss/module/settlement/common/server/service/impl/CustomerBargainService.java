package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillReceivableEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditCustomerDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditOrgDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineOfcreditResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 客户信用额度管理服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-10 下午3:47:39
 * @since
 * @version
 */
public class CustomerBargainService implements ICustomerBargainService {

	/**
	 * 组织欠款额度管理服务
	 */
	private ICreditOrgService creditOrgService;
	
	/**
	 * 应收单Dao
	 */
	private IBillReceivableEntityDao billReceivableEntityDao;
	
    /**
	 * 客户信用额度服务
	 */
	private ICreditCustomerService creditCustomerService;
	
	/**
	 * 营业部Service，查询营业部的最大欠款额度
	 */
	private ISaleDepartmentService  saleDepartmentService;
	
	/**
	 * 客户合同信息，获取月结客户的信用额度
	 */
	private ICusBargainService cusBargainService;
    /**
     * 配置参数
     */
    private IConfigurationParamsService configurationParamsService;
    private static final double NUM_BILLION  =  1000000000L;
	
	/**
	 * 
	 * 判断能否欠款
	 * 
	 * 部门的最大临欠额度从综合管理中读取
	 * 客户信用额度从综合管理里面读取
	 * 
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-19 下午2:31:18
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService#isBeBebt(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public DebitDto isBeBebt(String customerCode, String orgCode,
			String debtType, BigDecimal debtAmount) {

		// 判断参数
		// 判断欠款方式
		// 判断付款方式是否为空
		if (StringUtils.isEmpty(debtType)) {
			throw new SettlementException("接口传入的付款方式不能为空！");

		}
		// 判断付款方式是否为非月结或临欠
		else if (!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
				.equals(debtType)
				&& !SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
						.equals(debtType)) {
			throw new SettlementException("接口传入的付款方式非临欠或月结！");
		}

		// 判断欠款额度
		if (null == debtAmount) {
			throw new SettlementException("欠款额度不能为空！");
		}

		// 可用额度
		DebitDto result = validaType(customerCode, orgCode, debtType,
				debtAmount);
		return result;
	}

	private DebitDto validaType(String customerCode, String orgCode,
			String debtType, BigDecimal debtAmount) {
		BigDecimal usableAmount = BigDecimal.ZERO;

		// 超期欠款
		String isOverdue = FossConstants.NO;

		// 返回结果
		DebitDto result = new DebitDto();

		// 超期备注信息
		String notes = "";

		// 如果欠款类别为月结,从数据库中获取可用额度
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
				.equals(debtType)) {
			
			// 判断客户编码是否为空
			if (StringUtils.isEmpty(customerCode)) {
				throw new SettlementException("客户编码不能为空！");
			}
			// 付款方式 （默认为月结）
			result.setPayment(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
			// 从dao层回去相应的数据
			CreditCustomerDto customerDebitVo =creditCustomerService.queryDebitCustomerInfo(customerCode);
//                    this.creditCustomerService
//					.queryDebitByCustomerCode(customerCode);
			
			CusBargainEntity  cubaEntity=cusBargainService.queryCusBargainByCustCode(customerCode);
			if(cubaEntity==null||cubaEntity.getArrearsAmount()==null){
				throw new SettlementException("客户信用额度信息不存在，请在综合管理系统中维护！");
			}
			// 对返回结果进行检查
			if (customerDebitVo != null) {
                //当前时间
                Date currentDate = new Date();
                //判断两个是否
                if(customerDebitVo.getMinDebitDate() == null){
                    customerDebitVo.setMinDebitDate(currentDate);
                }
                //判断相差天数
                long differDays = DateUtils.getTimeDiff( customerDebitVo.getMinDebitDate(),currentDate);
                if(cubaEntity.getDebtDays()<differDays){
                    isOverdue = FossConstants.YES;
                   String minDate = new SimpleDateFormat("yyyy-MM-dd").format(customerDebitVo.getMinDebitDate());
                    notes = "该客户存在超期应收单,最早欠款日期:" +minDate+"最大欠款天数为："+cubaEntity.getDebtDays();
                }
                // 可用额度----客户只判断欠款额度
                //如果超期则设置可用信用额度为0
/*                if(cubaEntity.getArrearsAmount().compareTo(BigDecimal.ZERO)>0){
	                if(customerDebitVo.getUsedAmount()==null){
	                    customerDebitVo.setUsedAmount(BigDecimal.ZERO);
	                }
				usableAmount = cubaEntity.getArrearsAmount().subtract(
						customerDebitVo.getUsedAmount());
                }*/
			}

			/**
			 *  月结额度取消掉，前面逻辑完全不想看了，此处直接重新设置为10亿。
			 * @author 杨书硕
			 */
			usableAmount = new BigDecimal(NUM_BILLION);
		}
		// 如果欠款类别为临时欠款,从数据库中获取可用额度
		else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
				.equals(debtType)) {
			// 判断营业部编码是否为空
			if (StringUtils.isEmpty(orgCode)) {
				throw new SettlementException("部门编码不能为空");
			}
			// 从Dao层获取相应的数据
			CreditOrgDto orgDebitDto = this.creditOrgService
					.queryOrgCreditInfo(orgCode);

			// 付款方式 付款方式为临欠
			result.setPayment(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);

			//综合管理：营业部信息接口，获取营业部的最大临欠额度
			SaleDepartmentEntity  saleEntity=saleDepartmentService.querySaleDepartmentByCode(orgCode);
			if(saleEntity==null||saleEntity.getMaxTempArrears()==null){
				throw new SettlementException("请在综合管理系统维护营业部的最大临欠额度");
			}
			
			// 对返回结果进行检查
			if (null != orgDebitDto) {
                if(orgDebitDto.getUsedAmount() == null){
                    orgDebitDto.setUsedAmount(BigDecimal.ZERO);
                }
				//部门的最大临欠额度    可用额度
				usableAmount = saleEntity.getMaxTempArrears().subtract(
						orgDebitDto.getUsedAmount());
				
				// 是否超期欠款
//				isOverdue = orgDebitDto.getIsOverdue();
                //计算是否超期
                Date current = new Date();
                String configValue = configurationParamsService.
                        queryConfValueByCode(ConfigurationParamsConstants.STL_DEBT_LIMIT_DAY);
                Date maxDebitDate = new Date();
                //获取欠款时间
                if(StringUtils.isNotBlank(configValue)&&
                        StringUtils.isNumeric(configValue)){
                maxDebitDate = org.apache.commons.lang.time.DateUtils.
                        addDays(orgDebitDto.getMinDebitDate(), Integer.parseInt(configValue));
                }
                //判断是否超期
                if(maxDebitDate.before(current)){
                    isOverdue = FossConstants.YES;
                }
				// 如果超期部门临欠额度
				if (FossConstants.YES.equals(isOverdue)) {
					notes = "部门已用额度金额已透支，不能选择临欠";
				} 

			}

            else {
				//2013-02-22设计方案变更，结算表中查询不到数据，查询综合管理表数据，新增一条部门收支平衡信息
				usableAmount=saleEntity.getMaxTempArrears();//最大临欠额度
				
			}
		}

		// 判断返回结果
		if (FossConstants.YES.equals(isOverdue)) {
			// 超期欠款
			result.setBeBebt(false);// 不允许欠款
			result.setMessage(notes);
		}
		// 如果可用额度小于欠款额度
		else if (usableAmount.compareTo(debtAmount) < 0) {
			// 额度已经不够
			result.setBeBebt(false);// 不允许欠款

			//对提示信息进行更改 2013-01-22
			result.setMessage("可用额度小于欠款额度，不能进行后续操作");
		} else {
			// 可以欠款
			result.setBeBebt(true);
			result.setMessage("允许欠款");
		}
		return result;
	}

	/**
	 * 扣减客户信用额度
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-19 下午2:46:51
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService#deductionCustomerCredit(java.lang.String,
	 *      java.lang.String, java.math.BigDecimal)
	 */
	@Override
	public boolean updateUsedAmount(String customerCode, String orgCode,
			String debtType, BigDecimal debtAmount, CurrentInfo currentInfo) {

		// 判断欠款方式
		// 判断付款方式是否为空
		if (StringUtils.isEmpty(debtType)) {
			throw new SettlementException("付款方式不能为空，不能进行扣减客户信用额度");

		}
		// 判断付款方式是否为非月结或临欠
		else if (!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
				.equals(debtType)
				&& !SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
						.equals(debtType)) {
			throw new SettlementException("付款方式只有为临欠或月结，才能调用扣减客户/部门信用额度接口");
		}

		// 判断欠款额度
		if (null == debtAmount) {
			throw new SettlementException("传入的欠款额度不能为空");
		}

		// 判断欠款方式
		// 如果欠款方式为月结时，扣减客户信用额度
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(debtType)) {
			// 判断客户编码是否为空
			if (StringUtils.isEmpty(customerCode)) {
				throw new SettlementException("扣减客户信用额度，客户编码不能为空");
			}
			// 扣减客户信息额度
			this.creditCustomerService.updateUsedAmount(customerCode,
					debtAmount);
		}
		// 如果付款方式为欠款时，扣减部门信用额度
		else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT.equals(debtType)) {
			// 判断客户编码是否为空
			if (StringUtils.isEmpty(orgCode)) {
				throw new SettlementException("扣减部门临欠额度，部门编码不能为空");
			}
			// 扣减组织已用额度
			this.creditOrgService.updateUsedAmount(orgCode, debtAmount);
		}

		// 返回成功
		return true;

	}
	
	/**
	 * @author 268217
	 * 整车返回类型
	 */
	@Override
	public LineOfcreditResponseDto getIsBeBebt(String customerCode, String orgCode,
			String debtType, BigDecimal debtAmount) {
		LineOfcreditResponseDto response = new LineOfcreditResponseDto();
		// 判断付款方式是否为空
		if (StringUtils.isEmpty(debtType)) {
			response.setIsNot("接口传入的付款方式不能为空！");
			return response;
		}
		// 判断欠款额度
		if (null == debtAmount) {
			response.setIsNot("欠款额度不能为空！");
			return response;
		}

		// 可用额度
		BigDecimal usableAmount = BigDecimal.ZERO;

		// 如果欠款类别为月结,从数据库中获取可用额度
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
				.equals(debtType)) {
			
			// 判断客户编码是否为空
			if (StringUtils.isEmpty(customerCode)) {
				response.setIsNot("客户编码不能为空！");
				return response;
			}
			String productId="C30005";
			// 从dao层回去相应的数据
			CreditCustomerDto customerDebitVo =creditCustomerService.queryCustomerDebit(customerCode,productId);
			// 对返回结果进行检查
			if (customerDebitVo != null) {
				response.setLongCrdeitDate(customerDebitVo.getMinDebitDate());
                return response;
             }else{
            	 response.setLongCrdeitDate(null);
             }
            return response;
		}
		// 如果欠款类别为临时欠款,从数据库中获取可用额度
		else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
				.equals(debtType)) {
			// 判断营业部编码是否为空
			if (StringUtils.isEmpty(customerCode)) {
				response.setIsNot("客户编码不能为空！");
				return response;
			}
			// 从Dao层获取相应的数据
			CreditOrgDto orgDebitDto = this.creditOrgService
					.queryOrgCreditInfo(orgCode);
			
			BillReceivableConditionDto dto = new BillReceivableConditionDto();
			//设置为有效
			dto.setActive(FossConstants.YES);
			String[] billTypes = new String[]{SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE};
			dto.setBillTypes(billTypes);
			dto.setCustomerCode(customerCode);
			//dto.setOrgCode(orgCode);
			dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
			Date minDate = billReceivableEntityDao.queryMinTimebyCondition(dto);

			//综合管理：营业部信息接口，获取营业部的最大临欠额度
			SaleDepartmentEntity  saleEntity=saleDepartmentService.querySaleDepartmentByCode(orgCode);
			// 对返回结果进行检查
			if (null != orgDebitDto) {
                if(orgDebitDto.getUsedAmount() == null){
                    orgDebitDto.setUsedAmount(BigDecimal.ZERO);
                }
				//部门的最大临欠额度    可用额度
				usableAmount = saleEntity.getMaxTempArrears().subtract(
						orgDebitDto.getUsedAmount());
				
				response.setLongCrdeitDate(minDate);//客户在部门的最长一笔欠款日期
                response.setUnCrdeitAmount(usableAmount);
            }else {
				//2013-02-22设计方案变更，结算表中查询不到数据，查询综合管理表数据，新增一条部门收支平衡信息
				usableAmount=saleEntity.getMaxTempArrears();//最大临欠额度
				response.setUnCrdeitAmount(usableAmount);
				response.setLongCrdeitDate(null);
			}
			return response;
		}
		return response;
	}
	
	public void setCreditOrgService(ICreditOrgService creditOrgService) {
		this.creditOrgService = creditOrgService;
	}

	public void setCreditCustomerService(
			ICreditCustomerService creditCustomerService) {
		this.creditCustomerService = creditCustomerService;
	}

	
	/**
	 * @param saleDepartmentService the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	
	/**
	 * @param cusBargainService the cusBargainService to set
	 */
	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}

    public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }

	public void setBillReceivableEntityDao(
			IBillReceivableEntityDao billReceivableEntityDao) {
		this.billReceivableEntityDao = billReceivableEntityDao;
	}
    
}
