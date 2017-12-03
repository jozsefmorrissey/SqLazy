package com.dataAccess.bean;

import java.util.List;

import com.dataAccess.util.StringUtil;

public class Query
{
	String name;
	String format;
	List<Field> fields;
	List<Parameter> parameters;
	
	public Query()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public Query(String name, String format, List<Field> fields, List<Parameter> parameters)
	{
		super();
		this.name = name;
		this.format = format;
		this.fields = fields;
		this.parameters = parameters;
	}
	public Query(String queryFormat)
	{
		super();
		this.format = queryFormat;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
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
		Query other = (Query) obj;
		if (fields == null)
		{
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		if (format == null)
		{
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parameters == null)
		{
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		return true;
	}
	@Override
	public String toString()
	{
		String formatStr = "";
		int previousNewLine = -1;
		int newLineIndex = 0;
		while((newLineIndex = previousNewLine + 1 + format.substring(previousNewLine + 1).indexOf("\n")) > previousNewLine)
		{
			formatStr += format.substring(previousNewLine + 1, newLineIndex + 1) + "\t\t\t\t";
			previousNewLine = newLineIndex;
		}
		formatStr += format.substring(previousNewLine + 1);
		formatStr.trim();
		
		return "Query [name=" + name + ",\n\t\t\tformat=" + formatStr + ",\n\t\t\tfields=" + 
				StringUtil.arrayPretty(fields, 4) + "\n\t\t\tparameters=" + 
				StringUtil.arrayPretty(parameters, 4) + "]";
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getFormat()
	{
		return format;
	}
	public void setFormat(String format)
	{
		this.format = format;
	}
	public List<Field> getFields()
	{
		return fields;
	}
	public void setFields(List<Field> fields)
	{
		this.fields = fields;
	}
	public List<Parameter> getParameters()
	{
		return parameters;
	}
	public void setParameters(List<Parameter> parameters)
	{
		this.parameters = parameters;
	}
}
