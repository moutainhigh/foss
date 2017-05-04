package com.deppon.pda.bdm.module.ocb.server.job;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.ocb.server.service.impl.FileUtil;

/**
 * @ClassName PictureDeleteJob.java
 * @Description BDM保存的图 片开单历史图片删除30天以前的图片与文件夹 job每天晚上2点执行
 * @author 201638
 * @date 2015-3-5
 */
public class PictureDeleteJob {
	// 获取图片路径 app01/ocb
	private String picturePath = Constant.UPLOAD_IMAGE_PATH;
	private Logger log = Logger.getLogger(getClass());

	public void start(String[] args) {
		FileUtil fileUtil = new FileUtil();
		log.info("[PictureDeleteJob]start");
		try {
			List<String> deletePaths = getDeletePaths();
			//删除文件目录
			if (null != deletePaths && deletePaths.size() > 0) {
				for (String deletePath : deletePaths) {
					fileUtil.deleteDirectory(deletePath);
				}
			}
		} catch (Exception e) {
			log.error(LogUtil.logFormat(e));
		}
		log.info("[PictureDeleteJob]end");
	}

	/**
	 * @description 遍历 app01/ocb下的文件，查找出30天以前的文件
	 * @return 30天以前文件路径
	 * @author 201638
	 * @date 2015-3-5
	 */
	private List<String> getDeletePaths() {
		File file = new File(picturePath);
		ArrayList<String> deletePaths = new ArrayList<String>();
		String[] sonFolder = file.list();
		for (int i = 0; i < sonFolder.length; i++) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = format.parse(sonFolder[i]);
			} catch (ParseException e) {
				continue;
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			//1、得到文件夹创建的时间
			long time1 = cal.getTimeInMillis();
			//2、得到当前时间
			cal.setTime(new Date());
			long time2 = cal.getTimeInMillis();
			//3、当前时间与文件夹创建时间的差值
			long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
			//4、如果差值大于30天，则返回当前文件夹的路径
			Integer day = Integer.parseInt(String.valueOf(betweenDays));
			//auhtor:245960 Date:2015-06-16 comment:修改为删除90天前的历史图片   modify reason:2015-06-23版本中  袁金彪  提出需求。
			if (day > 90) {
				deletePaths.add(picturePath + File.separator + sonFolder[i]);
			}
		}
		return deletePaths;
	}
	
	/**
	 * @description Test job
	 * @param args
	 * @author 201638
	 * @date 2015-3-10 
	 */
	/*public static void main(String[] args) {
		PictureDeleteJob job = new PictureDeleteJob();
		job.start(new String[]{"1","2"});
	}*/

}
