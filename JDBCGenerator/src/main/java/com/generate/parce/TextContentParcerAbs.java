package com.generate.parce;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Element;

import com.generate.exception.XmlParceException;

public abstract class TextContentParcerAbs<T> extends TagParcerAbs<T>
{
	String parsed;
	Map<T, Boolean> returnedMap;
	
	protected abstract List<T> parceString(String textContent);

	@Override
	public T parce(Element node) throws XmlParceException
	{
		if(node == null)
			return null;
		
		update(node);
		
		T retVal = null;
		for(Entry<T, Boolean> entry : returnedMap.entrySet())
		{
			if(!entry.getValue()) {
				retVal = entry.getKey();
				returnedMap.put(entry.getKey(), true);
				break;
			}
		}
		
		if(retVal == null)
			resetBooleans();
		
		return retVal;
	}

	private void update(Element node)
	{
		if(!node.getTextContent().equals(parsed)) 
		{
			Collection<T> collect = parceString(node.getTextContent());
			buildMap(collect, false);
			parsed = node.getTextContent();
		}
	}

	private void buildMap(Collection<T> keys, Boolean value)
	{
		returnedMap = new HashMap<T, Boolean>();
		for(T key : keys)
		{
			returnedMap.put(key, value);
		}
	}
	
	private void resetBooleans()
	{
		buildMap(returnedMap.keySet(), false);
	}

	@Override
	public List<T> parceAll(Element node)
	{
		if(node == null)
			return new ArrayList<T>();
		
		update(node);
		
		return new ArrayList<T>(returnedMap.keySet());
	}

	protected String getParsed()
	{
		return parsed;
	}

	protected void setParsed(String parsed)
	{
		this.parsed = parsed;
	}

	protected Map<T, Boolean> getReturnedMap()
	{
		return returnedMap;
	}

	protected void setReturnedMap(Map<T, Boolean> returnedMap)
	{
		this.returnedMap = returnedMap;
	}
}
