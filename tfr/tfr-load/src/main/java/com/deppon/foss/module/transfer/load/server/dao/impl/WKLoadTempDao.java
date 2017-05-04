package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.dto.WKLoadTempDto;
import com.deppon.foss.module.transfer.load.api.server.dao.IWKLoadTempDao;


/**
* @description  同步给悟空创建装车,完成装车的临时表(T_OPT_WK_LOAD_TEMP)操作
* @version 1.0
* @author 328864-foss-xieyang
* @update 2016年5月11日 下午4:30:52
*/
public class WKLoadTempDao  extends iBatis3DaoImpl implements IWKLoadTempDao {
	
	private static final String NAMESPACE = "wk-load-temp.";
	
	private static final Logger LOGGER = Logger.getLogger(WKLoadTempDao.class);

	@Override
	public int inertData(WKLoadTempDto dto) {
		String sql = NAMESPACE + "insertData";
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("向T_OPT_WK_LOAD_TEMP表里面插入数据开始");
		}
		
		int result = 0;
		
		try {
			// 插入交接单
			result = this.getSqlSession().insert(sql, dto);
		} catch(Exception e) {
			LOGGER.error(e);
		}
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("向T_OPT_WK_LOAD_TEMP表里面插入数据结束");
		}
		
		return result;
	}

	@Override
	public int deleteData(WKLoadTempDto dto) {
		String sql = NAMESPACE + "deleteData";
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("从表T_OPT_WK_LOAD_TEMP删除数据开始");
		}
		
		int result = 0;
		try {
			result = this.getSqlSession().delete(sql, dto);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("从表T_OPT_WK_LOAD_TEMP删除数据结束");
		}
		return result;
	}

	@Override
	public List<WKLoadTempDto> getDatasByPage(int taskType, int offset, int limit) {
		String sql = NAMESPACE + "getDatasByPage";
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("从表T_OPT_WK_LOAD_TEMP查询数据开始");
		}
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<WKLoadTempDto> list = null;
		try {
			list = this.getSqlSession().selectList(sql, taskType, rowBounds );
		} catch (Exception e) {
			LOGGER.error(e);
		}
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("从表T_OPT_WK_LOAD_TEMP查询数据结束");
		}
		return list;
	}

	@Override
	public int getCount(int taskType) {
		String sql = NAMESPACE + "getCount";
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("获取表T_OPT_WK_LOAD_TEMP开始");
		}
		
		int count = 0;
		try {
			count = (Integer) this.getSqlSession().selectOne(sql,taskType);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("获取表T_OPT_WK_LOAD_TEMP结束");
		}
		return count;
	}

	@Override
	public int isLeasedTruck(String newVehicleNo) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "isLeasedTruck", newVehicleNo);
	}

	@Override
	public int updateWKLoadTempDto(String taskNo) {
		return this.getSqlSession().update(NAMESPACE + "updateWKLoadTempDto", taskNo);
	}

	@Override
	public void updateWKLoadTempDtoState() {
		this.getSqlSession().update(NAMESPACE + "updateWKLoadTempDtoState");
	}


}
