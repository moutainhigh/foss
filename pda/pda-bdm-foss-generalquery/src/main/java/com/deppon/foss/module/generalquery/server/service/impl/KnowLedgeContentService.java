package com.deppon.foss.module.generalquery.server.service.impl;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.generalquery.server.dao.IKnowledgeDao;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeContentEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeContentInfoEntity;
import com.deppon.foss.module.generalquery.shared.domain.KnowledgeInfoEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.GeneralQueryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;




/**
 * 查询某个标题对应的内容服务类
 * @author wfy       
 * @version 1.0     
 * @created 2014-9-19 
 */
public class KnowLedgeContentService implements IBusinessService<KnowledgeContentEntity, KnowledgeInfoEntity> {
	
	private static final Log LOG = LogFactory.getLog(KnowLedgeContentService.class);
	
	private IKnowledgeDao knowledgeDao;
	
	public void setKnowledgeDao(IKnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}


	/**
	 * 解析包体
	 */
	@Override
	public KnowledgeInfoEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		//解析内容
		KnowledgeInfoEntity knowledgeInfoEntity = JsonUtil.parseJsonToObject(KnowledgeInfoEntity.class, asyncMsg.getContent());
		
		return knowledgeInfoEntity;
	}
	
	
	/**
	 * 服务方法
	 */
	@Override
	public KnowledgeContentEntity service(AsyncMsg asyncMsg,
			KnowledgeInfoEntity knowledgeInfoEntity) throws PdaBusiException {
		
		try {
			//验证数据有效性
			this.validate(knowledgeInfoEntity);
			
			KnowledgeContentEntity kContentEntity = null;
			KnowledgeContentInfoEntity  knowledgeContent = new KnowledgeContentInfoEntity();
			//封装实体
	//		knowledgeContent = toKnowledgeContent(knowledgeInfoEntity);
			knowledgeContent.setTitle(knowledgeInfoEntity.getTitle());
			//数据库中查询数据（查询标题对应的有效内容）
			kContentEntity = knowledgeDao.knowledgeContent(knowledgeContent);
			
			if(kContentEntity!=null){
				String content = kContentEntity.getContent();
				//将内容去css操作
				kContentEntity.setContent(delHTMLTag(content));
			}else{
				kContentEntity = new KnowledgeContentEntity();
			}
			kContentEntity.setVersion(String.valueOf(System.currentTimeMillis()));
			
			return kContentEntity;
		} catch (BusinessException e) {
			LOG.error("知识库内容查询服务异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}
	//去掉css配置
	public String delHTMLTag(String htmlStr){ 
	    htmlStr = htmlStr.replaceAll("<br>|<div>", "@");
	    htmlStr = htmlStr.replaceAll("<br[^>]+>|<div[^>]+>", "@");
        String regExStyle="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regExHtml="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern pStyle=Pattern.compile(regExStyle,Pattern.CASE_INSENSITIVE); 
        Matcher mStyle=pStyle.matcher(htmlStr); 
        htmlStr=mStyle.replaceAll(""); //过滤style标签 
         
        Pattern pHtml=Pattern.compile(regExHtml,Pattern.CASE_INSENSITIVE); 
        Matcher mHtml=pHtml.matcher(htmlStr); 
        htmlStr=mHtml.replaceAll(""); //过滤html标签 
        
        htmlStr=htmlStr.replace(" ","");
        htmlStr=htmlStr.replaceAll("\t|&nbsp;","");
        htmlStr=htmlStr.replace("“","");
        htmlStr=htmlStr.replace("”","");
        htmlStr=htmlStr.replaceAll("　","");

          
        return htmlStr.trim(); //返回文本字符串 
  
		/*htmlStr=htmlStr.replace(" ","");
		htmlStr=htmlStr.replaceAll("\t","");
		htmlStr=htmlStr.replaceAll("&nbsp;"," ");
		htmlStr = htmlStr.replaceAll("<br>|<div>", "@");
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 
        
        htmlStr=htmlStr.replace("“","");
        htmlStr=htmlStr.replace("”","");
//        htmlStr=htmlStr.replaceAll("　","");
          
	   //返回文本字符串 
		return htmlStr;
	*/
	} 

	
	/*
	public KnowledgeContentInfoEntity toKnowledgeContent(KnowledgeInfoEntity knowledgeInfoEntity){
		KnowledgeContentInfoEntity  knowledgeContent = new KnowledgeContentInfoEntity();
		//给属性赋值
		knowledgeContent.setTitle(knowledgeInfoEntity.getTitle());
		
		long versionLong=0;
		String versionString = knowledgeInfoEntity.getVersion();
		if(versionString !=null && !"".equals(versionString)){
			 versionLong = Long.parseLong(versionString);
		}
		
		knowledgeContent.setVersionLong(versionLong);
		if(versionLong!=0){
			knowledgeContent.setVersionDate(new Date(versionLong));
		}
		return knowledgeContent;
	}
	*/
	/**
	 * 验证数据有效性
	 * @param qryTraceInfoEntity
	 */
	private void validate(KnowledgeInfoEntity knowledgeInfoEntity) {
		
		Argument.notNull(knowledgeInfoEntity, "KnowledgeInfoEntity");
		// 判断知识主题
		Argument.hasText(knowledgeInfoEntity.getTitle(), "KnowledgeBaseEntity.title");
		
	}
	

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return GeneralQueryConstant.OPER_TYPE_AID_KNOWLEDGE_CONTENT.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	

	
}
