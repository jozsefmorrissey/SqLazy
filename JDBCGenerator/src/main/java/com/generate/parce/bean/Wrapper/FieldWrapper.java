package com.generate.parce.bean.Wrapper;

import com.dataAccess.bean.Field;

public class FieldWrapper extends JavaAndSqlVarWrapper<Field>
{
	public FieldWrapper(Field jasVar)
	{
		super(jasVar);
	}
	
	public String getJavaInitialValue()
	{
		if(!jasVar.stringIsEmpty(jasVar.getJavaInitialValue()))
			return jasVar.getJavaInitialValue();
		
		return null;
	}
}
