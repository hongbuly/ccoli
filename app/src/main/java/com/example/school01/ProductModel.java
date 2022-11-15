package com.example.school01;

import java.util.HashMap;
import java.util.Map;

public class ProductModel {
    public Map<String, DetailModel> products = new HashMap<>();

    public static class DetailModel {
        public String img01, img02;
        public String name01, name02;
        public String cost01, cost02;
    }
}
