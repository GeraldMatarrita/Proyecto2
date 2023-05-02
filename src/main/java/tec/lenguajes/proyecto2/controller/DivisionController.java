package tec.lenguajes.proyecto2.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tec.lenguajes.proyecto2.model.Taxones.Division;
import tec.lenguajes.proyecto2.repository.DivisionRepository;
import tec.lenguajes.proyecto2.repository.DivisionRepository;
import tec.lenguajes.proyecto2.repository.DivisionRepository;
import tec.lenguajes.proyecto2.repository.ReinoRepository;

@Controller
@RequestMapping(path = "/divisiones")
public class DivisionController {

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private ReinoRepository reinoRepository;


    @RequestMapping(value = "/show")
    public String showDivision(Model model) {
        model.addAttribute("divisiones", divisionRepository.findAll());
        return "/taxones/division/showDivisiones";
    }

    @GetMapping(value = "/create")
    public String createDivision(Model model) {
        model.addAttribute("newDivision", new Division());
        model.addAttribute("reinos", reinoRepository.findAll());
        return "/taxones/division/createDivision";
    }

    @PostMapping(value = "/create")
    public String createDivision(@ModelAttribute @Valid Division division, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()
                || division.getAuthor() == null
                || division.getParent() == null
                || division.getPublication_year() == null
                || division.getScientific_name() == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al crear la división")
                    .addAttribute("division", "danger");
            return "redirect:/divisiones/show";
        }

        if (!division.suffixVerification(division.getScientific_name()) || !division.initialLetterVerification(division.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/divisiones/show";
        }

        divisionRepository.save(division);
        redirectAttributes
                .addFlashAttribute("message", "Division creada con éxito")
                .addAttribute("division", "success");
        return "redirect:/divisiones/show";
    }

    @GetMapping(value = "/edit/{id}")
    public String editDivision(Model model, @PathVariable Integer id){
        Division divisionAt = divisionRepository.findById(id).orElse(null);
        model.addAttribute("division", divisionAt);
        model.addAttribute("reinos", reinoRepository.findAll());
        return "/taxones/division/editDivision";
    }

    @PostMapping(value = "/edit/{id}")
    public String editDivision(@ModelAttribute @Valid Division division, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("division", "danger");
            return "redirect:/divisiones/show";
        }

        if (!division.suffixVerification(division.getScientific_name()) || !division.initialLetterVerification(division.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/divisiones/show";
        }

        Division posibleDivision = divisionRepository.findFirstById(division.getId()).orElse(null);

        if (posibleDivision == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("division", "danger");
            return "redirect:/divisiones/show";
        }

        divisionRepository.save(division);

        redirectAttributes
                .addFlashAttribute("message", "Division editada con éxito")
                .addAttribute("division", "success");
        return "redirect:/divisiones/show";
    }

    @PostMapping(value = "/delete")
    public String deleteDivision(@ModelAttribute Division division, RedirectAttributes redirectAttributes) {
        divisionRepository.delete(division);
        redirectAttributes
                .addFlashAttribute("message", "Division eliminada con éxito")
                .addAttribute("division", "success");
        return "redirect:/divisiones/show";
    }
}
