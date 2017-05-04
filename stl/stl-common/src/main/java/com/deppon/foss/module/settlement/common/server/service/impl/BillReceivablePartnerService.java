package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillReceivablePartnerEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivablePartnerService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillingQueryRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StlBillDetailDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.FossConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 应收单Service服务 应收单新增、修改、红冲、查询、审核等公共Service
 * @author huangxb
 * @date 2012-10-10 下午3:23:57
 * @since
 * @version
 */
public class BillReceivablePartnerService implements IBillReceivablePartnerService {

	/**
	 * 日志属性
	 */
//	private static final Logger logger = LogManager
//			.getLogger(BillReceivablePartnerService.class);

	/**
	 * 应收单Dao
	 */
	private IBillReceivablePartnerEntityDao billReceivablePartnerEntityDao;

	/**
	 * 
	 * 根据传入的一个应收单号，获取一条应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-12 上午11:58:36
	 * @param receivableNo
	 *            应收单号
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	public BillReceivableEntity queryByReceivableNO(String receivableNo,
			String active) {
		if (StringUtils.isEmpty(receivableNo)) {
			throw new SettlementException("查询应收单，输入的应收单号不能为空！");
		}
		List<String> receivableNos = new ArrayList<String>();
		receivableNos.add(receivableNo);
		List<BillReceivableEntity> list = this.billReceivablePartnerEntityDao
				.queryByReceivableNOs(receivableNos, active);

		// 判断是否为空
		if (CollectionUtils.isNotEmpty(list)) {

			// 判断是否有多个有效应收单
			if (list.size() != 1 && FossConstants.ACTIVE.equals(active)) {
				throw new SettlementException(String.format(
						"应收单号为:%s的有效应收单有多个，请检查数据是否正确", receivableNo));
			}

			return (BillReceivableEntity) list.get(0);
			
		}

		return null;
	}

	/**
	 * 按照运单号和应付部门编码集合查询应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-28 下午3:25:57
	 * @param waybillNos
	 * @param orgCodeList
	 * @param active
	 * @param currentInfo
	 * @return
	 */
	@Override
	public List<BillReceivableEntity> queryByWaybillNosAndOrgCodes(
			List<String> waybillNos, List<String> orgCodeList, String active,
			CurrentInfo currentInfo) {
		if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException(" 按照运单号和应付部门编码集合查询应收单信息参数不能为空");
		}
		return this.billReceivablePartnerEntityDao.queryByWaybillNosAndOrgCodes(
				waybillNos, orgCodeList, active, currentInfo);
	}
	
	/**
	 * 根据来源单号集合和应收部门编码集合，查询应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 下午4:38:08
	 * @param sourceBillNos
	 * @param orgCodes
	 * @param sourceBillType
	 * @param active
	 * @return
	 */
	@Override
	public List<BillReceivableEntity> queryBySourceBillNOsAndOrgCodes(
			List<String> sourceBillNos, List<String> orgCodes,
			String sourceBillType, String active, CurrentInfo currentInfo) {
		if (CollectionUtils.isEmpty(sourceBillNos)) {
			throw new SettlementException("按照来源单号集合和应收部门编码集合，查询应收单传入的参数不能为空！");
		}
		return this.billReceivablePartnerEntityDao.queryBySourceBillNOsAndOrgCodes(
				sourceBillNos, orgCodes, sourceBillType, active, currentInfo);
	}

	/**
     * 按应收单号和数据权限查询应收单
     * @author 045738-foss-maojianqiang
     * @date 2013-6-12 下午6:36:24
     * @param receivableNos
     * @param active
     * @param currentInfo
     * @return
     */
	public List<BillReceivableEntity> queryByReceivableNosAndOrgCodes(
			List<String> receivableNos, String active, CurrentInfo currentInfo) {
		//用手段号不能为空
		if (CollectionUtils.isEmpty(receivableNos)) {
			throw new SettlementException(" 按照应收单号和数据权限查询应收单信息参数不能为空");
		}
		return this.billReceivablePartnerEntityDao.queryByReceivableNosAndOrgCodes(receivableNos, active, currentInfo);
	}

    /*
    *@author 099995-foss-hemingyu
     * @date 2016-01-14 上午15:47:38
     * @param wayBillNo
     *            运单号
     * @param billType
     *            应收类型
     */
    public BillReceivableEntity selectByWayBillNoAndBillType(String wayBillNo,String billType) {
        if (StringUtils.isEmpty(wayBillNo) || StringUtils.isEmpty(billType)) {
            throw new SettlementException("查询应收单，输入的运单号和应收单类型不能为空！");
        }

        List<BillReceivableEntity>  billReceivableEntityList= this.billReceivablePartnerEntityDao
                .selectByWayBillNoAndBillType(wayBillNo, billType);

        // 判断是否为空
        if (CollectionUtils.isEmpty(billReceivableEntityList)) {
            return null;
        }else if(billReceivableEntityList.size() != 1){
            throw new SettlementException(String.format(
                    "运单号为:%s的有效应收单有多条数据，请检查数据是否正确", wayBillNo));
        }else{
            BillReceivableEntity billReceivableEntity = billReceivableEntityList.get(0);
            return billReceivableEntity;
        }
    }
	
	/**
	 * 
	 * 根据传入的一到多个应收单号，获取一到多条应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-12 下午6:28:48
	 * @param receivableNos
	 *            应收单号集合
	 * @param active
	 *            是否有效
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BillReceivableEntity> queryByReceivableNOs(List<String> receivableNos, String active) {
		if (CollectionUtils.isEmpty(receivableNos)) {
			throw new SettlementException("查询应收单，输入的应收单号不能为空！");
		}
		// 应收单号个数小于等于1000个时，直接查询返回
		if (receivableNos.size() <= SettlementConstants.MAX_LIST_SIZE) {
			return this.billReceivablePartnerEntityDao.queryByReceivableNOs(
					receivableNos, active);
		} 
		// 应收单号个数大于1000个时，分批查询
		else {
			List<BillReceivableEntity> recEntitys = new ArrayList<BillReceivableEntity>();
			List<String> queryRecNos = new ArrayList<String>();
			for (int index = 0; index < receivableNos.size(); index++) {
				queryRecNos.add(receivableNos.get(index));
				if (queryRecNos.size() == SettlementConstants.MAX_LIST_SIZE) {
					recEntitys = (List<BillReceivableEntity>) CollectionUtils
							.union(recEntitys, billReceivablePartnerEntityDao
									.queryByReceivableNOs(queryRecNos, active));
					queryRecNos.clear();
				}
			}
			if (CollectionUtils.isNotEmpty(queryRecNos)) {
				recEntitys = (List<BillReceivableEntity>) CollectionUtils
						.union(recEntitys, billReceivablePartnerEntityDao
								.queryByReceivableNOs(queryRecNos, active));
			}
			return recEntitys;
		}
	}
	
	
	/* 
	 * ptp监控查询应收单各单据的总记录数和总金额数
	 * 
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillReceivablePartnerService#countReceivableBills(com.deppon.foss.module.settlement.common.api.shared.dto.BillingQueryRequestDto)
	 */
	@Override
	public List<StlBillDetailDto> countReceivableBills(
			BillingQueryRequestDto requestDto) {
		return billReceivablePartnerEntityDao.countReceivableBills(requestDto);
	}

    
	/**
	 * @param billReceivablePartnerEntityDao
	 *            the billReceivablePartnerEntityDao to set
	 */
	public void setBillReceivablePartnerEntityDao(IBillReceivablePartnerEntityDao billReceivablePartnerEntityDao) {
		this.billReceivablePartnerEntityDao = billReceivablePartnerEntityDao;
	}
	
}
