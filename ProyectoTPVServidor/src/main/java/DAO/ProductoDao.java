package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import Model.Perfil;
import Model.Producto;

public class ProductoDao {
	
	public String InsertarProducto(Producto producto) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    try {
	        em.getTransaction().begin();
	        
	        // Insertar el Producto en la base de datos
	        em.persist(producto);
	        
	        em.getTransaction().commit();
	        em.close();
	        emf.close();
	        
	        return "PRODUCTO INSERTADO CORRECTAMENTE";
	    }
	    catch(Exception e) {
	        return "ERROR AL INSERTAR PRODUCTO";
	    }
	}
	
	public List<Producto> obtenerTodosLosProductos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
        EntityManager em = emf.createEntityManager();
        List<Producto> productos = null;
        try {
            em.getTransaction().begin();
            productos = em.createQuery("SELECT p from Producto p", Producto.class).getResultList();
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
        return productos;
    }
	
	public List<Producto> obtenerProductosPorCategoria(String categoria) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    List<Producto> productos = null;
	    try {
	        em.getTransaction().begin();
	        productos = em.createQuery("SELECT p from Producto p WHERE p.categoria = :categoria", Producto.class)
	                .setParameter("categoria", categoria)
	                .getResultList();
	        em.getTransaction().commit();
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        em.close();
	        emf.close();
	    }
	    return productos;
	}
}
