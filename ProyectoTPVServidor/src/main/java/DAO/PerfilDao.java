package DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import com.proyectotpvservidor.Model.Perfil;

public class PerfilDao {

	
	public String UsuarioExiste(String user, String password) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    try {
	        em.getTransaction().begin();
	        Perfil perfil = em.createQuery("SELECT p from Perfil p WHERE p.username =:username", Perfil.class)
	        .setParameter("username", user).getSingleResult();
	        em.getTransaction().commit();
	        em.close();
	        emf.close();

	        if(password.equals(perfil.getPassword())) {
	        	return "LOGIN CORRECTO";
	        }
	        else {
	        	return "LOGIN INCORRECTO";
	        }

	    }
	    catch(NoResultException e) {
	        return "LOGIN INCORRECTO";
	    }
	}
	
	public String agregarUsuario(String nombre, String usuario, String contrasena, int rol) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    try {
	        em.getTransaction().begin();
	        
	        // Crear un nuevo objeto Perfil
	        Perfil perfil = new Perfil();
	        perfil.setNombre(nombre);
	        perfil.setUsername(usuario);
	        perfil.setPassword(contrasena);
	        perfil.setRol(rol);
	        
	        // Persistir el objeto Perfil en la base de datos
	        em.persist(perfil);
	        
	        em.getTransaction().commit();
	        em.close();
	        emf.close();
	        
	        return "USUARIO CREADO";
	    }
	    catch(Exception e) {
	        return "ERROR DE CREACIÓN";
	    }
	}
}
