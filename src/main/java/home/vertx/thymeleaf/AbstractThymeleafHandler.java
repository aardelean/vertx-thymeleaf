package home.vertx.thymeleaf;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;

public abstract class AbstractThymeleafHandler implements Handler<RoutingContext> {
    private ThymeleafTemplateEngine engine;
    private String template;
    @Override
    public void handle(RoutingContext ctx) {
        setModel(ctx);
        engine.render(ctx, "templates/"+template, res -> {
            if (res.succeeded()) {
                ctx.response().end(res.result());
            } else {
                ctx.fail(res.cause());
            }
        });
    }
    protected abstract void setModel(RoutingContext ctx);

    public void setEngine(ThymeleafTemplateEngine engine) {
        this.engine = engine;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
