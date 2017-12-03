package com.generate.parce.jdbcType;

import java.util.List;

import org.w3c.dom.Element;

import com.dataAccess.bean.Query;
import com.generate.exception.XmlParceException;
import com.generate.parce.TagParcerAbs;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.generate.parce.query.DefaultTagQueryParcer;

public class ParceJdbcType extends TagParcerAbs<JdbcTypeWrapper>
{
	private TagParcerAbs<Query> queryParcer = new DefaultTagQueryParcer();
	
	public ParceJdbcType()
	{
		setTagName("jdbcClass");
	}
	
	@Override
	public JdbcTypeWrapper parce(Element node) throws XmlParceException
	{
		String name = node.getAttribute("name");
		String abstractName = node.getAttribute("abstractName");
		String pckage = node.getAttribute("package");
		List<Query> queries = queryParcer.parceAll(node);
		if(name.isEmpty())
			throw new XmlParceException(node, "Attribute name must be defined for tag jdbcClass");

		if(queries.size() == 0)
			throw new XmlParceException(node, "No queries defined for class " + name);
		
		return new JdbcTypeWrapper(name, abstractName, pckage, queries);
	}
}
