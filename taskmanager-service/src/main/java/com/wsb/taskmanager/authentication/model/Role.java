package com.wsb.taskmanager.authentication.model;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    public static final class Name {
        public static final String ROLE_USER = "USER";
        public static final String ROLE_ADMIN = "ADMIN";

        private Name() {
        }
    }
}
