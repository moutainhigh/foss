package com.deppon.foss.module.base.baseinfo.api.shared.domain;

/**
 * 主客户数据日志信息 * 
 * version:V1.0,author:261997-foss-css,date:2015-07-24 上午10:32:49
 * </p>
 * 
 * @author 261997-foss-css
 * @date 2015-07-11 上午10:32:49
 * @since
 * @version
 */
import java.math.BigDecimal;
import java.util.Date;

public class CrmpushfossLogEntity {

    private String id;


    private BigDecimal crmCusId;


    private String code;


    private Date createTime;


    private String result;


    private String trueorfalse;

 
    private String falsereason;


    private String content;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public BigDecimal getCrmCusId() {
        return crmCusId;
    }


    public void setCrmCusId(BigDecimal crmCusId) {
        this.crmCusId = crmCusId;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getResult() {
        return result;
    }

 
    public void setResult(String result) {
        this.result = result;
    }

 
    public String getTrueorfalse() {
        return trueorfalse;
    }


    public void setTrueorfalse(String trueorfalse) {
        this.trueorfalse = trueorfalse;
    }


    public String getFalsereason() {
        return falsereason;
    }


    public void setFalsereason(String falsereason) {
        this.falsereason = falsereason;
    }

 
    public String getContent() {
        return content;
    }

 
    public void setContent(String content) {
        this.content = content;
    }
}