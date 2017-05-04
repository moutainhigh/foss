package com.deppon.foss.module.login.server.dao;

import java.util.List;

import com.deppon.foss.module.login.shared.domain.GuiMonitorEntity;
import com.deppon.foss.module.login.shared.domain.GuiMonitorLogEntity;

/**
 * 
 * @Description: GUI客户端用户登录采集接口类
 * IGuiMonitorDao.java Create on 2013-4-24 上午9:50:00
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public interface IGuiMonitorDao {
	/**
	 * 
	 * @Description: 增加GUI客户端用户登录采集信息
	 * @author FOSSDP-sz
	 * @date 2013-4-24 上午9:50:37
	 * @param guiMonitorEntity
	 * @version V1.0
	 */
	void insert(GuiMonitorEntity guiMonitorEntity);
	
	/**
	 * 
	 * @Description: 查询登陆信息 MANA-2018
	 * @author 157229-zxy
	 * @date 2014-3-11 
	 * @param id
	 * @version V1.0
	 */
	GuiMonitorEntity selectById(String id);
	
	/**
	 * 
	 * @Description: 更新登陆信息 MANA-2018
	 * @author 157229-zxy
	 * @date 2014-3-11 
	 * @param id
	 * @version V1.0
	 */
	void updateGuiMonitor(GuiMonitorEntity guiMonitorEntity);
	
	/**
	 * 
	 * @Description: 查询登陆人数 MANA-2018
	 * @author 157229-zxy
	 * @date 2014-4-8 
	 * @param guiMonitorEntity
	 * @version V1.0
	 */
	Long selectCountByPara(GuiMonitorEntity guiMonitorEntity);
	
	/**
	 * 
	 * @Description: 查询登陆信息 MANA-2018
	 * @author 157229-zxy
	 * @date 2014-4-8 
	 * @param guiMonitorEntity
	 * @version V1.0
	 */
	List<GuiMonitorEntity> selectGuiMonitorByPara(GuiMonitorEntity guiMonitorEntity);
	
	/**
	 * (非 Javadoc) 添加一个月干掉
		* @author 270293-foss-zhangfeng
		* @date 2015-8-31 上午9:46:28 
	    * <p>Title: insertLog</p> 
	    * <p>Description:添加</p> 
	    * @param @param log
	    * @return void
	 */
	void insertLog(GuiMonitorLogEntity log);
}
