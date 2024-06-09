package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import Model.Categoria;

public class CategoriaDao {
	
	public List<Categoria> ObtenerTodosLasCategorias() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
        EntityManager em = emf.createEntityManager();
        List<Categoria> categorias = null;
        try {
            em.getTransaction().begin();
            categorias = em.createQuery("SELECT c from Categoria c", Categoria.class).getResultList();
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
        return categorias;
    }

}
