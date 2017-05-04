package com.deppon.pda.bdm.module.foss.load.shared.domain;



/** 检查封签装车
  * @ClassName SealDestDetail 
  * @Description TODO 
  * @author zhangzhenxian 
  * @date 2013-11-8 上午9:13:17 
*/ 
public class SealDestDetail {
	
    private String id;
    /**对应扫描表ID**/
    private String scanId;
    /**封签号码**/
    private String sealNo; 
    //PDA20140327版本后可删除该字段
    /**封签类型,back    后门, side 侧门**/
    private String sealType;     
    /**车牌号**/
    private String truckCode;
    /**封签校验方式SCANED扫描, BY_HAND手输**/
    private String checkType;
    /**备注**/
    private String remark;

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }
    public String getSealType() {
        return sealType;
    }

    public void setSealType(String sealType) {
        this.sealType = sealType;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public String getScanId() {
		return scanId;
	}

	public void setScanId(String scanId) {
		this.scanId = scanId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    

}
