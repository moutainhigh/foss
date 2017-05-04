package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IWayBillNoSectionDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IWayBillNoSectionService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WayBillNoSectionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WayBillNoSectionRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.WayBillNoSectionException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.lang3.StringUtils;
/**
 * 运单号段Service接口实现
 * @author 262036 HuangWei
 *
 * @date 2015-6-17 下午1:47:14
 */
public class WayBillNoSectionService implements IWayBillNoSectionService{
	/**
	 * 日志
	 */
    private static final Logger LOGGER = LoggerFactory.getLogger(WayBillNoSectionService.class);
    /**
     * 运单号段DAO接口
     */
    private IWayBillNoSectionDao wayBillNoSectionDao;
    
    /**
     * 客户表查询的接口
     */
    private ICustomerService customerService;
    
	/**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
	public void setWayBillNoSectionDao(IWayBillNoSectionDao wayBillNoSectionDao) {
		this.wayBillNoSectionDao = wayBillNoSectionDao;
	}


	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}


	/**
	 * @description 根据传入对象查询符合条件所有运单号段信息
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午1:57:09
	 */
	@Override
	public List<WayBillNoSectionEntity> queryWayBillNoSection(
			WayBillNoSectionEntity entity, int limit, int start) {
		if(null == entity){
			throw new WayBillNoSectionException("传入的参数不允许为空");
		}else{
			entity.setActive(FossConstants.ACTIVE);
			return wayBillNoSectionDao.queryWayBillNoSection(entity, limit, start);
		}
	}

	/**
	 * @description 统计总记录数
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午2:21:33
	 */
	@Override
	public Long queryRecordCount(WayBillNoSectionEntity entity) {
		if(null == entity){
			throw new WayBillNoSectionException("传入的参数不允许为空！");
		}else{
			entity.setActive(FossConstants.ACTIVE);
			return wayBillNoSectionDao.queryRecordCount(entity);
		}
	}

	/**
	 * @description 新增运单号段信息
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午1:50:45
	 */
	
	@Override
	@Transactional
	public WayBillNoSectionEntity addWayBillNoSection(WayBillNoSectionEntity entity) {
		if(null == entity){
			entity = new WayBillNoSectionEntity();
	    	entity.setState(-2);
	    	return entity;
		}
		
		if(StringUtils.isNotBlank(entity.getCustomerCode())){
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setCusCode(entity.getCustomerCode());
			customerEntity = customerService.queryCustInfoByCustomerEntity(customerEntity);
			if(customerEntity == null || !FossConstants.ACTIVE.equals(customerEntity.getIsElecBillBigCust())){
		    	entity.setState(NumberConstants.NUMBER_THE_3);
		    	return entity;
			}
		}
		
		MutexElement mutex = new MutexElement(WayBillNoSectionRequestEntity.class.getName(), "CRM_WAYBILLNO_SECTION",
				    MutexElementType.CRM_WAYBILLNO_SECTION);
		LOGGER.info("开始加锁：" + mutex.getBusinessNo());
		boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
		if(result){
			LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			
		    Long startValue = wayBillNoSectionDao.queryWayBillStart(entity);
		    if(startValue == -1){
		    	entity.setState(-1);//申请运单号长度超过截止号段
		    	
				LOGGER.info("开始解锁：" + mutex.getBusinessNo());
				 // 解业务锁
				businessLockService.unlock(mutex);
				LOGGER.info("完成解锁：" + mutex.getBusinessNo());
				
		    	return entity;
		    }
		    //根据申请数量计算出截止运单号
		    Long endValue = startValue + entity.getApplyCount();
		    //在开始运单号上加1，避免重复分配
		    startValue = startValue + 1;
		    //设置起始运单号
		    entity.setWayBillNoStart(String.valueOf(startValue));
		    //设置截止运单号
		    entity.setWayBillNoEnd(String.valueOf(endValue));
			//新增记录时，主键采用UUID的策略
			entity.setId(UUIDUtils.getUUID());
			//创建时间
			entity.setCreateTime(new Date());
			
			if(entity.getState() != NumberConstants.NUMBER_THE_8){//-8表示接口调用此方法
				//得到当前登录用户的信息
				UserEntity userEntity = FossUserContext.getCurrentUser();
				//创建人编号
				entity.setCreateUserCode(userEntity.getEmployee().getEmpCode());
				//创建人姓名
				entity.setCreateUserName(userEntity.getEmployee().getEmpName());
			}else{//表示接口调用此方法
				//创建人姓名
				entity.setCreateUserName("系统");
			}
			
			entity.setActive(FossConstants.ACTIVE);
	
			//打印日志
			LOGGER.debug("id: " + entity.getId());
			
			int stateAdd = wayBillNoSectionDao.addWayBillNoSection(entity);
			
			int stateUpdate = wayBillNoSectionDao.updateWayBillStart(entity);
			
			if(stateAdd == 1 && stateUpdate == 1){
				entity.setState(1);//操作成功
			}
			
			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			 // 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		}else{
			entity.setState(NumberConstants.NUMBER_THE_10);//枷锁失败
		}
		return entity;
	}

	/**
	 * @description 查询运单号参数起始值
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午3:49:23
	 */
	@Override
	public Long queryWayBillStart(WayBillNoSectionEntity entity) {
		return wayBillNoSectionDao.queryWayBillStart(entity);
	}

	/**
	 * @description 修改运单号参数起始值
	 * @author 262036 HuangWei
	 * @date 2015-6-23下午3:49:26
	 */
	@Override
	public int updateWayBillStart(WayBillNoSectionEntity entity) {
		return wayBillNoSectionDao.updateWayBillStart(entity);
	}
	
	
}
