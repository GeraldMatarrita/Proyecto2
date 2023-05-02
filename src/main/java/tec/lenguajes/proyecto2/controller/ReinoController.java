package tec.lenguajes.proyecto2.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tec.lenguajes.proyecto2.model.Taxones.Reino;
import tec.lenguajes.proyecto2.repository.ReinoRepository;
import tec.lenguajes.proyecto2.repository.ReinoRepository;

@Controller
@RequestMapping(path = "/reinos")
public class ReinoController {

    @Autowired
    private ReinoRepository reinoRepository;

    @RequestMapping(value = "/show")
    public String showReino(Model model) {
        model.addAttribute("reinos", reinoRepository.findAll());
        return "/taxones/reino/showReinos";
    }

    @GetMapping(value = "/create")
    public String createReino(Model model) {
        model.addAttribute("newReino", new Reino());
        return "/taxones/reino/createReino";
    }

    @PostMapping(value = "/create")
    public String createReino(@ModelAttribute @Valid Reino reino, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()
                || reino.getAuthor() == null
                || reino.getPublication_year() == null
                || reino.getScientific_name() == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al crear la imagen")
                    .addAttribute("reino", "danger");
            return "redirect:/reinos/show";
        }

        if (!reino.suffixVerification(reino.getScientific_name()) || !reino.initialLetterVerification(reino.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/reinos/show";
        }

        reinoRepository.save(reino);
        redirectAttributes
                .addFlashAttribute("message", "Reino creada con éxito")
                .addAttribute("reino", "success");
        return "redirect:/reinos/show";
    }

    @GetMapping(value = "/edit/{id}")
    public String editReino(Model model, @PathVariable Integer id){
        Reino reinoAt = reinoRepository.findById(id).orElse(null);
        model.addAttribute("reino", reinoAt);
        return "/taxones/reino/editReino";
    }

    @PostMapping(value = "/edit/{id}")
    public String editReino(@ModelAttribute @Valid Reino reino, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("reino", "danger");
            return "redirect:/reinos/show";
        }

        if (!reino.suffixVerification(reino.getScientific_name()) || !reino.initialLetterVerification(reino.getScientific_name())) {
            redirectAttributes
                    .addFlashAttribute("message", "No cumple con el formato de nombre científico")
                    .addAttribute("clase", "danger");
            return "redirect:/reinos/show";
        }

        Reino posibleReino = reinoRepository.findFirstById(reino.getId()).orElse(null);

        if (posibleReino == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("reino", "danger");
            return "redirect:/reinos/show";
        }

        reinoRepository.save(reino);

        redirectAttributes
                .addFlashAttribute("message", "Reino editada con éxito")
                .addAttribute("reino", "success");
        return "redirect:/reinos/show";
    }

    @PostMapping(value = "/delete")
    public String deleteReino(@ModelAttribute Reino reino, RedirectAttributes redirectAttributes) {
        reinoRepository.delete(reino);
        redirectAttributes
                .addFlashAttribute("message", "Reino eliminada con éxito")
                .addAttribute("reino", "success");
        return "redirect:/reinos/show";
    }
}
