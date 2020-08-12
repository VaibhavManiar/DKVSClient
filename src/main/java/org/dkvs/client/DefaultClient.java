package org.dkvs.client;

import org.dkvs.client.data.Pair;
import org.dkvs.client.data.Request;
import org.dkvs.client.data.Response;
import org.dkvs.client.handler.AsyncResponseHandler;
import org.dkvs.client.tcp.TcpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultClient implements Client {

    private final String ip;
    private final int port;
    private final ExecutorService es;
    private final Logger logger = LoggerFactory.getLogger(DefaultClient.class);

    public DefaultClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.es = Executors.newFixedThreadPool(10);
    }

    public Response<Pair> send(Request<Pair> request) {
        final Response<Pair>[] response = new Response[1];
        getTcpClient().send(request, s -> response[0] = s);
        return response[0];
    }

    private TcpClient<Request<Pair>, Response<Pair>> getTcpClient() {
        return TcpClientFactory.create(ip, port);
    }
    
    @Override
    public String get(String key) {
        String[] value = new String[1];
        getTcpClient().send(new Request<>(Request.Type.GET, new Pair(key, "")), new AsyncResponseHandler<Response<Pair>>(es) {
            @Override
            public void handle(Response<Pair> pairResponse) {
                System.out.println(pairResponse.getData());
                value[0] = pairResponse.getData().getValue();
            }
        });
        return value[0];
    }

    @Override
    public void put(Pair pair) {
        getTcpClient().send(new Request<>(Request.Type.GET, pair), new AsyncResponseHandler<Response<Pair>>(es) {
            @Override
            public void handle(Response<Pair> pairResponse) {
                System.out.println(pairResponse.getData());
            }
        });
    }

    public static void main(String[] args) {
        Client client = new DefaultClient("localhost", 8650);
        client.put(new Pair("ABC", "DEF"));
        client.put(new Pair("ABC1", "DEF1"));
        client.put(new Pair("ABC2", "DEF2"));
        System.out.println(client.get("ABC"));
    }
}
