/*package com.deppon.pda.bdm.module.push.server.service;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

*//**
 * Servlet implementation class TEST
 *//*
public class TEST extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    *//**
     * @see HttpServlet#HttpServlet()
     *//*
    public TEST() {
        super();
        // TODO Auto-generated constructor stub
    }

	*//**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	*//**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// userId=601025318125241463 channelId=3731165954167312081 
		 ServletContext servletContext = this.getServletContext();  
		 WebApplicationContext context =   
	                WebApplicationContextUtils.getWebApplicationContext(servletContext);  
		 IPushService service = (IPushService) context.getBean("pushService");  
//				IPushService service = new PushService();
				String channel_id = "3731165954167312081";
				String user_id = "601025318125241463";
				String deviceId = channel_id +"|"+user_id;
				String title = "测试";
				String description = "开单成功";
//				String str = "{\"pushType\":\"PDA_ACTIVE\",\"pushMessage\":{\"appreciationCost\":[{\"appreciationCost\":\"12\",\"appreciationName\":\"\"},{\"appreciationCost\":\"290\",\"appreciationName\":\"运费\"},{\"appreciationCost\":\"2\",\"appreciationName\":\"\"},{\"appreciationCost\":\"4\",\"appreciationName\":\"\"}],\"createTime\":\"2014-11-12 10:11:11\",\"consigneeName\":\"蒋才仓\",\"departDeptName\":\"W011306030306\",\"consigneeAddress\":\"浙江省-温州市-苍南县-金乡镇朝阳路112号\",\"shipperPhone\":\"057764577722\",\"shipperName\":\"蒋才仓\",\"shipperAddress\":\"浙江省-温州市-苍南县-金乡镇朝阳路112号\",\"destinationDeptName\":\"北京丰台区新发地营业部\",\"labelDetail\":{\"send\":null,\"userCode\":\"013772\",\"wrapType\":\"11纸1木\",\"goodsAreas\":[{\"goodsAreaCode\":\"13\",\"transferCode\":\"P01\"},{\"goodsAreaCode\":\"13\",\"transferCode\":\"P01\"},{\"goodsAreaCode\":\"13\",\"transferCode\":\"P01\"}],\"barcode\":null,\"destinationName\":\"北京丰台新发地\",\"destTransCenterName\":\"北京丰台区新发地运作部\",\"pieces\":\"2\",\"destStationNumber\":\"0130\",\"transType\":\"精准卡航\",\"departmentCityName\":\"郑州市\",\"wblCode\":\"11555221\",\"goodsType\":null},\"waybillCode\":\"11555221\",\"consigneePhone\":\"057764577722\"}}";
				String str = "{\"pushType\":\"PDA_ACTIVE\",\"pushMessage\":{\"appreciationCost\":[{\"appreciationCost\":\"12\",\"appreciationName\":\"\"},{\"appreciationCost\":\"290\",\"appreciationName\":\"运费\"},{\"appreciationCost\":\"2\",\"appreciationName\":\"\"},{\"appreciationCost\":\"4\",\"appreciationName\":\"\"}],\"createTime\":\"2014-11-12 10:11:11\",\"consigneeName\":\"蒋才仓\",\"departDeptName\":\"W011306030306\",\"consigneeAddress\":\"浙江省-温州市-苍南县-金乡镇朝阳路112号\",\"shipperPhone\":\"057764577722\",\"shipperName\":\"蒋才仓\",\"shipperAddress\":\"浙江省-温州市-苍南县-金乡镇朝阳路112号\",\"destinationDeptName\":\"北京丰台区新发地营业部\",\"labelDetail\":{\"send\":null,\"userCode\":\"122368\",\"wrapType\":\"11纸1木\",\"goodsAreas\":[{\"goodsAreaCode\":\"13\",\"transferCode\":\"P01\"},{\"goodsAreaCode\":\"13\",\"transferCode\":\"P01\"},{\"goodsAreaCode\":\"13\",\"transferCode\":\"P01\"}],\"barcode\":null,\"destinationName\":\"北京丰台新发地\",\"destTransCenterName\":\"北京丰台区新发地运作部\",\"pieces\":\"2\",\"destStationNumber\":\"0130\",\"transType\":\"精准卡航\",\"departmentCityName\":\"郑州市\",\"wblCode\":\"224682807\",\"goodsType\":null},\"waybillCode\":\"224682807\",\"consigneePhone\":\"057764577722\"}}";
//				String str = "1";
				String value = str;
				service.pushMessage(1,1,4,deviceId,title,description,value);
	}

}
*/