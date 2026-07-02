package com.example.ApplicationService.AI;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ResumeParserService {
    public String extractText(
            String filePath)
            throws Exception {

        File file =
                new File(filePath);

        PDDocument document =
                Loader.loadPDF(file);

        PDFTextStripper stripper =
                new PDFTextStripper();

        String text =
                stripper.getText(document);

        document.close();

        return text;
    }
}
