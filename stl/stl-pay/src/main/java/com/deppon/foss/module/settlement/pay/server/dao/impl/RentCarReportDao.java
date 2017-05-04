package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.IRentCarReportDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 045738
 * 临时租车报表Dao
 */
public class RentCarReportDao extends iBatis3DaoImpl implements
		IRentCarReportDao {
	/**
	 * 定义命名空间
	 */
	private static final String NAMESPACES = "foss.stl.RentCarForPayReport.";
	
	/**
	 * 功能：查询临时租车报表
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-27
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RentCarReportDto> queryRentCarReport(RentCarReportDto dto, int start, int limit) {
		//如果当前登录部门为空，则抛出异常
		if(StringUtils.isEmpty(dto.getCurrentDeptCode())){
			throw new SettlementException("当前登录部门为空，请重新登录！");
		}
		//分页信息
		RowBounds rb = new RowBounds(start,limit);
		//设置只能查询有效的租车记录
		dto.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACES+"selectByParams",dto , rb);
	}
	
	/**
	 * 功能：查询临时租车报表
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-27
	 * @return
	 */
	public RentCarReportDto queryRentCarReportCount(RentCarReportDto dto) {
		//如果当前登录部门为空，则抛出异常
		if(StringUtils.isEmpty(dto.getCurrentDeptCode())){
			throw new SettlementException("当前登录部门为空，请重新登录！");
		}
		//设置只能查询有效的租车记录
		dto.setActive(FossConstants.ACTIVE);
		return (RentCarReportDto) this.getSqlSession().selectOne(NAMESPACES+"selectCountByParams",dto );
	}

	/**
	 * 功能：查询临时租车报表
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-27
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RentCarReportDto> queryRentCarReportForExport(
			RentCarReportDto dto) {
		//如果当前登录部门为空，则抛出异常
		if(StringUtils.isEmpty(dto.getCurrentDeptCode())){
			throw new SettlementException("当前登录部门为空，请重新登录！");
		}
		//设置只能查询有效的租车记录
		dto.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACES+"selectByParams",dto);
	}
	
	/**
	 * 功能：查询已经预提的租车记录
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-23
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RentCarReportDto> queryWithholdingedRentCar(RentCarReportDto dto){
		//校验参数
		if(dto==null || CollectionUtils.isEmpty(dto.getBillNos())){
			throw new SettlementException("传入的查询参数为空！");
		}
		return this.getSqlSession().selectList(NAMESPACES+"queryWithholdingedRentCar",dto);
	}

	/**
	 * 功能：查询临时租车记录  提供给cubc查询使用
	 * 需求DN201704100011
	 * @author 378375
	 * @date 2017年4月7日 16:49:12
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RentCarReportDto> queryRentCarReportForCUBC(RentCarReportDto dto) {
		//设置只能查询有效的租车记录
		dto.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACES+"selectByParamsForCUBC",dto);
	}
	
}
