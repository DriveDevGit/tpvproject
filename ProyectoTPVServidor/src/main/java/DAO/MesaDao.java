package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


import Model.Mesa;
import Model.Pedido;
import Model.Producto;

public class MesaDao {
	
	public String InsertarMesa(Mesa mesa) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    try {
	        em.getTransaction().begin();
	        
	        // Insertar el Producto en la base de datos
	        em.persist(mesa);
	        
	        em.getTransaction().commit();
	        em.close();
	        emf.close();
	        
	        return "MESA INSERTADA CORRECTAMENTE";
	    }
	    catch(Exception e) {
	        return "ERROR AL INSERTAR MESA";
	    }
	}
	
	public List<Mesa> ObtenerTodosLasMesas() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
        EntityManager em = emf.createEntityManager();
        List<Mesa> mesas = null;
        try {
            em.getTransaction().begin();
            mesas = em.createQuery("SELECT m from Mesa m", Mesa.class).getResultList();
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
        return mesas;
    }
	
	public Boolean MesaExiste(String nombre, int numero) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    try {
	        // Create a query to retrieve the Mesa entity with the given nombre and numero
	        TypedQuery<Mesa> query = em.createQuery("SELECT m FROM Mesa m WHERE m.nombre = :nombre AND m.numero = :numero", Mesa.class);
	        query.setParameter("nombre", nombre);
	        query.setParameter("numero", numero);

	        // Execute the query and retrieve the Mesa entity
	        Mesa mesa = query.getSingleResult();

	        // If the query returns a result, the mesa exists
	        return true;
	    } catch (NoResultException e) {
	        // If the query doesn't return a result, the mesa doesn't exist
	        return false;
	    } finally {
	        em.close();
	        emf.close();
	    }
	}
	
	public Boolean MesaOcupada(String nombre, int numero) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    try {
	        // Create a query to retrieve the Mesa entity with the given nombre and numero
	        TypedQuery<Mesa> query = em.createQuery("SELECT m FROM Mesa m WHERE m.nombre = :nombre AND m.numero = :numero", Mesa.class);
	        query.setParameter("nombre", nombre);
	        query.setParameter("numero", numero);

	        // Execute the query and retrieve the Mesa entity
	        Mesa mesa = query.getSingleResult();

	        // Return the ocupado state of the Mesa entity
	        return mesa.getOcupado() == 1;
	    }
	    finally {
	        em.close();
	        emf.close();
	    }
	}
	
	public void ActualizarMesaOcupada(int id) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    try {
	        // Iniciar transacción
	        em.getTransaction().begin();

	        // Crear una consulta para recuperar la entidad Mesa con el id dado
	        Mesa mesa = em.find(Mesa.class, id);

	        // Verificar si la mesa existe
	        if (mesa!= null) {
	            // Actualizar el campo Ocupado a 1
	            mesa.setOcupado(1);
	            // Persistir los cambios
	            em.merge(mesa);
	        }

	        // Commit de la transacción
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        // Rollback de la transacción en caso de error
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        throw e;
	    } finally {
	        em.close();
	        emf.close();
	    }
	}
	
	public void ActualizarMesaLibre(int id) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    try {
	        // Iniciar transacción
	        em.getTransaction().begin();

	        // Crear una consulta para recuperar la entidad Mesa con el id dado
	        Mesa mesa = em.find(Mesa.class, id);

	        // Verificar si la mesa existe
	        if (mesa!= null) {
	            // Actualizar el campo Ocupado a 1
	            mesa.setOcupado(0);
	            // Persistir los cambios
	            em.merge(mesa);
	        }

	        // Commit de la transacción
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        // Rollback de la transacción en caso de error
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        throw e;
	    } finally {
	        em.close();
	        emf.close();
	    }
	}
	
	public Integer ObtenerIdMesaPorNombreYNumero(String nombreMesa, Integer numeroMesa) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    Integer idMesa = null;
	    try {
	        em.getTransaction().begin();
	        Query query = em.createQuery("SELECT m.id FROM Mesa m WHERE m.nombre = :nombre AND m.numero = :numero", Integer.class);
	        query.setParameter("nombre", nombreMesa);
	        query.setParameter("numero", numeroMesa);
	        idMesa = (Integer) query.getSingleResult();
	        em.getTransaction().commit();
	    } catch(NoResultException e) {
	        // Si no se encuentra una mesa con el nombre y número especificados, devuelve null
	        idMesa = null;
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        em.close();
	        emf.close();
	    }
	    return idMesa;
	}
	
	public String ActualizarOcupado(int idMesa, int ocupado) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
	    EntityManager em = emf.createEntityManager();
	    try {
	        em.getTransaction().begin();

	        // Create an HQL query to find the pedido by its mesa_id and cierre
	        String query = "SELECT m FROM Mesa m WHERE m.id = :idMesa";
	        Query nativeQuery = em.createQuery(query);
	        nativeQuery.setParameter("idMesa", idMesa);

	        // Execute the query and get the result
	        Mesa mesa = (Mesa) nativeQuery.getSingleResult();

	        // Update the mesa_id to the new value
	        mesa.setOcupado(ocupado);

	        em.getTransaction().commit();

	        return "MESA ACTUALIZADA CORRECTAMENTE";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "MESA ACTUALIZADA CORRECTAMENTE";
	    } finally {
	        em.close();
	        emf.close();
	    }
	}
}
