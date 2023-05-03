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

@Controller
@RequestMapping(path = "/especies")
public class EspecieController {
    @Autowired
    private EspecieRepository especieRepository;
    @Autowired
    private GeneroRepository generoRepository;


    @RequestMapping(value = "/show")
    public String showEspecie(Model model) {
        model.addAttribute("especies", especieRepository.findAll());
        return "/taxones/especie/showEspecies";
    }

    @GetMapping(value = "/create")
    public String createEspecie(Model model) {
        model.addAttribute("newEspecie", new Especie());
        model.addAttribute("generos", generoRepository.findAll());
        return "/taxones/especie/createEspecie";
    }

    @PostMapping(value = "/create")
    public String createEspecie(@ModelAttribute @Valid Especie especie, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()
                || especie.getAuthor() == null
                || especie.getParent() == null
                || especie.getPublication_year() == null
                || especie.getScientific_name() == null
                || especie.getSpecimen_location_drawer() == null
                || especie.getSpecimen_location_rack() == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al crear la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/especies/show";
        }

        if (!especie.suffixVerification(especie.getScientific_name()) || !especie.initialLetterVerification(especie.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/especies/show";
        }

        especieRepository.save(especie);
        redirectAttributes
                .addFlashAttribute("message", "Especie creada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/especies/show";
    }

    @GetMapping(value = "/edit/{id}")
    public String editEspecie(Model model, @PathVariable Integer id){
        Especie especieAt = especieRepository.findById(id).orElse(null);
        model.addAttribute("especie", especieAt);
        model.addAttribute("generos", generoRepository.findAll());
        return "/taxones/especie/editEspecie";
    }

    @PostMapping(value = "/edit/{id}")
    public String editEspecie(@ModelAttribute @Valid Especie especie, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/especies/show";
        }

        if (!especie.suffixVerification(especie.getScientific_name()) || !especie.initialLetterVerification(especie.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/familias/show";
        }

        Especie posibleEspecie = especieRepository.findFirstById(especie.getId()).orElse(null);

        if (posibleEspecie == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/especies/show";
        }

        especieRepository.save(especie);

        redirectAttributes
                .addFlashAttribute("message", "Clase editada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/especies/show";
    }

    @PostMapping(value = "/delete")
    public String deleteImage(@ModelAttribute Especie especie, RedirectAttributes redirectAttributes) {
        especieRepository.delete(especie);
        redirectAttributes
                .addFlashAttribute("message", "Clase eliminada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/especies/show";
    }
}
