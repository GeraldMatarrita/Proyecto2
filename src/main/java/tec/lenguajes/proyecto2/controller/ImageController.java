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
    /*
      Repositorio de Imágenes.
      Proporciona los métodos para acceder a la base de datos de Imágenes.
     */
    @Autowired
    private ImageRepository imageRepository;

    /*
      Repositorio de Personas.
      Proporciona los métodos para acceder a la base de datos de Personas.
     */
    @Autowired
    private PersonRepository personRepository;

    /*
      Repositorio de Instituciones.
      Proporciona los métodos para acceder a la base de datos de Instituciones.
     */
    @Autowired
    private InstitutionRepository institutionRepository;

    /*
      Repositorio de Clases.
      Proporciona los métodos para acceder a la base de datos de Clases.
     */
    @Autowired
    private ClaseRepository claseRepository;

    /*
      Repositorio de Divisiones.
      Proporciona los métodos para acceder a la base de datos de Divisiones.
     */
    @Autowired
    private DivisionRepository divisionRepository;

    /*
      Repositorio de Especies.
      Proporciona los métodos para acceder a la base de datos de Especies.
     */
    @Autowired
    private EspecieRepository especieRepository;

    /*
      Repositorio de Familias.
      Proporciona los métodos para acceder a la base de datos de Familias.
     */
    @Autowired
    private FamiliaRepository familiaRepository;

    /*
      Repositorio de Generos.
      Proporciona los métodos para acceder a la base de datos de Generos.
     */
    @Autowired
    private GeneroRepository generoRepository;

    /*
      Repositorio de Ordenes.
      Proporciona los métodos para acceder a la base de datos de Ordenes.
     */
    @Autowired
    private OrdenRepository ordenRepository;

    /*
      Repositorio de Reinos.
      Proporciona los métodos para acceder a la base de datos de Reinos.
     */
    @Autowired
    private ReinoRepository reinoRepository;


    /*
      Método que muestra todas las imágenes.
      Parámetros:
        - model: Modelo de la vista.
      Retorna: La vista de todas las imágenes.
     */
    @RequestMapping(value = "/show")
    public String showImage(Model model) {
        model.addAttribute("imagenes", imageRepository.findAll());
        model.addAttribute("isSearch", false);
        model.addAttribute("imageQuantity", imageRepository.countImages());
        return "/imagesViews/showImages";
    }

    /*
      Método que muestra una imagen en concreto.
      Parámetros:
        - id: Identificador de la imagen.
        - model: Modelo de la vista.
      Retorna: La vista de una imagen en concreto.
     */
    @GetMapping(value ="show/{id}")
    public String showImageById(@PathVariable Integer id, Model model) {

        // Busca la imagen por su id.
        Optional<Image> image = imageRepository.findFirstById(id);

        // Si la imagen existe, la muestra. Si no, muestra todas las imágenes.
        if (image.isPresent()) {
            model.addAttribute("image", image.get());
            return "/imagesViews/showImage";
        } else {
            return "/imagesViews/showImages";
        }
    }

    /*
      Método que muestra el formulario para crear una imagen.
        Parámetros:
            - model: Modelo de la vista.
        Retorna: La vista del formulario para crear una imagen.
     */
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

    /*
        Método que crea una imagen.
        Parámetros:
            - image: Imagen a crear.
            - bindingResult: Resultado del enlace.
            - redirectAttributes: Atributos de redirección.
            - file: Archivo de la imagen.
        Retorna: La vista de todas las imágenes.
     */
    @PostMapping(value = "/create")
    public String createImage(@ModelAttribute @Valid Image image, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile file) {

        // Verifica si hay errores en el formulario.
        if (bindingResult.hasErrors()
                || image.getAuthor() == null
                || (image.getOwnerPerson() == null
                && image.getOwnerInstitution() == null)
                || image.getLicense() == null
                || image.getDate() == null
                || image.getDescription() == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "ERROR (Los datos de la imagen no deben ser nulos)")
                    .addAttribute("clase", "danger");
            return "redirect:/images/show";
        }

        // Añade las palabras clave.
        image.setKeywords(new ArrayList<>());
        image.updateKeywords();

        // Añade la ruta de la imagen para ser usada por las vistas.
        String relativePath = "/images/";

        // Añade la ruta de la imagen para ser usada por el servidor.
        String absolutePath = "./src/main/resources/static/images";

        // Añade la imagen al servidor.
        int index = Objects.requireNonNull(file.getOriginalFilename().indexOf("."));
        String extension = "." + file.getOriginalFilename().substring(index+1);
        String fileName = file.getOriginalFilename().substring(0, index) + extension;
        Path rutaAbsoluta = Paths.get(absolutePath + "//" + fileName);
        Path rutaRelativa = Paths.get(relativePath + "//" + fileName);
        try {
            Files.write(rutaAbsoluta, file.getBytes());
            // Añade la ruta de la imagen para ser usada por las vistas al objeto imagen.
            image.setPath(rutaRelativa.toString());
        } catch (Exception e) {
            // Si hay un error, muestra un mensaje de error y redirige a la vista de crear imagen.
            redirectAttributes
                    .addFlashAttribute("mensaje", "ERROR (No se pudo subir la imagen)")
                    .addAttribute("clase", "danger");
            return "redirect:/images/create";
        }

        // Guarda la imagen en la base de datos.
        imageRepository.save(image);

        // Redirige a la vista de todas las imágenes.
        redirectAttributes
                .addFlashAttribute("mensaje", "Imagen creada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/images/show";
    }

    /*
        Método que muestra el formulario para editar una imagen.
        Parámetros:
            - model: Modelo de la vista.
            - id: ID de la imagen a editar.
        Retorna: La vista del formulario para editar una imagen.
     */
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

    /*
        Método que edita una imagen.
        Parámetros:
            - image: Imagen a editar.
            - bindingResult: Resultado del enlace.
            - redirectAttributes: Atributos de redirección.
        Retorna: La vista de todas las imágenes.
     */
    @PostMapping(value = "/edit/{id}")
    public String editImage(@ModelAttribute @Valid Image image, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verifica si hay errores en el formulario.
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "Error al editar la imagen")
                    .addAttribute("clase", "danger");
            return "redirect:/images/show";
        }

        // Verifica si la imagen existe.
        Image posibleImage = imageRepository.findFirstById(image.getId()).orElse(null);
        if (posibleImage == null) {
            redirectAttributes
                    .addFlashAttribute("mensaje", "La imagen no existe")
                    .addAttribute("clase", "danger");
            return "redirect:/images/show";
        }

        // Actualiza el tipo de dueño de la imagen.
        if (posibleImage.getOwnerInstitution() != null && image.getOwnerPerson() != null) {
            image.setOwnerInstitution(null);
        } else if (posibleImage.getOwnerPerson() != null && image.getOwnerInstitution() != null) {
            image.setOwnerPerson(null);
        }

        // Actualiza la ruta de la imagen.
        image.setPath(posibleImage.getPath());

        // Actualiza las palabras clave de la imagen.
        image.setKeywords(new ArrayList<>());
        image.updateKeywords();

        // Guarda la imagen en la base de datos.
        imageRepository.save(image);

        // Redirige a la vista de todas las imágenes.
        redirectAttributes
                .addFlashAttribute("mensaje", "Imagen editada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/images/show";
    }

    /*
        Método que elimina una imagen.
        Parámetros:
            - image: Imagen a eliminar.
            - redirectAttributes: Atributos de redirección.
        Retorna: La vista de todas las imágenes.
     */
    @PostMapping(value = "/delete")
    public String deleteImage(@ModelAttribute Image image, RedirectAttributes redirectAttributes) {
        imageRepository.delete(image);
        redirectAttributes
                .addFlashAttribute("mensaje", "Imagen eliminada con éxito")
                .addAttribute("clase", "success");
        return "redirect:/images/show";
    }

    /*
        Método que muestra la vista de todas las imágenes que cumplen un criterio de búsqueda.
        Parámetros:
            - query: Criterio de búsqueda.
            - model: Modelo de la vista.
        Retorna: La vista de todas las imágenes que cumplen un criterio de búsqueda.
     */
    @GetMapping(value = "/search")
    public String searchImages(@RequestParam(name = "query", required = false) String query, Model model) {
        // Verifica si el criterio de búsqueda es nulo o vacío.
        if (query == null || query.isEmpty()) {
            return "redirect:/images/show";
        }
        List<Image> images = imageRepository.findByKeywordsContaining(query);
        model.addAttribute("imagenes", images);
        model.addAttribute("isSearch", true);
        return "/imagesViews/showImages";
    }
}
