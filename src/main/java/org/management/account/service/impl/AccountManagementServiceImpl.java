package org.management.account.service.impl;

import lombok.Getter;
import org.management.account.model.User;
import org.management.account.service.AccountManagementService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class AccountManagementServiceImpl implements AccountManagementService {
    @Getter
    private List<User> users;

    public AccountManagementServiceImpl(int numberOfUsers) {
        this.users = new ArrayList<>(numberOfUsers);
    }

    @Override
    public User addUserWithPermissions(String... permissions) {
        User user = new User();
        user.setPermissions(new HashSet<>(Arrays.asList(permissions)));
        users.add(user);
        return user;
    }

    @Override
    public User updateManagerForUser(int managerIdx, int userIdx) {
        User manager = getUser(managerIdx);
        User user = getUser(userIdx);
        manager.getChildren().add(user);
        return manager;
    }

    @Override
    public void addPermission(int userIdx, String... permissions) {
        getUser(userIdx).getPermissions().addAll(Arrays.asList(permissions));
    }

    @Override
    public void removePermission(int userIdx, String... permissions) {
        getUser(userIdx).getPermissions().removeAll(Arrays.asList(permissions));
    }

    @Override
    public Set<String> getPermissions(int userIdx) {
        return getUser(userIdx).collectAllPermissions();
    }

    @Override
    public List<Set<String>> getPermissionsOfAllUsers() {
        return IntStream.range(0, users.size())
                .mapToObj(this::getPermissions)
                .collect(toList());
    }

    private User getUser(int userIdx) {
        if (userIdx < 0 || userIdx >= users.size()) {
            throw new IllegalArgumentException("The user not found");
        }
        return users.get(userIdx);
    }
}
