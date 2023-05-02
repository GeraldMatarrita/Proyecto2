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

@Controller
@RequestMapping(path = "/clases")
public class ClaseController {

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private DivisionRepository divisionRepository;


    @RequestMapping(value = "/show")
    public String showClase(Model model) {
        model.addAttribute("clases", claseRepository.findAll());
        return "/taxones/clase/showClases";
    }

    @GetMapping(value = "/create")
    public String createClase(Model model) {
        model.addAttribute("newClase", new Clase());
        model.addAttribute("divisiones", divisionRepository.findAll());
        return "/taxones/clase/createClase";
    }

    @PostMapping(value = "/create")
    public String createClase(@ModelAttribute @Valid Clase clase, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()
                || clase.getAuthor() == null
                || clase.getParent() == null
                || clase.getPublication_year() == null
                || clase.getScientific_name() == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al crear la clase")
                    .addAttribute("clase", "danger");
            return "redirect:/clases/show";
        }
        if (!clase.suffixVerification(clase.getScientific_name()) || !clase.initialLetterVerification(clase.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/clases/show";
        }

        claseRepository.save(clase);
        redirectAttributes
                .addFlashAttribute("message", "Clase creada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/clases/show";
    }

    @GetMapping(value = "/edit/{id}")
    public String editClase(Model model, @PathVariable Integer id){
        Clase claseAt = claseRepository.findById(id).orElse(null);
        model.addAttribute("clase", claseAt);
        model.addAttribute("divisiones", divisionRepository.findAll());
        return "/taxones/clase/editClase";
    }

    @PostMapping(value = "/edit/{id}")
    public String editClase(@ModelAttribute @Valid Clase clase, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/clases/show";
        }

        if (!clase.suffixVerification(clase.getScientific_name()) || !clase.initialLetterVerification(clase.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/clases/show";
        }

        Clase posibleClase = claseRepository.findFirstById(clase.getId()).orElse(null);

        if (posibleClase == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/clases/show";
        }

        claseRepository.save(clase);

        redirectAttributes
                .addFlashAttribute("message", "Clase editada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/clases/show";
    }

    @PostMapping(value = "/delete")
    public String deleteClase(@ModelAttribute Clase clase, RedirectAttributes redirectAttributes) {
        claseRepository.delete(clase);
        redirectAttributes
                .addFlashAttribute("message", "Clase eliminada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/clases/show";
    }
}
