package com.deppon.pda.bdm.module.foss.upgrade.server.dao;

import java.util.List;
import com.deppon.pda.bdm.module.foss.upgrade.shared.vo.BaseDataModel;


/**
 * 
 * TODO(基础数据DAO接口)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-3-20 下午5:30:35,content:TODO </p>
 * @author chengang
 * @date 2013-3-20 下午5:30:35
 * @since
 * @version
 */
public interface IBaseDataVerGenDao<T> {
	
	/**
	 * 
	 * <p>TODO(查询基础数据集合)</p> 
	 * @author chengang
	 * @date 2013-3-20 下午5:30:51
	 * @param model
	 * @return
	 * @see
	 */
	public List<T> queryLocalDatas(BaseDataModel model);
	
	/**
	 * <p>TODO(查询删除的数据)</p> 
	 * @author chengang
	 * @date 2013-6-13 上午10:23:40
	 * @param model
	 * @return
	 * @see
	 */
	public List<T> queryLocalDatasByDel(BaseDataModel model);
	
	/**
	 * 
	 * <p>TODO(查询增量基础数据)</p> 
	 * @author chengang
	 * @date 2013-3-20 下午5:31:11
	 * @param dataModel
	 * @return
	 * @see
	 */
	public Integer queryLocalIncDataList(BaseDataModel dataModel);

}
