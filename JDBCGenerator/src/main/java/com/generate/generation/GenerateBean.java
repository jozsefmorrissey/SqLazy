package com.generate.generation;

import java.io.IOException;
import java.util.Collection;

import javax.lang.model.element.Modifier;

import com.dataAccess.bean.Field;
import com.dataAccess.util.GenUtil;
import com.dataAccess.util.StringUtil;
import com.generate.parce.bean.Wrapper.FieldWrapper;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class GenerateBean
{
	private JdbcTypeWrapper jdbcType;

	public GenerateBean(JdbcTypeWrapper jdbcType) throws IOException
	{
		super();
		this.jdbcType = jdbcType;
	}

	public void generate() throws IOException, ClassNotFoundException
	{
		TypeSpec.Builder typeBuilder = defineBean();

		Collection<Field> fields = jdbcType.getFields();

		for (Field field : fields)
		{
			FieldWrapper fw = new FieldWrapper(field);
			String fieldName = fw.getJavaVarName();
			String methodSuffix = StringUtil.capitalizeIndex(fieldName, 0);

			FieldSpec.Builder fieldBuilder = FieldSpec.builder(fw.getJavaTypeClass(), fieldName, Modifier.PRIVATE);
			addAnnotations(field, fieldBuilder);
			if(fw.getJavaInitialValue() != null)
				fieldBuilder.initializer("$L", fw.getJavaInitialValue());
			typeBuilder.addField(fieldBuilder.build());

			MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("get".concat(methodSuffix))
					.addModifiers(Modifier.PUBLIC).addStatement("return " + fieldName).returns(fw.getJavaTypeClass());

			typeBuilder.addMethod(methodBuilder.build());

			methodBuilder = MethodSpec.methodBuilder("set".concat(methodSuffix)).addModifiers(Modifier.PUBLIC)
					.addParameter(fw.getJavaTypeClass(), fieldName).addStatement("this." + fieldName + " = " + fieldName)
					.returns(void.class);

			typeBuilder.addMethod(methodBuilder.build());
		}

		save(typeBuilder);
	}
	
	public void addAnnotations(Field field, FieldSpec.Builder fieldBuilder) throws ClassNotFoundException {
		for (AnnotationSpec ann : field.getAnnotations()) {
			fieldBuilder.addAnnotation(ann);
		}
		System.out.println(field.getAnnotations().size());
	}
	
	public void define() throws ClassNotFoundException
	{
		TypeSpec.Builder typeBuilder = defineBean();
		save(typeBuilder);
	}

	private void save(TypeSpec.Builder typeBuilder)
	{
		GenUtil.buildAndSave(jdbcType.getPackage(jdbcType.getBeanPkg()), typeBuilder);
	}

	private TypeSpec.Builder defineBean() throws ClassNotFoundException
	{
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getBeanName()).addModifiers(Modifier.PUBLIC);
		for (AnnotationSpec ann : jdbcType.getAnnotations("bean")) {
			typeBuilder.addAnnotation(ann);
		}
		return typeBuilder;
	}
}
