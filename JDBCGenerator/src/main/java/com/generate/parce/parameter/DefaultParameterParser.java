package com.generate.parce.parameter;

import java.util.List;

import org.w3c.dom.Element;

import com.dataAccess.bean.Parameter;
import com.generate.exception.XmlParceException;
import com.generate.parce.TagParcerAbs;
import com.generate.parce.parameter.SqlStringParamParcer;
import com.generate.parce.parameter.TagParameterParcer;

public class DefaultParameterParser extends TagParcerAbs<Parameter>
{
	TagParameterParcer tcp = new TagParameterParcer();
	SqlStringParamParcer sscp = new SqlStringParamParcer();
	
	List<Parameter> parameters;
	int index = 0;
	
	@Override
	public Parameter parce(Element node) throws XmlParceException
	{
		if(parameters == null)
		{
			while(!node.getTagName().equals("query"))
				node = (Element) node.getParentNode();
			
			parceAll(node);
		}
		
		if(index == parameters.size())
		{
			index = 0;
			return null;
		}
		
		return parameters.get(index++);
	}

	@Override
	public List<Parameter> parceAll(Element node) throws XmlParceException
	{
		List<Parameter> tagList = tcp.parceAll(node);
		List<Parameter> sqlList = sscp.parceAll(node);
		
		parameters = new Parameter().merge(tagList, sqlList);
		return parameters;
	}
}
