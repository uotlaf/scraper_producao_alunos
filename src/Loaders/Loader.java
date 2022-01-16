package Loaders;

import java.io.Serializable;

public class Loader implements Serializable {
    private String test;

    public String getTest() {
        return test;
    }

    public Loader(String test) {
        this.test = test;


    }
}
