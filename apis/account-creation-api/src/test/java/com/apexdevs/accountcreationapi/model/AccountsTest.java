package com.apexdevs.accountcreationapi.model;

import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cuentas")
public class
AccountsTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Integer id_usuario;

    @Column(name="nombre")
    private String nombre;

    @Column(name="correo")
    private String correo;

    @Column(name="contraseña")
    private String contraseña;


   /* public AccountsTest(JSONObject jsonObject)throws JSONException {
        this.id_usuario = jsonObject.getInt("id_usuario");
        this.nombre = jsonObject.getString("nombre");
        this.correo = jsonObject.getString("email");
        this.contraseña = jsonObject.getString("contraseña");
    }*/

}