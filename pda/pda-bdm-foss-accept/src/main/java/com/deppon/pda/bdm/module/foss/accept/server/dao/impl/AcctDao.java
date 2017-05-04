package com.deppon.pda.bdm.module.foss.accept.server.dao.impl;


import java.util.Date;
import java.util.HashMap;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.BackOrderScanEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.BillingScanEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.GougouPdaJmsEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.GpsAddressCollectEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.LabelPrintEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.RcvOrderScanEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.ReadOrderScanEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.ReceiveCarTaskEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.UnBillingEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.ValueAddServiceEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.WaybillExpressEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.WblWghtAndVoleEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.ElecBillingScanEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.ScanDataUploadEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.dao.impl 
 * @file AcctDao.java 
 * @description 接货DAO实现类
 * @author ChenLiang
 * @created 2012-12-31 上午10:58:38    
 * @version 1.0
 */
public class AcctDao extends iBatis3DaoImpl implements IAcctDao {

	/**
	 * 
	 * @description 保存开单扫描信息
	 * @param scanEntity 
	 * @created 2012-12-31 上午10:53:50
	 */
	@Override
	public void saveScanBilling(BillingScanEntity billingScan) {
		getSqlSession().insert(getClass().getName() + ".saveScanBilling", billingScan);
	}

	/**
	 * 
	 * @description 保存开单信息
	 * @param scanEntity 
	 * @created 2012-12-31 上午10:54:00
	 */
	@Override
	public void saveBilling(BillingScanEntity billingScan) {
		getSqlSession().insert(getClass().getName() + ".saveBilling", billingScan);
	}

	 /**
     * 
     * @description 保存快递开单扫描信息
     * @param waybillExpress 
     * @created 2013-07-23 上午10:54:00
     */
    @Override
    public void saveScanBillingExpress(WaybillExpressEntity waybillExpress) {
       getSqlSession().insert(getClass().getName() + ".saveScanBillingExpress", waybillExpress);   
    }
    
    /**
     * 
     * @description 保存快递开单信息
     * @param waybillExpress 
     * @created 2013-07-23 上午10:54:00
     */
    @Override
    public void saveBillingExpress(WaybillExpressEntity waybillExpress) {
        getSqlSession().insert(getClass().getName() + ".saveBillingExpress", waybillExpress);   
    }

    /**
     * 
     * @description 保存快递开单分录信息
     * @param waybillExpress 
     * @created 2013-07-23 上午10:54:00
     */
    @Override
    public void saveBillingExpressDetail(WaybillExpressEntity waybillExpress) {
        getSqlSession().insert(getClass().getName() + ".saveBillingExpressDetail", waybillExpress);
    }
	
	
	/**
	 * 
	 * @description 保存标签信息
	 * @param scanEntity 
	 * @created 2012-12-31 上午10:56:29
	 */
	@Override
	public void saveBillLabels(BillingScanEntity billingScan) {
		getSqlSession().insert(getClass().getName() + ".saveBillLabels", billingScan);
	}

	/**
	 * 
	 * @description 保存增值服务项
	 * @created 2012-12-31 上午10:56:52
	 */
	@Override
	public void saveBillVolAddService(ValueAddServiceEntity valAddService) {
		getSqlSession().insert(getClass().getName() + ".saveBillVolAddService", valAddService);
	}

	/**
	 * 
	 * @description 保存打印标签信息
	 * @param labelPrint 
	 * @created 2012-12-31 上午10:54:10
	 */
	@Override
	public void saveLabelPrint(LabelPrintEntity labelPrint) {
		getSqlSession().insert(getClass().getName() + ".saveLabelPrint", labelPrint);
	}

	/**
	 * 
	 * @description 保存已接收信息
	 * @param rcvOrderScan 
	 * @created 2012-12-31 上午11:11:38
	 */
	@Override
	public void saveRcvOrder(RcvOrderScanEntity rcvOrderScan) {
		getSqlSession().insert(getClass().getName() + ".saveRcvOrder", rcvOrderScan);
	}

	/**
	 * 
	 * @description 保存已读信息
	 * @param readOrderScan 
	 * @created 2012-12-31 上午11:11:46
	 */
	@Override
	public void saveReadOrder(ReadOrderScanEntity readOrderScan) {
		getSqlSession().insert(getClass().getName() + ".saveReadOrder", readOrderScan);
	}

	/**
	 * 
	 * @description 保存已退回信息
	 * @param backOrderScan 
	 * @created 2012-12-31 上午11:11:52
	 */
	@Override
	public void saveBackOrder(BackOrderScanEntity backOrderScan) {
		getSqlSession().insert(getClass().getName() + ".saveBackOrder", backOrderScan);
	}

	/**
	 * 
	 * @description 保存货物重量体积
	 * @param wblWghtAndVole 
	 * @created 2012-12-31 下午2:35:28
	 */
	@Override
	public void saveWghtAndVole(WblWghtAndVoleEntity wblWghtAndVole) {
		getSqlSession().insert(getClass().getName() + ".saveWghtAndVole", wblWghtAndVole);
	}

	/**
	 * 
	 * @description 接收约车任务
	 * @param receiveCarTask 
	 * @created 2013-1-8 下午2:59:22
	 */
	@Override
	public void saveReceiveCarTask(ReceiveCarTaskEntity receiveCarTask) {
		getSqlSession().insert(getClass().getName() + ".saveReceiveCarTask", receiveCarTask);
	}

	/**
	 * 
	 * @description 保存未开录入扫描数据
	 * @param unBilling 
	 * @created 2013-1-8 下午4:21:07
	 */
	@Override
	public void saveUnBilling(UnBillingEntity unBilling) {
		getSqlSession().insert(getClass().getName() + ".saveUnBilling", unBilling);
	}

	/**
	 * 
	 * @description 保存未开单录入数据
	 * @param unBilling 
	 * @created 2013-1-8 下午4:04:46
	 */
	@Override
	public void saveUnBillingScan(UnBillingEntity unBilling) {
		getSqlSession().insert(getClass().getName() + ".saveUnBillingScan", unBilling);
	}

	@Override
	public boolean queryNoSyncScanMsgCount(String userCode, String pdaCode) {
		int count = 0;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userCode", userCode);
		map.put("pdaCode", pdaCode);
		map.put("startTime",DateUtils.formatDate(new Date()) + " 00:00:00");
		map.put("endTime",DateUtils.formatDate(new Date()) + " 23:59:59");
		count = (Integer) getSqlSession().selectOne(getClass().getName() + ".queryNoSyncScanMsgCount", map);
		return count>0?true:false;
	}

	/**
     * 
     * @description 保存GPS采集信息
     * @param unBilling 
     * @created 2013-1-8 下午4:04:46
     */
    @Override
    public void saveGpsMessage(GpsAddressCollectEntity gpsAddressCollectEntity) {
        getSqlSession().insert(getClass().getName() + ".saveGpsAddress", gpsAddressCollectEntity);
    }

    /**
     * 
     * @description 保存GPS采集扫描信息
     * @param unBilling 
     * @created 2013-1-8 下午4:04:46
     */
    @Override
    public void saveGpsScanMessage(GpsAddressCollectEntity gpsAddressCollectEntity) {
        getSqlSession().insert(getClass().getName() + ".saveGpsScanAddress", gpsAddressCollectEntity);
    }

	@Override
	public void saveBigEScanBilling(ElecBillingScanEntity eWaybillExpress) {
		 getSqlSession().insert(getClass().getName() + ".saveBigScanEBillExpress", eWaybillExpress);
	}

	@Override
	public void saveBigEBilling(ElecBillingScanEntity eWaybillExpress) {
		 getSqlSession().insert(getClass().getName() + ".saveBigEBillExpress", eWaybillExpress);		
	}

	@Override
	public void saveIndividualEScanBilling(ElecBillingScanEntity eWaybillExpress) {
		getSqlSession().insert(getClass().getName() + ".saveIndividualScanEBillExpress", eWaybillExpress);
	}

	@Override
	public void saveIndividualEBilling(ElecBillingScanEntity eWaybillExpress) {
		getSqlSession().insert(getClass().getName() + ".saveIndividualEBillExpress", eWaybillExpress);
	}

	@Override
	public void saveIndividualEBillingDetail(
			ElecBillingScanEntity eWaybillExpress) {
		getSqlSession().insert(getClass().getName() + ".saveIndividualEBillExpressDetail", eWaybillExpress);
		
	}

	@Override
	public boolean queryNoSyncScanMsgCount(String userCode, String pdaCode,
			String customCode) {
		int count = 0;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userCode", userCode);
		map.put("pdaCode", pdaCode);
		map.put("customCode", customCode);
		map.put("startTime",DateUtils.formatDate(new Date()) + " 00:00:00");
		map.put("endTime",DateUtils.formatDate(new Date()) + " 23:59:59");
		count = (Integer) getSqlSession().selectOne(getClass().getName() + ".queryNoSyncBEwaybillCount", map);
		return count>0?true:false;
	}

	@Override
	public void saveBackEWaybills(BackOrderScanEntity backOrders) {
		getSqlSession().insert(getClass().getName() + ".saveBackEWaybills", backOrders);
	}

	/**
	 * @description 保存电子运单二期扫描数据
	 * @param scanOrCalScan
	 * @author 201638
	 * @date 2015-2-3 
	 */
	@Override
	public void saveEWaybillScan(ElecBillingScanEntity scanOrCalScan) {
		getSqlSession().insert(getClass().getName() + ".saveEWaybillScan", scanOrCalScan);
		
	}

	/*@Override
	public void saveFnshLoadTaskScan(FinishTaskEntity param) {
		getSqlSession().insert(getClass().getName() + ".saveFnshLoadTaskScan", param);
		
	}*/

	/**
	 * @description 保存电子运单二期开单信息
	 * @param billingScan
	 * @author 201638
	 * @date 2015-2-3 
	 */
	@Override
	public void saveEWaybillBilling(ElecBillingScanEntity billingScan) {
		getSqlSession().insert(getClass().getName() + ".saveEWaybillBilling", billingScan);
	}

	@Override
	public boolean queryNoSyncScanMsgCountByTaskCode(String taskCode) {
		int count = 0;
		count = (Integer) getSqlSession().selectOne(getClass().getName() + ".queryNoSyncScanMsgCountByTaskCode", taskCode);
		return count>0?true:false;
	}
	
	/**
	 * 保存裹裹渠道单号和验证码信息
	 * @author 245955
	 * @date 2016-01-10
	 */
	@Override
	public void saveVerifyCode(GougouPdaJmsEntity entity) {
		getSqlSession().insert(getClass().getName() + ".saveVerifyCode",entity);
	}

	/**
	 * 零担电子面单保存扫描数据
	 */
	@Override
	public void saveEWaybillScanData(ScanDataUploadEntity entity) {
		getSqlSession().insert(getClass().getName() + ".saveEWaybillScanData",entity);
		
	}	
	
}
