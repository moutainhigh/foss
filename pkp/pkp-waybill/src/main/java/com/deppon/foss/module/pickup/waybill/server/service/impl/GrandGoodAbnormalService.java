package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IGrandGoodAbnormalService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.GrandGoodAbnormalEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.UUIDUtils;

public class GrandGoodAbnormalService  implements IGrandGoodAbnormalService{
	/**
	 * 日志
	 */
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(GrandGoodAbnormalService.class);
	/**
	 * 返回值（成功）
	 */
	private final static String SUCCESS = "1";
	
	/**
	 * 返回值（失败）
	 */
	private final static String FAILURE = "0";
	
	/**
	 * 运单号
	 */
	private static final String WAYBILL_NO = "waybillNo";
	
	/**
	 * 出险原因
	 */
	private static final String DANGER_CAUSE = "dangerCause";
	
	/**
	 * 责任部门
	 */
	private static final String UNIFIED_CODE = "unifiedCode";
	
	/**
	 * 经手是否有责任
	 */
	private static final String PASS_IS_DUTY = "passIsDuty";
	
	/**
	 * 运单管理接口
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 业务互斥服务
	 */
	private IBusinessLockService businessLockService;
	
	private IWaybillDao  waybillDao ;
	
	
	public IWaybillDao getWaybillDao() {
		return waybillDao;
	}


	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	
	/**
	 * 重大异常货物处理
	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-7-14 上午10:30:35
	 */
	@Transactional
	@Override
	public String grandGoodAbnormalHandle(String grandGoodAbnormalJson){
		LOGGER.info("************重大货物异常处理开始************");
		
		JSONObject jsonObj = JSONObject.parseObject(grandGoodAbnormalJson);
		//返回值
		String response = "";

		if(jsonObj == null 
				|| StringUtils.isBlank((String)jsonObj.get(WAYBILL_NO))
				|| StringUtils.isBlank((String)jsonObj.get(DANGER_CAUSE))
				|| StringUtils.isBlank((String)jsonObj.get(PASS_IS_DUTY))){
			//记录日志
			LOGGER.info("重大货物异常处理失败：请求参数为空！");
			response = this.getResponseJson
					(null,FAILURE,"重大货物异常处理失败：请求参数为空！");
			LOGGER.info("************重大货物异常处理结束************");
			return response;
		}
		//运单号
		String waybillNo = (String)jsonObj.get(WAYBILL_NO);
		//出险原因
		String dangerCause = (String)jsonObj.get(DANGER_CAUSE);
		//责任部门
		String unifiedCode = (String)jsonObj.get(UNIFIED_CODE);
		//经手是否有责任
		String passIsDuty = (String)jsonObj.get(PASS_IS_DUTY);
		
		MutexElement mutexElement = new MutexElement
				(waybillNo,"运单编号",MutexElementType.RFC_SIGN);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		if(!isLocked){
			//如果没有得到锁
			LOGGER.info("重大货物异常处理失败：当前运单操作中，请稍后再试！");
			//记录日志
			response = this.getResponseJson
									(waybillNo,FAILURE,"重大货物异常处理失败：当前运单操作中，请稍后再试");
			LOGGER.info("************重大货物异常处理结束************");
			return response;
		}
		//查询运单信息
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if(waybill == null){
			//日志
			LOGGER.info("重大货物异常处理失败：该运单号无效！");
			//获取返回参数(运单号无效)
			response = this.getResponseJson(waybillNo,FAILURE,"重大货物异常处理失败：该运单号无效！");
			LOGGER.info("************重大货物异常处理结束************");
			//解锁
			businessLockService.unlock(mutexElement);
			return response;
		}
		try{
			GrandGoodAbnormalEntity grangGood = new GrandGoodAbnormalEntity();
			//id
			grangGood.setId(UUIDUtils.getUUID());
			grangGood.setWaybillNo(waybillNo);
			//出险原因
			grangGood.setDangerCause(dangerCause);
			//责任部门
			grangGood.setUnifiedCode(unifiedCode);
			//经手是否有责
			grangGood.setPassIsDuty(passIsDuty);
			//新增重大货物异常
			GrandGoodAbnormalEntity genty = this.queryGrandGoodAbnormal(waybillNo);
			if(genty!=null){
				//如果该运单已存在重大异常货，则修改
				this.updateGrandGoodAbnormal(grangGood);
				response = this.getResponseJson(waybillNo,SUCCESS,"处理成功！");
			}else{
				//如果该运单不存在重大异常货，则添加
				this.insertGrandGoodAbnormal(grangGood);
				response = this.getResponseJson(waybillNo,SUCCESS,"处理成功！");
			}
			LOGGER.info("************重大货物异常处理结束************");
			//解锁
			businessLockService.unlock(mutexElement);
			return response;
		}catch(Exception e){
			LOGGER.info("************重大货物异常处理结束************");
			//解锁
			businessLockService.unlock(mutexElement);
			throw new BusinessException();
		}
	}
	
	/**
	 * 获取返回信息
 	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-7-14 上午10:30:35
 	 */
	public String getResponseJson(String waybillNo,String resultCode,String message){
		Map<String,Object> resMap = new HashMap<String,Object>();
		//设置返回信息
		if(StringUtils.isNotBlank(waybillNo)){
			resMap.put("waybillNo",waybillNo);
		}
		resMap.put("resultCode",resultCode);
		resMap.put("message",message);
		//将返回信息转成Json字符串
		String response = JSONObject.toJSONString(resMap);
		return response;
	}

	@Override
	/**
	 * 查询重大货物异常
	 */
	public GrandGoodAbnormalEntity queryGrandGoodAbnormal(String waybillNo) {
		return waybillDao .queryGrandGoodAbnormal(waybillNo);
	}

	/**
	 * 更新重大货物异常
	 */
	@Override
	public void updateGrandGoodAbnormal(GrandGoodAbnormalEntity grangGood) {
		waybillDao .updateGrandGoodAbnormal(grangGood);
		
	}
	

	/**
	 * 新增重大货物异常
	 *  @author Foss-PanGuoYang
	 */
	@Override
	public void insertGrandGoodAbnormal(GrandGoodAbnormalEntity grangGood) {
		// TODO Auto-generated method stub
		waybillDao.insertGrandGoodAbnormal(grangGood);
	}


	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}


	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

}
