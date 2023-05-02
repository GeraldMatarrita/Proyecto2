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

@Controller
@RequestMapping(path = "/familias")
public class FamiliaController {

    @Autowired
    private FamiliaRepository familiaRepository;

    @Autowired
    private OrdenRepository ordenRepository;


    @RequestMapping(value = "/show")
    public String showFamilia(Model model) {
        model.addAttribute("familias", familiaRepository.findAll());
        return "/taxones/familia/showFamilias";
    }

    @GetMapping(value = "/create")
    public String createFamilia(Model model) {
        model.addAttribute("newFamilia", new Familia());
        model.addAttribute("ordenes", ordenRepository.findAll());
        return "/taxones/familia/createFamilia";
    }

    @PostMapping(value = "/create")
    public String createFamilia(@ModelAttribute @Valid Familia familia, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()
                || familia.getAuthor() == null
                || familia.getParent() == null
                || familia.getPublication_year() == null
                || familia.getScientific_name() == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al crear la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/familias/show";
        }

        if (!familia.suffixVerification(familia.getScientific_name()) || !familia.initialLetterVerification(familia.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple con el formato de sufijo o letra inicial")
                    .addAttribute("clase", "danger");
            return "redirect:/familias/show";
        }

        familiaRepository.save(familia);
        redirectAttributes
                .addFlashAttribute("message", "Familia creada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/familias/show";
    }

    @GetMapping(value = "/edit/{id}")
    public String editFamilia(Model model, @PathVariable Integer id){
        Familia familiaAt = familiaRepository.findById(id).orElse(null);
        model.addAttribute("familia", familiaAt);
        model.addAttribute("ordenes", ordenRepository.findAll());
        return "/taxones/familia/editFamilia";
    }

    @PostMapping(value = "/edit/{id}")
    public String editFamilia(@ModelAttribute @Valid Familia familia, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/familias/show";
        }

        if (!familia.suffixVerification(familia.getScientific_name()) || !familia.initialLetterVerification(familia.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/familias/show";
        }

        Familia posibleFamilia = familiaRepository.findFirstById(familia.getId()).orElse(null);

        if (posibleFamilia == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/familias/show";
        }

        familiaRepository.save(familia);

        redirectAttributes
                .addFlashAttribute("message", "Familia editada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/familias/show";
    }

    @PostMapping(value = "/delete")
    public String deleteFamilia(@ModelAttribute Familia familia, RedirectAttributes redirectAttributes) {
        familiaRepository.delete(familia);
        redirectAttributes
                .addFlashAttribute("message", "Familia eliminada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/familias/show";
    }
}
