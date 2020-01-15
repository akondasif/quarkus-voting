package com.redhat.demo.voting.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class JsonProvider {

    @Produces
    Jsonb json() {
        return JsonbBuilder.create();
    }
}
