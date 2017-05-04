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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryRegionsEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * 快递派送区域的实现Dao
 * 
 * @author 130566
 * 
 */
public class ExpressDeliveryRegionsDao extends SqlSessionDaoSupport implements
		IExpressDeliveryRegionsDao {
	/**
	 * 命名空间
	 */
	private final static String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			+ ".expressDeliveryRegions.";
	/**
	 * 日志
	 */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ExpressDeliveryRegionsDao.class);
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
	public ExpressDeliveryRegionsEntity addExpressDeliveryRegions(
			ExpressDeliveryRegionsEntity entity) {
		// 检查合法性
		if (entity == null) {
			return null;
		}
		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());
		// 设置时间
		entity.setCreateDate(now);
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setModifyUser(entity.getCreateUser());
		// 设置版本号
		entity.setVersionNo(now.getTime());
		entity.setActive(FossConstants.ACTIVE);
		// 插入操作
		int result = this.getSqlSession().insert(
				NAMESPACE + "addExpressDeliveryRegions", entity);
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
	public ExpressDeliveryRegionsEntity deleteExpressDeliveryRegions(
			ExpressDeliveryRegionsEntity entity) {
		// 合法性检查
		if (null == entity) {
			return entity;
		}
		if (StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		Date now = new Date();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(now);
		entity.setVersionNo(now.getTime());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		map.put("conditionActive", FossConstants.ACTIVE);
		// 更新操作
		int result = this.getSqlSession().update(NAMESPACE + "deleteExpressDeliveryRegions", map);
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
	public ExpressDeliveryRegionsEntity updateExpressDeliveryRegions(
			ExpressDeliveryRegionsEntity entity) {
		// 检查合法性
		if (null == entity) {
			return entity;
		}
		if (StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		// 先作废掉旧的数据
		ExpressDeliveryRegionsEntity entity2 = deleteExpressDeliveryRegions(entity);
		// 若为空，作废失败
		if (null == entity2) {
			String msg = "更新时，作废失败";
			LOGGER.error(msg);
		}
		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());
		// 设置时间
		entity.setCreateDate(now);
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setCreateUser(entity.getModifyUser());
		// 设置版本号
		entity.setVersionNo(System.currentTimeMillis());
		entity.setActive(FossConstants.ACTIVE);
		// 插入操作
		int result = this.getSqlSession().insert(
				NAMESPACE + "addExpressDeliveryRegions", entity);
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
	public List<ExpressDeliveryRegionsEntity> queryExpressDeliveryRegionsList(
			ExpressDeliveryRegionsEntity entity, int start, int end) {
		// 合法验证
		if (null == entity) {
			entity = new ExpressDeliveryRegionsEntity();
		}
		entity.setActive(FossConstants.ACTIVE);
		RowBounds rowBounds = new RowBounds(start, end);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryExpressDeliveryRegionsEntities", entity,
				rowBounds);
	}
	/**
	 * <P>
	 * 查询总数
	 * <P> 
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:46:29
	 * @param entity
	 * @return
	 */
	@Override
	public long queryExpressDeliveryRegionsCount(
			ExpressDeliveryRegionsEntity entity) {
		// 合法性检查
		if (null == entity) {
			entity = new ExpressDeliveryRegionsEntity();
		}
		entity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "queryExpressDeliveryRegionsCount", entity);
	}
	/**
	 * <P>
	 * 根据code查询实体
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-14上午9:46:44
	 * @param Code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExpressDeliveryRegionsEntity queryExpressDeliveryRegionsEntityByCode(
			String code) {
		// 合法性检查
		if (StringUtils.isBlank(code)) {
			return null;
		}
		// 构造查询条件
		ExpressDeliveryRegionsEntity entity = new ExpressDeliveryRegionsEntity();
		entity.setCode(code);
		entity.setActive(FossConstants.ACTIVE);
		List<ExpressDeliveryRegionsEntity> entities = this.getSqlSession()
				.selectList(
						NAMESPACE + "queryExpressDeliveryRegionsEntityByCode",
						entity);
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
	public List<ExpressDeliveryRegionsEntity> queryRoot() {
		ExpressDeliveryRegionsEntity entity =new ExpressDeliveryRegionsEntity();
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
	public List<ExpressDeliveryRegionsEntity> queryExpressDeliveryRegionsByParentDistrictCode(
			String parentDistrictCode) {
		//合法性判断
		if(StringUtils.isBlank(parentDistrictCode)){
			return null;
		}
		//创建一个实体对象
		ExpressDeliveryRegionsEntity entity =new ExpressDeliveryRegionsEntity();
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
		ExpressDeliveryRegionsEntity entity =new ExpressDeliveryRegionsEntity();
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
	public int deleteRegionsByCodes(String[] codes, String deleteUser) {
		Map<String,Object> map =new HashMap<String, Object>();
		ExpressDeliveryRegionsEntity entity =new ExpressDeliveryRegionsEntity();
		Date now =new Date();
		entity.setModifyUser(deleteUser);
		entity.setModifyDate(now);
		entity.setActive(FossConstants.INACTIVE);
		entity.setVersionNo(now.getTime());
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
	public List<ExpressDeliveryRegionsEntity> queryCityAndCountyRegions(
			ExpressDeliveryRegionsEntity entity) {
		 
		return getSqlSession().selectList(NAMESPACE+"queryCityAndCountyRegions",entity);
	}
	/**
	 * 查询当前区域编码下级所有的行政区域
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressDeliveryRegionsEntity> queryDwonRegions(String[] codes) {
		List<ExpressDeliveryRegionsEntity> entities=new ArrayList<ExpressDeliveryRegionsEntity>();
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
	public List<ExpressDeliveryRegionsEntity> queryDeleteRegions(String[] codes) {
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
	public List<ExpressDeliveryRegionsEntity> queryRegions(
			ExpressDeliveryRegionsEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressDeliveryRegionByParentCode", entity);
	}
}
