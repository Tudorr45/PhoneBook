package com.nagarro.util;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.mapping.ArangoJack;
import com.nagarro.model.Contact;
import com.nagarro.repo.ContactRepo;
import io.quarkus.runtime.StartupEvent;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import javax.enterprise.event.Observes;
import java.util.Collections;

public class CacheInitializer {

    private static IgniteCache<String, Contact> cache;

    public void onStart(@Observes StartupEvent ev) {
        IgniteConfiguration cfg = new IgniteConfiguration();

        // The node will be started as a client node.
        cfg.setClientMode(true);

        // Classes of custom Java logic will be transferred over the wire from this app.
        cfg.setPeerClassLoadingEnabled(true);

        // Setting up an IP Finder to ensure the client can locate the servers.
        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));
        cfg.setDiscoverySpi(new TcpDiscoverySpi().setIpFinder(ipFinder));

        // Starting the node
        Ignite ignite = Ignition.start(cfg);

        // Create an IgniteCache and put some values in it.
        cache = ignite.getOrCreateCache("contactCache");
        loadCache();

//        Contact contact = new Contact();
//        contact.setFirstName("Tudor");
//        contact.setLastName("Staicu");
//        contact.setPhone("073573543");
//        contact.setCreationDate(LocalDate.now());
//        cache.put("Obj1", contact);

        System.out.println(">> Created the cache and add the values.");

        // Executing custom Java compute task on server nodes.
        ignite.compute(ignite.cluster().forServers()).broadcast(new CacheInitializer.RemoteTask());

        System.out.println(">> Compute task is executed, check for output on the server nodes.");

        // Disconnect from the cluster.
        ignite.close();
    }

    private void loadCache() {
        String DB_NAME = "phonebookDB";
        String COLLECTION_NAME = "firstCollection";

        ArangoDB arangoDB = new ArangoDB.Builder()
                .serializer(new ArangoJack())
                .user("root")
                .password("tudor")
                .build();

        String getAllQuery = "FOR c IN " + COLLECTION_NAME + " RETURN c";
        try {
            ArangoCursor<BaseDocument> cursor = arangoDB.db(DB_NAME).query(getAllQuery, BaseDocument.class);
            cursor.forEachRemaining(baseDocument -> cache.put(baseDocument.getAttribute("firstName").toString(), ContactRepo.convertDocumentToContact(baseDocument)));
        } catch (ArangoDBException exception) {
            System.err.println("Failed to execute query " + exception.getMessage());
        }
    }

    /**
     * A compute tasks that prints out a node ID and some details about its OS and JRE.
     * Plus, the code shows how to access data stored in a cache from the compute task.
     */
    private static class RemoteTask implements IgniteRunnable {
        @IgniteInstanceResource
        Ignite ignite;

        @Override
        public void run() {
            System.out.println(">> Executing the compute task");

            System.out.println(
                    "   Node ID: " + ignite.cluster().localNode().id() + "\n" +
                            "   OS: " + System.getProperty("os.name") +
                            "   JRE: " + System.getProperty("java.runtime.name"));

            IgniteCache<Integer, String> cache = ignite.cache("contactCache");

            System.out.println(">> " + cache.get(1));
        }
    }
}
