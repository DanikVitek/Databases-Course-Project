package com.danikvitek.MCPluginMarketplace.configuration

import org.springframework.context.MessageSource
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.context.support.ReloadableResourceBundleMessageSource

@Configuration
class MessageSourceConfig {
  @Bean
  def messageSource: MessageSource = {
    val messageSource = new ReloadableResourceBundleMessageSource
    messageSource setBasename "classpath:/messages"
    messageSource setDefaultEncoding "UTF-8"
    messageSource setUseCodeAsDefaultMessage false
    messageSource setCacheSeconds 0
    messageSource
  }
}
