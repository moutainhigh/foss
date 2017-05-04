package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoadManagerExceptionEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskEntityDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskExceptionDto;

/**
 * 查询异常装车任务Dao
 * @author 332209-FOSS-ruilibao
 * @date 2017年3月29日
 */
public interface ILoadManagerExceptionDao {
	
	/**
	 * 查询装车任务异常记录数
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年3月29日
	 * @param loadManagerExceptionVo
	 * @return
	 */
	Long queryLoadManagerExceptionCount(LoadManagerExceptionEntity loadManagerExceptionEntity);
	
	/**
	 * 查询装车任务异常
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年3月30日
	 * @param loadManagerExceptionEntity
	 * @return
	 */
	List<LoadTaskExceptionDto> queryLoadManagerException(LoadManagerExceptionEntity loadManagerExceptionEntity,int limit,int start);
	
	/**
	 * 查询扫描件数
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年4月7日
	 * @param taskNo
	 * @return
	 */
	int queryScanQtyCount(String taskNo);
	
	/**
	 * 查询装车实体
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年4月7日
	 * @param taskNo
	 * @return
	 */
	LoadTaskEntityDto queryLoadManagerEntity(String taskNo);
}