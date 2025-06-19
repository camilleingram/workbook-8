package com.pluralsight;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private DataSource dataSource;

    public DataManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Actor> getActor(String name) {
        List<Actor> actors = new ArrayList<>();

        String query = "SELECT actor_id, first_name, last_name " +
                "FROM actor " +
                "WHERE first_name LIKE CONCAT('%', ?, '%') OR last_name LIKE CONCAT('%', ?, '%')";

        try(Connection connection = dataSource.getConnection();) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, name);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int actorId = resultSet.getInt("actor_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    Actor actor = new Actor(actorId, firstName, lastName);
                    actors.add(actor);
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return actors;
    }

    public List<Film> getFilm(int actorId) {
        List<Film> films = new ArrayList<>();

        String query = "SELECT film.film_id, title, description, release_year, length " +
                "FROM film " +
                "JOIN film_actor ON film_actor.film_id = film.film_id " +
                "WHERE actor_id = ? ";

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, actorId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int filmId = resultSet.getInt("film_id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    int releaseYear = resultSet.getInt("release_year");
                    int length = resultSet.getInt("length");

                    Film film = new Film(filmId, title, description, releaseYear, length);
                    films.add(film);
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return films;
    }

}
