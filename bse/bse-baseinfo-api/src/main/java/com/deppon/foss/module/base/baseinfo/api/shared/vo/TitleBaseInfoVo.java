package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.TitleBaseInfoEntity;

/**
 * 仓库预警短信接收岗位基础资料Vo
 * @author dujunhui-187862
 * @date 2014-08-08 下午3:02:22
 */
public class TitleBaseInfoVo {
	
	//仓库预警短信接收岗位基础资料实体集合
	private List<TitleBaseInfoEntity> titleBaseInfoEntityList;
	
	//仓库预警短信接收岗位基础资料实体
	private TitleBaseInfoEntity titleBaseInfoEntity = new TitleBaseInfoEntity();
	
	//实体中对应ID字段，用于批量删除
    private List<String> codeList;

	/**
	 * @return  the titleBaseInfoEntityList
	 */
	public List<TitleBaseInfoEntity> getTitleBaseInfoEntityList() {
		return titleBaseInfoEntityList;
	}

	/**
	 * @param titleBaseInfoEntityList the titleBaseInfoEntityList to set
	 */
	public void setTitleBaseInfoEntityList(
			List<TitleBaseInfoEntity> titleBaseInfoEntityList) {
		this.titleBaseInfoEntityList = titleBaseInfoEntityList;
	}

	/**
	 * @return  the titleBaseInfoEntity
	 */
	public TitleBaseInfoEntity getTitleBaseInfoEntity() {
		return titleBaseInfoEntity;
	}

	/**
	 * @param titleBaseInfoEntity the titleBaseInfoEntity to set
	 */
	public void setTitleBaseInfoEntity(TitleBaseInfoEntity titleBaseInfoEntity) {
		this.titleBaseInfoEntity = titleBaseInfoEntity;
	}

	/**
	 * @return  the codeList
	 */
	public List<String> getCodeList() {
		return codeList;
	}

	/**
	 * @param codeList the codeList to set
	 */
	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}

}
