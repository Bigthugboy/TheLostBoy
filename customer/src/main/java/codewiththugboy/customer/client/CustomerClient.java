package codewiththugboy.customer.client;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;


public class CustomerClient {
    public static CustomerClient createClient(String baseUrl) {
        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(CustomerClient.class, baseUrl);
    }

}
