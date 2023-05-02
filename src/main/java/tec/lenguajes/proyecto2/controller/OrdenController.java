package tec.lenguajes.proyecto2.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tec.lenguajes.proyecto2.model.Taxones.Familia;
import tec.lenguajes.proyecto2.model.Taxones.Orden;
import tec.lenguajes.proyecto2.repository.ClaseRepository;
import tec.lenguajes.proyecto2.repository.DivisionRepository;
import tec.lenguajes.proyecto2.repository.FamiliaRepository;
import tec.lenguajes.proyecto2.repository.OrdenRepository;

@Controller
@RequestMapping(path = "/ordenes")
public class OrdenController {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private DivisionRepository divisionRepository;


    @RequestMapping(value = "/show")
    public String showOrden(Model model) {
        model.addAttribute("ordenes", ordenRepository.findAll());
        return "/taxones/orden/showOrdenes";
    }

    @GetMapping(value = "/create")
    public String createOrden(Model model) {
        model.addAttribute("newOrden", new Orden());
        model.addAttribute("divisiones", divisionRepository.findAll());
        return "/taxones/orden/createOrden";
    }

    @PostMapping(value = "/create")
    public String createOrden(@ModelAttribute @Valid Orden orden, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()
                || orden.getAuthor() == null
                || orden.getParent() == null
                || orden.getPublication_year() == null
                || orden.getScientific_name() == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al crear la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/ordenes/show";
        }

        if (!orden.suffixVerification(orden.getScientific_name()) || !orden.initialLetterVerification(orden.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/ordenes/show";
        }

        ordenRepository.save(orden);
        redirectAttributes
                .addFlashAttribute("message", "Orden creada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/ordenes/show";
    }

    @GetMapping(value = "/edit/{id}")
    public String editOrden(Model model, @PathVariable Integer id){
        Orden ordenAt = ordenRepository.findById(id).orElse(null);
        model.addAttribute("orden", ordenAt);
        model.addAttribute("divisiones", divisionRepository.findAll());
        return "/taxones/orden/editOrden";
    }

    @PostMapping(value = "/edit/{id}")
    public String editOrden(@ModelAttribute @Valid Orden orden, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/ordenes/show";
        }

        if (!orden.suffixVerification(orden.getScientific_name()) || !orden.initialLetterVerification(orden.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/ordenes/show";
        }

        Orden posibleOrden = ordenRepository.findFirstById(orden.getId()).orElse(null);

        if (posibleOrden == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/ordenes/show";
        }

        ordenRepository.save(orden);

        redirectAttributes
                .addFlashAttribute("message", "Orden editado con éxito")
                .addAttribute("clase", "success");
        return "redirect:/ordenes/show";
    }

    @PostMapping(value = "/delete")
    public String deleteOrden(@ModelAttribute Orden orden, RedirectAttributes redirectAttributes) {
        ordenRepository.delete(orden);
        redirectAttributes
                .addFlashAttribute("message", "Orden eliminada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/ordenes/show";
    }
}
