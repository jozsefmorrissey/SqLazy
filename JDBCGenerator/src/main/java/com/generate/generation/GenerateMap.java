package com.generate.generation;

import java.io.IOException;
import java.util.Collection;

import javax.lang.model.element.Modifier;

import com.dataAccess.bean.Field;
import com.dataAccess.util.GenUtil;
import com.dataAccess.util.StringUtil;
import com.generate.marker.ObjectMap;
import com.generate.parce.bean.Wrapper.FieldWrapper;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class GenerateMap
{
	private JdbcTypeWrapper jdbcType;
	private Class<?> bean;
	
	String factoryClassName;
	String factoryName;

	public GenerateMap(JdbcTypeWrapper jdbcType) throws IOException
	{
		super();
		this.jdbcType = jdbcType;
		bean = StringUtil.getClass(jdbcType.getBeanNameFQ());
		factoryClassName = jdbcType.getMapFactoryName();
		factoryName = StringUtil.unCapitalizeIndex(factoryClassName, 0);
	}

	public void generate() throws IOException
	{

		
		TypeSpec.Builder typeBuilder = defineMap();
		
		Class<?> factClass = jdbcType.getClass(factoryClassName, String.class);
		FieldSpec.Builder fieldBuilder = FieldSpec.builder(factClass, factoryName)
				.initializer("new $T()", factClass);
		
		typeBuilder.superclass(jdbcType.getMapSuperClass(bean))	
				.addSuperinterface(jdbcType.getMapInterface(bean))
				.addField(fieldBuilder.build())
				.addModifiers(Modifier.PUBLIC);

		addMapRowMethod(typeBuilder);
		
		GenUtil.buildAndSave(jdbcType.getImplPackage("map"), typeBuilder);
	}

	private TypeSpec.Builder defineMap()
	{
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getMapName());
		return typeBuilder;
	}
	
	public void define()
	{
		TypeSpec.Builder typeBuilder = defineMap()
				.addAnnotation(ObjectMap.class);
		GenUtil.buildAndSave(jdbcType.getImplPackage("map"), typeBuilder);
	}
	
	private void addMapRowMethod(TypeSpec.Builder typeImplBuilder) {
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("mapRow")
			    .addModifiers(Modifier.PUBLIC)
			    .addParameter(GenUtil.getMapType(String.class, Object.class), "row")
			    .addParameter(String.class, "sqlVarName")
			    .addStatement("$T bean = new $T()", bean, bean);

		Collection<Field> fields = jdbcType.getFields();
		String format = "bean.set$L($L.get$T().$L(row, $S))";
		for(Field field : fields) {
			FieldWrapper fw = new FieldWrapper(field);

			methodBuilder.addStatement(format,
					StringUtil.capitalizeIndex(fw.getJavaVarName(), 0),
					factoryName,
					jdbcType.getMapClass(StringUtil.getClass(fw.getJavaType()).getSimpleName()),
					jdbcType.getMapMethod(fw.getJavaType()),
					fw.getSqlVarName());			
		}
		
		methodBuilder.addStatement("return bean")
			.returns(bean);
		
		typeImplBuilder.addMethod(methodBuilder.build());
	}
}
