package home.vertx.thymeleaf;

import home.vertx.thymeleaf.contact.ContactEndpoint;
import io.advantageous.qbit.server.EndpointServerBuilder;
import io.advantageous.qbit.server.ServiceEndpointServer;
import io.advantageous.qbit.vertx.http.VertxHttpServerBuilder;
import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
@Import({JOOQConfiguration.class, DataSourceConfiguration.class})
@ComponentScan("home.vertx.thymeleaf.contact")
@PropertySource("classpath:/application.properties")
public class StarterVerticle extends AbstractVerticle{

    private static final Vertx vertx = Vertx.vertx();

    @Autowired
    private HttpServer server;

    @Autowired
    private Router router;

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private ServiceEndpointServer serviceEndpointServer;

    @Autowired
    private ThymeleafTemplateEngine engine;

    @Autowired
    private StaticHandler staticHandler;

    public static void main(String[] args) throws Exception {
        vertx.deployVerticle(StarterVerticle.class.getName());
    }

    @Override
    public void start() throws Exception {
        SpringApplication.run(StarterVerticle.class);
        super.start();
    }


    @Bean
    public HttpServer server(){
        HttpServer server = vertx.createHttpServer();
        return server;
    }

    @Bean
    public Router router(StaticHandler staticHandler,
                         ApplicationContext ctx,
                         ThymeleafTemplateEngine engine){
        Router router = Router.router(vertx);
        router.route("/static/*").handler(staticHandler);
        registerThymeleafHandlers(ctx, engine, router);
        return router;
    }

    private void registerThymeleafHandlers(ApplicationContext ctx, ThymeleafTemplateEngine engine, Router router) {
        Map<String, Object> handlers = ctx.getBeansWithAnnotation(HandlerPath.class);
        for(Object object : handlers.values()){
            if(AbstractThymeleafHandler.class.isAssignableFrom(object.getClass())){
                AbstractThymeleafHandler handler = (AbstractThymeleafHandler)object;
                HandlerPath thymeleafSettings = handler.getClass().getAnnotation(HandlerPath.class);
                handler.setEngine(engine);
                handler.setTemplate(thymeleafSettings.template());
                router.get(thymeleafSettings.path()).handler(handler);
            }else if(Handler.class.isAssignableFrom(object.getClass())) {
                Handler handler = (Handler)object;
                HandlerPath pathSettings = handler.getClass().getAnnotation(HandlerPath.class);
                router.get(pathSettings.path()).handler(handler);
            }
        }
    }

    @Bean
    public io.advantageous.qbit.http.server.HttpServer qbitServer(HttpServer server, Router router){
        io.advantageous.qbit.http.server.HttpServer qbitServer = VertxHttpServerBuilder.vertxHttpServerBuilder()
                .setHttpServer(server)
                .setRouter(router)
                .setVertx(vertx)
                .build();
        return qbitServer;
    }

    @Bean
    public ServiceEndpointServer serviceEndpointServer(io.advantageous.qbit.http.server.HttpServer qbitServer,
                                                       ContactEndpoint endpoint){
        return EndpointServerBuilder.endpointServerBuilder()
                .setHttpServer(qbitServer)
                .setUri("/api/")
                .addServices(endpoint)
                .build();
    }

    @Bean
    public StaticHandler staticHandler(){
        return StaticHandler.create().setWebRoot("dist").setCachingEnabled(false);
    }

    @Bean
    public ThymeleafTemplateEngine engine(){
        ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create();
        return engine;
    }

    @PostConstruct
    public void gameOn(){
        server.requestHandler(router::accept);
        serviceEndpointServer.startServer();
        server.listen(80);
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

}