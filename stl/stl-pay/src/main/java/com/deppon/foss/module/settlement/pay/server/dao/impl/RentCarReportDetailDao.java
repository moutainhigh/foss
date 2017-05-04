package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IRentCarReportDetailDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDetailDto;
import com.deppon.foss.util.DateUtils;

/**
 * 租车明细接口实现
 * @author foss-maojianqiang
 * @date 2014-07-21 上午11:16:55
 */
public class RentCarReportDetailDao extends iBatis3DaoImpl implements IRentCarReportDetailDao {

	public static final String NAMESPACES = "foss.stl.RentCarReportDetail.";
	
	/**
	 * 按输入参数查询分页租车明细
	 * 
	 * @author foss-qiaolifeng
	 * @date 2014-08-05 上午11:15:58
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RentCarReportDetailDto> queryRentCarReportDetailDto(
			RentCarReportDetailDto rentCarReportDetailDto, int start, int limit) {
		//判断Dto中的查询类型
		if(rentCarReportDetailDto.getQueryType().equalsIgnoreCase("td")){
	    //得到查询终止日期的时间
		Date queryEndDate = DateUtils.addDayToDate(rentCarReportDetailDto.getQueryDate(), 1);
		//设置查询的终止日期
		rentCarReportDetailDto.setQueryEndDate(queryEndDate);
		}
		//通过分页查询租车明细
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回分页查询出租车明细的数据集合
		return this.getSqlSession().selectList( NAMESPACES + "selectAll", rentCarReportDetailDto, rowBounds);
	}

	/**
	 * 导出查询数据
	 * @author foss-qiaolifeng
	 * @date 2014-08-05 上午11:15:58
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RentCarReportDetailDto> exportQueryRentCarReportDetailDto(
			RentCarReportDetailDto rentCarReportDetailDto) {
		//判断Dto中的查询类型
		if(rentCarReportDetailDto.getQueryType().equalsIgnoreCase("td")){
	    //得到终止日期的时间
		Date queryEndDate = DateUtils.addDayToDate(rentCarReportDetailDto.getQueryDate(), 1);
		//设置查询的终止日期
		rentCarReportDetailDto.setQueryEndDate(queryEndDate);
		}
		//返回分页查询出租车明细的数据集合
		return this.getSqlSession().selectList( NAMESPACES + "selectAll", rentCarReportDetailDto);
	}

	/**
	 * 查询租车明细的总数据条数
	 * 
	 * @author foss-qiaolifeng
	 * @date 2014-08-05 上午11:15:58
	 */
	@Override
	public int queryRentCarReportCount(
			RentCarReportDetailDto rentCarReportDetailDto) {
		//判断Dto中的查询类型
		if(rentCarReportDetailDto.getQueryType().equalsIgnoreCase("td")){	
	    //得到终止日期的时间
		Date queryEndDate = DateUtils.addDayToDate(rentCarReportDetailDto.getQueryDate(), 1);
		//设置查询的终止日期
		rentCarReportDetailDto.setQueryEndDate(queryEndDate);
		}
		//返回分页查询出租车明细的数据的总条数
		return (Integer) this.getSqlSession().selectOne( NAMESPACES + "selectAllCount", rentCarReportDetailDto);
	}

}
