package tec.lenguajes.proyecto2.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tec.lenguajes.proyecto2.model.Taxones.Reino;
import tec.lenguajes.proyecto2.repository.DivisionRepository;
import tec.lenguajes.proyecto2.repository.ReinoRepository;
import tec.lenguajes.proyecto2.repository.ReinoRepository;

@Controller
@RequestMapping(path = "/reinos")
public class ReinoController {

    /*
      Repositorio de Reinos.
      Proporciona los métodos para acceder a la base de datos de Reinos.
     */
    @Autowired
    private ReinoRepository reinoRepository;
    @Autowired
    private DivisionRepository divisionRepository;

    /*
      Método que muestra todos los Reinos existentes.
      Parámetros:
          - model: Modelo utilizado para enviar información a la vista.
      Retorno: Vista que muestra todos los Reinos existentes.
    */
    @RequestMapping(value = "/show")
    public String showReino(Model model) {
        model.addAttribute("reinos", reinoRepository.findAll());
        return "/taxones/reino/showReinos";
    }

    /*
      Método que muestra el formulario para crear un nuevo Reino.
      Parámetros:
           - model: Modelo utilizado para enviar información a la vista.
      Retorno: Vista que muestra el formulario para crear uno nuevo Reino.
    */
    @GetMapping(value = "/create")
    public String createReino(Model model) {
        model.addAttribute("newReino", new Reino());
        return "/taxones/reino/createReino";
    }

    /*
        Método que crea una nueva Reino.
        Parámetros:
            - reino: Objeto de tipo Reino que contiene la información del Reino a crear.
            - bindingResult: Objeto que contiene el resultado de la validación.
            - redirectAttributes: Objeto utilizado para enviar atributos a la vista.
        Retorno: Vista que muestra todos los Reinos existentes.
     */
    @PostMapping(value = "/create")
    public String createReino(@ModelAttribute @Valid Reino reino, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en el formulario.
        if (bindingResult.hasErrors()
                || reino.getAuthor() == null
                || reino.getPublication_year() == null
                || reino.getScientific_name() == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al crear la imagen")
                    .addAttribute("reino", "danger");
            return "redirect:/reinos/show";
        }

        // Verifica si el reino ya existe.
        if (reinoRepository.existsById(reino.getId())){
            redirectAttributes
                    .addFlashAttribute("mensaje", "El id ya está siendo utilizado")
                    .addAttribute("reino", "danger");
            return "redirect:/reinos/show";
        }

        // Verifica si el nombre científico cumple con el formato.
        if (!reino.suffixVerification(reino.getScientific_name()) || !reino.initialLetterVerification(reino.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/reinos/show";
        }

        // Guarda el Reino en la base de datos.
        reinoRepository.save(reino);
        redirectAttributes
                .addFlashAttribute("mensaje", "Reino creada con éxito")
                .addAttribute("reino", "success");
        return "redirect:/reinos/show";
    }

    /*
        Método que muestra el formulario para editar un Reino.
        Parámetros:
            - model: Modelo utilizado para enviar información a la vista.
            - id: Identificador del Reino a editar.
        Retorno: Vista que muestra el formulario para editar un Reino.
     */
    @GetMapping(value = "/edit/{id}")
    public String editReino(Model model, @PathVariable Integer id){
        Reino reinoAt = reinoRepository.findById(id).orElse(null);
        model.addAttribute("reino", reinoAt);
        return "/taxones/reino/editReino";
    }

    /*
        Método que edita un Reino.
        Parámetros:
            - reino: Objeto de tipo Reino que contiene la información del Reino a editar.
            - bindingResult: Objeto que contiene el resultado de la validación.
            - redirectAttributes: Objeto utilizado para enviar atributos a la vista.
        Retorno: Vista que muestra todos los Reinos existentes.
     */
    @PostMapping(value = "/edit/{id}")
    public String editReino(@ModelAttribute @Valid Reino reino, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en la validación.
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("reino", "danger");
            return "redirect:/reinos/show";
        }

        // Verifica si el nombre científico cumple con el formato.
        if (!reino.suffixVerification(reino.getScientific_name()) || !reino.initialLetterVerification(reino.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/reinos/show";
        }

        // Verifica si el Reino existe.
        Reino posibleReino = reinoRepository.findFirstById(reino.getId()).orElse(null);
        if (posibleReino == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("reino", "danger");
            return "redirect:/reinos/show";
        }

        // Guarda el Reino en la base de datos.
        reinoRepository.save(reino);

        // Redirecciona a la vista de todos los Reinos.
        redirectAttributes
                .addFlashAttribute("mensaje", "Reino editada con éxito")
                .addAttribute("reino", "success");
        return "redirect:/reinos/show";
    }

    /*
        Método que elimina un Reino.
        Parámetros:
            - reino: Objeto de tipo Reino que contiene la información del Reino a eliminar.
            - redirectAttributes: Objeto utilizado para enviar atributos a la vista.
        Retorno: Vista que muestra todos los Reinos existentes.
     */
    @PostMapping(value = "/delete")
    public String deleteReino(@ModelAttribute Reino reino, RedirectAttributes redirectAttributes) {

        // Verifica si el Reino tiene imágenes asociadas.
        if (reino.getImagesReino() != null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No se puede eliminar el Reino porque tiene imágenes asociadas")
                    .addAttribute("reino", "danger");
            return "redirect:/reinos/show";
        }

        // Verifica si el Reino tiene divisiones asociadas.
        if (divisionRepository.findFirstByParent(reino).isPresent()) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "No se puede eliminar el Reino porque tiene divisiones asociadas")
                    .addAttribute("reino", "danger");
            return "redirect:/reinos/show";
        }

        // Elimina el Reino de la base de datos.
        reinoRepository.delete(reino);

        // Redirecciona a la vista de todos los Reinos.
        redirectAttributes
                .addFlashAttribute("mensaje", "Reino eliminada con éxito")
                .addAttribute("reino", "success");
        return "redirect:/reinos/show";
    }
}
