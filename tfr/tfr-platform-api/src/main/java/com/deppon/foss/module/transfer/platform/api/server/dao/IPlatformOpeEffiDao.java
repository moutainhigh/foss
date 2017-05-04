package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.PlatformOpeEffiEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.PlatformOpeEffiCondiDto;
/**
 * 
* @ClassName: IPlatformOpeEffiDao 
* @Description: 月台操作效率dao
* @author 105944
* @date 2015-3-21 上午10:49:31
 */
public interface IPlatformOpeEffiDao {
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
	List<PlatformOpeEffiEntity> queryOutfieldInfoList();
	
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
	boolean insertPlatformOpeEffiDayDetailData(PlatformOpeEffiEntity platformOpeEffiEntity);
	
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
	boolean updatePlatformOpeEffiMonthDetailData(PlatformOpeEffiEntity platformOpeEffiEntity);
	
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
	boolean insertPlatformOpeEffiData(PlatformOpeEffiEntity platformOpeEffiEntity);
	
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
	List<PlatformOpeEffiEntity> queryPlatformOpeEffi(PlatformOpeEffiCondiDto queryCondition,int start,int limit);
	
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
	int queryPlatformOpeEffiCount(PlatformOpeEffiCondiDto queryCondition);
	
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
	List<PlatformOpeEffiEntity> queryPlatformOpeEffiDetail(PlatformOpeEffiCondiDto queryCondition,int start,int limit);
	
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
	int queryPlatformOpeEffiDetailCount(PlatformOpeEffiCondiDto queryCondition);
	
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
	List<PlatformOpeEffiEntity> queryPlatformOpeEffi4Whole(PlatformOpeEffiCondiDto queryCondition);
}
