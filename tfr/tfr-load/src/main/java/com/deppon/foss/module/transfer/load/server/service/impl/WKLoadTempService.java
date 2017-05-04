package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.common.api.shared.dto.WKLoadTempDto;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IWKLoadTempDao;
import com.deppon.foss.module.transfer.load.api.server.service.IWKLoadTempService;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKLoadEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * @description 同步给悟空创建装车,完成装车的临时表(T_OPT_WK_LOAD_TEMP)操作
 * @version 1.0
 * @author 328864-foss-xieyang
 * @update 2016年5月11日 下午4:32:14
 */
public class WKLoadTempService implements IWKLoadTempService {

	private IWKLoadTempDao wkLoadTempDao;
	
	/**
	 * 依赖注入DAO
	 */
	private IPDALoadDao pdaLoadDao;



	private static final Logger LOGGER = Logger.getLogger(WKLoadTempService.class);

	/**
	 * @description 从表T_OPT_WK_LOAD_TEMP中删除数据 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IWKLoadTempService#deleteData(java.lang.String)
	 * @author 328864-foss-xieyang
	 * @update 2016年5月11日 下午4:49:58
	 * @version V1.0
	 */
	@Override
	public int deleteData(WKLoadTempDto dto) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("调用" + wkLoadTempDao.getClass().getName() + ".deleteData开始");
		}

		int res = wkLoadTempDao.deleteData(dto);

		if (res == 0) {
			LOGGER.error("删除数据失败,请检查数据");
		} 

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("调用" + wkLoadTempDao.getClass().getName() + ".deleteData结束");
		}
		return res;
	}

	/**
	 * @description 从T_OPT_WK_LOAD_TEMP表中分页查询数据 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IWKLoadTempService#getDatasByPage(int,
	 *      int, int)
	 * @author 328864-foss-xieyang
	 * @update 2016年5月11日 下午4:50:01
	 * @version V1.0
	 */
	@Override
	public List<WKLoadTempDto> getDatasByPage(int taskType, int offset, int limit) {
		return wkLoadTempDao.getDatasByPage(taskType, offset, limit);
	}

	/**
	 * @description 获取表T_OPT_WK_LOAD_TEMP记录总条数 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IWKLoadTempService#getCount(int)
	 * @author 328864-foss-xieyang
	 * @update 2016年5月11日 下午4:50:04
	 * @version V1.0
	 */
	@Override
	public int getCount(int taskType) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("调用" + wkLoadTempDao.getClass().getName() + ".getCount开始");
		}
		int res = wkLoadTempDao.getCount(taskType);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("调用" + wkLoadTempDao.getClass().getName() + ".getCount开始");
		}
		return res;
	}

	/**
	 * @description 向表T_OPT_WK_LOAD_TEMP添加数据 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IWKLoadTempService#inertData(com.deppon.foss.module.transfer.load.api.shared.dto.WKLoadTempDto)
	 * @author 328864-foss-xieyang
	 * @update 2016年5月11日 下午4:50:07
	 * @version V1.0
	 */
	@Override
	public int inertData(WKLoadTempDto dto) {
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("调用" + wkLoadTempDao.getClass().getName() + ".inertData开始");
		}
		int res = wkLoadTempDao.inertData(dto);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("调用" + wkLoadTempDao.getClass().getName() + ".inertData开始");
		}
		return res;
	}

	public void setWkLoadTempDao(IWKLoadTempDao wkLoadTempDao) {
		this.wkLoadTempDao = wkLoadTempDao;
	}

	@Override
	public int updateWKLoadTempDto(String taskNo) {
		return wkLoadTempDao.updateWKLoadTempDto(taskNo);
	}

	@Override
	public void updateWKLoadTempDtoState() {
		wkLoadTempDao.updateWKLoadTempDtoState();
		
	}
	
	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}

	@Override
	@Transactional
	public Map<String, Object> insertWKLoad(WKLoadEntity wkLoadEntity) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", false);
		// 把悟空装车任务的实体修改为foss能操作的实体
		LoadTaskEntity loadTaskEntity = null;
		try {
			loadTaskEntity = wkLoadEntity.getLoadTaskEntity().transEntity();
		} catch (Exception e) {
			LOGGER.error("实体对象不能为空:" + e.getMessage());
			result.put("errMsg","实体对象不能为空:" + e.getMessage());
			result.put("result",false);
			return result;
		}
		
		List<LoaderParticipationEntity> loaders = wkLoadEntity.getLoaderParticipationEntityList();
		loadTaskEntity.setLoaderQty(loaders == null ? 0 : loaders.size());
		try {
			// 获取id
			String loadTaskId = UUIDUtils.getUUID();
			loadTaskEntity.setId(loadTaskId);
			int res = pdaLoadDao.insertTransferLoadTask(loadTaskEntity);
			if (res == 1) {
				result.put("result",true);
			} else {
				result.put("errMsg","请检查数据字段是否正确");
				LOGGER.error("请检查数据字段是否正确");
			}
					
			if(loaders != null) {
				for (LoaderParticipationEntity loader : loaders) {
					loader.setId(UUIDUtils.getUUID());
					loader.setTaskId(loadTaskId);
					if(loader.getJoinTime() == null) {
						loader.setJoinTime(new Date());
					}
					if(loader.getLeaveTime() == null) {
						loader.setLeaveTime(new Date());
					}
					if(loader.getTaskType() == null) {
						loader.setTaskType("DELIVER_LOAD");
					}
					
				}
				
				
				//插入理货员
				pdaLoadDao.insertTransferLoaderParticipation(loaders);
			}
			
			
		} catch (Exception e) {
			result.put("errMsg","插入数据库失败,错误信息:" + e.getMessage());
			result.put("result",false);
			LOGGER.error("插入数据库失败,错误信息:" + e.getMessage(),e);
		}

		return result;
		
	}
	
	@Override
	public Map<String, Object> updateWKLoad(WKLoadEntity wkLoadEntity) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", false);
		// 把悟空装车任务的实体修改为foss能操作的实体
		if (wkLoadEntity == null || wkLoadEntity.getLoadTaskEntity() == null) {
			//sonar优化 为空判断 218427
			LOGGER.error("传入参数为空: wkLoadEntity = ;或wkLoadEntity.getLoadTaskEntity()为空" );
			result.put("errMsg","传入参数为空:");
			result.put("result",false);
			return result;
		}
		LoadTaskEntity loadTaskEntity = wkLoadEntity.getLoadTaskEntity().updateEntity();
		
		try {
			// 获取id
			int res = pdaLoadDao.updateLoadTask(loadTaskEntity);
			if (res == 1) {
				result.put("result",true);
			} else {
				result.put("errMsg","请检查数据字段是否正确");
				LOGGER.error("请检查数据字段是否正确");
			}
			
			//插入理货员
//			pdaLoadDao.insertTransferLoaderParticipation(wkLoadEntity.getLoaders());
			
			
		} catch (Exception e) {
			result.put("errMsg","插入数据库失败,错误信息:" + e.getMessage());
			LOGGER.error("插入数据库失败,错误信息:" + e.getMessage(),e);
		}

		return result;
		
	}

	
	
}
