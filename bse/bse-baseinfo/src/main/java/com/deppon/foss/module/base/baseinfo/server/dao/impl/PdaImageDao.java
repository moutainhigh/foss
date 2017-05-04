package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPdaImageDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ElectronicTicketEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 刷卡电子小票地址
 * @author 307196
 *
 */
public class PdaImageDao extends SqlSessionDaoSupport implements IPdaImageDao{
//	private static final Logger LOGGER = LoggerFactory.getLogger(PdaImageDao.class);
    private static final String NAMESPACE ="foss.bse.bse-baseinfo.electronicTicketEntity.";
	@Override
	public int pdaAddImage(List<ElectronicTicketEntity> entitys) {
		
		int result = getSqlSession().insert(NAMESPACE+"addElectronicTicket", entitys);
		
		return  0 < result ? FossConstants.SUCCESS : FossConstants.FAILURE;
		
	}
	/**
	 *作废 刷卡电子小票地址
	 * 
	 *
	 */
	@Override
	public int deleteElectronicTicket(ElectronicTicketEntity entity) {
		if (entity == null ) {
			return FossConstants.FAILURE;
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("electronicTicketEntity", entity);
		map.put("active", "Y");
		int result = getSqlSession().update(NAMESPACE+"deletebyWayBillNoorSerialNo", map);
		
		return  0 < result ? FossConstants.SUCCESS : FossConstants.FAILURE;
	}
	/**根据运单号查询总条数，用于分页
	 * 
	 * 
	 */
	@Override
	public long queryCountByWaybn(List<String> wayBillNos) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wayBillNos", wayBillNos);
		map.put("active", "Y");
		return (Long)getSqlSession().selectOne(NAMESPACE + "queryByEntityCount", map);
	}
	/**根据交易流水号查询总条数，用于分页
	 * 
	 * 
	 */
	@Override
	public long queryCountBySerialN(List<String> serialNos) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serialNos", serialNos);
		map.put("active", "Y");
		return (Long)getSqlSession().selectOne(NAMESPACE + "queryByEntityCount", map);
	}
	/**
	 * 根据交易流水号查询刷卡电子小票地址
	 * 
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ElectronicTicketEntity> queryElectronicTicketListBySerialNumber(List<String> serialNos,int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serialNos", serialNos);
		map.put("active", "Y");
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return getSqlSession().selectList(NAMESPACE+"queryElectronicTicketListNumber",map,rowBounds);
	}
	/**
	 * 根据运单号查询刷卡电子小票地址
	 * 
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ElectronicTicketEntity> queryElectronicTicketListByWaybillNumber(List<String> wayBillNos,int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wayBillNos", wayBillNos);
		map.put("active", "Y");
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return getSqlSession().selectList(NAMESPACE+"queryElectronicTicketListNumber",map,rowBounds);
	}
	
	/**
	 * 根据交易流水号精确查询
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
    @Override
    public List<ElectronicTicketEntity> queryElectronicTicketBySerialNo(ElectronicTicketEntity entity) {
		if (entity == null ) {
			return null;
		}
		if(StringUtils.isBlank(entity.getSerialNo())){
			return null;
		}
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryElectronicTicketBySerialNo", entity);
    } 
}