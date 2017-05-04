/*
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.action
 * FILE    NAME: SortingScanAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server.action;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.unload.api.server.service.ISortingScanService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanCompareDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.SortingScanVo;

/**
 * SortingScanAction
 * @author dp-duyi
 * @date 2013-7-26 下午2:49:17
 */
public class SortingScanAction  extends AbstractAction{

	private static final long serialVersionUID = 7577342451647194917L;
	private ISortingScanService sortingScanService;
	private SortingScanVo vo;
	private List<SortingScanEntity> sortingScans;
	//如果这个里面有值，但是struts文件里没有配，会报错，所以每个Action都要新起一个实例！！！
	private InputStream sortingScanExcelStream;
	private String excelFileName;
	private IPDACommonService pdaCommonService;

	@JSON
	public String querySortingScanIndex(){
		try{
			if(vo == null){
				vo = new SortingScanVo();
			}
			OrgAdministrativeInfoEntity superOrg = pdaCommonService.getTopCurrentOutfieldOrSalesDept(FossUserContext.getCurrentInfo().getCurrentDeptCode());
			if(superOrg != null){
				vo.setSuperOrgCode(superOrg.getCode());
			}
			return this.returnSuccess();
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	@JSON
	public String querySortingScan(){
		try{
			LOG.error("上分拣扫描查询开始");
			Long totalCount = sortingScanService.sortingScanCount(vo);
			LOG.error("显示大小" + this.getLimit());
			LOG.error("起始页" + this.getStart());
			sortingScans = sortingScanService.selectSortingScan(vo, this.getLimit(), this.getStart());
			this.setTotalCount(totalCount);
			LOG.error("上分拣扫描查询结束");
			return this.returnSuccess();
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	@SuppressWarnings("rawtypes")
	public String exportSortingScanToExcel(){
		List list = null;
		try{
			list = sortingScanService.exportSortingScanToExcel(vo, this.getLimit(), this.getStart());
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
		//文件名
		excelFileName = (String)list.get(0);
		//文件流
		sortingScanExcelStream = (InputStream) list.get(1);
		return this.returnSuccess();
	}
	
	/**
	 * 扫描与库存数据比对，查询比对后的差异数据
	 * @Author: 200978  xiaobingcheng
	 * 2014-9-2
	 * @return
	 */
	@JSON
	public String querySortingScanCompare(){
		SortingScanDto sortingScanDto = vo.getSortingScanDto();
		try {
			//进行比对，所以此时的分页是先查出所有数据，进行比对，然后进行分页显示
			List<SortingScanCompareDto> compareList = sortingScanService.querySortingScanCompare(sortingScanDto,this.getStart(),this.getLimit());
			Long totalCount = sortingScanService.querySortingScanCompareCount();//querySortingScanCompare方法中就已经计算出了总条数,所以不需要参数
			this.setTotalCount(totalCount);
			vo.setSortingScanCompareDtoList(compareList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	public SortingScanVo getVo() {
		return vo;
	}
	public void setVo(SortingScanVo vo) {
		this.vo = vo;
	}
	public List<SortingScanEntity> getSortingScans() {
		return sortingScans;
	}
	public void setSortingScans(List<SortingScanEntity> sortingScans) {
		this.sortingScans = sortingScans;
	}
	public InputStream getSortingScanExcelStream() {
		return sortingScanExcelStream;
	}
	public void setSortingScanExcelStream(InputStream sortingScanExcelStream) {
		this.sortingScanExcelStream = sortingScanExcelStream;
	}
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	public void setSortingScanService(ISortingScanService sortingScanService) {
		this.sortingScanService = sortingScanService;
	}
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}

	
}
