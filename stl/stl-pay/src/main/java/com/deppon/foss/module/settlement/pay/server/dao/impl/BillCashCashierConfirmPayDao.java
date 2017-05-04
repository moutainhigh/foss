package com.deppon.foss.module.settlement.pay.server.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillCashCashierConfirmPayDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashCashierConfirmDto;
/**
 * 确认收银Dao
 * @author foss-pengzhen
 * @date 2012-12-13 下午2:59:19
 * @since
 * @version
 */
public class BillCashCashierConfirmPayDao extends iBatis3DaoImpl implements IBillCashCashierConfirmPayDao{

	/**
	 * 声明namespace
	 */
	public static final String NAMESPACES = "foss.stl.BillCashCashierConfirmDao.";
	
	/**
	 * 按日期参数查询单据信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillCashCashierConfirmDto> querybillCashCashierConfirmParams(BillCashCashierConfirmDto billCashCashierConfirmDto){
		//判空
		if( null != billCashCashierConfirmDto){
			//调用接口查询
			return this.getSqlSession().selectList(NAMESPACES + "querybillCashCashierConfirmParams",billCashCashierConfirmDto);
		}
		return null;
	}
	
	/**
	 * 按单号查询单据信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillCashCashierConfirmDto> querybillCashCashierConfirmNos(BillCashCashierConfirmDto billCashCashierConfirmDto){
		//判空
		if( null != billCashCashierConfirmDto){
			//调用接口查询
			return this.getSqlSession().selectList(NAMESPACES + "querybillCashCashierConfirmNos",billCashCashierConfirmDto);
		}
		return null;
	}
	
	/**
	 * 按来源单号查询单据信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillCashCashierConfirmDto> querybillCashCashierConfirmWayBillNos(BillCashCashierConfirmDto billCashCashierConfirmDto){
		//判空
		if( null != billCashCashierConfirmDto){
			//调用接口查询
			return this.getSqlSession().selectList(NAMESPACES + "querybillCashCashierConfirmWayBillNos",billCashCashierConfirmDto);
		}
		return null;
	}
	
	/**
	 * 按id号查询单据信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillCashCashierConfirmDto> querybillCashCashierConfirmIds(List<String> billCashCashierConfirmIds){
		//判空
		if( CollectionUtils.isNotEmpty(billCashCashierConfirmIds)){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("billCashCashierConfirmIds", billCashCashierConfirmIds);
			//调用接口查询
			return this.getSqlSession().selectList(NAMESPACES + "querybillCashCashierConfirmIds",paramMap);
		}
		return null;
	}
	
	/**
	 * 按日期参数查询预收单信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillCashCashierConfirmDto> queryDepositReceivedParams(
			BillCashCashierConfirmDto billCashCashierConfirmDto){
		//判空
		if(billCashCashierConfirmDto !=  null){
			//调用接口查询
			return this.getSqlSession().selectList(NAMESPACES 	+ "queryDepositReceivedPayParams",billCashCashierConfirmDto);
		}
		return null;
	}
			
	/**
	 * 按日期参数查询还款单信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillCashCashierConfirmDto> queryRepaymentPayParams(
			BillCashCashierConfirmDto billCashCashierConfirmDto){
		//判空
		if(billCashCashierConfirmDto !=  null){
			//调用接口查询
			return this.getSqlSession().selectList(NAMESPACES + "queryRepaymentPayParams",billCashCashierConfirmDto);
		}
		return null;
	}
	
	
	/**
	 * 按日期参数查询还款单信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BillCashCashierConfirmDto> queryCashCollectionPayParams(
			BillCashCashierConfirmDto billCashCashierConfirmDto){
		//判空
		if(billCashCashierConfirmDto !=  null){
			//查询
			return this.getSqlSession().selectList(NAMESPACES+ "queryCashCollectionPayParams",billCashCashierConfirmDto);
		}
		return null;
	}
	
	/**
	 * 查询运单号
	 * @author foss-pengzhen
	 * @date 2013-6-28 上午10:54:33
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillCashCashierConfirmDto> cashCashierConfirmDetailWaybillNo(String billNo,String writeoffType){
		//判空
		if(StringUtils.isNotEmpty(billNo)){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("billNo", billNo);
			paramMap.put("writeoffType", writeoffType);
			//查询
			return this.getSqlSession().selectList(NAMESPACES+ "cashCashierConfirmDetailWaybillNo",paramMap);
		}
		return null;
	}
	
	/**
	 * 批量查询运单号
	 * @author foss-pengzhen
	 * @date 2013-6-28 上午10:54:33
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillCashCashierConfirmDto> cashCashierConfirmDetailWaybillNos(BillCashCashierConfirmDto billCashCashierConfirmDto){
		//判空
		if(null != billCashCashierConfirmDto){
			//查询
			return this.getSqlSession().selectList(NAMESPACES+ "cashCashierConfirmDetailWaybillNos",billCashCashierConfirmDto);
		}
		return null;
	}

	/**
	 * 根据银联交易流水号查询
	 * @author 045738-foss-maojianqiang
	 * @date 2013-7-24 上午9:08:51
	 * @param billCashCashierConfirmDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BillCashCashierConfirmDto> querybillCashCashierConfirmBatchNos(
			BillCashCashierConfirmDto billCashCashierConfirmDto) {
		//判空
		if( null != billCashCashierConfirmDto){
			//调用接口查询
			return this.getSqlSession().selectList(NAMESPACES + "querybillCashCashierConfirmBatchNos",billCashCashierConfirmDto);
		}
		return null;
	}

    /**
     * 查询未收银确认的代收货款相关单据
     * @param billCashCashierConfirmDto
     * @return 单号list
     */
    @Override
    public List<String> queryUnconfirmedCodRelatedBill(BillCashCashierConfirmDto billCashCashierConfirmDto) {
        return getSqlSession().selectList(NAMESPACES + "queryUnconfirmedCodRelatedBill",billCashCashierConfirmDto);
    }
}
