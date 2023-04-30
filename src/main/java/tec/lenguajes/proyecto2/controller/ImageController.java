package tec.lenguajes.proyecto2.controller;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tec.lenguajes.proyecto2.model.Image;
import tec.lenguajes.proyecto2.repository.ImageRepository;

import java.util.Optional;

@Controller
@RequestMapping(path = "/images")
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;

    @RequestMapping(value = "/show")
    public String showImage(Model model) {
        model.addAttribute("imagenes", imageRepository.findAll());
//        model.addAttribute()
        return "showImages";
    }

    @GetMapping(value ="show/{id}")
    public String showImageById(@PathVariable Integer id, Model model) {
        Optional<Image> image = imageRepository.findFirstById(id);


        if (image.isPresent()) {
            model.addAttribute("image", image.get());
            return "showImage";
        } else {
            return "error";
        }
    }
}
