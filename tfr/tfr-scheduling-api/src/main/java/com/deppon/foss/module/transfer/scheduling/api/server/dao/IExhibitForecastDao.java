package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ExhibitForecastEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitForecastDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitForecastForWorldDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitWaybillDto;





/**
 * 展会货货量统计
 * @author 200978  xiaobingcheng
 * 2014-11-26
 */
public interface IExhibitForecastDao {

	/**
	 * 查询以scheduledFireTime开始之前day天的提货网点是驻地营业部未全部签收的展会货运单
	 * @Author: 200978  xiaobingcheng
	 * 2014-11-27
	 * @param day
	 * @param scheduledFireTime
	 * @return
	 */
	List<ExhibitWaybillDto> queryExhibitWaybillOfNotSignAndStation(int day,Date scheduledFireTime);
	
	/**
	 * 新增展会货信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-3
	 * @param exhibitForecastEntity
	 */
	void saveExhibitForecastEntity(ExhibitForecastEntity exhibitForecastEntity);
	
	/**
	 * 根据运单号查询有效的展会货统计数量
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-3
	 * @param waybillNo
	 * @return
	 */
	int queryOldExhibitCargoByWaybillNo(String waybillNo);
	
	/**
	 * 设置该运单的统计信息为无效，方便后面添加新的信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-3
	 * @param waybillNo
	 */
	void cancleOldExhibitCargoByWaybillNo(String waybillNo);
	
	/**
	 * 查询展会货列表
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-5
	 * @param dto
	 * @return
	 */
	public List<ExhibitForecastEntity> queryForecastExhibitList(ExhibitForecastDto dto,int start,int limit);
	
	/**
	 * 查询展会货总记录数
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-5
	 * @param dto
	 * @return
	 */
	public Long queryForecastExhibitListCount(ExhibitForecastDto dto);
	
	/**
	 * 根据外场和货区类型查询货区
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-6
	 * @param orgCode
	 * @param goodsAreaType
	 * @return
	 */
	public List<GoodsAreaEntity> queryGoodsAreaByOrgCode(String orgCode,String goodsAreaType);
	
	/**
	 * 查询全国外场展会货货量统计信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-8
	 * @param transferCenterCode
	 * @return
	 */
	public List<ExhibitForecastForWorldDto> queryForecastExhibitForWorldList(String transferCenterCode);
	
	/**
	 * 查询展会货货中 已经签收的记录
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-18
	 * @return
	 */
	public List<String> querySignedAndActiveExhibit();
	
}
