package com.cloud.provider.configuration;

import com.cloud.provider.utils.ConstantUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket createRestApi() {
		ParameterBuilder tokenParam = new ParameterBuilder();
		List<Parameter> params = new ArrayList<>();
		tokenParam.name(ConstantUtil.CommomKey.CURRENT_USER_TOKEN).description("令牌").modelRef(new ModelRef("string"))
				.parameterType("header").required(false).build();
		params.add(tokenParam.build());
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.cloud.provider.controller"))
				.paths(PathSelectors.any()).build()
				.globalOperationParameters(params);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(ConstantUtil.Swagger.TITLE)
				.description(ConstantUtil.Swagger.DESCRIPTION)
				.termsOfServiceUrl("http://swagger.io/docs/").version("1.0").build();
	}
}
