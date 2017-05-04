package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressTrainProgramDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressTrainProgramEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 *<p>Title: ExpressTrainProgramDao</p>
 * <p>Description:快递支线班车方案Dao实现类 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-12
 */
public class ExpressTrainProgramDao extends SqlSessionDaoSupport implements
		IExpressTrainProgramDao {
	private static final String NAMESPACE =ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			+ ".expressTrainProgram.";
	/**
	 *<p>Title: addExpressTrainProgram</p>
	 *<p>新增快递支线班车方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午4:27:10
	 * @param entity
	 * @return
	 */
	@Override
	public ExpressTrainProgramEntity addExpressTrainProgram(
			ExpressTrainProgramEntity entity) {
		Date now =new Date();
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		entity.setCreateDate(now);
		entity.setModifyUser(entity.getCreateUser());
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setVersionNo(now.getTime());
		int result =this.getSqlSession().insert(NAMESPACE+"addExpressTrainProgram",entity);
		return result ==NumberConstants.ZERO? null:entity;
	}
	/**
	 *<p>Title: queryExpressTrainProgramList</p>
	 *<p>分页查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午4:27:38
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressTrainProgramEntity> queryExpressTrainProgramList(
			ExpressTrainProgramEntity entity, int start, int limit) {
		RowBounds rowBounds =new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressTrainProgramList", entity, rowBounds);
	}
	/**
	 *<p>Title: queryCount</p>
	 *<p>查询记录数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午4:27:57
	 * @param entity
	 * @return
	 */
	@Override
	public long queryCount(ExpressTrainProgramEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryCount", entity);
	}
	/**
	 *<p>Title: queryExpressTrainProgramByProgramName</p>
	 *<p>根据名称查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午4:28:21
	 * @param ProgramName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExpressTrainProgramEntity queryExpressTrainProgramByProgramName(
			String programName) {
		ExpressTrainProgramEntity entity =new ExpressTrainProgramEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setProgramName(programName);
		List<ExpressTrainProgramEntity> resultList =this.getSqlSession().selectList(NAMESPACE+"queryExpressTrainProgramByProgramName", entity);
		if(CollectionUtils.isEmpty(resultList)){
			return null;
		}
		return resultList.get(0);
	}
	/**
	 *<p>Title: deleteExpressTrainProgram</p>
	 *<p>作废方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午2:39:34
	 * @param entity
	 * @return
	 */
	@Override
	public int deleteExpressTrainProgram(ExpressTrainProgramEntity entity) {
		entity.setActive(FossConstants.INACTIVE);
		Date now =new Date();
		entity.setModifyDate(now);
		entity.setVersionNo(now.getTime());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		map.put("conditionActive", FossConstants.ACTIVE);
		return this.getSqlSession().update(NAMESPACE+"deleteExpressTrainProgram", map);
	}
	/**
	 *<p>Title: updateExpressTrainProgram</p>
	 *<p>更新方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午4:07:59
	 * @param programEntity
	 * @return
	 */
	@Override
	public int updateExpressTrainProgram(ExpressTrainProgramEntity programEntity) {
		//非空校验
		if(null ==programEntity ||StringUtils.isBlank(programEntity.getId())){
			return NumberConstants.ZERO;
		}
		//先作废
		int result =this.deleteExpressTrainProgram(programEntity);
		//若作废失败，则更新失败
		if(result<1){
			return NumberConstants.ZERO;
		}
		//新增
		programEntity.setId(UUIDUtils.getUUID());
		Date now =new Date();
		programEntity.setCreateDate(now);
		programEntity.setModifyDate(now);
		programEntity.setCreateUser(programEntity.getModifyUser());
		programEntity.setVersionNo(now.getTime());
		programEntity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().insert(NAMESPACE+"addExpressTrainProgram", programEntity);
	}

}
