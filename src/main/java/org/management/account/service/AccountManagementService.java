package org.management.account.service;

import org.management.account.model.User;

import java.util.List;
import java.util.Set;

public interface AccountManagementService {
    User addUserWithPermissions(String... permissions);

    User updateManagerForUser(int managerIdx, int userIdx);

    Set<String> getPermissions(int userIdx);

    List<Set<String>> getPermissionsOfAllUsers();

    void addPermission(int userIdx, String... permissions);

    void removePermission(int userIdx, String... permissions);
}
