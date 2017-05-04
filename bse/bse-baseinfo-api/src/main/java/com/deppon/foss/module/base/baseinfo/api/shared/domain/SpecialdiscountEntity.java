package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class SpecialdiscountEntity {
 
    private String id;

    private String code;
  
    private String producttype;

    private String productcode;
    
    private String creater;
    
    private Date createtime;

    private Date proposetime;

    private Date quittime;
    
    //产品类型
    private String type;
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private BigDecimal crmId;

	private String active;

	public BigDecimal getCrmId() {
		return crmId;
	}

	public void setCrmId(BigDecimal crmId) {
		this.crmId = crmId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public Date getProposetime() {
        return proposetime;
    }

    public void setProposetime(Date proposetime) {
        this.proposetime = proposetime;
    }

    public Date getQuittime() {
        return quittime;
    }

    public void setQuittime(Date quittime) {
        this.quittime = quittime;
    }

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
    
}