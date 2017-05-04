package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import java.math.BigDecimal;

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
public class PricingCriteriaDetailEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String caculate_type;
	private Long fee;
	private BigDecimal fee_rate;
	private BigDecimal leftRange;
	private BigDecimal rightRange;
	private Long min_fee;
	private Long max_fee;
	private String sub_type;
	private String canModify;
	private String process_program;
	private String process_parm_val;
	private String pricing_criteria_id;
	private Long parm2;
	private Long parm1;
	private String t_srv_price_rule_id;
	private Long parm3;
	private Long parm4;
	private Long parm5;
	private BigDecimal discount_rate;
	private String dept_region_id;
	private String pricing_valuation_id;
	private String canDelete;
	private String descRiption;
	private String active;
	private Long version_no;
	private String operFlag;
	
	
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCaculate_type() {
		return caculate_type;
	}
	public void setCaculate_type(String caculate_type) {
		this.caculate_type = caculate_type;
	}
	public Long getFee() {
		return fee;
	}
	public void setFee(Long fee) {
		this.fee = fee;
	}
	public BigDecimal getFee_rate() {
		return fee_rate;
	}
	public void setFee_rate(BigDecimal fee_rate) {
		this.fee_rate = fee_rate;
	}
	public BigDecimal getLeftRange() {
		return leftRange;
	}
	public void setLeftRange(BigDecimal leftRange) {
		this.leftRange = leftRange;
	}
	public BigDecimal getRightRange() {
		return rightRange;
	}
	public void setRightRange(BigDecimal rightRange) {
		this.rightRange = rightRange;
	}
	public Long getMin_fee() {
		return min_fee;
	}
	public void setMin_fee(Long min_fee) {
		this.min_fee = min_fee;
	}
	public Long getMax_fee() {
		return max_fee;
	}
	public void setMax_fee(Long max_fee) {
		this.max_fee = max_fee;
	}
	public String getSub_type() {
		return sub_type;
	}
	public void setSub_type(String sub_type) {
		this.sub_type = sub_type;
	}
	public String getCanModify() {
		return canModify;
	}
	public void setCanModify(String canModify) {
		this.canModify = canModify;
	}
	public String getProcess_program() {
		return process_program;
	}
	public void setProcess_program(String process_program) {
		this.process_program = process_program;
	}
	public String getProcess_parm_val() {
		return process_parm_val;
	}
	public void setProcess_parm_val(String process_parm_val) {
		this.process_parm_val = process_parm_val;
	}
	public String getPricing_criteria_id() {
		return pricing_criteria_id;
	}
	public void setPricing_criteria_id(String pricing_criteria_id) {
		this.pricing_criteria_id = pricing_criteria_id;
	}
	public Long getParm2() {
		return parm2;
	}
	public void setParm2(Long parm2) {
		this.parm2 = parm2;
	}
	public Long getParm1() {
		return parm1;
	}
	public void setParm1(Long parm1) {
		this.parm1 = parm1;
	}
	public String getT_srv_price_rule_id() {
		return t_srv_price_rule_id;
	}
	public void setT_srv_price_rule_id(String t_srv_price_rule_id) {
		this.t_srv_price_rule_id = t_srv_price_rule_id;
	}
	public Long getParm3() {
		return parm3;
	}
	public void setParm3(Long parm3) {
		this.parm3 = parm3;
	}
	public Long getParm4() {
		return parm4;
	}
	public void setParm4(Long parm4) {
		this.parm4 = parm4;
	}
	public Long getParm5() {
		return parm5;
	}
	public void setParm5(Long parm5) {
		this.parm5 = parm5;
	}
	public BigDecimal getDiscount_rate() {
		return discount_rate;
	}
	public void setDiscount_rate(BigDecimal discount_rate) {
		this.discount_rate = discount_rate;
	}
	public String getDept_region_id() {
		return dept_region_id;
	}
	public void setDept_region_id(String dept_region_id) {
		this.dept_region_id = dept_region_id;
	}
	public String getPricing_valuation_id() {
		return pricing_valuation_id;
	}
	public void setPricing_valuation_id(String pricing_valuation_id) {
		this.pricing_valuation_id = pricing_valuation_id;
	}
	public String getCanDelete() {
		return canDelete;
	}
	public void setCanDelete(String canDelete) {
		this.canDelete = canDelete;
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
	public Long getVersion_no() {
		return version_no;
	}
	public void setVersion_no(Long version_no) {
		this.version_no = version_no;
	}
	
}
