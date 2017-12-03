package com.generate.parce.bean.Wrapper;

import com.dataAccess.bean.Parameter;
import com.dataAccess.enums.SQL_TYPE;

public class ParameterWrapper extends JavaAndSqlVarWrapper<Parameter>
{

	public ParameterWrapper(Parameter jasVar)
	{
		super(jasVar);
	}

	public String getSqlType() {		
		return SQL_TYPE.getEnum(getJasVar().getSqlType()).toString();
	}
}
