package home.vertx.thymeleaf.contact;

import home.vertx.thymeleaf.AbstractThymeleafHandler;
import home.vertx.thymeleaf.HandlerPath;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;

@HandlerPath(path = "/contact", template = "templates/contact.html")
public class ContactPage extends AbstractThymeleafHandler {

    @Autowired
    private ContactService service;

    @Override
    protected void setModel(RoutingContext ctx) {
        ctx.put("contact", service.getOneContact(Long.valueOf(ctx.request().getParam("id"))));
    }
}
