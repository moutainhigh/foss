package com.deppon.pda.bdm.module.foss.delivery.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverSignService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSignDetailDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryDao;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.ExcpSignByPcScanEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PdaSignDetailEntity;

/**
 * 
  * @ClassName ExcpSignbyPcService 
  * @Description 按件异常签收 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class ExcpSignbyPcService implements IBusinessService<Void, ExcpSignByPcScanEntity> {
	
	private static final Log LOG = LogFactory.getLog(ExcpSignbyPcService.class);
	
	private IPdaDeliverSignService pdaDeliverSignService;
	private IDeliveryDao deliveryDao;
	
	public void setDeliveryDao(IDeliveryDao deliveryDao) {
		this.deliveryDao = deliveryDao;
	}

	/**
	 * 解析包体
	 */
	@Override
	public ExcpSignByPcScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		ExcpSignByPcScanEntity excpSignByPcScan = JsonUtil.parseJsonToObject(ExcpSignByPcScanEntity.class, asyncMsg.getContent());
		// 部门
		excpSignByPcScan.setDeptCode(asyncMsg.getDeptCode());
	    // PDA编号
		excpSignByPcScan.setPdaCode(asyncMsg.getPdaCode());
		// user编号
		excpSignByPcScan.setScanUser(asyncMsg.getUserCode());
		// 操作类型
		excpSignByPcScan.setScanType(asyncMsg.getOperType());
		// ID
		excpSignByPcScan.setId(asyncMsg.getId());
		// 上传时间
		excpSignByPcScan.setUploadTime(asyncMsg.getUploadTime());
		// 同步状态
		excpSignByPcScan.setSyncStatus(asyncMsg.getAsyncStatus()); 
		// 同步次数	
		excpSignByPcScan.setCount(asyncMsg.getSyncCount());		   
		return excpSignByPcScan;
	}

	/**
	 * 服务方法
	 */
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, ExcpSignByPcScanEntity excpSignByPcScan) throws PdaBusiException {
		LOG.info(excpSignByPcScan);
		PdaDeliverSignDto pdaDeliverSignDto = null;
		//验证数据有效性
		this.validate(asyncMsg,excpSignByPcScan);
		//封装实体
		pdaDeliverSignDto = wrapPdaDeliverSignDto(asyncMsg, excpSignByPcScan);
		long startTime = System.currentTimeMillis();
		//调用派送签收接口
		try {
			pdaDeliverSignService.pdaSign(pdaDeliverSignDto);
		} catch (BusinessException e) {
			LOG.info("调用接送货接口失败"+e.getMessage());
			try{
				ObjectMapper map=new ObjectMapper();
				LOG.info("pdaDeliverSignDto的参数为："+map.writeValueAsString(pdaDeliverSignDto));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
			}
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}

		long endTime = System.currentTimeMillis();
		QueueMonitorInfo.addTotalFossTime(endTime-startTime);
		LOG.info("[asyncinfo]按件签收接口消耗时间:"+(endTime-startTime)+"ms");
		saveScanMsgAndSignAndSerilnumberAndPic(excpSignByPcScan);
		return null;
	}

	/**
	 * 保存扫描信息,签收信息,流水号信息表,图片信息表
	 * @param normPcSignScan
	 */
	@Transactional
	private void saveScanMsgAndSignAndSerilnumberAndPic(ExcpSignByPcScanEntity excpSignByPcScan){
		//扫描表
		deliveryDao.saveExcpSignByPcScanScanMsg(excpSignByPcScan);
		//签收信息表
		deliveryDao.saveExcpSignByPcScanSign(excpSignByPcScan);
		List<PdaSignDetailEntity> pdaSignDetailEntityList = excpSignByPcScan.getLabelCodes();
		for (int i = 0; i < pdaSignDetailEntityList.size(); i++) {
			PdaSignDetailEntity detailEntity = pdaSignDetailEntityList.get(i);
			//运单号
			detailEntity.setWblCode(excpSignByPcScan.getWblCode());
			detailEntity.setId(UUIDUtils.getUUID());
			deliveryDao.saveExcpSignByPcScanSerilnumber(detailEntity);
			/*
			List<PictureEntity> pictureEntities = detailEntity.getPictures();
			for (int j = 0; j < pictureEntities.size(); j++) {
				PictureEntity pictureEntity = pictureEntities.get(j);
				//运单号
				String wblCode = excpSignByPcScan.getWblCode();
				//流水号
				String labelCode = detailEntity.getLabelCode();
				
				//文件名称为,   运单号_流水号_当前文件序号
				StringBuffer fileName = new StringBuffer();
				fileName.append(wblCode).append("_");
				fileName.append(labelCode).append(j);
				//输出文件
				String pictureUrl = FileUtils.writePicture(pictureEntity.getPicture(), fileName.toString());
				pictureEntity.setId(UUIDUtils.getUUID());
				pictureEntity.setPictureUrl(pictureUrl);
				pictureEntity.setSerilnumberId(detailEntity.getId());
				deliveryDao.saveExcpSignByPcScanPicture(pictureEntity);
			}
			*/
		}
	}
	
	/**
	 * 拼装实体
	 * @param asyncMsg
	 * @param excpSignByPcScan
	 * @return
	 */
	private PdaDeliverSignDto wrapPdaDeliverSignDto(AsyncMsg asyncMsg, ExcpSignByPcScanEntity excpSignByPcScan){
		PdaDeliverSignDto pdaDeliverSignDto = new PdaDeliverSignDto();
		//运单号
		pdaDeliverSignDto.setWaybillNo(excpSignByPcScan.getWblCode());
		//用户编号
		pdaDeliverSignDto.setDriverCode(asyncMsg.getUserCode());
		//支付方式
		pdaDeliverSignDto.setPaymentType(excpSignByPcScan.getPayType());
		
		List<PdaSignDetailEntity> labelCodes = excpSignByPcScan.getLabelCodes();
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
		pdaDeliverSignDto.setSignGoodsQty(excpSignByPcScan.getPieces());
		/*
		 * wwn 3013-05-31 16:16
		 * 2014 3.22 取消过滤
		 * */
		//签收备注
		pdaDeliverSignDto.setSignNote(excpSignByPcScan.getExcpReason());
		//扫描时间
		pdaDeliverSignDto.setSignTime(excpSignByPcScan.getScanTime());
		//运单号
		pdaDeliverSignDto.setWaybillNo(excpSignByPcScan.getWblCode());
		//到达联编号
		pdaDeliverSignDto.setArrivesheetNo(excpSignByPcScan.getArrInfoCode());
		//签收情况
		pdaDeliverSignDto.setSituation(excpSignByPcScan.getSignStatus());
		 //是否提供定额发票
      	pdaDeliverSignDto.setIsofferInvoice(excpSignByPcScan.getIsofferInvoice());
        //银行交易流水号--代收货款流水号
       	pdaDeliverSignDto.setCodBankTradeSerail(excpSignByPcScan.getCodBankTradeSerail());
     	// 银行交易流水号--到付流水号
       	pdaDeliverSignDto.setBankTradeSerail(excpSignByPcScan.getBankTradeSerail());
		return pdaDeliverSignDto;
	}

	/**
	 * 数据合法性验证
	 * @param asyncMsg
	 * @param excpSignByPcScan
	 */
	private void validate(AsyncMsg asyncMsg, ExcpSignByPcScanEntity excpSignByPcScan){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		
		Argument.notNull(excpSignByPcScan, "ExcpSignByPcScanEntity");
		//扫描数据UUID
		Argument.hasText(excpSignByPcScan.getId(), "ExcpSignByPcScanEntity.id");
		//运单号
		Argument.hasText(excpSignByPcScan.getWblCode(), "ExcpSignByPcScanEntity.wblCode");
		//流水号
		Argument.notEmpty(excpSignByPcScan.getLabelCodes(), "ExcpSignByPcScanEntity.labelCodes");
		//扫描标识
		Argument.hasText(excpSignByPcScan.getScanFlag(), "ExcpSignByPcScanEntity.scanFlag");
		//扫描时间
		Argument.notNull(excpSignByPcScan.getScanTime(), "ExcpSignByPcScanEntity.scanTime");
		//异常原因
		Argument.hasText(excpSignByPcScan.getExcpReason(), "ExcpSignByPcScanEntity.excpReason");
		//签收时间
		Argument.notNull(excpSignByPcScan.getSignTime(), "ExcpSignByPcScanEntity.signTime");
		//签收人
		Argument.hasText(excpSignByPcScan.getSignPerson(), "ExcpSignByPcScanEntity.signPerson");
		//付款方式	
//		Argument.hasText(excpSignByPcScan.getPayType(), "ExcpSignByPcScanEntity.payType");
		//签收情况
		Argument.hasText(excpSignByPcScan.getSignStatus(), "ExcpSignByPcScanEntity.signStatus");
		//到达联编号
		Argument.hasText(excpSignByPcScan.getArrInfoCode(), "ExcpSignByPcScanEntity.arrInfoCode");
	}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_DERY_SIGN_EXCP.VERSION;
	}

	/**
	 * 异步 
	 * true
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

	public void setPdaDeliverSignService(IPdaDeliverSignService pdaDeliverSignService) {
		this.pdaDeliverSignService = pdaDeliverSignService;
	}
	
}
