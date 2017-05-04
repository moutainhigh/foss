/**
 * @author foss 257200
 * 2015-9-1
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.InputWeightVolumnEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnDto;

/**
 * @author 257220
 *
 */
public interface IInputWeightVolumnService {

	/**
	 * <p>查询录入重量体积dto</p>
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @author 257220
	 * @date 2015-9-1上午9:02:31
	 */
	public List<InputWeightVolumnDto> queryInputWeightVolumnBaseInfo(
			InputWeightVolumnDto dto, int start, int limit);

	/**
	 * <p>根据母件查询对应的子母件重量体积信息</p>
	 * @param parentWaybillNo
	 * @author 257220
	 * @return 
	 * @date 2015-9-7下午12:22:18
	 */
	public List<InputWeightVolumnDto> queryInputWeightVolumnInfo(String parentWaybillNo);
	
	/**
	 *<p>根据母件查询对应的子母件重量体积数量</p>
	 *@param dto
	 *@return
	 *@date  2015-11-14下午4:32:15
	 *@author 257220
	 */
	Long queryInputWeightVolumnBaseInfoCount(InputWeightVolumnDto dto);

	/**
	 * <ul>批量保存修改的重量体积方法<ul>
	 * <li>如果有录入则修改</li>
	 * <li>如果未曾录入则新增</li>
	 * @param list
	 * @author 257220
	 * @date 2015-9-8下午5:52:31
	 */
	public void save(List<InputWeightVolumnDto> list);
	/**
	* <ul>保存修改的重量体积方法<ul>
	 * <li>如果有录入则修改</li>
	 * <li>如果未曾录入则新增</li>
	 * @param inputWeightVolumnDto
	 * @author 257220
	 * @date 2015-9-9下午3:18:28
	 */
	public void save(InputWeightVolumnDto inputWeightVolumnDto);
	/**
	 * 批量保存来自excel导入的数据
	 * @param excelDtos
	 * @author 257220
	 * @return 母件单号集合
	 * @date 2015-9-9下午3:52:26
	 */
	public List<String> batchSaveDataFromExcel(List<InputWeightVolumnDto> excelDtos);
	/**
	 * 新增一条重量体积信息
	 * @param inputWeightVolumnDto
	 * @author 257220
	 * @date 2015-9-8下午6:05:58
	 */
	public void addWeightVolumnInfo(InputWeightVolumnDto inputWeightVolumnDto);
	/**
	 * 更新一条重量体积信息
	 * @param inputWeightVolumnDto
	 * @author 257220
	 * @date 2015-9-8下午6:05:40
	 */
	public void updateWeightVolumnInfo(InputWeightVolumnDto inputWeightVolumnDto);
	
	/**
	 * 根据运单号查询子母件运单重量体积信息,需校验是否子母件
	 * @param waybillNo
	 * @return
	 */
	public InputWeightVolumnEntity queryInputWeightVolumnByWaybillNo(String waybillNo,String serialNo);
	/**
	 * 根据运单号查询运单重量体积信息
	 * @param waybillNo
	 * @return
	 */
	public InputWeightVolumnEntity getInputWeightVolumnByWaybillNo(String waybillNo,String serialNo);
	/**
	 * <ul>判断能否修改,条件：</ul>
	 * <li>未做交接单</li>
	 * <li>已生成外发单</li>
	 * @param inputWeightVolumnDto
	 * @return
	 */
	public InputWeightVolumnDto checkCanModify(InputWeightVolumnDto inputWeightVolumnDto);

}
