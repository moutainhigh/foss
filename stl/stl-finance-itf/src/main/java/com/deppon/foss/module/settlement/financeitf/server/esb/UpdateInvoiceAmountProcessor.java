package com.deppon.foss.module.settlement.financeitf.server.esb;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.finance.InvoiceAmountType;
import com.deppon.esb.inteface.domain.finance.InvoiceProcessType;
import com.deppon.esb.inteface.domain.finance.UpdateInvoiceamountRequestType;
import com.deppon.esb.inteface.domain.finance.UpdateInvoiceamountResponseType;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IInvoiceService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.InvoiceEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceDto;

/**
 * 更新已开票金额.
 *
 * @author ibm-guxinhua
 * @date 2012-11-21 下午7:15:15
 */
public class UpdateInvoiceAmountProcessor implements IProcess {
	
	/** 日志. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateInvoiceAmountProcessor.class);
	
	/** 开发发票服务. */
	private IInvoiceService invoiceService;
	
	/**
	 * 异常处理.
	 *
	 * @param obj the obj
	 * @return the object
	 * @throws ESBBusinessException the eSB business exception
	 * @author ibm-guxinhua
	 * @date 2012-12-25 下午4:18:56
	 * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public Object errorHandler(Object obj) throws ESBBusinessException {
		/**
		 * 打印日志
		 */
		LOGGER.error("更新已开票金额出错:" + obj);
		return null;
	}
	
	/**
	 * 更新已开票金额.
	 *
	 * @param obj the obj
	 * @return the object
	 * @throws ESBBusinessException the eSB business exception
	 * @author ibm-guxinhua
	 * @date 2012-11-21 下午7:15:06
	 * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
	 */
	@Override
	public Object process(Object obj) throws ESBBusinessException {
		//获取传入接口参数
		UpdateInvoiceamountRequestType request = (UpdateInvoiceamountRequestType) obj;
		//获取编号
		String serialNO = request.getSerialNO();
		//获取开票集合
		List<InvoiceAmountType> list = request.getInvoices();
		//打印日志
		LOGGER.info("Process start,serialNO:" + (serialNO == null ? null : serialNO));
		//判空
		if(CollectionUtils.isEmpty(list)){ 
			throw new ESBBusinessException("invoiceAmountTypeList 为空！"); 
		}
		//声明变量
		int successCount = 0,failedCount = 0;
		InvoiceDto dto = null;
		InvoiceProcessType process = null;
		//实例化该变量
		UpdateInvoiceamountResponseType response = new UpdateInvoiceamountResponseType();
		List<InvoiceProcessType> invoiceProcessList = response.getProcessDeails();//处理结果明细
		//声明错误变量
		String message = null;
		//循环更新已开票金额
		for (InvoiceAmountType invoiceAmountType : list) { 
			message = null;
			//判空
			if(null == invoiceAmountType){
				throw new ESBBusinessException("invoiceAmountType 为空！"); 
			}
			//实例化dto
			dto = new InvoiceDto();
			//封装dto信息
			dto.setSourceBillNo(invoiceAmountType.getWaybillNO());
			//封装dto信息
			dto.setAlreadyOpenAmount(invoiceAmountType.getAmount());
			//封装dto信息
			dto.setSourceBillType(invoiceAmountType.getWaybillType());
			//封装dto信息
			dto.setApplyUserCode(invoiceAmountType.getBillingEmpCd());
			//封装dto信息
			dto.setBillingDeptCode(invoiceAmountType.getBillingDeptCd());
			//生宁开票记录实体
			InvoiceEntity entity = null;
			try {
				//调用接口标记已开发票
				entity = invoiceService.markInvoice(dto); // 标记发票已开
			} catch (BusinessException e) {
				message = e.getErrorCode();//异常信息储存
				//打印异常日志
				LOGGER.error(e.getMessage(), e);
				LOGGER.error(message);
				entity = null;
			}
			if(entity != null){ // 标记成功，添加成功信息
				//标记成功，添加成功信息处理
				process = new InvoiceProcessType();
				process.setWaybillNO(invoiceAmountType.getWaybillNO());
				process.setIsSuccess(true);
				invoiceProcessList.add(process);
				successCount++;
			}else{
				//实例化实体
				process = new InvoiceProcessType();
				process.setWaybillNO(invoiceAmountType.getWaybillNO());
				process.setIsSuccess(false);
				//返回异常信息
				if(message != null)
				{ 
					process.setFailedReason(message); 
				}
				invoiceProcessList.add(process);
				failedCount++;
			}
		}
		//封装接口返回信息
		response.setSerialNO(serialNO);
		//封装接口返回信息
		response.setSuccessCount(successCount);
		//封装接口返回信息
		response.setFailedCount(failedCount);
		//记录日志
		LOGGER.info("Process end");
		//返回信息
		return response;
	}

	/**
	 * Sets the invoice service.
	 *
	 * @param invoiceService the new invoice service
	 */
	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
}
