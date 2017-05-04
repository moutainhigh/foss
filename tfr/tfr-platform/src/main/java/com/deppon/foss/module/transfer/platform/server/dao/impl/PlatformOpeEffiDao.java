package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IPlatformOpeEffiDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PlatformOpeEffiEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.PlatformOpeEffiCondiDto;

/**
 * 
* @ClassName: PlatformOpeEffiDao 
* @Description: 月台操作效率dao层实现类
* @author 105944
* @date 2015-3-21 下午2:17:33
 */
public class PlatformOpeEffiDao  extends iBatis3DaoImpl implements IPlatformOpeEffiDao{
	//mybatis映射的命名空间
	private static final String NAMESPACE = "Foss.platform.platformOpeEffi.";
	//查询月台操作效率
	private static final String QUERYPLATFORMOPEEFFI = "queryPlatformOpeEffi";
	//查询月台操作效率 总条数
	private static final String QUERYPLATFORMOPEEFFICOUNT = "queryPlatformOpeEffiCount";
	//查询月台操作效率明细
	private static final String QUERYPLATFORMOPEEFFIDETAIL = "queryPlatformOpeEffiDetail";
	//查询月台操作效率明细 总条数-
	private static final String QUERYPLATFORMOPEEFFIDETAILCOUNT = "queryPlatformOpeEffiDetailCount";
	//查询需要统计月台操作效率的外场列表
	private static final String QUERYOUTFIELDINFOLIST = "queryOutfieldInfoList";
	//插入月台操作效率明细日数据
	private static final String INSERTPLATFORMOPEEFFIDAYDETAILDATA = "insertPlatformOpeEffiDayDetailData";
	//更新月台操作效率明细月数据
	private static final String UPDATEPLATFORMOPEEFFIMONTHDETAILDATA = "updatePlatformOpeEffiMonthDetailData";
	//插入月台操作效率数据
	private static final String INSERTPLATFORMOPEEFFIDATA = "insertPlatformOpeEffiData";
	/**
	 * 
	* @Title: queryPlatformOpeEffi 
	* @Description: 查询月台操作效率
	* @author 105944
	* @date 2015-3-21 上午10:42:37  
	* @param platformOpeEffi
	* @param start
	* @param totalCount
	* @param @return    
	* @return List<PlatformOpeEffiEntity>    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PlatformOpeEffiEntity> queryPlatformOpeEffi(
			PlatformOpeEffiCondiDto queryCondition, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+QUERYPLATFORMOPEEFFI, queryCondition, rb);
	}

	/**
	 * 
	* @Title: queryPlatformOpeEffiCount 
	* @Description: 月台操作效率查询总条数
	* @author 105944
	* @date 2015-3-21 上午10:45:26  
	* @param @param platformOpeEffi
	* @param @return    
	* @return Long    
	* @throws
	 */
	@Override
	public int queryPlatformOpeEffiCount(PlatformOpeEffiCondiDto queryCondition) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+QUERYPLATFORMOPEEFFICOUNT,queryCondition);
	}

	/**
	 * 
	* @Title: queryPlatformOpeEffi 
	* @Description: 查询月台操作效率明细
	* @author 105944
	* @date 2015-3-21 上午10:42:37  
	* @param platformOpeEffi
	* @param start
	* @param totalCount
	* @param @return    
	* @return List<PlatformOpeEffiEntity>    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PlatformOpeEffiEntity> queryPlatformOpeEffiDetail(
			PlatformOpeEffiCondiDto queryCondition, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+QUERYPLATFORMOPEEFFIDETAIL, queryCondition, rb);
	}

	/**
	 * 
	* @Title: queryPlatformOpeEffiCount 
	* @Description: 月台操作效率明细查询总条数
	* @author 105944
	* @date 2015-3-21 上午10:45:26  
	* @param @param platformOpeEffi
	* @param @return    
	* @return Long    
	* @throws
	 */
	@Override
	public int queryPlatformOpeEffiDetailCount(
			PlatformOpeEffiCondiDto queryCondition) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+QUERYPLATFORMOPEEFFIDETAILCOUNT,queryCondition);
	}
	
	/**
	 * 
	* @Title: queryOutfieldInfoList 
	* @Description: 查询需要统计月台操作效率的外场信息列表
	* @author 105944
	* @date 2015-3-24 上午9:35:13  
	* @param @return    
	* @return List<PlatformOpeEffiEntity>    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PlatformOpeEffiEntity> queryOutfieldInfoList() {
		return this.getSqlSession().selectList(NAMESPACE+QUERYOUTFIELDINFOLIST);
	}

	/**
	 * 
	* @Title: insertPlatformOpeEffiDayDetailData 
	* @Description:  插入月台操作效率明细日数据
	* @author 105944
	* @date 2015-3-24 上午9:40:05  
	* @param @param platformOpeEffiEntity
	* @param @return    
	* @return boolean    
	* @throws
	 */
	@Override
	public boolean insertPlatformOpeEffiDayDetailData(
			PlatformOpeEffiEntity platformOpeEffiEntity) {
		return this.getSqlSession().insert(NAMESPACE+INSERTPLATFORMOPEEFFIDAYDETAILDATA, platformOpeEffiEntity) > 0;
	}

	/**
	 * 
	* @Title: updatePlatformOpeEffiMonthDetailData 
	* @Description: 更新月台操作效率明细月数据
	* @author 105944
	* @date 2015-3-24 上午9:42:49  
	* @param @param platformOpeEffiEntity
	* @param @return    
	* @return boolean    
	* @throws
	 */
	@Override
	public boolean updatePlatformOpeEffiMonthDetailData(
			PlatformOpeEffiEntity platformOpeEffiEntity) {
		return this.getSqlSession().update(NAMESPACE+UPDATEPLATFORMOPEEFFIMONTHDETAILDATA, platformOpeEffiEntity) > 0;
	}

	/**
	 * 
	* @Title: insertPlatformOpeEffiData 
	* @Description: 插入月台操作效率数据
	* @author 105944
	* @date 2015-3-24 上午9:43:32  
	* @param @param platformOpeEffiEntity
	* @param @return    
	* @return boolean    
	* @throws
	 */
	@Override
	public boolean insertPlatformOpeEffiData(
			PlatformOpeEffiEntity platformOpeEffiEntity) {
		return this.getSqlSession().insert(NAMESPACE+INSERTPLATFORMOPEEFFIDATA, platformOpeEffiEntity) > 0;
	}

	/**
	 * 
	* @Title: queryPlatformOpeEffi 
	* @Description: 查询月台操作效率信息-不分页
	* @author 105944
	* @date 2015-3-21 上午10:42:37  
	* @param platformOpeEffi
	* @param start
	* @param totalCount
	* @param @return    
	* @return List<PlatformOpeEffiEntity>    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PlatformOpeEffiEntity> queryPlatformOpeEffi4Whole(
			PlatformOpeEffiCondiDto queryCondition) {
		return this.getSqlSession().selectList(NAMESPACE+QUERYPLATFORMOPEEFFI, queryCondition);
	}

}
