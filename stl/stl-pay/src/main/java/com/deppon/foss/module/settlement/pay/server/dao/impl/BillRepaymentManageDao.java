package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageResultDto;


/**
 * 还款单Dao
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午9:04:00
 */
public class BillRepaymentManageDao extends iBatis3DaoImpl implements  IBillRepaymentManageDao{
	
	private static final String NAMESPACE = "foss.stl.BillRepaymentEntityDao.";// 命名还款单空间


	/**
	 * 按业务日期查询还款单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-7 下午6:18:02
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao#queryBillBusinessDateRepayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentManageDto> queryBillBusinessDateRepayment(
			BillRepaymentManageDto billRepaymentManageDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "selectBillRepayBunessDateParams",
				billRepaymentManageDto, rowBounds);
	}

	/**
	 * 按业务日期查询还款单总条数
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-7 下午6:18:02
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao#queryCountBillBusinessDateRepayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto)
	 */
	@Override
	public BillRepaymentManageResultDto queryCountBillBusinessDateRepayment(
			BillRepaymentManageDto billRepayDto) {
		return (BillRepaymentManageResultDto) this.getSqlSession().selectOne(
				NAMESPACE + "selectCountBillRepayBusiDateParams", billRepayDto);
	}

	/**
	 * 按记账日期查询还款单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-2 下午4:39:30
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao#queryBillRepayment()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentManageDto> queryBillAccountDateRepayment(
			BillRepaymentManageDto billRepaymentManageDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "selectBillRepayAccountDateParams",
				billRepaymentManageDto, rowBounds);
	}

	/**
	 * 按记账日期查询还款单总条数
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-4 下午7:20:17
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao#queryCountBillRepayment(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto)
	 */
	@Override
	public BillRepaymentManageResultDto queryCountBillAccountDateRepayment(
			BillRepaymentManageDto billRepayDto) {
		return (BillRepaymentManageResultDto) this.getSqlSession().selectOne(
				NAMESPACE + "selectCountBillRepayAccountDateParams",
				billRepayDto);
	}

	/**
	 * 根据还款单查询还款单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-9 下午4:05:27
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao#queryBillRepaymentByNos(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentManageDto> queryBillRepaymentByNos(
			BillRepaymentManageDto billRepaymentManageDto) {
		return this.getSqlSession().selectList(
				NAMESPACE + "selectRepaymentByNos", billRepaymentManageDto);
	}

	/**
	 * 根据运单查询运单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-9 下午4:05:27
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao#queryBillRepaymentByWbNos(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentManageDto> queryBillRepaymentByWbNos(
			BillRepaymentManageDto billRepaymentManageDto) {

		return this.getSqlSession().selectList(
				NAMESPACE + "selectRepaymentByWblNos", billRepaymentManageDto);
	}

	/**
	 * 根据对账单查询还款单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-9 下午4:05:27
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao#queryBillRepaymentByStNos(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto)
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentManageDto> queryBillRepaymentByStNos(
			BillRepaymentManageDto billRepaymentManageDto) {
		return this.getSqlSession().selectList(
				NAMESPACE + "selectRepaymentByStNOs", billRepaymentManageDto);
	}


	/** 
	 * 根据还款单号查询是否存在反核销的红单
	 * @author foss-qiaolifeng
	 * @date 2012-11-12 上午10:08:48
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao#queryRedBackBillByNo(java.lang.String, java.lang.String)
	 */
	@Override
	public BillRepaymentEntity queryRebackBillByNo(String repaymentNo,
			String active) {
		
		//将参数分装到map
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isNotEmpty(repaymentNo)){
			map.put("repaymentNo", repaymentNo);
		}
		
		if(StringUtils.isNotEmpty(active)){
			map.put("active", active);
		}
			
		//查询数据
		return (BillRepaymentEntity) this.getSqlSession().selectOne(
				NAMESPACE + "selectReBackRepaymentByNo", map);
	}
	
	
	

    /** 
     * 根据传入的一到多个来源单号，获取一到多条还款单信息 
     * @author 095793-foss-LiQin
     * @date 2013-1-24 上午9:09:24
     * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao#queryBySourceBillNOs(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto)
     */

	@SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentManageDto> queryBySourceBillNOs(
			BillRepaymentManageDto billRepayDto) {
    	
		return this.getSqlSession().selectList(
				NAMESPACE + "selectRepaymentBySourceBillNos", billRepayDto);
	}
	
    /** 
     * 根据传入的一到多个来源单号，获取一到多条还款单信息 
     * @author 095793-foss-LiQin
     * @date 2013-1-24 上午9:09:24
     * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao#queryBySourceBillNOs(com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto)
     */

	@SuppressWarnings("unchecked")
	@Override
	public List<BillRepaymentManageDto> queryByBatchBillNOs(
			BillRepaymentManageDto billRepayDto) {
    	
		return this.getSqlSession().selectList(
				NAMESPACE + "selectRepaymentByBatchBillNos", billRepayDto);
	}
	

}
