package com.deppon.foss.module.pickup.creating.client.vo;

/**
 * 
 * @author DP-FOSS-YANGKANG
 * 发票标记vo
 */
public class InvoiceMarkVo {
	//发票名称
	private String name;
	//发票code
	private String code;
	
	
	/**
	 *重写toString方法  直接返回名称  让下拉框显示
	 */
	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof InvoiceMarkVo)) {
			return false;
		}
		InvoiceMarkVo other = (InvoiceMarkVo) obj;
		if(other.code==null){
			return this.code==null;
		}
		
		if (!other.code.equals(this.code)) {
			return false;
		}
		return true;
	}
	

	@Override
	public int hashCode() {
		return name.hashCode() + code.hashCode();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
