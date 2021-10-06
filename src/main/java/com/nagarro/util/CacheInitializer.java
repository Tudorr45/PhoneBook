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
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import javax.cache.Cache;
import javax.enterprise.event.Observes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        System.out.println(">> Created the cache and added the values.");

        System.out.println(">> Executing the compute task");

        System.out.println(
                "   Node ID: " + ignite.cluster().localNode().id() + "\n" +
                        "   OS: " + System.getProperty("os.name") +
                        "   JRE: " + System.getProperty("java.runtime.name"));

        System.out.println(">> Size: " + cache.size()); // size e 3, dar da 4
        //System.out.println(cache.get("Denissss")); // aici da ClassNotFoundException
        if (cache.containsKey("Denissss")) {
            System.out.println("Contine cheia Denissss");
        }
        if (!cache.containsKey("Bicicleta")) {
            System.out.println("Nu contine cheia Bicicleta");
        }
//        for (Cache.Entry<String, Contact> entry : cache) { // aici da ClassNotFoundException
//            System.out.println("\n\n\n" + entry + "\n\n\n");
//        }
        // Get an instance of binary-enabled cache.
        IgniteCache<String, BinaryObject> binaryCache = ignite.cache("contactCache").withKeepBinary();
        for (Cache.Entry<String, BinaryObject> entry : binaryCache) { // aßici da ClassNotFoundException
            System.out.println("\n+++" + entry.getValue().field("lastName") + "++++\n");
            LocalDate data = LocalDate.parse(entry.getValue().field("creationDate").toString());
            System.out.println("Data este: " + data);
            //TODO: Reconstruct Contact from Binary Object.
        }
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
        List<Contact> contacts = new ArrayList<>();
        try {
            ArangoCursor<BaseDocument> cursor = arangoDB.db(DB_NAME).query(getAllQuery, BaseDocument.class);
            cursor.forEachRemaining(baseDocument -> contacts.add(ContactRepo.convertDocumentToContact(baseDocument)));
            contacts.forEach(contact -> {
                System.out.println(contact); // it works fine
                cache.put(contact.getFirstName(), contact);
            });
        } catch (ArangoDBException exception) {
            System.err.println("Failed to execute query " + exception.getMessage());
        }
    }
}
