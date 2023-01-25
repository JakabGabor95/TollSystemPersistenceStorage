package hu.ltk.jakabgabor;
import hu.ltk.jakabgabor.entity.MotorwayVignette;
import hu.ltk.jakabgabor.storage.MotorwayVignetteStorageInterface;
import hu.ltk.jakabgabor.validator.TollSystemValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TollSystemPersistenceStorage implements MotorwayVignetteStorageInterface {
    private TollSystemValidator tollSystemValidator = new TollSystemValidator();
    Connection connection;
    public static final String DB_URL = "jdbc:mysql://localhost:3306/bootcamp";
    public static final String USER = "root";
    public static final String PASSWORD = "bootcamp";

    public TollSystemPersistenceStorage() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<MotorwayVignette> findByRegistrationNumber(String registrationNumber) {
        tollSystemValidator.checkRegistrationNumberIsNull(registrationNumber);
        return findMotorwayVignettesByRegistrationNumber(registrationNumber);
    }

   public List<MotorwayVignette> findMotorwayVignettesByRegistrationNumber(String registrationNumber) {
        List<MotorwayVignette> motorwayVignettes = new ArrayList<>();
        String sql = "SELECT * FROM motorway_vignette WHERE registrationNumber = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, registrationNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                motorwayVignettes.add(new MotorwayVignette(
                        resultSet.getString("registrationNumber"),
                        resultSet.getString("vehicleCategory"),
                        resultSet.getString("motorwayVignetteType"),
                        resultSet.getInt("price"),
                        setNewDate(resultSet.getDate("validFrom")),
                        setNewDate(resultSet.getDate("validTo")),
                        setNewDate(resultSet.getDate("dateOfPurchase"))
                        ));
            }
            return  motorwayVignettes;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private Date setNewDate(Date date) {
        int year = date.getYear();
        int moth = date.getMonth();
        int day = date.getDay();

        return new Date(year,moth,day);
    }
}
