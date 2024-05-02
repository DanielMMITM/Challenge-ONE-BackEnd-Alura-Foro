package com.alura.forum.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    public static final String USER_ID_NOT_FOUND = "user id not found";
    public static final String RESPONSE_ID_NOT_FOUND = "response id not found";
    public static final String COURSE_ID_NOT_FOUND = "course id not found";
    public static final String POST_DELETED_SUCCESSFULLY = "Post deleted successfully!";
    public static final String POST_ID_NOT_FOUND = "post id not found";
    public static final String RESPONSE_DELETED_SUCCESSFULLY = "Response deleted successfully!";
    public static final String POST_PATH = "/posts/{id}";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully!";
    public static final String BEARER = "Bearer ";
    public static final int BEGIN_INDEX = 7;
    public static final String USER_NOT_FOUND = "User not found";
    public static final String OFFSET_ID = "-05:00";
    public static final int HOURS = 6;
    public static final String [] PUBLIC_URLS = {
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/auth/**"
    };
    public static final String MALFORMED_JSON_BODY = "Malformed JSON body";
    public static final String DELETE_USER_PATH = "/users/{id}";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String DATABASE_ERROR = "An error occurred in the Database";
    public static final String EXPIRED_JWT = "JWT Expired";
    public static final String MALFORMED_JWT = "Malformed JWT";
    public static final String SIGNATURE_NOT_VALID = "JWT Signature not valid";
    public static final String EMPTY = "";
    public static final String INVALID_CREDENTIALS = "Invalid credentials. Try again";
    public static final String INTEGRITY_DATA_VIOLATION = "Invalid credentials. Try again";

}
