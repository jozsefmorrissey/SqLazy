package com.generate.generation;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.dataAccess.enums.JAVA_TYPE;
import com.dataAccess.util.GenUtil;
import com.generate.marker.ObjectMap;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class GenerateSetup2
{
	private JdbcTypeWrapper jdbcType;

	public GenerateSetup2(JdbcTypeWrapper jdbcType) throws IOException
	{
		super();
		this.jdbcType = jdbcType;
	}

	public void generate() throws IOException
	{
		buildStringMap();
		buildNumberMaps();
	}
	
	public void define()
	{
		Class<?> clazz = JAVA_TYPE.getClass("Long");
		TypeSpec.Builder typeBuilder = defineNumberMap(clazz)
				.addAnnotation(ObjectMap.class);
		
		GenUtil.buildAndSave(jdbcType.getImplPackage(jdbcType.getMapPkg()), typeBuilder);

		
		clazz = JAVA_TYPE.getClass("Double");
		typeBuilder = defineNumberMap(clazz)
				.addAnnotation(ObjectMap.class);
		
		GenUtil.buildAndSave(jdbcType.getImplPackage(jdbcType.getMapPkg()), typeBuilder);
		
		typeBuilder = defineStringMap()
				.addAnnotation(ObjectMap.class);
		GenUtil.buildAndSave(jdbcType.getImplPackage(jdbcType.getMapPkg()), typeBuilder);
	}
	
	private void buildNumberMaps()
	{
		Class<?> clazz = JAVA_TYPE.getClass("Long");
		genNumberMap(clazz);
		
		clazz = JAVA_TYPE.getClass("Double");
		genNumberMap(clazz);
	}

	private void genNumberMap(Class<?> clazz)
	{
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(jdbcType.getMapMethod(null))
				.addModifiers(Modifier.PUBLIC)
				.addParameter(GenUtil.getRowType(), "results")
				.addParameter(String.class, "sqlVarName")
				.addStatement("try{return $T.parse$T(sqlVarName)", clazz, clazz)
				.addStatement("}catch(NumberFormatException e){/*TODO: log*/}")
				.addStatement("return $T.MIN_VALUE", clazz)
				.returns(clazz);

		TypeSpec.Builder typeBuilder = defineNumberMap(clazz)
			    .superclass(jdbcType.getMapSuperClass(clazz))	
				.addSuperinterface(jdbcType.getMapInterface(clazz))
				.addMethod(methodBuilder.build());

		GenUtil.buildAndSave(jdbcType.getImplPackage(jdbcType.getMapPkg()), typeBuilder);
	}

	private TypeSpec.Builder defineNumberMap(Class<?> clazz)
	{
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getMapName(clazz.getSimpleName()))
				.addModifiers(Modifier.PUBLIC);
		return typeBuilder;
	}

	private void buildStringMap()
	{
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(jdbcType.getMapMethod(null))
				.addModifiers(Modifier.PUBLIC)
				.addParameter(GenUtil.getRowType(), "results")
				.addParameter(String.class, "sqlVarName")
				.addStatement("$T resultObj = results.get(sqlVarName)", Object.class)
				.addStatement("if(resultObj != null)\nreturn resultObj.toString()")
				.addStatement("return null")
				.returns(String.class);

		TypeSpec.Builder typeBuilder = defineStringMap()
			    .superclass(jdbcType.getMapSuperClass(String.class))	
				.addSuperinterface(jdbcType.getMapInterface(String.class))
				.addMethod(methodBuilder.build());

		GenUtil.buildAndSave(jdbcType.getImplPackage(jdbcType.getMapPkg()), typeBuilder);
	}

	private TypeSpec.Builder defineStringMap()
	{
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getMapName("String"))
				.addModifiers(Modifier.PUBLIC);
		
		return typeBuilder;
	}
}
