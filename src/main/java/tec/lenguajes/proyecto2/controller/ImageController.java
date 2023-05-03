package tec.lenguajes.proyecto2.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tec.lenguajes.proyecto2.model.Image.Image;
import tec.lenguajes.proyecto2.repository.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping(path = "/images")
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private EspecieRepository especieRepository;

    @Autowired
    private FamiliaRepository familiaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private ReinoRepository reinoRepository;


    @RequestMapping(value = "/show")
    public String showImage(Model model) {
        model.addAttribute("imagenes", imageRepository.findAll());
        model.addAttribute("isSearch", false);
        model.addAttribute("imageQuantity", imageRepository.countImages());
        return "/imagesViews/showImages";
    }

    @GetMapping(value ="show/{id}")
    public String showImageById(@PathVariable Integer id, Model model) {
        Optional<Image> image = imageRepository.findFirstById(id);
        if (image.isPresent()) {
            model.addAttribute("image", image.get());
            return "/imagesViews/showImage";
        } else {
            return "/imagesViews/showImages";
        }
    }

    @GetMapping(value = "/create")
    public String createImage(Model model) {
        model.addAttribute("newImage", new Image());
        model.addAttribute("authors", personRepository.findAll());
        model.addAttribute("persons", personRepository.findAll());
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("especies", especieRepository.findAll());
        model.addAttribute("especies", especieRepository.findAll());
        model.addAttribute("clases", claseRepository.findAll());
        model.addAttribute("divisiones", divisionRepository.findAll());
        model.addAttribute("familias", familiaRepository.findAll());
        model.addAttribute("generos", generoRepository.findAll());
        model.addAttribute("ordenes", ordenRepository.findAll());
        model.addAttribute("reinos", reinoRepository.findAll());
        return "/imagesViews/createImage";
    }

    @PostMapping(value = "/create")
    public String createImage(@ModelAttribute @Valid Image image, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile file) {

        if (bindingResult.hasErrors()
                || image.getAuthor() == null
                || (image.getOwnerPerson() == null
                && image.getOwnerInstitution() == null)
                || image.getEspecie() == null
                || image.getLicense() == null
                || image.getDate() == null
//                || image.getKeywords() == null
                || image.getDescription() == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al crear la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/images/show";
        }

        List<String> keywords = new ArrayList<>();

        keywords.add(image.getAuthor().getName());
        if (image.getOwnerPerson() != null) {
            keywords.add(image.getOwnerPerson().getLastName());
        } else {
            keywords.add(image.getOwnerInstitution().getWebSite());
        }

        keywords.add(image.getAuthor().getName());
        keywords.add(image.getAuthor().getLastName());
        keywords.add(image.getEspecie().getScientific_name());
        keywords.add(image.getGenero().getScientific_name());
        keywords.add(image.getFamilia().getScientific_name());
        keywords.add(image.getOrden().getScientific_name());
        keywords.add(image.getClase().getScientific_name());
        keywords.add(image.getDivision().getScientific_name());
        keywords.add(image.getReino().getScientific_name());
        String[] description = image.getDescription().split(" ");
        keywords.addAll(Arrays.asList(description));
        Image.eliminarArticulos(keywords);
        image.setKeywords(keywords);

        String relativePath = "/images/";
        String absolutePath = "./src/main/resources/static/images";

        int index = Objects.requireNonNull(file.getOriginalFilename().indexOf("."));
        String extension = "." + file.getOriginalFilename().substring(index+1);
        String fileName = file.getOriginalFilename().substring(0, index) + extension;
        Path rutaAbsoluta = Paths.get(absolutePath + "//" + fileName);
        Path rutaRelativa = Paths.get(relativePath + "//" + fileName);
        try {
            Files.write(rutaAbsoluta, file.getBytes());
            image.setPath(rutaRelativa.toString());
        } catch (Exception e) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al crear la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/images/create";
        }

        imageRepository.save(image);
        redirectAttributes
                .addFlashAttribute("message", "Imagen creada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/images/show";
    }

    @GetMapping(value = "/edit/{id}")
    public String editImage(Model model, @PathVariable Integer id){
        Image imageAt = imageRepository.findById(id).orElse(null);
        model.addAttribute("image", imageAt);
        model.addAttribute("authors", personRepository.findAll());
        model.addAttribute("persons", personRepository.findAll());
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("especies", especieRepository.findAll());
        model.addAttribute("clases", claseRepository.findAll());
        model.addAttribute("divisiones", divisionRepository.findAll());
        model.addAttribute("familias", familiaRepository.findAll());
        model.addAttribute("generos", generoRepository.findAll());
        model.addAttribute("ordenes", ordenRepository.findAll());
        model.addAttribute("reinos", reinoRepository.findAll());
        return "/imagesViews/editImage";
    }

    @PostMapping(value = "/edit/{id}")
    public String editImage(@ModelAttribute @Valid Image image, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/images/show";
        }

        Image posibleImage = imageRepository.findFirstById(image.getId()).orElse(null);

        if (posibleImage == null) {
            redirectAttributes
                    .addFlashAttribute("message", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/images/show";
        }

        if (posibleImage.getOwnerInstitution() != null && image.getOwnerPerson() != null) {
            image.setOwnerInstitution(null);
        } else if (posibleImage.getOwnerPerson() != null && image.getOwnerInstitution() != null) {
            image.setOwnerPerson(null);
        }

        image.setPath(posibleImage.getPath());

        imageRepository.save(image);

        redirectAttributes
                .addFlashAttribute("message", "Imagen editada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/images/show";
    }

    @PostMapping(value = "/delete")
    public String deleteImage(@ModelAttribute Image image, RedirectAttributes redirectAttributes) {
        imageRepository.delete(image);
        redirectAttributes
                .addFlashAttribute("message", "Imagen eliminada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/images/show";
    }

    @GetMapping(value = "/search")
    public String searchImages(@RequestParam(name = "query", required = false) String query, Model model) {
        if (query == null || query.isEmpty()) {
            return "redirect:/images/show";
        }
        List<Image> images = imageRepository.findByKeywordsContaining(query);
        model.addAttribute("imagenes", images);
        model.addAttribute("isSearch", true);
        return "/imagesViews/showImages";
    }
}
