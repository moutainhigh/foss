package com.deppon.foss.module.settlement.ptpitf.server.esb;

import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivablePtpChangeService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillNewReceivablePtpDto;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.receivable.BillRecevivableChangedRequest;
import com.deppon.esb.inteface.domain.receivable.BillRecevivableChangedResponse;
import com.deppon.foss.framework.exception.BusinessException;

/**
 * 合伙人财务更改后红冲原应收单，并生成新应收单
 * @author 黄乐为
 * @date 2016-1-14 下午4:14:15
 */
public class BillReceivableFinaChangeProcessor implements IProcess {
	
	/**
	 * 更新现金缴款信息logger
	 */
	private static final Logger logger = LogManager.getLogger(BillReceivableFinaChangeProcessor.class);


	/**
	 * 更改生成应付单service
	 */
	private IBillReceivablePtpChangeService billReceivablePtpChangeService;
	
	/**
     * 错误消息日志记录service
     */
    private IEsbInterfaceLogService esbInterfaceLogService;
	
	/**
	 * 红冲并生成新应收单接口
	 * @author 黄乐为
	 * @date 2016-1-14 下午4:14:28
	 * @param req
	 * @return
	 * @throws ESBBusinessException
	 */
	@SuppressWarnings("finally")
	@Override
	public Object process(Object req) throws ESBBusinessException {
		logger.info("FOSS开始更改应收单");
		BillRecevivableChangedRequest request = (BillRecevivableChangedRequest) req;
		BillRecevivableChangedResponse response = new BillRecevivableChangedResponse();
		if(null ==request || null ==request.getWaybillNo() ){
			logger.error("FOSS更改合伙人应收单传入的参数为空或者运单号为空，");
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("传入的参数为空或者运单号为空");//失败原因
			return response;
		}
		
		//生成新的应收单
		//if (request != null && request.getWaybillNo() != null) {
			//日志记录接口参数信息
			logger.info(request.getWaybillNo());
			// 获取传递信息
			String waybillNo = request.getWaybillNo();//运单号
			String billType = request.getBillType();//运单类型
			response.setWaybillNo(waybillNo);//响应运单号
			response.setId(request.getId());//流水id
			//验证...
			try {
				BillNewReceivablePtpDto dto = billReceivablePtpChangeService.buildDto(request);
				//调用合伙人公共红冲接口:作废原应收单生成红单
				if(null != dto) {
					billReceivablePtpChangeService.writeBackBillReceivable(dto);
					response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_SUCCESS);//处理成功标志
					response.setReason("FOSS结束更改应收单，成功");
				}else{
					response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理成功标志
					response.setReason("FOSS结束更改应收单，应收单金额和为0，不生成此项应收单");
				}
				//异常处理
			} catch (BusinessException ex) {
				response.setWaybillNo(waybillNo);//响应编码
				logger.error("FOSS更改合伙人应收单错误，" + ex.getErrorCode(), ex);
				response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
				response.setReason("运单号:"+waybillNo+"运单类型"+billType+"，。接收请求信息错误，"+ex.getErrorCode());//失败原因
				
				// 记录错误日志到数据库
				esbInterfaceLogService.savePtpInterfaceLog(req,
						ex.getErrorCode() + "\n" + ExceptionUtils.getFullStackTrace(ex));
			} catch (Exception e) {
				response.setWaybillNo(waybillNo);//响应编码
				logger.error("FOSS更改合伙人应收单发生未知错误，" + e.getMessage(), e);
				response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
				response.setReason("运单号:"+waybillNo+"运单类型"+billType+"。接收请求信息错误，"+e.getMessage());//失败原因
				
				// 记录错误日志到数据库
				esbInterfaceLogService.savePtpInterfaceLog(req, e.getMessage()
						+ "\n" + ExceptionUtils.getFullStackTrace(e));
			}finally{
				logger.info("FOSS结束更改应收单");
				return response;
			}
		/*} else {
			logger.error("FOSS更改合伙人应收单传入的参数为空或者运单号为空，");
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("传入的参数为空或者运单号为空");//失败原因
		}*/
	}

	/**
	 * 错误处理
	 * @author 黄乐为
	 * @date 2016-1-14 下午4:14:32
	 * @param req
	 * @return
	 * @throws ESBBusinessException
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		return null;
	}

	public IBillReceivablePtpChangeService getBillReceivablePtpChangeService() {
		return billReceivablePtpChangeService;
	}

	public void setBillReceivablePtpChangeService(IBillReceivablePtpChangeService billReceivablePtpChangeService) {
		this.billReceivablePtpChangeService = billReceivablePtpChangeService;
	}

	/**
	 * @return the esbInterfaceLogService
	 */
	public IEsbInterfaceLogService getEsbInterfaceLogService() {
		return esbInterfaceLogService;
	}

	/**
	 * @param esbInterfaceLogService the esbInterfaceLogService to set
	 */
	public void setEsbInterfaceLogService(
			IEsbInterfaceLogService esbInterfaceLogService) {
		this.esbInterfaceLogService = esbInterfaceLogService;
	}
	
}
