package org.example;

import org.example.models.Categoria;
import org.example.models.Producto;
import org.example.repositorio.ProductoRepositorioImpl;
import org.example.repositorio.Repositorio;
import org.example.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class EjemploJDBCTrx {
    public static void main(String[] args) throws SQLException {

        try (Connection conn = ConexionBaseDatos.getInstance();){ //se encarga de administrar las conexiones y drivers que tenemos disponibles
            if (conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
            try {
                Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
                System.out.println("======= listar =======");
                repositorio.listar().forEach(System.out::println);

                System.out.println("======= obtener por id =======");
                System.out.println(repositorio.porId(2L));

                System.out.println("======= insertar nuevo producto =======");
                Producto producto = new Producto();
                producto.setNombre("Teclado mecanico nuevo");
                producto.setPrecio(500);
                producto.setFechaRegistro(new Date());
                Categoria categoria = new Categoria();
                categoria.setId(3L);
                producto.setCategoria(categoria);
                producto.setSku("abd12345");
                repositorio.guardar(producto);
                System.out.println("Producto guardado con exito");


                System.out.println("======= Editar un Producto =======");
                producto = new Producto();
                producto.setId(12L);
                producto.setNombre("Teclado mecanico mod");
                producto.setPrecio(1000);
                producto.setSku("abed123456");
                categoria = new Categoria();
                categoria.setId(3L);
                producto.setCategoria(categoria);
                repositorio.guardar(producto);
                System.out.println("Producto editado con exito");

                repositorio.listar().forEach(System.out::println);
                conn.commit();

            } catch(SQLException e){
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
}