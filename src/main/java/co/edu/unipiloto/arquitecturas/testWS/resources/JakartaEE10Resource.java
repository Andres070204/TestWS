package co.edu.unipiloto.arquitecturas.testWS.resources;

import co.edu.unipiloto.arquitecturas.testWS.entity.Persona;
import co.edu.unipiloto.arquitecturas.testWS.entity.Salario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author 
 */
@Path("rest")
public class JakartaEE10Resource {
      @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
    
    private static Map<Integer, Persona> persons = new HashMap<>();
       
    static {
        for (int i = 0; i < 10; i++) {
            Persona persona = new Persona();
            int id = i+1;
            persona.setId(id);
            persona.setFullName("Mi Persona " + id);
            persona.setAge(new Random().nextInt(20+id));
            persona.setSalario(persona.getAge()*Persona.minSalario/3);
            persons.put(id, persona);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAllPersonsInJSON")
    public List<Persona> getAllPersonsInJSON() {
        return new ArrayList<Persona>(persons.values());
    }   
    
    @POST
    @Path("addPerson/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Persona> addPerson(List<Persona> pr) {
        for(Persona persona : pr) {
            persona.setSalario(persona.getAge()*Persona.minSalario/3);
            persons.put(persona.getId(), persona);
        }
        return new ArrayList<Persona>(persons.values());
    }
    @GET
    @Path("promedio")
    @Produces(MediaType.APPLICATION_XML)
    public Salario promedio() {
        double totalSalario = persons.values().stream()
                .mapToDouble(Persona::getSalario)
                .sum();
        double promedioSalario = totalSalario / persons.size();
        Salario salarioPromedio = new Salario();
        salarioPromedio.setSalario(promedioSalario);
        return salarioPromedio;
    }
    @GET
    @Path("añadirSalario")
    @Produces(MediaType.APPLICATION_JSON)
     public Response añadirSalario() {
        double totalSalario = 0.0;
        for (Persona persona : persons.values()) {
            totalSalario += persona.getSalario(); 
        }
        return  Response.ok(totalSalario).build();
    }
}

