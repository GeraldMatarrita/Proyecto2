package tec.lenguajes.proyecto2.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tec.lenguajes.proyecto2.model.Taxones.Division;
import tec.lenguajes.proyecto2.repository.*;
import tec.lenguajes.proyecto2.repository.DivisionRepository;
import tec.lenguajes.proyecto2.repository.DivisionRepository;

/*
    Controlador de la clase Division.
    Se encarga de manejar las peticiones relacionadas con la clase Division.
 */
@Controller
@RequestMapping(path = "/divisiones")
public class DivisionController {

    /*
      Repositorio de Divisiones.
      Proporciona los métodos para acceder a la base de datos de Divisiones.
     */
    @Autowired
    private DivisionRepository divisionRepository;

    /*
      Repositorio de Reinos.
      Proporciona los métodos para acceder a la base de datos de Reinos.
     */
    @Autowired
    private ReinoRepository reinoRepository;
    @Autowired
    private ClaseRepository claseRepository;


    /*
       Método que muestra todas las Divisiones existentes.
       Parámetros:
           - model: Modelo utilizado para enviar información a la vista.
       Retorno: Vista que muestra todas las Divisiones existentes.
     */
    @RequestMapping(value = "/show")
    public String showDivision(Model model) {
        model.addAttribute("divisiones", divisionRepository.findAll());
        return "/taxones/division/showDivisiones";
    }

    /*
         Método que muestra el formulario para crear una nueva Division.
         Parámetros:
              - model: Modelo utilizado para enviar información a la vista.
         Retorno: Vista que muestra el formulario para crear una nueva Division.
     */
    @GetMapping(value = "/create")
    public String createDivision(Model model) {
        model.addAttribute("newDivision", new Division());
        model.addAttribute("reinos", reinoRepository.findAll());
        return "/taxones/division/createDivision";
    }

    /*
         Método que crea una nueva Division.
         Parámetros:
             - division: Division que se desea crear.
             - bindingResult: Resultado de la validación de la Division.
             - redirectAttributes: Atributos para redireccionar a la vista.
         Retorno: Redireccionamiento a la vista de Divisiones.
     */
    @PostMapping(value = "/create")
    public String createDivision(@ModelAttribute @Valid Division division, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en el formulario.
        if (bindingResult.hasErrors()
                || division.getAuthor() == null
                || division.getParent() == null
                || division.getPublication_year() == null
                || division.getScientific_name() == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al crear la división")
                    .addAttribute("division", "danger");
            return "redirect:/divisiones/show";
        }

        // Verifica si la division ya existe.
        if (divisionRepository.existsById(division.getId())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "El id ya está siendo utilizado")
                    .addAttribute("division", "danger");
            return "redirect:/divisiones/show";
        }

        // Verifica si el nombre científico cumple con el formato.
        if (!division.suffixVerification(division.getScientific_name()) || !division.initialLetterVerification(division.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/divisiones/show";
        }

        // Se guarda la Division en la base de datos.
        divisionRepository.save(division);

        // Se redirecciona a la vista de Divisiones.
        redirectAttributes
                .addFlashAttribute("mensaje", "Division creada con éxito")
                .addAttribute("division", "success");
        return "redirect:/divisiones/show";
    }

    /*
        Método que muestra el formulario para editar una Division.
        Parámetros:
            - model: Modelo utilizado para enviar información a la vista.
            - id: Id de la Division que se desea editar.
        Retorno: Vista que muestra el formulario para editar una Division.
     */
    @GetMapping(value = "/edit/{id}")
    public String editDivision(Model model, @PathVariable Integer id) {
        Division divisionAt = divisionRepository.findById(id).orElse(null);
        model.addAttribute("division", divisionAt);
        model.addAttribute("reinos", reinoRepository.findAll());
        return "/taxones/division/editDivision";
    }

    /*
        Método que edita una Division.
        Parámetros:
            - division: Division que se desea editar.
            - bindingResult: Resultado de la validación de la Division.
            - redirectAttributes: Atributos para redireccionar a la vista.
        Retorno: Redireccionamiento a la vista de Divisiones.
     */
    @PostMapping(value = "/edit/{id}")
    public String editDivision(@ModelAttribute @Valid Division division, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en el formulario.
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("division", "danger");
            return "redirect:/divisiones/show";
        }

        // Verifica si el nombre científico cumple con el formato.
        if (!division.suffixVerification(division.getScientific_name()) || !division.initialLetterVerification(division.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/divisiones/show";
        }

        // Verifica si la Division existe.
        Division posibleDivision = divisionRepository.findFirstById(division.getId()).orElse(null);
        if (posibleDivision == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("division", "danger");
            return "redirect:/divisiones/show";
        }

        // Se edita la Division en la base de datos.
        divisionRepository.save(division);

        // Se redirecciona a la vista de Divisiones.
        redirectAttributes
                .addFlashAttribute("mensaje", "Division editada con éxito")
                .addAttribute("division", "success");
        return "redirect:/divisiones/show";
    }

    /*
        Método que eliminar una Division.
        Parámetros:
            - model: División que se desea eliminar.
            - redirectAttributes: Atributos para redireccionar a la vista.
        Retorno: Redireccionamiento a la vista de Divisiones.
     */
    @PostMapping(value = "/delete")
    public String deleteDivision(@ModelAttribute Division division, RedirectAttributes redirectAttributes) {

        // Verifica si la división tiene imágenes asociadas.
        if (division.getImagesDivision() != null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No se puede eliminar la división porque tiene imágenes asociadas")
                    .addAttribute("division", "danger");
            return "redirect:/divisiones/show";
        }

        // Verifica si la división tiene clases asociadas.
        if (claseRepository.findFirstByParent(division).isPresent()){
            redirectAttributes
                    .addFlashAttribute("mensaje", "No se puede eliminar la división porque tiene clases asociadas")
                    .addAttribute("division", "danger");
            return "redirect:/divisiones/show";
        }

        // Elimina la división de la base de datos.
        divisionRepository.delete(division);

        // Redirecciona a la vista de todas las Divisiones.
        redirectAttributes
                .addFlashAttribute("mensaje", "Division eliminada con éxito")
                .addAttribute("division", "success");
        return "redirect:/divisiones/show";
    }
}
