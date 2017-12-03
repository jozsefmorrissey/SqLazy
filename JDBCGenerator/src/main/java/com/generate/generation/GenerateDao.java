package com.generate.generation;

import java.io.IOException;
import java.util.List;

import javax.lang.model.element.Modifier;

import com.dataAccess.bean.Parameter;
import com.dataAccess.bean.Query;
import com.dataAccess.util.GenUtil;
import com.dataAccess.util.StringUtil;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.generate.parce.bean.Wrapper.ParameterWrapper;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class GenerateDao
{
	private JdbcTypeWrapper jdbcType;
	
	public GenerateDao(JdbcTypeWrapper jdbcType) throws IOException {
		super();
		this.jdbcType = jdbcType;
	}
	
	public void generate() throws IOException {
		
		TypeSpec.Builder typeInfBuilder = defineDAO();
		

		List<Query> qs = jdbcType.getQueries();

		for(Query q : qs) {
			
			addQueryMethod(typeInfBuilder, q.getName(), q);
		}
		
		save(typeInfBuilder);
	}
	
	public void define()
	{
		TypeSpec.Builder typeInfBuilder = defineDAO();

		save(typeInfBuilder);
	}

	private void save(TypeSpec.Builder typeInfBuilder)
	{
		GenUtil.buildAndSave(jdbcType.getPackage("dao"), typeInfBuilder);
	}

	private TypeSpec.Builder defineDAO()
	{
		TypeSpec.Builder typeInfBuilder = TypeSpec.interfaceBuilder(jdbcType.getDaoName())
			    .addModifiers(Modifier.PUBLIC);
		return typeInfBuilder;
	}

	private void addQueryMethod(TypeSpec.Builder typeInfBuilder,
			String methodName, Query q)
	{
		MethodSpec.Builder methodInfBuilder = MethodSpec.methodBuilder(methodName)
			    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
				.returns(GenUtil.getListType(StringUtil.getClass(jdbcType.getBeanNameFQ())));
		
		for(Parameter param : q.getParameters()) {
			ParameterWrapper pw = new ParameterWrapper(param);
			methodInfBuilder.addParameter(pw.getJavaTypeClass(), StringUtil.convertUpperToCammel(param.getSqlVarName()));
		}
		
		typeInfBuilder.addMethod(methodInfBuilder.build());
	}
}
