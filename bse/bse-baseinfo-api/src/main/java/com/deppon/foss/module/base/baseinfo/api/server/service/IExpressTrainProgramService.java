package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressTrainProgramEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressTrainProgramDto;

/**
 * 
 *<p>Title: IExpressTrainProgramService</p>
 * <p>Description:快递支线班车方案Service接口 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-12
 */
public interface IExpressTrainProgramService {
	/**
	 *<p>Title: addExpressTrainProgram</p>
	 *<p>新增快递方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午2:08:52
	 * @param dto
	 * @return
	 */
	ExpressTrainProgramEntity addExpressTrainProgram(ExpressTrainProgramDto dto);
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
	 *<p>Title: deleteProgramList</p>
	 *<p>批量作废方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午2:15:26
	 * @param programList
	 * @param modifyUser
	 * @return
	 */
	int deleteProgramList(List<ExpressTrainProgramEntity> programList,
			String modifyUser);
	/** 
	 *<p>Title: deleteProgram</p>
	 *<p>根据id作废方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午2:17:39
	 * @param entity
	 * @return
	 */
	int deleteProgram(ExpressTrainProgramEntity entity);
	/**
	 * 
	 *<p>Title: updateExpressTrainProgram</p>
	 *<p>更新方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午3:48:01
	 * @param programEntity
	 * @return
	 */
	int updateExpressTrainProgram(ExpressTrainProgramEntity programEntity);
	
}
