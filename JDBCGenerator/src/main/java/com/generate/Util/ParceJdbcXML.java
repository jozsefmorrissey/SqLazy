package com.generate.Util;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dataAccess.util.GenUtil;
import com.generate.exception.XmlParceException;
import com.generate.parce.TagParcerAbs;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.generate.parce.jdbcType.ParceJdbcType;

public class ParceJdbcXML
{
	TagParcerAbs<JdbcTypeWrapper> jdbcTypeParcer = new ParceJdbcType();
	
	public List<JdbcTypeWrapper> xmlParser()
	{
		List<JdbcTypeWrapper> jdbcTypes = null;

		Document doc = GenUtil.getXmlObj("src/main/resources/jdbcTemplates.xml");
		    	
    	try
		{
    		Element root = (Element) doc.getElementsByTagName("jdbcTemplates").item(0);
			jdbcTypes = jdbcTypeParcer.parceAll(root);
		} catch (XmlParceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jdbcTypes;
	}
}
