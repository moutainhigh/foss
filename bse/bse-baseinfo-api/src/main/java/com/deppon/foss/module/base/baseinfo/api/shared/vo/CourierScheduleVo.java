/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleReportEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GridHeaderEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CourierScheduleExcelDto;

/**
 *<p>Title: CourierScheduleVo</p>
 * <p>Description:快递员排班的前后台交付类 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-4-19
 */
public class CourierScheduleVo {
	/**
	 * 快递员排班实体
	 */
	private CourierScheduleEntity courierScheduleEntity;
	/**
	 * 快递员排班集合
	 */
	private List<CourierScheduleEntity> courierScheduleList;
	/**
	 * 报表列头
	 */
	private List<GridHeaderEntity> gridHeaderFields;
	/**
	 * 导入的exceldto集合
	 */
	private List<CourierScheduleExcelDto> excelDtos;
	/**
	 * 报表查看的集合
	 */
	private List<CourierScheduleReportEntity> reportEntityList;
	/**
	 * (报表)排班日期
	 */
	private String ymd;
	/**
	 * 导入总数
	 */
	private int importToTal;
	/**
	 * 用来作废的id集合
	 */
	private List<String> ids;
	/**
	 * 用户传递用户数据权限集合
	 */
	private List<String> userOrgDatas;
	
	/**
	 * @return the reportEntityList
	 */
	public List<CourierScheduleReportEntity> getReportEntityList() {
		return reportEntityList;
	}
	/**
	 * @param reportEntityList the reportEntityList to set
	 */
	public void setReportEntityList(
			List<CourierScheduleReportEntity> reportEntityList) {
		this.reportEntityList = reportEntityList;
	}
	/**
	 * @return the ids
	 */
	public List<String> getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	/**
	 * @return the importToTal
	 */
	public int getImportToTal() {
		return importToTal;
	}
	/**
	 * @param importToTal the importToTal to set
	 */
	public void setImportToTal(int importToTal) {
		this.importToTal = importToTal;
	}
	/**
	 * @return the courierScheduleEntity
	 */
	public CourierScheduleEntity getCourierScheduleEntity() {
		return courierScheduleEntity;
	}
	/**
	 * @param courierScheduleEntity the courierScheduleEntity to set
	 */
	public void setCourierScheduleEntity(CourierScheduleEntity courierScheduleEntity) {
		this.courierScheduleEntity = courierScheduleEntity;
	}
	/**
	 * @return the courierScheduleList
	 */
	public List<CourierScheduleEntity> getCourierScheduleList() {
		return courierScheduleList;
	}
	/**
	 * @param courierScheduleList the courierScheduleList to set
	 */
	public void setCourierScheduleList(
			List<CourierScheduleEntity> courierScheduleList) {
		this.courierScheduleList = courierScheduleList;
	}
	/**
	 * @return the gridHeaderFields
	 */
	public List<GridHeaderEntity> getGridHeaderFields() {
		return gridHeaderFields;
	}
	/**
	 * @param gridHeaderFields the gridHeaderFields to set
	 */
	public void setGridHeaderFields(List<GridHeaderEntity> gridHeaderFields) {
		this.gridHeaderFields = gridHeaderFields;
	}
	
	/**
	 * @return the ymd
	 */
	public String getYmd() {
		return ymd;
	}
	/**
	 * @param ymd the ymd to set
	 */
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	/**
	 * @return the excelDtos
	 */
	public List<CourierScheduleExcelDto> getExcelDtos() {
		return excelDtos;
	}
	/**
	 * @param excelDtos the excelDtos to set
	 */
	public void setExcelDtos(List<CourierScheduleExcelDto> excelDtos) {
		this.excelDtos = excelDtos;
	}
	/**
	 * @return the userOrgDatas
	 */
	public List<String> getUserOrgDatas() {
		return userOrgDatas;
	}
	/**
	 * @param userOrgDatas the userOrgDatas to set
	 */
	public void setUserOrgDatas(List<String> userOrgDatas) {
		this.userOrgDatas = userOrgDatas;
	}
	
}
