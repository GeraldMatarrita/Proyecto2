package tec.lenguajes.proyecto2.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tec.lenguajes.proyecto2.model.Taxones.Clase;
import tec.lenguajes.proyecto2.repository.ClaseRepository;
import tec.lenguajes.proyecto2.repository.ClaseRepository;
import tec.lenguajes.proyecto2.repository.DivisionRepository;

/*
    Controlador de la clase Clase.
    Se encarga de manejar las peticiones relacionadas con la clase Clase.
 */
@Controller
@RequestMapping(path = "/clases")
public class ClaseController {

    /*
      Repositorio de Clases.
      Proporciona los métodos para acceder a la base de datos de Clases.
     */
    @Autowired
    private ClaseRepository claseRepository;

    /*
      Repositorio de Divisiones.
      Proporciona los métodos para acceder a la base de datos de Divisiones.
     */
    @Autowired
    private DivisionRepository divisionRepository;

    /*
       Método que muestra todas las Clases existentes.
       Parámetros:
           - model: Modelo utilizado para enviar información a la vista.
       Retorno: Vista que muestra todas las Clases existentes.
     */
    @RequestMapping(value = "/show")
    public String showClase(Model model) {
        model.addAttribute("clases", claseRepository.findAll());
        return "/taxones/clase/showClases";
    }

    /*
         Método que muestra el formulario para crear una nueva Clase.
         Parámetros:
             - model: Modelo utilizado para enviar información a la vista.
         Retorno: Vista que muestra el formulario para crear una nueva Clase.
     */
    @GetMapping(value = "/create")
    public String createClase(Model model) {
        model.addAttribute("newClase", new Clase());
        model.addAttribute("divisiones", divisionRepository.findAll());
        return "/taxones/clase/createClase";
    }

    /*
        Método que crea una nueva Clase.
        Parámetros:
            - clase: Clase que se desea crear.
            - bindingResult: Resultado de la validación de la Clase.
            - redirectAttributes: Atributos para redireccionar a la vista.
        Retorno: Vista que muestra todas las Clases existentes.
     */
    @PostMapping(value = "/create")
    public String createClase(@ModelAttribute @Valid Clase clase, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en el formulario.
        if (bindingResult.hasErrors()
                || clase.getAuthor() == null
                || clase.getParent() == null
                || clase.getPublication_year() == null
                || clase.getScientific_name() == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al crear la clase")
                    .addAttribute("clase", "danger");
            return "redirect:/clases/show";
        }

        // Verifica si el nombre científico cumple con el formato.
        if (!clase.suffixVerification(clase.getScientific_name()) || !clase.initialLetterVerification(clase.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/clases/show";
        }

        // Guarda la Clase en la base de datos.
        claseRepository.save(clase);

        // Redirecciona a la vista de todas las Clases.
        redirectAttributes
                .addFlashAttribute("mensaje", "Clase creada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/clases/show";
    }

    /*
        Método que elimina una Clase.
        Parámetros:
            - id: Id de la Clase que se desea eliminar.
            - redirectAttributes: Atributos para redireccionar a la vista.
        Retorno: Vista que muestra todas las Clases existentes.
     */
    @GetMapping(value = "/edit/{id}")
    public String editClase(Model model, @PathVariable Integer id){
        Clase claseAt = claseRepository.findById(id).orElse(null);
        model.addAttribute("clase", claseAt);
        model.addAttribute("divisiones", divisionRepository.findAll());
        return "/taxones/clase/editClase";
    }

    /*
        Método que edita una Clase.
        Parámetros:
            - clase: Clase que se desea editar.
            - bindingResult: Resultado de la validación de la Division.
            - redirectAttributes: Atributos para redireccionar a la vista.
        Retorno: Redireccionamiento a la vista de Clase.
     */
    @PostMapping(value = "/edit/{id}")
    public String editClase(@ModelAttribute @Valid Clase clase, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en el formulario.
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/clases/show";
        }

        // Verifica si el nombre científico cumple con el formato.
        if (!clase.suffixVerification(clase.getScientific_name()) || !clase.initialLetterVerification(clase.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/clases/show";
        }

        // Verifica si la Clase existe.
        Clase posibleClase = claseRepository.findFirstById(clase.getId()).orElse(null);

        // Verifica si la Clase existe.
        if (posibleClase == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/clases/show";
        }

        // Guarda la Clase en la base de datos.
        claseRepository.save(clase);

        // Redirecciona a la vista de todas las Clases.
        redirectAttributes
                .addFlashAttribute("mensaje", "Clase editada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/clases/show";
    }

    /*
        Método que elimina una Clase.
        Parámetros:
            - id: Id de la Clase que se desea eliminar.
            - redirectAttributes: Atributos para redireccionar a la vista.
        Retorno: Vista que muestra todas las Clases existentes.
     */
    @PostMapping(value = "/delete")
    public String deleteClase(@ModelAttribute Clase clase, RedirectAttributes redirectAttributes) {

        // Verifica si la Clase tiene imágenes asociadas.
        if (clase.getImagesClase() != null){
            redirectAttributes
                    .addFlashAttribute("mensaje", "No se puede eliminar la clase porque tiene imágenes asociadas")
                    .addAttribute("clase", "danger");
            return "redirect:/clases/show";
        }

        // Elimina la Clase de la base de datos.
        claseRepository.delete(clase);

        // Redirecciona a la vista de todas las Clases.
        redirectAttributes
                .addFlashAttribute("mensaje", "Clase eliminada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/clases/show";
    }
}
