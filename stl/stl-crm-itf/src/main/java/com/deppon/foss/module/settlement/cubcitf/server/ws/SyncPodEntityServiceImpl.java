package com.deppon.foss.module.settlement.cubcitf.server.ws;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.SyncPodEntityRequest;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.SyncPodEntityResponse;
import com.deppon.foss.module.settlement.common.api.server.service.IPODService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PODEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.cubcitf.server.inter.ISyncPodEntityService;
import com.deppon.foss.util.UUIDUtils;

/**
 * cubc同步财务单据完结信息到foss
 * 
 * @author 269044-zhurongrong
 * @date 2017-04-21
 */
public class SyncPodEntityServiceImpl implements ISyncPodEntityService {
	
	/**
	 * 打印日志
	 */
	private static Logger logger = LoggerFactory.getLogger(SyncPodEntityServiceImpl.class);
	
	@Context
	HttpServletResponse res;
	
	/**
	 * 成功：1--生成理赔应付单成功，若为在线理赔，付款也成功
	 */
	private int SUCCESS = 1;
	
	/**
	 * 失败：0--生成理赔应付单失败
	 */
	private int FAILURE = 0;
	
	/**
	 * 财务签收记录Service，开单默认插入一条数据，便于后续凭证数据使用
	 */
	private IPODService podService;

	@Override
	public @ResponseBody String syncPodEntity(@RequestBody String jsonStr) {
		//打印参数信息
		logger.info("cubc传来的pod参数为：" + JSONObject.toJSONString(jsonStr));
		
		res.setHeader("ESB-ResultCode" , "1");
		//生成返回实体
		String response = "";
		try {
			//参数判空
			if(StringUtil.isBlank(jsonStr)) {
				//记录日志
				logger.info("生成pod记录失败，CUBC请求参数为空");
				//参数为空
				throw new SettlementException("生成pod记录失败，CUBC请求参数为空");
			}
			//参数转换
			SyncPodEntityRequest request = JSONObject.parseObject(jsonStr, SyncPodEntityRequest.class);
			//判空
			if(StringUtils.isEmpty(request.getWaybillNo())){
				logger.info("生成pod记录失败，运单号为空");
				throw new SettlementException("生成pod记录失败，运单号为空");
			}
			//判空
			if(null == request.getPodDate()){
				logger.info("生成pod记录失败，签收时间为空，运单号为：" + request.getWaybillNo());
				throw new SettlementException("生成pod记录失败，签收时间为空，运单号为：" + request.getWaybillNo());
			}
			//判空
			if(StringUtils.isEmpty(request.getPodType())){
				logger.info("生成pod记录失败，签收类型为空，运单号为：" + request.getWaybillNo());
				throw new SettlementException("生成pod记录失败，签收类型为空，运单号为：" + request.getWaybillNo());
			}
			//判空
			if(StringUtils.isEmpty(request.getPodUserCode())){
				logger.info("生成pod记录失败，签收客户编码为空，运单号为：" + request.getWaybillNo());
				throw new SettlementException("生成pod记录失败，签收客户编码为空，运单号为：" + request.getWaybillNo());
			}
			//判空
			if(StringUtils.isEmpty(request.getPodUserName())){
				logger.info("生成pod记录失败，签收客户名称为空，运单号为：" + request.getWaybillNo());
				throw new SettlementException("生成pod记录失败，签收客户名称为空，运单号为：" + request.getWaybillNo());
			}
			//判空
			if(StringUtils.isEmpty(request.getPodOrgCode())){
				logger.info("生成pod记录失败，签收部门编码为空，运单号为：" + request.getWaybillNo());
				throw new SettlementException("生成pod记录失败，签收部门编码为空，运单号为：" + request.getWaybillNo());
			}
			//判空
			if(StringUtils.isEmpty(request.getPodOrgName())){
				logger.info("生成pod记录失败，签收部门名称为空，运单号为：" + request.getWaybillNo());
				throw new SettlementException("生成pod记录失败，签收部门名称为空，运单号为：" + request.getWaybillNo());
			}
			
			//构建当前登录人信息
			CurrentInfo currentInfo = SettlementUtil.getECSCurrentInfo(request.getPodUserCode(),request.getPodUserName(),
					request.getPodOrgCode(),request.getPodOrgName());
			//创建对象
			PODEntity pod = new PODEntity();
			//设置id
			pod.setId(UUIDUtils.getUUID());
			//运单号
			pod.setWaybillNo(request.getWaybillNo());
			//开单时间
			pod.setPodDate(request.getPodDate());
			//类型
			pod.setPodType(request.getPodType());
			//插入数据库
			this.podService.addPOD(pod, currentInfo);
			//打印日志
			logger.info(request.getWaybillNo() + "pod记录添加成功");
			//响应消息
			response = this.getResponse(SUCCESS,"pod记录添加成功");			
		//捕获异常
		} catch (BusinessException e) {
			//打印日志
			logger.info("cubc的pod记录添加失败" + e.getErrorCode());
			//响应消息
			response = this.getResponse(FAILURE,"cubc的pod记录添加失败" + e.getErrorCode());	
		} catch (Exception e) {
			//打印日志
			logger.info("cubc的pod记录添加失败" + e.getMessage());
			//响应消息
			response = this.getResponse(FAILURE,"cubc的pod记录添加失败" + e.getMessage());	
		}
		return response;
	}

	
	/**
	 * 根据参数生成返回值信息
	 * @param result
	 * @param reason
	 * @return
	 */
	private String getResponse(int result , String reason){
		SyncPodEntityResponse response = new SyncPodEntityResponse();
		response.setResult(result);
		response.setReason(reason);
		return JSONObject.toJSONString(response);
	}
	
	/**
	 * @param podService
	 *            the podService to set
	 */
	public void setPodService(IPODService podService) {
		this.podService = podService;
	}
}
