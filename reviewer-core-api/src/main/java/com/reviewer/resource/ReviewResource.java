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