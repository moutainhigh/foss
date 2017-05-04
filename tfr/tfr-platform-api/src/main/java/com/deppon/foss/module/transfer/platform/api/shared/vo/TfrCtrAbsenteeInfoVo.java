package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrAbsenteeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrAbsenteeInfoQcDto;


public class TfrCtrAbsenteeInfoVo implements Serializable{

	private static final long serialVersionUID = -7984007950832268696L;

	/**
	 * 外场异常人员信息；
	 * 1、用于根据id查询外场异常人员信息方法的返回值
	 * 2、用于修改外场异常人员信息方法的参数
	 */
	private TfrCtrAbsenteeInfoEntity tfrCtrAbsenteeInfoEntity;
	
	/**
	 * 外场异常人员信息集合
	 * 1、用于查询外场异常人员信息返回值
	 */
	private List<TfrCtrAbsenteeInfoEntity> tfrCtrAbsenteeInfoEntities;
	
	/**
	 * 外场异常人员信息id
	 * 1、用于根据id查询外场异常人员信息方法的参数
	 * 2、用于删除外场异常人员信息方法的参数
	 */
	private String id;
	
	/**
	 * 外场异常人员信息id集合
	 * 1、用于删除外场人员异常信息方法的参数
	 * 2、用于选中行导出
	 */
	private List<String> ids;
	
	/**
	 * 外场异常人员信息查询条件
	 * 1、用于查询外场异常人员信息方法的参数
	 */
	private TfrCtrAbsenteeInfoQcDto tfrCtrAbsenteeInfoQcDto;
	
	/**
	 * 当前部门所属外场编码
	 * 1、用于判断查询界面的部门是否可编辑
	 */
	private String parentTfrCtrCode;
	
	/**
	 * 当前部门所属外场名称
	 */
	private String parentTfrCtrName;


	/**
	 * 外场自离人数
	 * */
	private int restgnationNum;
	
	/**
	 * 外场旷工人数
	 * */
	private int absenteeismNum;
	
	/**
	 * 外场工伤人数
	 * */
	private int industrialInjuryNum;
	
	/**
	 * 外场长假人数
	 * */
    private int longHolidaysNum;
	
	public TfrCtrAbsenteeInfoEntity getTfrCtrAbsenteeInfoEntity() {
		return tfrCtrAbsenteeInfoEntity;
	}

	public void setTfrCtrAbsenteeInfoEntity(
			TfrCtrAbsenteeInfoEntity tfrCtrAbsenteeInfoEntity) {
		this.tfrCtrAbsenteeInfoEntity = tfrCtrAbsenteeInfoEntity;
	}

	public List<TfrCtrAbsenteeInfoEntity> getTfrCtrAbsenteeInfoEntities() {
		return tfrCtrAbsenteeInfoEntities;
	}

	public void setTfrCtrAbsenteeInfoEntities(
			List<TfrCtrAbsenteeInfoEntity> tfrCtrAbsenteeInfoEntities) {
		this.tfrCtrAbsenteeInfoEntities = tfrCtrAbsenteeInfoEntities;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public TfrCtrAbsenteeInfoQcDto getTfrCtrAbsenteeInfoQcDto() {
		return tfrCtrAbsenteeInfoQcDto;
	}

	public void setTfrCtrAbsenteeInfoQcDto(
			TfrCtrAbsenteeInfoQcDto tfrCtrAbsenteeInfoQcDto) {
		this.tfrCtrAbsenteeInfoQcDto = tfrCtrAbsenteeInfoQcDto;
	}

	public String getParentTfrCtrCode() {
		return parentTfrCtrCode;
	}

	public void setParentTfrCtrCode(String parentTfrCtrCode) {
		this.parentTfrCtrCode = parentTfrCtrCode;
	}

	public String getParentTfrCtrName() {
		return parentTfrCtrName;
	}

	public void setParentTfrCtrName(String parentTfrCtrName) {
		this.parentTfrCtrName = parentTfrCtrName;
	}

	public int getRestgnationNum() {
		return restgnationNum;
	}

	public void setRestgnationNum(int restgnationNum) {
		this.restgnationNum = restgnationNum;
	}

	public int getAbsenteeismNum() {
		return absenteeismNum;
	}

	public void setAbsenteeismNum(int absenteeismNum) {
		this.absenteeismNum = absenteeismNum;
	}

	public int getIndustrialInjuryNum() {
		return industrialInjuryNum;
	}

	public void setIndustrialInjuryNum(int industrialInjuryNum) {
		this.industrialInjuryNum = industrialInjuryNum;
	}

	public int getLongHolidaysNum() {
		return longHolidaysNum;
	}

	public void setLongHolidaysNum(int longHolidaysNum) {
		this.longHolidaysNum = longHolidaysNum;
	}
	

	
}
