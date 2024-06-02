package DAO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import Model.Mesa;
import Model.Pedido;
import Model.ProductoSeleccionado;

public class PedidoDao {
	public int InsertarPedido(Pedido pedido) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();

			// Insertar el pedido en la base de datos
			em.persist(pedido);

			em.getTransaction().commit();

			// Obtener el ID del último registro insertado
			String query = "SELECT p.id FROM Pedido p ORDER BY p.id DESC";
			em.getTransaction().begin();
			Query nativeQuery = em.createQuery(query);
			nativeQuery.setMaxResults(1); // Coge el primer elemento de la consulta
			int id = (int) nativeQuery.getSingleResult();
			em.getTransaction().commit();

			em.close();
			emf.close();

			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			em.close();
			emf.close();
		}
	}

	public List<ProductoSeleccionado> getProductosSeleccionadosByMesa(String nombreMesa, int numeroMesa) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
		EntityManager em = emf.createEntityManager();
		List<ProductoSeleccionado> productosSeleccionados = null;
		try {
			// Primero, obtenemos el ID de la mesa correspondiente al nombre y número de
			// mesa
			String hqlMesa = "SELECT m FROM Mesa m WHERE m.nombre = :nombreMesa AND m.numero = :numeroMesa";
			TypedQuery<Mesa> queryMesa = em.createQuery(hqlMesa, Mesa.class);
			queryMesa.setParameter("nombreMesa", nombreMesa);
			queryMesa.setParameter("numeroMesa", numeroMesa);
			Mesa mesa = queryMesa.getSingleResult();

			// Luego, obtenemos los pedidos que pertenecen a esa mesa
			String hqlPedido = "SELECT p FROM Pedido p WHERE p.mesa_id = :mesaId AND p.cierre IS NULL";
			TypedQuery<Pedido> queryPedido = em.createQuery(hqlPedido, Pedido.class);
			queryPedido.setParameter("mesaId", mesa.getId());

			// Ejecutamos la consulta y obtenemos la lista de pedidos
			List<Pedido> pedidos = queryPedido.getResultList();

			// Creamos una lista para almacenar los productoseleccionados
			productosSeleccionados = new ArrayList<>();

			// Iteramos sobre la lista de pedidos y obtenemos los productoseleccionados
			// asociados a cada pedido
			for (Pedido pedido : pedidos) {
				String hqlProductoSeleccionado = "SELECT ps FROM ProductoSeleccionado ps WHERE ps.pedido_id = :pedidoId";
				TypedQuery<ProductoSeleccionado> queryProductoSeleccionado = em.createQuery(hqlProductoSeleccionado,
						ProductoSeleccionado.class);
				queryProductoSeleccionado.setParameter("pedidoId", pedido.getId());

				// Agregamos los productoseleccionados a la lista
				productosSeleccionados.addAll(queryProductoSeleccionado.getResultList());
			}

			return productosSeleccionados;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
		return productosSeleccionados;
	}

	public List<ProductoSeleccionado> getProductosSeleccionados(int idPedido) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
		EntityManager em = emf.createEntityManager();
		List<ProductoSeleccionado> productosSeleccionados = null;
		try {

			// Creamos una lista para almacenar los productoseleccionados
			productosSeleccionados = new ArrayList<>();
			String hqlProductoSeleccionado = "SELECT ps FROM ProductoSeleccionado ps WHERE ps.pedido_id = :pedidoId";
			TypedQuery<ProductoSeleccionado> queryProductoSeleccionado = em.createQuery(hqlProductoSeleccionado,
					ProductoSeleccionado.class);
			queryProductoSeleccionado.setParameter("pedidoId", idPedido);

			// Agregamos los productoseleccionados a la lista
			productosSeleccionados.addAll(queryProductoSeleccionado.getResultList());

			return productosSeleccionados;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
		return productosSeleccionados;
	}

	public int ObtenerIdPedidoPorIdMesa(int idMesa) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();

			// Crear una consulta para obtener el id del pedido asociado a la mesa
			String query = "SELECT p.id FROM Pedido p WHERE p.mesa_id = :idMesa AND p.cierre IS NULL";
			Query nativeQuery = em.createQuery(query);
			nativeQuery.setParameter("idMesa", idMesa);

			// Ejecutar la consulta y obtener el resultado
			int idPedido = (int) nativeQuery.getSingleResult();

			em.getTransaction().commit();

			em.close();
			emf.close();

			return idPedido;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			em.close();
			emf.close();
		}
	}

	public int ActualizarPedido(int idMesa, int idMesaNuevo) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();

			// Create an HQL query to find the pedido by its mesa_id and cierre
			String query = "SELECT p FROM Pedido p WHERE p.mesa_id = :idMesa AND p.cierre IS NULL";
			Query nativeQuery = em.createQuery(query);
			nativeQuery.setParameter("idMesa", idMesa);

			// Execute the query and get the result
			Pedido pedido = (Pedido) nativeQuery.getSingleResult();

			// Update the mesa_id to the new value
			pedido.setId_mesa(idMesaNuevo);

			em.getTransaction().commit();

			return pedido.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			em.close();
			emf.close();
		}
	}

	public double ObtenerTotalPedido(int idMesa) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();

			// Crear una consulta para obtener el total del pedido
			String query = "SELECT p.total FROM Pedido p WHERE p.mesa_id = :idMesa AND p.cierre IS NULL";
			Query nativeQuery = em.createQuery(query);
			nativeQuery.setParameter("idMesa", idMesa);

			// Ejecutar la consulta y obtener el resultado
			Double totalPedido = (Double) nativeQuery.getSingleResult();

			em.getTransaction().commit();

			em.close();
			emf.close();

			return totalPedido != null ? totalPedido : 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			em.close();
			emf.close();
		}
	}

	public int CerrarPedido(int idMesa) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();

			// Create an HQL query to find the pedido by its mesa_id and cierre
			String query = "SELECT p FROM Pedido p WHERE p.mesa_id = :idMesa AND p.cierre IS NULL";
			Query nativeQuery = em.createQuery(query);
			nativeQuery.setParameter("idMesa", idMesa);

			// Execute the query and get the result
			Pedido pedido = (Pedido) nativeQuery.getSingleResult();

			// Update the mesa_id to the new value
			LocalDate fechaActual = LocalDate.now();
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String cierre = fechaActual.format(formato);
			pedido.setCierre(cierre);

			em.getTransaction().commit();

			return pedido.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			em.close();
			emf.close();
		}
	}

	public List<Pedido> getAllPedidos() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoTPVServidor");
		EntityManager em = emf.createEntityManager();
		List<Pedido> pedidos = null;
		try {
			String hql = "SELECT p FROM Pedido p WHERE p.cierre IS NULL";
			TypedQuery<Pedido> query = em.createQuery(hql, Pedido.class);
			pedidos = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
		return pedidos;
	}
}
