package hello.entity;

import org.apache.jmeter.protocol.http.util.HTTPArgument;

import java.util.ArrayList;

public class JMeterEntity {


    private ArrayList<HTTPArgument> httpArguments;

    public ArrayList<HTTPArgument> getHttpArguments() {
        return httpArguments;
    }

    public void setHttpArguments(ArrayList<HTTPArgument> httpArguments) {
        this.httpArguments = httpArguments;
    }
}
