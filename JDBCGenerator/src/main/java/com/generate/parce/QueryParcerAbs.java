package com.generate.parce;

import java.util.List;

import org.w3c.dom.Element;

import com.dataAccess.bean.Field;
import com.dataAccess.bean.Parameter;
import com.dataAccess.bean.Query;
import com.generate.exception.XmlParceException;

public abstract class QueryParcerAbs extends TagParcerAbs<Query>
{
	TagParcerAbs<Field> colParcer;
	TagParcerAbs<Parameter> paramParcer;
	
	@Override
	public Query parce(Element elem) throws XmlParceException
	{
		String name = elem.getAttribute("name");
		String format = elem.getTextContent().replaceAll("\t", "").trim();
		if(format.isEmpty())
			throw new XmlParceException((Element) elem, "Sql string must be found inside of query tag");
		
		List<Field> fields = colParcer.parceAll(elem);
		
		List<Parameter> params = paramParcer.parceAll(elem);
		
		return new Query(name, format, fields, params);
	}

	protected TagParcerAbs<Field> getColParcer()
	{
		return colParcer;
	}

	protected void setColParcer(TagParcerAbs<Field> colParcer)
	{
		this.colParcer = colParcer;
	}

	protected TagParcerAbs<Parameter> getParamParcer()
	{
		return paramParcer;
	}

	protected void setParamParcer(TagParcerAbs<Parameter> paramParcer)
	{
		this.paramParcer = paramParcer;
	}
}
