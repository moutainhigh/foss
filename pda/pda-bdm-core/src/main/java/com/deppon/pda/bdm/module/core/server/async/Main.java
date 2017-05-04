package com.deppon.pda.bdm.module.core.server.async;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.pda.bdm.module.core.server.async.job.AsyncDataSyncMonitorJob;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:testjob.xml","classpath:bean-transaction.xml");
//		while(true){
			AsyncDataSyncMonitorJob job = (AsyncDataSyncMonitorJob)app.getBean("scanDataSyncJob");
			job.start();
//			Thread.sleep(10000);
//		}
		
//		PictureEntity pic = new PictureEntity();
//		try {
//			FileInputStream fr = new FileInputStream(new File("d:\\error.gif"));
//			byte[] b = new byte[1024];
//			int i=0;
//			ByteOutputStream bo  =new ByteOutputStream();
//			while((i=fr.read(b))>0){
//				bo.write(b, 0, i);
//			}
//			byte[] c = new byte[bo.size()];
//			System.arraycopy(bo.getBytes(), 0, c, 0, bo.size());
//			pic.setPicture(c);
//			String json = JsonUtil.encapsulateJsonObject(pic);
//			System.out.println(json);
//			
//			PictureEntity pic2 = JsonUtil.parseJsonToObject(PictureEntity.class, json);
//			FileOutputStream fo = new FileOutputStream(new File("d:\\err2.gif"));
//			fo.write(pic2.getPicture());
//			fo.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
