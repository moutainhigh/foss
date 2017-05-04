package com.deppon.foss.module.pickup.waybill.shared.dto;

/**
 * 运单入库DTO
 * @author WangQianJin
 * @date 2013-3-21 下午4:36:06
 */
public class WaybillStockDto {
    
    //失败
    public static final String FAILURE="failure";
    //处理中
    public static final String PRO="pro";

    /**
     * 是否成功
     */
    private String status;
    /**
     * 失败的消息
     */
    private String faileMessage;
    
    
    
    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getFaileMessage() {
        return faileMessage;
    }
    
    public void setFaileMessage(String faileMessage) {
        this.faileMessage = faileMessage;
    }
    
    
    
    
}
