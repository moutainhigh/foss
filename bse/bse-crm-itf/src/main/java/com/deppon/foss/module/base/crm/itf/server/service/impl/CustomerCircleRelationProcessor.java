package com.deppon.foss.module.base.crm.itf.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.dpap.esb.mqc.core.exception.ESBBusinessException;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.dpap.esb.mqc.core.process.IProcess;
import com.deppon.esb.pojo.domain.crm2foss.CustCircleRelationRequest;
import com.deppon.esb.pojo.domain.crm2foss.CustCircleRelationResponse;
import com.deppon.esb.pojo.domain.crm2foss.CustomerCircleCrmEntity;
import com.deppon.esb.pojo.transformer.json.SyncCustomerCircleRelationRequestTrans;
import com.deppon.esb.pojo.transformer.json.SyncCustomerCircleRelationResponseTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncCustomerCircleRelationLogService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncCustomerCircleRelationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleLogEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 
 * 客户圈信息接口
 * @author 308861 
 * @date 2016-12-21 上午10:50:45
 * @since
 * @version
 */
public class CustomerCircleRelationProcessor implements IProcess{
	
	/**
	 * 日志类
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerCircleRelationProcessor.class);
	
	//同步成功总数
    int successCount = 0;
    //同步失败总数
    int failedCount = 0;
    
	/**
     * 业务互斥锁服务.
     */
    @Autowired
	private IBusinessLockService businessLockService;
    
	/**
	 * "客户圈客户信息"接口结果操作Service
	 */
	@Autowired
	private ISyncCustomerCircleRelationService syncCustomerCircleRelationService;

	/**
	 * "客户圈客户日志信息"接口结果操作sevice
	 */
	@Autowired
	private ISyncCustomerCircleRelationLogService syncCustomerCircleRelationLogService;
	/**
	 * 业务处理
	 * @author 308861
	 */
	@Override
	public Object process(Object req) throws ESBBusinessException {
		LOGGER.info("------------接收客户圈信息开始--------------");
		//声明对象
		CustCircleRelationRequest request=(CustCircleRelationRequest)req;
		//返回参数
		CustCircleRelationResponse response=new CustCircleRelationResponse();
		if(request == null){
			//如果CRM系统传过的客户圈客户信息为空 那么直接返回失败信息
			response.setResultCode(false);
			response.setReason("由于CRM系统传过来的参数为空，故不执行任何操作！");
			/**
			 * 客户圈客户日志信息
			 */
			CustomerCircleLogEntity logEntity=new CustomerCircleLogEntity();
			//编号
			logEntity.setId(UUIDUtils.getUUID());
			//创建时间
			logEntity.setCreateDate(new Date());
			//返回参数
			logEntity.setResult(new SyncCustomerCircleRelationResponseTrans()
			.fromMessage(response));
			//是否成功
			logEntity.setTrueorfalse("N");
			//失败原因
			logEntity.setFalsereason(response.getReason());
			//获取请求参数
			logEntity.setContent(new SyncCustomerCircleRelationRequestTrans()
			.fromMessage(request));
			//执行新增客户圈基础信息日志表
			syncCustomerCircleRelationLogService.addCustomerCircleLog(logEntity);
    		return response;
		}
		//同步成功总数
	    successCount = 0;
	    //同步失败总数
	    failedCount = 0;
	    LOGGER.info("调用  同步客户圈信息请求： \n"
				+ new SyncCustomerCircleRelationRequestTrans()
				.fromMessage(request));
	    //获取客户圈内客户信息数据
		List<CustomerCircleCrmEntity> list=request.getCustCircleRelationList();
		//判断crm发送请求客户圈内客户结合
		if(CollectionUtils.isEmpty(list)){
	    	LOGGER.info("同步客户圈客户信息的集合不能为空");
	    	response.setTransactionCode(request.getTransactionCode());
	    	response.setResultCode(false);
	    	response.setReason("客户圈客户信息集合不能为空");
	    	
	    	/**
			 * 客户圈客户日志信息
			 */
			CustomerCircleLogEntity logEntity=new CustomerCircleLogEntity();
			//编号
			logEntity.setId(UUIDUtils.getUUID());
			//处理编码
			logEntity.setTransactionCode(response.getTransactionCode());
			//创建时间
			logEntity.setCreateDate(new Date());
			//返回参数
			logEntity.setResult(new SyncCustomerCircleRelationResponseTrans()
			.fromMessage(response));
			//是否成功
			logEntity.setTrueorfalse("N");
			//失败原因
			logEntity.setFalsereason(response.getReason());
			//获取请求参数
			logEntity.setContent(new SyncCustomerCircleRelationRequestTrans()
			.fromMessage(request));
			//执行新增客户圈基础信息日志表
			syncCustomerCircleRelationLogService.addCustomerCircleLog(logEntity);
	    	return response;
	    }
		//循环crm发送请求中客户圈的客户信息
		for(CustomerCircleCrmEntity crmEntity : list) {
			//判断数据合理性
			if(null == crmEntity.getId() 
					&& StringUtils.isEmpty(crmEntity.getCustCircleCode())
					&& StringUtils.isEmpty(crmEntity.getCustCode())
					&& null == crmEntity.getBeginTime()
					&& null == crmEntity.getEndTime())
			{
				LOGGER.error("该同步过来的数据客户圈编码或者客户编码或者客户圈客户的开始结束时间为空！");
				// 保存异常原因
				response.setReason("该同步过来的数据客户圈编码或者客户编码或者客户圈客户的开始结束时间为空！");
				response.setResultCode(false);
				
				/**
				 * 客户圈客户日志信息
				 */
				CustomerCircleLogEntity logEntity=new CustomerCircleLogEntity();
				//编号
				logEntity.setId(UUIDUtils.getUUID());
				//处理编码
				logEntity.setTransactionCode(response.getTransactionCode());
				//创建时间
				logEntity.setCreateDate(new Date());
				//返回参数
				logEntity.setResult(new SyncCustomerCircleRelationResponseTrans()
				.fromMessage(response));
				//是否成功
				logEntity.setTrueorfalse("N");
				//失败原因
				logEntity.setFalsereason(response.getReason());
				//获取请求参数
				logEntity.setContent(new SyncCustomerCircleRelationRequestTrans()
				.fromMessage(request));
				//执行新增客户圈基础信息日志表
				syncCustomerCircleRelationLogService.addCustomerCircleLog(logEntity);
		    	return response;
			}
			//将esbinfo转为foss对象
			CustomerCircleEntity fossEntity=transEsbToFOSS(crmEntity);
			if(fossEntity == null){
				LOGGER.info("请求实体转换错误");
				response.setTransactionCode(request.getTransactionCode());
		    	response.setResultCode(false);
		    	response.setReason("请求实体转换错误");
		    	/**
				 * 客户圈客户日志信息
				 */
				CustomerCircleLogEntity logEntity=new CustomerCircleLogEntity();
				//编号
				logEntity.setId(UUIDUtils.getUUID());
				//处理编码
				logEntity.setTransactionCode(response.getTransactionCode());
				//创建时间
				logEntity.setCreateDate(new Date());
				//返回参数
				logEntity.setResult(new SyncCustomerCircleRelationResponseTrans()
				.fromMessage(response));
				//是否成功
				logEntity.setTrueorfalse("N");
				//失败原因
				logEntity.setFalsereason(response.getReason());
				//获取请求参数
				logEntity.setContent(new SyncCustomerCircleRelationRequestTrans()
				.fromMessage(request));
				//执行新增客户圈基础信息日志表
				syncCustomerCircleRelationLogService.addCustomerCircleLog(logEntity);
		    	return response;
			}
			//返回消息里设置信息标识
			response.setTransactionCode(request.getTransactionCode());
			// 业务锁
			MutexElement mutex = new MutexElement(
					String.valueOf(crmEntity.getId()),
					"CRM_CustomerCircleRelation_CRMID",
					MutexElementType.CRM_CustomerCircleRelation_CRMID);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (!result) {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				// 保存失败原因
				response.setReason("出现了高并发操作！");
				failedCount++;
				continue;
			}
			LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			//根据crmid查询客户圈客户信息
			CustomerCircleEntity resultEntity = syncCustomerCircleRelationService
					.getCustomerCircleById(fossEntity);
			if(resultEntity != null){
				//存在数据（先作废再新增）
				updateCusCircleEntity(response, fossEntity);
			}else{
				//不存在数据（直接新增）
				addCusCircleEntity(response, fossEntity);
			}
			//客户圈客户信息处理结束
			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			//解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());	
		}
		LOGGER.info("调用  同步客户圈信息响应： \n"
				+ new SyncCustomerCircleRelationResponseTrans()
				.fromMessage(response));
		LOGGER.info("------------接收客户圈信息结束--------------");
		//如果失败的话保存到客户圈客户日志表中
		addCustomerCircleLog(request, response);	
		return response;
	}

	/**
	 * 
	 * 不存在数据（直接新增） 
	 * @author 308861 
	 * @date 2017-2-23 下午6:59:47
	 * @param response
	 * @param fossEntity
	 * @see
	 */
	private void addCusCircleEntity(CustCircleRelationResponse response,
			CustomerCircleEntity fossEntity) {
		try {
			int add=0;
			//id
			fossEntity.setId(UUIDUtils.getUUID());
			//创建时间
			fossEntity.setCreateDate(new Date());
			//创建人
			fossEntity.setCreateUser("CRM");
			//修改时间默认为2999年
			fossEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
			//修改人
			fossEntity.setModifyUser("CRM");
			//执行新增操作
			add=syncCustomerCircleRelationService.addCustomerCircle(fossEntity);
			if(add > 0){
				response.setResultCode(true);
				response.setReason("FOSS:新增成功");
				// 成功数
				successCount++;
			}else{
				response.setResultCode(false);
				response.setReason("FOSS:新增失败");
				// 失败数
				failedCount++;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 保存异常原因
			response.setReason(e.getMessage());
			response.setResultCode(false);
			// 失败数
			failedCount++;
			LOGGER.error("直接新增失败次数："+failedCount);
		}
	}

	/**
	 * 
	 * 存在数据（先作废再新增） 
	 * @author 308861 
	 * @date 2017-2-23 下午6:59:21
	 * @param response
	 * @param fossEntity
	 * @see
	 */
	private void updateCusCircleEntity(CustCircleRelationResponse response,
			CustomerCircleEntity fossEntity) {
		try {
			int addUp=0;
			//先作废原有数据
			fossEntity.setModifyDate(new Date());
			fossEntity.setModifyUser("CRM");
			//执行作废操作
			int updateResult=syncCustomerCircleRelationService.delCustomerCircle(fossEntity);
			if (updateResult > 0) {// 作废成功
				//id
				fossEntity.setId(UUIDUtils.getUUID());
				//创建时间
				fossEntity.setCreateDate(new Date());
				//创建人
				fossEntity.setCreateUser("CRM");
				//修改时间默认为2999年
				fossEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
				//修改人
				fossEntity.setModifyUser("CRM");
				//执行新增操作
				addUp=syncCustomerCircleRelationService.addCustomerCircle(fossEntity);
				if(addUp > 0){
					response.setResultCode(true);
					response.setReason("FOSS:修改成功");
					// 成功数
					successCount++;
				}else{
					response.setResultCode(false);
					response.setReason("FOSS:修改失败");
					// 失败数
					failedCount++;
					LOGGER.error("修改失败次数："+failedCount);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 保存异常原因
			response.setReason(e.getMessage());
			response.setResultCode(false);
			// 失败数
			failedCount++;
			LOGGER.error("修改失败次数："+failedCount);
		}
	}

	/**
	 * 
	 * 如果失败的话保存到客户圈客户日志表中 
	 * @author 308861 
	 * @date 2017-2-23 下午6:48:48
	 * @param request
	 * @param response
	 * @throws ESBConvertException
	 * @see
	 */
	private void addCustomerCircleLog(CustCircleRelationRequest request,
			CustCircleRelationResponse response) throws ESBConvertException {
		if(!response.getResultCode()){
			/**
			 * 客户圈客户日志信息
			 */
			CustomerCircleLogEntity logEntity=new CustomerCircleLogEntity();
			//编号
			logEntity.setId(UUIDUtils.getUUID());
			//处理编码
			logEntity.setTransactionCode(request.getTransactionCode());
			//创建时间
			logEntity.setCreateDate(new Date());
			//返回参数
			logEntity.setResult(new SyncCustomerCircleRelationResponseTrans()
			.fromMessage(response));
			//是否成功
			logEntity.setTrueorfalse("N");
			//失败原因
			logEntity.setFalsereason(response.getReason());
			//获取请求参数
			logEntity.setContent(new SyncCustomerCircleRelationRequestTrans()
			.fromMessage(request));
			//执行新增客户圈基础信息日志表
			syncCustomerCircleRelationLogService.addCustomerCircleLog(logEntity);
		}
	}

	/**
	 * 异常处理
	 * @author 308861
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("ESB处理错误");                                                                                    
		return req;
	}

	//将esbinfo转为foss对象
	public CustomerCircleEntity transEsbToFOSS(CustomerCircleCrmEntity crmEntity){
		if(crmEntity == null){
		    return null;
		}
		CustomerCircleEntity entity=new CustomerCircleEntity();
		//非空验证
		if(null != crmEntity.getId()){
			//客户圈id
			entity.setCrmId(crmEntity.getId());
		}
		//客户圈编码
		entity.setCustCircleCode(crmEntity.getCustCircleCode());
		//客户圈名称
		entity.setCustCircleName(crmEntity.getCustCircleName());
		//客户编码
		entity.setCustCode(crmEntity.getCustCode());
		//客户名称
		entity.setCustName(crmEntity.getCustName());
		//是否主客户-------需要类型编码转换
		if(StringUtil.equals(NumberConstants.NUMERAL_S_ZORE, crmEntity.getIsMainCust())){
			//0是从客户N
		    entity.setIsMainCust(FossConstants.NO);
		}else {
			//1是主客户Y
		    entity.setIsMainCust(FossConstants.YES);
		}
		//是否统一结算-------需要类型编码转换
		if(StringUtil.equals(NumberConstants.NUMERAL_S_ZORE, crmEntity.getIsFocusPay())){
			//0否N
		    entity.setIsFocusPay(FossConstants.NO);
		}else {
			//1是Y
		    entity.setIsFocusPay(FossConstants.YES);
		}
		//催款部门编码
		entity.setHastenfunddeptCode(crmEntity.getDunningDeptCode());
		//开始时间
		entity.setBeginTime(crmEntity.getBeginTime());
		//结束时间
		entity.setEndTime(crmEntity.getEndTime());
		//是否可用-------需要类型编码转换
		if(StringUtil.equals(NumberConstants.NUMERAL_S_ZORE, crmEntity.getIsUsed())){
			//0为启用N
		    entity.setActive(FossConstants.NO);
		}else {
			//1已启用Y
		    entity.setActive(FossConstants.YES);
		}
		return entity;
	}
}
