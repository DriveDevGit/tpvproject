package com.proyectotpvservidor.Model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class PerfilDao {

	
	public boolean UsuarioExiste(String user, String password) {
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
	        	return true;
	        }
	        else {
	        	return false;
	        }

	    }
	    catch(NoResultException e) {
	        return false;
	    }
	}
}
