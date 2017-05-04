package com.deppon.foss.module.pickup.tps.server.jms;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.dpap.esb.mqc.core.exception.ESBBusinessException;
import com.deppon.dpap.esb.mqc.core.process.IProcess;

import com.deppon.esb.inteface.domain.vtsbill.VtsWaybillSignResultRequest;
import com.deppon.esb.inteface.domain.vtsbill.VtsWaybillSignResultResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.sign.api.server.service.ITPSSignSettleService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;


/**
 * TPS同步签收结果表至foss异步接口
 */
public class VtsWaybillSignResultProcess implements IProcess {

    /**
     * 日志记录
     */
    private static final Logger logger = LogManager.getLogger(VtsWaybillSignResultProcess.class);
    
	/**
	 * 注入Serive
	 */
	private ITPSSignSettleService tpsSignSettleService;
	
	
	/**
	 *接口响应成功标志 
	 */
	public static final int INTERFACE_RESPONSE_SUCCESS = 1;//调用接口方法成功
	
	/**
	 *接口响应失败标志 
	 */
	public static final int INTERFACE_RESPONSE_FAILED = 0;//调用接口方法失败

	/**
     * TPS同步签收结果表至foss
     * @author 330547	
     * @date 2017-3-5
     * @see com.deppon.esb.core.process.IProcess#process(Object)
     */
    @Override
    public Object process(Object obj) throws ESBBusinessException {
    	logger.info("**********TPS同步签收结果信息到foss开始*************");
        // 获取接口参数
    	VtsWaybillSignResultRequest request = (VtsWaybillSignResultRequest) obj;
        // 接口返回信息
    	VtsWaybillSignResultResponse response = new VtsWaybillSignResultResponse();
        try {
			// 非空判断
			if (request == null) {
				throw new SignException("传入参数为空,不能进行新增操作！");
			}
			
			if(StringUtils.isEmpty(request.getBusinessId())){
				logger.error("TPS传入foss业务id为空！运单号："+request.getWaybillNo());
				throw new SignException("TPS传入foss业务id为空！");
			}
			
			if (StringUtils.isEmpty(request.getWaybillNo())) {
				logger.error("TPS传入foss运单号为空！运单号："+request.getWaybillNo());
				throw new SignException("TPS传入foss运单号为空！");
			}
			
			if (StringUtils.isEmpty(request.getSignSituation())) {
				logger.error("TPS传入foss签收情况为空！运单号："+request.getWaybillNo());
				throw new SignException("TPS传入foss签收情况为空！");
			}
			
			if (StringUtils.isEmpty(request.getSignGoodsQty())) {
				logger.error("TPS传入foss签收件数为空！运单号："+request.getWaybillNo());
				throw new SignException("TPS传入foss签收件数为空！");
			}
			
			if (StringUtils.isEmpty(request.getActive())) {
				logger.error("TPS传入foss是否有效为空！运单号："+request.getWaybillNo());
				throw new SignException("TPS传入foss是否有效为空！");
			}

			
			/**
			 * 封装参数到运单实体
			 */
			WaybillSignResultEntity entity = new WaybillSignResultEntity();
			BeanUtils.copyProperties(entity, request);
			
			tpsSignSettleService.signSettle(entity);
			
			// 插入成功
			response.setWaybillNo(request.getWaybillNo());
			//业务id
			response.setBusinessId(request.getBusinessId());
			response.setResult(INTERFACE_RESPONSE_SUCCESS);
			response.setReason("TPS同步签收结果表至foss成功！");
			logger.info("TPS同步签收结果表至foss成功！业务id："+request.getBusinessId()+"运单号："+request.getWaybillNo());
		} catch (RepaymentException re) {
			logger.info("TPS同步签收结果表至foss失败！业务id："+request.getBusinessId()+"运单号："+request.getWaybillNo()+re.getErrorCode());
			// 日志处理
			logger.error(re.getErrorCode(), re);
			// 运单号
			response.setWaybillNo(request.getWaybillNo());
			//业务id
			response.setBusinessId(request.getBusinessId());
			// 返回结果
			response.setResult(INTERFACE_RESPONSE_FAILED);
			// 返回信息
			response.setReason("TPS同步签收结果表至foss失败！" + re.getErrorCode());
		}  catch (BusinessException be) {
			logger.info("TPS同步签收结果表至foss失败！业务id："+request.getBusinessId()+"运单号："+request.getWaybillNo()+be.getErrorCode());
			// 日志处理
			logger.error(be.getErrorCode(), be);
			// 运单号
			response.setWaybillNo(request.getWaybillNo());
			//业务id
			response.setBusinessId(request.getBusinessId());
			// 返回结果
			response.setResult(INTERFACE_RESPONSE_FAILED);
			// 返回信息
			response.setReason("TPS同步签收结果表至foss失败！" +be.getErrorCode());
		} catch (Exception e) {
			logger.info("TPS同步签收结果表至foss失败！业务id："+request.getBusinessId()+"运单号："+request.getWaybillNo()+e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);
			// 运单号
			response.setWaybillNo(request.getWaybillNo());
			//业务id
			response.setBusinessId(request.getBusinessId());
			// 返回结果
			response.setResult(INTERFACE_RESPONSE_FAILED);
			// 返回信息
			response.setReason("TPS同步签收结果表至foss失败！"+e.getMessage());
		}
		logger.info("**********TPS同步签收结果表信息到foss结束*************");
        return response;
    }

    /**
     * 错误处理
     *
     * @see com.deppon.esb.core.process.IProcess#errorHandler(Object)
     */
    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
        // 错误日志
    	logger.error(req.getClass().getName() + "TPS同步签收结果表信息至foss错误！");
        return null;
    }

	public void setTpsSignSettleService(ITPSSignSettleService tpsSignSettleService) {
		this.tpsSignSettleService = tpsSignSettleService;
	}

}
