package com.example;

import java.util.List;

import com.example.model.Actor;
import com.example.repository.ActorRepository;

public class Main {
    public static void main(String[] args) {

        ActorRepository actorRepository = new ActorRepository();

        System.out.println("Actores");
        List<Actor> actores = actorRepository.findAll();
        for (Actor actor : actores) {
            System.out.println(actor);
        }


        int buscarID = 45; 
        System.out.println("\nActor con ID" + buscarID );
        Actor actorEncontrado = actorRepository.getByID(buscarID);
        
        if (actorEncontrado != null) {
            System.out.println(actorEncontrado);
        } else {
            System.out.println("actor no encontrado.");
        }
    }
}
