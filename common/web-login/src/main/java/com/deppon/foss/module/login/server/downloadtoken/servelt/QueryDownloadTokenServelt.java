package com.deppon.foss.module.login.server.downloadtoken.servelt;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.deppon.foss.module.login.server.downloadtoken.DownloadTokenManager;
import com.deppon.foss.module.login.server.downloadtoken.DownloadTokenManagerFactory;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;


/**
 * 监控器
 * zxy 20140306 MANA-2018
 * @author 157229-zxy
 *
 */
public class QueryDownloadTokenServelt extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DownloadTokenManagerFactory factory = DownloadTokenManagerFactory.getInstance();
		DownloadTokenManager downloadTokenManager = factory.getDownloadTokenManager();
		int curIndex = downloadTokenManager.getClusterCurTokenSize();
		int maxTimeInterval = downloadTokenManager.getMaxTimeInterval();
		int maxThreadSize = downloadTokenManager.getMaxThreadSize();
		int threadCount = downloadTokenManager.getMaxThreadSize();
		int downloadingInt = ExpWaybillConstants.count;
		StringBuffer sb = new StringBuffer();
		sb.append("============令牌池信息===============<p/>");
		sb.append("当前使用令牌数:").append("curIndex="+curIndex).append("(有效),")
			.append("threadList="+threadCount).append("(全部)<p/>");
		sb.append("令牌存活时长 :maxTimeInterval="+maxTimeInterval).append("<p/>");
		sb.append("令牌总数 :maxThreadSize="+maxThreadSize).append("<p/>");
		sb.append("正在下载的用户数:downloadingInt="+downloadingInt).append("<p/>"); 
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		response.getWriter().append(sb.toString());
		response.flushBuffer();
	}

}
