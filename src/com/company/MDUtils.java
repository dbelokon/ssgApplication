package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MDUtils {
    public static void createHTMLFromMd(File file) throws IOException {
        String convertedText = MDUtils.readFile(file);

        String title = convertedText.split("<br/>")[0].replace("# ","");

        String body = MDUtils.getBodyFromText(convertedText.split("<br/>"));


        // Complete HTML file
        String html = "<!doctype html>\r\n"
                + "<html lang=\"en\">\r\n"
                + "<head>\r\n"
                + "  <meta charset=\"utf-8\">\r\n"
                + "  <title>"
                + title
                + "</title>\r\n"
                + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
                + "</head>\r\n"
                + "<body>\r\n"
                + body
                + "</body>\r\n"
                + "</html>\r\n"
                + "";

        // write File
        String htmlFileName = "dist\\" + title + ".html";

        Path path = Paths.get(htmlFileName);

        byte[] strToBytes = html.getBytes();

        Files.write(path, strToBytes);

    }

    public static String readFile(File file){
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.equals("")) {
                    sb.append("<br/>");
                } else {
                    sb.append(line).append(" ");
                }
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getBodyFromText(String[] lines){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < lines.length; i++){
            if(lines[i].substring(0,2).equals("# ")){
                sb.append("<h1>").append(lines[i].replace("# ","")).append("</h1>").append("<br/>");
            }
            else if(lines[i].length()==0){
                sb.append("<br/>");
            }
            else{
                sb.append("<p>").append(lines[i]).append("</p>").append("<br/>");
            }
        }
        return sb.toString();
    }
}
