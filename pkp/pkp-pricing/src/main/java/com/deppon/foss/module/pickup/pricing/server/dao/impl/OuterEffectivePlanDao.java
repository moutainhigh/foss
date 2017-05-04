package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IOuterEffectivePlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterEffectivePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterEffectivePlanConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterEffectivePlanDto;

public class OuterEffectivePlanDao extends iBatis3DaoImpl implements IOuterEffectivePlanDao {
	private static final String NAMESPACE = "foss.pkp.pricing.OuterEffectivePlanEntityMapper.";

	/**
	 * 根据条件查询后台数据的条数
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-3-26 10:22:05
	 */
	@Override
	public Long queryOuterEffectivePlanVoBatchCount(
			OuterEffectivePlanConditionDto outerEffectivePlanConditionDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryOuterEffectivePlanVoBatchCount", outerEffectivePlanConditionDto);
	}

	/**
	 * 根据条件查询后台数据，如果不想分页，可以采取传递 0 0 过来进行后台数据的查询，目前只有导出的时候才才用到该方式
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-3-26 10:21:42
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OuterEffectivePlanDto> queryOuterPriceVoBatchInfo(
			OuterEffectivePlanConditionDto outerEffectivePlanConditionDto,
			int start, int limit) {
		//查询后台数据
		if(start ==0 && limit ==0){
			return this.getSqlSession().selectList(NAMESPACE+"queryOuterPriceVoBatchInfo", outerEffectivePlanConditionDto);
		}
		//否则进行分页查询
		RowBounds row = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryOuterPriceVoBatchInfo", outerEffectivePlanConditionDto, row);
	}

	/**
	 * 激活或者中止时效方案
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-3-26 10:20:27
	 */
	@Override
	public int updateActiveToYesOrNo(String outerEffectivePlanId, String no) {
		Map<Object, Object> maps = new HashMap<Object, Object>();
		maps.put("outerEffectivePlanId", outerEffectivePlanId);
		maps.put("no", no);
		return this.getSqlSession().update(NAMESPACE+"updateActiveToYesOrNo", maps);
	}

	/**
	 * 根据主键进行更新数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-3-26 10:20:02
	 */
	@Override
	public int updateOuterEffectivePlanEndTime(OuterEffectivePlanEntity record) {
		return this.getSqlSession().update(NAMESPACE+"updateOuterEffectivePlanEndTime", record);
	}

	/**
	 * 根据主键进行批量激活或者中止
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月26日 10:19:45
	 */
	@Override
	public int updateOuterEffectivePlanActiveById(List<String> ids,
			String yesOrNo) {
		Map<Object, Object> maps = new HashMap<Object, Object>();
		//偏线时效方案ID集合
		maps.put("outerEffectivePlanIds", ids);
		//是否激活
		maps.put("yesOrNo", yesOrNo);
		return this.getSqlSession().update(NAMESPACE+"updateOuterEffectivePlanActiveById", maps);
	}

	/**
	 * 根据主键查询数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-3-26 10:19:16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OuterEffectivePlanEntity selectByPrimaryKey(
			String outerEffectivePlanId) {
		Map<Object, Object> maps = new HashMap<Object, Object>();
		//偏线时效方案ID
		maps.put("outerEffectivePlanId", outerEffectivePlanId);
		List<OuterEffectivePlanEntity> list = this.getSqlSession().selectList(NAMESPACE+"selectByPrimaryKey", maps);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据主键跟新数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月26日 10:18:52
	 */
	@Override
	public int updateByPrimaryKeySelective(OuterEffectivePlanEntity opt) {
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective", opt);
	}
	/**
	 * 根据方案名称查询偏线时效方案，
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月26日 10:18:34
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OuterEffectivePlanEntity> queryOuterEffectivePlanByName(
			String name) {
		Map<Object, Object> maps = new HashMap<Object, Object>();
		maps.put("name", name);
		return this.getSqlSession().selectList(NAMESPACE+"queryOuterEffectivePlanByName", maps);
	}
	/**
	 * 选择性的插入后台数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-3-26 10:17:39
	 */
	@Override
	public int insertSelective(OuterEffectivePlanEntity outerEffectivePlanEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insertSelective", outerEffectivePlanEntity);
	}

	/**
	 * 检查是否存在相同的时效方案
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月26日 10:17:10
	 */
	@Override
	public int checkIsExistName(OuterEffectivePlanEntity record) {
		return (Integer)getSqlSession().selectOne(NAMESPACE+"checkIsExistName", record);
	}
	/**
	 * 拷贝偏线时效方案
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月26日 10:16:00
	 */
	@Override
	public int copyOuterEffectivePlan(OuterEffectivePlanEntity record) {
		return this.getSqlSession().insert(NAMESPACE+"insertSelective", record);
	}

	/**
	 * 根据主键进行数据的查询
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-3-26 10:15:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OuterEffectivePlanDto selectById(String outerEffectivePlanId) {
		Map<Object, Object> maps = new HashMap<Object, Object>();
		maps.put("outerEffectivePlanId", outerEffectivePlanId);
		List<OuterEffectivePlanDto> list = this.getSqlSession().selectList(NAMESPACE+"selectById", maps);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 进行偏线时效的批量删除
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月26日 10:15:19
	 */
	@Override
	public int deleteByPrimaryKey(List<String> outerEffectivePlanIds) {
		Map<Object, Object> maps = new HashMap<Object, Object>();
		//偏线时效ID集合
		maps.put("outerEffectivePlanIds", outerEffectivePlanIds);
		return this.getSqlSession().delete(NAMESPACE+"deleteByPrimaryKey", maps);
	}

	/**
	 * 根据外发外场、偏线代理网点、产品类型、时间进行查询偏线时效数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月24日 11:11:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OuterEffectivePlanEntity queryOuterEffectPlanByFieldAndBranch(String outFieldCode, String outerBranchCode, String productCode, Date billDate) {
		Map<Object, Object> maps = new HashMap<Object, Object>();
		//外场编码
		maps.put("outFieldCode", outFieldCode);
		//偏线代理网点
		maps.put("outerBranchCode", outerBranchCode);
		//产品类型
		maps.put("productCode", productCode);
		//业务时间
		maps.put("billDate", billDate);
		List<OuterEffectivePlanEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryOuterEffectPlanByFieldAndBranch", maps);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
}
