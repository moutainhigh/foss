package com.deppon.foss.module.transfer.stock.server.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.oms.inteface.domain.SynContrabandErrorsRequest;
import com.deppon.oms.inteface.domain.SynContrabandErrorsResponse;

@RequestMapping(value = "/qmsToFossService")
public class ExceptionGoodsService implements IProcess {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionGoodsService.class);
	
	/**
	 * 违禁品业务操作方法
	 */
	private IContrabandGoodsService contrabandGoodsService;

	public void setContrabandGoodsService(IContrabandGoodsService contrabandGoodsService) {
		this.contrabandGoodsService = contrabandGoodsService;
	}
	
	/**
	 * 
	 * 保存QMS推送过来的违禁品数据
	 * 
	 * @author ruipengwang 316759
	 * @date 2016年5月19日 下午15:33:12
	 * @param requestStr
	 * @return
	 */
	@RequestMapping(value = "/addExceptionGoodsFromQms", method = RequestMethod.POST)
	@Override
	public Object process(Object requestStr) throws ESBBusinessException {
		// 响应消息
		SynContrabandErrorsResponse responseStr = new SynContrabandErrorsResponse();
		// 解析请求参数
		SynContrabandErrorsRequest req = (SynContrabandErrorsRequest) requestStr;
		try {
			if (req == null || StringUtils.isBlank(req.getWaybillNo()) || StringUtils.isBlank(req.getGoodsName()) || StringUtils.isBlank(req.getQmsErrorNo())) {
				LOGGER.info("保存违禁品失败：请求参数为空！");
				// 获取返回信息
				responseStr = this.getResponseJson(req == null ? "" : req.getWaybillNo(), req == null ? "" : req.getQmsErrorNo(), FossConstants.FAILURE, "违禁品保存失败！");
				return responseStr;
			}
			int result = contrabandGoodsService.qmsToFossContrabandGoods(req.getWaybillNo(), req.getQmsErrorNo(), req.getGoodsName(), req.getExceptionStatus(), req.getExceptionResult());
			if (result == FossConstants.SUCCESS) {
				LOGGER.info("违禁品信息保存成功");
				// 获取返回信息
				responseStr = this.getResponseJson(req.getWaybillNo(), req.getQmsErrorNo(), result, "违禁品保存成功！");
				return responseStr;
			} else {
				LOGGER.info("违禁品信息保存失败");
				// 获取返回信息
				responseStr = this.getResponseJson(req.getWaybillNo(), req.getQmsErrorNo(), result, "违禁品保存失败！");
				return responseStr;
			}
		} catch (Exception e) {
			LOGGER.info("违禁品异常信息:" + e.getMessage(), e);
			// 获取返回信息
			responseStr = this.getResponseJson(req.getWaybillNo(), req.getQmsErrorNo(), FossConstants.FAILURE, "违禁品推送失败：系统异常，请重新操作！" + e.getMessage());
			return responseStr;
		}
	}

	/**
	 * 获取返回信息
	 * 
	 * @author 316759 ruipengwang
	 * @date 2016-05-20 下午17:25:35
	 */
	public SynContrabandErrorsResponse getResponseJson(String waybillNo, String qmsErrorNo, int result, String message) {
		// 设置返回信息
		SynContrabandErrorsResponse response = new SynContrabandErrorsResponse();
		// 运单号
		if (StringUtils.isNotBlank(waybillNo)) {
			response.setWaybillNo(waybillNo);
		}
		// 差错编号
		if (StringUtils.isNotBlank(qmsErrorNo)) {
			response.setQmsErrorNo(qmsErrorNo);
		}
		// 结果
		response.setResult(result);
		// 详细信息
		response.setMessage(message);
		return response;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
