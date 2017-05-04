/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.server.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrAbsenteeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrAbsenteeInfoQcDto;

/**
 * 人员异常信息service
 * @author Ouyang
 */
public interface ITfrCtrAbsenteeInfoService extends IService {

	/**
	 * 新增外场人员异常信息
	 * @param entity
	 * @date 2014-2-24
	 * @author Ouyang
	 */
	void insertTfrCtrAbsenteeInfo(TfrCtrAbsenteeInfoEntity entity);

	/**
	 * 批量新增外场人员异常信息
	 * @param entities
	 * @date 2014-2-24
	 * @author Ouyang
	 */
	void insertTfrCtrAbsenteeInfos(List<TfrCtrAbsenteeInfoEntity> entities);

	/**
	 * 删除外场人员异常信息
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
	void updateTfrCtrAbsenteeInfo(TfrCtrAbsenteeInfoEntity entity);

	/**
	 * 查询外场人员异常信息
	 * @param qcDto
	 * @return 满足记录条数
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
	 * 返回传入部门code所属外场
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	Map<String, String> queryParentTfrCtrCode(String code);

	/**
	 * 导出excel
	 * @param ids 选择中行记录的id集合
	 * @param qcDto 非选中条件下，导出的查询条件
	 * @return
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	ExportResource exportTfrCtrAbsenteeInfos(List<String> ids,
			TfrCtrAbsenteeInfoQcDto qcDto);

	/**
	 * 批量导入
	 * @param uploadFileName
	 * @param uploadFile
	 * @date 2014-2-26
	 * @author Ouyang
	 */
	void importTfrCtrAbsenteeInfos(String uploadFileName, File uploadFile);
	
	/**
	 * 分页查询外场人员异常信息 不分页
	 * @param qcDto
	 * @return 外场人员异常信息集合
	 * @date 2015-01-22
	 * @author wqh
	 */
	List<TfrCtrAbsenteeInfoEntity> queryPagingTfrCtrAbsenteeInfos(TfrCtrAbsenteeInfoQcDto qcDto);
	
}
