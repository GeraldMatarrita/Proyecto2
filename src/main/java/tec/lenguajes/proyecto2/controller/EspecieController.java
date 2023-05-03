package tec.lenguajes.proyecto2.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tec.lenguajes.proyecto2.model.Taxones.Especie;
import tec.lenguajes.proyecto2.repository.*;
/*
    Controlador de la clase Especie.
    Se encarga de manejar las peticiones relacionadas con la clase Especie.
 */
@Controller
@RequestMapping(path = "/especies")
public class EspecieController {
    /*
      Repositorio de Especies.
      Proporciona los métodos para acceder a la base de datos de Especies.
     */
    @Autowired
    private EspecieRepository especieRepository;

    /*
      Repositorio de Generos.
      Proporciona los métodos para acceder a la base de datos de Generos.
     */
    @Autowired
    private GeneroRepository generoRepository;

    /*
       Método que muestra todas las Especies existentes.
       Parámetros:
           - model: Modelo utilizado para enviar información a la vista.
       Retorno: Vista que muestra todas las Especies existentes.
     */
    @RequestMapping(value = "/show")
    public String showEspecie(Model model) {
        model.addAttribute("especies", especieRepository.findAll());
        return "/taxones/especie/showEspecies";
    }

    /*
        Método que muestra el formulario para crear una nueva Especie.
        Parámetros:
            - model: Modelo utilizado para enviar información a la vista.
        Retorno: Vista que muestra el formulario para crear una nueva Especie.
     */
    @GetMapping(value = "/create")
    public String createEspecie(Model model) {
        model.addAttribute("newEspecie", new Especie());
        model.addAttribute("generos", generoRepository.findAll());
        return "/taxones/especie/createEspecie";
    }

    /*
        Método que crea una nueva Especie.
        Parámetros:
            - especie: Especie que se desea crear.
            - bindingResult: Resultado de la validación de la Especie.
            - redirectAttributes: Atributos para redireccionar.
        Retorno: Redireccionamiento a la vista que muestra todas las Especies.
     */
    @PostMapping(value = "/create")
    public String createEspecie(@ModelAttribute @Valid Especie especie, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en el formulario.
        if (bindingResult.hasErrors()
                || especie.getAuthor() == null
                || especie.getParent() == null
                || especie.getPublication_year() == null
                || especie.getScientific_name() == null
                || especie.getSpecimen_location_drawer() == null
                || especie.getSpecimen_location_rack() == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al crear la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/especies/show";
        }

        // Verifica si el nombre científico cumple con el formato.
        if (!especie.suffixVerification(especie.getScientific_name()) || !especie.initialLetterVerification(especie.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/especies/show";
        }

        // Guarda la Especie en la base de datos.
        especieRepository.save(especie);

        // Redirecciona a la vista que muestra todas las Especies.
        redirectAttributes
                .addFlashAttribute("mensaje", "Especie creada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/especies/show";
    }

    /*
        Método que muestra el formulario para editar una Especie existente.
        Parámetros:
            - model: Modelo utilizado para enviar información a la vista.
            - id: Id de la Especie que se desea editar.
        Retorno: Vista que muestra el formulario para editar una Especie existente.
     */
    @GetMapping(value = "/edit/{id}")
    public String editEspecie(Model model, @PathVariable Integer id){
        Especie especieAt = especieRepository.findById(id).orElse(null);
        model.addAttribute("especie", especieAt);
        model.addAttribute("generos", generoRepository.findAll());
        return "/taxones/especie/editEspecie";
    }

    /*
        Método que edita una Especie existente.
        Parámetros:
            - especie: Especie que se desea editar.
            - bindingResult: Resultado de la validación de la Especie.
            - redirectAttributes: Atributos para redireccionar.
        Retorno: Redirección a la vista que muestra todas las Especies existentes.
     */
    @PostMapping(value = "/edit/{id}")
    public String editEspecie(@ModelAttribute @Valid Especie especie, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en el formulario.
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/especies/show";
        }

        // Verifica si el nombre científico cumple con el formato.
        if (!especie.suffixVerification(especie.getScientific_name()) || !especie.initialLetterVerification(especie.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/familias/show";
        }

        // Verifica si la Especie existe.
        Especie posibleEspecie = especieRepository.findFirstById(especie.getId()).orElse(null);
        if (posibleEspecie == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/especies/show";
        }

        // Guarda la Especie en la base de datos.
        especieRepository.save(especie);

        // Redirecciona a la vista que muestra todas las Especies existentes.
        redirectAttributes
                .addFlashAttribute("mensaje", "Clase editada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/especies/show";
    }

    /* Método que elimina una Especie existente.
       Parámetros:
           - especie: Especie que se desea eliminar.
           - redirectAttributes: Atributos para redireccionar.
       Retorno: Redirección a la vista que muestra todas las Especies existentes.
     */
    @PostMapping(value = "/delete")
    public String deleteImage(@ModelAttribute Especie especie, RedirectAttributes redirectAttributes) {
        especieRepository.delete(especie);
        redirectAttributes
                .addFlashAttribute("mensaje", "Clase eliminada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/especies/show";
    }
}
