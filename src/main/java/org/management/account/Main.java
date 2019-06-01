package org.management.account;

import org.management.account.constant.Operation;
import org.management.account.service.AccountManagementService;
import org.management.account.service.impl.AccountManagementServiceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import static org.management.account.constant.Const.CEO;
import static org.management.account.constant.Const.CEO_IDX;
import static org.management.account.constant.Const.JOIN_DELIMITER;
import static org.management.account.constant.Const.SPACE_DELIMITER;
import static org.management.account.utils.StringUtils.join;
import static org.management.account.utils.StringUtils.parse;

public class Main {
    private static final String OUTPUT_FILENAME = "output.txt";

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("The input file must be provided");
        }
        Scanner scanner = null;
        AccountManagementService service;
        try {
            scanner = new Scanner(new File(args[0]));
            // create the service based on the number of users
            int numberOfUsers = Integer.parseInt(scanner.nextLine());
            service = new AccountManagementServiceImpl(numberOfUsers);

            // read CEO's permission
            service.addUserWithPermissions(parse(scanner.nextLine(), SPACE_DELIMITER));

            // read Users' permission
            for (int i = 0; i < numberOfUsers; i++) {
                service.addUserWithPermissions(parse(scanner.nextLine(), SPACE_DELIMITER));
            }

            // read Users' manager
            for (int userIdx = 1; userIdx <= numberOfUsers; userIdx++) {
                int managerIdx = CEO_IDX;
                String manager = scanner.nextLine();
                if (!CEO.equalsIgnoreCase(manager)) {
                    managerIdx = Integer.parseInt(manager);
                }
                service.updateManagerForUser(managerIdx, userIdx);
            }

            // write output
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(Files.newBufferedWriter(Paths.get(OUTPUT_FILENAME), Charset.defaultCharset(),
                                                                 StandardOpenOption.TRUNCATE_EXISTING), true);
                service.getPermissionsOfAllUsers()
                        .stream()
                        .map(perSet -> join(perSet, JOIN_DELIMITER))
                        .forEach(writer::println);

                // read operations
                while (scanner.hasNext()) {
                    try {
                        String[] separatedOperation = parse(scanner.nextLine(), SPACE_DELIMITER);
                        Operation operation = Operation.valueOf(separatedOperation[0]);
                        int userIdx = CEO.equalsIgnoreCase(separatedOperation[1]) ? CEO_IDX : Integer.parseInt(
                                separatedOperation[1]);
                        switch (operation) {
                            case ADD:
                                service.addPermission(userIdx, separatedOperation[2]);
                                break;
                            case REMOVE:
                                service.removePermission(userIdx, separatedOperation[2]);
                                break;
                            case QUERY:
                                writer.println(join(service.getPermissions(userIdx), JOIN_DELIMITER));
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("The input format in the file is wrong!!!");
                        System.exit(1);
                    }
                }
            } catch (IOException e) {
                System.out.println("Cannot open the input file");
                System.exit(1);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The input file not found");
            System.exit(1);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        System.out.println("The result is in the output.txt");
    }
}