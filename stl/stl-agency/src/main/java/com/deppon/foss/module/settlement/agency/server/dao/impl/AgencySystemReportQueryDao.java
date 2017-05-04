package com.deppon.foss.module.settlement.agency.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.agency.api.server.dao.IAgencySystemReportQueryDao;
import com.deppon.foss.module.settlement.agency.api.shared.dto.AgencySystemReportQueryDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.AgencySystemReportResultDto;

/**
 * 偏线全盘报表Dao接口实现
 * 
 * @author foss-zhangxiaohui
 * @date Dec 25, 2012 3:44:50 PM
 */
public class AgencySystemReportQueryDao extends iBatis3DaoImpl implements IAgencySystemReportQueryDao {

	/**
	 * 命名空间路径
	 */
	public static final String NAMESPACES = "foss.stl.AgencySystemReportQueryDao.";
	
	/**
	 * 根据运单单号查询偏线全盘报表
	 * 
	 * @author foss-zhangxiaohui 
	 * @param 
	 * @date Dec 26, 2012 4:39:43 PM
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AgencySystemReportResultDto> querAgencySystemReportByWayBillNo(AgencySystemReportQueryDto dto){
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACES + "queryByWayBillNo",dto);
	}
	
	/** 
	 * 根据Dto查询偏线全盘报表
	 * 
	 * @author foss-zhangxiaohui
	 * @param 
	 * @date Dec 25, 2012 3:45:23 PM
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AgencySystemReportResultDto> querAgencySystemReportByDto(int offset, int start,
			AgencySystemReportQueryDto dto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(offset, start);	
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACES + "queryByDto",dto,rb);
	}

	/** 
	 * 根据Dto查询总记录条数
	 * 
	 * @author foss-zhangxiaohui
	 * @param 
	 * @date Dec 25, 2012 3:45:48 PM
	 * @return 
	 */
	@Override
	public AgencySystemReportResultDto queryTotalRecordsInDBByDto(
			AgencySystemReportQueryDto dto) {
		return (AgencySystemReportResultDto) this.getSqlSession().selectOne(NAMESPACES + "queryTotalAmountByDto",dto);
	}
}
