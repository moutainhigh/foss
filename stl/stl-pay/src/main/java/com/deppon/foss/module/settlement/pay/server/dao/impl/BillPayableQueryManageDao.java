package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillPayableQueryManageDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageResultDto;
import com.deppon.foss.util.CollectionUtils;


/**
 * 应付单dao
 * @author 045738-foss-maojianqiang
 * @date 2012-11-19 下午1:46:15
 */
public class BillPayableQueryManageDao extends iBatis3DaoImpl implements IBillPayableQueryManageDao{
	/**
	 * 声明namespace
	 */
	private static final String NAMESPACE = "foss.stl.BillPayableEntityDao.";// 命名还款单空间
	
	/** 
	 * 根据日期查询应付单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-19 下午1:47:01
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillPayableQueryManageDao#queryBillPayableByDate(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto)
	 */
	@SuppressWarnings("unchecked")
	public List<BillPayableEntity> queryBillPayableByDate(RowBounds rb,
			BillPayableManageDto dto) {
		//进行查询返回结果集
		return this.getSqlSession().selectList(NAMESPACE+"queryBillPayableByDate",dto,rb);
	}

	/**
	 * 计算日期查询的总条数和总金额
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-19 下午7:37:36
	 */
	public BillPayableManageResultDto countBillPayableByDate(
			BillPayableManageDto dto) {
		return (BillPayableManageResultDto) this.getSqlSession().selectOne(NAMESPACE+"countBillPayableByDate",dto);
	}

	/** 
	 * 根据付款单号和付款单明细来源单号查询应付单
	 * @author foss-qiaolifeng
	 * @date 2013-3-20 下午6:30:56
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IBillPayableQueryManageDao#queryListByPaymentNo(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryListByPaymentNo(BillPayableManageDto dto) {
		
		//进行查询返回结果集
		return this.getSqlSession().selectList(NAMESPACE+"queryListByPaymentNo",dto);
	}
	

	/**
	 * 根据应付单号查询应付单信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BillPayableEntity queryPayableByPayableNo(String payableNo){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("payableNo", payableNo);
		List<BillPayableEntity> billPayableEntities = getSqlSession().selectList(NAMESPACE + "queryPayableByPayableNo", paramMap);
		if(CollectionUtils.isEmpty(billPayableEntities)){
			throw new SettlementException("查询应付单失败");
		}
		return billPayableEntities.get(0);
	}
}
