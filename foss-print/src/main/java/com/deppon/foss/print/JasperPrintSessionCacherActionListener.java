package com.deppon.foss.print;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * @author niujian
 *
 */
public class JasperPrintSessionCacherActionListener implements
		HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		/*
		HttpSession session = arg0.getSession();
		String id = session.getId();
		JasperPrintCacher cacher = JasperPrintCacher.getInstance();
		cacher.remove(id);
		*/
	}

}
