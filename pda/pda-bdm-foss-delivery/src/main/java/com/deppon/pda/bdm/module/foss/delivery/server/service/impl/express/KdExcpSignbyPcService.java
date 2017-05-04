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
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryDao;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryPdaDao;
import com.deppon.pda.bdm.module.foss.delivery.server.service.impl.ExcpSignbyPcService;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.ExpExcpSignEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PdaSignDetailEntity;

/**
 * 快递按件异常签收
 * @author Administrator
 *
 */
public class KdExcpSignbyPcService implements IBusinessService<Void, ExpExcpSignEntity> {

	private static final Log LOG = LogFactory.getLog(ExcpSignbyPcService.class);
	
	private IPdaDeliverSignService pdaDeliverSignService;
    private IDeliveryDao deliveryDao;
	private IDeliveryPdaDao deliveryPdaDao;
    private UserCache userCache;
	
	private DeptCache deptCache;
    
	/**
	 * 解析包体
	 */
	@Override
	public ExpExcpSignEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
	    ExpExcpSignEntity expExcpSign = JsonUtil.parseJsonToObject(ExpExcpSignEntity.class, asyncMsg.getContent());
	    // 部门
	    expExcpSign.setDeptCode(asyncMsg.getDeptCode());
        // PDA编号
	    expExcpSign.setPdaCode(asyncMsg.getPdaCode());
        // user编号
	    expExcpSign.setScanUser(asyncMsg.getUserCode());
        // 操作类型
	    expExcpSign.setScanType(asyncMsg.getOperType());
        // ID
	    expExcpSign.setId(asyncMsg.getId());
        // 上传时间 
	    expExcpSign.setUploadTime(asyncMsg.getUploadTime());
        // 同步状态
	    expExcpSign.setSyncStatus(asyncMsg.getAsyncStatus()); 
        // 同步次数 
	    expExcpSign.setCount(asyncMsg.getSyncCount());       
		return expExcpSign;
	}

	/**
	 * 服务方法
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, ExpExcpSignEntity expExcpSign) throws PdaBusiException {
		LOG.info(expExcpSign);
		PdaDeliverSignDto pdaDeliverSignDto = null;
		//276198-duhao-foss-201607291628
		expExcpSign.setSignTime(new Date());
		try {
			//验证数据有效性
			this.validate(asyncMsg,expExcpSign);
			//封装实体
			pdaDeliverSignDto = wrapPdaDeliverSignDto(asyncMsg, expExcpSign);
			long startTime = System.currentTimeMillis();
			//调用派送签收接口
			pdaDeliverSignService.pdaExpressSign(pdaDeliverSignDto);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]按件签收接口消耗时间:"+(endTime-startTime)+"ms");	
			 saveScanMsgAndSignAndSerilnumberAndPic(expExcpSign);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}
	
	   /**
     * 保存扫描信息,签收信息,流水号信息表,图片信息表,快递签收分录表
     * @param normPcSignScan
     */
    @Transactional
    private void saveScanMsgAndSignAndSerilnumberAndPic(ExpExcpSignEntity expExcpSign){
    	LOG.info("保存快递异常签收各时间节点");
    	//保存快递异常签收操作的各时间节点
    	UserEntity userEntity = userCache.getUser(expExcpSign.getScanUser());
		DeptEntity deptEntity = deptCache.getDept(userEntity.getDeptId());
		//set快递员的当前部门编码
		expExcpSign.setDeptCode(deptEntity.getDeptCode());
//		try {
			deliveryPdaDao.saveExpAbnormalSignTimeNode(expExcpSign);
//		} catch (Exception e) {
//		}
    	
        //扫描表
        deliveryDao.saveExpExcpSignScanScanMsg(expExcpSign);
        //签收信息表
        deliveryDao.saveExpExcpSignScanSign(expExcpSign);
        List<PdaSignDetailEntity> pdaSignDetailEntityList = expExcpSign.getLabelCodes();
        for (int i = 0; i < pdaSignDetailEntityList.size(); i++) {
            PdaSignDetailEntity detailEntity = pdaSignDetailEntityList.get(i);
            //运单号
            detailEntity.setWblCode(expExcpSign.getWblCode()); 
            detailEntity.setId(UUIDUtils.getUUID());
            deliveryDao.saveExcpSignByPcScanSerilnumber(detailEntity);         
        }
        //保存快递签收分录
        deliveryDao.saveExpExcpSignScanSignDetail(expExcpSign);
        
    }
    
	/**
	 * 拼装实体
	 * @param asyncMsg
	 * @param excpSignByPcScan
	 * @return
	 */
	private PdaDeliverSignDto wrapPdaDeliverSignDto(AsyncMsg asyncMsg, ExpExcpSignEntity expExcpSign){
	      PdaDeliverSignDto pdaDeliverSignDto = new PdaDeliverSignDto();
	        //运单号
	        pdaDeliverSignDto.setWaybillNo(expExcpSign.getWblCode());
	        //用户编号
	        pdaDeliverSignDto.setDriverCode(asyncMsg.getUserCode());
	        //支付方式(到付)
	        pdaDeliverSignDto.setPaymentType(expExcpSign.getArriveType());
	        
	        List<PdaSignDetailEntity> labelCodes = expExcpSign.getLabelCodes();
	        List<PdaSignDetailDto> pdaSignDetailDtos = new ArrayList<PdaSignDetailDto>();
	        PdaSignDetailDto pdaSignDetailDto = null;
	        for (PdaSignDetailEntity pdaSignDetailEntity : labelCodes) {
	            pdaSignDetailDto = new PdaSignDetailDto();
	            //签收情况.
	            pdaSignDetailDto.setSituation(pdaSignDetailEntity.getSignStatus());
	            //流水号
	            pdaSignDetailDto.setSerialNo(pdaSignDetailEntity.getLabelCode());
	        
	           //是否内物缺少
	            pdaSignDetailDto.setGoodShorts(pdaSignDetailEntity.getGoodShorts());
	            //异常签收原因，一个流水号对应一个签收情况
	            pdaSignDetailDto.setSignSituation(pdaSignDetailEntity.getSignSituation());
	            pdaSignDetailDtos.add(pdaSignDetailDto);
	        }
	        pdaDeliverSignDto.setPdaSignDetailDtos(pdaSignDetailDtos);
	        //签收部门编码
	        pdaDeliverSignDto.setSignDeptCode(asyncMsg.getDeptCode());
	        //签收件数
	        pdaDeliverSignDto.setSignGoodsQty(expExcpSign.getPieces());

	        //签收备注
	        pdaDeliverSignDto.setSignNote(expExcpSign.getExcpReason());
	        //扫描时间
	        pdaDeliverSignDto.setSignTime(expExcpSign.getScanTime());
	        //运单号
	        pdaDeliverSignDto.setWaybillNo(expExcpSign.getWblCode());
	        //到达联编号
	        pdaDeliverSignDto.setArrivesheetNo(expExcpSign.getArrInfoCode());
	        //签收情况
	        pdaDeliverSignDto.setSituation(expExcpSign.getSignStatus());
	        //PDA串口
	        pdaDeliverSignDto.setPdaSerial(expExcpSign.getPdaSerial());
	        //到付银行交易流水号
	        pdaDeliverSignDto.setBankTradeSerail(expExcpSign.getBankTradeSerail());
	        //代收货款银行交易流水号
	        pdaDeliverSignDto.setCodBankTradeSerail(expExcpSign.getCodBankTradeSerail());
	        //到付金额
            pdaDeliverSignDto.setToPayAmount(BigDecimal.valueOf(expExcpSign.getArriveAmount()));
	        //代收货款金额
            pdaDeliverSignDto.setCodAmount(BigDecimal.valueOf(expExcpSign.getPayAmount()));
            //合并金额
            pdaDeliverSignDto.setTotalAmount(BigDecimal.valueOf(expExcpSign.getTotalAmount()));
	        //代收货款付款方式（现金、临欠、月结、银行卡、支票、电汇）
            pdaDeliverSignDto.setCodPaymentType(expExcpSign.getPayType());
	        //合并付款方式
            pdaDeliverSignDto.setTotalPaymentType(expExcpSign.getTotalType());	  
            //添加快递员工号
            pdaDeliverSignDto.setExpressEmpCode(asyncMsg.getUserCode());
            //添加签收人
            pdaDeliverSignDto.setDeliverymanName(expExcpSign.getSignPerson());
            //是否提供定额发票
          	pdaDeliverSignDto.setIsofferInvoice(expExcpSign.getIsofferInvoice());
          	//刷卡交易流水号 314587
          	pdaDeliverSignDto.setRadeSerialNo(expExcpSign.getTradeSerialNo());
          	//签收人类型
          	pdaDeliverSignDto.setDeliverymanType(expExcpSign.getSignPerType());
	        return pdaDeliverSignDto;
	}

	/**
	 * 数据合法性验证
	 * @param asyncMsg
	 * @param excpSignByPcScan
	 */
	private void validate(AsyncMsg asyncMsg, ExpExcpSignEntity expExcpSign){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		
	       Argument.notNull(expExcpSign, "ExpExcpSignEntity");
	        //扫描数据UUID
	        Argument.hasText(expExcpSign.getId(), "ExpExcpSignEntity.id");
	        //运单号
	        Argument.hasText(expExcpSign.getWblCode(), "ExpExcpSignEntity.wblCode");
	        //流水号
	        Argument.notEmpty(expExcpSign.getLabelCodes(), "ExpExcpSignEntity.labelCodes");
	        //扫描标识
	        Argument.hasText(expExcpSign.getScanFlag(), "ExpExcpSignEntity.scanFlag");
	        //扫描时间
	        Argument.notNull(expExcpSign.getScanTime(), "ExpExcpSignEntity.scanTime");
	        //异常原因
	        Argument.hasText(expExcpSign.getExcpReason(), "ExpExcpSignEntity.excpReason");
	        //签收时间
	        Argument.notNull(expExcpSign.getSignTime(), "ExpExcpSignEntity.signTime");
	        //签收人
	       // Argument.hasText(expExcpSign.getSignPerson(), "ExpExcpSignEntity.signPerson");
	        //付款方式  
	        //签收情况
	        Argument.hasText(expExcpSign.getSignStatus(), "ExpExcpSignEntity.signStatus");
	        //到达联编号
	        Argument.hasText(expExcpSign.getArrInfoCode(), "ExpExcpSignEntity.arrInfoCode");
	        
	        /**
	         * 快递新增字段校验
	         * */
	        //到付支付类型
	        Argument.hasText(expExcpSign.getPayType(), "ExpNormSignEntity.payType");
	        //代收货款支付类型
	        Argument.hasText(expExcpSign.getArriveType(), "ExpNormSignEntity.arriveType");
	        //合并支付类型
	        Argument.hasText(expExcpSign.getTotalType(), "ExpNormSignEntity.totalType");
	        //PDA串口号
	        Argument.hasText(expExcpSign.getPdaSerial(), "ExpNormSignEntity.pdaSerial");     	
	}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_KD_DERY_SIGN_EXCP.VERSION;
	}
	
	/**
	 * @description：是否异步
	 * @parameters：void
	 * @return：true  异步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

	public void setPdaDeliverSignService(IPdaDeliverSignService pdaDeliverSignService) {
		this.pdaDeliverSignService = pdaDeliverSignService;
	}
	
	 public void setDeliveryDao(IDeliveryDao deliveryDao) {
	        this.deliveryDao = deliveryDao;
	    }

	/**
	 * 注入deliveryPdaDao
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
