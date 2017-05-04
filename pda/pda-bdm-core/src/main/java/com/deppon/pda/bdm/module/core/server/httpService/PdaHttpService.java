package com.deppon.pda.bdm.module.core.server.httpService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.deppon.ar.bamp.client.constant.Constants;
import com.deppon.ar.bamp.client.listener.RequestContainer;
import com.deppon.ar.bamp.client.util.ClientLogUtil;
import com.deppon.ar.bamp.client.util.ErrorInfoUtil;
import com.deppon.ar.bamp.client.util.MessageSendUtil;
import com.deppon.ar.bamp.common.dto.ErrorBizLog;
import com.deppon.pda.bdm.module.core.server.service.impl.ValidateService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoginConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.PdaInfo;
import com.deppon.pda.bdm.module.core.shared.domain.ReqData;
import com.deppon.pda.bdm.module.core.shared.domain.ReqJsonData;
import com.deppon.pda.bdm.module.core.shared.domain.Result;
import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgTaskEntity;
import com.deppon.pda.bdm.module.core.shared.exception.IPdaException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.JsonNotNullException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.BeanFactory;
import com.deppon.pda.bdm.module.core.shared.util.CodeParseUtil;
import com.deppon.pda.bdm.module.core.shared.util.ExceptionFilterUtil;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;

/**
 * PDA请求接入模块
 * 
 * @author 王洪领
 * @date 2012-09-06
 * @version 1.0
 * 
 */
public class PdaHttpService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7254356943253309693L;

	private static Logger log = Logger.getLogger(PdaHttpService.class);

	/**
	 * POST请求方法
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long start = System.currentTimeMillis();
		Result rs;
		try {
			// 获得包头和包体的值
			String reqJson = request.getParameter(Constant.REQ_JSON_BODY);
			if(StringUtils.isEmpty(reqJson)){
				throw new JsonNotNullException();
			}
			//Argument.hasText(reqJson, "jsonStr");
			reqJson = new String(reqJson.getBytes("iso-8859-1"), "GBK");
			log.info("PDA端JSON输入:" + reqJson);
			
			ReqData reqData = JsonUtil
					.parseJsonToObject(ReqData.class, reqJson);
			List<Result> resultList = new ArrayList<Result>();
			for (ReqJsonData reqJsonData : reqData.getReqData()) {
				try {
					String pdaInfoStr = reqJsonData.getPdaInfo();
					// 解析包头
					AsyncMsg asyncMsg = JsonUtil.parseJsonToObject(AsyncMsg.class,
							pdaInfoStr);
					asyncMsg.setContent(reqJsonData.getBody());
					//校验是否是外请车司机登录
					if(asyncMsg.getUserCode()!=null&&Constant.EXTERNAL_DRIVER.equals(asyncMsg.getUserCode())){
						ScanMsgTaskEntity entity = null;
						try {
							entity = JsonUtil.parseJsonToObject(
							ScanMsgTaskEntity.class, reqJsonData.getBody());
						} catch (Exception e) {
							log.info("com.deppon.pda.bdm.module.core.server.httpService.PdaHttpService:ScanMsgTaskEntity实体解析异常" + e);
						}
						if (entity != null) {
							asyncMsg.setId(entity.getId());
							asyncMsg.setTaskCode(entity.getTaskCode());
						}
						// 验证合法性
						ValidateService validateService = (ValidateService) BeanFactory
								.getBean("validateService");
						PdaInfo pdaInfo = JsonUtil.parseJsonToObject(PdaInfo.class,
								pdaInfoStr);
						//1、OCB_08为设备注册接口，此时pdaInfo肯定不存在
						//2、如果操作类型是OCB_08 则不做校验
						if(!"OCB_08".equals(asyncMsg.getOperType())){
							validateService.check(pdaInfo);
						}
						// 调用具体的业务类
						Object obj = null;
						obj = OperationFactory.invokeService(asyncMsg);
						Result reqRs = encapsulateInnerResult(obj,reqData.getIsMobile());	
						//返回值
						resultList.add(reqRs);	
					} else {	
						if(checkDept(asyncMsg.getOperType())){
							//根据员工工号查询该工号对应的部门ID
							asyncMsg.setDeptCode(CodeParseUtil.getDeptId(asyncMsg
									.getUserCode()));
							Argument.hasText(asyncMsg.getDeptCode(), "asyncMsg.deptCode");
						}
						if (checkDept(asyncMsg.getOperType())) {
							//查询部门编码，如果是快递员，则返回快递员所在的营业部的编码
							asyncMsg.setDeptCode(CodeParseUtil.getDeptCode(asyncMsg.getDeptCode(),//部门ID
									asyncMsg.getUserCode(),asyncMsg.getUserType()));
							Argument.hasText(asyncMsg.getDeptCode(), "asyncMsg.deptCode");
						}
						
						ScanMsgTaskEntity entity = null;
						try {
							entity = JsonUtil.parseJsonToObject(
							ScanMsgTaskEntity.class, reqJsonData.getBody());
						} catch (Exception e) {
							log.info("com.deppon.pda.bdm.module.core.server.httpService.PdaHttpService:ScanMsgTaskEntity实体解析异常" + e);
						}
						
						if (entity != null) {
							asyncMsg.setId(entity.getId());
							asyncMsg.setTaskCode(entity.getTaskCode());
						}
						// 验证合法性
						ValidateService validateService = (ValidateService) BeanFactory
								.getBean("validateService");
						PdaInfo pdaInfo = JsonUtil.parseJsonToObject(PdaInfo.class,
								pdaInfoStr);
						//1、OCB_08为设备注册接口，此时pdaInfo肯定不存在
						//2、如果操作类型是OCB_08 则不做校验
						if(!"OCB_08".equals(asyncMsg.getOperType())){
							validateService.check(pdaInfo);
						}
						
						// 调用具体的业务类
						Object obj = null;
						// if(reqData.getAsyncFlag().equals(Constant.ASYNC_FLAG)){
						// OperationFactory.invokeAsyncService(pdaInfo,
						// reqJsonData.getBody());
						// }else{
						obj = OperationFactory.invokeService(asyncMsg);
						// }
						Result reqRs = encapsulateInnerResult(obj,reqData.getIsMobile());
						resultList.add(reqRs);
					}
					
				} catch (Throwable e) {
					
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
							Result reqRs = dealException(e);
							resultList.add(reqRs);
						}else if (ExceptionFilterUtil.filterException(errorname)
								){
							//如果属于业务异常,则放弃这个错误消息
							log.error(LogUtil.logFormat(e));
							Result reqRs = dealException(e);
							resultList.add(reqRs);	
						}else{
							ErrorBizLog ebLog = ClientLogUtil.createErrorBizLog(e,Constants.BUSINESS);
							MessageSendUtil.asyncSendErrorBizLog(ebLog);
							RequestContainer.setSuccessed(Constants.FAIL);
							log.error(LogUtil.logFormat(e));
							Result reqRs = dealException(e);
							resultList.add(reqRs);					
						}
					}else{
						//当Errorname为null时，也放弃这条错误消息
						log.error(LogUtil.logFormat(e));
						Result reqRs = dealException(e);
						resultList.add(reqRs);				
					}				
				}
			}
			rs = encapsulateResult(resultList);
		} catch (Throwable e) {
			
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
				}else if (ExceptionFilterUtil.filterException(errorname)
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
				
		} 
		// 将返回值转换成JSON格式
		String msg = JsonUtil.encapsulateJsonObject(rs);
		log.info("BDM端JSON输出：" + msg);
		this.responseResult(msg, response);
		log.info("PDA后台接口所用时间:"+(System.currentTimeMillis()-start));
	}

	/**
	 * 
	* @Description: TODO
	* @param operType
	* @return
	* @return boolean    
	* @author mt hyssmt@vip.qq.com
	* @date 2013-9-23 下午2:28:46
	 */
	private boolean checkDept(String operType){
		//程序版本更新\PDA登录\程序模块版本更新 \NCI登录 不需要验证部门
		if(!operType.equals(LoginConstant.OPER_TYPE_SYS_VER_DNLD.VERSION)
				&& !operType.equals(LoginConstant.OPER_TYPE_SYS_LOGIN.VERSION)
				&& !operType.equals(LoginConstant.OPER_TYPE_SYS_MODULE_VER_DNLD.VERSION)
				&& !operType.equals(LoginConstant.OPER_TYPE_NCI_LOGIN.VERSION)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * GET 请求方法
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	private Result encapsulateResult(Object obj) {
		Result rs = new Result();
		rs.setRetStatus(Result.SUCCESS);
		rs.setRetValue(obj);
		return rs;
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
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

	/**
	 * 处理异常
	 * 
	 * @param ex
	 * @return
	 */
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

	/**
	 * 输出
	 * 
	 */
	private void responseResult(String msg, HttpServletResponse response)
			throws IOException {
		ServletOutputStream outputStream = null;
		try {
			response.setContentType("application/json; charset=GBK");
			byte[] bytes = msg.getBytes("GBK");
			//System.out.println("jsonStr字符串大小："+bytes.length);
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
}
