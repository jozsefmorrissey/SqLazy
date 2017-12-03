package com.generate.parce;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.generate.exception.XmlParceException;

public abstract class TagParcerAbs<T> implements Parcer<T, Element>
{
	String tagName;
	String visited = "visited";

	/**
	 * Care should be taken when writing this function if you want
	 * parceAll to work properly.
	 */
	public abstract T parce(Element node) throws XmlParceException;

	/**
	 * 	<parent>
	 * 		<tagName.concat(s)>
	 * 			<tagName>
	 * 			</tagName>
	 * 		</tagName.concat(s)>
	 * 	</parent>
	 * 
	 * This method calls...
	 * 		if tag name is set - parce(Element(<tagName>))
	 * 		otherwise          - parce(Element(<parent>))
	 * to build list.
	 * 			
	 * note: otherwise you might want to use getNewElement()
	 * to keep track of nodes visited
	 * 
	 * @param node - parent tag
	 */
	public List<T> parceAll(Element node) throws XmlParceException
	{
		String tagName = this.tagName + "s";
		NodeList nodes = node.getElementsByTagName(tagName);

		if(nodes.getLength() > 1)
			throw new XmlParceException(node, "Expected only one element with name '" + tagName + "'.");

		Element elem = null;
		if(nodes.getLength() == 1)
			elem = (Element) nodes.item(0);
		
		if(this.tagName != null)
			return parceIterate(elem, this.tagName);

		return parceIterate(elem);
	}
	
	/**
	 * This function is intended to be used if child class wants to access more
	 * than one tag.
	 * 
	 * @param node - parent of taget tags
	 * @return - objects created by parse(Element)
	 * 
	 * @throws XmlParceException
	 */
	private List<T> parceIterate(Element node) throws XmlParceException
	{
		List<T> list = new ArrayList<T>();
		T obj;
		while((obj = parce(node)) != null)
		{
			list.add(obj);
		}
		return list;
	}
	
	/**
	 * This function is intended to be used if tagName is set.
	 * 
	 * @param node - tag containing any number of tags <tagName>
	 * @param tagName - the tag that concerns this class.
	 * @return - objects created by parse(Element)
	 * 
	 * @throws XmlParceException
	 */
	private List<T> parceIterate(Element node, String tagName) throws XmlParceException
	{
		if(node == null)
			return new ArrayList<T>();
		
		List<T> list = new ArrayList<T>();
		NodeList nodeList = node.getElementsByTagName(tagName);
		for(int index = 0; index < nodeList.getLength(); index++)
		{
			list.add(parce((Element) nodeList.item(index)));
		}
		return list;
	}

	/**
	 * This class finds an element by tagName that does not have the attribute
	 * visited set to true.
	 * 
	 * Warning - if you tags use the attribute visited use setVisited(String)
	 * 				so that the function does not destroy information.
	 * 
	 * @param elem - tag that contains tags in the form of <tagName>
	 * @param tagName - see @param elem.
	 * @return - returns null if no tags are found or if all tags have
	 * 				been visited. It resets visited when returning null;
	 */
	protected Element getNewElement(Element elem, String tagName)
	{
		String trueStr = new Boolean(true).toString(); 
		
		NodeList nodes = elem.getElementsByTagName(tagName);
		Element e = null;
		for(int index = 0; index < nodes.getLength() && e == null; index++)
		{
			e = (Element) nodes.item(index);
			if(trueStr.equals(elem.getAttribute(visited)))
					e = null;
			else
				e.setAttribute(visited, trueStr);

		}
		
		if(e == null)
			resetVisited(elem, tagName);
		
		return e;
	}
	
	/**
	 * Loops through and resets all tags to not visited.
	 * @param elem - tag that contains tags in the form of <tagName>
	 * @param tagName - see @param elem.
	 */
	public void resetVisited(Element elem, String tagName)
	{
		String falseStr = new Boolean(false).toString(); 
		
		NodeList nodes = elem.getElementsByTagName(tagName);
		for(int index = 0; index < nodes.getLength(); index++)
		{
			Element e = (Element) nodes.item(index);
			e.setAttribute(visited, falseStr);
		}
	}
	
	public String getTagName()
	{
		return tagName;
	}

	public void setTagName(String tagName)
	{
		this.tagName = tagName;
	}

	public String getVisited()
	{
		return visited;
	}

	public void setVisited(String visited)
	{
		this.visited = visited;
	}
}
