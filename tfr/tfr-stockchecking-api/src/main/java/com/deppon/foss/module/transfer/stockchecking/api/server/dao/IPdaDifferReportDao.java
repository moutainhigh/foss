/**
 * 
 */
package com.deppon.foss.module.transfer.stockchecking.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.domain.PdaDifferDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PdaDifferEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.PdaDifferDetailLogEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.PdaDifferLogEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.PdaDifferOperatorEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferReportEntity;

/**
 * @author niuly
 * @date 2014-05-21
 * @function PDA处理清仓差异报告dao
 */
public interface IPdaDifferReportDao {
	/**
	 * @author niuly
	 * @date 2014-5-21上午10:24:34
	 * @function PDA处理差异报告时，查询处理人所在部门规定小时内的所有含快递货的差异报告
	 * @param deptCode,hours
	 * @return
	 */
	List<PdaDifferEntity> queryDifferReportList(List<String> deptCodes,int hours);

	/**
	 * @author niuly
	 * @date 2014-5-24下午5:06:19
	 * @function 根据差异报告编号查询差异明细
	 * @param reportNo
	 * @return
	 */
	List<PdaDifferDetailEntity> queryDifferDetailByReportNo(String reportNo);

	/**
	 * @author niuly
	 * @date 2014-5-24下午5:26:53
	 * @function 记录PDA处理差异扫描日志
	 * @param reportCode
	 * @param waybillNo
	 * @param serialNo
	 * @param scanTime
	 * @param operatorCode
	 * @param pdaNo
	 * @return
	 */
	int addScanInfo(PdaDifferDetailLogEntity detailEntity);

	/**
	 * @author niuly
	 * @date 2014-5-29上午9:53:58
	 * @function 根据清仓差异报告编号，运单号，流水号查询差异明细
	 * @param reportCode
	 * @param waybillNo
	 * @param serialNo
	 * @return
	 */
	StDifferDetailEntity queryStDifferDetail(String reportCode,String waybillNo, String serialNo);

	/**
	 * @author niuly
	 * @date 2014-5-29下午4:50:04
	 * @function 插入PDA处理日志
	 * @param differEntity
	 */
	int addBranchInfo(PdaDifferLogEntity differEntity);

	/**
	 * @author niuly
	 * @date 2014-5-29下午5:29:22
	 * @function 更新差异报告中的PDA处理状态
	 * @param reportCode
	 */
	int updatePdaHandleStatus(String reportCode);

	/**
	 * @author niuly
	 * @date 2014-5-29下午6:04:13
	 * @function 根据差异报告编号查询差异报告信息
	 * @param reportCode
	 * @return
	 */
	StDifferReportEntity queryDifferByCode(String reportCode);

	/**
	 * @author niuly
	 * @date 2014-6-1上午10:02:58
	 * @function PDA处理清仓差异参与人
	 * @param operatorEntity
	 */
	int addOperatorInfo(PdaDifferOperatorEntity operatorEntity);

	/**
	 * @author niuly
	 * @date 2014-6-1上午10:22:40
	 * @function 根据差错编号和操作人工号查询操作人信息
	 * @param reportCode
	 * @param operatorCode
	 * @return
	 */
	PdaDifferOperatorEntity queryDifferOperator(String reportCode,String operatorCode);

}
