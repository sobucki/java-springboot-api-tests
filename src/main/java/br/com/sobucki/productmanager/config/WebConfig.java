package br.com.sobucki.productmanager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

/**
 * Configuração para registrar os endpoints disponíveis no aplicativo
 * ao iniciar a aplicação.
 */
@Configuration
public class WebConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public WebConfig(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    /**
     * Registra todos os endpoints disponíveis no log ao iniciar a aplicação.
     * @param event o evento de contexto atualizado
     */
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.requestMappingHandlerMapping.getHandlerMethods();
        logger.info("===== Endpoints disponíveis ({}) =====", handlerMethods.size());
        handlerMethods.forEach((mapping, method) -> 
            logger.info("{} => {}.{}", 
                        mapping, 
                        method.getMethod().getDeclaringClass().getSimpleName(),
                        method.getMethod().getName()));
        logger.info("=====================================");
    }
} 