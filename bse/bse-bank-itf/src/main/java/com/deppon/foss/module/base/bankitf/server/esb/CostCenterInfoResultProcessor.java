package com.deppon.foss.module.base.bankitf.server.esb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.inter.eascostcenter.service.EASCostCenterDataInfo;
import com.deppon.foss.inter.eascostcenter.service.EASCostCenterDataProcessResult;
import com.deppon.foss.inter.eascostcenter.service.EASCostCenterDataRequest;
import com.deppon.foss.inter.eascostcenter.service.EASCostCenterDataResponse;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCostCenterDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CostCenterDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BankException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
/**
 * 同步财务7.0系统的成本中心费用承担部门数据到foss数据库，供结算使用
 * 服务端 JMS格式
 * @author 132599-FOSS-ShenWeiHua
 * @date 2014-08-7 上午10:23:05
 * @since
 * @version
 */
public class CostCenterInfoResultProcessor implements IProcess{
	
	/**
     * 声明logger日志对象.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CostCenterInfoResultProcessor.class);
	
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
    /**
     * 成本中心费用承担部门service
     */
    private ICommonCostCenterDeptService commonCostCenterDeptService;
    
    private String EAS ="EAS7.0";
    
    //同步成功总数
    int successCount = 0;
    //同步失败总数
    int failedCount = 0;
    
    /**
     * <p>接收财务7.0系统的成本中心费用承担部门数据</p>.
     *
     * @param req 
     * @return 
     * @throws ESBBusinessException 
     * @author 132599-FOSS-ShenWeiHua
     * @date 2014-08-07 上午10:30:05
     * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
     */
    @Override
	public Object process(Object req) throws ESBBusinessException {
    	LOGGER.info("同步财务7.0成本中心信息开始");
    	//声明成本中心信息请求对象
    	EASCostCenterDataRequest easCostCenteRequest = (EASCostCenterDataRequest)req;
    	//声明成本中心信息相应对象
    	EASCostCenterDataResponse easCostCenteResponse = new EASCostCenterDataResponse();
    	
    	if(easCostCenteRequest != null){
    	    //获得response集合
    	   /* List<EASCostCenterDataProcessResult> easCostCenterProcessResultList = new ArrayList<EASCostCenterDataProcessResult>();
    	    //同步成功总数
    	    int successCount = 0;
    	    //同步失败总数
    	    int failedCount = 0;*/
    	    //获取同步过来的数据
    	    List<EASCostCenterDataInfo> easCostCenterInfoList = easCostCenteRequest.getEasCostCenterDataInfo();
    	    
    	    if(CollectionUtils.isNotEmpty(easCostCenterInfoList)){
    	    	this.getEasCostCenteResponse(easCostCenterInfoList, easCostCenteResponse);
    		/*for(EASCostCenterDataInfo easCostCenterInfo : easCostCenterInfoList){
    			//定义一个同步处理结果对象
    		    EASCostCenterDataProcessResult processResult = new EASCostCenterDataProcessResult();
    			//判断同步过来的部门编码code是否为空
    			if(StringUtils.isNotBlank(easCostCenterInfo.getDeptCode())){
    		    //定义成本中心费用承担部门信息实体
    			CostCenterDeptEntity entity = new CostCenterDeptEntity();
    		    if(StringUtil.isNotBlank(easCostCenterInfo.getAdminId())){
    			//费用承担部门信息id
    			entity.setAdminId(easCostCenterInfo.getAdminId().trim());
    		    }else {
    			//费用承担部门信息id
    			entity.setAdminId(easCostCenterInfo.getAdminId());
    		    }
    		    
    		    //费用承担部门信息标杆编码
    		    entity.setSimpleCode(easCostCenterInfo.getSimpleName());
    		    
    			//费用承担部门code
    			entity.setDeptCode(easCostCenterInfo.getDeptCode().trim());
    		    
    		    if(StringUtil.isNotBlank(easCostCenterInfo.getDeptName())){
    			//费用承担部门名称
    			entity.setDeptName(easCostCenterInfo.getDeptName().trim());
    		    }else {
    			//费用承担部门名称
    			entity.setDeptName(easCostCenterInfo.getDeptName());
    		    }
    		    
    		    if(StringUtil.isNotBlank(easCostCenterInfo.getTypeCode())){
    			//费用承担部门类型code
    			entity.setTypeCode(easCostCenterInfo.getTypeCode().trim());
    		    }else {
    			//费用承担部门类型code
    			entity.setTypeCode(easCostCenterInfo.getTypeCode());
    		    }
    		    
    		    if(StringUtil.isNotBlank(easCostCenterInfo.getTypeName())){
				//费用承担部门类型名称
    			entity.setTypeName(easCostCenterInfo.getTypeName().trim());
    		    }else {
    			//费用承担部门类型名称
    			entity.setTypeName(easCostCenterInfo.getTypeName());
    		    }
    		    
    		    if(StringUtil.isNotBlank(easCostCenterInfo.getIsCostOrgUnit())){
				//是否是成本中心(1是0否)
    			entity.setIsCostOrgUnit(easCostCenterInfo.getIsCostOrgUnit().trim());
    		    }else {
    			//是否是成本中心(1是0否)
    			entity.setIsCostOrgUnit(easCostCenterInfo.getIsCostOrgUnit());
    		    }
    		    
    		    if(StringUtil.isNotBlank(easCostCenterInfo.getIsFreeze())){
				//是否冻结(1是0否)
    			entity.setIsFreeze(easCostCenterInfo.getIsFreeze().trim());
    		    }else {
    			//是否冻结(1是0否)
    			entity.setIsFreeze(easCostCenterInfo.getIsFreeze());
    		    }
    		    
    		    if(StringUtil.isNotBlank(easCostCenterInfo.getState())){
				//状态信息
    			entity.setStatus(easCostCenterInfo.getState().trim());
    		    }else {
    			//状态信息
    			entity.setStatus(easCostCenterInfo.getState());
    		    }
    		    
    		    //成本中心部门编码
    		    processResult.setDeptCode(easCostCenterInfo.getDeptCode());
    		    
    		    // 业务锁
    			MutexElement mutex = new MutexElement(easCostCenterInfo.getDeptCode(), "COST_CENTER_DEPT_CODE",
    				    MutexElementType.COST_CENTER_DEPT_CODE);
    			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
    			boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
    			if (result) {
    			    LOGGER.info("成功加锁：" + mutex.getBusinessNo());
    			
    			boolean flag = commonCostCenterDeptService.queryCostCenterDeptInfoByDeptCode(easCostCenterInfo.getDeptCode());
    		    if(flag){//更新部门信息（如果存在就更新否则就新增）
    			try {
    				int updateResult = 0;
    				entity.setModifyDate(new Date());
    				entity.setModifyUser(EAS);
    				updateResult = commonCostCenterDeptService.updateCostCenterDeptInfo(entity);
        			    if(updateResult > 0){//保存成功
        				processResult.setIsSucess(true);
        				processResult.setFailReason("FOSS:update is success");
        				//成功数
        				successCount++;
        			    }else {
        				processResult.setIsSucess(false);
        				processResult.setFailReason("FOSS:update is failed");
        				//失败数
        				failedCount++;
    			    }
    			} catch (BankException e) {
    			    LOGGER.error(e.getMessage(), e);
    			    //保存异常原因
    			    processResult.setFailReason(e.getMessage());
    			    processResult.setIsSucess(false);
    			    //失败数
    			    failedCount++;
    			}
    		    }else{
    		    	try {// 如果不存在就新增
        				int addResult = 0;
        				entity.setCreateDate(new Date());
        				entity.setCreateUser(EAS);
        				addResult = commonCostCenterDeptService.insertCostCenterDeptInfo(entity);
            			    if(addResult > 0){//保存成功
            				processResult.setIsSucess(true);
            				processResult.setFailReason("FOSS:insert is success");
            				//成功数
            				successCount++;
            			    }else {
            				processResult.setIsSucess(false);
            				processResult.setFailReason("FOSS:insert is failed");
            				//失败数
            				failedCount++;
        			    }
        			} catch (BankException e) {
        			    LOGGER.error(e.getMessage(), e);
        			    //保存异常原因
        			    processResult.setFailReason(e.getMessage());
        			    processResult.setIsSucess(false);
        			    //失败数
        			    failedCount++;
        			}
    		    }
    		
    		    LOGGER.info("开始解锁：" + mutex.getBusinessNo());
    			// 解业务锁
    			businessLockService.unlock(mutex);
    			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
    		} else {
    		    LOGGER.info("失败加锁：" + mutex.getBusinessNo());
    		    //保存失败原因
    		    processResult.setFailReason("出现了高并发操作！");
    		    failedCount++;
    		    continue;
    		}
    		    //把处理结果放在集合里面
    			easCostCenterProcessResultList.add(processResult);
    		    
    		}else{
    			
    			LOGGER.error("该同步过来的部门编码为空！");
			    //保存异常原因
			    processResult.setFailReason("该同步过来的部门编码为空！");
			    processResult.setIsSucess(false);
			    
			    //失败数
			    failedCount++;
			    easCostCenterProcessResultList.add(processResult);
    		}
    	    }
    		//成功总数
    		easCostCenteResponse.setSuccessCount(successCount);
    		easCostCenteResponse.getProcessResult().addAll(easCostCenterProcessResultList);
    		//失败总数
    		easCostCenteResponse.setFailedCount(failedCount);
    	    }*/
    	}else{
    		//如果财务与7.0系统传过的部门信息为空 那么直接返回失败信息
        	List<EASCostCenterDataProcessResult> easCostCenterProcessResultList = new ArrayList<EASCostCenterDataProcessResult>();
        	EASCostCenterDataProcessResult result = new EASCostCenterDataProcessResult();
        	result.setFailReason("由于财务7.0系统传过来的参数为空，故不执行任何操作！");
        	easCostCenterProcessResultList.add(result);
        	easCostCenteResponse.getProcessResult().addAll(easCostCenterProcessResultList);
    		return easCostCenteResponse;
    	}
    	    }
    	LOGGER.info("同步成本中心部门信息结束");
    	return easCostCenteResponse;
	}
    /**
     * <p>
     * 同步信息情况
     * </p>
     * @author 273311
     * @date 2016-4-26 上午11:30:51
     * @param 
     * @return
     * @see
     */
	private void getEasCostCenteResponse(
			List<EASCostCenterDataInfo> easCostCenterInfoList,
			EASCostCenterDataResponse easCostCenteResponse) {
		// 获得response集合
		List<EASCostCenterDataProcessResult> easCostCenterProcessResultList = new ArrayList<EASCostCenterDataProcessResult>();
		// 同步成功总数
		successCount = 0;
		// 同步失败总数
		failedCount = 0;
		for (EASCostCenterDataInfo easCostCenterInfo : easCostCenterInfoList) {
			easCostCenterProcessResultList = setProcessResult(
					easCostCenterProcessResultList, easCostCenterInfo);
		}
		// 成功总数
		easCostCenteResponse.setSuccessCount(successCount);
		easCostCenteResponse.getProcessResult().addAll(
				easCostCenterProcessResultList);
		// 失败总数
		easCostCenteResponse.setFailedCount(failedCount);
	}
	/**
	 * 统计同步信息
	 *273296
	 * @param easCostCenterProcessResultList
	 * @param easCostCenterInfo
	 * @return
	 */
	private List<EASCostCenterDataProcessResult> setProcessResult(
			List<EASCostCenterDataProcessResult> easCostCenterProcessResultList,
			EASCostCenterDataInfo easCostCenterInfo) {
		// 定义一个同步处理结果对象
		EASCostCenterDataProcessResult processResult = new EASCostCenterDataProcessResult();
		// 判断同步过来的部门编码code是否为空
		if (StringUtils.isNotBlank(easCostCenterInfo.getDeptCode())) {
			CostCenterDeptEntity entity = this.convertInfo(easCostCenterInfo);
			MutexElement mutex=null;
			try {
				// 业务锁
				mutex = new MutexElement(easCostCenterInfo.getDeptCode(),
						"COST_CENTER_DEPT_CODE",
						MutexElementType.COST_CENTER_DEPT_CODE);
				LOGGER.info("开始加锁：" + mutex.getBusinessNo());
				boolean result = businessLockService.lock(mutex,
						NumberConstants.ZERO);

				if (result) {
					LOGGER.info("成功加锁：" + mutex.getBusinessNo());
					processResult = updateProcessResult(easCostCenterInfo,
							processResult, entity);
				} else {
					LOGGER.info("失败加锁：" + mutex.getBusinessNo());
					// 保存失败原因
					processResult.setFailReason("出现了高并发操作！");
					failedCount++;

				}
			}catch (BankException e) {
				LOGGER.error(e.getMessage(), e);
				// 保存异常原因
				processResult.setFailReason(e.getMessage());
				processResult.setIsSucess(false);
				// 失败数
				failedCount++;
			}finally{
				if(mutex!=null){
					LOGGER.info("开始解锁：" + mutex.getBusinessNo());					
				}
				// 解业务锁
				businessLockService.unlock(mutex);
				if(mutex!=null){
					LOGGER.info("完成解锁：" + mutex.getBusinessNo());
				}
			}
			// 把处理结果放在集合里面
			easCostCenterProcessResultList.add(processResult);
		} else {
			LOGGER.error("该同步过来的部门编码为空！");
			// 保存异常原因
			processResult.setFailReason("该同步过来的部门编码为空！");
			processResult.setIsSucess(false);

			// 失败数
			failedCount++;
			easCostCenterProcessResultList.add(processResult);
		}
		return easCostCenterProcessResultList;
	}
	/**
	 *273296
	 * @param easCostCenterInfo
	 * @param processResult
	 * @param entity
	 * @param mutex
	 */
	private EASCostCenterDataProcessResult updateProcessResult(EASCostCenterDataInfo easCostCenterInfo,
			EASCostCenterDataProcessResult processResult,
			CostCenterDeptEntity entity) {
		if(StringUtils.isEmpty(easCostCenterInfo
						.getDeptCode())&&StringUtils.isEmpty(easCostCenterInfo.getSimpleName())){
			processResult.setIsSucess(false);
			processResult
					.setFailReason("部门编码和标杆编码都为空！");
			// 失败数
			failedCount++;
			return processResult;
		}
		boolean flag = commonCostCenterDeptService
				.queryCostCenterDeptInfoByDeptCode(easCostCenterInfo
						.getDeptCode(),easCostCenterInfo.getSimpleName());
		if (flag) {// 更新部门信息（如果存在就更新否则就新增）
				int updateResult = 0;
				entity.setModifyDate(new Date());
				entity.setModifyUser(EAS);
				updateResult = commonCostCenterDeptService
						.updateCostCenterDeptInfo(entity);
				if (updateResult > 0) {// 保存成功
					processResult.setIsSucess(true);
					processResult
							.setFailReason("FOSS:update is success");
					// 成功数
					successCount++;
				} else {
					processResult.setIsSucess(false);
					processResult
							.setFailReason("FOSS:update is failed");
					// 失败数
					failedCount++;
				}
		} else {
				int addResult = 0;
				entity.setCreateDate(new Date());
				entity.setCreateUser(EAS);
				addResult = commonCostCenterDeptService
						.insertCostCenterDeptInfo(entity);
				if (addResult > 0) {// 保存成功
					processResult.setIsSucess(true);
					processResult
							.setFailReason("FOSS:insert is success");
					// 成功数
					successCount++;
				} else {
					processResult.setIsSucess(false);
					processResult
							.setFailReason("FOSS:insert is failed");
					// 失败数
					failedCount++;
				}
		}
		return processResult;
	}
	/**
     * <p>
     * 把同步过来的信息转化成Foss信息实体
     * </p>
     * @author 273311
     * @date 2016-4-26 上午10:10:39
     * @param easCostCenterInfo
     * @return
     * @see
     */
    private CostCenterDeptEntity convertInfo(EASCostCenterDataInfo easCostCenterInfo) {
    	//定义成本中心费用承担部门信息实体
		CostCenterDeptEntity entity = new CostCenterDeptEntity();
	    if(StringUtil.isNotBlank(easCostCenterInfo.getAdminId())){
		//费用承担部门信息id
		entity.setAdminId(easCostCenterInfo.getAdminId().trim());
	    }else {
		//费用承担部门信息id
		entity.setAdminId(easCostCenterInfo.getAdminId());
	    }
	    
	    //费用承担部门信息标杆编码
	    entity.setSimpleCode(easCostCenterInfo.getSimpleName());
	    
		//费用承担部门code
		entity.setDeptCode(easCostCenterInfo.getDeptCode().trim());
	    
	    if(StringUtil.isNotBlank(easCostCenterInfo.getDeptName())){
		//费用承担部门名称
		entity.setDeptName(easCostCenterInfo.getDeptName().trim());
	    }else {
		//费用承担部门名称
		entity.setDeptName(easCostCenterInfo.getDeptName());
	    }
	    
	    if(StringUtil.isNotBlank(easCostCenterInfo.getTypeCode())){
		//费用承担部门类型code
		entity.setTypeCode(easCostCenterInfo.getTypeCode().trim());
	    }else {
		//费用承担部门类型code
		entity.setTypeCode(easCostCenterInfo.getTypeCode());
	    }
	    
	    if(StringUtil.isNotBlank(easCostCenterInfo.getTypeName())){
		//费用承担部门类型名称
		entity.setTypeName(easCostCenterInfo.getTypeName().trim());
	    }else {
		//费用承担部门类型名称
		entity.setTypeName(easCostCenterInfo.getTypeName());
	    }
	    
	    if(StringUtil.isNotBlank(easCostCenterInfo.getIsCostOrgUnit())){
		//是否是成本中心(1是0否)
		entity.setIsCostOrgUnit(easCostCenterInfo.getIsCostOrgUnit().trim());
	    }else {
		//是否是成本中心(1是0否)
		entity.setIsCostOrgUnit(easCostCenterInfo.getIsCostOrgUnit());
	    }
	    
	    if(StringUtil.isNotBlank(easCostCenterInfo.getIsFreeze())){
		//是否冻结(1是0否)
		entity.setIsFreeze(easCostCenterInfo.getIsFreeze().trim());
	    }else {
		//是否冻结(1是0否)
		entity.setIsFreeze(easCostCenterInfo.getIsFreeze());
	    }
	    
	    if(StringUtil.isNotBlank(easCostCenterInfo.getState())){
		//状态信息
		entity.setStatus(easCostCenterInfo.getState().trim());
	    }else {
		//状态信息
		entity.setStatus(easCostCenterInfo.getState());
	    }
		return entity;
	    
	}
    /**
     * 声明错误处理函数
     * @date 2014-08-7 上午10:35:05
	 * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
     */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("同步财务7.0系统成本中心数据异常："+ req);
		return null;
	}

	/**
	 * 设置业务锁
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	/**
	 * 设置成本中心费用承担部门service
	 * @param commonCostCenterDeptService
	 */
	public void setCommonCostCenterDeptService(
			ICommonCostCenterDeptService commonCostCenterDeptService) {
		this.commonCostCenterDeptService = commonCostCenterDeptService;
	}
	

}
