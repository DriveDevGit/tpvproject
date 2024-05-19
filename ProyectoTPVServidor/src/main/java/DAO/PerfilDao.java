package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import Model.Perfil;

public class PerfilDao {

	
	public String InsertarPerfil(Perfil perfil) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    try {
	        em.getTransaction().begin();
	        
	        // Insertar el Perfil en la base de datos
	        em.persist(perfil);
	        
	        em.getTransaction().commit();
	        em.close();
	        emf.close();
	        
	        return "PERFIL INSERTADO CORRECTAMENTE";
	    }
	    catch(Exception e) {
	        return "ERROR AL INSERTAR PERFIL";
	    }
	}
	
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
	        	return "LOGIN CORRECTO:"+perfil.getRol();
	        }
	        else {
	        	return "LOGIN INCORRECTO";
	        }

	    }
	    catch(NoResultException e) {
	        return "LOGIN INCORRECTO";
	    }
	}
	
	public Perfil obtenerPerfil(String username) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Perfil perfil = em.createQuery("SELECT p from Perfil p WHERE p.username =:username", Perfil.class)
            .setParameter("username", username).getSingleResult();
            em.getTransaction().commit();

            return perfil;
        } catch(NoResultException e) {
            return null;
        } finally {
            em.close();
            emf.close();
        }
    }
	
	public List<Perfil> obtenerTodosLosPerfiles() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
        EntityManager em = emf.createEntityManager();
        List<Perfil> perfiles = null;
        try {
            em.getTransaction().begin();
            perfiles = em.createQuery("SELECT p from Perfil p", Perfil.class).getResultList();
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
        return perfiles;
    }
	
}
