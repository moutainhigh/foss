package com.deppon.foss.module.transfer.partialline.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.InputWeightVolumnEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnMoreDto;

/**
 * 一票多件外发重量体积录入service
 * @author 257220
 *
 */
public interface IInputWeightVolumnMoreService {
	/**
	 * 根据界面查询条件查询一票多件dto
	 *@param dto
	 *@param start
	 *@param limit
	 *@return 
	 *@date  2015-11-14下午2:49:41
	 *@author 257220
	 */
	List<InputWeightVolumnMoreDto>queryInputWeightVolumnMoreList(InputWeightVolumnMoreDto dto,int start, int limit);
	
	
	/**
	 *  根据界面查询条件查询一票多件数量
	 *@param 
	 *@param dto
	 *@return
	 *@date  2015-11-16上午9:41:03
	 *@author 257220
	 */
	Long queryInputWeightVolumnMoreListCount(InputWeightVolumnMoreDto dto);
	/**
	 * <ul>批量保存修改的重量体积方法<ul>
	 * <li>如果有录入则修改</li>
	 * <li>如果未曾录入则新增</li>
	 * @param list
	 * @author 257220
	 * @date 2015-11-16下午5:52:31
	 */
	void save(List<InputWeightVolumnMoreDto> list);
	/**
	 * 根据inputWeightVolumnMoreDto 查询一条记录
	 *@param inputWeightVolumnMoreDto
	 *@return
	 *@date  2015-11-16上午10:25:41
	 *@author 257220
	 */
	InputWeightVolumnEntity queryInputWeightVolumnEntity (InputWeightVolumnMoreDto inputWeightVolumnMoreDto);
	
	/**
	 * 批量保存数据
	 *@param 
	 *@param list
	 *@date  2015-11-16下午2:38:10
	 *@author 257220
	 */
	void batchSaveDataFromExcel(List<InputWeightVolumnMoreDto> list);


}
