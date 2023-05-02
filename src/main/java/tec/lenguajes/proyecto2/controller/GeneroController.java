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

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private FamiliaRepository familiaRepository;


    @RequestMapping(value = "/show")
    public String showGenero(Model model) {
        model.addAttribute("generos", generoRepository.findAll());
        return "/taxones/genero/showGeneros";
    }

    @GetMapping(value = "/create")
    public String createGenero(Model model) {
        model.addAttribute("newGenero", new Genero());
        model.addAttribute("familias", familiaRepository.findAll());
        return "/taxones/genero/createGenero";
    }

    @PostMapping(value = "/create")
    public String createGenero(@ModelAttribute @Valid Genero genero, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()
                || genero.getAuthor() == null
                || genero.getParent() == null
                || genero.getPublication_year() == null
                || genero.getScientific_name() == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al crear la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }
        if (!genero.suffixVerification(genero.getScientific_name()) || !genero.initialLetterVerification(genero.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }

        generoRepository.save(genero);
        redirectAttributes
                .addFlashAttribute("message", "Genero creada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/generos/show";
    }

    @GetMapping(value = "/edit/{id}")
    public String editGenero(Model model, @PathVariable Integer id){
        Genero generoAt = generoRepository.findById(id).orElse(null);
        model.addAttribute("genero", generoAt);
        model.addAttribute("familias", familiaRepository.findAll());
        return "/taxones/genero/editGenero";
    }

    @PostMapping(value = "/edit/{id}")
    public String editGenero(@ModelAttribute @Valid Genero genero, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }

        Genero posibleGenero = generoRepository.findFirstById(genero.getId()).orElse(null);

        if (posibleGenero == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar el género")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }

        if (!genero.suffixVerification(genero.getScientific_name()) || !genero.initialLetterVerification(genero.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/generos/show";
        }

        generoRepository.save(genero);

        redirectAttributes
                .addFlashAttribute("message", "Género editado con éxito")
                .addAttribute("clase", "success");
        return "redirect:/generos/show";
    }

    @PostMapping(value = "/delete")
    public String deleteImage(@ModelAttribute Genero genero, RedirectAttributes redirectAttributes) {
        generoRepository.delete(genero);
        redirectAttributes
                .addFlashAttribute("message", "Género eliminada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/generos/show";
    }
}
