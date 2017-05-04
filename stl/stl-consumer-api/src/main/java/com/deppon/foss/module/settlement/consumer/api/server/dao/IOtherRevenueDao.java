package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.dto.AbnormalBillAmountCalculatedDto;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto;

/**
 * 小票Dao
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-11-20 上午9:59:17
 */
public interface IOtherRevenueDao {

	/**
	 * 新加小票记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:48:42
	 */
	int addOtherRevenue(OtherRevenueDto otherRevenueDto);

	/**
	 * 作废小票
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:48:56
	 */
	int cancelOtherRevenue(OtherRevenueDto otherRevenueDto);

	/**
	 * 按日期查询小票记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	List<OtherRevenueEntity> queryOtherRevenueByDate(
			OtherRevenueDto otherRevenueDto, int start, int limit);
	
	/**
	 * 按时间查询打印小票记录
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	public List<OtherRevenueEntity> queryOtherRevenueByDate(OtherRevenueDto otherRevenueDto);
	
	/**
	 * 按日期查询小票记录总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:20
	 */
	OtherRevenueDto countQueryOtherRevenueByDate(OtherRevenueDto otherRevenueDto);
	
	/**
	 * 按小票号查询小票记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	List<OtherRevenueEntity> queryOtherRevenueByOtherRevenueNos(
			OtherRevenueDto otherRevenueDto, int start, int limit);

	/**
	 * 按小票号查询小票记录总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:20
	 */
	OtherRevenueDto countQueryOtherRevenueByOtherRevenueNos(OtherRevenueDto otherRevenueDto);
	
	/**
	 * 查询小票单号是否已存在
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:20
	 */
	int queryOtherRevenueNo(String otherRevenueNo);
	
	/**
	 * 按小票ID查询小票记录
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	OtherRevenueEntity queryOtherRevenueById(String id);
	
	/**
	 *  按小票号查询小票记录
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-23 上午10:50:27
	 */
	List<OtherRevenueEntity> queryOtherRevenueByOtherRevenueNos(List<String> otherRevenueNos,String active);
	
	/**
	 * 根据运单号查询小票记录
	 *
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-10-10 上午8:43:01 
	 * @param waybillNO
	 * @return List<OtherRevenueEntity>
	 */
	List<OtherRevenueEntity> queryOtherRevenueByWayBillNO(String waybillNO);
	/**
	 * @Function: com.deppon.foss.module.settlement.consumer.api.server.dao.IOtherRevenueDao.queryOtherRevenueByWayBillNOAndInvoiceCategory
	 * @Description:根据运单号和发票类别 发票产生部门  查询相应的小票信息
	 * @param waybillNO
	 * @return
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-20 上午9:18:43
	 */
	List<OtherRevenueEntity> queryOtherRevenueByWayBillNOAndInvoiceCategory(String waybillNO,CurrentInfo currentInfo);

	/** 347069
	 * 根据小票号,查询有效的EH类型小票金额
	 * @param dto
	 * @return
	 */
	public AbnormalBillAmountCalculatedDto queryOtherRevenueByRevenueNo(AbnormalBillAmountCalculatedDto dto);

	/**
	 * 根据运单号查询小票记录

	 * @author 045738  
	 * @date 2014-08-27 上午8:43:01 
	 * @param waybillNO
	 * @return List<OtherRevenueEntity>
	 */
	public  List<OtherRevenueDto> isExistRentCarOtherNos(String revenueNo);
}
