package com.dataAccess.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class GenUtil
{
	public static void buildAndSave(String pckg, TypeSpec.Builder typeInfBuilder)
	{
		JavaFile javaInfFile = JavaFile.builder(pckg, typeInfBuilder.build()).build();

		try
		{
			javaInfFile.writeTo(new File("../DataAccess/src/generated/java/"));
		} catch (IOException e)
		{
			// TODO: implement Logger
		}
	}

	public static Document getXmlObj(String filePath)
	{
		Document doc = null;
		try
		{

			File fXmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return doc;
	}

	public static List<String> parseFields(Node tag, String tagName)
	{
		Element eElem = (Element) tag;
		ArrayList<String> values = new ArrayList<String>();
		NodeList nl = eElem.getElementsByTagName(tagName);
		for (int index = 0; index < nl.getLength(); index++)
		{
			values.add(nl.item(index).getTextContent());
		}

		return values;
	}

	public static Element getByTagName(Node node, String name, int index)
	{
		return (Element) ((Element) node).getElementsByTagName(name).item(index);
	}

	public static MethodSpec.Builder getGetter(String fieldName, Class<?> returnType)
	{
		MethodSpec.Builder getter = MethodSpec.methodBuilder("get" + StringUtil.capitalizeIndex(fieldName, 0))
				.addStatement("return $L", fieldName).addModifiers(Modifier.PUBLIC).returns(returnType);

		return getter;
	}

	public static MethodSpec.Builder getSetter(String fieldName, Class<?> returnType)
	{
		MethodSpec.Builder getter = MethodSpec.methodBuilder("set" + StringUtil.capitalizeIndex(fieldName, 0))
				.addModifiers(Modifier.PUBLIC).addParameter(returnType, fieldName)
				.addStatement("this.$L = $L", fieldName, fieldName);

		return getter;
	}

	public static void addGetterAndSetter(TypeSpec.Builder typeBuilder, String fieldName, Class<?> clazz)
	{
		MethodSpec.Builder getter = getGetter(fieldName, clazz);
		MethodSpec.Builder setter = getSetter(fieldName, clazz);

		typeBuilder.addMethod(getter.build());
		typeBuilder.addMethod(setter.build());
	}

	public static TypeName getListType(Class<?> clazz)
	{
		return ParameterizedTypeName.get(List.class, clazz);
	}

	public static TypeName getMapType(Class<?> class1, Class<?> class2)
	{
		return ParameterizedTypeName.get(Map.class, class1, class2);
	}

	public static AnnotationSpec getSuppressAnnotation()
	{
		return AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "{\"unchecked\", \"rawtypes\"}")
				.build();
	}

	public static TypeName getResultType()
	{
		ClassName listType = ClassName.get(List.class);
		return ParameterizedTypeName.get(listType, getRowType());
	}

	public static TypeName getRowType()
	{
		return GenUtil.getMapType(String.class, Object.class);
	}
}



























