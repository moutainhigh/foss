package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressTrainProgramEntity;

/**
 * 
 *<p>Title: IExpressTrainProgramDao</p>
 * <p>Description:快递支线班车方案Dao接口 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-12
 */
public interface IExpressTrainProgramDao {
	/**
	 *<p>Title: addExpressTrainProgram</p>
	 *<p>新增快递班车</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午4:19:16
	 * @param entity
	 * @return
	 */
	ExpressTrainProgramEntity addExpressTrainProgram(ExpressTrainProgramEntity entity);
	/**
	 *<p>Title: queryExpressTrainProgramList</p>
	 *<p>分页查询方案集合</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午2:13:33
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<ExpressTrainProgramEntity> queryExpressTrainProgramList(ExpressTrainProgramEntity entity, int start,int limit);
	/**
	 *<p>Title: queryCount</p>
	 *<p>查询记录数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午2:15:45
	 * @param entity
	 * @return
	 */
	long queryCount(ExpressTrainProgramEntity entity);
	/**
	 *<p>Title: queryExpressTrainProgramByProgramName</p>
	 *<p>根据方案名称查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午4:10:13
	 * @param ProgramName
	 * @return
	 */
	ExpressTrainProgramEntity queryExpressTrainProgramByProgramName(String programName);
	/**
	 * 
	 *<p>Title: deleteExpressTrainProgram</p>
	 *<p>根据id 作废方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午2:38:48
	 * @param entity
	 * @return
	 */
	int deleteExpressTrainProgram(ExpressTrainProgramEntity entity);
	/**
	 * 
	 *<p>Title: updateExpressTrainProgram</p>
	 *<p>更新方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午4:07:07
	 * @param programEntity
	 * @return
	 */
	int updateExpressTrainProgram(ExpressTrainProgramEntity programEntity);
}
