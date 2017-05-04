package com.deppon.foss.module.settlement.agency.test.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.dao.IBillLandReceivableAgencyDao;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillReceivableAgencyDto;
import com.deppon.foss.module.settlement.agency.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.util.define.FossConstants;

public class BillLandReceivableAgencyDaoTest extends BaseTestCase {
	
	@Autowired
	protected IBillLandReceivableAgencyDao billLandReceivableAgencyDao;
	
	/**
	 *  根据传入获取一到多条应收单信息
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午11:30:20
	 * @param billReceivableDto
	 * @return
	 * @see
	 */
	@Test
	@Rollback(true)
	public void  TestQueryBillReceivableEntityParamsForPage(){
		List<BillReceivableEntity> list=  billLandReceivableAgencyDao.queryBillReceivableEntityParams(getDto(),1,20);
		Assert.assertTrue(list.size()>=0);
	}
	
	/**
	 *  根据传入多条件参数获取一到多条应收单信息(不分页)
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午11:30:20
	 * @param billReceivableAgencyDto
	 * @return
	 * @see
	 */
	@Test
	@Rollback(true)
	public void  TestQueryBillReceivableEntityParams(){
		List<BillReceivableEntity> list=  billLandReceivableAgencyDao.queryBillReceivableEntityParams(getDto());
		Assert.assertTrue(list.size()>=0);
	}
	
	/**
	 *  根据传入获取一到多条应收单总数
	 * @author foss-pengzhen
	 * @date 2012-10-26 上午11:30:20
	 * @param billReceivableDto
	 * @return
	 * @see
	 */
	@Test
	@Rollback(true)
	public void  TestQueryBillReceivableEntityParamsCount(){
		long count = billLandReceivableAgencyDao.queryBillReceivableEntityParamsCount(getDto());
		Assert.assertTrue(count>=0);
	}
	/**
     * 根据传入的一到多个应收单号，获取一到多条应收单信息
     * @author foss-pengzhen
     * @date @date 2012-10-26 下午4:13:09
     * @param BillReceivableAgencyDto
     *            单号集合
     * @return
     * @see
     */
	@Test
	@Rollback(true)
	public void  TestQueryByReceivableNOs(){
		List<BillReceivableEntity> list = billLandReceivableAgencyDao.queryByReceivableNOs(getDto(), 1, 20);
		Assert.assertTrue(list.size()>0);
	}
    
    /**
     * 根据传入的一到多个应收单号，获取一到多条应收单总数
     * @author foss-pengzhen
     * @date @date 2012-10-26 下午4:13:09
     * @param BillReceivableAgencyDto
     *            单号集合
     * @return
     * @see
     */
	@Test
	@Rollback(true)
	public void  TestQueryByReceivableNOsCount(){
		long i = billLandReceivableAgencyDao.queryByReceivableNOsCount(getDto());
		Assert.assertTrue(i>0);
	}
    
    /**
     * 根据传入的一到多个航空正单号，获取一到多条应收单信息 (分页)    
     * * @author foss-pengzhen
     * @date 2012-10-26 下午4:14:51
     * @param billReceivableDto
     * @return
     * @see
     */
	@Test
	@Rollback(true)
	public void  TestQueryBySourceBillNOsForPage(){
		List<BillReceivableEntity> list = billLandReceivableAgencyDao.queryBySourceBillNOs(getDto(), 1, 20);
		Assert.assertTrue(list.size()>0);
	}
    /**  
     * 根据传入的一到多个航空正单号，获取一到多条应收单信息 
     * @author foss-pengzhen
     * @date 2013-1-7 上午10:28:18
     * @param billReceivableAgencyDto
     * @return
     * @see
     */
	@Test
	@Rollback(true)
	public void  TestQueryBySourceBillNOs(){
		List<BillReceivableEntity> list = billLandReceivableAgencyDao.queryBySourceBillNOs(getDto());
		Assert.assertTrue(list.size()>0);
	}
    /**
     * 根据传入的一到多个航空正单号，获取一到多条应收单总数     
     * * @author foss-pengzhen
     * @date 2012-10-26 下午4:14:51
     * @param billReceivableDto
     * @return
     * @see
     */
	@Test
	@Rollback(true)
	public void  TestQueryBySourceBillNOsCount(){
		long i = billLandReceivableAgencyDao.queryBySourceBillNOsCount(getDto());
		Assert.assertTrue(i>0);
	}	
	
	/**
	 * 获取查询条件dto
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 下午3:10:03
	 * @return
	 */
	private BillReceivableAgencyDto getDto(){
		BillReceivableAgencyDto dto = new BillReceivableAgencyDto();
		dto.setActive(FossConstants.ACTIVE);
		dto.setIsRedBack(FossConstants.NO);
		dto.setCustomerCode("LDP130814");
		dto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
		dto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);
		List<String> reNos = new ArrayList<String>();
		reNos.add("YS9300000121");
		reNos.add("YS9300000122");
		dto.setReceivableNos(reNos);
		
		List<String> sources = new ArrayList<String>();
		sources.add("9909021301");
		sources.add("9909021301");
		dto.setSourceBillNos(sources);
		return dto;
	}
}
