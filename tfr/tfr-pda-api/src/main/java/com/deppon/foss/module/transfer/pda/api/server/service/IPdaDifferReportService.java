/**
 * 
 */
package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PdaDifferDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PdaDifferEntity;

/**
 * @author niuly
 * @date 2014-05-21
 */
public interface IPdaDifferReportService extends IService {
	/**
	 * @author niuly
	 * @date 2014-5-21上午9:15:00
	 * @function 查询清仓差异报告
	 * @param deptCode
	 * @param operatorCode
	 * @param pdaNo
	 * @return List
	 */
	List<PdaDifferEntity> queryDifferReportList(String deptCode,String operatorCode,String pdaNo);
	
	/**
	 * @author niuly
	 * @date 2014-5-24下午4:46:49
	 * @function 根据差异报告编号查询对应的差异明细。flag为YES时，表示是通过输入差异报告编号查询，此时无时间范围和PDA是否提交限制
	 * @param reportNo
	 * @param deptCode
	 * @param operatorCode
	 * @param pdaNo
	 * @param handInputFlg 用于标记是否通过输入差异报告编号查询
	 * @return
	 */
	List<PdaDifferDetailEntity> queryDifferDetailByReportNo(String reportNo,String deptCode,String operatorCode,String pdaNo,String handInputFlg);
	
	/**
	 * @author niuly
	 * @date 2014-5-24下午4:51:05
	 * @function 异步处理清仓差异明细。注：保留最早的处理时间；PDA端提交任务后，依然接收处理消息
	 * @param reportCode
	 * @param waybillNo
	 * @param serialNo
	 * @param ScanTime
	 * @param operatorCode
	 * @param pdaNo
	 * @return
	 */
	boolean handleDifferDetail(String reportCode,String waybillNo,String serialNo,Date scanTime,String operatorCode,String pdaNo);
	
	/**
	 * @author niuly
	 * @date 2014-5-24下午4:53:08
	 * @function PDA提交差异报告处理任务
	 * @param reportCode
	 * @param operatorCode
	 * @param pdaNo
	 * @return
	 */
	boolean commitHandleTask(String reportCode,String operatorCode,String pdaNo);
}
