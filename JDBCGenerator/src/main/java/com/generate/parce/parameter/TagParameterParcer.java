package com.generate.parce.parameter;

import org.w3c.dom.Element;

import com.dataAccess.bean.Parameter;
import com.generate.exception.XmlParceException;
import com.generate.parce.TagParcerAbs;

public class TagParameterParcer
		extends TagParcerAbs<Parameter>
{
	public TagParameterParcer(){setTagName("parameter");}
	
	public Parameter parce(Element node) throws XmlParceException
	{
		if(node == null || !node.getTagName().equals(getTagName()))
			return null;
		
		String sqlVarName = node.getAttribute("sqlVarName");
		String sqlType = node.getAttribute("isSqlLiteral");
		String javaVarName = node.getAttribute("javaVarName");
		String javaType = node.getAttribute("javaType");
		
		return new Parameter(sqlVarName, sqlType, javaVarName, javaType);
	}

}
