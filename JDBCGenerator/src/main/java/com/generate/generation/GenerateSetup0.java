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
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

public class GenerateSetup0
{
	private JdbcTypeWrapper jdbcType;

	public GenerateSetup0(JdbcTypeWrapper jdbcType) throws IOException
	{
		super();
		this.jdbcType = jdbcType;
	}

	public void generate() throws IOException
	{
		buildMapInterface();
	}
	
	public void define()
	{
		TypeSpec.Builder typeBuilder = defineMapInterface();
		
		GenUtil.buildAndSave(jdbcType.getPackage(jdbcType.getMapPkg()), typeBuilder);
	}

	private void buildMapInterface()
	{
		TypeVariableName t = TypeVariableName.get("T");
		
		ClassName cls = ClassName.get(List.class);
		ParameterizedTypeName collectRetType = ParameterizedTypeName.get(cls, t);
		
		Builder resultBuilder = ParameterSpec.builder(GenUtil.getResultType(), "results");
		Builder stringBuilder = ParameterSpec.builder(String.class, "sqlVarName");
		Builder rowBuilder = ParameterSpec.builder(GenUtil.getRowType(), "row");
		
		MethodSpec.Builder meth1 = MethodSpec.methodBuilder(jdbcType.getMapMethod(this.getClass()))
				.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
				.addParameter(rowBuilder.build())
				.addParameter(stringBuilder.build())
				.returns(t);
		
		MethodSpec.Builder meth2 = MethodSpec.methodBuilder(jdbcType.getMapMethod(new ArrayList<String>()))
				.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
				.addParameter(resultBuilder.build())
				.addParameter(stringBuilder.build())
				.returns(collectRetType);

		TypeSpec.Builder typeBuilder = defineMapInterface();
		
		typeBuilder.addTypeVariable(t)
			.addMethod(meth1.build())
			.addMethod(meth2.build());
		
		GenUtil.buildAndSave(jdbcType.getPackage(jdbcType.getMapPkg()), typeBuilder);
	}

	private TypeSpec.Builder defineMapInterface()
	{
		TypeSpec.Builder typeBuilder = TypeSpec.interfaceBuilder(jdbcType.getMapInterfaceName())
			.addModifiers(Modifier.PUBLIC);
		return typeBuilder;
	}
}
