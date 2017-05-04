/**
 * @author foss 257200
 * 2015-9-1
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.InputWeightVolumnEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnDto;

/**
 * @author 257220
 *
 */
public interface IInputWeightVolumnDao {

	/**
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @author 257220
	 * @date 2015-9-1上午9:10:02
	 */
	List<InputWeightVolumnDto> queryInputWeightVolumnBaseInfo(
			InputWeightVolumnDto dto, int start, int limit);

	/**
	 * @param parentWaybillNo
	 * @return
	 * @author 257220
	 * @date 2015-9-7下午12:26:37
	 */
	List<InputWeightVolumnDto> queryInputWeightVolumnInfo(String parentWaybillNo);

	/**
	 * 查询表里的记录数
	 * @param waybillNo
	 * @return
	 * @author 257220
	 * @date 2015-9-8下午6:00:02
	 */
	int queryInputWeightVolumnCount(String waybillNo);

	/**
	 * @param inputWeightVolumnDto
	 * @author 257220
	 * @date 2015-9-8下午6:06:42
	 */
	void addWeightVolumnInfo(InputWeightVolumnDto inputWeightVolumnDto);

	/**
	 * @param inputWeightVolumnDto
	 * @author 257220
	 * @date 2015-9-8下午6:06:49
	 */
	void updateWeightVolumnInfo(InputWeightVolumnDto inputWeightVolumnDto);
	/**
	 * 根据运单号查询重量体积信息
	 * @param waybillNo
	 * @return
	 */
	InputWeightVolumnEntity queryInputWeightVolumnByWaybillNo(String waybillNo,String serialNo);
	
	Long queryInputWeightVolumnBaseInfoCount(InputWeightVolumnDto dto);
}

