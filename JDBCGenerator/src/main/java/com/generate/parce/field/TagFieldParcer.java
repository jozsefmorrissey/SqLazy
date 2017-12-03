package com.generate.parce.field;

import org.w3c.dom.Element;

import com.dataAccess.bean.Field;
import com.generate.parce.TagParcerAbs;

public class TagFieldParcer extends TagParcerAbs<Field>
{

	public TagFieldParcer()
	{
		setTagName("field");
	}
	
	@Override
	public Field parce(Element node)
	{
		if(node == null || !node.getTagName().equals(getTagName()))
			return null;
		
		String sqlName = node.getAttribute("sqlVarName");
		String javaVarName = node.getAttribute("javaVarName");
		String javaType = node.getAttribute("javaType");
		String javaInitialValue = node.getAttribute("javaInitialValue");
		
		if((sqlName == null || sqlName.isEmpty()) && (javaVarName == null || javaVarName.isEmpty()))
			return null;
		return new Field(sqlName, javaVarName, javaType, javaInitialValue);
	}
}
