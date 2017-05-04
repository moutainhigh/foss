package com.deppon.foss.module.settlement.financeitf.server.esb;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.finance.VerifyBadBebtsRequest;
import com.deppon.esb.inteface.domain.finance.VerifyBadBebtsResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillBadWriteoffDto;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IBillBadAccountWriteoffService;

/**
 * 核销坏账服务
 * 
 * @author foss-wangxuemin
 * @date 2012-10-10 下午3:30:25
 */
public class VerifyBadDebtsProcessor implements IProcess {

	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(VerifyBadDebtsProcessor.class);

	/**
	 * 注入坏账核销服务
	 */
	private IBillBadAccountWriteoffService billBadAccountWriteoffService;

	/**
	 * 坏账核销
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 29, 2012 3:46:57 PM
	 * @see com.deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object)
	 */
	public Object process(Object obj) throws ESBBusinessException {
		//记录日志
		logger.info("开始核销坏账.");
		//获取接口传入参数
		VerifyBadBebtsRequest request = (VerifyBadBebtsRequest) obj;
		//声明接口返回实体
		VerifyBadBebtsResponse response = new VerifyBadBebtsResponse();
		//获取工作流审批状态
		boolean result = request.isResult();
		logger.info("坏账核销参数，审批状态：" + result);
		//如果审批状态为false，则直接返回处理失败的状态
		if(!result){
			logger.error("工作流号:"+request.getSerialNO()+"为OA审批不同意状态，不能调用核销坏账接口");
			// 返回失败
			response.setResult(false);
			response.setReason("工作流:"+request.getSerialNO()+"OA审批不同意，不能调用接口");
		} 
		//如果审批状态为true，则进行核销处理
		else {
			// 工作流号
			String serialNo = request.getSerialNO();
			// 申请金额(单位：元)
			BigDecimal writeoffAmount = request.getApplyMoney();
			// 应收单编号列表
			List<String> receivableNos = request.getBillNumber();
			// 冲账方式
			String billType = request.getBillType();
			// 设置核销处理Dto
			BillBadWriteoffDto badWriteoffDto = new BillBadWriteoffDto();
			//设置值
			badWriteoffDto.setSerialNo(serialNo);
			badWriteoffDto.setReceivableNos(receivableNos);
			badWriteoffDto.setWriteoffAmount(writeoffAmount);
			badWriteoffDto.setBillType(billType);
	
			try {
				// 调用核销坏账服务处理
				billBadAccountWriteoffService.writeoffBadAccount(badWriteoffDto);
				// 返回成功
				response.setResult(true);
			} catch (BusinessException e) {
				logger.error(e.getErrorCode(), e);
				// 返回失败
				response.setResult(false);
				response.setReason(e.getErrorCode());
			}
		}
		//打印日志
		logger.info("结束核销坏账.");
		//返回信息
		return response;
	}
	
	/**
	 * 异常处理
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 29, 2012 3:46:57 PM
	 * @see com.deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		return null;
	}

	/**
	 * @param billBadAccountWriteoffService
	 */
	public void setBillBadAccountWriteoffService(
			IBillBadAccountWriteoffService billBadAccountWriteoffService) {
		this.billBadAccountWriteoffService = billBadAccountWriteoffService;
	}

}
