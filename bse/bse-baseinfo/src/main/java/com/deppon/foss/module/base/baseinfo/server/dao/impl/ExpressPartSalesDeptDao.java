package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPartSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressSaleDepartmentResultDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递点部营业部映射关系Dao实现
 * 
 * @author foss-qiaolifeng
 * @date 2013-7-25 上午10:13:35
 */
public class ExpressPartSalesDeptDao extends SqlSessionDaoSupport implements
		IExpressPartSalesDeptDao {

	/**
	 * 定义命名空间
	 */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.expressPartSalesDeptEntity.";

	
	/** 
	 * 根据快递点部名称查询快递点部营业部映射关系总条数
	 * @author foss-qiaolifeng
	 * @date 2013-8-27 下午2:35:36
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao#queryTotalByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@Override
	public long queryTotalByCondition(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto) {
		return (Long) getSqlSession().selectOne(
				NAMESPACE + "queryTotalByCondition", expressPartSalesDeptQueryDto);
	}
	
	/**
	 * 根据快递点部名称查询快递点部营业部映射关系
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-25 上午10:13:43
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao#queryExpressPartSalesDeptByExpressPartName(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressPartSalesDeptResultDto> queryExpressPartSalesDeptByCondition(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto, int start, int limit) {

		// 查询数据
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryExpressPartSalesDeptByCondition",
				expressPartSalesDeptQueryDto,rowBounds);
	}

	/**
	 * 根据营业部编码查询快递点部信息(只查询有效信息)
	 * 这里有疑问 TODO
	 * @author foss-zhangjiheng
	 * @date 2013-7-25 上午10:06:34
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	public ExpressPartSalesDeptResultDto queryExpressPartSalesDeptBySalesCode(
			String salesDeptCode) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("salesDeptCode", salesDeptCode);
		map.put("contionActive", FossConstants.ACTIVE);
		return (ExpressPartSalesDeptResultDto)this.getSqlSession().selectOne(
				NAMESPACE + "queryExpressPartSalesDeptBySalesCode", map);
		
	}

	/**
	 * 根据营业部编码和时间查询快递点部信息(可以查询无效信息)
	 * 
	 * @author foss-zhangjiheng
	 * @date 2013-7-25 上午10:06:34
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExpressPartSalesDeptResultDto> queryExpressPartSalesDeptBySalesCodeAndTime(
			String salesDeptCode, Date creteTime) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("salesDeptCode", salesDeptCode);
//		map.put("contionActive", FossConstants.ACTIVE);
		map.put("createTime", creteTime);
		return  this.getSqlSession().selectList(
				NAMESPACE + "queryExpressPartSalesDeptBySalesCodeAndTime", map);
	}

	/**
	 * 根据快递点部编码查询快递点部营业部映射关系
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 上午11:15:19
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao#queryExpressPartSalesDeptByExpressPartCode(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressPartSalesDeptResultDto> queryExpressPartSalesDeptByExpressPartCode(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto) {

		// 查询数据
		return this.getSqlSession().selectList(
				NAMESPACE + "queryExpressPartSalesDeptByExpressPartCode",
				expressPartSalesDeptQueryDto);

	}

	/**
	 * 根据营业部编码列表查询营业部扩展dto
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 下午1:51:29
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao#querySaleDepartmentResultDtoBySalesDeptCodeList(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressSaleDepartmentResultDto> querySaleDepartmentResultDtoBySalesDeptCodeList(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto) {
		// 查询数据
		return this.getSqlSession().selectList(
				NAMESPACE + "querySaleDepartmentResultDtoBySalesDeptCodeList",
				expressPartSalesDeptQueryDto);
	}

	/**
	 * 根据营业部条件查部营业部信息,多条件模糊查询
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 下午5:19:02
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao#querySaleDepartmentResultDtoByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressSaleDepartmentResultDto> querySaleDepartmentResultDtoByCondition(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto) {
		// 查询数据
		return this.getSqlSession().selectList(
				NAMESPACE + "querySalesDeptResultDtoListByCondition",
				expressPartSalesDeptQueryDto);
	}

	/**
	 * 根据快递点部编码、营业部编码查询有效的映射信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 下午2:09:14
	 * @param expressPartCode
	 * @param salesDeptCode
	 * @param active
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao#queryResultDtoByExpressPartCodeAndSalesDeptCode(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressPartSalesDeptResultDto> queryResultDtoByExpressPartCodeAndSalesDeptCode(
			String expressPartCode, String salesDeptCode, String active) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("expressPartCode", expressPartCode);
		map.put("salesDeptCode", salesDeptCode);
		map.put("active", active);
		map.put("activeY", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryResultDtoByExpressPartCodeAndSalesDeptCode",
				map);
	}

	/**
	 * 新增快递点部营业部映射信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 下午2:23:54
	 * @param entity
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao#addExpressPartSalesDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPartSalesDeptEntity)
	 */
	@Override
	public int addExpressPartSalesDept(ExpressPartSalesDeptEntity entity) {
		// 验证数据合法性
		if (entity == null) {
			throw new BusinessException("新增快递点部营业部映射信息时参数异常");
		}

		// 新增
		int result = getSqlSession().insert(NAMESPACE + "insert", entity);
		return result;
	}

	/**
	 * 修改快递点部营业部映射信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 下午2:44:21
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao#disableExpressPartSalesDeptByPartCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPartSalesDeptEntity)
	 */
	@Override
	public int updateExpressPartSalesDeptByPartCode(
			ExpressPartSalesDeptEntity entity) {
		
		// 修改
		int result = getSqlSession().update(
				NAMESPACE + "updateExpressPartSalesDept", entity);
		return result;
	}

	/** 
	 * 根据id查询快递点部营业部映射信息
	 * @author foss-qiaolifeng
	 * @date 2013-8-28 下午2:50:45
	 * @param ids
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao#queryInfosByIds(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressPartSalesDeptEntity> queryInfosByIds(List<String> ids) {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("ids", ids);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryInfosByIds",map);
	}

	/** 
	 * 根据id删除快递点部营业部映射信息
	 * @author foss-qiaolifeng
	 * @date 2013-8-28 下午2:50:48
	 * @param ids
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao#deleteInfosByIds(java.util.List)
	 */
	@Override
	public int deleteInfosByIds(List<String> ids) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("ids", ids);
		return this.getSqlSession().delete(
				NAMESPACE + "deleteInfosByIds",map);
	}

	/** 
	 * 根据营业部编码\激活状态查询营业部快递点部映射关系
	 * @author foss-qiaolifeng
	 * @date 2013-8-29 上午9:42:15
	 * @param salesDeptCode
	 * @param active
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao#queryInfosBySalesCode(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressPartSalesDeptEntity> queryInfosBySalesCode(
			String salesDeptCode, String active) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("salesDeptCode", salesDeptCode);
		map.put("active", active);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryInfosBySalesCode",map);
	}
	
	
}
