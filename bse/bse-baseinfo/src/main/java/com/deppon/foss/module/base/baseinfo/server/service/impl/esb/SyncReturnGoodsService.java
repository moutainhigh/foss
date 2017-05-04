package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncReturnGoodsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsResponseEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.util.define.ESBHeaderConstant;

/**
 * 返货工单上报服务端
 * @author 198771
 *
 */
public class SyncReturnGoodsService implements ISyncReturnGoodsService{
	/**
	 * 日志
	 */
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncReturnGoodsService.class);


	private IReturnGoodsRequestEntityService  returnGoodsRequestEntityService;

	/**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
    @Context
    protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;
	
	
	
	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	@Override
	public ReturnGoodsResponseEntity addReturnGoods(String returnBill) {
		resp.setHeader(ESBHeaderConstant.VERSION, req.getHeader(ESBHeaderConstant.VERSION));
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_CRM_SELECT_GOODS_ORDER");
		resp.setHeader(ESBHeaderConstant.REQUESTID, req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID, req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT, req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE, req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		ReturnGoodsResponseEntity response = new ReturnGoodsResponseEntity();
		JSONObject obj = JSONObject.fromObject(returnBill).getJSONObject("returnBill");
		ReturnGoodsRequestEntity request = (ReturnGoodsRequestEntity) JSONObject.toBean(obj,ReturnGoodsRequestEntity.class);
		 if(request==null){
			 	response.setIfSuccess(false);
				resp.setHeader("ESB-ResultCode", "0");
				response.setErrorMsg("实体类不为空");
				return response;
		 }
		 MutexElement mutex = new MutexElement(String.valueOf(request.getDealnumber())
				 , "CRM_RETURN_GOODS_Dealnumber",
				    MutexElementType.CRM_RETURN_GOODS_Dealnumber);
		try{
			LOGGER.info("crm同步返货处理工单["+request.getOriWaybill()+"]["+request.getDealnumber()+"]至foss");
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
				resp.setHeader("ESB-ResultCode", "1");
				returnGoodsRequestEntityService.updateReturnGoodsRequestEntity(request); 
				response.setIfSuccess(true);
			} else {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				response.setIfSuccess(false);
				resp.setHeader("ESB-ResultCode", "0");
				response.setErrorMsg("加锁失败");
			}
			
		}catch(Exception e){
			response.setIfSuccess(false);
			resp.setHeader("ESB-ResultCode", "0");
			response.setErrorMsg(e.getMessage());
		}finally{
			 // 解业务锁
			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		}
	//	LOGGER.info("foss处理完crm返货工单["+request.getOriWaybill()+"]["+request.getDealnumber()+"]所耗的时间:"+(System.currentTimeMillis()-s));
		return response;
	}

	

}
