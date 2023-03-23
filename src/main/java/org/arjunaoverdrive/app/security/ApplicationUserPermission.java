package org.arjunaoverdrive.app.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public enum ApplicationUserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    SET_READ("set:read"),
    SET_WRITE("set:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write"),
    TEACHER_READ("teacher:read"),
    TEACHER_WRITE("teacher:write");

    String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public final String getPermission(){
        return permission;
    }
}
