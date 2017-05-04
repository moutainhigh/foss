package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISystemHelpDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISystemHelpService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SystemHelpEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SystemHelpDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 系统帮助service接口实现
 * @author foss-qiaolifeng
 * @date 2013-8-6 下午4:16:00
 */
public class SystemHelpService implements ISystemHelpService {

	/**
	 * 日志记录
	 */
	private static final Logger LOGGER =LoggerFactory.getLogger(SystemHelpService.class); 
	
	/**
	 * 系统帮助DAO
	 */
	private ISystemHelpDao systemHelpDao;
	
	
	/**
	 * @get
	 * @return systemHelpDao
	 */
	public ISystemHelpDao getSystemHelpDao() {
		/*
		 * @get
		 * @return systemHelpDao
		 */
		return systemHelpDao;
	}

	
	/**
	 * @set
	 * @param systemHelpDao
	 */
	public void setSystemHelpDao(ISystemHelpDao systemHelpDao) {
		/*
		 *@set
		 *@this.systemHelpDao = systemHelpDao
		 */
		this.systemHelpDao = systemHelpDao;
	}

	/** 
	 * 分页查询
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:16:15
	 * @param systemHelpDto
	 * @param limit
	 * @param start
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISystemHelpService#querySystemHelpEntity(com.deppon.foss.module.base.baseinfo.api.shared.dto.SystemHelpDto, int, int)
	 */
	@Override
	public List<SystemHelpEntity> querySystemHelpEntity(
			SystemHelpDto systemHelpDto, int limit, int start) {
		
		if(null == systemHelpDto){
			throw new BusinessException("传入的参数值不能为空");
		}
		
		LOGGER.info("begin SystemHelpService querySystemHelpEntity()...");
		
		//有效
		systemHelpDto.getSystemHelpEntity().setActive(FossConstants.ACTIVE);
		
		LOGGER.info("end SystemHelpService querySystemHelpEntity()...");
		return systemHelpDao.querySystemHelpEntity(systemHelpDto, limit, start);
	}

	/** 
	 * 查询总记录条数
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:16:17
	 * @param systemHelpDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISystemHelpService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.dto.SystemHelpDto)
	 */
	@Override
	public long queryRecordCount(SystemHelpDto systemHelpDto) {
		if(null == systemHelpDto){
			throw new BusinessException("传入的参数值不能为空");
		}
		
		LOGGER.info("begin SystemHelpService queryRecordCount()...");
		//有效
		systemHelpDto.getSystemHelpEntity().setActive(FossConstants.ACTIVE);
		LOGGER.info("end SystemHelpService queryRecordCount()...");
		return systemHelpDao.queryRecordCount(systemHelpDto);
	}

	/** 
	 * 新增
	 * @author foss-qiaolifeng
	 * @date 2013-8-6 下午4:16:19
	 * @param systemHelpEntity
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISystemHelpService#addSystemHelpEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.SystemHelpEntity)
	 */
	@Override
	@Transactional
	public int addSystemHelpEntity(SystemHelpEntity systemHelpEntity) {
		
		//判断插入的是否为空
		if (systemHelpEntity ==null) {
			throw new BusinessException("传入的参数不能为空值");
		}
		LOGGER.info("begin SystemHelpService addSystemHelpEntity()...");
		systemHelpEntity.setId(UUIDUtils.getUUID());
		//设置为激活状态
		systemHelpEntity.setActive(FossConstants.ACTIVE);
		//设置时间
		systemHelpEntity.setCreateDate(new Date());
		systemHelpEntity.setModifyDate(new Date());
		//日志记录
		LOGGER.debug("end SystemHelpService addSystemHelpEntity(), id"+systemHelpEntity.getId());
		return systemHelpDao.addSystemHelpEntity(systemHelpEntity);
	}


	/** 
	 * 通过传过来的id 进行查询符合条件的信息
	 * @author foss-qiaolifeng
	 * @date 2013-8-8 下午5:28:12
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISystemHelpService#querySystemHelpEntityById(java.lang.String)
	 */
	@Override
	public SystemHelpEntity querySystemHelpEntityById(String id) {
		
		//判断插入的是否为空
		if (id ==null) {
			throw new BusinessException("传入的参数不能为空值");
		}
		LOGGER.info("begin SystemHelpService querySystemHelpEntityById()...");

		//日志记录
		LOGGER.debug("end SystemHelpService querySystemHelpEntityById(), id"+id);
		return systemHelpDao.querySystemHelpEntityById(id);
	}


	/** 
	 * 通过传人的对象修改信息
	 * @author foss-qiaolifeng
	 * @date 2013-8-9 上午9:21:35
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISystemHelpService#upadteSystemHelpEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.SystemHelpEntity)
	 */
	@Override
	public int upadteSystemHelpEntity(SystemHelpEntity entity) {
		if(entity ==null){
			throw new BusinessException("传入的值不允许为空");	
		}
		
		LOGGER.info("SystemHelpService upadteSystemHelpEntity()...");

		List<String> list =new ArrayList<String>();
		list.add(entity.getId());
		int result =systemHelpDao.deleteSystemHelpEntityById(list, entity.getModifyUser());
		//结果大于0 时 说明删除成功 ，
		if(result>0){
			
			//添加新的数据
			 entity.setActive(FossConstants.ACTIVE);
			 entity.setId(UUIDUtils.getUUID());
			 entity.setCreateDate(new Date());
			 entity.setModifyDate(new Date());
			 
			 return systemHelpDao.addSystemHelpEntity(entity);
		}else{
			 return FossConstants.FAILURE;
		}
	}


	/** 
	 *根据传过来的id集合进行废除
	 * @author foss-qiaolifeng
	 * @date 2013-8-9 上午9:24:30
	 * @param idList
	 * @param modifyUser
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISystemHelpService#deleteSystemHelpEntityById(java.util.List, java.lang.String)
	 */
	@Override
	public int deleteSystemHelpEntityById(List<String> idList, String modifyUser) {
		if(CollectionUtils.isEmpty(idList)){
			throw new BusinessException("传入的参数不能为空");
		}
		LOGGER.info("SystemHelpService deleteSystemHelpEntityById()...");
		return systemHelpDao.deleteSystemHelpEntityById(idList, modifyUser);
	}

}
