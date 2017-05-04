package com.deppon.pda.bdm.module.foss.upgrade.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.foss.upgrade.server.dao.IBaseDataVerGenDao;
import com.deppon.pda.bdm.module.foss.upgrade.shared.vo.BaseDataModel;

/**
 * 
 * TODO(基础数据DAO抽象类)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-3-20 下午5:28:26,content:TODO </p>
 * @author chengang
 * @date 2013-3-20 下午5:28:26
 * @since
 * @version
 */

public abstract class AbstractBaseDataVerGenDao<T> extends SqlSessionDaoSupport
		implements IBaseDataVerGenDao<T> {
	
	/**
	 * 
	 * <p>TODO(查询基础数据集合)</p> 
	 * @author chengang
	 * @date 2013-3-20 下午5:26:24
	 * @param model
	 * @return 
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.dao.IBaseDataVerGenDao#queryLocalDatas(com.deppon.pda.bdm.module.foss.upgrade.shared.vo.BaseDataModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryLocalDatas(BaseDataModel model) {
		return getSqlSession().selectList(getClass().getName() + ".queryLocalDatas",
				model);
	}
	
	/**
	 * 
	 * <p>TODO(查询增量基础数据)</p> 
	 * @author chengang
	 * @date 2013-3-20 下午5:27:45
	 * @param dataModel
	 * @return 
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.dao.IBaseDataVerGenDao#queryLocalIncDataList(com.deppon.pda.bdm.module.foss.upgrade.shared.vo.BaseDataModel)
	 */
	@Override
	public Integer queryLocalIncDataList(BaseDataModel dataModel) {
		return (Integer) getSqlSession().selectOne(getClass().getName() + ".queryLocalIncDataList",
				dataModel);
	}
	
	/**
	 * 
	 * <p>TODO(查询增量删除数据)</p> 
	 * @author chengang
	 * @date 2013-6-13 上午10:36:26
	 * @param model
	 * @return 
	 * @see com.deppon.pda.bdm.module.foss.upgrade.server.dao.IBaseDataVerGenDao#queryLocalDatasByDel(com.deppon.pda.bdm.module.foss.upgrade.shared.vo.BaseDataModel)
	 */
	@Override
	public List<T> queryLocalDatasByDel(BaseDataModel model) {
		return getSqlSession().selectList(getClass().getName() + ".queryLocalDatasByDel",
				model);
	}
}
