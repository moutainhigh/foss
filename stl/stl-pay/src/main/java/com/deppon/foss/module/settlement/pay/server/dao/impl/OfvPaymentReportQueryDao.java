package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IOfvPaymentReportQueryDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportResultDto;

/**
 * 外请车付款报表Dao实现
 * 
 * @author foss-zhangxiaohui
 * @date Dec 20, 2012 5:12:12 PM
 */
public class OfvPaymentReportQueryDao extends iBatis3DaoImpl implements IOfvPaymentReportQueryDao {

	/**
	 * 命名空间路径
	 */
	private static final String NAMESPACES ="foss.stl.OfvPaymentReportDao.";
	
	/**
	 * 外请车付款报表Dao实现--导出的时候做数据源
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 5:15:57 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OfvPaymentReportResultDto> queryOfvPaymentReportByDate(OfvPaymentReportQueryDto dto) {
		//返回查询结果，返回Dto的List
		return (List<OfvPaymentReportResultDto>)this.getSqlSession().selectList(NAMESPACES + "queryOfvPaymentReport",dto);
	}

	/**
	 * 查询分页条件下总的数据条数
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 5:18:57 PM
	 */
	@Override
	public OfvPaymentReportResultDto queryOfvPaymentReportTotalRecordsByDate(OfvPaymentReportQueryDto dto) {
		//返回查询结果
		return (OfvPaymentReportResultDto) this.getSqlSession().selectOne(NAMESPACES + "queryTotalRecords",dto);
	}
	
	/** 
	 * 查询分页条件下总的数据金额
	 * @author foss-qiaolifeng
	 * @date 2013-4-24 下午2:10:22
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IOfvPaymentReportQueryDao#queryOfvPaymentReportTotalAmountByDate(com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportQueryDto)
	 */
	@Override
	public OfvPaymentReportResultDto queryOfvPaymentReportTotalAmountByDate(
			OfvPaymentReportQueryDto dto) {
		//返回查询结果
				return (OfvPaymentReportResultDto) this.getSqlSession().selectOne(NAMESPACES + "queryTotalAmount",dto);
	}

	/**
	 * 分页查询的查询实现
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 5:29:57 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OfvPaymentReportResultDto> queryOfvPaymentReportByDateAndPage(int offset,int start,OfvPaymentReportQueryDto dto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(offset,start);
		//返回查询结果
		return (List<OfvPaymentReportResultDto>)this.getSqlSession().selectList(NAMESPACES + "queryOfvPaymentReport",dto,rb);
	}

	/** 
	 * 外请车付款报表Dao接口--根据工作流号查询外请车付款报表
	 * @author foss-qiaolifeng
	 * @date 2013-4-1 上午9:17:19
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IOfvPaymentReportQueryDao#queryOfvPaymentReportByWorkFlowNo(com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OfvPaymentReportResultDto> queryOfvPaymentReportByWorkFlowNo(
			OfvPaymentReportQueryDto dto) {
		//返回查询结果，返回Dto的List
		return (List<OfvPaymentReportResultDto>)this.getSqlSession().selectList(NAMESPACES + "queryOfvPaymentReportByWorkFlowNo",dto);
	}

	@Override
	public OfvPaymentReportResultDto queryOfvPaymentReportTotalRecordsByContractCodesNo(
			OfvPaymentReportQueryDto dto) {
		return (OfvPaymentReportResultDto)this.getSqlSession().selectOne(NAMESPACES + "queryOfvPaymentReportTotalRecordsByContractCodesNo",dto);
	}

	@Override
	public OfvPaymentReportResultDto queryOfvPaymentReportTotalAmountByContractCodesNo(
			OfvPaymentReportQueryDto dto) {
		return (OfvPaymentReportResultDto)this.getSqlSession().selectOne(NAMESPACES + "queryOfvPaymentReportTotalAmountByContractCodesNo",dto);
	}

	@Override
	public List<OfvPaymentReportResultDto> queryOfvPaymentReportByContractCodesNoAndPage(
			int offset, int start, OfvPaymentReportQueryDto dto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(offset,start);
		return (List<OfvPaymentReportResultDto>)this.getSqlSession().selectList(NAMESPACES + "queryOfvPaymentReportByContractCodesNo",dto,rb);
	}

	@Override
	public List<OfvPaymentReportResultDto> queryOfvPaymentReportByContractCodesNo(
			OfvPaymentReportQueryDto dto) {
		return (List<OfvPaymentReportResultDto>)this.getSqlSession().selectList(NAMESPACES + "queryOfvPaymentReportByContractCodesNo",dto);

	}

	@Override
	public OfvPaymentReportResultDto queryOfvPaymentReportTotalAmountByDate2(
			OfvPaymentReportQueryDto dto) {
		return (OfvPaymentReportResultDto) this.getSqlSession().selectOne(NAMESPACES + "queryTotalAmount2",dto);
	}

	@Override
	public OfvPaymentReportResultDto queryOfvPaymentReportTotalRecordsByDate2(
			OfvPaymentReportQueryDto dto) {
		return (OfvPaymentReportResultDto) this.getSqlSession().selectOne(NAMESPACES + "queryTotalRecords2",dto);
	}

	@Override
	public List<OfvPaymentReportResultDto> queryOfvPaymentReportByDateAndPage2(
			int offset, int start, OfvPaymentReportQueryDto dto) {
		//设置分页大小及起始页
		RowBounds rb = new RowBounds(offset,start);
		return (List<OfvPaymentReportResultDto>)this.getSqlSession().selectList(NAMESPACES + "queryOfvPaymentReport2",dto,rb);
	}

	@Override
	public List<OfvPaymentReportResultDto> queryOfvPaymentReportByDate2(
			OfvPaymentReportQueryDto dto) {
		return (List<OfvPaymentReportResultDto>)this.getSqlSession().selectList(NAMESPACES + "queryOfvPaymentReport2",dto);
	}
}
