package com.example.school01;

import java.util.HashMap;
import java.util.Map;

public class UserModel {
    public Map<String, UserDataModel> user = new HashMap<>();

    public static class UserDataModel {
        public String email;
        public String name;
        public String number;
        public String address;
    }
}
