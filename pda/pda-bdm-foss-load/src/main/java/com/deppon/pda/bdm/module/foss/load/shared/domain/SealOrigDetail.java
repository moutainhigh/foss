package com.deppon.pda.bdm.module.foss.load.shared.domain;

/** 录入装车封签
  * @ClassName SealOrigDetail 
  * @Description TODO 
  * @author zhangzhenxian 
  * @date 2013-11-8 上午9:28:13 
*/ 
public class SealOrigDetail {
    /**封签号码**/
    private String sealNo; 
    //PDA20140327版本后可删除该字段
    /**封签类型,back    后门, side 侧门**/
    private String sealType; 
    
    /**绑定封签  SCAN  , BY_HAND  **/
    private String BindType ;

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

    public String getBindType() {
        return BindType;
    }

    public void setBindType(String bindType) {
        BindType = bindType;
    }
    
    
}
