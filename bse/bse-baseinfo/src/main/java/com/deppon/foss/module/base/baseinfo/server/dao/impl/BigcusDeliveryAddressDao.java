package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBigcusDeliveryAddressDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 零担大客户派送地址库 Dao
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2016-5-30 上午9:24:36,content:TODO </p>
 * @author 232607 
 * @date 2016-5-30 上午9:24:36
 * @since
 * @version
 */
public class BigcusDeliveryAddressDao extends SqlSessionDaoSupport implements
		IBigcusDeliveryAddressDao {
	/**
	 * 命名空间
	 */
	private final static String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			+ ".bigcusDeliveryAddress.";
	/**
	 * 日志
	 */
//	private final static Logger LOGGER = LoggerFactory
//			.getLogger(BigcusDeliveryAddressDao.class);
	/**
	 * <P>
	 * 添加实体
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:43:56
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public BigcusDeliveryAddressEntity addBigcusDeliveryAddress(
			BigcusDeliveryAddressEntity entity) {
		// 检查合法性
		if (entity == null) {
			return null;
		}
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setActive(FossConstants.ACTIVE);
		// 插入操作
		int result = this.getSqlSession().insert(
				NAMESPACE + "addBigcusDeliveryAddress", entity);
		return result == NumberConstants.ZERO ? null : entity;
	}

	/**
	 * <P>
	 * 删除作废实体
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:44:48
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public BigcusDeliveryAddressEntity deleteBigcusDeliveryAddress(
			BigcusDeliveryAddressEntity entity) {
		// 合法性检查
		if (null == entity) {
			return entity;
		}
		if (StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(new Date());
		entity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		map.put("conditionActive", FossConstants.ACTIVE);
		// 更新操作
		int result = this.getSqlSession().update(NAMESPACE + "deleteBigcusDeliveryAddress", map);
		return result == NumberConstants.ZERO ? null : entity;
	}
	/**
	 * <P>
	 * 更新实体
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:45:13
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public BigcusDeliveryAddressEntity updateBigcusDeliveryAddress(
			BigcusDeliveryAddressEntity entity) {
		// 检查合法性
		if (null == entity) {
			return entity;
		}
		if (StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		// 先作废掉旧的数据
		deleteBigcusDeliveryAddress(entity);
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setActive(FossConstants.ACTIVE);
		// 插入操作
		int result = this.getSqlSession().insert(
				NAMESPACE + "addBigcusDeliveryAddress", entity);
		return result == NumberConstants.ZERO ? null : entity;
	}

	/**
	 * <P>
	 * 根据entity动态查询
	 * <P> 
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:45:54
	 * @param entity
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BigcusDeliveryAddressEntity> queryBigcusDeliveryAddressList(
			BigcusDeliveryAddressEntity entity, int start, int end) {
		// 合法验证
		if (null == entity) {
			entity = new BigcusDeliveryAddressEntity();
		}
		entity.setActive(FossConstants.ACTIVE);
		RowBounds rowBounds = new RowBounds(start, end);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryBigcusDeliveryAddressEntities", entity,
				rowBounds);
	}
	/** 
	 * <p>分页查询的查询总数</p> 
	 * @author 232607 
	 * @date 2016-5-28 下午4:06:08
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBigcusDeliveryAddressDao#queryBigcusDeliveryAddressCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity)
	 */
	@Override
	public long queryBigcusDeliveryAddressCount(
			BigcusDeliveryAddressEntity entity) {
		// 合法性检查
		if (null == entity) {
			entity = new BigcusDeliveryAddressEntity();
		}
		entity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "queryBigcusDeliveryAddressCount", entity);
	}
	/** 
	 * <p>根据编码查询区域实体</p> 
	 * @author 232607 
	 * @date 2016-5-28 下午4:05:35
	 * @param code
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBigcusDeliveryAddressDao#queryBigcusDeliveryAddressEntityByCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BigcusDeliveryAddressEntity queryBigcusDeliveryAddressEntityByCode(
			String code) {
		// 合法性检查
		if (StringUtils.isBlank(code)) {
			return null;
		}
		// 构造查询条件
		BigcusDeliveryAddressEntity entity = new BigcusDeliveryAddressEntity();
		entity.setCode(code);
		entity.setActive(FossConstants.ACTIVE);
		List<BigcusDeliveryAddressEntity> entities = this.getSqlSession().selectList(
				NAMESPACE + "queryBigcusDeliveryAddressEntityByCode",entity);
		// 判断是否为空
		if (CollectionUtils.isEmpty(entities)) {
			return null;
		}
		return entities.get(0);
	}
	/**
	 *<P>查询区域的根节点<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-17下午5:52:15
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BigcusDeliveryAddressEntity> queryRoot() {
		BigcusDeliveryAddressEntity entity =new BigcusDeliveryAddressEntity();
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryRoot", entity);
	}
	/**
	 *<P>根据上级区域编码查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-18上午9:08:30
	 * @param parentDistrictCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BigcusDeliveryAddressEntity> queryBigcusDeliveryAddressByParentDistrictCode(
			String parentDistrictCode) {
		//合法性判断
		if(StringUtils.isBlank(parentDistrictCode)){
			return null;
		}
		//创建一个实体对象
		BigcusDeliveryAddressEntity entity =new BigcusDeliveryAddressEntity();
		entity.setParentDistrictCode(parentDistrictCode);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryByParentDistrictCode", entity);
	}
	/**
	 *<P>根据上级编码查询子节点最大编码<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-24上午11:11:49
	 * @param parentDistrictCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryMaxCodeChildRegions(
			String parentDistrictCode) {
		if(StringUtils.isBlank(parentDistrictCode)){
			return null;
		}
		BigcusDeliveryAddressEntity entity =new BigcusDeliveryAddressEntity();
		entity.setParentDistrictCode(parentDistrictCode);
		entity.setActive(FossConstants.ACTIVE);
		return (String) getSqlSession().selectOne(NAMESPACE+"queryMaxCodeChildRegions", entity);
	}
	/**
	 *<P>根据codes批量作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-26下午4:41:05
	 * @param codes
	 * @param deleteUser
	 * @return
	 */
	@Override
	public int deleteRegionsByCodes(String[] codes) {
		Map<String,Object> map =new HashMap<String, Object>();
		BigcusDeliveryAddressEntity entity =new BigcusDeliveryAddressEntity();
		entity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setModifyDate(new Date());
		entity.setActive(FossConstants.INACTIVE);
		//构造map
		map.put("entity", entity);
		map.put("codes",codes);
		map.put("conditionActive", FossConstants.ACTIVE);
		//执行操作
		return getSqlSession().update(NAMESPACE+"deleteRegionsByCodes",map);
	}

	/**
	 * 查询当前区域上级的城市和区县
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BigcusDeliveryAddressEntity> queryCityAndCountyRegions(
			BigcusDeliveryAddressEntity entity) {
		 
		return getSqlSession().selectList(NAMESPACE+"queryCityAndCountyRegions",entity);
	}
	/**
	 * 查询当前区域编码下级所有的行政区域
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BigcusDeliveryAddressEntity> queryDwonRegions(String[] codes) {
		List<BigcusDeliveryAddressEntity> entities=new ArrayList<BigcusDeliveryAddressEntity>();
		for(String code:codes){
			Map<String,String> map =new HashMap<String, String>();
			map.put("code", code);
			entities.addAll(getSqlSession().selectList(NAMESPACE+"queryCityAndCountyRegions",map));
		}
		return entities;
	}
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BigcusDeliveryAddressEntity> queryDeleteRegions(String[] codes) {
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("codes",codes);
		map.put("conditionActive", FossConstants.ACTIVE);
		map.put("degree", "DISTRICT_COUNTY");
		return this.getSqlSession().selectList(NAMESPACE+"queryDeleteRegions", map);
	}

	/**
	 * 根据上级区域编码查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BigcusDeliveryAddressEntity> queryRegions(
			BigcusDeliveryAddressEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressDeliveryRegionByParentCode", entity);
	}
}
