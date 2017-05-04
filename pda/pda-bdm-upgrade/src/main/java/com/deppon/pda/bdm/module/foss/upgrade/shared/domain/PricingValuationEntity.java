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
public class PricingValuationEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String goods_type_id;
	private String goods_type_code;
	private String product_id;
	private String product_code;
	private String dept_region_id;
	private String arrv_region_id;
	private String sales_channel_code;
	private String sales_channel_id;
	private String pricing_entry_id;
	private String pricing_entry_code;
	private String price_plan_id;
	private String price_plan_code;
	private String marketing_event_id;
	private String marketing_event_code;
	private String long_or_short;
	private String currency_code;
	private String centralize_pickup;
	private String flight_shift;
	private String pricing_industry_id;
	private String pricing_industry_code;
	private String type;
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
	public String getGoods_type_id() {
		return goods_type_id;
	}
	public void setGoods_type_id(String goods_type_id) {
		this.goods_type_id = goods_type_id;
	}
	public String getGoods_type_code() {
		return goods_type_code;
	}
	public void setGoods_type_code(String goods_type_code) {
		this.goods_type_code = goods_type_code;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getDept_region_id() {
		return dept_region_id;
	}
	public void setDept_region_id(String dept_region_id) {
		this.dept_region_id = dept_region_id;
	}
	public String getArrv_region_id() {
		return arrv_region_id;
	}
	public void setArrv_region_id(String arrv_region_id) {
		this.arrv_region_id = arrv_region_id;
	}
	public String getSales_channel_code() {
		return sales_channel_code;
	}
	public void setSales_channel_code(String sales_channel_code) {
		this.sales_channel_code = sales_channel_code;
	}
	public String getSales_channel_id() {
		return sales_channel_id;
	}
	public void setSales_channel_id(String sales_channel_id) {
		this.sales_channel_id = sales_channel_id;
	}
	public String getPricing_entry_id() {
		return pricing_entry_id;
	}
	public void setPricing_entry_id(String pricing_entry_id) {
		this.pricing_entry_id = pricing_entry_id;
	}
	public String getPricing_entry_code() {
		return pricing_entry_code;
	}
	public void setPricing_entry_code(String pricing_entry_code) {
		this.pricing_entry_code = pricing_entry_code;
	}
	public String getPrice_plan_id() {
		return price_plan_id;
	}
	public void setPrice_plan_id(String price_plan_id) {
		this.price_plan_id = price_plan_id;
	}
	public String getPrice_plan_code() {
		return price_plan_code;
	}
	public void setPrice_plan_code(String price_plan_code) {
		this.price_plan_code = price_plan_code;
	}
	public String getMarketing_event_id() {
		return marketing_event_id;
	}
	public void setMarketing_event_id(String marketing_event_id) {
		this.marketing_event_id = marketing_event_id;
	}
	public String getMarketing_event_code() {
		return marketing_event_code;
	}
	public void setMarketing_event_code(String marketing_event_code) {
		this.marketing_event_code = marketing_event_code;
	}
	public String getLong_or_short() {
		return long_or_short;
	}
	public void setLong_or_short(String long_or_short) {
		this.long_or_short = long_or_short;
	}
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	public String getCentralize_pickup() {
		return centralize_pickup;
	}
	public void setCentralize_pickup(String centralize_pickup) {
		this.centralize_pickup = centralize_pickup;
	}
	public String getFlight_shift() {
		return flight_shift;
	}
	public void setFlight_shift(String flight_shift) {
		this.flight_shift = flight_shift;
	}
	public String getPricing_industry_id() {
		return pricing_industry_id;
	}
	public void setPricing_industry_id(String pricing_industry_id) {
		this.pricing_industry_id = pricing_industry_id;
	}
	public String getPricing_industry_code() {
		return pricing_industry_code;
	}
	public void setPricing_industry_code(String pricing_industry_code) {
		this.pricing_industry_code = pricing_industry_code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
