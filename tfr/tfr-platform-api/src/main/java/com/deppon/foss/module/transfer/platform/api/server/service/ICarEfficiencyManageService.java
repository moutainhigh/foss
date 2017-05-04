package com.deppon.foss.module.transfer.platform.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.CarEfficiencyEntity;


/**
* @description 车辆装卸车效率管理Service
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年5月5日 上午11:09:55
*/
public interface ICarEfficiencyManageService  extends IService {
	
	/**
	* @description 查询外场信息，查询不到则视为统计部门
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午2:23:44
	*/
	String[] queryOutfieldInfo();
	
	/**
	* @description 长途装卸车效率查询
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:11:20
	*/
	List<CarEfficiencyEntity> queryCarEfficiencyWayLong(CarEfficiencyEntity carEfficiency,int start, int limit);
	
	
	/**
	* @description 长途装卸车效率 导出
	* @param carEfficiency
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午2:59:33
	*/
	InputStream carEfficiencyLongWayExport(CarEfficiencyEntity carEfficiency);
	
	
	/**
	* @description 短途装卸车效率 导出
	* @param carEfficiency
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午2:59:35
	*/
	InputStream carEfficiencyShortWayExport(CarEfficiencyEntity carEfficiency);
	
	
	/**
	* @description 长途装卸车效率查询 总记录数
	* @param carEfficiency
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:48:29
	*/
	long queryCarEfficiencyWayLongCount(CarEfficiencyEntity carEfficiency);
	
	
	/**
	* @description 短装卸车效率查询
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:11:14
	*/
	List<CarEfficiencyEntity> queryCarEfficiencyWayShort(CarEfficiencyEntity carEfficiency,int start, int limit);
	
	/**
	* @description 短装卸车效率查询 总记录数
	* @param carEfficiency
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:48:43
	*/
	long queryCarEfficiencyWayShortCount(CarEfficiencyEntity carEfficiency);
	
	
	/**
	* @description 相关的理货员
	* @param list
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午2:44:24
	*/
	List<CarEfficiencyEntity>  queryLoaderListByTaskId(String taskIds);
}
