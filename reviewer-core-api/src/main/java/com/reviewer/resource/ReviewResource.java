package com.reviewer.resource;

import com.reviewer.model.Review;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
//import io.smallrye.reactive.messaging.annotations.Channel; // depercated
import org.eclipse.microprofile.reactive.messaging.Channel;// <-- REMOVER
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import io.vertx.core.json.JsonObject;

@Path("/api/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewResource {

    // Injeta o emissor mapeado com o canal 'notifications-out' do application.properties
    @Inject
    @Channel("notifications-out")
    Emitter<String> notificationEmitter;

    @POST
    public Uni<Response> createTestReview(Review review) {
        return Panache.withTransaction(review::persist)
                .map(insertedEntity -> {
                    // Após persistir com sucesso, enviamos o JSON para o tópico do Kafka
                    String payload = JsonObject.mapFrom(insertedEntity).encode();
                    notificationEmitter.send(payload);

                    return Response.status(Response.Status.CREATED).entity(insertedEntity).build();
                });
    }
}



/*
package com.reviewer.resource;


import com.reviewer.model.Review;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewResource {

    @POST
    public Uni<Response> createTestReview(Review review) {
        // Por ser reativo, operações de escrita exigem controle de transação explícito via Panache.withTransaction
        return Panache.withTransaction(review::persist)
                .map(insertedEntity -> Response.status(Response.Status.CREATED).entity(insertedEntity).build());
    }
}

*/