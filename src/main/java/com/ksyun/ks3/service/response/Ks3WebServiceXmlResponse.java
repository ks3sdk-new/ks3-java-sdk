package com.ksyun.ks3.service.response;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler; 

import com.ksyun.ks3.exception.Ks3ClientException;

/**
 * @author lijunwei[13810414122@163.com]  
 * 
 * @date 2014年10月14日 下午7:48:26
 * 
 * @description 
 **/
public abstract class Ks3WebServiceXmlResponse<T> extends DefaultHandler implements Ks3WebServiceResponse<T>{
	private HttpResponse response;
	protected T result;
	private List<String> preTags = new ArrayList<String>();
	/**
	 * 0返回当前节点名，1为父节点，2为父节点的父节点，以此类推
	 * @param i
	 * @return
	 */
	protected String getTag(int i)
	{
		if(i<preTags.size())
		{
			return preTags.get(preTags.size()-1-i);
		}
		return null;
	}
	protected String getTag()
	{
		return this.getTag(0);
	}
	private void setResponse(HttpResponse response)
	{
		this.response = response;
	}
	public HttpResponse getResponse()
	{
		return this.response;
	}
	public T handleResponse(HttpResponse response) {
		this.setResponse(response);
		preHandle();
		InputStream in = null;
		try {
			in = response.getEntity().getContent();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			parser.parse(in, this);
			return result;
		} catch (Exception e) {
			throw new Ks3ClientException("failed to handle the response", e);
		} finally{
			try {
				if(in!=null)
				    in.close();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public abstract void preHandle();
	@Override  
	public abstract void startDocument() throws SAXException ;
	@Override  
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
		if(qName.startsWith("ns2:"))
	    	qName = qName.substring(4);
		preTags.add(qName);
		startEle(uri,localName,qName,attributes);
    }
	public abstract void startEle(String uri, String localName, String qName, Attributes attributes) throws SAXException;
	@Override  
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if(qName.startsWith("ns2:"))
	    	qName = qName.substring(4);
		endEle(uri,localName,qName);
		if(preTags.get(preTags.size()-1).equals(qName))
		    preTags.remove(preTags.size()-1);
	}
	public abstract void endEle(String uri, String localName, String qName) throws SAXException;
	@Override  
    public void characters(char[] ch, int start, int length) throws SAXException
    {
		if(getTag()!=null){  
            String content = new String(ch,start,length);  
	    	string(content);
		}
    }
	public abstract void string(String s);
	
}