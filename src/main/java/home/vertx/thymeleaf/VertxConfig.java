package home.vertx.thymeleaf;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
public class VertxConfig {

    @Autowired
    private HttpServer server;

    @Autowired
    private Vertx vertx;

    @Autowired
    private Router router;

    @Autowired
    private ApplicationContext ctx;

    @Bean
    public Vertx vertx(){
        VertxOptions options = new VertxOptions();
        Vertx vertx = Vertx.vertx(options);
        return vertx;
    }

    @Bean
    public HttpServer server(Vertx vertx){
        HttpServer server = vertx.createHttpServer();
        return server;
    }

    @Bean
    public Router router(){
        Router router = Router.router(vertx);
        return router;
    }

    @PostConstruct
    public void gameOn(){
        vertx.deployVerticle(SimpleVerticle.class.getName() , new DeploymentOptions().setInstances(4));
        ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create();
        Map<String, Object> handlers = ctx.getBeansWithAnnotation(ThymeleafHandlerPath.class);
        for(Object object : handlers.values()){
            if(AbstractThymeleafHandler.class.isAssignableFrom(object.getClass())){
                AbstractThymeleafHandler handler = (AbstractThymeleafHandler)object;
                ThymeleafHandlerPath thymeleafSettings = handler.getClass().getAnnotation(ThymeleafHandlerPath.class);
                handler.setEngine(engine);
                handler.setTemplate(thymeleafSettings.template());
                router.get(thymeleafSettings.path()).handler(handler);
            }
        }
        server.requestHandler(router::accept).listen(80);
    }
}

