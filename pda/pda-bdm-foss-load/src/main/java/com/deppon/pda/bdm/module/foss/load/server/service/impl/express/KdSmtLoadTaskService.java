package com.deppon.pda.bdm.module.foss.load.server.service.impl.express;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdScanBusinessErrorLog;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdSubmitLoadTask;
import com.deppon.pda.bdm.module.foss.load.shared.exception.LoadHasNoFnshScanUserException;


/**
 * 提交快递派送装车任务
 * @author 
 * @date 2013年7月19日15:56:26
 * @version 1.0
 * @since
 */

public class KdSmtLoadTaskService implements IBusinessService<Set<KdScanBusinessErrorLog>, KdSubmitLoadTask>{
	private IPDAExpressDeliverLoadService pdaExpressDeliverLoadService;
	private ILoadDao loadDao;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:21:37
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	
	@Override
	public KdSubmitLoadTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		KdSubmitLoadTask model = JsonUtil.parseJsonToObject(KdSubmitLoadTask.class, asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:21:43
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Set<KdScanBusinessErrorLog> service(AsyncMsg asyncMsg, KdSubmitLoadTask param)
			throws PdaBusiException {
		this.validate(param);
		try {
			if(loadDao.queryNoSyncScanMsgCount(param.getTaskCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
			//将不符合业务逻辑的扫描数据返回给PDA
			Set<KdScanBusinessErrorLog> kdScanBusinessErrorLogs = new HashSet<KdScanBusinessErrorLog>();
			KdScanBusinessErrorLog kdScanBusinessErrorLog = new KdScanBusinessErrorLog();
			kdScanBusinessErrorLog.setTaskCode(param.getTaskCode());
			//正扫
			kdScanBusinessErrorLog.setScanStatus(TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL);
			List<KdScanBusinessErrorLog> kdScanBusinessErrorLogScans = loadDao.selectKdScanBusinessErrorLogByTaskCode(kdScanBusinessErrorLog);
			//反扫
			kdScanBusinessErrorLog.setScanStatus(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
			//正扫-反扫
			List<KdScanBusinessErrorLog> kdScanBusinessErrorLogCacels = loadDao.selectKdScanBusinessErrorLogByTaskCode(kdScanBusinessErrorLog);
			boolean flag = true;
			if(kdScanBusinessErrorLogScans!=null && kdScanBusinessErrorLogScans.size()>0){
				for(KdScanBusinessErrorLog kdScanBusinessErrorLogScan:kdScanBusinessErrorLogScans){
					if(kdScanBusinessErrorLogCacels!=null&&kdScanBusinessErrorLogCacels.size()>0){
						for(KdScanBusinessErrorLog kdScanBusinessErrorLogCacel:kdScanBusinessErrorLogCacels){
							if(kdScanBusinessErrorLogScan.getWaybillCode().equals(kdScanBusinessErrorLogCacel.getWaybillCode())
							 &&kdScanBusinessErrorLogScan.getLabelCode().equals(kdScanBusinessErrorLogCacel.getLabelCode())){
								kdScanBusinessErrorLogCacels.remove(kdScanBusinessErrorLogCacel);
								flag=false;
								break;
							}
						}
					}
					if(flag){
						kdScanBusinessErrorLogs.add(kdScanBusinessErrorLogScan);
					}else{
						flag=true;
					}					
				}
			}			
			if(kdScanBusinessErrorLogs!=null && kdScanBusinessErrorLogs.size()>0){
				return kdScanBusinessErrorLogs;
			}
			//loadDao.deleteKdScanBusinessErrorLog(param.getTaskCode());
			pdaExpressDeliverLoadService.submitLoadTask(param.getTaskCode(), param.getScanTime(), asyncMsg.getPdaCode(),asyncMsg.getUserCode());
		} catch (BusinessException e) {
			if(e.getErrorCode().equals(TransferPDAExceptionCode.EXCEPTION_EXTIST_UNFINISH_PDA_MESSAGECODE)){
				//保存错误信息到文件中
				this.saveErrorInfo(e, asyncMsg);
				throw new LoadHasNoFnshScanUserException();
			}else{
				//保存错误信息到文件中
				this.saveErrorInfo(e, asyncMsg);
				throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			}
			
		} catch(FossInterfaceException e){
			
			throw new PdaBusiException(e);
			
		} catch(Exception e){
			//保存错误信息到文件中
			this.saveErrorInfo(e, asyncMsg);
			
			throw new PdaBusiException(e.getCause());
		}
		return null;
	}
	
	//最后还是用写文件记录
	public void saveErrorInfo(Exception e, AsyncMsg asyncMsg) {
		// 当前年月日，格式yyyyMMdd
		String currDate = DateUtils.getNowDateStrNoSep();
		String path = File.separator + "app01" + File.separator + "ocb" + File.separator + "load17" + File.separator + currDate + File.separator + asyncMsg.getUserCode();
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		
		File  fileError = new File(path + File.separator + "errorInfo.txt");
		if(!file.exists()){
			try {
				fileError.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			// 错误信息
			String errInfo = LogUtil.logFormat(e);
			//请求参数
			String content = asyncMsg.getContent();
			//分割
			String seprator = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
			FileWriter fw = new FileWriter(fileError, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.println(seprator );
			pw.println(errInfo);
			pw.println(seprator);
			pw.flush();
            pw.close();
            fw.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//删除10天前的数据
		//1:获取要删除的路径
		File file1 = new File(File.separator + "app01" + File.separator + "ocb" + File.separator + "load17" );
		
		File[] deleteFiles = file1.listFiles();
		
		Arrays.sort(deleteFiles);
		
		int count = 0;
		for(int i = deleteFiles.length -1; i >= 0; i--){
			count++;
			if(count > 10){
				//非空且存在		
				if (deleteFiles[i] == null || !deleteFiles[i].exists()){
					continue;
				}
				//如果是文件 直接删掉
				if (deleteFiles[i].isFile()){
					deleteFiles[i].delete();
					continue;
				}
				deleteFlie(deleteFiles[i]);
			}
		}
	}
	
	/**
	 * 删除文件（夹）
	 * 
	 */
	public  void deleteFlie(File file) {
		//如果都是文件删除所有文件
		if(isAllFile(file)){
			for(File f : file.listFiles()){
				f.delete();
			}
			file.delete();
		}else{//如果有目录，递归调用
			for(File f : file.listFiles()){
				if(f.isFile()){
					f.delete();
				}else{
					deleteFlie(f);
				}
			}
			file.delete();
		}
	}
	//判断是否是为纯文件目录
	private boolean isAllFile(File file) {
		for (File f : file.listFiles()) {
			if(!f.isFile()){
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:21:50
	 * @param submitLoadTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(KdSubmitLoadTask kdSubmitLoadTask) throws ArgumentInvalidException{
		Argument.notNull(kdSubmitLoadTask, "kdSubmitLoadTask");
		//提交员工编号非空
		Argument.hasText(kdSubmitLoadTask.getUserCode(), "kdSubmitLoadTask.userCode");
		//提交任务号非空
		Argument.hasText(kdSubmitLoadTask.getTaskCode(), "kdSubmitLoadTask.taskCode");
		//扫描时间非空
		Argument.notNull(kdSubmitLoadTask.getScanTime(), "kdSubmitLoadTask.scanTime");
	}
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_KD_LOAD_SUBMIT.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaExpressDeliverLoadService(
			IPDAExpressDeliverLoadService pdaExpressDeliverLoadService) {
		this.pdaExpressDeliverLoadService = pdaExpressDeliverLoadService;
	}
	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
	
	
	
	
}
