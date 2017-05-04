package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.payment.CallCreatePaymentBillWorkflowFossRequest;
import com.deppon.esb.inteface.domain.payment.ExpenseDetail;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IOrgShareService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OrgShareEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IPayToCostcontrolSendService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PayToCostcontrolDto;
import com.deppon.foss.util.define.FossConstants;


/**
 * 生成费控付款工作流接口
 * @author 045738-foss-maojianqiang
 * @date 2012-11-30 上午10:08:57
 */
public class PayToCostcontrolSendService implements IPayToCostcontrolSendService{

	/**
	 * Logger 预付单服务
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(PayToCostcontrolSendService.class);
	/**
	 * 服务编码
	 */
	private static final String COSTCONTROL_DEAL_PAY_WORKFLOW = "ESB_FOSS2ESB_CALLCREATEWORKFLOWFORFOSS";
	
	/**
	 * 版本编号
	 */
	private static final String version = "1.0";
	/**
	 * 注入分摊部门servbice
	 */
	private IOrgShareService orgShareService;
	/**
	 * 注入部门service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	
	/** 
	 * 将数据推给费控
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-30 上午10:09:26
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPayToCostcontrolSendService#PayToCostcontrol(com.deppon.foss.module.settlement.pay.api.shared.dto.PayToCostcontrolDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void payToCostcontrol(PayToCostcontrolDto dto) throws Exception{
		LOGGER.info("发送给费控开始！");
		//校验dto
		validatorParams(dto);
		
		//声明要传递的值
		CallCreatePaymentBillWorkflowFossRequest request = new CallCreatePaymentBillWorkflowFossRequest();
		request.setPaymentBillNo(dto.getBatchNo());//批次号
		request.setPayBillAmount(dto.getPayBillAmount());//付款金额
		request.setPayBillAppNo(dto.getPayBillAppNo());//申请人工号
		request.setPayBillAppType(dto.getPayBillAppType());//类型
		request.setPayBillBankNo(dto.getPayBillBankNo());//银行账号
		request.setAccountType(dto.getAccountType());//账户类型
		request.setAccountBankNumber(dto.getBankHqCode());//开户行编码
		request.setAccountBranchBankNumber(dto.getBankBranchCode());//支行编码
		request.setAccountCityNumber(dto.getCityCode());//城市编码
		request.setAccountProviceNumber(dto.getProvinceCode());//省份编码
		request.setPayBillPayeeName(dto.getPayBillPayeeName());//收款人名称
		request.setPayBillPayeeCode(dto.getPayBillPayeeCode());//收款人编码
		request.setPayBillCelephone(dto.getPayBillCelephone());//收款人手机
		request.setPayBillComNo(dto.getPayBillComNo());//发票抬头
		request.setPayBillDeptName(dto.getPayBillDeptName());//付款部门
		request.setPayBillDeptNo(dto.getPayBillDeptNo());//部门标杆编码
		request.setPayBillLastTime(dto.getPayBillLastTime());//最迟汇款日期
		request.setPeyBillDiscription(dto.getPayBillDiscription());//描述
		request.setPayBillLastTime(null);//最迟汇款日期
		request.setPayType(dto.getPayType());//支付类型
		request.getExpenseDetail().addAll(dto.getExpenseDetail());//明细
		if(CollectionUtils.isNotEmpty((dto.getStowageEntityList()))){
			request.getStowageList().addAll(dto.getStowageEntityList());//配载单信息
		}
		//批次号规则 
		String batchNo = dto.getBatchNo();
		AccessHeader header = buildHeader(batchNo);//工作流
		// 发送预付款申请到ESB
		ESBJMSAccessor.asynReqeust(header, request);
		LOGGER.info("批次号："+dto.getBatchNo()+"发送给费控结束！");
	}
	/**
	 * 生成工作流设置ESB请求头
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-30 上午10:09:26
	 */
	private AccessHeader buildHeader(String batchNo) {
		AccessHeader header = new AccessHeader();
		//绑定业务id
		header.setBusinessId(batchNo);
		//服务编码
		header.setEsbServiceCode(COSTCONTROL_DEAL_PAY_WORKFLOW);
		// 处理工作流审批结果
		header.setVersion(version);
		header.setBusinessDesc1("付款申请");
		return header;
	}
	
	/**
	 * 校验dto参数都不能为空
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午3:23:54
	 */
	private void validatorParams(PayToCostcontrolDto dto){
		//校验付款编号
		if(StringUtils.isBlank(dto.getPaymentBillNo())){
			//抛出异常
			throw new SettlementException("传入付款编号为空！");
		}
		//校验付款部门名称
		if(StringUtils.isBlank(dto.getPayBillDeptName())){
			//抛出异常
			throw new SettlementException("传入付款部门名称为空！");
		}
		//校验付款部门编码
		if(StringUtils.isBlank(dto.getPayBillDeptNo())){
			//抛出异常
			throw new SettlementException("传入付款部门编码为空！");
		}
		//校验付款总金额
		if(dto.getPayBillAmount()==null || dto.getPayBillAmount().compareTo(BigDecimal.ZERO)<=0){
			//抛出异常
			throw new SettlementException("传入付款金额不能空且必须大于0！");
		}
		//校验付款子公司--发票抬头
		if(StringUtils.isBlank(dto.getPayBillComNo())){
			//抛出异常
			throw new SettlementException("传入发票抬头编码为空！");
		}
		//校验银行账号
		if(StringUtils.isBlank(dto.getPayBillBankNo())){
			//抛出异常
			throw new SettlementException("付款银行账号为空！");
		}
		//校验账户类型
		if(StringUtils.isBlank(dto.getAccountType())){
			//抛出异常
			throw new SettlementException("账户类型不能为空！");
		}
		//校验开户行编码
		if(StringUtils.isBlank(dto.getBankHqCode())){
			//抛出异常
			throw new SettlementException("开户行编码不能为空！");
		}
		//校验支行编码（行号）
		if(StringUtils.isBlank(dto.getBankBranchCode())){
			//抛出异常
			throw new SettlementException("开户行支行编码不能为空！");
		}
		//校验省份编码
		if(StringUtils.isBlank(dto.getProvinceCode())){
			//抛出异常
			throw new SettlementException("开户行所在省份编码不能为空！");
		}
		//校验城市编码
		if(StringUtils.isBlank(dto.getPayBillBankNo())){
			//抛出异常
			throw new SettlementException("开户行所在城市编码不能为空！");
		}
		
		//校验收款人姓名
		if(StringUtils.isBlank(dto.getPayBillPayeeName())){
			//抛出异常
			throw new SettlementException("传入收款人姓名为空！");
		}
		//校验联系方式
		if(StringUtils.isBlank(dto.getPayBillCelephone())){
			//抛出异常
			throw new SettlementException("传入收款人联系方式为空！");
		}
		//校验申请人工号
		if(StringUtils.isBlank(dto.getPayBillAppNo())){
			//抛出异常
			throw new SettlementException("传入申请人工号为空！");
		}
		//校验工作流类型
		if(StringUtils.isBlank(dto.getPayBillAppType())){
			//抛出异常
			throw new SettlementException("传入工作流类型为空！");
		}
		//校验支付类型
		if(StringUtils.isBlank(dto.getPayType())){
			//抛出异常
			throw new SettlementException("传入支付类型为空！");
		}
		//校验费用明细
		if(CollectionUtils.isEmpty(dto.getExpenseDetail())){
			//抛出异常
			throw new SettlementException("传入费用明细为空！");
		}
		//增加校验 明细总条数不超过100条 由于费控那边有限制，故而foss也要加
		if(dto.getExpenseDetail().size()>100){
			//抛出异常
			throw new SettlementException("传入费用明细总条数不能超过100条！");
		}
			
		//校验批次号，给esb头用，方便之后查询消息状态
		if(StringUtils.isBlank(dto.getBatchNo())){
			//抛出异常
			throw new SettlementException("传入付款批次号为空！");
		}
	}
	/** 
	 * 进行理赔应付单拆分
	 * @author 045738-foss-maojianqiang
	 * @date 2013-2-1 上午11:00:04
	 * @param detail
	 * @param payableNo
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IPayToCostcontrolSendService#sharePayableForBad(java.util.List, java.lang.String)
	 */
	@Override
	public void sharePayableForBad(List<ExpenseDetail> detail,BillPayableEntity entity,String payDesc) {
		List<OrgShareEntity> orgList = orgShareService.selectBySourceBillNo(entity.getPayableNo(), FossConstants.ACTIVE);
		BigDecimal paymentAmount = entity.getPaymentAmount();
		//如果不为空，则将其进行分摊
		if(CollectionUtils.isNotEmpty(orgList)){
			//声明分摊总金额
			BigDecimal shareAmountTotal = BigDecimal.ZERO;
			//声明分摊总金额
			BigDecimal shareAmountSum = BigDecimal.ZERO;
			
			//循环获取总金额
			for(OrgShareEntity sh:orgList){
				shareAmountTotal = shareAmountTotal.add(sh.getAmount());
			}
			//如果分摊总金额为0，则抛出异常
			if(shareAmountTotal.compareTo(BigDecimal.ZERO)==0){
				throw new SettlementException("应付单："+entity.getPayableNo()+"对应的分摊明细金额之和为0，不能进行按比例拆分！");
			}
			//循环添加明细
			for(int i=0;i<orgList.size();i++){
				OrgShareEntity os = orgList.get(i);//获取分摊部门实体
				ExpenseDetail d = new ExpenseDetail();
				d.setBillNumber(entity.getPayableNo());//明细单号
				d.setExpenseExplain(entity.getWaybillNo());//费用说明
				//转化给对应费控那边费用明细
				String payType = BillPaymentPayService.convertBillType(entity.getPayableNo(),entity.getBillType());
				d.setExpensesType(payType);
				d.setPayBillExpDate(entity.getBusinessDate());//业务发生日期
				//如果对应的费用承担部门编码为空，则抛出异常
				if(StringUtils.isBlank(os.getOrgCode())){
					throw new SettlementException("应付单："+entity.getPayableNo()+"对应的分摊部门的标杆编码不存在，不能进行付款操作！");
				}
				d.setExpenseCostCenter(os.getOrgCode());//费用承担部门
				
				//计算明细费用 。分摊明细金额，按照比例拆分应付单对应的付款金额。
				//拆分方案:默认采用截取方式，截取到整数。最后一位截取到小数，前面都按比例，最后一个用总金额减去前面金额
				if(i!=orgList.size()-1){
					//获取拆分后的金额
					BigDecimal sharedAmount = paymentAmount.multiply(os.getAmount()).divide(shareAmountTotal,BigDecimal.ROUND_DOWN);
					d.setExpensesMoney(sharedAmount);//明细金额
					//累加拆分金额
					shareAmountSum = shareAmountSum.add(sharedAmount);
				}else{
					d.setExpensesMoney(paymentAmount.subtract(shareAmountSum));//明细金额
				}
				detail.add(d);
			}
		}else{
			//实例化明细
			ExpenseDetail d = new ExpenseDetail();
			d.setBillNumber(entity.getPayableNo());//明细单号
			d.setExpenseExplain(entity.getWaybillNo());//费用说明
			d.setExpensesMoney(entity.getPaymentAmount());//明细金额
			//转化给对应费控那边费用明细
			String payType = BillPaymentPayService.convertBillType(entity.getPayableNo(),entity.getBillType());
			//设置支付类型呢
			d.setExpensesType(payType);
			d.setPayBillExpDate(entity.getBusinessDate());//业务发生日期
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getPayableOrgCode());
			//校验部门
			if(org!=null && StringUtils.isNotBlank(org.getUnifiedCode())){
				d.setExpenseCostCenter(org.getUnifiedCode());// 获取部门标杆编码
			}else{
				//抛出异常
				throw new SettlementException("应付单"+entity.getPayableOrgCode()+"的付款部门标杆编码不存在！不能申请备用金");
			}
			//添加明细
			detail.add(d);
		}
		
	}
	
	/**
	 * @return orgShareService
	 */
	public IOrgShareService getOrgShareService() {
		return orgShareService;
	}

	/**
	 * @param orgShareService
	 */
	public void setOrgShareService(IOrgShareService orgShareService) {
		this.orgShareService = orgShareService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	   
	
}
