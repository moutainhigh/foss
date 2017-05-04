package com.deppon.foss.module.transfer.unload.api.server.service;
/**
 * 
* @ClassName: IUnloadTaskSMSService 
* @Description: 卸车时 根据运单 发短信
* @author 189284-ZhangXu 
* @date 2015-6-18 下午3:59:52 
*
 */
public interface IUnloadTaskSmsService {
	/**
	 * 
	* @Title: sendUnloadTaskSms 
	* @Description: 卸车任务创建的时候 发送短信
	* @param     设定文件 
	* @return void    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-6-24  上午9:59:36
	* @throws
	 */
  public void sendUnloadTaskSms();
}
