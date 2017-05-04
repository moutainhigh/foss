package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.express;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverSignService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignDto;
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
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryDao;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryPdaDao;
import com.deppon.pda.bdm.module.foss.delivery.server.service.impl.NormSignByPcService;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.ExpNormSignEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PdaSignDetailEntity;

/**
 * 快递按件正常签收
 * @author Administrator
 *
 */
public class KdNormSignByPcService implements IBusinessService<Void, ExpNormSignEntity> {

	private static final Log LOG = LogFactory.getLog(NormSignByPcService.class);
	
	private IPdaDeliverSignService pdaDeliverSignService;
    private IDeliveryDao deliveryDao;
	private IDeliveryPdaDao deliveryPdaDao;
    private UserCache userCache;
	
	private DeptCache deptCache;
	
    
    public void setDeliveryDao(IDeliveryDao deliveryDao) {
        this.deliveryDao = deliveryDao;
    }
	
	/**
	 * 解析包体
	 */
	@Override
	public ExpNormSignEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
	    ExpNormSignEntity expNormSign = JsonUtil.parseJsonToObject(ExpNormSignEntity.class, asyncMsg.getContent());
	    // 部门
	    expNormSign.setDeptCode(asyncMsg.getDeptCode());
        // PDA编号
	    expNormSign.setPdaCode(asyncMsg.getPdaCode());   
        // user编号
	    expNormSign.setScanUser(asyncMsg.getUserCode()); 
        // 操作类型
	    expNormSign.setScanType(asyncMsg.getOperType());
        // ID
	    expNormSign.setId(asyncMsg.getId());           
        // 上传时间
	    expNormSign.setUploadTime(asyncMsg.getUploadTime()); 
	    //expNormSign.setUploadTime(new Date());
        // 同步状态
	    expNormSign.setSyncStatus(asyncMsg.getAsyncStatus()); 
        // 同步次数
	    expNormSign.setCount(asyncMsg.getSyncCount());       
		return expNormSign;
	}

	/**
	 * 服务方法
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, ExpNormSignEntity expNormSign) throws PdaBusiException {
		LOG.info(expNormSign);
		PdaDeliverSignDto pdaDeliverSignDto = null;
		//276198-duhao-foss-201607291626
		expNormSign.setSignTime(new Date());
		try {
			//验证数据有效性
			this.validate(asyncMsg,expNormSign);
			//封装实体
			pdaDeliverSignDto = wrapPdaDeliverSignDto(asyncMsg, expNormSign);
			long startTime = System.currentTimeMillis();
			//pda签收出库操作
			pdaDeliverSignService.pdaExpressSign(pdaDeliverSignDto);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]快递按件正常签收接口消耗时间:"+(endTime-startTime)+"ms");
			saveScanMsgAndSignAndSerilnumber(expNormSign);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}
	
    /**
     * 保存扫描信息,签收信息,流水号信息表,快递签收分录
     * @param normPcSignScan
     */
    @Transactional(value="transactionManager")
    private void saveScanMsgAndSignAndSerilnumber(ExpNormSignEntity expNormSign){
    	LOG.info("保存快递正常签收各时间节点");
    	//保存快递正常签收操作的各时间节点
    	UserEntity userEntity = userCache.getUser(expNormSign.getScanUser());
		DeptEntity deptEntity = deptCache.getDept(userEntity.getDeptId());
		//set快递员的当前部门编码
		expNormSign.setDeptCode(deptEntity.getDeptCode());
//		try {
			deliveryPdaDao.saveExpNormalSignTimeNode(expNormSign);
//		} catch (Exception e) {
//		}
        //扫描表
        deliveryDao.saveExpNormSignScanScanMsg(expNormSign);
        //签收信息表
        try {
        	 deliveryDao.saveExpNormSignScanSign(expNormSign);
		} catch (Exception e) {
			LOG.error(LogUtil.logFormat(e), e);
		}
       
        List<PdaSignDetailEntity> pdaSignDetailEntityList = expNormSign.getLabelCodes();
        for (int i = 0; i < pdaSignDetailEntityList.size(); i++) {
            PdaSignDetailEntity detailEntity = pdaSignDetailEntityList.get(i);
            //运单号
            detailEntity.setWblCode(expNormSign.getWblCode());
            detailEntity.setId(UUIDUtils.getUUID());
            deliveryDao.saveNormPcSignScanSerilnumber(detailEntity);
        }
        //保存快递签收分录
        deliveryDao.saveExpNormSignScanSignDetail(expNormSign);
        
    }
	
	/**
	 * 封装实体
	 * @param asyncMsg
	 * @param normPcSignScan
	 * @return
	 */
	private PdaDeliverSignDto wrapPdaDeliverSignDto(AsyncMsg asyncMsg,  ExpNormSignEntity expNormSign){
	    PdaDeliverSignDto pdaDeliverSignDto = new PdaDeliverSignDto();
        //运单号
        pdaDeliverSignDto.setWaybillNo(expNormSign.getWblCode());
        //到达联编号
        pdaDeliverSignDto.setArrivesheetNo(expNormSign.getArrInfoCode());
        //用户编号
        pdaDeliverSignDto.setDriverCode(asyncMsg.getUserCode());
        //支付方式(到付)
        pdaDeliverSignDto.setPaymentType(expNormSign.getArriveType());
        
        List<PdaSignDetailEntity> labelCodes = expNormSign.getLabelCodes();
        List<PdaSignDetailDto> pdaSignDetailDtos = new ArrayList<PdaSignDetailDto>();
        PdaSignDetailDto pdaSignDetailDto = null;
        for (PdaSignDetailEntity pdaSignDetailEntity : labelCodes) {
            pdaSignDetailDto = new PdaSignDetailDto();
            //签收情况.
            pdaSignDetailDto.setSituation(pdaSignDetailEntity.getSignStatus());
            //流水号
            pdaSignDetailDto.setSerialNo(pdaSignDetailEntity.getLabelCode());
            pdaSignDetailDtos.add(pdaSignDetailDto);
        }
        //签收流水号列表
        pdaDeliverSignDto.setPdaSignDetailDtos(pdaSignDetailDtos);
        //部门编号
        pdaDeliverSignDto.setSignDeptCode(asyncMsg.getDeptCode());
        //签收件数
        pdaDeliverSignDto.setSignGoodsQty(expNormSign.getPieces());
        //扫描时间
        pdaDeliverSignDto.setSignTime(expNormSign.getScanTime());
        //车牌号
        pdaDeliverSignDto.setVehicleNo(expNormSign.getTruckCode());
        //运单号
        pdaDeliverSignDto.setWaybillNo(expNormSign.getWblCode());
        //签收情况
        pdaDeliverSignDto.setSituation(expNormSign.getSignStatus());
        //PDA串口
        pdaDeliverSignDto.setPdaSerial(expNormSign.getPdaSerial());
        //到付银行交易流水号
        pdaDeliverSignDto.setBankTradeSerail(expNormSign.getBankTradeSerail());
        //代收货款银行交易流水号
        pdaDeliverSignDto.setCodBankTradeSerail(expNormSign.getCodBankTradeSerail());
        //到付金额
        pdaDeliverSignDto.setToPayAmount(BigDecimal.valueOf(expNormSign.getArriveAmount()));
        //代收货款金额
        pdaDeliverSignDto.setCodAmount(BigDecimal.valueOf(expNormSign.getPayAmount()));
        //合并金额
        pdaDeliverSignDto.setTotalAmount(BigDecimal.valueOf(expNormSign.getTotalAmount()));
        //代收货款付款方式（现金、临欠、月结、银行卡、支票、电汇）
        pdaDeliverSignDto.setCodPaymentType(expNormSign.getPayType());
        //合并付款方式
        pdaDeliverSignDto.setTotalPaymentType(expNormSign.getTotalType());    
        //添加快递员工号
        pdaDeliverSignDto.setExpressEmpCode(asyncMsg.getUserCode());
        //添加签收人
        pdaDeliverSignDto.setDeliverymanName(expNormSign.getSignPerson());
        //是否提供定额发票
      	pdaDeliverSignDto.setIsofferInvoice(expNormSign.getIsofferInvoice());
      	//刷卡交易流水号 314587
      	pdaDeliverSignDto.setRadeSerialNo(expNormSign.getTradeSerialNo());
      	//签收人类型
    	pdaDeliverSignDto.setDeliverymanType(expNormSign.getSignPerType());
       
        return pdaDeliverSignDto;
	}

	/**
	 * 验证数据
	 * @param asyncMsg
	 * @param normPcSignScan
	 */
	private void validate(AsyncMsg asyncMsg, ExpNormSignEntity expNormSign){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		
		Argument.notNull(expNormSign, "ExpNormSignEntity");
		
        //扫描数据UUID
        Argument.hasText(expNormSign.getId(), "ExpNormSignEntity.id");
        //运单号
        Argument.hasText(expNormSign.getWblCode(), "ExpNormSignEntity.wblCode");
        //流水号
        Argument.notEmptyElements(expNormSign.getLabelCodes(), "ExpNormSignEntity.labelCodes");
        //扫描标识
        Argument.hasText(expNormSign.getScanFlag(), "ExpNormSignEntity.scanFlag");
        //扫描时间
        Argument.notNull(expNormSign.getScanTime(), "ExpNormSignEntity.scanTime");
        //车牌号
        Argument.hasText(expNormSign.getTruckCode(), "ExpNormSignEntity.truckCode");
        //到达联编号
        Argument.hasText(expNormSign.getArrInfoCode(), "ExpNormSignEntity.arrInfoCode");
        //签收人
        //Argument.hasText(expNormSign.getSignPerson(), "ExpNormSignEntity.signPerson");
        //签收情况
        Argument.hasText(expNormSign.getSignStatus(), "ExpNormSignEntity.signStatus");
        
        /**
         * 快递新增字段校验
         * */
        //到付支付类型
        Argument.hasText(expNormSign.getPayType(), "ExpNormSignEntity.payType");
        //代收货款支付类型
        Argument.hasText(expNormSign.getArriveType(), "ExpNormSignEntity.arriveType");
        //合并支付类型
        Argument.hasText(expNormSign.getTotalType(), "ExpNormSignEntity.totalType");
        //PDA串口号
        Argument.hasText(expNormSign.getPdaSerial(), "ExpNormSignEntity.pdaSerial");
        
        
        
    	
	
	}
	
	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_KD_DERY_PC_SIGN_NORM.VERSION;
	}
	
	/**
	 * 是否异同步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

	public void setPdaDeliverSignService(IPdaDeliverSignService pdaDeliverSignService) {
		this.pdaDeliverSignService = pdaDeliverSignService;
	}

	/**
	 * @param deliveryPdaDao the deliveryPdaDao to set
	 */
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
