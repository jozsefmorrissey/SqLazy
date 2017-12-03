package com.generate.parce.field;

import java.util.List;

import org.w3c.dom.Element;

import com.dataAccess.bean.Field;
import com.generate.exception.XmlParceException;
import com.generate.parce.TagParcerAbs;

public class DefaultFieldParcer extends TagParcerAbs<Field>
{
	TagFieldParcer tcp = new TagFieldParcer();
	SqlStringFieldParcer sscp = new SqlStringFieldParcer();
	
	List<Field> fields;
	int index = 0;
	
	@Override
	public Field parce(Element node) throws XmlParceException
	{
		if(fields == null)
		{
			while(!node.getTagName().equals("query"))
				node = (Element) node.getParentNode();
			
			parceAll(node);
		}
		
		if(index == fields.size())
		{
			index = 0;
			return null;
		}
		
		return fields.get(index++);
	}

	@Override
	public List<Field> parceAll(Element node) throws XmlParceException
	{
		List<Field> tagList = tcp.parceAll(node);
		List<Field> sqlList = sscp.parceAll(node);
		
		fields = new Field().merge(tagList, sqlList);
		return fields;
	}
}
