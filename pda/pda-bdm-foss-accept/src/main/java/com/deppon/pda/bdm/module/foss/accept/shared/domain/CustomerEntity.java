package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

import com.deppon.pda.bdm.module.core.shared.domain.DomainEntity;



/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain
 * @file CustomerEntity.java
 * @description 客户信息
 * @author ZhangZhenXian
 * @created 2013-07-23 下午4:05:05
 * @version 1.0
 */
public class CustomerEntity extends DomainEntity implements Serializable{

    /**    
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）    
     *    
     * @since Ver 1.1    
     */    
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 客户编码
     */      
    private String customerId;
    /**
     * 客户名称
     */        
    private String customerName;    
    /**
     * 手机号
     */    
    private String customerPhone;
    /**
     * 固定电话
     * */
    private String fixedPhone;
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerPhone() {
        return customerPhone;
    }
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    public String getFixedPhone() {
        return fixedPhone;
    }
    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }
}
