package com.generate.parce.bean;

import java.util.List;

import com.generate.generation.bean.Constructor;

public class XmlProps
{
	List<String> classAnnotations;
	String superFQClassName;
	String queryFunctionName;
	Constructor constructor;
	public XmlProps(List<String> classAnnotations, String superFQClassName, String queryFQName, Constructor constructor)
	{
		super();
		this.classAnnotations = classAnnotations;
		this.superFQClassName = superFQClassName;
		this.queryFunctionName = queryFQName;
		this.constructor = constructor;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classAnnotations == null) ? 0 : classAnnotations.hashCode());
		result = prime * result + ((constructor == null) ? 0 : constructor.hashCode());
		result = prime * result + ((queryFunctionName == null) ? 0 : queryFunctionName.hashCode());
		result = prime * result + ((superFQClassName == null) ? 0 : superFQClassName.hashCode());
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
		XmlProps other = (XmlProps) obj;
		if (classAnnotations == null)
		{
			if (other.classAnnotations != null)
				return false;
		} else if (!classAnnotations.equals(other.classAnnotations))
			return false;
		if (constructor == null)
		{
			if (other.constructor != null)
				return false;
		} else if (!constructor.equals(other.constructor))
			return false;
		if (queryFunctionName == null)
		{
			if (other.queryFunctionName != null)
				return false;
		} else if (!queryFunctionName.equals(other.queryFunctionName))
			return false;
		if (superFQClassName == null)
		{
			if (other.superFQClassName != null)
				return false;
		} else if (!superFQClassName.equals(other.superFQClassName))
			return false;
		return true;
	}
	public List<String> getClassAnnotations()
	{
		return classAnnotations;
	}
	public void setClassAnnotations(List<String> classAnnotations)
	{
		this.classAnnotations = classAnnotations;
	}
	public String getSuperClassName() {
		int index = getSuperFQClassName().lastIndexOf('.');
		return getSuperFQClassName().substring(index + 1);
	}
	public String getSuperPackageName() {
		int index = getSuperFQClassName().lastIndexOf('.');
		return getSuperFQClassName().substring(0, index);
	}
	public String getSuperFQClassName()
	{
		if(superFQClassName.isEmpty())
			return "com.generated.bean.GenericJDBC";
		return superFQClassName;
	}
	public void setSuperFQClassName(String superFQClassName)
	{
		this.superFQClassName = superFQClassName;
	}
	public String getQueryName()
	{
		if(queryFunctionName.isEmpty())
			return "exicuteQuery";
		return queryFunctionName;
	}
	public void setQueryName(String queryFQName)
	{
		this.queryFunctionName = queryFQName;
	}
	public Constructor getConstructor()
	{
		return constructor;
	}
	public void setConstructor(Constructor constructor)
	{
		this.constructor = constructor;
	}
}
