package com.deppon.foss.dubbo.uip.api.define.exception;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:异常接口</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2011-4-9 steven.cheng 新增
* </div>  
********************************************
 */
public interface IException {
    
    String getErrorCode();
    
    String getNativeMessage();
    
    void setErrorArguments(Object... objects);
    
    Object[] getErrorArguments();
    
}
