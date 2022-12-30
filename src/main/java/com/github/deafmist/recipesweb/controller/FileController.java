package com.github.deafmist.recipesweb.controller;

import com.github.deafmist.recipesweb.service.FileService;
import com.github.deafmist.recipesweb.service.impl.FileIngredientServiceImpl;
import com.github.deafmist.recipesweb.service.impl.FileRecipeServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FileController {
    private FileService fileIngredientService;

    private FileService fileRecipeService;

    @Value("recipesDataFile")
    private String recipeDataFileName;

    public FileController(@Qualifier("fileIngredientServiceImpl") FileService fileIngredientService,
                          @Qualifier("fileRecipeServiceImpl") FileService fileRecipeService) {
        this.fileIngredientService = fileIngredientService;
        this.fileRecipeService = fileRecipeService;
    }

    @GetMapping("/recipes/export")
    public ResponseEntity<InputStreamResource> downloadRecipesDataFile() {
        File file = fileRecipeService.getDataFile();

        if (file.exists()) {
            try {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentLength(file.length())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + recipeDataFileName)
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

    @PostMapping(value = "/recipes/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipesDataFile(@RequestParam MultipartFile file) {
        return uploadDataFile(file, fileRecipeService);
    }

    @PostMapping(value = "/ingredients/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
