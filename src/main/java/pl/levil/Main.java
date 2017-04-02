package pl.levil;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import pl.levil.resources.StaticResources;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URI;

/**
 * Created by levil on 2017-03-28.
 */
public class Main {
    private static final String BASE_URI = "http://0.0.0.0:8082";
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("planerDB");

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("serwer znajsuje się pod adresem "+BASE_URI);

        //---statyczne tymczasowe zasoby
        StaticResources.populateDB();
        System.in.read();
        entityManagerFactory.close();
        server.shutdownNow();
    }

    private static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("pl.levil.API");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
}
