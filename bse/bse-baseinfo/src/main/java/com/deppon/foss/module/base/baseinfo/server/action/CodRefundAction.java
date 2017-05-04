package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundAdditionalDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICodRefundService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundAdditionalEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CodRefundVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * (代收货款打包退款CodRefund的Action类)
 * 
 * @author 187862-dujunhui
 * @date 2014-7-16 下午2:35:53
 * @since
 * @version v1.0
 */
public class CodRefundAction extends AbstractAction {

	/**
	 * （序列化）
	 */
	private static final long serialVersionUID = 2189835402674070988L;
	//图片文件上传路径
	@Value(value="${file.rootDir}")
	private String rootDir;

	// VO类
	private CodRefundVo codRefundVo = new CodRefundVo();

	// Service类
	private ICodRefundService codRefundService;
	// 附件Dao层
	private ICodRefundAdditionalDao codRefundAdditionalDao;
	// 组织信息Service
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	// 部门名称
	private String deptName;
	// 部门编码
	private String deptCode;
	// 用户名称
	private String customerName;
	// 用户编码
	private String customerCode;
	// 期限开始时间
	private Date timeLimitStart;
	// 期限结束时间
	private Date timeLimitEnd;

	// 附件上传
	// 附件文件
	private String[] uploadFile;
	// 附件文件名
	private String[] uploadFileFileName;
	//导出文件下载
	private String downloadFileName;
	//导出文件数据流
	private InputStream inputStream;
	/**
     * 导出附件流
     */
	private InputStream imgStream;
	/**
     * 导出附件名称
     */
	private String additionalName;
	/**
     * 更新时删除的附件名称
     */
	private String additionalNameDel;
    
	/**
	 * @return the uploadFile
	 */
	public String[] getUploadFile() {
		return uploadFile;
	}

	/**
	 * @param uploadFile
	 *            the uploadFile to set
	 */
	public void setUploadFile(String[] uploadFile) {
		this.uploadFile = uploadFile;
	}

	/**
	 * @return the uploadFileFileName
	 */
	public String[] getUploadFileFileName() {
		return uploadFileFileName;
	}

	/**
	 * @param uploadFileFileName
	 *            the uploadFileFileName to set
	 */
	public void setUploadFileFileName(String[] uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	/**
	 * @param codRefundAdditionalDao the codRefundAdditionalDao to set
	 */
	public void setCodRefundAdditionalDao(
			ICodRefundAdditionalDao codRefundAdditionalDao) {
		this.codRefundAdditionalDao = codRefundAdditionalDao;
	}


	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName
	 *            the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode
	 *            the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 *            the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the timeLimitStart
	 */
	public Date getTimeLimitStart() {
		return timeLimitStart;
	}

	/**
	 * @param timeLimitStart
	 *            the timeLimitStart to set
	 */
	public void setTimeLimitStart(Date timeLimitStart) {
		this.timeLimitStart = timeLimitStart;
	}

	/**
	 * @return the timeLimitEnd
	 */
	public Date getTimeLimitEnd() {
		return timeLimitEnd;
	}

	/**
	 * @param timeLimitEnd
	 *            the timeLimitEnd to set
	 */
	public void setTimeLimitEnd(Date timeLimitEnd) {
		this.timeLimitEnd = timeLimitEnd;
	}

	/**
	 * @return the codRefundVo
	 */
	public CodRefundVo getCodRefundVo() {
		return codRefundVo;
	}

	/**
	 * @param codRefundVo
	 *            the codRefundVo to set
	 */
	public void setCodRefundVo(CodRefundVo codRefundVo) {
		this.codRefundVo = codRefundVo;
	}

	/**
	 * @param codRefundService
	 *            the codRefundService to set
	 */
	public void setCodRefundService(ICodRefundService codRefundService) {
		this.codRefundService = codRefundService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 *            the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @return  the imgStream
	 */
	public InputStream getImgStream() {
		return imgStream;
	}

	/**
	 * @param imgStream the imgStream to set
	 */
	public void setImgStream(InputStream imgStream) {
		this.imgStream = imgStream;
	}

	/**
	 * @return  the additionalName
	 */
	public String getAdditionalName() {
		return additionalName;
	}

	/**
	 * @param additionalName the additionalName to set
	 */
	public void setAdditionalName(String additionalName) {
		this.additionalName = additionalName;
	}

	/**
	 * @return  the additionalNameDel
	 */
	public String getAdditionalNameDel() {
		return additionalNameDel;
	}

	/**
	 * @param additionalNameDel the additionalNameDel to set
	 */
	public void setAdditionalNameDel(String additionalNameDel) {
		this.additionalNameDel = additionalNameDel;
	}

	/**
	 * @return  the downloadFileName
	 */
	public String getDownloadFileName() {
		return downloadFileName;
	}

	/**
	 * @param downloadFileName the downloadFileName to set
	 */
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	/**
	 * @return  the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * (新增代收货款打包退款基础信息)
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午2:41:23
	 * @since
	 * @version v1.0
	 */
	@JSON
	public String addCodRefund() {
		try {
			CodRefundEntity addEntity = new CodRefundEntity(); // codRefundVo.getCodRefundEntity();
			CodRefundEntity existEntity = new CodRefundEntity();
			existEntity.setActive(FossConstants.ACTIVE);
			existEntity.setCustomerCode(customerCode);
			List<CodRefundEntity> existsEntity=codRefundService.
					queryCodRefundListByCondition(existEntity, 0, 1);
			if(existsEntity.size()>0){
				return returnError("该客户编码已存在，请作废后新增或进行修改！");
			}
			
			addEntity.setDeptCode(deptCode);
			addEntity.setDeptName(orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(deptCode).getName());
			addEntity.setCustomerCode(customerCode);
			// addEntity.setCustomerName(customerName);
			addEntity.setTimeLimitStart(timeLimitStart);
			addEntity.setTimeLimitEnd(timeLimitEnd);

			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户实体
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户工号
			String userName = user.getEmployee().getEmpName();// 当前登录用户名称

			addEntity.setCreateDate(new Date());
			addEntity.setCreateUser(userCode);
			addEntity.setActive(FossConstants.ACTIVE);

			addEntity.setOperatorName(userName);
			addEntity.setOperatorCode(userCode);
			addEntity.setEnteringTime(new Date());

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			addEntity.setTimeLimit(format.format(addEntity.getTimeLimitStart())
					+ " 至 " + format.format(addEntity.getTimeLimitEnd()));

			// 上传附件处理开始
			List<String> fileNames=new ArrayList<String>();
			
//			// 获取上传文件名
//			if (this.uploadFile.length == 0) {
//				return returnError("附件不能为空！");
//			}
			if(this.uploadFile!=null){
				for(int i=0;i<this.uploadFile.length;i++){
					File file = new File(this.uploadFile[i]);
					if(StringUtils.isEmpty(this.getFormatName(file))){
						return returnError("附件格式不正确！只可以上传图片附件！");
					}
					String[] str =this.uploadFileFileName[i].split("\\.");
					String fileName=str[0]+"_"+new Date().getTime()+"."+str[1];
					try {
						FileUtils.copyFile(file, new File(rootDir + "\\"+ fileName));
					} catch (IOException e) {
						e.printStackTrace();
					}
					fileNames.add(fileName);
				}
			}
			// 上传附件处理结束

			codRefundService.addCodRefund(addEntity,fileNames);
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * (图片格式校验函数)
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-18 上午10:27:23
	 * @since
	 * @version v1.0
	 */
	public String getFormatName(File file) {
		ImageInputStream iis;
		try {
			iis = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);
			while (iterator.hasNext()) {
				ImageReader reader = (ImageReader) iterator.next();
				return reader.getFormatName();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * (删除单条代收货款打包退款基础信息)
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午2:54:35
	 * @since
	 * @version v1.0
	 */
	@JSON
	public String deleteCodRefund() {
		try {
			CodRefundEntity delEntity = codRefundVo.getCodRefundEntity();

			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户实体
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户工号

			delEntity.setModifyUser(userCode);

			codRefundService.deleteCodRefund(delEntity);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * (批量删除代收货款打包退款基础信息)
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午3:03:47
	 * @since
	 * @version v1.0
	 */
	@JSON
	public String deleteCodRefunds() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户实体
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户工号

			codRefundService.deleteCodRefunds(codRefundVo.getCodeList(),
					userCode);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * (更新单条代收货款打包退款基础信息)
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午3:16:58
	 * @since
	 * @version v1.0
	 */
	@JSON
	public String updateCodRefund() {
		try {//总体思路：先作废，即作废主表信息即可，附表信息及文件不做修改（更新表单时点击删除会修改附件信息）
			 //再新增主附表及附件信息，同addCodRefund方法

			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户实体
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户工号
			String userName = user.getEmployee().getEmpName();// 当前登录用户名称
			//先作废主表信息
			CodRefundEntity entity=new CodRefundEntity();
			entity.setCustomerCode(this.customerCode);
			entity.setModifyUser(userCode);
			codRefundService.deleteCodRefund(entity);
			
			//再新增主附表数据及附件

			CodRefundEntity addEntity = new CodRefundEntity(); // codRefundVo.getCodRefundEntity();
			CodRefundEntity existEntity = new CodRefundEntity();
			existEntity.setActive(FossConstants.ACTIVE);
			existEntity.setCustomerCode(customerCode);
			List<CodRefundEntity> existsEntity=codRefundService.
					queryCodRefundListByCondition(existEntity, 0, 1);
			if(existsEntity.size()>0){
				return returnError("该客户编码已存在，请作废后新增或进行修改！");
			}
				
			addEntity.setDeptCode(deptCode);
			addEntity.setDeptName(orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(deptCode).getName());
			addEntity.setCustomerCode(customerCode);
			// addEntity.setCustomerName(customerName);
			addEntity.setTimeLimitStart(timeLimitStart);
			addEntity.setTimeLimitEnd(timeLimitEnd);

			addEntity.setCreateDate(new Date());
			addEntity.setCreateUser(userCode);
			addEntity.setActive(FossConstants.ACTIVE);

			addEntity.setOperatorName(userName);
			addEntity.setOperatorCode(userCode);
			addEntity.setEnteringTime(new Date());

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			addEntity.setTimeLimit(format.format(addEntity.getTimeLimitStart())
					+ " 至 " + format.format(addEntity.getTimeLimitEnd()));

			// 上传附件处理开始
			List<String> fileNames=new ArrayList<String>();
				
//			// 获取上传文件名
//			if (this.uploadFile.length == 0) {
//				return returnError("附件不能为空！");
//			}
			if(this.uploadFile!=null){
				for(int i=0;i<this.uploadFile.length;i++){
					File file = new File(this.uploadFile[i]);
					if(StringUtils.isEmpty(this.getFormatName(file))){
						return returnError("附件格式不正确！只可以上传图片附件！");
					}
					String[] str =this.uploadFileFileName[i].split("\\.");
					String fileName=str[0]+"_"+new Date().getTime()+"."+str[1];
					try {
						FileUtils.copyFile(file, new File(rootDir + "\\"+ fileName));
					} catch (IOException e) {
						e.printStackTrace();
					}
					fileNames.add(fileName);
				}
			}
			// 上传附件处理结束
			codRefundService.addCodRefund(addEntity,fileNames);
			
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * (根据条件查询代收货款打包退款基础信息)
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午3:22:34
	 * @since
	 * @version v1.0
	 */
	@JSON
	public String queryCodRefundListByCondition() {
		try {
			CodRefundEntity queryEntity = codRefundVo.getCodRefundEntity();
			this.setTotalCount(codRefundService
					.queryCodRefundListCountByCondition(codRefundVo
							.getCodRefundEntity()));
			List<CodRefundEntity> entityList = codRefundService
					.queryCodRefundListByCondition(queryEntity, start, limit);
			codRefundVo.setCodRefundEntityList(entityList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * (根据ID查询代收货款打包退款基础信息)
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午3:40:37
	 * @since
	 * @version v1.0
	 */
	@JSON
	public String queryCodRefundById() {
		try {
			CodRefundEntity queryEntity = codRefundVo.getCodRefundEntity();
			CodRefundEntity resultEntity=codRefundService.queryCodRefundById(queryEntity.getId());
			
			CodRefundAdditionalEntity additionalEntity=new CodRefundAdditionalEntity();
			additionalEntity.setCustomerCode(resultEntity.getCustomerCode());
			additionalEntity.setActive(FossConstants.ACTIVE);
			List<CodRefundAdditionalEntity> additionalList=codRefundAdditionalDao.
					queryCodRefundAdditionalByCustomerCode(additionalEntity, 0, Integer.MAX_VALUE);
			
			//String additional=new String();//构造字符串
			//additional=additional+"";
			StringBuffer additionall = new StringBuffer();
			for(CodRefundAdditionalEntity jEntity:additionalList){
				//additional+="<a href=\"javascript:void(0);\" onclick=\"baseinfo.codRefund.additionalShow('"+
						//jEntity.getAdditional()+"')"+"\">"+jEntity.getAdditional()+"</a><br>";
				additionall.append("<a href=\"javascript:void(0);\" onclick=\"baseinfo.codRefund.additionalShow('");
				additionall.append(jEntity.getAdditional());
				additionall.append("')");
				additionall.append("\">");
				additionall.append(jEntity.getAdditional());
				additionall.append("</a><br>");
				
			}
			resultEntity.setAdditional(additionall.toString());
			
			codRefundVo.setCodRefundEntity(resultEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * (根据附件名下载该附件)(作废)
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-29 下午4:22:45
	 * @since
	 * @version v1.0
	 */
	@JSON
	public String downloadAdditionalPath(){
		try {
			File destFile= new File(rootDir + "\\"+ additionalName);
			imgStream=new FileInputStream(destFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			additionalName =new String(additionalName.getBytes("UTF-8"),"iso-8859-1");
		}catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		return returnSuccess();
	}
	
	/**
	 * (根据主表ID查询代收货款打包退款及附件信息)
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-31 上午8:30:54
	 * @since
	 * @version v1.0
	 */
	@JSON
	public String queryCodRefundAdditionalById() {
		try {
			CodRefundEntity queryEntity = codRefundVo.getCodRefundEntity();
			CodRefundEntity resultEntity=codRefundService.queryCodRefundById(queryEntity.getId());
			
			CodRefundAdditionalEntity additionalEntity=new CodRefundAdditionalEntity();
			additionalEntity.setCustomerCode(resultEntity.getCustomerCode());
			additionalEntity.setActive(FossConstants.ACTIVE);
			List<CodRefundAdditionalEntity> additionalList=codRefundAdditionalDao.
					queryCodRefundAdditionalByCustomerCode(additionalEntity, 0, Integer.MAX_VALUE);
			
			//String additional=new String();//构造字符串
			//additional=additional+"";
			StringBuffer additional = new StringBuffer();
			for(CodRefundAdditionalEntity jEntity:additionalList){
				//additional+="<a href=\"javascript:void(0);\" onclick=\"baseinfo.codRefund.additionalShow('"+
					//jEntity.getAdditional()+"')"+"\" id=\""+jEntity.getAdditional()+"\">"+jEntity.getAdditional()+"</a>" +
					//"&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"javascript:baseinfo.codRefund.additionalDel('" +
					//jEntity.getAdditional()+"')\"  id=\""+jEntity.getAdditional()+"Button\">删除</a><br>";
				additional.append("<a href=\"javascript:void(0);\" onclick=\"baseinfo.codRefund.additionalShow('");
				additional.append(jEntity.getAdditional());
				additional.append("')");
				additional.append("\" id=\"");
				additional.append(jEntity.getAdditional());
				additional.append("\">");
				additional.append(jEntity.getAdditional());
				additional.append("</a>");
				additional.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"javascript:baseinfo.codRefund.additionalDel('");
				additional.append(jEntity.getAdditional());
				additional.append("')\"  id=\"");
				additional.append(jEntity.getAdditional());
				additional.append("Button\">删除</a><br>");
			}
			resultEntity.setAdditional(additional.toString());
			codRefundVo.setCodRefundEntity(resultEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * (根据附件名删除附件信息)
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-31 上午10:24:33
	 * @since
	 * @version v1.0
	 */
	@JSON
	public String deleteAdditional() {
		try {
			CodRefundAdditionalEntity entity=new CodRefundAdditionalEntity();
			entity.setAdditional(this.additionalNameDel);
			codRefundAdditionalDao.deleteCodRefundAdditional(entity);
			try {//删除本地文件
				FileUtils.forceDelete(new File(rootDir + "\\"+ this.additionalNameDel));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * (导出)
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-9-20 下午3:26:45
	 * @since
	 * @version v1.0
	 */
	@JSON
	public String exportCodRefund(){
		try {
			CodRefundEntity entity = codRefundVo.getCodRefundEntity();
			
			List<CodRefundEntity> exportSource = codRefundService.queryCodRefundListByCondition(entity, 0, Integer.MAX_VALUE);
			
			//导出文件名
			downloadFileName =new String(ColumnConstants.EXPORT_CODREFUND_NAME.getBytes("UTF-8"), "iso-8859-1");
		
			final ExportSetting settings = new ExportSetting();
			settings.setFileName(ColumnConstants.EXPORT_CODREFUND_NAME);
			settings.setSize(ComnConst.SINGLE_ASYN_EXPORT_FILE_MAX_SIZE);
			settings.setFileDesc(String.format("%s导出", settings.getFileName()));
			
			ExportResource resource = new ExportResource();
			//设置表头
			resource.setHeads(ComnConst.COD_REFUND_TITLE);
			//数据封装
			List<List<String>> resultList = new ArrayList<List<String>>();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatHMS=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(!CollectionUtils.isEmpty(exportSource)) {
				for(CodRefundEntity entityList:exportSource){
					List<String> list = new ArrayList<String>();
					list.add(entityList.getDeptName());//部门名称
					list.add(entityList.getCustomerCode());//客户编码
					list.add(format.format(entityList.getTimeLimitStart())+" 至 "+
							format.format(entityList.getTimeLimitEnd()));//期限
					list.add(entityList.getOperatorName());//操作员
					list.add(formatHMS.format(entityList.getEnteringTime()));//录入时间
					resultList.add(list);
				}
			}
			//获取excel 信息
			resource.setRowList(resultList);
			ExporterExecutor executor = new ExporterExecutor();
			inputStream =executor.exportSync(resource, settings);
		    return returnSuccess("导出成功！文件已下载至浏览器的默认下载路径中！");
		} catch (BusinessException e) {
		    return returnError(e);
		}catch (UnsupportedEncodingException e) {
		    return returnError("UnsupportedEncodingException", e);
		}
    }

}