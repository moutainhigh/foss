package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.OptTruckDepartArriveEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.OptTruckTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.OptTruckTaskResponseEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.RequestSealBindANDCheckOutEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ResponseJsonInfoEntity;


/**
* @description 获取封签信息
* @version 1.0
* @author 106162-gis
* @update 2016年4月25日 上午8:07:27
*/
public interface ISealInformationService  extends IService {

	/**-----------------------------------------配合快递项目开发 开始-------------------------------------------**/
	/**
	 * @param  eirNum 交接单号
	 * @note   根据快递传过来的交接单号，获取封签号返回
	 * @author 106162-gis-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	List<String> querySealNumByEIRNumList(String eirNum);
	

	/**
	* @description 校验当前的车牌号对应的车辆是否被校验
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkCarIsSeal(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	* @paramers String vehicleNo
	*/
	List<String> checkCarIsSealService(String vehicleNo);
	
	/**
	* @description 配合(快递)校验快递交接单生成
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	* @paramers String vehicleNo,String deptCode,String status
	*/
	Object checkExpressEIRCarGenerateService(Map<String,String> paramMap);
	
	/**
	* @description 配合(快递)校验校验临时租车标记
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	Object tempLeaseCarStateService(String handOverBillNo);
	
	
	
	/**
	* @description 配合(快递)根据正单号查询运单号
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	List<String> queryWaybillNumByPositiveNum(String positiveNum);
	
	
	/**
	* @description 查询车牌号是否正确(是不是外请车和公司自有车)
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年5月16日 上午10:07:04
	* @version V1.0
	*/
	String isDOVehicleNo(String vehicleNo);
	
	/**-----------------------------------------配合快递项目开发 结束-----------------------------------------**/
	
	
	
	/**-----------------------------------------配合PDA开发 开始-------------------------------------------**/
	
	/**
	* @description 配合(快递)绑定封签
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	*/
	ResponseJsonInfoEntity bindSealForExpressService(RequestSealBindANDCheckOutEntity seal);
	
	
	/**
	* @description 配合(快递)校验封签
	* (non-Javadoc)
	* @see com.deppon.foss.service.IITFSealInformationService#checkExpressEIRGenerate(java.lang.String)
	* @author 106162-foss-liping
	* @update 2016年4月25日 上午10:07:04
	* @version V1.0
	*/
	ResponseJsonInfoEntity checkOutSealForExpressService(RequestSealBindANDCheckOutEntity seal);
	
	
	/**
	 * @param  OptTruckDepartArriveEntity
	 * @note   根据PDA传过来的部门信息，查询装车任务的车辆信息
	 * @author 106162-gis-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	List<OptTruckTaskResponseEntity>queryTruckTaskByByDeptCodeORCarNum(OptTruckDepartArriveEntity ent);
	
	
	
	/**
	 * @param  OptTruckTaskEntity
	 * @note   根据PDA传过来的车牌号,修改该车是否到达,发车状态
	 * @author 106162-gis-liping
	 * @date 2016-4-24 下午4:05:21
	 */
	ResponseJsonInfoEntity updateDepartureState(OptTruckTaskEntity obj);
	
	
	/**
	 * @param  VehicleNum
	 * @note   根据PDA传过来的车牌号,判断当前车是否已绑定封签
	 * @author 106162-gis-liping
	 * @date 2016-11-24 下午4:05:21
	 */
	List<String>  checkSealForCar(String vehicleNum);
	/**-----------------------------------------配合PDA开发 结束-------------------------------------------**/
}
