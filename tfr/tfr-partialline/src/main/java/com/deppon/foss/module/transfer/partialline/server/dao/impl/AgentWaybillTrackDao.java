package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillNoRequestDto;
import com.deppon.foss.module.transfer.common.api.shared.vo.AgentWaybillExceptionDto;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IAgentWaybillTrackDao;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.AgentWaybillNoEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.AgentWaybillTrackEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InternationalTrackingEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExpressPushInfoDto;

public class AgentWaybillTrackDao extends iBatis3DaoImpl implements IAgentWaybillTrackDao {
	
	private static String prefix = "foss.partialline.agentWaybillTrackMapper.";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AgentWaybillNoEntity> queryWaybillNo(int count) {
		RowBounds rowBounds = new RowBounds(0, count);
		return this.getSqlSession().selectList(prefix + "queryWaybillNos",rowBounds);
	}

	@Override
	public int addOneTrack(AgentWaybillTrackEntity entity) {
		return this.getSqlSession().insert(prefix + "addOneTrack", entity);
	}

	@Override
	public int updateWaybillNoInfo(AgentWaybillNoEntity waybill) {
		return this.getSqlSession().update(prefix + "updateWaybillNoInfo", waybill);
	}
	
	@Override
	public int addWaybillNo(AgentWaybillNoEntity entity) {
		return this.getSqlSession().insert(prefix + "addWaybillNo", entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentWaybillNoRequestDto> queryAgentWaybillNoList(Date startDate) {
		return this.getSqlSession().selectList(prefix + "queryAgentWaybillNoList", startDate);
	}

	@Override
	public int updatePrintWaybillInfo(String agentWaybillNo) {
		return this.getSqlSession().update(prefix + "updatePrintWaybillInfo", agentWaybillNo);
	}

	@Override
	public int addExceptionInfo(AgentWaybillExceptionDto dto) {
		return this.getSqlSession().insert(prefix + "addExceptionInfo", dto);
	}
	@Override
	public int updatePrintWaybillInfoByAgentInfo(String agentWaybillNo,String kd100CompanyCode) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("agentWaybillNo", agentWaybillNo);
		map.put("kd100CompanyCode", kd100CompanyCode);
		return this.getSqlSession().update(prefix+ "updatePrintWaybillInfoByAgentInfo", map);
	}
	/**
	 * @author gongjp
	 * @date 2015年10月19日 上午10:24:34
	 * @function 保存异常信息
	 * @param expressPushInfoDto
	 */
	@Override
	public int savePushExpressInfo(ExpressPushInfoDto expressPushInfoDto){
		 return this.getSqlSession().insert(prefix + "savePushExpressInfo", expressPushInfoDto);
	}
	/**
	 * 根据运单号和拒收原因编号查询
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ExpressPushInfoDto> queryPushExpressListByWaybillNo(
			String waybillNo, String reasonCode) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		map.put("reasonCode", reasonCode);
		return this.getSqlSession().selectList(prefix+"queryPushExpressListByWaybillNo",map);
	}

	/**
	 * @author alfred
	 * 插入国际外发轨迹
	 */
	@Override
	public int addInternationTrackings(InternationalTrackingEntity entity) {
		return this.getSqlSession().insert(prefix + "addInternationTrack", entity);
	}

	/**
	 * @author alfred
	 * 插入国际外发轨迹
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InternationalTrackingEntity> queryInterTrackingsEntity(
			String waybillNo) {
		return this.getSqlSession().selectList(prefix+"queryInterTrackings",waybillNo);
	}

	/**
	 * @author 352203
	 * 删除轨迹信息
	 */
	@Override
	public int deleteInternationTrackings(String waybillNo) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete(prefix+"deleteInterTrackings",waybillNo);
	}
}
