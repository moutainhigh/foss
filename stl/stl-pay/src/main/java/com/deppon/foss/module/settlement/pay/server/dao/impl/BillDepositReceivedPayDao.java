package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillingQueryRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StlBillDetailDto;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillDepositReceivedPayDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;

/**
 * 预收单dao
 * @author foss-pengzhen
 * @date 2012-11-19 下午4:28:20
 * @since
 * @version
 */
public class BillDepositReceivedPayDao  extends iBatis3DaoImpl implements IBillDepositReceivedPayDao {
	public static final String NAMESPACES = "foss.stl.BillDepositReceivedEntityDao.";
	/**
	 * 根据日期等参数查询对应的预收单信息
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDepositReceivedEntity> queryDepositReceivedParams(
			BillDepositReceivedPayDto billDepositReceivedPayDto,
			int start,int limit) {
		if(billDepositReceivedPayDto != null && billDepositReceivedPayDto.getStartBusinessDate()!=null
				&& billDepositReceivedPayDto.getEndBusinessDate()!=null) {
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACES
					+ "queryDepositReceivedParams", billDepositReceivedPayDto,rowBounds);
		}
		return null;
	}
	
	/**
	 * 根据日期等参数查询对应的预收单信息(不分页)
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDepositReceivedEntity> queryDepositReceivedParam(
			BillDepositReceivedPayDto billDepositReceivedPayDto) {
		if(billDepositReceivedPayDto != null && billDepositReceivedPayDto.getStartBusinessDate()!=null
				&& billDepositReceivedPayDto.getEndBusinessDate()!=null) {
			return this.getSqlSession().selectList(NAMESPACES
					+ "queryDepositReceivedParams", billDepositReceivedPayDto);
		}
		return null;
	}

	/**
	 * 根据日期等参数查询对应的预收单总条数
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	public Long queryDepositReceivedParamsCount(BillDepositReceivedPayDto billDepositReceivedPayDto){
		if(billDepositReceivedPayDto != null && billDepositReceivedPayDto.getStartBusinessDate()!=null
				&& billDepositReceivedPayDto.getEndBusinessDate()!=null) {
			return  (Long) this.getSqlSession().selectOne(NAMESPACES
					+ "queryDepositReceivedParamsCount", billDepositReceivedPayDto);
		}
		return null;
	}
	
	/**
	 * 根据预收单参数查询对应的预收单信息
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDepositReceivedEntity> queryDepositReceivedNos(
			List<String> depositReceivedNos,CurrentInfo currentInfo,
			int start,int limit) {
		if(CollectionUtils.isNotEmpty(depositReceivedNos)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("depositReceivedNos", depositReceivedNos);
			paramMap.put("empCode",currentInfo.getEmpCode());
			
			paramMap.put("isLeagueSaleDept",currentInfo.getUser().getUserName());//是否是合伙人
			paramMap.put("createOrgCode",currentInfo.getUser().getTitle());//当前用户所在部门
			
			RowBounds rowBounds = new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACES
					+ "queryDepositReceivedNos", paramMap,rowBounds);
		}
		return null;
	}
	
	/**
	 * 根据预收单参数查询对应的预收单总条数
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	public BillDepositReceivedPayDto queryDepositReceivedNosCount(BillDepositReceivedPayDto billDepositReceivedPayDto,CurrentInfo currentInfo){
		if(billDepositReceivedPayDto != null) {
			billDepositReceivedPayDto.setEmpCode(currentInfo.getEmpCode());
			return (BillDepositReceivedPayDto) this.getSqlSession().selectOne(NAMESPACES
					+ "queryDepositReceivedNosCount", billDepositReceivedPayDto);
		}
		return null;
	}

	/** 
	 * 根据付款单号获取预收单
	 * @author foss-qiaolifeng
	 * @date 2013-3-20 下午7:14:35
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillDepositReceivedPayDao#queryListByPaymentNo(com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillDepositReceivedEntity> queryListByPaymentNo(
			BillDepositReceivedPayDto dto) {
		// 查询预收单明细
		return this.getSqlSession().selectList(NAMESPACES+ "queryListByPaymentNo", dto);
	}

	/**
	 * 根据预收单参数查询对应的预收单总条数
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	public BillDepositReceivedPayDto queryDepositReceivedParamsCountAndAmount(BillDepositReceivedPayDto billDepositReceivedPayDto){
		
		if(billDepositReceivedPayDto != null && billDepositReceivedPayDto.getStartBusinessDate()!=null
				&& billDepositReceivedPayDto.getEndBusinessDate()!=null) {
			return (BillDepositReceivedPayDto) this.getSqlSession().selectOne(NAMESPACES
					+ "queryDepositReceivedParamsCountAndAmount", billDepositReceivedPayDto);
		}
		return null;
	}

	/**
	 * ptp监控查询合伙人预收单总记录数和总金额数
	 */
	@Override
	public StlBillDetailDto countDepositReceivedBills(
			BillingQueryRequestDto requestDto) {
		if (requestDto != null) {
			return (StlBillDetailDto) this.getSqlSession()
					.selectOne(NAMESPACES + "countDepositReceivedBills",
							requestDto);
		}
		return null;
	}
	
}
