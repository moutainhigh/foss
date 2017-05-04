package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrAbsenteeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrAbsenteeInfoQcDto;


/**
 * 人员异常信息dao
 * @author Ouyang
 */
public interface ITfrCtrAbsenteeInfoDao {

	/**
	 * 新增外场人员异常信息
	 * @param entity
	 * @date 2014-2-24
	 * @author Ouyang
	 */
	void insertTfrCtrAbsenteeInfo(TfrCtrAbsenteeInfoEntity entity);
	
	/**
	 *删除外场人员异常信息
	 * @param ids
	 * @date 2014-2-24
	 * @author Ouyang
	 */
	void deleteTfrCtrAbsenteeInfos(List<String> ids);

	/**
	 * 修改外场人员异常信息
	 * @param entity
	 * @date 2014-2-24
	 * @author Ouyang
	 */
	int updateTfrCtrAbsenteeInfo(TfrCtrAbsenteeInfoEntity entity);
	
	/**
	 * 查询外场人员异常信息
	 * @param qcDto
	 * @param limit
	 * @param start
	 * @return 外场人员异常信息集合
	 * @date 2014-2-24
	 * @author Ouyang
	 */
	List<TfrCtrAbsenteeInfoEntity> queryTfrCtrAbsenteeInfos(
			TfrCtrAbsenteeInfoQcDto qcDto);

	/**
	 * 分页查询外场人员异常信息
	 * @param qcDto
	 * @param limit
	 * @param start
	 * @return 外场人员异常信息集合
	 * @date 2014-2-24
	 * @author Ouyang
	 */
	List<TfrCtrAbsenteeInfoEntity> queryPagingTfrCtrAbsenteeInfos(
			TfrCtrAbsenteeInfoQcDto qcDto, int start, int limit);

	/**
	 * 分页查询外场人员异常信息 不分页
	 * @param qcDto
	 * @return 外场人员异常信息集合
	 * @date 2015-01-22
	 * @author wqh
	 */
	List<TfrCtrAbsenteeInfoEntity> queryPagingTfrCtrAbsenteeInfos(
			TfrCtrAbsenteeInfoQcDto qcDto);
	
	
	/**
	 * 查询外场人员异常信息记录条数
	 * @param qcDto
	 * @return 满足记录条数
	 * @date 2014-2-24
	 * @author Ouyang
	 */
	Long queryTfrCtrAbsenteeInfoCount(TfrCtrAbsenteeInfoQcDto qcDto);
	
	/**
	 * 根据id查询外场人员异常信息
	 * @param id
	 * @return
	 * @date 2014-2-24
	 * @author Ouyang
	 */
	TfrCtrAbsenteeInfoEntity queryTfrCtrAbsenteeInfoById(String id);

	/**
	 * 根据查询条件查询出一条外场异常人员信息
	 * @param entity
	 * @return
	 * @date 2014-3-7
	 * @author Ouyang
	 */
	TfrCtrAbsenteeInfoEntity select1TfrCtrAbsenteeInfo(TfrCtrAbsenteeInfoEntity entity);
	
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 根据员工code查询入职信息
	 *@param empCode 员工工号
	 *@return Date
	 *@author 105795
	 *@date 2015年5月19日上午10:13:16 
	 */
	EmployeeEntity queryEmployeeByEmpCode(String empCode);
}
