package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.NoUnloadGoodsDetail;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadAndUnloadStandardDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryUnloadingPlatformConditionDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryUnloadingPlatformResultDto;


/**
 * 查询卸车进度VO
 * @author 046130-foss-xuduowei
 * @date 2012-12-12 上午9:18:01
 */
public class QueryUnloadingPlatformVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 625103101140704049L;
	/**
	 * 卸车进度查询条件
	 */
	private QueryUnloadingPlatformConditionDto queryUnloadingProgressConditionDto;
	/**
	 * 卸车进度查询结果
	 */
	private List<QueryUnloadingPlatformResultDto> queryUnloadingProgressResultDtoList;
	/**
	 * 装卸车标准
	 */
	private LoadAndUnloadStandardDto loadAndUnloadStandardDto;
	
	
	/**
	 * 卸车任务id
	* @fields taskId
	* @author 14022-foss-songjie
	* @update 2014年4月16日 下午5:22:59
	* @version V1.0
	*/
	private String taskId;
	
	/**
	 * 未卸车明细 卡货
	* @fields noUnloadGoodsDetailList
	* @author 14022-foss-songjie
	* @update 2014年4月16日 下午5:23:16
	* @version V1.0
	*/
	private List<NoUnloadGoodsDetail> noUnloadGoodsDetailListKH;
	
	/**
	 * 未卸车明细 城际
	* @fields noUnloadGoodsDetailListCJ
	* @author 14022-foss-songjie
	* @update 2014年4月17日 下午2:51:14
	* @version V1.0
	*/
	private List<NoUnloadGoodsDetail> noUnloadGoodsDetailListCJ;
	
	
	/**
	 * 未装车实体
	* @fields noUnloadGoodsDetail
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午11:35:03
	* @version V1.0
	*/
	private NoUnloadGoodsDetail noUnloadGoodsDetail;
	/**
	 * 每页数量
	 */
	private int pageSize;
	/**
	 * 	起始页
	 */
	private int initStart;
	
	/**共几页*/
	private Long totalPageSize;
	/**当前第几页*/
	private Long currentPageSize;

	/**
	 * 获取 卸车进度查询条件.
	 *
	 * @return the 卸车进度查询条件
	 */
	public QueryUnloadingPlatformConditionDto getQueryUnloadingProgressConditionDto() {
		return queryUnloadingProgressConditionDto;
	}

	/**
	 * 设置 卸车进度查询条件.
	 *
	 * @param queryUnloadingProgressConditionDto the new 卸车进度查询条件
	 */
	public void setQueryUnloadingProgressConditionDto(
			QueryUnloadingPlatformConditionDto queryUnloadingProgressConditionDto) {
		this.queryUnloadingProgressConditionDto = queryUnloadingProgressConditionDto;
	}
	
	
	/**
	 * 获取 装卸车标准.
	 *
	 * @return the 装卸车标准
	 */
	public LoadAndUnloadStandardDto getLoadAndUnloadStandardDto() {
		return loadAndUnloadStandardDto;
	}

	/**
	 * 设置 装卸车标准.
	 *
	 * @param loadAndUnloadStandardDto the new 装卸车标准
	 */
	public void setLoadAndUnloadStandardDto(
			LoadAndUnloadStandardDto loadAndUnloadStandardDto) {
		this.loadAndUnloadStandardDto = loadAndUnloadStandardDto;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 获取 卸车进度查询结果.
	 *
	 * @return the 卸车进度查询结果
	 */
	public List<QueryUnloadingPlatformResultDto> getQueryUnloadingProgressResultDtoList() {
		return queryUnloadingProgressResultDtoList;
	}

	/**
	 * 设置 卸车进度查询结果.
	 *
	 * @param queryUnloadingProgressResultDtoList the new 卸车进度查询结果
	 */
	public void setQueryUnloadingProgressResultDtoList(
			List<QueryUnloadingPlatformResultDto> queryUnloadingProgressResultDtoList) {
		this.queryUnloadingProgressResultDtoList = queryUnloadingProgressResultDtoList;
	}

	/**
	 * 获取 每页数量.
	 *
	 * @return the 每页数量
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置 每页数量.
	 *
	 * @param pageSize the new 每页数量
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取 起始页.
	 *
	 * @return the 起始页
	 */
	public int getInitStart() {
		return initStart;
	}

	/**
	 * 设置 起始页.
	 *
	 * @param initStart the new 起始页
	 */
	public void setInitStart(int initStart) {
		this.initStart = initStart;
	}

	/**
	 * @return the totalPageSize
	 */
	public Long getTotalPageSize() {
		return totalPageSize;
	}

	/**
	 * @param totalPageSize the totalPageSize to set
	 */
	public void setTotalPageSize(Long totalPageSize) {
		this.totalPageSize = totalPageSize;
	}

	/**
	 * @return the currentPageSize
	 */
	public Long getCurrentPageSize() {
		return currentPageSize;
	}

	/**
	 * @param currentPageSize the currentPageSize to set
	 */
	public void setCurrentPageSize(Long currentPageSize) {
		this.currentPageSize = currentPageSize;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<NoUnloadGoodsDetail> getNoUnloadGoodsDetailListKH() {
		return noUnloadGoodsDetailListKH;
	}

	public void setNoUnloadGoodsDetailListKH(
			List<NoUnloadGoodsDetail> noUnloadGoodsDetailListKH) {
		this.noUnloadGoodsDetailListKH = noUnloadGoodsDetailListKH;
	}

	public List<NoUnloadGoodsDetail> getNoUnloadGoodsDetailListCJ() {
		return noUnloadGoodsDetailListCJ;
	}

	public void setNoUnloadGoodsDetailListCJ(
			List<NoUnloadGoodsDetail> noUnloadGoodsDetailListCJ) {
		this.noUnloadGoodsDetailListCJ = noUnloadGoodsDetailListCJ;
	}

	
	/**
	* @description  未装车实体
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午11:35:36
	*/
	public NoUnloadGoodsDetail getNoUnloadGoodsDetail() {
		return noUnloadGoodsDetail;
	}

	
	/**
	* @description  未装车实体
	* @param noUnloadGoodsDetail
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午11:35:42
	*/
	public void setNoUnloadGoodsDetail(NoUnloadGoodsDetail noUnloadGoodsDetail) {
		this.noUnloadGoodsDetail = noUnloadGoodsDetail;
	}

	
	
}