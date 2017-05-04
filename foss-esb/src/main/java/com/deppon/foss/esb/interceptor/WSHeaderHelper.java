package com.deppon.foss.esb.interceptor;

import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPHeader;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 帮助类，生成esbHeader,
 * 使用方法：
 * 	客户端
 *      1、在业务处理方法中使用setClientHeaderConfig 设置HeaderConfig；
 * 		2、在客户端拦截器类中使用createClientOutHeader 方法创建header；
 *  服务端
 *      1、在消息进入拦截器中使用processServerInHeader处理soapHeader；
 *      2、在消息出去拦截器中使用createServerOutHeader方法创建outHeader;
 * @author qiancheng
 *
 */
public class WSHeaderHelper {
	private static  Logger log = Logger.getLogger(WSHeaderHelper.class);
	/**
	 * header
	 */
	private final static String HEADER = "esbHeader";
	/**
	 * header 命名空间
	 */
	private final static String HEADER_NS = "http://www.deppon.com/esb/header";
	/**
	 * 版本号
	 */
	private final static String VERSION = "version";
	/**
	 * businessId业务id
	 */
	private final static String BUSINESS_ID = "businessId";
	/**
	 * businessDesc1 业务辅助字段
	 */
	private final static String BUSINESS_DESC1 = "businessDesc1";
	/**
	 * businessDesc2 业务辅助字段
	 */
	private final static String BUSINESS_DESC2 = "businessDesc2";
	/**
	 * businessDesc3 业务辅助字段
	 */
	private final static String BUSINESS_DESC3 = "businessDesc3";
	/**
	 * 请求ID
	 */
	private final static String REQUEST_ID = "requestId";
	/**
	 * 响应ID
	 */
	private final static String RESPONSE_ID = "responseId";
	
	/**
	 * 客户端系统编码
	 */
	private final static String SOURCE_SYSTEM = "sourceSystem";
	/**
	 * 服务端系统编码
	 */
	private final static String TARGET_SYSTEM = "targetSystem";
	
	/**
	 * ESB服务编码
	 */
	private final static String ESB_SERIVCE_CODE = "esbServiceCode";
	/**
	 * 后端服务编码
	 */
	private final static String BACK_SERVICE_CODE="backServiceCode";
	/**
	 * 消息格式：SOAP、XML、JSON
	 */
	private final static String MESSAGE_FORMAT = "messageFormat";
	/**
	 * 交互模式
	 */
	private final static String EXCHANGE_PATTERN = "exchangePattern";
	/**
	 * 用户、用于权限认证/授权
	 */
	private final static String USERNAME = "username";
	/**
	 * 密码，用于权限验证/授权
	 */
	private final static String PASSWORD = "password";
	
	/**
	 * 结果状态码，表示调用成功或者失败
	 */
	private final static String RESULT_CODE = "resultCode";
	/**
	 * 失败状态
	 */
	public final static int RESULT_CODE_STATUS_FAIL=0;
	/**
	 * 成功状态
	 */
	public final static int RESULT_CODE_STATUS_SUCCESS=1;
	/**
	 * 线程安全的类，存储HeaderConfig对象(客户端存放headerConfig)，
	 */
	private static ThreadLocal<ClientHeader> clientThreadLocal = new ThreadLocal<ClientHeader>();
	
	/**
	 * 线程安全的类，服务端存储ESBHeader
	 */
	private static ThreadLocal<ServerHeader> serverThreadLocal = new ThreadLocal<ServerHeader>();
	
	
	/**
	 * 设置客户端headerConfig
	 * @param config
	 */
	public static void setClientHeader(ClientHeader config){
		//保存config到clientThreadLocal
		clientThreadLocal.set(config);
	}
	
	/**
	 * 创建客户端out header
	 */
	public static Header createClientOutHeader(){
		//获取客户端headConfig
		ClientHeader config = clientThreadLocal.get();
		//生成header
		QName qName = new QName(HEADER);
		Document doc = DOMUtils.createDocument();
		Element root = doc.createElementNS(HEADER_NS,
				HEADER);
		if(clientThreadLocal.get()== null){
			throw new RuntimeException("HeaderConfig is null");
		}
		//必填 参数不填写则抛异常。
		validateNotNull(config.getEsbServiceCode());
		validateNotNull(config.getMessageFormat());
		validateNotNull(config.getVersion());
		validateNotNull(config.getSourceSystem());
		validateNotNull(config.getVersion());
		validateNotNull(config.getMessageFormat());
		root.appendChild(createElement(VERSION, config.getVersion(), doc));
		if(isNull(config.getBusinessId())){
			root.appendChild(createElement(BUSINESS_ID, config.getBusinessId(), doc));
		}
		if(isNull(config.getBusinessDesc1())){
			root.appendChild(createElement(BUSINESS_DESC1, config.getBusinessDesc1(), doc));
		}
		if(isNull(config.getBusinessDesc2())){
			root.appendChild(createElement(BUSINESS_DESC2, config.getBusinessDesc3(), doc));
		}
		if(isNull(config.getBusinessDesc3())){
			root.appendChild(createElement(BUSINESS_DESC3, config.getBusinessDesc3(), doc));
		}
		root.appendChild(createElement(REQUEST_ID, UUID.randomUUID().toString(), doc));
		root.appendChild(createElement(SOURCE_SYSTEM, config.getSourceSystem(), doc));
		root.appendChild(createElement(ESB_SERIVCE_CODE, config.getEsbServiceCode(), doc));
		root.appendChild(createElement(MESSAGE_FORMAT, config.getMessageFormat(), doc));
		root.appendChild(createElement(EXCHANGE_PATTERN, config.getExchangePattern()+"", doc));
		if(config.getUserName()!=null&&!"".equals(config.getUserName() ) && 
				config.getPassword()!=null&&!"".equals(config.getPassword())) {
			root.appendChild(createElement(USERNAME, config.getUserName(), doc));
			root.appendChild(createElement(PASSWORD, config.getPassword(), doc));
		}
		SoapHeader header = new SoapHeader(qName, root);
		clientThreadLocal.remove();
		return header;
	}
	
	/**
	 * 处理服务器端in Header
	 */
	public static void processServerInHeader( SOAPHeader head){
		//创建ESBHeader
        Node node = (Node)head;
        ServerHeader esbHeader = null;
        if(node.hasChildNodes()){
        	NodeList list = node.getChildNodes();
        	for(int i=0;i<list.getLength();i++){
        		Node child = list.item(i);
        		//判断是否有“eabHeader”节点，若存在则遍历esbHeader，构造ESBHeader对象
        		if(HEADER.equals(child.getNodeName())){
        			esbHeader = new ServerHeader();
        			NodeList esbHeaderList = child.getChildNodes();
        			for(int j=0;j<esbHeaderList.getLength();j++){
        				Node header = esbHeaderList.item(j);
        				if(VERSION.equals(header.getNodeName())){
        					esbHeader.setVersion(header.getTextContent());
        				}else if(BUSINESS_ID.equals(header.getNodeName())){
        					esbHeader.setBusinessId(header.getTextContent());
        				}else if(BUSINESS_DESC1.equals(header.getNodeName())){
        					esbHeader.setBusinessDesc1(header.getTextContent());
        				}else if(BUSINESS_DESC2.equals(header.getNodeName())){
        					esbHeader.setBusinessDesc2(header.getTextContent());
        				}else if(BUSINESS_DESC3.equals(header.getNodeName())){
        					esbHeader.setBusinessDesc3(header.getTextContent());
        				}else if(REQUEST_ID.equals(header.getNodeName())){
        					esbHeader.setRequestId(header.getTextContent());
        				}else if(SOURCE_SYSTEM.equals(header.getNodeName())){
        					esbHeader.setSourceSystem(header.getTextContent());
        				}else if(TARGET_SYSTEM.equals(header.getNodeName())){
        					esbHeader.setTargetSystem(header.getTextContent());
        				}else if(ESB_SERIVCE_CODE.equals(header.getNodeName())){
        					esbHeader.setEsbServiceCode(header.getTextContent());
        				}else if(BACK_SERVICE_CODE.equals(header.getNodeName())){
        					esbHeader.setBackServiceCode(header.getTextContent());
        				}else if(MESSAGE_FORMAT.equals(header.getNodeName())){
        					esbHeader.setMessageFormat(header.getTextContent());
        				}else if(EXCHANGE_PATTERN.equals(header.getNodeName())){
        					esbHeader.setExchangePattern(header.getTextContent());
        				}else if(USERNAME.equals(header.getNodeName())){
        					esbHeader.setUserName(header.getTextContent());
        				}else if(PASSWORD.equals(header.getNodeName())){
        					esbHeader.setPassword(header.getTextContent());
        				}
        			}
        		}
        	}
        }
        //临时存放在在serverThreadLocal中
        serverThreadLocal.set(esbHeader);
	}
	
	/**
	 * 创建服务端out header
	 * 
	 */
	public static  Header createServerOutHeader(){
		//获取服务端ESBHeader对象
		ServerHeader esbHeader = serverThreadLocal.get();
		//生成org.apache.cxf.binding.soap.SoapHeader对象
		QName qName = new QName(HEADER);
		Document doc = DOMUtils.createDocument();
		Element root = doc.createElementNS(HEADER_NS,
				HEADER);
		if(serverThreadLocal.get()== null){
			throw new RuntimeException("ESBHeader is null");
		}
		root.appendChild(createElement(VERSION, esbHeader.getVersion(), doc));
		root.appendChild(createElement(REQUEST_ID, esbHeader.getRequestId(), doc));
		root.appendChild(createElement(RESPONSE_ID, UUID.randomUUID().toString(), doc));
		root.appendChild(createElement(SOURCE_SYSTEM, esbHeader.getSourceSystem(), doc));
		root.appendChild(createElement(TARGET_SYSTEM, esbHeader.getTargetSystem(), doc));
		root.appendChild(createElement(ESB_SERIVCE_CODE, esbHeader.getEsbServiceCode(), doc));
		root.appendChild(createElement(BACK_SERVICE_CODE, esbHeader.getEsbServiceCode(), doc));
		root.appendChild(createElement(MESSAGE_FORMAT, esbHeader.getMessageFormat(), doc));
		root.appendChild(createElement(RESULT_CODE, esbHeader.getResultCode()+"", doc));
		root.appendChild(createElement(EXCHANGE_PATTERN, esbHeader.getExchangePattern()+"", doc));
		root.appendChild(createElement(RESULT_CODE, esbHeader.getResultCode()+"", doc));
		if(null != esbHeader.getBusinessId()){
			root.appendChild(createElement(BUSINESS_ID, esbHeader.getBusinessId()+"", doc));	
		}
		if(null != esbHeader.getBusinessDesc1()){
			root.appendChild(createElement(BUSINESS_DESC1, esbHeader.getBusinessDesc1()+"", doc));	
		}
		if(null != esbHeader.getBusinessDesc2()){
			root.appendChild(createElement(BUSINESS_DESC2, esbHeader.getBusinessDesc2()+"", doc));	
		}
		if(null != esbHeader.getBusinessDesc3()){
			root.appendChild(createElement(BUSINESS_DESC3, esbHeader.getBusinessDesc3()+"", doc));	
		}
		if(esbHeader.getUserName() !=null && !"".equals(esbHeader.getUserName())) {
			root.appendChild(createElement(USERNAME, esbHeader.getUserName(), doc));
			root.appendChild(createElement(PASSWORD, esbHeader.getPassword(), doc));
		}
		SoapHeader header = new SoapHeader(qName, root);
		//serverThreadLocal.remove();
		return header;
	}
	/**
	 * 服务端调用
	 * 设置ResultCode,根据调用服务设置成功或者失败
	 */
	public static void setResultCode(int resultCode){
		ServerHeader serverHeader = serverThreadLocal.get();
		if(serverHeader == null){
			log.warn("serverHeader is null can not set resultCode");
		}else{
			serverHeader.setResultCode(resultCode);
		}
	}
	
	/**
	 * 判断是否空值，若为空值，则抛异常。
	 * @param arg
	 */
	private static  void validateNotNull(String arg){
		if("".equals(arg) || arg==null){
			throw new RuntimeException(" headerConfig value is null");
		}
	}
	/**
	 * 判断是否空值，若为空值，则返回false，若不为空值则返回true。
	 * @param arg
	 * @return
	 */
	private static boolean isNull(String arg){
		boolean b = false;
		if(!"".equals(arg) && arg!=null){
			b = true;
		}
		return b;
	}

	/**
	 * 创建Element
	 * @param key
	 * @param value
	 * @param doc
	 * @return
	 */
	protected static Element createElement(String key, String value, Document doc) {
		Element element = doc.createElementNS("", key);
		element.setTextContent(value);
		return element;
	}
	
	/**
	 * 判断服务端是否抛出异常
	 * @return
	 */
	public boolean isFault(){
		return false;
	}
}
