package com.deppon.pda.bdm.module.push.server;

import org.junit.Test;

import com.deppon.pda.bdm.module.push.server.service.IPushService;
import com.deppon.pda.bdm.module.push.server.service.impl.PushService;

/**
 * 
* @ClassName: AppTest 
* @Description: 
* @author 183272
* @date 2014年10月31日 上午11:12:09 
*
 */
public class AppTest{
	/** 
	 * @Title: sendMessage 
	 * @Description: 
	 * @author 183272
	 * @date 2014年10月31日 上午11:12:21 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	@Test
	public void sendMessage() {
		// userId=601025318125241463 channelId=3731165954167312081 
		/*IPushService service = new PushService();
		String channel_id = "3731165954167312081";
		String user_id = "601025318125241463";
		String deviceId = channel_id +"|"+user_id;
		String title = "测试";
		String description = "开单成功";
//		String str = "{\"pushType\":\"PDA_ACTIVE\",\"pushMessage\":{\"appreciationCost\":[{\"appreciationCost\":\"12\",\"appreciationName\":\"\"},{\"appreciationCost\":\"290\",\"appreciationName\":\"运费\"},{\"appreciationCost\":\"2\",\"appreciationName\":\"\"},{\"appreciationCost\":\"4\",\"appreciationName\":\"\"}],\"createTime\":\"2014-11-12 10:11:11\",\"consigneeName\":\"蒋才仓\",\"departDeptName\":\"W011306030306\",\"consigneeAddress\":\"浙江省-温州市-苍南县-金乡镇朝阳路112号\",\"shipperPhone\":\"057764577722\",\"shipperName\":\"蒋才仓\",\"shipperAddress\":\"浙江省-温州市-苍南县-金乡镇朝阳路112号\",\"destinationDeptName\":\"北京丰台区新发地营业部\",\"labelDetail\":{\"send\":null,\"userCode\":\"013772\",\"wrapType\":\"11纸1木\",\"goodsAreas\":[{\"goodsAreaCode\":\"13\",\"transferCode\":\"P01\"},{\"goodsAreaCode\":\"13\",\"transferCode\":\"P01\"},{\"goodsAreaCode\":\"13\",\"transferCode\":\"P01\"}],\"barcode\":null,\"destinationName\":\"北京丰台新发地\",\"destTransCenterName\":\"北京丰台区新发地运作部\",\"pieces\":\"2\",\"destStationNumber\":\"0130\",\"transType\":\"精准卡航\",\"departmentCityName\":\"郑州市\",\"wblCode\":\"11555221\",\"goodsType\":null},\"waybillCode\":\"11555221\",\"consigneePhone\":\"057764577722\"}}";
		String str = "{\"pushType\":\"PDA_ACTIVE\",\"pushMessage\":{\"appreciationCost\":[{\"appreciationCost\":\"12\",\"appreciationName\":\"\"},{\"appreciationCost\":\"290\",\"appreciationName\":\"运费\"},{\"appreciationCost\":\"2\",\"appreciationName\":\"\"},{\"appreciationCost\":\"4\",\"appreciationName\":\"\"}],\"createTime\":\"2014-11-12 10:11:11\",\"consigneeName\":\"蒋才仓\",\"departDeptName\":\"W011306030306\",\"consigneeAddress\":\"浙江省-温州市-苍南县-金乡镇朝阳路112号\",\"shipperPhone\":\"057764577722\",\"shipperName\":\"蒋才仓\",\"shipperAddress\":\"浙江省-温州市-苍南县-金乡镇朝阳路112号\",\"destinationDeptName\":\"北京丰台区新发地营业部\",\"labelDetail\":{\"send\":null,\"userCode\":\"122368\",\"wrapType\":\"11纸1木\",\"goodsAreas\":[{\"goodsAreaCode\":\"13\",\"transferCode\":\"P01\"},{\"goodsAreaCode\":\"13\",\"transferCode\":\"P01\"},{\"goodsAreaCode\":\"13\",\"transferCode\":\"P01\"}],\"barcode\":null,\"destinationName\":\"北京丰台新发地\",\"destTransCenterName\":\"北京丰台区新发地运作部\",\"pieces\":\"2\",\"destStationNumber\":\"0130\",\"transType\":\"精准卡航\",\"departmentCityName\":\"郑州市\",\"wblCode\":\"224682807\",\"goodsType\":null},\"waybillCode\":\"224682807\",\"consigneePhone\":\"057764577722\"}}";
//		String str = "1";
		String value = str;
			service.pushMessage(3,1,3,deviceId,title,description,value);*/
	}
//	public static void main(String[] args) {
//		String s = "'{\\\"pushType\\\":\\\"P";
//		s = s.replaceAll("\\\\\"", "\"");
//		System.out.println(s);
//	}
}