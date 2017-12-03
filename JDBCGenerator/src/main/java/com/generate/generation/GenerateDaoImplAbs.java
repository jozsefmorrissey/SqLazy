package com.generate.generation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dataAccess.bean.Field;
import com.dataAccess.bean.Parameter;
import com.dataAccess.bean.Query;
import com.dataAccess.enums.SQL_TYPE;
import com.dataAccess.util.GenUtil;
import com.dataAccess.util.StringUtil;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class GenerateDaoImplAbs
{
	private JdbcTypeWrapper jdbcType;
	
	String jdbcTempName = "jdbcTemplate";
	String queryMapName = "queries";
	String logName = "log";
	String msgName = "messageSource";
	
	public GenerateDaoImplAbs(JdbcTypeWrapper jdbcTypeW) throws IOException {
		super();
		this.jdbcType = jdbcTypeW;
	}
	
	public void generate() throws IOException {
		TypeSpec.Builder typeBuilder = defineImplAbs();

		typeBuilder.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
			    .addAnnotation(GenUtil.getSuppressAnnotation());
		
		buildFieldsAndGetSet(typeBuilder);
		
		buildConstructors(typeBuilder);
		
		buildMethods(typeBuilder);
		
		save(typeBuilder);
	}

	private void save(TypeSpec.Builder typeBuilder)
	{
		GenUtil.buildAndSave(jdbcType.getPackage(jdbcType.getDaoPkg()), typeBuilder);
	}

	public void define()
	{
		TypeSpec.Builder typeBuilder = defineImplAbs();

		save(typeBuilder);
	}
	
	private TypeSpec.Builder defineImplAbs()
	{
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getAbstractName());
		return typeBuilder;
	}

	private void buildMethods(TypeSpec.Builder typeBuilder)
	{
		buildAddQuery(typeBuilder);
		
		buildAddParameter(typeBuilder);
		
		buildAddField(typeBuilder);
	
		buildSetParameterValue(typeBuilder);

		buildExtractResult(typeBuilder);

		buildExicuteQuery(typeBuilder);
		
		buildMockData(typeBuilder);
		
		buildExicuteMockQuery(typeBuilder);
		
		buildBuildQuery(typeBuilder);
		
		buildGetRowCount(typeBuilder);
		
		buildLogRequestTimer(typeBuilder);
		
		buildMockDataRowBuilder(typeBuilder);
		
		buildMockDataAddWord(typeBuilder);
	}

	private void buildFieldsAndGetSet(TypeSpec.Builder typeBuilder)
	{
		FieldSpec.Builder logFieldBuilder = FieldSpec.builder(Log.class, logName, Modifier.PRIVATE)
				.initializer("$T.getLog($L.class)", LogFactory.class, jdbcType.getAbstractName());
		
		FieldSpec.Builder msgFieldBuilder = FieldSpec.builder(AbstractMessageSource.class, msgName, Modifier.PRIVATE)
				.addAnnotation(Autowired.class);


		typeBuilder.addField(logFieldBuilder.build())
			.addField(msgFieldBuilder.build())
			.addField(GenUtil.getMapType(String.class, Query.class), queryMapName, Modifier.PRIVATE)
		    .addField(JdbcTemplate.class, jdbcTempName, Modifier.PRIVATE);

		
		GenUtil.addGetterAndSetter(typeBuilder, logName, Log.class);
		GenUtil.addGetterAndSetter(typeBuilder, msgName, AbstractMessageSource.class);
		GenUtil.addGetterAndSetter(typeBuilder, jdbcTempName, JdbcTemplate.class);
	}

	private void buildAddQuery(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("addQuery")
			    .addModifiers(Modifier.PROTECTED)
			    .addParameter(String.class, "name")
			    .addParameter(String.class, "queryFormat")
			    .addStatement("$T q = new Query(queryFormat)", Query.class)
			    .addStatement("((Map<String, Query>)$L).put(name, q)", queryMapName);
		
		typeBuilder.addMethod(methodBuilder.build());
	}

	private void buildAddParameter(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("addParameter")
				    .addModifiers(Modifier.PROTECTED)
				    .addParameter(String.class, "queryName")
				    .addParameter(Parameter.class, "param")
				    .addStatement("if(((Map<String, Query>)$L).containsKey(queryName))\n"
				    		+ "((Map<String, Query>)$L).get(queryName).getParameters()"
				    		+ ".add(param)"
				    		, queryMapName, queryMapName);
		
		typeBuilder.addMethod(methodBuilder.build());
	}

	private void buildAddField(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("addField")
			    .addModifiers(Modifier.PROTECTED)
			    .addParameter(String.class, "queryName")
			    .addParameter(Field.class, "fieldName")
			    .addStatement("if(((Map<String, Query>)$L).containsKey(queryName))\n"
			    		+ "((Map<String, Query>)$L).get(queryName).getFields().add(fieldName)"
			    		, queryMapName, queryMapName);
	
		typeBuilder.addMethod(methodBuilder.build());
	}

	private void buildSetParameterValue(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("setParameterValue")
			    .addModifiers(Modifier.PROTECTED)
			    .addParameter(String.class, "queryName")
			    .addParameter(Parameter.class, "paramName")
			    .addParameter(Object.class, "value")
				.addStatement("if(((Map<String, Query>)$L).containsKey(queryName))\n"
						+ "for(Parameter s : ((Map<String, Query>)$L).get(queryName).getParameters())\n"
						+ "if(s.getSqlVarName().equals(paramName))\n"
						+ "s.setValue(value)"
						, queryMapName, queryMapName);
	
		typeBuilder.addMethod(methodBuilder.build());
	}

	private void buildExtractResult(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("extractResult")
			    .addModifiers(Modifier.PROTECTED)
			    .addParameter(GenUtil.getMapType(String.class, Object.class), "results")
			    .addParameter(Field.class, "field")
			    .addStatement("Object resultObj = results.get(field)")
			    .addStatement("if(resultObj != null)\nreturn resultObj.toString()")
			    .addStatement("return null")
			    .returns(String.class);
				
	
		typeBuilder.addMethod(methodBuilder.build());
	}
	
	private void buildExicuteQuery(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("exicuteQuery")
			    .addModifiers(Modifier.PROTECTED)
			    .addParameter(String.class, "queryName")
			    .addParameter(Parameter[].class, "parameters")
			    .addStatement("long startJavaLogTime = $T.getInstance().getTimeInMillis()", Calendar.class)
			    .addStatement("$T query = buildQuery(queryName, parameters)", String.class)
			    .addStatement("$T rows = (List) jdbcTemplate.queryForList(query)", GenUtil.getResultType())
			    .addStatement("logRequestTimer(startJavaLogTime, query)")
			    .addStatement("return rows")
			    .returns(GenUtil.getResultType());
	
		typeBuilder.addMethod(methodBuilder.build());
	}
	
	private void buildGetRowCount(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("getRowCount")
			    .addModifiers(Modifier.PROTECTED)
			    .addParameter(String.class, "queryName")
			    .addParameter(Parameter[].class, "parameters")
			    .addStatement("return exicuteQuery(queryName, parameters).size()")
			    .returns(int.class);

		typeBuilder.addMethod(methodBuilder.build());
	}
	
	private void buildLogRequestTimer(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("logRequestTimer")
			    .addModifiers(Modifier.PROTECTED)
			    .addParameter(long.class, "startJavaLogTime")
			    .addParameter(String.class, "query");
	
		typeBuilder.addMethod(methodBuilder.build());
	}
	
	private void buildExicuteMockQuery(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("exicuteMockQuery")
			    .addModifiers(Modifier.PROTECTED)
			    .addParameter(String.class, "queryName")
			    .addParameter(Parameter[].class, "parameters")
			    .addStatement("return mockData(queryName)")
			    .returns(GenUtil.getResultType());
					
		typeBuilder.addMethod(methodBuilder.build());
	}
	
	private void buildBuildQuery(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("buildQuery")
			    .addModifiers(Modifier.PROTECTED)
			    .addParameter(String.class, "queryName")
			    .addParameter(Parameter[].class, "parameters")
			    .addStatement("$T q = (Query) $L.get(queryName)", Query.class, queryMapName)
			    .addStatement("String queryStr = q.getFormat()")
			    .addStatement("for(Parameter param : parameters)\nqueryStr = makeParameter(queryStr, param)")
			    .addStatement("System.out.println(\"Query: \" + queryStr)")
			    .addStatement("return queryStr")
			    .returns(String.class);
				
		buildMakeParameter(typeBuilder);
	
		typeBuilder.addMethod(methodBuilder.build());
	}
	
	private void buildMakeParameter(TypeSpec.Builder typeBuilder) {
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("makeParameter")
			    .addModifiers(Modifier.PRIVATE)
			    .addParameter(String.class, "queryStr")
			    .addParameter(Parameter.class, "param")
			    .addStatement("String varStr = $T.getVarString(param.getSqlType())", StringUtil.class)
			    .addStatement("String valueStr = $T.prepValue(param.getSqlType(), param.getValue())", SQL_TYPE.class)
			    .addStatement("queryStr = queryStr.replace(varStr, valueStr)")
			    .addStatement("return queryStr")
			    .returns(String.class);
				
	
		typeBuilder.addMethod(methodBuilder.build());
	}

	private void buildConstructors(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.constructorBuilder()
			    .addModifiers(Modifier.PROTECTED)
				.addAnnotation(Autowired.class)
				.addParameter(DataSource.class, "dseDataSource")
				.addStatement("$L = new JdbcTemplate(dseDataSource)", jdbcTempName)
			    .addStatement("$L = new $T<String, Query>()", queryMapName, HashMap.class);
	
		typeBuilder.addMethod(methodBuilder.build());
	}

	private void buildMockData(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("mockData")
			    .addModifiers(Modifier.PRIVATE)
			    .addParameter(String.class, "name")
			    .addStatement("Query q = (Query) $L.get(name)", queryMapName)
			    .addStatement("$T results = new $T<$T>()", GenUtil.getResultType(), ArrayList.class, GenUtil.getRowType())
			    .addStatement("StringBuilder[] words = new StringBuilder[]{new StringBuilder(\"First\"), new StringBuilder(\"Second\"), new StringBuilder(\"Third\")}")
			    .addStatement("for(int i = 0; i < 3; i++)\nmockDataRowBuilder(results, q, words[i])")
			    .addStatement("return results")
			    .returns(GenUtil.getResultType());
				
		typeBuilder.addMethod(methodBuilder.build());
	}

	private void buildMockDataRowBuilder(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("mockDataRowBuilder")
			    .addModifiers(Modifier.PRIVATE)
			    .addParameter(List.class, "results")
			    .addParameter(Query.class, "q")
			    .addParameter(StringBuilder.class, "word")
			    
			    .addStatement("$T row = new $T()", Map.class, HashMap.class)
			    .addStatement("for($T field : q.getFields())\nmockDataAddWord(row, word, field.getSqlVarName())", Field.class)
			    .addStatement("results.add(row)")
			    .addStatement("return row")
			    .returns(Map.class);
				
	
		typeBuilder.addMethod(methodBuilder.build());
	}

	private void buildMockDataAddWord(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("mockDataAddWord")
			    .addModifiers(Modifier.PROTECTED)
			    .addParameter(Map.class, "row")
			    .addParameter(StringBuilder.class, "word")
			    .addParameter(String.class, "field")
			    .addStatement("row.put(field, word)")
			    .addStatement("word.append(word)");
				
	
		typeBuilder.addMethod(methodBuilder.build());
	}
}
