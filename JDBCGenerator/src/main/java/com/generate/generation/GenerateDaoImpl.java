package com.generate.generation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.dataAccess.bean.Field;
import com.dataAccess.bean.Parameter;
import com.dataAccess.bean.Query;
import com.dataAccess.util.GenUtil;
import com.dataAccess.util.StringUtil;
import com.generate.marker.JDBC;
import com.generate.parce.bean.Wrapper.FieldWrapper;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.generate.parce.bean.Wrapper.ParameterWrapper;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeSpec;

public class GenerateDaoImpl
{
	private JdbcTypeWrapper jdbcType;
	
	public GenerateDaoImpl(JdbcTypeWrapper jdbcType) throws IOException {
		super();
		this.jdbcType = jdbcType;
	}
	
	public void generate() throws IOException {
		
		TypeSpec.Builder typeImplBuilder = defineImpl()
				.addModifiers(Modifier.PUBLIC)
			    .superclass(StringUtil.getType(jdbcType.getAbstractNameFQ()))	
				.addAnnotation(GenUtil.getSuppressAnnotation());
		
		List<Query> qs = jdbcType.getQueries();
		
		for(Query q : qs) {			
			addQueryMethod(typeImplBuilder, q);
		}
						
		addBuildMethod(typeImplBuilder);
		
		addConstructor(typeImplBuilder);
		
		typeImplBuilder.addSuperinterface(StringUtil.getType(jdbcType.getDaoNameFQ()));

				
		save(typeImplBuilder);
	}

	private void save(TypeSpec.Builder typeImplBuilder)
	{
		GenUtil.buildAndSave(jdbcType.getPackage(jdbcType.getJdbcPkg()), typeImplBuilder);
	}
	
	public void define()
	{
		TypeSpec.Builder typeImplBuilder = defineImpl()
				.addAnnotation(JDBC.class);

		save(typeImplBuilder);
	}

	private TypeSpec.Builder defineImpl()
	{
		TypeSpec.Builder typeImplBuilder = TypeSpec.classBuilder(jdbcType.getJdbcName());
		return typeImplBuilder;
	}

	private void addBuildMethod(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("build")
				.addModifiers(Modifier.PRIVATE);
		
		  methodBuilder.addStatement("$T field", Field.class);
		  methodBuilder.addStatement("$T param", Parameter.class);

			  for(Query curr: jdbcType.getQueries()) {
				  methodBuilder.addStatement("this.addQuery($S, $S)", curr.getName(), curr.getFormat());
				  
				  String newFormat = "param = new Parameter($S, $S, $S, $S)";
				  String format = "this.addParameter($S, $L)";
				  for(Parameter param : curr.getParameters()) {
					  ParameterWrapper pw = new ParameterWrapper(param);
					  methodBuilder.addStatement(newFormat, pw.getSqlVarName(), pw.getJavaVarName(), pw.getJavaType(), pw.getSqlType());
					  methodBuilder.addStatement(format, curr.getName(), "param");
				  }
				  				  
				  newFormat = "field = new Field($S, $S, $S, $S)";
				  format = "this.addField($S, $L)";
				  for(Field field : curr.getFields()) {
					  FieldWrapper fw = new FieldWrapper(field);
					  methodBuilder.addStatement(newFormat, fw.getSqlVarName(), fw.getJavaVarName(), fw.getJavaType(), fw.getJavaInitialValue());
					  methodBuilder.addStatement(format, curr.getName(), "field");
				  }
			  }
		
		typeBuilder.addMethod(methodBuilder.build());
	}
	
	private void addConstructor(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC)
				.addAnnotation(Autowired.class)
				.addParameter(DataSource.class, "dseDataSource")
				.addStatement("super(dseDataSource)")
				.addStatement("build()");
		
		typeBuilder.addMethod(methodBuilder.build());
	}

	private void addQueryMethod(TypeSpec.Builder typeImplBuilder, Query q)
	{
		MethodSpec.Builder methodImplBuilder = addParameters(q)
				.addStatement("List<$T<String, Object>> results = (List)$L($S, parameters)", Map.class, "exicuteQuery", q.getName())
			    .addStatement("return new $T().$L(results, \"TODO: add field\")", StringUtil.getClass(jdbcType.getMapNameFQ()), jdbcType.getMapMethod(new ArrayList<Long>()))
				.returns(GenUtil.getListType(StringUtil.getClass(jdbcType.getBeanNameFQ())));
		
		for(Parameter param : q.getParameters()) {
			ParameterWrapper pw = new ParameterWrapper(param);
			methodImplBuilder.addParameter(StringUtil.getType(pw.getJavaType()), StringUtil.convertUpperToCammel(param.getSqlVarName()));
		}
				
		typeImplBuilder.addMethod(methodImplBuilder.build());
	}
	
	

	private Builder addParameters(Query q)
	{
		List<Parameter> params = q.getParameters();
		
		Builder method = MethodSpec.methodBuilder(q.getName())
			    .addModifiers(Modifier.PUBLIC)
			    .addStatement("Parameter[] parameters = new $T[$L]", Parameter.class, params.size());
		
		int count = 0;
		String format = "parameters[$L] = new Parameter($S, $S, $S, $S)";
		for(Parameter param : params) {
			ParameterWrapper pw = new ParameterWrapper(param);
			String sqlVar = pw.getSqlVarName();
			String javaVar = pw.getJavaVarName();
			String javaType = pw.getJavaType();
			String sqlType = pw.getSqlType();
			method.addStatement(format, count++, sqlVar, javaVar, javaType, sqlType);
		}
		
		return method;
	}
}
