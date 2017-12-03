package com.dataAccess.bean;

public class Field extends JavaAndSqlVar<Field>
{
	String javaInitialValue;
	  
	public Field()
	{
		super();
	}
	
	public Field(String sqlName)
	{
		super(sqlName);
	}

	public Field(String sqlName, String javaVarName, String javaType, String javaInitialValue)
	{
		super(sqlName, javaVarName, javaType);
		this.javaInitialValue = javaInitialValue;
	}
	
	public Field merge(Field obj)
	{
		if(obj == null)
			return new Field(this.getSqlVarName(), this.getJavaVarName(), this.getJavaType(), this.javaInitialValue);
		
		Field merged = super.mergeJASvar(obj, new Field());
		
		merged.setJavaInitialValue(mergeParam(this.getJavaInitialValue(), obj.getJavaInitialValue()));
	
		return merged;
	}

	@Override
	public String toString()
	{
		return "Field [javaInitialValue=" + javaInitialValue + ", getSqlVarName()=" + getSqlVarName()
				+ ", getJavaVarName()=" + getJavaVarName() + ", getJavaType()=" + getJavaType() + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((javaInitialValue == null) ? 0 : javaInitialValue.hashCode());
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
		Field other = (Field) obj;
		if (javaInitialValue == null)
		{
			if (other.javaInitialValue != null)
				return false;
		} else if (!javaInitialValue.equals(other.javaInitialValue))
			return false;
		return true;
	}

	public String getJavaInitialValue()
	{
		return javaInitialValue;
	}

	public void setJavaInitialValue(String javaInitialValue)
	{
		this.javaInitialValue = javaInitialValue;
	}
}
