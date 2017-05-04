package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PrintMarketingContentEntity;

/**
 * 打印营销内容基础资料Vo
 * @author dujunhui-187862
 * @date 2014-08-26 上午8:47:22
 */
public class PrintMarketingContentVo {
	
	//打印营销内容基础资料实体集合
	private List<PrintMarketingContentEntity> printMarketingContentEntityList;
	
	//打印营销内容基础资料实体
	private PrintMarketingContentEntity printMarketingContentEntity = new PrintMarketingContentEntity();
	
	//实体中对应ID字段，用于批量删除
    private List<String> codeList;

	/**
	 * @return  the printMarketingContentEntityList
	 */
	public List<PrintMarketingContentEntity> getPrintMarketingContentEntityList() {
		return printMarketingContentEntityList;
	}

	/**
	 * @param printMarketingContentEntityList the printMarketingContentEntityList to set
	 */
	public void setPrintMarketingContentEntityList(
			List<PrintMarketingContentEntity> printMarketingContentEntityList) {
		this.printMarketingContentEntityList = printMarketingContentEntityList;
	}

	/**
	 * @return  the printMarketingContentEntity
	 */
	public PrintMarketingContentEntity getPrintMarketingContentEntity() {
		return printMarketingContentEntity;
	}

	/**
	 * @param printMarketingContentEntity the printMarketingContentEntity to set
	 */
	public void setPrintMarketingContentEntity(
			PrintMarketingContentEntity printMarketingContentEntity) {
		this.printMarketingContentEntity = printMarketingContentEntity;
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
