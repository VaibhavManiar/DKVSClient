package org.dkvs.client;

import org.dkvs.client.data.Pair;

public interface Client {
    String get(String key);
    void put(Pair pair);
}
