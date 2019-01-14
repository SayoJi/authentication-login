package com.sayo.authlogin;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthLoginApplicationTests {

    @Test
    public void contextLoads() throws IOException {

//        String dest = "C:/MY/merge.pdf";
//        Image image;
//        List<byte[]> pdfs = new ArrayList<>();
//        pdfs.add(this.getBytePdf("C:/ideaProject/PDFViewer/index.pdf"));
//        pdfs.add(this.getBytePdf("C:/ideaProject/PDFViewer/index.pdf"));
//
//
//        try {
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream(dest));
//            document.open();
//
//            for (byte[] pdf : pdfs) {
//                image = Image.getInstance(pdf);
//                document.setPageSize(image);
//                document.newPage();
//                image.setAbsolutePosition(0, 0);
//                document.add(image);
//            }
//        } catch (DocumentException de) {
//            de.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            List<byte[]> pdfs = new ArrayList<byte[]>();
            pdfs.add(this.getBytePdf("C:/ideaProject/PDFViewer/index.pdf"));
            pdfs.add(this.getBytePdf("C:/ideaProject/PDFViewer/index.pdf"));
            OutputStream output = new FileOutputStream("c:/merge.pdf");
            this.concatPDFs(pdfs, output, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] getBytePdf(String path) throws IOException {
//        Path pdfPath = Paths.get(path);
//        byte[] data = Files.readAllBytes(pdfPath);
//        return data;
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        fis.read(data);
        fis.close();
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        bos.write(data);
//        data =  bos.toByteArray();
        return data;
    }

    public void concatPDFs(List<byte[]> streamOfPDFFiles,
                           OutputStream outputStream, boolean paginate) throws FileNotFoundException {

        OutputStream testoOutput = new FileOutputStream("c:/merge123.pdf");

        Document document = new Document();
        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        try {
            List<byte[]> pdfs = streamOfPDFFiles;
            byte[] combined = null;
            List<PdfReader> readers = new ArrayList<PdfReader>();
            int totalPages = 0;
            Iterator<byte[]> iteratorPDFs = pdfs.iterator();

            // Create Readers for the pdfs.
            while (iteratorPDFs.hasNext()) {
                byte[] pdf = iteratorPDFs.next();
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
            // Create a writer for the outputstream
            PdfWriter writer = PdfWriter.getInstance(document, outputStream1);

            document.open();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
                    BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
            // data

            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();


            // Loop through the PDF files and add to the output.
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();

                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader,
                            pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);

                    // Code for pagination.
                    if (paginate) {
                        cb.beginText();
                        cb.setFontAndSize(bf, 9);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, ""
                                        + currentPageNumber + " of " + totalPages, 520,
                                5, 0);
                        cb.endText();
                    }
                    byte[] byte1 = pdfReader.getPageContent(pageOfCurrentReaderPDF);
                    if(pageOfCurrentReaderPDF == 0){
                        combined = this.combineByteArray(byte1, null);
                    }else {
                        combined = this.combineByteArray(byte1, combined);
                    }


                }
                pageOfCurrentReaderPDF = 0;
            }
            testoOutput.write(outputStream1.toByteArray());
            testoOutput.flush();
            testoOutput.close();
            outputStream1.flush();
            document.close();
            outputStream1.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen())
                document.close();
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private byte[] combineByteArray(byte[] byte1, byte[] byte2) {
        byte[] combine = null;
        if (byte2 == null){
            combine = new byte[byte1.length];
            System.arraycopy(byte1,0,combine,0,byte1.length);
        }else {
            combine = new byte[byte1.length + byte2.length];
            System.arraycopy(byte1,0,combine,0,byte1.length);
            System.arraycopy(byte2,0,combine,byte1.length,byte2.length);
        }

        return combine;
    }

}
