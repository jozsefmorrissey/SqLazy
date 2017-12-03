package com.generate.Util;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dataAccess.bean.Parameter;
import com.dataAccess.util.GenUtil;
import com.generate.generation.bean.Constructor;
import com.generate.parce.bean.XmlProps;

public class ParcePropertiesXML
{
	public static XmlProps xmlParser()
	{
		XmlProps props = null;

		Document doc = GenUtil.getXmlObj("jdbcTemplates.xml");
		
    	props = getProperties(doc);
		return props;
	}

	private static XmlProps getProperties(Document doc)
	{
		Node properties = doc.getElementsByTagName("properties").item(0);
		Element classAnnNode = GenUtil.getByTagName(properties, "classAnnotations", 0);
		
		List<String> classAnnotations = GenUtil.parseFields(classAnnNode, "annotation");
		String superClassName = GenUtil.getByTagName(properties, "superclass", 0).getTextContent();
		String queryFunctionName = GenUtil.getByTagName(properties, "queryFunctionName", 0).getTextContent();

		Constructor qMeth = buildQueryMethod(GenUtil.getByTagName(properties, "constructor", 0));
		
		return new XmlProps(classAnnotations , superClassName, queryFunctionName, qMeth);
	}
	
	private static Constructor buildQueryMethod(Element queryMethod)
	{
	
		Element parameterSet = GenUtil.getByTagName(queryMethod, "parameters", 0);
		NodeList params = parameterSet.getElementsByTagName("param");
		List<Parameter> paramList = new ArrayList<Parameter>();
		for(int index = 0; index < params.getLength(); index++) {
//			Element elem = (Element) params.item(index);
//			
//			String paramName = GenUtil.getByTagName(elem, "paramName", 0).getTextContent();
//			String type = GenUtil.getByTagName(elem, "type", 0).getTextContent();
//			Node annoElem = GenUtil.getByTagName(elem, "paramAnnotations", 0);
//			List<String> annotations = GenUtil.parseFields(annoElem, "paramAnnotation");
			
			Parameter param = null;//new Parameter(paramName, type, annotations);
			paramList.add(param);
		}
		new Constructor(paramList);

		
		return new Constructor(paramList);
	}
}
