/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

/**
 *<p>Title: GridHeaderEntity</p>
 * <p>Description:用于生成快递排班报表表头实体 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-4-23
 */
public class GridHeaderEntity implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1218080715399104514L;
	/**
	 * 表头日期（1-31）
	 */
	private int headerDate;
	/**
	 * 星期（大写）
	 */
	private String dayText;
	/**
	 * 列头的name(根据日期1-31)
	 */
	private String dataIndex;
	/**
	 * 数据类型（用于model定义数据类型）
	 */
	private String dataType;
	/**
	 * render的数据
	 */
	private String rendererData;
	/**
	 * @return the headerDate
	 */
	public int getHeaderDate() {
		return headerDate;
	}
	/**
	 * @param headerDate the headerDate to set
	 */
	public void setHeaderDate(int headerDate) {
		this.headerDate = headerDate;
	}
	/**
	 * @return the dayText
	 */
	public String getDayText() {
		return dayText;
	}
	/**
	 * @param dayText the dayText to set
	 */
	public void setDayText(String dayText) {
		this.dayText = dayText;
	}
	/**
	 * @return the dataIndex
	 */
	public String getDataIndex() {
		return dataIndex;
	}
	/**
	 * @param dataIndex the dataIndex to set
	 */
	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * @return the rendererData
	 */
	public String getRendererData() {
		return rendererData;
	}
	/**
	 * @param rendererData the rendererData to set
	 */
	public void setRendererData(String rendererData) {
		this.rendererData = rendererData;
	}
	
}
