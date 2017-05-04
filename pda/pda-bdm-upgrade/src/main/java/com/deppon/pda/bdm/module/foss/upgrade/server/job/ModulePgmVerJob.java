package com.deppon.pda.bdm.module.foss.upgrade.server.job;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.deppon.pda.bdm.module.core.shared.util.PropertyConstant;

/**
 * 
  * @ClassName ModulePgmVerJob 
  * @Description TODO 定时删除模块升级DLL包
  * @author mt 
  * @date 2013-8-24 上午9:38:09
 */
public class ModulePgmVerJob {
	public static final int dayNum=3;
	public void execute(){
		String date = getDate();
		//获取要删除的文件路径
		String filePath = PropertyConstant.ZIP_DIR + date + File.separator;
		File file = new File(filePath);
		if(file.exists() && file.isDirectory()){
			File[] listFiles = file.listFiles();
			//删除ZIP文件包
			for (int i = 0; i < listFiles.length; i++) {
				listFiles[i].delete();
			}
			//删除目录
			file.delete();
		}
	}
	
	private String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-dayNum);
		String date = sdf.format(calendar.getTime());
		return date;
	} 
}
