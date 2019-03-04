package com.dataAccess.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.squareup.javapoet.AnnotationSpec;

public class JdbcType
{
	private String name;
	private String abstractName;
	private String pckage;
	private List<Query> queries;
	private HashMap<String, List<AnnotationSpec>> annotationMap = new HashMap<String, List<AnnotationSpec>>();
	
	public JdbcType()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public JdbcType(String name, String abstractName, String pckage, List<Query> queries)
	{
		super();
		this.name = name;
		this.abstractName = abstractName;
		this.pckage = pckage;
		this.queries = queries;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abstractName == null) ? 0 : abstractName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pckage == null) ? 0 : pckage.hashCode());
		result = prime * result + ((queries == null) ? 0 : queries.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JdbcType other = (JdbcType) obj;
		if (abstractName == null)
		{
			if (other.abstractName != null)
				return false;
		} else if (!abstractName.equals(other.abstractName))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pckage == null)
		{
			if (other.pckage != null)
				return false;
		} else if (!pckage.equals(other.pckage))
			return false;
		if (queries == null)
		{
			if (other.queries != null)
				return false;
		} else if (!queries.equals(other.queries))
			return false;
		return true;
	}
	@Override
	public String toString()
	{
		return "JdbcType [name=" + name + ", abstractName=" + abstractName + ", pckage=" + pckage
				+ ", queries=" + queries + "]";
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getAbstractName()
	{
		return abstractName;
	}
	public void setAbstractName(String abstractName)
	{
		this.abstractName = abstractName;
	}
	public String getPackage()
	{
		return pckage;
	}
	public void setPackage(String pckage)
	{
		this.pckage = pckage;
	}
	public List<Query> getQueries()
	{
		return queries;
	}
	public void setQueries(List<Query> queries)
	{
		this.queries = queries;
	}
	
	public List<AnnotationSpec> getAnnotations(String type) {
		if (annotationMap.get(type) == null) {
			return new ArrayList<AnnotationSpec>();
		}
		return annotationMap.get(type);
	}
	public void setAnnotations(String type, List<AnnotationSpec> annotations) {
		annotationMap.put(type, annotations);
	}
}
