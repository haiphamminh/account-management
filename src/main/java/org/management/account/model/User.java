package org.management.account.model;

import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Data
public class User {
    private Set<String> permissions = new HashSet<>();
    private Set<User> children = new HashSet<>();

    public Set<String> collectAllPermissions() {
        return flattened()
                .map(User::getPermissions)
                .flatMap(Collection::stream)
                .filter(permission -> permission != null && !permission.trim().isEmpty())
                .collect(toSet());
    }

    private Stream<User> flattened() {
        return Stream.concat(
                Stream.of(this),
                children.stream()
                        .flatMap(User::flattened));
    }
}
