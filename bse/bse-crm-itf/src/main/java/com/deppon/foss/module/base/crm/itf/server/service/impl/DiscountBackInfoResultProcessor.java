package com.deppon.foss.module.base.crm.itf.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.crm.DiscountBackInfo;
import com.deppon.esb.inteface.domain.crm.DiscountBackInfoProcessResult;
import com.deppon.esb.inteface.domain.crm.DiscountBackInfoRequest;
import com.deppon.esb.inteface.domain.crm.DiscountBackInfoResponse;
import com.deppon.esb.inteface.domain.crm.DiscountBackItemInfo;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDiscountBackInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDiscountBackItemInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DiscountBackEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DiscountBackItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BankException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 同步CRM系统事后折数据到foss数据库，供结算使用
 * 服务端 JMS格式
 * @author 132599-FOSS-ShenWeiHua
 * @date 2015-02-07 上午10:23:05
 * @since
 * @version
 */
public class DiscountBackInfoResultProcessor implements IProcess{
	
	/**
     * 声明logger日志对象.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountBackInfoResultProcessor.class);
    
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
    /**
     * 事后折概要信息service
     */
    private IDiscountBackInfoService discountBackInfoService;
    
    /**
     * 事后折详细信息service
     */
    private IDiscountBackItemInfoService discountBackItemInfoService;
    
    /**
	 * 定义常量
	 */
	private static final String CRM ="CRM";
	
	 //同步成功总数
    int successCount = 0;
    //同步失败总数
    int failedCount = 0;
    
    /**
     * <p>接收CRM系统的事后折信息</p>.
     *
     * @param req 
     * @return 
     * @throws ESBBusinessException 
     * @author 132599-FOSS-ShenWeiHua
     * @date 2015-02-06 上午10:30:05
     * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
     */
	@Override
	public Object process(Object req) throws ESBBusinessException {
		LOGGER.info("同步crm系统事后折信息开始");
		//声明事后折信息请求对象
		DiscountBackInfoRequest discountBackInfoRequest = (DiscountBackInfoRequest)req;
		//声明事后折信息请求对象
		DiscountBackInfoResponse discountBackInfoResponse = new DiscountBackInfoResponse(); 
		
		if(discountBackInfoRequest == null){
			//如果CRM系统传过的部门信息为空 那么直接返回失败信息
        	List<DiscountBackInfoProcessResult> discountBackInfoProcessResultList = new ArrayList<DiscountBackInfoProcessResult>();
        	DiscountBackInfoProcessResult result = new DiscountBackInfoProcessResult();
        	result.setFailReason("由于CRM系统传过来的参数为空，故不执行任何操作！");
        	discountBackInfoProcessResultList.add(result);
        	discountBackInfoResponse.getProcessResult().addAll(discountBackInfoProcessResultList);
    		return discountBackInfoResponse;
		}
    	    //获得response集合
    	    List<DiscountBackInfoProcessResult> discountBackInfoProcessResultList = new ArrayList<DiscountBackInfoProcessResult>();
    	    //同步成功总数
    	    successCount = 0;
    	    //同步失败总数
    	    failedCount = 0;
    	    //获取同步过来的数据
    	    List<DiscountBackInfo> discountBackInfoList = discountBackInfoRequest.getDiscountBackInfo();
    	    
    	    if(CollectionUtils.isEmpty(discountBackInfoList)){
    	    	LOGGER.info("同步成本中心部门信息结束");
    	    	return discountBackInfoResponse;
    	    }
		for (DiscountBackInfo discountBackInfo : discountBackInfoList) {
			// 定义一个同步处理结果对象
			DiscountBackInfoProcessResult processResult = new DiscountBackInfoProcessResult();
			// 判断同步过来的crmId是否为空
			if (null != discountBackInfo.getFid()
					&& StringUtils.isNotEmpty(discountBackInfo.getCusCode())
					&& null != discountBackInfo.getBeginTime()
					&& null != discountBackInfo.getEndTime()) {
				// 信息转换封装
				DiscountBackEntity entity = convertDiscountBackInfo(discountBackInfo);
				// 返回消息里设置信息标识
				processResult.setCrmId(entity.getCrmId());
				// 获取事后折详细信息
				List<DiscountBackItemInfo> discountBackItemInfoList = discountBackInfo
						.getDiscountBackItemInfos();
				List<DiscountBackItemEntity> discountBackItemEntityList = new ArrayList<DiscountBackItemEntity>();
				// 如果事后折详细信息不为空 那么遍历处理
				if (CollectionUtils.isNotEmpty(discountBackItemInfoList)) {
					addDiscountBackInfoProcessResult(
							discountBackInfoProcessResultList, processResult,
							discountBackItemInfoList,
							discountBackItemEntityList);
				}
				// 业务锁
				MutexElement mutex = new MutexElement(
						String.valueOf(discountBackInfo.getFid()),
						"CRM_DISCOUNT_BACK_ID",
						MutexElementType.CRM_DISCOUNT_BACK_ID);
				LOGGER.info("开始加锁：" + mutex.getBusinessNo());
				boolean result = businessLockService.lock(mutex,
						NumberConstants.ZERO);
				if (!result) {
					LOGGER.info("失败加锁：" + mutex.getBusinessNo());
					// 保存失败原因
					processResult.setFailReason("出现了高并发操作！");
					failedCount++;
					continue;
				}
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
				// 事后折概要信息
				boolean flag = discountBackInfoService
						.queryDiscountBackInfoByCrmId(discountBackInfo.getFid());
				if (flag) {// 更新事后折概要信息（如果存在就更新否则就新增）
					try {
						int updateResult = 0;
						entity.setModifyDate(new Date());
						entity.setModifyUser(CRM);
						updateResult = discountBackInfoService
								.updateDiscountBackInfo(entity);
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
					} catch (BankException e) {
						LOGGER.error(e.getMessage(), e);
						// 保存异常原因
						processResult.setFailReason(e.getMessage());
						processResult.setIsSucess(false);
						// 失败数
						failedCount++;
					}
				} else {
					try {// 如果不存在就新增
						int addResult = 0;
						entity.setCreateDate(new Date());
						entity.setCreateUser(CRM);
						addResult = discountBackInfoService
								.insertDiscountBackInfo(entity);
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
					} catch (BankException e) {
						LOGGER.error(e.getMessage(), e);
						// 保存异常原因
						processResult.setFailReason(e.getMessage());
						processResult.setIsSucess(false);
						// 失败数
						failedCount++;
					}
				}

				// 事后折详细信息
				if (CollectionUtils.isNotEmpty(discountBackItemEntityList)) {
					setDiscountBackInfoProcessResult(processResult, entity,
							discountBackItemEntityList);
				}
				// 事后折详细信息处理结束
				LOGGER.info("开始解锁：" + mutex.getBusinessNo());
				// 解业务锁
				businessLockService.unlock(mutex);
				LOGGER.info("完成解锁：" + mutex.getBusinessNo());
				// 把处理结果放在集合里面
				discountBackInfoProcessResultList.add(processResult);

			} else {

				LOGGER.error("该同步过来的数据crmId或者客户编码或者方案的开始结束时间为空！");
				// 保存异常原因
				processResult
						.setFailReason("该同步过来的数据crmId或者客户编码或者方案的开始结束时间为空！");
				processResult.setIsSucess(false);

				// 失败数
				failedCount++;
				discountBackInfoProcessResultList.add(processResult);
			}
		}
    		//成功总数
    		discountBackInfoResponse.setSuccessCount(successCount);
    		discountBackInfoResponse.getProcessResult().addAll(discountBackInfoProcessResultList);
    		//失败总数
    		discountBackInfoResponse.setFailedCount(failedCount);
    	    
    	LOGGER.info("同步成本中心部门信息结束");
    	return discountBackInfoResponse;
	
	}

	private void setDiscountBackInfoProcessResult(
			DiscountBackInfoProcessResult processResult,
			DiscountBackEntity entity,
			List<DiscountBackItemEntity> discountBackItemEntityList) {
		for (DiscountBackItemEntity itemEntity : discountBackItemEntityList) {
			boolean itemflag = discountBackItemInfoService
					.queryDiscountBackItemInfoByCrmId(itemEntity
							.getCrmId());
			if (itemflag) {// 更新事后折详细信息（如果存在就更新否则就新增）
				try {
					int updateResult = 0;
					entity.setModifyDate(new Date());
					entity.setModifyUser(CRM);
					updateResult = discountBackItemInfoService
							.updateDiscountBackItemInfo(itemEntity);
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
				} catch (BankException e) {
					LOGGER.error(e.getMessage(), e);
					// 保存异常原因
					processResult.setFailReason(e.getMessage());
					processResult.setIsSucess(false);
					// 失败数
					failedCount++;
				}
			} else {
				try {// 如果不存在就新增
					int addResult = 0;
					entity.setCreateDate(new Date());
					entity.setCreateUser(CRM);
					addResult = discountBackItemInfoService
							.insertDiscountBackItemInfo(itemEntity);
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
				} catch (BankException e) {
					LOGGER.error(e.getMessage(), e);
					// 保存异常原因
					processResult.setFailReason(e.getMessage());
					processResult.setIsSucess(false);
					// 失败数
					failedCount++;
				}
			}
		}
	}

	private void addDiscountBackInfoProcessResult(
			List<DiscountBackInfoProcessResult> discountBackInfoProcessResultList,
			DiscountBackInfoProcessResult processResult,
			List<DiscountBackItemInfo> discountBackItemInfoList,
			List<DiscountBackItemEntity> discountBackItemEntityList) {
		for(DiscountBackItemInfo discountBackItemInfo : discountBackItemInfoList){
			if(null != discountBackItemInfo.getFid() &&
			   null != discountBackItemInfo.getDisCrmId()
			   && null != discountBackItemInfo.getMinMoney()
			   && null != discountBackItemInfo.getMaxMoney()){
			// 事后折详细信息转换封装	
			DiscountBackItemEntity itemEntity = convertDiscountBackItemInfo(discountBackItemInfo);
			discountBackItemEntityList.add(itemEntity);
			}else{
				LOGGER.error("同步过来的事后折详细信息crmId或者概要信息crmId或者最大最小金额为空！");
			    //保存异常原因
			    processResult.setFailReason("同步过来的事后折详细信息crmId或者概要信息crmId或者最大最小金额为空！");
			    processResult.setIsSucess(false);
			    //失败数
			    failedCount++;
			    discountBackInfoProcessResultList.add(processResult);
			}
		}
	}
	
	/**
     * 声明错误处理函数
     * @date 2015-02-7 上午10:35:05
	 * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
     */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("同步crm系统事后折信息数据异常："+ req);
		return null;
	}
	
	/**
	 * 事后折信息实体转换方法
	 * @param info
	 * @return
	 */
	private DiscountBackEntity convertDiscountBackInfo(DiscountBackInfo discountBackInfo) {
		//定义事后折信息实体
		DiscountBackEntity entity = new DiscountBackEntity();
		// 设置实体的信息id
		entity.setId(UUIDUtils.getUUID());
		// 设置crmId
		entity.setCrmId(discountBackInfo.getFid());
		// 1 代表生效 2代表失效   （3是待生效不同步）
		if(NumberConstants.NUMERAL_S_ONE.equals(discountBackInfo.getStatus())){
			//启用状态
		    entity.setActive(FossConstants.ACTIVE);
		}else{
			//启用状态
		    entity.setActive(FossConstants.INACTIVE);
		}
		//客户编码
		entity.setCusCode(discountBackInfo.getCusCode().trim());
		//客户名称
		entity.setCusName(discountBackInfo.getCusName());
		//方案编码
		entity.setSchemeCode(discountBackInfo.getSchemeCode().trim());
		//方案名称
		entity.setSchemeName(discountBackInfo.getSchemeName());
		//方案开始时间
	    entity.setBeginTime(discountBackInfo.getBeginTime());
	    //方案结束时间
	    entity.setEndTime(discountBackInfo.getEndTime());
	    //创建时间
	    entity.setCreateDate(discountBackInfo.getCreateTime());
	    //修改时间
	    entity.setModifyDate(discountBackInfo.getModifyTime());
	    //折扣类型（COLLAFTERDIS  代收事后折  ENSUREDPRICEAFTERDIS 保价事后折  CARRIAGEAFTERDIS  运费事后折）
	    entity.setPreferType(discountBackInfo.getPreferType());
	    //创建人
	    entity.setCreateUser(CRM);
	    //修改人
	    entity.setModifyUser(CRM);
		return entity;
	}
	
	/**|
	 * 事后折详细信息转换方法
	 * @param info
	 * @return
	 */
	private DiscountBackItemEntity convertDiscountBackItemInfo(DiscountBackItemInfo info) {
		// 新建事后折详细信息实体
		DiscountBackItemEntity entity = new DiscountBackItemEntity();
		entity.setId(UUIDUtils.getUUID());
		//crmId
		entity.setCrmId(info.getFid());
		// 事后折概要信息ID
		entity.setDiscountCrmId(info.getDisCrmId());
		// 等级（即折扣金额之间的一个顺序）
		entity.setDegree(info.getDegree());
		//最小金额(foss规则  折扣统一乘以100 后台mapper实现)
		entity.setMinMoney(info.getMinMoney());
		//最大金额(foss规则  折扣统一乘以100 后台mapper实现)
		entity.setMaxMoney(info.getMaxMoney());
		//创建时间
		entity.setCreateDate(info.getCreatTime());
		//修改时间
		entity.setModifyDate(info.getModifyTime());
		//运费折扣
		entity.setRate(info.getRate());
		entity.setCreateUser(CRM);
		entity.setModifyUser(CRM);
		return entity;
	}
	
	/**
	 * 设置业务锁
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	/**
	 * 设置事后折概要信息service
	 * @param discountBackInfoService
	 */
	public void setDiscountBackInfoService(
			IDiscountBackInfoService discountBackInfoService) {
		this.discountBackInfoService = discountBackInfoService;
	}
	
	/**
	 * 设置事后折详细信息service
	 * @param discountBackItemInfoService
	 */
	public void setDiscountBackItemInfoService(
			IDiscountBackItemInfoService discountBackItemInfoService) {
		this.discountBackItemInfoService = discountBackItemInfoService;
	}
	
	
}
