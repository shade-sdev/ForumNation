package com.shade.enterprise;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class ForumNationApp {

    public static void main(String... args) {
        Quarkus.run(args);
    }

}