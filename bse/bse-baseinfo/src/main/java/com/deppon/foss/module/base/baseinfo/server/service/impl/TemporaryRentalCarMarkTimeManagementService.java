package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ITemporaryRentalCarMarkTimeManagementDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ITemporaryRentalCarMarkTimeManagementService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TemporaryRentalCarMarkTimeManagementEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.TemporaryRentalCarMarkTimeManagementException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 临时租车标记时间管理Service接口实现
 * @author 218392  张永雪
 * @date 创建时间：2014-12-18 上午11:15:36
 */
public class TemporaryRentalCarMarkTimeManagementService implements ITemporaryRentalCarMarkTimeManagementService{

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TemporaryRentalCarMarkTimeManagementService.class);
	
	/**
	 * 注入临时租车标记时间管理DAO接口
	 */
	private ITemporaryRentalCarMarkTimeManagementDao temporaryRentalCarMarkTimeManagementDao;
	
	/**
	 * 设置 临时租车标记时间管理 temporaryRentalCarMarkTimeManagementDao 接口
	 * @param temporaryRentalCarMarkTimeManagementDao
	 */
	public void setTemporaryRentalCarMarkTimeManagementDao(
			ITemporaryRentalCarMarkTimeManagementDao temporaryRentalCarMarkTimeManagementDao) {
		this.temporaryRentalCarMarkTimeManagementDao = temporaryRentalCarMarkTimeManagementDao;
	}
	
	/**
	 * 新增临时租车标记时间管理信息
	 */
	@Override
	public int addTemporaryRentalCarMarkTimeManagement(TemporaryRentalCarMarkTimeManagementEntity entity) {
		if(null == entity){
			throw new TemporaryRentalCarMarkTimeManagementException("传入的参数不允许为空!");
		}
		//判断部门属性是否已经存在，要保证部门属性唯一
		entity.setActive(FossConstants.ACTIVE);
		Long num = temporaryRentalCarMarkTimeManagementDao.queryCount(entity);
		if(num > 0){
			throw new TemporaryRentalCarMarkTimeManagementException("部门属性是唯一，且已经存在，请重新选择！");
		}
		// 第一次记录新增时，虚拟编码为记录的id
		entity.setId(UUIDUtils.getUUID());
		
		UserEntity user = (UserEntity)UserContext.getCurrentUser();
		String userCode = user.getEmployee().getEmpCode();
		String userName = user.getEmployee().getEmpName();
		
		entity.setOperationCode(userCode);
		entity.setOperationName(userName);
		entity.setActive(FossConstants.ACTIVE);
		entity.setOperationDate(new Date());//创建时间（操作时间）fuzhi
		//获取无限大时间				
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		//打印日志
		LOGGER.debug("id: " + entity.getId());
		
		return temporaryRentalCarMarkTimeManagementDao.addTemporaryRentalCarMarkTimeManagement(entity);
	}

	
	


	/**
	 * 修改临时租车标记时间管理 信息
	 */
	@Override
	public int updateTemporaryRentalCarMarkTimeManagement(
			TemporaryRentalCarMarkTimeManagementEntity entity) {
		if(null == entity){
			throw new TemporaryRentalCarMarkTimeManagementException("传入的参数不允许为空!");
		}else if(StringUtils.isBlank(entity.getId())){
			throw new TemporaryRentalCarMarkTimeManagementException("ID不允许为空!");
		}
		List<String> list = new ArrayList<String>();
		list.add(entity.getId());
		//先作废
		int result = temporaryRentalCarMarkTimeManagementDao.deleteByIdTemporaryRentalCarMarkTimeManagement(list);
		if(result >0){
			//作废成功
			UserEntity user = (UserEntity)UserContext.getCurrentUser();
			String userCode = user.getEmployee().getEmpCode();
			String userName = user.getEmployee().getEmpName();
			
			entity.setId(UUIDUtils.getUUID());
			entity.setOperationCode(userCode);
			entity.setOperationName(userName);
			entity.setActive(FossConstants.ACTIVE);
			entity.setCreateDate(new Date());
			//获取最大日期时间
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
			
			return temporaryRentalCarMarkTimeManagementDao.addTemporaryRentalCarMarkTimeManagement(entity); 
		}else{
			return FossConstants.FAILURE;
		}
	}

	/**
	 * 作废 临时租车标记时间管理
	 */
	@Override
	public int deleteByIdTemporaryRentalCarMarkTimeManagement(
			List<String> idList) {
		if(CollectionUtils.isEmpty(idList)){
			throw new TemporaryRentalCarMarkTimeManagementException("虚拟编码不允许为空!");
		}else{
			return temporaryRentalCarMarkTimeManagementDao.deleteByIdTemporaryRentalCarMarkTimeManagement(idList);
		}
	}
	
	
	/**
	 * 根据传入对象查询符合条件的所有信息
	 */
	@Override
	public List<TemporaryRentalCarMarkTimeManagementEntity> queryAllTemporaryRentalCarMarkTimeManagement(
			TemporaryRentalCarMarkTimeManagementEntity entity, int limit,int start) {
		if(null == entity){
			throw new TemporaryRentalCarMarkTimeManagementException("传入的参数不允许为空！");
		}else {
			entity.setActive(FossConstants.ACTIVE);
			return temporaryRentalCarMarkTimeManagementDao.queryAllTemporaryRentalCarMarkTimeManagement(entity, limit, start);
		}
	}

	/**
	 * 统计总记录数
	 */
	@Override
	public Long queryCount(TemporaryRentalCarMarkTimeManagementEntity entity) {
		if(null == entity){
			throw new TemporaryRentalCarMarkTimeManagementException("传入的参数不允许为空！");
		}else{
			entity.setActive(FossConstants.ACTIVE);
			return temporaryRentalCarMarkTimeManagementDao.queryCount(entity);
		}
	}

	@Override
	public String querySetTimeByDeptAttributes(String deptAttributes) {
		if(StringUtil.isBlank(deptAttributes)){
			throw new TemporaryRentalCarMarkTimeManagementException("传入的部门属性参数不能为空");
		}else {
			 return temporaryRentalCarMarkTimeManagementDao.querySetTimeByDeptAttributes(deptAttributes);
		}
	}
	
}
