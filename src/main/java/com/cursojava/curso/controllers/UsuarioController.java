package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    private JWTUtil jwtUtil;



    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Lucas");
        usuario.setApellido("Moys");
        usuario.setEmail("lucas@gmail.com");
        usuario.setTelefono("123456789");
        return usuario;
    }



    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List <Usuario> getUsuarios( @RequestHeader(value = "Authorization") String token){
        if(!validateToken(token)) {return null;}

        return usuarioDao.getUsuarios();

    }
    private boolean validateToken(String token){
        String usuario = jwtUtil.getKey(token);
        return usuario != null;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuarios(@RequestBody Usuario usuario){


        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash= argon2.hash(1, 1024,1, usuario.getPassword());
        usuario.setPassword(hash);
         usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "usuario45  ")
    public Usuario editar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setEmail("lucas@gmail.com");
        usuario.setTelefono("123456789");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value = "Authorization") String token,
                          @PathVariable Long id){
        if(!validateToken(token)) {return;}
        usuarioDao.eliminar(id);

    }

    @RequestMapping(value = "usuario123")
    public Usuario buscar(){

        Usuario usuario = new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setEmail("lucas@gmail.com");
        usuario.setTelefono("123456789");
        return usuario;
    }
}
