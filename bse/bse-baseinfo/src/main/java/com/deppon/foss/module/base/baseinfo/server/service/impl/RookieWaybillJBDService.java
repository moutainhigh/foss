package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IRookieWaybillJBDDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRookieWaybillJBDService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncRookieWaybillJBDService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RookieWaybillJBDEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TaobaoDepponDistrictMapEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.RookieWaybillJBDException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ShortFieldMapException;

@Transactional
public class RookieWaybillJBDService implements IRookieWaybillJBDService{
	
	private IRookieWaybillJBDDao rookieWaybillJBDDao;
	
	/**
	 * 同步接口
	 */
	private ISyncRookieWaybillJBDService  syncRookieWaybillJBDService;
	
	public void setSyncRookieWaybillJBDService(
			ISyncRookieWaybillJBDService syncRookieWaybillJBDService) {
		this.syncRookieWaybillJBDService = syncRookieWaybillJBDService;
	}

	public void setRookieWaybillJBDDao(IRookieWaybillJBDDao rookieWaybillJBDDao) {
		this.rookieWaybillJBDDao = rookieWaybillJBDDao;
	}
	
	/**
	 * <p> 新增集包地信息</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public long addRookieWaybillJBD(RookieWaybillJBDEntity entity) {
		// TODO Auto-generated method stub
		if(null==entity){
			throw new RookieWaybillJBDException("传入参数为空");
		}
		//将省市区的name拼接起来
		StringBuffer start=new StringBuffer();
		start.append(entity.getStartProvinceName()).append(entity.getStartCityName()).append(entity.getStartCountyName());
		String startAddress=start.toString();
		StringBuffer reach=new StringBuffer();
		reach.append(entity.getReachProvinceName()).append(entity.getReachCityName()).append(entity.getReachCountyName());
		String reachAddress=reach.toString();
		entity.setStartAddress(startAddress);
		entity.setReachAddress(reachAddress);
		List<RookieWaybillJBDEntity> entityList=rookieWaybillJBDDao.queryRookieWaybillJBDByAddress(entity);
		if(entityList.size()!=0){
			throw new RookieWaybillJBDException("已经存在发货地址-收货地址的映射关系！请勿再添加!");
		}
		long addEntity = rookieWaybillJBDDao.addRookieWaybillJBD(entity);
		//0新增，1修改，2删除
		//调用同步方法    273311  
		if(addEntity!=0){
			List<RookieWaybillJBDEntity> syncentitys = new ArrayList<RookieWaybillJBDEntity>();
			syncentitys.add(entity);
			syncRookieWaybillJBDService.syncToDopOms(syncentitys, "0");
		}
		return addEntity;
	}
	/**
	 * <p>分页查询</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public List<RookieWaybillJBDEntity> queryRookieWaybillJBDByCondition(
			RookieWaybillJBDEntity entity, int start, int limit) {
		// TODO Auto-generated method stub
		//判断是否为空
		if (entity == null) {
			entity=new RookieWaybillJBDEntity();
		}		
		String typeCode=entity.getTypeCode();
		if("2".equals(typeCode)){
			entity.setTypeCode(null);
		}
		return rookieWaybillJBDDao.queryRookieWaybillJBDByCondition(entity, start, limit);
	}
	
	/**
	 * <p> 查询总数</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public long queryCount(RookieWaybillJBDEntity entity) {
		// TODO Auto-generated method stub
		if(entity==null){
			entity=new RookieWaybillJBDEntity();
		}
		String typeCode=entity.getTypeCode();
		if("2".equals(typeCode)){
			entity.setTypeCode(null);
		}
		return rookieWaybillJBDDao.queryCount(entity);
	}
	
	/**
	 * <p> 删除信息</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public long deleteRookieWaybillJBD(List<String> ids) {
		// TODO Auto-generated method stub
		if(CollectionUtils.isEmpty(ids)){
			throw new RookieWaybillJBDException("传入参数有误");
		}
		//通过ID查询出实体
		List<RookieWaybillJBDEntity> entities=rookieWaybillJBDDao.queryRookieWaybillJBDByIds(ids);
		long deleteEntity = rookieWaybillJBDDao.deleteRookieWaybillJBD(ids);
		//同步数据   273311 
		if(deleteEntity!=0){

			syncRookieWaybillJBDService.syncToDopOms(entities, "2");	
		}
		return deleteEntity;
	}
	/**
	 * <p> 跟新信息</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public long updateRookieWaybillJBD(RookieWaybillJBDEntity entity) {
		if (entity == null) {
			throw new ShortFieldMapException("传入参数为空");
		}
		//将省市区拼接起来
		StringBuffer start=new StringBuffer();
		start.append(entity.getStartProvinceName()).append(entity.getStartCityName()).append(entity.getStartCountyName());
		String startAddress=start.toString();
		StringBuffer reach=new StringBuffer();
		reach.append(entity.getReachProvinceName()).append(entity.getReachCityName()).append(entity.getReachCountyName());
		String reachAddress=reach.toString();
		entity.setStartAddress(startAddress);
		entity.setReachAddress(reachAddress);
		//跟新数据都是先删除再作废
		List<String> ids=new ArrayList<String>();
		ids.add(entity.getId());
		rookieWaybillJBDDao.deleteRookieWaybillJBD(ids);
		long addEntity = rookieWaybillJBDDao.addRookieWaybillJBD(entity);
		//同步数据 273311 
		if(addEntity!=0){
			List<RookieWaybillJBDEntity> syncentitys = new ArrayList<RookieWaybillJBDEntity>();
			syncentitys.add(entity);
			syncRookieWaybillJBDService.syncToDopOms(syncentitys, "1");
		}
		/*//跟新数据都是先删除再作废
		List<String> ids=new ArrayList<String>();
		ids.add(entity.getId());
		rookieWaybillJBDDao.deleteRookieWaybillJBD(ids);
		return rookieWaybillJBDDao.addRookieWaybillJBD(entity);*/
		return addEntity;
	}
    @Override
	public TaobaoDepponDistrictMapEntity queryDistrictIfEqual(TaobaoDepponDistrictMapEntity entity){
		if(entity==null){
			return null;
		}
		return rookieWaybillJBDDao.queryDistrictIfEqual(entity);
	}

}
