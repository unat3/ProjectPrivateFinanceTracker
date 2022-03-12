package com.privatefinancetracker.privatefinancetracker.service;

import com.privatefinancetracker.privatefinancetracker.model.DailyLimit;
import com.privatefinancetracker.privatefinancetracker.model.Goal;
import com.privatefinancetracker.privatefinancetracker.model.Transactions;
import com.privatefinancetracker.privatefinancetracker.model.User;
import com.privatefinancetracker.privatefinancetracker.repository.DBManager;
import com.privatefinancetracker.privatefinancetracker.repository.DataManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.privatefinancetracker.privatefinancetracker.repository.DataManager.*;

public class UserService {
    private static Connection connection = DBManager.getConnection();

    public void changePassword(int userId, String password) throws Exception{
        connection = DBManager.getConnection();

        String query = "UPDATE user SET password = ? WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, password);
        preparedStatement.setInt(2, userId);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    public void changeEmail(int userId, String email) throws Exception{
        connection = DBManager.getConnection();

        String query = "UPDATE user SET email = ? WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setInt(2, userId);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    public int verifyUserDetails(String email, String password) throws Exception {
        connection = DBManager.getConnection();
        String query = "SELECT * FROM user WHERE email = ? && password = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        statement.setString(2, password);

        Integer userId = null;
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) userId = resultSet.getInt("userId");
        DBManager.close(resultSet, statement, connection);

        if (userId == null || userId == 0) throw new Exception("Username or password not correct, please try again");
        return userId;
    }

    public User getUserProfile(int userId) throws Exception {
        connection = DBManager.getConnection();
        String query = "SELECT userId, name, lastName, dateOfBirth, email, phone, location, createdAt, updatedAt"
                + " FROM user WHERE userId = ? LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet result = statement.executeQuery();
        User user = null;
        if (result.next()) {
            user = new User(
                    result.getInt("userId"), result.getString("name"), result.getString("lastName"),
                    result.getString("dateOfBirth"), result.getString("email"), result.getString("phone"), result.getString("location"),
                    result.getTimestamp("createdAt"), result.getTimestamp("updatedAt"));
        }
        DBManager.close(result, statement, connection);
        if (user == null) throw new Exception("Username or password not correct, please try again");
        return user;
    }

    public Goal getUserGoal(int userId) throws Exception {
        connection = DBManager.getConnection();
        String query = "SELECT goalId, goalName, userId, description, amountToSave, startDate, endDate, amountSaved"
                + " FROM financialGoalsCalculator WHERE userId = ? LIMIT 1";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet result = statement.executeQuery();
        Goal goal = null;
        if (result.next()) {
            goal = new Goal(
                    result.getInt("goalId"), result.getInt("userId"), result.getString("goalName"),
                    result.getString("description"), result.getString("amountToSave"), result.getDate("startDate").toLocalDate(),
                    result.getDate("endDate").toLocalDate(), result.getString("amountSaved"));
        }
        DBManager.close(result, statement, connection);
        if (goal == null) throw new Exception("Goal not found, please try again");
        return goal;
    }


    public void registerNewUser(User user) throws SQLException {
        connection = DBManager.getConnection();
        String query = "INSERT INTO user (name, lastName, dateOfBirth, email, phone, location, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getDateOfBirth());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getPhone());
        preparedStatement.setString(6, user.getLocation());
        preparedStatement.setString(7, user.getPassword());

        preparedStatement.execute();
        preparedStatement.close();

    }

    public String findNameById(int userId) throws Exception {
        connection = DBManager.getConnection();
        String query = "SELECT name FROM user WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);

        String name = null;
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) name = resultSet.getString("name");
        DBManager.close(resultSet, preparedStatement, connection);
        if (name == null) throw new Exception("No users found with this id");
        return name;
    }

    public void saveNewGoal(Goal goal) throws SQLException{
        connection = DBManager.getConnection();

        String query = "INSERT INTO financialGoalsCalculator (goalName, userId, description, amountToSave, startDate, endDate, amountSaved) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, goal.getName());
        getInstance();
        preparedStatement.setInt(2, goal.getUserId(getLoggedInUserId()));
        preparedStatement.setString(3, goal.getDescription());
        preparedStatement.setString(4, goal.getAmountToSave());
        preparedStatement.setDate(5, goal.getStartDate());
        preparedStatement.setDate(6, goal.getEndDate());
        preparedStatement.setString(7, goal.getAmountSaved());

        preparedStatement.execute();
        preparedStatement.close();
    }

    public void deleteGoal(Goal goal) throws SQLException{
        connection = DBManager.getConnection();

        String query = "DELETE FROM financialGoalsCalculator WHERE goalName = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, goal.getName());

        preparedStatement.execute();
        preparedStatement.close();
    }

    public void deleteUserProfile(User user) throws SQLException{
        connection = DBManager.getConnection();

        String query = "DELETE FROM user WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, user.getUserId(getLoggedInUserId()));

        preparedStatement.execute();
        preparedStatement.close();
    }

    public void saveDailyLimit(DailyLimit dailyLimit) throws SQLException{
        connection = DBManager.getConnection();

        String query = "INSERT INTO dailyLimit (userId, dailyLimitAmount) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        getInstance();
        preparedStatement.setInt(1, dailyLimit.getUserId(getLoggedInUserId()));
        preparedStatement.setDouble(2, Double.parseDouble(dailyLimit.getDailyLimit()));


        preparedStatement.execute();
        preparedStatement.close();
    }

    public void editDailyLimit(int userId, String newDailyLimit) throws Exception{
        connection = DBManager.getConnection();

        String query = "UPDATE dailyLimit SET dailyLimitAmount = ? WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, newDailyLimit);
        preparedStatement.setInt(2, userId);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    public double getDailyLimitById(int userId) throws Exception {
        connection = DBManager.getConnection();
        String query = "SELECT dailyLimitAmount FROM dailyLimit WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);

        double dailyLimitAmount = 0;
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) dailyLimitAmount = resultSet.getDouble("dailyLimitAmount");
        DBManager.close(resultSet, preparedStatement, connection);
       // if (dailyLimitAmount == 0) throw new Exception("No users found with this id");
        return dailyLimitAmount;
    }
    public void saveTransactions(Transactions transaction) throws SQLException{
        connection = DBManager.getConnection();

        String query = "INSERT INTO financialGoalsCalculator (userId,amount,dateAndTimeOfTransaction,description,category) VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        getInstance();
        preparedStatement.setInt(1, transaction.getUserId(getLoggedInUserId()));
        preparedStatement.setDouble(2, transaction.getSum());
        preparedStatement.setString(3, transaction.getDate());
        preparedStatement.setString(4, transaction.getName());
        preparedStatement.setString(5, transaction.getCategory());

        preparedStatement.execute();
        preparedStatement.close();
    }
}

