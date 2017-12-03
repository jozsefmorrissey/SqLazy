package com.generate.generation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

import com.dataAccess.util.GenUtil;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterSpec.Builder;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

public class GenerateSetup1
{
	private JdbcTypeWrapper jdbcType;

	public GenerateSetup1(JdbcTypeWrapper jdbcType) throws IOException
	{
		super();
		this.jdbcType = jdbcType;
	}
	public void generate() throws IOException
	{
		buildMapAbs();
	}
	
	public void define()
	{
		TypeSpec.Builder typeBuilder = defineMapAbs();
		
		GenUtil.buildAndSave(jdbcType.getPackage(jdbcType.getMapPkg()), typeBuilder);
	}

	private void buildMapAbs()
	{
		TypeVariableName t = TypeVariableName.get("T");
		
		ClassName cls = ClassName.get(List.class);
		ParameterizedTypeName collectRetType = ParameterizedTypeName.get(cls, t);
		
		Builder resultBuilder = ParameterSpec.builder(GenUtil.getResultType(), "results");
		Builder stringBuilder = ParameterSpec.builder(String.class, "sqlVarName");

		
		ClassName superClassName = ClassName.get(jdbcType.getMapClass(jdbcType.getMapInterfaceName()));
		TypeName superTypeName = ParameterizedTypeName.get(superClassName, t);
				
		MethodSpec.Builder meth2 = MethodSpec.methodBuilder(jdbcType.getMapMethod(new ArrayList<String>()))
				.addModifiers(Modifier.PUBLIC)
				.addParameter(resultBuilder.build())
				.addParameter(stringBuilder.build())
				.addStatement("$T<T> list = new $T<T>()", List.class, ArrayList.class)
				.addStatement("for(Map<String, Object> row : results)\nlist.add(mapRow(row, sqlVarName))")
				.addStatement("return list")
				.returns(collectRetType);
		
		TypeSpec.Builder typeBuilder = defineMapAbs()
				.addTypeVariable(t)
				.addSuperinterface(superTypeName)
				.addMethod(meth2.build());
		
		GenUtil.buildAndSave(jdbcType.getPackage(jdbcType.getMapPkg()), typeBuilder);
	}
	private TypeSpec.Builder defineMapAbs()
	{
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getMapSuperClassName())
			.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);

		return typeBuilder;
	}
}
