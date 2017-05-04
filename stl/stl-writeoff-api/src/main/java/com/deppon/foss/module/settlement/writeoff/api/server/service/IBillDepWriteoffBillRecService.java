package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepWriteoffBillRecDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillReceivableDto;

/**
 * 待核销预收单、应收单进行查询、核销等操作service
 * 
 * @author ibm-qiaolifeng
 * @date 2012-10-15 上午11:06:29
 */
public interface IBillDepWriteoffBillRecService extends IService {

	/**
	 * 查询预收单
	 * 
	 * @author ibm-qiaolifeng
	 * @date 2012-10-15 上午9:50:50
	 * @param billDepositReceivedDto
	 *            	预收单参数Dto
	 * @return BillDepWriteoffBillRecDto 
	 * 				预收单
	 * @see
	 */
	BillDepWriteoffBillRecDto queryBillDep(
			BillDepositReceivedDto billDepositReceivedDto);

	/**
	 * 查询预收单、应收单
	 * 
	 * @author ibm-qiaolifeng
	 * @date 2012-10-15 上午9:50:50
	 * @param billReceivableDto
	 *            	应收单参数Dto
	 * @return List<BillReceivableEntity> 
	 * 				应收单集合
	 * @see
	 */
	BillDepWriteoffBillRecDto queryBillRec(BillReceivableDto billReceivableDto);

	/**
	 * 根据传入的一到多个预收单号，获取一到多条预收单信息
	 * 
	 * @author ibm-qiaolifeng
	 * @date 2012-10-15 上午9:50:50
	 * @param billDepositReceivedDto
	 *            	预收单参数Dto
	 * @return List<BillDepWriteoffBillRecDto> 
	 * 				预收单集合
	 */
	List<BillDepositReceivedEntity> queryByDepositReceivedNOs(
			BillDepositReceivedDto billDepositReceivedDto);

	/**
	 * 根据传入的一到多个应收单号，获取一到多条应收单信息
	 * 
	 * @author ibm-qiaolifeng
	 * @date 2012-10-12 上午11:58:36
	 * @param billReceivableDto
	 *            	应收单参数Dto
	 * @return List<BillReceivableEntity> 
	 * 				应收单集合
	 */
	List<BillReceivableEntity> queryByReceivableNOs(
			BillReceivableDto billReceivableDto);

	/**
	 * 根据传入参数获取一到多条预收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-16 下午3:43:20
	 * @param billDepositReceivedDto
	 *            	预收单参数Dto
	 * @return List<BillDepWriteoffBillRecDto> 
	 * 				预收单集合
	 */
	List<BillDepositReceivedEntity> queryByDepositReceivedParams(
			BillDepositReceivedDto billDepositReceivedDto);

	/**
	 * 根据传入的参数获取一到多条应收单信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-16 下午4:44:23
	 * @param billReceivableDto
	 *            	应收单参数Dto
	 * @return List<BillReceivableEntity> 
	 * 				应收单集合
	 */
	List<BillReceivableEntity> queryByReceivableParams(
			BillReceivableDto billReceivableDto);

	/**
	 * 预收冲应收核销
	 * @author foss-qiaolifeng
	 * @date 2012-10-18 下午4:31:46
	 * @param billDepositReceivedEntityListFrom,billReceivableEntityListFrom
	 *     		billDepositReceivedEntityListAll,billReceivableEntityListAll
	 *     		cInfo
	 *     	  	界面勾选的预收单集合,界面勾选的应收单集合
	 *       	后台load的预收单集合,后台load的应收单集合
	 *       	当前登录用户
	 * @return BillDepWriteoffBillRecDto
	 * 			预收冲应收核销返回dto
	 */
	BillDepWriteoffBillRecDto writeoffBillDepAndBillRec(
			List<BillDepositReceivedEntity> billDepositReceivedEntityListFrom,
			List<BillReceivableEntity> billReceivableEntityListFrom,
			List<BillDepositReceivedEntity> billDepositReceivedEntityListAll,
			List<BillReceivableEntity> billReceivableEntityListAll,
			CurrentInfo cInfo) throws SettlementException;

	/**
	 * 重新刷新界面数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-29 上午10:15:57
	 * @param billReceivableDto
	 *     		billDepositReceivedDto
	 *     	  	应收单参数dto,
	 *       	预收单参数dto
	 * @return BillDepWriteoffBillRecDto
	 * 			预收冲应收核销返回dto
	 */
	BillDepWriteoffBillRecDto reQueryBillDepAndBillRec(
			BillReceivableDto billReceivableDto,
			BillDepositReceivedDto billDepositReceivedDto);

}
