package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Controller
public class FileController {
    private final ResourceLoader resourceLoader;

    @Autowired
    private IProductRepository _products;

    @Autowired
    public FileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/update")
    public String update() throws IOException {
        var directoryPath = "C:\\Users\\jos3m\\Downloads\\images";

        var directory = new File(directoryPath);

        File[] filesList = directory.listFiles();

        if (filesList != null) {
            for (File file : filesList) {
                if (file.isFile()) {
                    var productId = file.getName().split("\\.");

                    var product = _products.findById(Long.parseLong(productId[0]));

                    if(product.isPresent()) {
                        product.get().generateSKU();
                        product.get().generateFriendlyUrlFromName();

                        var fileName = product.get().getFriendlyUrl().toLowerCase() + "-" + product.get().getSku();

                        var newFileName = fileName + "." + productId[1];

                        var path = switch (product.get().getCategory()) {
                            case Tops -> "/images/clothes/tops/";
                            case Bottoms -> "/images/clothes/bottoms/";
                            case Shoes -> "/images/clothes/shoes/";
                            case Accessories -> "/images/clothes/accessories/";
                        };

                        var resource = "C:/Users/jos3m/OneDrive/Documentos/Projects/Java/ClothesDelivery/src/main/resources/static/";

                        var fullName = resource + path;

                        //var fileFullName = Paths.get(fullName, newFileName);

                        File directoryPath2 = new File(fullName);

                        if (!directoryPath2.exists()) {
                            directoryPath2.mkdirs();
                        }

                        File newFile = new File(directoryPath2, newFileName);

                        if (!newFile.exists()) {
                            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }

                        /*if (!Files.exists(fileFullName)) {
                            Files.createDirectories(fileFullName.getParent());
                        }

                        Files.move(file.toPath(), fileFullName, StandardCopyOption.REPLACE_EXISTING);*/

                        product.get().setImageUrl(path + newFileName);

                        _products.save(product.get());
                    }

                } else if (file.isDirectory()) {
                    System.out.println("Diretório: " + file.getName());
                }
            }
        } else {
            System.out.println("O caminho fornecido não existe ou não é um diretório.");
        }

        return "update";
    }
}
