package com.deppon.pda.bdm.module.core.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto;
import com.deppon.pda.bdm.module.core.server.dao.IMobileExceptionDao;
import com.deppon.pda.bdm.module.core.server.dao.IOcrDao;
import com.deppon.pda.bdm.module.core.server.service.IOcrService;
import com.deppon.pda.bdm.module.core.shared.domain.MobileExceptionBean;
import com.deppon.pda.bdm.module.core.shared.domain.WaybillPictureOcrDto;

/**
 * @项目：ocr二期
 * @功能：实现IOcrService方法
 * @author:218371-foss-zhaoyanjun
 * @date:20160918
 */
public class OcrService implements IOcrService{
	private static final Log LOG = LogFactory.getLog(OcrService.class);
	//OcrDao
	IOcrDao ocrDao;
	//pdaWaybillService
	IPdaWaybillService pdaWaybillService;
	//mobileExceptionDao
	private IMobileExceptionDao mobileExceptionDao;
	//返回发送是否成功
	Map<String,List<WaybillPictureOcrDto>> map=new HashMap<String, List<WaybillPictureOcrDto>>();
	
	//查询N秒前未发送给foss的运单
	@Override
	public List<WaybillPictureOcrDto> queryWaybillsWithoutSend(String jobID) {
		// TODO Auto-generated method stub
		return ocrDao.queryWaybillsWithoutSend(jobID);
	}

	public IOcrDao getOcrDao() {
		return ocrDao;
	}

	public void setOcrDao(IOcrDao ocrDao) {
		this.ocrDao = ocrDao;
	}
	
	//将未发送给foss的运单发送给foss
	@Override
	public Map<String,List<WaybillPictureOcrDto>> sendToFoss(List<WaybillPictureOcrDto> list) {
		// TODO Auto-generated method stub
		WaybillPicturePdaDto wbp;
		List<WaybillPictureOcrDto> success=new ArrayList<WaybillPictureOcrDto>();
		List<WaybillPictureOcrDto> fail=new ArrayList<WaybillPictureOcrDto>();
		for(WaybillPictureOcrDto dto:list){
			wbp=copy(new WaybillPicturePdaDto(),dto);
			try {
				ResultDto rd =pdaWaybillService.submitWaybillPictureByPDA(wbp);
				LOG.info("FOSS接口调用返回"+rd.getCode()+""+rd.getMsg());
				success.add(dto);
			} catch (BusinessException e) {
				LOG.error("FOSS接口调用返回异常",e);
				//当抛异常时，保存异常信息
				MobileExceptionBean mobileExceptionBean = new MobileExceptionBean();
				mobileExceptionBean.setUsercode(wbp.getOrderNo());//保存运单号
				mobileExceptionBean.setException(e.toString());//保存异常信息
				//调用FOSS接口失败时，将失败信息保存到数据库
				try {
					mobileExceptionDao.saveMobileException(mobileExceptionBean);
				} catch (Exception e2) {
					// TODO: handle exception
					//这里不用管
				}
				fail.add(dto);
			}
		}
		map.put("SUCCESS", success);
		map.put("FAIL", fail);
		return map;
	}
	
	//复制属性至waybillPicturePdaDto
	private WaybillPicturePdaDto copy(
			WaybillPicturePdaDto waybillPicturePdaDto, WaybillPictureOcrDto dto) {
		// TODO Auto-generated method stub
		waybillPicturePdaDto.setId(dto.getId());
		waybillPicturePdaDto.setWaybillUuid(dto.getWaybillUuid());
		waybillPicturePdaDto.setWaybillNo(dto.getWaybillNo());
		waybillPicturePdaDto.setOrderNo(dto.getOrderNo());
		waybillPicturePdaDto.setDriverCode(dto.getDriverCode());
		waybillPicturePdaDto.setBigGoodsFlag(dto.getBigGoodsFlag());
		waybillPicturePdaDto.setCashPayFlag(dto.getCashPayFlag());
		if(dto.getNewFilePath()!=null&&!"-1".equals(dto.getNewFilePath())){
			waybillPicturePdaDto.setFilePath(dto.getNewFilePath());
		}else{
			waybillPicturePdaDto.setFilePath(dto.getFilePath());
		}
		waybillPicturePdaDto.setPendgingType(dto.getPendgingType());
		waybillPicturePdaDto.setBillOrgCode(dto.getBillOrgCode());
		waybillPicturePdaDto.setRemark(dto.getRemark());
		waybillPicturePdaDto.setEquipmentNo(dto.getEquipmentNo());
		waybillPicturePdaDto.setOperator(dto.getOperator());
		waybillPicturePdaDto.setBaiDuId(dto.getBaiDuId());
		waybillPicturePdaDto.setReceiveOrgCode(dto.getReceiveOrgCode());
		waybillPicturePdaDto.setIsBigUp(dto.getIsBigUp().trim());
		waybillPicturePdaDto.setFhToOtOverQty(dto.getFhToOtOverQty());
		waybillPicturePdaDto.setOtToTtOverQty(dto.getOtToTtOverQty());
		waybillPicturePdaDto.setServiceRate(dto.getServiceRate());
		waybillPicturePdaDto.setServiceFee(dto.getServiceFee());
		waybillPicturePdaDto.setTruckCode(dto.getTruckCode());
		waybillPicturePdaDto.setMobilephone(dto.getMobilephone());
		waybillPicturePdaDto.setSpecialCustomer(dto.getSpecialCustomer());
		//添加展会字段 by 352676
		waybillPicturePdaDto.setIsExhibitCargo(dto.getIsExhibitCargo());
		return waybillPicturePdaDto;
	}
	
	//修改所有当次发送给foss的状态
	@Override
	public void updateWetherSend(List<WaybillPictureOcrDto> list) {
		// TODO Auto-generated method stub
		ocrDao.updateWetherSendForApp(list);
		ocrDao.updateWetherSendForOcr(list);
	}
	
	//插入ocr表中
	@Override
	public void insertToOcr(WaybillPicturePdaDto wbp) {
		// TODO Auto-generated method stub
		ocrDao.insertToOcr(wbp);
	}

	public IPdaWaybillService getPdaWaybillService() {
		return pdaWaybillService;
	}

	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	public IMobileExceptionDao getMobileExceptionDao() {
		return mobileExceptionDao;
	}

	public void setMobileExceptionDao(IMobileExceptionDao mobileExceptionDao) {
		this.mobileExceptionDao = mobileExceptionDao;
	}
	
	//修改当前JobID
	@Override
	public int updateJobID(int waittime, String jobId) {
		// TODO Auto-generated method stub
		return ocrDao.updateJobID(waittime,jobId);
	}

	//修改所有当次发送给foss的状态为false
	@Override
	public void updateWetherSendForFail(List<WaybillPictureOcrDto> list) {
		// TODO Auto-generated method stub
		ocrDao.updateWetherSendForFailForApp(list);
		ocrDao.updateWetherSendForFailForOcr(list);
	}
	
	//查询是否有运单没有发送给foss
	@Override
	public int waybillNoSend(int waittime) {
		// TODO Auto-generated method stub
		return ocrDao.waybillNoSend(waittime);
	}
}
