package com.sahariardev;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.HashMap;
import java.util.Map;

@Controller()
public class HomeController {

    @Get("/")
    public HttpResponse<?> hello() {
        Map<String, Object> model = new HashMap<>();
        model.put("message", "Hello From app 02");
        return HttpResponse.ok(model);
    }

}