package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.express;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverSignService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignShellDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSignDetailDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.cache.DeptCache;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryDao;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryPdaDao;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.ExpSignParentEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.MotherSonSignList;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PdaSignLabelCodeEntity;

/**
 * 子母件签收Services
 * @author 245955
 *
 */
public class KdSignParentService implements IBusinessService<Void,ExpSignParentEntity> {
	private static final Log LOG = LogFactory.getLog(KdSignParentService.class);
	private IPdaDeliverSignService pdaDeliverSignService;
	private IDeliveryDao deliveryDao;
	private IDeliveryPdaDao deliveryPdaDao;
    private UserCache userCache;
	private DeptCache deptCache;
	
	/**
	 * 获取包头信息
	 */
	@Override
	public ExpSignParentEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		ExpSignParentEntity expSignParentEntity=JsonUtil.parseJsonToObject(ExpSignParentEntity.class, asyncMsg.getContent());
		// 部门
		expSignParentEntity.setDeptCode(asyncMsg.getDeptCode());
		// PDA编号
		expSignParentEntity.setPdaCode(asyncMsg.getPdaCode());   
		// user编号
		expSignParentEntity.setScanUser(asyncMsg.getUserCode()); 
		// 操作类型
		expSignParentEntity.setScanType(asyncMsg.getOperType());
		// ID
		expSignParentEntity.setId(asyncMsg.getId());			  
		// 上传时间
		expSignParentEntity.setUploadTime(asyncMsg.getUploadTime()); 
		// 同步状态
		expSignParentEntity.setSyncStatus(asyncMsg.getAsyncStatus()); 
		// 同步次数
		expSignParentEntity.setCount(asyncMsg.getSyncCount());		
		return expSignParentEntity;
	}

	/**
	 * 服务方法
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-9-16
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, ExpSignParentEntity param)
			throws PdaBusiException {
		try {
			// 参数验证
			this.validate(asyncMsg, param);
			//保存快递签收分录
	        deliveryDao.saveExpParentSignDetail(param);
			PdaDeliverSignShellDto dto=null;
			//封装实体
			dto = wrapPdaParentSignDto(asyncMsg, param);	
			long startTime = System.currentTimeMillis();
			//PDA子母件签收出库操作
			pdaDeliverSignService.pdaExpressBatchSign(dto);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]子母件签收接口消耗时间:"+(endTime-startTime)+"ms");
			//保存签收信息
			saveScanMsgAndSignAndSerilnumber(param);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} 	
		return null;
	}
	
	
	/**
	 * 封装实体
	 * @param asyncMsg
	 * @param ExpSignParentEntity
	 * @return
	 * @throws DateParseException 
	 */
	private PdaDeliverSignShellDto wrapPdaParentSignDto(AsyncMsg asyncMsg,  ExpSignParentEntity expSignParentEntity){
		PdaDeliverSignShellDto pdaDeliverSignShellDto=new PdaDeliverSignShellDto();
 		//到付金额
		pdaDeliverSignShellDto.setToPayAmount(BigDecimal.valueOf(expSignParentEntity.getArriveAmount()));
		//付款方式 -- 快递  到付金额
		pdaDeliverSignShellDto.setPaymentType(expSignParentEntity.getArriveType());
		//银行交易流水号--到付流水号
		pdaDeliverSignShellDto.setBankTradeSerail(expSignParentEntity.getBankTradeSerail());
		//代收货款
		pdaDeliverSignShellDto.setCodAmount(BigDecimal.valueOf(expSignParentEntity.getPayAmount()));
		//代收货款--付款方式 
		pdaDeliverSignShellDto.setCodPaymentType(expSignParentEntity.getPayType());
		// 银行交易流水号--代收货款流水号
		pdaDeliverSignShellDto.setCodBankTradeSerail(expSignParentEntity.getCodBankTradeSerail());
		//合并金额
		pdaDeliverSignShellDto.setTotalAmount(BigDecimal.valueOf(expSignParentEntity.getTotalAmount()));
		//合并--付款方式 
		pdaDeliverSignShellDto.setTotalPaymentType(expSignParentEntity.getTotalType());
		List<MotherSonSignList> motherSonSignList=expSignParentEntity.getMotherSonSignList();
		List<PdaDeliverSignDto>  dto=new ArrayList<PdaDeliverSignDto>();
		PdaDeliverSignDto pdaDeliverSignDto=null;
		//循环取子母件签收明细信息
		for (MotherSonSignList list : motherSonSignList) {
			pdaDeliverSignDto=new PdaDeliverSignDto();
			//运单号i
			pdaDeliverSignDto.setWaybillNo(list.getWblCode());
			//到达联编号
			pdaDeliverSignDto.setArrivesheetNo(list.getArrInfoCode());
			//签收部门编码
			pdaDeliverSignDto.setSignDeptCode(asyncMsg.getDeptCode());
			 //子母件签收件数
	        pdaDeliverSignDto.setSignGoodsQty(list.getPieces());
	        //签收情况
	        pdaDeliverSignDto.setSituation(list.getSignStatus());
	        //签收人类型
	    	pdaDeliverSignDto.setDeliverymanType(list.getSignPerType());
	    	//车牌号
	        pdaDeliverSignDto.setVehicleNo(list.getTruckCode());
	        //司机工号
	        pdaDeliverSignDto.setDriverCode(asyncMsg.getUserCode());
	       //是否提供定额发票
	      	pdaDeliverSignDto.setIsofferInvoice(list.getIsofferInvoice());
	        //PDA串口
	        pdaDeliverSignDto.setPdaSerial(asyncMsg.getPdaCode());
	        //签收时间
			pdaDeliverSignDto.setSignTime(list.getSignTime());
	        //获取流水号
	        List<PdaSignLabelCodeEntity> labelCodes = list.getLabelCodes();
	        List<PdaSignDetailDto> pdaSignDetailDtos = new ArrayList<PdaSignDetailDto>();
	        PdaSignDetailDto pdaSignDetailDto = null;
	        for (PdaSignLabelCodeEntity pdaSignLabelCodeEntity : labelCodes) {
	            pdaSignDetailDto = new PdaSignDetailDto();
	            //签收情况.
	            pdaSignDetailDto.setSituation(pdaSignLabelCodeEntity.getSignSituation());
	            //流水号
	            pdaSignDetailDto.setSerialNo(pdaSignLabelCodeEntity.getLabelCode());
	            pdaSignDetailDtos.add(pdaSignDetailDto);
	        }
	        //签收流水号列表
	        pdaDeliverSignDto.setPdaSignDetailDtos(pdaSignDetailDtos);
	        dto.add(pdaDeliverSignDto);
		}	
		pdaDeliverSignShellDto.setPdaDeliverSignDtos(dto);
		return pdaDeliverSignShellDto;
	}
	/**
     * 保存扫描信息,签收信息,流水号信息表,快递签收分录
     * @param ExpSignParentEntity
     */
    @Transactional
    private void saveScanMsgAndSignAndSerilnumber(ExpSignParentEntity entity){
    	LOG.info("保存子母件签收各时间节点");
    	//保存子母件签收各时间节点
    	UserEntity userEntity = userCache.getUser(entity.getScanUser());
		DeptEntity deptEntity = deptCache.getDept(userEntity.getDeptId());
		//set快递员的当前部门编码
		entity.setDeptCode(deptEntity.getDeptCode());
		List<MotherSonSignList>  motherSonSignList=entity.getMotherSonSignList();
		try {
			for(int i=0;i<motherSonSignList.size();i++){
				MotherSonSignList list=motherSonSignList.get(i);
				list.setId(UUIDUtils.getUUID());
				list.setDeptCode(entity.getDeptCode());
				list.setScanUser(entity.getScanUser());
				list.setPdaCode(entity.getPdaCode());
				list.setScanType(entity.getScanType());
				//保存节点
				deliveryPdaDao.saveExpParentSignNode(list);
				 //扫描表
		        deliveryDao.saveExpParentSignScanmsg(list);
		        //签收信息表
		        deliveryDao.saveExpParentSignScanSign(list);
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
    }
	/**
	 * 验证数据有效性
	 * @param asyncMsg
	 * @param param
	 */
	private void validate(AsyncMsg asyncMsg, ExpSignParentEntity param){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//PDA编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		Argument.notNull(param, "ExpSignParentEntity");
	}
	/**
	 * 操作类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_KDDERY_PARENT_SIGN.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

	public void setPdaDeliverSignService(
			IPdaDeliverSignService pdaDeliverSignService) {
		this.pdaDeliverSignService = pdaDeliverSignService;
	}

	public void setDeliveryDao(IDeliveryDao deliveryDao) {
		this.deliveryDao = deliveryDao;
	}

	public void setDeliveryPdaDao(IDeliveryPdaDao deliveryPdaDao) {
		this.deliveryPdaDao = deliveryPdaDao;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public void setDeptCache(DeptCache deptCache) {
		this.deptCache = deptCache;
	}

}
