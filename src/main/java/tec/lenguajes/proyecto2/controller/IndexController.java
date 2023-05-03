package tec.lenguajes.proyecto2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/*
    Controlador de la página principal.
 */
@Controller
public class IndexController {

    /*
        Método que muestra la página principal.
        Retorno: Vista que muestra la página principal.
     */
    @RequestMapping(value = {"/home", "/"})
    public String showImage () {
        return "index";
    }
}
