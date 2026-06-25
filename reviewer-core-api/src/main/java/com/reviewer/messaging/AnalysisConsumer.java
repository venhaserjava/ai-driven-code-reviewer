package com.reviewer.messaging;

// import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AnalysisConsumer {

    private static final Logger LOG = Logger.getLogger(AnalysisConsumer.class);

    @Incoming("analysis-in")
    public Uni<Void> consumeAnalysisRequest(String payload) {
        // O retorno de um Uni<Void> garante que o Quarkus processe de forma reativa
        return Uni.createFrom().item(payload)
                .onItem().invoke(p -> LOG.infof("Recebido novo evento para análise no Kafka: %s", p))
                // Próximo passo (Amanhã): Aqui chamaremos o serviço do Gemini
                .onItem().ignore().andContinueWithNull();
    }
}