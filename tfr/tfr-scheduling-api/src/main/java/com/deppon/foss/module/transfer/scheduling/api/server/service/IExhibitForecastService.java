package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitForecastDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitForecastForWorldDto;

/**
 * 展会货货量统计
 * @author 200978  xiaobingcheng
 * 2014-11-26
 */
public interface IExhibitForecastService extends IService {
	
	/**
	 * 展会货货量统计 job调用
	 * @Author: 200978  xiaobingcheng
	 * 2014-11-26
	 * @param day    取前day天的展会货
	 * @param scheduledFireTime    job开始执行的时间（当前时间）
	 */
	public void forecastExhibitCargo(int day,Date scheduledFireTime);
	
	
	/**
	 * 根据当前部门和货物状态 查询所有的展会货货量统计
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-5
	 * @param dto    当前部门和货物状态
	 * @return
	 */
	public ExhibitForecastDto queryForecastExhibitList(ExhibitForecastDto dto,int start,int limit);
	
	/**
	 * 根据当前部门和货物状态 查询所有的展会货货量统计 de 总记录数
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-5
	 * @param dto    当前部门和货物状态
	 * @return
	 */
	public Long queryForecastExhibitListCount(ExhibitForecastDto dto);
	
	/**
	 * 生成导出文件名称
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-6
	 * @param fileName
	 * @return
	 * @throws TfrBusinessException
	 */
	public String encodeFileName(String fileName);
	
	/**
	 * 导出展会货信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-6
	 * @param exhibitForecastDto
	 * @return
	 */
	public InputStream queryStatisticalInquiriesExcelStream(ExhibitForecastDto exhibitForecastDto);
	
	/**
	 * 根据当前的员工的CODE查找所对应的外场或者营业部
	 * @param org
	 * @return
	 */
	public String findTransforCenter(String org);

	/**
	 * 查询全国外场展会货货量统计信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-8
	 * @param transferCenterCode
	 * @return
	 */
	public List<ExhibitForecastForWorldDto> queryForecastExhibitForWorldList(String transferCenterCode);
	
	/**
	 * 导出全国展会货统计货量查询到Excel
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-6
	 * @param forecastVO
	 * @return
	 * @throws TfrBusinessException
	 */
	public InputStream queryExhibitForWorldExcelStream(String transferCenterCode);
	
}
