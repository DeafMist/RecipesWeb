package com.github.deafmist.recipesweb.controller;

import com.github.deafmist.recipesweb.service.FileService;
import com.github.deafmist.recipesweb.service.impl.FileIngredientServiceImpl;
import com.github.deafmist.recipesweb.service.impl.FileRecipeServiceImpl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
public class FileController {
    private FileIngredientServiceImpl fileIngredientService;

    private FileRecipeServiceImpl fileRecipeService;

    public FileController(FileIngredientServiceImpl fileIngredientService, FileRecipeServiceImpl fileRecipeService) {
        this.fileIngredientService = fileIngredientService;
        this.fileRecipeService = fileRecipeService;
    }

    @GetMapping("/recipesFile/export")
    public ResponseEntity<InputStreamResource> downloadRecipesDataFile() {
        File file = fileRecipeService.getDataFile();

        if (file.exists()) {
            try {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentLength(file.length())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesLog.json\"")
                        .body(resource);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return ResponseEntity.noContent().build();
            }
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/recipesFile/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipesDataFile(@RequestParam MultipartFile file) {
        return uploadDataFile(file, fileRecipeService);
    }

    @PostMapping(value = "/ingredientsFile/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientsDataFile(@RequestParam MultipartFile file) {
        return uploadDataFile(file, fileIngredientService);
    }

    private ResponseEntity<Void> uploadDataFile(MultipartFile file, FileService fileService) {
        fileService.cleanDataFile(fileService.getDataFile().toPath());
        File dataFile = fileService.getDataFile();

        try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dataFile))) {
            byte[] buffer = new byte[1024];
            while (bis.read(buffer) > 0) {
                bos.write(buffer);
            }
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
