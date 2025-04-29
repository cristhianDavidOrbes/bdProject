package com.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Actor;
import com.example.utils.DatabaseConnection;

public class ActorRepository implements Repository<Actor> {

    private Connection conn;

    public ActorRepository() {
        try {
            this.conn = DatabaseConnection.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo conectar a la base de datos.");
        }
    }

    @Override
    public List<Actor> findAll() {
        List<Actor> actors = new ArrayList<>();
        String sql = "SELECT * FROM sakila.actor";

        try (
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                Integer id = rs.getInt("actor_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                Actor actor = new Actor(id, firstName, lastName);
                actors.add(actor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actors;
    }

    @Override
    public Actor getByID(Integer id) {
        Actor actor = null;
        String sql = "SELECT * FROM sakila.actor WHERE actor_id = ?";

        try (
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");

                    actor = new Actor(id, firstName, lastName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actor;
    }

    @Override
    public void save(Actor t) {
        int id = t.getActorID();
        String sql = "SELECT * FROM sakila.actor WHERE actor_id = ?";
    
        try (
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
 
                String updateSql = "UPDATE sakila.actor SET first_name = ?, last_name = ? WHERE actor_id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setString(1, t.getFirstName());
                    updateStmt.setString(2, t.getLastName());
                    updateStmt.setInt(3, id);
                    updateStmt.executeUpdate();
                    System.out.println("Actor actualizado.");
                }

            } else {
                String insertSql = "INSERT INTO sakila.actor (actor_id, first_name, last_name) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, id);
                    insertStmt.setString(2, t.getFirstName());
                    insertStmt.setString(3, t.getLastName());
                    insertStmt.executeUpdate();
                    System.out.println("Actor insertado.");
                }
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM sakila.actor WHERE actor_id = ?";
    
        try (
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Actor eliminado correctamente.");
            } else {
                System.out.println("No se encontró un actor con ese ID.");
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
