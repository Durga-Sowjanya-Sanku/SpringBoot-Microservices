package com.example.apigateway.route;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

@Configuration
public class InventoryServiceRoutes {

    @Bean
    public RouterFunction<ServerResponse> inventoryRoutes() {
        return GatewayRouterFunctions.route("inventory-service")
                .route(RequestPredicates.path("/api/inventory/venue/{venueId}"),
                        request -> forwardWithPathVariable(request, "venueId", "http://localhost:8080/api/inventory/venue/"))

                .route(RequestPredicates.path("/api/inventory/event/{eventId}"),
                        request -> forwardWithPathVariable(request, "eventId", "http://localhost:8080/api/inventory/event/"))
                .build();
    }

    private ServerResponse forwardWithPathVariable(ServerRequest request, String pathVariable, String baseUrl) throws Exception {
        String value = request.pathVariable(pathVariable);
        String finalUrl = baseUrl + value;
        return HandlerFunctions.http(finalUrl).handle(request);
    }
}
