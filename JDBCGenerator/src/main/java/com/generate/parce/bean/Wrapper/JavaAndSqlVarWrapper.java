package com.generate.parce.bean.Wrapper;

import com.dataAccess.bean.JavaAndSqlVar;
import com.dataAccess.enums.JAVA_TYPE;
import com.dataAccess.util.StringUtil;

public class JavaAndSqlVarWrapper<T extends JavaAndSqlVar<T>>
{
	T jasVar;
	
	protected T getJasVar()
	{
		return jasVar;
	}
	
	public String getSqlVarName() {
		return jasVar.getSqlVarName();
	}

	public JavaAndSqlVarWrapper(T jasVar)
	{
		this.jasVar = jasVar;
	}
	
	public String getJavaVarName()
	{
		if(!jasVar.stringIsEmpty(jasVar.getJavaVarName()))
			return jasVar.getJavaVarName();
		
		return StringUtil.convertUpperToCammel(jasVar.getSqlVarName());
	}

	public String getJavaType()
	{
		return getJavaTypeClass().getName();
	}
	
	public Class<?> getJavaTypeClass()
	{
		Class<?> c = JAVA_TYPE.getClass(jasVar.getJavaType());
		if(c != null)
			return c;

		return String.class;
	}
}
