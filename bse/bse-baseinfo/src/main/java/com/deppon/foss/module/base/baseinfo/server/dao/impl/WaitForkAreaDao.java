package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IWaitForkAreaDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaitForkAreaDistanceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaitForkAreaEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.base.util.define.NumberConstants;
public class WaitForkAreaDao extends SqlSessionDaoSupport implements IWaitForkAreaDao {
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.waitForkArea.";
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码查询待叉区
	 * @Time 2014-4-25
	 * @param transferCode
	 * @param limit
	 * @param start
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaitForkAreaEntity> queryWaitForkAreaByTransferCode(
			String organizationCode, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationCode", organizationCode);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryWaitForkAreaByTransferCode", map,rowBounds);
	}
	/**
	 * @author 李鹏飞
	 * @Description 新增待叉区
	 * @Time 2014-4-25
	 * @param entity
	 * @return
	 */
	@Override
	public int addWaitForkArea(WaitForkAreaEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		entity.setCreateDate(new Date());
		entity.setId(UUIDUtils.getUUID());
		entity.setVirtualCode(entity.getId());
		return this.getSqlSession().insert(NAMESPACE+"addWaitForkArea", entity);
	}
	/**
	 * @author 李鹏飞
	 * @Description 根据虚拟编码删除待叉区
	 * @Time 2014-4-25
	 * @param virtualCode
	 * @return
	 */
	@Override
	public int deleteWaitForkAreaByVirtualCode(WaitForkAreaEntity entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("virtualCode", entity.getVirtualCode());
		map.put("modifyUser", entity.getModifyUser());
		map.put("active", FossConstants.ACTIVE);
		map.put("active0", FossConstants.INACTIVE);
		return this.getSqlSession().update(NAMESPACE + "deleteWaitForkAreaByVirtualCode", map);
	}
	/**
	 * @author 李鹏飞
	 * @Description 新增待叉区与月台、货区距离
	 * @Time 2014-4-25
	 * @param distanceEntity
	 * @return
	 */
	@Override
	public int addWaitForkAreaDistance(
			List<WaitForkAreaDistanceEntity> distanceEntityList) {
		List<WaitForkAreaDistanceEntity> list=new ArrayList<WaitForkAreaDistanceEntity>();
		int result=0;
		//分批处理，每50条处理一次
		for(int i=0;i<distanceEntityList.size();i++){
			list.add(distanceEntityList.get(i));
			if(i%NumberConstants.NUMBER_50==0||i==distanceEntityList.size()-1){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("distanceEntityList", list);
				result+=this.getSqlSession().insert(NAMESPACE + "addWaitForkAreaDistance", map);
				list.clear();
			}
		}
		return result;
	}
	/**
	 * @author 李鹏飞
	 * @Description 根据待叉区编码删除待叉区与月台、货区距离
	 * @Time 2014-4-25
	 * @param waitForkAreaCode
	 * @return
	 */
	@Override
	public int deleteWaitForkAreaDistanceByCode(WaitForkAreaEntity entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waitForkAreaCode", entity.getWaitForkAreaCode());
		map.put("transferCode", entity.getOrganizationCode());
		if(StringUtil.isNotBlank(entity.getTargetCode())){
			map.put("targetCode", entity.getTargetCode());	
		}
		map.put("modifyUser", entity.getModifyUser());
		map.put("active", FossConstants.ACTIVE);
		map.put("active0", FossConstants.INACTIVE);
		if(StringUtil.isNotBlank(entity.getIsUpdate())){
			map.put("targetType", DictionaryValueConstants.DISTANCE_BETWEEN_WAITFORKAREA_PLATFORM);
		}
		return this.getSqlSession().update(NAMESPACE + "deleteWaitForkAreaDistanceByCode", map);
	}
	/**
	 * @author 李鹏飞
	 * @Description 根据待叉区虚拟编码查询待叉区
	 * @Time 2014-4-25
	 * @param waitForkAreaCode
	 * @return
	 */
	@Override
	public WaitForkAreaEntity queryWaitForkAreaByCode(
			String waitForkAreaCode,String virtuaCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waitForkAreaCode", waitForkAreaCode);
		map.put("virtualCode", virtuaCode);
		map.put("active", FossConstants.ACTIVE);
		return (WaitForkAreaEntity) this.getSqlSession().selectOne(NAMESPACE+"queryWaitForkAreaByCode", map);
	}
	/**
	 * @author lifanghong
	 * @Description 根据待叉区编码查询待叉区
	 * @Time 2014-4-25
	 * @param waitForkAreaCode
	 * @return
	 */
	@Override
	public WaitForkAreaEntity queryWaitForkAreaByTransferCodeAndWaitForkAreaCode(
			String waitForkAreaCode,String transferCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waitForkAreaCode", waitForkAreaCode);
		map.put("transferCode", transferCode);
		map.put("active", FossConstants.ACTIVE);
		return (WaitForkAreaEntity) this.getSqlSession().selectOne(NAMESPACE+"queryWaitForkAreaByTransferCodeAndWaitForkAreaCode", map);
	}
	/**
	 * @author 李鹏飞
	 * @Description 根据待叉区编码查询待叉区与月台、货区的距离
	 * @Time 2014-4-28
	 * @param waitForkAreaCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaitForkAreaDistanceEntity> queryWaitForkAreaDistanceByCode(
			String waitForkAreaCode,String transferCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waitForkAreaCode", waitForkAreaCode);
		map.put("transferCode", transferCode);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryWaitForkAreaDistanceByCode", map);
	}
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码查询待叉区总量
	 * @Time 2014-4-28
	 * @param transferCode
	 * @return
	 */
	@Override
	public long countWaitForkAreaByTransferCode(String transferCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transferCode", transferCode);
		map.put("active", FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"countWaitForkAreaByTransferCode", map);
	}
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码、月台编码查询待叉区与月台
	 * @Time 2014-4-28
	 * @param transferCode
	 * @param platformCode
	 * @return List<WaitForkAreaDistanceEntity> 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaitForkAreaDistanceEntity> queryDistanceBetweenPlatform(
			String transferCode, String platformCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transferCode", transferCode);
		map.put("platformCode", platformCode);
		map.put("targetType", DictionaryValueConstants.DISTANCE_BETWEEN_WAITFORKAREA_PLATFORM);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryDistanceBetweenPlatform", map);
	}
	
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码、待叉区编码、库区编码查询待叉区与库区距离
	 * @Time 2014-4-28
	 * @param transferCode
	 * @param goodsAreaCode
	 * @param waitForkAreaCode
	 * @return WaitForkAreaDistanceEntity
	 */
	@Override
	public WaitForkAreaDistanceEntity queryDistanceBetweenGoodsArea(
			String transferCode, String goodsAreaCode, String waitForkAreaCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transferCode", transferCode);
		map.put("goodsAreaCode", goodsAreaCode);
		if(StringUtil.isNotBlank(waitForkAreaCode)){
			map.put("waitForkAreaCode", waitForkAreaCode);
		}
		map.put("targetType", DictionaryValueConstants.DISTANCE_BETWEEN_WAITFORKAREA_GOODSAREA);
		map.put("active", FossConstants.ACTIVE);
		List<WaitForkAreaDistanceEntity> n = this.getSqlSession().selectList(NAMESPACE+"queryDistanceBetweenGoodsArea", map);
		if(CollectionUtils.isNotEmpty(n)){
			if(n.size()>=1){
				return n.get(0);
			}else{
				return null;
			}
		}else{
			return null;
		}
		
	}
	
}
