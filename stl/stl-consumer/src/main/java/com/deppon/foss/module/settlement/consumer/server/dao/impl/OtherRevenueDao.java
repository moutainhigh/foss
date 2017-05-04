package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.AbnormalBillAmountCalculatedDto;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IOtherRevenueDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 小票DAO
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-11-20 上午9:45:07
 */
public class OtherRevenueDao extends iBatis3DaoImpl implements IOtherRevenueDao {

	private static final String NAMESPACE = "foss.stl.OtherRevenueEntityDao.";// 小票命名空间

	/**
	 * 新加小票记录
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:48:42
	 */
	public int addOtherRevenue(OtherRevenueDto otherRevenueDto) {
		return getSqlSession().insert(NAMESPACE+"addOtherRevenue", otherRevenueDto);
	}

	/**
	 * 作废小票
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:48:56
	 */
	public int cancelOtherRevenue(OtherRevenueDto otherRevenueDto) {
		return getSqlSession().insert(NAMESPACE+"cancelOtherRevenue", otherRevenueDto);
	}

	/**
	 * 按时间查询小票记录
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	@SuppressWarnings("unchecked")
	public List<OtherRevenueEntity> queryOtherRevenueByDate(OtherRevenueDto otherRevenueDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryOtherRevenueByDate", otherRevenueDto, rowBounds);
	}
	
	/**
	 * 按时间查询打印小票记录
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	@SuppressWarnings("unchecked")
	public List<OtherRevenueEntity> queryOtherRevenueByDate(OtherRevenueDto otherRevenueDto) {
		return getSqlSession().selectList(NAMESPACE+"queryOtherRevenueByDate", otherRevenueDto);
	}
	
	/**
	 * 按时间查询小票记录总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:20
	 */
	public OtherRevenueDto countQueryOtherRevenueByDate(OtherRevenueDto otherRevenueDto) {
		return (OtherRevenueDto) getSqlSession().selectOne(NAMESPACE+"queryOtherRevenueCountByDate", otherRevenueDto);
	}
	
	/**
	 * 按小票单号查询小票记录
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	@SuppressWarnings("unchecked")
	public List<OtherRevenueEntity> queryOtherRevenueByOtherRevenueNos(OtherRevenueDto otherRevenueDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryOtherRevenueByOtherRevenueNos", otherRevenueDto, rowBounds);
	}
	/**
	 * 347069
	 * 按小票单号查询小票记录
	 * @param no
	 * @return
	 */
	public AbnormalBillAmountCalculatedDto queryOtherRevenueByRevenueNo(AbnormalBillAmountCalculatedDto dto){
		return (AbnormalBillAmountCalculatedDto) getSqlSession().selectOne(NAMESPACE+"queryOtherRevenueByRevenueNo", dto);
	}
	
	/**
	 * 按小票号查询小票记录
	 * @otherRevenueNos 小票单号集合
	 * @active 是否有效
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	@SuppressWarnings("unchecked")
	public List<OtherRevenueEntity> queryOtherRevenueByOtherRevenueNos(List<String> otherRevenueNos,String active) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (CollectionUtils.isNotEmpty(otherRevenueNos)) {
			map.put("otherRevenueNos",otherRevenueNos);
			if(StringUtils.isNotEmpty(active)){
				map.put("active",active);
			}
			return this.getSqlSession().selectList(NAMESPACE + "queryByOtherRevenueNosWithActive", map);
		}
		return null;
	}
	
	/**
	 * 按小票ID查询小票记录
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	public OtherRevenueEntity queryOtherRevenueById(String id) {
		return (OtherRevenueEntity) this.getSqlSession().selectOne(NAMESPACE + "queryOtherRevenueById", id);
	}
	/**
	 * 按小票查询小票记录总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:20
	 */
	public OtherRevenueDto countQueryOtherRevenueByOtherRevenueNos(OtherRevenueDto otherRevenueDto) {
		return (OtherRevenueDto) getSqlSession().selectOne(NAMESPACE+"queryOtherRevenueCountByOtherRevenueNos", otherRevenueDto);
	}
	
	/**
	 * 查询小票单号是否已存在
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:20
	 */
	public int queryOtherRevenueNo(String otherRevenueNo) {
		return  (Integer) getSqlSession().selectOne(NAMESPACE+"queryOtherRevenueNo", otherRevenueNo);
	}
	

	/**
	 * 根据运单号查询小票记录
	 *
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-10-10 上午8:43:01 
	 * @param waybillNO
	 * @return List<OtherRevenueEntity>
	 */
	@SuppressWarnings("unchecked")
	public List<OtherRevenueEntity> queryOtherRevenueByWayBillNO(
			String waybillNO) {
		
		Map<String ,String> map = new HashMap<String ,String>();
		map.put("waybillNO", waybillNO);
		map.put("active", FossConstants.ACTIVE);
		
		return getSqlSession().selectList(NAMESPACE+"queryOtherRevenueByWayBillNO" ,map);
	}
	/**
	 * @Function: com.deppon.foss.module.settlement.consumer.api.server.dao.IOtherRevenueDao.queryOtherRevenueByWayBillNOAndInvoiceCategory
	 * @Description:根据运单号和发票类别查询相应的小票信息,主要用于查询结清货款产生的保管费小票信息
	 * @param waybillNO
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-20 上午9:18:43
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OtherRevenueEntity> queryOtherRevenueByWayBillNOAndInvoiceCategory(String waybillNO,CurrentInfo currentInfo) {
		Map<String ,String> map = new HashMap<String ,String>();
		map.put("waybillNO", waybillNO);
		map.put("active", FossConstants.ACTIVE);
	    map.put("invoiceCategory",SettlementDictionaryConstants.SETTLEMENT_INVOICE_CATEGORY);
		//查询登录部门产生的小票记录
	    if(currentInfo!=null){
			map.put("currentdeptcode",currentInfo.getCurrentDeptCode());
		}
		return getSqlSession().selectList(NAMESPACE+"queryOtherRevenueByWayBillNO" ,map);
	}
	
	/**
	 * 根据运单号查询小票记录
	 *
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-10-10 上午8:43:01 
	 * @param waybillNO
	 * @return List<OtherRevenueEntity>
	 */
	@SuppressWarnings("unchecked")
	public List<OtherRevenueDto> isExistRentCarOtherNos(
			String revenueNo) {
		Map<String ,String> map = new HashMap<String ,String>();
		map.put("revenueNo", revenueNo);
		return this.getSqlSession().selectList(NAMESPACE+"isExistRentCarOtherNos" ,map);
	}
}
