package org.management.account.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class UserTest {
    @Test
    public void returnEmptySetWhenPermissionSetIsEmpty() {
        User user = new User();
        user.setPermissions(emptySet());

        assertTrue(user.collectAllPermissions().isEmpty());
    }

    @Test
    public void returnEmptySetWhenPermissionSetContainsEmptyString() {
        User user = new User();
        user.setPermissions(new HashSet<>(Arrays.asList("", " ", null)));
        assertTrue(user.collectAllPermissions().isEmpty());
    }

    @Test
    public void returnPermissionSetOfHimselfWhenNoChildren() {
        User manager = new User();
        Set<String> permissions = new HashSet<>(Arrays.asList("A", "B"));
        manager.setPermissions(permissions);

        assertTrue(manager.collectAllPermissions().containsAll(permissions));
    }

    @Test
    public void returnPermissionSetIncludingPermissionOfChildren() {
        User manager = new User();
        Set<String> managerPermissions = new HashSet<>(Arrays.asList("A", "B"));
        manager.setPermissions(managerPermissions);

        User user = new User();
        Set<String> userPermissions = new HashSet<>(Arrays.asList("A", "C", "D"));
        user.setPermissions(userPermissions);

        manager.getChildren().add(user);

        Set<String> allPermissions = new HashSet<>();
        allPermissions.addAll(managerPermissions);
        allPermissions.addAll(userPermissions);

        assertTrue(manager.collectAllPermissions().containsAll(allPermissions));
        assertTrue(user.collectAllPermissions().containsAll(userPermissions));
    }

    @Test
    public void returnAllNestedPermission() {
        User manager = new User();
        Set<String> managerPermissions = new HashSet<>(Arrays.asList("A", "B"));
        manager.setPermissions(managerPermissions);

        User user = new User();
        Set<String> userPermissions = new HashSet<>(Arrays.asList("A", "C", "D"));
        user.setPermissions(userPermissions);

        manager.getChildren().add(user);

        User user1 = new User();
        Set<String> user1Permissions = new HashSet<>(Arrays.asList("A", "E", "F"));
        user1.setPermissions(user1Permissions);

        user.getChildren().add(user1);

        Set<String> allPermissions = new HashSet<>();
        allPermissions.addAll(managerPermissions);
        allPermissions.addAll(userPermissions);
        allPermissions.addAll(user1Permissions);

        assertTrue(manager.collectAllPermissions().containsAll(allPermissions));
        assertTrue(user.collectAllPermissions().containsAll(userPermissions));
        assertTrue(user1.collectAllPermissions().containsAll(user1Permissions));
    }
}
