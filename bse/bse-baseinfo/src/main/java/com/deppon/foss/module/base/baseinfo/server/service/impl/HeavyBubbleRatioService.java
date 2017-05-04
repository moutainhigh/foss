package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IHeavyBubbleRatioDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IHeavyBubbleRatioService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HeavyBubbleRatioEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.HeavyBubbleRatioException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 218392 张永雪
 *
 * 2014-11-19下午3:53:00
 */
public class HeavyBubbleRatioService implements IHeavyBubbleRatioService{

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HeavyBubbleRatioService.class);
	
	/**
	 * 注入IHeavyBubbleRatioDao
	 */
	private IHeavyBubbleRatioDao heavyBubbleRatioDao;
	/**
	 * 设置重泡比Dao接口
	 */
	public void setHeavyBubbleRatioDao(IHeavyBubbleRatioDao heavyBubbleRatioDao) {
		this.heavyBubbleRatioDao = heavyBubbleRatioDao;
	}

	/**
	 * 验证重泡比外场是否存在
	 */
	public boolean queryOutfieldNameByHeavyBubbleRatio(String outfield){
		if(StringUtil.isBlank(outfield)){
		    return false;
		}else {
		    return heavyBubbleRatioDao.queryOutfieldNameByHeavyBubbleRatio(outfield);
		}
	}
	/**
	 *  根据外场code查询重泡比参数
	 */
	@Override
	public String queryHeavyBubbleParamByOutfield(String outfield) {
		if(StringUtil.isBlank(outfield)){
			throw new HeavyBubbleRatioException("传入的外场参数不能为空");
		}else {
			 return heavyBubbleRatioDao.queryHeavyBubbleParamByOutfield(outfield);
		}
	   
	}
	
	
	/**
	 * 新增重泡比信息
	 * @author 218392
	 */
	
	@Override
	public int addHeavyBubbleRatio(HeavyBubbleRatioEntity entity) {
		if(null == entity){
			throw new HeavyBubbleRatioException("传入的参数不允许为空!");
		}
		UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
		String createUser = user.getEmployee().getEmpCode();// 当前登录用户
		entity.setCreateUser(createUser);
		//第一次记录新增时，虚拟编码为记录的id
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		entity.setActive(FossConstants.ACTIVE);
		LOGGER.debug("id : "+entity.getId());
		return heavyBubbleRatioDao.addHeavyBubbleRatio(entity);
	}

	
	/**
	 * 修改重泡比信息
	 * @author 218392
	 */
	@Override
	public int updateHeavyBubbleRatio(HeavyBubbleRatioEntity entity) {
		if(null == entity){
			throw new HeavyBubbleRatioException("传入的参数不允许为空！");
		}else if(StringUtils.isBlank(entity.getId())){
			throw new HeavyBubbleRatioException("ID不允许为空!");
		}
		List<String> list = new ArrayList<String>();
		list.add(entity.getId());
		//作废重泡比信息
		int result = heavyBubbleRatioDao.deleteHeavyBubbleRatio(list);
		list = null;
		if(result > 0){
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String modifyUser = user.getEmployee().getEmpCode();// 当前登录用户
			entity.setModifyUser(modifyUser);
			//作废成功
			entity.setActive(FossConstants.ACTIVE);
			entity.setId(UUIDUtils.getUUID());
			entity.setModifyDate(new Date());
			return heavyBubbleRatioDao.addHeavyBubbleRatio(entity);
		}else{
			return FossConstants.FAILURE;
		}
	}

	
	/**
	 * 作废重泡比信息
	 * @author 218392
	 * 
	 */
	@Override
	public int deleteHeavyBubbleRatio(List<String> idList) {
		if(CollectionUtils.isEmpty(idList)){
			throw new HeavyBubbleRatioException("虚拟编码不允许为空！");
		}else{
			return heavyBubbleRatioDao.deleteHeavyBubbleRatio(idList);
		}
	}

	
	
	
	/**
	 * 根据输入条件查询重泡比信息
	 * @author 218392
	 */
	@Override
	public List<HeavyBubbleRatioEntity> queryAllHeavyBubbleRatio(
			HeavyBubbleRatioEntity entity, int limit, int start) {
		if(null == entity){
			throw new HeavyBubbleRatioException("传入参数不允许为空！");
		}else{
			entity.setActive(FossConstants.ACTIVE);
			return heavyBubbleRatioDao.queryAllHeavyBubbleRatio(entity, limit, start);
		}
		
	}
	
	
	/**
	 * 统计总记录数
	 * @author 218392
	 */
	@Override
	public Long queryRecordCount(HeavyBubbleRatioEntity entity) {
		if(null == entity){
			throw new HeavyBubbleRatioException("传入参数不允许为空");
		}else{
			entity.setActive(FossConstants.ACTIVE);
			return heavyBubbleRatioDao.queryRecordCount(entity);
		}
	}
	
}
