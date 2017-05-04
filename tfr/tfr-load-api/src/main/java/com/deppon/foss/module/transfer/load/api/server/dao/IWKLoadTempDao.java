package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.WKLoadTempDto;


/**
* @description 同步给悟空创建装车,完成装车的临时表(T_OPT_WK_LOAD_TEMP)操作
* @version 1.0
* @author 328864-foss-xieyang
* @update 2016年5月11日 下午2:22:48
*/
public interface IWKLoadTempDao {
	
	
	
	/**
	* @description 插入数据
	* @param dto
	* @return 0失败|1成功
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午2:21:59
	*/
	public int inertData(WKLoadTempDto dto);
	
	
	/**
	* @description 删除数据
	* @param taskNo
	* @return 0失败|1成功
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午2:22:08
	*/
	public int deleteData(WKLoadTempDto dto);
	
	
	/**
	* @description 分页查询
	* @param taskType
	* @param offset
	* @param limit
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午2:22:20
	*/
	public List<WKLoadTempDto> getDatasByPage(int taskType, int offset, int limit); 
	
	
	/**
	* @description 获取记录总体条数
	* @param taskType
	* @return 记录条数
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午2:22:28
	*/
	public int getCount(int taskType);
	
	

	
	/**
	* @description  查看是否是外请车
	* @param newVehicleNo
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月30日 下午6:40:44
	*/
	int isLeasedTruck(String newVehicleNo);
	
	
	
	
	/**
	* @description 安装taskno更新交接单状态
	* @param taskNo
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年6月8日 上午9:06:09
	*/
	public int updateWKLoadTempDto(String taskNo);
	
	
	
	/**
	* @description 更新所有交接单的状态
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年6月8日 上午9:06:56
	*/
	public void updateWKLoadTempDtoState();

}
