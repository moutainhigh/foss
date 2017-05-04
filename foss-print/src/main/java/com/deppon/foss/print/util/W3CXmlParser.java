package com.deppon.foss.print.util;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * 
 * @author niujian
 *
 */
public class W3CXmlParser {

	protected Document xmlDocument = null;
	
	public W3CXmlParser(InputStream is) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		xmlDocument = builder.parse(is);
	}
	
	public W3CXmlParser(InputSource is) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		xmlDocument = builder.parse(is);
	}

	public String getValueOfConfigItem(String configItemName) throws Exception
	{
		String retValue = null;
		try	{
			Node currentNode = getChildNodeByName(xmlDocument, configItemName);
			if( currentNode != null )
			{
				if(currentNode.hasChildNodes())
				{
					NodeList nl = currentNode.getChildNodes();
					int size = nl.getLength();
					if(size==1){
						retValue = currentNode.getChildNodes().item(0).getNodeValue().trim();	
					}
					else if(size>1){
						retValue = currentNode.getChildNodes().item(1).getNodeValue().trim();
					}
					
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return retValue;
	}
	
	public String[] getValuesOfConfigItem(String configItemName) throws Exception {
		String[] retValue = null;
		ArrayList<String> array = new ArrayList<String>();
		try	{
			if(configItemName.indexOf('.')==-1){
				throw new Exception ("[ error input node name: "+configItemName+" ]");
			}
			
			String upParent =  configItemName.substring(0,configItemName.lastIndexOf('.'));
			String childNodeName = configItemName.substring(configItemName.lastIndexOf('.')+1);
			Node currentNode = getChildNodeByName(xmlDocument, upParent);
			if( currentNode != null )
			{
				if(currentNode.hasChildNodes())
				{	
					NodeList vNodelist = currentNode.getChildNodes();
					int vlength = vNodelist.getLength();					
					for(int a=0;a<vlength;a++){
						String vCldName =vNodelist.item(a).getNodeName();						
						if(vCldName.equals(childNodeName) ){
							array.add((vNodelist.item(a).getChildNodes().item(0)).getNodeValue().trim());
						}
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		Object strobj[] = array.toArray(new String[0]);
		retValue = new String[strobj.length];
		for(int a=0;a<strobj.length;a++){
			retValue[a] = (String)strobj[a];
		}
		return retValue;		
	}

	protected Node getChildNodeByName(Node parent, String sName) throws Exception 
	{
		int index = sName.indexOf('.');
		Node currentNode = null;
		boolean bHasChild = false;
		String currentNodeName = sName;
		String tailName = "";
		if( index != -1 )
		{//if not leaf node, we need tailName and currentNodeName
			currentNodeName = sName.substring(0, index);
			tailName = sName.substring(index+1);
			bHasChild = true;
		}
		else
		{//if its leaf node, we just need currentNodeName
			currentNodeName = sName;
			bHasChild = false;
		}
		NodeList list = parent.getChildNodes();
		for(int i=0 ; i<list.getLength() ; i++)
		{//
			Node nodeTmp = list.item(i);
			if(nodeTmp.getNodeName().equals(currentNodeName))
			{
				if( bHasChild )
				{//if not leaf node, recursive call getChildNodeByName()
					currentNode = getChildNodeByName(nodeTmp, tailName);
				}
				else
				{
					currentNode = nodeTmp;
				}
			}
		}
		return currentNode;
	}
	
	public Map getParametersOfConfigItem(String configItemName) throws Exception
	{
		Map returnMap = null;
		Node currentNode = getChildNodeByName(xmlDocument, configItemName);
		if( currentNode != null )
		{
			returnMap = new HashMap();
			NamedNodeMap attrList = currentNode.getAttributes();
			int iLength = attrList.getLength();
			for(int i=0; i<iLength; i++)
			{
				Node nodeTmp = attrList.item(i);
				returnMap.put(nodeTmp.getNodeName(), nodeTmp.getNodeValue());
			}
		}
		return returnMap;
	}	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
