package com.deppon.pda.bdm.module.core.server.httpService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
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
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.BeanFactory;
import com.deppon.pda.bdm.module.core.shared.util.CodeParseUtil;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;


public class UploadHttpService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7254356943253309693L;

	private static Logger log = Logger.getLogger(UploadHttpService.class);
	
	public static final int maxSize = 50000000;
	/**
	 * POST请求方法
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long start = System.currentTimeMillis();
		AsyncMsg asyncMsg = new AsyncMsg();
		//JSONObject jsonObject = new JSONObject();
		// TODO 过时 ，后续会使用DiskFileItemFactory和ServletFileUpload来代替
		DiskFileUpload fu = new DiskFileUpload();
		//上传文件最大size
		fu.setSizeMax(maxSize);
		List<FileItem> fileItems;
		Result rs = null;
		String uploadException = "";
		String fileInfoStr ="";
		String uuid = "";
		String deptCode = "";
		try {
			String snippetFilePathFolder = Constant.UPLOAD_IMAGE_SNIPPET_PATH+"/"+DateUtils.formatDate(new Date());
			//double snippetFileSize = 0;
			fileItems = fu.parseRequest(request);
			Iterator<FileItem> itr = fileItems.iterator();
			FileItem fiFile = null;
			//遍历itr来获取上传文件和参数
			while (itr.hasNext()) {
				FileItem fi = (FileItem) itr.next();
				if (!fi.isFormField()) {
					fiFile = fi;

					String fileName = fiFile.getName();
					String wabcode = fileName.split("\\.")[0];
//					File fNew = new File("F:\\" + "/temp/" + fi.getName());
					File fNew = new File(snippetFilePathFolder + "/" + wabcode);
					if (!fNew.exists()) {
						fNew.mkdirs();
					}
					fNew=new File(fNew.getAbsolutePath()+"/"+fiFile.getName());
					System.out.println("fileName:" + fileName);
					System.out.println("getPath:" + fNew.getPath());
//					snippetFilePath = fNew.getAbsolutePath();
					fiFile.write(fNew);
				
				} else {
					//获取上传参数
					//String strname = fi.getFieldName();
					String value = fi.getString();
					value = new String(value.getBytes("iso-8859-1"), "GBK");
					log.info("手机端JSON输入:" + value);
					ReqData reqData = JsonUtil
							.parseJsonToObject(ReqData.class, value);
					List<Result> resultList = new ArrayList<Result>();
					for (ReqJsonData reqJsonData : reqData.getReqData()) {
							String pdaInfoStr = reqJsonData.getPdaInfo();
							fileInfoStr = reqJsonData.getBody();
							JSONObject bodyJson = JSONObject.parseObject(fileInfoStr);
							uuid =String.valueOf(bodyJson.get("uuid"));
							

							//生成文件路径，并将路径传给fpath
							String fpath =String.valueOf(bodyJson.get("fpath"));
								String fileName = 	fpath.substring(fpath.lastIndexOf("/"),fpath.length());
								deptCode =String.valueOf(bodyJson.get("deptcode"));
								log.info("deptcode"+deptCode);
								// TODO
								String fWblCode =String.valueOf(bodyJson.get("wblcode"));
								
							bodyJson.put("fpath", snippetFilePathFolder + "/" + fWblCode + fileName);
							bodyJson.put("fpathparent", snippetFilePathFolder+"/" + fWblCode);
//							System.out.println("@@@@@@@@@@@@" + snippetFilePath_folder+"/" + fWblCode);
							reqJsonData.setBody(bodyJson.toJSONString());
							
							// 解析包头
							asyncMsg = JsonUtil.parseJsonToObject(AsyncMsg.class,
									pdaInfoStr);
							asyncMsg.setContent(reqJsonData.getBody());
							if(asyncMsg.getUserCode()==null||!(Constant.EXTERNAL_DRIVER.equals(asyncMsg.getUserCode()))){
								if(checkDept(asyncMsg.getOperType())){
									asyncMsg.setDeptCode(CodeParseUtil.getDeptId(asyncMsg
											.getUserCode()));
									Argument.hasText(asyncMsg.getDeptCode(), "asyncMsg.deptCode");
								}
								if (checkDept(asyncMsg.getOperType())) {
									asyncMsg.setDeptCode(CodeParseUtil.getDeptCode(asyncMsg.getDeptCode(),
											asyncMsg.getUserCode(),asyncMsg.getUserType()));
									Argument.hasText(asyncMsg.getDeptCode(), "asyncMsg.deptCode");
								}
							}
							ScanMsgTaskEntity entity = JsonUtil.parseJsonToObject(
									ScanMsgTaskEntity.class, reqJsonData.getBody());
							if (entity != null) {
								asyncMsg.setId(entity.getId());
								asyncMsg.setTaskCode(entity.getTaskCode());
							}
							// 验证合法性
							ValidateService validateService = (ValidateService) BeanFactory
									.getBean("validateService");
							PdaInfo pdaInfo = JsonUtil.parseJsonToObject(PdaInfo.class,
									pdaInfoStr);
							validateService.check(pdaInfo);
							// 调用具体的业务类
							Object obj = uuid;
							Result reqRs = encapsulateInnerResult(obj,reqData.getIsMobile());
							resultList.add(reqRs);
							OperationFactory.invokeService(asyncMsg);
					}
					rs = encapsulateResult(resultList);
				}
			}
			//if(fiFile!=null){}
		} catch (Exception e) {
			uploadException = e.getMessage();
			e.printStackTrace();
		}
		if(!"".equals(uploadException)){
			if(rs==null){
				rs = encapsulateResult("");
			}
			Result result = new Result();
				result.setRetStatus(0);
				result.setErrMsg(uploadException);
				result.setRetValue(uuid);
			rs.setRetValue(result);
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
	private boolean checkDept(String operType) {
		// 程序版本更新\PDA登录\程序模块版本更新 不需要验证部门
		if (!operType.equals(LoginConstant.OPER_TYPE_SYS_VER_DNLD.VERSION) && !operType.equals(LoginConstant.OPER_TYPE_SYS_LOGIN.VERSION)
				&& !operType.equals(LoginConstant.OPER_TYPE_SYS_MODULE_VER_DNLD.VERSION)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * GET 请求方法
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	private Result encapsulateInnerResult(Object obj, String isMobile) {
		Result rs = new Result();
		rs.setRetStatus(Result.SUCCESS);
		if ("Y".equals(isMobile)) {
			String res = JsonUtil.encapsulateJsonObject(obj);
			rs.setRetValue(res);
		} else {
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
	/*private Result dealException(Throwable ex) {
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
	}*/

	/**
	 * 输出
	 * 
	 */
	private void responseResult(String msg, HttpServletResponse response) throws IOException {
		ServletOutputStream outputStream = null;
		try {
			response.setContentType("application/json; charset=GBK");
			byte[] bytes = msg.getBytes("GBK");
			// System.out.println("jsonStr字符串大小："+bytes.length);
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
