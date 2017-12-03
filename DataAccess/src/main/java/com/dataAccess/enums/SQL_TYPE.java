package com.dataAccess.enums;

public enum SQL_TYPE
{
	STRING("string"), LITERAL("literal");
	
	String identifier;
	
	private SQL_TYPE(String str) {
		identifier = str;
	}
	
	
	@Override
	public String toString()
	{
		return identifier;
	}


	public static SQL_TYPE getEnum(String str) {
		for(SQL_TYPE sqlT : SQL_TYPE.values())
		{
			if(sqlT.identifier.toLowerCase().equals(str))
				return sqlT;
		}
		return STRING;
	}
	
	public static String prepValue(String string2, Object object)
	{
		if(string2.toLowerCase().equals(LITERAL.identifier))
			return object.toString();
		
		return "'" + object.toString() + "'";
	}
}
