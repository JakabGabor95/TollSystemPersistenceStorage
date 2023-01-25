package hu.ltk.jakabgabor;
import hu.ltk.jakabgabor.entity.MotorwayVignette;
import hu.ltk.jakabgabor.storage.MotorwayVignetteStorageInterface;
import hu.ltk.jakabgabor.validator.TollSystemValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TollSystemPersistenceStorage implements MotorwayVignetteStorageInterface {
    private TollSystemValidator tollSystemValidator = new TollSystemValidator();
    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/sqlite/db/motorwayVignette.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public TollSystemPersistenceStorage() {}

    private List<MotorwayVignette> motorwayVignetteList = new ArrayList<>(List.of(new MotorwayVignette("asd-123",
            "d1", "yearly", 1000,
            new Date(2023 - 1900, 0, 1),new Date(2023 - 1900, 12, 1),
            new Date(2021 - 1900, 0, 1)), new MotorwayVignette("asd-123",
            "B2", " monthly", 1000,
            new Date(2021 - 1900, 0, 1), new Date(2021 - 1900, 0, 1),
            new Date(2021 - 1900, 0, 1))));

    @Override
    public List<MotorwayVignette> findByRegistrationNumber(String registrationNumber) {
        connect();
        tollSystemValidator.checkRegistrationNumberIsNull(registrationNumber);
        return motorwayVignetteList.stream()
                .filter(motorwayVignette -> motorwayVignette.getRegistrationNumber()
                        .equals(registrationNumber))
                .collect(Collectors.toList());
    }
}
