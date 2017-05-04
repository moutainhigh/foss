/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressTrainProgramEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressTrainProgramDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgGisLongitudeDto;

/**
 *<p>Title: ExpressTrainScheduleVo</p>
 * <p>Description: 快递支线班车时刻表前后台交互实体</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-8
 */
public class ExpressTrainScheduleVo {
	/**
	 * 班车方案Dto
	 */
	private ExpressTrainProgramDto expressTrainProgramDto;
	/**
	 * 快递班车线路时刻表实体
	 */
	private ExpressLineScheduleEntity expressLineScheduleEntity;
	/**
	 * 班车方案集合
	 */
	private List<ExpressTrainProgramEntity> programEntityList;
	/**
	 * 线路时刻表集合
	 */
	private List<ExpressLineScheduleEntity> lineScheduleEntityList;
	/**
	 * 返回到达线路对应的外场
	 */
	private List<SaleDepartmentEntity> salesDeptList;
	/**
	 * 传递给前台的组织
	 */
	private List<OrgAdministrativeInfoEntity> orgList;
	/**
	 * gis部门坐标识别集合
	 */
	private List<OrgGisLongitudeDto> orgGisList;
	/**
	 * 线路名称集合
	 */
	private List<String> lineNameList;
	/**
	 * @return the expressTrainProgramDto
	 */
	public ExpressTrainProgramDto getExpressTrainProgramDto() {
		return expressTrainProgramDto;
	}
	/**
	 * @param expressTrainProgramDto the expressTrainProgramDto to set
	 */
	public void setExpressTrainProgramDto(
			ExpressTrainProgramDto expressTrainProgramDto) {
		this.expressTrainProgramDto = expressTrainProgramDto;
	}
	/**
	 * @return the programEntityList
	 */
	public List<ExpressTrainProgramEntity> getProgramEntityList() {
		return programEntityList;
	}
	/**
	 * @param programEntityList the programEntityList to set
	 */
	public void setProgramEntityList(
			List<ExpressTrainProgramEntity> programEntityList) {
		this.programEntityList = programEntityList;
	}
	/**
	 * @return the lineScheduleEntityList
	 */
	public List<ExpressLineScheduleEntity> getLineScheduleEntityList() {
		return lineScheduleEntityList;
	}
	/**
	 * @param lineScheduleEntityList the lineScheduleEntityList to set
	 */
	public void setLineScheduleEntityList(
			List<ExpressLineScheduleEntity> lineScheduleEntityList) {
		this.lineScheduleEntityList = lineScheduleEntityList;
	}
	public List<SaleDepartmentEntity> getSalesDeptList() {
		return salesDeptList;
	}
	public void setSalesDeptList(List<SaleDepartmentEntity> salesDeptList) {
		this.salesDeptList = salesDeptList;
	}
	public ExpressLineScheduleEntity getExpressLineScheduleEntity() {
		return expressLineScheduleEntity;
	}
	public void setExpressLineScheduleEntity(
			ExpressLineScheduleEntity expressLineScheduleEntity) {
		this.expressLineScheduleEntity = expressLineScheduleEntity;
	}
	public List<String> getLineNameList() {
		return lineNameList;
	}
	public void setLineNameList(List<String> lineNameList) {
		this.lineNameList = lineNameList;
	}
	public List<OrgGisLongitudeDto> getOrgGisList() {
		return orgGisList;
	}
	public void setOrgGisList(List<OrgGisLongitudeDto> orgGisList) {
		this.orgGisList = orgGisList;
	}
	public List<OrgAdministrativeInfoEntity> getOrgList() {
		return orgList;
	}
	public void setOrgList(List<OrgAdministrativeInfoEntity> orgList) {
		this.orgList = orgList;
	}
	
}
