package com.deppon.pda.bdm.module.core.server.dao;

import java.util.Date;
import java.util.List;
/**
 * 
 * TODO(缓存查询服务DAO)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-3-21 上午9:34:54,content:TODO </p>
 * @author Administrator
 * @date 2013-3-21 上午9:34:54
 * @since
 * @version
 */
public interface IBaseDao<T> {
	/**
	 * 
	 * <p>TODO(获取最后更新时间)</p> 
	 * @author Administrator
	 * @date 2013-3-21 上午9:37:37
	 * @return
	 * @see
	 */
	Date getLastModifyTime();
	/**
	 * 
	 * <p>TODO(根据ID获取ENTITY)</p> 
	 * @author Administrator
	 * @date 2013-3-21 上午9:37:55
	 * @param id
	 * @return
	 * @see
	 */
	T getEntityById(String id);
	
	List<T> getEntitiesByLastModifyTime(Date date);
	
	List<T> getEntityByIds(List<String> ids);
}
