package com.dataAccess.enums;

import javax.xml.validation.Validator;

import com.dataAccess.util.StringUtil;

public enum JAVA_TYPE
{
	LONG("long", Long.class), INTEGER("int", Integer.class), STRING("string", String.class),
	DOUBLE("double", Double.class);
	
	private String identifier;
	private Class<?> clazz;
	
	private JAVA_TYPE(String identifier, Class<?> clazz)
	{
		this.identifier = identifier;
		this.clazz = clazz;
	}
	
	public static JAVA_TYPE getEnum(String indicator)
	{
		if(indicator == null)
			return null;
		
		for(JAVA_TYPE sqlT : JAVA_TYPE.values())
		{
			if(sqlT.identifier.toLowerCase().equals(indicator.toLowerCase()))
				return sqlT;
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <U extends Validator>  U getValidator(String str)
	{
		try
		{
			Class<?> clazz = getClass("com.generate.validate." + str + "validator");
			if(clazz != null && clazz.isInstance(Validator.class))
				return (U) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e)
		{}
		
		return null;
	}
	
	public static Class<?> getClass(String classStr)
	{
		JAVA_TYPE jt = getEnum(classStr);
		return jt == null ? null : jt.getClazz();
	}
	
	protected String getIdentifier()
	{
		return identifier;
	}

	protected Class<?> getClazz()
	{
		return clazz;
	}

}
