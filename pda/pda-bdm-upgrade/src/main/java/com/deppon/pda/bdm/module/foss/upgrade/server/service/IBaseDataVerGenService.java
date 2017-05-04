package com.deppon.pda.bdm.module.foss.upgrade.server.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.foss.upgrade.shared.exception.DataTimeOutException;
import com.deppon.pda.bdm.module.foss.upgrade.shared.vo.QueryLocalListDto;

/**
 * @author lxw
 * @version 1.0
 * @created 2012-09-22
 */
public interface IBaseDataVerGenService<T> {
	/**
	 * 
	 * <p>TODO(获取对象路径)</p> 
	 * @author chengang
	 * @date 2013-6-29 下午6:22:48
	 * @return
	 * @see
	 */
	public String getBaseDataClassName();
	
	/**
	 * 
	 * <p>TODO(初始化基础资料查询参数)</p> 
	 * @author chengang
	 * @date 2013-6-29 下午6:22:48
	 * @return
	 * @see
	 */
	public QueryLocalListDto initBaseDataParam(AsyncMsg asyncMsg,QueryLocalListDto dto);
	/**
	 * 查询基础数据并生成文件
	 * 
	 * @param startDate
	 * @param verName
	 * @param file
	 * @return
	 * @throws DataTimeOutException
	 */
	public void genBaseDataFile(QueryLocalListDto dto);

	/**
	 * 查询本地基础数据
	 * 
	 * @param str
	 * @param dateVer
	 * @param currVer
	 * @return
	 */
	public List<T> queryLocalEntityList(QueryLocalListDto queryDto);

	/**
	 * <p>
	 * TODO(查询基础数据表中删除数据)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-6-13 上午10:20:39
	 * @param queryDto
	 * @return
	 * @see
	 */
	public List<T> queryLocalDelList(QueryLocalListDto queryDto);

	/**
	 * 生成完整基础数据文件路径
	 * 
	 * @param currVer
	 * @param file
	 * @return
	 */
	public String bulidAllPath(String currVer, File file);

	/**
	 * 生成增量基础数据文件路径
	 * 
	 * @param dataVer
	 * @param currVer
	 * @param file
	 * @return
	 */
	public String bulidIncPath(String dataVer, String currVer, File file,
			String delFlag);

	/**
	 * 处理本地数据
	 * 
	 * @param localDatas
	 * @return
	 */
	public String dealLocalDatas(Set<T> localDatas);
	
	/**
	 * <p>TODO(处理删除数据)</p> 
	 * @author chengang
	 * @date 2013-6-13 上午11:41:35
	 * @param localDatas
	 * @return
	 * @see
	 */
	public String dealLocalDatasByDel(Set<T> localDatas);

	/**
	 * 生成基础数据文件
	 * 
	 * @param filePath
	 * @param buffer
	 */
	public void zipAllBaseDataFiles(String filePath, String buffer);

	/**
	 * 替换属性中的换行符、竖线
	 * 
	 * @param attribute
	 * @return
	 */
	public String replaceAttribute(String attribute);

	/**
	 * 查询基础数据表是否有更新
	 * 
	 * @param str
	 * @param dateVer
	 * @param currVer
	 * @return
	 *//*
	public Integer queryLocalIncDataList(String dataVer, String currVer);*/

	/**
	 * 
	 * <p>
	 * TODO(判断属性是否为空)
	 * </p>
	 * 
	 * @author chengang
	 * @date 2013-3-28 下午2:55:41
	 * @param object
	 * @return
	 * @see
	 */
	public String isNull(String object);
	
	/**
	 * 
	* @Description: TODO 可下载用户类型
	* @return
	* @return String    
	* @author mt
	* @date 2013-8-27 上午9:02:20
	 */
	public String getUserType();
}