package tec.lenguajes.proyecto2.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tec.lenguajes.proyecto2.model.Taxones.Especie;
import tec.lenguajes.proyecto2.model.Taxones.Genero;
import tec.lenguajes.proyecto2.repository.EspecieRepository;
import tec.lenguajes.proyecto2.repository.FamiliaRepository;
import tec.lenguajes.proyecto2.repository.GeneroRepository;

@Controller
@RequestMapping(path = "/generos")
public class GeneroController {

    /*
      Repositorio de Géneros.
      Proporciona los métodos para acceder a la base de datos de Géneros.
     */
    @Autowired
    private GeneroRepository generoRepository;

    /*
      Repositorio de Familias.
      Proporciona los métodos para acceder a la base de datos de Familias.
     */
    @Autowired
    private FamiliaRepository familiaRepository;
    @Autowired
    private EspecieRepository especieRepository;

    /*
       Método que muestra todas las Géneros existentes.
         Parámetros:
              - model: Modelo utilizado para enviar información a la vista.
       Retorno: Vista que muestra todas las Géneros existentes.
     */
    @RequestMapping(value = "/show")
    public String showGenero(Model model) {
        model.addAttribute("generos", generoRepository.findAll());
        return "/taxones/genero/showGeneros";
    }

    /*
        Método que muestra el formulario para crear un nuevo Género.
        Parámetros:
            - model: Modelo utilizado para enviar información a la vista.
        Retorno: Vista que muestra el formulario para crear un nuevo Género.
     */
    @GetMapping(value = "/create")
    public String createGenero(Model model) {
        model.addAttribute("newGenero", new Genero());
        model.addAttribute("familias", familiaRepository.findAll());
        return "/taxones/genero/createGenero";
    }

    /*
        Método que crea un nuevo Género.
        Parámetros:
            - genero: Género que se desea crear.
            - bindingResult: Resultado de la validación del Género.
            - redirectAttributes: Atributos para redireccionar a la vista.
        Retorno: Vista que muestra todos los Géneros existentes.
     */
    @PostMapping(value = "/create")
    public String createGenero(@ModelAttribute @Valid Genero genero, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en el formulario.
        if (bindingResult.hasErrors()
                || genero.getAuthor() == null
                || genero.getParent() == null
                || genero.getPublication_year() == null
                || genero.getScientific_name() == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al crear la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }

        // Verifica si el género ya existe.
        if (generoRepository.existsById(genero.getId())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "El id ya está siendo utilizado")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }

        // Verifica si el nombre científico cumple con el formato.
        if (!genero.suffixVerification(genero.getScientific_name()) || !genero.initialLetterVerification(genero.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }

        // Guarda el Género en la base de datos.
        generoRepository.save(genero);

        // Redirecciona a la vista de todos los Géneros.
        redirectAttributes
                .addFlashAttribute("mensaje", "Genero creada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/generos/show";
    }

    /*
        Método que muestra el formulario para editar un Género.
        Parámetros:
            - model: Modelo utilizado para enviar información a la vista.
            - id: Identificador del Género que se desea editar.
        Retorno: Vista que muestra el formulario para editar un Género.
     */
    @GetMapping(value = "/edit/{id}")
    public String editGenero(Model model, @PathVariable Integer id){
        Genero generoAt = generoRepository.findById(id).orElse(null);
        model.addAttribute("genero", generoAt);
        model.addAttribute("familias", familiaRepository.findAll());
        return "/taxones/genero/editGenero";
    }

    /*
        Método que edita un Género.
        Parámetros:
            - genero: Género que se desea editar.
            - bindingResult: Resultado de la validación del Género.
            - redirectAttributes: Atributos para redireccionar a la vista.
        Retorno: Vista que muestra todas los Géneros existentes.
     */
    @PostMapping(value = "/edit/{id}")
    public String editGenero(@ModelAttribute @Valid Genero genero, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en el formulario.
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }

        // Verifica si el Género existe.
        Genero posibleGenero = generoRepository.findFirstById(genero.getId()).orElse(null);
        if (posibleGenero == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar el género")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }

        // Verifica si el nombre científico cumple con el formato.
        if (!genero.suffixVerification(genero.getScientific_name()) || !genero.initialLetterVerification(genero.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }

        // Guarda el Género en la base de datos.
        generoRepository.save(genero);

        // Redirecciona a la vista de todos los Géneros.
        redirectAttributes
                .addFlashAttribute("mensaje", "Género editado con éxito")
                .addAttribute("clase", "success");
        return "redirect:/generos/show";
    }

    /*
        Método que elimina un Género.
        Parámetros:
            - genero: Género que se desea eliminar.
            - redirectAttributes: Atributos para redireccionar a la vista.
        Retorno: Vista que muestra todos los Géneros existentes.
     */
    @PostMapping(value = "/delete")
    public String deleteImage(@ModelAttribute Genero genero, RedirectAttributes redirectAttributes) {

        // Verifica si el Género tiene imágenes asociadas.
        if (genero.getImagesGenero() != null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No se puede eliminar el género porque tiene imágenes asociadas")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }

        // Verifica si el Género tiene especies asociadas.
        if (especieRepository.findFirstByParent(genero).isPresent()) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No se puede eliminar el género porque tiene especies asociadas")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }

        // Elimina el Género de la base de datos.
        generoRepository.delete(genero);

        // Redirecciona a la vista de todos los Géneros.
        redirectAttributes
                .addFlashAttribute("mensaje", "Género eliminada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/generos/show";
    }
}
