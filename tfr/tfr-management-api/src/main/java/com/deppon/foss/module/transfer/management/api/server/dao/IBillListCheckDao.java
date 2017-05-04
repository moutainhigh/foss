package com.deppon.foss.module.transfer.management.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.management.api.shared.dto.BillListCheckDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.BillListCheckStaDto;


/**
* @description 电子对账单Dao
* @version 1.0
* @author 14022-foss-songjie
* @update 2013年11月30日 下午4:49:12
*/
public interface IBillListCheckDao {
	
	/**
	* @description 插入电子对账单log(批量)
	* @param billListCheckDtoList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月3日 下午2:14:58
	*/
	public void batchInsertBillListCheckLog(List<BillListCheckDto> billListCheckDtoList);
	
	
	/**
	* @description 插入电子对账单log(单条)
	* @param billListCheckDto
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月3日 下午2:15:08
	*/
	public void insertBillListCheckLogOne(BillListCheckDto billListCheckDto);
	
	
	/**
	* @description 查询电子对账单group by 日期 所属事业部、所属车队
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月3日 下午2:26:48
	*/
	@SuppressWarnings("rawtypes")
	public List<BillListCheckDto> queryBillListCheckLogGroup(Map map);
	
	
	/**
	* @description 插入电子对账单Main
	* @param billListCheckDto
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月3日 下午2:15:34
	*/
	public void insertBillListCheckOne(BillListCheckDto billListCheckDto);
	
	
	/**
	* @description 查询电子对账单Main
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月3日 下午2:15:43
	*/
	@SuppressWarnings("rawtypes")
	public BillListCheckDto queryBillListCheck(Map map);
	
	/**
	* @description 查询电子对账单Main分页
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月3日 下午2:15:43
	*/
	@SuppressWarnings("rawtypes")
	public List<BillListCheckDto> queryBillListCheck(Map map,int start,int limit);
	
	
	/**
	* @description 分页查询页面的导出功能所需数据
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月6日 下午2:29:24
	*/
	@SuppressWarnings("rawtypes")
	public List<BillListCheckDto> exportBillListCheck(Map map);
	
	
	/**
	* @description 查询电子对账单Main(总条数)
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月3日 下午2:15:53
	*/
	@SuppressWarnings("rawtypes")
	public long queryBillListCheckCount(Map map);
	
	/**
	* @description 查询结果去掉分页统计static
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月3日 下午2:16:03
	*/
	@SuppressWarnings("rawtypes")
	public BillListCheckStaDto queryBillListCheckStatic(Map map);
	
	/**
	* @description 更新电子对账单Main
	* @param billListCheckDto
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年12月3日 下午2:16:17
	*/
	public void updateBillListCheck(BillListCheckDto billListCheckDto);
}
