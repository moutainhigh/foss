package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.WKLoadTempDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKLoadEntity;


/**
* @description 同步给悟空创建装车,完成装车的临时表(T_OPT_WK_LOAD_TEMP)操作
* @version 1.0
* @author 328864-foss-xieyang
* @update 2016年5月11日 下午4:31:59
*/
public interface IWKLoadTempService extends IService  {
	
	
	
	/**
	* @description 向表T_OPT_WK_LOAD_TEMP添加数据
	* @param dto
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:28:10
	*/
	public int inertData(WKLoadTempDto dto);
	
	
	/**
	* @description 从表T_OPT_WK_LOAD_TEMP中删除数据
	* @param taskNo
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:28:28
	*/
	public int deleteData(WKLoadTempDto dto);
	
	
	/**
	* @description 从T_OPT_WK_LOAD_TEMP表中分页查询数据
	* @param taskType
	* @param offset
	* @param limit
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:28:41
	*/
	public List<WKLoadTempDto> getDatasByPage(int taskType, int offset, int limit); 
	
	
	/**
	* @description 获取表T_OPT_WK_LOAD_TEMP记录总条数
	* @param taskType 
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:28:58
	*/
	public int getCount(int taskType);
	
	
	
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
	
	
	
	/**
	* @description 插入悟空传来的装车信息
	* @param wkLoadEntity
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年6月10日 下午3:08:00
	*/
	public Map<String, Object> insertWKLoad(WKLoadEntity wkLoadEntity);
	
	
	/**
	* @description 更新悟空传来的装车信息
	* @param wkLoadEntity
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年6月10日 下午3:08:00
	*/
	public Map<String, Object> updateWKLoad(WKLoadEntity wkLoadEntity);

}
