package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISortdestStationService;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SortdestStationException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SortdestStationVo;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;

public class SortdestStationAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ISortdestStationService sortdestStationService;
	
	private SortdestStationVo objectVo=new SortdestStationVo();
	
	
	
	
	public void setSortdestStationService(
			ISortdestStationService sortdestStationService) {
		this.sortdestStationService = sortdestStationService;
	}
	
	/*private IDataDictionaryValueService dataDictionaryValueService;
	
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}*/
	
	
	/**
	 * 
	* @Title: querySortdestStation 
	* @Description:  
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@JSON
	public String querySortdestStation(){
//		List<DataDictionaryValueEntity> ts=dataDictionaryValueService.queryDataDictionaryValueByTermsCode("BSE_SOURCE_CATEGORY");
//		System.out.println(ts);
//		
		objectVo.setSortdestStationEntitys(sortdestStationService.queryAll(objectVo.getSortdestStationEntity(),this.limit,this.start));
		this.setTotalCount(sortdestStationService.countAll(objectVo.getSortdestStationEntity()));
		
		return returnSuccess();
	}

	
	/**
	 * 
	* @Title: update 
	* @Description:  
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@JSON
	public String updateSortdestStation(){
		
		try {
			sortdestStationService.sortDestStationUpdate(objectVo.getSortdestStationEntity());
			
		} catch (SortdestStationException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	 * 
	* @Title: insert 
	* @Description:  
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@JSON
	public String insertSortdestStation(){
		
		try {
			sortdestStationService.sortDestStationInsert(objectVo.getSortdestStationEntity());
		} catch (SortdestStationException e) {
		 return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	 * 
	* @Title: insert 
	* @Description:  
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@JSON
	public String delSortdestStation(){
		try {
			
			sortdestStationService.sortDestStationDelByCode(objectVo.getCodeStr(),objectVo.getActive());
		} catch (SortdestStationException e) {
			return returnError(e.getMessage());
		}
		
		return returnSuccess();
	}
 	
//----------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * 
	* @Title: queryChildSortdestStations 
	* @Description:  
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@JSON
	public String queryChildSortdestStations(){
		try {
			objectVo.setSortdestStationDtos(sortdestStationService.querySortChildAll(objectVo.getSortdestStationDto(),this.limit,this.start));
			this.setTotalCount(sortdestStationService.countAllSortChild(objectVo.getSortdestStationDto()));
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	
	/**
	 * 
	* @Title: delChildSortdestStations 
	* @Description: 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@JSON
	public String delChildSortdestStations(){
		try {
			sortdestStationService.sortDestStationInfoDel(objectVo.getCodeStr());
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	
	/**
	 * 
	* @Title: updateChildSortdestStations 
	* @Description: 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@JSON
	public String updateChildSortdestStations(){
		try {
			sortdestStationService.sortDestStationInfoUpdate(objectVo.getSortdestStationDto());
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	 * 
	* @Title: addChildSortdestStations 
	* @Description:  
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@JSON
	public String addChildSortdestStations(){
		try {
			sortdestStationService.sortDestStationInfoInsert(objectVo.getSortdestStationDto());
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
//------------------------------------------VO----------------------------------
	 
	
	public SortdestStationVo getObjectVo() {
		return objectVo;
	}
	
	public void setObjectVo(SortdestStationVo objectVo) {
		this.objectVo = objectVo;
	}
	
	
	
	
	

}
