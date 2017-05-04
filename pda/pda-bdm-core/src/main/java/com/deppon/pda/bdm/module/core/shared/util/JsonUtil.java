package com.deppon.pda.bdm.module.core.shared.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;

import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.exception.sys.utilex.JsonEncapsulateException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.utilex.JsonFormatException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.utilex.JsonParseMappingException;


/**
 * JSON工具类
 * 
 * @author 王洪领
 * @date 2012-09-06
 * @version 1.0
 *
 */
public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	
	static{
		DateFormat df = new SimpleDateFormat(Constant.PDA_HTTP_SERVICE_DATE_FORMAT);
		mapper.setDateFormat(df);
		//设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
		mapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		//输出非空字段
		//mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
	}
	/**
	 * <p>解析Json</p>
	 * <pre>
	 * 将Json字符串解析成指定的java类。
	 * </pre>
	 * @param <T>
	 * @param clazz
	 * @param jsonStr
	 * @return
	 * @throws com.deppon.pda.exception.sys.utilex.JsonParseException 
	 */
	public static <T> T parseJsonToObject(Class<T> clazz, String jsonStr) throws com.deppon.pda.bdm.module.core.shared.exception.sys.utilex.JsonParseException {
		try {
			return mapper.readValue(jsonStr, clazz);
		} catch (JsonParseException e) {
			throw new JsonFormatException(e);
		} catch (JsonMappingException e) {
			throw new JsonParseMappingException(e);
		} catch (IOException e) {
			throw new com.deppon.pda.bdm.module.core.shared.exception.sys.utilex.JsonParseException(e);
		}
	}
	/**
	 * <p>解析Json</p>
	 * <pre>
	 * 将Json字符串解析成指定的java集合。
	 * </pre>
	 * @param <T>
	 * @param clazz
	 * @param jsonStr
	 * @return
	 * @throws com.deppon.pda.exception.sys.utilex.JsonParseException 
	 */
	public static <T> List<T>  parseJsonToList(Class<T> clazz, String jsonStr) throws com.deppon.pda.bdm.module.core.shared.exception.sys.utilex.JsonParseException{
		try {
			return mapper.readValue(jsonStr, new TypeReference<List<T>>(){} );
		} catch (JsonParseException e) {
			throw new JsonFormatException(e);
		} catch (JsonMappingException e) {
			throw new JsonParseMappingException(e);
		} catch (IOException e) {
			throw new com.deppon.pda.bdm.module.core.shared.exception.sys.utilex.JsonParseException(e);
		}
	}
	/**
	 * <p>封装JSON</p>
	 * <pre>
	 * 将Java对象序列化成JSON字符串。
	 * </pre>
	 * @param obj
	 * @return
	 * @throws JsonEncapsulateException 
	 */
	public static String encapsulateJsonObject(Object obj) throws JsonEncapsulateException{
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			throw new JsonEncapsulateException(e);
		} catch (JsonMappingException e) {
			throw new JsonEncapsulateException(e);
		} catch (IOException e) {
			throw new JsonEncapsulateException(e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		
//		PdaInfo pdainfo = new PdaInfo();
//		pdainfo.setOperType("SYS_01");
//		pdainfo.setPdaCode("A001");
//		pdainfo.setStationCode("sha");
//		pdainfo.setUserCode("whl");
//		
//		PdaLoginInfo info = new PdaLoginInfo();
//		info.setPassword("123");
//		info.setPdaDataVer("1.0.0");
//		info.setPdaPgmVer("1.0.0");
//		info.setTruckCode("");
//		
//		ReqJsonData json = new ReqJsonData();
//		json.setPdaInfo(JsonUtil.encapsulateJsonObject(pdainfo));
//		json.setBody(JsonUtil.encapsulateJsonObject(info));
////		json.setPdaInfo("{\\\"operType\\\":\\\"SYS_01\\\",\\\"pdaCode\\\":\\\"A001\\\",\\\"stationCode\\\":\\\"sha\\\",\\\"userCode\\\":\\\"whl\\\"}");
////		json.setBody("bbbb");
//		
//		List<ReqJsonData> list =  new ArrayList<ReqJsonData>();
//		list.add(json);
//		
//		ReqData reqData = new ReqData();
//		reqData.setReqData(list);
////		reqData.setReqDataStr(JsonUtil.encapsulateJsonCollection(list));
//		
//		String jsonStr = JsonUtil.encapsulateJsonObject(reqData);
//		System.out.println(jsonStr);
////		String str = "{\"reqData\":[{\"body\":\"{\\\"deptCode\\\":\\\"\\\",\\\"password\\\":\\\"123\\\",\\\"pdaCode\\\":\\\"\\\",\\\"pdaDataVer\\\":\\\"1.0.0\\\",\\\"pdaPgmVer\\\":\\\"1.0.0\\\",\\\"truckCode\\\":\\\"\\\",\\\"userCode\\\":\\\"\\\"}\",\"pdaInfo\":\"{\\\"operType\\\":\\\"SYS_01\\\",\\\"pdaCode\\\":\\\"A001\\\",\\\"stationCode\\\":\\\"sha\\\",\\\"userCode\\\":\\\"whl\\\"}\"}]}";
//		
////		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
////		classMap.put("reqData", ReqJsonData.class);
////		ReqData rd = (ReqData)JSONObject.toBean(JSONObject.fromObject(jsonStr), ReqData.class, classMap);
////		JsonUtil.parseJsonToObject(PdaInfo.class, rd.getReqData().get(0).getPdaInfo());
////		
////		ReqData r2 = JsonUtil.parseJsonToObject(ReqData.class, jsonStr);
//		
//		
//		
//		
//		
////		Collection<ReqJsonData> r3 = JsonUtil.parseJsonToCollection(ReqJsonData.class,r2.getReqDataStr());
////		System.out.println(r2.getReqData().size());
//		
//		BillingLabel lb1 = new BillingLabel();
//		lb1.setBillingCode("A001");
//		lb1.setLabel("L01");
//		List<String> list1 = new ArrayList<String>();
//		list1.add("aa");
//		list1.add("bb");
//		lb1.setSlist(list1);
//		
//		List<LabelArr> list2 = new ArrayList<LabelArr>();
//		LabelArr a1 = new LabelArr();
//		a1.setArr1("aaa");
//		a1.setArr2("bbb");
//		list2.add(a1);
//		LabelArr a2 = new LabelArr();
//		a2.setArr1("aaa");
//		a2.setArr2("bbb");
//		list2.add(a2);
//		
//		lb1.setAlist(list2);
//
//		BillingLabel lb2 = new BillingLabel();
//		lb2.setBillingCode("A001");
//		lb2.setLabel("L02");
//		
//		List<BillingLabel> bllist = new ArrayList<BillingLabel>();
//		bllist.add(lb1);
//		bllist.add(lb2);
//		
//		Billing b = new Billing();
//		b.setBillingCode1("A001");
//		b.setUserCode("U021023");
//		b.setBillingLabels(bllist);
//		
//		String ss = JsonUtil.encapsulateJsonObject(b);
//		System.out.println(ss);
//		
//		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
//		classMap.put("billingLabels", BillingLabel.class);
//		classMap.put("alist", LabelArr.class);
//		
//		Billing bb = (Billing)JSONObject.toBean(JSONObject.fromObject(ss), Billing.class, classMap);
//		System.out.println(bb.getBillingCode1());
	}
}
