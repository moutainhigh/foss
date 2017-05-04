package com.deppon.pda.bdm.module.core.server.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.pda.bdm.module.core.server.dao.IBaseDao;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;

/**
 * 
  * @ClassName UserProvider 
  * @Description UserEntity 的数据提供类 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class UserProvider implements ITTLCacheProvider<UserEntity> {
	
	private IBaseDao<UserEntity> baseDao;
	
	
//	/**
//	  * 此方法从数据库获取最后一次修改时间
//	  */
//	@Override
//	public Date getLastModifyTime() {
//		return baseDao.getLastModifyTime();
//	}
//
//	/**
//	  * 此方法是在外部cache调用get方法时如果cache中找不到数据会调用这个方法去数据库中返回数据
//	  * dpap缓存管理会把获取的这个数据自动放入cache中提供下次调用
//	  */
//	@Override
//	public UserEntity get(String key) {
//		return baseDao.getEntityById(key);
//	}
//	/**
//	  * 此方法是cache自动刷新或cache调用refresh方法，dpap缓存管理会调用此方法刷新数据
//	  * 次参数time是通过getLastModifyTime方法返回的最后一次修改时间
//	  */
//	@Override
//	public Map<String, UserEntity> getUpdateObjectMaps(Date time) {
//	 	Map<String, UserEntity> userMap = new HashMap<String, UserEntity>();
//		List<UserEntity> users = baseDao.getEntitiesByLastModifyTime(time);
//	 	for(UserEntity user:users){
//	 		userMap.put(user.getEmpCode(), user);
//	 	}
//		return userMap;
//	}
//
//	/**
//	  * 此方法是cache调用refresh(String... keys)方法，dpap缓存管理会调用此方法刷新数据
//	  * 次参数keys是需要刷新数据的key值
//	  */
//	@Override
//	public Map<String, UserEntity> getUpdateObjectMaps(String... keys) {
//		Map<String, UserEntity> userMap = new HashMap<String, UserEntity>();
//		List<UserEntity> users = new ArrayList<UserEntity>();
//		for(String key : keys){
//			UserEntity userEntity = baseDao.getEntityById(key);
//			if(userEntity != null){
//				users.add(userEntity);
//			}
//		}
//		for(UserEntity user:users){
//	 		userMap.put(user.getEmpCode(), user);
//	 	}
//		return userMap;
//	}

	
	public void setBaseDao(IBaseDao<UserEntity> baseDao) {
		this.baseDao = baseDao;
	}


	@Override
	public UserEntity get(String key) {
		return baseDao.getEntityById(key);
	}

}
