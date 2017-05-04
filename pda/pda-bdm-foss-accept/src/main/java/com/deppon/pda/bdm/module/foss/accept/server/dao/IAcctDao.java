package com.deppon.pda.bdm.module.foss.accept.server.dao;


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
 * @package com.deppon.pda.bdm.module.foss.accept.server.dao 
 * @file IAcctDao.java 
 * @description 接货模块DAO接口
 * @author ChenLiang
 * @created 2012-12-31 上午10:53:11    
 * @version 1.0
 */
public interface IAcctDao {
	
	/**
	 * 
	 * @description 保存开单扫描信息
	 * @param scanEntity 
	 * @created 2012-12-31 上午10:53:50
	 */
	public void saveScanBilling(BillingScanEntity billingScan);
	
	/**
	 * 
	 * @description 保存开单信息
	 * @param scanEntity 
	 * @created 2012-12-31 上午10:54:00
	 */
	public void saveBilling(BillingScanEntity billingScan);
	 
	/**
     * 
     * @description 保存快递开单扫描信息
     * @param waybillExpress 
     * @created 2013-07-23 上午10:54:00
     */
    public void saveScanBillingExpress(WaybillExpressEntity waybillExpress);
    
    /**
     * 
     * @description 保存快递开单信息
     * @param waybillExpress 
     * @created 2013-07-23 上午10:54:00
     */
    public void saveBillingExpress(WaybillExpressEntity waybillExpress);
    
    /**
     * 
     * @description 保存快递开单分录信息
     * @param waybillExpress 
     * @created 2013-07-23 上午10:54:00
     */
    public void saveBillingExpressDetail(WaybillExpressEntity waybillExpress);
	
    /**
	 * 
	 * @description 保存标签信息
	 * @param scanEntity 
	 * @created 2012-12-31 上午10:56:29
	 */
	public void saveBillLabels(BillingScanEntity billingScan);
	
	/**
	 * 
	 * @description 保存增值服务项
	 * @created 2012-12-31 上午10:56:52
	 */
	public void saveBillVolAddService(ValueAddServiceEntity valAddService);

	/**
	 * 
	 * @description 保存打印标签信息
	 * @param labelPrint 
	 * @created 2012-12-31 上午10:54:10
	 */
	public void saveLabelPrint(LabelPrintEntity labelPrint);

	/**
	 * 
	 * @description 保存已接收信息
	 * @param rcvOrderScan 
	 * @created 2012-12-31 上午11:11:38
	 */
	public void saveRcvOrder(RcvOrderScanEntity rcvOrderScan);

	/**
	 * 
	 * @description 保存已读信息
	 * @param readOrderScan 
	 * @created 2012-12-31 上午11:11:46
	 */
	public void saveReadOrder(ReadOrderScanEntity readOrderScan);

	/**
	 * 
	 * @description 保存已退回信息
	 * @param backOrderScan 
	 * @created 2012-12-31 上午11:11:52
	 */
	public void saveBackOrder(BackOrderScanEntity backOrderScan);

	/**
	 * 
	 * @description 保存货物重量体积
	 * @param wblWghtAndVole 
	 * @created 2012-12-31 下午2:35:28
	 */
	public void saveWghtAndVole(WblWghtAndVoleEntity wblWghtAndVole);
	
	/**
	 * 
	 * @description 接收约车任务
	 * @param receiveCarTask 
	 * @created 2013-1-8 下午2:59:22
	 */
	public void saveReceiveCarTask(ReceiveCarTaskEntity receiveCarTask);
	
	/**
	 * 
	 * @description 保存未开录入扫描数据
	 * @param unBilling 
	 * @created 2013-1-8 下午4:21:07
	 */
	public void saveUnBillingScan(UnBillingEntity unBilling);
	
	/**
	 * 
	 * @description 保存未开单录入数据
	 * @param unBilling 
	 * @created 2013-1-8 下午4:04:46
	 */
	public void saveUnBilling(UnBillingEntity unBilling);

	/** 
	* @Description: 查询未扫描信息
	* @param userCode
	* @param pdaCode
	* @return boolean
	* @author 092038
	* @date 2014-3-10 上午11:04:11
	*/ 
	public boolean queryNoSyncScanMsgCount(String userCode, String pdaCode);
	
	/** 
	* @Description:大客户提交
	* @param userCode
	* @param pdaCode
	* @param customCode
	* @return boolean
	* @author 092038
	* @date 2014-3-10 上午11:04:11
	*/ 
	public boolean queryNoSyncScanMsgCount(String userCode, String pdaCode,String customCode);
	
	
	
	/** 
	* @Description: 保存GPS扫描信息
	* @param gpsAddressCollectEntity 
	* @return void
	* @author 092038
	* @date 2014-3-10 上午11:06:12
	*/ 
	public void saveGpsMessage(GpsAddressCollectEntity gpsAddressCollectEntity);
	
	
   /** 
    * @Description: 保存GPS扫描信息
    * @param gpsAddressCollectEntity 
    * @return void
    * @author 092038
    * @date 2014-3-10 上午11:06:12
    */ 
	
	public void saveGpsScanMessage(GpsAddressCollectEntity gpsAddressCollectEntity);
	
	
	
	/**
     * 
     * @description 保存大客户电子运单扫描信息
     * @param waybillExpress 
     * @created 2013-07-23 上午10:54:00
     */
    public void saveBigEScanBilling(ElecBillingScanEntity eWaybillExpress);
    
    /**
     * 
     * @description 保存大客户电子运单信息
     * @param waybillExpress 
     * @created 2013-07-23 上午10:54:00
     */
    public void saveBigEBilling(ElecBillingScanEntity eWaybillExpress);
	
    
	/**
     * 
     * @description 保存散客电子运单扫描信息
     * @param waybillExpress 
     * @created 2013-07-23 上午10:54:00
     */
    public void saveIndividualEScanBilling(ElecBillingScanEntity eWaybillExpress);
    
    /**
     * 
     * @description 保存散客电子运单信息
     * @param waybillExpress 
     * @created 2013-07-23 上午10:54:00
     */
    public void saveIndividualEBilling(ElecBillingScanEntity eWaybillExpress);
    
    /**
     * 
     * @description 保存散客电子运单信息
     * @param waybillExpress 
     * @created 2013-07-23 上午10:54:00
     */
    public void saveIndividualEBillingDetail(ElecBillingScanEntity eWaybillExpress);
   
    /**
     * 
     * @description 批量保存退回电子运单
     * @param waybillExpress 
     * @created 2013-07-23 上午10:54:00
     */
    public void saveBackEWaybills(BackOrderScanEntity backOrders);

	/**
	 * @description 保存电子运单二期扫描数据
	 * @param scanOrCalScan
	 * @author 201638
	 * @date 2015-2-3 
	 */
	public void saveEWaybillScan(ElecBillingScanEntity scanOrCalScan);

//	public void saveFnshLoadTaskScan(FinishTaskEntity param);

	/**
	 * @description 保存电子运单二期开单信息
	 * @param billingScan
	 * @author 201638
	 * @date 2015-2-3 
	 */
	public void saveEWaybillBilling(ElecBillingScanEntity billingScan);

	public boolean queryNoSyncScanMsgCountByTaskCode(String taskCode);

	
	/**
	 * 保存裹裹渠道单号和验证码信息
	 * @author 245955
	 * @date 2016-01-10
	 */
	public void saveVerifyCode(GougouPdaJmsEntity entity);
	
	/**
	 * 保存零担电子面单扫描信息
	 */
	public void saveEWaybillScanData(ScanDataUploadEntity entity);
	
	
}
