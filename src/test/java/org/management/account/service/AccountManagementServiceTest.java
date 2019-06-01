package org.management.account.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.management.account.model.User;
import org.management.account.service.impl.AccountManagementServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@RunWith(JUnit4.class)
public class AccountManagementServiceTest {

    AccountManagementServiceImpl inst;

    private User addUser() {
        return inst.addUserWithPermissions("A", "B");
    }

    @Before
    public void before() {
        inst = new AccountManagementServiceImpl(6);
    }

    @Test
    public void userListIsCreatedSuccessfully() {
        Assert.assertTrue(inst.getUsers() != null);
        Assert.assertTrue(inst.getUsers() instanceof ArrayList);
    }

    @Test
    public void addedUserWithoutPermissions() {
        User user = inst.addUserWithPermissions();
        Assert.assertEquals(1, inst.getUsers().size());
        Assert.assertTrue(user.collectAllPermissions().isEmpty());
    }

    @Test
    public void addedUserWithPermissions() {
        User user = addUser();
        Assert.assertEquals(1, inst.getUsers().size());
        Assert.assertTrue(user.collectAllPermissions().containsAll(Arrays.asList("A", "B")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateManagerForUserThrowExceptionIfManagerNotFound() {
        inst.updateManagerForUser(0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateManagerForUserThrowExceptionIfUserNotFound() {
        addUser();
        inst.updateManagerForUser(0, 1);
    }

    @Test
    public void updateManagerForUserSuccessfully() {
        addUser();
        User user = inst.addUserWithPermissions("A", "C");
        User updatedManager = inst.updateManagerForUser(0, 1);
        Assert.assertEquals(1, updatedManager.getChildren().size());
        Assert.assertTrue(updatedManager.getChildren().contains(user));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPermissionThrowExceptionIfUserNotFound() {
        inst.addPermission(0, "C");
    }

    @Test
    public void addPermissionSuccessfully() {
        addUser();
        inst.addPermission(0, "C");
        Assert.assertTrue(inst.getUsers().get(0).getPermissions().containsAll(Arrays.asList("A", "B", "C")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void removePermissionsThrowExceptionIfUserNotFound() {
        inst.removePermission(0, "C");
    }

    @Test
    public void removePermissionsSuccessfully() {
        addUser();
        inst.removePermission(0, "C");
        Assert.assertTrue(inst.getUsers().get(0).getPermissions().containsAll(Arrays.asList("A")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPermissionsThrowExceptionIfUserNotFound() {
        inst.getPermissions(0);
    }

    @Test
    public void getPermissionsSuccessfully() {
        addUser();
        Set<String> permissions = inst.getPermissions(0);
        Assert.assertTrue(permissions.containsAll(Arrays.asList("A", "B")));
    }

    @Test
    public void getPermissionsOfAllUsers() {
        IntStream.range(0, 3)
                .forEach(idx -> addUser());

        List<Set<String>> permissionsPerUser = inst.getPermissionsOfAllUsers();
        permissionsPerUser.forEach(
                permissionSet -> Assert.assertTrue(permissionSet.containsAll(Arrays.asList("A", "B"))));
    }
}
