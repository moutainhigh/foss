package com.deppon.foss.print.labelprint.util;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

public class W3CXmlParser {

	protected Document xmlDocument = null;
	
	public W3CXmlParser(InputStream is) throws Exception {
		try	{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			xmlDocument = builder.parse(is);	
		}catch(Exception e)
		{
			e.printStackTrace();
			xmlDocument = null;
			throw new Exception("The chart xml file Parse Error!"+e.toString());
		}
	}
	
	public W3CXmlParser(InputSource is) throws Exception {
		try	{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			xmlDocument = builder.parse(is);	
		}catch(Exception e)
		{
			e.printStackTrace();
			xmlDocument = null;
			throw new Exception("The chart xml file Parse Error!"+e.toString());
		}
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
		ArrayList array = new ArrayList();
		try	{
			if(configItemName.indexOf(".")==-1)
				throw new Exception ("[ error input node name: "+configItemName+" ]");
			
			String upParent =  configItemName.substring(0,configItemName.lastIndexOf("."));
			String childNodeName = configItemName.substring(configItemName.lastIndexOf(".")+1);
			Node currentNode = getChildNodeByName(xmlDocument, upParent);
			if( currentNode != null )
			{
				if(currentNode.hasChildNodes())
				{	
					NodeList _nodelist = currentNode.getChildNodes();
					int _length = _nodelist.getLength();					
					for(int a=0;a<_length;a++){
						String _name =_nodelist.item(a).getNodeName();						
						if(_name.equals(childNodeName) ){
							String _value = (_nodelist.item(a).getChildNodes().item(0)).getNodeValue().trim();							
							array.add(_value);
						}
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return (String[])array.toArray(new String[0]);
	}
	
	public List getChildMapAsList(String configItemName){
		ArrayList array = new ArrayList();
		try	{
			if(configItemName.indexOf(".")==-1)
				throw new Exception ("[ error input node name: "+configItemName+" ]");
			
			String upParent =  configItemName.substring(0,configItemName.lastIndexOf("."));
			String childNodeName = configItemName.substring(configItemName.lastIndexOf(".")+1);
			Node currentNode = getChildNodeByName(xmlDocument, upParent);
			
			if( currentNode != null )
			{
				if(currentNode.hasChildNodes())
				{	
					NodeList _nodelist = currentNode.getChildNodes();
					int _length = _nodelist.getLength();
					for(int a=0;a<_length;a++){
						String _name =_nodelist.item(a).getNodeName();		
						int _nodetype = _nodelist.item(a).getNodeType();
						if(_name.equals(childNodeName) && Node.ELEMENT_NODE==_nodetype){
							Map m = new HashMap();
							NodeList _nlst = _nodelist.item(a).getChildNodes();
							for(int b=0;b<_nlst.getLength();b++){
								String _key = (_nlst.item(b)).getNodeName();
								int _ntype =  (_nlst.item(b)).getNodeType();
								if( Node.ELEMENT_NODE==_ntype ){
									String _value = (_nlst.item(b)).getChildNodes().item(0).getNodeValue();
									m.put(_key, _value);
								}
							}
							array.add(m);
						}
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return array;
	}

	protected Node getChildNodeByName(Node parent, String sName) throws Exception 
	{
		int index = sName.indexOf(".");
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
	
	public Hashtable getParametersOfConfigItem(String configItemName) throws Exception
	{
		Hashtable htRet = null;
		Node currentNode = getChildNodeByName(xmlDocument, configItemName);
		if( currentNode != null )
		{
			htRet = new Hashtable();
			NamedNodeMap attrList = currentNode.getAttributes();
			int iLength = attrList.getLength();
			for(int i=0; i<iLength; i++)
			{
				Node nodeTmp = attrList.item(i);
				htRet.put(nodeTmp.getNodeName(), nodeTmp.getNodeValue());
			}
		}
		return htRet;
	}	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
