package com.tgr.springbootmybatis.mvc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/*@EnableWebMvc*/
public class WebMvcConfig implements WebMvcConfigurer{

	/*@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(this.properties.isEnableSpringElCompiler());
		this.templateResolvers.forEach(engine::addTemplateResolver);
		this.dialects.forEach(engine::addDialect);
		engine.addDialect(new Dial);
		return engine;
	}*/
}
