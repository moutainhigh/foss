package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadingProgressConditionDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadingProgressResultDto;

/**
* @ClassName: QueryLoadingProgressVo 
* @Description: 月台项目-查询装车进度-Vo
* @author shiwei shiwei@outlook.com
* @date 2014年4月12日 上午11:08:26 
*
 */
public class LoadingProgressVo implements Serializable {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 6298698867995160085L;
	/**
	 * 查询装车进度条件
	 */
	private LoadingProgressConditionDto loadingProgressConditionDto;
	/**
	 * 查询装车进度返回结果
	 */
	private List<LoadingProgressResultDto> loadingProgressResultList;
	/**
	 * 每页数量
	 */
	private int pageSize;
	/**
	 * 起始页
	 */
	private int initStart;
	
	/**
	 * 总条数(总记录数)
	 */
	private Integer count;
	
	
	/** 
	 * @return loadingProgressConditionDto 
	 */
	public LoadingProgressConditionDto getLoadingProgressConditionDto() {
		return loadingProgressConditionDto;
	}

	/** 
	 * @param loadingProgressConditionDto 要设置的 loadingProgressConditionDto 
	 */
	public void setLoadingProgressConditionDto(
			LoadingProgressConditionDto loadingProgressConditionDto) {
		this.loadingProgressConditionDto = loadingProgressConditionDto;
	}

	/** 
	 * @return loadingProgressResultList 
	 */
	public List<LoadingProgressResultDto> getLoadingProgressResultList() {
		return loadingProgressResultList;
	}

	/** 
	 * @param loadingProgressResultList 要设置的 loadingProgressResultList 
	 */
	public void setLoadingProgressResultList(
			List<LoadingProgressResultDto> loadingProgressResultList) {
		this.loadingProgressResultList = loadingProgressResultList;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}