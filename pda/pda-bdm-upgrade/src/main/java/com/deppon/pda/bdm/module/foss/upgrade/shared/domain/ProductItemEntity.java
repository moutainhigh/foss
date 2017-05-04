package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-3-16 下午4:22:31,content:TODO </p>
 * @author chengang
 * @date 2013-3-16 下午4:22:31
 * @since
 * @version
 */
public class ProductItemEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	private String t_srv_goodstype_id;
	private String t_srv_goodstype_code;
	private String t_srv_product_id;
	private String t_srv_product_code;
	private String code;
	private String name;
	private String fee_bottom;
	private String descRiption;
	private String active;
	private Date begin_time;
	private Date end_time;
	private Long version_no;
	private String operFlag;
	
	
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	public String getT_srv_goodstype_id() {
		return t_srv_goodstype_id;
	}
	public void setT_srv_goodstype_id(String t_srv_goodstype_id) {
		this.t_srv_goodstype_id = t_srv_goodstype_id;
	}
	public String getT_srv_goodstype_code() {
		return t_srv_goodstype_code;
	}
	public void setT_srv_goodstype_code(String t_srv_goodstype_code) {
		this.t_srv_goodstype_code = t_srv_goodstype_code;
	}
	public String getT_srv_product_id() {
		return t_srv_product_id;
	}
	public void setT_srv_product_id(String t_srv_product_id) {
		this.t_srv_product_id = t_srv_product_id;
	}
	public String getT_srv_product_code() {
		return t_srv_product_code;
	}
	public void setT_srv_product_code(String t_srv_product_code) {
		this.t_srv_product_code = t_srv_product_code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFee_bottom() {
		return fee_bottom;
	}
	public void setFee_bottom(String fee_bottom) {
		this.fee_bottom = fee_bottom;
	}
	public String getDescRiption() {
		return descRiption;
	}
	public void setDescRiption(String descRiption) {
		this.descRiption = descRiption;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public Long getVersion_no() {
		return version_no;
	}
	public void setVersion_no(Long version_no) {
		this.version_no = version_no;
	}
	
}
