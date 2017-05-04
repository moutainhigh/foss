package com.deppon.foss.module.settlement.dubbo.api.service.impl.expose;

import java.math.BigDecimal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deppon.foss.dubbo.ptp.api.define.UpdateWithholdStatusEntity;
import com.deppon.foss.dubbo.ptp.api.define.exception.SettlementException;
import com.deppon.foss.dubbo.ptp.api.service.UpdateWithholdStatusService;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableEntity;
import com.deppon.foss.module.settlement.dubbo.api.service.IBillReceivableService4dubbo;

/**
 * 修改应收单扣款状态
 * 
 * @ClassName: UpdateWithholdStatusServiceImpl
 * @author 099995-foss-hemingyu
 * @date 2016-01-12 上午18:23:38
 */
@Service
public class UpdateWithholdStatusServiceImpl4dubbo implements UpdateWithholdStatusService {
	/**
	 * 日志属性
	 */
	private static final Logger logger = LogManager.getLogger(UpdateWithholdStatusServiceImpl4dubbo.class);
	/**
	 * 注入Service
	 * <p/>
	 * 修改应收单扣款状态 2016-01-12 上午18:23:38
	 */
	private IBillReceivableService4dubbo billReceivableService;

	/**
	 * 修改应收单扣款状态
	 *
	 * @param 应收单实体
	 * @author 099995-foss-hemingyu
	 * @date 2016-01-12 上午18:23:38
	 */
	@Override
	public UpdateWithholdStatusEntity updateWithholdStatus(UpdateWithholdStatusEntity billReceivable) {
		// 记录日志
		logger.info("开始接收合伙人计价平台发送的扣款状态.");
		if (billReceivable == null) {
			logger.error("传入实体为空，失败！");
			throw new SettlementException("传入实体为空，失败！");
		}
		// 获取返回信息(改成此业务的类)
		UpdateWithholdStatusEntity db = new UpdateWithholdStatusEntity();
		{
			// 获取接口的运单号
			String wayBillNo = billReceivable.getWayBillNo();
			// 获取接口的运单号的应收类型
			String billType = billReceivable.getBillType();
			// 获取接口的运单号扣款状态
			String withholdStatus = billReceivable.getWithholdStatus();
			logger.info("接受修改应收单扣款状态接口参数。运单号：" + wayBillNo + "应收单单据类型：" + billType + "扣款状态：" + withholdStatus);
			try {
				db.setWayBillNo(wayBillNo);
				db.setBillType(billType);
				// 根据运单号与应收类型找到需要更新的数据
				BillReceivableEntity entity = getBillReceivableService().selectByWayBillNoAndBillType(wayBillNo, billType);
				if (entity == null) {
					throw new SettlementException(String.format("不存在运单号为:%s的有效应收单，请检查数据是否正确", wayBillNo));
				}

				// 应收单是否已结清货款
				// 未核销金额
				BigDecimal unverifyAmount = entity.getUnverifyAmount() == null ? BigDecimal.ZERO
						: entity.getUnverifyAmount();
				if (unverifyAmount.compareTo(BigDecimal.ZERO) == 0) {// 已结清
					logger.info("已结清货款，不能再更新扣款状态！");
					throw new BusinessException("合伙人计价平台发送的扣款状态接口失败！已结清货款，不能再更新扣款状态！");
				}

				entity.setWithholdStatus(withholdStatus);
				getBillReceivableService().updateBillReceivableWithholdStatus(entity);
				db.setIsSuccess("true");
			} catch (SettlementException e) {
				logger.error(e.getErrorCode());
				db.setErrorType("2");
				db.setErrorMsg(e.getMessage());
//				throw e;
			}
			catch (BusinessException be) {
				logger.error("合伙人计价平台发送的扣款状态业务异常。已结清货款，不能再更新扣款状态！");
				db.setErrorType("1");
				db.setErrorMsg("合伙人计价平台发送的扣款状态业务异常。已结清货款，不能再更新扣款状态！");
//				throw new SettlementException("已结清货款，不能再更新扣款状态！");
			} catch (Exception e1) {
				logger.error("合伙人计价平台发送的扣款状态系统异常。" + e1.getMessage());
				db.setErrorType("3");
				db.setErrorMsg("合伙人计价平台发送的扣款状态系统异常。" + e1.getMessage());
//				throw new SettlementException("合伙人计价平台发送的扣款状态系统异常。" + e1.getMessage());
			}
			logger.info("合伙人计价平台发送的扣款状态结束");
			return db;
		}
	}

	public IBillReceivableService4dubbo getBillReceivableService() {
		return billReceivableService;
	}
	@Autowired
	public void setBillReceivableService(IBillReceivableService4dubbo billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

}
