package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VerificationEntity;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.foss.delivery.server.ISettlementPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.AccountStatementEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.BushCardDetailEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetBushCardEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetPaymentDeatilSuccessEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetPaymentSuccessEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PayInfoDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PayInfoDetailDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PrepaymentEntity;

public class SettlementPdaToCubcService implements ISettlementPdaToCubcService {
	// 封装CUBC对账单刷卡信息
	public PayInfoDO wrapCommonQueryPayInfoDO(AsyncMsg asyncMsg,
			AccountStatementEntitys param) {
		PayInfoDO payInfoDO = new PayInfoDO();
		List<PayInfoDetailDO> payInfoDetailList = new ArrayList<PayInfoDetailDO>();
		PayInfoDetailDO payInfoDetailDO=null;
		//创建人编码
		payInfoDO.setCreateUserCode(param.getCreateUserCode());
		//创建人名称
		payInfoDO.setCreateUserName(param.getCreateUserName());
		//客户编码
		payInfoDO.setCustomerCode(param.getCustomerCode());
		//客户名称
		payInfoDO.setCustomerName(param.getCustomerName());
		//刷卡部门编号
		payInfoDO.setPayDeptCode(asyncMsg.getDeptCode());
		//刷卡部门名称
		payInfoDO.setPayDeptName(param.getCardDeptName());
		//刷卡时间
		payInfoDO.setPayDate(param.getOperateTime());
		//流水号
		payInfoDO.setUniqueId(param.getTradeSerialNo());
		//去重
		payInfoDO.setPayCode(param.getTradeSerialNo());
		//刷卡金额
		payInfoDO.setAmount(param.getSerialAmount());
		//业务类型
		payInfoDO.setBizHandle("NCI_CD_DZ");
		List<PosCardDetailEntity> posCardDetailEntity=param.getPosCardDetailEntitys();
		for (PosCardDetailEntity entitys : posCardDetailEntity) {
            payInfoDetailDO = new PayInfoDetailDO();
			// 运单号
            payInfoDetailDO.setRelatedTarget(entitys.getInvoiceNo());
			// 刷卡操作人编号
            payInfoDetailDO.setCustomerCode(asyncMsg.getUserCode());
			// 交易流水号
            payInfoDetailDO.setPayCode(entitys.getTradeSerialNo());
            // 客户编码
            payInfoDetailDO.setCustomerCode(param.getCustomerCode());
            // 客户名称
            payInfoDetailDO.setCreateUserName(param.getCreateUserName());
            // 明细金额
            payInfoDetailDO.setAmount(entitys.getAmount());
            payInfoDetailList.add(payInfoDetailDO);
		}
		payInfoDO.setPayInfoDetailDO(payInfoDetailList);
		return payInfoDO;
	}

	// 封装CUBC待刷卡信息上传
	@Override
	public PayInfoDO wrapRecordQueryPayInfoDO(AsyncMsg asyncMsg,
			GetBushCardEntitys param) {
		PayInfoDO payInfoDO = new PayInfoDO();
		List<PayInfoDetailDO> payInfoDetailList = new ArrayList<PayInfoDetailDO>();
		PayInfoDetailDO payInfoDetailDO=null;
		// 创建人编码
		payInfoDO.setCreateUserCode(param.getCreateUserCode());
		// 创建人名称
		payInfoDO.setCreateUserName(param.getCreateUserName());
		// 客户编码
		payInfoDO.setCustomerCode(param.getBushCardDetailEntity().get(0).getSendCustomerCode());
		// 客户名称
		payInfoDO.setCustomerName(param.getBushCardDetailEntity().get(0).getSendCustomerName());
		//刷卡部门编号
		payInfoDO.setPayDeptCode(asyncMsg.getDeptCode());
		//刷卡部门名称
		payInfoDO.setPayDeptName(param.getCardDeptName());
		//刷卡时间
		payInfoDO.setPayDate(param.getOperateTime());
		//流水号
		payInfoDO.setUniqueId(param.getTradeSerialNo());
		payInfoDO.setPayCode(param.getTradeSerialNo());
		//刷卡金额
		payInfoDO.setAmount(param.getSerialAmount());
		//业务类型
		payInfoDO.setBizHandle("NCI_CD_WS");
		List<BushCardDetailEntity> bushCardDetailEntity=param.getBushCardDetailEntity();
		for (BushCardDetailEntity entitys : bushCardDetailEntity) {
            payInfoDetailDO = new PayInfoDetailDO();
			// 运单号
            payInfoDetailDO.setRelatedTarget(entitys.getInvoiceNo());
			// 交易流水号
            payInfoDetailDO.setPayCode(entitys.getTradeSerialNo());
            // 运单金额
            payInfoDetailDO.setAmount(entitys.getAmount());
            payInfoDetailList.add(payInfoDetailDO);
		}
		payInfoDO.setPayInfoDetailDO(payInfoDetailList);
		return payInfoDO;
	}

	// 封装CUBC预存款刷卡信息上传
	@Override
	public PayInfoDO wrapPosCardQueryPayInfoDO(AsyncMsg asyncMsg,
			PrepaymentEntity param) {
		PayInfoDO payInfoDO = new PayInfoDO();
		List<AccountStatementEntitys> accountStatementEntitys=param.getAccountStatementEntitys();
		for (AccountStatementEntitys entity : accountStatementEntitys) {
			// 创建人编码
			payInfoDO.setCreateUserCode(entity.getCreateUserCode());
			// 创建人名称
			payInfoDO.setCreateUserName(entity.getCreateUserName());
			// 客户编码
			payInfoDO.setCustomerCode(entity.getCustomerCode());
			// 客户名称
			payInfoDO.setCreateUserName(entity.getCreateUserName());
			// 刷卡部门编号
			payInfoDO.setPayDeptCode(asyncMsg.getDeptCode());
			// 刷卡部门名称
			payInfoDO.setPayDeptName(entity.getCardDeptName());
			// 刷卡时间
			payInfoDO.setPayDate(entity.getOperateTime());
			// 流水号
			payInfoDO.setUniqueId(entity.getTradeSerialNo());
			payInfoDO.setPayCode(entity.getTradeSerialNo());
			// 刷卡金额
			payInfoDO.setAmount(entity.getSerialAmount());
			// 打款方式
			payInfoDO.setPayType("CD");
			// 业务类型
			payInfoDO.setBizHandle("NCI_CD_US");
		}
		// payInfoDO.setPayInfoDetailDO(payInfoDetailList);
		return payInfoDO;
	}

	// 封装结清货款刷卡数据上传
	@Override
	public PayInfoDO settlementOfPaymentPosCard(AsyncMsg asyncMsg,
			GetPaymentSuccessEntitys param) {
		PayInfoDO payInfoDO = new PayInfoDO();
		List<PayInfoDetailDO> payInfoDetailList = new ArrayList<PayInfoDetailDO>();
		PayInfoDetailDO payInfoDetailDO=null;
		// 创建人编码
		payInfoDO.setCreateUserCode(param.getCreateUserCode());
		// 创建人名称
		payInfoDO.setCreateUserName(param.getCreateUserName());
		// 刷卡部门编号
		payInfoDO.setPayDeptCode(asyncMsg.getDeptCode());
		// 刷卡部门名称
		payInfoDO.setPayDeptName(param.getCardDeptName());
		// 刷卡时间
		payInfoDO.setPayDate(param.getOperateTime());
		// 流水号
		payInfoDO.setUniqueId(param.getTradeSerialNo());
		payInfoDO.setPayCode(param.getTradeSerialNo());
		//刷卡金额
		payInfoDO.setAmount(param.getSerialAmount());
		//业务类型
	    payInfoDO.setBizHandle("NCI_SM_CD_ORDER");
	    //是否司机
	    payInfoDO.setIsDriver("Y");
		List<GetPaymentDeatilSuccessEntity> posCardDetailEntity=param.getGetPaymentDeatilSuccessEntity();
		for (GetPaymentDeatilSuccessEntity entitys : posCardDetailEntity) {
            payInfoDetailDO = new PayInfoDetailDO();
			// 运单号
            payInfoDetailDO.setRelatedTarget(entitys.getInvoiceNo());
			// 刷卡操作人编号
            //payInfoDetailDO.setCustomerCode(asyncMsg.getUserCode());
			// 刷卡操作人名称
            //payInfoDetailDO.setCustomerName(entitys.getSendCustomerName());
			// 交易流水号
            payInfoDetailDO.setPayCode(entitys.getTradeSerialNo());
            // 金额
            payInfoDetailDO.setAmount(entitys.getAmount());
            payInfoDetailList.add(payInfoDetailDO);
		}
		payInfoDO.setPayInfoDetailDO(payInfoDetailList);
		return payInfoDO;
	}

	// 支付宝条码结清货款信息上传
	@Override
	public PayInfoDO setVerificationPosCard(VerificationEntity entity,String isSettleCredit) {
		PayInfoDO payInfoDO = new PayInfoDO();
		// 创建人编码
		payInfoDO.setCreateUserCode(entity.getEmpCode());
		// 创建人名称
		payInfoDO.setCreateUserName(entity.getEmpName());
		// 刷卡部门编号
		payInfoDO.setPayDeptCode(entity.getOrgCode());
		// 刷卡部门名称
		payInfoDO.setPayDeptName(entity.getOrgName());
		// 刷卡时间
		payInfoDO.setPayDate(new Date());
		// 支付宝交易账号
		payInfoDO.setAccountNo(entity.getTradeNo());
		// 打款编码
		payInfoDO.setPayCode("ZFBCODE"+entity.getOutTradeNo());
		// 刷卡金额
		payInfoDO.setAmount(entity.getTotalAmount());
		// 运单号和对账单号
		payInfoDO.setRelatedTarget(entity.getOutTradeNo());
		// 去重
		payInfoDO.setUniqueId(payInfoDO.getPayCode());
		// 业务类型
		if(isSettleCredit.equals("isSettleCredit"))
	       payInfoDO.setBizHandle("NCI_SM_ZFB_ORDER");
		else if(isSettleCredit.equals("isAccountStatement"))
		   payInfoDO.setBizHandle("NCI_ZFB_DZ");
	    //打款方式
	    payInfoDO.setPayType("OL");
	    //是否司机
	    payInfoDO.setIsDriver("Y");
		return payInfoDO;
	}

}
