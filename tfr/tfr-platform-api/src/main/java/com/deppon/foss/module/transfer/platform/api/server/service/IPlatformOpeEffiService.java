package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.PlatformOpeEffiCondiDto;

/**
 * 
* @ClassName: IPlatformOpeEffiService 
* @Description: 月台操作效率service
* @author 105944
* @date 2015-3-21 上午10:34:44
 */
public interface IPlatformOpeEffiService extends IService{
	/**
	 * 
	* @Title: statisticPlatformOpeEffi 
	* @Description: 统计月台操作效率
	* @author 105944
	* @date 2015-3-21 上午10:37:28  
	* @param      
	* @return void    
	* @throws
	 */
	void statisticPlatformOpeEffi();
	
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
	Map<String,Object> queryPlatformOpeEffi(PlatformOpeEffiCondiDto queryCondition,int start,int limit);
	
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
	Map<String,Object> queryPlatformOpeEffiDetail(PlatformOpeEffiCondiDto queryCondition,int start,int limit);
	
	/**
	 * 
	* @Title: exportPlatformOpeEffiData 
	* @Description: 按查询条件导出月台操作信息明细
	* @author 105944
	* @date 2015-3-24 下午3:02:48  
	* @param @param queryCondition
	* @param @return    
	* @return ExportResource    
	* @throws
	 */
	ExportResource exportPlatformOpeEffiData(PlatformOpeEffiCondiDto queryCondition);
	
	/**
	 * 
	* @Title: queryOutfieldInfoByDeptCode 
	* @Description: 根据当前部门信息查询该部门所属外场
	* @author 105944
	* @date 2015-3-24 下午4:15:26  
	* @param @param currentDeptCode
	* @param @return    
	* @return OrgAdministrativeInfoEntity    
	* @throws
	 */
	OrgAdministrativeInfoEntity queryOutfieldInfoByDeptCode(String currentDeptCode);
}
