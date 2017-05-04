/**
 *  initial comments.
 */

package com.deppon.foss.module.transfer.dubbo.api.define;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 空运流水号明细
 * @author 038300-foss-pengzhen
 * @date 2012-12-25 下午6:22:28
 */
public class AirSerialNoDetail extends BaseEntity {

	private static final long serialVersionUID = 4604027169670222742L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 标记是否删除
	 */
	private String flag;
	/**
	 * 当前操作标记
	 */
	private String operateFlag;
	/**
	 * 显示类型(Y"删除"高亮显示N"灰色"灰色显示)
	 */
	private String viewFlag;
	
	/**
	 * 当前操作状态
	 */
	private String status;
	
	/**
	 * 库存状态
	 */
	private String stockStatus;
	
	/**
	 * 总件数
	 */
	private Integer totalNumber;
	
	/**
	 * 运单号
	 */
	private String waybillNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * 设置 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 获取 显示类型(Y"删除"高亮显示N"灰色"灰色显示).
	 *
	 * @return the 显示类型(Y"删除"高亮显示N"灰色"灰色显示)
	 */
	public String getViewFlag() {
		return viewFlag;
	}

	/**
	 * 设置 显示类型(Y"删除"高亮显示N"灰色"灰色显示).
	 *
	 * @param viewFlag the new 显示类型(Y"删除"高亮显示N"灰色"灰色显示)
	 */
	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

	/**
	 * 获取 标记是否删除.
	 *
	 * @return the 标记是否删除
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * 设置 标记是否删除.
	 *
	 * @param flag the new 标记是否删除
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 获取 当前操作标记.
	 *
	 * @return the 当前操作标记
	 */
	public String getOperateFlag() {
		return operateFlag;
	}

	/**
	 * 设置 当前操作标记.
	 *
	 * @param operateFlag the new 当前操作标记
	 */
	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
}