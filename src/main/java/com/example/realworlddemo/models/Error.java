package com.example.realworlddemo.models;

import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.Arrays;

public class Error {
    Errors errors;
    public Errors getErrors() {
        return errors;
    }

    private Error(Errors errors) {
        this.errors = errors;
    }

    static class Errors {
        ArrayList<String> body;
        public ArrayList<String> getBody() {
            return body;
        }

        private Errors(ArrayList<String> body) {
            this.body = body;
        }
    }

    public static Error from(String... errorMessages) {
        return new Error(new Errors(
                new ArrayList<>(Arrays.asList(errorMessages))
        ));
    }
}
