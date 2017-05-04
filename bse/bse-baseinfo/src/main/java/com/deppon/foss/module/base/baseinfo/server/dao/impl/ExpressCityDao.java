package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressCityDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto;

/**
 * 试点城市dao
 * @author foss-qiaolifeng
 * @date 2013-7-17 下午2:45:46
 */
public class ExpressCityDao extends SqlSessionDaoSupport implements
		IExpressCityDao {

	 /**
     * 定义命名空间
     */
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.expressCityEntity.";
	
	/** 
	 * 查询总条数
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午2:45:49
	 * @param expressCityQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressCityDao#queryExpressCityCountByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto)
	 */
	@Override
	public long queryExpressCityCountByCondition(
			ExpressCityQueryDto expressCityQueryDto) {
		return (Long) getSqlSession().selectOne(
				NAMESPACE + "queryExpressCityCountByCondition", expressCityQueryDto);
	}

	/** 
	 * 查询列表
	 * @author foss-qiaolifengen 
	 * @date 2013-7-17 下午2:45:51
	 * @param expressCityQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressCityDao#queryExpressCityListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressCityEntity> queryExpressCityListByCondition(
			ExpressCityQueryDto expressCityQueryDto, int start, int limit) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession()
				.selectList(NAMESPACE + "queryExpressCityListByCondition",
						expressCityQueryDto, rowBounds);
	}

	/** 
	 * 根据ID查询一条快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-18 下午4:27:01
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressCityDao#queryOneExpressCityById(java.lang.String)
	 */
	@Override
	public ExpressCityEntity queryOneExpressCityById(ExpressCityQueryDto expressCityQueryDto) {
		
		return (ExpressCityEntity)getSqlSession().selectOne(NAMESPACE + "queryOneExpressCityById",expressCityQueryDto);
	}

	/** 
	 * 根据城市编码和有效状态查询快递代理/试点城市信息
	 * @author foss-qiaolifeng
	 * @date 2013-7-19 上午10:32:41
	 * @param expressCityQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressCityDao#queryOneExpressCityByCodeAndType(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressCityEntity> queryOneExpressCityListByCodeAndType(
			ExpressCityQueryDto expressCityQueryDto) {
		
		return this.getSqlSession().selectList(NAMESPACE + "queryOneExpressCityByCodeAndType",expressCityQueryDto);
	}

	/** 
	 * 新增快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-22 上午9:19:32
	 * @param expressCityEntity
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressCityDao#addExpressCityEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity)
	 */
	@Override
	public int addExpressCityEntity(ExpressCityEntity expressCityEntity) {

		//验证数据合法性
		if(expressCityEntity==null){
			throw new BusinessException("新增快递代理/试点城市时参数异常");
		}
		
		//新增
		int result = getSqlSession().insert(NAMESPACE + "insert", expressCityEntity);
		return result;
	}

	/** 
	 * 修改快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-22 上午10:33:54
	 * @param expressCityEntity
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressCityDao#updateExpressCityEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity)
	 */
	@Override
	public int updateExpressCityEntity(ExpressCityEntity expressCityEntity) {

		//验证数据合法性
		if(expressCityEntity==null){
			throw new BusinessException("修改快递代理/试点城市时参数异常");
		}
		
		//执行修改操作
		int result = getSqlSession().update(NAMESPACE + "updateExpressCityEntity", expressCityEntity);
		
		return result;
	}

	/** 
	 * 根据营业部网点编码获取该营业网点所在城市的快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-23 下午1:59:38
	 * @param orgCode
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressCityDao#queryExpressCityByOrgCode(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ExpressCityEntity> queryExpressCityByOrgCode(String orgCode,String active) {
		
		if(StringUtils.isEmpty(orgCode)){
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgCode", orgCode);
		map.put("active", active);
	
		List<ExpressCityEntity> list= getSqlSession().selectList(NAMESPACE + "queryExpressCityByOrgCode",map);
		
		return list;
		
	}
	
	  /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午7:55:49
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressCityEntity> queryExpressCityForDownloadByPage(
    		ExpressCityEntity entity, int start, int limited){
	    RowBounds rowBounds = new RowBounds(start, limited);
	    ExpressCityEntity queryEntity = entity == null ? new ExpressCityEntity() : entity;
		return (List<ExpressCityEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressCityForDownload", queryEntity, rowBounds);
    }
    
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午7:55:49
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ExpressCityEntity> queryExpressCityForDownload(ExpressCityEntity entity){
 		return (List<ExpressCityEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressCityForDownload", entity );
    
    }

	
}
