package tec.lenguajes.proyecto2.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tec.lenguajes.proyecto2.model.Taxones.Familia;
import tec.lenguajes.proyecto2.model.Taxones.Orden;
import tec.lenguajes.proyecto2.repository.ClaseRepository;
import tec.lenguajes.proyecto2.repository.DivisionRepository;
import tec.lenguajes.proyecto2.repository.FamiliaRepository;
import tec.lenguajes.proyecto2.repository.OrdenRepository;

/*
    Controlador de la clase Orden.
    Se encarga de manejar las peticiones relacionadas con la clase Orden.
 */
@Controller
@RequestMapping(path = "/ordenes")
public class OrdenController {

    /*
      Repositorio de Ordenes.
      Proporciona los métodos para acceder a la base de datos de Ordenes.
     */
    @Autowired
    private OrdenRepository ordenRepository;

    /*
      Repositorio de Divisiones.
      Proporciona los métodos para acceder a la base de datos de Divisiones.
     */
    @Autowired
    private DivisionRepository divisionRepository;

    /*
       Método que muestra todas las Órdenes existentes.
       Parámetros:
           - model: Modelo utilizado para enviar información a la vista.
       Retorno: Vista que muestra todas las Órdenes existentes.
     */
    @RequestMapping(value = "/show")
    public String showOrden(Model model) {
        model.addAttribute("ordenes", ordenRepository.findAll());
        return "/taxones/orden/showOrdenes";
    }

    /*
         Método que muestra el formulario para crear una nueva Orden.
         Parámetros:
             - model: Modelo utilizado para enviar información a la vista.
         Retorno: Vista que muestra el formulario para crear una nueva Orden.
     */
    @GetMapping(value = "/create")
    public String createOrden(Model model) {
        model.addAttribute("newOrden", new Orden());
        model.addAttribute("divisiones", divisionRepository.findAll());
        return "/taxones/orden/createOrden";
    }

    /*
        Método que crea una nueva Orden.
        Parámetros:
            - orden: Orden que se desea crear.
            - bindingResult: Resultado de la validación de la Orden.
            - redirectAttributes: Atributos para redireccionar a la vista.
        Retorno: Redireccionamiento a la vista que muestra todas las Órdenes.
     */
    @PostMapping(value = "/create")
    public String createOrden(@ModelAttribute @Valid Orden orden, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en la validación
        if (bindingResult.hasErrors()
                || orden.getAuthor() == null
                || orden.getParent() == null
                || orden.getPublication_year() == null
                || orden.getScientific_name() == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al crear la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/ordenes/show";
        }

        // Verifica si el nombre científico cumple con el formato
        if (!orden.suffixVerification(orden.getScientific_name()) || !orden.initialLetterVerification(orden.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/ordenes/show";
        }

        // Guarda la Orden en la base de datos
        ordenRepository.save(orden);

        // Redirecciona a la vista que muestra todas las Órdenes
        redirectAttributes
                .addFlashAttribute("mensaje", "Orden creada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/ordenes/show";
    }

    /*
        Método que muestra el formulario para editar una Orden.
        Parámetros:
            - model: Modelo utilizado para enviar información a la vista.
            - id: Identificador de la Orden que se desea editar.
        Retorno: Vista que muestra el formulario para editar una Orden.
     */
    @GetMapping(value = "/edit/{id}")
    public String editOrden(Model model, @PathVariable Integer id){
        Orden ordenAt = ordenRepository.findById(id).orElse(null);
        model.addAttribute("orden", ordenAt);
        model.addAttribute("divisiones", divisionRepository.findAll());
        return "/taxones/orden/editOrden";
    }

    /*
        Método que edita una Orden.
        Parámetros:
            - orden: Orden que se desea editar.
            - bindingResult: Resultado de la validación de la Orden.
            - redirectAttributes: Atributos para redireccionar a la vista.
        Retorno: Redireccionamiento a la vista que muestra todas las Órdenes.
     */
    @PostMapping(value = "/edit/{id}")
    public String editOrden(@ModelAttribute @Valid Orden orden, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en la validación
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/ordenes/show";
        }

        // Verifica si el nombre científico cumple con el formato
        if (!orden.suffixVerification(orden.getScientific_name()) || !orden.initialLetterVerification(orden.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/ordenes/show";
        }

        // Verifica si la Orden existe
        Orden posibleOrden = ordenRepository.findFirstById(orden.getId()).orElse(null);
        if (posibleOrden == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/ordenes/show";
        }

        // Guarda la Orden en la base de datos
        ordenRepository.save(orden);

        // Redirecciona a la vista que muestra todas las Órdenes
        redirectAttributes
                .addFlashAttribute("mensaje", "Orden editado con éxito")
                .addAttribute("clase", "success");
        return "redirect:/ordenes/show";
    }

    /*
        Método que elimina una Orden.
        Parámetros:
            - orden: Orden que se desea eliminar.
            - redirectAttributes: Atributos para redireccionar a la vista.
        Retorno: Redireccionamiento a la vista que muestra todas las Órdenes.
     */
    @PostMapping(value = "/delete")
    public String deleteOrden(@ModelAttribute Orden orden, RedirectAttributes redirectAttributes) {

        // Verifica si la Orden tiene imágenes asociadas
        if (orden.getImagesOrden() != null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No se puede eliminar la orden porque tiene imágenes asociadas")
                    .addAttribute("clase", "danger");
            return "redirect:/ordenes/show";
        }

        // Elimina la Orden de la base de datos
        ordenRepository.delete(orden);

        // Redirecciona a la vista que muestra todas las Órdenes
        redirectAttributes
                .addFlashAttribute("mensaje", "Orden eliminada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/ordenes/show";
    }
}
