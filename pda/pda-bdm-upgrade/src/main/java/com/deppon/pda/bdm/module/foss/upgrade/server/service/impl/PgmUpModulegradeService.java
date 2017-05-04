package com.deppon.pda.bdm.module.foss.upgrade.server.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoginConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.ModuleVerEntity;
import com.deppon.pda.bdm.module.core.shared.domain.PgmModuleVerEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.DLLVersionHelp;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;
import com.deppon.pda.bdm.module.core.shared.util.FileUtils;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.util.PropertyConstant;
import com.deppon.pda.bdm.module.foss.upgrade.server.dao.IPdaMuduleDao;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.PdaModuleInfoEntity;
import com.deppon.pda.bdm.module.foss.upgrade.shared.exception.DLLVerNotFoundException;
import com.deppon.pda.bdm.module.foss.upgrade.shared.exception.PgmVerNotFoundException;
import com.deppon.pda.bdm.module.foss.upgrade.shared.util.ConstantsUtil;
import com.deppon.pda.bdm.module.foss.upgrade.shared.vo.DLLPgmVer;
import com.deppon.pda.bdm.module.foss.upgrade.shared.vo.DLLPgmVerModel;

/**
 * 
  * @ClassName PgmUpModulegradeService 
  * @Description TODO PDA程序模块更新
  * @author mt 
  * @date 2013-8-16 下午2:47:22
 */
public class PgmUpModulegradeService implements
		IBusinessService<DLLPgmVer, PgmModuleVerEntity> {
	private Logger log = Logger.getLogger(getClass());
	private IPdaMuduleDao pdaMuduleDao;

	@Override
	public PgmModuleVerEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		// 解析body
		PgmModuleVerEntity pgmVerEntity = JsonUtil.parseJsonToObject(
				PgmModuleVerEntity.class, asyncMsg.getContent());
		return pgmVerEntity;
	}
	
	@Override
	public DLLPgmVer service(AsyncMsg asyncMsg, PgmModuleVerEntity pgmVerEntity)
			throws PdaBusiException {
		List<ModuleVerEntity> moduleVerList = pgmVerEntity.getModuleVerEntity();
		//获取需要更新的版本
		Map<String,Map<String,String>> map = checkPgmVerFile(moduleVerList);
		//系统当前DLL版本
		Map<String,String> sysModuleVerMap = map.get("SYS");
		//PDA需要更新的新版本
		Map<String,String> newModuleVerMap = map.get("NEW");
		//PDA当前版本
		Map<String,String> pdaModuleVerMap = map.get("PDA");
		
		DLLPgmVer dllPgmVer = new DLLPgmVer();
		
		//打包ZIP,存在需要更新的数据
		if(newModuleVerMap.size() > 0){
			//获取生成的ZIP文件信息
			DLLPgmVerModel dllPgmVerModel = zipFile(newModuleVerMap, asyncMsg.getUserCode(), asyncMsg.getPdaCode());
			
			String updUrl = checkPgmVerFile(dllPgmVerModel);
			//设置更新路径
			dllPgmVer.setUpdUrl(updUrl);
			
			//设置状态为需要更新
			dllPgmVer.setReqUpgrade("Y");
		}else{
			//设置状态为不需要更新
			dllPgmVer.setReqUpgrade("N");
		}
		
		//封装实体
		PdaModuleInfoEntity entity = wrapEntity(sysModuleVerMap, 
				pdaModuleVerMap,asyncMsg.getPdaCode(),asyncMsg.getUserCode());
		pdaMuduleDao.updatePdaModuleInfo(entity);
		return dllPgmVer;
	}
	
	private String checkPgmVerFile(DLLPgmVerModel dllPgmVerModel)
			throws PgmVerNotFoundException {
		// 程序包路径
		String filePath = dllPgmVerModel.getUpdUrl() + 
							dllPgmVerModel.getFileName();
		File file = new File(filePath);
		// 判断程序包是否存在
		if (!file.exists()) {
			throw new DLLVerNotFoundException();
		}
		return ConstantsUtil.HTTPED_HOST + PropertyConstant.ZIP_PATH
				+ dllPgmVerModel.getNowDate() + File.separator + 
					dllPgmVerModel.getFileName();
	}
	
	/**
	 * 
	* @Description: TODO 封装更新实体
	* @param sysModuleVerMap
	* @param pdaModuleVerMap
	* @return
	* @return PdaModuleInfoEntity    
	* @author mt
	* @date 2013-8-24 上午8:25:47
	 */
	private PdaModuleInfoEntity wrapEntity(Map<String,String> sysModuleVerMap,
			Map<String,String> pdaModuleVerMap,String pdaCode,String empCode){
		PdaModuleInfoEntity entity = new PdaModuleInfoEntity();
		
		StringBuffer currentVersion = new StringBuffer();
		StringBuffer newestVersion = new StringBuffer();
		StringBuffer moduleName = new StringBuffer();
		
		//系统当前版本
		Set<Entry<String,String>> entrySet = sysModuleVerMap.entrySet();
		Iterator<Entry<String,String>> iterator = entrySet.iterator();
		while(iterator.hasNext()){
			Entry<String,String> entry = iterator.next();
			String dllName = entry.getKey();
			String version = entry.getValue();
			//系统当前版本
			newestVersion.append(version).append(Constant.VERTICAL_LINE);
			//PDA当前版本
			currentVersion.append(pdaModuleVerMap.get(dllName)).append(Constant.VERTICAL_LINE);
			moduleName.append(dllName).append(Constant.VERTICAL_LINE);
		}
		
		entity.setCurrentVersion(currentVersion.toString());
		entity.setNewestVersion(newestVersion.toString());
		entity.setModuleName(moduleName.toString());
		
		entity.setDvccoode(pdaCode);
		entity.setLastUpdateCode(empCode);
		return entity;
	}
	
	/**
	 * 
	* @Description: TODO 将当前用户
	* @param newModuleVerMap
	* @return
	* @return String    
	* @author mt
	* @date 2013-8-23 上午10:52:03
	 */
	private DLLPgmVerModel zipFile(Map<String,String> newModuleVerMap,
			String empCode,String pdaCode){
		//获取当前时间
		String nowDate = DateUtils.getNowDateStr();
		//构造ZIP路径,以及名称
		String zipPath = PropertyConstant.ZIP_DIR + 
				nowDate + File.separator;
		FileUtils.mkdirs(zipPath);
		
		String fileName = empCode + Constant.UNDERLINE_DELIMITER + 
				pdaCode + Constant.UNDERLINE_DELIMITER + 
				DateUtils.getNowDateTimeStrNoSep() + "_dll.zip";
		
		DLLPgmVerModel dllPgmVerModel = new DLLPgmVerModel();
		dllPgmVerModel.setNowDate(nowDate);
		dllPgmVerModel.setFileName(fileName);
		dllPgmVerModel.setUpdUrl(zipPath);
		
		//构造ZIP路径
		ZipOutputStream zipOutputStream = null;
		BufferedInputStream input = null;
		File zipFile = null;
		String dllFilePath;
		int length = 0;
		byte[] data = new byte[1024];
		try{
			zipFile = new File(zipPath + fileName);
			if(!zipFile.exists()){
				zipFile.createNewFile();
			}
			
			Set<Entry<String,String>> entrySet = newModuleVerMap.entrySet();
			Iterator<Entry<String,String>> iterator = entrySet.iterator();
			zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
			while(iterator.hasNext()){
				Entry<String,String> entry = iterator.next();
				//获取DLL文件名
				String dllName = entry.getKey();
				//dll文件路径
				dllFilePath = PropertyConstant.DLL_DIR + dllName;
				input = new BufferedInputStream(new FileInputStream(new File(dllFilePath)));
				zipOutputStream.putNextEntry(new ZipEntry(dllName));
				while((length = input.read(data)) > 0){
					zipOutputStream.write(data, 0, length);
				}
			}
		}catch(IOException e){
			log.error(LogUtil.logFormat(e));
		}finally{
			try{
				input.close();
				zipOutputStream.closeEntry();
				zipOutputStream.close();
			}catch(Exception e){
				log.error(LogUtil.logFormat(e));
			}
		}
		
		return dllPgmVerModel;
	}
	
	/**
	 * 
	* @Description: TODO 检测哪些DLL需要更新,将需要更新的DLL名称返回
	* @param moduleVerList
	* @return
	* @return String    
	* @author mt
	* @date 2013-8-22 下午5:23:00
	 */
	private Map<String,Map<String,String>> checkPgmVerFile(List<ModuleVerEntity> moduleVerList){
		Map<String,Map<String,String>> map = new HashMap<String, Map<String,String>>();
		//获取当前系统的DLL版本信息
		Map<String,String> sysModuleVerMap = new HashMap<String,String>();
		//获取PDA模块版本与系统不一致的DLL名称
		Map<String,String> newModuleVerMap = new HashMap<String,String>();
		//保存当前PDA的DLL版本
		Map<String,String> pdaModuleVerMap = new HashMap<String,String>();
		File file = null;
		String version = null;
		ModuleVerEntity verEntity = null;
		for (int i = 0; i < moduleVerList.size(); i++) {
			verEntity = moduleVerList.get(i);
			file = new File(PropertyConstant.DLL_DIR + 
					verEntity.getModuleName());
			//文件存在则获取版本信息
			if(file.exists()){
				version = DLLVersionHelp.getVersion(file);
				//保存系统当前DLL版本信息
				sysModuleVerMap.put(verEntity.getModuleName(), version);
				pdaModuleVerMap.put(verEntity.getModuleName(), verEntity.getPgmVer());
				//版本不一致则记录该文件
				if(!verEntity.getPgmVer().equals(version)){
					//要将当前服务器对应的DLL版本都记录到数据库
					newModuleVerMap.put(verEntity.getModuleName(), version);
				}
			}
		}
		map.put("SYS", sysModuleVerMap);
		map.put("NEW", newModuleVerMap);
		map.put("PDA", pdaModuleVerMap);
		return map;
	}
	
	@Override
	public String getOperType() {
		return LoginConstant.OPER_TYPE_SYS_MODULE_VER_DNLD.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaMuduleDao(IPdaMuduleDao pdaMuduleDao) {
		this.pdaMuduleDao = pdaMuduleDao;
	}
}
