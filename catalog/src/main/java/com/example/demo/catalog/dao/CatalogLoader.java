package com.example.demo.catalog.dao;


import com.example.demo.catalog.model.Product;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class CatalogLoader {

    @Value("${catalog.products.file.csv}")
    private String filepath;

   public List<Product> getProducts() throws IOException {
       ObjectReader oReader = new CsvMapper().readerWithSchemaFor(Product.class).with(CsvSchema.emptySchema().withHeader());

       MappingIterator<Product> personIter = oReader.readValues(new FileInputStream(filepath));
       return personIter.readAll();
    }

}
