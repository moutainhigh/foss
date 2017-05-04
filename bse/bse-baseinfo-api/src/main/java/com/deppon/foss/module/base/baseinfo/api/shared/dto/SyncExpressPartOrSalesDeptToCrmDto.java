package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

/**
 * 同步crm快递点部和营业部对应关系的Dto
 * 
 * @author WangPeng
 * @date   2013-08-01 6:19 PM
 *
 */
public class SyncExpressPartOrSalesDeptToCrmDto implements Serializable {


	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 739113098199165719L;
	
	/**
	 * 营业部名称
	 */
	private String salesPartName;
	
	/**
	 * 营业部标杆编码
	 */
	private String salesUnifyCode;
	
	/**
	 * 营业部编码
	 */
	private String salesDeptCode;
	
	/**
	 * 新的快递点部名称
	 */
	private String newExpressPartName;
	
	/**
	 * 新的快递点部编码
	 */
	private String newExpressPartCode;
	
	/**
	 * 新的快递点部标杆编码
	 */
	private String newExpressPartUnifyCode;
	
	/**
	 * 旧的快递点部名称
	 */
	private String oldExpressPartName;
	
	/**
	 * 旧的快递点部编码
	 */
	private String oldExpressPartCode;
	
	/**
	 * 旧的快递点部杆编码
	 */
	private String oldExpressPartUnifyCode;

	public String getSalesPartName() {
		return salesPartName;
	}

	public void setSalesPartName(String salesPartName) {
		this.salesPartName = salesPartName;
	}

	public String getSalesUnifyCode() {
		return salesUnifyCode;
	}

	public void setSalesUnifyCode(String salesUnifyCode) {
		this.salesUnifyCode = salesUnifyCode;
	}

	public String getNewExpressPartName() {
		return newExpressPartName;
	}

	public void setNewExpressPartName(String newExpressPartName) {
		this.newExpressPartName = newExpressPartName;
	}

	public String getNewExpressPartUnifyCode() {
		return newExpressPartUnifyCode;
	}

	public void setNewExpressPartUnifyCode(String newExpressPartUnifyCode) {
		this.newExpressPartUnifyCode = newExpressPartUnifyCode;
	}

	public String getOldExpressPartName() {
		return oldExpressPartName;
	}

	public void setOldExpressPartName(String oldExpressPartName) {
		this.oldExpressPartName = oldExpressPartName;
	}

	public String getOldExpressPartUnifyCode() {
		return oldExpressPartUnifyCode;
	}

	public void setOldExpressPartUnifyCode(String oldExpressPartUnifyCode) {
		this.oldExpressPartUnifyCode = oldExpressPartUnifyCode;
	}

	public String getSalesDeptCode() {
		return salesDeptCode;
	}

	public void setSalesDeptCode(String salesDeptCode) {
		this.salesDeptCode = salesDeptCode;
	}

	public String getNewExpressPartCode() {
		return newExpressPartCode;
	}

	public void setNewExpressPartCode(String newExpressPartCode) {
		this.newExpressPartCode = newExpressPartCode;
	}

	public String getOldExpressPartCode() {
		return oldExpressPartCode;
	}

	public void setOldExpressPartCode(String oldExpressPartCode) {
		this.oldExpressPartCode = oldExpressPartCode;
	}
	
	

}
