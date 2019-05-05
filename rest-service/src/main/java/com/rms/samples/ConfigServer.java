package com.rms.samples;

import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;
import com.sun.net.httpserver.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConfigServer extends RestServer {

    private static final Map<String, String> configValues = new HashMap<>();

    private boolean debug;

    public ConfigServer() {
        this.SetHandler("/api/config/get_value", (e -> this.GetConfigValue(e)));
        this.SetHandler("/api/config/set_value", (e -> this.SetConfigValue(e)));
        this.debug = true;
    }

    protected void GetConfigValue(HttpExchange exchange) throws IOException {
        String name = "";
        String value = "";

        try {
            // Parse the request object.
            JSONObject jo = (JSONObject) new JSONParser().parse(new InputStreamReader(exchange.getRequestBody()));

            // try and retrieve it's value. Make sure we avoid threading issues by synchronizing.
            name = (String) jo.get("name");

            synchronized (ConfigServer.configValues) {
                value = configValues.getOrDefault(name, "");
            }

            // Log the name of the requested config
            System.out.println("Requested: " + name);
        }
        catch(ParseException e)
        {}

        // Send the response
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("value", value);

        if(this.debug)
        {
            InetAddress ip = InetAddress.getLocalHost();
            String hostName = ip.getHostName();
            jo.put("host", hostName);
        }

        this.SendResponse(jo.toJSONString(), exchange);
    }

    protected void SetConfigValue(HttpExchange exchange) throws IOException {
        String respText = "Config not set";

        try {
            // Parse the request object.
            JSONObject jo = (JSONObject) new JSONParser().parse(new InputStreamReader(exchange.getRequestBody()));

            // try and retrieve it's value
            String name = (String) jo.get("name");
            String value = (String) jo.get("value");

            // Store the value. Make sure we lock to avoid threading issues.
            synchronized (ConfigServer.configValues) {
                ConfigServer.configValues.put(name, value);
            }

            // Log the name of the requested config
            respText = "Set: " + name;
            System.out.println(respText);
        }
        catch(ParseException e)
        {}

        this.SendResponse(respText, exchange);
    }
}
