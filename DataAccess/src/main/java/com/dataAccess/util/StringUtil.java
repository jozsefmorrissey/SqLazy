package com.dataAccess.util;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.common.reflect.TypeToken;

public class StringUtil
{
	public static void main(String... args)
	{
		System.out.println(convertUpperToCammel("HELLO_WORLD"));
	}

	public static String convertUpperToCammel(String upperCase)
	{
		upperCase = upperCase.toLowerCase();
		int index = 0;
		while (index >= 0)
		{
			index = upperCase.indexOf("_", index);
			if (index > 0 && index + 1 < upperCase.length())
			{
				char oldC = upperCase.charAt(index + 1);
				char newC = Character.toUpperCase(oldC);

				upperCase = upperCase.replace("_" + oldC, "_" + newC);
				index++;
			}
		}
		return upperCase.replace("_", "");
	}

	public static String capitalizeIndex(String upperCase, int index)
	{
		char oldC = upperCase.charAt(index);
		Character newC = Character.toUpperCase(oldC);

		upperCase = upperCase.substring(0, index).concat(newC.toString()).concat(upperCase.substring(index + 1));
		return upperCase;
	}

	public static String unCapitalizeIndex(String upperCase, int index)
	{
		char oldC = upperCase.charAt(index);
		char newC = Character.toLowerCase(oldC);

		upperCase = upperCase.replace(oldC + "", "" + newC);
		return upperCase;
	}

	public static String convertUpperToPascal(String upperCase)
	{
		return capitalizeIndex(convertUpperToCammel(upperCase), 0);
	}

	public static Type getType(String classStr)
	{
		Class<?> clazz = getClass(classStr);
		TypeToken<?> typeToken = TypeToken.of(clazz);

		return typeToken.getType();
	}

	public static Class<?> getClass(String classStr)
	{
		try
		{
			return Class.forName(classStr);
		} catch (ClassNotFoundException e)
		{}

		return null;
	}

	public static String getVarString(String varName)
	{
		return surroundSemetric(varName, "$$");
	}

	public static String reverse(String varName)
	{
		StringBuilder rev = new StringBuilder("");
		for (int i = 0; i < varName.length(); i++)
		{
			rev.append(varName.charAt(varName.length() - i - 1));
		}
		return rev.toString();
	}

	public static String surroundSemetric(String str, String cap)
	{
		return cap.concat(str).concat(reverse(cap));
	}

	public static <T> String arrayPretty(Collection<T> collect, int tabs)
	{
		String retVal = "[";
		for (Object obj : collect)
		{
			retVal += obj.toString() + ",\n" + tabStr(tabs);
		}
		int cutCount = 2 + tabs;
		return retVal.substring(0, retVal.length() - cutCount) + "]";
	}

	public static String tabStr(int count)
	{
		String retVal = "";
		for (int i = 0; i < count; i++)
			retVal += "\t";
		return retVal;
	}
}
