package com.billennium.vaccinationproject.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.billennium.vaccinationproject.security.AppUserPermission.*;


public enum AppUserRole {
    ADMIN(Sets.newHashSet(VACCINE_READ,VACCINE_WRITE,FACILITY_READ,FACILITY_WRITE,USER_READ,USER_WRITE,VISIT_READ,VISIT_WRITE)),
    USER(Sets.newHashSet(USER_READ,USER_WRITE,VISIT_READ)),
    MEDICAL_STAFF(Sets.newHashSet(VACCINE_READ,VACCINE_WRITE,FACILITY_READ,FACILITY_WRITE,USER_READ,VISIT_READ,VISIT_WRITE));

    private final Set<AppUserPermission> permissions;

    AppUserRole(Set<AppUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
