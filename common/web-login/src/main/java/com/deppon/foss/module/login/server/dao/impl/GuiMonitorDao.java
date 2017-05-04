package com.deppon.foss.module.login.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.login.server.dao.IGuiMonitorDao;
import com.deppon.foss.module.login.shared.domain.GuiMonitorEntity;
import com.deppon.foss.module.login.shared.domain.GuiMonitorLogEntity;

/**
 * 
 * @Description: GUI客户端用户登录采集DAO
 * GuiMonitorDao.java Create on 2013-4-24 上午9:50:55
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class GuiMonitorDao extends SqlSessionDaoSupport implements IGuiMonitorDao {

	private static final String NAMESPACE = "foss.login.guimonitor.";
	
	private static final String NAMESPACE_LOG="foss.login.guimonitorlog.";

	@Override
	public void insert(GuiMonitorEntity guiMonitorEntity) {
		getSqlSession().insert(NAMESPACE + "insertGuiMonitor", guiMonitorEntity);
	}

	@Override
	public GuiMonitorEntity selectById(String id) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("id", id);
		return (GuiMonitorEntity)getSqlSession().selectOne(NAMESPACE + "selectById", params);
		
	}

	@Override
	public void updateGuiMonitor(GuiMonitorEntity guiMonitorEntity) {
		getSqlSession().selectOne(NAMESPACE + "updateGuiMonitor", guiMonitorEntity);
		
	}

	@Override
	public Long selectCountByPara(GuiMonitorEntity guiMonitorEntity) {
		return (Long)getSqlSession().selectOne(NAMESPACE + "selectCountByPara", guiMonitorEntity);
	}

	@Override
	public List<GuiMonitorEntity> selectGuiMonitorByPara(
			GuiMonitorEntity guiMonitorEntity) {
		return (List<GuiMonitorEntity>)getSqlSession().selectList(NAMESPACE + "selectGuiMonitorByPara", guiMonitorEntity);
	}

	/** (非 Javadoc)添加登录信息 
	* @author 270293-pkpfoss-zhangfeng 
	* @date 2015-8-31 上午10:06:27 
	* <p>Title: insertLog</p> 
	* <p>Description: </p> 
	* @param log 
	* @see com.deppon.foss.module.login.server.dao.IGuiMonitorDao#insertLog(com.deppon.foss.module.login.shared.domain.GuiMonitorLogEntity) 
	*/
	@Override
	public void insertLog(GuiMonitorLogEntity log) {
		getSqlSession().insert(NAMESPACE_LOG + "insertGuiMonitorlog", log);		
	}
}
