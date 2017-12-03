package com.dataAccess.bean;

public class Parameter extends JavaAndSqlVar<Parameter>
{
	String sqlType;
	Object value;
	  
	
	public Parameter()
	{
		super();
	}
	public Parameter(String sqlVarName, String value)
	{
		super(sqlVarName);
		this.value = value;
	}
	public Parameter(String sqlVarName, String javaVarName, String javaType)
	{
		super(sqlVarName, javaVarName, javaType);
	}
	public Parameter(String sqlName)
	{
		super(sqlName);
	}
	
	
	public Parameter(String sqlVarName, String javaVarName, String javaType, String sqlType)
	{
		super(sqlVarName, javaVarName, javaType);
		this.sqlType = sqlType;
	}
	public Parameter merge(Parameter obj)
	{
		if(obj == null)
			return new Parameter(this.getSqlVarName(), this.getJavaVarName(), this.getJavaType(), this.sqlType);
		
		Parameter merged = super.mergeJASvar(obj, new Parameter());
		
		merged.setSqlType(mergeParam(this.sqlType, obj.sqlType));
		
		return merged;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sqlType == null) ? 0 : sqlType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parameter other = (Parameter) obj;
		if (sqlType == null)
		{
			if (other.sqlType != null)
				return false;
		} else if (!sqlType.equals(other.sqlType))
			return false;
		return true;
	}
	@Override
	public String toString()
	{
		return "Parameter [sqlType=" + sqlType + ", getSqlVarName()=" + getSqlVarName() + ", getJavaVarName()="
				+ getJavaVarName() + ", getJavaType()=" + getJavaType() + "]";
	}
	public String getSqlType()
	{
		return sqlType;
	}
	public void setSqlType(String sqlType)
	{
		this.sqlType = sqlType;
	}
	public Object getValue()
	{
		return value;
	}
	public void setValue(Object value)
	{
		this.value = value;
	}
}
