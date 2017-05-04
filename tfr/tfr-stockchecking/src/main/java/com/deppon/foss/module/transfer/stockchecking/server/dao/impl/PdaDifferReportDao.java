/**
 * 
 */
package com.deppon.foss.module.transfer.stockchecking.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PdaDifferDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PdaDifferEntity;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IPdaDifferReportDao;
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
public class PdaDifferReportDao extends iBatis3DaoImpl implements IPdaDifferReportDao {

	public static final String NAMESPACE = "foss.tfr.PdaDifferReportDao.";
	/**
	 * @author niuly
	 * @date 2014-5-21上午10:24:34
	 * @function PDA处理差异报告时，查询处理人所在部门48小时内的所有含快递货的差异报告
	 * @param deptCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaDifferEntity> queryDifferReportList(List<String> deptCodes,int hours) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("deptCodes", deptCodes);
		map.put("hours", hours);
		return this.getSqlSession().selectList("queryDifferReportList",map);
	}
	/**
	 * @author niuly
	 * @date 2014-5-24下午5:06:19
	 * @function 根据差异报告编号查询差异明细
	 * @param reportNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaDifferDetailEntity> queryDifferDetailByReportNo(String reportCode) {
		return this.getSqlSession().selectList("queryDifferDetailByReportNo",reportCode);
	}
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
	@Override
	public int addScanInfo(PdaDifferDetailLogEntity detailEntity) {
		
		return this.getSqlSession().insert("insertScanInfo",detailEntity);
	}
	/**
	 * @author niuly
	 * @date 2014-5-29上午9:53:58
	 * @function 根据清仓差异报告编号，运单号，流水号查询差异明细
	 * @param reportCode
	 * @param waybillNo
	 * @param serialNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StDifferDetailEntity queryStDifferDetail(String reportCode,String waybillNo, String serialNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("reportCode", reportCode);
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		List<StDifferDetailEntity> list =  this.getSqlSession().selectList("queryDifferDetail",map);
		if(null == list || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	/**
	 * @author niuly
	 * @date 2014-5-29下午4:50:04
	 * @function 插入PDA处理日志
	 * @param differEntity
	 */
	@Override
	public int addBranchInfo(PdaDifferLogEntity differEntity) {
		return this.getSqlSession().insert("insertBranchInfo", differEntity);
	}
	/**
	 * @author niuly
	 * @date 2014-5-29下午5:29:22
	 * @function 更新差异报告中的PDA处理状态
	 * @param reportCode
	 */
	@Override
	public int updatePdaHandleStatus(String reportCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("reportCode", reportCode);
		map.put("pdaHandleStatus", TransferConstants.STOCK_CHECKING_REPORT_DONE);
		return this.getSqlSession().update("updatePdaHandleStatus", map);
	}

	/**
	 * @author niuly
	 * @date 2014-5-29下午6:04:13
	 * @function 根据差异报告编号查询差异报告信息
	 * @param reportCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StDifferReportEntity queryDifferByCode(String reportCode) {
		StDifferReportEntity entity = null;
		List<StDifferReportEntity> differList = this.getSqlSession().selectList("queryDifferByCode", reportCode);
		if(null != differList && differList.size() >0 ){
			entity = differList.get(0);
		}
		return entity;
	}
	
	/**
	 * @author niuly
	 * @date 2014-6-1上午10:02:58
	 * @function PDA处理清仓差异参与人
	 * @param operatorEntity
	 */
	@Override
	public int addOperatorInfo(PdaDifferOperatorEntity operatorEntity) {
		return this.getSqlSession().insert("insertOperatorInfo", operatorEntity);
	}
	/**
	 * @author niuly
	 * @date 2014-6-1上午10:22:40
	 * @function 根据差错编号和操作人工号查询操作人信息
	 * @param reportCode
	 * @param operatorCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PdaDifferOperatorEntity queryDifferOperator(String reportCode,String operatorCode) {
		PdaDifferOperatorEntity entity = null;
		Map<String,String> map = new HashMap<String,String>();
		map.put("reportCode", reportCode);
		map.put("operatorCode", operatorCode);
		List<PdaDifferOperatorEntity> operatorList = this.getSqlSession().selectList("queryDifferOperator", map);
		if(null != operatorList && operatorList.size() >0 ){
			entity = operatorList.get(0);
		}
		return entity;
	}
}
