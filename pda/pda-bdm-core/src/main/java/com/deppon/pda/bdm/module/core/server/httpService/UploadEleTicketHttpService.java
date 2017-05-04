package com.deppon.pda.bdm.module.core.server.httpService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.deppon.ar.bamp.client.constant.Constants;
import com.deppon.ar.bamp.client.listener.RequestContainer;
import com.deppon.ar.bamp.client.util.ClientLogUtil;
import com.deppon.ar.bamp.client.util.ErrorInfoUtil;
import com.deppon.ar.bamp.client.util.MessageSendUtil;
import com.deppon.ar.bamp.common.dto.ErrorBizLog;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.pda.bdm.module.core.server.monitor.MonitorControl;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.server.service.impl.ValidateService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.constants.OperationConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.PdaInfo;
import com.deppon.pda.bdm.module.core.shared.domain.ReqData;
import com.deppon.pda.bdm.module.core.shared.domain.ReqJsonData;
import com.deppon.pda.bdm.module.core.shared.domain.Result;
import com.deppon.pda.bdm.module.core.shared.exception.IPdaException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ServiceNotLoadException;
import com.deppon.pda.bdm.module.core.shared.util.BeanFactory;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;
import com.deppon.pda.bdm.module.core.shared.util.ExceptionFilterUtil;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;

public class UploadEleTicketHttpService  extends HttpServlet {

	private static final long serialVersionUID = -7254356943253309693L;
	private static final int SIZE_THRESHOLD = 1048576;

	private static Logger log = Logger.getLogger(UploadEleTicketHttpService.class);
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long start = System.currentTimeMillis();
		Result rs = new Result();
		String requetJson = "";
		try {
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			// threshold 极限、临界值，即硬盘缓存  1M  1024 * 1024
			diskFactory.setSizeThreshold(SIZE_THRESHOLD);
			ServletFileUpload upload = new ServletFileUpload(diskFactory);
			// 设置允许上传的最大文件大小 4M
			upload.setSizeMax(SIZE_THRESHOLD);
			List<FileItem> fileItems = upload.parseRequest(request);
			Iterator<FileItem> iter = (Iterator) fileItems.iterator();
			
			FileItem  fileItem = null;
			do {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString();
					if("uploadJsonPart".equals(name)){
						requetJson = value;
					}
				} else {
					fileItem = item;
				}
			} while (iter.hasNext());
			
			//校验参数，失败就不保存文件到本地
			rs = checkInputParma(requetJson,fileItem,rs);
			if(rs.getRetStatus() == Result.SUCCESS){
				rs = savePicToDisk(requetJson,fileItem,rs);	
				ReqData requestjson = JSON.parseObject(requetJson,ReqData.class);
				Result reqRs = encapsulateInnerResult(rs,
						requestjson.getIsMobile());
				rs = reqRs;
			}
			
			if(rs.getRetStatus() == Result.SUCCESS){
				List<Result> resultList = doService(rs,requetJson);
				rs = encapsulateResult(resultList);
			}
		} catch (RuntimeException e) {
			
			if (ErrorInfoUtil.getErrorSimpleName(e)!=null){
				String errorname;
				if (e.getCause()==null){
					//当这个异常没有原因时,则直接取异常包名和类名
					errorname = e.getClass().getName();
				}else{
					errorname = e.getCause().getClass().getName();
				}
				
				if(ErrorInfoUtil.getErrorSimpleName(e).indexOf("ClientAbortException") != -1){
					
					log.error(LogUtil.logFormat(e));
					rs = dealException(e);	
				}else if (/*ErrorInfoUtil.filterBusinessException(AppProfile.getInstance().getAppCode(),errorname)
						||*/ ExceptionFilterUtil.filterException(errorname)
						){
					//如果属于业务异常,则放弃这个错误消息
					log.error(LogUtil.logFormat(e));
					rs = dealException(e);	
				}else{
					ErrorBizLog ebLog = ClientLogUtil.createErrorBizLog(e,Constants.BUSINESS);
					MessageSendUtil.asyncSendErrorBizLog(ebLog);
					RequestContainer.setSuccessed(Constants.FAIL);
					log.error(LogUtil.logFormat(e));
					rs = dealException(e);				
				}
			}else{
				//当Errorname为null时，也放弃这条错误消息
				log.error(LogUtil.logFormat(e));
				rs = dealException(e);
			}
				
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			if (ErrorInfoUtil.getErrorSimpleName(e)!=null){
				String errorname;
				if (e.getCause()==null){
					//当这个异常没有原因时,则直接取异常包名和类名
					errorname = e.getClass().getName();
				}else{
					errorname = e.getCause().getClass().getName();
				}
				
				if(ErrorInfoUtil.getErrorSimpleName(e).indexOf("ClientAbortException") != -1){
					
					log.error(LogUtil.logFormat(e));
					rs = dealException(e);	
				}else if (/*ErrorInfoUtil.filterBusinessException(AppProfile.getInstance().getAppCode(),errorname)
						||*/ ExceptionFilterUtil.filterException(errorname)
						){
					//如果属于业务异常,则放弃这个错误消息
					log.error(LogUtil.logFormat(e));
					rs = dealException(e);	
					log.info("rs"+rs);
				}else{
					ErrorBizLog ebLog = ClientLogUtil.createErrorBizLog(e,Constants.BUSINESS);
					MessageSendUtil.asyncSendErrorBizLog(ebLog);
					RequestContainer.setSuccessed(Constants.FAIL);
					log.error(LogUtil.logFormat(e));
					rs = dealException(e);				
				}
			}else{
				//当Errorname为null时，也放弃这条错误消息
				log.error(LogUtil.logFormat(e));
				rs = dealException(e);
			}
		} 
		// 将返回值转换成JSON格式
		String msg = JsonUtil.encapsulateJsonObject(rs);
		log.info("BDM端JSON输出：" + msg);
		this.responseResult(msg, response);
		log.info("PDA后台接口所用时间:"+(System.currentTimeMillis()-start));
	}
	
	private Result checkInputParma(String requetJson,FileItem  fileItem,Result rs){
		//先文件效验
		if(fileItem == null || fileItem.getSize() == 0){
			rs.setRetStatus(Result.ERROR);
			rs.setErrCode(ExceptionConstant.ERRCODE_SYS_INVALID_ARG);
			rs.setErrMsg("imageFile is null");
			return rs;
		}
		//PDA校验
		if(StringUtil.isNotBlank(requetJson)){
			ReqData reqData = JsonUtil.parseJsonToObject(ReqData.class, requetJson);
			
			if(!reqData.getReqData().isEmpty()){
				ReqJsonData reqJsonData = reqData.getReqData().get(0);
				PdaInfo pdaInfo = JsonUtil.parseJsonToObject(PdaInfo.class,reqJsonData.getPdaInfo());
				ValidateService validateService = (ValidateService) BeanFactory.getBean("validateService");
				validateService.check(pdaInfo);
			}
			rs.setRetStatus(Result.SUCCESS);
		} else {
			
			rs.setErrCode(ExceptionConstant.ERRCODE_SYS_INVALID_ARG);
			rs.setErrMsg("uploadJsonPart is null");
		}
		
		return rs;
	}
	/**
	 * 生成文件夹
	 * @param requetJson
	 * @param fileItem
	 * @param rs
	 * @return
	 */
	private Result savePicToDisk(String requetJson,FileItem  fileItem,Result rs) {
		//已经检验过
		ReqData reqData = JsonUtil.parseJsonToObject(ReqData.class, requetJson);
		ReqJsonData reqJsonData = reqData.getReqData().get(0);
		String pdaInfoStr = reqJsonData.getPdaInfo();
		// 解析包头
		AsyncMsg asyncMsg = JsonUtil.parseJsonToObject(AsyncMsg.class,pdaInfoStr);
		asyncMsg.setContent(reqJsonData.getBody());
		HashMap<String,String> paramMap = JsonUtil.parseJsonToObject(HashMap.class,asyncMsg.getContent());
		String serialNo = paramMap.get("serialNo");
		StringBuilder builder = new StringBuilder();
		//保存图片路径  开会确定路径 /opt/swipeimage/ + 年月日   + / + 流水号 + /
		String filePath = builder.append(Constant.ELECTRONIC_TICKET_PATH)
				.append(DateUtils.getNowDateStr()).append("/").append(UUID.randomUUID()).append("/").toString();
		try {
			File dir = new File(filePath);
			if(!dir.exists()){
				boolean is = dir.mkdirs();
				if(!is){
					log.info("************************* “ 创建文件夹失败。电子小票无法上传 ” 位置：loadEleTicketHttpService.savePicToDisk()******************************");
					throw new RuntimeException("创建文件夹失败。电子小票无法上传");
				}
			}
			filePath = builder.append(serialNo).append(".jpg").toString();
			File pic = new File(filePath);
			if(!pic.exists()){
				pic.createNewFile();
			}
			fileItem.write(pic);
			
			rs.setRetValue(filePath);
		} catch(IOException e) {
			rs.setRetStatus(Result.ERROR);
			rs.setErrCode(ExceptionConstant.ERRCODE_BASE);
			rs.setErrMsg(e.getMessage());
		} catch (Exception e) {
			rs.setRetStatus(Result.ERROR);
			rs.setErrCode(ExceptionConstant.ERRCODE_BASE);
			rs.setErrMsg(e.getMessage());
		}
		return rs;
	}
	
	private List<Result> doService(Result result, String requetJson) {
		List<Result> resultList = new ArrayList<Result>();
		String picPath = result.getRetValue().toString();
		Result res = JSON.parseObject(picPath,Result.class);
		picPath = res.getRetValue().toString();
		ReqData reqData = JsonUtil.parseJsonToObject(ReqData.class, requetJson);
		for (ReqJsonData reqJsonData : reqData.getReqData()) {
			try {
				String pdaInfoStr = reqJsonData.getPdaInfo();
				// 解析包头
				AsyncMsg asyncMsg = JsonUtil.parseJsonToObject(AsyncMsg.class,
						pdaInfoStr);
				asyncMsg.setContent(reqJsonData.getBody());
				String serviceBeanName = OperationConstant.OPER_SERVICE_MAP.get(asyncMsg.getOperType());
				IBusinessService service = (IBusinessService) BeanFactory.getBean(serviceBeanName);

				if (service == null) {
					log.error("服务类未加载，类型为：" + asyncMsg.getOperType());
					throw new ServiceNotLoadException();
				}

				// 同步接入BAM监控
				String classname = "";
				String methodname = "";
				if (BeanFactory.getBean(serviceBeanName) != null) {
					classname = BeanFactory.getBean(serviceBeanName).toString();
					if (classname.indexOf("@") != -1)
						methodname = classname.substring(0,
								classname.indexOf("@"));
				}
				RequestContainer.setMethodName(new StringBuffer()
						.append(methodname).append(".service").toString());
				RequestContainer.setNamespace("/pkp-pda-itf");
				if (asyncMsg != null) {
					RequestContainer.setEmpCode(asyncMsg.getUserCode());
				}
				//特殊处理  因为一张图片可能对应多张快递单，所以值需要保存一张图片就行，后台传一样的数据
				HashMap<String,String> addImageUrl = JsonUtil.parseJsonToObject(HashMap.class, asyncMsg.getContent());
				addImageUrl.put("imageUrl", picPath);
				asyncMsg.setContent(JsonUtil.encapsulateJsonObject(addImageUrl));
				
				Object param = service.parseBody(asyncMsg);
				Object obj = service.service(asyncMsg, param);

				// 将数据提交至监控
				MonitorControl.put(asyncMsg, Constant.MONITOR.NORMAL);
				Result reqRs = encapsulateInnerResult(obj,reqData.getIsMobile());
				resultList.add(reqRs);

			} catch (Throwable e) {

				if (ErrorInfoUtil.getErrorSimpleName(e) != null) {
					String errorname;
					if (e.getCause() == null) {
						// 当这个异常没有原因时,则直接取异常包名和类名
						errorname = e.getClass().getName();
					} else {
						errorname = e.getCause().getClass().getName();
					}

					if (ErrorInfoUtil.getErrorSimpleName(e).indexOf("ClientAbortException") != -1) {

						log.error(LogUtil.logFormat(e));
						Result reqRs = dealException(e);
						resultList.add(reqRs);
					} else if (/*
								 * ErrorInfoUtil.filterBusinessException(
								 * AppProfile .getInstance().getAppCode(),
								 * errorname) ||
								 */ExceptionFilterUtil
							.filterException(errorname)) {
						// 如果属于业务异常,则放弃这个错误消息
						log.error(LogUtil.logFormat(e));
						Result reqRs = dealException(e);
						resultList.add(reqRs);
					} else {
						ErrorBizLog ebLog = ClientLogUtil.createErrorBizLog(e,
								Constants.BUSINESS);
						MessageSendUtil.asyncSendErrorBizLog(ebLog);
						RequestContainer.setSuccessed(Constants.FAIL);
						log.error(LogUtil.logFormat(e));
						Result reqRs = dealException(e);
						resultList.add(reqRs);
					}
				} else {
					// 当Errorname为null时，也放弃这条错误消息
					log.error(LogUtil.logFormat(e));
					Result reqRs = dealException(e);
					resultList.add(reqRs);
				}
			}
		}
		return resultList;
	}
	
	private Result dealException(Throwable ex) {
		Result rs = new Result();
		rs.setRetStatus(Result.ERROR);
		if (ex instanceof IPdaException) {
			rs.setErrCode(((IPdaException) ex).getErrCode());
			rs.setErrId(((IPdaException) ex).getErrId());
		} else {
			rs.setErrCode(ExceptionConstant.ERRCODE_BASE);
			rs.setErrId("");
		}
		rs.setErrMsg(ex.getMessage());
		return rs;
	}
	
	private void responseResult(String msg, HttpServletResponse response) throws IOException {
		ServletOutputStream outputStream = null;
		try {
			response.setContentType("application/json; charset=GBK");
			byte[] bytes = msg.getBytes("GBK");
			response.setContentLength(bytes.length);
			outputStream = response.getOutputStream();
			outputStream.write(bytes);
			outputStream.flush();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
	
	private Result encapsulateInnerResult(Object obj,String isMobile) {
		Result rs = new Result();
		rs.setRetStatus(Result.SUCCESS);
		if("Y".equals(isMobile)){
			String res = JsonUtil.encapsulateJsonObject(obj);
			rs.setRetValue(res);
		}else{
			rs.setRetValue(obj);
		}
		return rs;
	}
	
	private Result encapsulateResult(Object obj) {
		Result rs = new Result();
		rs.setRetStatus(Result.SUCCESS);
		rs.setRetValue(obj);
		return rs;
	}
	
}
