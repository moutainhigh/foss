package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillBadAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupWriteBackDto;

import java.util.List;


/**
 * 运单开单服务，供接送货调用
 * @author ibm-zhuwei
 * @date 2012-10-25 上午10:37:48
 */
public interface IWaybillPickupService {

	/**
	 * 新增运单
	 * @author ibm-zhuwei
	 * @date 2012-10-25 上午10:38:17
	 */
	void addWaybill(WaybillPickupInfoDto waybill, CurrentInfo currentInfo);
	
	/**
	 * 作废运单
	 * @author ibm-zhuwei
	 * @date 2012-10-25 上午10:38:52
	 */
	void cancelWaybill(String waybillNo, CurrentInfo currentInfo);
	
	/**
	 * 悟空单据处理取消运单
	 * 
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-02
	 * @param waybillNo
	 * @param currentInfo
	 * @return 红冲的原始单据信息
	 */
	WaybillPickupWriteBackDto handleCanceledWaybill(String waybillNo,CurrentInfo currentInfo);
	
	/**
	 * 更改运单
	 * @author ibm-zhuwei
	 * @date 2012-10-25 上午10:39:15
	 */
	void modifyWaybill(WaybillPickupInfoDto oldWaybill, WaybillPickupInfoDto newWaybill, CurrentInfo currentInfo);
	
	/**
	 * 判断能否更改
	 * @author ibm-zhuwei
	 * @date 2012-10-25 上午10:39:47
	 */
	void canChange(String waybillNo);
	
	/**
	 * 判断是否可以欠款
	 * @param orgCode
	 * @param customerCode
	 */
	void canDebit(String orgCode,String customerCode);	
    /**
     * @Description: 电子运单新增校验
     * @author lianghaisheng  
     * @date 2014-9-1 下午5:08:14
     * @return void    
     * @throws
     */
    public void electriWaybillCanAdd(WaybillPickupInfoDto waybill, CurrentInfo currentInfo);
    
    /**
	 * 
	 * @author 198704
	 * @date 2014-10-17 下午4:00:47
	 * @param waybillNos
	 * @return
	 */
	public List<BillBadAccountEntity> queryByWaybillNOs(List<String> waybillNos);

	/**
	 * 根据传入的一到多个运单单号，获取一到多条折扣单信息
     * @author 邓大伟
     * @date 2015-02-03
     * @param wayBillNos  运单单号集合
	 */
	public String querydiscountPayable(String waybillNo);
	
	/**
	 * 返货更改应收单接口
	 * @param waybill
	 * @param currentInfo
	 */
	public void returnedGoodsWriteoffReceivable(WaybillPickupInfoDto waybill, CurrentInfo currentInfo);
	
	/**
	 * 转寄退回(签收前)开单
	 * @author foss-yanghang
	 * ECS-327090
	 * @date 2016-4-25 
	 * @param waybill
	 * @param currentInfo
	 */
	void toChangeTermsPayment(WaybillPickupInfoDto waybill,WaybillPickupInfoDto oldWaybill,CurrentInfo currentInfo,boolean bl);

	/**
	 * 快递系统转寄开单
	 * @author foss-231434-bieuexiong
	 * @date 2016-6-6
	 */
    void ecsToChangeTermsPayment(WSCEntity wscEntity, WaybillPickupInfoDto waybill, WaybillPickupInfoDto oldWaybill, CurrentInfo currentInfo, boolean bl);
	void ecsToChangeTermsPayment(WaybillPickupInfoDto waybill,WaybillPickupInfoDto oldWaybill,CurrentInfo currentInfo, boolean bl);

	/**
	 * 快递系统非转寄开单
	 * @author foss-231434-bieuexiong
	 * @date 2016-6-6
	 */
    void ecsAddWaybill(WSCEntity wscEntity,WaybillPickupInfoDto waybill, CurrentInfo currentInfo);
	void ecsAddWaybill(WaybillPickupInfoDto waybill, CurrentInfo currentInfo);
	
}
