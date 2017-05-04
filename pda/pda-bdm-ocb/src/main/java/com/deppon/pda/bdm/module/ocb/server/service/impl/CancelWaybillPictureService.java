package com.deppon.pda.bdm.module.ocb.server.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPictureLogEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.OcbConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.ocb.server.dao.IUploadImageDao;
import com.deppon.pda.bdm.module.ocb.shared.domain.UploadProBean;

/**  
 * 作者：xiaolongwu
 * 描述：集中开单，运单在线撤销功能
 * 包名：com.deppon.pda.bdm.module.ocb.server.service.impl
 * 时间：2015-1-6 下午2:17:33
 */
public class CancelWaybillPictureService implements IBusinessService<ResultDto, UploadProBean>{

	private static final Log LOG = LogFactory.getLog(CancelWaybillPictureService.class);
	private IPdaWaybillService pdaWaybillService;
	private IUploadImageDao uploadImageDao;
	
	@Override
	public UploadProBean parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		UploadProBean up = JSONObject.parseObject(asyncMsg.getContent(), UploadProBean.class);
		return up;
	}

	@Transactional
	@Override
	public ResultDto service(AsyncMsg asyncMsg, UploadProBean param)
			throws PdaBusiException {
		//调用 FOSS接口
		FileUtil fileUtil = new FileUtil();
		WaybillPictureLogEntity logEntity=new WaybillPictureLogEntity();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		try {
			//获取调用接口之前的时间
			long fossStart=System.currentTimeMillis();
			Date dateFossStart = new Date(fossStart);
			logEntity.setStartFossTime(formatter.format(dateFossStart));
			
			//调用FOSS接口
			ResultDto rd =pdaWaybillService.cancelWaybillPictureByPDA(param.getWblcode(),asyncMsg.getUserCode());
			
			//获取调用接口结束的时间
			long fossEnd=System.currentTimeMillis();
			Date dateFossEnd = new Date(fossEnd);
			logEntity.setEndFossTime(formatter.format(dateFossEnd));
			LOG.info("FOSS接口调用返回"+rd.getCode()+""+rd.getMsg());
			return rd;
		} catch (Exception e) {
			LOG.error("FOSS接口调用返回异常",e);
			throw new BusinessException("FOSS接口调用返回异常:"+e.getMessage());
		}finally{
			//删除运单目录
			//获取调用接口之前的时间
			long queryStart=System.currentTimeMillis();
			Date dateQuerySart = new Date(queryStart);
			logEntity.setQueryFilePathStartTime(formatter.format(dateQuerySart));
			
			//1、得到文件的绝对路径
			String fPath = uploadImageDao.getUploadFileByWblcode(param.getWblcode());
			//2、得到文件的上一层目录，也就是运单目录
			File file = new File(fPath);
			String parentPath = file.getParent();
			//3、不管成功还是失败，删除运单目录以及目录下的所有文件
			try {
				fileUtil.deleteDirectory(parentPath);
			} catch (Exception e2) {
				LOG.info("删除文件失败");
			}
			//获取调用接口结束的时间
			long queryEnd=System.currentTimeMillis();
			Date dateQueryEnd = new Date(queryEnd);
			logEntity.setQueryFilePathEndTime(formatter.format(dateQueryEnd));
			
			//author:245960 Date:2015-08-06 comment：2015-08-06袁金彪需求撤销运单的时候删除pda.t_app_uploadfilesnippet表中的数据。
			//2015-08-12由于没有做版本需求，放到下一个版本测试，注释掉，下个版本放开：
			//获取调用接口之前的时间
			long deleteStart=System.currentTimeMillis();
			Date dateDeleteStart = new Date(deleteStart);
			logEntity.setDeleteFilePathStartTime(formatter.format(dateDeleteStart));
			
			uploadImageDao.deleteWaybillDateInTable(param.getWblcode());
			
			//获取调用接口结束的时间
			long deleteEnd=System.currentTimeMillis();
			Date dateDeleteEnd = new Date(deleteEnd);
			logEntity.setDeleteFilePathEndTime(formatter.format(dateDeleteEnd));
			
			//保存日志信息至数据库
			uploadImageDao.saveWaybillPictureTimeLog(logEntity);
		}
		
	}

	/**
	 * @author：xiaolongwu
	 * @description：操作类型OCB_06，运单在线撤销功能
	 * @parameters：void
	 * @return：OCB_06
	 * @time：2015-1-6 下午2:16:42
	 */
	@Override
	public String getOperType() {
		return OcbConstant.OPER_TYPE_OCB_WAYBILL_CAN.VERSION;
	}

	/**
	 * @author：xiaolongwu
	 * @description：同步
	 * @parameters：void
	 * @return：false
	 * @time：2015-1-5 上午10:58:04
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * @parameters：注入pdaWaybillService
	 * @return：
	 */
	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	/**
	 * @parameters：
	 * @return：
	 */
	public void setUploadImageDao(IUploadImageDao uploadImageDao) {
		this.uploadImageDao = uploadImageDao;
	}
	
	
	
	
}
