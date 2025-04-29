package com.example;

import java.util.List;

import com.example.model.Actor;
import com.example.repository.ActorRepository;

public class Main {
    public static void main(String[] args) {

        ActorRepository repo = new ActorRepository();


        System.out.println("todos los actores:");
        List<Actor> actores = repo.findAll();
        for (Actor actor : actores) {
            System.out.println(actor);
        }

        System.out.println("actor con ID 1:");
        Actor actor = repo.getByID(1);
        if (actor != null) {
            System.out.println(actor);
        } else {
            System.out.println("No se encontró el actor con ID 1.");
        }


        System.out.println("Guardar nuevo actor o actualizarlo ");
        Actor nuevoActor = new Actor(201, "NuevoNombre", "NuevoApellido");
        repo.save(nuevoActor);

        System.out.println("eliminando actor con ID 201:");
        repo.delete(201);
    }
}
