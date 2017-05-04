package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDto;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadDiffReportDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.PDAUnloadDiffReportDetailLogEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.PDAUnloaddiffReportLogEntity;

public class PDAUnloadDiffReportDao extends iBatis3DaoImpl implements
		IPDAUnloadDiffReportDao {
	
	/**
	 * mapper文件命名空间
	 */
	public static final String NAMESPACE = "foss.unload.pdaunloaddiffreport.";

	/**
	 * 
	 * <p>PDA根据部门查询卸车差异</p> 
	 * @author alfred
	 * @date 2014-6-11 上午11:02:42
	 * @param deptCodes
	 * @param hours
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadDiffReportDao#queryPDAUnloadDiffReportList(java.util.List, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PDAUnloadDiffReportDto> queryPDAUnloadDiffReportList(
			List<String> deptCodes, int hours) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("deptCodes", deptCodes);
		map.put("hours", hours);
		return this.getSqlSession().selectList(NAMESPACE+"queryPDAUnloadDiffReportList",map);
	}

	/**
	 * 
	 * <p>PDA根据卸车差异编号查询卸车差异明细</p> 
	 * @author alfred
	 * @date 2014-6-13 上午11:03:10
	 * @param reportCode
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadDiffReportDao#queryPDAUnloadDiffDetailList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PDAUnloadDiffReportDetailDto> queryPDAUnloadDiffDetailList(
			String reportCode) {
		return this.getSqlSession().selectList(NAMESPACE+"queryPDAUnloadDiffDetailList",reportCode);
	}

	/**
	 * 
	 * <p>插入PDA处理卸车差异报告明细日志</p> 
	 * @author alfred
	 * @date 2014-6-13 下午3:10:54
	 * @param detailLogEntity
	 * @return
	 * @see
	 */
	@Override
	public int addPDAScanDetailLog(
			PDAUnloadDiffReportDetailLogEntity detailLogEntity) {
		return this.getSqlSession().insert(NAMESPACE+"addPDAScanDetailLog",detailLogEntity);
	}

	/**
	 * 
	 * <p>插入PDA处理卸车报告日志</p> 
	 * @author alfred
	 * @date 2014-6-13 下午3:12:04
	 * @param reportLogEntity
	 * @return
	 * @see
	 */
	@Override
	public int addPDAReportLog(PDAUnloaddiffReportLogEntity reportLogEntity) {
		return this.getSqlSession().insert(NAMESPACE+"addPDAReportLog",reportLogEntity);
	}

	/**
	 * 
	 * <p>更新PDA处理状态</p> 
	 * @author alfred
	 * @date 2014-6-13 下午3:34:54
	 * @param reportCode
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadDiffReportDao#updateUnloadDiffReportHandleState(java.lang.String)
	 */
	@Override
	public int updateUnloadDiffReportHandleState(String reportCode) {
		return this.getSqlSession().update(NAMESPACE + "updateUnloadDiffReportHandleState",reportCode);
	}

	/**
	 *查询二程接驳卸车差异报告
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-24 13:26:30
	 * @param deptCode部门编码，operatorCode操作人
	 ***/
	@Override
	public List<PDAUnloadDiffReportDto>  querySCPDAUnloadDiffReportList(String deptCode,String operatorCode){
		
		Map<String,String> hs=new HashMap<String,String>();
		hs.put("deptCode", deptCode);
		hs.put("operatorCode", operatorCode);
		
		return this.getSqlSession().selectList(NAMESPACE+"querySCPDAUnloadDiffReportList", hs);
	}

	/**
	 *查询二程接驳卸车差异报告
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-24 13:26:30
	 * @param deptCode部门编码，operatorCode操作人
	 ***/
	@Override
	public List<PDAUnloadDiffReportDto>  querySCPDAUnloadDiffReportList(String deptCode,String operatorCode,
			String reportCode,Date queryDate){
		
		Map<String,Object> hs=new HashMap<String,Object>();
		hs.put("deptCode", deptCode);
		hs.put("operatorCode", operatorCode);
		hs.put("reportCode", reportCode);
		hs.put("queryDate", queryDate);
		
		return this.getSqlSession().selectList(NAMESPACE+"querySCPDAUnloadDiffReportByDate", hs);
	}
	
	/**
	 *根据差异报告编号查询二程接驳卸车差异报告明细
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-24 13:26:30
	 * @param reportCode
	 ***/
	@Override
   public	List<PDAUnloadDiffReportDetailDto> querySCPDAUnloadDiffReportDetailList(String reportCode, String operatorCode){
		Map<String,String> hs=new HashMap<String,String>();
		hs.put("operatorCode", operatorCode);
		hs.put("reportCode", reportCode);
		return this.getSqlSession().selectList(NAMESPACE+"querySCPDAUnloadDiffReportDetailList", hs);

	}
}
