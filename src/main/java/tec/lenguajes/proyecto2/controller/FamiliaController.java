package tec.lenguajes.proyecto2.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tec.lenguajes.proyecto2.model.Taxones.Familia;
import tec.lenguajes.proyecto2.model.Taxones.Genero;
import tec.lenguajes.proyecto2.repository.FamiliaRepository;
import tec.lenguajes.proyecto2.repository.GeneroRepository;
import tec.lenguajes.proyecto2.repository.OrdenRepository;
/*
    Controlador de la clase Familia.
    Se encarga de manejar las peticiones relacionadas con la clase Familia.
 */
@Controller
@RequestMapping(path = "/familias")
public class FamiliaController {

    /*
      Repositorio de Familias.
      Proporciona los métodos para acceder a la base de datos de Familias.
     */
    @Autowired
    private FamiliaRepository familiaRepository;

    /*
      Repositorio de Géneros.
      Proporciona los métodos para acceder a la base de datos de Géneros.
     */
    @Autowired
    private OrdenRepository ordenRepository;

    /*
       Método que muestra todas las Familias existentes.
       Parámetros:
           - model: Modelo utilizado para enviar información a la vista.
       Retorno: Vista que muestra todas las Familias existentes.
     */
    @RequestMapping(value = "/show")
    public String showFamilia(Model model) {
        model.addAttribute("familias", familiaRepository.findAll());
        return "/taxones/familia/showFamilias";
    }

    /*
        Método que muestra el formulario para crear una nueva Familia.
        Parámetros:
            - model: Modelo utilizado para enviar información a la vista.
        Retorno: Vista que muestra el formulario para crear una nueva Familia.
     */
    @GetMapping(value = "/create")
    public String createFamilia(Model model) {
        model.addAttribute("newFamilia", new Familia());
        model.addAttribute("ordenes", ordenRepository.findAll());
        return "/taxones/familia/createFamilia";
    }

    /*
        Método que crea una nueva Familia.
        Parámetros:
            - familia: Familia que se desea crear.
            - bindingResult: Resultado de la validación de la Familia.
            - redirectAttributes: Atributos para redireccionar.
        Retorno: Redireccionamiento a la vista de Familias.
     */
    @PostMapping(value = "/create")
    public String createFamilia(@ModelAttribute @Valid Familia familia, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en la validación.
        if (bindingResult.hasErrors()
                || familia.getAuthor() == null
                || familia.getParent() == null
                || familia.getPublication_year() == null
                || familia.getScientific_name() == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al crear la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/familias/show";
        }

        // Verifica si el nombre científico cumple con el formato.
        if (!familia.suffixVerification(familia.getScientific_name()) || !familia.initialLetterVerification(familia.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple con el formato de sufijo o letra inicial")
                    .addAttribute("clase", "danger");
            return "redirect:/familias/show";
        }

        // Guarda la Familia en la base de datos.
        familiaRepository.save(familia);

        // Redirecciona a la vista de Familias.
        redirectAttributes
                .addFlashAttribute("mensaje", "Familia creada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/familias/show";
    }

    /*
        Método que muestra el formulario para editar una Familia.
        Parámetros:
            - model: Modelo utilizado para enviar información a la vista.
            - id: Identificador de la Familia que se desea editar.
        Retorno: Vista que muestra el formulario para editar una Familia.
     */
    @GetMapping(value = "/edit/{id}")
    public String editFamilia(Model model, @PathVariable Integer id){
        Familia familiaAt = familiaRepository.findById(id).orElse(null);
        model.addAttribute("familia", familiaAt);
        model.addAttribute("ordenes", ordenRepository.findAll());
        return "/taxones/familia/editFamilia";
    }

    /*
        Método que edita una Familia.
        Parámetros:
            - familia: Familia que se desea editar.
            - bindingResult: Resultado de la validación de la Familia.
            - redirectAttributes: Atributos para redireccionar.
        Retorno: Redireccionamiento a la vista de Familias.
     */
    @PostMapping(value = "/edit/{id}")
    public String editFamilia(@ModelAttribute @Valid Familia familia, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en el formulario.
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/familias/show";
        }

        // Verifica si el nombre científico cumple con el formato.
        if (!familia.suffixVerification(familia.getScientific_name()) || !familia.initialLetterVerification(familia.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/familias/show";
        }

        // Verifica si la Familia existe.
        Familia posibleFamilia = familiaRepository.findFirstById(familia.getId()).orElse(null);

        // Verifica si la Familia existe.
        if (posibleFamilia == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/familias/show";
        }

        // Guarda la Familia en la base de datos.
        familiaRepository.save(familia);

        // Redirecciona a la vista de Familias.
        redirectAttributes
                .addFlashAttribute("mensaje", "Familia editada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/familias/show";
    }

    /*
        Método que elimina una Familia.
        Parámetros:
            - familia: Familia que se desea eliminar.
            - redirectAttributes: Atributos para redireccionar.
        Retorno: Redireccionamiento a la vista de Familias.
     */
    @PostMapping(value = "/delete")
    public String deleteFamilia(@ModelAttribute Familia familia, RedirectAttributes redirectAttributes) {
        familiaRepository.delete(familia);
        redirectAttributes
                .addFlashAttribute("mensaje", "Familia eliminada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/familias/show";
    }
}
