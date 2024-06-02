package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import Model.Perfil;
import Model.Producto;
import Model.ProductoSeleccionado;

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
	
	public List<Producto> ObtenerTodosLosProductos() {
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
	
	public Integer ObtenerIdProductoPorNombre(String nombreProducto) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    Integer idProducto = null;
	    try {
	        em.getTransaction().begin();
	        Query query = em.createQuery("SELECT p.id FROM Producto p WHERE p.nombre = :nombre", Integer.class);
	        query.setParameter("nombre", nombreProducto);
	        idProducto = (Integer) query.getSingleResult();
	        em.getTransaction().commit();
	    } catch(NoResultException e) {
	        // Si no se encuentra un producto con el nombre especificado, devuelve null
	        idProducto = null;
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        em.close();
	        emf.close();
	    }
	    return idProducto;
	}
	
	public List<Producto> ObtenerProductosPorCategoria(String categoria) {
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
	
	public Double ObtenerPrecioProductoPorNombre(String nombre) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    Double precio = null;
	    try {
	        em.getTransaction().begin();
	        precio = em.createQuery("SELECT p.precio FROM Producto p WHERE p.nombre = :nombre", Double.class)
	               .setParameter("nombre", nombre)
	               .getSingleResult();
	        em.getTransaction().commit();
	    } catch(NoResultException e) {
	        // Si no se encuentra el producto, devuelve null
	        precio = null;
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        em.close();
	        emf.close();
	    }
	    return precio;
	}
	
	
	public String InsertarProductoSeleccionado(ProductoSeleccionado productoSeleccionado) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    try {
	        em.getTransaction().begin();

	        // Insertar el ProductoSeleccionado en la base de datos
	        em.persist(productoSeleccionado);

	        em.getTransaction().commit();
	        em.close();
	        emf.close();

	        return "PRODUCTO SELECCIONADO INSERTADO CORRECTAMENTE";
	    } catch(Exception e) {
	    	e.printStackTrace();
	        return "ERROR AL INSERTAR PRODUCTO SELECCIONADO";
	    }
	}
	
	public String EliminarProductoSeleccionadosPorPedidoId(int pedidoId) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    try {
	        em.getTransaction().begin();

	        // Crear una consulta para eliminar los ProductoSeleccionado del pedido
	        Query query = em.createQuery("DELETE FROM ProductoSeleccionado ps WHERE ps.pedido_id = :pedidoId");
	        query.setParameter("pedidoId", pedidoId);
	        query.executeUpdate();

	        em.getTransaction().commit();
	        em.close();
	        emf.close();

	        return "PRODUCTOS SELECCIONADOS ELIMINADOS CORRECTAMENTE";
	    } catch(Exception e) {
	        e.printStackTrace();
	        return "ERROR AL ELIMINAR PRODUCTOS SELECCIONADOS";
	    }
	}
}
