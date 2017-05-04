package com.deppon.foss.module.transfer.partialline.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.InputWeightVolumnEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnMoreDto;

/**
 * 
 * @author 257220
 *
 */
public interface IInputWeightVolumnMoreDao {
	/**
	 *查询一票多件录入重量体积,不包括字母件
	 *@param dto
	 *@param start
	 *@param limit
	 *@return
	 *@date  2015-11-14下午2:56:08
	 *@author 257220
	 */
	List<InputWeightVolumnMoreDto> queryInputWeightVolumnMoreList(InputWeightVolumnMoreDto dto,int start, int limit);
	/**
	 *查询一票多件录入重量体积数量,不包括字母件
	 *@param dto
	 *@param start
	 *@param limit
	 *@return
	 *@date  2015-11-14下午2:56:08
	 *@author 257220
	 */
	Long queryInputWeightVolumnMoreListCount(InputWeightVolumnMoreDto dto);
	/**
	 * 
	 *@param inputWeightVolumnDto
	 *@return
	 *@date  2015-11-14下午2:59:03
	 *@author 257220
	 */
	int addWeightVolumnMoreInfo(InputWeightVolumnMoreDto inputWeightVolumnMoreDto);
	
	int updateWeightVolumnMoreInfo(InputWeightVolumnMoreDto inputWeightVolumnMoreDto);
	
	InputWeightVolumnEntity queryEntityByWaybillNoSerialNo(String waybillNo,String serialNo);
}
