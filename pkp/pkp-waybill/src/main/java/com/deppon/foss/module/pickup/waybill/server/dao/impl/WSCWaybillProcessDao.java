package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWSCWaybillProcessDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.AddWSCWaybillProcessEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.WSCWaybillProcessVo;
import com.deppon.foss.util.UUIDUtils;
/**
 * 
 * @author 350909    郭倩云
 *主要与数据库表PKP.T_SRV_ADD_ASYN_WAYBILL交互
 */
public class WSCWaybillProcessDao extends iBatis3DaoImpl implements IWSCWaybillProcessDao{
	
	private static final String NAMESPACE = "foss.pkp.WSCWaybillProcessEntityMapper.";
	
	AddWSCWaybillProcessEntity addWSCWaybillProcessEntity=new AddWSCWaybillProcessEntity();
	/**
	 * @author 350909    郭倩云
	 * 将生成财务单据所需要的运单信息和代刷卡数据相关信息插入到数据库表PKP.T_SRV_ADD_ASYN_WAYBILL
	 */
	public int insertWSCWaybillProcessEntity(String requestType,String jsonString) {
		addWSCWaybillProcessEntity.setId(UUIDUtils.getUUID());
		addWSCWaybillProcessEntity.setCreateTime(new Date());
		addWSCWaybillProcessEntity.setModifyTime(new Date());
		addWSCWaybillProcessEntity.setRequestBody(jsonString);
		addWSCWaybillProcessEntity.setJobId(WaybillConstants.UNKNOWN);
		addWSCWaybillProcessEntity.setFailReason(WaybillConstants.UNKNOWN);
		addWSCWaybillProcessEntity.setRequestType(requestType);
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", addWSCWaybillProcessEntity);
	}
	/**
	 * @author 350909    郭倩云
	 * 将jobId为N/A的前100条数据的jobId更新为uuid
	 */
	public int updateJobIDTopByRowNum(WSCWaybillProcessVo wSCWaybillProcessVo) {
		return this.getSqlSession().update(NAMESPACE + "updateJobIDTopByRowNum", wSCWaybillProcessVo);
	}
	/**
	 * @author 350909    郭倩云
	 * 根据jobId将生成财务单据所需要的运单信息和代刷卡数据相关信息从数据库表PKP.T_SRV_ADD_ASYN_WAYBILL查询出来
	 */
	public List<AddWSCWaybillProcessEntity> queryInfoByJobId(String jobId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryInfoByJobId", jobId);
	}
	/**
	 * @author 350909    郭倩云
	 * 假如线程执行失败,根据jobId将相关数据的jobId更新成N/A
	 */
	public int updateJobIdToNA(WSCWaybillProcessVo wSCWaybillProcessVo) {
		return this.getSqlSession().update(NAMESPACE + "updateJobIdToNA", wSCWaybillProcessVo);
	}

}
